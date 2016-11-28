package hellogbye.com.hellogbyeandroid.adapters.flights;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;

import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightsDetailsFragment;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 10/7/15.
 */
public class AlternativeFlightsAdapter extends  RecyclerView.Adapter<AlternativeFlightsAdapter.ViewHolder> {


    private  List<NodesVO> itemsData;

    private OnItemClickListener mItemClickListener;
    private AlternativeFlightsDetailsFragment.ISortClickCB onSortClickCB;

    public void setData(List<NodesVO> itemsData){
        this.itemsData = itemsData;
    }

    @Override
    public AlternativeFlightsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alternate_flight_item, null);


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if(position == getItemCount()){
            viewHolder.viewDivider.setVisibility(View.GONE);
        }

        NodesVO itemData = itemsData.get(position);

        ArrayList<LegsVO> legs = itemData.getLegs();
        int stopOverCount = 0;
        for(LegsVO leg : legs){
            if(leg.getmType().equals("Leg")){
                stopOverCount = stopOverCount + 1;
            }
        }
        stopOverCount = stopOverCount - 1; //StopOver one less then legs
        if(stopOverCount > 0){
            viewHolder.alternative_flight_stops.setText("Stops: " + stopOverCount);
        }else{
            viewHolder.alternative_flight_stops.setText("Non-Stop");
        }


        HGBUtility.loadRoundedImage(legs.get(0).getmCarrierBadgeUrl(), viewHolder.alternative_airplane_image, R.drawable.profile_image);
        viewHolder.alternative_airplane_name.setText(itemData.getmOperatorName());

     //   viewHolder.view.drawAlternativeFlights(itemData);
       // String startTime = itemData.getmDepartureTime().split(" ",2)[0];
/*        viewHolder.txtStartTime.setText(HGBUtilityDate.parseDateToHHmm(itemData.getmDeparture()));
       // String endTime = itemData.getmArrivalTime().split(" ",2)[0];

        viewHolder.txtEndTime.setText(HGBUtilityDate.parseDateToHHmm(itemData.getmArrival()));
        viewHolder.txtTravelTime.setText(itemData.getmTravelTime());*/

        viewHolder.alternative_flight_price.setText("$"+HGBUtility.roundNumber(itemData.getCost()) + itemData.getmCurrency());
        viewHolder.alternative_flight_price.setTag(itemData.getmGuid());
        viewHolder.alternative_city_name_from.setText(itemData.getmOriginAirportName());
        viewHolder.alternative_airport_name_from.setText("("+itemData.getmOrigin()+")");

        viewHolder.alternative_city_name_to.setText(itemData.getmDestinationAirportName());
        viewHolder.alternative_airport_name_to.setText("("+itemData.getmDestination()+")");
        viewHolder.alternative_time_to.setText(legs.get(0).getmDepartureTime());
        viewHolder.alternative_time_from.setText(legs.get(legs.size()-1).getmArrivalTime());

        viewHolder.alternative_date_from.setText(HGBUtilityDate.parseDateToddMMyyyy(itemData.getmDeparture()));
        viewHolder.alternative_date_to.setText(HGBUtilityDate.parseDateToddMMyyyy(itemData.getmArrival()));

        if(position == 0){
            viewHolder.alternative_flight_title_ll.setVisibility(View.VISIBLE);
            viewHolder.alternative_flight_sort_ll.setVisibility(View.GONE);
            viewHolder.alternative_flight_title_text.setVisibility(View.VISIBLE);
            viewHolder.alternative_flight_title_text.setText("CURRENT FLIGHT");
        }else if(position == 1){
            viewHolder.alternative_flight_title_ll.setVisibility(View.VISIBLE);
            viewHolder.alternative_flight_sort_ll.setVisibility(View.VISIBLE);
            viewHolder.alternative_flight_title_text.setVisibility(View.VISIBLE);
            viewHolder.alternative_flight_title_text.setText("ALTERNATIVE FLIGHT");

            viewHolder.alternative_flight_sort_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSortClickCB.onSortClick();
                }
            });

        }else {
            viewHolder.alternative_flight_title_ll.setVisibility(View.GONE);
            viewHolder.alternative_flight_sort_ll.setVisibility(View.GONE);
        }


  /*      if(itemData.getLegs().size() > 1)
        {
            viewHolder.alternative_flight_multiple.setText("Multiple");
        }else{
            viewHolder.alternative_flight_multiple.setText("Single");
        }*/
      //  viewHolder.mainRelativeItem.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }

    public void setOnSortClick(AlternativeFlightsDetailsFragment.ISortClickCB onSortClickCB) {
        this.onSortClickCB = onSortClickCB;
    }


    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final FontTextView alternative_flight_price;
      //  private final FontTextView alternative_flight_multiple;
      /*  public FontTextView txtStartTime;
        public FontTextView txtEndTime;
        public FontTextView txtTravelTime;*/
   //     public FontTextView txtTravelCost;
     //   public GraphicsViewLayout view;
        public View viewDivider;
        private RelativeLayout mainRelativeItem;
   //     private FontTextView alternative_flight_guid;

        private FontTextView alternative_flight_stops;
        private FontTextView alternative_airplane_name;
        private ImageView alternative_airplane_image;
        private FontTextView alternative_city_name_from;
        private FontTextView alternative_airport_name_from;
        private FontTextView alternative_city_name_to;
        private FontTextView alternative_airport_name_to;
        private FontTextView alternative_time_from;
        private FontTextView alternative_time_to;
        private FontTextView alternative_date_from;
        private FontTextView alternative_date_to;

        private RelativeLayout alternative_flight_title_ll;
        private LinearLayout alternative_flight_sort_ll;
        private FontTextView alternative_flight_title_text;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
       //     alternative_flight_guid = (FontTextView)itemLayoutView.findViewById(R.id.alternative_flight_guid);
            mainRelativeItem = (RelativeLayout)itemLayoutView.findViewById(R.id.alternate_flight_item_rl);
       /*     txtStartTime = (FontTextView) itemLayoutView.findViewById(R.id.startTime);
            txtEndTime = (FontTextView) itemLayoutView.findViewById(R.id.endTime);
            txtTravelTime = (FontTextView) itemLayoutView.findViewById(R.id.travelTime);*/
         //   view = (GraphicsViewLayout)itemLayoutView.findViewById(R.id.view);
            viewDivider = (View)itemLayoutView.findViewById(R.id.alternative_list_divider);
         //   txtTravelCost = (FontTextView)itemLayoutView.findViewById(R.id.alternative_flight_price);
            alternative_flight_price = (FontTextView)itemLayoutView.findViewById(R.id.alternative_flight_price);
       //     alternative_flight_multiple = (FontTextView)itemLayoutView.findViewById(R.id.alternative_flight_multiple);
            alternative_airplane_image = (ImageView)itemLayoutView.findViewById(R.id.alternative_airplane_image);

            alternative_flight_stops = (FontTextView) itemLayoutView.findViewById(R.id.alternative_flight_stops);
            alternative_airplane_name = (FontTextView)itemLayoutView.findViewById(R.id.alternative_airplane_name);
            alternative_city_name_from = (FontTextView)itemLayoutView.findViewById(R.id.alternative_city_name_from);
            alternative_airport_name_from = (FontTextView)itemLayoutView.findViewById(R.id.alternative_airport_name_from);
            alternative_city_name_to = (FontTextView)itemLayoutView.findViewById(R.id.alternative_city_name_to);
            alternative_airport_name_to = (FontTextView)itemLayoutView.findViewById(R.id.alternative_airport_name_to);
            alternative_time_from = (FontTextView)itemLayoutView.findViewById(R.id.alternative_time_from);
            alternative_time_to = (FontTextView)itemLayoutView.findViewById(R.id.alternative_time_to);
            alternative_date_from = (FontTextView)itemLayoutView.findViewById(R.id.alternative_date_from);
            alternative_date_to = (FontTextView)itemLayoutView.findViewById(R.id.alternative_date_to);

            alternative_flight_title_ll = (RelativeLayout)itemLayoutView.findViewById(R.id.alternative_flight_title_ll);
            alternative_flight_sort_ll = (LinearLayout)itemLayoutView.findViewById(R.id.alternative_flight_sort_ll);
            alternative_flight_title_text = (FontTextView)itemLayoutView.findViewById(R.id.alternative_flight_title_text);

            itemLayoutView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            mItemClickListener.onItemClick(alternative_flight_price.getTag().toString());
        }

    }


    public interface OnItemClickListener {
        void onItemClick(String guid);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
