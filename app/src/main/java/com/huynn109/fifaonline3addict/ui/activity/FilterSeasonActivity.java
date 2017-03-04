package com.huynn109.fifaonline3addict.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.normal.Season;
import com.huynn109.fifaonline3addict.ui.adapter.SeasonAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterSeasonActivity extends BaseActivity {

  private static final String TAG = FilterSeasonActivity.class.getSimpleName();
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.button_finish_season) ImageView buttonFinishSeason;
  @BindView(R.id.recycler_season) RecyclerView recyclerViewSeason;
  private List<Season> seasonList = new ArrayList<>();
  private List<Season> resultSeasonList = new ArrayList<>();
  private SeasonAdapter seasonAdapter;
  private HashMap<Integer, Boolean> isChecked = new HashMap<>();
  private ArrayList<Season> seasonChecked = new ArrayList<>();

  @Override protected int getDefaultLayout() {
    return R.layout.activity_filter_season;
  }

  @Override protected int getMdCoreLayout() {
    return R.layout.activity_filter_season;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    parseData();
    setupToolbar();
    setupRecyclerView();
  }

  private void parseData() {
    if (seasonList.size() > 0) seasonList.clear();
    String[] stringSeasonIds = getResources().getStringArray(R.array.season_id);
    String[] stringSeasonNames = getResources().getStringArray(R.array.season_name);
    for (int i = 0; i < stringSeasonIds.length; i++) {
      Season season = new Season();
      season.setId(stringSeasonIds[i]);
      season.setName(stringSeasonNames[i]);
      seasonList.add(season);
    }
    Intent intent = getIntent();
    seasonChecked = (ArrayList<Season>) intent.getSerializableExtra("season");
    if (seasonChecked.size() > 0) {
      for (Season season : seasonChecked) {
        for (Season seasonOfList : seasonList) {
          if (season.getId().equals(seasonOfList.getId())) {
            isChecked.put(seasonList.indexOf(seasonOfList), true);
          }
        }
      }
    }
  }

  private void setupRecyclerView() {
    recyclerViewSeason.setLayoutManager(new LinearLayoutManager(this));
    seasonAdapter = new SeasonAdapter(this, seasonList, isChecked);
    recyclerViewSeason.setAdapter(seasonAdapter);
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(R.string.title_filter_season);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Nullable @OnClick(R.id.button_finish_season) void onClickButtonSeason(View view) {
    isChecked.putAll(seasonAdapter.getChecked());
    for (Map.Entry<Integer, Boolean> entry : isChecked.entrySet()) {
      if (entry.getValue()) {
        resultSeasonList.add(seasonList.get(entry.getKey()));
      }
    }
    Intent intent = new Intent();
    intent.putExtra("season", (Serializable) resultSeasonList);
    setResult(RESULT_OK, intent);
    finish();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.filter_season, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case android.R.id.home:
        finish();
        return true;
      case R.id.action_remove:
        isChecked.clear();
        seasonAdapter.notifyDataSetChanged();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
