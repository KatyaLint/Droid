package hellogbye.com.hellogbyeandroid.fragments.alternative;

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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionsTravelers;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;


/**
 * Created by nyawka on 4/20/16.
 */
public class AlternativeFlightTabsWidgetFragment extends HGBAbstractFragment {

    private FragmentTabHost mTabHost;
    private static final String TAB_1_TAG = "OUTBOUND";
    private static final String TAB_2_TAG = "INBOUND";
    private Typeface textFont;

    public static Fragment newInstance(int position) {
        Fragment fragment = new AlternativeFlightTabsWidgetFragment();
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
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
            } else { //unselected
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_c3cad3));
            }
            tv.setTypeface(textFont);
            tv.setTransformationMethod(null);
            // tv.setTextSize(R.dimen.SP10);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView;
            rootView = inflater.inflate(R.layout.tabs_widget, container, false);
            mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
            createTabsView();


        return rootView;
    }


    private void createTabsView(){


        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG).setIndicator(TAB_1_TAG),
                AlternativeFlightOutbound.class, getArguments());
        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG).setIndicator(TAB_2_TAG),
                AlternativeFlightInbound.class, getArguments());


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
                    tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.COLOR_c3cad3));
                    tv.setTypeface(textFont);
                    tv.setTransformationMethod(null);

                }

                TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                tv.setTypeface(textFont);
                tv.setTransformationMethod(null);

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }
}
