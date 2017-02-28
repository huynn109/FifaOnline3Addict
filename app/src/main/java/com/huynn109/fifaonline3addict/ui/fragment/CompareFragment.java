package com.huynn109.fifaonline3addict.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompareFragment extends Fragment {

  private View rootView;

  public CompareFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.fragment_compare, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
    if (menu.findItem(R.id.action_search) != null) {
      menu.findItem(R.id.action_search).setVisible(false);
    }
  }
}
