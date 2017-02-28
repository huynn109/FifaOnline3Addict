package com.huynn109.fifaonline3addict.data.model.normal;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huyuit on 2/24/2017.
 */

public class Player implements Serializable {
  public String id;
  public String imageId;
  public String name;
  public String position;
  public int skillmoves;
  public String price;
  public List<Position> positions;
  public String rosterUpdate;
  public String liveBoost;
  public String season;
  public String country;
  public String countryFlag;
  public String club;
  public String clubLogo;
  public String birthDay;
  public String height;
  public String weight;
  public String imageRealUrl;
  public PlayerStat playerStat;
  public int attack;
  public int defence;
  public int leftFoot;
  public int rightFoot;
}
