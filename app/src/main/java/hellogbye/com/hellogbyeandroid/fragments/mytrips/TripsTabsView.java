package hellogbye.com.hellogbyeandroid.fragments.mytrips;

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
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by nyawka on 5/16/16.
 */
public class TripsTabsView extends HGBAbstractFragment {

    private FragmentTabHost mTabHost;
    private static final String TAB_1_TAG = "Upcoming trips";
    private static final String TAB_2_TAG = "Favorites";
    private static final String TAB_3_TAG = "History";
    private Typeface textFont;


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

           // view.setBackgroundResource(R.color.red_pink);
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_trips_tabwidget_view,container, false);

        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG).setIndicator(TAB_2_TAG),
                TabFavoritesView.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG).setIndicator(TAB_1_TAG),
                TabUpcomingTripsView.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_3_TAG).setIndicator(TAB_3_TAG),
                TabHistoryView.class, null);

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
                  //  tv.setTextSize(R.dimen.SP16);
                    tv.setTransformationMethod(null);

                }

                TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                tv.setTypeface(textFont);
               // tv.setTextSize(R.dimen.SP16);
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
