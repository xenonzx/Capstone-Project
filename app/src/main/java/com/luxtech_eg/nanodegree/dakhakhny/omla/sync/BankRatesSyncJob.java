package com.luxtech_eg.nanodegree.dakhakhny.omla.sync;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.Contract;
import com.luxtech_eg.nanodegree.dakhakhny.omla.model.Bank;
import com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class BankRatesSyncJob {

    private static final int ONE_OFF_ID = 2;
    public static final String ACTION_DATA_UPDATED = "com.luxtech_eg.nanodegree.dakhakhny.omla.ACTION_DATA_UPDATED";
    public static final String ACTION_INVALID_SYMBOL = "com.luxtech_eg.nanodegree.dakhakhny.omla.ACTION_INVALID_SYMBOL";
    public static final String EXTRAS_INVALID_SYMBOL = "EXTRAS_INVALID_SYMBOL";
    private static final int PERIOD = 300000;
    private static final int INITIAL_BACKOFF = 10000;
    private static final int PERIODIC_ID = 1;
    private static final int YEARS_OF_HISTORY = 2;

    static String TAG = BankRatesSyncJob.class.getSimpleName();

    private BankRatesSyncJob() {
    }

    //TODO PARSE BANK SYMBOLS
    static void getBankRates(Context context) {

        Log.d(TAG, "getBankRates");
        ArrayList<ContentValues> BankCVs = new ArrayList<>();
        //TODO MAke Api Request and parse response
        try {
            //Response<ResponseBody> response = ClientGenerator.getRatesApiClient().fetchRates().execute();
            //Log.v(TAG, "ResponseBody" + new Gson().toJson(response.body()));
//            ArrayList<Bank> banks = response.body().getBanks();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.curates.club/")
                    .build();

            Response response = client.newCall(request).execute();
            String body = new String(response.body().string());

            Log.v(TAG, "ResponseBody" + body);
            Log.v(TAG, "RatesResponse" + new Gson().toJson(new Gson().fromJson(body, RatesResponse.class)));
            ArrayList<Bank> banks = new Gson().fromJson(body, RatesResponse.class).getBanks();

            int banksLength = banks.size();
            for (int i = 0; i < banksLength; i++) {

                Bank b = banks.get(i);
                ContentValues BankCV = new ContentValues();
                BankCV.put(Contract.Bank.COLUMN_BANK_SYMBOL, b.getId());
                BankCV.put(Contract.Bank.COLUMN_BANK_TITLE, b.getTitle());
                BankCV.put(Contract.Bank.COLUMN_BANK_URL, b.getRef());
                BankCV.put(Contract.Bank.COLUMN_USD_BUY_PRICE, b.getCurrencyRate().getUsd().getBuy());
                BankCV.put(Contract.Bank.COLUMN_USD_SELL_PRICE, b.getCurrencyRate().getUsd().getSell());
                BankCV.put(Contract.Bank.COLUMN_EUR_BUY_PRICE, b.getCurrencyRate().getEur().getBuy());
                BankCV.put(Contract.Bank.COLUMN_EUR_SELL_PRICE, b.getCurrencyRate().getEur().getSell());
                BankCV.put(Contract.Bank.COLUMN_SAR_BUY_PRICE, b.getCurrencyRate().getSar().getBuy());
                BankCV.put(Contract.Bank.COLUMN_SAR_SELL_PRICE, b.getCurrencyRate().getSar().getSell());
                BankCV.put(Contract.Bank.COLUMN_GBP_BUY_PRICE, b.getCurrencyRate().getGbp().getBuy());
                BankCV.put(Contract.Bank.COLUMN_GBP_SELL_PRICE, b.getCurrencyRate().getGbp().getSell());
                BankCVs.add(BankCV);

            }
            int numRowsInserted = context.getContentResolver()
                    .bulkInsert(
                            Contract.Bank.URI,
                            BankCVs.toArray(new ContentValues[BankCVs.size()]));
            Log.e(TAG, "number of rows newly inserted" + numRowsInserted);
            Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
            context.sendBroadcast(dataUpdatedIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO Check if not Success
        Log.e(TAG, "Error fetching bank rates");
    }


    private static void schedulePeriodic(Context context) {
        Log.d(TAG, "Scheduling a periodic task");
        //TODO
    }


    public static synchronized void initialize(final Context context) {

        schedulePeriodic(context);
        syncImmediately(context);

    }

    public static synchronized void syncImmediately(Context context) {
        Log.d(TAG, "syncImmediately");
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            Log.d(TAG, "syncImmediately online");
            Intent nowIntent = new Intent(context, BanksRatesIntentService.class);
            context.startService(nowIntent);
        } else {
            Log.d(TAG, "syncImmediately not ONLINE");
            //TODO call Jop Dispatcher

        }
    }


}
