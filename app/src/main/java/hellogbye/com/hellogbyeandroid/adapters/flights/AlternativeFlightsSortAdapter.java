package hellogbye.com.hellogbyeandroid.adapters.flights;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/14/16.
 */

public class AlternativeFlightsSortAdapter extends BaseAdapter {

    private List<String> data;
    private int selectedID = 0;

    public void setSelectedID(int selected){
        this.selectedID = selected;
    }

    public AlternativeFlightsSortAdapter(List<String> data){
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
                    .inflate(R.layout.popup_alternative_layout_item, null);
        }

        String sortType = data.get(position);

        FontCheckedTextView itemText = (FontCheckedTextView)v.findViewById(R.id.alternative_sort_item);


        itemText.setText(sortType);
        if(position == selectedID){
            itemText.setChecked(true);
        }else{
            itemText.setChecked(false);
        }


        return v;
    }

}
