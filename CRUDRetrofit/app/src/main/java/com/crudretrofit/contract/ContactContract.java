package com.crudretrofit.contract;

import com.crudretrofit.model.ContactModel;
import com.crudretrofit.model.LocalContactModel;
import com.crudretrofit.object.ContactPerson;

import java.util.List;

/**
 * Created by ArLoJi on 14/02/2018.
 */

public interface ContactContract {

    interface Presenter{
        void getAllContact(ContactModel contactModel, LocalContactModel localContactModel);
        void addContact(ContactPerson contactPerson,ContactModel contactModel, LocalContactModel localContactModel);
        void editContact(String id, ContactPerson contactPerson,ContactModel contactModel, LocalContactModel localContactModel);
        void deleteContact(String id,ContactModel contactModel, LocalContactModel localContactModel);
    }

    interface ContactView{
        void doShowContact(List<ContactPerson> contactPersonList);
        void doOnError(String message);
        void noConnection();
    }

    interface DetailContactView{
        void doOnError(String message);
    }
}
