package com.huynn109.fifaonline3addict.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerR;
import com.huynn109.fifaonline3addict.data.model.realm.PositionR;
import com.huynn109.fifaonline3addict.util.Season;
import com.huynn109.fifaonline3addict.util.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyuit on 2/12/2017.
 */

public class PlayerListRealmAdapter
    extends RecyclerView.Adapter<PlayerListRealmAdapter.ViewHolder> {
  public static final String TAG = PlayerListRealmAdapter.class.getSimpleName();



  public interface OnItemClickListener {

    void onItemClick(PlayerR player);
  }

  private OnItemClickListener listener;
  private final Context mContext;
  private List<PlayerR> playerRs = new ArrayList<>();

  public PlayerListRealmAdapter(Context context, List<PlayerR> realmResults,
      PlayerListRealmAdapter.OnItemClickListener listener) {
    this.mContext = context;
    this.listener = listener;
    Log.d(TAG, "PlayerListRealmAdapter: " + realmResults.size());
    Log.d(TAG, "PlayerListRealmAdapter: " + realmResults.get(0).name);
    this.playerRs = realmResults;
    Log.d(TAG, "PlayerListRealmAdapter: " + playerRs.get(0).name);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rowPlayerView = LayoutInflater.from(mContext).inflate(R.layout.row_player, parent, false);
    return new ViewHolder(rowPlayerView);
  }


  @Override public int getItemCount() {
    Log.d(TAG, "getItemCount: " + playerRs.size());
    return playerRs.size();
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    final PlayerR player = playerRs.get(position);
    ((ViewHolder) holder).linearPosition1.setVisibility(View.GONE);
    ((ViewHolder) holder).linearPosition2.setVisibility(View.GONE);
    ((ViewHolder) holder).linearPosition3.setVisibility(View.GONE);
    ((ViewHolder) holder).linearPosition4.setVisibility(View.GONE);
    ((ViewHolder) holder).imageStar1.setVisibility(View.GONE);
    ((ViewHolder) holder).imageStar2.setVisibility(View.GONE);
    ((ViewHolder) holder).imageStar3.setVisibility(View.GONE);
    ((ViewHolder) holder).imageStar4.setVisibility(View.GONE);
    ((ViewHolder) holder).imageStar5.setVisibility(View.GONE);
    ((ViewHolder) holder).textViewRosterUpdate.setVisibility(View.GONE);
    ((ViewHolder) holder).textViewLiveBoost.setVisibility(View.GONE);
    ((ViewHolder) holder).textViewSeason.setVisibility(View.GONE);
    ((ViewHolder) holder).imageViewSeasonWb.setVisibility(View.GONE);
    ((ViewHolder) holder).imageViewSeasonWl.setVisibility(View.GONE);
    ((ViewHolder) holder).imageViewSeason.setVisibility(View.GONE);

    if (!TextUtils.isEmpty(player.liveBoost)) {
      ((ViewHolder) holder).textViewLiveBoost.setVisibility(View.VISIBLE);
      ((ViewHolder) holder).textViewLiveBoost.setText(player.liveBoost);
    }
    if (!TextUtils.isEmpty(player.rosterUpdate)) {
      ((ViewHolder) holder).textViewRosterUpdate.setVisibility(View.VISIBLE);
      ((ViewHolder) holder).textViewRosterUpdate.setText("+" + player.rosterUpdate);
    }

    if (!TextUtils.isEmpty(player.season)) {
      ((ViewHolder) holder).textViewSeason.setBackgroundColor(
          ContextCompat.getColor(mContext, android.R.color.background_dark));
      ((ViewHolder) holder).textViewSeason.setTextColor(
          ContextCompat.getColor(mContext, android.R.color.primary_text_dark));
      if (player.season.equals(Season.getSeasonFromClass(Season.season16))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).textViewSeason.setText("16");
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season15))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).textViewSeason.setText("15");
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season14))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).textViewSeason.setText("14");
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season06Wc))
          || player.season.equals(Season.getSeasonFromClass(Season.season14Wc))
          || player.season.equals(Season.getSeasonFromClass(Season.season10Wc))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        if (player.season.equals(Season.getSeasonFromClass(Season.season06Wc))) {
          ((ViewHolder) holder).textViewSeason.setText("06");
        } else if (player.season.equals(Season.getSeasonFromClass(Season.season14Wc))) {
          ((ViewHolder) holder).textViewSeason.setText("14");
        } else {
          ((ViewHolder) holder).textViewSeason.setText("10");
        }
        ((ViewHolder) holder).textViewSeason.setTextColor(
            ContextCompat.getColor(mContext, R.color.color_green_wc));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonWb))) {
        ((ViewHolder) holder).imageViewSeasonWb.setVisibility(View.VISIBLE);
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonLp))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        String text = "<font color=#ffffff>L</font><font color=#e4ac40>P</font>";
        ((ViewHolder) holder).textViewSeason.setText(Html.fromHtml(text));
        ((ViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.color_green_lp));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonEc))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        String text = "<font color=#D5D7D6>E</font><font color=#DFBE32>C</font>";
        ((ViewHolder) holder).textViewSeason.setText(Html.fromHtml(text));
        ((ViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.color_green_ec));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season06))
          || player.season
          .equals(Season.getSeasonFromClass(Season.season07))
          || player.season.equals(Season.getSeasonFromClass(Season.season08))
          || player.season.equals(Season.getSeasonFromClass(Season.season09))
          || player.season.equals(Season.getSeasonFromClass(Season.season10))
          || player.season.equals(Season.getSeasonFromClass(Season.season11))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).textViewSeason.setText(player.season.split("20")[1]);
        ((ViewHolder) holder).textViewSeason.setTextColor(
            ContextCompat.getColor(mContext, R.color.color_yellow_season));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season08Eu))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).textViewSeason.setText("08");
        ((ViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, android.R.color.holo_red_dark));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season14T))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).textViewSeason.setText("14");
        ((ViewHolder) holder).textViewSeason.setTextColor(
            ContextCompat.getColor(mContext, R.color.yellow_14t));
        ((ViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.blu_14t));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season06U)) || player.season
          .equals(Season.getSeasonFromClass(Season.season10U))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        if (player.season.equals(Season.getSeasonFromClass(Season.season06U))) {
          ((ViewHolder) holder).textViewSeason.setText("06");
        } else {
          ((ViewHolder) holder).textViewSeason.setText("10");
        }
        ((ViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.blu_06u));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season15TauKhua))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).textViewSeason.setText("15");
        ((ViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.red_taukhua));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season16W))) {
        ((ViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).textViewSeason.setText("W");
        ((ViewHolder) holder).textViewSeason.setTextColor(
            ContextCompat.getColor(mContext, R.color.yellow_16w));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonWl))) {
        ((ViewHolder) holder).imageViewSeasonWl.setVisibility(View.VISIBLE);
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonMuLegend)) || player.season
          .equals(Season.getSeasonFromClass(Season.seasonTc92))) {
        ((ViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.mu_logo));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonTlLegend))) {
        ((ViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.t_legend));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.season16Th))) {
        ((ViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.th16));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonVnLegend))) {
        ((ViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.v_legend));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonMalayLegend))) {
        ((ViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.m_legend));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonU23))) {
        ((ViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.u23));
      } else if (player.season.equals(Season.getSeasonFromClass(Season.seasonKxi))) {
        ((ViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.kxi));
      }
    }

    Glide.with(mContext)
        .load(URL.getImageUrlFromId(player.imageId))
        .into(((ViewHolder) holder).imageAvatarPlayer);

    ((ViewHolder) holder).textPlayerName.setText(player.name);
    ((ViewHolder) holder).textPositionActive.setText(player.positions.get(0).getValue());
    switch (player.skillmoves) {
      case 1:
        ((ViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
        break;
      case 2:
        ((ViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar2.setVisibility(View.VISIBLE);
        break;
      case 3:
        ((ViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar2.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar3.setVisibility(View.VISIBLE);
        break;
      case 4:
        ((ViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar2.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar3.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar4.setVisibility(View.VISIBLE);
        break;
      case 5:
        ((ViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar2.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar3.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar4.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).imageStar5.setVisibility(View.VISIBLE);
        break;
    }

    for (int i = 0; i < player.positions.size(); i++) {
      PositionR positionPlayer = player.positions.get(i);
      switch (i) {
        case 0:
          ((ViewHolder) holder).linearPosition1.setVisibility(View.VISIBLE);
          handleBackgroudPosiotion(((ViewHolder) holder).textPosition1Name,
              positionPlayer.getName());
          setNameAndValue(((ViewHolder) holder).textPosition1Name,
              ((ViewHolder) holder).textPosition1Value, positionPlayer);
          break;
        case 1:
          ((ViewHolder) holder).linearPosition2.setVisibility(View.VISIBLE);
          handleBackgroudPosiotion(((ViewHolder) holder).textPosition2Name,
              positionPlayer.getName());
          setNameAndValue(((ViewHolder) holder).textPosition2Name,
              ((ViewHolder) holder).textPosition2Value, positionPlayer);
          break;
        case 2:
          ((ViewHolder) holder).linearPosition3.setVisibility(View.VISIBLE);
          handleBackgroudPosiotion(((ViewHolder) holder).textPosition3Name,
              positionPlayer.getName());
          setNameAndValue(((ViewHolder) holder).textPosition3Name,
              ((ViewHolder) holder).textPosition3Value, positionPlayer);
          break;
        case 3:
          ((ViewHolder) holder).linearPosition4.setVisibility(View.VISIBLE);
          handleBackgroudPosiotion(((ViewHolder) holder).textPosition4Name,
              positionPlayer.getName());
          setNameAndValue(((ViewHolder) holder).textPosition4Name,
              ((ViewHolder) holder).textPosition4Value, positionPlayer);
          break;
      }
    }
    ((ViewHolder) holder).bind(player, listener);
  }

  private void setNameAndValue(TextView textViewName, TextView textViewValue, PositionR position) {
    textViewName.setText(position.getName());
    textViewValue.setText(position.getValue());
  }

  private void handleBackgroudPosiotion(TextView textViewName, String positionName) {
    if (positionName.equals("st")
        || positionName.equals("lw")
        || positionName.equals("rw")
        || positionName.equals("ls")
        || positionName.equals("rs")
        || positionName.equals("lf")
        || positionName.equals("rf")
        || positionName.equals("cf")) {
      textViewName.setBackgroundResource(R.drawable.position_st);
    } else if (positionName.equals("cam")
        || positionName.equals("cm")
        || positionName.equals("rm")
        || positionName.equals("lm")
        || positionName.equals("cdm")) {
      textViewName.setBackgroundResource(R.drawable.position_cm);
    } else if (positionName.equals("cb")
        || positionName.equals("rb")
        || positionName.equals("lb")
        || positionName.equals("rwb")
        || positionName.equals("lwb")
        || positionName.equals("sw")) {
      textViewName.setBackgroundResource(R.drawable.position_cb);
    } else if (positionName.equals("gk")) {
      textViewName.setBackgroundResource(R.drawable.position_gk);
    }
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
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

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bind(final PlayerR item, final PlayerListRealmAdapter.OnItemClickListener listener) {
      itemView.setOnClickListener(v -> listener.onItemClick(item));
    }
  }
}
