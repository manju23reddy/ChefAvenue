package com.manju23reddy.chefavenue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.manju23reddy.chefavenue.ui.data.RecipesDataHolder;
import com.manju23reddy.chefavenue.ui.model.RecipeStepModel;
import com.manju23reddy.chefavenue.ui.model.RecipesModel;
import com.manju23reddy.chefavenue.ui.ui.CfAMainActivity;
import com.manju23reddy.chefavenue.ui.ui.CfARecipeDetailsActivity;
import com.manju23reddy.chefavenue.ui.ui.LauncherScreen;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by MReddy3 on 2/20/2018.
 */


@RunWith(AndroidJUnit4.class)
public class ChefAveVerifyRecipeDetailsTest {
    final String TAG = ChefAveVerifyRecipeDetailsTest.class.getSimpleName();
    @Rule
    public ActivityTestRule<LauncherScreen> launcherScreen = new ActivityTestRule<>
            (LauncherScreen.class);

    ArrayList<RecipesModel> allReceipes = null;

    private MainActivityIdlingResource idleResource;


    @Before
    public void downloadAllRecipes(){
        //LauncherScreen activty = launcherScreen.getActivity();
        //idleResource = new MainActivityIdlingResource(activty);
        //Espresso.registerIdlingResources(idleResource);
        try {
            Thread.sleep(5000);
            allReceipes = RecipesDataHolder.getRecipesDataHolderInstance(
                    launcherScreen.getActivity().getApplicationContext()).getAllRecipes();
        }
        catch (Exception ee){
            Log.e(TAG, ee.getMessage());
        }
    }

    @Test
    public void checkIfAllRecipesAreLoaded(){

        try{

            int i = 0;
            for (RecipesModel curRecipe : allReceipes){
                onView(withId(R.id.rcv_recipes)).perform(RecyclerViewActions.scrollToPosition(i)).
                        check(matches(hasDescendant(withText(curRecipe.getRecipeName()))));
                i++;
            }
        }
        catch (Exception ee){
            Log.e(TAG, ee.getMessage());
        }


    }


    @Test
    public void checkIfSelectedRecipeFromMainActivityIsLaunched(){

        //in Detail screen check if all the steps are matching
        //RecyclerViewMatcher stepsRCVMatcher = new RecyclerViewMatcher(R.id.rcv_recipe_steps_list);
        try {


            onView(withId(R.id.rcv_recipes)).perform(RecyclerViewActions.
                    actionOnItemAtPosition(0, click()));

            int i = 0;
            for (RecipeStepModel curStep : allReceipes.get(0).getSteps()) {
                onView(withId(R.id.rcv_recipe_steps_list)).perform(RecyclerViewActions.
                        scrollToPosition(i)).check(matches(hasDescendant(withText(
                        curStep.getShortDescription()))));
                i++;
            }
        }
        catch (Exception ee){
            Log.e(TAG, ee.getMessage());
        }


    }


    @After
    public void unregisterIdling(){
        //Espresso.unregisterIdlingResources(idleResource);
    }




}
