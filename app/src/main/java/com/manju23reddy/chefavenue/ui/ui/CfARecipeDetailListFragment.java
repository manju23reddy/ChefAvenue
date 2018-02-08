package com.manju23reddy.chefavenue.ui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.adapters.RecipeStepsListAdapter;
import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class CfARecipeDetailListFragment extends Fragment {


    LinearLayout mIngredientsLyt;

    RecyclerView mStepsRcv;

    RecipeStepsListAdapter mStepsAdapter = null;

    int mSelectedRecipePos = -1;

    public CfARecipeDetailListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail_list, container,
                false);

        mStepsRcv = rootView.findViewById(R.id.rcv_recipe_steps_list);

        initUI(this.getArguments());

        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    public void initUI(Bundle args){

        LinearLayoutManager setpsLyt = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mStepsRcv.setHasFixedSize(true);
        mStepsRcv.setLayoutManager(setpsLyt);

        mStepsAdapter= new RecipeStepsListAdapter(mStepClickListener);
        mStepsRcv.setAdapter(mStepsAdapter);
        if (null != args){
            mSelectedRecipePos = args.getInt(ChefAvenueConsts.RECIPE_ID);
            RecipesModel model = RecipesDataHolder.getRecipesDataHolderInstance().
                    getRecipe(mSelectedRecipePos);
            mStepsAdapter.setSteps(model.getSteps());
        }

    }


    RecipeStepsListAdapter.OnStepClickedListener mStepClickListener = new
            RecipeStepsListAdapter.OnStepClickedListener() {
                @Override
                public void onStepClicked(int pos) {

                }
            };
}
