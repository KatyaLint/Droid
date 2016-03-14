package hellogbye.com.hellogbyeandroid.adapters.preferencesadapter;

/**
 * Created by nyawka on 12/7/15.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.Collection;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 11/25/15.
 */
public class PreferencesSettingsPreferencesCheckAdapter extends ArrayAdapter<AccountDefaultSettingsVO> implements Swappable, SettingsAdapter {

    private Context context;
    private List<AccountDefaultSettingsVO> items;
    private boolean isEditMode = false;
    private PreferenceSettingsFragment.ListLineClicked listLineClicked;
    public PreferencesSettingsPreferencesCheckAdapter(Context context, List<AccountDefaultSettingsVO> accountAttributes) {
        super(accountAttributes);
        //  items = accountAttributes;
        this.context = context;
    }


    public boolean getIsEditMode(){
        return this.isEditMode;
    }

    @Override
    public void setClickedLineCB(PreferenceSettingsFragment.ListLineClicked listLineClicked) {
        this.listLineClicked = listLineClicked;
    }

    @Override
    public void setSelectedRadioButtonListener(PreferenceSettingsFragment.ListRadioButtonClicked listRadioButtonClicked) {

    }

    @Override
    public void selectedItemID(String id) {

    }

    public void setEditMode(boolean isEditMode){
        this.isEditMode = isEditMode;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends AccountDefaultSettingsVO> collection) {
        // this.items = (List<SettingsValuesVO>) collection;
        return super.addAll(collection);
    }

    @Override
    public boolean add(@NonNull AccountDefaultSettingsVO object) {
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
        AccountDefaultSettingsVO attribute = this.getItem(position);//items.get(position);
        if(attribute != null){
            FontTextView settings_flight_title = (FontTextView) v.findViewById(R.id.settings_check_name);

                settings_flight_title.setText(attribute.getmProfileName());
                settings_flight_title.setTag(attribute.getmId());
            LinearLayout settings_item_check_ll = (LinearLayout)v.findViewById(R.id.settings_item_check_ll);
            settings_item_check_ll.setTag(attribute.getmId());
            settings_item_check_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listLineClicked.clickedItem(view.getTag().toString());
                }
            });
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
