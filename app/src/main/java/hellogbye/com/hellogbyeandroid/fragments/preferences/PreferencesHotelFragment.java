package hellogbye.com.hellogbyeandroid.fragments.preferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by nyawka on 9/18/16.
 */
public class PreferencesHotelFragment extends PreferencesTabCommonFragment{


    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferencesHotelFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public PreferencesHotelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Kate PreferencesHotelFragment");
        View rootView = createViewForTab(R.layout.settings_tabs_hotel_list, getContext(), false);
        return rootView;
    }
}
