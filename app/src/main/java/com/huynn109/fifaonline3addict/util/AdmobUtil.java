package com.huynn109.fifaonline3addict.util;

import android.content.Context;
import android.provider.Settings;
import com.google.android.gms.ads.AdRequest;
import com.huynn109.fifaonline3addict.BuildConfig;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by huyuit on 2/28/2017.
 */

public class AdmobUtil {
  public static AdRequest getAdMobRequest(Context context) {
    AdRequest.Builder adRequestBuilder =
        new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

    if (!BuildConfig.BUILD_TYPE.equals("release")) {
      // Register as a test device
      AdRequest adReq = adRequestBuilder.addTestDevice(adMobID(context)).build();
      if (!adReq.isTestDevice(context)) return null;
      return adReq;
    }

    return adRequestBuilder.build();
  }

  public static String adMobID(Context context) {
    String android_id =
        Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    return md5(android_id).toUpperCase();
  }

  private static String md5(final String s) {
    try {
      // Create MD5 Hash
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(s.getBytes());
      byte messageDigest[] = digest.digest();
      // Create Hex String
      StringBuilder hexString = new StringBuilder();
      for (byte aMessageDigest : messageDigest) {
        String h = Integer.toHexString(0xFF & aMessageDigest);
        while (h.length() < 2) h = "0" + h;
        hexString.append(h);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new Error(e);
    }
  }
}
