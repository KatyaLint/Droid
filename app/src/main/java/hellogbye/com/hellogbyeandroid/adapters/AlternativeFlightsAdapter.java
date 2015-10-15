package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;

/**
 * Created by nyawka on 10/7/15.
 */
public class AlternativeFlightsAdapter extends  RecyclerView.Adapter<AlternativeFlightsAdapter.ViewHolder> {


    private  List<AlternativeFlightsVO> itemsData;

    public AlternativeFlightsAdapter(Context context,  List<AlternativeFlightsVO> itemsData) {
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
        AlternativeFlightsVO itemData = itemsData.get(position);
        viewHolder.view.drawAlternativeFlights(itemData);
       // String startTime = itemData.getmDepartureTime().split(" ",2)[0];
        viewHolder.txtStartTime.setText(itemData.getmDepartureTime());
       // String endTime = itemData.getmArrivalTime().split(" ",2)[0];

        viewHolder.txtEndTime.setText(itemData.getmArrivalTime());
        viewHolder.txtTravelTime.setText(itemData.getmTravelTime());

        viewHolder.txtTravelCost.setText("$"+itemData.getCost());
    }

    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtStartTime;
        public TextView txtEndTime;
        public TextView txtTravelTime;
        public TextView txtTravelCost;
        public GraphicsViewLayout view;
        public View viewDivider;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtStartTime = (TextView) itemLayoutView.findViewById(R.id.startTime);
            txtEndTime = (TextView) itemLayoutView.findViewById(R.id.endTime);
            txtTravelTime = (TextView) itemLayoutView.findViewById(R.id.travelTime);
            view = (GraphicsViewLayout)itemLayoutView.findViewById(R.id.view);
            viewDivider = (View)itemLayoutView.findViewById(R.id.alternative_list_divider);
            txtTravelCost = (TextView)itemLayoutView.findViewById(R.id.alternative_flight_price);
        }
    }


}
