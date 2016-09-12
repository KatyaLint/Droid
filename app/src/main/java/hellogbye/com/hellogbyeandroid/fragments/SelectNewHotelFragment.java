package hellogbye.com.hellogbyeandroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.AlternativeHotelAdapter;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

/**
 * Created by arisprung on 8/29/16.
 */


public class SelectNewHotelFragment extends HGBAbstractFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {


    private SupportMapFragment mMapfragment;
    private GoogleMap mMap;
    private AlternativeHotelAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<NodesVO> mNodesList;
    private LinearLayoutManager mLayoutManager;
    private NodesVO mCurrentSelectedNode;

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

        mNodesList.add(0,mCurrentSelectedNode);
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
}
