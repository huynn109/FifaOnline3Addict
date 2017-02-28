package com.huynn109.fifaonline3addict.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.normal.League;
import com.huynn109.fifaonline3addict.ui.adapter.LeagueAdapter;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FilterLeagueActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.recycler_league) RecyclerView recyclerViewLeague;
  private List<League> leagueList = new ArrayList<>();
  private LeagueAdapter leagueAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter_league);
    ButterKnife.bind(this);
    setupToolbar();
    parseData();
    setupRecyclerView();
  }

  private void setupRecyclerView() {
    recyclerViewLeague.setLayoutManager(new LinearLayoutManager(this));
    leagueAdapter = new LeagueAdapter(this, leagueList);
    recyclerViewLeague.setAdapter(leagueAdapter);
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(R.string.title_filter_league);
    }
  }

  private void parseData() {
    try {
      InputStream is = this.getAssets().open("league.json");
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      String bufferString = new String(buffer);
      Gson gson = new Gson();
      Type listType = new TypeToken<List<League>>() {
      }.getType();
      List<League> leagueListGson = gson.fromJson(bufferString, listType);
      leagueList.addAll(leagueListGson);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.filter_season, menu);
    return super.onCreateOptionsMenu(menu);
  }
}
