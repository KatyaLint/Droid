package hellogbye.com.hellogbyeandroid.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.AlternativeHotelAdapter;
import hellogbye.com.hellogbyeandroid.adapters.PlaceAutocompleteAdapter;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.network.HGBJsonRequest;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

/**
 * Created by arisprung on 8/29/16.
 */


public class SelectNewHotelFragment extends HGBAbstractFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener {


    private SupportMapFragment mMapfragment;
    private GoogleMap mMap;
    private AlternativeHotelAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<NodesVO> mNodesList;
    private LinearLayoutManager mLayoutManager;
    private NodesVO mCurrentSelectedNode;
    private AutoCompleteTextView mAutocomplete;


    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;

    public static Fragment newInstance(int position) {
        Fragment fragment = new SelectNewHotelFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.select_new_hotel_main_layout, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.alt_hotel_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        getFlowInterface().setSelectedHotelNode(null);

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        initHotel();
        mMapfragment = (SupportMapFragment) fm.findFragmentById(R.id.map); //((SupportMapFragment) activity.getFragmentManager().findFragmentById(R.id.map));//(SupportMapFragment) fm.findFragmentById(R.id.map);

        if (mMapfragment == null) {
            mMapfragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mMapfragment).commit();
            mMapfragment.getMapAsync(this);
        }

        // How to retrieve your Java object back from the string

        String strValue = getArguments().getString("alternative_hotels", "");
        Type listType = new TypeToken<List<NodesVO>>() {
        }.getType();

        Gson gson = new Gson();
        mNodesList = gson.fromJson(strValue, listType);

        mAdapter = new AlternativeHotelAdapter(mNodesList,mCurrentSelectedNode,getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new AlternativeHotelAdapter.OnLinearLayoutClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NodesVO node = mNodesList.get(position);
                resetSelectedNode(node);
            }
        });

        mAdapter.SetOnSelectClickListener(new AlternativeHotelAdapter.OnSelectItemClickListener() {
            @Override
            public void onSelectItemClick(View view, int position) {
                getFlowInterface().setSelectedHotelNode(mNodesList.get(position));
                getActivity().onBackPressed();
            }
        });

        initAutoComplete();





    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void initAutoComplete() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .build();
        mAutocomplete =  ((MainActivityBottomTabs)getActivity()).getmAutoComplete();

// // Register a listener that receives callbacks when a suggestion has been selected
        mAutocomplete.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, new LatLngBounds(new LatLng(mCurrentSelectedNode.getmLatitude(),mCurrentSelectedNode.getmLongitude()),new LatLng(mCurrentSelectedNode.getmLatitude(),mCurrentSelectedNode.getmLongitude())),
                null);
        mAutocomplete.setAdapter(mPlaceAutocompleteAdapter);
    }

    private void sendToGoogleAutoComplete(CharSequence charSequence) {


    }


    private void resetSelectedNode(NodesVO node) {
        mCurrentSelectedNode = node;
        resetMarker();
        resetList(node);
        setCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeMap();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latlang = marker.getPosition();

        for(NodesVO node:mNodesList){
            if (node.getmLatitude() == latlang.latitude && node.getmLongitude() == latlang.longitude) {
                resetSelectedNode(node);
                break;
            }
        }

        return false;
    }

    private void resetMarker() {
        mMap.clear();
        setMarkers();
    }

    private void resetList(NodesVO node) {
        mRecyclerView.smoothScrollToPosition(mNodesList.indexOf(node));
        mAdapter.setmMyNode(node);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadMap();
    }

    private void loadMap() {
        //SET UP MAP
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMarkerClickListener(this);

        setMarkers();
        setCamera();
    }

    private void setCamera() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(mCurrentSelectedNode.getmLatitude(),mCurrentSelectedNode.getmLongitude()))      // TODO set curret hotel
                .zoom(13)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setMarkers() {
        for (int i = 0; i < mNodesList.size(); i++) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mNodesList.get(i).getmLatitude(), mNodesList.get(i).getmLongitude())));
            if(mCurrentSelectedNode.getmHotelCode().equals(mNodesList.get(i).getmHotelCode())){
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(HGBUtility.getMarkerBitmap(true,i + 1, mNodesList.get(i).getmStarRating(), mNodesList.get(i).getmMinimumAmount(),getActivity())));

            }else{
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(HGBUtility.getMarkerBitmap(false,i + 1, mNodesList.get(i).getmStarRating(), mNodesList.get(i).getmMinimumAmount(),getActivity())));
            }
        }
    }

    private void initializeMap() {
        mMapfragment.getMapAsync(this);
    }


    private void initHotel(){
        mCurrentSelectedNode = getLegWithGuid(getActivityInterface().getTravelOrder());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */

            mAutocomplete.setText("");
            getFlowInterface().getToolBar().closeAutoComplete();
            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };


    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e("", "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            searchForMoreHotels( places.get(0).getLatLng());

            places.release();
        }
    };


    private void searchForMoreHotels(LatLng latlng){

        ConnectionManager.getInstance(getActivity()).postSearchHotels(getActivityInterface().getTravelOrder().getmSolutionID(),
                mCurrentSelectedNode.getmGuid(),
                latlng, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        CellsVO cellsVO = (CellsVO) data;
                        mNodesList.clear();
                        mNodesList.addAll(cellsVO.getmNodes());
                        mAdapter.notifyDataSetChanged();
                        resetSelectedNode(mNodesList.get(0));
                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });

    }

}
