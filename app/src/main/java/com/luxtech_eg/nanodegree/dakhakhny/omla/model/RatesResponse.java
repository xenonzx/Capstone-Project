package com.luxtech_eg.nanodegree.dakhakhny.omla.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ahmed on 21/01/17.
 */

public class RatesResponse {

    @SerializedName("aaib")
    @Expose
    private Bank aaib;
    @SerializedName("alexbank")
    @Expose
    private Bank alexbank;
    @SerializedName("banqueducaire")
    @Expose
    private Bank banqueducaire;
    @SerializedName("benquemisr")
    @Expose
    private Bank benquemisr;
    @SerializedName("blom")
    @Expose
    private Bank blom;
    @SerializedName("cbe")
    @Expose
    private Bank cbe;
    @SerializedName("cib")
    @Expose
    private Bank cib;
    @SerializedName("nbe")
    @Expose
    private Bank nbe;
    @SerializedName("scbank")
    @Expose
    private Bank scbank;

    ArrayList<Bank> getBanks() {
        ArrayList<Bank> banks = new ArrayList<Bank>();
        addBankIfNOtNull(banks, aaib);
        addBankIfNOtNull(banks, alexbank);
        addBankIfNOtNull(banks, banqueducaire);
        addBankIfNOtNull(banks, benquemisr);
        addBankIfNOtNull(banks, blom);
        addBankIfNOtNull(banks, cbe);
        addBankIfNOtNull(banks, cib);
        addBankIfNOtNull(banks, nbe);
        addBankIfNOtNull(banks, scbank);
        return banks;
    }

    void addBankIfNOtNull(ArrayList<Bank> banks, Bank bank) {
        if (bank != null) {
            banks.add(bank);
        }
    }

    public Bank getAaib() {
        return aaib;
    }

    public void setAaib(Bank aaib) {
        this.aaib = aaib;
    }

    public Bank getAlexbank() {
        return alexbank;
    }

    public void setAlexbank(Bank alexbank) {
        this.alexbank = alexbank;
    }

    public Bank getBanqueducaire() {
        return banqueducaire;
    }

    public void setBanqueducaire(Bank banqueducaire) {
        this.banqueducaire = banqueducaire;
    }

    public Bank getBenquemisr() {
        return benquemisr;
    }

    public void setBenquemisr(Bank benquemisr) {
        this.benquemisr = benquemisr;
    }

    public Bank getBlom() {
        return blom;
    }

    public void setBlom(Bank blom) {
        this.blom = blom;
    }

    public Bank getCbe() {
        return cbe;
    }

    public void setCbe(Bank cbe) {
        this.cbe = cbe;
    }

    public Bank getCib() {
        return cib;
    }

    public void setCib(Bank cib) {
        this.cib = cib;
    }

    public Bank getNbe() {
        return nbe;
    }

    public void setNbe(Bank nbe) {
        this.nbe = nbe;
    }

    public Bank getScbank() {
        return scbank;
    }

    public void setScbank(Bank scbank) {
        this.scbank = scbank;
    }


}
