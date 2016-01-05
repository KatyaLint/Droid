package hellogbye.com.hellogbyeandroid.adapters;

/**
 * Created by nyawka on 12/7/15.
 */

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
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AcountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 11/25/15.
 */
public class PreferencesSettingsPreferencesCheckAdapter extends ArrayAdapter<AcountDefaultSettingsVO> implements Swappable {

    private Context context;
    private List<AcountDefaultSettingsVO> items;
    private boolean isEditMode = false;

    public PreferencesSettingsPreferencesCheckAdapter(Context context, List<AcountDefaultSettingsVO> accountAttributes) {
        super(accountAttributes);
        //  items = accountAttributes;
        this.context = context;
    }


    public boolean getIsEditMode(){
        return this.isEditMode;
    }
    public void setEditMode(boolean isEditMode){
        this.isEditMode = isEditMode;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends AcountDefaultSettingsVO> collection) {
        // this.items = (List<SettingsValuesVO>) collection;
        return super.addAll(collection);
    }

    @Override
    public boolean add(@NonNull AcountDefaultSettingsVO object) {
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
        AcountDefaultSettingsVO attribute = this.getItem(position);//items.get(position);
        if(attribute != null){
            FontTextView settings_flight_title = (FontTextView) v.findViewById(R.id.settings_check_name);
            if(settings_flight_title != null){
                settings_flight_title.setText(attribute.getmProfileName());
                settings_flight_title.setTag(attribute.getmId());
            }
//            FontTextView settings_text_drag = (FontTextView) v.findViewById(R.id.settings_place_number);
            //  settings_text_drag.setText();
            int correntPosition = position+1;
//            settings_text_drag.setText(""+correntPosition);
            this.getItem(position).setRank("" + correntPosition);
            ImageView image = (ImageView)v.findViewById(R.id.setting_check_image);
            if(!isEditMode){
                image.setVisibility(View.GONE);
            }else{
                image.setVisibility(View.VISIBLE);
               // image.setBackgroundResource(R.drawable.check_off);
                if(attribute.isChecked()){
                    image.setBackgroundResource(R.drawable.check_on);
                }else{
                    image.setBackgroundResource(R.drawable.check_off);
                }
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
