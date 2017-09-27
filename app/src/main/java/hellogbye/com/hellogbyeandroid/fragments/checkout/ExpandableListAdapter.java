package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.activities.RefreshComplete;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymentChild;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymnentGroup;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static hellogbye.com.hellogbyeandroid.utilities.HGBUtility.setCCIcon;

/**
 * Created by amirlubashevsky on 25/09/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final LayoutInflater inf;
    private final ArrayList<PaymnentGroup> groupsList;
    private List<ArrayList<PaymentChild>> childrenList = new ArrayList<>();
    private Context context;
    private SummaryPaymentExpendableFragment.IGroupClickListener igroupClickListener;

    public ExpandableListAdapter(ArrayList<PaymnentGroup> groups, List<ArrayList<PaymentChild>> children, Context context) {
        this.groupsList = groups;
        this.childrenList = children;
        this.inf = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childrenList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childrenList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ExpandableListAdapter.ChildViewHolder holder;
        if (convertView == null) {
            convertView = inf.inflate(R.layout.payment_child_item, parent, false);
            holder = new ExpandableListAdapter.ChildViewHolder();

            holder.childNametext = (FontTextView) convertView.findViewById(R.id.payment_child_name);
            holder.childPricetext = (FontTextView) convertView.findViewById(R.id.payment_child_price);
            holder.payment_info_text = (FontTextView) convertView.findViewById(R.id.payment_info_text);


            holder.childSelectCCText = (FontTextView) convertView.findViewById(R.id.passenger_select_cc);
            holder.childSelectCCImage = (ImageView) convertView.findViewById(R.id.passenger_select_cc_image);
            //  holder.childSelectCCDropDown = (ImageView) convertView.findViewById(R.id.passenger_select_cc_dropdown);
            holder.childSelectCCLinearLayout = (LinearLayout) convertView.findViewById(R.id.passenger_select_cc_ll);
            holder.childPlaneLinearLayout = (LinearLayout) convertView.findViewById(R.id.plane_ll);
            holder.childHotelCCLinearLayout = (LinearLayout) convertView.findViewById(R.id.hotel_ll);
            holder.childPath = (FontTextView) convertView.findViewById(R.id.payment_child_path);
            holder.childDurationPricenight = (FontTextView) convertView.findViewById(R.id.payment_child_duration_pricenight);
            holder.childHotelRoomType = (FontTextView) convertView.findViewById(R.id.hotel_room_type);
            holder.childHotelCheckIn = (FontTextView) convertView.findViewById(R.id.hotel_checkin);
            holder.childHotelDuration = (FontTextView) convertView.findViewById(R.id.hotel_duration);
            holder.childPlaneFlightNumber = (FontTextView) convertView.findViewById(R.id.plane_flight_number);
            holder.childPlaneFlightPath = (FontTextView) convertView.findViewById(R.id.plane_flight_path);
            holder.childPlaneFlightClass = (FontTextView) convertView.findViewById(R.id.plane_flight_class);
            holder.childPlaneFlightDeparture = (FontTextView) convertView.findViewById(R.id.plane_flight_departure);
            holder.childImage = (ImageView) convertView.findViewById(R.id.node_image);
            holder.childPlaneFlightArrival = (FontTextView) convertView.findViewById(R.id.plane_flight_arrival);
            holder.plane_flight_seat_location = (FontTextView)convertView.findViewById(R.id.plane_flight_seat_location);
            //   holder.plane_flight_seat_location_ll = (LinearLayout)convertView.findViewById(R.id.plane_flight_seat_location_ll);

            convertView.setTag(holder);
        } else {
            holder = (ExpandableListAdapter.ChildViewHolder) convertView.getTag();
        }
        final PaymentChild child = (PaymentChild) getChild(groupPosition, childPosition);

        //Kate add currency
        holder.childPricetext.setText( child.getTotalText() + " ");
        holder.childSelectCCText.setText(child.getCreditcard()
        );
        holder.childSelectCCLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    getFlowInterface().goToFragment(ToolBarNavEnum.CREDIT_CARDS_LIST.getNavNumber(), null);
//
//                    LinearLayout ll = (LinearLayout)v;
//                    mSelectedView = (FontTextView) ll.getChildAt(1);
//                    mSelectedView.setTag(child);

//Kate

//                if (selectCCDialog != null) {
//                    selectCCDialog.show();
//
//                    //    getFlowInterface().goToFragment(ToolBarNavEnum.CREDIT_CARDS_LIST.getNavNumber(), null);
//
//                    LinearLayout ll = (LinearLayout)v;
//                    mSelectedView = (FontTextView) ll.getChildAt(1);
//                    mSelectedView.setTag(child);
//                }


            }
        });

        if(child.getCreditcard().equals(context.getString(R.string.select_card))){
            // holder.childSelectCCText.setVisibility(View.VISIBLE);
            holder.childSelectCCImage.setVisibility(View.GONE);
        }else{
            //  holder.childSelectCCText.setVisibility(View.GONE);
            holder.childSelectCCImage.setVisibility(View.VISIBLE);
            setCCIcon(holder.childSelectCCImage,child.getCreditcardid());
        }
        if(child.getParentflight() == null){
            holder.childSelectCCLinearLayout.setVisibility(View.VISIBLE);

        }else{
            holder.childSelectCCLinearLayout.setVisibility(View.INVISIBLE);
        }

        if(child.getNameText().equalsIgnoreCase("Hotel")){
            holder.childPlaneLinearLayout.setVisibility(View.GONE);
            holder.childHotelCCLinearLayout.setVisibility(View.VISIBLE);
            holder.childPath.setText(child.getHotelName());
            //holder.childDurationPricenight.setText(child.getHotelPricePerNight());
            holder.childDurationPricenight.setVisibility(View.GONE);
            holder.childHotelRoomType.setText(child.getHotelRoomType());
            holder.childHotelCheckIn.setText(child.getHotelCheckIn());
            holder.childHotelDuration.setText(child.getHotelDuration());
            holder.childImage.setBackgroundResource(R.drawable.hotels);
            holder.payment_info_text.setText("Hotel Info");
            holder.childNametext.setText(child.getNameText());

        }else if(child.getNameText().equalsIgnoreCase("Flight")){

            holder.childNametext.setText(child.getOperatorName() +"\n" + child.getFlighNumber());

            holder.childPlaneLinearLayout.setVisibility(View.VISIBLE);
            holder.childHotelCCLinearLayout.setVisibility(View.GONE);
            holder.childPath.setText(child.getFlightPath());
            holder.childDurationPricenight.setText(child.getFlightDuraion());
            holder.childPlaneFlightNumber.setText(child.getFlighNumber());
            holder.childPlaneFlightPath.setText(child.getFlightPath());
            holder.childPlaneFlightClass.setText(child.getFlightClass());
            holder.childPlaneFlightDeparture.setText(child.getFlightDeparture());
            holder.childPlaneFlightArrival.setText(child.getFlightArrival());
            holder.childImage.setBackgroundResource(R.drawable.dlight_b_icon);
            holder.payment_info_text.setText(child.getOperatorName());


            holder.plane_flight_seat_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String selectedStr = holder.plane_flight_seat_location.getText().toString();

                    ((MainActivityBottomTabs)context).buildSeatTypeDialog(selectedStr,new RefreshComplete() {
                        @Override
                        public void onRefreshSuccess(Object data) {
                            String choosenText = (String)data;
                            holder.plane_flight_seat_location.setText(choosenText);
                        }

                        @Override
                        public void onRefreshError(Object data) {

                        }
                    });
                }
            });
        }else if(child.getNameText().equalsIgnoreCase("Traveller")){ //Traveller info

        }

        return convertView;
    }





    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ExpandableListAdapter.GroupViewHolder holder;

        if (convertView == null) {
            convertView = inf.inflate(R.layout.payment_group_item, parent, false);

            holder = new ExpandableListAdapter.GroupViewHolder();
            holder.groupNametext = (FontTextView) convertView.findViewById(R.id.payment_group_name);
            holder.groupPricetext = (FontTextView) convertView.findViewById(R.id.payment_group_price);
            holder.groupSelectCC = (FontTextView) convertView.findViewById(R.id.passenger_select_cc);
            holder.groupSelectCCImage = (ImageView) convertView.findViewById(R.id.passenger_select_cc_image);
            //       holder.groupSelectCCDropDown = (ImageView) convertView.findViewById(R.id.passenger_select_cc_dropdown);
            holder.groupSelectCCLinearLayout = (LinearLayout) convertView.findViewById(R.id.passenger_select_cc_ll);

            // holder.groupCheckBox = (CheckBox) convertView.findViewById(R.id.payment_group_checkbox);
            holder.groupImageView = (ImageView) convertView.findViewById(R.id.payment_group_image);

            convertView.setTag(holder);
        } else {
            holder = (ExpandableListAdapter.GroupViewHolder) convertView.getTag();

        }

        final PaymnentGroup group = (PaymnentGroup) getGroup(groupPosition);
        holder.groupNametext.setText(group.getNameText());
        holder.groupPricetext.setText("Total: $"+ HGBUtility.roundNumber(group.getTotalText())+" "+group.getCurrency());

        holder.groupSelectCCLinearLayout.setVisibility(View.GONE);

      //  holder.groupSelectCC.setText(group.getCreditcard());
        holder.groupSelectCCLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                    getFlowInterface().goToFragment(ToolBarNavEnum.CREDIT_CARDS_LIST.getNavNumber(), null);
//                    LinearLayout ll =(LinearLayout) v;
//                    mSelectedView = (FontTextView) ll.getChildAt(1);
//                    mSelectedView.setTag(group);


                //Kate
//                if (selectCCDialog != null) {
//                    selectCCDialog.show();
//                    LinearLayout ll =(LinearLayout) v;
//                    mSelectedView = (FontTextView) ll.getChildAt(1);
//                    mSelectedView.setTag(group);
//                }


            }
        });

        if (group.isSelected()) {
            holder.groupImageView.setBackgroundResource(R.drawable.open_icon);
        } else {
            holder.groupImageView.setBackgroundResource(R.drawable.close_icon);
        }
        holder.groupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(group.isSelected()){
                    group.setSelected(false);
                    v.setBackgroundResource(R.drawable.open_icon);
                }else{
                    group.setSelected(true);
                    v.setBackgroundResource(R.drawable.close_icon);
                }

                igroupClickListener.groupClicked(groupPosition);

//                if (lv.isGroupExpanded(groupPosition)) {
//                    lv.collapseGroup(groupPosition);
//                    v.setBackgroundResource(R.drawable.open_icon);
//                    group.setSelected(true);
//                } else {
//                    lv.expandGroup(groupPosition);
//                    group.setSelected(false);
//                    v.setBackgroundResource(R.drawable.close_icon);
//                }
            }
        });

//        if(group.getCreditcard().equals(context.getString(R.string.select_card))){
//            //   holder.groupSelectCCDropDown.setVisibility(View.VISIBLE);
//            holder.groupSelectCCImage.setVisibility(View.GONE);
//        }else{
//            //  holder.groupSelectCCDropDown.setVisibility(View.GONE);
//            holder.groupSelectCCImage.setVisibility(View.VISIBLE);
//            setCCIcon(holder.groupSelectCCImage,group.getCreditcardid());
//        }



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setGroupClickListenerPosition(SummaryPaymentExpendableFragment.IGroupClickListener iGroupClickListener) {
        this.igroupClickListener = iGroupClickListener;
    }

    private class GroupViewHolder {
        FontTextView groupNametext;
        FontTextView groupPricetext;
        //CheckBox groupCheckBox;
        ImageView groupImageView;
        FontTextView groupSelectCC;
        //    ImageView groupSelectCCDropDown;
        ImageView groupSelectCCImage;
        LinearLayout groupSelectCCLinearLayout;
    }

    private class ChildViewHolder {

        FontTextView childNametext;
        FontTextView childPricetext;
        FontTextView payment_info_text;

        FontTextView childSelectCCText;
        ImageView childSelectCCImage;
        //  ImageView childSelectCCDropDown;
        ImageView childImage;
        LinearLayout childSelectCCLinearLayout;
        LinearLayout childPlaneLinearLayout;
        LinearLayout childHotelCCLinearLayout;
        FontTextView childPath;
        FontTextView childDurationPricenight;
        FontTextView childHotelRoomType;
        FontTextView childHotelCheckIn;
        FontTextView childHotelDuration;
        FontTextView childPlaneFlightNumber;
        FontTextView childPlaneFlightPath;
        FontTextView childPlaneFlightClass;
        FontTextView childPlaneFlightDeparture;
        FontTextView childPlaneFlightArrival;
        FontTextView plane_flight_seat_location;

    }


    private void setGroupPrice(boolean add, int groupPosition, FontTextView childPricetext) {
        PaymnentGroup paymnentGroup = groupsList.get(groupPosition);
        String string = childPricetext.getText().toString().substring(1);

        if (add) {
            double d = Double.valueOf(paymnentGroup.getTotalText()) + Double.valueOf(string);
            groupsList.get(groupPosition).setTotalText(d);
        } else {

            double d = Double.valueOf(paymnentGroup.getTotalText()) - Double.valueOf(string);
            groupsList.get(groupPosition).setTotalText(d);
        }

    }

    private View getNameHeaderPriceView(CompoundButton buttonView) {
        ViewGroup viewParent = (ViewGroup) buttonView.getParent();
        ViewParent vrr = viewParent.getParent();
        ViewGroup vrr1 = (ViewGroup) vrr.getParent();
        View v4 = vrr1.getChildAt(0);
        return v4;
    }

    private void setHeaderPrice(boolean add, View nameHeaderPriceTextView, String value) {
        FontTextView priceFontText = (FontTextView) nameHeaderPriceTextView.findViewById(R.id.checkout_name_price);
        String string = priceFontText.getText().toString().substring(1);
        if (value.contains("$")) {
            value = value.substring(1);
        }
        if (add) {
            double dTotal = Double.valueOf(string) + Double.valueOf(value);
            priceFontText.setText("$" + String.format("%.2f", dTotal));
        } else {
            double dTotal = Double.valueOf(string) - Double.valueOf(value);
            priceFontText.setText("$" + String.format("%.2f", dTotal));
        }

    }
}
