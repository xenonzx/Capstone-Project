package com.luxtech_eg.nanodegree.dakhakhny.omla.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.luxtech_eg.nanodegree.dakhakhny.omla.R;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.Contract;
import com.luxtech_eg.nanodegree.dakhakhny.omla.sync.BankRatesSyncJob;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements CurrencyAdapter.CurrencyAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor> {
    private String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout refreshLayout;
    RecyclerView.LayoutManager layoutManager;
    CurrencyAdapter adapter;

    private static final int RATES_LOADER = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        //Log.v(TAG, new Gson().toJson(new Gson().fromJson(getDummyJson(), RatesResponse.class).getBanks()));
        layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new CurrencyAdapter(MainActivity.this, this);
        //adapter.setBanks(new Gson().fromJson(getDummyJson(), RatesResponse.class).getBanks());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        BankRatesSyncJob.initialize(this);
        getSupportLoaderManager().initLoader(RATES_LOADER, null, this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BankRatesSyncJob.syncImmediately(MainActivity.this);
            }
        });

    }


    @Override
    public void onClick(String symbol) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(TAG, "onCreateLoader");
        String sortOrder = Contract.Bank.COLUMN_BANK_SYMBOL;
        return new CursorLoader(this,
                Contract.Bank.URI,
                Contract.Bank.BANK_COLUMNS,
                null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(TAG, "onLoadFinished");
        if (data.getCount() != 0) {
            //TODO Hide ERROR MESSAGE
        }
        adapter.setCursor(data);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.v(TAG, "onLoaderReset");
        adapter.setCursor(null);
    }
}
