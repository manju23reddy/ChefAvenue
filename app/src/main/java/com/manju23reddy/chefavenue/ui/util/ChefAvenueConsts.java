package com.manju23reddy.chefavenue.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.BitmapFactory;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public final class ChefAvenueConsts {

    /**
     * JSON Keys for Recipe JSON Object
     */
    public final static String RECIPE_ID = "id";
    public final static String RECIPE_NAME = "name";
    public final static String RECIPE_SERVINGS = "servings";
    public final static String RECIPE_IMAGE = "image";
    public final static String RECIPE_INGREDIENTS = "ingredients";
    public final static String RECIPE_STEPS = "steps";

    /**
     * JSON Keys for Recipe ingredient
     */
    public final static String INGREDIENTS_QUANTITY = "quantity";
    public final static String INGREDIENTS_MEASURE = "measure";
    public final static String INGREDIENTS_INGREDIENT = "ingredient";


    /**
     * JSON keys for STEPS
     */
    public final static String STEP_ID = "id";
    public final static String STEP_SHORT_DESCRIPTION = "shortDescription";
    public final static String STEP_DESCRIPTION = "description";
    public final static String STEP_VIDEO_URL = "videoURL";
    public final static String STEP_THUMBNAIL_URL = "thumbnailURL";


    /**
     * keys for bundle
     */
    public final static String SELECTED_RECIPE = "recipe";


    public final static int PHONE_GRID_SPAN_COUNT = 1;
    public final static int TAB_GRID_SPAN_COUNT = 3;

    public final static String MAIN_RCV_INDEX = "rcv_index";
    public final static String STEPS_RCV_INDEX = "persist";

    public final static String RESTORE_LAST_FRAGMENT = "restore_fragment";

    public final static String RESTORE_DETAIL_FRAGMENT = "restore_detail";



    public final static String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/" +
            "topher/2017/May/59121517_baking/baking.json";



    public final static String PLAYER_POS = "PLAYER_POS";



    private ChefAvenueConsts(){

    }

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager conManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conManager.getActiveNetworkInfo();
        if (null != activeNetwork && activeNetwork.isConnectedOrConnecting()) {
            return true;
        }
        else{
            return false;
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
