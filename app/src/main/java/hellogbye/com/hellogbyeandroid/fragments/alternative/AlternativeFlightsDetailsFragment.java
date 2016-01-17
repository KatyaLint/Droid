package hellogbye.com.hellogbyeandroid.fragments.alternative;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.AlternativeFlightsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

/**
 * Created by nyawka on 10/14/15.
 */
public class AlternativeFlightsDetailsFragment extends HGBAbtsractFragment {

   // private AlternativeFlightsAdapter mAdapter;

    public static Fragment newInstance(int position) {
        Fragment fragment = new AlternativeFlightsDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.alternative_flights_list_layout, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);


       List<NodesVO> alternativeFlights = getActivityInterface().getAlternativeFlights();


       // ArrayList<AlternativeFlightsVO> alternativeFlights = parseFlight();


        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter
        AlternativeFlightsAdapter mAdapter = new AlternativeFlightsAdapter(alternativeFlights);
        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new AlternativeFlightsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(String guid) {
                Fragment fragment = new AlternativeFlightFragment();
                ((AlternativeFlightFragment)fragment).selectedItemGuidNumber(guid);
                getActivityInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber(),null);
            }
        });


        // 4. set adapter

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return rootView;
    }



    private ArrayList<AlternativeFlightsVO> airplaneDataVO;
    //TODO move to correct place
    private ArrayList<AlternativeFlightsVO> parseFlight(){
        Gson gson = new Gson();

        //        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<FlightsVO>>(){}.getType();
//        String strJson = loadJSONFromAsset();
//        ArrayList<FlightsVO> airplaneDataVO = gson.fromJson(strJson, type);

        Type listType = new TypeToken<List<AlternativeFlightsVO>>() {
        }.getType();

        //  Type type = new TypeToken<ArrayList<AirplaneDataVO>>(){}.getType();
        String strJson = HGBUtility.loadJSONFromAsset("alternativeflights.txt", getActivity());

        airplaneDataVO = gson.fromJson(strJson, listType);
        return airplaneDataVO;
    }

    @Override
    public void onDestroyView() {
     //   getActivityInterface().setAlternativeFlights(null);
        super.onDestroyView();
    }
}
