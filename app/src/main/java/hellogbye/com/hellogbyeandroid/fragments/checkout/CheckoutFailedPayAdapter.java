package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/14/16.
 */

public class CheckoutFailedPayAdapter extends BaseAdapter {

    private  List<BookingPayVO> bookingPayVOs;

    public CheckoutFailedPayAdapter( List<BookingPayVO> bookingPayVOs) {
        this.bookingPayVOs = bookingPayVOs;
    }

    @Override
    public int getCount() {
        return bookingPayVOs.size();
    }

    @Override
    public Object getItem(int position) {
        return bookingPayVOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checkout_failed_pay_layout_item, null);
        }

        FontTextView check_out_failed_pay_title = (FontTextView)v.findViewById(R.id.check_out_failed_pay_title);
        FontTextView check_out_failed_pay_description  = (FontTextView)v.findViewById(R.id.check_out_failed_pay_description);

        FontTextView check_out_failed_pay_error_description = (FontTextView)v.findViewById(R.id.check_out_failed_pay_error_description);

        check_out_failed_pay_title.setText(bookingPayVOs.get(position).getItemTitle());
        check_out_failed_pay_description.setText(bookingPayVOs.get(position).getItemDescription());


        ArrayList<String> errorMessages = bookingPayVOs.get(position).getErrormessages();
        String error="";
        for(String errorMessage : errorMessages){
            error = error + errorMessage + "\n";
        }

        check_out_failed_pay_error_description.setText(error);
        return v;
    }

}
