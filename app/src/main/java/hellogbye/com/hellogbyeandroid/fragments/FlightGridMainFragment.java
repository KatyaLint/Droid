package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

/**
 * Created by nyawka on 10/19/15.
 */
public class FlightGridMainFragment extends HGBAbtsractFragment {

    private ItineraryFragment.TravelerShowChoose itemSelected;
    private TextView[] gridDateHotel;
    private TextView[] gridMonthHotel;
    private TextView[] grid_hotel_name;
    private LinearLayout[] linaerLayoutHotel;
    private LinearLayout[] linarLayoutDateHotel;
    private TextView[] grid_hotel_price;
    private TextView[] grid_hotel_place;
    private View[] childEmptyView;
    private LinearLayout[] linaerLayoutEmpty;
    private TextView[] gridDateEmpty;
    private TextView[] gridMonthEmpty;
    private LinearLayout[] linarLayoutDateEmpty;
    private LinearLayout[] white_squer_ll;
    private EditText[] grid_hotel_guid;
    private TableLayout stickyHeader;

    public FlightGridMainFragment(){}
    private UserTravelVO airplaneDataVO;

    private LayoutInflater inflater;
    private Activity activity;
    private TableLayout table;

    private static int MAXIMUM_ROW_NUMBER = 7;
    private static int MAXIMUM_COL_NUMBER = 3;
    private int maxRowNumber;
    private int realRowNumber;
    private int numberOfRow;
    private int columnNumber;


    public View createGridView(Activity activity,View rootView, UserTravelVO userTravelVO,  LayoutInflater inflater
                               ){
        table = (TableLayout)rootView.findViewById(R.id.tlGridTable);

        stickyHeader = (TableLayout)rootView.findViewById(R.id.stickyHeader);

        this.inflater = inflater;
        this.activity = activity;

        createGridViewTable(userTravelVO);

        return rootView;
    }

    private void calculateNumberOfRowAndCol(){
        if(maxRowNumber < MAXIMUM_ROW_NUMBER && columnNumber<MAXIMUM_COL_NUMBER){
            maxRowNumber = MAXIMUM_ROW_NUMBER * MAXIMUM_COL_NUMBER;
        }else if(maxRowNumber < MAXIMUM_ROW_NUMBER){
            maxRowNumber = MAXIMUM_ROW_NUMBER*columnNumber;
        }else if(columnNumber < MAXIMUM_COL_NUMBER){
            maxRowNumber = realRowNumber*MAXIMUM_COL_NUMBER;
        }

        initializeFlightItems(maxRowNumber);
        initializeHotelItems(maxRowNumber);
        initializeEmptyItems(maxRowNumber);


        if(numberOfRow < MAXIMUM_ROW_NUMBER){
            numberOfRow = MAXIMUM_ROW_NUMBER;
        }
        if(columnNumber < MAXIMUM_COL_NUMBER){
            columnNumber = MAXIMUM_COL_NUMBER;
        }
    }


    private void createGridViewTable(UserTravelVO userTravelVO){

        int counter = 0;

        ArrayList<PassengersVO> passengers = userTravelVO.getPassengerses();


        PassangerDataOrganization passData = new PassangerDataOrganization();
        passData.organizeDataStructure(userTravelVO);

        ArrayList<PassengersVO> pass = passData.passangersVOs;


         columnNumber = pass.size();//passengers.size();


        setStickyHeaderData(passengers);


        numberOfRow = pass.get(0).getPassengerNodes().size();
        realRowNumber = pass.get(0).getPassengerNodes().size();
        maxRowNumber = numberOfRow*columnNumber;
        calculateNumberOfRowAndCol();




        for(int i=0;i < numberOfRow ;i++){  // row 16

            //  row[i] = new TableRow(activity);
            row[i] = new TableRow(activity);
            for(int j=0;j< columnNumber  ;j++) {  //column  0-1-2


                initializeFlightGridItems(counter, j);
                initializeHotelGridItems(counter,j);
                initializeEmptyGridItems(counter, j);

                if(pass.size() > j && i < realRowNumber){
                    String date = "";
                    PassengersVO traveler = pass.get(j);
                    ArrayList<NodesVO> travelerNodes = traveler.getPassengerNodes();
                    NodesVO travelerNode = travelerNodes.get(i);
                    if(travelerNode.getmType().isEmpty()){
                        emptyNode(j, counter);
                        date = travelerNode.getDateOfCell();
                        //  initializeEmptyView(j, counter);
                        row[i].addView(childEmptyView[counter]);
                    }else {
                        if (travelerNode.getmType().equals("flight")) {
                            initializeFlightData(travelerNode, counter);
                            row[i].addView(child[counter]);

                        } else if (travelerNode.getmType().equals("hotel")) {
                            initializeHotelData(travelerNode, counter);
                            row[i].addView(childHotel[counter]);

                        }
                        date = travelerNode.getDateOfCell();

                    }
                    setDate(j, counter, date); //TODO date to hotels
                }else{
                    emptyNode(j, counter);
                    row[i].addView(childEmptyView[counter]);
                }

                counter++;

            }


            table.addView(row[i]);
        }

    }


    private void emptyNode(int columnNumber,int counter ){

        if(columnNumber == 0) {
            gridMonthEmpty[counter].setVisibility(View.VISIBLE);
            gridDateEmpty[counter].setVisibility(View.VISIBLE);
            linarLayoutDateEmpty[counter].setVisibility(View.VISIBLE);
        }else{
            gridMonthEmpty[counter].setVisibility(View.GONE);
            gridDateEmpty[counter].setVisibility(View.GONE);
            linarLayoutDateEmpty[counter].setVisibility(View.GONE);

        }

    }

    private void initializeFlightData(NodesVO node, int counter){
        String orgCityName = node.getmOriginCityName();
        String destCityName = node.getmDestinationCityName();
        grid_flight_destination_from[counter].setText(orgCityName + " to");
        grid_flight_destination_to[counter].setText(destCityName);
        grid_traveler_flight_price[counter].setText("$" + node.getCost() );

        String flight_stops = "";
        ArrayList<LegsVO> legs = node.getLegs();
        for (int i=0;i < legs.size();i++){
            LegsVO leg = legs.get(i);
            if(leg.getmType().equals("Leg")){
                flight_stops = flight_stops + leg.getmOrigin()+" - ";
            }
        }

        flight_stops = flight_stops + node.getmDestination();
        grid_traveler_flight_stops[counter].setText(flight_stops);
        grid_flight_time[counter].setText(node.getmFlightTime());
        grid_flight_operator[counter].setText(node.getmOperatorName());


        nodeGuIdNumberFlight[counter].setTag(node.getmGuid());


    }

    private void initializeHotelData(NodesVO node, int counter){
        String hotelName = node.getmHotelName();
        grid_hotel_name[counter].setText(hotelName);
        grid_hotel_price[counter].setText("$" + node.getRoomsVOs().get(0).getmCost());
        grid_hotel_place[counter].setText(node.getmCityName());
        grid_hotel_guid[counter].setTag(node.getmGuid());
    }



    private void setDate(int columnNumber, int counter, String dateRefactore){

        if(columnNumber == 0) {
            linarLayoutDate[counter].setVisibility(View.VISIBLE);
            linarLayoutDateHotel[counter].setVisibility(View.VISIBLE);
            linarLayoutDateEmpty[counter].setVisibility(View.VISIBLE);
                String date = HGBUtility.formattDateToStringMonthDate(dateRefactore);
                if (date != null) {
                    String[] result = date.split(":");
                    gridMonth[counter].setText(result[0]);
                    gridDate[counter].setText(result[1]);
                    gridMonthHotel[counter].setText(result[0]);
                    gridDateHotel[counter].setText(result[1]);

                    gridMonthEmpty[counter].setText(result[0]);
                    gridDateEmpty[counter].setText(result[1]);

                }
        }else{
            gridMonth[counter].setVisibility(View.GONE);
            gridDate[counter].setVisibility(View.GONE);
            gridMonthHotel[counter].setVisibility(View.GONE);
            gridDateHotel[counter].setVisibility(View.GONE);
            linarLayoutDate[counter].setVisibility(View.GONE);
            linarLayoutDateHotel[counter].setVisibility(View.GONE);

            gridMonthEmpty[counter].setVisibility(View.GONE);
            gridDateEmpty[counter].setVisibility(View.GONE);
            linarLayoutDateEmpty[counter].setVisibility(View.GONE);

        }
    }





    private void setStickyHeaderData(ArrayList<PassengersVO> passengers){
        stickyRow = new TableRow(activity);

        int passengerSize = passengers.size();
        int passengerSizeCorrect = passengers.size();
        if(passengerSize < MAXIMUM_COL_NUMBER){
            passengerSize = MAXIMUM_COL_NUMBER;
        }
        initializeItemsHeader(passengerSize);
        for(int i=0; i<passengerSize;i++){
            mainHeaderView[i] = inflater.inflate(R.layout.grid_view_sticky_header, null);
            if(passengerSizeCorrect > i){
                PassengersVO passenger = passengers.get(i);
                travelerName[i] = (TextView) mainHeaderView[i].findViewById(R.id.traveler_name);
                travelerName[i].setText(passenger.getmDisplayName());
                if(i ==0 ) {
                    travelerName[i].setPadding((int)activity.getResources().getDimension(R.dimen.DP44), 0, 0, 0);
                }
            }

            stickyRow.addView(mainHeaderView[i]);
            //tableStickyHeader
        }
//        tableStickyHeader.addView(stickyRow);


       // stickyHeader.addView(stickyRow);
        table.addView(stickyRow);

    }


    View mainHeaderView[];
    TextView travelerName[];
    TableRow stickyRow;
    private void initializeItemsHeader(int itemNumber){
        travelerName = new TextView[itemNumber];
        mainHeaderView = new View[itemNumber];
    }



    LinearLayout[] linaerLayoutFlight;
    TextView[] grid_flight_destination_from ;
    TextView[] grid_flight_destination_to;
    TextView[] gridMonth;
    TextView[] gridDate;
    TextView[] grid_traveler_flight_price;
    TextView[] grid_flight_operator;
    TextView[] grid_flight_time;
    TableRow[] row ;
    TextView[] grid_traveler_flight_stops;
    LinearLayout[] linarLayoutDate;
    View[] child ;
    EditText[] nodeGuIdNumberFlight;

    private void initializeFlightItems(int itemNumber){
       linaerLayoutFlight = new LinearLayout[itemNumber];
        grid_flight_destination_from = new TextView[itemNumber];
        grid_flight_destination_to = new TextView[itemNumber];
        grid_traveler_flight_price  = new TextView[itemNumber];
        grid_traveler_flight_stops  = new TextView[itemNumber];
        grid_flight_time = new TextView[itemNumber];
        grid_flight_operator  = new TextView[itemNumber];
        gridMonth = new TextView[itemNumber];
        gridDate = new TextView[itemNumber];
        nodeGuIdNumberFlight = new EditText[itemNumber];
       row = new TableRow[itemNumber];
       child = new View[itemNumber];
        linarLayoutDate = new LinearLayout[itemNumber];
    }


    private void initializeFlightGridItems(int counter, int colomnNumber){
        child[counter] = inflater.inflate(R.layout.grid_view_inner_flight_item, null);
        grid_flight_destination_from[counter] = (TextView) child[counter].findViewById(R.id.grid_flight_destination_from);
        grid_flight_destination_to[counter] = (TextView) child[counter].findViewById(R.id.grid_flight_destination_to);

        nodeGuIdNumberFlight[counter] = (EditText) child[counter].findViewById(R.id.nodeGuIdNumberFlight);

        grid_traveler_flight_price[counter] = (TextView) child[counter].findViewById(R.id.grid_traveler_flight_price);
        grid_traveler_flight_stops[counter] = (TextView) child[counter].findViewById(R.id.grid_traveler_flight_stops);
        grid_flight_time[counter] = (TextView) child[counter].findViewById(R.id.grid_flight_time);
        grid_flight_operator[counter] = (TextView) child[counter].findViewById(R.id.grid_flight_operator);


        gridMonth[counter] = (TextView) child[counter].findViewById(R.id.grid_calendar_month);
        gridDate[counter] = (TextView) child[counter].findViewById(R.id.grid_calender_date);
        linaerLayoutFlight[counter] = (LinearLayout)child[counter].findViewById(R.id.grid_squer);
        linarLayoutDate[counter] = (LinearLayout)child[counter].findViewById(R.id.grid_date_ll);


        if(colomnNumber%2 == 0){ //even colum lightgray color

            linaerLayoutFlight[counter].setBackgroundColor(activity.getResources().getColor(R.color.grey_very_light));
        }else{ //odd colomn dark_gray color
            linaerLayoutFlight[counter].setBackgroundColor(activity.getResources().getColor(R.color.odd_grey));
        }

        child[counter].setOnClickListener(itemClickListenerFlight);

    }

    public void initializeCB(ItineraryFragment.TravelerShowChoose itemSelected){
        this.itemSelected = itemSelected;
    }

    private View.OnClickListener itemClickListenerFlight = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            View main_view = (View) view.getRootView();
            EditText text = (EditText)main_view.findViewById(R.id.nodeGuIdNumberFlight);
            String guID = text.getTag().toString();
            itemSelected.itemSelected(guID, "flight");
        }
    };

    private View.OnClickListener itemClickListenerHotel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            View main_view = (View) view.getRootView();
            EditText text = (EditText)main_view.findViewById(R.id.grid_hotel_guid);
            String guID = text.getTag().toString();
            itemSelected.itemSelected(guID,"hotel");
        }
    };

    View childHotel[];

    private void initializeHotelItems(int itemNumber) {
        childHotel = new View[itemNumber];
        gridMonthHotel = new TextView[itemNumber];
        gridDateHotel = new TextView[itemNumber];
        grid_hotel_name = new TextView[itemNumber];
        grid_hotel_place = new TextView[itemNumber];
        grid_hotel_price = new TextView[itemNumber];
        linaerLayoutHotel = new LinearLayout[itemNumber];
        linarLayoutDateHotel = new LinearLayout[itemNumber];
        grid_hotel_guid = new EditText[itemNumber];
    }


    private void initializeHotelGridItems(int counter, int colomnNumber){
        childHotel[counter] = inflater.inflate(R.layout.grid_view_inner_hotel_item, null);
        gridMonthHotel[counter] = (TextView) childHotel[counter].findViewById(R.id.grid_calendar_month_hotel);
        grid_hotel_guid[counter] = (EditText) childHotel[counter].findViewById(R.id.grid_hotel_guid);
        gridDateHotel[counter] = (TextView) childHotel[counter].findViewById(R.id.grid_calender_date_hotel);
        grid_hotel_name[counter] = (TextView) childHotel[counter].findViewById(R.id.grid_hotel_name);
        grid_hotel_price[counter] = (TextView) childHotel[counter].findViewById(R.id.grid_hotel_price);
        grid_hotel_place[counter] = (TextView) childHotel[counter].findViewById(R.id.grid_hotel_place);
        linaerLayoutHotel[counter] = (LinearLayout)childHotel[counter].findViewById(R.id.grid_squer_hotel);
        linarLayoutDateHotel[counter] = (LinearLayout)childHotel[counter].findViewById(R.id.grid_date_ll_hotel);

        if(colomnNumber%2 == 0){ //even colum lightgray color

            linaerLayoutHotel[counter].setBackgroundColor(activity.getResources().getColor(R.color.grey_very_light));
        }else{ //odd colomn dark_gray color
            linaerLayoutHotel[counter].setBackgroundColor(activity.getResources().getColor(R.color.odd_grey));
        }


        childHotel[counter].setOnClickListener(itemClickListenerHotel);

    }







    private void initializeEmptyItems(int itemNumber) {
        childEmptyView=  new View[itemNumber];
        linaerLayoutEmpty = new LinearLayout[itemNumber];
        gridMonthEmpty = new TextView[itemNumber];
        gridDateEmpty = new TextView[itemNumber];
        linarLayoutDateEmpty = new LinearLayout[itemNumber];
        white_squer_ll = new LinearLayout[itemNumber];
    }

private void initializeEmptyGridItems(int counter, int colomnNumber){
    childEmptyView[counter] = inflater.inflate(R.layout.grid_view_inner_empty_item, null);
    linaerLayoutEmpty[counter] = (LinearLayout)childEmptyView[counter].findViewById(R.id.grid_squer_empty);

    white_squer_ll[counter] = (LinearLayout)childEmptyView[counter].findViewById(R.id.white_squer_ll);

    gridMonthEmpty[counter] = (TextView) childEmptyView[counter].findViewById(R.id.grid_calendar_month_empty);
    gridDateEmpty[counter] = (TextView) childEmptyView[counter].findViewById(R.id.grid_calender_date_empty);
    linarLayoutDateEmpty[counter] = (LinearLayout)childEmptyView[counter].findViewById(R.id.grid_date_ll_empty);

    if(colomnNumber%2 == 0){ //even colum lightgray color
        white_squer_ll[counter].setBackgroundColor(activity.getResources().getColor(R.color.grey_very_light));
        linaerLayoutEmpty[counter].setBackgroundColor(activity.getResources().getColor(R.color.grey_very_light));
    }else{ //odd colomn dark_gray color
        white_squer_ll[counter].setBackgroundColor(activity.getResources().getColor(R.color.odd_grey));
        linaerLayoutEmpty[counter].setBackgroundColor(activity.getResources().getColor(R.color.odd_grey));
    }
}


    ArrayList<NodesVO> nodesTemp = new ArrayList<NodesVO>();


    private int getMaxmimuRowNumber(ArrayList<PassengersVO> passengers){

        for(PassengersVO passanger:passengers) {
            ArrayList<CellsVO> cells = passanger.getmCells();

            for (int i = 0; i < cells.size(); i++) {
                CellsVO cell = cells.get(i);
                ArrayList<NodesVO> nodes = cell.getmNodes();

                if (nodes.size() == 0) {
                    NodesVO demoNode = new NodesVO();
                    demoNode.setDateOfCell(cell.getmDate());
                    demoNode.setEmpty(true);
                    nodesTemp.add(demoNode);
                }else{
                    for (int j = 0; j < nodes.size(); j++) {
                        nodesTemp.add(nodes.get(j));
                    }
                }
            }
        }


        return nodesTemp.size();
    }



    public UserTravelVO getAirplaneDataVO() {
        return airplaneDataVO;
    }

    public void setAirplaneDataVO(UserTravelVO airplaneDataVO) {
        this.airplaneDataVO = airplaneDataVO;
    }
}
