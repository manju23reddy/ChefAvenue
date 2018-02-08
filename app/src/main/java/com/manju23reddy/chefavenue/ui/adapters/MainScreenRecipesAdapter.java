package com.manju23reddy.chefavenue.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by MReddy3 on 2/7/2018.
 */

public class MainScreenRecipesAdapter extends RecyclerView.Adapter<MainScreenRecipesAdapter.RecipesViewHolder> {

    public interface OnRecipeClicked {
        void onReceipeClicked(int pos);
    }

    OnRecipeClicked mRecipeClickListener = null;

    ArrayList<RecipesModel> mRecipesList = null;

    private Context mContext = null;


    public class RecipesViewHolder extends RecyclerView.ViewHolder{

        public TextView mRecipeName;

        public TextView mServings;

        public ImageView mRecipeImg;
        public RecipesViewHolder(View itemView) {
            super(itemView);

            mRecipeName = itemView.findViewById(R.id.txtv_title);
            mServings = itemView.findViewById(R.id.txtv_serving);
            mRecipeImg = itemView.findViewById(R.id.img_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecipeClickListener.onReceipeClicked(getAdapterPosition());
                }
            });
        }
    }

    public MainScreenRecipesAdapter(Context context, OnRecipeClicked listener){
        mContext = context;
        mRecipeClickListener = listener;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.receipe_card_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        RecipesModel recipe = mRecipesList.get(position);
        holder.mRecipeName.setText(recipe.getRecipeName());
        holder.mServings.setText(" "+recipe.getServings());
        String imgPath = recipe.getImagePath();
        if (null != imgPath && imgPath.length() > 0){
            Picasso.with(mContext)
                    .load(imgPath)
                    .placeholder(mContext.getDrawable(R.drawable.dinner))
                    .error(mContext.getDrawable(R.drawable.dinner))
                    .into(holder.mRecipeImg);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mRecipesList)
            return 0;

        return mRecipesList.size();
    }

    public void setRecipes(ArrayList<RecipesModel> recipes){
        mRecipesList = recipes;
        notifyDataSetChanged();
    }

    public RecipesModel getRecipeAtPos(int pos){
        if(null == mRecipesList) {
            return null;
        }
        return mRecipesList.get(pos);


    }

    public void clearAdapter(){
        if (null != mRecipesList){
            mRecipesList.clear();
            notifyDataSetChanged();
        }
    }

    public ArrayList<RecipesModel> getAllRecipes(){
        return mRecipesList;
    }
}
