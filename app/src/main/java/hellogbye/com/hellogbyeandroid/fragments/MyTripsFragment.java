package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.MyTripPinnedAdapter;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.PinnedHeaderListView;


/**
 * Created by arisprung on 8/17/15.
 */
public class MyTripsFragment extends Fragment {

    private PinnedHeaderListView stickyList;
    private  ArrayList<MyTripItem> mItemsList;


    public static Fragment newInstance(int position) {
        Fragment fragment = new MyTripsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_trips_layout, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stickyList = (PinnedHeaderListView) view.findViewById(R.id.pinnedListView);
        ConnectionManager.getInstance(getActivity()).getMyTrips(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                mItemsList= (ArrayList<MyTripItem>) data;

                MyTripPinnedAdapter sectionedAdapter = new MyTripPinnedAdapter(mItemsList);
                stickyList.setAdapter(sectionedAdapter);

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });

        stickyList.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

                if (section == 0) {
                    //TODO go to current itinerary

                } else if (section == 1) {

                    String solutionId = mItemsList.get(position).getSolutionid();
                    ConnectionManager.getInstance(getActivity()).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                          Log.d("","");
                            //TODO set Travel and got to current itenrary
                        }

                        @Override
                        public void onError(Object data) {
                            Log.e("MainActivity", "Problem updating grid  " + data);
                        }
                    });

                }

            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });

    }
}
