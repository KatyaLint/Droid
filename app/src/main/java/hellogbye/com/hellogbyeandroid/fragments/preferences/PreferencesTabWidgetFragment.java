package hellogbye.com.hellogbyeandroid.fragments.preferences;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 9/18/16.
 */
public class PreferencesTabWidgetFragment extends HGBAbstractFragment {
    private static final String TAB_1_TAG = "Flight";
    private static final String TAB_2_TAG = "Hotel";
    private FragmentTabHost mTabHost;
    private Typeface textFont;
    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferencesTabWidgetFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFlowInterface().enableFullScreen(false);
    }


    private void firstTextInit(){

        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++) {
            View view = mTabHost.getTabWidget().getChildAt(i);

            view.setBackgroundResource(R.drawable.selector_tabs_indicator);
            TextView tv = (TextView) view.findViewById(android.R.id.title);
            if (i == 0) { //selected
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_WHITE));
            } else { //unselected
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_F5F5F5));
            }
            tv.setTypeface(textFont);
            tv.setTransformationMethod(null);
            // tv.setTextSize(R.dimen.SP10);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tabs_widget, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String titleName =  args.getString(HGBConstants.BUNDLE_SETTINGS_TITLE_NAME);
            FontTextView titleBar = ((MainActivityBottomTabs) getActivity()).getTitleBar();
            titleBar.setText(titleName);
        }

        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG).setIndicator(TAB_1_TAG),
                PreferencesFlightFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG).setIndicator(TAB_2_TAG),
                PreferencesHotelFragment.class, null);

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
                    tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_F5F5F5));
                    tv.setTypeface(textFont);
                    tv.setTransformationMethod(null);

                }

                TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_WHITE));
                tv.setTypeface(textFont);
                tv.setTransformationMethod(null);

            }
        });

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }

}
