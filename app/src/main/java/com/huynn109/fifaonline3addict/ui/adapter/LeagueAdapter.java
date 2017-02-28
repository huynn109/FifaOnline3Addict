package com.huynn109.fifaonline3addict.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huynn109.fifaonline3addict.R;
import com.huynn109.fifaonline3addict.data.model.normal.League;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by huyuit on 2/5/2017.
 */
public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueHolder> {
  private Context context;
  private List<League> leagueList;
  private LayoutInflater inflater;

  public LeagueAdapter(Context context, List<League> leagueList) {
    this.context = context;
    this.leagueList = leagueList;
    this.inflater = LayoutInflater.from(context);
  }

  @Override public LeagueAdapter.LeagueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View convertview = inflater.inflate(R.layout.single_row, parent, false);
    LeagueHolder holder = new LeagueHolder(convertview);
    return holder;
  }

  @Override public void onBindViewHolder(LeagueAdapter.LeagueHolder holder, int position) {
    holder.textView.setText(
        WordUtils.capitalizeFully(leagueList.get(position).getName().replace("-", " ")));
  }

  @Override public int getItemCount() {
    return leagueList.size();
  }

  public class LeagueHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_view) TextView textView;

    public LeagueHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
