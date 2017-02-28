package com.huynn109.fifaonline3addict.util;

/**
 * Created by huyuit on 1/31/2017.
 */

public class Season {
  public static String seasonWb = "y2091";
  public static String seasonWl = "y2093";
  public static String season02 = "y2095";
  public static String season06 = "y2006";
  public static String season07 = "y2007";
  public static String season08 = "y2008";
  public static String season09 = "y2009";
  public static String season10 = "y2010";
  public static String season11 = "y2011";
  public static String season16 = "y2088";
  public static String season15 = "y2015";
  public static String season14 = "y2014";
  public static String season06Wc = "y2065";
  public static String season10Wc = "y2047";
  public static String season14Wc = "y2077";
  public static String seasonLp = "y2023";
  public static String seasonEc = "y2035";
  public static String season08Eu = "y2067";
  public static String season14T = "y2053";
  public static String season06U = "y2063";
  public static String season10U = "y2069";
  public static String season15TauKhua = "y2085";
  public static String season16W = "y2081";
  public static String seasonMuLegend = "y2055";
  public static String seasonTlLegend = "y2051";
  public static String season16Th = "y2045";
  public static String seasonVnLegend = "y2061";
  public static String seasonMalayLegend = "y2071";
  public static String seasonU23 = "y2029";
  public static String seasonKxi = "y2097";
  public static String seasonTc92 = "y2049";

  public static String getSeasonFromClass(String season) {
    if (season.equals(seasonWb)) {
      return "xi";
    } else if (season.equals(seasonWl)) {
      return "wl";
    } else if (season.equals(season02)) {
      return "kl2002";
    } else if (season.equals(season06)) {
      return "2006";
    } else if (season.equals(season07)) {
      return "2007";
    } else if (season.equals(season08)) {
      return "2008";
    } else if (season.equals(season09)) {
      return "2009";
    } else if (season.equals(season10)) {
      return "2010";
    } else if (season.equals(season11)) {
      return "2011";
    } else if (season.equals(season16)) {
      return "2016";
    } else if (season.equals(season15)) {
      return "2015";
    } else if (season.equals(season14)) {
      return "2014";
    } else if (season.equals(season06Wc)) {
      return "wc2006";
    } else if (season.equals(season10Wc)) {
      return "wc2010";
    } else if (season.equals(season14Wc)) {
      return "wc2014";
    } else if (season.equals(seasonLp)) {
      return "lp";
    } else if (season.equals(seasonEc)) {
      return "16ec";
    } else if (season.equals(season08Eu)) {
      return "ue2008";
    } else if (season.equals(season14T)) {
      return "tots2014";
    } else if (season.equals(season06U)) {
      return "ucl2006";
    } else if (season.equals(season10U)) {
      return "ucl2010";
    } else if (season.equals(season15TauKhua)) {
      return "15csl";
    } else if (season.equals(season16W)) {
      return "16c";
    } else if (season.equals(seasonMuLegend)) {
      return "mufclegend";
    } else if (season.equals(seasonTlLegend)) {
      return "tl";
    } else if (season.equals(season16Th)) {
      return "tb11";
    } else if (season.equals(seasonVnLegend)) {
      return "vl";
    } else if (season.equals(seasonMalayLegend)) {
      return "ml";
    } else if (season.equals(seasonU23)) {
      return "u23";
    } else if (season.equals(seasonKxi)) {
      return "kxi";
    } else if (season.equals(seasonTc92)) {
      return "tco92";
    }
    return null;
  }

  /**
   *
   * @param s seasonWb...
   * @return number
   */
  public static String getFirtLetterSeasonKr(String s) {
    return s.split("y")[0];
  }
}
