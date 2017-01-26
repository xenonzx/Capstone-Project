package com.luxtech_eg.nanodegree.dakhakhny.omla.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.luxtech_eg.nanodegree.dakhakhny.omla.R;

/**
 * Created by ahmed on 21/01/17.
 */

public class DetailsActivity extends AppCompatActivity {
    public static String EXTRAS_BANK_URI_KEY = "bank_uri";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DetailsFragment detailsFragment;
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(EXTRAS_BANK_URI_KEY)) {
            detailsFragment = DetailsFragment.newInstance(getIntent().getExtras().getString(EXTRAS_BANK_URI_KEY));
        } else {
            detailsFragment = DetailsFragment.newInstance();
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, detailsFragment);
        fragmentTransaction.commit();
    }


}
