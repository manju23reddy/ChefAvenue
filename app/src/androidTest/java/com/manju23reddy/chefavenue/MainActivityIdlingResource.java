package com.manju23reddy.chefavenue;

import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.manju23reddy.chefavenue.ui.ui.CfAMainActivity;
import com.manju23reddy.chefavenue.ui.ui.LauncherScreen;

import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Handler;

/**
 * Created by MReddy3 on 2/20/2018.
 */

public class MainActivityIdlingResource implements IdlingResource {

    private LauncherScreen launchScreen;
    private ResourceCallback mCallBack;

    private ProgressBar mDownloadPbr;

    public MainActivityIdlingResource(LauncherScreen activity){
        this.launchScreen = activity;

        mDownloadPbr = this.launchScreen.findViewById(R.id.pbr_download_progress);

    }

    @Override
    public String getName() {
        Random rand = new Random();
        return MainActivityIdlingResource.class.getSimpleName()+ rand.nextInt(100);
    }

    @Override
    public boolean isIdleNow() {
       if (mDownloadPbr.getVisibility() == View.VISIBLE){
           return false;
       }
       else{
           mCallBack.onTransitionToIdle();
           return true;
       }

    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallBack = callback;
    }
}
