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
import android.widget.RadioButton;
import android.widget.RelativeLayout;

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

    private boolean isEditMode = false;
    private PreferenceSettingsFragment.ListLineClicked listLineClicked;
    private PreferenceSettingsFragment.ListRadioButtonClicked listRadioButtonClickedClicked;
    private int selectedPosition = -1;
    private String selectedPreferebcesID="";

    public PreferencesSettingsPreferencesCheckAdapter(Context context, List<AccountDefaultSettingsVO> accountAttributes) {
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
            RelativeLayout settings_item_check_ll = (RelativeLayout)v.findViewById(R.id.settings_item_check_rl);
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

            RadioButton radioButton = (RadioButton)v.findViewById(R.id.setting_radio_image);

            if(selectedPreferebcesID.equals(attribute.getmId())){
                selectedPosition = position;
                selectedPreferebcesID = "";

            }



            radioButton.setChecked(position == selectedPosition);
            radioButton.setTag(position);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedPosition = (Integer)view.getTag();
                    listRadioButtonClickedClicked.clickedItem(selectedPosition);
                    notifyDataSetChanged();

                }
            });



            if(!isEditMode){
                image.setVisibility(View.INVISIBLE);
                radioButton.setVisibility(View.VISIBLE);
            }else{
                image.setVisibility(View.VISIBLE);
                radioButton.setVisibility(View.INVISIBLE);
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
