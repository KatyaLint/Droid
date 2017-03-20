package hellogbye.com.hellogbyeandroid.adapters.preferencesadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 11/3/15.
 */
public class PreferencesSettingsMainTabsAdapter extends RecyclerView.Adapter<PreferencesSettingsMainTabsAdapter.ViewHolder> {

    private OnItemClickListener mItemClickListener;
    private List<SettingsAttributesVO> itemsData;
    private PreferenceSettingsFragment.ListRadioButtonClicked listRadioButtonClickedClicked;
    private int selectedPosition = -1;
    private String selectedPreferebcesID="";


    public PreferencesSettingsMainTabsAdapter(List<SettingsAttributesVO> accountSettings) {
        itemsData = new ArrayList<>(accountSettings);
    }


    public void updateItems(List<SettingsAttributesVO> accountSettings){
        this.itemsData = new ArrayList<>(accountSettings);
        notifyDataSetChanged();
    }

    @Override
    public PreferencesSettingsMainTabsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_item_check_layout, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SettingsAttributesVO item = itemsData.get(position);

        List<SettingsValuesVO> attributes = item.getAttributesVOs();
        String strAttributes = "";
        for (int i = 0; i < attributes.size(); i++) {
            SettingsValuesVO attribute = attributes.get(i);
            strAttributes = strAttributes + attribute.getmDescription();
            if (i < attributes.size() - 1) {
                strAttributes = strAttributes + ", ";
            }
        }
      //  holder.setting_flight_text.setText(strAttributes);
        holder.settings_item_check_rl.setTag(position);
        holder.settings_flight_title_check.setText(item.getmName());
        holder.settings_flight_title_check.setTag(item.getmId());
        if(item.isChecked()){
            holder.settings_flight_title_check.setChecked(true);
        }else {
            //holder.setting_check_image.setBackgroundResource(R.drawable.check_off);

            holder.settings_flight_title_check.setChecked(false);
        }


/*        int currentPosition = position+1;
//            settings_text_drag.setText(""+correntPosition);
        attributes.get(position).setmRank("" + currentPosition);



        if(selectedPreferebcesID.equals(attributes.get(position).getmID())){
            selectedPosition = position;
            selectedPreferebcesID = "";

        }



        holder.radioButton.setChecked(position == selectedPosition);
        holder.radioButton.setTag(position);

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedPosition = (Integer)view.getTag();
                listRadioButtonClickedClicked.clickedItem(selectedPosition);
                notifyDataSetChanged();

            }
        });*/


    }


    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private FontCheckedTextView settings_flight_title_check ;
        //private FontTextView settings_flight_title;
        private RelativeLayout settings_item_check_rl;
        //private ImageView setting_check_image;
        private  RadioButton radioButton;
        public ViewHolder(View itemView) {
            super(itemView);
            settings_flight_title_check = (FontCheckedTextView) itemView.findViewById(R.id.setting_check_image);

         //   settings_flight_title = (FontTextView) itemView.findViewById(R.id.settings_check_name);
            settings_item_check_rl = (RelativeLayout) itemView.findViewById(R.id.settings_item_check_rl);
          //  setting_check_image = (ImageView) itemView.findViewById(R.id.setting_check_image);


            radioButton = (RadioButton)itemView.findViewById(R.id.setting_radio_image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            FontCheckedTextView settings_flight_title_check = (FontCheckedTextView) view.findViewById(R.id.setting_check_image);

        //   FontTextView textView = (FontTextView) view.findViewById(R.id.settings_check_name);
            RelativeLayout settings_item_check_rl = (RelativeLayout) view.findViewById(R.id.settings_item_check_rl);
            mItemClickListener.onItemClick(settings_flight_title_check.getTag().toString(), settings_item_check_rl.getTag().toString());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String guid, String position);
    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public List<SettingsAttributesVO> removeItem(int position) {
        final SettingsAttributesVO model = itemsData.remove(position);
        notifyItemRemoved(position);
        return itemsData;
    }

    public void addItem(int position, SettingsAttributesVO model) {
        itemsData.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final SettingsAttributesVO model = itemsData.remove(fromPosition);
        itemsData.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<SettingsAttributesVO> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<SettingsAttributesVO> newModels) {
        for (int i = itemsData.size() - 1; i >= 0; i--) {
            final SettingsAttributesVO model = itemsData.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<SettingsAttributesVO> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final SettingsAttributesVO model = newModels.get(i);
            if (!itemsData.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<SettingsAttributesVO> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final SettingsAttributesVO model = newModels.get(toPosition);
            final int fromPosition = itemsData.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


}
