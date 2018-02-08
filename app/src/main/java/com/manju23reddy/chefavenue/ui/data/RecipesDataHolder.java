package com.manju23reddy.chefavenue.ui.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.manju23reddy.chefavenue.ui.model.RecipeIngredientModel;
import com.manju23reddy.chefavenue.ui.model.RecipeStepModel;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public final class RecipesDataHolder {

    private final String TAG = RecipesDataHolder.class.getSimpleName();
    private ArrayList<RecipesModel> allRecipes = null;

    private static class RecipesInstanceCreator{
        private static RecipesDataHolder INSTANCE = new RecipesDataHolder();
    }

    private RecipesDataHolder(){

    }

    public static RecipesDataHolder getRecipesDataHolderInstance(){
        return RecipesInstanceCreator.INSTANCE;
    }


    public void parseRecipesFromJSON(JSONArray allRecipesJSON){
        if (null != allRecipesJSON){
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            try{
                if (null == allRecipes){
                    allRecipes = new ArrayList<RecipesModel>();
                }
                else{
                    allRecipes.clear();
                }
                for(int i = 0; i < allRecipesJSON.length(); i++){
                    JSONObject recipe = allRecipesJSON.getJSONObject(i);

                    //get Ingredients from recipe

                    JSONArray ingredients = recipe.
                            getJSONArray(ChefAvenueConsts.RECIPE_INGREDIENTS);
                    ArrayList<RecipeIngredientModel> ingredientsList = new ArrayList<>();

                    for(int j = 0; j < ingredients.length(); j++){
                        JSONObject curIngredient = ingredients.getJSONObject(j);
                        /*
                        RecipeIngredientModel ingrModel = new RecipeIngredientModel(
                                curIngredient.getInt(ChefAvenueConsts.INGREDIENTS_QUANTITY),
                                curIngredient.getString(ChefAvenueConsts.INGREDIENTS_INGREDIENT),
                                curIngredient.getString(ChefAvenueConsts.INGREDIENTS_QUANTITY));*/

                        RecipeIngredientModel ingrModel =  gson.fromJson(curIngredient.toString(),
                                RecipeIngredientModel.class);
                        ingredientsList.add(ingrModel);
                    }

                    //get Steps from recipe
                    JSONArray steps = recipe.getJSONArray(ChefAvenueConsts.RECIPE_STEPS);
                    ArrayList<RecipeStepModel> stepsList = new ArrayList<>();
                    for (int k = 0; k < steps.length(); k++){
                        JSONObject curStep = steps.getJSONObject(k);
                        /*RecipeStepModel stepModel = new RecipeStepModel(
                                curStep.getInt(ChefAvenueConsts.STEP_ID),
                                curStep.getString(ChefAvenueConsts.STEP_SHORT_DESCRIPTION),
                                curStep.getString(ChefAvenueConsts.STEP_DESCRIPTION),
                                curStep.getString(ChefAvenueConsts.STEP_VIDEO_URL),
                                curStep.getString(ChefAvenueConsts.STEP_THUMBNAIL_URL));*/

                        RecipeStepModel stepModel = gson.fromJson(curStep.toString(),
                                RecipeStepModel.class);
                        stepsList.add(stepModel);
                    }

                    RecipesModel recipeModel = new RecipesModel(
                            recipe.getInt(ChefAvenueConsts.RECIPE_ID),
                            recipe.getString(ChefAvenueConsts.RECIPE_NAME),
                            recipe.getInt(ChefAvenueConsts.RECIPE_SERVINGS),
                            recipe.getString(ChefAvenueConsts.RECIPE_IMAGE),
                            ingredientsList,
                            stepsList
                    );



                    allRecipes.add(recipeModel);

                }
            }
            catch (Exception ee){
                Log.e(TAG, ee.getMessage());
            }
        }
    }

    public ArrayList<RecipesModel> getAllRecipes(){
        return allRecipes;
    }
}
