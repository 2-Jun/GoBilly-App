package com.TwoJun.gobillyapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by Arjun on 11/24/13.
 */
public class BroadcastReceiver extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews labels = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        ComponentName widget = new ComponentName(context, BroadcastReceiver.class);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GoBillyWordDispatcher.getInstance().update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
            labels.setTextViewText(R.id.word, GoBillyWordDispatcher.getInstance().getWord());
            labels.setTextViewText(R.id.desc, GoBillyWordDispatcher.getInstance().getDescription());
            labels.setTextViewText(R.id.ex, GoBillyWordDispatcher.getInstance().getExample());

            appWidgetManager.updateAppWidget(widget, labels);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
}

