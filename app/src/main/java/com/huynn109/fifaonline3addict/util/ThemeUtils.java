package com.huynn109.fifaonline3addict.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.huynn109.fifaonline3addict.R;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huyuit on 3/3/2017.
 */

public class ThemeUtils {
  private static final String KEY_THEME_ID = "KEY_THEME_ID";

  public static int DEFAULT_THEME_RES_ID = R.style.AppTheme;

  private static SharedPreferences sSharedPreferences;

  @SuppressLint("UseSparseArrays") private static Map<Integer, Integer> sThemes = new HashMap<>();

  static {
    sThemes.put(R.id.action_theme_red, R.style.AppTheme_Red);
    sThemes.put(R.id.action_theme_pink, R.style.AppTheme_Pink);
    sThemes.put(R.id.action_theme_purple, R.style.AppTheme_Purple);
    sThemes.put(R.id.action_theme_deep_purple, R.style.AppTheme_DeepPurple);
    sThemes.put(R.id.action_theme_indigo, R.style.AppTheme_Indigo);
    sThemes.put(R.id.action_theme_blue, R.style.AppTheme_Blue);
    sThemes.put(R.id.action_theme_light_blue, R.style.AppTheme_LightBlue);
    sThemes.put(R.id.action_theme_cyan, R.style.AppTheme_Cyan);
    sThemes.put(R.id.action_theme_teal, R.style.AppTheme_Teal);
    sThemes.put(R.id.action_theme_green, R.style.AppTheme_Green);
    sThemes.put(R.id.action_theme_light_green, R.style.AppTheme_LightGreen);
    sThemes.put(R.id.action_theme_lime, R.style.AppTheme_Lime);
    sThemes.put(R.id.action_theme_yellow, R.style.AppTheme_Yellow);
    sThemes.put(R.id.action_theme_amber, R.style.AppTheme_Amber);
    sThemes.put(R.id.action_theme_orange, R.style.AppTheme_Orange);
    sThemes.put(R.id.action_theme_deep_orange, R.style.AppTheme_DeepOrange);
    sThemes.put(R.id.action_theme_brown, R.style.AppTheme_Brown);
    sThemes.put(R.id.action_theme_grey, R.style.AppTheme_Grey);
    sThemes.put(R.id.action_theme_blue_grey, R.style.AppTheme_BlueGrey);
    sThemes.put(R.id.action_theme_white, R.style.AppTheme_White);
    sThemes.put(R.id.action_theme_black, R.style.AppTheme_Black);
  }

  public static void changeToTheme(Activity activity, int themeId) {
    int themeResId = sThemes.containsKey(themeId) ? sThemes.get(themeId) : 0;
    if (themeResId > 0 && themeResId != getThemeId(activity)) {
      setThemeId(activity, themeResId);
      Intent intent = new Intent(activity, activity.getClass());
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
      activity.startActivity(intent);
      activity.overridePendingTransition(0, 0);
      activity.finish();
    }
  }

  public static void onCreate(Activity activity) {
    activity.setTheme(getThemeId(activity));
  }

  private static SharedPreferences getSharedPreferences(Context context) {
    if (sSharedPreferences == null) {
      sSharedPreferences = context.getSharedPreferences("any-key", Context.MODE_PRIVATE);
    }
    return sSharedPreferences;
  }

  private static int getThemeId(Context context) {
    return getSharedPreferences(context).getInt(KEY_THEME_ID, DEFAULT_THEME_RES_ID);
  }

  private static void setThemeId(Context context, int themeId) {
    getSharedPreferences(context)
        .edit()
        .putInt(KEY_THEME_ID, themeId)
        .apply();
  }
}
