package com.crudretrofit.model;

import com.crudretrofit.object.ContactPerson;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ArLoJi on 19/02/2018.
 */

public interface LocalContactModel {
    RealmResults<ContactPerson> getAllContact(Realm realm);
    void setContact(Realm realm,ContactPerson contactPerson);
    void setContact(Realm realm,List<ContactPerson> contactPersonList);
    void deleteContact(Realm realm, String id);
}
