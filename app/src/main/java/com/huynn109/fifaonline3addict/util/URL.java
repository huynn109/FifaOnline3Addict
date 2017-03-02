package com.huynn109.fifaonline3addict.util;

/**
 * Created by huyuit on 1/29/2017.
 */

public class URL {

  public static final String BASE_URL = "https://vn.fifaaddict.com/";
  public static final String SEARCH_URL = "https://vn.fifaaddict.com/fo3db.php?q=player";
  public static final String SEARCH_URL_NAME = "https://vn.fifaaddict.com/fo3db.php?q=player&name=";
  public static final String FIFA_ADDICT_PLAYER_URL = "https://fifaaddict.com/fo3player.php?id=";
  public static final String KR_INVEN_PLAYER_URL =
      "http://fifaonline3.inven.co.kr/dataninfo/player/detail.php?code=";
  public static final String KR_INVEN_PLAYER_IMAGE_SPECIALITY =
      "https://fifaaddict.com/fo3img/specialities/s";
  public static final String CONSTANT_PNG = ".png";
  public static String imageUrl = "https://fifaaddict.com/fo3img/players/p";

  public static String getImageUrlFromId(String id) {
    return imageUrl + id + ".png";
  }
}
