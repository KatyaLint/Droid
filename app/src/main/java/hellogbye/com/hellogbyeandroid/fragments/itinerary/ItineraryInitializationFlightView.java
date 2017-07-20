package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.HGBMainInterface;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;

/**
 * Created by nyawka on 7/13/17.
 */

public class ItineraryInitializationFlightView {



    /**
     * Create passanger names layout
     * @param scrollViewLinearLayout
     * @param user
     */
    public void createPassengersName(View scrollViewLinearLayout, UserTravelMainVO user, Activity activity, boolean isBooked){

        int cellHieght = (int) activity.getResources().getDimension(R.dimen.DP220);
        float iScreenSize = activity.getResources().getDimension(R.dimen.DP335);

        LinearLayout passengersNames;
        if(isBooked) {
            passengersNames = (LinearLayout) scrollViewLinearLayout.findViewById(R.id.passangers_names_ll_booked);
        }else{
            passengersNames = (LinearLayout) scrollViewLinearLayout.findViewById(R.id.passangers_names_ll);

        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) iScreenSize, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        ArrayList<PassengersVO> passengers = user.getPassengerses();

        LinearLayout inner = new LinearLayout(activity);

        for (PassengersVO passenger:passengers){
            View child ;
            FontTextView text;
            FontTextView grid_passanger_cost_flight;
            RoundedImageView grid_round_image;
            if(isBooked){
                child = activity.getLayoutInflater().inflate(R.layout.new_grid_passanger_name_booked, null);
                text = (FontTextView)child.findViewById(R.id.grid_passanger_name_booked);
                grid_passanger_cost_flight = (FontTextView)child.findViewById(R.id.grid_passanger_cost_flight_booked);
                grid_round_image = (RoundedImageView)child.findViewById(R.id.grid_round_image_booked);
            }else{
                child = activity.getLayoutInflater().inflate(R.layout.new_grid_passanger_name, null);
                text = (FontTextView)child.findViewById(R.id.grid_passanger_name);
                grid_passanger_cost_flight = (FontTextView)child.findViewById(R.id.grid_passanger_cost_flight);
                grid_round_image = (RoundedImageView)child.findViewById(R.id.grid_round_image);
            }

            text.setText(passenger.getmName() );

            String priceLine= "" + HGBUtility.roundNumber(passenger.getmTotalPrice()) + " ";
            if(user.getmCurrency() != null) {
                priceLine += user.getmCurrency();
            }

            grid_passanger_cost_flight.setText("$" + priceLine + " + Taxes & Feess");


            HGBUtility.loadRoundedImage(passenger.getAvatarurl(), grid_round_image, R.drawable.avatar_companions);

            LinearLayout outer = new LinearLayout(activity);

            outer.setLayoutParams(params);
            outer.setOrientation(LinearLayout.VERTICAL);

            outer.addView(child);
            inner.addView(outer);

        }
        passengersNames.addView(inner);

    }





    /**
     * Add flight details to its layout
     * @param node
     * @param activityInterface
     * @return view of flight
     */
    public View flightLayout(NodesVO node, Activity activity, String nodeBeforeID, HGBMainInterface activityInterface){
        int cellHieght = (int) activity.getResources().getDimension(R.dimen.DP220);
        float iScreenSize = activity.getResources().getDimension(R.dimen.DP335);


        View child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_flight_item, null);



       /* TextView grid_flight_destination_from = (TextView)child.findViewById(R.id.grid_flight_destination_from);
        grid_flight_destination_from.setText(node.getmOriginCityName() + " - " + node.getmDestinationCityName());*/

/*        ImageView flight_image = (ImageView)child.findViewById(R.id.flight_image);
        flight_image.setImageResource(R.drawable.dlight_b_icon);*/

        //accountID

        LinearLayout grid_flight_square_ll =(LinearLayout)child.findViewById(R.id.grid_flight_square_ll);
        grid_flight_square_ll.setTag(node.getAccountID());


        FontTextView grid_traveler_flight_price = (FontTextView)child.findViewById(R.id.grid_traveler_flight_price);
        TextView grid_traveler_continued_flight = (TextView)child.findViewById(R.id.grid_traveler_continued_flight);

        grid_traveler_flight_price.setText("$" + HGBUtility.roundNumber(node.getCost()));

        FontTextView grid_traveler_flight_currency = (FontTextView)child.findViewById(R.id.grid_traveler_flight_currency);
        grid_traveler_flight_currency.setText(node.getmCurrency());

        LinearLayout grid_money_ll = (LinearLayout)child.findViewById(R.id.grid_money_ll);

        grid_money_ll.setVisibility(View.VISIBLE);
        grid_traveler_continued_flight.setVisibility(View.INVISIBLE);

      /*  if(counter == 0){

            grid_traveler_flight_price.setText("$" + HGBUtility.roundNumber(node.getCost()) + node.getmCurrency());
            grid_traveler_flight_price.setVisibility(View.VISIBLE);
            grid_traveler_continued_flight.setVisibility(View.INVISIBLE);
        }else{
           */
        if(node.getParentflightid() != null){
            grid_traveler_continued_flight.setVisibility(View.VISIBLE);
            grid_money_ll.setVisibility(View.INVISIBLE);
            grid_traveler_continued_flight.setText("Return Flight");
        }

        if(nodeBeforeID.equals(node.getmPrimaryguid())){
            grid_traveler_continued_flight.setVisibility(View.VISIBLE);
            grid_money_ll.setVisibility(View.INVISIBLE);
            grid_traveler_continued_flight.setText("Continued Flight");
        }else{
            nodeBeforeID = node.getmPrimaryguid();
        }

           /* else {
                grid_traveler_continued_flight.setText("Continued Flight");
            }
*/


       /* }*/


        //guid
        grid_traveler_flight_price.setTag(node.getmGuid());

        TextView grid_traveler_flight_stops_departure = (TextView)child.findViewById(R.id.grid_traveler_flight_stops_departure);
        grid_traveler_flight_stops_departure.setText(node.getmOrigin() );


        ImageView grid_flight_direction_view = (ImageView)child.findViewById(R.id.grid_flight_direction_view);
        String isRoundTrip = isRoundTrip(activityInterface);

        if(node.getmPrimaryguid().equals(isRoundTrip) || node.getParentflightid() != null) {

            grid_flight_direction_view.setImageDrawable(activity.getResources().getDrawable(R.drawable.arrow_bi_directional_copy));
        }else{
            grid_flight_direction_view.setImageResource(R.drawable.arrow_forth_on);
        }

      /*  if(isRoundTrip != null) {
            grid_flight_direction_view.setImageDrawable(getResources().getDrawable(R.drawable.arrow_bi_directional_copy));
        }else{
            grid_flight_direction_view.setImageDrawable(getResources().getDrawable(R.drawable.arrow_one_direction));
        }*/

        TextView grid_traveler_flight_stops_arrival = (TextView)child.findViewById(R.id.grid_traveler_flight_stops_arrival);
        grid_traveler_flight_stops_arrival.setText( node.getmDestination());

        ImageView grid_airplane_logo = (ImageView)child.findViewById(R.id.grid_airplane_logo);

        HGBUtility.loadRoundedImage(node.getLegs().get(0).getmCarrierBadgeUrl(), grid_airplane_logo, R.drawable.profile_image);


        TextView grid_flight_operator_departure = (TextView)child.findViewById(R.id.grid_flight_operator_departure);
        grid_flight_operator_departure.setText("Depart: " + HGBUtilityDate.parseDateToHHmm(node.getmDeparture()) + " Arrival: "+HGBUtilityDate.parseDateToHHmm(node.getmArrival()));


        TextView grid_flight_airlines_class = (TextView)child.findViewById(R.id.grid_flight_airlines_class);
        grid_flight_airlines_class.setText(node.getmOperatorName() );


        FontTextView grid_flight_airlines_flightnumber = (FontTextView)child.findViewById(R.id.grid_flight_airlines_flightnumber);
        grid_flight_airlines_flightnumber.setText(node.getmOperator() + " " + node.getmEquipment());

        ArrayList<LegsVO> legs = node.getLegs();
        int stopCounter = 0;
        String stopsStr = legs.get(0).getmOriginCityName();
        for(int i=1;i<legs.size();i++){// LegsVO leg : legs){
            if( legs.get(i).getmType().equals("Leg")){
                stopCounter++;
                stopsStr += "-"+legs.get(i).getmOriginCityName();
            }
        }

        stopsStr += "-" +legs.get(0).getmDestinationCityName();
        FontTextView grid_flight_airlines_stops = (FontTextView)child.findViewById(R.id.grid_flight_airlines_stops);

        grid_flight_airlines_stops.setText(stopsStr);

        FontTextView grid_flight_airlines_fareclass = (FontTextView)child.findViewById(R.id.grid_flight_airlines_fareclass);
        grid_flight_airlines_fareclass.setText(node.getmFareClass() + " Class");


/*        TextView grid_flight_operator_arrival = (TextView)child.findViewById(R.id.grid_flight_operator_arrival);
        grid_flight_operator_arrival.setText("Arrival: "+HGBUtility.parseDateToHHmm(node.getmArrival()));*/


        //type
        //grid_flight_operator.setTag(node.getmType());


/*        TextView grid_flight_time = (TextView)child.findViewById(R.id.grid_flight_time);
        grid_flight_time.setText(node.getmFlightTime());*/


        LinearLayout.LayoutParams outerLayoutParams = new LinearLayout.LayoutParams((int)iScreenSize, cellHieght);
        RelativeLayout innerWhiteFlightLayout = (RelativeLayout)child.findViewById(R.id.innerWhiteFlightLayout);
        innerWhiteFlightLayout.setLayoutParams(outerLayoutParams);

        LinearLayout outer = new LinearLayout(activity);

        //  outer.setLayoutParams(outerLayoutParams);

        outer.setOrientation(LinearLayout.VERTICAL);
        outer.addView(child);


        return outer;
    }

    private String isRoundTrip(HGBMainInterface activityInterface){
        UserTravelMainVO userOrder = activityInterface.getTravelOrder();
        Map<String, NodesVO> flightItems = userOrder.getItems();
        Collection<NodesVO> values = flightItems.values();
        for(NodesVO value : values){
            if(value.getParentflightid() != null ){
                return value.getParentflightid();
            }
        }
        return null;
    }


}
