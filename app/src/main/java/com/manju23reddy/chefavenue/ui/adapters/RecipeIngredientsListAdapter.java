package com.manju23reddy.chefavenue.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.model.RecipeIngredientModel;

import java.util.ArrayList;

/**
 * Created by manju on 2/7/18.
 */

public class RecipeIngredientsListAdapter extends RecyclerView.Adapter
        <RecipeIngredientsListAdapter.IngredientsListViewHolder> {

    private ArrayList<RecipeIngredientModel> mIngredientsList = null;





    public RecipeIngredientsListAdapter(){

    }

    @Override
    public IngredientsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ingredients_items_lyt;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeIngredientsListAdapter.IngredientsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsListViewHolder holder, int position) {
        RecipeIngredientModel model = mIngredientsList.get(position);

        holder.mIngredientNameTxtV.setText(model.getIngredient());
        holder.mQuantityTxtV.setText(model.getQuantity()+" "+model.getMeasure());
    }

    @Override
    public int getItemCount() {
        if (null == mIngredientsList)
            return 0;

        return mIngredientsList.size();
    }

    public void setIngredients(ArrayList<RecipeIngredientModel> steps){
        this.mIngredientsList = steps;
        notifyDataSetChanged();
    }


    public ArrayList<RecipeIngredientModel> getAllIngredients(){
        return mIngredientsList;
    }

    public class IngredientsListViewHolder extends RecyclerView.ViewHolder{
        private TextView mIngredientNameTxtV;
        private TextView mQuantityTxtV;

        public IngredientsListViewHolder(View view){
            super(view);

            mIngredientNameTxtV = view.findViewById(R.id.txtv_ingredient_name);
            mQuantityTxtV = view.findViewById(R.id.txtv_quantity);

        }
    }
}
