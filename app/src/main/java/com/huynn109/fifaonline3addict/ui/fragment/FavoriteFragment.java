package com.huynn109.fifaonline3addict.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.ui.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

  private View rootView;
  private MainActivity mainActivity;

  public FavoriteFragment() {
    // Required empty public constructor
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof MainActivity) {
      mainActivity = (MainActivity) context;
    }
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
    if (menu.findItem(R.id.action_search) != null) {
      menu.findItem(R.id.action_search).setVisible(false);
    }
  }
}
