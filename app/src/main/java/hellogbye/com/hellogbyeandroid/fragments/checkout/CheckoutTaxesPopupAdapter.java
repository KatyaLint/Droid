package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/14/16.
 */

public class CheckoutTaxesPopupAdapter extends BaseAdapter {


    private Map<String, Object> taxBreakDown;
    private String[] mKeys;

    public CheckoutTaxesPopupAdapter(Map<String, Object> taxBreakDown) {
        this.taxBreakDown = taxBreakDown;
        mKeys = taxBreakDown.keySet().toArray(new String[taxBreakDown.size()]);
    }

    @Override
    public int getCount() {
        return taxBreakDown.size();
    }

    @Override
    public Object getItem(int position) {
        return taxBreakDown.get(mKeys[position]);

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
                    .inflate(R.layout.popup_checkout_taxes_layout_item, null);
        }
        String key = mKeys[position];
        String Value = getItem(position).toString();

        FontTextView check_out_taxes_name = (FontTextView)v.findViewById(R.id.check_out_taxes_name);
        FontTextView check_out_taxes_price = (FontTextView)v.findViewById(R.id.check_out_taxes_price);
        check_out_taxes_name.setText(key);
        check_out_taxes_price.setText("$" + HGBUtility.roundNumber(Double.parseDouble(Value)));


        return v;
    }

}
