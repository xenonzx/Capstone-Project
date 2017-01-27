package com.luxtech_eg.nanodegree.dakhakhny.omla;

import android.app.Activity;

import com.google.android.gms.ads.AdView;

/**
 * Created by ahmed on 26/09/16.
 */
public class AdsHelper {
    public static AdView getAdView(Activity activity) {
        return (AdView) activity.findViewById(R.id.adView);
    }
}
