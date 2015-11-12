package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
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

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.FlightAdapter;

import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;

/**
 * Created by kate on 8/17/15.
 */
public class AlternativeFlightFragment extends HGBAbtsractFragment {


    private ArrayList<AlternativeFlightsVO> airplaneDataVO;
    private UserTravelVO userOrder;
    private Button showAlternativeFlight;
    private NodesVO nodeFromAlternative;
    

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



    private void conectionRequest(NodesVO currentNode){



        String solutionID =getActivityInterface().getSolutionID();
      //  ArrayList<PassengersVO> passengers = userOrder.getPassengerses();
        String paxId = currentNode.getAccountID();//passengers.get(0).getmPaxguid();
    //    ArrayList<CellsVO> cells = passengers.get(0).getmCells();
    //    ArrayList<NodesVO> node = passengers.get(0).getmCells().get(0).getmNodes();
       // ArrayList<LegsVO> leg = node.get(0).getLegs();

        String flightID =   currentNode.getmGuid(); //leg.get(0).getmParentguid(); //paxId;

        ConnectionManager.getInstance(getActivity()).getAlternateFlightsForFlight(solutionID, paxId, flightID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    Type listType = new TypeToken<List<NodesVO>>() {
                    }.getType();

                    Gson gson = new Gson();

                    List<NodesVO> alternativeFlightsVOs = gson.fromJson((String) data, listType);
                    getActivityInterface().setAlternativeFlights(alternativeFlightsVOs);
                }

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });
    }


//    private onBackPressEvent(){
//        ((MainActivity) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
//            public void doBack() {
//                if (mRecyclerView.getVisibility() != View.VISIBLE) {
//                    if (getActivity() != null && getActivity().getFragmentManager() != null) {
//                        FragmentManager fm = getActivity().getFragmentManager();
//                        fm.popBackStack(HotelFragment.class.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    }
//                } else {
//                    hideAlternativeHotels();
//                }
//
//            }
//        });
//    }

   // private static NodesVO userFirstChoice;
    private static String primaryGuid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.flight_layout_details, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.flightRecyclerView);
        recyclerView.removeAllViews();
        recyclerView.removeAllViewsInLayout();
        boolean isMyFlight = true;

        final NodesVO currentNode;
        List<NodesVO> alternativeFlights = getActivityInterface().getAlternativeFlights();
        if (alternativeFlights != null) {
            currentNode = getNodeFromAlternative(alternativeFlights);
            primaryGuid = currentNode.getmPrimaryguid();
            isMyFlight = false;
        }else {
            userOrder = getActivityInterface().getTravelOrder();
            currentNode = getLegWithGuid(userOrder);
       //     userFirstChoice = currentNode;
        }



       // NodesVO currentFlightToShow = currentNode;
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
        FlightAdapter mAdapter = new FlightAdapter(currentNode,legsFlights, isMyFlight);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
      //  recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setFlightCost(currentNode.getCost());
        mAdapter.setDestinationFlights(allFlights);
        mAdapter.setButtonListener(new AlternativeButtonCB() {
            @Override
            public void showAlternative() {
//                AlternativeFlightsDetailsFragment fragemnt = new AlternativeFlightsDetailsFragment();
//                HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragemnt, true); //now we always want to add to the backstack
                getActivityInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_DETAILS.getNavNumber());
            }

            @Override
            public void selectCurrentFlight(String guidSelected) {
                sendServerNewHotelOrder(guidSelected);
            }
        });






        // TODO empty alternative flight after select clicked


        conectionRequest(currentNode);

        return rootView;
    }


    private void sendServerNewHotelOrder(String guiSelected) {

        ConnectionManager.getInstance(getActivity()).putFlight(getActivityInterface().getTravelOrder().getmSolutionID(),
                getTravellerWitGuid(getActivityInterface().getTravelOrder()).getmPaxguid(),
                guiSelected,primaryGuid,
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        getActivityInterface().callRefreshItinerary();
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
