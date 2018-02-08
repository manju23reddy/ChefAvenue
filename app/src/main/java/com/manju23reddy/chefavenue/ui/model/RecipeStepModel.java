package com.manju23reddy.chefavenue.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class RecipeStepModel implements Parcelable {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public RecipeStepModel(final int sId, final String sSD, final String sDesc, final String sVUrl,
                        final String sTUrl){
        id = sId;
        shortDescription = sSD;
        description = sDesc;
        videoURL = sVUrl;
        thumbnailURL = sTUrl;
    }

    public RecipeStepModel(Parcel in){
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public int getId(){
        return id;
    }

    public String getShortDescription(){
        return shortDescription;
    }

    public String getDescription(){
        return description;
    }

    public String getVideoURL(){
        return videoURL;
    }

    public String getThumbnailURL(){
        return thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    public static final Parcelable.Creator<RecipeStepModel> CREATOR = new
            Parcelable.Creator<RecipeStepModel>(){
            public RecipeStepModel createFromParcel(Parcel in){
                return new RecipeStepModel(in);
            }
            public RecipeStepModel[] newArray(int size){
                return new RecipeStepModel[size];
            }

    };
}
