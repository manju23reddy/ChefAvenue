package com.manju23reddy.chefavenue.ui.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.adapters.MainScreenRecipesAdapter;
import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CfAMainActivity extends AppCompatActivity implements
        android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<RecipesModel>> {

    private static String TAG = CfAMainActivity.class.getSimpleName();
    int CHEF_AVE_LOADER_ID = 2394;

    @BindView(R.id.rcv_recipes)
    RecyclerView mRecipeRCV;

    MainScreenRecipesAdapter mRecipeAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cfa_main);

        ButterKnife.bind(this);

        initUI();

        startLoader();
    }

    public void initUI(){
        boolean isTab = getResources().getBoolean(R.bool.is_tab);
        GridLayoutManager thumbNailLyt = new GridLayoutManager(this,
                    false == isTab ?
                            ChefAvenueConsts.PHONE_GRID_SPAN_COUNT :
                            ChefAvenueConsts.TAB_GRID_SPAN_COUNT
        );


        mRecipeRCV.setLayoutManager(thumbNailLyt);
        mRecipeRCV.setHasFixedSize(true);

        mRecipeAdapter = new MainScreenRecipesAdapter(this, recipeClickListener);
        mRecipeRCV.setAdapter(mRecipeAdapter);

    }

    public void startLoader(){
        android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
        android.support.v4.content.Loader<String> loader = loaderManager.
                getLoader(CHEF_AVE_LOADER_ID);
        if (null == loader) {
            loaderManager.initLoader(CHEF_AVE_LOADER_ID, null, this);
        }
        else {
            loaderManager.restartLoader(CHEF_AVE_LOADER_ID, null, this);
        }
    }

    MainScreenRecipesAdapter.OnRecipeClicked recipeClickListener =
            new MainScreenRecipesAdapter.OnRecipeClicked() {
        @Override
        public void onReceipeClicked(int pos) {
            Intent recipeDetailActivity = new Intent(CfAMainActivity.this,
                    CfARecipeDetailsActivity.class);
            Bundle args = new Bundle();
            args.putInt(ChefAvenueConsts.RECIPE_ID, pos);
            recipeDetailActivity.putExtras(args);
            startActivity(recipeDetailActivity);

        }
    };

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mRecipeAdapter.setRecipes(RecipesDataHolder.getRecipesDataHolderInstance().getAllRecipes());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final int scrollPos =  savedInstanceState.getInt(ChefAvenueConsts.
                        MAIN_RCV_INDEX);
                GridLayoutManager laytManager = (GridLayoutManager)mRecipeRCV.
                        getLayoutManager();
                View v = mRecipeRCV.getChildAt(scrollPos);

                int top = (v == null)? 0 : (v.getTop() -
                        mRecipeRCV.getPaddingTop());

                laytManager.scrollToPositionWithOffset(scrollPos, top);
            }
        }, 1000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        GridLayoutManager laytManager = (GridLayoutManager)mRecipeRCV.
                getLayoutManager();

        int index = laytManager.findFirstCompletelyVisibleItemPosition();


        outState.putInt(ChefAvenueConsts.MAIN_RCV_INDEX,
                index);
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<RecipesModel>> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader<ArrayList<RecipesModel>>(this) {

            @Override
            protected void onStartLoading() {
                ArrayList<RecipesModel> dataRecipes = RecipesDataHolder.
                        getRecipesDataHolderInstance().getAllRecipes();
                if ( null != dataRecipes){
                    deliverResult(dataRecipes);
                }
                else{
                    forceLoad();
                }
            }

            @Override
            public ArrayList<RecipesModel> loadInBackground() {
                try{

                    return RecipesDataHolder.getRecipesDataHolderInstance().getAllRecipes();
                }
                catch (Exception ee){
                    Log.e(TAG, ee.getMessage());
                }
                return null;
            }

            @Override
            public void deliverResult(ArrayList<RecipesModel> data) {
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<RecipesModel>> loader, ArrayList<RecipesModel> data) {
        mRecipeAdapter.setRecipes(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<RecipesModel>> loader) {
        mRecipeAdapter.clearAdapter();
    }



}
