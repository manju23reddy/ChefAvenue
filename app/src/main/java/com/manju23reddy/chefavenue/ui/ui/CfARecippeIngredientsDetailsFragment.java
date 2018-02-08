package com.manju23reddy.chefavenue.ui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manju23reddy.chefavenue.R;

import butterknife.ButterKnife;

/**
 * Created by MReddy3 on 2/7/2018.
 */

public class CfARecippeIngredientsDetailsFragment extends Fragment {

    public CfARecippeIngredientsDetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients_list_layout, container,
                false);

        ButterKnife.bind(rootView);

        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }
}
