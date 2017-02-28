package com.huynn109.fifaonline3addict.data.helper;

import com.huynn109.fifaonline3addict.data.model.normal.Player;
import com.huynn109.fifaonline3addict.data.model.normal.Position;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerR;
import com.huynn109.fifaonline3addict.data.model.realm.PlayerStatR;
import com.huynn109.fifaonline3addict.data.model.realm.PositionR;
import com.huynn109.fifaonline3addict.data.model.realm.SpecialityInteger;
import com.huynn109.fifaonline3addict.data.model.realm.TraitInteger;
import io.realm.Realm;
import java.util.List;

/**
 * Created by huyuit on 2/24/2017.
 */

public class DatabaseHelper {

  private static final String TAG = DatabaseHelper.class.getSimpleName();
  private Realm mRealm;

  public DatabaseHelper(Realm realm) {
    this.mRealm = realm;
  }

  public void saveListPlayerToRealm(List<Player> players) {
    if (players.size() > 0) {
      for (Player player : players) {
        mRealm.executeTransaction(realm -> {
          PlayerR playerR = realm.where(PlayerR.class).equalTo("id", player.id).findFirst();
          if (playerR == null) {
            playerR = realm.createObject(PlayerR.class, player.id);
          }
          playerR.imageId = player.imageId;
          playerR.name = player.name;
          playerR.position = player.position;
          playerR.skillmoves = player.skillmoves;
          playerR.price = player.price;
          if (playerR.positions.size() > 0) playerR.positions.clear();
          for (Position position : player.positions) {
            PositionR positionR = new PositionR();
            positionR.setName(position.getName());
            positionR.setValue(position.getValue());
            realm.copyToRealm(positionR);
            playerR.positions.add(positionR);
          }
          playerR.rosterUpdate = player.rosterUpdate;
          playerR.liveBoost = player.liveBoost;
          playerR.season = player.season;
          playerR.country = player.country;
          playerR.countryFlag = player.countryFlag;
          playerR.club = player.club;
          playerR.clubLogo = player.clubLogo;
          playerR.birthDay = player.birthDay;
          playerR.height = player.height;
          playerR.weight = player.weight;
          playerR.imageRealUrl = player.imageRealUrl;
          if (playerR.playerStat != null) playerR.playerStat.deleteFromRealm();
          if (player.playerStat != null) {
            PlayerStatR playerStatR = new PlayerStatR();
            playerStatR.setFromEntity(realm, player.playerStat);
            playerR.playerStat = playerStatR;
          }
          playerR.attack = player.attack;
          playerR.defence = player.defence;
          playerR.leftFoot = player.leftFoot;
          playerR.rightFoot = player.rightFoot;
          realm.copyToRealmOrUpdate(playerR);
        });
      }
    }
  }

  public void updatePlayerInfoToRealm(Player player) {
    if (player != null) {
      mRealm.executeTransaction(realm -> {
        PlayerR playerR = mRealm.where(PlayerR.class).equalTo("id", player.id).findFirst();
        if (playerR != null) {
          playerR.imageRealUrl = player.imageRealUrl;
          playerR.country = player.country;
          playerR.countryFlag = player.countryFlag;
          playerR.club = player.club;
          playerR.clubLogo = player.clubLogo;
          playerR.birthDay = player.birthDay;
          playerR.height = player.height;
          playerR.weight = player.weight;
          playerR.attack = player.attack;
          playerR.defence = player.defence;
          playerR.rightFoot = player.rightFoot;
          playerR.leftFoot = player.leftFoot;
          realm.copyToRealmOrUpdate(playerR);
        }
      });
    }
  }

  public void updatePlayerStatRealm(PlayerStatR playerStatR, String idPlayer) {
    if (playerStatR != null) {
      mRealm.executeTransaction(realm -> {
        PlayerR playerR = mRealm.where(PlayerR.class).equalTo("id", idPlayer).findFirst();
        if (playerR != null) {
          if (playerR.playerStat != null) playerR.playerStat.deleteFromRealm();
          playerR.playerStat = realm.copyToRealm(playerStatR);
          realm.copyToRealmOrUpdate(playerR);
        }
      });
    }
  }

  public void updatePlayerSpecialityRealm(List<Integer> listSpeciality, String idPlayer) {
    if (listSpeciality.size() > 0) {
      mRealm.executeTransaction(realm -> {
        PlayerR playerR = mRealm.where(PlayerR.class).equalTo("id", idPlayer).findFirst();
        if (playerR != null) {
          if (playerR.specialityIntegers.size() > 0) {
            playerR.specialityIntegers.deleteAllFromRealm();
          }
          for (Integer integerSpeciality : listSpeciality) {
            SpecialityInteger specialityInteger = new SpecialityInteger();
            specialityInteger.speciality = integerSpeciality;
            realm.copyToRealm(specialityInteger);
            playerR.specialityIntegers.add(specialityInteger);
          }
          realm.copyToRealmOrUpdate(playerR);
        }
      });

    }
  }

  public void updatePlayerTraitRealm(List<Integer> listTrait, String idPlayer) {
    if (listTrait.size() > 0) {
      mRealm.executeTransaction(realm -> {
        PlayerR playerR = mRealm.where(PlayerR.class).equalTo("id", idPlayer).findFirst();
        if (playerR != null) {
          if (playerR.traitIntegers.size() > 0) {
            playerR.traitIntegers.deleteAllFromRealm();
          }
          for (Integer integerTrait : listTrait) {
            TraitInteger traitInteger = new TraitInteger();
            traitInteger.trait = integerTrait;
            realm.copyToRealm(traitInteger);
            playerR.traitIntegers.add(traitInteger);
          }
          realm.copyToRealmOrUpdate(playerR);
        }
      });
    }
  }
}
