package com.luxtech_eg.nanodegree.dakhakhny.omla.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.luxtech_eg.nanodegree.dakhakhny.omla.R;

/**
 * Created by ahmed on 21/01/17.
 */

public class DetailsActivity extends AppCompatActivity {
    public static String EXTRAS_BANK_SYMBOL_KEY = "bank_symbol";
    public static String DETAILS_FRAG_TAG = "details_frag_tag";
    DetailsFragment detailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        detailsFragment = (DetailsFragment) getFragmentManager().findFragmentByTag(DETAILS_FRAG_TAG);
        if (detailsFragment == null) {
            Log.v("gaga", "detailsFragment is not null");

            if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(EXTRAS_BANK_SYMBOL_KEY)) {
                detailsFragment = DetailsFragment.newInstance(getIntent().getExtras().getString(EXTRAS_BANK_SYMBOL_KEY));
            } else {
                detailsFragment = DetailsFragment.newInstance();
            }
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, detailsFragment, DETAILS_FRAG_TAG);
        fragmentTransaction.commit();
    }


}
