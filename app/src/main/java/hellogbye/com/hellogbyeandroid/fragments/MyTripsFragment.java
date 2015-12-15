package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.MyTripAdapter;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;

/**
 * Created by arisprung on 8/17/15.
 */
public class MyTripsFragment extends Fragment {

    private RecyclerView mRecycerView;


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
        mRecycerView = (RecyclerView) view.findViewById(R.id.my_trip_recycler_view);
        ConnectionManager.getInstance(getActivity()).getMyTrips(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<MyTripItem> list = (ArrayList<MyTripItem>) data;
                mRecycerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                // 3. create an adapter
                MyTripAdapter mAdapter = new MyTripAdapter(list);
                // 4. set adapter
                mRecycerView.setAdapter(mAdapter);
                // 5. set item animator to DefaultAnimator
                mRecycerView.setItemAnimator(new DefaultItemAnimator());

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });


        // 2. set layoutManger


    }
}
