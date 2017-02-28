package com.huynn109.fifaonline3addict.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerR;
import com.huynn109.fifaonline3addict.data.model.realm.PositionR;
import com.huynn109.fifaonline3addict.ui.viewholder.PlayerViewHolder;
import com.huynn109.fifaonline3addict.util.Season;
import com.huynn109.fifaonline3addict.util.URL;
import java.util.List;

/**
 * Created by huyuit on 1/27/2017.
 */

public class PlayerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public static final String TAG = PlayerListAdapter.class.getSimpleName();

  public interface OnItemClickListener {

    void onItemClick(PlayerR player);
  }

  private OnItemClickListener listener;

  private List<PlayerR> mPlayerList;
  private Context mContext;

  public PlayerListAdapter(Context context, List<PlayerR> playerList,
      OnItemClickListener listener) {
    this.mContext = context;
    this.mPlayerList = playerList;
    this.listener = listener;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rowPlayerView = LayoutInflater.from(mContext).inflate(R.layout.row_player, parent, false);
    return new PlayerViewHolder(rowPlayerView);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((PlayerViewHolder) holder).linearPosition1.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).linearPosition2.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).linearPosition3.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).linearPosition4.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).imageStar1.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).imageStar2.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).imageStar3.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).imageStar4.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).imageStar5.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).textViewRosterUpdate.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).textViewLiveBoost.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).imageViewSeasonWb.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).imageViewSeasonWl.setVisibility(View.GONE);
    ((PlayerViewHolder) holder).imageViewSeason.setVisibility(View.GONE);

    if (!TextUtils.isEmpty(mPlayerList.get(position).liveBoost)) {
      ((PlayerViewHolder) holder).textViewLiveBoost.setVisibility(View.VISIBLE);
      ((PlayerViewHolder) holder).textViewLiveBoost.setText(mPlayerList.get(position).liveBoost);
    }
    if (!TextUtils.isEmpty(mPlayerList.get(position).rosterUpdate)) {
      ((PlayerViewHolder) holder).textViewRosterUpdate.setVisibility(View.VISIBLE);
      ((PlayerViewHolder) holder).textViewRosterUpdate.setText(
          "+" + mPlayerList.get(position).rosterUpdate);
    }

    if (!TextUtils.isEmpty(mPlayerList.get(position).season)) {
      ((PlayerViewHolder) holder).textViewSeason.setBackgroundColor(
          ContextCompat.getColor(mContext, android.R.color.background_dark));
      ((PlayerViewHolder) holder).textViewSeason.setTextColor(
          ContextCompat.getColor(mContext, android.R.color.primary_text_dark));
      if (mPlayerList.get(position).season.equals(Season.getSeasonFromClass(Season.season16))) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).textViewSeason.setText("16");
      } else if (mPlayerList.get(position).season.equals(Season.season15)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).textViewSeason.setText("15");
      } else if (mPlayerList.get(position).season.equals(Season.season14)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).textViewSeason.setText("14");
      } else if (mPlayerList.get(position).season.equals(Season.season06Wc) || mPlayerList.get(
          position).season.equals(Season.season14Wc) || mPlayerList.get(position).season.equals(
          Season.season10Wc)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        if (mPlayerList.get(position).season.equals(Season.season06Wc)) {
          ((PlayerViewHolder) holder).textViewSeason.setText("06");
        } else if (mPlayerList.get(position).season.equals(Season.season14Wc)) {
          ((PlayerViewHolder) holder).textViewSeason.setText("14");
        } else {
          ((PlayerViewHolder) holder).textViewSeason.setText("10");
        }
        ((PlayerViewHolder) holder).textViewSeason.setTextColor(
            ContextCompat.getColor(mContext, R.color.color_green_wc));
      } else if (mPlayerList.get(position).season.equals(Season.seasonWb)) {
        ((PlayerViewHolder) holder).imageViewSeasonWb.setVisibility(View.VISIBLE);
      } else if (mPlayerList.get(position).season.equals(Season.seasonLp)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        String text = "<font color=#ffffff>L</font><font color=#e4ac40>P</font>";
        ((PlayerViewHolder) holder).textViewSeason.setText(Html.fromHtml(text));
        ((PlayerViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.color_green_lp));
      } else if (mPlayerList.get(position).season.equals(Season.seasonEc)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        String text = "<font color=#D5D7D6>E</font><font color=#DFBE32>C</font>";
        ((PlayerViewHolder) holder).textViewSeason.setText(Html.fromHtml(text));
        ((PlayerViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.color_green_ec));
      } else if (mPlayerList.get(position).season.equals(Season.season06)
          || mPlayerList.get(position).season.equals(Season.season07)
          || mPlayerList.get(position).season.equals(Season.season08)
          || mPlayerList.get(position).season.equals(Season.season09)
          || mPlayerList.get(position).season.equals(Season.season10)
          || mPlayerList.get(position).season.equals(Season.season11)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).textViewSeason.setText(
            mPlayerList.get(position).season.split("y20")[1]);
        ((PlayerViewHolder) holder).textViewSeason.setTextColor(
            ContextCompat.getColor(mContext, R.color.color_yellow_season));
      } else if (mPlayerList.get(position).season.equals(Season.season08Eu)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).textViewSeason.setText("08");
        ((PlayerViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, android.R.color.holo_red_dark));
      } else if (mPlayerList.get(position).season.equals(Season.season14T)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).textViewSeason.setText("14");
        ((PlayerViewHolder) holder).textViewSeason.setTextColor(
            ContextCompat.getColor(mContext, R.color.yellow_14t));
        ((PlayerViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.blu_14t));
      } else if (mPlayerList.get(position).season.equals(Season.season06U) || mPlayerList.get(
          position).season.equals(Season.season10U)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        if (mPlayerList.get(position).season.equals(Season.season06U)) {
          ((PlayerViewHolder) holder).textViewSeason.setText("06");
        } else {
          ((PlayerViewHolder) holder).textViewSeason.setText("10");
        }
        ((PlayerViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.blu_06u));
      } else if (mPlayerList.get(position).season.equals(Season.season15TauKhua)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).textViewSeason.setText("15");
        ((PlayerViewHolder) holder).textViewSeason.setBackgroundColor(
            ContextCompat.getColor(mContext, R.color.red_taukhua));
      } else if (mPlayerList.get(position).season.equals(Season.season16W)) {
        ((PlayerViewHolder) holder).textViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).textViewSeason.setText("W");
        ((PlayerViewHolder) holder).textViewSeason.setTextColor(
            ContextCompat.getColor(mContext, R.color.yellow_16w));
      } else if (mPlayerList.get(position).season.equals(Season.seasonWl)) {
        ((PlayerViewHolder) holder).imageViewSeasonWl.setVisibility(View.VISIBLE);
      } else if (mPlayerList.get(position).season.equals(Season.seasonMuLegend) || mPlayerList.get(
          position).season.equals(Season.seasonTc92)) {
        ((PlayerViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.mu_logo));
      } else if (mPlayerList.get(position).season.equals(Season.seasonTlLegend)) {
        ((PlayerViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.t_legend));
      } else if (mPlayerList.get(position).season.equals(Season.season16Th)) {
        ((PlayerViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.th16));
      } else if (mPlayerList.get(position).season.equals(Season.seasonVnLegend)) {
        ((PlayerViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.v_legend));
      } else if (mPlayerList.get(position).season.equals(Season.seasonMalayLegend)) {
        ((PlayerViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.m_legend));
      } else if (mPlayerList.get(position).season.equals(Season.seasonU23)) {
        ((PlayerViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.u23));
      } else if (mPlayerList.get(position).season.equals(Season.seasonKxi)) {
        ((PlayerViewHolder) holder).imageViewSeason.setVisibility(View.VISIBLE);
        ((PlayerViewHolder) holder).imageViewSeason.setImageDrawable(
            mContext.getResources().getDrawable(R.drawable.kxi));
      }
    }

    Glide.with(mContext)
        .load(URL.getImageUrlFromId(mPlayerList.get(position).imageId))
        .into(((PlayerViewHolder) holder).imageAvatarPlayer);

    ((PlayerViewHolder) holder).textPlayerName.setText(mPlayerList.get(position).name);
    //((PlayerViewHolder) holder).textPositionActive.setText(
    //    mPlayerList.get(position).positions.get(0).getValue());
    //switch (mPlayerList.get(position).getSkillmoves()) {
    //  case 1:
    //    ((PlayerViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
    //    break;
    //  case 2:
    //    ((PlayerViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar2.setVisibility(View.VISIBLE);
    //    break;
    //  case 3:
    //    ((PlayerViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar2.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar3.setVisibility(View.VISIBLE);
    //    break;
    //  case 4:
    //    ((PlayerViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar2.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar3.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar4.setVisibility(View.VISIBLE);
    //    break;
    //  case 5:
    //    ((PlayerViewHolder) holder).imageStar1.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar2.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar3.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar4.setVisibility(View.VISIBLE);
    //    ((PlayerViewHolder) holder).imageStar5.setVisibility(View.VISIBLE);
    //    break;
    //}
    //
    //for (int i = 0; i < mPlayerList.get(position).positions.size(); i++) {
    //  PositionR positionPlayer = mPlayerList.get(position).positions.get(i);
    //  switch (i) {
    //    case 0:
    //      ((PlayerViewHolder) holder).linearPosition1.setVisibility(View.VISIBLE);
    //      handleBackgroudPosiotion(((PlayerViewHolder) holder).textPosition1Name,
    //          positionPlayer.getName());
    //      setNameAndValue(((PlayerViewHolder) holder).textPosition1Name,
    //          ((PlayerViewHolder) holder).textPosition1Value, positionPlayer);
    //      break;
    //    case 1:
    //      ((PlayerViewHolder) holder).linearPosition2.setVisibility(View.VISIBLE);
    //      handleBackgroudPosiotion(((PlayerViewHolder) holder).textPosition2Name,
    //          positionPlayer.getName());
    //      setNameAndValue(((PlayerViewHolder) holder).textPosition2Name,
    //          ((PlayerViewHolder) holder).textPosition2Value, positionPlayer);
    //      break;
    //    case 2:
    //      ((PlayerViewHolder) holder).linearPosition3.setVisibility(View.VISIBLE);
    //      handleBackgroudPosiotion(((PlayerViewHolder) holder).textPosition3Name,
    //          positionPlayer.getName());
    //      setNameAndValue(((PlayerViewHolder) holder).textPosition3Name,
    //          ((PlayerViewHolder) holder).textPosition3Value, positionPlayer);
    //      break;
    //    case 3:
    //      ((PlayerViewHolder) holder).linearPosition4.setVisibility(View.VISIBLE);
    //      handleBackgroudPosiotion(((PlayerViewHolder) holder).textPosition4Name,
    //          positionPlayer.getName());
    //      setNameAndValue(((PlayerViewHolder) holder).textPosition4Name,
    //          ((PlayerViewHolder) holder).textPosition4Value, positionPlayer);
    //      break;
    //  }
    //}
    ((PlayerViewHolder) holder).bind(mPlayerList.get(position), listener);
  }

  @Override public int getItemCount() {
    return mPlayerList.size();
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
}
