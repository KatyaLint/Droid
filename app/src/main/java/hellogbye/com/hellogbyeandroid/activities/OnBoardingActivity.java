package hellogbye.com.hellogbyeandroid.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.OnBoardingFragment;
import hellogbye.com.hellogbyeandroid.models.TravelPreference;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.views.CirclePageIndicator;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 9/9/15.
 */
public class OnBoardingActivity extends FragmentActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private ArrayList<TravelPreference> mTravelPrefList;
    private CirclePageIndicator titleIndicator;
    private FontTextView mSetTravelPrefrenceFontTextView;
    private HGBPreferencesManager hgbPrefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_layout);
        mSetTravelPrefrenceFontTextView = (FontTextView) findViewById(R.id.travel_pref_but);
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        final TravelPreference dummyTravelPreference = new TravelPreference(0, "Almost Done! Choose a Travel Profile", "Travel Profile helps us choose" +
                "the right types of flights and hotels that most interest you. You can always change them later", "", "");

        ConnectionManager.getInstance(OnBoardingActivity.this).getTravelProfiles(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
//                Gson gson = new Gson();
//                Type listType = new TypeToken<List<TravelPreference>>() {
//                }.getType();
                mTravelPrefList = (ArrayList<TravelPreference>)data;  //gson.fromJson((String) data, listType);
                // Instantiate a ViewPager and a PagerAdapter.
                mPager = (ViewPager) findViewById(R.id.pager);
                mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                mPager.setAdapter(mPagerAdapter);

                titleIndicator = (CirclePageIndicator) findViewById(R.id.titles);
                titleIndicator.setExtraSpacing(20);
                titleIndicator.setPageColor(Color.argb(63, 255, 255, 255));
                titleIndicator.setStrokeWidth(0);
                titleIndicator.setViewPager(mPager);

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });






        mSetTravelPrefrenceFontTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TravelPreference preference = mTravelPrefList.get(mPager.getCurrentItem());
                final String strPrefrence = String.valueOf(preference.getId());

                ConnectionManager.getInstance(OnBoardingActivity.this).postChoosePrebuiltPreferenceProfileId(strPrefrence, preference.getName(), new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_PREFRENCE_ID,strPrefrence);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getFragmentManager(), (String) data);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            final Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String json = gson.toJson(mTravelPrefList.get(position));
            bundle.putString("travel_prefrence", json);
            final OnBoardingFragment fragment = new OnBoardingFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTravelPrefList.size();
        }
    }
}