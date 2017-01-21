package com.luxtech_eg.nanodegree.dakhakhny.omla.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.luxtech_eg.nanodegree.dakhakhny.omla.R;
import com.luxtech_eg.nanodegree.dakhakhny.omla.model.RatesResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CurrencyAdapter.CurrencyAdapterOnClickHandler {
    private String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    CurrencyAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        Log.v(TAG, new Gson().toJson(new Gson().fromJson(getDummyJson(), RatesResponse.class).getBanks()));
        layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new CurrencyAdapter(MainActivity.this, this);
        adapter.setBanks(new Gson().fromJson(getDummyJson(), RatesResponse.class).getBanks());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


    String getDummyJson() {
        String dummy = "{\"aaib\":{\"currency_rate\":{\"eur\":{\"sell\":20.1329,\"buy\":19.8688,\"title\":\"اليورو\"},\"gbp\":{\"sell\":23.2744,\"buy\":22.9299,\"title\":\"الجنيه الإسترلينى\"},\"sar\":{\"sell\":5.0129,\"buy\":4.9581,\"title\":\"الريال السعودى\"},\"usd\":{\"sell\":18.8,\"buy\":18.7,\"title\":\"الدولار الأمريكى\"}},\"ref\":\"http://aaib.com/ar/rates\",\"title\":\"البنك العربى الأفريقى\"},\"alexbank\":{\"currency_rate\":{\"eur\":{\"sell\":20.19977,\"buy\":19.94938,\"title\":\"اليورو\"},\"gbp\":{\"sell\":23.37308,\"buy\":23.01804,\"title\":\"الجنيه الإسترلينى\"},\"sar\":{\"sell\":5.03933,\"buy\":4.98474,\"title\":\"الريال السعودى\"},\"usd\":{\"sell\":18.9,\"buy\":18.78,\"title\":\"الدولار الأمريكى\"}},\"ref\":\"https://www.alexbank.com/Home/ExchangeRates\",\"title\":\"بنك الأسكندريه\"},\"banqueducaire\":{\"currency_rate\":{\"eur\":{\"sell\":20.0258,\"buy\":19.7588,\"title\":\"اليورو\"},\"gbp\":{\"sell\":23.1506,\"buy\":22.8017,\"title\":\"الجنيه الإسترلينى\"},\"sar\":{\"sell\":4.9887,\"buy\":4.9077,\"title\":\"الريال السعودى\"},\"usd\":{\"sell\":18.7,\"buy\":18.6,\"title\":\"الدولار الأمريكى\"}},\"ref\":\"http://www.banqueducaire.com/Arabic/MarketUpdates/Pages/CurrencyExchange.aspx\",\"title\":\"بنك القاهره\"},\"benquemisr\":{\"currency_rate\":{\"eur\":{\"sell\":19.9859,\"buy\":19.7625,\"title\":\"اليورو\"},\"gbp\":{\"sell\":23.1241,\"buy\":22.8073,\"title\":\"الجنيه الإسترلينى\"},\"sar\":{\"sell\":2.6876,\"buy\":2.6579,\"title\":\"الريال السعودى\"},\"usd\":{\"sell\":18.7,\"buy\":18.6,\"title\":\"الدولار الأمريكى\"}},\"ref\":\"http://www.banquemisr.com/ar/exchangerates\",\"title\":\"بنك مصر\"},\"blom\":{\"currency_rate\":{\"eur\":{\"sell\":19.9594,\"buy\":19.81,\"title\":\"اليورو\"},\"gbp\":{\"sell\":23.0438,\"buy\":22.8537,\"title\":\"الجنيه الإسترلينى\"},\"sar\":{\"sell\":4.9997,\"buy\":4.9727,\"title\":\"الريال السعودى\"},\"usd\":{\"sell\":18.75,\"buy\":18.65,\"title\":\"الدولار الامريكى\"}},\"ref\":\"http://www.blombankegypt.com/BlomEgypt/Exchange-rates\",\"title\":\"بنك بلوم\"},\"cbe\":{\"currency_rate\":{\"eur\":{\"sell\":20.1694,\"buy\":19.9903,\"title\":\"اليورو\"},\"gbp\":{\"sell\":23.3104,\"buy\":23.1002,\"title\":\"الجنيه الإسترلينى\"},\"sar\":{\"sell\":5.042,\"buy\":4.998,\"title\":\"الريال السعودى\"},\"usd\":{\"sell\":18.91,\"buy\":18.7456,\"title\":\"الدولار الامريكى\"}},\"ref\":\"http://www.cbe.org.eg/ar/EconomicResearch/Statistics/Pages/ExchangeRatesListing.aspx\",\"title\":\"البنك المركزى\"},\"cib\":{\"currency_rate\":{\"eur\":{\"sell\":20.0073,\"buy\":19.8111,\"title\":\"اليورو\"},\"gbp\":{\"sell\":23.1387,\"buy\":22.8549,\"title\":\"الجنيه الإسترلينى\"},\"sar\":{\"sell\":5.0015,\"buy\":4.964,\"title\":\"الريال السعودى\"},\"usd\":{\"sell\":18.751,\"buy\":18.651,\"title\":\"الدولار الامريكى\"}},\"ref\":\"http://www.cibeg.com/Arabic/Pages/CIBCurrencies.aspx\",\"title\":\"البنك التجارى الدولى\"},\"nbe\":{\"currency_rate\":{\"eur\":{\"sell\":20.0258,\"buy\":19.7625,\"title\":\"اليورو\"},\"gbp\":{\"sell\":23.1506,\"buy\":22.8073,\"title\":\"الجنيه الإسترلينى\"},\"sar\":{\"sell\":4.986,\"buy\":4.9592,\"title\":\"الريال السعودى\"},\"usd\":{\"sell\":18.7,\"buy\":18.6,\"title\":\"الدولار الامريكى\"}},\"ref\":\"https://www.nbe.com.eg/ExchangeRate.aspx\",\"title\":\"البنك الأهلى\"},\"scbank\":{\"currency_rate\":{\"eur\":{\"sell\":20.1191,\"buy\":19.8631,\"title\":\"اليورو\"},\"gbp\":{\"sell\":23.2281,\"buy\":22.915,\"title\":\"الجنيه الإسترلينى\"},\"sar\":{\"sell\":5.0397,\"buy\":4.9647,\"title\":\"الريال السعودى\"},\"usd\":{\"sell\":18.9,\"buy\":18.7,\"title\":\"الدولار الامريكى\"}},\"ref\":\"http://scbank.com.eg/Arabic/CurrencyAll.aspx\",\"title\":\"بنك قناة السويس\"}}";
        return dummy;
    }

    @Override
    public void onClick(String symbol) {

    }
}
