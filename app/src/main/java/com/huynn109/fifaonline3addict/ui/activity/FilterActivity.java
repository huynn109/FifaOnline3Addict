package com.huynn109.fifaonline3addict.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.normal.Ability;
import com.huynn109.fifaonline3addict.data.model.normal.AbilityValue;
import com.huynn109.fifaonline3addict.data.model.normal.Club;
import com.huynn109.fifaonline3addict.data.model.normal.Country;
import com.huynn109.fifaonline3addict.data.model.normal.League;
import com.huynn109.fifaonline3addict.data.model.realm.PositionR;
import com.huynn109.fifaonline3addict.data.model.normal.Season;
import com.huynn109.fifaonline3addict.data.model.normal.SkillMoves;
import com.huynn109.fifaonline3addict.util.URL;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity
    implements AdapterView.OnItemSelectedListener {

  public static final int REQUEST_CODE_SEASON = 1;
  public static final int REQUEST_CODE_LEAGUE = 2;
  private static final String TAG = FilterActivity.class.getSimpleName();
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.button_apply) TextView buttonApply;
  @BindView(R.id.linear_season) LinearLayout linearLayoutSeason;
  @BindView(R.id.text_season) TextView textViewSeason;
  @BindView(R.id.spinner_league) Spinner spinnerLeague;
  @BindView(R.id.spinner_club) Spinner spinnerClub;
  @BindView(R.id.spinner_country) Spinner spinnerCountry;
  @BindView(R.id.spinner_position_generality) Spinner spinnerPositionGenerality;
  @BindView(R.id.spinner_position_detail) Spinner spinnerPositionDetail;
  @BindView(R.id.spinner_ability) Spinner spinnerAbility;
  @BindView(R.id.spinner_ability_value) Spinner spinnerAbilityValue;
  @BindView(R.id.spinner_skill_moves) Spinner spinnerSkillMoves;
  @BindView(R.id.checkbox_live_boost) CheckBox checkBoxLiveBoost;
  @BindView(R.id.checkbox_limited) CheckBox checkBoxLimited;
  private ArrayList<Season> seasonChecked = new ArrayList<>();
  private League leagueChecked = new League();
  private Country countryChecked = new Country();
  private String positionGenegalityChecked;
  private String positionDetailChecked;
  private ArrayAdapter<League> adapterLeague;
  private ArrayAdapter<Club> adapterClub;
  private ArrayAdapter<Country> adapterCountry;
  private ArrayAdapter<String> adapterPositionGenerality;
  private ArrayAdapter<PositionR> adapterPositionDetail;
  private ArrayAdapter<Ability> adapterAbility;
  private ArrayAdapter<AbilityValue> adapterAbilityValue;
  private List<League> leagueList = new ArrayList<>();
  private List<Club> clubList = new ArrayList<>();
  private List<Country> countryList = new ArrayList<>();
  private List<PositionR> positionDetailList = new ArrayList<>();
  private List<Ability> abilityList = new ArrayList<>();
  private List<AbilityValue> abilityValueList = new ArrayList<>();
  private List<SkillMoves> skillMovesList = new ArrayList<>();
  private String[] stringPositionGenerality;
  private String abilityChecked;
  private String abilityValueChecked;
  private ArrayAdapter<SkillMoves> adapterSkillMoves;
  private String skillMovesChecked;
  private Boolean checkBoxLiveBoostChecked;
  private boolean checkBoxLimitedChecked;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter);
    ButterKnife.bind(this);
    setupToolbar();
    setUpSpinner();
    parseData();
  }

  private void setUpSpinner() {
    loadStringArray();
    loadJsonFromFile("league.json");
    adapterLeague =
        new ArrayAdapter<League>(this, android.R.layout.simple_dropdown_item_1line, leagueList);
    spinnerLeague.setAdapter(adapterLeague);
    spinnerLeague.setOnItemSelectedListener(this);

    adapterClub =
        new ArrayAdapter<Club>(this, android.R.layout.simple_dropdown_item_1line, clubList);
    spinnerClub.setAdapter(adapterClub);
    spinnerClub.setOnItemSelectedListener(this);

    adapterCountry =
        new ArrayAdapter<Country>(this, android.R.layout.simple_dropdown_item_1line, countryList);
    spinnerCountry.setAdapter(adapterCountry);
    spinnerCountry.setOnItemSelectedListener(this);

    adapterPositionGenerality =
        new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
            stringPositionGenerality);
    spinnerPositionGenerality.setAdapter(adapterPositionGenerality);
    spinnerPositionGenerality.setOnItemSelectedListener(this);

    adapterPositionDetail =
        new ArrayAdapter<PositionR>(this, android.R.layout.simple_dropdown_item_1line,
            positionDetailList);
    spinnerPositionDetail.setAdapter(adapterPositionDetail);
    spinnerPositionDetail.setOnItemSelectedListener(this);

    adapterAbility =
        new ArrayAdapter<Ability>(this, android.R.layout.simple_dropdown_item_1line, abilityList);
    spinnerAbility.setAdapter(adapterAbility);
    spinnerAbility.setOnItemSelectedListener(this);

    adapterAbilityValue =
        new ArrayAdapter<AbilityValue>(this, android.R.layout.simple_dropdown_item_1line,
            abilityValueList);
    spinnerAbilityValue.setAdapter(adapterAbilityValue);
    spinnerAbilityValue.setOnItemSelectedListener(this);

    adapterSkillMoves =
        new ArrayAdapter<SkillMoves>(this, android.R.layout.simple_dropdown_item_1line,
            skillMovesList);
    spinnerSkillMoves.setAdapter(adapterSkillMoves);
    spinnerSkillMoves.setOnItemSelectedListener(this);
  }

  private void loadJsonFromFile(String s) {
    try {
      InputStream is = this.getAssets().open(s);
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      String bufferString = new String(buffer);
      Gson gson = new Gson();
      Type listType = new TypeToken<List<League>>() {
      }.getType();
      League league = new League();
      league.setName("All");
      leagueList.add(league);
      List<League> leagueListGson = gson.fromJson(bufferString, listType);
      leagueList.addAll(leagueListGson);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadStringArray() {
    if (countryList.size() > 0) countryList.clear();
    String[] stringCountryIds = getResources().getStringArray(R.array.country_id);
    String[] stringCountryNames = getResources().getStringArray(R.array.country_name);
    for (int i = 0; i < stringCountryIds.length; i++) {
      Country country = new Country();
      country.setId(stringCountryIds[i]);
      country.setName(stringCountryNames[i]);
      countryList.add(country);
    }
    stringPositionGenerality = getResources().getStringArray(R.array.position_generality);
    if (abilityList.size() > 0) abilityList.clear();
    String[] stringAbilityIds = getResources().getStringArray(R.array.ability_id);
    String[] stringAbilityNames = getResources().getStringArray(R.array.ability_name);
    for (int i = 0; i < stringAbilityIds.length; i++) {
      Ability ability = new Ability();
      ability.setId(stringAbilityIds[i]);
      ability.setName(stringAbilityNames[i]);
      abilityList.add(ability);
    }
    if (abilityValueList.size() > 0) abilityValueList.clear();
    for (int i = 100; i >= 40; i--) {
      AbilityValue abilityValue = new AbilityValue();
      if (i == 100) {
        abilityValue.setName(getString(R.string.title_all));
      } else {
        abilityValue.setId(i + "");
        abilityValue.setName(">=" + i);
      }
      abilityValueList.add(abilityValue);
    }
    if (skillMovesList.size() > 0) skillMovesList.clear();
    for (int i = 0; i <= 5; i++) {
      SkillMoves skillMoves = new SkillMoves();
      if (i == 0) {
        skillMoves.setName(getString(R.string.title_all));
      } else {
        skillMoves.setName(">=" + i);
      }
      skillMoves.setId(i + "");
      skillMovesList.add(skillMoves);
    }
  }

  private void parseData() {
    Intent intent = getIntent();
    seasonChecked = (ArrayList<Season>) intent.getSerializableExtra("season");
    if (seasonChecked.size() > 0) {
      StringBuilder textSeason = new StringBuilder();
      for (int i = 0; i < seasonChecked.size(); i++) {
        textSeason.append(seasonChecked.get(i).getName());
        if (i < seasonChecked.size() - 1) {
          textSeason.append(", ");
        }
      }
      if (textSeason.length() == 0) {
        textViewSeason.setText(getString(R.string.title_all));
      } else {
        textViewSeason.setText(textSeason.toString());
      }
    }

    leagueChecked = (League) intent.getSerializableExtra("league");
    if (leagueChecked.getId() != null) {
      for (League league : leagueList) {
        if (leagueChecked.getName().equals(league.getName())) {
          spinnerLeague.setSelection(leagueList.indexOf(league));
        }
      }
    }
    countryChecked = (Country) intent.getSerializableExtra("country");
    if (countryChecked.getId() != null) {
      for (Country country : countryList) {
        if (countryChecked.getName().equals(country.getName())) {
          spinnerCountry.setSelection(countryList.indexOf(country));
        }
      }
    }
    positionGenegalityChecked = (String) intent.getStringExtra("position_generality");
    if (positionGenegalityChecked != null) {
      for (int i = 0; i < stringPositionGenerality.length; i++) {
        if (positionGenegalityChecked.equals(stringPositionGenerality[i])) {
          spinnerPositionGenerality.setSelection(i);
        }
      }
    }
    positionDetailChecked = intent.getStringExtra("position_detail");

    abilityChecked = intent.getStringExtra("ability");
    if (abilityChecked != null) {
      for (int i = 0; i < abilityList.size(); i++) {
        if (abilityChecked.equals(abilityList.get(i).getId())) {
          spinnerAbility.setSelection(i);
        }
      }
    }
    abilityValueChecked = intent.getStringExtra("ability_value");

    skillMovesChecked = intent.getStringExtra("skill_moves");
    if (skillMovesChecked != null) {
      for (int i = 0; i < skillMovesList.size(); i++) {
        if (skillMovesChecked.equals(skillMovesList.get(i).getId())) {
          spinnerSkillMoves.setSelection(i);
        }
      }
    }

    checkBoxLiveBoostChecked = intent.getBooleanExtra("checkbox_live_boost", false);
    checkBoxLimitedChecked = intent.getBooleanExtra("checkbox_limited", false);

    if (checkBoxLiveBoostChecked) {
      checkBoxLiveBoost.setChecked(checkBoxLiveBoostChecked);
    }

    if (checkBoxLimitedChecked) {
      checkBoxLimited.setChecked(checkBoxLimitedChecked);
    }

    Log.d(TAG, "parseData: " + checkBoxLiveBoostChecked);
    Log.d(TAG, "parseData: " + checkBoxLimitedChecked);
  }
  private void setupToolbar() {
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_black_24dp);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(R.string.title_filter);
    }
  }

  @OnClick(R.id.button_apply) void onClickApply() {
    StringBuilder url = new StringBuilder(URL.SEARCH_URL);
    if (seasonChecked.size() > 0) {
      url.append("&season=");
      for (int i = 0; i < seasonChecked.size(); i++) {
        url.append(seasonChecked.get(i).getId());
        if (i < seasonChecked.size() - 1) {
          url.append(",");
        }
      }
    }
    url.append("&league=")
        .append(leagueList.get(spinnerLeague.getSelectedItemPosition()).getName());
    leagueChecked.setId(leagueList.get(spinnerLeague.getSelectedItemPosition()).getId());
    leagueChecked.setName(leagueList.get(spinnerLeague.getSelectedItemPosition()).getName());

    url.append("&nation=")
        .append(countryList.get(spinnerCountry.getSelectedItemPosition()).getId());
    countryChecked.setId(countryList.get(spinnerCountry.getSelectedItemPosition()).getId());
    countryChecked.setName(countryList.get(spinnerCountry.getSelectedItemPosition()).getName());

    url.append("&position=")
        .append(stringPositionGenerality[spinnerPositionGenerality.getSelectedItemPosition()]);

    positionGenegalityChecked =
        stringPositionGenerality[spinnerPositionGenerality.getSelectedItemPosition()];

    if (positionDetailList.size() > 0) {
      positionDetailChecked =
          positionDetailList.get(spinnerPositionDetail.getSelectedItemPosition()).getName();
    }

    abilityChecked = abilityList.get((spinnerAbility.getSelectedItemPosition())).getId();
    if (abilityValueList.size() > 0) {
      abilityValueChecked =
          abilityValueList.get((spinnerAbilityValue.getSelectedItemPosition())).getId();
    }

    skillMovesChecked = skillMovesList.get(spinnerSkillMoves.getSelectedItemPosition()).getId();

    checkBoxLiveBoostChecked = checkBoxLiveBoost.isChecked();
    checkBoxLimitedChecked = checkBoxLimited.isChecked();

    Log.d(TAG, "onClickApply: " + checkBoxLimited.isChecked());
    
    Intent intent = new Intent();
    intent.putExtra("season", (Serializable) seasonChecked);
    intent.putExtra("league", (Serializable) leagueChecked);
    intent.putExtra("country", (Serializable) countryChecked);
    intent.putExtra("position_generality", positionGenegalityChecked);
    intent.putExtra("position_detail", positionDetailChecked);
    intent.putExtra("ability", abilityChecked);
    intent.putExtra("ability_value", abilityValueChecked);
    intent.putExtra("skill_moves", skillMovesChecked);
    intent.putExtra("checkbox_live_boost", checkBoxLiveBoostChecked);
    intent.putExtra("checkbox_limited", checkBoxLimitedChecked);
    setResult(RESULT_OK, intent);
    finish();
  }

  @OnClick(R.id.linear_season) void onClickLinearSeason() {
    Intent intent = new Intent(this, FilterSeasonActivity.class);
    intent.putExtra("season", (Serializable) seasonChecked);
    startActivityForResult(intent, REQUEST_CODE_SEASON);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_SEASON) {
      if (resultCode == RESULT_OK) {
        seasonChecked = (ArrayList<Season>) data.getSerializableExtra("season");
        StringBuilder textSeason = new StringBuilder();
        for (int i = 0; i < seasonChecked.size(); i++) {
          textSeason.append(seasonChecked.get(i).getName());
          if (i < seasonChecked.size() - 1) {
            textSeason.append(", ");
          }
        }
        if (textSeason.length() == 0) {
          textViewSeason.setText(getString(R.string.title_all));
        } else {
          textViewSeason.setText(textSeason.toString());
        }
      }
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    Spinner spinner = (Spinner) parent;
    int itemId = spinner.getId();
    switch (itemId) {
      case R.id.spinner_league:
        if (position > 0) {
          clubList.clear();
          Club club = new Club();
          club.setName("All");
          clubList.add(0, club);
          clubList.addAll(leagueList.get(position).getClubs());
          spinnerClub.setSelection(0);
        } else {
          clubList.clear();
        }
        adapterClub.notifyDataSetChanged();
        break;
      case R.id.spinner_club:
        break;
      case R.id.spinner_country:
        break;
      case R.id.spinner_position_generality:
        if (position != 0 && position != 4) {
          String[] arrPositionDetail = new String[0];
          positionDetailList.clear();
          switch (position) {
            case 1:
              arrPositionDetail = getResources().getStringArray(R.array.position_detail_fwd);
              break;
            case 2:
              arrPositionDetail = getResources().getStringArray(R.array.position_detail_mid);
              break;
            case 3:
              arrPositionDetail = getResources().getStringArray(R.array.position_detail_def);
              break;
          }
          for (String anArrPositionDetail : arrPositionDetail) {
            PositionR positionDetail = new PositionR();
            positionDetail.setName(anArrPositionDetail);
            positionDetailList.add(positionDetail);
          }
          if (positionDetailChecked != null) {
            for (int i = 0; i < positionDetailList.size(); i++) {
              if (positionDetailChecked.equals(positionDetailList.get(i).getName())) {
                spinnerPositionDetail.setSelection(i);
              }
            }
          }
          adapterPositionDetail.notifyDataSetChanged();
        } else {
          positionDetailList.clear();
          adapterPositionDetail.notifyDataSetChanged();
        }
        break;
      case R.id.spinner_position_detail:
        break;
      case R.id.spinner_ability:
        if (position == 0) {
          abilityValueList.clear();
        } else {
          for (int i = 100; i >= 40; i--) {
            AbilityValue abilityValue = new AbilityValue();
            if (i == 100) {
              abilityValue.setName("All");
            } else {
              abilityValue.setId(i + "");
              abilityValue.setName(">=" + i);
            }
            abilityValueList.add(abilityValue);
          }
          if (abilityValueChecked != null) {
            for (int i = 0; i < abilityValueList.size(); i++) {
              if (abilityValueChecked.equals(abilityValueList.get(i).getId())) {
                spinnerAbilityValue.setSelection(i);
              }
            }
          }
        }
        adapterAbilityValue.notifyDataSetChanged();
        break;
      case R.id.spinner_ability_value:

        break;
    }
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {

  }
}
