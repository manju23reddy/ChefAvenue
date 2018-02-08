package com.manju23reddy.chefavenue.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.model.RecipeStepModel;

import java.util.ArrayList;

/**
 * Created by manju on 2/7/18.
 */

public class RecipeStepsListAdapter extends RecyclerView.Adapter<RecipeStepsListAdapter.StepsListViewHolder> {

    private ArrayList<RecipeStepModel> mStepsList = null;

    OnStepClickedListener mListener = null;

    public interface OnStepClickedListener{
        public void onStepClicked(int pos);
    }

    public RecipeStepsListAdapter(OnStepClickedListener listener){
        mListener = listener;
    }

    @Override
    public StepsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_detail_card_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeStepsListAdapter.StepsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsListViewHolder holder, int position) {
        RecipeStepModel model = mStepsList.get(position);

        holder.mShortDescStepTxtView.setText("Step "+model.getId()+" "+model.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == mStepsList)
            return 0;

        return mStepsList.size();
    }

    public void setSteps(ArrayList<RecipeStepModel> steps){
        this.mStepsList = steps;
        notifyDataSetChanged();
    }

    public void clearSteps(){
        if (null != mStepsList){
            mStepsList.clear();
            notifyDataSetChanged();
        }
    }

    public RecipeStepModel getStep(int pos){
        if (null == mStepsList)
            return null;

        return mStepsList.get(pos);
    }

    public class StepsListViewHolder extends RecyclerView.ViewHolder{
        private TextView mShortDescStepTxtView;

        public StepsListViewHolder(View view){
            super(view);

            mShortDescStepTxtView = view.findViewById(R.id.txtv_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onStepClicked(getAdapterPosition());
                }
            });
        }
    }
}
