package hellogbye.com.hellogbyeandroid.fragments.alternative;



import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.FlightAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.AirportCoordinatesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.ViewPDFManager;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by kate on 8/17/15.
 */

public class AlternativeFlightFragment extends HGBAbstractFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private SupportMapFragment fragment;
    private GoogleMap mMap;
    private RecyclerView recyclerView;
    private SlidingUpPanelLayout mSlidingPanels;
    public final float PANEL_HIGHT = 0.5f;
 //   private ProgressDialog progressDialog;
    private RelativeLayout pull_down;
    private FlightAdapter mAdapter;
    private View rootView;
    private FontTextView select_flight;
    private boolean isInbound;
    private NodesVO currentNodeVO;


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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment)fm.findFragmentById(R.id.map); //((SupportMapFragment) activity.getFragmentManager().findFragmentById(R.id.map));//(SupportMapFragment) fm.findFragmentById(R.id.map);

        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
            fragment.getMapAsync(this);
        }
    }



    private void slidingPanelInitialization(){

        mSlidingPanels.setCoveredFadeColor(Color.TRANSPARENT);
        mSlidingPanels.setAnchorPoint(PANEL_HIGHT);
        mSlidingPanels.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        mSlidingPanels.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                pull_down.setVisibility(View.GONE);
            }

            @Override
            public void onPanelCollapsed(View panel) {

            }

            @Override
            public void onPanelExpanded(View panel) {

            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });
        mSlidingPanels.setVisibility(View.VISIBLE);
    }




    public void initializeAdapter(final NodesVO currentNode){

        currentNodeVO = currentNode;

        if(!currentNodeVO.ismIsAlternative()){
            getAlternativeFlights(currentNodeVO.getmPrimaryguid());
        }

        ArrayList<LegsVO> legsFlights = currentNode.getLegs();
        String allFlights =  setAllFlights(currentNode);

        mAdapter = new FlightAdapter(currentNode, legsFlights);

        mAdapter.setWebViewLinkClicked(new IWebViewClicked(){
            @Override
            public void webLinkClicked() {
                Uri uri = Uri.parse("http://www.flightnetwork.com/pages/airline-information/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        if(!currentNode.ismIsAlternative()){
            select_flight.setVisibility(View.GONE);
        }

        select_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendServerNewFlightOrder(currentNode.getLegs().get(0).getmParentguid());
            }
        });

        mAdapter.setFlightCost(currentNode.getCost());
        mAdapter.setPaid(currentNode.getmPaymentProcessingState());
        mAdapter.setDestinationFlights(allFlights);
        mAdapter.updateMyFlight(!currentNode.ismIsAlternative());
     //   mAdapter.updateMyFlight(isMyFlight);
        mAdapter.setAlternativeButtonDisable(false);

        mAdapter.setButtonListener(new AlternativeButtonCB() {
            @Override
            public void showAlternative() {
                Bundle arg = new Bundle();
                arg.putString(HGBConstants.BUNDLE_CURRENT_VIEW_NODE_ID, currentNode.getmPrimaryguid());
                getFlowInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_DETAILS.getNavNumber(),arg);

            }

            @Override
            public void selectCurrentFlight(String guidSelected) {

            }

            @Override
            public void selectedPressEticket(String guid) {

                String url = ConnectionManager.getURL(ConnectionManager.Services.BOOKING_CONFIRMATION) + "itineraryId="+getActivityInterface().getSolutionID()+"&itemGuid="+guid;
                new BackgroundTask().execute(url);

            }
        });



        // 4. set adapter
        recyclerView.setAdapter(mAdapter);


        // 2. set layoutManger

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }



    public void flightType(boolean isInbound){
        this.isInbound = isInbound;
    }

    private NodesVO getCurrentNodeOutbound(){
        final NodesVO currentNode;
        UserTravelMainVO userOrder = getActivityInterface().getTravelOrder();
        currentNode = getLegWithGuid(userOrder);

        return currentNode;
    }

    private NodesVO getCurrentNodeInbound(){
        final NodesVO currentNode;
        NodesVO nodeVO = null;

        UserTravelMainVO userOrder = getActivityInterface().getTravelOrder();
        currentNode = getLegWithGuid(userOrder);
        String primaryGuid = currentNode.getmPrimaryguid();

        Map<String, NodesVO> flightItems = userOrder.getItems();
        //  nodeVO = flightItems.get(primaryGuid);
        Collection<NodesVO> nodesValue = flightItems.values();
        for(NodesVO node : nodesValue){
            if(node.getParentflightid() != null && node.getParentflightid().equals(primaryGuid)){
                return node;
            }
        }
        return nodeVO;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.flight_layout_details, null, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.flightRecyclerView);
        pull_down = (RelativeLayout) rootView.findViewById(R.id.pull_down);

        select_flight = (FontTextView)rootView.findViewById(R.id.select_flight);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //   slidingPanelInitialization();
        //   mSlidingPanels = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout_flight);
        mSlidingPanels = (SlidingUpPanelLayout)rootView.findViewById(R.id.sliding_layout_flight);
        slidingPanelInitialization();

        Bundle args = getArguments();

        boolean isRoundTrip = args.getBoolean(HGBConstants.BUNDLE_ROUND_TRIP);
        NodesVO currentNode;
        if(!isRoundTrip){
            currentNode = getCurrentNode();
        }
        else{
            if(isInbound){
                currentNode =  getCurrentNodeInbound();
            }else{
                currentNode =  getCurrentNodeOutbound();
            }
        }
        initializeAdapter(currentNode);
        return rootView;
    }

    private NodesVO getCurrentNode(){
        NodesVO currentNode;
        List<NodesVO> alternativeFlights = getActivityInterface().getAlternativeFlights();

        if (alternativeFlights != null) {
            currentNode = getNodeFromAlternative(alternativeFlights);
        }else {
            UserTravelMainVO userOrder = getActivityInterface().getTravelOrder();
            currentNode = getLegWithGuid(userOrder);

        }

        return currentNode;
    }




    private void getAlternativeFlights(String mGuid){

        String solutionID = getActivityInterface().getTravelOrder().getmSolutionID();

        String paxId = getSelectedUserGuid();
        String flightID = mGuid;

        ConnectionManager.getInstance(getActivity()).getAlternateFlightsForFlight(solutionID, paxId, flightID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                List<NodesVO> alternativeFlightsVOs = (List<NodesVO>)data;//gson.fromJson((String) data, listType);

                getActivityInterface().setAlternativeFlights(alternativeFlightsVOs);
            }

            @Override
            public void onError(Object data) {

                ErrorMessage(data);
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        mMap = fragment.getMap();

        if (mMap != null) {

            //SET UP MAP
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
           // mMap.setMyLocationEnabled(true);
            mMap.setOnMarkerClickListener(this);
        } else {
            Log.d("DEBUG", "map is null");
        }

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
                        //TODO why this here???
                        getFlowInterface().callRefreshItinerary(ToolBarNavEnum.ALTERNATIVE_FLIGHT_ROUND_TRIP.getNavNumber());
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });
    }


    private String setAllFlights(NodesVO currentNode){
        String allFlights = "";

        ArrayList<LegsVO> legsFlights = currentNode.getLegs();// legs; //TODO change
        int legSize = legsFlights.size();
        // LatLngBounds.Builder bc = new LatLngBounds.Builder();
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
        return allFlights;
    }


    private void setMapCoordinates(ArrayList<LegsVO> legsFlights, int i){
        int size = legsFlights.size();
        LegsVO leg = legsFlights.get(i);

        AirportCoordinatesVO airportOrginCoordinatesVO = leg.getOriginairportcoordinates();
        AirportCoordinatesVO airportDestinCoordinatesVO = leg.getDestinationairportcoordinates();
        if(mMap == null) {
            return;
        }
            mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(airportOrginCoordinatesVO.getLatitude(), airportOrginCoordinatesVO.getLongitude()),
                            new LatLng(airportDestinCoordinatesVO.getLatitude(), airportDestinCoordinatesVO.getLongitude()))
                    .width(5).color(Color.RED).geodesic(true));

        if (i != 0 || i != size - 1) {
            mMap.addMarker(new MarkerOptions().
                  position(new LatLng(leg.getDestinationairportcoordinates().getLatitude(), leg.getDestinationairportcoordinates().getLongitude())));
         }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        ArrayList<LegsVO> legsFlights = currentNodeVO.getLegs();
        for (int i = 0; i < legsFlights.size(); i++) {
            setMapCoordinates(legsFlights, i);
        }

        LatLngBounds.Builder bc = new LatLngBounds.Builder();
        bc.include(new LatLng(currentNodeVO.getOriginairportcoordinates().getLatitude(), currentNodeVO.getOriginairportcoordinates().getLongitude()));
        bc.include(new LatLng(currentNodeVO.getDestinationairportcoordinates().getLatitude(), currentNodeVO.getDestinationairportcoordinates().getLongitude()));

        //Kate
        mMap.addMarker(new MarkerOptions().
                position(new LatLng(currentNodeVO.getOriginairportcoordinates().getLatitude(), currentNodeVO.getOriginairportcoordinates().getLongitude())));
        mMap.addMarker(new MarkerOptions().
                position(new LatLng(currentNodeVO.getDestinationairportcoordinates().getLatitude(), currentNodeVO.getDestinationairportcoordinates().getLongitude())));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(currentNodeVO.getDestinationairportcoordinates().getLatitude(), currentNodeVO.getDestinationairportcoordinates().getLongitude()),8);
        mMap.moveCamera(cameraUpdate);



    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    public interface AlternativeButtonCB{
        void showAlternative();
        void selectCurrentFlight( String guid);
        void selectedPressEticket( String guid);
    }

    public class BackgroundTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... ulr) {
            ViewPDFManager viewPDFManager = new ViewPDFManager(getActivity().getApplicationContext());
            return  viewPDFManager.starDownloadPDF(ulr[0]);

        }

        @Override
        protected void onPostExecute(String result) {

            if(result != null)
            {
                File file = new File(result);
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                startActivity(intent);
            }else{
                Toast.makeText(getActivity().getApplicationContext(),"There was a problem loading PDF",Toast.LENGTH_SHORT).show();
            }
        }

    }

/*    private void startProgressDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "", "loading");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void dissmissProgressDialog(){
        progressDialog.dismiss();
    }*/


}
