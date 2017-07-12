package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.TitleNameChange;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by nyawka on 7/10/17.
 */

public class ItineraryFragmentNoScrollingAdapter extends HGBAbstractFragment implements TitleNameChange {

    private FragmentActivity activity;

    public static Fragment newInstance(int position) {
        Fragment fragment = new ItineraryFragmentNoScrollingAdapter();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void titleChangeName() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getFlowInterface().enableFullScreen(true);
        int cellHieght = (int) getResources().getDimension(R.dimen.DP150);
        activity = getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
