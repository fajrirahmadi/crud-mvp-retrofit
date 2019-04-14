package com.crudretrofit.model;

import com.crudretrofit.object.ContactPerson;
import com.crudretrofit.object.Result;
import com.crudretrofit.object.ResultList;

import retrofit2.Call;

/**
 * Created by ArLoJi on 14/02/2018.
 */

public interface ContactModel {
    Call<ResultList> getAllContact();
    Call<Result> addContact(ContactPerson contactPerson);
    Call<Result> editContact(String id, ContactPerson contactPerson);
    Call<Result> deleteContact(String id);
}
