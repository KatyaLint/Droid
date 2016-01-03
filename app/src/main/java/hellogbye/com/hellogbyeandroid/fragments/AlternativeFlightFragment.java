package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.FlightAdapter;

import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;

/**
 * Created by kate on 8/17/15.
 */
public class AlternativeFlightFragment extends HGBAbtsractFragment {


    private ArrayList<AlternativeFlightsVO> airplaneDataVO;
    private UserTravelMainVO userOrder;
    private Button showAlternativeFlight;
    private NodesVO nodeFromAlternative;
    

    public AlternativeFlightFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new AlternativeFlightFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }



    private void getAlternativeFlights(NodesVO currentNode){

        String solutionID =getActivityInterface().getSolutionID();
        String paxId = currentNode.getAccountID();
        String flightID =   currentNode.getmGuid();

        ConnectionManager.getInstance(getActivity()).getAlternateFlightsForFlight(solutionID, paxId, flightID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                    List<NodesVO> alternativeFlightsVOs = (List<NodesVO>)data;//gson.fromJson((String) data, listType);
                    getActivityInterface().setAlternativeFlights(alternativeFlightsVOs);
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

        boolean isMyFlight = true;
        View rootView = inflater.inflate(R.layout.flight_layout_details, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.flightRecyclerView);


        final NodesVO currentNode;
        List<NodesVO> alternativeFlights = getActivityInterface().getAlternativeFlights();

        if (alternativeFlights != null) {
            currentNode = getNodeFromAlternative(alternativeFlights);
           // primaryGuid = currentNode.getmPrimaryguid();
            isMyFlight = false;
        }else {
            userOrder = getActivityInterface().getTravelOrder();
            currentNode = getLegWithGuid(userOrder);

        }


        String allFlights = "";

        ArrayList<LegsVO> legsFlights = currentNode.getLegs();// legs; //TODO change

        for (int i = 0; i < legsFlights.size(); i++) {
            LegsVO leg = legsFlights.get(i);
            if (leg.getmType().equals("Leg")) {
                if (i == legsFlights.size() - 1) {
                    allFlights = allFlights + leg.getmOrigin() + " - " + leg.getmDestination();
                } else {
                    allFlights = allFlights + leg.getmOrigin() + " - ";
                }
            }
        }


        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter
        final FlightAdapter mAdapter = new FlightAdapter(currentNode,legsFlights);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
      //  recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setFlightCost(currentNode.getCost());
        mAdapter.setDestinationFlights(allFlights);
        mAdapter.updateMyFlight(isMyFlight);
        mAdapter.setAlternativeButtonDisable(false);

        mAdapter.setButtonListener(new AlternativeButtonCB() {
            @Override
            public void showAlternative() {

                getActivityInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_DETAILS.getNavNumber(),null);

            }

            @Override
            public void selectCurrentFlight(String guidSelected) {
                mAdapter.updateMyFlight(true);
                mAdapter.notifyDataSetChanged();
                mAdapter.setAlternativeButtonDisable(true);
                sendServerNewFlightOrder(guidSelected);

            }
        });


        // TODO empty alternative flight after select clicked

        if(isMyFlight){
            getAlternativeFlights(currentNode);
        }

        return rootView;
    }


    private void sendServerNewFlightOrder(String guiSelected) {
        List<NodesVO> alternative = getActivityInterface().getAlternativeFlights();
        String primaryGuid = getPrimaryGuid(guiSelected, alternative);
        ConnectionManager.getInstance(getActivity()).putFlight(getActivityInterface().getTravelOrder().getmSolutionID(),
                getTravellerWitGuid(getActivityInterface().getTravelOrder()).getmPaxguid(),
                primaryGuid, guiSelected,
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        getActivityInterface().setAlternativeFlights(null);
                        getActivityInterface().callRefreshItinerary(ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber());
                        //TODO clean nodeVO, go to iternarary screen

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.show(getFragmentManager(), (String) data);
                    }
                });
    }



    public NodesVO getNodeFromAlternative(List<NodesVO> alternativeFlights) {

        String selectedGuid = getSelectedGuid();
        for(NodesVO nodeVO:alternativeFlights){
            if(selectedGuid.equals(nodeVO.getmGuid())){
                return nodeVO;
            }
        }

        return null;
    }


    public interface AlternativeButtonCB{
        void showAlternative();
        void selectCurrentFlight( String guid);
    }



}
