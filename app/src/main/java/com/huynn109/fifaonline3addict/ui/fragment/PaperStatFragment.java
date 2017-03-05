package com.huynn109.fifaonline3addict.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerR;
import com.huynn109.fifaonline3addict.ui.activity.PlayerDetailActivity;
import io.realm.Realm;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaperStatFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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
  @BindView(R.id.spinner_enchant) Spinner spinnerEnchant;
  @BindView(R.id.spinner_level) Spinner spinnerLevel;
  @BindView(R.id.linear_spinner) LinearLayout linearSpinner;
  private View rootView;
  private String idPlayer;
  private Realm realm;
  private List<String> enchantList;
  private List<String> levelList;

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
    setupSpinner();
    return rootView;
  }

  private void setupSpinner() {
    enchantList = Arrays.asList(getResources().getStringArray(R.array.enchant));
    spinnerEnchant.setAdapter(
        new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
            enchantList));
    spinnerEnchant.setOnItemSelectedListener(this);
    spinnerEnchant.setSelection(1);
    levelList = Arrays.asList(getResources().getStringArray(R.array.level));
    spinnerLevel.setAdapter(
        new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, levelList));
    spinnerLevel.setOnItemSelectedListener(this);
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

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  public void onEvent(PlayerDetailActivity.MessageEvent event) {
    PlayerR player = realm.where(PlayerR.class).equalTo("id", event.getIdPlayer()).findFirst();
    if (idPlayer != null && player.playerStat.sprintspeed > 0) {
      setShowMainView(true);
      updateStatView(player, 0);
    } else {
      linearStat.setVisibility(View.GONE);
      progressBar.setVisibility(View.GONE);
    }
  }

  private void updateStatView(PlayerR player, int sum) {
    setTextAndColorStat(textNameSprintspeed, textValueSprintspeed,
        player.playerStat.sprintspeed + sum);
    setTextAndColorStat(textNameAcceleration, textValueAcceleration,
        player.playerStat.acceleration + sum);
    setTextAndColorStat(textNameAgility, textValueAgility, player.playerStat.agility + sum);
    setTextAndColorStat(textNameAggression, textValueAggression,
        player.playerStat.aggression + sum);
    setTextAndColorStat(textNameBalance, textValueBalance, player.playerStat.balance + sum);
    setTextAndColorStat(textNameBallcontrol, textValueBallcontrol,
        player.playerStat.ballcontrol + sum);
    setTextAndColorStat(textNameCrossing, textValueCrossing, player.playerStat.crossing + sum);
    setTextAndColorStat(textNameCurve, textValueCurve, player.playerStat.curve + sum);
    setTextAndColorStat(textNameDribbling, textValueDribbling, player.playerStat.dribbling + sum);
    setTextAndColorStat(textNameFreekickaccuracy, textValueFreekickaccuracy,
        player.playerStat.freekickaccuracy + sum);
    setTextAndColorStat(textNameJumping, textValueJumping, player.playerStat.jumping + sum);
    setTextAndColorStat(textNameHeadingaccuracy, textValueHeadingaccuracy,
        player.playerStat.headingaccuracy + sum);
    setTextAndColorStat(textNameLongpassing, textValueLongpassing,
        player.playerStat.longpassing + sum);
    setTextAndColorStat(textNameMarking, textValueMarking, player.playerStat.marking + sum);
    setTextAndColorStat(textNameLongshots, textValueLongshots, player.playerStat.longshots + sum);
    setTextAndColorStat(textNamePenalties, textValuePenalties, player.playerStat.penalties + sum);
    setTextAndColorStat(textNamePositioning, textValuePositioning,
        player.playerStat.positioning + sum);
    setTextAndColorStat(textNameReactions, textValueReactions, player.playerStat.reactions + sum);
    setTextAndColorStat(textNameShortpassing, textValueShortpassing,
        player.playerStat.shortpassing + sum);
    setTextAndColorStat(textNameShotpower, textValueShotpower, player.playerStat.shotpower + sum);
    setTextAndColorStat(textNameVision, textValueVision, player.playerStat.vision + sum);
    setTextAndColorStat(textNameSlidingtackle, textValueSlidingtackle,
        player.playerStat.slidingtackle + sum);
    setTextAndColorStat(textNameStamina, textValueStamina, player.playerStat.stamina + sum);
    setTextAndColorStat(textNameStrength, textValueStrength, player.playerStat.strength + sum);
    setTextAndColorStat(textNameGkdiving, textValueGkdiving, player.playerStat.gkdiving + sum);
    setTextAndColorStat(textNameGkhandling, textValueGkhandling,
        player.playerStat.gkhandling + sum);
    setTextAndColorStat(textNameGkkicking, textValueGkkicking, player.playerStat.gkkicking + sum);
    setTextAndColorStat(textNameGkreflexes, textValueGkreflexes,
        player.playerStat.gkreflexes + sum);
    setTextAndColorStat(textNameGkpositioning, textValueGkpositioning,
        player.playerStat.gkpositioning + sum);
    setTextAndColorStat(textNameTacticalawareness, textValueTacticalawareness,
        player.playerStat.tacticalawareness + sum);
    setTextAndColorStat(textNameStandingtackle, textValueStandingtackle,
        player.playerStat.standingtackle + sum);
    setTextAndColorStat(textNameVolleys, textValueVolleys, player.playerStat.volleys + sum);
    setTextAndColorStat(textNameFinishing, textValueFinishing, player.playerStat.finishing + sum);
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
    } else if (value >= 80) {
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

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    PlayerR playerR = realm.where(PlayerR.class).equalTo("id", idPlayer).findFirst();
    int enchant = 0;
    if (playerR != null && playerR.playerStat != null) {
      if (spinnerEnchant.getSelectedItemPosition() == 0) {
        enchant = -5;
      } else if (spinnerEnchant.getSelectedItemPosition() < 5) {
        enchant = spinnerEnchant.getSelectedItemPosition() - 1;
      } else {
        switch (spinnerEnchant.getSelectedItemPosition()) {
          case 5:
            enchant = spinnerEnchant.getSelectedItemPosition();
            break;
          case 6:
            enchant = spinnerEnchant.getSelectedItemPosition() + 1;
            break;
          case 7:
            enchant = spinnerEnchant.getSelectedItemPosition() + 2;
            break;
          case 8:
            enchant = spinnerEnchant.getSelectedItemPosition() + 4;
            break;
          case 9:
            enchant = spinnerEnchant.getSelectedItemPosition() + 6;
            break;
          case 10:
            enchant = spinnerEnchant.getSelectedItemPosition() + 9;
            break;
        }
      }
      int level = spinnerLevel.getSelectedItemPosition();
      updateStatView(playerR, enchant + level);
    }
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {

  }
}
