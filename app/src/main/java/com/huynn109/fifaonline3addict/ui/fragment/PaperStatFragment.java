package com.huynn109.fifaonline3addict.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerR;
import com.huynn109.fifaonline3addict.ui.activity.PlayerDetailActivity;
import io.realm.Realm;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaperStatFragment extends Fragment {

  private static final String TAG = PaperStatFragment.class.getSimpleName();
  private static final String BUNDLE_STRING_ID_PLAYER = "BUNDLE_STRING_ID_PLAYER";
  @BindView(R.id.text_name_sprintspeed) TextView textNameSprintspeed;
  @BindView(R.id.text_value_sprintspeed) TextView textValueSprintspeed;
  @BindView(R.id.relative_sprintspeed) RelativeLayout relativeSprintspeed;
  @BindView(R.id.text_name_acceleration) TextView textNameAcceleration;
  @BindView(R.id.text_value_acceleration) TextView textValueAcceleration;
  @BindView(R.id.relative_acceleration) RelativeLayout relativeAcceleration;
  @BindView(R.id.text_name_finishing) TextView textNameFinishing;
  @BindView(R.id.text_value_finishing) TextView textValueFinishing;
  @BindView(R.id.relative_finishing) RelativeLayout relativeFinishing;
  @BindView(R.id.text_name_shotpower) TextView textNameShotpower;
  @BindView(R.id.text_value_shotpower) TextView textValueShotpower;
  @BindView(R.id.relative_shotpower) RelativeLayout relativeShotpower;
  @BindView(R.id.text_name_curve) TextView textNameCurve;
  @BindView(R.id.text_value_curve) TextView textValueCurve;
  @BindView(R.id.relative_curve) RelativeLayout relativeCurve;
  @BindView(R.id.text_name_longshots) TextView textNameLongshots;
  @BindView(R.id.text_value_longshots) TextView textValueLongshots;
  @BindView(R.id.relative_longshots) RelativeLayout relativeLongshots;
  @BindView(R.id.text_name_volleys) TextView textNameVolleys;
  @BindView(R.id.text_value_volleys) TextView textValueVolleys;
  @BindView(R.id.relative_volleys) RelativeLayout relativeVolleys;
  @BindView(R.id.text_name_freekickaccuracy) TextView textNameFreekickaccuracy;
  @BindView(R.id.text_value_freekickaccuracy) TextView textValueFreekickaccuracy;
  @BindView(R.id.relative_freekickaccuracy) RelativeLayout relativeFreekickaccuracy;
  @BindView(R.id.text_name_penalties) TextView textNamePenalties;
  @BindView(R.id.text_value_penalties) TextView textValuePenalties;
  @BindView(R.id.relative_penalties) RelativeLayout relativePenalties;
  @BindView(R.id.text_name_headingaccuracy) TextView textNameHeadingaccuracy;
  @BindView(R.id.text_value_headingaccuracy) TextView textValueHeadingaccuracy;
  @BindView(R.id.relative_headingaccuracy) RelativeLayout relativeHeadingaccuracy;
  @BindView(R.id.text_name_positioning) TextView textNamePositioning;
  @BindView(R.id.text_value_positioning) TextView textValuePositioning;
  @BindView(R.id.relative_positioning) RelativeLayout relativePositioning;
  @BindView(R.id.text_name_agility) TextView textNameAgility;
  @BindView(R.id.text_value_agility) TextView textValueAgility;
  @BindView(R.id.relative_agility) RelativeLayout relativeAgility;
  @BindView(R.id.text_name_reactions) TextView textNameReactions;
  @BindView(R.id.text_value_reactions) TextView textValueReactions;
  @BindView(R.id.relative_reactions) RelativeLayout relativeReactions;
  @BindView(R.id.text_name_jumping) TextView textNameJumping;
  @BindView(R.id.text_value_jumping) TextView textValueJumping;
  @BindView(R.id.relative_jumping) RelativeLayout relativeJumping;
  @BindView(R.id.text_name_stamina) TextView textNameStamina;
  @BindView(R.id.text_value_stamina) TextView textValueStamina;
  @BindView(R.id.relative_stamina) RelativeLayout relativeStamina;
  @BindView(R.id.text_name_strength) TextView textNameStrength;
  @BindView(R.id.text_value_strength) TextView textValueStrength;
  @BindView(R.id.relative_strength) RelativeLayout relativeStrength;
  @BindView(R.id.text_name_balance) TextView textNameBalance;
  @BindView(R.id.text_value_balance) TextView textValueBalance;
  @BindView(R.id.relative_balance) RelativeLayout relativeBalance;
  @BindView(R.id.text_name_shortpassing) TextView textNameShortpassing;
  @BindView(R.id.text_value_shortpassing) TextView textValueShortpassing;
  @BindView(R.id.relative_shortpassing) RelativeLayout relativeShortpassing;
  @BindView(R.id.text_name_longpassing) TextView textNameLongpassing;
  @BindView(R.id.text_value_longpassing) TextView textValueLongpassing;
  @BindView(R.id.relative_longpassing) RelativeLayout relativeLongpassing;
  @BindView(R.id.text_name_crossing) TextView textNameCrossing;
  @BindView(R.id.text_value_crossing) TextView textValueCrossing;
  @BindView(R.id.relative_crossing) RelativeLayout relativeCrossing;
  @BindView(R.id.text_name_ballcontrol) TextView textNameBallcontrol;
  @BindView(R.id.text_value_ballcontrol) TextView textValueBallcontrol;
  @BindView(R.id.relative_ballcontrol) RelativeLayout relativeBallcontrol;
  @BindView(R.id.text_name_dribbling) TextView textNameDribbling;
  @BindView(R.id.text_value_dribbling) TextView textValueDribbling;
  @BindView(R.id.relative_dribbling) RelativeLayout relativeDribbling;
  @BindView(R.id.text_name_tacticalawareness) TextView textNameTacticalawareness;
  @BindView(R.id.text_value_tacticalawareness) TextView textValueTacticalawareness;
  @BindView(R.id.relative_tacticalawareness) RelativeLayout relativeTacticalawareness;
  @BindView(R.id.text_name_vision) TextView textNameVision;
  @BindView(R.id.text_value_vision) TextView textValueVision;
  @BindView(R.id.relative_vision) RelativeLayout relativeVision;
  @BindView(R.id.text_name_standingtackle) TextView textNameStandingtackle;
  @BindView(R.id.text_value_standingtackle) TextView textValueStandingtackle;
  @BindView(R.id.relative_standingtackle) RelativeLayout relativeStandingtackle;
  @BindView(R.id.text_name_slidingtackle) TextView textNameSlidingtackle;
  @BindView(R.id.text_value_slidingtackle) TextView textValueSlidingtackle;
  @BindView(R.id.relative_slidingtackle) RelativeLayout relativeSlidingtackle;
  @BindView(R.id.text_name_marking) TextView textNameMarking;
  @BindView(R.id.text_value_marking) TextView textValueMarking;
  @BindView(R.id.relative_marking) RelativeLayout relativeMarking;
  @BindView(R.id.text_name_aggression) TextView textNameAggression;
  @BindView(R.id.text_value_aggression) TextView textValueAggression;
  @BindView(R.id.relative_aggression) RelativeLayout relativeAggression;
  @BindView(R.id.text_name_gkdiving) TextView textNameGkdiving;
  @BindView(R.id.text_value_gkdiving) TextView textValueGkdiving;
  @BindView(R.id.relative_gkdiving) RelativeLayout relativeGkdiving;
  @BindView(R.id.text_name_gkhandling) TextView textNameGkhandling;
  @BindView(R.id.text_value_gkhandling) TextView textValueGkhandling;
  @BindView(R.id.relative_gkhandling) RelativeLayout relativeGkhandling;
  @BindView(R.id.text_name_gkkicking) TextView textNameGkkicking;
  @BindView(R.id.text_value_gkkicking) TextView textValueGkkicking;
  @BindView(R.id.relative_gkkicking) RelativeLayout relativeGkkicking;
  @BindView(R.id.text_name_gkreflexes) TextView textNameGkreflexes;
  @BindView(R.id.text_value_gkreflexes) TextView textValueGkreflexes;
  @BindView(R.id.relative_gkreflexes) RelativeLayout relativeGkreflexes;
  @BindView(R.id.text_name_gkpositioning) TextView textNameGkpositioning;
  @BindView(R.id.text_value_gkpositioning) TextView textValueGkpositioning;
  @BindView(R.id.relative_gkpositioning) RelativeLayout relativeGkpositioning;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.linear_stat) LinearLayout linearStat;
  private View rootView;
  private String idPlayer;
  private Realm realm;

  public static PaperStatFragment newInstance(String s) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_STRING_ID_PLAYER, s);
    PaperStatFragment fragment = new PaperStatFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.fragment_paper_stat, container, false);
    ButterKnife.bind(this, rootView);
    idPlayer = this.getArguments().getString(BUNDLE_STRING_ID_PLAYER);
    realm = Realm.getDefaultInstance();
    setShowMainView(false);
    return rootView;
  }

  private void setShowMainView(boolean b) {
    if (b) {
      linearStat.setVisibility(View.VISIBLE);
      progressBar.setVisibility(View.GONE);
    } else {
      linearStat.setVisibility(View.GONE);
      progressBar.setVisibility(View.VISIBLE);
    }
  }

  @Override public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    EventBus.getDefault().unregister(this);
    super.onStop();
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN) public void onEvent(
      PlayerDetailActivity.MessageEvent event) {
    PlayerR player = realm.where(PlayerR.class).equalTo("id", event.getIdPlayer()).findFirst();
    if (idPlayer != null) {
      setShowMainView(true);
      setTextAndColorStat(textNameSprintspeed, textValueSprintspeed, player.playerStat.sprintspeed);
      setTextAndColorStat(textNameAcceleration, textValueAcceleration, player.playerStat.acceleration);
      setTextAndColorStat(textNameAgility, textValueAgility, player.playerStat.agility);
      setTextAndColorStat(textNameAggression, textValueAggression, player.playerStat.aggression);
      setTextAndColorStat(textNameBalance, textValueBalance, player.playerStat.balance);
      setTextAndColorStat(textNameBallcontrol, textValueBallcontrol, player.playerStat.ballcontrol);
      setTextAndColorStat(textNameCrossing, textValueCrossing, player.playerStat.crossing);
      setTextAndColorStat(textNameCurve, textValueCurve, player.playerStat.curve);
      setTextAndColorStat(textNameDribbling, textValueDribbling, player.playerStat.dribbling);
      setTextAndColorStat(textNameFreekickaccuracy, textValueFreekickaccuracy, player.playerStat.freekickaccuracy);
      setTextAndColorStat(textNameJumping, textValueJumping, player.playerStat.jumping);
      setTextAndColorStat(textNameHeadingaccuracy, textValueHeadingaccuracy, player.playerStat.headingaccuracy);
      setTextAndColorStat(textNameLongpassing, textValueLongpassing, player.playerStat.longpassing);
      setTextAndColorStat(textNameMarking, textValueMarking, player.playerStat.marking);
      setTextAndColorStat(textNameLongshots, textValueLongshots, player.playerStat.longshots);
      setTextAndColorStat(textNamePenalties, textValuePenalties, player.playerStat.penalties);
      setTextAndColorStat(textNamePositioning, textValuePositioning, player.playerStat.positioning);
      setTextAndColorStat(textNameReactions, textValueReactions, player.playerStat.reactions);
      setTextAndColorStat(textNameShortpassing, textValueShortpassing, player.playerStat.shortpassing);
      setTextAndColorStat(textNameShotpower, textValueShotpower, player.playerStat.shotpower);
      setTextAndColorStat(textNameVision, textValueVision, player.playerStat.vision);
      setTextAndColorStat(textNameSlidingtackle, textValueSlidingtackle, player.playerStat.slidingtackle);
      setTextAndColorStat(textNameStamina, textValueStamina, player.playerStat.stamina);
      setTextAndColorStat(textNameStrength, textValueStrength, player.playerStat.strength);
      setTextAndColorStat(textNameGkdiving, textValueGkdiving, player.playerStat.gkdiving);
      setTextAndColorStat(textNameGkhandling, textValueGkhandling, player.playerStat.gkhandling);
      setTextAndColorStat(textNameGkkicking, textValueGkkicking, player.playerStat.gkkicking);
      setTextAndColorStat(textNameGkreflexes, textValueGkreflexes, player.playerStat.gkreflexes);
      setTextAndColorStat(textNameGkpositioning, textValueGkpositioning, player.playerStat.gkpositioning);
      setTextAndColorStat(textNameTacticalawareness, textValueTacticalawareness, player.playerStat.tacticalawareness);
      setTextAndColorStat(textNameStandingtackle, textValueStandingtackle, player.playerStat.standingtackle);
      setTextAndColorStat(textNameVolleys, textValueVolleys, player.playerStat.volleys);
      setTextAndColorStat(textNameFinishing, textValueFinishing, player.playerStat.finishing);
    }
  }

  private void setTextAndColorStat(TextView textName, TextView textValue, int value) {
    textValue.setText(String.valueOf(value));
    if (value >= 120) {
      textName.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_max));
      textValue.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_max));
    } else if (value >= 110) {
      textName.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_110));
      textValue.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_110));
    } else if (value >= 100) {
      textName.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_100));
      textValue.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_100));
    } else if (value >= 90) {
      textName.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_90));
      textValue.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_90));
    }else if (value >= 80) {
      textName.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_80));
      textValue.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_80));
    } else if (value >= 70) {
      textName.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_70));
      textValue.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_70));
    } else {
      textName.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_60));
      textValue.setTextColor(ContextCompat.getColor(getContext(), R.color.stat_60));
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    realm.close();
  }
}
