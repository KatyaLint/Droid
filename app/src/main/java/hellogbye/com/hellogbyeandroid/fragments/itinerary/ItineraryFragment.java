package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import android.app.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.HotelFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

import hellogbye.com.hellogbyeandroid.views.FontTextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItineraryFragment extends HGBAbstractFragment {


    private UserTravelMainVO userOrder;
    private Activity activity;
    private float iScreenSize;
    private Button grid_make_payment;
    private int cellHieght;

    public ItineraryFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new ItineraryFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }



    public interface TravelerShowChoose {
        void itemSelected(String guidSelectedItem, String itemType,String guidSelectedUser);
    }

    private UserTravelMainVO parseFlight(){
        UserTravelMainVO airplaneDataVO = null;
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<UserTravelMainVO>() {
            }.getType();
            String strJson = HGBUtility.loadJSONFromAsset("flights_three_person.txt", getActivity());

            airplaneDataVO = gson.fromJson(strJson, type);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return airplaneDataVO;
    }

    /**
     * Create passanger names layout
     * @param scrollViewLinearLayout
     * @param user
     */
    private void createPassengersName(View scrollViewLinearLayout, UserTravelMainVO user){

    LinearLayout passengersNames = (LinearLayout)scrollViewLinearLayout.findViewById(R.id.passangers_names_ll);
    Typeface textFont = Typeface.createFromAsset(activity.getAssets(), "fonts/" + "dinnextltpro_regular.otf");

    LayoutParams params = new LayoutParams((int) iScreenSize,LayoutParams.WRAP_CONTENT);
    params.gravity = Gravity.CENTER_VERTICAL;
    ArrayList<PassengersVO> passengers = user.getPassengerses();
    for (PassengersVO passenger:passengers){
        FontTextView textView = new FontTextView(activity);

        textView.setTextAppearance(activity, R.style.GridViewPassangersTextStyle);
        textView.setText(passenger.getmName());
        textView.setGravity(Gravity.CENTER);
     //   textView.setTextSize(R.dimen.SP16);
    //    LayoutParams params = new LayoutParams((int) getResources().getDimension(R.dimen.DP150),LayoutParams.WRAP_CONTENT); //width 150

        textView.setLayoutParams(params);
        textView.setTypeface(textFont);
        passengersNames.addView(textView);
    }

        LayoutInflater li = LayoutInflater.from(activity);
        View promptsView = li.inflate(R.layout.new_grid_add_companion, null);

        LinearLayout new_grid_add_companion_ll = (LinearLayout) promptsView.findViewById(R.id.new_grid_add_companion_ll);
        new_grid_add_companion_ll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();

                args.putString("class_name", ItineraryFragment.class.toString());
                getFlowInterface().goToFragment(ToolBarNavEnum.ALL_COMPANIONS_VIEW.getNavNumber(), args);
            }
        });
        passengersNames.addView(promptsView);
    }





    private void createMainNodes(UserTravelMainVO user){
        Map<String, NodesVO> items = user.getItems();

        ArrayList<PassengersVO> passangers = user.getPassengerses();
        ArrayList<NodesVO> nodesVOs = new ArrayList<>();

        String arrival = "";
        String departure = "";
        for (PassengersVO passenger: passangers){
            ArrayList<NodesVO> passengerNodesVOs = new ArrayList<>();
            ArrayList<String> ItineraryItems = passenger.getmItineraryItems();
             for (String itineraryItem :ItineraryItems){

                NodesVO node = items.get(itineraryItem);

                if(NodeTypeEnum.FLIGHT.getType().equals(node.getmType())){
                     departure = node.getmDeparture();
                     arrival = node.getmArrival();

                }else if(NodeTypeEnum.HOTEL.getType().equals(node.getmType())){
                     departure = node.getmCheckIn();
                     arrival = node.getmCheckOut();
                }

                long difference = HGBUtility.dayDifference(departure,arrival) ;


                departure = HGBUtility.parseDateToEEEMMMDyy(departure); //parse return 23 is 2 different scenario need time not just date
           //      HGBUtility.parseDateToddMMyyyyMyTrip(departure);
                 if(node.getmType().equals(NodeTypeEnum.HOTEL.getType())){
                     String time = HGBUtility.addDayHourToDate(departure);
                     node.setDateOfCell(time);
                 }else {
                     node.setDateOfCell(departure);
                 }
               //  node.setDateOfCell(departure);
                node.setUserName(passenger.getmName());
                node.setAccountID(passenger.getmPaxguid());
                nodesVOs.add(node);

                correctPassangerNodes(node, passengerNodesVOs,  passenger);


                for(int i=1;i<=difference;i++){ //adding days, if hotel is 3 day, adding another 2
                 //   String time = HGBUtility.parseDateToddMMyyyyForPayment(departure);
                    departure =  HGBUtility.addDayToDate(departure);

                    node.setUserName(passenger.getmName());
                    node.setDateOfCell(departure);
                    correctPassangerNodes(node, passengerNodesVOs,  passenger);
                    nodesVOs.add(node);

                }

            }

            if(passengerNodesVOs == null || passengerNodesVOs.get(0) == null){
                //TODO chack why!!!!!
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError("Bad Case Scenario");
            }
           // passenger.addToPassengerNodeVOS(nodesVOs);

            passenger.setDateHashMap(passengerNodesVOs.get(0).getDateOfCell(), passengerNodesVOs);
        }
    }


    private LinearLayout createGridView(UserTravelMainVO user){

        ArrayList<PassengersVO> passengers = user.getPassengerses();


        LinearLayout MainLinearLayout = new LinearLayout(activity);
        LayoutParams LLParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);

//        LayoutParams MarginLLParams = new LayoutParams((int) getResources().getDimension(R.dimen.DP250),LayoutParams.MATCH_PARENT);

        LayoutParams MarginLLParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);


        // MainLinearLayout.setLayoutParams(LLParams);
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
        //    currentDate = HGBUtility.parseDateToddMMyyyyForPayment(date);

            for(int i=0;i<passengers.size();i++){

                HashMap<String, ArrayList<NodesVO>> hashMap = passengers.get(i).getHashMap();
                ArrayList<NodesVO> passengerNode = hashMap.get(date);


            /*    ArrayList<NodesVO> passengerNodes = passengers.get(i).getPassengerNodes();
                passengerNode = getNodesWithSameDate(date,passengerNodes);*/


                LinearLayout NodeLinearLayout = new LinearLayout(activity);
                NodeLinearLayout.setLayoutParams(MarginLLParams);
                NodeLinearLayout.setOrientation(LinearLayout.VERTICAL);
                if(i%2 == 0) {
                    NodeLinearLayout.setBackgroundColor(getResources().getColor(R.color.cc_edit_text));
                }else{
                    NodeLinearLayout.setBackgroundColor(getResources().getColor(R.color.grid_odd_grey));
                }
                for (NodesVO node : passengerNode) {
                    View view;
                    if (node.isEmpty()) {
                        view = emptyLayout();
                        view.setTag(NodeTypeEnum.EMPTY.getType());
                    } else if (NodeTypeEnum.FLIGHT.getType().equals(node.getmType())) {
                        //create flight
                        view = flightLayout(node, counter);
                        view.setTag(NodeTypeEnum.FLIGHT.getType());

                    } else {
                        //create hotel
                        view = hotelLayout(node,counter);
                        view.setTag(NodeTypeEnum.HOTEL.getType());
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
            counter ++;

            if(!priviouseDate.equals(currentDate)){
                View dateView = dateLayout(currentDate);
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




   private View.OnClickListener nodeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           String nodeType =  view.getTag().toString();

            //                NodeTypeEnum.FLIGHT.getType();
                if (NodeTypeEnum.FLIGHT.getType().equals(nodeType)){

                    Fragment fragment = new AlternativeFlightFragment();
                    //accountID
                    TextView grid_flight_destination_from = (TextView)view.findViewById(R.id.grid_flight_destination_from);
                    String guidSelectedUser = grid_flight_destination_from.getTag().toString();


                    TextView grid_traveler_flight_price = (TextView)view.findViewById(R.id.grid_traveler_flight_price);
                    String guidSelectedItem = grid_traveler_flight_price.getTag().toString();


                    ((AlternativeFlightFragment) fragment).selectedItemGuidNumber(guidSelectedItem);
                    ((AlternativeFlightFragment) fragment).selectedUserGuidNumber(guidSelectedUser);
                    getFlowInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber(),null);

                }else if (NodeTypeEnum.HOTEL.getType().equals(nodeType)){

                    TextView grid_hotel_name = (TextView)view.findViewById(R.id.grid_hotel_name);
                    String guidSelectedUser = grid_hotel_name.getTag().toString();

                    TextView grid_hotel_place = (TextView)view.findViewById(R.id.grid_hotel_place);
                    String guidSelectedItem = grid_hotel_place.getTag().toString();

                    Fragment fragment = new HotelFragment();
                    ((HotelFragment) fragment).selectedItemGuidNumber(guidSelectedItem);
                    ((HotelFragment) fragment).selectedUserGuidNumber(guidSelectedUser);
                    getFlowInterface().goToFragment(ToolBarNavEnum.HOTEL.getNavNumber(),null);
                }

        }
    };

    private void makeEqualAllNodes(UserTravelMainVO user){
        ArrayList<PassengersVO> passangers = user.getPassengerses();

        for(PassengersVO passenger : passangers){
            HashMap<String, ArrayList<NodesVO>> hashMap = passenger.getHashMap();
            Set<String> dates = hashMap.keySet();
            for (String date:dates) {
                ArrayList<NodesVO> nodes = hashMap.get(date);
                int nodesSize = nodes.size();
                int maxNodeSize = runForPassangers(passangers, nodesSize, date); //get Maximum size of nodes to add to another users
              //  if(nodesSize < maxNodeSize){
                addToPassangersAllNodes(passangers, maxNodeSize, date);
               // }
            }
        }
    }

    private void addToPassangersAllNodes(ArrayList<PassengersVO> passangers,int maxNodeSize,String date){
        for(PassengersVO passenger : passangers) {
            HashMap<String, ArrayList<NodesVO>> hashMap = passenger.getHashMap();
            ArrayList<NodesVO> dates = hashMap.get(date);
            int correntSize = 0;
            if(dates != null){
                 correntSize = dates.size();
            }else{
                dates = new ArrayList<>();
            }

            int maxNodesToAdd = maxNodeSize - correntSize;

            for (int i=0;i<maxNodesToAdd;i++){
                NodesVO nodesVO = new NodesVO();
                nodesVO.setEmpty(true);
                dates.add(nodesVO);
            }
            if(maxNodesToAdd > 0) {
                passenger.setDateHashMap(date, dates);
            }
        }

    }
    private int runForPassangers(ArrayList<PassengersVO> passangers, int nodesSize, String date){

        for(PassengersVO passenger : passangers) {
            HashMap<String, ArrayList<NodesVO>> hashMap = passenger.getHashMap();
            ArrayList<NodesVO> dates = hashMap.get(date);
            if(dates != null) {
                int nodesSizeTemp = dates.size();
                if (nodesSize < nodesSizeTemp) {
                    nodesSize = nodesSizeTemp;
                }
            }
        }
        return nodesSize;
    }


    private void correctPassangerNodes(NodesVO node, ArrayList<NodesVO> passangerNodesVOs, PassengersVO passanger){

        if(!passangerNodesVOs.isEmpty() ){    //&& !node.getDateOfCell().equals(passangerNodesVOs.get(0).getDateOfCell())){
            String dateOfCell = passangerNodesVOs.get(0).getDateOfCell();
            if(!node.getDateOfCell().equals(dateOfCell)) {
                ArrayList<NodesVO> passangerNodesVOsTemp = new ArrayList<>();
                passangerNodesVOsTemp.addAll(passangerNodesVOs);
                passanger.setDateHashMap(passangerNodesVOs.get(0).getDateOfCell(), passangerNodesVOsTemp);

                passangerNodesVOs.clear();
            }
        }

        passangerNodesVOs.add(node.getClone());
    }

    /**
     * Add flight details to its layout
     * @param node
     * @return view of flight
     */
    private View flightLayout(NodesVO node, int counter){

        View child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_flight_item, null);

        TextView grid_flight_destination_from = (TextView)child.findViewById(R.id.grid_flight_destination_from);
        grid_flight_destination_from.setText(node.getmOriginCityName() + " to " + node.getmDestinationCityName());

        ImageView flight_image = (ImageView)child.findViewById(R.id.flight_image);
        if(counter == 0){
            flight_image.setImageResource(R.drawable.dlight_b_icon);
        }else{
            flight_image.setImageResource(R.drawable.flight_g_icon);
        }
        //accountID
        grid_flight_destination_from.setTag(node.getAccountID());


        TextView grid_traveler_flight_price = (TextView)child.findViewById(R.id.grid_traveler_flight_price);
        grid_traveler_flight_price.setText("$" + node.getCost());

        //guid
        grid_traveler_flight_price.setTag(node.getmGuid());

        TextView grid_traveler_flight_stops = (TextView)child.findViewById(R.id.grid_traveler_flight_stops);
        grid_traveler_flight_stops.setText(node.getmOrigin() + " - " + node.getmDestination());

     /*   TextView grid_flight_operator = (TextView)child.findViewById(R.id.grid_flight_operator);
        grid_flight_operator.setText(node.getmOperatorName() + " " + node.getmEquipment());

        //type
        grid_flight_operator.setTag(node.getmType());*/


     /*   TextView grid_flight_time = (TextView)child.findViewById(R.id.grid_flight_time);
        grid_flight_time.setText(node.getmTravelTime());*/


        LayoutParams outerLayoutParams = new LayoutParams((int)iScreenSize,cellHieght);
        RelativeLayout innerWhiteFlightLayout = (RelativeLayout)child.findViewById(R.id.innerWhiteFlightLayout);
        innerWhiteFlightLayout.setLayoutParams(outerLayoutParams);

        LinearLayout outer = new LinearLayout(activity);

      //  outer.setLayoutParams(outerLayoutParams);

        outer.setOrientation(LinearLayout.VERTICAL);
        outer.addView(child);

        return outer;
    }




    /**
     * Create empty layout, for dates when nothing is happening
     * @return empty layout
     */
    private View emptyLayout() {
        View child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_empty_item, null);


        LayoutParams outerLayoutParams = new LayoutParams((int)iScreenSize,cellHieght);
        LinearLayout innerWhiteLayout = (LinearLayout)child.findViewById(R.id.white_squer_ll);
        innerWhiteLayout.setLayoutParams(outerLayoutParams);

        LinearLayout outer = new LinearLayout(activity);
        outer.setOrientation(LinearLayout.VERTICAL);
        outer.addView(child);

        return outer;
    }

    /**
     * Create hotel layout, with node details
     * @param node
     * @return hotel layout
     */
    private View hotelLayout(NodesVO node,int counter){
        View child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_hotel_item, null);
        ImageView hotel_bad_image = (ImageView)child.findViewById(R.id.hotel_bad_image);
        if(counter == 0){
            hotel_bad_image.setImageResource(R.drawable.group_2);
        }else{
            hotel_bad_image.setImageResource(R.drawable.group_2_copy);
        }
        TextView grid_hotel_name = (TextView)child.findViewById(R.id.grid_hotel_name);
        grid_hotel_name.setText(node.getmHotelName());

        grid_hotel_name.setTag(node.getAccountID());

        TextView grid_hotel_place = (TextView)child.findViewById(R.id.grid_hotel_place);
        grid_hotel_place.setText(node.getmAddress1());

        grid_hotel_place.setTag(node.getmGuid());

        TextView grid_hotel_price = (TextView)child.findViewById(R.id.grid_hotel_price);
        long diff = HGBUtility.dayDifference(node.getmCheckIn(), node.getmCheckOut());//HGBUtility.getDateDiff(node.getmCheckIn(), node.getmCheckOut());
        double iCharge = node.getmMinimumAmount()/(diff+1);
        String result = String.format("%.2f", iCharge);
        grid_hotel_price.setText("$" + result);

        //type
        grid_hotel_price.setTag(node.getmType());

        LayoutParams outerLayoutParams = new LayoutParams((int)iScreenSize,cellHieght);
        RelativeLayout innerWhiteLayout = (RelativeLayout)child.findViewById(R.id.innerWhiteHotelLayout);
        innerWhiteLayout.setLayoutParams(outerLayoutParams);



        LinearLayout outer = new LinearLayout(activity);

       // outer.setLayoutParams(outerLayoutParams);
        outer.setOrientation(LinearLayout.VERTICAL);
        outer.addView(child);

        return outer;

    }

    /**
     * Create dates layout, the linear layout of dates
     * @param date
     * @return dates layout
     */
    private LinearLayout dateLayout(String date){
        View child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_date_item, null);

        TextView date_text_layout = (TextView)child.findViewById(R.id.date_text_layout);
      //  String correctDate = HGBUtility.parseDateFromddMMyyyyToddmmYYYY(date);
        date_text_layout.setText(date);
        LinearLayout outer = new LinearLayout(activity);
        outer.setOrientation(LinearLayout.VERTICAL);
        outer.setLayoutParams(layoutParams);
        outer.addView(child);

        return outer;
    }

   private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);



    private void createItinenaryView(View view){

        LinearLayout scrollViewLinearLayout = (LinearLayout)view.findViewById(R.id.new_grid_main_ll);


        HorizontalScrollView horizontal = (HorizontalScrollView)view.findViewById(R.id.table_scroll);
        horizontal.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_MOVE:
                        //TODO
//                       LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.scroll_view_ll);
//                       LinearLayout mainLayoutChild = (LinearLayout)linearLayout.getChildAt(0);
//                        int childCount = mainLayoutChild.getChildCount();
//                        for(int i=0;i<childCount;i++){
//                            View child = mainLayoutChild.getChildAt(i);
//                            TextView textView = null;
//                            if(child != null) {
//                                textView = (TextView) child.findViewById(R.id.date_text_layout);
//                            }
//                            if(textView != null){
//                                int dimens = iScreenSize - (int)motionEvent.getRawX() ;
//
//                                layoutParams.setMargins(dimens, 0,0, 0);
//                                textView.setLayoutParams(layoutParams);
//                            }
//                        }

                        break;
                }
                return false;
            }
        });


        LinearLayout itineraryLayout = (LinearLayout)scrollViewLinearLayout.findViewById(R.id.scroll_view_ll);
        LinearLayout cnc_empty_view = (LinearLayout)scrollViewLinearLayout.findViewById(R.id.cnc_empty_view);
      //  UserTravelMainVO user = parseFlight();
     //  getActivityInterface().setTravelOrder(user);
        UserTravelMainVO  user = getActivityInterface().getTravelOrder();
        userOrder = user;


        if(userOrder != null && !userOrder.getItems().isEmpty()) {
            itineraryLayout.setVisibility(View.VISIBLE);
            createPassengersName(scrollViewLinearLayout, userOrder);
            createMainNodes(userOrder);
            makeEqualAllNodes(userOrder);
            View mainView = createGridView(userOrder);
            itineraryLayout.addView(mainView);
            cnc_empty_view.setVisibility(View.GONE);
            grid_make_payment.setEnabled(true);
        }else{  // server returning wrong data
            itineraryLayout.setVisibility(View.GONE);
            cnc_empty_view.setVisibility(View.VISIBLE);
            grid_make_payment.setEnabled(false);
        }
    }


    private void getDimentions(){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float dp =  HGBUtility.convertPixelsToDp(size.x,activity);//convertPixelsToDp(size.x, activity);
        iScreenSize = getActivity().getResources().getDimension(R.dimen.DP200); //dp + activity.getResources().getDimension(R.dimen.DP50);


    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cellHieght = (int) getResources().getDimension(R.dimen.DP120);
        activity = getActivity();
        View rootView = inflater.inflate(R.layout.new_grid_main_table, container, false);

        grid_make_payment = (Button)rootView.findViewById(R.id.grid_make_payment);
        grid_make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_DETAILS.getNavNumber(), null);
            }
        });

        iScreenSize = getActivity().getResources().getDimension(R.dimen.DP250); //dp + activity.getResources().getDimension(R.dimen.DP50);

        createItinenaryView(rootView);

        getActivityInterface().setAlternativeFlights(null);



        return rootView;
    }


    @Override
    public void onDestroyView() {
        userOrder = getActivityInterface().getTravelOrder();
        if (userOrder != null) {


            ArrayList<PassengersVO> passangers = userOrder.getPassengerses();
            for (PassengersVO passanger : passangers) {
                passanger.clearData();
            }
        }
        super.onDestroyView();
    }


}
