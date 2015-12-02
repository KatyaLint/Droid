package hellogbye.com.hellogbyeandroid.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/22/15.
 */
public class PreferenceSettingsListAdapter extends ArrayAdapter<SettingsAttributesVO> implements Swappable{

    private Context context;
    private List<SettingsAttributesVO> items;

    public PreferenceSettingsListAdapter(Context context, List<SettingsAttributesVO> accountAttributes) {
     //  super(accountAttributes);
        this.context = context;
    }


    public void setItems( List<SettingsAttributesVO> items){
       this.items = items;
       this.notifyDataSetChanged();
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends SettingsAttributesVO> collection) {
        this.items = (List<SettingsAttributesVO>) collection;
        return super.addAll(collection);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
           v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_hotel_tab_fragment, null);

//            LayoutInflater vi = LayoutInflater.from(context);
//            v = vi.inflate(R.layout.settings_hotel_tab_fragment, null);
        }
        SettingsAttributesVO attributesVO = items.get(position);//getItem(position);
        if (attributesVO != null) {
            FontTextView settings_flight_title = (FontTextView) v.findViewById(R.id.settings_flight_title);
            FontTextView setting_flight_text = (FontTextView) v.findViewById(R.id.setting_flight_text);


            List<SettingsValuesVO> attributes = attributesVO.getAttributesVOs();

            String strAttributes = "";
            for (int i = 0; i < attributes.size(); i++) {
                SettingsValuesVO attribute = attributes.get(i);
                strAttributes = strAttributes + attribute.getmDescription();
                if (i < attributes.size() - 1) {
                    strAttributes = strAttributes + ", ";
                }
            }
            if(setting_flight_text != null) {
                setting_flight_text.setText(strAttributes);
            }
            if(settings_flight_title != null) {
                settings_flight_title.setText(attributesVO.getmName());
                settings_flight_title.setTag(attributesVO.getmId());
            }
        }
        return v;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void swapItems(int i, int i2) {
        super.swapItems(i,i2);
    }
}
