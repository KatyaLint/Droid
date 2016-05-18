package hellogbye.com.hellogbyeandroid.fragments.mytrips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by nyawka on 5/8/16.
 */
public class TabHistoryView extends TabViewMainFragment {


    public static Fragment newInstance(int position) {
        Fragment fragment = new TabHistoryView();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_trips_tabs_view, container, false);

        initFragmentTabsView(rootView);
        updateTabsView(1,getActivity());
        return rootView;
    }
}
