package com.luxtech_eg.nanodegree.dakhakhny.omla.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed on 21/01/17.
 */

public class CurrencyRate {

    public Currency getEur() {
        return eur;
    }

    public void setEur(Currency eur) {
        this.eur = eur;
    }

    public Currency getGbp() {
        return gbp;
    }

    public void setGbp(Currency gbp) {
        this.gbp = gbp;
    }

    public Currency getSar() {
        return sar;
    }

    public void setSar(Currency sar) {
        this.sar = sar;
    }

    public Currency getUsd() {
        return usd;
    }

    public void setUsd(Currency usd) {
        this.usd = usd;
    }

    @SerializedName("eur")
    @Expose
    private Currency eur;
    @SerializedName("gbp")
    @Expose
    private Currency gbp;
    @SerializedName("sar")
    @Expose
    private Currency sar;
    @SerializedName("usd")
    @Expose
    private Currency usd;


}
