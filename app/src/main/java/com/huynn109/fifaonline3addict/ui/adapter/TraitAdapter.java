package com.huynn109.fifaonline3addict.ui.adapter;

/**
 * Created by huyuit on 2/28/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.normal.Trait;
import java.util.List;

/**
 * Created by huyuit on 2/28/2017.
 */

public class TraitAdapter extends RecyclerView.Adapter<TraitAdapter.TraitHolder> {

  private final LayoutInflater inflater;
  private Context context;
  private List<Trait> traits;

  public TraitAdapter(Context context, List<Trait> traitList) {
    this.context = context;
    this.traits = traitList;
    this.inflater = LayoutInflater.from(context);
  }

  @Override public TraitAdapter.TraitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View convertview = inflater.inflate(R.layout.trait_row, parent, false);
    return new TraitHolder(convertview);
  }

  @Override public void onBindViewHolder(TraitAdapter.TraitHolder holder, int position) {
    holder.textName.setText(traits.get(position).getName());
    holder.textValue.setText(traits.get(position).getValue());
  }

  @Override public int getItemCount() {
    return traits.size();
  }

  class TraitHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_name) TextView textName;
    @BindView(R.id.text_value) TextView textValue;

    TraitHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
