package com.crudretrofit.utils;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ArLoJi on 19/02/2018.
 */

public class RealmDB {
    private Realm realm;

    public RealmDB(){
        if(realm == null){
            realm = Realm.getDefaultInstance();
        }
    }

    public RealmDB(Context context){
        Realm.init(context);
    }

    public void configRealm(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("contact-person")
                .schemaVersion(1)
                .build();

        realm = Realm.getInstance(realmConfiguration);
    }

    public Realm getRealm(){
        return realm;
    }

    public void closeRealm(){
        if(realm!=null){
            realm.close();
            realm = null;
        }
    }
}
