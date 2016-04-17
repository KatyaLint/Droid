//package hellogbye.com.hellogbyeandroid.fragments;
//
//import android.app.Fragment;
//import android.os.Bundle;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.LinearLayout;
//import android.widget.SearchView;
//import android.widget.Toast;
//
//import com.hudomju.swipe.SwipeToDismissTouchListener;
//import com.hudomju.swipe.adapter.ListViewAdapter;
//import com.hudomju.swipe.adapter.RecyclerViewAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import hellogbye.com.hellogbyeandroid.R;
//import hellogbye.com.hellogbyeandroid.activities.MainActivity;
//import hellogbye.com.hellogbyeandroid.adapters.MyTripPinnedAdapter;
//import hellogbye.com.hellogbyeandroid.models.MyTripItem;
//import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
//import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
//import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
//import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
//import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
//import hellogbye.com.hellogbyeandroid.views.FontTextView;
//import hellogbye.com.hellogbyeandroid.views.PinnedHeaderListView;
//
//
///**
// * Created by arisprung on 8/17/15.
// */
//public class MyTripsFragment extends HGBAbtsractFragment {
//
//    private PinnedHeaderListView stickyList;
//    private  ArrayList<MyTripItem> mItemsList;
//    private  ArrayList<MyTripItem> mCurrItemsList;
//    private OnItemClickListener editMyTripsClickCB;
//    private MyTripPinnedAdapter sectionedAdapter;
//    private FontTextView my_trips_upcoming;
//    private FontTextView my_trips_favorites;
//    private FontTextView my_trips_paid;
//    private SearchView my_trip_search_view;
//    private LinearLayout my_trips_empty_view;
//
//
//    public static Fragment newInstance(int position) {
//        Fragment fragment = new MyTripsFragment();
//        Bundle args = new Bundle();
//        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.my_trips_layout, container, false);
//        return rootView;
//    }
//
//    public void setEditMyTripsClickCB(OnItemClickListener editMyTripsClickCB) {
//        this.editMyTripsClickCB = editMyTripsClickCB;
//    }
//
//    public interface OnItemClickListener {
//        void onItemEditMyTripsClick(String guid);
//    }
//
//    private void setupSearchView() {
//        my_trip_search_view.setIconifiedByDefault(true);
////        my_trip_search_view.setFocusable(false);
//        my_trip_search_view.setIconified(true);
////        my_trip_search_view.requestFocusFromTouch();
//
//      //  my_trip_search_view.setIconifiedByDefault(false);
//      //  my_trip_search_view.setSubmitButtonEnabled(false);
//       my_trip_search_view.setQueryHint(getString(R.string.settings_search_hunt));
//    }
//
//    private void initialize(View view){
//        my_trips_upcoming = (FontTextView)view.findViewById(R.id.my_trips_upcoming);
//        my_trips_upcoming.setOnClickListener(paidTripsClickListener);
//
//        my_trips_favorites = (FontTextView)view.findViewById(R.id.my_trips_favorites);
//        my_trips_favorites.setOnClickListener(favoriteTripClickListener);
//
//        my_trips_paid = (FontTextView)view.findViewById(R.id.my_trips_paid);
//        my_trips_paid.setOnClickListener(historyTripClickListener);
//
//        my_trip_search_view=(SearchView) view.findViewById(R.id.my_trip_search_view);
//
//
//        setupSearchView();
//        my_trip_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//            //    mCurrItemsList.addAll(mItemsList);
//                final List<MyTripItem> filteredModelList = filter(mCurrItemsList, newText);
//                sectionedAdapter.animateTo(filteredModelList);
//                return false;
//            }
//        });
//    }
//
//    private List<MyTripItem> filter(List<MyTripItem> models, String query) {
//        final List<MyTripItem> filteredModelList = new ArrayList<>();
//        if(models == null){
//            return filteredModelList;
//        }
//        query = query.toLowerCase();
//
//
//        for (MyTripItem model : models) {
//            final String text = model.getName().toLowerCase();
//            if (text.contains(query)) {
//                filteredModelList.add(model);
//            }
//        }
//        return filteredModelList;
//    }
//
//
//    private void createCityImageUrl(ArrayList<MyTripItem> mItemsList){
//        for (MyTripItem mItemList: mItemsList) {
//            mItemList.setUrlToCityView("");
//        }
//    }
//
//
//    private void getAllMyTrips(){
//        ConnectionManager.getInstance(getActivity()).getMyTrips(new ConnectionManager.ServerRequestListener() {
//            @Override
//            public void onSuccess(Object data) {
//
//                mItemsList = (ArrayList<MyTripItem>) data;
//                setListsVisability();
////                createCityImageUrl(mItemsList);
////                mCurrItemsList = new ArrayList<MyTripItem>(mItemsList);
////
////                sectionedAdapter = new MyTripPinnedAdapter(mItemsList, getActivity());
////                sectionedAdapter.setMaxCurrentInitialization(1);
////                stickyList.setAdapter(sectionedAdapter);
//
//            }
//
//            @Override
//            public void onError(Object data) {
//                HGBErrorHelper errorHelper = new HGBErrorHelper();
//                errorHelper.setMessageForError((String) data);
//                errorHelper.show(getFragmentManager(), (String) data);
//            }
//        });
//    }
//
//    private View.OnClickListener favoriteTripClickListener = new View.OnClickListener(){
//        @Override
//        public void onClick(View view) {
//            ConnectionManager.getInstance(getActivity()).getMyTripsFavorite(new ConnectionManager.ServerRequestListener() {
//                @Override
//                public void onSuccess(Object data) {
//                    mItemsList = (ArrayList<MyTripItem>) data;
//                    setListsVisability();
//                }
//
//                @Override
//                public void onError(Object data) {
//                    HGBErrorHelper errorHelper = new HGBErrorHelper();
//                    errorHelper.setMessageForError( (String) data);
//                    errorHelper.show(getFragmentManager(), (String) data);
//                }
//            });
//        }
//    };
//
//    private View.OnClickListener historyTripClickListener = new View.OnClickListener(){
//        @Override
//        public void onClick(View view) {
//            ConnectionManager.getInstance(getActivity()).getMyTrips(new ConnectionManager.ServerRequestListener() {
//                @Override
//                public void onSuccess(Object data) {
//
//                    mItemsList = (ArrayList<MyTripItem>) data;
//                    setListsVisability();
//                }
//
//                @Override
//                public void onError(Object data) {
//                    HGBErrorHelper errorHelper = new HGBErrorHelper();
//                    errorHelper.setMessageForError((String) data);
//                    errorHelper.show(getFragmentManager(), (String) data);
//                }
//            });
//
//        }
//
//    };
//
//
//    private void setListsVisability(){
//        if(mItemsList.isEmpty()){
//            my_trips_empty_view.setVisibility(View.VISIBLE);
//            stickyList.setVisibility(View.GONE);
//        }else {
//
//            my_trips_empty_view.setVisibility(View.GONE);
//            stickyList.setVisibility(View.VISIBLE);
//
//            createCityImageUrl(mItemsList);
//            sectionedAdapter.addItems(mItemsList);
//            sectionedAdapter.setMaxCurrentInitialization(0);
//            sectionedAdapter.notifyDataSetChanged();
//        }
//    }
//
//    private void upcomigTrips(){
//        ConnectionManager.getInstance(getActivity()).getMyTripsPaid(new ConnectionManager.ServerRequestListener() {
//            @Override
//            public void onSuccess(Object data) {
//                mItemsList = (ArrayList<MyTripItem>) data;
//                setListsVisability();
//            }
//
//            @Override
//            public void onError(Object data) {
//                HGBErrorHelper errorHelper = new HGBErrorHelper();
//                errorHelper.setMessageForError((String) data);
//                errorHelper.show(getFragmentManager(), (String) data);
//            }
//        });
//    }
//
//    private View.OnClickListener paidTripsClickListener = new View.OnClickListener(){
//        @Override
//        public void onClick(View view) {
//            upcomigTrips();
//        }
//    };
//
//
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        initialize(view);
//
//
//
//
//        stickyList = (PinnedHeaderListView) view.findViewById(R.id.pinnedListView);
//        my_trips_empty_view = (LinearLayout)view.findViewById(R.id.my_trips_empty_view);
//        mItemsList = new ArrayList<>();
//        sectionedAdapter = new MyTripPinnedAdapter(mItemsList, getActivity());
//        sectionedAdapter.setMaxCurrentInitialization(1);
//        stickyList.setAdapter(sectionedAdapter);
//     //  getAllMyTrips();
//        upcomigTrips();
//
//        stickyList.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
//
////                if(sectionedAdapter.isEditMode()){
////                    return;
////                }
//
//                if (section == 0) {
//                    //TODO go to current itinerary
//
//                } else if (section == 1) {
//
//                    String solutionId = mItemsList.get(position).getSolutionid();
//                    ConnectionManager.getInstance(getActivity()).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;
//                            getActivityInterface().setTravelOrder(userTravelMainVO);
//                            getFlowInterface().goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(), null);
//                            Log.d("", "");
//                            //TODO set Travel and got to current itenrary
//                        }
//
//                        @Override
//                        public void onError(Object data) {
//                            HGBErrorHelper errorHelper = new HGBErrorHelper();
//                            errorHelper.setMessageForError((String) data);
//                            errorHelper.show(getFragmentManager(), (String) data);
//                            Log.e("MainActivity", "Problem updating grid  " + data);
//                        }
//                    });
//
//                }
//
//            }
//
//            @Override
//            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
//
//            }
//        });
//
// //       swipeToDessmiss();
//
////        ((MainActivity) getActivity()).setEditMyTripsClickCB(new OnItemClickListener() {
////            @Override
////            public void onItemEditMyTripsClick(String option) {
////
////                if(sectionedAdapter == null){
////                    return;
////                }
////                if(option.equalsIgnoreCase("edit")) {
////                    sectionedAdapter.isEditMode(true);
////                }else{
////                    sectionedAdapter.isEditMode(false);
////                }
////                sectionedAdapter.notifyDataSetChanged();
////            }
////        });
//
//    }
//}
