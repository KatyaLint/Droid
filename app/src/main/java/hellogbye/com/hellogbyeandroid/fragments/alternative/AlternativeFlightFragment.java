package hellogbye.com.hellogbyeandroid.fragments.alternative;



import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import java.util.List;
import hellogbye.com.hellogbyeandroid.R;
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


/**
 * Created by kate on 8/17/15.
 */

public class AlternativeFlightFragment extends HGBAbstractFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {


    private UserTravelMainVO userOrder;
    private SupportMapFragment fragment;
    private GoogleMap mMap;
    private RecyclerView recyclerView;
    private boolean isMyFlight = true;
    private SlidingUpPanelLayout mSlidingPanels;
    public final float PANEL_HIGHT = 0.5f;
    private ProgressDialog progressDialog;
    private RelativeLayout pull_down;
    private FlightAdapter mAdapter;

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



    private void getAlternativeFlights(NodesVO currentNode){

        String solutionID = getActivityInterface().getTravelOrder().getmSolutionID();

        String paxId = currentNode.getAccountID();
        String flightID =   currentNode.getmGuid();
        ConnectionManager.getInstance(getActivity()).getAlternateFlightsForFlight(solutionID, paxId, flightID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                    List<NodesVO> alternativeFlightsVOs = (List<NodesVO>)data;//gson.fromJson((String) data, listType);

                 /*   if(alternativeFlightsVOs.isEmpty()){
                        mAdapter.setAlternativeButtonDisable(false);
                    }else{
                        mAdapter.setAlternativeButtonDisable(true);
                    }*/

                    getActivityInterface().setAlternativeFlights(alternativeFlightsVOs);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*View rootView = inflater.inflate(R.layout.flight_layout_details, container, false);*/
        View rootView = inflater.inflate(R.layout.flight_layout_details, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.flightRecyclerView);


        mSlidingPanels = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout_flight);
        pull_down = (RelativeLayout) rootView.findViewById(R.id.pull_down);

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
        progressDialog = new ProgressDialog(getActivity());

        return rootView;
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
                        getFlowInterface().callRefreshItinerary(ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber());
                        //TODO clean nodeVO, go to iternarary screen

                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

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
        LatLngBounds.Builder bc = new LatLngBounds.Builder();
        for (int i = 0; i < legsFlights.size(); i++) {
            LegsVO leg = legsFlights.get(i);
            if (leg.getmType().equals("Leg")) {
                if (i == legsFlights.size() - 1) {
                    allFlights = allFlights + leg.getmOrigin() + " - " + leg.getmDestination();
                } else {
                    allFlights = allFlights + leg.getmOrigin() + " - ";
                }
                AirportCoordinatesVO airportOrginCoordinatesVO = leg.getOriginairportcoordinates();
                AirportCoordinatesVO airportDestinCoordinatesVO = leg.getDestinationairportcoordinates();
                mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(airportOrginCoordinatesVO.getLatitude(), airportOrginCoordinatesVO.getLongitude()),
                                new LatLng(airportDestinCoordinatesVO.getLatitude(), airportDestinCoordinatesVO.getLongitude()))
                        .width(5).color(Color.RED).geodesic(true));

                if(i!=0 || i != legsFlights.size() - 1){
                    mMap.addMarker(new MarkerOptions().
                            position(new LatLng(leg.getDestinationairportcoordinates().getLatitude(), leg.getDestinationairportcoordinates().getLongitude())));
                }



            }
        }
        bc.include(new LatLng(currentNode.getOriginairportcoordinates().getLatitude(), currentNode.getOriginairportcoordinates().getLongitude()));
        bc.include(new LatLng(currentNode.getDestinationairportcoordinates().getLatitude(), currentNode.getDestinationairportcoordinates().getLongitude()));
        mMap.addMarker(new MarkerOptions().
                position(new LatLng(currentNode.getOriginairportcoordinates().getLatitude(), currentNode.getOriginairportcoordinates().getLongitude())));
        mMap.addMarker(new MarkerOptions().
                position(new LatLng(currentNode.getDestinationairportcoordinates().getLatitude(), currentNode.getDestinationairportcoordinates().getLongitude())));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));




        // 2. set layoutManger

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);


        // 3. create an adapter
        mAdapter = new FlightAdapter(currentNode,legsFlights);

        mAdapter.setWebViewLinkClicked(new IWebViewClicked(){
            @Override
            public void webLinkClicked() {
                Uri uri = Uri.parse("http://www.flightnetwork.com/pages/airline-information/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        //  recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setFlightCost(currentNode.getCost());
        mAdapter.setPaid(currentNode.getmPaymentProcessingState());
        mAdapter.setDestinationFlights(allFlights);
        mAdapter.updateMyFlight(isMyFlight);
        mAdapter.setAlternativeButtonDisable(false);

        mAdapter.setButtonListener(new AlternativeButtonCB() {
            @Override
            public void showAlternative() {

                getFlowInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_DETAILS.getNavNumber(),null);

            }

            @Override
            public void selectCurrentFlight(String guidSelected) {
                mAdapter.updateMyFlight(true);
                mAdapter.notifyDataSetChanged();
                mAdapter.setAlternativeButtonDisable(true);
                sendServerNewFlightOrder(guidSelected);

            }

            @Override
            public void selectedPressEticket(String guid) {

                String url = ConnectionManager.getURL(ConnectionManager.Services.BOOKING_CONFIRMATION) + "itineraryId="+getActivityInterface().getSolutionID()+"&itemGuid="+guid;
                new BackgroundTask().execute(url);

            }
        });


        // TODO empty alternative flight after select clicked

        if(isMyFlight){
            getAlternativeFlights(currentNode);
        }

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
            progressDialog = ProgressDialog.show(getActivity(), "", "loading");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected String doInBackground(String... ulr) {
            ViewPDFManager viewPDFManager = new ViewPDFManager(getActivity().getApplicationContext());
            return  viewPDFManager.starDownloadPDF(ulr[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
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



}
