package hellogbye.com.hellogbyeandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;

/**
 * Created by arisprung on 12/15/15.
 */
public class MyTripPinnedAdapter extends SectionedBaseAdapter {


    private ArrayList<MyTripItem> items;
    private boolean isEditMode = false;
    private int maxCurrentInitialization = 0;

    public MyTripPinnedAdapter (ArrayList<MyTripItem> items, Activity acitivity){
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
            return getMaxCurrentInitialization();
        }else if (section ==1 ){
            return items.size();
        }
      //  return items.size();

        else{
            return 1;
        }
    }

    public void isEditMode(boolean b) {
        this.isEditMode = b;
    }

    public boolean isEditMode(){
        return this.isEditMode;
    }

    public int getMaxCurrentInitialization() {
        return maxCurrentInitialization;
    }

    public void setMaxCurrentInitialization(int maxCurrentInitialization) {
        this.maxCurrentInitialization = maxCurrentInitialization;
    }

    public static class ViewHolderItem {


        private final FontTextView my_trip_name;
        private final FontTextView my_trip_dates;
        private final FontTextView my_trip_paid;
        private final ImageView my_trip_arrow_next;
        private ImageView imageEditTrips;
//        private FontTextView my_trip_delete_forever;
        private Button my_trip_delete_forever;
        private RoundedImageView my_trip_user_image;
        private boolean isDeleteItemClicked = false;

        public ViewHolderItem(View view) {
            imageEditTrips = (ImageView) view.findViewById(R.id.my_trip_delete);

            my_trip_delete_forever = (Button)view.findViewById(R.id.my_trip_delete_forever);
            my_trip_user_image = (RoundedImageView) view.findViewById(R.id.my_trip_user_image);
            my_trip_name = (FontTextView) view.findViewById(R.id.my_trip_name);
            my_trip_dates = (FontTextView) view.findViewById(R.id.my_trip_dates);
            my_trip_paid = (FontTextView) view.findViewById(R.id.my_trip_paid);
            my_trip_arrow_next = (ImageView) view.findViewById(R.id.my_trip_arrow_next);

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

        if(items.isEmpty()){
            return itemView;
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
//get city image
         //   cityURLBase = cityURLBase + "NYC.jpg";//item.getName();
        //    holder.my_trip_user_image.setBackgroundResource(R.drawable.cityavatar);
            System.out.println("Kate  item.getUrlToCityView() =" +  item.getUrlToCityView());
            HGBUtility.loadRoundedImage( item.getUrlToCityView(),  holder.my_trip_user_image, R.drawable.cityavatar);
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
                if(getMaxCurrentInitialization() == 0){
                    ((FontTextView) layout.findViewById(R.id.header_text)).setVisibility(View.GONE);
                }else {
                    ((FontTextView) layout.findViewById(R.id.header_text)).setText("Current Itinerary");
                }
            }else if (section == 1){
                ((FontTextView) layout.findViewById(R.id.header_text)).setText("My Trips");
            }




        return layout;
    }


    public void animateTo(List<MyTripItem> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<MyTripItem> newModels) {
        for (int i = items.size() - 1; i >= 0; i--) {
            final MyTripItem model = items.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<MyTripItem> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final MyTripItem model = newModels.get(i);
            if (!items.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<MyTripItem> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final MyTripItem model = newModels.get(toPosition);
            final int fromPosition = items.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public List<MyTripItem> removeItem(int position) {
        final MyTripItem model = items.remove(position);
       notifyDataSetChanged();
        return items;
    }

    public void addItem(int position, MyTripItem model) {
        items.add(position, model);
        notifyDataSetChanged();
    }

    public void moveItem(int fromPosition, int toPosition) {
        final MyTripItem model = items.remove(fromPosition);
        items.add(toPosition, model);
        notifyDataSetChanged();
    }
}