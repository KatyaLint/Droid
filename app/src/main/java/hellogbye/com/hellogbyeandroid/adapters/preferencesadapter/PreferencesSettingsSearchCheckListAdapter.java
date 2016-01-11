package hellogbye.com.hellogbyeandroid.adapters.preferencesadapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.Collection;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/25/15.
 */
public class PreferencesSettingsSearchCheckListAdapter extends ArrayAdapter<SettingsValuesVO> implements Swappable{


    public PreferencesSettingsSearchCheckListAdapter(Activity activity, List<SettingsValuesVO> attributesVOs) {
        super(attributesVOs);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends SettingsValuesVO> collection) {
        // this.items = (List<SettingsValuesVO>) collection;
        return super.addAll(collection);
    }

    @Override
    public boolean add(@NonNull SettingsValuesVO object) {
        return super.add(object);
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
                    .inflate(R.layout.settings_item_serach_check_layout, null);
        }
        SettingsValuesVO attribute = this.getItem(position);//items.get(position);
        if(attribute != null){
            FontTextView settings_flight_title = (FontTextView) v.findViewById(R.id.settings_search_check_name);
            if(settings_flight_title != null){
                settings_flight_title.setText(attribute.getmDescription());
                settings_flight_title.setTag(attribute.getmID());
            }
            ImageView setting_check_image = (ImageView)v.findViewById(R.id.setting_search_check_image);
            if(attribute.getmRank() != null){
                setting_check_image.setBackgroundResource(R.drawable.check_on);
            }else{
                setting_check_image.setBackgroundResource(R.drawable.check_off);
            }

            FontTextView settings_search_place_number = (FontTextView) v.findViewById(R.id.settings_search_place_number);
            int correntPosition = position+1;
            settings_search_place_number.setText(""+correntPosition);
            this.getItem(position).setmRank(""+correntPosition);
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
