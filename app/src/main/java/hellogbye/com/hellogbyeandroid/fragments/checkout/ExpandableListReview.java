package hellogbye.com.hellogbyeandroid.fragments.checkout;

/**
 * Created by amirlubashevsky on 02/10/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymentChild;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static hellogbye.com.hellogbyeandroid.utilities.HGBUtility.setCCIcon;




public class ExpandableListReview extends BaseExpandableListAdapter {

    private LayoutInflater inf;

    private List<PaymentChild> _listDataHeader; // header titles
    private HashMap<PaymentChild, List<PaymentChild>> _listDataChild;
    private Context context;
    private SummaryPaymentExpendableFragment.IGroupClickListener igroupClickListener;

    public ExpandableListReview(List<PaymentChild> listDataHeader,
                                 HashMap<PaymentChild, List<PaymentChild>> listChildData, Context context) {
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

        this.inf = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }



    @Override
    public Object getGroup(int groupPosition) {
        return _listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition);
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

        final ExpandableListReview.ChildViewHolder holder;
        if (convertView == null) {
            convertView = inf.inflate(R.layout.payment_child_review_item, parent, false);
            holder = new ExpandableListReview.ChildViewHolder();

            holder.childNametext = (FontTextView) convertView.findViewById(R.id.payment_child_name);
            holder.childPricetext = (FontTextView) convertView.findViewById(R.id.payment_child_price);



            holder.childSelectCCText = (FontTextView) convertView.findViewById(R.id.passenger_select_cc);
            holder.childSelectCCImage = (ImageView) convertView.findViewById(R.id.passenger_select_cc_image);
            //  holder.childSelectCCDropDown = (ImageView) convertView.findViewById(R.id.passenger_select_cc_dropdown);
            holder.childSelectCCLinearLayout = (View) convertView.findViewById(R.id.passenger_select_cc_ll_include);
            holder.childPlaneLinearLayout = (LinearLayout) convertView.findViewById(R.id.plane_ll);
            holder.childHotelCCLinearLayout = (LinearLayout) convertView.findViewById(R.id.hotel_ll);

         //   holder.childDurationPricenight = (FontTextView) convertView.findViewById(R.id.payment_child_duration_pricenight);
            holder.childHotelRoomType = (FontTextView) convertView.findViewById(R.id.hotel_room_type);
            holder.childHotelCheckIn = (FontTextView) convertView.findViewById(R.id.hotel_checkin);
            holder.childHotelDuration = (FontTextView) convertView.findViewById(R.id.hotel_duration);

            holder.childPlaneFlightPath = (FontTextView) convertView.findViewById(R.id.plane_flight_path);
            holder.childPlaneFlightClass = (FontTextView) convertView.findViewById(R.id.plane_flight_class);
            holder.childPlaneFlightDeparture = (FontTextView) convertView.findViewById(R.id.plane_flight_departure);

            holder.childPlaneFlightArrival = (FontTextView) convertView.findViewById(R.id.plane_flight_arrival);


            holder.review_details_price_flight_ll = (LinearLayout)convertView.findViewById(R.id.review_details_price_flight_ll) ;
            holder.review_details_price_hotel_ll = (LinearLayout)convertView.findViewById(R.id.review_details_price_hotel_ll) ;

            holder.review_tax_details_traveler_ll = (LinearLayout)convertView.findViewById(R.id.review_tax_details_traveler_ll);
            holder.review_tax_details_flight_ll = (LinearLayout)convertView.findViewById(R.id.review_tax_details_flight_ll);
            holder.payment_child_review_price_ll = (LinearLayout)convertView.findViewById(R.id.payment_child_review_price_ll);

            holder.review_child_traveller_name = (FontTextView) convertView.findViewById(R.id.review_child_traveller_name);
            holder.review_child_traveller_email = (FontTextView) convertView.findViewById(R.id.review_child_traveller_email);
            holder.review_child_traveller_phone = (FontTextView) convertView.findViewById(R.id.review_child_traveller_phone);
            holder.review_child_traveller_dob = (FontTextView) convertView.findViewById(R.id.review_child_traveller_dob);

            holder.flight_review_total_bf = (FontTextView) convertView.findViewById(R.id.flight_review_total_bf);
            holder.flight_review_total_gst = (FontTextView) convertView.findViewById(R.id.flight_review_total_gst);
            holder.flight_review_total_hst = (FontTextView) convertView.findViewById(R.id.flight_review_total_hst);



            //   holder.plane_flight_seat_location_ll = (LinearLayout)convertView.findViewById(R.id.plane_flight_seat_location_ll);

            convertView.setTag(holder);
        } else {
            holder = (ExpandableListReview.ChildViewHolder) convertView.getTag();
        }
        final PaymentChild child = (PaymentChild) getChild(groupPosition, childPosition);


        holder.childPricetext.setText( child.getTotalText() + " " + child.getCurrency());


//        holder.childSelectCCLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                    getFlowInterface().goToFragment(ToolBarNavEnum.CREDIT_CARDS_LIST.getNavNumber(), null);
////
////                    LinearLayout ll = (LinearLayout)v;
////                    mSelectedView = (FontTextView) ll.getChildAt(1);
////                    mSelectedView.setTag(child);
//
////Kate
//
////                if (selectCCDialog != null) {
////                    selectCCDialog.show();
////
////                    //    getFlowInterface().goToFragment(ToolBarNavEnum.CREDIT_CARDS_LIST.getNavNumber(), null);
////
////                    LinearLayout ll = (LinearLayout)v;
////                    mSelectedView = (FontTextView) ll.getChildAt(1);
////                    mSelectedView.setTag(child);
////                }
//
//
//            }
//        });
        holder.childSelectCCText.setVisibility(View.GONE);

        if(child.getCreditcard() != null && child.getCreditcard().equals(context.getString(R.string.select_card))){
            // holder.childSelectCCText.setVisibility(View.VISIBLE);
            holder.childSelectCCImage.setVisibility(View.GONE);

        }else{
            //  holder.childSelectCCText.setVisibility(View.GONE);
            holder.childSelectCCImage.setVisibility(View.VISIBLE);

            setCCIcon(holder.childSelectCCImage,child.getCreditcardid(), false);
        }
        if(child.getParentflight() == null){
            holder.childSelectCCLinearLayout.setVisibility(View.VISIBLE);

        }else{
            holder.childSelectCCLinearLayout.setVisibility(View.INVISIBLE);
        }

        if(child.getNameText().equalsIgnoreCase("Hotel information")){

            holder.review_tax_details_flight_ll.setVisibility(View.VISIBLE);
            holder.review_details_price_flight_ll.setVisibility(View.GONE);
            holder.review_details_price_hotel_ll.setVisibility(View.VISIBLE);

            //PassengersVO currentPassenger = getCurrentPassengerByName(getActivityInterface().getCurrentUser().getFirstname());
            holder.childSelectCCLinearLayout.setVisibility(View.VISIBLE);
            holder.childPlaneLinearLayout.setVisibility(View.GONE);

            holder.childHotelCCLinearLayout.setVisibility(View.VISIBLE);
            holder.payment_child_review_price_ll.setVisibility(View.VISIBLE);
            holder.childNametext.setVisibility(View.VISIBLE);

            holder.review_tax_details_traveler_ll.setVisibility(View.GONE);

            //holder.childDurationPricenight.setText(child.getHotelPricePerNight());
          //  holder.childDurationPricenight.setVisibility(View.GONE);
            holder.childHotelRoomType.setText(child.getHotelRoomType());
            holder.childHotelCheckIn.setText(child.getHotelCheckIn());
            holder.childHotelDuration.setText(child.getHotelDuration());


            holder.childNametext.setText(child.getHotelName());

        }else if(child.getNameText().equalsIgnoreCase("Flight information")){

            holder.review_tax_details_flight_ll.setVisibility(View.VISIBLE);
            holder.review_details_price_flight_ll.setVisibility(View.VISIBLE);
            holder.review_details_price_hotel_ll.setVisibility(View.GONE);
            holder.childSelectCCLinearLayout.setVisibility(View.VISIBLE);
            holder.childPlaneLinearLayout.setVisibility(View.VISIBLE);

            holder.childHotelCCLinearLayout.setVisibility(View.GONE);
            holder.payment_child_review_price_ll.setVisibility(View.VISIBLE);
            holder.childNametext.setVisibility(View.VISIBLE);

            holder.review_tax_details_traveler_ll.setVisibility(View.GONE);


            holder.childNametext.setText(child.getOperatorName() +"\n" + child.getFlighNumber());


          //  holder.childDurationPricenight.setText(child.getFlightDuraion());

            holder.childPlaneFlightPath.setText(child.getFlightPath());
            holder.childPlaneFlightClass.setText(child.getFlightClass());
            holder.childPlaneFlightDeparture.setText(child.getFlightDeparture());
            holder.childPlaneFlightArrival.setText(child.getFlightArrival());


//            holder.flight_review_total_bf.setText();
//            holder.flight_review_total_gst.setText();
//            holder.flight_review_total_hst.setText();




        }else if(child.getNameText().equalsIgnoreCase("Traveller information")){ //Traveller info

            holder.review_tax_details_flight_ll.setVisibility(View.GONE);
            holder.review_details_price_flight_ll.setVisibility(View.GONE);
            holder.review_details_price_hotel_ll.setVisibility(View.GONE);
            holder.childSelectCCLinearLayout.setVisibility(View.GONE);
            holder.childPlaneLinearLayout.setVisibility(View.GONE);

            holder.childHotelCCLinearLayout.setVisibility(View.GONE);
            holder.payment_child_review_price_ll.setVisibility(View.GONE);
            holder.childNametext.setVisibility(View.GONE);


            holder.review_tax_details_traveler_ll.setVisibility(View.VISIBLE);

            holder.review_child_traveller_name.setText(child.getUserName());
            holder.review_child_traveller_email.setText(child.getUserEmail());
            holder.review_child_traveller_phone.setText(child.getUserTel());
            holder.review_child_traveller_dob.setText(child.getUserDOB());

        }

        return convertView;
    }





    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ExpandableListReview.GroupViewHolder holder;

        if (convertView == null) {
            convertView = inf.inflate(R.layout.payment_group_item, parent, false);

            holder = new ExpandableListReview.GroupViewHolder();
            holder.groupNametext = (FontTextView) convertView.findViewById(R.id.payment_group_name);
            holder.groupPricetext = (FontTextView) convertView.findViewById(R.id.payment_group_price);
            holder.groupSelectCC = (FontTextView) convertView.findViewById(R.id.passenger_select_cc);
        //    holder.groupSelectCCImage = (ImageView) convertView.findViewById(R.id.passenger_select_cc_image);
            //       holder.groupSelectCCDropDown = (ImageView) convertView.findViewById(R.id.passenger_select_cc_dropdown);
            holder.groupSelectCCLinearLayout = (LinearLayout) convertView.findViewById(R.id.passenger_select_cc_ll);

            // holder.groupCheckBox = (CheckBox) convertView.findViewById(R.id.payment_group_checkbox);
            holder.groupImageView = (ImageView) convertView.findViewById(R.id.payment_group_image);

            convertView.setTag(holder);
        } else {
            holder = (ExpandableListReview.GroupViewHolder) convertView.getTag();

        }

        //    final PaymnentGroup group = (PaymnentGroup) getGroup(groupPosition);

        final PaymentChild group = (PaymentChild) getGroup(groupPosition);

        holder.groupNametext.setText(group.getNameText());
        if(group.getNameText().equalsIgnoreCase("Traveller information")){
            holder.groupPricetext.setVisibility(View.GONE);
        }else{
            holder.groupPricetext.setVisibility(View.VISIBLE);
        }
        holder.groupPricetext.setText("Total: $"+ group.getTotalText() + " " + group.getCurrency());

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
      //  ImageView groupSelectCCImage;
        LinearLayout groupSelectCCLinearLayout;
    }

    private class ChildViewHolder {

        FontTextView childNametext;
        FontTextView childPricetext;


        FontTextView childSelectCCText;
        ImageView childSelectCCImage;
        //  ImageView childSelectCCDropDown;

        View childSelectCCLinearLayout;
        LinearLayout childPlaneLinearLayout;
        LinearLayout childHotelCCLinearLayout;
        LinearLayout review_details_price_hotel_ll;
        LinearLayout review_details_price_flight_ll;
        LinearLayout review_tax_details_traveler_ll;
        LinearLayout review_tax_details_flight_ll;
        LinearLayout payment_child_review_price_ll;
//        FontTextView childDurationPricenight;
        FontTextView childHotelRoomType;
        FontTextView childHotelCheckIn;
        FontTextView childHotelDuration;

        FontTextView childPlaneFlightPath;
        FontTextView childPlaneFlightClass;
        FontTextView childPlaneFlightDeparture;
        FontTextView childPlaneFlightArrival;


        FontTextView review_child_traveller_name;
        FontTextView review_child_traveller_email;
        FontTextView review_child_traveller_phone;
        FontTextView review_child_traveller_dob;


        FontTextView flight_review_total_bf;
        FontTextView flight_review_total_gst;
        FontTextView flight_review_total_hst;


    }


//    private void setGroupPrice(boolean add, int groupPosition, FontTextView childPricetext) {
//        PaymnentGroup paymnentGroup = groupsList.get(groupPosition);
//        String string = childPricetext.getText().toString().substring(1);
//
//        if (add) {
//            double d = Double.valueOf(paymnentGroup.getTotalText()) + Double.valueOf(string);
//            groupsList.get(groupPosition).setTotalText(d);
//        } else {
//
//            double d = Double.valueOf(paymnentGroup.getTotalText()) - Double.valueOf(string);
//            groupsList.get(groupPosition).setTotalText(d);
//        }
//
//    }

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
