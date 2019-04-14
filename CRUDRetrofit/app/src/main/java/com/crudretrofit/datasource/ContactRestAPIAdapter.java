package com.crudretrofit.datasource;

import com.crudretrofit.restapi.BaseRestApiAdapter;

/**
 * Created by ArLoJi on 09/02/2018.
 */

public class ContactRestAPIAdapter extends BaseRestApiAdapter{

    public static ContactRestAPI getContactRestApi(){
        restAdapter = getRestAdapter("http://dev.nostratech.com:10093/");
        return restAdapter.create(ContactRestAPI.class);
    }
}
