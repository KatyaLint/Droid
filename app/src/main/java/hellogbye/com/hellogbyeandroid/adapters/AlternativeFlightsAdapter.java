package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import hellogbye.com.hellogbyeandroid.R;

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
        viewHolder.view.drawAlternativeFlights(itemData);
       // String startTime = itemData.getmDepartureTime().split(" ",2)[0];
        viewHolder.txtStartTime.setText(HGBUtilityDate.parseDateToHHmm(itemData.getmDeparture()));
       // String endTime = itemData.getmArrivalTime().split(" ",2)[0];

        viewHolder.txtEndTime.setText(HGBUtilityDate.parseDateToHHmm(itemData.getmArrival()));
        viewHolder.txtTravelTime.setText(itemData.getmTravelTime());

        viewHolder.alternative_flight_price.setText("$"+itemData.getCost());
        viewHolder.alternative_flight_price.setTag(itemData.getmGuid());

        if(itemData.getLegs().size() > 1)
        {
            viewHolder.alternative_flight_multiple.setText("Multiple");
        }else{
            viewHolder.alternative_flight_multiple.setText("Single");
        }
      //  viewHolder.mainRelativeItem.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }



    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final FontTextView alternative_flight_price;
        private final FontTextView alternative_flight_multiple;
        public FontTextView txtStartTime;
        public FontTextView txtEndTime;
        public FontTextView txtTravelTime;
   //     public FontTextView txtTravelCost;
        public GraphicsViewLayout view;
        public View viewDivider;
        private RelativeLayout mainRelativeItem;
   //     private FontTextView alternative_flight_guid;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
       //     alternative_flight_guid = (FontTextView)itemLayoutView.findViewById(R.id.alternative_flight_guid);
            mainRelativeItem = (RelativeLayout)itemLayoutView.findViewById(R.id.alternate_flight_item_rl);
            txtStartTime = (FontTextView) itemLayoutView.findViewById(R.id.startTime);
            txtEndTime = (FontTextView) itemLayoutView.findViewById(R.id.endTime);
            txtTravelTime = (FontTextView) itemLayoutView.findViewById(R.id.travelTime);
            view = (GraphicsViewLayout)itemLayoutView.findViewById(R.id.view);
            viewDivider = (View)itemLayoutView.findViewById(R.id.alternative_list_divider);
         //   txtTravelCost = (FontTextView)itemLayoutView.findViewById(R.id.alternative_flight_price);
            alternative_flight_price = (FontTextView)itemLayoutView.findViewById(R.id.alternative_flight_price);
            alternative_flight_multiple = (FontTextView)itemLayoutView.findViewById(R.id.alternative_flight_multiple);
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
