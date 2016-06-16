package hellogbye.com.hellogbyeandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 6/16/16.
 */
public class AlertCheckoutAdapter extends BaseAdapter {

    ArrayList<String> mData;
    Context mContext;
    LayoutInflater inflater;

    public AlertCheckoutAdapter(ArrayList<String> data, Context context) {
        mData = data;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.alert_checkout_item_layout, null);
        }
        FontTextView text = (FontTextView) convertView.findViewById(R.id.alert_checkout_text);
        ImageView image = (ImageView) convertView.findViewById(R.id.alert_checkout_image);
        Typeface textFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "dinnextltpro_medium.otf");
        Typeface textFontReg = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "dinnextltpro_regular.otf");
        text.setText(mData.get(position));

        if(mContext.getResources().getString(R.string.add_card).equals(mData.get(position))){
            image.setBackgroundResource(R.drawable.add_icon);
            text.setTypeface(textFont);
        }else if(mContext.getResources().getString(R.string.remove_card).equals(mData.get(position))){
            image.setBackgroundResource(R.drawable.remove_icon);
            text.setTypeface(textFont);
        }else{
            image.setBackgroundResource(R.drawable.all_card_icon);
            text.setTypeface(textFontReg);
        }


        return convertView;
    }
}