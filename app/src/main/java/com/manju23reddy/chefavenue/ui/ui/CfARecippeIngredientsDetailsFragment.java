package com.manju23reddy.chefavenue.ui.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manju23reddy.chefavenue.R;
import com.manju23reddy.chefavenue.ui.adapters.RecipeIngredientsListAdapter;
import com.manju23reddy.chefavenue.ui.model.RecipeIngredientModel;
import com.manju23reddy.chefavenue.ui.util.ChefAvenueConsts;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class CfARecippeIngredientsDetailsFragment extends Fragment {

    IRecipeDetailSelectionHandler mSelectionHandler = null;

    RecyclerView mIngredientsRCV = null;

    RecipeIngredientsListAdapter mAdapter = null;

    public CfARecippeIngredientsDetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients_list_layout, container,
                false);

        mIngredientsRCV = rootView.findViewById(R.id.rcv_ingredients);

        initUI(this.getArguments());

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

    public void initUI(Bundle args){

        LinearLayoutManager lytManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        mIngredientsRCV.setLayoutManager(lytManager);
        mIngredientsRCV.setHasFixedSize(true);


        mAdapter = new RecipeIngredientsListAdapter();
        ArrayList<RecipeIngredientModel> model = args.getParcelableArrayList
                (ChefAvenueConsts.RECIPE_INGREDIENTS);
        mAdapter.setIngredients(model);

        mIngredientsRCV.setAdapter(mAdapter);

    }

    public void onBackPressed(){
        mSelectionHandler.closeIngredientsPage();
    }


}
