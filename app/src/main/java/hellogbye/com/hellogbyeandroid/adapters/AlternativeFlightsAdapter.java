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

/**
 * Created by nyawka on 10/7/15.
 */
public class AlternativeFlightsAdapter extends  RecyclerView.Adapter<AlternativeFlightsAdapter.ViewHolder> {


    private  List<NodesVO> itemsData;

    private OnItemClickListener mItemClickListener;
    public AlternativeFlightsAdapter(List<NodesVO> itemsData) {
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
        viewHolder.txtStartTime.setText(HGBUtility.parseDateToHHmm(itemData.getmDeparture()));
       // String endTime = itemData.getmArrivalTime().split(" ",2)[0];

        viewHolder.txtEndTime.setText(HGBUtility.parseDateToHHmm(itemData.getmArrival()));
        viewHolder.txtTravelTime.setText(itemData.getmTravelTime());

        viewHolder.txtTravelCost.setText("$"+itemData.getCost());
        viewHolder.txtTravelCost.setTag(itemData.getmGuid());

      //  viewHolder.mainRelativeItem.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }



    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtStartTime;
        public TextView txtEndTime;
        public TextView txtTravelTime;
        public TextView txtTravelCost;
        public GraphicsViewLayout view;
        public View viewDivider;
        private RelativeLayout mainRelativeItem;
        private TextView alternative_flight_guid;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            alternative_flight_guid = (TextView)itemLayoutView.findViewById(R.id.alternative_flight_guid);
            mainRelativeItem = (RelativeLayout)itemLayoutView.findViewById(R.id.alternate_flight_item_rl);
            txtStartTime = (TextView) itemLayoutView.findViewById(R.id.startTime);
            txtEndTime = (TextView) itemLayoutView.findViewById(R.id.endTime);
            txtTravelTime = (TextView) itemLayoutView.findViewById(R.id.travelTime);
            view = (GraphicsViewLayout)itemLayoutView.findViewById(R.id.view);
            viewDivider = (View)itemLayoutView.findViewById(R.id.alternative_list_divider);
            txtTravelCost = (TextView)itemLayoutView.findViewById(R.id.alternative_flight_price);
            itemLayoutView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            View root = v.getRootView();
            TextView textView = (TextView)v.findViewById(R.id.alternative_flight_price);
            mItemClickListener.onItemClick(textView.getTag().toString());
        }

    }



    public interface OnItemClickListener {
        void onItemClick(String guid);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
