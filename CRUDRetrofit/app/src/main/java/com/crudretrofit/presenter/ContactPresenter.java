package com.crudretrofit.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.crudretrofit.contract.ContactContract;
import com.crudretrofit.datasource.ContactRestAPI;
import com.crudretrofit.datasource.ContactRestAPIAdapter;
import com.crudretrofit.model.ContactModel;
import com.crudretrofit.model.ContactRepository;
import com.crudretrofit.model.LocalContactModel;
import com.crudretrofit.object.ContactPerson;
import com.crudretrofit.object.Result;
import com.crudretrofit.object.ResultList;
import com.crudretrofit.utils.NetworkUtils;
import com.crudretrofit.utils.RealmDB;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ArLoJi on 14/02/2018.
 */

public class ContactPresenter implements ContactContract.Presenter {

    private ContactContract.ContactView contactView;
    private ContactContract.DetailContactView detailContactView;
    private Context context;
    private Gson gson;
    private RealmDB realmDB;

    //Konstraktor Presenter untuk view detail contact
    public ContactPresenter(Context context, ContactContract.DetailContactView detailContactView, RealmDB realmDB){
        this.context = context;
        this.gson = new Gson();
        this.detailContactView = detailContactView;
        this.realmDB = realmDB;
    }

    //Konstraktor Presenter untuk view contact semuanya
    public ContactPresenter(Context context, ContactContract.ContactView contactView, RealmDB realmDB){
        this.context = context;
        this.gson = new Gson();
        this.contactView = contactView;
        this.realmDB = realmDB;
    }

    //Method untuk mengambil semua kontak dari server
    public void getAllContact(ContactModel contactModel, final LocalContactModel localContactModel) {
        if(NetworkUtils.isConnected(context)){
            contactModel.getAllContact().enqueue(new Callback<ResultList>() {
                @Override
                public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                    if(response.isSuccessful()){
                        if(response.body().getMessage().equals("OK")){
                            JsonArray jsonArray = gson.toJsonTree(response.body().getResult()).getAsJsonArray();
                            Type contactType = new TypeToken<ArrayList<ContactPerson>>(){
                            }.getType();
                            ArrayList<ContactPerson> contactPersonList = gson.fromJson(jsonArray,contactType);
                            contactView.doShowContact(contactPersonList);
                            localContactModel.setContact(realmDB.getRealm(),contactPersonList);
                        }else{
                            contactView.doOnError(response.body().getResult().toString());
                        }
                    }else{
                        try{
                            contactView.doOnError(response.errorBody().string());
                        }catch (IOException error){
                            error.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResultList> call, Throwable t) {
                    //TODO set action failure
                }
            });
        }else{
            contactView.noConnection();
        }
        getListLocal(localContactModel);
    }

    private void getListLocal(final LocalContactModel localContactModel){
        realmDB.getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ContactPerson> listContact = localContactModel.getAllContact(realm);
                if(listContact.isLoaded()){
                    contactView.doShowContact(listContact);
                }
            }
        });
    }

    //Method untuk menambahkan kontak ke server
    @Override
    public void addContact(final ContactPerson contactPerson, ContactModel contactModel, final LocalContactModel localContactModel) {
        if(NetworkUtils.isConnected(context)){
            contactModel.addContact(contactPerson).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if(response.isSuccessful()){
                        if(response.body().getMessage().equals("OK")){
                            contactPerson.setId(response.body().getResult().toString());
                        }
                        localContactModel.setContact(realmDB.getRealm(),contactPerson);
                    }else{
                        try{
                            detailContactView.doOnError(response.errorBody().string());
                        }catch (IOException error){
                            error.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    detailContactView.doOnError(t.getMessage());
                }
            });
        }
    }

    //Method untuk mengupdate data kontak di server
    @Override
    public void editContact(String id, final ContactPerson contactPerson, ContactModel contactModel, final LocalContactModel localContactModel) {
        if(NetworkUtils.isConnected(context)){
            contactModel.editContact(id, contactPerson).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if(response.isSuccessful()){
                        if (response.body().getMessage().equals("OK")) {
                            localContactModel.setContact(realmDB.getRealm(),contactPerson);
                        }else{
                            detailContactView.doOnError(response.body().getResult().toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    detailContactView.doOnError(t.getMessage());
                }
            });
        }else{
            detailContactView.doOnError("No internet connection");
        }
    }

    //Method untuk menghapus kontak dari server
    @Override
    public void deleteContact(final String id, ContactModel contactModel, final LocalContactModel localContactModel) {
        if(NetworkUtils.isConnected(context)){
            deleteLocal(id,localContactModel);
            contactModel.deleteContact(id).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if(response.isSuccessful()){
                        if (!response.body().getMessage().equals("OK")) {
                            detailContactView.doOnError(response.body().getResult().toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    detailContactView.doOnError(t.getMessage());
                }
            });
        }else{
            detailContactView.doOnError("No internet connection");
        }

    }

    private void deleteLocal(final String id, final LocalContactModel localContactModel){
        realmDB.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                localContactModel.deleteContact(realm,id);
            }
        });
    }
}
