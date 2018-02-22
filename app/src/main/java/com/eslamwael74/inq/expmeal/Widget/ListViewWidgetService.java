package com.eslamwael74.inq.expmeal.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.eslamwael74.inq.expmeal.Model.Meal;
import com.eslamwael74.inq.expmeal.R;

import java.util.ArrayList;

/**
 * Created by eslam on 2/21/2018.
 */

public class ListViewWidgetService extends RemoteViewsService {

    ArrayList<Meal> meals;
    static String MEAL_KEY = "meal";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(this.getApplicationContext(), intent);
    }


    class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        Context context;

        public ListRemoteViewFactory(Context context, Intent intent) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            meals = ExpWidgetProvider.meals;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return meals.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            //here i return my widget View...

            String category;


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_listview_item);
            Meal meal = meals.get(position);

            if (meal.getCategory() == 0)
                category = getString(R.string.breakfast);
            else if (meal.getCategory() == 1)
                category = getString(R.string.lunch);
            else
                category = getString(R.string.dinner);

            remoteViews.setTextViewText(R.id.tv_widget_favs,
                    meal.getName() + "\n" +
                            getString(R.string.category) + category + "\n"
            );

            Intent intent = new Intent();
            intent.putExtra(MEAL_KEY, meal);
            remoteViews.setOnClickFillInIntent(R.id.tv_widget_favs, intent);

            return remoteViews;
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
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }


}
