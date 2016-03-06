package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    private boolean isEditMode = false;
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

    public void isEditMode(boolean b) {
        this.isEditMode = b;
    }

    public boolean isEditMode(){
        return this.isEditMode;
    }

    public static class ViewHolderItem {


        private final FontTextView my_trip_name;
        private final FontTextView my_trip_dates;
        private final FontTextView my_trip_paid;
        private final ImageView my_trip_arrow_next;
        private ImageView imageEditTrips;
//        private FontTextView my_trip_delete_forever;
        private Button my_trip_delete_forever;
        private ImageView my_trip_user_image;
        private boolean isDeleteItemClicked = false;

        public ViewHolderItem(View view) {
            imageEditTrips = (ImageView) view.findViewById(R.id.my_trip_delete);
//            imageEditTrips.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    System.out.println("Kate Delete");
////                    isDeleteItemClicked = true;
//                  //  my_trip_dates.setText("Clickkkkkeeedddd");
//                    isDeleteItemClicked = true;
//                    my_trip_delete_forever.setVisibility(View.VISIBLE);
//
//
//                }
//            });
//            my_trip_delete_forever = (FontTextView)view.findViewById(R.id.my_trip_delete_forever);
            my_trip_delete_forever = (Button)view.findViewById(R.id.my_trip_delete_forever);
            my_trip_user_image = (ImageView) view.findViewById(R.id.my_trip_user_image);
            my_trip_name = (FontTextView) view.findViewById(R.id.my_trip_name);
            my_trip_dates = (FontTextView) view.findViewById(R.id.my_trip_dates);
            my_trip_paid = (FontTextView) view.findViewById(R.id.my_trip_paid);
            my_trip_arrow_next = (ImageView) view.findViewById(R.id.my_trip_arrow_next);

        }


        public void setIsDeleteClick(boolean isClick){
            this.isDeleteItemClicked = isClick;
        }
        public boolean getIsDelteClicked(){
            return isDeleteItemClicked;
        }
    }

//    public static class ViewHolderHeaderItem {
//        public ViewHolderHeaderItem(View view) {
//        }
//    }



    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {

         ViewHolderItem holder;
        View itemView = convertView;


        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView =  inflator.inflate(R.layout.mytrip_item_layout, null);
            holder = new ViewHolderItem(itemView);
            itemView.setTag(holder);
        } else {
            holder = (ViewHolderItem)itemView.getTag();
        }

        final MyTripItem item = items.get(position);

        if(isEditMode) {
            holder.imageEditTrips.setVisibility(View.VISIBLE);
            holder.imageEditTrips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setEditDelete(true);
                    notifyDataSetChanged();
                }
            });
            holder.my_trip_arrow_next.setVisibility(View.GONE);
        }else{
            item.setEditDelete(false);
            holder.imageEditTrips.setVisibility(View.GONE);
            holder.my_trip_arrow_next.setVisibility(View.VISIBLE);
            holder.my_trip_delete_forever.setVisibility(View.GONE);
        }



        if(section == 0 ){
            holder.my_trip_name.setText("Current");
            holder.my_trip_dates.setText("Current");
            if(item.getPaymentstatus().equals("UPD")){
                holder.my_trip_paid.setText("UNPAID");
            }else{
                holder.my_trip_paid.setText("PAID");
            }

        }else{

            if(item.isEditDelete()){
                holder.my_trip_delete_forever.setVisibility(View.VISIBLE);
            }else{
                holder.my_trip_delete_forever.setVisibility(View.GONE);
            }

            holder.my_trip_user_image.setBackgroundResource(R.drawable.cityavatar);
            holder.my_trip_name.setText(item.getName());
            holder.my_trip_dates.setText(HGBUtility.parseDateToddMMyyyyMyTrip(item.getStartdate())+" - "+HGBUtility.parseDateToddMMyyyyMyTrip(item.getEnddate()));
            if(item.getPaymentstatus().equals("UPD")){
                holder.my_trip_paid.setText("UNPAID");
            }else{
                holder.my_trip_paid.setText("PAID");
            }
        }


        return itemView;
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



            layout.setVisibility(View.VISIBLE);
            if(section == 0){
                ((FontTextView) layout.findViewById(R.id.header_text)).setText("Current Itinerary");
            }else if (section == 1){
                ((FontTextView) layout.findViewById(R.id.header_text)).setText("My Trips");
            }




        return layout;
    }

}