package hellogbye.com.hellogbyeandroid.fragments.preferences;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by nyawka on 9/18/16.
 */
public class PreferencesFlightFragment extends PreferencesTabCommonFragment {

    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferencesFlightFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public PreferencesFlightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Kate PreferencesFlightFragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Kate PreferencesFlightFragment");
        View rootView = createViewForTab(R.layout.settings_drag_list, getContext(), true);
        return rootView;

    }

}
