package com.manju23reddy.chefavenue.ui.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class CfARecipeDetailsActivity extends AppCompatActivity implements IRecipeDetailSelectionHandler {
    boolean mIsTwoPane = false;
    RecipesModel mSelectedRecipe = null;
    int mSelectedPos = -1;
    android.support.v4.app.Fragment mCurrentFragment = null;
    private enum TopFragment{
        ALL_LIST_FRAGMENT,
        INGREDIENT_LIST_FRAGMENT,
        STEP_FRAGMENT
    };

    TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent inComingIntet = getIntent();
        if (null != inComingIntet){
            Bundle args = inComingIntet.getExtras();
            mSelectedPos = args.getInt(ChefAvenueConsts.RECIPE_ID);
            mSelectedRecipe = RecipesDataHolder.getRecipesDataHolderInstance().
                    getRecipe(mSelectedPos);
            getSupportActionBar().setTitle(mSelectedRecipe.getRecipeName());
        }

        mIsTwoPane = (null != findViewById(R.id.lyt_tablet_view)) ? true : false;

        if (null != savedInstanceState){
            if (savedInstanceState.containsKey(ChefAvenueConsts.RESTORE_LAST_FRAGMENT) &&
                    false == mIsTwoPane){
                int lastFrag = savedInstanceState.getInt(ChefAvenueConsts.RESTORE_LAST_FRAGMENT);
                if (lastFrag == TopFragment.ALL_LIST_FRAGMENT.ordinal()){
                    createListPaneFragment();
                }
                else if (lastFrag == TopFragment.INGREDIENT_LIST_FRAGMENT.ordinal()){
                    createIngredientListFragment();
                }
                else if (lastFrag == TopFragment.STEP_FRAGMENT.ordinal()){
                    //Todo setps ID and details should be saved in bundle in onSavedState
                    //createStepDetailFragment();
                }
            }
            else if (savedInstanceState.containsKey(ChefAvenueConsts.RESTORE_DETAIL_FRAGMENT) &&
                    true == mIsTwoPane){

            }
        }
        else {
            if (mIsTwoPane) {
                createListPaneFragment();
                createIngredientListFragment();


            } else {
                createListPaneFragment();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_to_widget_menu, menu);
        return true;

    }





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mIsTwoPane){
            outState.putInt(ChefAvenueConsts.RESTORE_DETAIL_FRAGMENT, mTopFragment.ordinal());
        }
        else{
            outState.putInt(ChefAvenueConsts.RESTORE_LAST_FRAGMENT, mTopFragment.ordinal());
        }


    }

    public void createListPaneFragment(){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        CfARecipeDetailListFragment recipeListFragment = new CfARecipeDetailListFragment();
        Bundle recipeDetails = new Bundle();
        recipeDetails.putInt(ChefAvenueConsts.RECIPE_ID, mSelectedPos);
        recipeListFragment.setArguments(recipeDetails);

        fragmentManager.beginTransaction().
                    replace(R.id.lyt_recipe_details_list, recipeListFragment).commit();

        if (!mIsTwoPane){
            mCurrentFragment = (android.support.v4.app.Fragment) recipeListFragment;
            mTopFragment = TopFragment.ALL_LIST_FRAGMENT;
        }

    }

    public void createIngredientListFragment(){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        CfARecippeIngredientsDetailsFragment ingredientsDetailsFragment =
                new CfARecippeIngredientsDetailsFragment();
        Bundle ingredientList = new Bundle();
        ingredientList.putInt(ChefAvenueConsts.RECIPE_ID, mSelectedPos);
        ingredientList.putParcelableArrayList(ChefAvenueConsts.RECIPE_INGREDIENTS,
                mSelectedRecipe.getIngredients());
        ingredientsDetailsFragment.setArguments(ingredientList);
        if (mIsTwoPane) {
            fragmentManager.beginTransaction().
                    add(R.id.lyt_recipe_description_view, ingredientsDetailsFragment).commit();
        }
        else{
            fragmentManager.beginTransaction().
                    replace(R.id.lyt_recipe_details_list, ingredientsDetailsFragment).commit();

            mCurrentFragment = (android.support.v4.app.Fragment) ingredientsDetailsFragment;
            mTopFragment = TopFragment.INGREDIENT_LIST_FRAGMENT;

        }
    }

    /**
     * call only after user selects steps item from the lyt_recipe_details_list fragment in twopane
     * mode
     */
    public void createStepDetailFragment(int stepId){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        CfARecipeStepDetailsFragment stepDetailFragment =  new CfARecipeStepDetailsFragment();
        Bundle stepsList = new Bundle();
        stepsList.putInt(ChefAvenueConsts.STEP_ID, stepId);
        stepsList.putParcelableArrayList(ChefAvenueConsts.RECIPE_STEPS, mSelectedRecipe.getSteps());
        stepDetailFragment.setArguments(stepsList);
        if(mIsTwoPane) {
            fragmentManager.beginTransaction().
                    add(R.id.lyt_recipe_description_view, stepDetailFragment).commit();
        }
        else{
            fragmentManager.beginTransaction().
                    replace(R.id.lyt_recipe_details_list, stepDetailFragment).commit();
            mCurrentFragment = (android.support.v4.app.Fragment) stepDetailFragment;
            mTopFragment = TopFragment.STEP_FRAGMENT;
        }
    }


    @Override
    public void showIngredientsPage() {
        createIngredientListFragment();
    }

    @Override
    public void showStepPage(int stepId) {
        createStepDetailFragment(stepId);
    }

    @Override
    public void closeIngredientsPage() {
        if (!mIsTwoPane){
            createListPaneFragment();
        }
    }

    @Override
    public void closeStepsPage() {
        if (!mIsTwoPane){
            createListPaneFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mIsTwoPane) {
            switch (mTopFragment) {
                case STEP_FRAGMENT:
                    CfARecipeStepDetailsFragment stepFrag =
                            (CfARecipeStepDetailsFragment) mCurrentFragment;
                    stepFrag.onBackPressed();
                    break;
                case ALL_LIST_FRAGMENT:
                    super.onBackPressed();
                    break;
                case INGREDIENT_LIST_FRAGMENT:
                    CfARecippeIngredientsDetailsFragment ingredFrag =
                            (CfARecippeIngredientsDetailsFragment) mCurrentFragment;
                    ingredFrag.onBackPressed();
                    break;
                default:
                    super.onBackPressed();
                    break;

            }
        }

    }
}
