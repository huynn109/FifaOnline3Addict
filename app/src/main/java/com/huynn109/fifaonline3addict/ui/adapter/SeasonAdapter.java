package com.huynn109.fifaonline3addict.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.normal.Season;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huyuit on 2/4/2017.
 */
public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonHolder> {
  public static final String TAG = SeasonAdapter.class.getSimpleName();
  private List<Season> seasonList;
  private Context context;
  private HashMap<Integer, Boolean> isChecked;
  private LayoutInflater inflater;

  public SeasonAdapter(Context context, List<Season> seasonList,
      HashMap<Integer, Boolean> isChecked) {
    this.context = context;
    this.seasonList = seasonList;
    this.isChecked = isChecked;
    inflater = LayoutInflater.from(context);
  }

  @Override public SeasonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View convertview = inflater.inflate(R.layout.single_row_with_check, parent, false);
    SeasonHolder holder = new SeasonHolder(convertview);
    return holder;
  }

  @Override public void onBindViewHolder(SeasonHolder holder, int position) {
    holder.checkBoxSeason.setText(seasonList.get(position).getName());
    if (isChecked.containsKey(position)) {
      holder.checkBoxSeason.setChecked(isChecked.get(position));
    } else {
      holder.checkBoxSeason.setChecked(false);
    }
  }

  @Override public int getItemCount() {
    return seasonList.size();
  }

  public HashMap<Integer, Boolean> getChecked() {
    return this.isChecked;
  }

  public class SeasonHolder extends RecyclerView.ViewHolder {
    public @BindView(R.id.checkbox_season) CheckBox checkBoxSeason;

    public SeasonHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      checkBoxSeason.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
          isChecked.put(getAdapterPosition(), b);
        }
      });
    }
  }
}
