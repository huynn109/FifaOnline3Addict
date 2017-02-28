package com.huynn109.fifaonline3addict.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huynn109.fifaonline3addict.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaperReviewFragment extends Fragment {

  public PaperReviewFragment() {
    // Required empty public constructor
  }

  public static PaperReviewFragment newInstance() {
    Bundle args = new Bundle();
    PaperReviewFragment fragment = new PaperReviewFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_paper_review, container, false);
  }
}
