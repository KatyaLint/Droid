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
import android.widget.RelativeLayout;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.Collection;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 11/25/15.
 */
public class PreferencesSettingsRadioButtonAdapter extends ArrayAdapter<AccountDefaultSettingsVO> implements Swappable, SettingsAdapter {


    private boolean isEditMode = false;
    private PreferenceSettingsFragment.ListLineClicked listLineClicked;
    private PreferenceSettingsFragment.ListRadioButtonClicked listRadioButtonClickedClicked;
    private int selectedPosition = -1;
    private String selectedPreferebcesID="";


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

    @Override
    public void updateItems(List<AccountDefaultSettingsVO> accountAttributes) {

    }

    @Override
    public void updateItem(AccountDefaultSettingsVO accountAttribute) {

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_item_radio_button_layout, null);
        }
        AccountDefaultSettingsVO attribute = this.getItem(position);//items.get(position);
        if(attribute != null){
            FontCheckedTextView settings_flight_title = (FontCheckedTextView) v.findViewById(R.id.settings_radio_name);
            settings_flight_title.setText(attribute.getmProfileName());


          //  settings_flight_title.setTag(attribute.getmId());

            RelativeLayout settings_radio_button_ll = (RelativeLayout)v.findViewById(R.id.settings_radio_button_ll);
            settings_radio_button_ll.setTag(attribute.getmId());
            settings_radio_button_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listLineClicked != null) {
                        listLineClicked.clickedItem(view.getTag().toString());
                    }

                }
            });


//            FontTextView settings_text_drag = (FontTextView) v.findViewById(R.id.settings_place_number);
            //  settings_text_drag.setText();
            int currentPosition = position+1;
//            settings_text_drag.setText(""+correntPosition);
            this.getItem(position).setRank("" + currentPosition);


         //   RadioButton r = (RadioButton)v.findViewById(R.id.setting_radio_image);

            if(selectedPreferebcesID.equals(attribute.getmId())){
                selectedPosition = position;
                selectedPreferebcesID = "";

            }



            settings_flight_title.setChecked(position == selectedPosition);
            settings_flight_title.setTag(position);

            settings_flight_title.setOnClickListener(new View.OnClickListener() {
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
