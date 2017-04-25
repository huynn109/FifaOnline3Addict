package com.huynn109.fifaonline3addict.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import com.huynn109.fifaonline3addict.R;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaxFragment extends Fragment implements TextWatcher {

  @BindView(R.id.edit_text_price) TextInputEditText editTextPrice;
  @BindView(R.id.text_tax) TextView textTax;
  @BindView(R.id.checkbox_pc) CheckBox checkboxPc;
  @BindView(R.id.text_pc) TextView textPc;
  @BindView(R.id.checkbox_vip) CheckBox checkboxVip;
  @BindView(R.id.text_vip) TextView textVip;
  @BindView(R.id.text_balance) TextView textBalance;

  public TaxFragment() {
  }

  public static TaxFragment newInstance() {
    Bundle args = new Bundle();
    TaxFragment fragment = new TaxFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_tax, container, false);
    ButterKnife.bind(this, rootView);
    editTextPrice.addTextChangedListener(this);
    return rootView;
  }

  @Override public void onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
    if (menu.findItem(R.id.action_search) != null) {
      menu.findItem(R.id.action_search).setVisible(false);
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @SuppressLint("SetTextI18n") @Override public void afterTextChanged(Editable s) {
    editTextPrice.removeTextChangedListener(this);
    try {
      String originalString = s.toString();

      if (originalString.contains(",")) {
        originalString = originalString.replaceAll(",", "");
      }
      long longVal = Long.parseLong(originalString);

      long longTax = 0;
      if (longVal < 1000) {
        textTax.setText("");
        textPc.setText("");
        textVip.setText("");
        textBalance.setText("");
      } else {
        if (longVal <= 500000) {
          longTax = longVal * 30 / 100;
        } else {
          longTax = longVal * 40 / 100 - 50000;
        }
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");
        String formattedString = formatter.format(longVal);
        String formattedTax = formatter.format(longTax);

        editTextPrice.setText(formattedString);
        editTextPrice.setSelection(editTextPrice.getText().length());

        long longPc = 0;
        if (checkboxPc.isChecked()) {
          longPc = longTax * 20 / 100;
          String formattedPc = formatter.format(longPc);
          textPc.setText("+" + formattedPc);
        }
        long longVip = 0;
        if (checkboxVip.isChecked()) {
          longVip = longTax * 30 / 100;
          String formattedVip = formatter.format(longVip);
          textVip.setText("+" + formattedVip);
        }
        textTax.setText("-" + formattedTax);

        long longBalance = longVal - longTax + longPc + longVip;
        String formattedBalance = formatter.format(longBalance);
        textBalance.setText(formattedBalance);
      }
    } catch (NumberFormatException nfe) {
      textTax.setText("");
      textPc.setText("");
      textVip.setText("");
      textBalance.setText("");
      nfe.printStackTrace();
    }
    editTextPrice.addTextChangedListener(this);
  }

  @SuppressLint("SetTextI18n") @OnCheckedChanged(R.id.checkbox_pc)
  public void checkboxPcToggled(boolean isChecked) {
    long longVal = 0;
    long longTax = 0;
    long longPc = 0;
    long longVip = 0;
    DecimalFormat formatter = null;
    String originalString = editTextPrice.getText().toString();

    if (originalString.contains(",")) {
      originalString = originalString.replaceAll(",", "");
    }
    longVal = Long.parseLong(originalString);
    if (longVal < 1000) {
      textTax.setText("");
      textPc.setText("");
      textVip.setText("");
      textBalance.setText("");
    } else {
      if (longVal <= 500000) {
        longTax = longVal * 30 / 100;
      } else {
        longTax = longVal * 40 / 100 - 50000;
      }
      if (checkboxVip.isChecked()) {
        longVip = longTax * 30 / 100;
      }
      formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
      formatter.applyPattern("#,###,###,###");
      if (isChecked) {
        try {

          if (checkboxPc.isChecked()) {
            longPc = longTax * 20 / 100;
            String formattedPc = formatter.format(longPc);
            textPc.setText("+" + formattedPc);
          }
        } catch (NumberFormatException nfe) {
          nfe.printStackTrace();
        }
      } else {
        textPc.setText("");
      }
      long longBalance = longVal - longTax + longPc + longVip;
      String formattedBalance = null;
      formattedBalance = formatter.format(longBalance);
      textBalance.setText(formattedBalance);
    }
  }

  @SuppressLint("SetTextI18n") @OnCheckedChanged(R.id.checkbox_vip)
  public void checkboxVipToggled(boolean isChecked) {
    long longVal = 0;
    long longTax = 0;
    long longPc = 0;
    long longVip = 0;
    DecimalFormat formatter;
    String originalString = editTextPrice.getText().toString();

    if (originalString.contains(",")) {
      originalString = originalString.replaceAll(",", "");
    }
    longVal = Long.parseLong(originalString);
    if (longVal < 1000) {
      textTax.setText("");
      textPc.setText("");
      textVip.setText("");
      textBalance.setText("");
    } else {
      if (longVal <= 500000) {
        longTax = longVal * 30 / 100;
      } else {
        longTax = longVal * 40 / 100 - 50000;
      }
      formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
      formatter.applyPattern("#,###,###,###");
      if (checkboxPc.isChecked()) {
        longPc = longTax * 20 / 100;
      }
      if (isChecked) {
        try {
          if (checkboxVip.isChecked()) {
            longVip = longTax * 30 / 100;
            String formattedVip = formatter.format(longVip);
            textVip.setText("+" + formattedVip);
          }
        } catch (NumberFormatException nfe) {
          nfe.printStackTrace();
        }
      } else {
        textVip.setText("");
      }
      long longBalance = longVal - longTax + longPc + longVip;
      String formattedBalance;
      formattedBalance = formatter.format(longBalance);
      textBalance.setText(formattedBalance);
    }
  }
}
