package hellogbye.com.hellogbyeandroid.fragments.mytrips;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;

/**
 * Created by nyawka on 5/16/16.
 */
public class TripsTabsView extends HGBAbstractFragment {

    private FragmentTabHost mTabHost;
    private static final String TAB_1_TAG = "UPCOMING TRIPS";
    private static final String TAB_2_TAG = "FAVORITES";
    private static final String TAB_3_TAG = "HISTORY";
    private Typeface textFont;
    private ImageButton newIteneraryImageButton;



    public static Fragment newInstance(int position) {
        Fragment fragment = new TripsTabsView();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }



    private void firstTextInit(){

        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++) {
            View view = mTabHost.getTabWidget().getChildAt(i);

            view.setBackgroundResource(R.drawable.selector_tabs_indicator);
            TextView tv = (TextView) view.findViewById(android.R.id.title);
            if (i == 0) { //selected
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_WHITE));
            } else { //unselected
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_b6bec9));
            }
            tv.setTypeface(textFont);
            tv.setTransformationMethod(null);
           // tv.setTextSize(R.dimen.SP10);
        }
    }






    private void upComingFirst(){
        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG).setIndicator(TAB_1_TAG),
                TabUpcomingTripsView.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_3_TAG).setIndicator(TAB_3_TAG),
                TabHistoryView.class, null);


        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG).setIndicator(TAB_2_TAG),
                TabFavoritesView.class, null);
    }


    private void historyFirst(){
        mTabHost.addTab(mTabHost.newTabSpec(TAB_3_TAG).setIndicator(TAB_3_TAG),
                TabHistoryView.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG).setIndicator(TAB_1_TAG),
                TabUpcomingTripsView.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG).setIndicator(TAB_2_TAG),
                TabFavoritesView.class, null);
    }


    private void initializeTabs(){
        mTabHost.setCurrentTab(0);

        textFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + "dinnextltpro_medium.otf");

        firstTextInit();
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_b6bec9));
                    tv.setTypeface(textFont);
                    //  tv.setTextSize(R.dimen.SP16);
                    tv.setTransformationMethod(null);

                }

                TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_WHITE));
                tv.setTypeface(textFont);
                // tv.setTextSize(R.dimen.SP16);
                tv.setTransformationMethod(null);

            }
        });




    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFlowInterface().enableFullScreen(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_trips_tabwidget_view,container, false);

        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        ArrayList<MyTripItem> upComingTrips = getActivityInterface().getUpComingTrips();
        if(upComingTrips != null && !upComingTrips.isEmpty()){
            upComingFirst();
        }else{
            historyFirst();
        }


        initializeTabs();

        //  getUpComingTrips();


        newIteneraryImageButton =  ((MainActivityBottomTabs)getActivity()).getNewIternararyButton();
        setNewIteneraryImageButtonClickListener();

        return rootView;
    }




    private void setNewIteneraryImageButtonClickListener(){
        newIteneraryImageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
            getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), args);
        }
    });
    }


/*    private void clearCNCItems() {

       *//* getActivityInterface().setCNCItems(null);
        getActivityInterface().setTravelOrder(null);
        HGBPreferencesManager sharedPreferences = HGBPreferencesManager.getInstance(getContext());
        sharedPreferences.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        sharedPreferences.removeKey(HGBPreferencesManager.HGB_LAST_TRAVEL_VO);*//*
        Bundle args = new Bundle();
        args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
        getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), args);
      //  selectItem(ToolBarNavEnum.CNC.getNavNumber(), null, true);
    }*/



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }



}
