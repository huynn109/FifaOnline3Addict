package com.huynn109.fifaonline3addict.data.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.io.Serializable;

/**
 * Created by huyuit on 1/27/2017.
 */

public class PlayerR extends RealmObject implements Serializable {
  @PrimaryKey public String id;
  public String imageId;
  public String name;
  public String position;
  public int skillmoves;
  public String price;
  public RealmList<PositionR> positions;
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
  public PlayerStatR playerStat;
  public int attack;
  public int defence;
  public int leftFoot;
  public int rightFoot;
  public RealmList<SpecialityInteger> specialityIntegers;
  public RealmList<TraitInteger> traitIntegers;
}
