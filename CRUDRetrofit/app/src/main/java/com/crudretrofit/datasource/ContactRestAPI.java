package com.crudretrofit.datasource;

import com.crudretrofit.object.Result;
import com.crudretrofit.object.ResultList;
import com.crudretrofit.object.ContactPerson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ArLoJi on 09/02/2018.
 */

public interface ContactRestAPI {

    @GET("api/v1/person?limit=5")
    Call<ResultList> getAllContact();

    @Headers("Content-Type: application/json")
    @POST("api/v1/person")
    Call<Result> addUser(@Body ContactPerson contactPerson);

    @Headers("Content-Type: application/json")
    @PUT("api/v1/person/{secure_id}")
    Call<Result> updateContact(@Path("secure_id") String contact_id, @Body ContactPerson contactPerson);

    @DELETE("api/v1/person/{secure_id}")
    Call<Result> deleteContact(@Path("secure_id") String contact_id);
}
