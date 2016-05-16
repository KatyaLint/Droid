package hellogbye.com.hellogbyeandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 10/7/15.
 */
public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {

    private ArrayList<LegsVO> itemsData;
    private double flightCost;
    private String destinationFlights;
    private boolean mIsMyFlight;
    private boolean alternativeButtonDisable;
    private String paid;

    public FlightAdapter(NodesVO currentNode, ArrayList<LegsVO> itemsData) {
        this.itemsData = itemsData;
    }


    public void updateMyFlight(boolean mIsMyFlight) {
        this.mIsMyFlight = mIsMyFlight;
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
        if (position != 0) {
            viewHolder.flight_ticket_details_layout.setVisibility(View.GONE);
        } else {
            viewHolder.flight_cost.setText("$" + getFlightCost());

            viewHolder.flight_direction.setText(destinationFlights);
            viewHolder.flight_ticket_details_layout.setVisibility(View.VISIBLE);
        }
        if (position != itemsData.size() - 1) {
            viewHolder.show_alternative_flights.setVisibility(View.GONE);
        } else {
            viewHolder.show_alternative_flights.setVisibility(View.VISIBLE);
        }
        if (legFlightVO.getmType().equals("Leg")) {
            viewHolder.operatorName.setText(legFlightVO.getmCarrierName());
            viewHolder.flightNumber.setText(legFlightVO.getmCarrierCode() + legFlightVO.getmFlightNumber());
            viewHolder.flight_airport_text.setText(legFlightVO.getmOrigin() + " - " + legFlightVO.getmDestination());
            viewHolder.flight_boarding_text.setText(legFlightVO.getmDepartureTime());
            viewHolder.flight_duration_text.setText(legFlightVO.getmFlightTime());
            viewHolder.flight_class_text.setText(legFlightVO.getmFareClass());
            viewHolder.flight_details.setText(legFlightVO.getmOriginCityName()+", "+legFlightVO.getmOriginAirPortName()+"\n"
            +legFlightVO.getmDestinationCityName()+", "+legFlightVO.getmDestinationAirportName()+"\n"
                    + HGBUtility.parseDateToddMMyyyyMyTrip(legFlightVO.getmDeparture()));
            viewHolder.stop_over_include_layout.setVisibility(View.GONE);

        } else if (legFlightVO.getmType().equals("StopOver")) {
            viewHolder.stop_over_txt.setText(legFlightVO.getmCityName());
            viewHolder.stop_over_time.setText((int) legFlightVO.getmDurationHours() + "h " + (int) legFlightVO.getmDurationMinutes() + "m");

            viewHolder.includeAirplaneDetails.setVisibility(View.GONE);
        }

        if (mIsMyFlight) {
            viewHolder.image_my_flight.setVisibility(View.VISIBLE);
            viewHolder.text_my_flight.setVisibility(View.VISIBLE);
            viewHolder.select_flight.setVisibility(View.GONE);
            viewHolder.mBadgeImageView.setVisibility(View.VISIBLE);
            if (paid.equals("PAID")) {
                viewHolder.mBadgeImageView.setBackgroundResource(R.drawable.paid_badge);
                viewHolder.press_here_ll.setVisibility(View.VISIBLE);

            } else {
                viewHolder.mBadgeImageView.setBackgroundResource(R.drawable.cofirm_badge);
                viewHolder.press_here_ll.setVisibility(View.GONE);
            }

        } else {
            viewHolder.image_my_flight.setVisibility(View.GONE);
            viewHolder.text_my_flight.setVisibility(View.GONE);
            viewHolder.select_flight.setVisibility(View.VISIBLE);
            viewHolder.mBadgeImageView.setVisibility(View.GONE);
        }

        if (alternativeButtonDisable) {
            viewHolder.show_alternative_flights.setBackgroundResource(R.drawable.round_grey_button_shape);
            viewHolder.show_alternative_flights.setClickable(false);

        } else {
            viewHolder.show_alternative_flights.setBackgroundResource(R.drawable.round_azure_button_shape);
        }

        viewHolder.select_flight.setTag(legFlightVO.getmParentguid());
        viewHolder.press_here.setTag(legFlightVO.getmParentguid());

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

    public void setDestinationFlights(String destinationFlights) {
        this.destinationFlights = destinationFlights;
    }

    private static AlternativeFlightFragment.AlternativeButtonCB alternativeButtonCB;

    public void setButtonListener(AlternativeFlightFragment.AlternativeButtonCB alternativeButtonCB) {
        this.alternativeButtonCB = alternativeButtonCB;
    }

    public void setAlternativeButtonDisable(boolean alternativeButtonDisable) {
        this.alternativeButtonDisable = alternativeButtonDisable;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {

        private Button show_alternative_flights;
        private View flight_ticket_details_layout;
        private FontTextView operatorName;
        private FontTextView flightNumber;
        private FontTextView flight_airport_text;
        private FontTextView flight_boarding_text;
        private FontTextView flight_duration_text;
        private FontTextView flight_class_text;
        private FontTextView flight_meal_text;
        private FontTextView flight_aircraft_text;
        private FontTextView flight_baggage_text;
        private FontTextView flight_seat_selection_text;
        private FontTextView stop_over_txt;
        private FontTextView stop_over_time;
        private View includeAirplaneDetails;
        private View stop_over_include_layout;
        private FontTextView flight_direction;
        private FontTextView flight_cost;
        private ImageView image_my_flight;
        private FontTextView text_my_flight;
        private FontTextView select_flight;
        private FontTextView flight_details;
        private FontTextView press_here;
        private LinearLayout press_here_ll;

        private ImageView mBadgeImageView;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            initializeAirplaneDetails(itemLayoutView);
            stopOverInitialization(itemLayoutView);
            flightMainCostInitialization(itemLayoutView);
            show_alternative_flights = (Button) itemLayoutView.findViewById(R.id.show_alternative_flights);

            show_alternative_flights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alternativeButtonCB.showAlternative();
                }
            });


            select_flight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String guid = view.getTag().toString();
                    alternativeButtonCB.selectCurrentFlight(guid);
                }
            });

            press_here.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String guid = view.getTag().toString();
                    alternativeButtonCB.selectedPressEticket(guid);
                }
            });

        }


        private void initializeSelecteOrMyFlight(View mainView) {
            image_my_flight = (ImageView) mainView.findViewById(R.id.image_my_flight);
            text_my_flight = (FontTextView) mainView.findViewById(R.id.text_my_flight);
            select_flight = (FontTextView) mainView.findViewById(R.id.select_flight);
            flight_details = (FontTextView) mainView.findViewById(R.id.flight_details);

            press_here_ll = (LinearLayout) mainView.findViewById(R.id.select_tix_ll);
            press_here = (FontTextView) mainView.findViewById(R.id.select_tix_press_here);

        }

        private void flightMainCostInitialization(View mainView) {
            flight_ticket_details_layout = mainView.findViewById(R.id.flight_ticket_details_layout);
            flight_direction = (FontTextView) flight_ticket_details_layout.findViewById(R.id.flight_direction);
            flight_cost = (FontTextView) flight_ticket_details_layout.findViewById(R.id.flight_cost);
            mBadgeImageView = (ImageView) flight_ticket_details_layout.findViewById(R.id.flight_confirm_badge);
            initializeSelecteOrMyFlight(flight_ticket_details_layout);
        }


        private void stopOverInitialization(View mainView) {
            stop_over_include_layout = mainView.findViewById(R.id.stop_over_include_layout);
            stop_over_txt = (FontTextView) stop_over_include_layout.findViewById(R.id.stop_over_txt);
            stop_over_time = (FontTextView) stop_over_include_layout.findViewById(R.id.stop_over_time);
        }


        private void initializeAirplaneDetails(View itemLayoutView) {
            includeAirplaneDetails = itemLayoutView.findViewById(R.id.airplane_details_layout);
            operatorName = (FontTextView) includeAirplaneDetails.findViewById(R.id.operated_by_text);
            flightNumber = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_number_text);
            flight_airport_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_airport_text);
            flight_boarding_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_boarding_text);
            flight_duration_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_duration_text);
            flight_class_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_class_text);
            flight_meal_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_meal_text);
            // flight_meal_text.setText(legFlightVO.getmFareClass());

            flight_aircraft_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_aircraft_text);
            //flight_aircraft_text.setText(legFlightVO.getmFareClass());

            flight_baggage_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_baggage_text);

            flight_seat_selection_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_seat_selection_text);
        }

    }


}
