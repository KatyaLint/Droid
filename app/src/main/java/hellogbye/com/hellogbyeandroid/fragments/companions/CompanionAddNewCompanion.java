package hellogbye.com.hellogbyeandroid.fragments.companions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by amirlubashevsky on 31/07/2017.
 */

public class CompanionAddNewCompanion  extends HGBAbstractFragment {
    public static Fragment newInstance(int position) {
        Fragment fragment = new CompanionAddNewCompanion();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.companion_add_new_companion, container, false);

        return rootView;
    }
}
