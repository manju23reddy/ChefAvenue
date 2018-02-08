package com.manju23reddy.chefavenue.ui.ui;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.ApplicationClass;
import com.manju23reddy.chefavenue.ui.adapters.MainScreenRecipesAdapter;
import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;
import com.manju23reddy.chefavenue.ui.model.RecipeStepModel;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CfAMainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<RecipesModel>> {

    private static String TAG = CfAMainActivity.class.getSimpleName();

    @BindView(R.id.rcv_recipes)
    RecyclerView mRecipeRCV;

    MainScreenRecipesAdapter mRecipeAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cfa_main);

        ButterKnife.bind(this);

        initUI();
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


        new JSONFileReader(this).execute(getApplicationContext());

    }

    MainScreenRecipesAdapter.OnRecipeClicked recipeClickListener =
            new MainScreenRecipesAdapter.OnRecipeClicked() {
        @Override
        public void onReceipeClicked(int pos) {

            RecipesModel selectedRecipe = mRecipeAdapter.getRecipeAtPos(pos);
            Intent recipeDetailActivity = new Intent(CfAMainActivity.this,
                    CfARecipeDetailsActivity.class);
            recipeDetailActivity.putExtra(ChefAvenueConsts.SELECTED_RECIPE, selectedRecipe);
            startActivity(recipeDetailActivity);

        }
    };

    @Override
    public Loader<ArrayList<RecipesModel>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<RecipesModel>>(this) {
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
                    String recipesJSON = null;
                    InputStream is = getAssets().open("baking.json");
                    int size = is.available();
                    byte[] bufffer = new byte[size];
                    is.read(bufffer);
                    is.close();
                    recipesJSON = new String(bufffer, "UTF-8");
                    JSONArray allReceipes = new JSONArray(recipesJSON);
                    RecipesDataHolder holder = RecipesDataHolder.getRecipesDataHolderInstance();
                    holder.parseRecipesFromJSON(allReceipes);

                    //Todo just for debug remove later
                    Log.d(TAG, RecipesDataHolder.getRecipesDataHolderInstance().getAllRecipes().
                            toString());
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
    public void onLoadFinished(Loader<ArrayList<RecipesModel>> loader, ArrayList<RecipesModel> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<RecipesModel>> loader) {
        mRecipeAdapter.
    }

    private static class JSONFileReader extends AsyncTask<Context, Void, Void> {
        CfAMainActivity instance;
        public JSONFileReader(CfAMainActivity inst){
            instance = inst;
        }
        @Override
        protected Void doInBackground(Context... contexts) {
            Context context = contexts[0];
            try{
                String recipesJSON = null;
                InputStream is = context.getAssets().open("baking.json");
                int size = is.available();
                byte[] bufffer = new byte[size];
                is.read(bufffer);
                is.close();
                recipesJSON = new String(bufffer, "UTF-8");
                JSONArray allReceipes = new JSONArray(recipesJSON);
                RecipesDataHolder holder = RecipesDataHolder.getRecipesDataHolderInstance();
                holder.parseRecipesFromJSON(allReceipes);

                //Todo just for debug remove later
                Log.d(TAG, RecipesDataHolder.getRecipesDataHolderInstance().getAllRecipes().
                        toString());
            }
            catch (Exception ee){
                Log.e(TAG, ee.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            instance.mRecipeAdapter.setRecipes(RecipesDataHolder.getRecipesDataHolderInstance().getAllRecipes());
        }
    }
}
