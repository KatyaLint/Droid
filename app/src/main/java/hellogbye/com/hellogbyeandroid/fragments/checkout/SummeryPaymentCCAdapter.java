package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.PaymentSummaryItem;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 4/3/16.
 */
public class SummeryPaymentCCAdapter extends ArrayAdapter<PaymentSummaryItem> {
    private ArrayList<PaymentSummaryItem> mArrayList;


    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;

    private Context mContext;

    public SummeryPaymentCCAdapter(ArrayList<PaymentSummaryItem> myDataset, int textViewResourceId, Context context) {
        super(context, textViewResourceId, myDataset);
        mArrayList = myDataset;
        mContext = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.payment_summary_item, null);
        }

        FontTextView mCardNumberText = (FontTextView) v.findViewById(R.id.summary_passenger_cc);
        FontTextView mNameText = (FontTextView) v.findViewById(R.id.summary_passenger_name);
        FontTextView mTotalText = (FontTextView) v.findViewById(R.id.summary_passenger_total);
        PaymentSummaryItem cc = mArrayList.get(position);
        mCardNumberText.setText(cc.getLast4());
        mNameText.setText(cc.getName());
        mTotalText.setText("$" + cc.getTotal());

        return v;

    }
}
