package com.crudretrofit.object;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by ArLoJi on 12/02/2018.
 */

public class Result
{
    @Expose
    private String message;
    @Expose
    private Object result;

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }
}
