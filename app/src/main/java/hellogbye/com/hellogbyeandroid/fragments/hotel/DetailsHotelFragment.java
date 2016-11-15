package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityHotel;
import hellogbye.com.hellogbyeandroid.views.ExpandableHeightGridView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static hellogbye.com.hellogbyeandroid.R.id.hotel_detail_location_label;

/**
 * Created by arisprung on 9/15/16.
 */
public class DetailsHotelFragment extends HGBAbstractFragment  implements  OnMapReadyCallback {

    private SupportMapFragment fragment;
    private ExpandableHeightGridView mGridView;
    private  NodesVO mNodesVO;
    private GoogleMap mMap;
    private boolean permissionDenied = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hotel_details_layout, container, false);
        FontTextView descTextView = (FontTextView) v.findViewById(R.id.hotel_detail_description);
        FontTextView descLabelTextView = (FontTextView) v.findViewById(R.id.hotel_detail_description_label);
        FontTextView contactLabelTextView = (FontTextView) v.findViewById(R.id.hotel_detail_contact_label);
        FontTextView locationLabelTextView = (FontTextView) v.findViewById(R.id.hotel_detail_location_label);

        FontTextView contact = (FontTextView) v.findViewById(R.id.hotel_detail_contact);
        descLabelTextView.setVisibility(View.VISIBLE);
        contactLabelTextView.setVisibility(View.VISIBLE);
        locationLabelTextView.setVisibility(View.VISIBLE);
        mNodesVO = getLegWithGuid(getActivityInterface().getTravelOrder());
        mGridView = (ExpandableHeightGridView) v.findViewById(R.id.grid);
        mGridView.setExpanded(true);
        descTextView.setText(Html.fromHtml(mNodesVO.getmShortDescription()));
        contact.setText(mNodesVO.getmAddress1());
        return v;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if(mNodesVO.getRoomsVOs().get(0) != null){
            CustomDetailAmenitiesGridAdapter adapter = new CustomDetailAmenitiesGridAdapter(getActivity(),mNodesVO.getRoomsVOs().get(0).getmAmenities() );
            mGridView.setAdapter(adapter);
        }

        FragmentManager fm = getChildFragmentManager();

        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map); //((SupportMapFragment)
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
            fragment.getMapAsync(this);
        }



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadMap();
    }

    private void loadMap() {
        if(!permissionDenied){
            mMap.setMyLocationEnabled(true);
        }

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        setCurrentHotel();

    }

    @Override
    public void onResume() {
        super.onResume();
        initializeMap();
    }

    private void initializeMap() {

        fragment.getMapAsync(this);
    }


    private Marker setCurrentHotel() {
        if(mMap != null){
            mMap.clear();
        }
        NodesVO nodesVO = getLegWithGuid(getActivityInterface().getTravelOrder());
        LatLng currentHotelLatLon = new LatLng(nodesVO.getmLatitude(), nodesVO.getmLongitude());
        Marker currentHotel = mMap.addMarker(new MarkerOptions()
                .position(currentHotelLatLon)
                .icon(BitmapDescriptorFactory.fromBitmap(HGBUtilityHotel.getMyHotelMarkerBitmap(nodesVO.getmStarRating(), nodesVO.getmMinimumAmount(), getActivity()))));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentHotelLatLon)      // Sets the center of the map to Mountain View
                .zoom(11)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return currentHotel;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == HGBConstants.MY_PERMISSION_ACCESS_COARSE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                permissionDenied = false;
            } else {
                permissionDenied = true;
            }
            // Permission was denied. Display an error message.
        }
    }
}
