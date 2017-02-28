package com.huynn109.fifaonline3addict.service;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by huyuit on 2/11/2017.
 */

public class MyApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    Realm.init(this);
    RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
        .deleteRealmIfMigrationNeeded()
        .build();
    Realm.setDefaultConfiguration(realmConfiguration);
  }
}
