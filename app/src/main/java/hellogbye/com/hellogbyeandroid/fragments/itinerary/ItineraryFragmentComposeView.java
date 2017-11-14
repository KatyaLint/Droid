package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.activities.RefreshComplete;
import hellogbye.com.hellogbyeandroid.fragments.ChangeTripName;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.TitleNameChange;
import hellogbye.com.hellogbyeandroid.fragments.cnc.CNCSignalRFragment;
import hellogbye.com.hellogbyeandroid.fragments.cnc.Information_Popup_View;
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 7/17/17.
 */

public class ItineraryFragmentComposeView extends HGBAbstractFragment implements TitleNameChange {

    private Activity activity;
    private float iScreenSize;
    private FontButtonView grid_make_payment;
    private int cellHieght;
    private FontTextView grid_total_price;

    private RelativeLayout continue_to_checkout_flight_baggage_rl;
    private ImageButton up_bar_favorite;
    private FontTextView itirnarary_title_Bar;
    private FontTextView grid_total_price_currency;
    private LinearLayout grid_price_ll;
    private ItineraryInitializationHotelsView itineraryInitializationHotelView;
    private ItineraryInitializationFlightView itineraryInitializationFlightView;

    private String nodeBeforeID = "";
    private ItineraryInitializationNodes itineraryInitializationNodes;
    private ItineraryFragmentBookedView itineraryFragmentBookedView;
    private boolean isBooked = false;
    private RadioGroup postbooking_tabs;
    private RadioButton proposed_itinerary;
    private RadioButton current_itenerary;
    private RelativeLayout continue_to_checkout_flight_baggage_booked_rl;


    public static Fragment newInstance(int position) {
        Fragment fragment = new ItineraryFragmentComposeView();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getFlowInterface().selectBottomBar(R.id.bb_menu_companions);
        getFlowInterface().enableFullScreen(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        itineraryInitializationHotelView = new ItineraryInitializationHotelsView();
        itineraryInitializationFlightView = new ItineraryInitializationFlightView();
        itineraryInitializationNodes = new ItineraryInitializationNodes();
   //     itineraryFragmentBookedView = new ItineraryFragmentBookedView();
        getFlowInterface().enableFullScreen(true);


        boolean isFreeUser = ((MainActivityBottomTabs)getActivity()).isFreeUser;

        final Information_Popup_View information_popup_view = new Information_Popup_View(getActivity(), getFlowInterface(), getActivityInterface(),
                new CNCSignalRFragment.IClearCNC() {
                    @Override
                    public void clearCNCScreen() {
                        //   clearCNCItems();
                    }
                }, isFreeUser);

        cellHieght = (int) getResources().getDimension(R.dimen.DP220);
        iScreenSize = getActivity().getResources().getDimension(R.dimen.DP335); //dp + activity.getResources().getDimension(R.dimen.DP50);

        activity = getActivity();


        View rootView = null;
       // View rootViewBooked;

        boolean isProposed = getArguments().getBoolean(HGBConstants.BUNDLE_IS_PROPOSED_ITINERARY);

        boolean isBookedVersionExist = getActivityInterface().getTravelOrder().getmHasbookedversion();

        if(!isBookedVersionExist){
            rootView = inflater.inflate(R.layout.new_grid_main_table, container, false);
            initializeRegularView(rootView);
            View new_grid_post_booking = (View)rootView.findViewById(R.id.new_grid_post_booking_booked);
            new_grid_post_booking.setVisibility(View.GONE);
            createItinenaryView(rootView, false);
        }else
        {
           rootView = manageBookingView( rootView,  inflater,  container,  isProposed,  isBookedVersionExist);
        }


        ImageButton newIteneraryImageButton = ((MainActivityBottomTabs) getActivity()).getToolbar_new_iterneraryCnc();
        newIteneraryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information_popup_view.freeUserPopUp(false);


            }
        });

   /*     ImageButton toolbar_new_iternerary_cnc = ((MainActivityBottomTabs) getActivity()).getToolbar_new_iterneraryCnc();
        System.out.println("Kate toolbar_new_iternerary_cnc = " + toolbar_new_iternerary_cnc);
        toolbar_new_iternerary_cnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Kate toolbar_new_iternerary_cnc");
                information_popup_view.freeUserPopUp();
            }
        });*/


        titleChangeName();
        return rootView;
    }

    private View manageBookingView(View rootView, LayoutInflater inflater, ViewGroup container, boolean isProposed, boolean isBookedVersionExist){
        View new_grid_post_booking;

        if(isBookedVersionExist && !isProposed){

            rootView = inflater.inflate(R.layout.new_grid_main_table, container, false);
            initializeRegularView(rootView);

            createItinenaryView(rootView, false);
            new_grid_post_booking = (View)rootView.findViewById(R.id.new_grid_post_booking_booked);
            new_grid_post_booking.setVisibility(View.VISIBLE);
            postbooking_tabs = (RadioGroup)new_grid_post_booking.findViewById(R.id.postbooking_tabs);
            proposed_itinerary = (RadioButton) new_grid_post_booking.findViewById(R.id.proposed_itinerary_booking);
            current_itenerary = (RadioButton) new_grid_post_booking.findViewById(R.id.current_itenerary_booking);
            // postbooking_tabs.setOnCheckedChangeListener(onCheckedListener);

            //initializeRadioBtnPostbooking(rootView);

        }else{
            rootView = inflater.inflate(R.layout.new_grid_main_table_booked, container, false);

            // rootView = inflater.inflate(R.layout.new_grid_main_table_booked, container, false);
            initializeBookedView(rootView);
            new_grid_post_booking = (View)rootView.findViewById(R.id.new_grid_post_booking_booked);
            new_grid_post_booking.setVisibility(View.VISIBLE);
            postbooking_tabs = (RadioGroup)new_grid_post_booking.findViewById(R.id.postbooking_tabs);

            // initializeRadioBtnPostbooking(rootView);
            createItinenaryView(rootView, true);
        }
        proposed_itinerary = (RadioButton) new_grid_post_booking.findViewById(R.id.proposed_itinerary_booking);
        current_itenerary = (RadioButton) new_grid_post_booking.findViewById(R.id.current_itenerary_booking);
        postbooking_tabs.setOnCheckedChangeListener(onCheckedListener);
        if(prevCheckedId == 0){
            proposed_itinerary.setTextColor(ContextCompat.getColor(getContext(),R.color.COLOR_00516f));
            current_itenerary.setTextColor(ContextCompat.getColor(getContext(),R.color.COLOR_WHITE));
            postbooking_tabs.check(R.id.proposed_itinerary_booking);

        }else {
            if(current_itenerary.getId() == prevCheckedId){
                proposed_itinerary.setTextColor(ContextCompat.getColor(getContext(),R.color.COLOR_00516f));
                current_itenerary.setTextColor(ContextCompat.getColor(getContext(),R.color.COLOR_WHITE));
            }else{
                proposed_itinerary.setTextColor(ContextCompat.getColor(getContext(),R.color.COLOR_WHITE ));
                current_itenerary.setTextColor(ContextCompat.getColor(getContext(),R.color.COLOR_00516f));
            }
            postbooking_tabs.check(prevCheckedId);
        }
        return rootView;
    }


    private static int prevCheckedId;
    RadioGroup.OnCheckedChangeListener onCheckedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            Bundle arg = new Bundle();
            if(checkedId == prevCheckedId){
                return;
            }

            prevCheckedId = checkedId;
            switch (checkedId) {
                case R.id.proposed_itinerary_booking:
                    proposed_itinerary.setTextColor(ContextCompat.getColor(getContext(),R.color.COLOR_WHITE));
                    current_itenerary.setTextColor(ContextCompat.getColor(getContext(),R.color.COLOR_00516f));
                    arg.putBoolean(HGBConstants.BUNDLE_IS_PROPOSED_ITINERARY, true);
                    getFlowInterface().goToFragment(ToolBarNavEnum.ITINERARY.getNavNumber(),arg);
                    break;
                case R.id.current_itenerary_booking:
                    proposed_itinerary.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_00516f));
                    current_itenerary.setTextColor(ContextCompat.getColor(getContext(),R.color.COLOR_WHITE ));
                    arg.putBoolean(HGBConstants.BUNDLE_IS_PROPOSED_ITINERARY, false);
                    getFlowInterface().goToFragment(ToolBarNavEnum.ITINERARY.getNavNumber(),arg);
                    break;
            }

        }
    };


    private void initializeBookedView(View bookedView){
        grid_total_price = (FontTextView)bookedView.findViewById(R.id.grid_total_price_booked);
        grid_total_price_currency = (FontTextView)bookedView.findViewById(R.id.grid_total_price_currency_booked);
        continue_to_checkout_flight_baggage_booked_rl = (RelativeLayout)bookedView.findViewById(R.id.continue_to_checkout_flight_baggage_booked_rl);
        continue_to_checkout_flight_baggage_booked_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceInformationPopup();
            }
        });

        grid_price_ll = (LinearLayout)bookedView.findViewById(R.id.grid_price_ll_booked);
        grid_make_payment = (FontButtonView)bookedView.findViewById(R.id.grid_make_payment_booked);
        grid_make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivityBottomTabs)getActivity()). getTravellersInfoWithSolutionId(new RefreshComplete() {
                    @Override
                    public void onRefreshSuccess(Object data) {
                        getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVELERS.getNavNumber(), null);
                    }

                    @Override
                    public void onRefreshError(Object data) {

                    }
                });


//                ConnectionManager.getInstance(getActivity()).getTravellersInforWithSolutionId(getActivityInterface().getTravelOrder().getmSolutionID(),
//                        new ConnectionManager.ServerRequestListener() {
//                            @Override
//                            public void onSuccess(Object data) {
//                                getFlowInterface().setListUsers((ArrayList<UserProfileVO>) data);
//                                getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVELERS.getNavNumber(), null);
//                            }
//                            @Override
//                            public void onError(Object data) {
//                                ErrorMessage(data);
//                            }
//                        });
            }
        });

    }

    private void initializeRegularView(View rootView){
        grid_total_price = (FontTextView)rootView.findViewById(R.id.grid_total_price);
        grid_total_price_currency = (FontTextView)rootView.findViewById(R.id.grid_total_price_currency);
        continue_to_checkout_flight_baggage_rl = (RelativeLayout) rootView.findViewById(R.id.continue_to_checkout_flight_baggage_rl);
        continue_to_checkout_flight_baggage_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceInformationPopup();
            }
        });

        grid_price_ll = (LinearLayout)rootView.findViewById(R.id.grid_price_ll);
        grid_make_payment = (FontButtonView)rootView.findViewById(R.id.grid_make_payment);
        grid_make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivityBottomTabs)getActivity()). getTravellersInfoWithSolutionId(new RefreshComplete() {
                    @Override
                    public void onRefreshSuccess(Object data) {
                        getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVELERS.getNavNumber(), null);
                    }

                    @Override
                    public void onRefreshError(Object data) {

                    }
                });
//                ConnectionManager.getInstance(getActivity()).getTravellersInforWithSolutionId(getActivityInterface().getTravelOrder().getmSolutionID(),
//                        new ConnectionManager.ServerRequestListener() {
//                            @Override
//                            public void onSuccess(Object data) {
//                                getFlowInterface().setListUsers((ArrayList<UserProfileVO>) data);
//                                getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVELERS.getNavNumber(), null);
//                            }
//                            @Override
//                            public void onError(Object data) {
//                                ErrorMessage(data);
//                            }
//                        });
            }
        });

    }






    @Override
    public void titleChangeName() {
        ChangeTripName.onClickListener(getActivity(), getActivityInterface().getSolutionID(), getActivityInterface().getTravelOrder());
    }

    private void createItinenaryView(View mainViewLayout, boolean isBookedView){


        isBooked = isBookedView;
        //LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // View view = inflater.inflate(R.layout.new_grid_main_adapter_layout, null);


        HorizontalScrollView passenger_name_sc ;//= (HorizontalScrollView)view.findViewById(R.id.passenger_name_sc);//((MainActivityBottomTabs)getActivity()).getItitneraryHS();
        LinearLayout scrollViewLinearLayout ;//= (LinearLayout)view.findViewById(R.id.new_grid_main_ll);
        HorizontalScrollView horizontal;// = (HorizontalScrollView)view.findViewById(R.id.table_scroll);

        LinearLayout itineraryLayout ;// = (LinearLayout)scrollViewLinearLayout.findViewById(R.id.scroll_view_ll);
        LinearLayout cnc_empty_view ;//= (LinearLayout)scrollViewLinearLayout.findViewById(R.id.cnc_empty_view);
        UserTravelMainVO  user;// = getActivityInterface().getTravelOrder();
        if(isBookedView){ // booked , show two views

            passenger_name_sc = (HorizontalScrollView)mainViewLayout.findViewById(R.id.passenger_name_sc_booked);

            scrollViewLinearLayout = (LinearLayout)mainViewLayout.findViewById(R.id.new_grid_main_ll_adapter_booked);

            horizontal = (HorizontalScrollView)mainViewLayout.findViewById(R.id.table_scroll_booked);

            itineraryLayout = (LinearLayout)scrollViewLinearLayout.findViewById(R.id.scroll_view_booking_ll);
            cnc_empty_view = (LinearLayout)scrollViewLinearLayout.findViewById(R.id.cnc_empty_booking_view);
            user = getActivityInterface().getBookedTravelOrder();


        }else {
            passenger_name_sc = (HorizontalScrollView)mainViewLayout.findViewById(R.id.passenger_name_sc);
            scrollViewLinearLayout = (LinearLayout)mainViewLayout.findViewById(R.id.new_grid_main_ll_adapter);

            horizontal = (HorizontalScrollView)mainViewLayout.findViewById(R.id.table_scroll);

            itineraryLayout = (LinearLayout)scrollViewLinearLayout.findViewById(R.id.scroll_view_ll);
            cnc_empty_view = (LinearLayout)scrollViewLinearLayout.findViewById(R.id.cnc_empty_view);
            user = getActivityInterface().getTravelOrder();
        }


        syncScrolls(horizontal, passenger_name_sc);


        if(user != null && !user.getItems().isEmpty()) {

            setSolutionNameForItirnarary(false, user.getmSolutionName(), user.getmSolutionID());
            itineraryLayout.setVisibility(View.VISIBLE);
            itineraryInitializationFlightView.createPassengersName(mainViewLayout, user, getActivity(),isBookedView);
            itineraryInitializationNodes.createMainNodes(user);
            itineraryInitializationNodes.makeEqualAllNodes(user);
            View mainView = createGridView(user, isBookedView);
            itineraryLayout.addView(mainView);
            cnc_empty_view.setVisibility(View.GONE);
            grid_price_ll.setVisibility(View.VISIBLE);
            grid_make_payment.setEnabled(true);
            grid_total_price.setText("$" + HGBUtility.roundNumber(Double.parseDouble(user.getmTotalPrice())));
            UserProfileVO currentUser = getActivityInterface().getCurrentUser();
            grid_total_price_currency.setText(currentUser.getCurrency());

        }else{
            setSolutionNameForItirnarary(true, "", "");// server returning wrong data
            itineraryLayout.setVisibility(View.GONE);
            cnc_empty_view.setVisibility(View.VISIBLE);
            grid_make_payment.setEnabled(false);
            grid_price_ll.setVisibility(View.INVISIBLE);

        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void syncScrolls(final HorizontalScrollView currentView, final HorizontalScrollView otherView) {


        currentView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                otherView.scrollTo(scrollX, scrollY);
            }
        });

        otherView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                currentView.scrollTo(scrollX,scrollY);
            }
        });
    }




    private void priceInformationPopup(){
        LayoutInflater li = LayoutInflater.from(getContext());
        final  View popupView = li.inflate(R.layout.popup_flight_baggage_info, null);
        FontTextView popup_flight_baggage_text = (FontTextView) popupView.findViewById(R.id.popup_flight_baggage_text);
        String currency = getActivityInterface().getCurrentUser().getCurrency();
        String text = String.format(getActivity().getResources().getString(R.string.popup_flight_baggage_info_text),currency );
        popup_flight_baggage_text.setText(text);
        HGBUtility.showAlertPopUpOneButton(getActivity(),  null, popupView,
                null, null, true);
    }

    private void setSolutionNameForItirnarary(boolean isEmpty, String solutionName, String solutionID) {
        itirnarary_title_Bar = ((MainActivityBottomTabs)getActivity()).getItirnaryTitleBar();
        if(isEmpty){
            itirnarary_title_Bar.setVisibility(View.INVISIBLE);
            return;
        }
        // String solutionName = userOrder.getmSolutionName();
        if(solutionName != null) {
            itirnarary_title_Bar.setText(solutionName);
        }
        itirnarary_title_Bar.setTag(solutionID);
    }


    private LinearLayout createGridView(UserTravelMainVO user, boolean isBookedView){

        ArrayList<PassengersVO> passengers = user.getPassengerses();

        LinearLayout MainLinearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        LinearLayout.LayoutParams MarginLLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        MainLinearLayout.setOrientation(LinearLayout.VERTICAL);

        if(passengers.size() < 1){
            return MainLinearLayout;
        }

        HashMap<String, ArrayList<NodesVO>> hashMapPassangers = passengers.get(0).getHashMap();
        Set<String> dates = hashMapPassangers.keySet();

        ArrayList<String> list = new ArrayList(dates);
        Collections.sort(list);

        String priviouseDate="";
        String currentDate="";

        int counter =0 ;
        for(String date : list) { //run for dates insteadof for users
            //new horizontal
            LinearLayout DatesLinearLayout = new LinearLayout(activity);
            DatesLinearLayout.setLayoutParams(LLParams);
            DatesLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            currentDate = date;



            for(int i=0;i<passengers.size();i++){

                HashMap<String, ArrayList<NodesVO>> hashMap = passengers.get(i).getHashMap();
                ArrayList<NodesVO> passengerNode = hashMap.get(date);


            /*    ArrayList<NodesVO> passengerNodes = passengers.get(i).getPassengerNodes();
                passengerNode = getNodesWithSameDate(date,passengerNodes);*/


                LinearLayout NodeLinearLayout = new LinearLayout(activity);
                NodeLinearLayout.setLayoutParams(MarginLLParams);
                NodeLinearLayout.setOrientation(LinearLayout.VERTICAL);


                //TODO check if there a difference of colors
               /* if(i%2 == 0) {
                    NodeLinearLayout.setBackgroundColor(getResources().getColor(R.color.COLOR_F5F5F5));
                }else{
                    NodeLinearLayout.setBackgroundColor(getResources().getColor(R.color.COLOR_F5F5F5));
                }*/

                for (NodesVO node : passengerNode) {
                    View view;
                    if (node.isEmpty()) {
                        view = emptyLayout(isBookedView);
                        view.setTag(NodeTypeEnum.EMPTY.getType());
                    } else if (NodeTypeEnum.FLIGHT.getType().equals(node.getmType())) {
                        //create flight


                        view =  itineraryInitializationFlightView.flightLayout(node, getActivity(), nodeBeforeID, getActivityInterface());
                        view.setTag(NodeTypeEnum.FLIGHT.getType() +"," + node.getAccountID()+","+node.getmGuid());
                        counter  = counter + 1;

                    } else {
                        counter = 0;
                        //create hotel

                        view = itineraryInitializationHotelView.hotelLayout(node,getActivity());//hotelLayout(node,counter);
                        view.setTag(NodeTypeEnum.HOTEL.getType()+"," + node.getAccountID()+"," + node.getmGuid());
                    }
                    view.setOnClickListener(nodeClickListener);
                    //   view.setLayoutParams(nodeParams);

                    NodeLinearLayout.addView(view); //add node view

                    view.setPadding(
                            (int) getResources().getDimension(R.dimen.DP6),
                            (int) getResources().getDimension(R.dimen.DP6),
                            (int) getResources().getDimension(R.dimen.DP6),
                            (int) getResources().getDimension(R.dimen.DP6));

                }
                DatesLinearLayout.addView(NodeLinearLayout);

            }
            //   counter ++;

            if(!priviouseDate.equals(currentDate)){
                View dateView = dateLayout(currentDate, isBookedView);
                //        dateView.setLayoutParams(LLParams);
                MainLinearLayout.addView(dateView);
                priviouseDate = currentDate;
            }

      /*      View dateView = dateLayout(date);
            //        dateView.setLayoutParams(LLParams);
            MainLinearLayout.addView(dateView);*/

            //new date
            MainLinearLayout.addView(DatesLinearLayout); //add all date row
        }
        return MainLinearLayout;

    }

    private View emptyLayout(boolean isBookedView) {
        View child;// = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_empty_item_booked, null);
        LinearLayout innerWhiteLayout ;//= (LinearLayout)child.findViewById(R.id.white_squer_ll_booked);
        if(isBookedView){
             child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_empty_item_booked, null);
             innerWhiteLayout = (LinearLayout)child.findViewById(R.id.white_squer_ll_booked);
        }else{
             child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_empty_item, null);
             innerWhiteLayout = (LinearLayout)child.findViewById(R.id.white_squer_ll);
        }
        //View child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_empty_item_booked, null);


        LinearLayout.LayoutParams outerLayoutParams = new LinearLayout.LayoutParams((int)iScreenSize,cellHieght);
        //LinearLayout innerWhiteLayout = (LinearLayout)child.findViewById(R.id.white_squer_ll_booked);
        innerWhiteLayout.setLayoutParams(outerLayoutParams);

        LinearLayout outer = new LinearLayout(activity);
        outer.setOrientation(LinearLayout.VERTICAL);
        outer.addView(child);

        return outer;
    }

    private View.OnClickListener nodeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String[] nodeTypeStr =  view.getTag().toString().split(",");
            String nodeType = nodeTypeStr[0];
            String guidSelectedUser  = nodeTypeStr[1];
            String guidSelectedItem = nodeTypeStr[2];

            //                NodeTypeEnum.FLIGHT.getType();
            if (NodeTypeEnum.FLIGHT.getType().equals(nodeType)){

                selectedItemGuidNumber(guidSelectedItem);
                selectedUserGuidNumber(guidSelectedUser);
                //getFlowInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_ROUND_TRIP.getNavNumber(),null);
                getFlowInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_FACTORY.getNavNumber(),null);

            }else if (NodeTypeEnum.HOTEL.getType().equals(nodeType)){

                selectedItemGuidNumber(guidSelectedItem);
                selectedUserGuidNumber(guidSelectedUser);
                getFlowInterface().goToFragment(ToolBarNavEnum.HOTEL.getNavNumber(),null);
            }

        }
    };

    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    /**
     * Create dates layout, the linear layout of dates
     * @param date
     * @param isBookedView
     * @return dates layout
     */
    private LinearLayout dateLayout(String date, boolean isBookedView){
        View child ;//= activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_date_item, null);
        FontTextView date_text_layout;// = (FontTextView) child.findViewById(R.id.date_text_layout);
        if(isBookedView){
             child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_date_item_booked, null);
             date_text_layout = (FontTextView) child.findViewById(R.id.date_text_layout_booked);
        }else{
             child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_date_item, null);
             date_text_layout = (FontTextView) child.findViewById(R.id.date_text_layout);
        }


//        FontTextView date_text_layout = (FontTextView) child.findViewById(R.id.date_text_layout);
        //  String correctDate = HGBUtility.parseDateFromddMMyyyyToddmmYYYY(date);
        String correctDate  = HGBUtilityDate.parseDateFromddMMyyyyToddmmYYYY(date);
        date_text_layout.setText(correctDate.toUpperCase());
        LinearLayout outer = new LinearLayout(activity);
        outer.setOrientation(LinearLayout.VERTICAL);
        outer.setLayoutParams(layoutParams);
        outer.addView(child);

        return outer;
    }


    @Override
    public void onDestroy() {
        getActivityInterface().setAlternativeFlights(null);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        UserTravelMainVO userOrder = getActivityInterface().getTravelOrder();
        if (userOrder != null) {
            ArrayList<PassengersVO> passangers = userOrder.getPassengerses();
            for (PassengersVO passanger : passangers) {
                passanger.clearData();
            }
        }

        UserTravelMainVO userOrderBooked = getActivityInterface().getBookedTravelOrder();
        if (userOrderBooked != null) {
            ArrayList<PassengersVO> passangers = userOrderBooked.getPassengerses();
            for (PassengersVO passanger : passangers) {
                passanger.clearData();
            }
        }



        super.onDestroyView();
    }
}
