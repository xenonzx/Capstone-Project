package com.luxtech_eg.nanodegree.dakhakhny.omla.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luxtech_eg.nanodegree.dakhakhny.omla.R;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.Contract;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.PrefUtils;
import com.luxtech_eg.nanodegree.dakhakhny.omla.model.Bank;
import com.luxtech_eg.nanodegree.dakhakhny.omla.utils.BankNamesHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmed on 21/01/17.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private final Context context;
    private CurrencyAdapterOnClickHandler clickHandler;

    private ArrayList<Bank> banks;
    private Cursor cursor;

    CurrencyAdapter(Context context, CurrencyAdapterOnClickHandler clickListener) {
        this.context = context;
        this.clickHandler = clickListener;
    }

    void setCursor(Cursor cursor) {

        this.cursor = cursor;
        notifyDataSetChanged();
    }


    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.layout_rates_list_item_main_activity, parent, false);

        return new CurrencyViewHolder(item);

    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String displayMode = PrefUtils.getCurrencyDisplayMode(context);

        double buyPrice = 0;
        double sellPrice = 0;

        if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_usd))) {
            buyPrice = cursor.getDouble(Contract.Bank.POSITION_USD_BUY_PRICE);
            sellPrice = cursor.getDouble(Contract.Bank.POSITION_USD_SELL_PRICE);
        } else if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_sar))) {
            buyPrice = cursor.getDouble(Contract.Bank.POSITION_SAR_BUY_PRICE);
            sellPrice = cursor.getDouble(Contract.Bank.POSITION_SAR_SELL_PRICE);
        } else if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_gbp))) {
            buyPrice = cursor.getDouble(Contract.Bank.POSITION_GBP_BUY_PRICE);
            sellPrice = cursor.getDouble(Contract.Bank.POSITION_GBP_SELL_PRICE);
        } else if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_eur))) {
            buyPrice = cursor.getDouble(Contract.Bank.POSITION_EUR_BUY_PRICE);
            sellPrice = cursor.getDouble(Contract.Bank.POSITION_EUR_SELL_PRICE);
        }

        holder.bind(BankNamesHelper.getBankNames(context, cursor.getString(Contract.Bank.POSITION_BANK_SYMBOL)), buyPrice, sellPrice);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
        }
        return count;
    }

    interface CurrencyAdapterOnClickHandler {
        void onClick(String symbol);
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        String TAG = CurrencyViewHolder.class.getSimpleName();
        @BindView(R.id.tv_item_title)
        TextView title;

        @BindView(R.id.tv_buy_rate)
        TextView buy;

        @BindView(R.id.tv_sell_rate)
        TextView sell;

        CurrencyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.v(TAG, "onclick");
            int adapterPosition = getAdapterPosition();
            cursor.moveToPosition(adapterPosition);
            clickHandler.onClick(cursor.getString(Contract.Bank.POSITION_BANK_SYMBOL));

        }

        void bind(String rateTitle, Double buyRate, Double sellRate) {
            title.setText(rateTitle);
            buy.setText("" + buyRate);
            sell.setText("" + sellRate);
        }


    }
}
