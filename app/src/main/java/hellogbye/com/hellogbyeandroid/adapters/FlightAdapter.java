package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;

/**
 * Created by nyawka on 10/7/15.
 */
public class FlightAdapter extends  RecyclerView.Adapter<FlightAdapter.ViewHolder> {


    private  ArrayList<LegsVO> itemsData;
    private double flightCost;
    private String destinationFlights;


    public FlightAdapter(Context context, ArrayList<LegsVO>  itemsData) {
        this.itemsData = itemsData;
    }

    @Override
    public FlightAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flight_all_details, null);


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        LegsVO legFlightVO = itemsData.get(position);
        if(position != 0){
            viewHolder.flight_ticket_details_layout.setVisibility(View.GONE);
        }else{
            viewHolder.flight_cost.setText("$" + getFlightCost());
            viewHolder.flight_direction.setText(destinationFlights);
            viewHolder.flight_ticket_details_layout.setVisibility(View.VISIBLE);
        }
        if(position != itemsData.size() - 1){
            viewHolder.show_alternative_flights.setVisibility(View.GONE);
        }else{
            viewHolder.show_alternative_flights.setVisibility(View.VISIBLE);
        }
        if(legFlightVO.getmType().equals("Leg")) {
            viewHolder.operatorName.setText(legFlightVO.getmCarrierName());
            viewHolder.flightNumber.setText(legFlightVO.getmCarrierCode()+legFlightVO.getmFlightNumber());
            viewHolder.flight_airport_text.setText(legFlightVO.getmOrigin() + " - " + legFlightVO.getmDestination());
            viewHolder.flight_boarding_text.setText(legFlightVO.getmDepartureTime());
            viewHolder.flight_duration_text.setText(legFlightVO.getmDepartureTime());
            viewHolder.flight_class_text.setText(legFlightVO.getmFareClass());
            viewHolder.stop_over_include_layout.setVisibility(View.GONE);

        }else if(legFlightVO.getmType().equals("StopOver")){
            viewHolder.stop_over_txt.setText(legFlightVO.getmCityName());
            viewHolder.stop_over_time.setText((int)legFlightVO.getmDurationHours() + "h " + (int)legFlightVO.getmDurationMinutes() + "m");

            viewHolder.includeAirplaneDetails.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }

    public double getFlightCost() {
        return flightCost;
    }

    public void setFlightCost(double flightCost) {
        this.flightCost = flightCost;
    }

    public String getDestinationFlights() {
        return destinationFlights;
    }

    public void setDestinationFlights(String destinationFlights) {
        this.destinationFlights = destinationFlights;
    }

    private  static AlternativeFlightFragment.AlternativeButtonCB alternativeButtonCB;

    public void setButtonListener(AlternativeFlightFragment.AlternativeButtonCB alternativeButtonCB){
        this.alternativeButtonCB = alternativeButtonCB;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public  Button show_alternative_flights;
        public  View flight_ticket_details_layout;
        public TextView operatorName;
        public  TextView flightNumber;
        public TextView flight_airport_text ;
        public TextView flight_boarding_text;
        public TextView flight_duration_text;
        public TextView flight_class_text;
        public  TextView flight_meal_text;
        public TextView flight_aircraft_text;
        public TextView flight_baggage_text;
        public TextView flight_seat_selection_text;
        public TextView stop_over_txt;
        public TextView stop_over_time;

        public View includeAirplaneDetails;
        public View stop_over_include_layout;

        public TextView flight_direction;
        public TextView flight_cost;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            initializeAirplaneDetails(itemLayoutView);
            stopOverInitialization(itemLayoutView);
            flightMainCostInitialization( itemLayoutView);
            show_alternative_flights = (Button)itemLayoutView.findViewById(R.id.show_alternative_flights);

            show_alternative_flights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alternativeButtonCB.showAlternative();
                }
            });

        }



        private void flightMainCostInitialization(View mainView){
            flight_ticket_details_layout = mainView.findViewById(R.id.flight_ticket_details_layout);
            flight_direction = (TextView)flight_ticket_details_layout.findViewById(R.id.flight_direction);

           flight_cost = (TextView)flight_ticket_details_layout.findViewById(R.id.flight_cost);

        }


        private void stopOverInitialization(View mainView){
             stop_over_include_layout = mainView.findViewById(R.id.stop_over_include_layout);
            stop_over_txt = (TextView)stop_over_include_layout.findViewById(R.id.stop_over_txt);
            stop_over_time = (TextView)stop_over_include_layout.findViewById(R.id.stop_over_time);
        }


        private void initializeAirplaneDetails(View itemLayoutView){
              includeAirplaneDetails = itemLayoutView.findViewById(R.id.airplane_details_layout);
            operatorName = (TextView)includeAirplaneDetails.findViewById(R.id.operated_by_text);
            flightNumber = (TextView)includeAirplaneDetails.findViewById(R.id.flight_number_text);
             flight_airport_text = (TextView)includeAirplaneDetails.findViewById(R.id.flight_airport_text);
             flight_boarding_text = (TextView)includeAirplaneDetails.findViewById(R.id.flight_boarding_text);
             flight_duration_text = (TextView)includeAirplaneDetails.findViewById(R.id.flight_duration_text);
             flight_class_text = (TextView)includeAirplaneDetails.findViewById(R.id.flight_class_text);
             flight_meal_text = (TextView)includeAirplaneDetails.findViewById(R.id.flight_meal_text);
            // flight_meal_text.setText(legFlightVO.getmFareClass());

             flight_aircraft_text = (TextView)includeAirplaneDetails.findViewById(R.id.flight_aircraft_text);
            //flight_aircraft_text.setText(legFlightVO.getmFareClass());

             flight_baggage_text = (TextView)includeAirplaneDetails.findViewById(R.id.flight_baggage_text);

             flight_seat_selection_text = (TextView)includeAirplaneDetails.findViewById(R.id.flight_seat_selection_text);
        }

    }


}