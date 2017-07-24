package hellogbye.com.hellogbyeandroid.adapters.cncadapters;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/14/16.
 */

public class CNCMenuAdapter extends BaseAdapter {


    private List<String> data;
    private int checkedPosition = -1;
    public CNCMenuAdapter(List<String> data){
        this.data = data;

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
                    .inflate(R.layout.popup_cnc_menu_layout_item, null);
        }
        if(position == getCount() -1){
            v.findViewById(R.id.bottom_view_popup_cnc).setVisibility(View.VISIBLE);
        }else{
            v.findViewById(R.id.bottom_view_popup_cnc).setVisibility(View.GONE);
        }
        String sortType = data.get(position);
        CheckedTextView itemText = (CheckedTextView)v.findViewById(R.id.cnc_menu_item);

        itemText.setText(sortType);

        if(position == checkedPosition){
            itemText.setChecked(true);
        }else{
            itemText.setChecked(false);
        }


        return v;
    }


    public void setCheckedItemPosition(int position){
        checkedPosition = position;
    }
    public int getCheckedItemPosition(){
        return checkedPosition;
    }


}
