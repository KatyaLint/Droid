package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
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

    public interface TravelerShowChoose {
        void itemSelected(String guidSelectedItem, String itemType,String guidSelectedUser);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View rootView = inflater.inflate(R.layout.itinerary_main_view, container, false);
        View rootView = inflater.inflate(R.layout.grid_view_table_main_layoutl, container, false);


        FlightGridMainFragment flightGridMainFragment = new FlightGridMainFragment();


        flightGridMainFragment.initializeCB(new TravelerShowChoose() {
            @Override
            public void itemSelected(String guidSelectedItem, String itemType, String guidSelectedUser) {
                NodeTypeEnum.FLIGHT.getType();
                if (itemType.equals( NodeTypeEnum.FLIGHT.getType()) ) {
                    Fragment fragment = new AlternativeFlightFragment();
                    ((AlternativeFlightFragment) fragment).selectedItemGuidNumber(guidSelectedItem);
                    ((AlternativeFlightFragment) fragment).selectedUserGuidNumber(guidSelectedUser);
                    getActivityInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber(),null);

                } else if (itemType.equals( NodeTypeEnum.HOTEL.getType())) {
                    Fragment fragment = new HotelFragment();
                    ((HotelFragment) fragment).selectedItemGuidNumber(guidSelectedItem);
                    ((HotelFragment) fragment).selectedUserGuidNumber(guidSelectedUser);
                    getActivityInterface().goToFragment(ToolBarNavEnum.HOTEL.getNavNumber(),null);

                }
            }
        });


        userOrder = getActivityInterface().getTravelOrder();

        if (userOrder != null) {
           flightGridMainFragment.createGridView(getActivity(), rootView, userOrder, inflater);
        }

        getActivityInterface().getToolBar().updateToolBarView(ToolBarNavEnum.ITINARERY.getNavNumber());
        return rootView;
    }


    @Override
    public void onDestroyView() {
        userOrder = getActivityInterface().getTravelOrder();
        ArrayList<PassengersVO> passangers = userOrder.getPassengerses();
        for(PassengersVO passanger:passangers){
            passanger.clearData();
        }
        super.onDestroyView();
    }
}
