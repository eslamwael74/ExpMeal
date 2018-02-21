package com.eslamwael74.inq.expmeal.Widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.eslamwael74.inq.expmeal.Model.Meal;

import java.util.ArrayList;

/**
 * Created by eslam on 2/21/2018.
 */

public class ExpService extends IntentService {

    static final String FAV_ACTIVITY = "FAV_ACTIVITY";
    static final String ACTION_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ExpService(String name) {
        super(name);
    }

    public static void updateService(ArrayList<Meal> meals, Context context) {

        Intent intent = new Intent(context, ExpService.class);
        intent.putExtra(FAV_ACTIVITY, meals);
        context.startService(intent);

    }

    void handleServiceIntent(ArrayList<Meal> meals) {

        Intent intent = new Intent(ACTION_UPDATE);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(FAV_ACTIVITY, meals);
        sendBroadcast(intent);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null)
            handleServiceIntent((ArrayList<Meal>) intent.getExtras().get(FAV_ACTIVITY));

    }
}
