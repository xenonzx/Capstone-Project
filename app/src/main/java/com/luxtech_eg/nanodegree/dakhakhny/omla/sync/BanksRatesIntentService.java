package com.luxtech_eg.nanodegree.dakhakhny.omla.sync;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class BanksRatesIntentService extends IntentService {
    String TAG = BanksRatesIntentService.class.getSimpleName();

    public BanksRatesIntentService() {
        super(BanksRatesIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(TAG, "onHandleIntent");
        BankRatesSyncJob.getBankRates(getApplicationContext());
    }
}
