package com.luxtech_eg.nanodegree.dakhakhny.omla.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.luxtech_eg.nanodegree.dakhakhny.omla.R;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.Contract;
import com.luxtech_eg.nanodegree.dakhakhny.omla.data.PrefUtils;
import com.luxtech_eg.nanodegree.dakhakhny.omla.ui.DetailsActivity;
import com.luxtech_eg.nanodegree.dakhakhny.omla.utils.BankNamesHelper;

/**
 * Created by ahmed on 04/01/17.
 */

public class DetailWidgetRemoteViewsService extends RemoteViewsService {
    String TAG = DetailWidgetRemoteViewsService.class.getSimpleName();
    int REQUEST_CODE = 0;
    int NO_FLAGS = 0;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                Log.v(TAG, "onCreate");
            }

            @Override
            public void onDataSetChanged() {
                Log.v(TAG, "onDataSetChanged");
                if (data != null) {
                    data.close();
                }
                //AS FOUND IN THE Advanced_Android_Development REPO
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                data = getContentResolver().query(Contract.Bank.URI,
                        Contract.Bank.BANK_COLUMNS,
                        null, null, Contract.Bank.COLUMN_BANK_SYMBOL);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {


                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                Log.v(TAG, "getViewAt position " + position);

                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }

                double buyPrice = 0;
                double sellPrice = 0;
                String bankSymbol = data.getString(Contract.Bank.POSITION_BANK_SYMBOL);
                Context context = DetailWidgetRemoteViewsService.this;
                String displayMode = PrefUtils.getCurrencyDisplayMode(context);
                if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_usd))) {
                    buyPrice = data.getDouble(Contract.Bank.POSITION_USD_BUY_PRICE);
                    sellPrice = data.getDouble(Contract.Bank.POSITION_USD_SELL_PRICE);
                } else if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_sar))) {
                    buyPrice = data.getDouble(Contract.Bank.POSITION_SAR_BUY_PRICE);
                    sellPrice = data.getDouble(Contract.Bank.POSITION_SAR_SELL_PRICE);
                } else if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_gbp))) {
                    buyPrice = data.getDouble(Contract.Bank.POSITION_GBP_BUY_PRICE);
                    sellPrice = data.getDouble(Contract.Bank.POSITION_GBP_SELL_PRICE);
                } else if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_eur))) {
                    buyPrice = data.getDouble(Contract.Bank.POSITION_EUR_BUY_PRICE);
                    sellPrice = data.getDouble(Contract.Bank.POSITION_EUR_SELL_PRICE);
                }


                RemoteViews views = new RemoteViews(getPackageName(), R.layout.layout_rates_list_item_widget);
                views.setTextViewText(R.id.tv_item_title, BankNamesHelper.getBankNames(context, bankSymbol));
                views.setTextViewText(R.id.tv_buy_rate, String.format(context.getString(R.string.price_with_egp_currency), buyPrice));
                views.setTextViewText(R.id.tv_sell_rate, String.format(context.getString(R.string.price_with_egp_currency), sellPrice));
                views.setOnClickFillInIntent(R.id.fl_list_item, getFillIntent(bankSymbol));
                return views;

            }


            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(Contract.Bank.POSITION_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                //TODO
                return false;
            }

            Intent getFillIntent(String symbol) {
                Intent intent = new Intent();
                intent.putExtra(DetailsActivity.EXTRAS_BANK_SYMBOL_KEY, symbol);
                return intent;
            }
        };
    }
}
