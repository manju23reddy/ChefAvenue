package com.manju23reddy.chefavenue.ui.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.model.RecipeIngredientModel;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class ChefAvenueWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.app_name);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.chef_avenue_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, CfAMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                0);


        views.setOnClickPendingIntent(R.id.txtv_widget_ingredients, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateIngredients(Context context, AppWidgetManager appWidgetManager,
                                         RecipesModel recipe, int[] appWidgets){
        for (int widgetID : appWidgets){
            updateWidgetIngredients(context, appWidgetManager, recipe, widgetID);
        }

    }

    static void updateWidgetIngredients(Context context, AppWidgetManager appWidgetManager,
                                        RecipesModel recipe, int appWidgetId){
        if (null != recipe){
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.chef_avenue_widget);
            views.setTextViewText(R.id.appwidget_text, recipe.getRecipeName());
            String ingredients = "";
            ArrayList<RecipeIngredientModel> ingreModel = recipe.getIngredients();
            for (RecipeIngredientModel cur : ingreModel){
                ingredients = ingredients+"\n"+cur.getIngredient()+" "+cur.getQuantity()+
                        " "+cur.getMeasure();
            }
            views.setTextViewText(R.id.txtv_widget_ingredients, ingredients);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

