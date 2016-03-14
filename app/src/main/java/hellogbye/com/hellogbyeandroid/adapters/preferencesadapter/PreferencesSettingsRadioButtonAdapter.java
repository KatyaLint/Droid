package hellogbye.com.hellogbyeandroid.adapters.preferencesadapter;

/**
 * Created by nyawka on 12/7/15.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

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
public class PreferencesSettingsRadioButtonAdapter extends ArrayAdapter<AccountDefaultSettingsVO> implements Swappable, SettingsAdapter {


    private boolean isEditMode = false;
    private PreferenceSettingsFragment.ListLineClicked listLineClicked;
    private PreferenceSettingsFragment.ListRadioButtonClicked listRadioButtonClickedClicked;
    public PreferencesSettingsRadioButtonAdapter(Context context, List<AccountDefaultSettingsVO> accountAttributes) {
        super(accountAttributes);

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
        this.listRadioButtonClickedClicked = listRadioButtonClicked;
    }

    @Override
    public void selectedItemID(String id) {
        this.selectedPreferebcesID = id;
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


    int selectedPosition = 0;

    public String selectedPreferebcesID="";




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_item_radio_button_layout, null);
        }
        AccountDefaultSettingsVO attribute = this.getItem(position);//items.get(position);
        if(attribute != null){
            FontTextView settings_flight_title = (FontTextView) v.findViewById(R.id.settings_radio_name);
            settings_flight_title.setText(attribute.getmProfileName());
          //  settings_flight_title.setTag(attribute.getmId());

            LinearLayout settings_radio_button_ll = (LinearLayout)v.findViewById(R.id.settings_radio_button_ll);
            settings_radio_button_ll.setTag(attribute.getmId());
            settings_radio_button_ll.setOnClickListener(new View.OnClickListener() {
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

            if(selectedPreferebcesID.equals(attribute.getmId())){
                selectedPosition = position;
                selectedPreferebcesID = "";
            }

            RadioButton r = (RadioButton)v.findViewById(R.id.setting_radio_image);
            r.setChecked(position == selectedPosition);
            r.setTag(position);

            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedPosition = (Integer)view.getTag();
                    listRadioButtonClickedClicked.clickedItem(selectedPosition);
                    notifyDataSetChanged();

                }
            });

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
