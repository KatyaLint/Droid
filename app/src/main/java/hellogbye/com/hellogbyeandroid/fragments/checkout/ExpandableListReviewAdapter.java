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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymentChild;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.TaxVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.UpdateAvailabilityItemVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.UpdateAvailabilityVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static hellogbye.com.hellogbyeandroid.utilities.HGBUtility.setCCIcon;




public class ExpandableListReviewAdapter extends BaseExpandableListAdapter {

    private final View promptsView;
    private final FontEditTextView companion_editTextDialog;
    private LayoutInflater inf;

    private List<PaymentChild> _listDataHeader; // header titles
    private HashMap<PaymentChild, List<PaymentChild>> _listDataChild;
    private Context context;
    private SummaryPaymentExpendableFragment.IGroupClickListener igroupClickListener;
    private UpdateAvailabilityVO updateAvailabilityVO;

    public ExpandableListReviewAdapter(List<PaymentChild> listDataHeader,
                                       HashMap<PaymentChild, List<PaymentChild>> listChildData, Context context, UpdateAvailabilityVO updateAvailabilityVO) {
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.updateAvailabilityVO = updateAvailabilityVO;
        this.inf = LayoutInflater.from(context);
        this.context = context;
        promptsView = inf.inflate(R.layout.popup_layout_with_edit_text_new, null);
        companion_editTextDialog = (FontEditTextView) promptsView
                .findViewById(R.id.companion_editTextDialog);

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

        final ExpandableListReviewAdapter.ChildViewHolder holder;
        if (convertView == null) {
            convertView = inf.inflate(R.layout.payment_child_review_item, parent, false);
            holder = new ExpandableListReviewAdapter.ChildViewHolder();

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


            holder.total_taxes = (FontTextView)convertView.findViewById(R.id.total_taxes) ;
            holder.review_details_price_flight_im = (ImageView)convertView.findViewById(R.id.review_details_price_flight_im) ;



            holder.total_taxes_hotel = (FontTextView)convertView.findViewById(R.id.total_taxes_hotel);
            holder.review_details_price_hotel_price_im = (ImageView)convertView.findViewById(R.id.review_details_price_hotel_price_im) ;



            holder.review_tax_details_traveler_ll = (LinearLayout)convertView.findViewById(R.id.review_tax_details_traveler_ll);
            holder.review_tax_details_flight_ll = (LinearLayout)convertView.findViewById(R.id.review_tax_details_flight_ll);
            holder.review_tax_details_hotel_ll = (LinearLayout)convertView.findViewById(R.id.review_tax_details_hotel_ll);
            holder.payment_child_review_price_ll = (LinearLayout)convertView.findViewById(R.id.payment_child_review_price_ll);
            holder.review_details_price_hotel_price_ll = (LinearLayout)convertView.findViewById(R.id.review_details_price_hotel_price_ll);



            holder.review_child_traveller_name = (FontTextView) convertView.findViewById(R.id.review_child_traveller_name);
            holder.review_child_traveller_email = (FontTextView) convertView.findViewById(R.id.review_child_traveller_email);
            holder.review_child_traveller_phone = (FontTextView) convertView.findViewById(R.id.review_child_traveller_phone);
            holder.review_child_traveller_dob = (FontTextView) convertView.findViewById(R.id.review_child_traveller_dob);
            holder.review_child_traveller_name_confirmation = (FontTextView) convertView.findViewById(R.id.review_child_traveller_name_confirmation);
            holder.review_child_traveller_email_confirmation = (FontTextView) convertView.findViewById(R.id.review_child_traveller_email_confirmation);
            holder.review_child_traveller_edit_email_confirmation = (FontTextView) convertView.findViewById(R.id.review_child_traveller_edit_email_confirmation);





            holder.flight_review_total_bf = (FontTextView) convertView.findViewById(R.id.flight_review_total_bf);
            holder.flight_review_total_gst = (FontTextView) convertView.findViewById(R.id.flight_review_total_gst);
            holder.flight_review_total_hst = (FontTextView) convertView.findViewById(R.id.flight_review_total_hst);


            holder.hotel_review_total_charges = (FontTextView) convertView.findViewById(R.id.hotel_review_total_charges);
            holder.hotel_review_total_night_cost = (FontTextView) convertView.findViewById(R.id.hotel_review_total_night_cost);
            holder.hotel_review_total_num_of_nights = (FontTextView) convertView.findViewById(R.id.hotel_review_total_num_of_nights);
            holder.hotel_review_nightly = (FontTextView) convertView.findViewById(R.id.hotel_review_nightly);

            //   holder.plane_flight_seat_location_ll = (LinearLayout)convertView.findViewById(R.id.plane_flight_seat_location_ll);

            convertView.setTag(holder);
        } else {
            holder = (ExpandableListReviewAdapter.ChildViewHolder) convertView.getTag();
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
       // holder.childSelectCCText.setVisibility(View.GONE);

        if(child.getCreditcard() != null && child.getCreditcard().equals(context.getString(R.string.select_card))){
             holder.childSelectCCText.setVisibility(View.GONE);
            holder.childSelectCCImage.setVisibility(View.GONE);

        }else{
            holder.childSelectCCText.setVisibility(View.VISIBLE);
            holder.childSelectCCImage.setVisibility(View.VISIBLE);
            holder.childSelectCCText.setText(child.getCreditcard());
            setCCIcon(holder.childSelectCCImage,child.getCreditcardid(), false);
        }
        if(child.getParentflight() == null){
            holder.childSelectCCLinearLayout.setVisibility(View.VISIBLE);

        }else{
            holder.childSelectCCLinearLayout.setVisibility(View.INVISIBLE);
        }

        if(child.getNameText().equalsIgnoreCase("Hotel information")){

            holder.review_tax_details_flight_ll.setVisibility(View.GONE);
            holder.review_details_price_hotel_price_ll.setVisibility(View.VISIBLE);
            holder.review_tax_details_hotel_ll.setVisibility(View.VISIBLE);
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


//            holder.hotel_review_total_charges.setText(updateAvailabilityVO.getHotels().get(0).get);
            ArrayList<UpdateAvailabilityItemVO> hotels = updateAvailabilityVO.getHotels();
            double nightlyRate = 0;
            for(Double d : hotels.get(0).getNightlyrateusd()){
                nightlyRate = nightlyRate+d;
            }

            holder.hotel_review_total_night_cost.setText(""+nightlyRate);
            holder.hotel_review_total_num_of_nights.setText(updateAvailabilityVO.getHotels().get(0).getNightlyrateusd().size() + " Nights:");
            holder.hotel_review_nightly.setText(String.format("%.2f", updateAvailabilityVO.getHotels().get(0).getNightlyrateusd().get(0)));


            double totalPriceTax = 0;
            if(updateAvailabilityVO.getHotels().get(0).getTaxbreakdown().getCity_tax() != null){

                totalPriceTax = totalPriceTax + Double.parseDouble(updateAvailabilityVO.getHotels().get(0).getTaxbreakdown().getCity_tax());

            }
            if(updateAvailabilityVO.getHotels().get(0).getTaxbreakdown().getTax() != null){
                totalPriceTax = totalPriceTax + Double.parseDouble(updateAvailabilityVO.getHotels().get(0).getTaxbreakdown().getTax());

            }

            holder.total_taxes_hotel.setText(String.format("%.2f",totalPriceTax));



            holder.review_details_price_hotel_price_im.setOnClickListener(showTaxesHotelDialogListener);

            holder.review_details_price_hotel_ll.setOnClickListener(showCurrentCurrencyHotelDialogListener);



        }else if(child.getNameText().equalsIgnoreCase("Flight information")){
            holder.review_details_price_hotel_price_ll.setVisibility(View.GONE);
            holder.review_tax_details_flight_ll.setVisibility(View.VISIBLE);
            holder.review_tax_details_hotel_ll.setVisibility(View.GONE);
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


            holder.flight_review_total_bf.setText(""+updateAvailabilityVO.getFlights().get(0).getBasefare());
            holder.flight_review_total_gst.setText(""+updateAvailabilityVO.getFlights().get(0).getGst());
            holder.flight_review_total_hst.setText(""+updateAvailabilityVO.getFlights().get(0).getHst());


            holder.total_taxes.setText(String.format("%.2f",updateAvailabilityVO.getFlights().get(0).getTotaltax()));

            holder.review_details_price_flight_im.setOnClickListener(showTaxesFlightDialogListener);


        }else if(child.getNameText().equalsIgnoreCase("Traveller information")){ //Traveller info
            holder.review_details_price_hotel_price_ll.setVisibility(View.GONE);
            holder.review_tax_details_flight_ll.setVisibility(View.GONE);
            holder.review_tax_details_hotel_ll.setVisibility(View.GONE);
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


            holder.review_child_traveller_name_confirmation.setText(child.getUserName());
            holder.review_child_traveller_email_confirmation.setText(child.getUserEmail());
            holder.review_child_traveller_edit_email_confirmation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    HGBUtility.showAlertPopUp(context, companion_editTextDialog, promptsView, null
                            , context.getResources().getString(R.string.save_button),
                            new PopUpAlertStringCB() {
                                @Override
                                public void itemSelected(String inputItem) {

                                    holder.review_child_traveller_email_confirmation.setText(inputItem);
                                }

                                @Override
                                public void itemCanceled() {

                                }
                            });

                }
            });




        }

        return convertView;
    }

    private void taxesDialog(Context context, Map<String, Object> taxVreakDown, boolean isFlight){


        if(taxVreakDown == null){
            return;
        }

        CheckoutTaxesPopupAdapter checkoutTaxesPopupAdapter = new CheckoutTaxesPopupAdapter(taxVreakDown);

        View promptsViewTeest = inf.inflate(R.layout.popup_alternative_layout_sort, null);

        FontTextView text_title = (FontTextView)promptsViewTeest.findViewById(R.id.text_title);
        text_title.setVisibility(View.VISIBLE);
        if(isFlight){
            text_title.setText("Tax Breakdown");
        }else{
            text_title.setText("Local Currency");
        }

        ListView user_profile_popup_list_view = (ListView) promptsViewTeest.findViewById(R.id.alternative_popup_sort);

        user_profile_popup_list_view.setAdapter(checkoutTaxesPopupAdapter);


        HGBUtility.showAlertPopUpOneButton(context,  null, promptsViewTeest,
                null, null, true);


    }






    View.OnClickListener showCurrentCurrencyHotelDialogListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {



//            View promptsViewTeest = inf.inflate(R.layout.payment_child_review_hotel_total_price, null);
//
//            FontTextView text_title = (FontTextView)promptsViewTeest.findViewById(R.id.hotel_review_currency_title);
//            text_title.setVisibility(View.VISIBLE);
//
//
//            FontTextView hotel_review_nightly = (FontTextView)promptsViewTeest.findViewById(R.id.hotel_review_nightly);
//            FontTextView hotel_review_total_night_cost = (FontTextView)promptsViewTeest.findViewById(R.id.hotel_review_total_night_cost);
//            FontTextView hotel_review_total_num_of_nights = (FontTextView)promptsViewTeest.findViewById(R.id.hotel_review_total_num_of_nights);
//            FontTextView hotel_review_total_charges = (FontTextView)promptsViewTeest.findViewById(R.id.hotel_review_total_charges);
//
//
            ArrayList<UpdateAvailabilityItemVO> hotels = updateAvailabilityVO.getHotels();
            double nightlyRate = 0;
            for(Double d : hotels.get(0).getNightlyrate()){
                nightlyRate = nightlyRate+d;
            }
//
//            hotel_review_total_night_cost.setText(String.format("%.2f",nightlyRate));
//            hotel_review_total_num_of_nights.setText(""+updateAvailabilityVO.getHotels().get(0).getNightlyrate().size() + " Nights:");
//            hotel_review_nightly.setText(String.format("%.2f",updateAvailabilityVO.getHotels().get(0).getNightlyrate().get(0)));
//
//
//            HGBUtility.showAlertPopUpOneButton(context,  null, promptsViewTeest,
//                    null, null);



            LinkedHashMap<String, Object> taxBreakDown = new LinkedHashMap<String, Object>();
            taxBreakDown.put("Nightly",String.format("%.2f",nightlyRate));
            taxBreakDown.put(updateAvailabilityVO.getHotels().get(0).getNightlyrate().size() + " Nights:",String.format("%.2f",updateAvailabilityVO.getHotels().get(0).getNightlyrate().get(0)));

            //TODO check taxes fees
            taxBreakDown.put("Taxes, fees"+"\n"+"and charges",0);

            taxesDialog(context, taxBreakDown, false);


        }
    };


    View.OnClickListener showTaxesHotelDialogListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            Map<String, Object> taxBreakDown = updateAvailabilityVO.getHotels().get(0).getTaxbreakdownhash();
            taxesDialog(context, taxBreakDown, false);
        }
    };

    View.OnClickListener showTaxesFlightDialogListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Map<String, Object> taxBreakDown = updateAvailabilityVO.getFlights().get(0).getTaxbreakdownhash();
            taxesDialog(context, taxBreakDown, true);
        }
    };

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ExpandableListReviewAdapter.GroupViewHolder holder;

        if (convertView == null) {
            convertView = inf.inflate(R.layout.payment_group_item, parent, false);

            holder = new ExpandableListReviewAdapter.GroupViewHolder();
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
            holder = (ExpandableListReviewAdapter.GroupViewHolder) convertView.getTag();

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
        LinearLayout review_tax_details_hotel_ll;
        LinearLayout payment_child_review_price_ll;
        LinearLayout review_details_price_hotel_price_ll;
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

        FontTextView hotel_review_total_charges;
        FontTextView hotel_review_total_night_cost;
        FontTextView hotel_review_total_num_of_nights;
        FontTextView hotel_review_nightly;


        FontTextView total_taxes;
        FontTextView total_taxes_hotel;

        ImageView review_details_price_hotel_price_im;
        ImageView review_details_price_flight_im;
        FontTextView review_child_traveller_name_confirmation;
        FontTextView review_child_traveller_email_confirmation;
        FontTextView review_child_traveller_edit_email_confirmation;
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
