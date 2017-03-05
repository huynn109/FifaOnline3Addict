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
import java.util.List;

/**
 * Created by huyuit on 3/4/2017.
 */

public class EnchantAdapter extends RecyclerView.Adapter<EnchantAdapter.ViewHolder> {
  private Context context;
  private List<String> strings;
  private LayoutInflater inflater;

  public EnchantAdapter(Context context, List<String> strings) {
    this.context = context;
    this.strings = strings;
    this.inflater = LayoutInflater.from(context);
  }

  @Override public EnchantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View convertview = inflater.inflate(R.layout.single_row, parent, false);
    return new ViewHolder(convertview);
  }

  @Override public void onBindViewHolder(EnchantAdapter.ViewHolder holder, int position) {
    holder.textView.setText(strings.get(position));
  }

  @Override public int getItemCount() {
    return strings.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_view) TextView textView;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
