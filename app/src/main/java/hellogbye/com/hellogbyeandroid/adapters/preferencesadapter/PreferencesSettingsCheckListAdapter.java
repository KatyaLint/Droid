package hellogbye.com.hellogbyeandroid.adapters.preferencesadapter;

import android.content.Context;
import android.media.Image;
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
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/25/15.
 */
public class PreferencesSettingsCheckListAdapter extends ArrayAdapter<SettingsAttributesVO> implements Swappable{

    public PreferencesSettingsCheckListAdapter(Context context, List<SettingsAttributesVO> accountAttributes) {
        super(accountAttributes);
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
                    .inflate(R.layout.settings_item_check_layout, null);
        }
        SettingsAttributesVO attribute = this.getItem(position);//items.get(position);
        /*if(attribute != null){*/

        FontCheckedTextView setting_check_image = (FontCheckedTextView) v.findViewById(R.id.setting_check_image);


        setting_check_image.setText(attribute.getmDescription());
        setting_check_image.setTag(attribute.getmId());

          //  ImageView setting_check_image = (ImageView)v.findViewById(R.id.setting_check_image);

            if(attribute.isChecked()){
                setting_check_image.setChecked(true);

            }else{
                setting_check_image.setChecked(false);

            }




    /*        FontTextView settings_flight_title = (FontTextView) v.findViewById(R.id.settings_check_name);
            if(settings_flight_title != null){
                settings_flight_title.setText(attribute.getmDescription());
                settings_flight_title.setTag(attribute.getmId());
            }
            ImageView setting_check_image = (ImageView)v.findViewById(R.id.setting_check_image);

            if(attribute.isChecked()){
                setting_check_image.setBackgroundResource(R.drawable.check_on);
            }else{
                setting_check_image.setBackgroundResource(R.drawable.check_off);
            }*/

     /*   }*/

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
