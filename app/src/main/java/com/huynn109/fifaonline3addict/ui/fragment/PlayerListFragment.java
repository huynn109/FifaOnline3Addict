package com.huynn109.fifaonline3addict.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;
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
public class PlayerListFragment extends Fragment {

  private static final String TAG = PlayerListFragment.class.getSimpleName();
  public static final int REQUEST_CODE_FILTER = 1;
  @BindView(R.id.progress_loading_view) ProgressBar progressLoadingView;
  @BindView(R.id.recycler_player_fragment) RecyclerView recyclerPlayerFragment;
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
  private Club clubChecked = new Club();
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
  private MaterialSearchView searchView;
  private List<PlayerR> playerRList;

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
    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    realm = Realm.getDefaultInstance();
    View rootView = inflater.inflate(R.layout.fragment_player_list, container, false);
    ButterKnife.bind(this, rootView);
    setUpRecyclerViewPlayer();
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
      }

      @Override public void onNext(Document document) {
        DatabaseHelper databaseHelper = new DatabaseHelper(realm);
        databaseHelper.saveListPlayerToRealm(playerList);

        RealmQuery<PlayerR> query = realm.where(PlayerR.class);
        if (playerList.size() > 0) {
          query = query.equalTo("id", playerList.get(0).id);
          for (int i = 1; i < playerList.size(); i++) {
            query = query.or().equalTo("id", playerList.get(i).id);
          }
          playerRList.clear();
          playerRealmResults = query.findAll();
          playerRList.addAll(playerRealmResults);
          playerListAdapter.notifyDataSetChanged();
        } else {
          playerRList.clear();
          playerListAdapter.notifyDataSetChanged();
        }
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

  @Override public void onStart() {
    super.onStart();
    realm = Realm.getDefaultInstance();
  }

  @Override public void onStop() {
    super.onStop();
    realm.close();
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.main, menu);
    searchView = (MaterialSearchView) getActivity().findViewById(R.id.search_view);
    MenuItem item = menu.findItem(R.id.action_search);
    searchView.setMenuItem(item);
    searchView.setOnQueryTextListener(searchViewQueryListener());
    searchView.setOnSearchViewListener(searchViewListener());
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        return true;
      case R.id.action_filter:
        Intent intent = new Intent(getContext(), FilterActivity.class);
        intent.putExtra("season", (Serializable) seasonChecked);
        intent.putExtra("league", (Serializable) leagueChecked);
        intent.putExtra("club", (Serializable) clubChecked);
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

  private MaterialSearchView.OnQueryTextListener searchViewQueryListener() {
    return new MaterialSearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        getJsoupPlayerList(URL.SEARCH_URL_NAME + query.trim());
        return false;
      }

      @Override public boolean onQueryTextChange(String newText) {
        return false;
      }
    };
  }

  private MaterialSearchView.SearchViewListener searchViewListener() {
    return new MaterialSearchView.SearchViewListener() {
      @Override public void onSearchViewShown() {
        //Do some magic
      }

      @Override public void onSearchViewClosed() {
        //Do some magic
      }
    };
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_FILTER) {
      if (resultCode == RESULT_OK) {
        StringBuilder urlBuilder = new StringBuilder(URL.SEARCH_URL);
        seasonChecked = (ArrayList<Season>) data.getSerializableExtra("season");
        leagueChecked = (League) data.getSerializableExtra("league");
        clubChecked = (Club) data.getSerializableExtra("club");
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
          Log.d(TAG, "onActivityResult: " + leagueChecked);
          urlBuilder.append("&league=");
          urlBuilder.append(leagueChecked.getName());
        }

        if (clubChecked.getId() != null) {
          Log.d(TAG, "onActivityResult: " + clubChecked);
        }

        if (countryChecked.getId() != null && !countryChecked.getId().equals("")) {
          Log.d(TAG, "onActivityResult: " + countryChecked);
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
        getJsoupPlayerList(urlBuilder.toString());
      }
    }
  }

  private void setUpRecyclerViewPlayer() {
    playerRealmResults = realm.where(PlayerR.class).findAll();
    playerRList = new ArrayList<>(playerRealmResults);
    playerListAdapter = new PlayerListRealmAdapter(getContext(), playerRList, itemClickListener);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerPlayerFragment.setLayoutManager(layoutManager);
    recyclerPlayerFragment.setAdapter(playerListAdapter);
  }

  @Override public void onResume() {
    super.onResume();
  }

  PlayerListRealmAdapter.OnItemClickListener itemClickListener = player -> {
    Intent intent = new Intent(getContext(), PlayerDetailActivity.class);
    intent.putExtra("id", player.id);
    intent.putExtra("name", player.name);
    intent.putExtra("id_image", player.imageId);
    startActivity(intent);
  };
}
