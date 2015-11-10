package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

public class ItineraryFragment extends HGBAbtsractFragment {


    private UserTravelVO userOrder;

    // private UserTravelVO airplaneDataVO;
    public ItineraryFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new ItineraryFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // parseFlight();

        userOrder = getActivityInterface().getTravelOrder();
        if (userOrder != null) {
            Log.d("ItineraryFragment", userOrder.toString());
        }

    }


    public interface TravelerShowChoose {
        void itemSelected(String guidSelectedItem, String itemType);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.grid_view_table_main_layoutl, container, false);
        FlightGridMainFragment flightGridMainFragment = new FlightGridMainFragment();
        flightGridMainFragment.initializeCB(new TravelerShowChoose() {
            @Override
            public void itemSelected(String guidSelectedItem, String itemType) {
                if (itemType.equals("flight")) {
                    Fragment fragment = new AlternativeFlightFragment();
                    ((AlternativeFlightFragment) fragment).selectedItem(guidSelectedItem);
                    // HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragment, true);
                    getActivityInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber());
                } else if (itemType.equals("hotel")) {
                    Fragment fragment = new HotelFragment();
                    ((HotelFragment) fragment).selectedItem(guidSelectedItem);
                    getActivityInterface().goToFragment(ToolBarNavEnum.HOTEL.getNavNumber());
                }
            }
        });

        if (userOrder != null) {
            rootView = flightGridMainFragment.createGridView(getActivity(), rootView, userOrder, inflater);
        }
        getActivityInterface().getToolBar().updateToolBarView(ToolBarNavEnum.ITINARERY.getNavNumber());
        return rootView;

    }

}
