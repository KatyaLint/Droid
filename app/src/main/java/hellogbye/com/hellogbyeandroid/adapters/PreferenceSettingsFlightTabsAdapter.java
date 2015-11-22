package hellogbye.com.hellogbyeandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AcountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
* Created by nyawka on 11/3/15.
*/
public class PreferenceSettingsFlightTabsAdapter extends  RecyclerView.Adapter<PreferenceSettingsFlightTabsAdapter.ViewHolder> {

   // private List<AcountDefaultSettingsVO> itemsData = new ArrayList<AcountDefaultSettingsVO>();

    private OnItemClickListener mItemClickListener;
    List<SettingsAttributesVO> itemsData;
    public PreferenceSettingsFlightTabsAdapter(List<SettingsAttributesVO> accountSettings) {
        this.itemsData = accountSettings;
    }

//    public void swapArray(List<AcountDefaultSettingsVO> acountDefaultSettings){
//        if(acountDefaultSettings != null) {
//            this.itemsData.clear();
//            this.itemsData.addAll(acountDefaultSettings);
//
//        }
//        notifyDataSetChanged();
//    }

    @Override
    public PreferenceSettingsFlightTabsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_hotel_tab_fragment, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SettingsAttributesVO item = itemsData.get(position);

        ArrayList<SettingsValuesVO> attributes = item.getAttributesVOs();
        String strAttributes = "";
        for (int i = 0; i < attributes.size(); i++) {
            SettingsValuesVO attribute = attributes.get(i);
            strAttributes =  strAttributes + attribute.getmDescription();
            if(i < attributes.size() - 1){
                strAttributes = strAttributes+ ", ";
            }
        }
        holder.setting_flight_text.setText(strAttributes);

        holder.settings_flight_title.setText(item.getmName());
        holder.settings_flight_title.setTag(item.getmId());
    }


    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }



    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       private FontTextView settings_flight_title;
        private FontTextView setting_flight_text;

        public ViewHolder(View itemView) {
            super(itemView);
            settings_flight_title = (FontTextView)itemView.findViewById(R.id.settings_flight_title);
            setting_flight_text = (FontTextView)itemView.findViewById(R.id.setting_flight_text);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            FontTextView textView = (FontTextView)view.findViewById(R.id.settings_flight_title);
            mItemClickListener.onItemClick(textView.getTag().toString());
        }
    }


    public interface OnItemClickListener {
        public void onItemClick(String guid);
    }
//
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
