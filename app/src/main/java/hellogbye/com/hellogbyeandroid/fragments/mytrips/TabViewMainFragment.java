package hellogbye.com.hellogbyeandroid.fragments.mytrips;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;

/**
 * Created by nyawka on 5/8/16.
 */
public class TabViewMainFragment extends HGBAbstractFragment   implements SearchView.OnQueryTextListener{

    private RecyclerView mRecyclerView;
    private ArrayList<MyTripItem> mItemsList;
    private TripsSwipeItemsAdapter mAdapter;
    private SearchView my_trip_search_view;
    private List<MyTripItem> mCurrItemsList;
    private LinearLayout my_trips_empty_view;
    private Activity activity;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.activity = getActivity();
        System.out.println("Kate TabViewMainFragment onCreateView " );

        View rootView = inflater.inflate(R.layout.my_trips_tabs_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_trips_recycle_list);

        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        // Item Decorator:
        mRecyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        mItemsList = new ArrayList<>();

        mAdapter = new TripsSwipeItemsAdapter(activity, mItemsList);

        adapterClickListener();

        mAdapter.setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(mAdapter);

        my_trip_search_view=(SearchView) rootView.findViewById(R.id.my_trips_search_view);

        my_trips_empty_view = (LinearLayout) rootView.findViewById(R.id.companion_empty_view);

        searchListInitialization();

        upcomigTrips();


       // View rootView = initViews();
        return rootView;
    }




    private void initViews(){

        if(mRecyclerView != null){
            return;
        }

        LayoutInflater inflater = (LayoutInflater)   activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.my_trips_tabs_view, null);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_trips_recycle_list);

        // Layout Managers:
 /*       mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        // Item Decorator:
        mRecyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));*/
        mItemsList = new ArrayList<>();

        mAdapter = new TripsSwipeItemsAdapter(activity, mItemsList);

        adapterClickListener();

        mAdapter.setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(mAdapter);

        my_trip_search_view=(SearchView) rootView.findViewById(R.id.my_trips_search_view);

        my_trips_empty_view = (LinearLayout) rootView.findViewById(R.id.companion_empty_view);

        searchListInitialization();

     //   upcomigTrips();
      //  return rootView;
    }

    private void searchListInitialization(){


    /*    my_trip_search_view.setIconifiedByDefault(true);
        my_trip_search_view.setIconified(true);
        my_trip_search_view.setQueryHint(getString(R.string.settings_search_hunt));
*/
        // mSearchView.setVisibility(View.GONE);
        my_trip_search_view.setOnQueryTextListener(this);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        // 5. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

    }


    private void adapterClickListener(){
        mAdapter.addClickeListeners(new ISwipeAdapterExecution(){

            @Override
            public void clickedItem(String solutionId) {
                //TODO check solotunion id

                ConnectionManager.getInstance(activity).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;
                        getActivityInterface().setTravelOrder(userTravelMainVO);
                        getFlowInterface().goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(), null);
                        Log.d("", "");
                        //TODO set Travel and got to current itenrary
                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(activity.getFragmentManager(), (String) data);

                    }
                });

            }

            @Override
            public void deleteClicked(final String solutionId) {
                //   String solutionId = mItemsList.get(position).getSolutionid();


                ConnectionManager.getInstance(activity).deleteItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

//                        mAdapter.notifyItemRemoved(position);
//                        mAdapter.notifyItemRangeChanged(position, mItemsList.size());

                        //TODO set Travel and got to current itenrary
                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(activity.getFragmentManager(), (String) data);

                    }
                });
            }
        });

    }



    public void updateTabsView(int tabPosition, Activity activity){
      /*  this.activity = activity;
        initViews();*/
        System.out.println("Kate TabViewMainFragment updateTabsView =  " + tabPosition );
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
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(activity.getFragmentManager(), (String) data);
            }
        });
    }

    private void historyTrips(){
        System.out.println("Kate history getActivity = " + getActivity());
        System.out.println("Kate history getContext = " + getContext());

        ConnectionManager.getInstance(activity).getMyTrips(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                mItemsList = (ArrayList<MyTripItem>) data;
                setListsVisability();
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(activity.getFragmentManager(), (String) data);
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
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError( (String) data);
                errorHelper.show(activity.getFragmentManager(), (String) data);
            }
        });
    }

    private void setListsVisability(){

        if(mItemsList.isEmpty()){
            my_trips_empty_view.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else{
            mCurrItemsList = new ArrayList<MyTripItem>(mItemsList);
            my_trips_empty_view.setVisibility(View.GONE);
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
