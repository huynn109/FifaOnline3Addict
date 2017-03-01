package com.huynn109.fifaonline3addict.data.model.normal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import org.apache.commons.lang3.text.WordUtils;

public class Club implements Serializable{

  @SerializedName("name") @Expose private String name;
  @SerializedName("id") @Expose private Integer id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override public String toString() {
    return WordUtils.capitalizeFully(name.replace("-", " "));
  }
}
