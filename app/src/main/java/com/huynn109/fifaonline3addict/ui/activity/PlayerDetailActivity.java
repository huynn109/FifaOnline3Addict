package com.huynn109.fifaonline3addict.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.helper.DatabaseHelper;
import com.huynn109.fifaonline3addict.data.model.normal.Player;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerR;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerStatR;
import com.huynn109.fifaonline3addict.data.model.realm.PositionR;
import com.huynn109.fifaonline3addict.ui.fragment.PaperHiddenStatFragment;
import com.huynn109.fifaonline3addict.ui.fragment.PaperReviewFragment;
import com.huynn109.fifaonline3addict.ui.fragment.PaperStatFragment;
import com.huynn109.fifaonline3addict.util.AdmobUtil;
import com.huynn109.fifaonline3addict.util.Season;
import com.huynn109.fifaonline3addict.util.URL;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PlayerDetailActivity extends BaseActivity {

  private static final String TAG = PlayerDetailActivity.class.getSimpleName();
  @BindView(R.id.image_avatar_player) CircleImageView imageAvatarPlayer;
  @BindView(R.id.text_season) TextView textSeason;
  @BindView(R.id.image_season_wb) ImageView imageSeasonWb;
  @BindView(R.id.image_season_wl) ImageView imageSeasonWl;
  @BindView(R.id.image_season) ImageView imageSeason;
  @BindView(R.id.text_name) TextView textName;
  @BindView(R.id.text_view_roster_update) TextView textViewRosterUpdate;
  @BindView(R.id.text_view_live_boost) TextView textViewLiveBoost;
  @BindView(R.id.linear_title) LinearLayout linearTitle;
  @BindView(R.id.text_foot_left) TextView textFootLeft;
  @BindView(R.id.text_foot_right) TextView textFootRight;
  @BindView(R.id.linear_foot) LinearLayout linearFoot;
  @BindView(R.id.linear_top_layer) RelativeLayout linearTopLayer;
  @BindView(R.id.img_star_1) ImageView imgStar1;
  @BindView(R.id.img_star_2) ImageView imgStar2;
  @BindView(R.id.img_star_3) ImageView imgStar3;
  @BindView(R.id.img_star_4) ImageView imgStar4;
  @BindView(R.id.img_star_5) ImageView imgStar5;
  @BindView(R.id.linear_star) LinearLayout linearStar;
  @BindView(R.id.text_view_position_1_name) TextView textViewPosition1Name;
  @BindView(R.id.text_view_position_1_value) TextView textViewPosition1Value;
  @BindView(R.id.linear_1) LinearLayout linear1;
  @BindView(R.id.text_view_position_2_name) TextView textViewPosition2Name;
  @BindView(R.id.text_view_position_2_value) TextView textViewPosition2Value;
  @BindView(R.id.linear_2) LinearLayout linear2;
  @BindView(R.id.text_view_position_3_name) TextView textViewPosition3Name;
  @BindView(R.id.text_view_position_3_value) TextView textViewPosition3Value;
  @BindView(R.id.linear_3) LinearLayout linear3;
  @BindView(R.id.text_view_position_4_name) TextView textViewPosition4Name;
  @BindView(R.id.text_view_position_4_value) TextView textViewPosition4Value;
  @BindView(R.id.linear_4) LinearLayout linear4;
  @BindView(R.id.linear_position) LinearLayout linearPosition;
  @BindView(R.id.text_attack) TextView textAttack;
  @BindView(R.id.text_defence) TextView textDefence;
  @BindView(R.id.text_country) TextView textCountry;
  @BindView(R.id.image_country_flag) ImageView imageCountryFlag;
  @BindView(R.id.text_club) TextView textClub;
  @BindView(R.id.image_logo_club) ImageView imageLogoClub;
  @BindView(R.id.text_birth_day) TextView textBirthDay;
  @BindView(R.id.text_body) TextView textBody;
  @BindView(R.id.relative_info) RelativeLayout relativeInfo;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.collapsing_layout) CollapsingToolbarLayout collapsingLayout;
  @BindView(R.id.tab_stat) DachshundTabLayout tabStat;
  @BindView(R.id.app_bar) AppBarLayout appBar;
  @BindView(R.id.view_pager_stat) ViewPager viewPagerStat;
  @BindView(R.id.ad_player_detail) AdView adPlayerDetail;
  private String idPlayer;
  private String idStat;
  private Realm realm;
  private PlayerR playerRealm;
  private static final String DOG_BREEDS[] = { "OVR", "Chỉ số đặc biệt", "Review" };
  private ViewPagerAdapter viewPagerAdapter;
  private Player player;
  private PlayerStatR playerStatR;
  private Observer<Document> subscribePlayerInfo;
  private List<Integer> listSpeciality = new ArrayList<>();
  private List<Integer> listTrait = new ArrayList<>();

  @Override protected int getDefaultLayout() {
    return R.layout.activity_player_detail;
  }

  @Override protected int getMdCoreLayout() {
    return R.layout.activity_player_detail;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    realm = Realm.getDefaultInstance();
    ButterKnife.bind(this);
    getExtraFromList();
    handleSeason();
    handlePositionList();
    setupToolbar();
    excuteTask();
    setupFragment();
    setupAds();
  }

  private void setupAds() {
    AdRequest adRequest = AdmobUtil.getAdMobRequest(this);
    if (adRequest != null) {
      adPlayerDetail.loadAd(adRequest);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    realm = Realm.getDefaultInstance();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return super.onOptionsItemSelected(item);
  }

  private void setupFragment() {
    viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    viewPagerAdapter.addFragment(PaperStatFragment.newInstance(playerRealm.id),
        getResources().getString(R.string.title_paper_stat_fragment));
    viewPagerAdapter.addFragment(PaperHiddenStatFragment.newInstance(),
        getResources().getString(R.string.title_paper_hidden_stat_fragment));
    viewPagerAdapter.addFragment(PaperReviewFragment.newInstance(),
        getResources().getString(R.string.title_paper_review_fragment));
    viewPagerStat.setAdapter(viewPagerAdapter);
    tabStat.setupWithViewPager(viewPagerStat);
  }

  class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override public Fragment getItem(int position) {
      return mFragmentList.get(position);
    }

    @Override public int getCount() {
      return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
      mFragmentList.add(fragment);
      mFragmentTitleList.add(title);
    }

    @Override public CharSequence getPageTitle(int position) {
      return mFragmentTitleList.get(position);
    }
  }

  private void handlePositionList() {
    linear1.setVisibility(View.GONE);
    linear2.setVisibility(View.GONE);
    linear3.setVisibility(View.GONE);
    linear4.setVisibility(View.GONE);
    for (int i = 0; i < playerRealm.positions.size(); i++) {
      PositionR positionPlayer = playerRealm.positions.get(i);
      switch (i) {
        case 0:
          linear1.setVisibility(View.VISIBLE);
          handleBackgroudPosiotion(textViewPosition1Name, positionPlayer.getName());
          setNameAndValue(textViewPosition1Name, textViewPosition1Value, positionPlayer);
          break;
        case 1:
          linear2.setVisibility(View.VISIBLE);
          handleBackgroudPosiotion(textViewPosition2Name, positionPlayer.getName());
          setNameAndValue(textViewPosition2Name, textViewPosition2Value, positionPlayer);
          break;
        case 2:
          linear3.setVisibility(View.VISIBLE);
          handleBackgroudPosiotion(textViewPosition3Name, positionPlayer.getName());
          setNameAndValue(textViewPosition3Name, textViewPosition3Value, positionPlayer);
          break;
        case 3:
          linear4.setVisibility(View.VISIBLE);
          handleBackgroudPosiotion(textViewPosition4Name, positionPlayer.getName());
          setNameAndValue(textViewPosition4Name, textViewPosition4Value, positionPlayer);
          break;
      }
    }
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

  private void excuteTask() {
    getJsoupPlayerDetail(URL.FIFA_ADDICT_PLAYER_URL + playerRealm.id);
    idStat = playerRealm.id;
    if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season16))) {
      idStat = playerRealm.imageId;
      if (idStat.length() == 5) idStat = "0" + idStat;
      idStat = "12" + idStat;
    }
    getJsoupPlayerStat(URL.KR_INVEN_PLAYER_URL + idStat);
  }

  private void getJsoupPlayerStat(String url) {
    getJsoupPlayerStatDocument(url).subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .doOnNext(this::parsePlayerStat)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscribePlayerStat());
  }

  private Observer<? super Document> subscribePlayerStat() {
    return new Observer<Document>() {
      @Override public void onSubscribe(Disposable d) {

      }

      @Override public void onNext(Document value) {
        DatabaseHelper databaseHelper = new DatabaseHelper(realm);
        databaseHelper.updatePlayerStatRealm(playerStatR, idPlayer);
        if (listSpeciality.size() > 0) {
          databaseHelper.updatePlayerSpecialityRealm(listSpeciality, idPlayer);
        }
        if (listTrait.size() > 0) {
          databaseHelper.updatePlayerTraitRealm(listTrait, idPlayer);
        }
        EventBus.getDefault().postSticky(new MessageEvent(playerRealm.id));
      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onComplete() {

      }
    };
  }

  private void parsePlayerStat(Document doc) {
    Element elementStat = doc.select("div#stateSort").first();
    if (elementStat != null) {
      playerStatR = new PlayerStatR();
      playerStatR.strength = Integer.valueOf(
          elementStat.getElementsByClass("strength").first().select("span.point").text()) + 5;
      playerStatR.sprintspeed = Integer.valueOf(
          elementStat.getElementsByClass("sprintspeed").first().select("span.point").text()) + 5;
      playerStatR.jumping = Integer.valueOf(
          elementStat.getElementsByClass("jumping").first().select("span.point").text()) + 5;
      playerStatR.slidingtackle = Integer.valueOf(
          elementStat.getElementsByClass("slidingtackle").first().select("span.point").text()) + 5;
      playerStatR.ballcontrol = Integer.valueOf(
          elementStat.getElementsByClass("ballcontrol").first().select("span.point").text()) + 5;
      playerStatR.marking = Integer.valueOf(
          elementStat.getElementsByClass("marking").first().select("span.point").text()) + 5;
      playerStatR.standingtackle = Integer.valueOf(
          elementStat.getElementsByClass("standingtackle").first().select("span.point").text()) + 5;
      playerStatR.shortpassing = Integer.valueOf(
          elementStat.getElementsByClass("shortpassing").first().select("span.point").text()) + 5;
      playerStatR.headingaccuracy = Integer.valueOf(
          elementStat.getElementsByClass("headingaccuracy").first().select("span.point").text())
          + 5;
      playerStatR.tacticalawareness = Integer.valueOf(
          elementStat.getElementsByClass("interceptions").first().select("span.point").text()) + 5;
      playerStatR.reactions = Integer.valueOf(
          elementStat.getElementsByClass("reactions").first().select("span.point").text()) + 5;
      playerStatR.aggression = Integer.valueOf(
          elementStat.getElementsByClass("aggression").first().select("span.point").text()) + 5;
      playerStatR.stamina = Integer.valueOf(
          elementStat.getElementsByClass("stamina").first().select("span.point").text()) + 5;
      playerStatR.stamina = Integer.valueOf(
          elementStat.getElementsByClass("stamina").first().select("span.point").text()) + 5;
      playerStatR.acceleration = Integer.valueOf(
          elementStat.getElementsByClass("acceleration").first().select("span.point").text()) + 5;
      playerStatR.agility = Integer.valueOf(
          elementStat.getElementsByClass("agility").first().select("span.point").text()) + 5;
      playerStatR.balance = Integer.valueOf(
          elementStat.getElementsByClass("balance").first().select("span.point").text()) + 5;
      playerStatR.dribbling = Integer.valueOf(
          elementStat.getElementsByClass("dribbling").first().select("span.point").text()) + 5;
      playerStatR.crossing = Integer.valueOf(
          elementStat.getElementsByClass("crossing").first().select("span.point").text()) + 5;
      playerStatR.finishing = Integer.valueOf(
          elementStat.getElementsByClass("finishing").first().select("span.point").text()) + 5;
      playerStatR.longpassing = Integer.valueOf(
          elementStat.getElementsByClass("longpassing").first().select("span.point").text()) + 5;
      playerStatR.shotpower = Integer.valueOf(
          elementStat.getElementsByClass("shotpower").first().select("span.point").text()) + 5;
      playerStatR.longshots = Integer.valueOf(
          elementStat.getElementsByClass("longshots").first().select("span.point").text()) + 5;
      playerStatR.volleys = Integer.valueOf(
          elementStat.getElementsByClass("volleys").first().select("span.point").text()) + 5;
      playerStatR.curve = Integer.valueOf(
          elementStat.getElementsByClass("curve").first().select("span.point").text()) + 5;
      playerStatR.freekickaccuracy = Integer.valueOf(
          elementStat.getElementsByClass("freekickaccuracy").first().select("span.point").text())
          + 5;
      playerStatR.penalties = Integer.valueOf(
          elementStat.getElementsByClass("penalties").first().select("span.point").text()) + 5;
      playerStatR.positioning = Integer.valueOf(
          elementStat.getElementsByClass("positioning").first().select("span.point").text()) + 5;
      playerStatR.vision = Integer.valueOf(
          elementStat.getElementsByClass("vision").first().select("span.point").text()) + 5;
      playerStatR.gkdiving = Integer.valueOf(
          elementStat.getElementsByClass("gkdiving").first().select("span.point").text()) + 5;
      playerStatR.gkkicking = Integer.valueOf(
          elementStat.getElementsByClass("gkkicking").first().select("span.point").text()) + 5;
      playerStatR.gkhandling = Integer.valueOf(
          elementStat.getElementsByClass("gkhandling").first().select("span.point").text()) + 5;
      playerStatR.gkpositioning = Integer.valueOf(
          elementStat.getElementsByClass("gkpositioning").first().select("span.point").text()) + 5;
      playerStatR.gkreflexes = Integer.valueOf(
          elementStat.getElementsByClass("gkreflexes").first().select("span.point").text()) + 5;

      Elements elementSpeciality = doc.select("div.badgeList").select("ul").select("li");
      if (elementSpeciality != null) {
        listSpeciality.clear();
        for (Element element : elementSpeciality) {
          int positionSpeciality = Integer.valueOf(
              element.select("img").attr("src").split("badgeicon_")[1].split(".png")[0]);
          if (positionSpeciality != 0) {
            listSpeciality.add(positionSpeciality);
          }
        }
      }

      Elements elementTrait = doc.select("div.traitsList").select("ul").select("li");
      if (elementTrait != null) {
        listTrait.clear();
        for (Element element : elementTrait) {
          int positionTrait =
              Integer.valueOf(element.select("a").attr("href").split("inTraits=")[1]);
          if (positionTrait != 0) {
            listTrait.add(positionTrait);
          }
        }
      }
    }
  }

  private Observable<Document> getJsoupPlayerStatDocument(String url) {
    return Observable.defer(() -> Observable.just(Jsoup.connect(url).get()));
  }

  public void getJsoupPlayerDetail(String url) {
    getJsoupPlayerDetailDocument(url)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .doOnNext(this::parsePlayerInfo)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscribePlayerInfo());
  }

  private Observer<? super Document> subscribePlayerInfo() {
    return subscribePlayerInfo = new Observer<Document>() {
      @Override public void onSubscribe(Disposable d) {

      }

      @Override public void onNext(Document document) {
        DatabaseHelper databaseHelper = new DatabaseHelper(realm);
        databaseHelper.updatePlayerInfoToRealm(player);

        textName.setText(playerRealm.name);
        Glide.with(PlayerDetailActivity.this)
            .load("https:" + player.imageRealUrl)
            .into(imageAvatarPlayer);
        Glide.with(PlayerDetailActivity.this)
            .load("http://flagpedia.net/data/flags/mini/" + player.countryFlag + ".png")
            .into(imageCountryFlag);
        textCountry.setText(player.country);
        textClub.setText(player.club);
        Glide.with(PlayerDetailActivity.this)
            .load("https://vn.fifaaddict.com/" + player.clubLogo)
            .into(imageLogoClub);
        textBirthDay.setText(player.birthDay);
        textBody.setText(player.height + "cm - " + player.weight + "kg");
        textFootLeft.setText(String.valueOf(player.leftFoot));
        textFootRight.setText(String.valueOf(player.rightFoot));
        handleFootBackgroundColor();

        textAttack.setText(String.valueOf(player.attack));
        textDefence.setText(String.valueOf(player.defence));
      }

      @Override public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e);
      }

      @Override public void onComplete() {

      }
    };
  }

  private void parsePlayerInfo(Document doc) {
    if (doc == null) return;
    player = new Player();
    Elements elements = doc.select("div.player_info_list");
    Element elementImageAvatar = doc.select("div.player_img_wrapper").first();
    Element elementNation = elements.get(0);
    player.imageRealUrl = elementImageAvatar.select("img").attr("src");
    player.country = elementNation.getElementsByClass("player_nation").text();
    player.countryFlag = elementNation.getElementsByClass("player_nation")
        .select("i")
        .attr("class")
        .split("flag ")[1];
    player.club = elementNation.getElementsByClass("player_club").text();
    player.clubLogo = elementNation.getElementsByClass("club_crest").attr("src");
    Element elementBody = elements.get(1);
    player.birthDay = elementBody.select("b").get(0).text();
    player.height = elementBody.select("b").get(1).text();
    player.weight = elementBody.select("b").get(2).text();

    Element elementWorkrate = doc.select("div.workrate").first();
    player.attack = Integer.valueOf(elementWorkrate.getElementsByClass("att").select("b").text());
    player.defence = Integer.valueOf(elementWorkrate.getElementsByClass("def").select("b").text());

    Element elementFoot = doc.select("div.stat_foot").first();
    player.leftFoot = Integer.valueOf(elementFoot.getElementsByClass("leftfoot").text().trim());
    player.rightFoot = Integer.valueOf(elementFoot.getElementsByClass("rightfoot").text().trim());
  }

  private Observable<Document> getJsoupPlayerDetailDocument(String url) {
    return Observable.defer(() -> Observable.just(Jsoup.connect(url).get()));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    realm.close();
    MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
    if (stickyEvent != null) {
      EventBus.getDefault().removeStickyEvent(stickyEvent);
    }
  }

  private void getExtraFromList() {
    Intent intent = getIntent();
    idPlayer = intent.getStringExtra("id");
    playerRealm = realm.where(PlayerR.class).equalTo("id", idPlayer).findFirst();
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      boolean isShow = false;
      int scrollRange = -1;

      @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
          scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset == 0) {
          collapsingLayout.setTitle(playerRealm.name);
          isShow = true;
        } else if (isShow) {
          collapsingLayout.setTitle(
              " ");
          isShow = false;
        }
      }
    });
  }

  private void handleFootBackgroundColor() {
    if (player.leftFoot == 5) {
      textFootLeft.setBackgroundColor(ContextCompat.getColor(this, R.color.foot_upon));
    } else {
      textFootLeft.setBackgroundColor(ContextCompat.getColor(this, R.color.foot_not_upon));
    }
    if (player.rightFoot == 5) {
      textFootRight.setBackgroundColor(ContextCompat.getColor(this, R.color.foot_upon));
    } else {
      textFootRight.setBackgroundColor(ContextCompat.getColor(this, R.color.foot_not_upon));
    }
  }

  private void handleSeason() {
    textSeason.setVisibility(View.GONE);
    imageSeasonWb.setVisibility(View.GONE);
    imageSeasonWl.setVisibility(View.GONE);
    imageSeason.setVisibility(View.GONE);
    if (!TextUtils.isEmpty(playerRealm.season)) {
      textSeason.setBackgroundColor(ContextCompat.getColor(this, android.R.color.background_dark));
      textSeason.setTextColor(ContextCompat.getColor(this, android.R.color.primary_text_dark));
      if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season16))) {
        textSeason.setVisibility(View.VISIBLE);
        textSeason.setText("16");
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season15))) {
        textSeason.setVisibility(View.VISIBLE);
        textSeason.setText("15");
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season14))) {
        textSeason.setVisibility(View.VISIBLE);
        textSeason.setText("14");
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season06Wc))
          || playerRealm.season.equals(Season.getSeasonFromClass(Season.season14Wc))
          || playerRealm.season.equals(Season.getSeasonFromClass(Season.season10Wc))) {
        textSeason.setVisibility(View.VISIBLE);
        if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season06Wc))) {
          textSeason.setText("06");
        } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season14Wc))) {
          textSeason.setText("14");
        } else {
          textSeason.setText("10");
        }
        textSeason.setTextColor(ContextCompat.getColor(this, R.color.color_green_wc));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonWb))) {
        imageSeasonWb.setVisibility(View.VISIBLE);
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonLp))) {
        textSeason.setVisibility(View.VISIBLE);
        String text = "<font color=#ffffff>L</font><font color=#e4ac40>P</font>";
        textSeason.setText(Html.fromHtml(text));
        textSeason.setBackgroundColor(ContextCompat.getColor(this, R.color.color_green_lp));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonEc))) {
        textSeason.setVisibility(View.VISIBLE);
        String text = "<font color=#D5D7D6>E</font><font color=#DFBE32>C</font>";
        textSeason.setText(Html.fromHtml(text));
        textSeason.setBackgroundColor(ContextCompat.getColor(this, R.color.color_green_ec));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season06))
          || playerRealm.season.equals(Season.getSeasonFromClass(Season.season07))
          || playerRealm.season.equals(Season.getSeasonFromClass(Season.season08))
          || playerRealm.season.equals(Season.getSeasonFromClass(Season.season09))
          || playerRealm.season.equals(Season.getSeasonFromClass(Season.season10))
          || playerRealm.season.equals(Season.getSeasonFromClass(Season.season11))) {
        textSeason.setVisibility(View.VISIBLE);
        textSeason.setText(playerRealm.season.split("20")[1]);
        textSeason.setTextColor(ContextCompat.getColor(this, R.color.color_yellow_season));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season08Eu))) {
        textSeason.setVisibility(View.VISIBLE);
        textSeason.setText("08");
        textSeason.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season14T))) {
        textSeason.setVisibility(View.VISIBLE);
        textSeason.setText("14");
        textSeason.setTextColor(ContextCompat.getColor(this, R.color.yellow_14t));
        textSeason.setBackgroundColor(ContextCompat.getColor(this, R.color.blu_14t));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season06U))
          || playerRealm.season.equals(Season.getSeasonFromClass(Season.season10U))) {
        textSeason.setVisibility(View.VISIBLE);
        if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season06U))) {
          textSeason.setText("06");
        } else {
          textSeason.setText("10");
        }
        textSeason.setBackgroundColor(ContextCompat.getColor(this, R.color.blu_06u));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season15TauKhua))) {
        textSeason.setVisibility(View.VISIBLE);
        textSeason.setText("15");
        textSeason.setBackgroundColor(ContextCompat.getColor(this, R.color.red_taukhua));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season16W))) {
        textSeason.setVisibility(View.VISIBLE);
        textSeason.setText("W");
        textSeason.setTextColor(ContextCompat.getColor(this, R.color.yellow_16w));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonWl))) {
        imageSeasonWl.setVisibility(View.VISIBLE);
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonMuLegend))
          || playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonTc92))) {
        imageSeason.setVisibility(View.VISIBLE);
        imageSeason.setImageDrawable(this.getResources().getDrawable(R.drawable.mu_logo));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonTlLegend))) {
        imageSeason.setVisibility(View.VISIBLE);
        imageSeason.setImageDrawable(this.getResources().getDrawable(R.drawable.t_legend));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.season16Th))) {
        imageSeason.setVisibility(View.VISIBLE);
        imageSeason.setImageDrawable(this.getResources().getDrawable(R.drawable.th16));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonVnLegend))) {
        imageSeason.setVisibility(View.VISIBLE);
        imageSeason.setImageDrawable(this.getResources().getDrawable(R.drawable.v_legend));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonMalayLegend))) {
        imageSeason.setVisibility(View.VISIBLE);
        imageSeason.setImageDrawable(this.getResources().getDrawable(R.drawable.m_legend));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonU23))) {
        imageSeason.setVisibility(View.VISIBLE);
        imageSeason.setImageDrawable(this.getResources().getDrawable(R.drawable.u23));
      } else if (playerRealm.season.equals(Season.getSeasonFromClass(Season.seasonKxi))) {
        imageSeason.setVisibility(View.VISIBLE);
        imageSeason.setImageDrawable(this.getResources().getDrawable(R.drawable.kxi));
      }
    }
  }

  public static class MessageEvent {
    private String idPlayer;

    MessageEvent(String idPlayer) {
      this.idPlayer = idPlayer;
    }

    public String getIdPlayer() {
      return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
      this.idPlayer = idPlayer;
    }
  }
}
