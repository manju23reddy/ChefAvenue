package com.manju23reddy.chefavenue.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class RecipeIngredientModel implements Parcelable {
    private double quantity;
    private String ingredient;
    private String measure;

    @Override
    public int describeContents() {
        return 0;
    }

    public RecipeIngredientModel(final double iQuantity, String iIngredient, String iUnitMeasure ){
        this.quantity = iQuantity;
        this.ingredient = iIngredient;
        this.measure = iUnitMeasure;
    }

    public RecipeIngredientModel(Parcel in){
        this.quantity = in.readDouble();
        this.ingredient = in.readString();
        this.measure = in.readString();
    }

    public String getIngredient(){
        return ingredient;
    }

    public String getMeasure(){
        return measure;
    }

    public double getQuantity(){
        return quantity;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(quantity);
            dest.writeString(ingredient);
            dest.writeString(measure);
    }

    public static final Parcelable.Creator<RecipeIngredientModel> CREATOR = new
            Parcelable.Creator<RecipeIngredientModel>(){
        public RecipeIngredientModel createFromParcel(Parcel in){
            return new RecipeIngredientModel(in);
        }
        public RecipeIngredientModel[] newArray(int size){
            return new RecipeIngredientModel[size];
        }

    };
}
