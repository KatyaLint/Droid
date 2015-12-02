package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/25/15.
 */
public class PreferenceSettingsAttributeAdapter extends ArrayAdapter<SettingsValuesVO> implements Swappable {

    private Context context;
    private List<SettingsValuesVO> items;

    public PreferenceSettingsAttributeAdapter(Context context, List<SettingsValuesVO> accountAttributes) {
         super(accountAttributes);
        items = accountAttributes;
        this.context = context;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends SettingsValuesVO> collection) {
        this.items = (List<SettingsValuesVO>) collection;
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
        }
        SettingsValuesVO attribute = this.getItem(position);//items.get(position);
        if(attribute != null){
            FontTextView settings_flight_title = (FontTextView) v.findViewById(R.id.settings_flight_title);
            if(settings_flight_title != null){
                settings_flight_title.setText(attribute.getmDescription());
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
