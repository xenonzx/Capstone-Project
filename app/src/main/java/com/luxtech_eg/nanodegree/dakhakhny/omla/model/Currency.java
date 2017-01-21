package com.luxtech_eg.nanodegree.dakhakhny.omla.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed on 21/01/17.
 */

public class Currency {


    @SerializedName("sell")
    @Expose
    private Double sell;
    @SerializedName("buy")
    @Expose
    private Double buy;
    @SerializedName("title")
    @Expose
    private String title;

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
