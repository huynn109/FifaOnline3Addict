package com.huynn109.fifaonline3addict.ui.viewholder;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;

/**
 * Created by huyuit on 3/3/2017.
 */

public class ThemeViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.icon) ImageView vIcon;

  @BindView(R.id.title) TextView vTitle;

  private int mId;

  public ThemeViewHolder(View itemView, OnItemClickListener onItemClickListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    itemView.setOnClickListener(v -> {
      if (onItemClickListener != null) {
        onItemClickListener.onClick(v, mId);
      }
    });
  }

  public void bind(int id, @DrawableRes int icon, String title) {
    mId = id;
    vIcon.setImageResource(icon);
    vTitle.setText(title);
  }

  public interface OnItemClickListener {

    void onClick(View view, int id);
  }
}
