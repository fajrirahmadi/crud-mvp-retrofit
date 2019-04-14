package com.crudretrofit.model;

import com.crudretrofit.object.ContactPerson;
import com.crudretrofit.utils.RealmDB;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ArLoJi on 19/02/2018.
 */

public class LocalContactRepository implements LocalContactModel {
    @Override
    public RealmResults<ContactPerson> getAllContact(Realm realm) {
        return realm.where(ContactPerson.class).findAll();
    }

    @Override
    public void setContact(Realm realm, ContactPerson contactPerson) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(contactPerson);
        realm.commitTransaction();
    }

    @Override
    public void setContact(Realm realm, List<ContactPerson> contactPersonList){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(contactPersonList);
        realm.commitTransaction();
    }

    @Override
    public void deleteContact(Realm realm, String id) {
        realm.where(ContactPerson.class)
                .equalTo("id",id)
                .findFirst()
                .deleteFromRealm();
    }
}
