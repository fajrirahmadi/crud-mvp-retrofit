package com.crudretrofit.model;

import com.crudretrofit.datasource.ContactRestAPI;
import com.crudretrofit.datasource.ContactRestAPIAdapter;
import com.crudretrofit.object.ContactPerson;
import com.crudretrofit.object.Result;
import com.crudretrofit.object.ResultList;

import retrofit2.Call;

/**
 * Created by ArLoJi on 14/02/2018.
 */

public class ContactRepository implements ContactModel {

    private ContactRestAPI contactRestAPI;

    public ContactRepository(ContactRestAPI contactRestAPI) {
        this.contactRestAPI = contactRestAPI;
    }

    @Override
    public Call<ResultList> getAllContact() {
        return contactRestAPI.getAllContact();
    }

    @Override
    public Call<Result> addContact(ContactPerson contactPerson) {
        return contactRestAPI.addUser(contactPerson);
    }

    @Override
    public Call<Result> editContact(String id, ContactPerson contactPerson) {
        return contactRestAPI.updateContact(id,contactPerson);
    }

    @Override
    public Call<Result> deleteContact(String id) {
        return contactRestAPI.deleteContact(id);
    }
}
