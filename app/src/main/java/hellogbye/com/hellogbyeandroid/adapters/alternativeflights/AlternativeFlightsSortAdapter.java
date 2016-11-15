package hellogbye.com.hellogbyeandroid.adapters.alternativeflights;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;


import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/14/16.
 */

public class AlternativeFlightsSortAdapter extends BaseAdapter {

    private ArrayList<String> data;
    public AlternativeFlightsSortAdapter(ArrayList<String> data){
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

        FontTextView itemText = (FontTextView)v.findViewById(R.id.alternative_sort_item);
        itemText.setText(sortType);


        return v;
    }

}
