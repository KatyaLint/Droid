package hellogbye.com.hellogbyeandroid.fragments.alternative;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.flights.AlternativeFlightsAdapter;
import hellogbye.com.hellogbyeandroid.adapters.flights.AlternativeFlightsSortAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilitySort;

/**
 * Created by nyawka on 10/14/15.
 */
public class AlternativeFlightsDetailsFragment extends HGBAbstractFragment {
    private AlternativeFlightsAdapter mAdapter;
    private AlternativeFlightsSortAdapter sortPopupAdapter;
    private AlertDialog alertDialog;
    private HGBUtilitySort hgbUtilitySort;
    private List<NodesVO> alternativeFlights;
    // private AlternativeFlightsAdapter mAdapter;

    public static Fragment newInstance(int position) {
        Fragment fragment = new AlternativeFlightsDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);

        return fragment;
    }

    public interface ISortClickCB{
        void onSortClick();
    }


    private void sortDialog(){

            LayoutInflater li = LayoutInflater.from(getActivity());
            View promptsView = li.inflate(R.layout.popup_custom_title_alternative_sort, null);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setCustomTitle(promptsView);

        final ArrayList<String> data = new ArrayList<>();
        data.add("Price");
        data.add("Airline");
            sortPopupAdapter = new AlternativeFlightsSortAdapter(data);

            View promptsViewTeest = li.inflate(R.layout.popup_alternative_layout_sort, null);
            ListView user_profile_popup_list_view = (ListView) promptsViewTeest.findViewById(R.id.alternative_popup_sort);


            user_profile_popup_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String sortType = data.get(position);
                    alternativeFlights = hgbUtilitySort.sortData(sortType, alternativeFlights);
                    mAdapter.notifyDataSetChanged();
                    alertDialog.dismiss();

                }
            });

        user_profile_popup_list_view.setAdapter(sortPopupAdapter);

       /*     LinearLayout manage_profile_ll = (LinearLayout)promptsViewTeest.findViewById(R.id.manage_profile_ll);
            manage_profile_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flowInterface.goToFragment(ToolBarNavEnum.TRAVEL_PREFERENCE.getNavNumber(), null);
                    selectDefaultProfileDialog.dismiss();
                }
            });*/

           /* mRadioPreferencesAdapter.setClickedLineCB(new PreferenceSettingsFragment.ListLineClicked() {
                @Override
                public void clickedItem(String itemID) {


                }
            });

            mRadioPreferencesAdapter.setSelectedRadioButtonListener(new PreferenceSettingsFragment.ListRadioButtonClicked(){

                @Override
                public void clickedItem(int selectedPosition) {
                    radioButtonSelected = selectedPosition;
                }
            });

            selectedRadioPreference(activity);*/


            dialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  /*  if(radioButtonSelected != -1) {

                        if(isDefaultProfile){
                            final DefaultsProfilesVO selected = accountDefaultSettings.get(radioButtonSelected);
                            postDefaultProfile(String.valueOf(selected.getId()), selected.getName(), activity, activityInterface, userProfileVOs);
                        }else{
                            putAccoutToServer(activity, activityInterface);
                        }


                        selectDefaultProfileDialog.dismiss();
                    }*/
                } });


            //Create alert dialog object via builder
        alertDialog = dialogBuilder.create();
        alertDialog.setView(promptsViewTeest);
        alertDialog.setCancelable(false);
        alertDialog.show();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.alternative_flights_list_layout, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter
        mAdapter = new AlternativeFlightsAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnSortClick(new ISortClickCB(){

            @Override
            public void onSortClick() {
                sortDialog();
            }
        });

        mAdapter.SetOnItemClickListener(new AlternativeFlightsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(String guid) {
                selectedItemGuidNumber(guid);
                getFlowInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_FACTORY.getNavNumber(),null);
                //getFlowInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_ROUND_TRIP.getNavNumber(),null);
            }
        });


        // 4. set adapter

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        alternativeFlights = getActivityInterface().getAlternativeFlights();
        mAdapter.setData(alternativeFlights);
        mAdapter.notifyDataSetChanged();

        hgbUtilitySort = new HGBUtilitySort();

        return rootView;
    }

   /* private void getAlternativeFlights(String mGuid){

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
    }*/

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
