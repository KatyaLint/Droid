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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.FlightAdapter;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

/**
 * Created by kate on 8/17/15.
 */
public class AlternativeFlightFragment extends HGBAbtsractFragment {


    private ArrayList<AlternativeFlightsVO> airplaneDataVO;
    private UserTravelVO userOrder;
    private Button showAlternativeFlight;

    public AlternativeFlightFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new AlternativeFlightFragment();
//        Bundle args = new Bundle();
//        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Kate onCreate");
        userOrder = getActivityInterface().getTravelOrder();
        conectionRequest();
    }

    private void conectionRequest(){

        String solutionID = userOrder.getmSolutionID();
        ArrayList<PassengersVO> passengers = userOrder.getPassengerses();
        String paxId = passengers.get(0).getmPaxguid();
        ArrayList<CellsVO> cells = passengers.get(0).getmCells();
        ArrayList<NodesVO> node = passengers.get(0).getmCells().get(0).getmNodes();
        ArrayList<LegsVO> leg = node.get(0).getLegs();
        String flightID =   leg.get(0).getmParentguid(); //paxId;

        ConnectionManager.getInstance(getActivity()).getAlternateFlightsForFlight(solutionID, paxId, flightID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {


                    Type listType = new TypeToken<List<AlternativeFlightsVO>>() {
                    }.getType();

                    Gson gson = new Gson();

                    List<AlternativeFlightsVO> alternativeFlightsVOs = gson.fromJson((String) data, listType);
                    getActivityInterface().setAlternativeFlights(alternativeFlightsVOs);
                }

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Kate onCreateVIew");
        //View rootView = inflater.inflate(R.layout.fragment_history_layout, container, false);
        View rootView = inflater.inflate(R.layout.flight_layout_details, container, false);


        UserTravelVO travelOrder = getActivityInterface().getTravelOrder();
        ArrayList<PassengersVO> passengers = travelOrder.getPassengerses();
        ArrayList<CellsVO> cells = passengers.get(0).getmCells();
        ArrayList<NodesVO> nodes = cells.get(0).getmNodes();
        ArrayList<LegsVO> legs = nodes.get(0).getLegs();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.flightRecyclerView);

       // ArrayList<AlternativeFlightsVO> alternativeFlights = parseFlight();
//        ArrayList<LegsVO> legsFlights = alternativeFlights.get(0).getLegs(); //TODO change
//        AlternativeFlightsVO currentFlightToShow = alternativeFlights.get(0);
        ArrayList<LegsVO> legsFlights = legs; //TODO change
        NodesVO currentFlightToShow = cells.get(0).getmNodes().get(0);
        String allFlights = "";

        for (int i = 0; i < legsFlights.size(); i++) {
            LegsVO leg = legsFlights.get(i);
            if(leg.getmType().equals("Leg")) {
                if (i == legsFlights.size() - 1) {
                    allFlights = allFlights + leg.getmOrigin() + " - " +leg.getmDestination();
                }else {
                    allFlights = allFlights + leg.getmOrigin() + " - ";
                }
            }
        }


        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter
        FlightAdapter mAdapter = new FlightAdapter(getActivity(),legsFlights);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setFlightCost(currentFlightToShow.getCost());
        mAdapter.setDestinationFlights(allFlights);
        mAdapter.setButtonListener(new AlternativeButtonCB() {
            @Override
            public void showAlternative() {
                AlternativeFlightsDetailsFragment fragemnt = new AlternativeFlightsDetailsFragment();
                HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragemnt, true); //now we always want to add to the backstack
            }
        });


        return rootView;
    }



    public interface AlternativeButtonCB{
        void showAlternative();
    }

}
