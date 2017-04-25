package com.huynn109.fifaonline3addict.ui.activity;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.config.Constants;
import com.huynn109.fifaonline3addict.ui.fragment.CompareFragment;
import com.huynn109.fifaonline3addict.ui.fragment.FavoriteFragment;
import com.huynn109.fifaonline3addict.ui.fragment.PlayerListFragment;
import com.huynn109.fifaonline3addict.ui.fragment.TaxFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import me.henrytao.mdcore.core.MdCompat;

public class MainActivity extends BaseActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.drawer_layout) DrawerLayout drawer;
  @BindView(R.id.appbar_main) AppBarLayout appBarLayout;
  @BindView(R.id.frame_main) FrameLayout frameMain;
  @BindView(R.id.nav_view) NavigationView navView;
  @BindView(R.id.search_view) MaterialSearchView searchView;
  @BindView(R.id.toolbar_container) FrameLayout toolbarContainer;
  private ActionBarDrawerToggle mActionBarDrawerToggle;

  @Override protected int getDefaultLayout() {
    return 0;
  }

  @Override protected int getMdCoreLayout() {
    return R.layout.activity_main;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    mActionBarDrawerToggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.addDrawerListener(mActionBarDrawerToggle);
    mActionBarDrawerToggle.syncState();

    navView.setNavigationItemSelectedListener(
        item -> onNavigationItemSelected(GravityCompat.START, item));
    ImageView leftLogo = (ImageView) navView.getHeaderView(0).findViewById(R.id.imageView);
    MdCompat.supportDrawableTint(this, leftLogo.getDrawable(), MdCompat.Palette.BACKGROUND);

    if (savedInstanceState == null) {
      MenuItem item =  navView.getMenu().getItem(0);
      onNavigationItemSelected(GravityCompat.START,item);
    }
  }

  private boolean onNavigationItemSelected(@Gravity int type, MenuItem item) {
    drawer.closeDrawers();
    drawer.postDelayed(() -> onNavigationItemSelected(type, item.getItemId()),
        Constants.Timer.SHORT);
    item.setChecked(true);
    setTitle(item.getTitle());
    return true;
  }

  private void onNavigationItemSelected(@Gravity int type, int menuItemId) {
    Fragment fragment = null;
    Class fragmentClass = null;

    switch (menuItemId) {
      case R.id.nav_home:
        fragmentClass = PlayerListFragment.class;
        break;
      case R.id.nav_tax:
        fragmentClass = TaxFragment.class;
        break;
      case R.id.nav_compare:
        fragmentClass = CompareFragment.class;
        break;
      case R.id.nav_favorite:
        fragmentClass = FavoriteFragment.class;
        break;
      case R.id.nav_share:
        showThemePicker();
        return;
    }
    try {
      fragment = (Fragment) (fragmentClass != null ? fragmentClass.newInstance() : null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.frame_main, fragment).commit();
  }

  @IntDef({ GravityCompat.START, GravityCompat.END }) @Retention(RetentionPolicy.SOURCE)
  private @interface Gravity {

  }

  @Override public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START) || searchView.isSearchOpen()) {
      if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
      }
      if (searchView.isSearchOpen()) {
        searchView.closeSearch();
      }
    } else {
      super.onBackPressed();
    }
  }
}
