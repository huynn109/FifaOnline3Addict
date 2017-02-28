package com.huynn109.fifaonline3addict.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.normal.Speciality;
import com.huynn109.fifaonline3addict.data.model.normal.Trait;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerR;
import com.huynn109.fifaonline3addict.data.model.realm.SpecialityInteger;
import com.huynn109.fifaonline3addict.data.model.realm.TraitInteger;
import com.huynn109.fifaonline3addict.ui.activity.PlayerDetailActivity;
import com.huynn109.fifaonline3addict.ui.adapter.SpecialityAdapter;
import com.huynn109.fifaonline3addict.ui.adapter.TraitAdapter;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaperHiddenStatFragment extends Fragment {

  private static final String TAG = PaperHiddenStatFragment.class.getSimpleName();
  @BindView(R.id.text_title_speciality) TextView textTitleSpeciality;
  @BindView(R.id.recycler_speciality) RecyclerView recyclerSpeciality;
  @BindView(R.id.text_title_trait) TextView textTitleTrait;
  @BindView(R.id.view_divider_trait) View viewDividerTrait;
  @BindView(R.id.recycler_trait) RecyclerView recyclerTrait;
  @BindView(R.id.view_divider_speciality) View viewDividerSpeciality;
  @BindView(R.id.text_count_trait) TextView textCountTrait;
  @BindView(R.id.text_count_speciality) TextView textCountSpeciality;

  private Realm realm;
  private List<Speciality> specialities = new ArrayList<>();
  private List<String> traitNameList;
  private List<String> traitValueList;
  private SpecialityAdapter specialityAdapter;

  private List<Trait> traits = new ArrayList<>();
  private List<String> specialityNameList;
  private List<String> specialityValueList;
  private TraitAdapter traitAdapter;

  public static PaperHiddenStatFragment newInstance() {
    Bundle args = new Bundle();
    PaperHiddenStatFragment fragment = new PaperHiddenStatFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_paper_hidden_stat, container, false);
    ButterKnife.bind(this, rootView);
    realm = Realm.getDefaultInstance();
    parseStringArrayToList();
    setupRecyclerView();
    return rootView;
  }

  private void parseStringArrayToList() {
    specialityNameList = Arrays.asList(getResources().getStringArray(R.array.speciality_name));
    specialityValueList = Arrays.asList(getResources().getStringArray(R.array.speciality_value));
    traitNameList = Arrays.asList(getResources().getStringArray(R.array.trait_name));
    traitValueList = Arrays.asList(getResources().getStringArray(R.array.trait_value));
  }

  private void setupRecyclerView() {
    recyclerSpeciality.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerSpeciality.setNestedScrollingEnabled(false);
    specialityAdapter = new SpecialityAdapter(getContext(), specialities);
    recyclerSpeciality.setAdapter(specialityAdapter);

    recyclerTrait.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerTrait.setNestedScrollingEnabled(false);
    traitAdapter = new TraitAdapter(getContext(), traits);
    recyclerTrait.setAdapter(traitAdapter);
  }

  @Override public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    EventBus.getDefault().unregister(this);
    realm.close();
    super.onStop();
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  public void onEvent(PlayerDetailActivity.MessageEvent event) {
    PlayerR player = realm.where(PlayerR.class).equalTo("id", event.getIdPlayer()).findFirst();
    int countSpeciality = 0;
    int countTrait = 0;
    if (player != null) {
      if (player.specialityIntegers.size() > 0) {
        if (specialities.size() > 0) specialities.clear();
        for (SpecialityInteger specialityInteger : player.specialityIntegers) {
          Speciality speciality = new Speciality();
          speciality.setName(specialityNameList.get(specialityInteger.speciality - 1));
          speciality.setValue(specialityValueList.get(specialityInteger.speciality - 1));
          speciality.setPosition(specialityInteger.speciality - 1);
          specialities.add(speciality);
        }
        specialityAdapter.notifyDataSetChanged();
        countSpeciality = player.specialityIntegers.size();
      }

      if (player.traitIntegers.size() > 0) {
        if (traits.size() > 0) traits.clear();
        for (TraitInteger traitInteger : player.traitIntegers) {
          Trait trait = new Trait();
          trait.setName(traitNameList.get(traitInteger.trait - 1));
          trait.setValue(traitValueList.get(traitInteger.trait - 1));
          trait.setPosition(traitInteger.trait - 1);
          traits.add(trait);
        }
        traitAdapter.notifyDataSetChanged();
        countTrait = player.traitIntegers.size();
      }
    }
    String sCountTrait = String.format(getString(R.string.text_count), countTrait);
    String sCountSpeciality = String.format(getString(R.string.text_count), countSpeciality);
    textCountTrait.setText(sCountTrait);
    textCountSpeciality.setText(sCountSpeciality);
  }
}
