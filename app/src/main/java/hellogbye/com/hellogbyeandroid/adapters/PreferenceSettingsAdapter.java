package hellogbye.com.hellogbyeandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.PrefrenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AcountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/3/15.
 */
public class PreferenceSettingsAdapter extends  RecyclerView.Adapter<PreferenceSettingsAdapter.ViewHolder> {

    private List<AcountDefaultSettingsVO> itemsData = new ArrayList<AcountDefaultSettingsVO>();
    private PrefrenceSettingsFragment.IViewCallBackClick prefernceViewClick;

    public void swapArray(List<AcountDefaultSettingsVO> acountDefaultSettings){
        if(acountDefaultSettings != null) {
            this.itemsData.clear();
            this.itemsData.addAll(acountDefaultSettings);

        }
        notifyDataSetChanged();
    }

    @Override
    public PreferenceSettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_item_layout, null);


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AcountDefaultSettingsVO setting = itemsData.get(position);

        holder.preferenses_name.setText(setting.getmProfileName());
        holder.settings_layout.setTag(setting.getmId());
        holder.settings_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefernceViewClick.viewCallBackClick(view.getTag().toString());
            }
        });
    }


    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }

    public void setViewClickListener(PrefrenceSettingsFragment.IViewCallBackClick iViewCallBackClick) {
        this.prefernceViewClick = iViewCallBackClick;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

       private FontTextView preferenses_name;
        private View more_view;
        private RelativeLayout settings_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            preferenses_name = (FontTextView)itemView.findViewById(R.id.preferenses_name);
            more_view = (View)itemView.findViewById(R.id.preferences_more);
            settings_layout=(RelativeLayout)itemView.findViewById(R.id.settings_ll);

        }
    }




    }
