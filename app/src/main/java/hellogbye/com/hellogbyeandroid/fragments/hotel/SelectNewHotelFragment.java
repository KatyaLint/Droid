package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.RefreshComplete;
import hellogbye.com.hellogbyeandroid.adapters.PlaceAutocompleteAdapter;
import hellogbye.com.hellogbyeandroid.adapters.hotel.CustomAlternativeHotelAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityHotel;
import info.hoang8f.android.segmented.SegmentedGroup;

import static hellogbye.com.hellogbyeandroid.R.id.map;


/**
 * Created by arisprung on 8/29/16.
 */


public class SelectNewHotelFragment extends HGBAbstractFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, OnStreetViewPanoramaReadyCallback {


    private SupportMapFragment mMapfragment;
    private GoogleMap mMap;
    private CustomAlternativeHotelAdapter mAdapter;
    private ViewPager mViewPager;
    private ArrayList<NodesVO> mNodesList;
    private NodesVO mCurrentSelectedNode;
    private NodesVO mPastSelectedNode;
    private AutoCompleteTextView mAutocomplete;
    private SegmentedGroup mSwitch;
    private StreetViewPanoramaView mStreetViewPanoramaView;
    private View mView;

    private StreetViewPanoramaOptions options;
    private StreetViewPanorama mPanorama;
    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private HashMap<Integer, Marker> mMarkersList;
    private static final String STREETVIEW_BUNDLE_KEY = "StreetViewBundleKey";

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

        mView = inflater.inflate(R.layout.select_new_hotel_main_layout, container, false);
        mViewPager = (ViewPager) mView.findViewById(R.id.alt_hotel_viewpager);
        mMarkersList = new HashMap<>();
        mAutocomplete = (AutoCompleteTextView) mView.findViewById(R.id.hotel_autocomplete);
        mStreetViewPanoramaView = (StreetViewPanoramaView) mView.findViewById(R.id.steet_view_panorama);
        mSwitch = (SegmentedGroup) mView.findViewById(R.id.segmented2);

        return mView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        initHotel();
        mMapfragment = (SupportMapFragment) fm.findFragmentById(map); //((SupportMapFragment) activity.getFragmentManager().findFragmentById(R.id.map));//(SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mMapfragment == null) {
            mMapfragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(map, mMapfragment).commit();
            mMapfragment.getMapAsync(this);
        }

        // How to retrieve your Java object back from the string

        String strValue = getArguments().getString("alternative_hotels", "");
        Type listType = new TypeToken<List<NodesVO>>() {
        }.getType();

        Gson gson = new Gson();
        mNodesList = gson.fromJson(strValue, listType);

        mAdapter = new CustomAlternativeHotelAdapter(getFragmentManager(), mNodesList, mCurrentSelectedNode, getActivity().getApplicationContext());
        mViewPager.setAdapter(mAdapter);

        mAdapter.SetOnSelectClickListener(new CustomAlternativeHotelAdapter.OnSelectItemClickListener() {
            @Override
            public void onSelectItemClick(View view, int position) {


                sendServerNewHotelOrder(mNodesList.get(position));
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int iIndex;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                iIndex = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    NodesVO node = mNodesList.get(iIndex);
                    resetList(node);
                    resetSelectedNode(node);
                }

            }
        });


        initAutoComplete();

        mSwitch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.button21) {
                    showStreetView();
                } else {
                    showMap();
                }

            }
        });
//        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    showStreetView();
//                } else {
//                    showMap();
//                }
//            }
//        });



        initStreetView(savedInstanceState);
    }

    private void initStreetView(Bundle savedInstanceState) {

        mStreetViewPanoramaView.onCreate(savedInstanceState);
        mStreetViewPanoramaView.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                panorama.setPosition(new LatLng(mCurrentSelectedNode.getmLatitude(), mCurrentSelectedNode.getmLongitude()));
                mPanorama = panorama;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mStreetViewBundle = outState.getBundle(STREETVIEW_BUNDLE_KEY);
        if (mStreetViewBundle == null) {
            mStreetViewBundle = new Bundle();
            outState.putBundle(STREETVIEW_BUNDLE_KEY, mStreetViewBundle);
        }

        mStreetViewPanoramaView.onSaveInstanceState(mStreetViewBundle);
    }

    private void showStreetView() {
        mStreetViewPanoramaView.setVisibility(View.VISIBLE);
    }

    private void showMap() {
        mStreetViewPanoramaView.setVisibility(View.GONE);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }


    private void initAutoComplete() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 0, this)
                .addApi(Places.GEO_DATA_API)
                .addOnConnectionFailedListener(this).addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .build();
        mGoogleApiClient.connect();


// // Register a listener that receives callbacks when a suggestion has been selected

    }

    private void sendToGoogleAutoComplete(CharSequence charSequence) {


    }


    private void resetSelectedNode(NodesVO node) {
        mPastSelectedNode = mCurrentSelectedNode;
        mCurrentSelectedNode = node;
        resetMarker();

    }

    @Override
    public void onResume() {
        super.onResume();
        initializeMap();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latlang = marker.getPosition();

        for (int i = 0; i < mNodesList.size(); i++) {
            if (mNodesList.get(i).getmLatitude() == latlang.latitude && mNodesList.get(i).getmLongitude() == latlang.longitude) {
                resetSelectedNode(mNodesList.get(i));
                mViewPager.setCurrentItem(i, true);
                break;
            }
        }
//
//        for (NodesVO node : mNodesList) {
//
//        }

        return false;
    }

    private void resetMarker() {
        //TODO NEEd to fix flickering in map
        setMarkers();
    }

    private void resetList(NodesVO node) {
        mViewPager.setCurrentItem(mNodesList.indexOf(node), true);
        mAdapter.setmMyNode(node);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (mMap == null) {
            mMap = googleMap;
            loadMap();
        }

    }

    private void loadMap() {
        //SET UP MAP
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMarkerClickListener(this);

        LoadMarkersAsyncTask loadMarkersAsyncTask = new LoadMarkersAsyncTask();
        loadMarkersAsyncTask.execute();
        setCamera();
    }

    private void setCamera() {
        if (mMap == null) {
            return;
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(mCurrentSelectedNode.getmLatitude(), mCurrentSelectedNode.getmLongitude()))      // TODO set curret hotel
                .zoom(13)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mPanorama.setPosition(new LatLng(mCurrentSelectedNode.getmLatitude(), mCurrentSelectedNode.getmLongitude()));

    }

    private void setMarkers() {

        if (mMap == null) {
            return;
        }

        ResetMarkersAsyncTask task = new ResetMarkersAsyncTask();
        task.execute();

    }

    private void initializeMap() {
        mMapfragment.getMapAsync(this);
    }


    private void initHotel() {
        mCurrentSelectedNode = getLegWithGuid(getActivityInterface().getTravelOrder());
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
            searchForMoreHotels(places.get(0).getLatLng());

            places.release();
        }
    };


    private void searchForMoreHotels(LatLng latlng) {

        ConnectionManager.getInstance(getActivity()).postSearchHotels(getActivityInterface().getTravelOrder().getmSolutionID(),
                mCurrentSelectedNode.getmGuid(),
                latlng, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        CellsVO cellsVO = (CellsVO) data;
                        mNodesList.clear();
                        mNodesList.addAll(cellsVO.getmNodes());
                        mNodesList.add(0, mCurrentSelectedNode);
                        loadMap();
                        mAdapter.notifyDataSetChanged();
                        resetSelectedNode(mNodesList.get(0));

                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });

    }


    private void sendServerNewHotelOrder(final NodesVO nodesVO) {

        ConnectionManager.getInstance(getActivity()).putAlternateHotel(getActivityInterface().getTravelOrder().getmSolutionID(),
                getTravellerWitGuid(getActivityInterface().getTravelOrder()).getmPaxguid(),
                nodesVO.getmCheckIn(), nodesVO.getmCheckOut(), nodesVO.getmGuid(),
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        mAdapter.setmCurrentNode(nodesVO);
                        mAdapter.notifyDataSetChanged();

                        getFlowInterface().callRefreshItineraryWithCallback(ToolBarNavEnum.HOTEL.getNavNumber(), new RefreshComplete() {
                            @Override
                            public void onRefreshSuccess(Object data) {
                                selectedItemGuidNumber(nodesVO.getmGuid());
                                getActivity().onBackPressed();
                            }

                            @Override
                            public void onRefreshError(Object data) {

                            }
                        }, getActivityInterface().getTravelOrder().getmSolutionID());


                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("", "onConnected. Error: ");
        mAutocomplete.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, new LatLngBounds(new LatLng(mCurrentSelectedNode.getmLatitude(), mCurrentSelectedNode.getmLongitude()), new LatLng(mCurrentSelectedNode.getmLatitude(), mCurrentSelectedNode.getmLongitude())),
                null);
        mAutocomplete.setAdapter(mPlaceAutocompleteAdapter);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("", "onConnectionSuspended. Error: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("", "ConnectionResult. Error: " + connectionResult.getErrorMessage());

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

    }

    private class LoadMarkersAsyncTask extends AsyncTask<Void, MarkerOptions, Void> {

        int iIndex = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMap.clear();
            iIndex = 0;
        }

        @Override
        protected Void doInBackground(Void... redo) {

            for (int i = 0; i < mNodesList.size(); i++) {
                if (mCurrentSelectedNode.getmHotelCode().equals(mNodesList.get(i).getmHotelCode())) {

                    publishProgress(new MarkerOptions()
                            .zIndex(1.0f)
                            .position(new LatLng(mNodesList.get(i).getmLatitude(), mNodesList.get(i).getmLongitude()))
                            .icon(BitmapDescriptorFactory.fromBitmap(HGBUtilityHotel.getMarkerBitmap(true, i + 1, mNodesList.get(i).getmStarRating(), mNodesList.get(i).getmMinimumAmount(), getActivity()))));
                } else {

                    publishProgress(new MarkerOptions()
                            .zIndex(0)
                            .position(new LatLng(mNodesList.get(i).getmLatitude(), mNodesList.get(i).getmLongitude()))
                            .icon(BitmapDescriptorFactory.fromBitmap(HGBUtilityHotel.getMarkerBitmap(false, i + 1, mNodesList.get(i).getmStarRating(), mNodesList.get(i).getmMinimumAmount(), getActivity()))));
                }
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(MarkerOptions... values) {
            Marker marker = mMap.addMarker(values[0]);
            mMarkersList.put(iIndex, marker);
            iIndex++;
        }

        @Override
        protected void onPostExecute(Void result) {
            setCamera();
        }

    }

    private class ResetMarkersAsyncTask extends AsyncTask<Void, MarkerSet, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... redo) {
            for (int i = 0; i < mNodesList.size(); i++) {
                if (mCurrentSelectedNode.getmLatitude() == mNodesList.get(i).getmLatitude() && mCurrentSelectedNode.getmLongitude() == mNodesList.get(i).getmLongitude()) {
                    publishProgress(new MarkerSet(i, true));

                } else if (mPastSelectedNode.getmLatitude() == mNodesList.get(i).getmLatitude() && mPastSelectedNode.getmLongitude() == mNodesList.get(i).getmLongitude()) {
                    publishProgress(new MarkerSet(i, false));

                }
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(MarkerSet... values) {
            Marker marker = mMarkersList.get(values[0].getIndex());
            if (values[0].isSelected()) {
                marker.setZIndex(1.0f);
            }
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(HGBUtilityHotel.getMarkerBitmap(values[0].isSelected(),
                    values[0].getIndex() + 1, mNodesList.get(values[0].getIndex()).getmStarRating(),
                    mNodesList.get(values[0].getIndex()).getmMinimumAmount(), getActivity())));
        }

        @Override
        protected void onPostExecute(Void result) {
            setCamera();
        }


    }

    public class MarkerSet {
        private int index;
        private boolean isSelected;

        public MarkerSet(int index, boolean isSelected) {
            this.index = index;
            this.isSelected = isSelected;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }


}
