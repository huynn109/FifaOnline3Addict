package com.huynn109.fifaonline3addict.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.config.Constants;
import com.huynn109.fifaonline3addict.ui.adapter.ThemeAdapter;
import com.huynn109.fifaonline3addict.util.ThemeUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import me.henrytao.mdcore.core.MdCore;

/**
 * Created by huyuit on 3/3/2017.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

  private BottomSheetDialog dialog;

  @LayoutRes protected abstract int getDefaultLayout();

  @LayoutRes protected abstract int getMdCoreLayout();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    ThemeUtils.onCreate(this);
    boolean isMdCoreEnabled = isMdCoreEnabled();
    if (isMdCoreEnabled) {
      MdCore.init(this);
    }
    super.onCreate(savedInstanceState);
    setContentView(isMdCoreEnabled ? getMdCoreLayout() : getDefaultLayout());
  }

  public boolean isMdCoreEnabled() {
    return this instanceof MainActivity || getIntent().getBooleanExtra(
        Constants.Extra.IS_MD_CORE_ENABLED, false);
  }

  protected void showThemePicker() {
    dialog = new BottomSheetDialog(this);
    View view = getLayoutInflater().inflate(R.layout.custom_color_dialog, null, false);
    RecyclerView recyclerView = (RecyclerView) view.findViewById(android.R.id.list);
    recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    recyclerView.setAdapter(new ThemeAdapter((v, id) -> ThemeUtils.changeToTheme(this, id)));
    dialog.setContentView(view);
    dialog.show();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (dialog != null && dialog.isShowing()) {
      dialog.dismiss();
    }
  }
}
