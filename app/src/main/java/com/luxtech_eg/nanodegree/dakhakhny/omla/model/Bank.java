package com.luxtech_eg.nanodegree.dakhakhny.omla.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed on 21/01/17.
 */

public class Bank {


    @SerializedName("currency_rate")
    @Expose
    private CurrencyRate currencyRate;
    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("title")
    @Expose
    private String title;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public CurrencyRate getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(CurrencyRate currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
