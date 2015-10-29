package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import hellogbye.com.hellogbyeandroid.R;


import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

public class ItineraryFragment extends HGBAbtsractFragment {


    private UserTravelVO userOrder;
    private UserTravelVO airplaneDataVO;
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

        parseFlight();
        userOrder = getActivityInterface().getTravelOrder();
        if(userOrder != null) {
            Log.d("ItineraryFragment", userOrder.toString());
        }

    }


    //TODO move to correct place
    private void parseFlight(){
        Gson gson = new Gson();
        Type type = new TypeToken<UserTravelVO>(){}.getType();
      //  Type type = new TypeToken<ArrayList<AirplaneDataVO>>(){}.getType();
//        String strJson = HGBUtility.loadJSONFromAsset("maingridfile.txt", getActivity());
        String strJson = HGBUtility.loadJSONFromAsset("maingridfilethree.txt", getActivity());

         airplaneDataVO = gson.fromJson(strJson, type);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.grid_view_table_main_layoutl, container, false);
        FlightGridMainFragment flightGridMainFragment = new FlightGridMainFragment();
      //  View gridView = inflater.inflate(R.layout.grid_view_inner_flight_item, null);

        //View gridTravelName = inflater.inflate(R.layout.grid_view_sticky_header, null);


        rootView = flightGridMainFragment.createGridView(getActivity(),rootView,airplaneDataVO , inflater);


//        TableMainLayout tableMainLayout = new TableMainLayout(getActivity());
//
//        rootView = tableMainLayout;


        View dumbRootView = inflater.inflate(R.layout.dumb_layout, container, false);


        Button btn = (Button)dumbRootView.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show flight
                //on alternative show alternative flight

                Fragment fragment = new AlternativeFlightFragment();
                HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragment, true);


            }
        });


        return rootView;

    }

}
