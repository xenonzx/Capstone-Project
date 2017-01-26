package com.luxtech_eg.nanodegree.dakhakhny.omla.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.luxtech_eg.nanodegree.dakhakhny.omla.R;

import static com.luxtech_eg.nanodegree.dakhakhny.omla.sync.BankRatesSyncJob.ACTION_DATA_UPDATED;


public final class PrefUtils {

    private PrefUtils() {
    }

    public static String getCurrencyDisplayMode(Context context) {
        String key = context.getString(R.string.prefs_currency_display_key);
        String defaultValue = context.getString(R.string.prefs_currency_display_value_default);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, defaultValue);
    }

    public static void setCurrencyDisplayMode(Context context, String prefs_currency_value) {
        String key = context.getString(R.string.prefs_currency_display_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(key, prefs_currency_value);

        editor.apply();
        // notify widget as display mode changed
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
        context.sendBroadcast(dataUpdatedIntent);
    }

}
