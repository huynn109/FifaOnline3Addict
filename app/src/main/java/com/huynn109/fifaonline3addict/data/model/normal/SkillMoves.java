package com.huynn109.fifaonline3addict.data.model.normal;

/**
 * Created by huyuit on 1/30/2017.
 */

public class SkillMoves {
  private String id;
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override public String toString() {
    return name;
  }
}
