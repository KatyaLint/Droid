package hellogbye.com.hellogbyeandroid.fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import hellogbye.com.hellogbyeandroid.R;

/**
 * Created by arisprung on 9/30/15.
 */
public class HotelFragment extends HGBAbtsractFragment {

    private MapFragment fragment;
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hotel_main_layout, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();

        fragment = ((MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map));//(SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap = fragment.getMap();

        if (mMap != null) {
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.setMyLocationEnabled(true);


        } else {
            Log.d("DEBUG", "map is null");
        }




    }
}
