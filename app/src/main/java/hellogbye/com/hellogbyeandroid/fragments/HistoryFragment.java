package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.AlternativeFlightsAdapter;
import hellogbye.com.hellogbyeandroid.adapters.FlightAdapter;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

/**
 * Created by arisprung on 8/17/15.
 */
public class HistoryFragment extends Fragment {


    private ArrayList<AlternativeFlightsVO> airplaneDataVO;

    public HistoryFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_history_layout, container, false);
        View rootView = inflater.inflate(R.layout.flight_layout_details, container, false);

//
//        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.flightRecyclerView);
//
//        ArrayList<AlternativeFlightsVO> alternativeFlights = parseFlight();
//        ArrayList<LegsVO> legsFlights = alternativeFlights.get(0).getLegs(); //TODO change
//        AlternativeFlightsVO currentFlightToShow = alternativeFlights.get(0);
//        String allFlights = "";
//
//        for (int i = 0; i < legsFlights.size(); i++) {
//            LegsVO leg = legsFlights.get(i);
//            if(leg.getmType().equals("Leg")) {
//                if (i == legsFlights.size() - 1) {
//                    allFlights = allFlights + leg.getmOrigin() + " - " +leg.getmDestination();
//                }else {
//                    allFlights = allFlights + leg.getmOrigin() + " - ";
//                }
//            }
//        }
//
//
//        // 2. set layoutManger
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        // 3. create an adapter
//        FlightAdapter mAdapter = new FlightAdapter(getActivity(),legsFlights);
//        // 4. set adapter
//        recyclerView.setAdapter(mAdapter);
//        // 5. set item animator to DefaultAnimator
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        mAdapter.setFlightCost(currentFlightToShow.getCost());
//        mAdapter.setDestinationFlights(allFlights);


        return rootView;
    }




    private ArrayList<AlternativeFlightsVO> parseFlight(){
        Gson gson = new Gson();

        //        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<FlightsVO>>(){}.getType();
//        String strJson = loadJSONFromAsset();
//        ArrayList<FlightsVO> airplaneDataVO = gson.fromJson(strJson, type);


        Type type = new TypeToken<ArrayList<AlternativeFlightsVO>>(){}.getType();
        //  Type type = new TypeToken<ArrayList<AirplaneDataVO>>(){}.getType();
        String strJson = HGBUtility.loadJSONFromAsset("flightdetails.txt", getActivity());

        airplaneDataVO = gson.fromJson(strJson, type);
        return airplaneDataVO;
    }



}
