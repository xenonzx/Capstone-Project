package com.luxtech_eg.nanodegree.dakhakhny.omla;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ahmed on 21/01/17.
 */

public class DetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        return rootView;
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }
}
