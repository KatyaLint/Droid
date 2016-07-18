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
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;

/**
 * Created by arisprung on 12/15/15.
 */
public class MyTripPinnedAdapter extends SectionedBaseAdapter {


    private ArrayList<MyTripItem> items;
  //  private boolean isEditMode = false;
    private int maxCurrentInitialization = 0;

    public MyTripPinnedAdapter (ArrayList<MyTripItem> items, Activity acitivity){
        this.items = items;
    }

    public void addItems(ArrayList<MyTripItem> items ){
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

//    public void isEditMode(boolean b) {
//        this.isEditMode = b;
//    }
//
//    public boolean isEditMode(){
//        return this.isEditMode;
//    }

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

//        private FontTextView my_trip_delete_forever;
   //     private Button my_trip_delete_forever;
        private RoundedImageView my_trip_user_image;
        private boolean isDeleteItemClicked = false;

        public ViewHolderItem(View view) {

   //         my_trip_delete_forever = (Button)view.findViewById(R.id.my_trip_delete_forever);
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


        if(section == 0 ){
            HGBUtility.loadRoundedImage( item.getUrlToCityView(),  holder.my_trip_user_image, R.drawable.city_avatar_a_2);
            holder.my_trip_name.setText(item.getName());
            holder.my_trip_dates.setText(HGBUtilityDate.parseDateToddMMyyyyMyTrip(item.getStartdate())+" - "+HGBUtilityDate.parseDateToddMMyyyyMyTrip(item.getEnddate()));
            if(item.getPaymentstatus().equals("UPD")){
                holder.my_trip_paid.setText("UNPAID");
            }else{
                holder.my_trip_paid.setText("PAID");
            }

        }else {

            HGBUtility.loadRoundedImage( item.getUrlToCityView(),  holder.my_trip_user_image, R.drawable.city_avatar_a_2);
            holder.my_trip_name.setText(item.getName());
            holder.my_trip_dates.setText(HGBUtilityDate.parseDateToddMMyyyyMyTrip(item.getStartdate())+" - "+HGBUtilityDate.parseDateToddMMyyyyMyTrip(item.getEnddate()));
            if(item.getPaymentstatus().equals("UPD")){
                holder.my_trip_paid.setText("UNPAID");
            }else{
                holder.my_trip_paid.setText("PAID");
            }
        }


        return itemView;
    }


    public static class ViewHolderMainSectionItem {


        private final FontTextView header_text;

        public ViewHolderMainSectionItem(View view) {

            header_text = (FontTextView) view.findViewById(R.id.header_text);
        }


    }


    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {

        ViewHolderMainSectionItem holder;
        View itemView = convertView;

        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView =  inflator.inflate(R.layout.mytrip_header, null);
            holder = new ViewHolderMainSectionItem(itemView);
            itemView.setTag(holder);
        } else {
            holder = (ViewHolderMainSectionItem)itemView.getTag();
        }



            if(section == 0){
                if(getMaxCurrentInitialization() == 0){
                    holder.header_text.setVisibility(View.GONE);
                }else {
                    holder.header_text.setText("Current Itinerary");
                }
            }else if (section == 1){
                holder.header_text.setText("My Trips");
            }




        return itemView;
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