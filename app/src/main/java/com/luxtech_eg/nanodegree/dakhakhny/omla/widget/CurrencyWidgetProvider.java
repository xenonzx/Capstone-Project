package com.luxtech_eg.nanodegree.dakhakhny.omla.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import com.luxtech_eg.nanodegree.dakhakhny.omla.R;
import com.luxtech_eg.nanodegree.dakhakhny.omla.sync.BankRatesSyncJob;
import com.luxtech_eg.nanodegree.dakhakhny.omla.ui.DetailsActivity;
import com.luxtech_eg.nanodegree.dakhakhny.omla.ui.MainActivity;

import static com.luxtech_eg.nanodegree.dakhakhny.omla.R.id.tv_rates_title;
import static com.luxtech_eg.nanodegree.dakhakhny.omla.data.PrefUtils.getCurrencyDisplayMode;

/**
 * Created by ahmed on 27/01/17.
 */

public class CurrencyWidgetProvider extends AppWidgetProvider {
    private final int MAIN_REQUEST_CODE = 0;
    private final int DETAILS_REQUEST_CODE = 1;
    int NO_FLAGS = 0;

    String TAG = CurrencyWidgetProvider.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.v(TAG, "onReceive" + intent.getAction());
        if (intent.getAction().equals(BankRatesSyncJob.ACTION_DATA_UPDATED)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_bank_rates);
            // appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.tv_rates_title);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);

            // Update text, images, whatever - here
            remoteViews.setTextViewText(tv_rates_title, getRatesTitle(context));
            // Trigger widget layout update
            AppWidgetManager.getInstance(context).updateAppWidget(appWidgetIds, remoteViews);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.v(TAG, "onUpdate");
        // once on update is called will make remote views object and notify all widgets with the view through passed id -through using app widget manager-
        for (int i = 0; i < appWidgetIds.length; i++) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            views.setOnClickPendingIntent(R.id.tv_widget_header, getMainActivityIntent(context));
            views.setTextViewText(tv_rates_title, getRatesTitle(context));
            views.setRemoteAdapter(R.id.lv_bank_rates, new Intent(context, DetailWidgetRemoteViewsService.class));
            views.setPendingIntentTemplate(R.id.lv_bank_rates, getClickIntentTemplate(context));
            appWidgetManager.updateAppWidget(appWidgetIds[i], views);

        }
    }

    String getRatesTitle(Context context) {
        Log.v(TAG, "notifyTitleChanged" + getCurrencyDisplayMode(context));
        String displayMode = getCurrencyDisplayMode(context);
        String rate = context.getString(R.string.us_dollar_to_egyptian_pound);

        if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_usd))) {

            rate = context.getString(R.string.us_dollar_to_egyptian_pound);
        } else if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_eur))) {
            rate = context.getString(R.string.euro_to_egyptian_pound);
        } else if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_sar))) {
            rate = context.getString(R.string.saudi_riyal_to_egyptian_pound);
        } else if (displayMode.equals(context.getString(R.string.prefs_currency_display_value_gbp))) {
            rate = context.getString(R.string.british_pound_to_egyptian_pound);
        }
        return rate;
    }

    PendingIntent getMainActivityIntent(Context cxt) {
        Intent intent = new Intent(cxt, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(cxt, MAIN_REQUEST_CODE, intent, NO_FLAGS);
        return pi;
    }

    PendingIntent getClickIntentTemplate(Context cxt) {
        Intent intent = new Intent(cxt, DetailsActivity.class);
        PendingIntent pendingIntentTemplate = TaskStackBuilder.create(cxt).addNextIntentWithParentStack(intent).getPendingIntent(DETAILS_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntentTemplate;
    }

}
