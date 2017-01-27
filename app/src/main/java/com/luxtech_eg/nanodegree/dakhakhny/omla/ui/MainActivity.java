package com.luxtech_eg.nanodegree.dakhakhny.omla.ui;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.luxtech_eg.nanodegree.dakhakhny.omla.R;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.Contract;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.PrefUtils;
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

    @BindView(R.id.tv_rates_title)
    TextView ratesTitle;
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
        notifyTitleChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_usd) {
            PrefUtils.setCurrencyDisplayMode(MainActivity.this, getString(R.string.prefs_currency_display_value_usd));
        } else if (id == R.id.menu_item_eur) {
            PrefUtils.setCurrencyDisplayMode(MainActivity.this, getString(R.string.prefs_currency_display_value_eur));
        } else if (id == R.id.menu_item_sar) {
            PrefUtils.setCurrencyDisplayMode(MainActivity.this, getString(R.string.prefs_currency_display_value_sar));
        } else if (id == R.id.menu_item_gbp) {
            PrefUtils.setCurrencyDisplayMode(MainActivity.this, getString(R.string.prefs_currency_display_value_gbp));
        }
        adapter.notifyDataSetChanged();
        notifyTitleChanged();
        return true;
    }

    @Override
    public void onClick(String symbol) {
        //Uri bankSymbol = Contract.Bank.makeUriForBank(symbol);
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRAS_BANK_SYMBOL_KEY, symbol);
        startActivity(intent);
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

    void notifyTitleChanged() {
        Log.v(TAG, "notifyTitleChanged" + PrefUtils.getCurrencyDisplayMode(MainActivity.this));
        String displayMode = PrefUtils.getCurrencyDisplayMode(MainActivity.this);
        String rate = getString(R.string.us_dollar_to_egyptian_pound);

        if (displayMode.equals(getString(R.string.prefs_currency_display_value_usd))) {

            rate = getString(R.string.us_dollar_to_egyptian_pound);
        } else if (displayMode.equals(getString(R.string.prefs_currency_display_value_eur))) {
            rate = getString(R.string.euro_to_egyptian_pound);
        } else if (displayMode.equals(getString(R.string.prefs_currency_display_value_sar))) {
            rate = getString(R.string.saudi_riyal_to_egyptian_pound);
        } else if (displayMode.equals(getString(R.string.prefs_currency_display_value_gbp))) {
            rate = getString(R.string.british_pound_to_egyptian_pound);
        }
        String ratesTitleText = String.format(getString(R.string.showing_rates_for), rate);
        Log.v(TAG, ratesTitleText);
        ratesTitle.setText(ratesTitleText);

    }
}
