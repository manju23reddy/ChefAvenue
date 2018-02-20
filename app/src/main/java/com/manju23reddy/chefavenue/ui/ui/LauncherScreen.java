package com.manju23reddy.chefavenue.ui.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.manju23reddy.chefavenue.BuildConfig;
import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

import org.json.JSONArray;

import java.lang.ref.ReferenceQueue;

public class LauncherScreen extends AppCompatActivity {

    final String TAG = LauncherScreen.class.getSimpleName();

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_screen);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ChefAvenueConsts.isInternetAvailable(this)) {

            mRequestQueue = Volley.newRequestQueue(this);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    ChefAvenueConsts.RECIPES_URL, null, new
                    Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            RecipesDataHolder holder = RecipesDataHolder.getRecipesDataHolderInstance();
                            holder.parseRecipesFromJSON(response);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onRecipesDownloadFinish();
                                }
                            });

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, error.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.failed_to_download_recipes),
                                    Toast.LENGTH_LONG).show();
                            onRecipesDownloadError();
                        }
                    });
                }
            });

            mRequestQueue.add(jsonArrayRequest);
        }
        else{
            Toast.makeText(this, getString(R.string.no_internet_Connection),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onRecipesDownloadFinish(){
        Intent launchMainActivity = new Intent(this, CfAMainActivity.class);
        launchMainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP );
        startActivity(launchMainActivity);
        finish();
    }

    public void onRecipesDownloadError(){
        finish();
    }
}
