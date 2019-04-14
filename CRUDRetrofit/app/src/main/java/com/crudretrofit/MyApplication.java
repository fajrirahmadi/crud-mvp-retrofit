package com.crudretrofit;

import android.app.Application;

import com.crudretrofit.utils.RealmDB;

/**
 * Created by ArLoJi on 19/02/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmDB realmDB = new RealmDB(this);
        realmDB.configRealm();
    }
}
