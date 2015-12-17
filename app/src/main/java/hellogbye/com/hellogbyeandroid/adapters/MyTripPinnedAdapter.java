package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 12/15/15.
 */
public class MyTripPinnedAdapter extends SectionedBaseAdapter {

    private ArrayList<MyTripItem> items;

    public MyTripPinnedAdapter (ArrayList<MyTripItem> items){
        this.items = items;

    }

    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSectionCount() {
        return 2;
    }

    @Override
    public int getCountForSection(int section) {
        if(section == 0){
            return 1;
        }else if (section ==1 ){
            return items.size();
        }else{
            return 1;
        }
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        View view = null;
        MyTripItem item = items.get(position);
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (View) inflator.inflate(R.layout.mytrip_item_layout, null);
        } else {
            view = (View) convertView;
        }

        if(section == 0 ){
            ((FontTextView) view.findViewById(R.id.my_trip_name)).setText("Current");
            ((FontTextView) view.findViewById(R.id.my_trip_dates)).setText("Current");
            if(item.getPaymentstatus().equals("UPD")){
                ((FontTextView) view.findViewById(R.id.my_trip_paid)).setText("UNPAID");
            }else{
                ((FontTextView) view.findViewById(R.id.my_trip_paid)).setText("PAID");
            }

        }else{
            ((FontTextView) view.findViewById(R.id.my_trip_name)).setText(item.getName());
            ((FontTextView) view.findViewById(R.id.my_trip_dates)).setText(HGBUtility.parseDateToddMMyyyyMyTrip(item.getStartdate())+" - "+HGBUtility.parseDateToddMMyyyyMyTrip(item.getEnddate()));
            if(item.getPaymentstatus().equals("UPD")){
                ((FontTextView) view.findViewById(R.id.my_trip_paid)).setText("UNPAID");
            }else{
                ((FontTextView) view.findViewById(R.id.my_trip_paid)).setText("PAID");
            }
        }


        return view;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.mytrip_header, null);
        } else {
            layout = (LinearLayout) convertView;
        }

        if(section == 0){
            ((FontTextView) layout.findViewById(R.id.header_text)).setText("Current Itinerary");
        }else if (section == 1){
            ((FontTextView) layout.findViewById(R.id.header_text)).setText("My Trips");
        }

        return layout;
    }

}