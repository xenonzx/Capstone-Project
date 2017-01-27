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
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.v(TAG, "onUpdate");
        // once on update is called will make remote views object and notify all widgets with the view through passed id -through using app widget manager-
        for (int i = 0; i < appWidgetIds.length; i++) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            views.setOnClickPendingIntent(R.id.tv_widget_header, getMainActivityIntent(context));
            views.setRemoteAdapter(R.id.lv_bank_rates, new Intent(context, DetailWidgetRemoteViewsService.class));
            views.setPendingIntentTemplate(R.id.lv_bank_rates, getClickIntentTemplate(context));
            appWidgetManager.updateAppWidget(appWidgetIds[i], views);

        }
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
