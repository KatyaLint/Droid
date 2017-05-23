package hellogbye.com.hellogbyeandroid.adapters.hotel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.flights.AlternativeFlightFareClassAdapter;
import hellogbye.com.hellogbyeandroid.adapters.flights.AlternativeFlightsSortAdapter;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.IWebViewClicked;
import hellogbye.com.hellogbyeandroid.models.vo.flights.FairclassPreferencesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.WrapContentViewPager;



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
    private IWebViewClicked webViewLinkClicked;
    private Context mContext;
    private NodesVO currentNode;
    private AlternativeFlightsSortAdapter alternativeFlightsSortAdapter;

    private AlternativeFlightFareClassAdapter mFlightFareClassAdapter;
    private AlternativeFlightFragment.IFareClassClickListener onFareClassClickListener;
    private AlertDialog alertDialog;
    private ListView user_profile_popup_list_view;
    private ArrayList<String> preferred_seat_type_list;


    public FlightAdapter(NodesVO currentNode, ArrayList<LegsVO> itemsData, Context context) {

        this.itemsData = itemsData;
        this.mContext = context;
        this.currentNode = currentNode;
        this.mFlightFareClassAdapter = new AlternativeFlightFareClassAdapter(currentNode.getDropdownoptions(), mContext);

        sortDialog(context);
    }

    private void sortDialog(Context context){

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup_custom_title_alternative_sort, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        FontTextView profile_title_layout_text = (FontTextView)promptsView.findViewById(R.id.profile_title_layout_text);
        profile_title_layout_text.setText(context.getResources().getString(R.string.flight_alternative_preferred_seat_type));
        dialogBuilder.setCustomTitle(promptsView);



        String[] preferred_seat_type = context.getResources().getStringArray(R.array.preferred_seat_type);
        preferred_seat_type_list = new ArrayList<String>(Arrays.asList(preferred_seat_type));
        this.alternativeFlightsSortAdapter = new AlternativeFlightsSortAdapter(preferred_seat_type_list);


        alternativeFlightsSortAdapter.setSelectedID(currentNode.getSelectedSeatType());


        View promptsViewTeest = li.inflate(R.layout.popup_alternative_layout_sort, null);
        user_profile_popup_list_view = (ListView) promptsViewTeest.findViewById(R.id.alternative_popup_sort);

        user_profile_popup_list_view.setAdapter(alternativeFlightsSortAdapter);

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            } });



        //Create alert dialog object via builder
        alertDialog = dialogBuilder.create();
        alertDialog.setView(promptsViewTeest);
        alertDialog.setCancelable(false);

        Button positive_button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (positive_button != null) {
            positive_button.setTextColor(context.getResources()
                    .getColor(R.color.COLOR_EE3A3C));
        }
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
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {


        LegsVO legFlightVO = itemsData.get(position);
        viewHolder.flight_tickets_destination_preferred_seat_rl.setTag(position);

        if (position != 0) {
            viewHolder.flight_tickets_rl.setVisibility(View.GONE);
            viewHolder.flight_details_fare_class_ll.setVisibility(View.GONE);
            viewHolder.flight_tickets_destination_preferred_seat_rl.setVisibility(View.GONE);
          //  viewHolder.flight_tickets_destination_preferred_seat_rl.setTag(position);
        }
        else
        {

            viewHolder.flight_ticket_details_seat_type_preferred.setText( preferred_seat_type_list.get(currentNode.getSelectedSeatType())  );
            viewHolder.flight_cost.setText("$" + HGBUtility.roundNumber(getFlightCost()) + currentNode.getmCurrency());
            viewHolder.flight_direction.setText(destinationFlights);
            viewHolder.flight_tickets_rl.setVisibility(View.VISIBLE);

          //  ArrayList<FairclassPreferencesVO> dropDownOptions = currentNode.getDropdownoptions();
            if(!currentNode.getDropdownoptions().isEmpty()) {
                viewHolder.flight_details_fare_class_ll.setVisibility(View.VISIBLE);
            }else{
                viewHolder.flight_details_fare_class_ll.setVisibility(View.GONE);
            }
            viewHolder.flight_details_view_pager.setAdapter(mFlightFareClassAdapter);


           // viewHolder.mFlightFareClassAdapter.setDataset(dropDownOptions);

        }



     /*   if (position != itemsData.size() - 1) {
            viewHolder.show_alternative_flights.setVisibility(View.GONE);
        } else {
            viewHolder.show_alternative_flights.setVisibility(View.VISIBLE);
        }*/
        if (legFlightVO.getmType().equals("Leg")) {
            viewHolder.operatorName.setText(legFlightVO.getmCarrierName());
            viewHolder.flightNumber.setText(legFlightVO.getmCarrierCode() + legFlightVO.getmFlightNumber());
            viewHolder.flight_airport_text.setText(legFlightVO.getmOrigin() + " - " + legFlightVO.getmDestination());
            viewHolder.flight_boarding_text.setText(legFlightVO.getmDepartureTime());
            viewHolder.flight_duration_text.setText(legFlightVO.getmFlightTime());
            viewHolder.flight_arrival.setText(legFlightVO.getmArrivalTime());
            viewHolder.flight_aircraft_text.setText(legFlightVO.getmEquipment());
            viewHolder.flight_class_text.setText(legFlightVO.getmFareClass());

            String departureDate = HGBUtilityDate.parseDateToddMMyyyyMyTrip(legFlightVO.getmDeparture());



            viewHolder.flight_tickets_destination_from.setText(legFlightVO.getmOriginCityName() + "," + legFlightVO.getmOriginAirPortName());
         //   viewHolder.flight_tickets_destination_stops.setText(legFlightVO.get + "," + legFlightVO.getmOriginAirPortName());
            viewHolder.flight_tickets_destination_to.setText(legFlightVO.getmDestinationCityName()+", "+legFlightVO.getmDestinationAirportName());
            viewHolder.flight_tickets_destination_time.setText("Depart: " + legFlightVO.getmDepartureTime()+"     Arrival: " +legFlightVO.getmArrivalTime());
           // flight_tickets_destination_time

            if (legFlightVO.getmType().equals("StopOver")) {
                viewHolder.flight_tickets_destination_stops.setText("To: " + legFlightVO.getmCityName() + ", " + legFlightVO.getmAirportName() + " Airport");
            }

           /* viewHolder.flight_details.setText(legFlightVO.getmOriginCityName()+", "+legFlightVO.getmOriginAirPortName()+"\n"
            +legFlightVO.getmDestinationCityName()+", "+legFlightVO.getmDestinationAirportName()+"\n"
                    + departureDate);*/

            viewHolder.stop_over_include_layout.setVisibility(View.GONE);
            viewHolder.flight_date.setText(departureDate);
            HGBUtility.loadAirplainImage( legFlightVO.getmCarrierBadgeUrl(),  viewHolder.airplane_details_operated_image);


        } else if (legFlightVO.getmType().equals("StopOver")) {
            viewHolder.stop_over_txt.setText(" " +legFlightVO.getmCityName()+", " +legFlightVO.getmAirportName() + " Airport");
            viewHolder.stop_over_time.setText((int) legFlightVO.getmDurationHours() + "h " + (int) legFlightVO.getmDurationMinutes() + "m");
            viewHolder.airplane_details_ll.setVisibility(View.GONE);
        }

        viewHolder.flight_tickets_destination_preferred_seat_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                user_profile_popup_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View viewPopup, int position, long id) {

                        FontTextView flight_ticket_details_seat_type_preferred = (FontTextView) view.findViewById(R.id.flight_ticket_details_seat_type_preferred);

                        currentNode.setSelectedSeatType(position);
                        flight_ticket_details_seat_type_preferred.setText( preferred_seat_type_list.get(currentNode.getSelectedSeatType()));
                        //    onFareClassClickListener.onSeatTypeClicked(""+position);


                        alternativeFlightsSortAdapter.setSelectedID(position);

               /* String selectedType = preferred_seat_type_list.get(position);
                alternativeFlightsSortAdapter.setSelectedID(position);
                currentNode.setSelectedSeatType(selectedType);
                alternativeFlightsSortAdapter.notifyDataSetChanged();*/



               /* if(tagPosition != null && tagPosition.equals(0)) {
                     flight_ticket_details_seat_type_preferred.setText(selectedType);
                    alternativeFlightsSortAdapter.notifyDataSetChanged();
                }*/
                        alertDialog.dismiss();

                    }
                });




                alertDialog.show();

            //    mFlightFareClassAdapter.set

                   /* String guid = view.getTag().toString();
                    alternativeButtonCB.selectedPressEticket(guid);*/
            }
        });


        if (mIsMyFlight) {
         /*   viewHolder.image_my_flight.setVisibility(View.VISIBLE);
            viewHolder.text_my_flight.setVisibility(View.VISIBLE);*/


         //   viewHolder.select_flight.setVisibility(View.GONE);

            if (paid.equals("PAID")) {
                viewHolder.mBadgeImageView.setBackgroundResource(R.drawable.paid_badge);
                viewHolder.press_here_ll.setVisibility(View.VISIBLE);

            } else {
                viewHolder.mBadgeImageView.setBackgroundResource(R.drawable.cofirm_badge);
                viewHolder.press_here_ll.setVisibility(View.GONE);
            }

        } else {
  /*          viewHolder.image_my_flight.setVisibility(View.GONE);
            viewHolder.text_my_flight.setVisibility(View.GONE);*/


         //   viewHolder.select_flight.setVisibility(View.VISIBLE);
            viewHolder.mBadgeImageView.setVisibility(View.GONE);
        }

        if (alternativeButtonDisable) {
            //TODO check if needed
        //    viewHolder.show_alternative_flights.setBackgroundResource(R.drawable.round_grey_button_shape);
            viewHolder.show_alternative_flights.setClickable(false);

        } else {
            viewHolder.show_alternative_flights.setBackgroundResource(R.drawable.red_button);
        }

    //    viewHolder.select_flight.setTag(legFlightVO.getmParentguid());
        viewHolder.press_here.setTag(legFlightVO.getmParentguid());


        viewHolder.show_alternative_flights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alternativeButtonCB.showAlternative();
                }
            });


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

    public void setWebViewLinkClicked(IWebViewClicked webViewLinkClicked) {
        this.webViewLinkClicked = webViewLinkClicked;
    }

    public void setOnFareClassClickListener(AlternativeFlightFragment.IFareClassClickListener onFareClassClickListener) {
        this.onFareClassClickListener = onFareClassClickListener;
        mFlightFareClassAdapter.setOnClickFareClass(onFareClassClickListener);
    }



    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final FontTextView flight_ticket_details_seat_type_preferred;
        private  RelativeLayout flight_tickets_destination_preferred_seat_rl;
        private FontTextView show_alternative_flights;
      //  private View flight_ticket_details_layout;
        private FontTextView operatorName;
        private FontTextView flightNumber;
        private FontTextView flight_airport_text;
        private FontTextView flight_boarding_text;
        private FontTextView flight_duration_text;
        private FontTextView flight_class_text;
     //   private FontTextView flight_meal_text;
        private FontTextView flight_aircraft_text;
       // private FontTextView flight_baggage_text;
       // private FontTextView flight_seat_selection_text;
        private FontTextView stop_over_txt;
        private FontTextView stop_over_time;
       // private View includeAirplaneDetails;
        private View stop_over_include_layout;
        private FontTextView flight_direction;
        private FontTextView flight_cost;
  /*      private ImageView image_my_flight;
        private FontTextView text_my_flight;*/

      //  private FontTextView select_flight;

        private FontTextView flight_details;


        private FontTextView press_here;
        private LinearLayout press_here_ll;

        private ImageView mBadgeImageView;
        private ImageView airplane_details_operated_image;
        private LinearLayout flight_addition_layout;
        private FontTextView flight_arrival;
        private FontTextView flight_aircraft_type_title;
        private FontTextView flight_date;

        private RelativeLayout flight_tickets_rl;
        private LinearLayout airplane_details_ll;
        private WrapContentViewPager flight_details_view_pager;

        private LinearLayout flight_details_fare_class_ll;


        private FontTextView flight_tickets_destination_from;
        private FontTextView flight_tickets_destination_stops;
        private FontTextView flight_tickets_destination_to;
        private FontTextView flight_tickets_destination_time;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            initializeAirplaneDetails(itemLayoutView);
            stopOverInitialization(itemLayoutView);
            flightMainCostInitialization(itemLayoutView);

            show_alternative_flights = (FontTextView) itemLayoutView.findViewById(R.id.show_alternative_flights);

            flight_tickets_destination_preferred_seat_rl = (RelativeLayout)itemLayoutView.findViewById(R.id.flight_tickets_destination_preferred_seat_rl);
            flight_ticket_details_seat_type_preferred = (FontTextView)itemLayoutView.findViewById(R.id.flight_ticket_details_seat_type_preferred);


          /*  show_alternative_flights = (FontTextView) itemLayoutView.findViewById(R.id.show_alternative_flights);

            show_alternative_flights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alternativeButtonCB.showAlternative();
                }
            });*/


         /*   select_flight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String guid = view.getTag().toString();
                    alternativeButtonCB.selectCurrentFlight(guid);
                }
            });*/

            press_here.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String guid = view.getTag().toString();
                    alternativeButtonCB.selectedPressEticket(guid);
                }
            });


            flight_addition_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webViewLinkClicked.webLinkClicked();

                }
            });



            initializeViewPager(itemLayoutView);


        }


        private void initializeViewPager(View mainView){

            flight_details_view_pager = (WrapContentViewPager) mainView.findViewById(R.id.flight_details_view_pager);
            flight_details_fare_class_ll = (LinearLayout)mainView.findViewById(R.id.flight_details_fare_class_ll);
            if(currentNode.getDropdownoptions().isEmpty()){
             //   flight_details_view_pager.setVisibility(View.GONE);
                flight_details_fare_class_ll.setVisibility(View.GONE);
            }else{
              //  flight_details_view_pager.setVisibility(View.VISIBLE);
                flight_details_fare_class_ll.setVisibility(View.VISIBLE);
            }


        }

        private void initializeSelecteOrMyFlight(View mainView) {
/*            image_my_flight = (ImageView) mainView.findViewById(R.id.image_my_flight);
            text_my_flight = (FontTextView) mainView.findViewById(R.id.text_my_flight);*/


      //      select_flight = (FontTextView) mainView.findViewById(R.id.select_flight);

            flight_tickets_destination_from = (FontTextView)mainView.findViewById(R.id.flight_tickets_destination_from);
            flight_tickets_destination_stops = (FontTextView)mainView.findViewById(R.id.flight_tickets_destination_stops);
            flight_tickets_destination_to  = (FontTextView)mainView.findViewById(R.id.flight_tickets_destination_to);
            flight_tickets_destination_time = (FontTextView)mainView.findViewById(R.id.flight_tickets_destination_time);

          //  flight_details = (FontTextView) mainView.findViewById(R.id.flight_details);

            press_here_ll = (LinearLayout) mainView.findViewById(R.id.select_tix_ll);
            press_here = (FontTextView) mainView.findViewById(R.id.select_tix_press_here);

        }

        private void flightMainCostInitialization(View mainView) {
          /*  flight_ticket_details_layout = mainView.findViewById(R.id.flight_ticket_details_layout);
            flight_direction = (FontTextView) flight_ticket_details_layout.findViewById(R.id.flight_direction);
            flight_cost = (FontTextView) flight_ticket_details_layout.findViewById(R.id.flight_cost);
            mBadgeImageView = (ImageView) flight_ticket_details_layout.findViewById(R.id.flight_confirm_badge);
            initializeSelecteOrMyFlight(flight_ticket_details_layout);*/

        //    flight_ticket_details_layout = mainView.findViewById(R.id.flight_ticket_details_layout);
            flight_direction = (FontTextView) mainView.findViewById(R.id.flight_direction);
            flight_cost = (FontTextView) mainView.findViewById(R.id.flight_cost);
            mBadgeImageView = (ImageView) mainView.findViewById(R.id.flight_confirm_badge);

            flight_tickets_rl = (RelativeLayout)mainView.findViewById(R.id.flight_tickets_rl);
            initializeSelecteOrMyFlight(mainView);

        }


        private void stopOverInitialization(View mainView) {
            stop_over_include_layout = mainView.findViewById(R.id.stop_over_include_layout);
            stop_over_txt = (FontTextView) stop_over_include_layout.findViewById(R.id.stop_over_txt);
            stop_over_time = (FontTextView) stop_over_include_layout.findViewById(R.id.stop_over_time);
        }


        private void initializeAirplaneDetails(View itemLayoutView) {
            airplane_details_ll = (LinearLayout) itemLayoutView.findViewById(R.id.airplane_details_ll);
          //  includeAirplaneDetails = itemLayoutView.findViewById(R.id.airplane_details_layout);
            operatorName = (FontTextView) itemLayoutView.findViewById(R.id.operated_by_text);
            flightNumber = (FontTextView) itemLayoutView.findViewById(R.id.flight_number_text);
            flight_airport_text = (FontTextView) itemLayoutView.findViewById(R.id.flight_airport_text);
            flight_boarding_text = (FontTextView) itemLayoutView.findViewById(R.id.flight_boarding_text);
            flight_duration_text = (FontTextView) itemLayoutView.findViewById(R.id.flight_duration_text);
            flight_class_text = (FontTextView) itemLayoutView.findViewById(R.id.flight_class_text);

            airplane_details_operated_image = (ImageView)itemLayoutView.findViewById(R.id.airplane_details_operated_image);


            flight_arrival = (FontTextView)itemLayoutView.findViewById(R.id.flight_arrival);

  /*          flight_meal_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_meal_text);*/
            // flight_meal_text.setText(legFlightVO.getmFareClass());

            flight_aircraft_text = (FontTextView) itemLayoutView.findViewById(R.id.flight_aircraft_text);
            //flight_aircraft_text.setText(legFlightVO.getmFareClass());

     /*       flight_baggage_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_baggage_text);

            flight_seat_selection_text = (FontTextView) includeAirplaneDetails.findViewById(R.id.flight_seat_selection_text);*/



            flight_addition_layout = (LinearLayout)itemLayoutView.findViewById(R.id.flight_addition_layout);

            flight_aircraft_type_title = (FontTextView)itemLayoutView.findViewById(R.id.flight_aircraft_type_title);
            flight_date = (FontTextView)itemLayoutView.findViewById(R.id.flight_date);

        }

    }


}
