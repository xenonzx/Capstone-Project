package com.luxtech_eg.nanodegree.dakhakhny.omla.sync;


import android.content.Intent;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

//TODO extend from jop dispatcher
public class BankRatesJobService extends JobService {
    String TAG = BankRatesJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob");
        Intent nowIntent = new Intent(getApplicationContext(), BanksRatesIntentService.class);
        getApplicationContext().startService(nowIntent);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


}
