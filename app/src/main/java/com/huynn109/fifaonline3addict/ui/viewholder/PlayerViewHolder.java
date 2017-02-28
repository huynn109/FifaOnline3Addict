package com.huynn109.fifaonline3addict.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerR;
import com.huynn109.fifaonline3addict.ui.adapter.PlayerListAdapter;

/**
 * Created by huyuit on 1/28/2017.
 */
public class PlayerViewHolder extends RecyclerView.ViewHolder {
  public @BindView(R.id.text_view_title) TextView textPlayerName;
  public @BindView(R.id.text_view_value) TextView textPositionActive;
  public @BindView(R.id.image_avatar_player) ImageView imageAvatarPlayer;

  public @BindView(R.id.linear_1) LinearLayout linearPosition1;
  public @BindView(R.id.text_view_position_1_name) TextView textPosition1Name;
  public @BindView(R.id.text_view_position_1_value) TextView textPosition1Value;

  public @BindView(R.id.linear_2) LinearLayout linearPosition2;
  public @BindView(R.id.text_view_position_2_name) TextView textPosition2Name;
  public @BindView(R.id.text_view_position_2_value) TextView textPosition2Value;

  public @BindView(R.id.linear_3) LinearLayout linearPosition3;
  public @BindView(R.id.text_view_position_3_name) TextView textPosition3Name;
  public @BindView(R.id.text_view_position_3_value) TextView textPosition3Value;

  public @BindView(R.id.linear_4) LinearLayout linearPosition4;
  public @BindView(R.id.text_view_position_4_name) TextView textPosition4Name;
  public @BindView(R.id.text_view_position_4_value) TextView textPosition4Value;

  public @BindView(R.id.linear_star) LinearLayout linearStar;
  public @BindView(R.id.img_star_1) ImageView imageStar1;
  public @BindView(R.id.img_star_2) ImageView imageStar2;
  public @BindView(R.id.img_star_3) ImageView imageStar3;
  public @BindView(R.id.img_star_4) ImageView imageStar4;
  public @BindView(R.id.img_star_5) ImageView imageStar5;

  public @BindView(R.id.text_view_roster_update) TextView textViewRosterUpdate;
  public @BindView(R.id.text_view_live_boost) TextView textViewLiveBoost;
  public @BindView(R.id.text_season) TextView textViewSeason;
  public @BindView(R.id.image_season_wb) ImageView imageViewSeasonWb;
  public @BindView(R.id.image_season_wl) ImageView imageViewSeasonWl;
  public @BindView(R.id.image_season) ImageView imageViewSeason;

  public PlayerViewHolder(View rowPlayerView) {
    super(rowPlayerView);
    ButterKnife.bind(this, rowPlayerView);
  }

  public void bind(final PlayerR item, final PlayerListAdapter.OnItemClickListener listener) {
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onItemClick(item);
      }
    });
  }
}
