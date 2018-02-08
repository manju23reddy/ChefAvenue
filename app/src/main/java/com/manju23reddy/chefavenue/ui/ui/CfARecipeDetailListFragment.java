package com.manju23reddy.chefavenue.ui.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.adapters.RecipeStepsListAdapter;
import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;
import com.manju23reddy.chefavenue.ui.model.RecipeStepModel;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class CfARecipeDetailListFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = CfARecipeDetailListFragment.class.getSimpleName();

    LinearLayout mIngredientsLyt;

    RecyclerView mStepsRcv;

    RecipeStepsListAdapter mStepsAdapter = null;

    int mSelectedRecipePos = -1;

    IRecipeDetailSelectionHandler mSelectionHandler = null;

    public CfARecipeDetailListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail_list, container,
                false);


        mStepsRcv = rootView.findViewById(R.id.rcv_recipe_steps_list);
        mIngredientsLyt = rootView.findViewById(R.id.lyt_ingredients);

        Bundle inData;
        if (null != savedInstanceState){
            inData = savedInstanceState;
        }
        else{
            inData = this.getArguments();
        }
        initUI(inData);

        mIngredientsLyt.setOnClickListener(this);

        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mSelectionHandler = (IRecipeDetailSelectionHandler) context;
        }
        catch (Exception ee){
            throw  new ClassCastException(context.toString()+ " must implement " +
                    "IRecipeDetailSelectionHandler");
        }
    }

    public void initUI(final Bundle args){

        LinearLayoutManager setpsLyt = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mStepsRcv.setHasFixedSize(true);
        mStepsRcv.setLayoutManager(setpsLyt);

        mStepsAdapter= new RecipeStepsListAdapter(mStepClickListener);
        mStepsRcv.setAdapter(mStepsAdapter);
        if (null != args){
            mSelectedRecipePos = args.getInt(ChefAvenueConsts.RECIPE_ID);

            if (args.containsKey(ChefAvenueConsts.RECIPE_STEPS)){
                ArrayList<RecipeStepModel> steps = args.
                        getParcelableArrayList(ChefAvenueConsts.RECIPE_STEPS);
                mStepsAdapter.setSteps(steps);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final int scrollPos =  args.getInt(ChefAvenueConsts.
                                STEPS_RCV_INDEX);
                        LinearLayoutManager laytManager = (LinearLayoutManager) mStepsRcv.
                                getLayoutManager();
                        View v = mStepsRcv.getChildAt(scrollPos);

                        int top = (v == null)? 0 : (v.getTop() -
                                mStepsRcv.getPaddingTop());

                        laytManager.scrollToPositionWithOffset(scrollPos, top);
                    }
                }, 1000);
            }
            else {
                RecipesModel model = RecipesDataHolder.getRecipesDataHolderInstance().
                        getRecipe(mSelectedRecipePos);
                mStepsAdapter.setSteps(model.getSteps());
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ChefAvenueConsts.RECIPE_ID, mSelectedRecipePos);
        outState.putParcelableArrayList(ChefAvenueConsts.RECIPE_STEPS, mStepsAdapter.getAllSteps());

        LinearLayoutManager laytManager = (LinearLayoutManager) mStepsRcv.
                getLayoutManager();

        int index = laytManager.findFirstCompletelyVisibleItemPosition();

        outState.putInt(ChefAvenueConsts.STEPS_RCV_INDEX,
                index);
    }




    RecipeStepsListAdapter.OnStepClickedListener mStepClickListener = new
            RecipeStepsListAdapter.OnStepClickedListener() {
                @Override
                public void onStepClicked(int pos) {
                    mSelectionHandler.showStepPage(pos);
                }
            };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lyt_ingredients){
            //call back to show Ingredients Screen
            mSelectionHandler.showIngredientsPage();
        }
    }


}
