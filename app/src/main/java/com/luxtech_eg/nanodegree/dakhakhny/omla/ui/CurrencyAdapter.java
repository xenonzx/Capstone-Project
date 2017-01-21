package com.luxtech_eg.nanodegree.dakhakhny.omla.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luxtech_eg.nanodegree.dakhakhny.omla.R;
import com.luxtech_eg.nanodegree.dakhakhny.omla.model.Bank;

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

    CurrencyAdapter(Context context, CurrencyAdapterOnClickHandler clickListener) {
        this.context = context;
        this.clickHandler = clickListener;
    }

    void setCursor(Cursor cursor) {
        //TODO convertCusortoArray list
    }

    void setBanks(ArrayList<Bank> banks) {
        this.banks = banks;
        notifyDataSetChanged();
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.layout_rates_list_item_main_activity, parent, false);

        return new CurrencyViewHolder(item);

    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        Bank b = banks.get(position);
        holder.bind(b.getTitle(), b.getCurrencyRate().getUsd().getBuy(), b.getCurrencyRate().getUsd().getSell());
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (banks != null) {
            count = banks.size();
        }
        return count;
    }

    interface CurrencyAdapterOnClickHandler {
        void onClick(String symbol);
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            int adapterPosition = getAdapterPosition();
//            cursor.moveToPosition(adapterPosition);
//            int symbolColumn = cursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL);
//            clickHandler.onClick(cursor.getString(symbolColumn));

        }

        void bind(String rateTitle, Double buyRate, Double sellRate) {
            title.setText(rateTitle);
            buy.setText("" + buyRate);
            sell.setText("" + sellRate);
        }


    }
}
