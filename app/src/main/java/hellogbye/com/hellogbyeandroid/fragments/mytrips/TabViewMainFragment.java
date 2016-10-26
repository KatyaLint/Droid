package hellogbye.com.hellogbyeandroid.fragments.mytrips;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.ISwipeAdapterExecution;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.myTripsSwipeAdapter.TripsSwipeItemsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;

import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 5/8/16.
 */
public class TabViewMainFragment extends HGBAbstractFragment implements SearchView.OnQueryTextListener{
    private ArrayList<MyTripItem> mItemsList;
    private TripsSwipeItemsAdapter mAdapter;
    private List<MyTripItem> mCurrItemsList;
    private Activity activity;
    private RecyclerView mRecyclerView;
    private LinearLayout my_trip_empty_view_ll;
    //private SearchReceiver mSearchReciever;
    private SearchView search_view;
    private ImageButton search_maginfy;
    private ImageButton toolbar_new_iternerary;
    private FontTextView titleBar;
    private Bundle args;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initSearchBar() {

        ImageView searchClose = (ImageView) search_view.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        TextView searchCloseText = (TextView) search_view.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchClose.setImageResource(R.drawable.close_icon_a_1);
        searchCloseText.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        searchCloseText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));

        search_maginfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchBar();
            }
        });

        search_view.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                closeSearchBar();
                return false;
            }
        });

    }

    private void closeSearchBar() {
        titleBar.setVisibility(View.VISIBLE);
        toolbar_new_iternerary.setVisibility(View.VISIBLE);
        search_maginfy.setVisibility(View.VISIBLE);
        search_view.setVisibility(View.GONE);

    }


    private void openSearchBar() {
        titleBar.setVisibility(View.GONE);
        toolbar_new_iternerary.setVisibility(View.GONE);
        search_maginfy.setVisibility(View.GONE);
        search_view.setVisibility(View.VISIBLE);
        search_view.setIconified(false);

    }

    public void initFragmentTabsView(View rootView){
        this.activity = getActivity();

         mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_trips_recycle_list);

    //    SearchView  my_trip_search_view=(SearchView) rootView.findViewById(R.id.my_trips_search_view);

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

        search_view =  ((MainActivityBottomTabs)getActivity()).getSearchView();
        search_maginfy =  ((MainActivityBottomTabs)getActivity()).getSearchMagifyImage();
        toolbar_new_iternerary =  ((MainActivityBottomTabs)getActivity()).getNewIternararyButton();
        titleBar =  ((MainActivityBottomTabs)getActivity()).getTitleBar();



        initSearchBar();
//        my_trip_search_view.setOnQueryTextListener(this);
        search_view.setOnQueryTextListener(this);
   //     mSearchReciever = new SearchReceiver();
        // 5. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        goToCNCView(rootView);

        args = new Bundle();
    }

/*    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mSearchReciever, new IntentFilter("search_query"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mSearchReciever);
    }*/

    private void goToCNCView(View rootView){
        FontTextView my_trips_go_to_cnc = (FontTextView) rootView.findViewById(R.id.my_trips_add_trip);
        my_trips_go_to_cnc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final Bundle bundle = new Bundle();
                bundle.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
                getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), bundle);
            }
        });
    }


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
        mAdapter.setTabPosition(tabPosition);
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

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_main, menu);
//        final MenuItem item = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setOnQueryTextListener(this);
//    }


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

/*    public class SearchReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO KATE I dont know how this is suppose to work.....
            if(intent.getStringExtra("query_type").equals("change")){
                String strChangeText = intent.getStringExtra("query");
                if(mCurrItemsList == null){ //list empty
                    return;
                }
                // Here is where we are going to implement our filter logic
                final List<MyTripItem> filteredModelList = filter(mCurrItemsList, strChangeText);
                mAdapter.animateTo(filteredModelList);
                mRecyclerView.scrollToPosition(0);

            } else if(intent.getStringExtra("query_type").equals("submit")){
                String strSubmitText = intent.getStringExtra("query");
            }

        }
    }*/


}
