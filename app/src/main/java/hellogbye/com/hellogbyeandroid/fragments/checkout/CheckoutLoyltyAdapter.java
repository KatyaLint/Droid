package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 11/3/15.
 */
public class CheckoutLoyltyAdapter extends RecyclerView.Adapter<CheckoutLoyltyAdapter.ViewHolder> {


    private OnItemClickListener mItemClickListener;
    private List<AirlinePointsProgramVO> itemsData;
    private PreferenceSettingsFragment.ListRadioButtonClicked listRadioButtonClickedClicked;
    private int selectedPosition = -1;
    private String selectedPreferebcesID="";
    private OnItemClickListener onItemClickListener;


    public CheckoutLoyltyAdapter(List<AirlinePointsProgramVO> accountSettings) {
        itemsData = new ArrayList<>(accountSettings);
    }


    public void updateItems(List<AirlinePointsProgramVO> accountSettings){
        this.itemsData = new ArrayList<>(accountSettings);
        notifyDataSetChanged();
    }

    @Override
    public CheckoutLoyltyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkout_loyalty_program_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AirlinePointsProgramVO item = itemsData.get(position);

       String programnameName = item.getProgramname();

        holder.checkout_item_loyalty_program.setText(programnameName);

    }


    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FontTextView checkout_item_loyalty_program;

        public ViewHolder(View itemView) {
            super(itemView);


         //   settings_flight_title = (FontTextView) itemView.findViewById(R.id.settings_check_name);
            checkout_item_loyalty_program = (FontTextView) itemView.findViewById(R.id.checkout_item_loyalty_program);
          //  setting_check_image = (ImageView) itemView.findViewById(R.id.setting_check_image);


        //    radioButton = (RadioButton)itemView.findViewById(R.id.setting_radio_image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View guid, int position);
    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public List<AirlinePointsProgramVO> removeItem(int position) {
        final AirlinePointsProgramVO model = itemsData.remove(position);
        notifyItemRemoved(position);
        return itemsData;
    }

    public void addItem(int position, AirlinePointsProgramVO model) {
        itemsData.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final AirlinePointsProgramVO model = itemsData.remove(fromPosition);
        itemsData.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<AirlinePointsProgramVO> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<AirlinePointsProgramVO> newModels) {
        for (int i = itemsData.size() - 1; i >= 0; i--) {
            final AirlinePointsProgramVO model = itemsData.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<AirlinePointsProgramVO> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final AirlinePointsProgramVO model = newModels.get(i);
            if (!itemsData.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<AirlinePointsProgramVO> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final AirlinePointsProgramVO model = newModels.get(toPosition);
            final int fromPosition = itemsData.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }



}
