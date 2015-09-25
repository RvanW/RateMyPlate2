package com.vanw.robbert.ratemyplate2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnItemClickListener {

    public static Context contextOfApplication;

    public void onItemSelected(Plate plate) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        DisplayActivityFragment displayFragment = (DisplayActivityFragment)
                getSupportFragmentManager().findFragmentById(R.id.displayfragment);
        final LinearLayout display_fragment_container = (LinearLayout) this.findViewById(R.id.fragment_container2);

        if (displayFragment != null) {
            // If display fragment is available, we're already in two-pane layout...
            // Call a method in the displayFragment to update its content
            displayFragment.updateDisplayView(plate);
        }
        else {
            // Otherwise, we must add or replace a fragment...

            // Create fragment and give it an argument for the selected article
            DisplayActivityFragment newFragment = new DisplayActivityFragment();
            Bundle args = new Bundle();
            args.putSerializable(DisplayActivityFragment.ARG_PLATE, plate);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Check container exists (only in landscape)
            // Replace whatever is in the fragment_container view with this fragment,
            if(display_fragment_container != null) {
                // If the container is not visible we have to animate it in to view
                if(display_fragment_container.getVisibility()==View.GONE) {
                    display_fragment_container.setVisibility(View.VISIBLE);
                    display_fragment_container.setAlpha(0.0f);
                    display_fragment_container.animate()
                            .translationX(-display_fragment_container.getWidth())
                            .alpha(1.0f);
                }
                transaction.replace(R.id.fragment_container2, newFragment);
            } else { // otherwise replace the current main list fragment
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.replace(R.id.fragment_container, newFragment);
            }
            // and add the transaction to the back stack so the user can navigate back
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get context for later
        contextOfApplication = getApplicationContext();

        // Initially hide the second fragment container
        if (findViewById(R.id.fragment_container2) != null) {
            LinearLayout display_fragment_container = (LinearLayout) this.findViewById(R.id.fragment_container2);
            display_fragment_container.setVisibility(View.GONE);
        }

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MainActivityFragment firstFragment = new MainActivityFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

}
