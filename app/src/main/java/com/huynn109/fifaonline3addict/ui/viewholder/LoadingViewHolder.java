package com.huynn109.fifaonline3addict.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;

/**
 * Created by huyuit on 1/28/2017.
 */
public class LoadingViewHolder extends RecyclerView.ViewHolder {
  public @BindView(R.id.progress_loading_view) ProgressBar mProgressBar;
  public LoadingViewHolder(View loadingView) {
    super(loadingView);
    ButterKnife.bind(this, loadingView);
  }
}
