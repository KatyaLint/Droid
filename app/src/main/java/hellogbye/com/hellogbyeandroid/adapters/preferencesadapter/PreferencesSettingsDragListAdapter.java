package hellogbye.com.hellogbyeandroid.adapters.preferencesadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class PreferencesSettingsDragListAdapter extends ArrayAdapter<SettingsAttributesVO> implements Swappable {

    private boolean isArrowNextShow;
    public PreferencesSettingsDragListAdapter(Context context, List<SettingsAttributesVO> accountAttributes, boolean isArrowNextShow) {
        super(accountAttributes);
        this.isArrowNextShow = isArrowNextShow;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends SettingsAttributesVO> collection) {
        return super.addAll(collection);
    }

    @Override
    public boolean add(@NonNull SettingsAttributesVO object) {
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
                    .inflate(R.layout.settings_preferences_item_drag_layout, null);
        }
        SettingsAttributesVO attribute = this.getItem(position);//items.get(position);
        if(attribute != null){
            FontTextView settings_flight_title = (FontTextView) v.findViewById(R.id.setting_preferences_title_drag);
            if(settings_flight_title != null){
                settings_flight_title.setText(attribute.getmName());
                settings_flight_title.setTag(attribute.getmId());
            }
            FontTextView settings_text_drag = (FontTextView) v.findViewById(R.id.settings_preferences_place_number);
          //  settings_text_drag.setText();
            int correntPosition = position+1;
            settings_text_drag.setText(""+correntPosition);
            this.getItem(position).setmRank(""+correntPosition);
            View preferences_next_arrow = v.findViewById(R.id.preferences_next_arrow);;
            if(isArrowNextShow){
                preferences_next_arrow.setVisibility(View.VISIBLE);
            }else{
                preferences_next_arrow.setVisibility(View.INVISIBLE);
            }

//            List<SettingsValuesVO> attributes = attribute.getAttributesVOs();
//            String strAttributes = "";
//            for (int i = 0; i < attributes.size(); i++) {
//                SettingsValuesVO settingAttribute = attributes.get(i);
//                strAttributes = strAttributes + settingAttribute.getmDescription();
//                if (i < attributes.size() - 1) {
//                    strAttributes = strAttributes + ", ";
//                }
//            }
//            FontTextView settings_item_text_drag = (FontTextView) v.findViewById(R.id.setting_preferences_text_drag);
//            settings_item_text_drag.setVisibility(View.VISIBLE);
//            settings_item_text_drag.setText(strAttributes);
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
