package hellogbye.com.hellogbyeandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 11/3/15.
 */
public class PreferencesSettingsMainTabsAdapter extends RecyclerView.Adapter<PreferencesSettingsMainTabsAdapter.ViewHolder> {

    private OnItemClickListener mItemClickListener;
    private List<SettingsAttributesVO> itemsData;

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
                .inflate(R.layout.settings_hotel_tab_fragment, null);

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
        holder.setting_flight_text.setText(strAttributes);
        holder.setting_flight_text.setTag(position);
        holder.settings_flight_title.setText(item.getmName());
        holder.settings_flight_title.setTag(item.getmId());
    }


    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private FontTextView settings_flight_title;
        private FontTextView setting_flight_text;

        public ViewHolder(View itemView) {
            super(itemView);
            settings_flight_title = (FontTextView) itemView.findViewById(R.id.settings_flight_title);
            setting_flight_text = (FontTextView) itemView.findViewById(R.id.setting_flight_text);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            FontTextView textView = (FontTextView) view.findViewById(R.id.settings_flight_title);
            FontTextView textViewText = (FontTextView) view.findViewById(R.id.setting_flight_text);
            mItemClickListener.onItemClick(textView.getTag().toString(), textViewText.getTag().toString());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(String guid, String position);
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
