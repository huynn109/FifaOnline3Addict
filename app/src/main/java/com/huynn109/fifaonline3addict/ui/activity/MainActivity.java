package com.huynn109.fifaonline3addict.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.ui.fragment.CompareFragment;
import com.huynn109.fifaonline3addict.ui.fragment.FavoriteFragment;
import com.huynn109.fifaonline3addict.ui.fragment.PlayerListFragment;
import com.huynn109.fifaonline3addict.ui.fragment.TaxFragment;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.drawer_layout) DrawerLayout drawer;
  @BindView(R.id.appbar_main) AppBarLayout appBarLayout;
  @BindView(R.id.frame_main) FrameLayout frameMain;
  @BindView(R.id.adView) AdView adView;
  @BindView(R.id.nav_view) NavigationView navView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    onNavigationItemSelected(navigationView.getMenu().getItem(0));

    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);
  }

  @Override public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody") @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();
    Fragment fragment = null;
    Class fragmentClass = null;
    if (id == R.id.nav_home) {
      fragmentClass = PlayerListFragment.class;
    } else if (id == R.id.nav_tax) {
      fragmentClass = TaxFragment.class;
    } else if (id == R.id.nav_compare) {
      fragmentClass = CompareFragment.class;
    } else if (id == R.id.nav_favorite) {
      fragmentClass = FavoriteFragment.class;
    } else if (id == R.id.nav_share) {
      return false;
    }
    try {
      fragment = (Fragment) (fragmentClass != null ? fragmentClass.newInstance() : null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Insert the fragment by replacing any existing fragment
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.frame_main, fragment).commit();

    item.setChecked(true);

    setTitle(item.getTitle());

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
