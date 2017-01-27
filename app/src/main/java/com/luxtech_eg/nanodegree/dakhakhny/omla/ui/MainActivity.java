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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.luxtech_eg.nanodegree.dakhakhny.omla.R;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.Contract;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.PrefUtils;
import com.luxtech_eg.nanodegree.dakhakhny.omla.sync.BankRatesSyncJob;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

import static com.luxtech_eg.nanodegree.dakhakhny.omla.data.PrefUtils.getCurrencyDisplayMode;

public class MainActivity extends AppCompatActivity implements CurrencyAdapter.CurrencyAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor> {
    private String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.tv_rates_title)
    TextView ratesTitle;

    @BindView(R.id.iv_buy_sort)
    ImageView buySort;
    @BindView(R.id.iv_sell_sort)
    ImageView sellSort;

    @BindView(R.id.tv_sell)
    TextView sell;
    @BindView(R.id.tv_buy)
    TextView buy;

    RecyclerView.LayoutManager layoutManager;
    CurrencyAdapter adapter;

    private static final int RATES_LOADER = 0;
    private static final String DESCENDING_ORDER = "  DESC";
    private static final int NO_SORTING = 0;
    private static final int BUY_SORTING = 1;
    private static final int SELL_SORTING = 2;
    int sortingOrder;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        sortingOrder = NO_SORTING;
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
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortWithBuy();
                buySort.setVisibility(View.VISIBLE);
                sellSort.setVisibility(View.INVISIBLE);
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortWithSell();
                buySort.setVisibility(View.INVISIBLE);
                sellSort.setVisibility(View.VISIBLE);
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
        // restarts loader to make sorting correctly
        getSupportLoaderManager().restartLoader(RATES_LOADER, null, this);
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

        if (sortingOrder == BUY_SORTING) {
            // buy Sorting of currency
            sortOrder = getBuyingCurrencySortOrder() + DESCENDING_ORDER;

        } else if (sortingOrder == SELL_SORTING) {
            // sell Sorting of currency
            sortOrder = getSellingCurrencySortOrder() + DESCENDING_ORDER;
        }
        Log.v(TAG, "sortOrder " + sortOrder);
        return new CursorLoader(this,
                Contract.Bank.URI,
                Contract.Bank.BANK_COLUMNS,
                null, null, sortOrder);
    }

    //returns the selling column of given currency to search with
    String getSellingCurrencySortOrder() {
        String currencyDisplayMode = PrefUtils.getCurrencyDisplayMode(this);
        String sortOrder = null;
        if (currencyDisplayMode.equals(getString(R.string.prefs_currency_display_value_usd))) {
            sortOrder = Contract.Bank.COLUMN_USD_SELL_PRICE;
        } else if (currencyDisplayMode.equals(getString(R.string.prefs_currency_display_value_eur))) {
            sortOrder = Contract.Bank.COLUMN_EUR_SELL_PRICE;
        } else if (currencyDisplayMode.equals(getString(R.string.prefs_currency_display_value_sar))) {
            sortOrder = Contract.Bank.COLUMN_SAR_SELL_PRICE;
        } else if (currencyDisplayMode.equals(getString(R.string.prefs_currency_display_value_gbp))) {
            sortOrder = Contract.Bank.COLUMN_GBP_SELL_PRICE;
        }
        return sortOrder;
    }

    String getBuyingCurrencySortOrder() {
        String currencyDisplayMode = PrefUtils.getCurrencyDisplayMode(this);
        String sortOrder = null;
        if (currencyDisplayMode.equals(getString(R.string.prefs_currency_display_value_usd))) {
            sortOrder = Contract.Bank.COLUMN_USD_BUY_PRICE;
        } else if (currencyDisplayMode.equals(getString(R.string.prefs_currency_display_value_eur))) {
            sortOrder = Contract.Bank.COLUMN_EUR_BUY_PRICE;
        } else if (currencyDisplayMode.equals(getString(R.string.prefs_currency_display_value_sar))) {
            sortOrder = Contract.Bank.COLUMN_SAR_BUY_PRICE;
        } else if (currencyDisplayMode.equals(getString(R.string.prefs_currency_display_value_gbp))) {
            sortOrder = Contract.Bank.COLUMN_GBP_BUY_PRICE;
        }
        return sortOrder;
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
        Log.v(TAG, "notifyTitleChanged" + getCurrencyDisplayMode(MainActivity.this));
        String displayMode = getCurrencyDisplayMode(MainActivity.this);
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

    void sortWithBuy() {
        sortingOrder = BUY_SORTING;
        getSupportLoaderManager().restartLoader(RATES_LOADER, null, this);
    }

    void sortWithSell() {
        sortingOrder = SELL_SORTING;
        getSupportLoaderManager().restartLoader(RATES_LOADER, null, this);
    }
}
