package com.manju23reddy.chefavenue.ui.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.model.RecipeStepModel;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class CfARecipeStepDetailsFragment extends Fragment implements View.OnClickListener{
    IRecipeDetailSelectionHandler mSelectionHandler = null;

    ImageView mThumbNailImgV;
    SimpleExoPlayerView mSMediaPlayer;
    SimpleExoPlayer mSexoPlayer;

    TextView mStepSDTxtV;
    TextView mStepFDTxtV;
    TextView mPreviousStepTxtV;
    TextView mNextStepTxtV;
    boolean mIsTab = false;
    int mCurrentStep = 0;
    ArrayList<RecipeStepModel> mRecipeSteps;


    public CfARecipeStepDetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_layout, container,
                false);

        mThumbNailImgV = rootView.findViewById(R.id.img_thumbnail);
        mSMediaPlayer = rootView.findViewById(R.id.sexop_media_player);

        mStepSDTxtV = rootView.findViewById(R.id.txtv_stepsd);
        mStepFDTxtV = rootView.findViewById(R.id.txtv_step_description);
        mPreviousStepTxtV = rootView.findViewById(R.id.btn_previous);
        mNextStepTxtV = rootView.findViewById(R.id.btn_next);

        mIsTab = getResources().getBoolean(R.bool.is_tab);
        if (mIsTab){
            mPreviousStepTxtV.setVisibility(View.GONE);
            mNextStepTxtV.setVisibility(View.GONE);
        }
        else{
            mPreviousStepTxtV.setOnClickListener(this);
            mNextStepTxtV.setOnClickListener(this);
        }


        Bundle args;

        if (null != savedInstanceState){
            mRecipeSteps = savedInstanceState.getParcelableArrayList(ChefAvenueConsts.RECIPE_STEPS);
            mCurrentStep = savedInstanceState.getInt(ChefAvenueConsts.STEP_ID);
        }
        else{
            args = this.getArguments();
            mRecipeSteps = args.getParcelableArrayList(ChefAvenueConsts.RECIPE_STEPS);
            mCurrentStep = args.getInt(ChefAvenueConsts.STEP_ID);

        }
        if (!mIsTab){
            validateButtons();
        }
        bindUI(mCurrentStep);


        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ChefAvenueConsts.STEP_ID, mCurrentStep);
        outState.putParcelableArrayList(ChefAvenueConsts.RECIPE_STEPS, mRecipeSteps);
    }

    public void initializePlayer(String url){
        if (null == mSexoPlayer){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mSexoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            mSMediaPlayer.setPlayer(mSexoPlayer);
        }
        else {
            mSexoPlayer.stop();
        }
        Uri medialUrl = Uri.parse(url);
        MediaSource mediaSource = new ExtractorMediaSource(medialUrl,
                new DefaultDataSourceFactory(getActivity(), "Recipe"),
                new DefaultExtractorsFactory(),
                null, null);
        mSexoPlayer.prepare(mediaSource);
        mSexoPlayer.setPlayWhenReady(true);
    }

    public void bindUI(int setpId){
        RecipeStepModel curStep = mRecipeSteps.get(setpId);
        mStepSDTxtV.setText(curStep.getShortDescription());
        mStepFDTxtV.setText(curStep.getDescription());
        String mediaUrl = curStep.getVideoURL();
        if (null != mediaUrl && mediaUrl.length() > 0 ){
            initializePlayer(mediaUrl);
            mThumbNailImgV.setVisibility(View.GONE);
        }
        else{
            if (null != mSexoPlayer){
                mSexoPlayer.stop();
                mSMediaPlayer.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mSelectionHandler = (IRecipeDetailSelectionHandler) context;
        }
        catch (Exception ee){
            throw  new ClassCastException(context.toString()+ " must implement " +
                    "IRecipeDetailSelectionHandler");
        }
    }

    public void releaseExoPlayer(){
        if (null != mSexoPlayer){
            mSexoPlayer.stop();
            mSexoPlayer.release();
            mSexoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        releaseExoPlayer();
        super.onDestroy();
    }

    public void onBackPressed(){
        releaseExoPlayer();
        mSelectionHandler.closeStepsPage();
    }

    public int getCurrentStepID(){
        return mCurrentStep;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_previous){
            mCurrentStep -= 1;
            bindUI(mCurrentStep);
            validateButtons();

        }
        else if (v.getId() == R.id.btn_next){
            mCurrentStep += 1;
            bindUI(mCurrentStep);
            validateButtons();

        }
    }
    public void validateButtons(){
        if (mCurrentStep <= 0){
            mPreviousStepTxtV.setVisibility(View.GONE);
        }
        else{
            mPreviousStepTxtV.setVisibility(View.VISIBLE);
        }
        if(mCurrentStep == mRecipeSteps.size()-1){
            mNextStepTxtV.setVisibility(View.GONE);
        }
        else{
            mNextStepTxtV.setVisibility(View.VISIBLE);
        }
    }
}
