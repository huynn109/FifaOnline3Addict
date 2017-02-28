package com.huynn109.fifaonline3addict.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.helper.DatabaseHelper;
import com.huynn109.fifaonline3addict.data.model.normal.Ability;
import com.huynn109.fifaonline3addict.data.model.normal.AbilityValue;
import com.huynn109.fifaonline3addict.data.model.normal.Club;
import com.huynn109.fifaonline3addict.data.model.normal.Country;
import com.huynn109.fifaonline3addict.data.model.normal.League;
import com.huynn109.fifaonline3addict.data.model.normal.Player;
import com.huynn109.fifaonline3addict.data.model.normal.Position;
import com.huynn109.fifaonline3addict.data.model.normal.Season;
import com.huynn109.fifaonline3addict.data.model.normal.SkillMoves;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerR;
import com.huynn109.fifaonline3addict.data.model.realm.PositionR;
import com.huynn109.fifaonline3addict.ui.activity.FilterActivity;
import com.huynn109.fifaonline3addict.ui.activity.PlayerDetailActivity;
import com.huynn109.fifaonline3addict.ui.adapter.PlayerListRealmAdapter;
import com.huynn109.fifaonline3addict.util.URL;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerListFragment extends Fragment implements View.OnClickListener {

  private static final String TAG = PlayerListFragment.class.getSimpleName();
  public static final int REQUEST_CODE_FILTER = 1;
  @BindView(R.id.progress_loading_view) ProgressBar progressLoadingView;
  @BindView(R.id.recycler_player_fragment) RealmRecyclerView recyclerPlayerFragment;
  private List<Player> playerList = new ArrayList<>();
  private PlayerListRealmAdapter playerListAdapter;

  private List<League> leagueList = new ArrayList<>();
  private List<Club> clubList = new ArrayList<>();
  private List<Season> seasonList = new ArrayList<>();
  private List<Country> countryList = new ArrayList<>();
  private List<PositionR> positionDetailList = new ArrayList<>();
  private List<Ability> abilityList = new ArrayList<>();
  private List<AbilityValue> abilityValueList = new ArrayList<>();
  private List<SkillMoves> skillMovesList = new ArrayList<>();
  private ArrayAdapter<Season> adapterSeason;
  private ArrayAdapter<League> adapterLeague;
  private ArrayAdapter<Club> adapterClub;
  private ArrayAdapter<Country> adapterCountry;
  private ArrayAdapter<String> adapterPositionGenerality;
  private ArrayAdapter<PositionR> adapterPositionDetail;
  private ArrayAdapter<Ability> adapterAbility;
  private ArrayAdapter<AbilityValue> adapterAbilityValue;
  private ArrayAdapter<SkillMoves> adapterSkillMoves;
  private String[] stringPositionGenerality;
  private ArrayList<Season> seasonChecked = new ArrayList<>();
  private League leagueChecked = new League();
  private Country countryChecked = new Country();
  private String positionGeneralityChecked;
  private String positionDetailChecked;
  private String abilityChecked;
  private String abilityValueChecked;
  private String skillMovesChecked;
  private boolean liveBoostChecked;
  private boolean limitedChecked;
  private Realm realm;
  private RealmResults<PlayerR> playerRealmResults;
  private RealmQuery<PlayerR> playerRealmQuery;

  public PlayerListFragment() {
  }

  public static PlayerListFragment newInstance() {
    Bundle args = new Bundle();
    PlayerListFragment fragment = new PlayerListFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    realm = Realm.getDefaultInstance();
    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_player_list, container, false);
    ButterKnife.bind(this, rootView);
    setUpRecyclerViewPlayer();
    setUpSpinner();
    getJsoupPlayerList(URL.BASE_URL);
    return rootView;
  }

  public void getJsoupPlayerList(String url) {
    showProgressBar(true);
    getJsoupDocument(url).subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .doOnNext(this::parseDocToList)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscribeJsoupPlayerList());
  }

  private Observer<? super Document> subscribeJsoupPlayerList() {
    return new Observer<Document>() {
      @Override public void onSubscribe(Disposable d) {
        Log.d(TAG, " onSubscribe : " + d.isDisposed());
      }

      @Override public void onNext(Document document) {
        DatabaseHelper databaseHelper = new DatabaseHelper(realm);
        databaseHelper.saveListPlayerToRealm(playerList);
        playerRealmQuery = realm.where(PlayerR.class);
        if (seasonChecked.size() > 0) {
          for (Season season : seasonChecked) {
            if (seasonChecked.size() > 1) {
              playerRealmQuery.equalTo("season", season.getId()).or();
            } else {
              playerRealmQuery.equalTo("season", season.getId());
            }
          }
        }
        playerRealmResults = playerRealmQuery.findAll();
        playerListAdapter =
            new PlayerListRealmAdapter(getContext(), playerRealmResults, itemClickListener);
        recyclerPlayerFragment.setAdapter(playerListAdapter);
      }

      @Override public void onError(Throwable e) {
        showProgressBar(false);
      }

      @Override public void onComplete() {
        showProgressBar(false);
      }
    };
  }

  private void parseDocToList(Document doc) {
    Log.i(TAG, "parseDocToList: ");
    if (doc == null) {
      return;
    }
    playerList.clear();
    Elements elementsPlayer = doc.select("tr.player-row");
    for (final Element element : elementsPlayer) {
      Player player = new Player();
      player.id = element.attr("id").split("player_id")[1];
      player.name = element.getElementsByClass("label_xs").text();
      player.rosterUpdate = element.getElementsByClass("rosterupdate_statchange statup").text();
      player.liveBoost = element.getElementsByClass("rosterupdate_statchange  liveboost").text();
      player.skillmoves = element.getElementsByClass("player_info_list player_skillmoves")
          .select("i.fa-star")
          .size();
      player.imageId = element.getElementsByClass("player_img img-rounded")
          .attr("src")
          .split("small/p")[1].split(".jpg")[0];
      if (element.getElementsByClass("player_position_list").text() != null) {
        player.positions = new ArrayList<>();
        for (final Element elementPosition : element.getElementsByClass("player_position_list")) {
          Position position = new Position();
          position.setName(elementPosition.getElementsByClass("badge_position").text());
          int valuePosition =
              Integer.parseInt(elementPosition.getElementsByClass("stat_value").text()) + 5;
          position.setValue(valuePosition + "");
          player.positions.add(position);
        }
        player.season = com.huynn109.fifaonline3addict.util.Season.getSeasonFromClass(
            element.getElementsByClass("player_info_list player_name")
                .first()
                .getElementsByClass("badged")
                .first()
                .attr("class")
                .split("badged")[1].trim());
      }
      playerList.add(player);
    }
  }

  private void showProgressBar(boolean b) {
    if (b) {
      progressLoadingView.setVisibility(View.VISIBLE);
      recyclerPlayerFragment.setVisibility(View.GONE);
    } else {
      progressLoadingView.setVisibility(View.GONE);
      recyclerPlayerFragment.setVisibility(View.VISIBLE);
    }
  }

  public Observable<Document> getJsoupDocument(String url) {
    return Observable.defer(() -> Observable.just(Jsoup.connect(url).get()));
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    realm.close();
    super.onDestroy();
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.main, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        //if (linearLayoutSearch.getVisibility() == View.GONE) {
        //  linearLayoutSearch.setVisibility(View.VISIBLE);
        //}
        //new Handler().postDelayed(new Runnable() {
        //  @Override public void run() {
        //    nestedScrollView.fullScroll(View.FOCUS_UP);
        //  }
        //}, 100);
        return true;
      case R.id.action_filter:
        Intent intent = new Intent(getContext(), FilterActivity.class);
        intent.putExtra("season", (Serializable) seasonChecked);
        intent.putExtra("league", (Serializable) leagueChecked);
        intent.putExtra("country", (Serializable) countryChecked);
        intent.putExtra("position_generality", positionGeneralityChecked);
        intent.putExtra("position_detail", positionDetailChecked);
        intent.putExtra("ability", abilityChecked);
        intent.putExtra("ability_value", abilityValueChecked);
        intent.putExtra("skill_moves", skillMovesChecked);
        intent.putExtra("checkbox_live_boost", liveBoostChecked);
        intent.putExtra("checkbox_limited", limitedChecked);
        startActivityForResult(intent, REQUEST_CODE_FILTER);
        return true;
    }
    return false;
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_FILTER) {
      if (resultCode == RESULT_OK) {
        StringBuilder urlBuilder = new StringBuilder(URL.SEARCH_URL);
        seasonChecked = (ArrayList<Season>) data.getSerializableExtra("season");
        leagueChecked = (League) data.getSerializableExtra("league");
        countryChecked = (Country) data.getSerializableExtra("country");
        positionGeneralityChecked = data.getStringExtra("position_generality");
        positionDetailChecked = data.getStringExtra("position_detail");
        abilityChecked = data.getStringExtra("ability");
        abilityValueChecked = data.getStringExtra("ability_value");
        skillMovesChecked = data.getStringExtra("skill_moves");
        liveBoostChecked = data.getBooleanExtra("checkbox_live_boost", false);
        limitedChecked = data.getBooleanExtra("checkbox_limited", false);
        if (seasonChecked.size() > 0) {
          urlBuilder.append("&season=");
          StringBuilder textSeason = new StringBuilder();
          for (int i = 0; i < seasonChecked.size(); i++) {
            textSeason.append(seasonChecked.get(i).getId());
            if (i < seasonChecked.size() - 1) {
              textSeason.append(",");
            }
          }
          if (textSeason.length() > 0) {
            urlBuilder.append(textSeason);
          }
        }

        if (leagueChecked.getId() != null) {
          urlBuilder.append("&league=");
          urlBuilder.append(leagueChecked.getName());
        }

        if (countryChecked.getId() != null && !countryChecked.getId().equals("")) {
          urlBuilder.append("&nation=");
          urlBuilder.append(countryChecked.getId());
        }
        if (positionDetailChecked != null && !positionDetailChecked.equals(
            getString(R.string.title_all))) {
          urlBuilder.append("&position=");
          urlBuilder.append(positionDetailChecked);
        } else {
          if (positionGeneralityChecked != null && !positionGeneralityChecked.equals(
              getString(R.string.title_all))) {
            urlBuilder.append("&position=");
            if (positionGeneralityChecked.equals("FWD")) {
              String[] strings =
                  getContext().getResources().getStringArray(R.array.position_detail_fwd);
              for (int i = 1; i < strings.length; i++) {
                urlBuilder.append(strings[i]);
                if (i < strings.length - 1) {
                  urlBuilder.append(",");
                }
              }
            } else if (positionGeneralityChecked.equals("MID")) {
              String[] strings =
                  getContext().getResources().getStringArray(R.array.position_detail_mid);
              for (int i = 1; i < strings.length; i++) {
                urlBuilder.append(strings[i]);
                if (i < strings.length - 1) {
                  urlBuilder.append(",");
                }
              }
            } else if (positionGeneralityChecked.equals("DEF")) {
              String[] strings =
                  getContext().getResources().getStringArray(R.array.position_detail_def);
              for (int i = 1; i < strings.length; i++) {
                urlBuilder.append(strings[i]);
                if (i < strings.length - 1) {
                  urlBuilder.append(",");
                }
              }
            } else if (positionGeneralityChecked.equals("GK")) {
              urlBuilder.append("GK");
            }
          }
        }
        if (abilityChecked != null
            && !abilityChecked.equals(getString(R.string.title_all))
            && !abilityChecked.equals("")
            && abilityValueChecked != null
            && !abilityValueChecked.equals(getString(R.string.title_all))) {
          urlBuilder.append("&ability=");
          urlBuilder.append(abilityChecked);
          urlBuilder.append("_");
          urlBuilder.append(abilityValueChecked);
        }
        if (skillMovesChecked != null
            && !skillMovesChecked.equals(getString(R.string.title_all))
            && !skillMovesChecked.equals("0")) {
          urlBuilder.append("&skillmoves=");
          urlBuilder.append(skillMovesChecked);
        }
        if (liveBoostChecked) {
          urlBuilder.append("&liveboost=yes");
        }
        if (limitedChecked) {
          urlBuilder.append("&limited=current");
        }
        //new LoadLiveBootPlayerTask().execute(urlBuilder.toString());
        getJsoupPlayerList(urlBuilder.toString());
      }
    }
  }

  private void setUpRecyclerViewPlayer() {
    playerRealmResults = realm.where(PlayerR.class).findAll();
    playerListAdapter =
        new PlayerListRealmAdapter(getContext(), playerRealmResults, itemClickListener);
    recyclerPlayerFragment.setAdapter(playerListAdapter);
  }

  @Override public void onResume() {
    super.onResume();
  }

  private void setUpSpinner() {

    //loadStringArray();
    //loadJsonFromFile("league.json");
    //
    //adapterSeason =
    //    new ArrayAdapter<Season>(getContext(), android.R.layout.simple_dropdown_item_1line,
    //        seasonList);
    //spinnerSeason.setAdapter(adapterSeason);
    //spinnerSeason.setOnItemSelectedListener(this);
    //
    //adapterLeague =
    //    new ArrayAdapter<League>(getContext(), android.R.layout.simple_dropdown_item_1line,
    //        leagueList);
    //spinnerLeague.setAdapter(adapterLeague);
    //spinnerLeague.setOnItemSelectedListener(this);
    //
    //adapterClub =
    //    new ArrayAdapter<Club>(getContext(), android.R.layout.simple_dropdown_item_1line, clubList);
    //spinnerClub.setAdapter(adapterClub);
    //spinnerClub.setOnItemSelectedListener(this);
    //
    //adapterCountry =
    //    new ArrayAdapter<Country>(getContext(), android.R.layout.simple_dropdown_item_1line,
    //        countryList);
    //spinnerCountry.setAdapter(adapterCountry);
    //spinnerCountry.setOnItemSelectedListener(this);
    //
    //adapterPositionGenerality =
    //    new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,
    //        stringPositionGenerality);
    //spinnerPositionGenerality.setAdapter(adapterPositionGenerality);
    //spinnerPositionGenerality.setOnItemSelectedListener(this);
    //
    //adapterPositionDetail =
    //    new ArrayAdapter<PositionR>(getContext(), android.R.layout.simple_dropdown_item_1line,
    //        positionDetailList);
    //spinnerPositionDetail.setAdapter(adapterPositionDetail);
    //spinnerPositionDetail.setOnItemSelectedListener(this);
    //
    //adapterAbility =
    //    new ArrayAdapter<Ability>(getContext(), android.R.layout.simple_dropdown_item_1line,
    //        abilityList);
    //spinnerAbility.setAdapter(adapterAbility);
    //spinnerAbility.setOnItemSelectedListener(this);
    //
    //adapterAbilityValue =
    //    new ArrayAdapter<AbilityValue>(getContext(), android.R.layout.simple_dropdown_item_1line,
    //        abilityValueList);
    //spinnerAbilityValue.setAdapter(adapterAbilityValue);
    //spinnerAbilityValue.setOnItemSelectedListener(this);
    //
    //adapterSkillMoves =
    //    new ArrayAdapter<SkillMoves>(getContext(), android.R.layout.simple_dropdown_item_1line,
    //        skillMovesList);
    //spinnerSkillMoves.setAdapter(adapterSkillMoves);
    //spinnerSkillMoves.setOnItemSelectedListener(this);
  }

  //private void loadStringArray() {
  //  if (seasonList.size() > 0) seasonList.clear();
  //  String[] stringSeasonIds = getResources().getStringArray(R.array.season_id);
  //  String[] stringSeasonNames = getResources().getStringArray(R.array.season_name);
  //  for (int i = 0; i < stringSeasonIds.length; i++) {
  //    Season season = new Season();
  //    season.setId(stringSeasonIds[i]);
  //    season.setName(stringSeasonNames[i]);
  //    seasonList.add(season);
  //  }
  //  if (countryList.size() > 0) countryList.clear();
  //  String[] stringCountryIds = getResources().getStringArray(R.array.country_id);
  //  String[] stringCountryNames = getResources().getStringArray(R.array.country_name);
  //  for (int i = 0; i < stringCountryIds.length; i++) {
  //    Country country = new Country();
  //    country.setId(stringCountryIds[i]);
  //    country.setName(stringCountryNames[i]);
  //    countryList.add(country);
  //  }
  //  stringPositionGenerality = getResources().getStringArray(R.array.position_generality);
  //  if (abilityList.size() > 0) abilityList.clear();
  //  String[] stringAbilityIds = getResources().getStringArray(R.array.ability_id);
  //  String[] stringAbilityNames = getResources().getStringArray(R.array.ability_name);
  //  for (int i = 0; i < stringAbilityIds.length; i++) {
  //    Ability ability = new Ability();
  //    ability.setId(stringAbilityIds[i]);
  //    ability.setName(stringAbilityNames[i]);
  //    abilityList.add(ability);
  //  }
  //  if (abilityValueList.size() > 0) abilityValueList.clear();
  //  for (int i = 100; i >= 40; i--) {
  //    AbilityValue abilityValue = new AbilityValue();
  //    if (i == 100) {
  //      abilityValue.setName("All");
  //    } else {
  //      abilityValue.setId(i + "");
  //      abilityValue.setName(">=" + i);
  //    }
  //    abilityValueList.add(abilityValue);
  //  }
  //  if (skillMovesList.size() > 0) skillMovesList.clear();
  //  for (int i = 0; i <= 5; i++) {
  //    SkillMoves skillMoves = new SkillMoves();
  //    if (i == 0) {
  //      skillMoves.setName("All");
  //    } else {
  //      skillMoves.setId(i + "");
  //      skillMoves.setName(i + " " + getResources().getString(R.string.skill_moves));
  //    }
  //    skillMovesList.add(skillMoves);
  //  }
  //}
  //
  //private void loadJsonFromFile(String filename) {
  //  try {
  //    InputStream is = getActivity().getAssets().open(filename);
  //    int size = is.available();
  //    byte[] buffer = new byte[size];
  //    is.read(buffer);
  //    is.close();
  //    String bufferString = new String(buffer);
  //    Gson gson = new Gson();
  //    Type listType = new TypeToken<List<League>>() {
  //    }.getType();
  //    League league = new League();
  //    league.setName("All");
  //    leagueList.add(league);
  //    List<League> leagueListGson = gson.fromJson(bufferString, listType);
  //    leagueList.addAll(leagueListGson);
  //  } catch (Exception e) {
  //    e.printStackTrace();
  //  }
  //}

  PlayerListRealmAdapter.OnItemClickListener itemClickListener = player -> {
    Intent intent = new Intent(getContext(), PlayerDetailActivity.class);
    intent.putExtra("id", player.id);
    intent.putExtra("name", player.name);
    intent.putExtra("id_image", player.imageId);
    startActivity(intent);
  };

  //@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
  //  Spinner spinner = (Spinner) parent;
  //  int itemId = spinner.getId();
  //  switch (itemId) {
  //    case R.id.spinner_season:
  //      break;
  //    case R.id.spinner_league:
  //      if (position > 0) {
  //        clubList.clear();
  //        Club club = new Club();
  //        club.setName("All");
  //        clubList.add(0, club);
  //        clubList.addAll(leagueList.get(position).getClubs());
  //        spinnerClub.setSelection(0);
  //      } else {
  //        clubList.clear();
  //      }
  //      adapterClub.notifyDataSetChanged();
  //      break;
  //    case R.id.spinner_club:
  //      break;
  //    case R.id.spinner_country:
  //      break;
  //    case R.id.spinner_position_generality:
  //      if (position != 0 && position != 4) {
  //        String[] arrPositionDetail = new String[0];
  //        positionDetailList.clear();
  //        switch (position) {
  //          case 1:
  //            arrPositionDetail = getResources().getStringArray(R.array.position_detail_fwd);
  //            break;
  //          case 2:
  //            arrPositionDetail = getResources().getStringArray(R.array.position_detail_mid);
  //            break;
  //          case 3:
  //            arrPositionDetail = getResources().getStringArray(R.array.position_detail_def);
  //            break;
  //        }
  //        for (String anArrPositionDetail : arrPositionDetail) {
  //          PositionR positionDetail = new PositionR();
  //          positionDetail.setName(anArrPositionDetail);
  //          positionDetailList.add(positionDetail);
  //        }
  //        adapterPositionDetail.notifyDataSetChanged();
  //      } else {
  //        positionDetailList.clear();
  //        adapterPositionDetail.notifyDataSetChanged();
  //      }
  //      break;
  //    case R.id.spinner_position_detail:
  //      break;
  //    case R.id.spinner_ability:
  //      if (position == 0) {
  //        abilityValueList.clear();
  //      } else {
  //        for (int i = 100; i >= 40; i--) {
  //          AbilityValue abilityValue = new AbilityValue();
  //          if (i == 100) {
  //            abilityValue.setName("All");
  //          } else {
  //            abilityValue.setId(i + "");
  //            abilityValue.setName(">=" + i);
  //          }
  //          abilityValueList.add(abilityValue);
  //        }
  //      }
  //      adapterAbilityValue.notifyDataSetChanged();
  //      break;
  //    case R.id.spinner_ability_value:
  //
  //      break;
  //    case R.id.spinner_skill_moves:
  //      break;
  //  }
  //}
  //
  //@Override public void onNothingSelected(AdapterView<?> parent) {
  //
  //}

  @Override public void onClick(View v) {
    //int id = v.getId();
    //switch (id) {
    //  case R.id.button_search:
    //    break;
    //  case R.id.button_reset:
    //    break;
    //}
  }

  //class LoadLiveBootPlayerTask extends AsyncTask<String, Void, List<Player>> {
  //
  //  private Elements elementsPlayer;
  //
  //  private Document doc;
  //  private Player player;
  //  private ArrayList<Position> positionList;
  //
  //  @Override protected void onPreExecute() {
  //    super.onPreExecute();
  //    if (playerList.size() > 0) {
  //      playerList.clear();
  //      playerListAdapter.notifyDataSetChanged();
  //    }
  //    if (progressLoadingView.getVisibility() == View.GONE) {
  //      progressLoadingView.setVisibility(View.VISIBLE);
  //    }
  //    //if (linearLayoutSearch.getVisibility() == View.VISIBLE) {
  //    //  linearLayoutSearch.setVisibility(View.GONE);
  //    //}
  //  }
  //
  //  @Override protected List<Player> doInBackground(String... params) {
  //    String url = params[0];
  //    //realm = Realm.getDefaultInstance();
  //    try {
  //      doc = Jsoup.connect(url).get();
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //    elementsPlayer = doc.select("tr.player-row");
  //
  //    for (final Element element : elementsPlayer) {
  //
  //      player = new Player();
  //      player.id = element.attr("id").split("player_id")[1];
  //      player.name = element.getElementsByClass("label_xs").text();
  //      player.rosterUpdate = element.getElementsByClass("rosterupdate_statchange statup").text();
  //      player.liveBoost = element.getElementsByClass("rosterupdate_statchange  liveboost").text();
  //      player.skillmoves = element.getElementsByClass("player_info_list player_skillmoves")
  //          .select("i.fa-star")
  //          .size();
  //      player.imageId = element.getElementsByClass("player_img img-rounded")
  //          .attr("src")
  //          .split("small/p")[1].split(".jpg")[0];
  //      //realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(player));
  //      if (element.getElementsByClass("player_position_list").text() != null) {
  //        //realm.executeTransaction(realm12 -> {
  //        //  PlayerR playerResult =
  //        //      realm12.where(PlayerR.class).equalTo("id", player.getId()).findFirst();
  //        player.positions = new ArrayList<>();
  //        for (final Element elementPosition : element.getElementsByClass("player_position_list")) {
  //          Position position = new Position();
  //          position.setName(elementPosition.getElementsByClass("badge_position").text());
  //          int valuePosition =
  //              Integer.parseInt(elementPosition.getElementsByClass("stat_value").text()) + 5;
  //          position.setValue(valuePosition + "");
  //          player.positions.add(position);
  //        }
  //        player.season = com.huynn109.fifaonline3addict.util.Season.getSeasonFromClass(
  //            element.getElementsByClass("player_info_list player_name")
  //                .first()
  //                .getElementsByClass("badged")
  //                .first()
  //                .attr("class")
  //                .split("badged")[1].trim());
  //        //realm12.copyToRealmOrUpdate(playerResult);
  //        //});
  //      }
  //      playerList.add(player);
  //    }
  //    return playerList;
  //  }
  //
  //  @Override protected void onPostExecute(List<Player> playerList) {
  //    super.onPostExecute(playerList);
  //    DatabaseHelper databaseHelper = new DatabaseHelper(realm);
  //    databaseHelper.saveListPlayerToRealm(playerList);
  //    if (progressLoadingView.getVisibility() == View.VISIBLE) {
  //      progressLoadingView.setVisibility(View.GONE);
  //    }
  //    playerRealmQuery = realm.where(PlayerR.class);
  //    if (seasonChecked.size() > 0) {
  //      for (Season season : seasonChecked) {
  //        if (seasonChecked.size() > 1) {
  //          playerRealmQuery.equalTo("season", season.getId()).or();
  //        } else {
  //          playerRealmQuery.equalTo("season", season.getId());
  //        }
  //      }
  //    }
  //    playerRealmResults = playerRealmQuery.findAll();
  //    playerListAdapter =
  //        new PlayerListRealmAdapter(getContext(), playerRealmResults, itemClickListener);
  //    recyclerPlayerFragment.setAdapter(playerListAdapter);
  //  }
  //}

  //@OnClick(R.id.button_search) void onClickButtonSearch(View view) {
  //  actionSearch();
  //}

  private void actionSearch() {
    //StringBuilder stringBuilderSearchUrl = new StringBuilder(URL.SEARCH_URL);
    //if (!TextUtils.isEmpty(editTextSearchPlayer.getText().toString().trim())) {
    //  stringBuilderSearchUrl.append("&name=")
    //      .append(editTextSearchPlayer.getText().toString().trim());
    //}
    //if (spinnerSeason.getSelectedItemPosition() != 0) {
    //  stringBuilderSearchUrl.append("&season=")
    //      .append(seasonList.get(spinnerSeason.getSelectedItemPosition()).getId());
    //}
    //new LoadLiveBootPlayerTask().execute(stringBuilderSearchUrl.toString());
  }

  //@OnClick(R.id.button_reset) void onClickButtonReset() {
  //
  //}
}
