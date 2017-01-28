package com.luxtech_eg.nanodegree.dakhakhny.omla.utils;

import android.content.Context;

import com.luxtech_eg.nanodegree.dakhakhny.omla.R;

import static com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse.Bank_AAIB;
import static com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse.Bank_ALEX_BANK;
import static com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse.Bank_BANQUE_DU_CAIRE;
import static com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse.Bank_BENQUE_MISR;
import static com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse.Bank_BLOM;
import static com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse.Bank_CEB;
import static com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse.Bank_CIB;
import static com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse.Bank_NBE;
import static com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse.Bank_SCBANK;

/**
 * Created by ahmed on 28/01/17.
 */

public class BankNamesHelper {


  public static String getBankNames(Context context, String symbol) {
        String bank = symbol;
        if (Bank_AAIB.equals(symbol)) {
            bank = context.getString(R.string.aaib);
        } else if (Bank_ALEX_BANK.equals(symbol)) {
            bank = context.getString(R.string.alexbank);
        } else if (Bank_BANQUE_DU_CAIRE.equals(symbol)) {
            bank = context.getString(R.string.banqueducaire);
        } else if (Bank_BENQUE_MISR.equals(symbol)) {
            bank = context.getString(R.string.benquemisr);
        } else if (Bank_BLOM.equals(symbol)) {
            bank = context.getString(R.string.blom);
        } else if (Bank_CEB.equals(symbol)) {
            bank = context.getString(R.string.cbe);
        } else if (Bank_CIB.equals(symbol)) {
            bank = context.getString(R.string.cib);
        } else if (Bank_NBE.equals(symbol)) {
            bank = context.getString(R.string.nbe);
        } else if (Bank_SCBANK.equals(symbol)) {
            bank = context.getString(R.string.scbank);
        }
        return bank;
    }
}
