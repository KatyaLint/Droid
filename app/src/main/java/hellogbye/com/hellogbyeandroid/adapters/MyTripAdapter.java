package hellogbye.com.hellogbyeandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 12/15/15.
 */
public class MyTripAdapter extends RecyclerView.Adapter<MyTripAdapter.ViewHolder> {

    private ArrayList<MyTripItem> itemsData;
    public MyTripAdapter(ArrayList<MyTripItem> itemsData) {
        this.itemsData = itemsData;
    }


    @Override
    public MyTripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mytrip_item_layout, null);


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyTripAdapter.ViewHolder holder, int position) {

        MyTripItem item = itemsData.get(position);

        holder.name.setText(item.getName());
        holder.date.setText(item.getStartdate());
        if(item.getPaymentstatus().equals("UPD")){
            holder.paid.setText("UNPAID");
        }else{
            holder.paid.setText("PAID");
        }

    }

    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }


    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {

        private FontTextView name;
        private FontTextView date;
        private FontTextView paid;





        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name = (FontTextView) itemLayoutView.findViewById(R.id.my_trip_name);
            date = (FontTextView) itemLayoutView.findViewById(R.id.my_trip_dates);
            paid = (FontTextView) itemLayoutView.findViewById(R.id.my_trip_paid);


        }

    }


}
