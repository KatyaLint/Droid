package hellogbye.com.hellogbyeandroid.fragments.mytrips;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.ISwipeAdapterExecution;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.myTripsSwipeAdapter.TripsSwipeItemsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;

import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;

/**
 * Created by nyawka on 5/8/16.
 */
public class TabViewMainFragment extends HGBAbstractFragment   implements SearchView.OnQueryTextListener{
    private ArrayList<MyTripItem> mItemsList;
    private TripsSwipeItemsAdapter mAdapter;
    private List<MyTripItem> mCurrItemsList;
    private Activity activity;
    private RecyclerView mRecyclerView;
    private LinearLayout my_trip_empty_view_ll;

    public void initFragmentTabsView(View rootView){
        this.activity = getActivity();

         mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_trips_recycle_list);

        SearchView  my_trip_search_view=(SearchView) rootView.findViewById(R.id.my_trips_search_view);

        my_trip_empty_view_ll = (LinearLayout) rootView.findViewById(R.id.my_trip_empty_view_ll);

        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Item Decorator:
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mItemsList = new ArrayList<>();

        mAdapter = new TripsSwipeItemsAdapter(getActivity(), mItemsList);

        adapterClickListener();

        mAdapter.setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(mAdapter);

        my_trip_search_view.setOnQueryTextListener(this);

        // 5. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        goToCNCView(rootView);

        args = new Bundle();
    }


    private void goToCNCView(View rootView){
        FloatingActionButton my_trips_go_to_cnc = (FloatingActionButton) rootView.findViewById(R.id.my_trips_add_trip);
        my_trips_go_to_cnc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final Bundle bundle = new Bundle();
                bundle.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
                getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), bundle);
            }
        });
    }
    private Bundle args;

    private void adapterClickListener(){
        mAdapter.addClickeListeners(new ISwipeAdapterExecution(){

            @Override
            public void clickedItem(String solutionId) {


                args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
                args.putString(HGBConstants.SOLUTION_ITINERARY_ID, solutionId);
                getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), args);



            }

            @Override
            public void deleteClicked(final String solutionId) {

                ConnectionManager.getInstance(activity).deleteItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

//                        mAdapter.notifyItemRemoved(position);
//                        mAdapter.notifyItemRangeChanged(position, mItemsList.size());

                        //TODO set Travel and got to current itenrary
                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });
            }

            @Override
            public void confirmItem(String companionId) {

            }

            @Override
            public void rejectItem(String companionId) {

            }
        });

    }



    public void updateTabsView(int tabPosition, Activity activity){
        this.activity = activity;
        switch (tabPosition){
            case 0:
                upcomigTrips();
                break;
            case 1:
                historyTrips();
                break;
            case 2:
                favoriteTrips();
                break;
        }
    }


    private void upcomigTrips(){
        ConnectionManager.getInstance(activity).getMyTripsPaid(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                mItemsList = (ArrayList<MyTripItem>) data;
                setListsVisability();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void historyTrips(){

        ConnectionManager.getInstance(activity).getMyTrips(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                mItemsList = (ArrayList<MyTripItem>) data;
                setListsVisability();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    private void favoriteTrips(){
        ConnectionManager.getInstance(activity).getMyTripsFavorite(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                mItemsList = (ArrayList<MyTripItem>) data;
                setListsVisability();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void setListsVisability(){

        if(mItemsList.isEmpty()){
            my_trip_empty_view_ll.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else{
            mCurrItemsList = new ArrayList<MyTripItem>(mItemsList);
            my_trip_empty_view_ll.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.updateDataSet(mItemsList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onQueryTextChange(String query) {
        if(mCurrItemsList == null){ //list empty
            return false;
        }
        // Here is where we are going to implement our filter logic
        final List<MyTripItem> filteredModelList = filter(mCurrItemsList, query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<MyTripItem> filter(List<MyTripItem> models, String query) {
        final List<MyTripItem> filteredModelList = new ArrayList<>();
        if(models == null){
            return filteredModelList;
        }
        query = query.toLowerCase();

        for (MyTripItem model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }




}
