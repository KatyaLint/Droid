package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 9/16/15.
 */
public class TravelPrefrenceStartingActivity extends Activity {


    private FontTextView mButtonTextView;
    private FontTextView mSkip;
    private HGBPreferencesManager hgbPrefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO remove layout
     //   setContentView(R.layout.onboarding_starting_layout);
        setContentView(R.layout.onboarding_layout);
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.TRAVEL_PREF_ENTRY,true);


        mButtonTextView = (FontTextView)findViewById(R.id.travel_pref_but);
//        mSkip = (FontTextView)findViewById(R.id.skip);

//        mButtonTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),OnBoardingActivity.class);
//                startActivity(intent);
//            }
//        });

        mButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivityBottomTabs.class);
                startActivity(intent);
            }
        });
    }
}
