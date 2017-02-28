package com.huynn109.fifaonline3addict.data.model.realm;

import io.realm.RealmObject;
import java.io.Serializable;

/**
 * Created by huyuit on 1/27/2017.
 */

public class PositionR extends RealmObject implements Serializable{
  private String name;
  private String value;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override public String toString() {
    return name;
  }
}
