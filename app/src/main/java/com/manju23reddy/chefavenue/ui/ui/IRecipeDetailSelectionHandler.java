package com.manju23reddy.chefavenue.ui.ui;

/**
 * Created by MReddy3 on 2/8/2018.
 */

public interface IRecipeDetailSelectionHandler {
    public void showIngredientsPage();
    public void showStepPage(int stepId);
    public void closeIngredientsPage();
    public void closeStepsPage();
}
