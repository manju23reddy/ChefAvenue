package com.manju23reddy.chefavenue.ui;

import android.app.Application;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;

import org.json.JSONArray;

import java.io.InputStream;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class ApplicationClass extends Application {

    private static String TAG = ApplicationClass.class.getSimpleName();

    public static boolean isTab = false;

    @Override
    public void onCreate() {
        super.onCreate();

        isTab = getResources().getBoolean(R.bool.is_tab);


    }


}
