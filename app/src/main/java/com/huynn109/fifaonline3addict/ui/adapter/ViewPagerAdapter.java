package com.huynn109.fifaonline3addict.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.henrytao.smoothappbarlayout.base.ObservableFragment;
import me.henrytao.smoothappbarlayout.base.ObservablePagerAdapter;

/**
 * Created by huyuit on 3/2/2017.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter implements ObservablePagerAdapter {

  private static String makeTagName(int position) {
    return ViewPagerAdapter.class.getName() + ":" + position;
  }

  private final FragmentManager mFragmentManager;

  private final List<Fragment> mFragments = new ArrayList<>();

  private final Map<Integer, String> mTags = new HashMap<>();

  private final List<CharSequence> mTitles = new ArrayList<>();

  private Bundle mSavedInstanceState = new Bundle();

  public ViewPagerAdapter(FragmentManager fm) {
    super(fm);
    mFragmentManager = fm;
  }

  @Override
  public int getCount() {
    return mFragments.size();
  }

  @Override
  public Fragment getItem(int position) {
    String tagName = mSavedInstanceState.getString(makeTagName(position));
    if (!TextUtils.isEmpty(tagName)) {
      Fragment fragment = mFragmentManager.findFragmentByTag(tagName);
      return fragment != null ? fragment : mFragments.get(position);
    }
    return mFragments.get(position);
  }

  @Override
  public ObservableFragment getObservableFragment(int position) {
    if (getItem(position) instanceof ObservableFragment) {
      return (ObservableFragment) getItem(position);
    }
    return null;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mTitles.get(position);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    Object object = super.instantiateItem(container, position);
    mTags.put(position, ((Fragment) object).getTag());
    return object;
  }

  public void addFragment(CharSequence title, Fragment fragment) {
    mTitles.add(title);
    mFragments.add(fragment);
  }

  public void onRestoreInstanceState(Bundle savedInstanceState) {
    mSavedInstanceState = savedInstanceState != null ? savedInstanceState : new Bundle();
  }

  public void onSaveInstanceState(Bundle outState) {
    Iterator<Map.Entry<Integer, String>> iterator = mTags.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Integer, String> entry = iterator.next();
      outState.putString(makeTagName(entry.getKey()), entry.getValue());
    }
  }
}
