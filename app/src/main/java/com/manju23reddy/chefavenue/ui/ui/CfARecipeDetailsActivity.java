package com.manju23reddy.chefavenue.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class CfARecipeDetailsActivity extends AppCompatActivity {
    boolean mIsTwoPane = false;
    RecipesModel mSelectedRecipe = null;
    int mSelectedPos = -1;
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

        if (null != findViewById(R.id.lyt_tablet_view)){
            mIsTwoPane = true;
            createListPaneFragment();
            createIngredientListFragment();

        }
        else{
            mIsTwoPane = false;
            createListPaneFragment();
        }



    }

    public void createListPaneFragment(){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        CfARecipeDetailListFragment recipeListFragment = new CfARecipeDetailListFragment();
        Bundle recipeDetails = new Bundle();
        recipeDetails.putInt(ChefAvenueConsts.RECIPE_ID, mSelectedPos);
        recipeListFragment.setArguments(recipeDetails);
        fragmentManager.beginTransaction().
                add(R.id.lyt_recipe_details_list, recipeListFragment).commit();
    }

    public void createIngredientListFragment(){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        CfARecippeIngredientsDetailsFragment ingredientsDetailsFragment =
                new CfARecippeIngredientsDetailsFragment();
        Bundle ingredientList = new Bundle();
        ingredientList.putInt(ChefAvenueConsts.RECIPE_ID, mSelectedPos);
        ingredientsDetailsFragment.setArguments(ingredientList);
        fragmentManager.beginTransaction().
                add(R.id.lyt_recipe_details_list, ingredientsDetailsFragment).commit();
    }

    /**
     * call only after user selects steps item from the lyt_recipe_details_list fragment in twopane
     * mode
     */
    public void createStepDetailFragment(){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        CfARecipeStepDetailsFragment stepDetailFragment =  new CfARecipeStepDetailsFragment();
        Bundle stepsList = new Bundle();
        stepsList.putInt(ChefAvenueConsts.RECIPE_ID, mSelectedPos);
        stepDetailFragment.setArguments(stepsList);
        fragmentManager.beginTransaction().
                add(R.id.lyt_recipe_description_view, stepDetailFragment).commit();
    }
}
