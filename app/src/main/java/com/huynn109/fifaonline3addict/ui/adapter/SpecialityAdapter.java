package com.huynn109.fifaonline3addict.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.normal.Speciality;
import com.huynn109.fifaonline3addict.util.URL;
import java.util.List;

/**
 * Created by huyuit on 2/28/2017.
 */

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.SpecialityHolder> {

  private final LayoutInflater inflater;
  private Context context;
  private List<Speciality> specialities;

  public SpecialityAdapter(Context context, List<Speciality> specialityList) {
    this.context = context;
    this.specialities = specialityList;
    this.inflater = LayoutInflater.from(context);
  }

  @Override
  public SpecialityAdapter.SpecialityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View convertview = inflater.inflate(R.layout.speciality_row, parent, false);
    return new SpecialityHolder(convertview);
  }

  @Override public void onBindViewHolder(SpecialityAdapter.SpecialityHolder holder, int position) {
    Glide.with(context)
        .load(URL.KR_INVEN_PLAYER_IMAGE_SPECIALITY
            + specialities.get(position).getPosition()
            + URL.CONSTANT_PNG)
        .into(holder.imageViewSpeciality);
    holder.textName.setText(specialities.get(position).getName());
    holder.textValue.setText(specialities.get(position).getValue());
  }

  @Override public int getItemCount() {
    return specialities.size();
  }

  class SpecialityHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_speciality) ImageView imageViewSpeciality;
    @BindView(R.id.text_name) TextView textName;
    @BindView(R.id.text_value) TextView textValue;

    SpecialityHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
