package com.crudretrofit.object; ;

import com.google.gson.annotations.Expose;

public class ResultList extends Result {

    @Expose
    private String pages;
    @Expose
    private String elements;

    public String getPages() {
        return pages;
    }

    public String getElements() {
        return elements;
    }

}
