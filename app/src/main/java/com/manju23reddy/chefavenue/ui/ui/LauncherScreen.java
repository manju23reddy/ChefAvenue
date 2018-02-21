package com.manju23reddy.chefavenue.ui.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;


public class LauncherScreen extends AppCompatActivity {

    final String TAG = LauncherScreen.class.getSimpleName();


    private ProgressBar mProgressBar;

    RecipesDataHolder mHolder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_screen);
        mProgressBar = findViewById(R.id.pbr_download_progress);
        mHolder = RecipesDataHolder.getRecipesDataHolderInstance(this);
        mHolder.subscribeForDownloadComplete(mDownloaderCallback);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ChefAvenueConsts.isInternetAvailable(this)){
            mHolder.downloadRecipes();
        }
        else{
            Toast.makeText(this, getText(R.string.no_internet_Connection),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onRecipesDownloadFinish(){
        mProgressBar.setVisibility(View.GONE);
        Intent launchMainActivity = new Intent(this, CfAMainActivity.class);
        launchMainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP );
        startActivity(launchMainActivity);
        finish();
    }

    public void onRecipesDownloadError(){
        mProgressBar.setVisibility(View.GONE);
        finish();
    }

    IRecipesDownloader mDownloaderCallback = new IRecipesDownloader() {
        @Override
        public void onDownloadComplete(final boolean status) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (true == status){
                            onRecipesDownloadFinish();
                        }
                        else{
                            onRecipesDownloadError();
                        }
                    }
                });


        }
    };
}
