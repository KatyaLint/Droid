package hellogbye.com.hellogbyeandroid.fragments.checkout;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;


import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/23/15.
 */
public class CheckoutConfirmationFragment extends HGBAbstractFragment {


    private FontTextView mEmail;
    private FontButtonView confirm_done;


    public static Fragment newInstance(int position) {
        Fragment fragment = new CheckoutConfirmationFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.checkout_complete_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmail = (FontTextView) view.findViewById(R.id.confirm_email);

        String userEmail =  getActivityInterface().getCurrentUser().getEmailaddress();
    if(userEmail != null || !userEmail.isEmpty()) {
        SpannableString content = new SpannableString(userEmail);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        mEmail.setText(content);
    }

        confirm_done = (FontButtonView) view.findViewById(R.id.confirm_done);

        confirm_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), null);
            }
        });


        UserTravelMainVO userTravelOrder = getActivityInterface().getBookedTravelOrder();
        Map<String, NodesVO> orderItems = userTravelOrder.getItems();


        List<BookingPayVO> bookingPayAnswear = getFlowInterface().getBookingPayAnswear();

        boolean isFailed = false;

        for(BookingPayVO bookingPayVO : bookingPayAnswear){
            NodesVO order = orderItems.get(bookingPayVO.getItemid());
            if(null != order && order.getmType().equals("flight") && !bookingPayVO.issuccessful()){
                bookingPayVO.setItemTitle("Flight");
                bookingPayVO.setItemDescription("Flight No. " + order.getmOperatorName() + " " + order.getmOperator());
                isFailed = true;
            }else if(null != order && order.getmType().equals("hotel") && !bookingPayVO.issuccessful()){
                bookingPayVO.setItemTitle("Hotel");
                bookingPayVO.setItemDescription("Hotel Name: " + order.getmHotelName()+"\n" + order.getmCheckIn());
                isFailed = true;
            }
        }


        LinearLayout checkout_succeded = (LinearLayout)view.findViewById(R.id.checkout_succeded);
        LinearLayout checkout_failed = (LinearLayout)view.findViewById(R.id.checkout_failed);

        FontButtonView confirm_failed_itinerary = (FontButtonView) view.findViewById(R.id.confirm_failed_itinerary);
        FontButtonView confirm_call_us = (FontButtonView) view.findViewById(R.id.confirm_call_us);



        if(isFailed) {

            ListView checkout_complete_failed_list = (ListView) view.findViewById(R.id.checkout_complete_failed_list);
            CheckoutFailedPayAdapter checkoutFailedPayAdapter = new CheckoutFailedPayAdapter(bookingPayAnswear);
            checkout_complete_failed_list.setAdapter(checkoutFailedPayAdapter);

            checkout_succeded.setVisibility(View.GONE);
            confirm_done.setVisibility(View.GONE);
            checkout_failed.setVisibility(View.VISIBLE);
            confirm_call_us.setVisibility(View.VISIBLE);
            confirm_failed_itinerary.setVisibility(View.VISIBLE);

        }else if(!isFailed) {

            checkout_succeded.setVisibility(View.VISIBLE);
            confirm_done.setVisibility(View.VISIBLE);
            checkout_failed.setVisibility(View.GONE);
            confirm_call_us.setVisibility(View.GONE);
            confirm_failed_itinerary.setVisibility(View.GONE);
        }


        confirm_failed_itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), null);
            }
        });

    }
}
