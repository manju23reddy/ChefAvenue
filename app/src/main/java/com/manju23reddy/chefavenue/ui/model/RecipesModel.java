package com.manju23reddy.chefavenue.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class RecipesModel implements Parcelable{
    private String TAG = RecipesModel.class.getSimpleName();
    private int id;
    private String recipeName;
    private int servings;
    private String imagePath;
    private ArrayList<RecipeIngredientModel> ingredients;
    private ArrayList<RecipeStepModel> steps;

    public RecipesModel(final int rId,
                        final String rName, final int rServings, final String rImagePath,
                        final ArrayList<RecipeIngredientModel> rIngredients,
                        final ArrayList<RecipeStepModel> rSteps){
        this.id = rId;
        this.recipeName = rName;
        this.servings = rServings;
        this.imagePath = rImagePath;
        ingredients = rIngredients;
        steps = rSteps;
    }

    public RecipesModel(Parcel in){
        this.id = in.readInt();
        this.recipeName = in.readString();
        this.servings = in.readInt();
        this.imagePath = in.readString();

        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.ingredients = in.createTypedArrayList(RecipeIngredientModel.CREATOR);
        this.steps = in.createTypedArrayList(RecipeStepModel.CREATOR);
    }

    public void setId(final int rId){
        this.id = rId;
    }
    public void setRecipeName(final String rName){
        this.recipeName = rName;
    }

    public void setServings(final int rServings){
        this.servings = rServings;
    }

    public void setImagePath(final String rImagePath){
        this.imagePath = rImagePath;
    }

    public void setIngredients(final ArrayList<RecipeIngredientModel> rIngredients){
        this.ingredients = rIngredients;
    }

    public void setSteps(final ArrayList<RecipeStepModel> rSteps){
        this.steps = rSteps;
    }

    public String getRecipeName(){
        return this.recipeName;
    }

    public int getServings(){
        return this.servings;
    }

    public String getImagePath(){
        return this.imagePath;
    }

    public int getId(){
        return this.id;
    }

    public ArrayList<RecipeIngredientModel> getIngredients(){
        return this.ingredients;
    }

    public ArrayList<RecipeStepModel> getSteps(){
        return this.steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recipeName);
        dest.writeInt(this.servings);
        dest.writeString(this.imagePath);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(this.steps);
    }

    public static final Parcelable.Creator<RecipesModel> CREATOR = new
        Parcelable.Creator<RecipesModel>(){
            public RecipesModel createFromParcel(Parcel in){
                return new RecipesModel(in);
            }
            public RecipesModel[] newArray(int size){
                return new RecipesModel[size];
            }
    };


}
