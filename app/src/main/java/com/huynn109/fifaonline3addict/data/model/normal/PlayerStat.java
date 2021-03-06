package com.huynn109.fifaonline3addict.data.model.normal;

import java.io.Serializable;

/**
 * Created by huyuit on 2/24/2017.
 */

public class PlayerStat implements Serializable {

  public PlayerStat() {
  }

  public PlayerStat(PlayerStat playerStat) {
    this.strength = playerStat.strength;
    this.stamina = playerStat.stamina;
    this.acceleration = playerStat.acceleration;
    this.sprintspeed = playerStat.sprintspeed;
    this.jumping = playerStat.jumping;
    this.agility = playerStat.agility;
    this.balance = playerStat.balance;
    this.slidingtackle = playerStat.slidingtackle;
    this.dribbling = playerStat.dribbling;
    this.ballcontrol = playerStat.ballcontrol;
    this.marking = playerStat.marking;
    this.standingtackle = playerStat.standingtackle;
    this.crossing = playerStat.crossing;
    this.shortpassing = playerStat.shortpassing;
    this.finishing = playerStat.finishing;
    this.longpassing = playerStat.longpassing;
    this.shotpower = playerStat.shotpower;
    this.headingaccuracy = playerStat.headingaccuracy;
    this.longshots = playerStat.longshots;
    this.volleys = playerStat.volleys;
    this.curve = playerStat.curve;
    this.freekickaccuracy = playerStat.freekickaccuracy;
    this.penalties = playerStat.penalties;
    this.tacticalawareness = playerStat.tacticalawareness;
    this.positioning = playerStat.positioning;
    this.vision = playerStat.vision;
    this.reactions = playerStat.reactions;
    this.aggression = playerStat.aggression;
    this.gkkicking = playerStat.gkkicking;
    this.gkdiving = playerStat.gkdiving;
    this.gkhandling = playerStat.gkhandling;
    this.gkpositioning = playerStat.gkpositioning;
    this.gkreflexes = playerStat.gkreflexes;
    this.poten = playerStat.poten;
    this.perfcon = playerStat.perfcon;
  }

  public int strength; // Sức mạnh
  public int stamina; // Thể lực
  public int acceleration; // Tăng tốc
  public int sprintspeed; // Tốc độ
  public int jumping; // Nhảy
  public int agility; // Khéo léo
  public int balance; // Thăng bằng
  public int slidingtackle; // Xoạc bóng
  public int dribbling; // Rê bóng
  public int ballcontrol; // Giữ bóng
  public int marking; // Kèm người
  public int standingtackle; // Tranh bóng
  public int crossing; // Tạt bóng
  public int shortpassing; // Chuyền ngắn
  public int finishing; // Dứt điểm
  public int longpassing; // Chuyền dài
  public int shotpower; // Lực sút
  public int headingaccuracy; // Đánh đầu
  public int longshots; // Sút xa
  public int volleys; // Vô lê
  public int curve; // Sút xoáy
  public int freekickaccuracy; // Đá phạt
  public int penalties; // Penalty
  public int tacticalawareness; // Cắt bóng
  public int positioning; // Chọn vị trí
  public int vision; // Tầm nhìn
  public int reactions; // Phản ứng
  public int aggression; // Quyết đoán
  public int gkkicking; // TM phát bóng
  public int gkdiving; // TM đổ người
  public int gkhandling; // TM bắt bóng
  public int gkpositioning; // TM chọn vị trí
  public int gkreflexes; // Thủ môn phản xạ
  public int poten; // Chỉ số
  public String perfcon; // Perf. Consistency
}
