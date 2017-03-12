package hellogbye.com.hellogbyeandroid.adapters.preferencesadapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.ISwipeAdapterExecution;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsSlideFragment;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.ICompanionsSearchCB;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;


/**
 * Created by nyawka on 3/23/16.
 */
public class PreferencesSwipeItemsAdapter extends RecyclerSwipeAdapter<PreferencesSwipeItemsAdapter.SimpleViewHolder> {


    private Context mContext;
    private ArrayList<AccountDefaultSettingsVO> mDataset;
    private ISwipeAdapterExecution swipeAdapterExecution;
    private ICompanionsSearchCB companionSearchCB;
    private PreferenceSettingsSlideFragment.ListLineClicked listLineClicked;


    public void addClickeListeners(ISwipeAdapterExecution swipeAdapterExecution) {
        this.swipeAdapterExecution = swipeAdapterExecution;

    }

    public void updateItems(ArrayList<AccountDefaultSettingsVO> accountSettings){
        this.mDataset = new ArrayList<>(accountSettings);
        notifyDataSetChanged();

    }

    public void setCompanionSearchCB(ICompanionsSearchCB companionSearchCB) {
        this.companionSearchCB = companionSearchCB;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout swipe_my_settings;
        private ImageButton settings_delete_item;
        private RelativeLayout settings_rl;
        private FontTextView settings_details_name_item;
        private RadioButton settings_radio_btn;

        private View companion_arrow;


        public SimpleViewHolder(View itemView) {
            super(itemView);

            swipe_my_settings = (SwipeLayout) itemView.findViewById(R.id.swipe_my_settings);
            swipe_my_settings.setDragEdge(SwipeLayout.DragEdge.Left);
            swipe_my_settings.setShowMode(SwipeLayout.ShowMode.PullOut);

            settings_delete_item = (ImageButton) itemView.findViewById(R.id.settings_delete_item);
            settings_rl = (RelativeLayout)itemView.findViewById(R.id.settings_rl);

            settings_details_name_item = (FontTextView) itemView.findViewById(R.id.settings_details_name_item);
            settings_radio_btn = (RadioButton)itemView.findViewById(R.id.settings_radio_btn);

            companion_arrow = (View)itemView.findViewById(R.id.companion_arrow);

//            my_trip_paid = (FontTextView) itemView.findViewById(R.id.my_trip_paid);


        }
    }

    public PreferencesSwipeItemsAdapter( ArrayList objects) {
        this.mDataset = objects;

    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_swipe_item, parent, false);
        return new SimpleViewHolder(view);
    }

    public void setClickedLineCB(PreferenceSettingsSlideFragment.ListLineClicked listLineClicked) {
        this.listLineClicked = listLineClicked;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final AccountDefaultSettingsVO item = mDataset.get(position);

        if(item.isActiveProfile()){
            viewHolder.settings_radio_btn.setChecked(true);
        }else{
            viewHolder.settings_radio_btn.setChecked(false);
        }



        viewHolder.settings_rl.setTag(item.getmId());
        viewHolder.settings_radio_btn.setTag(item.getmId());
        viewHolder.settings_delete_item.setTag(item.getmId());

        viewHolder.settings_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listLineClicked.longClickedItem(view.getTag().toString());

            }
        });
        viewHolder.settings_radio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listLineClicked.clickedItem(view.getTag().toString());

            }
        });

        viewHolder.settings_details_name_item.setText(item.getmProfileName());


        if(!item.isActiveProfile()) {
            viewHolder.swipe_my_settings.setEnabled(true);
        }else {
            viewHolder.swipe_my_settings.setEnabled(false);
        }
            viewHolder.swipe_my_settings.setShowMode(SwipeLayout.ShowMode.PullOut);
            viewHolder.swipe_my_settings.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {

                }

                @Override
                public void onStartOpen(SwipeLayout layout) {


                }
            });



        viewHolder.settings_delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String guid = (String) view.getTag();
                    mItemManger.removeShownLayouts(viewHolder.swipe_my_settings);

                    mItemManger.closeAllItems();
                    listLineClicked.deleteItem(guid);


            }
        });



    }




    @Override
    public int getItemCount() {
        return (null != mDataset ? mDataset.size() : 0);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_my_settings;
    }


    public List<AccountDefaultSettingsVO> removeItem(int position) {
        final AccountDefaultSettingsVO model = mDataset.remove(position);
        notifyItemRemoved(position);
        return mDataset;
    }

    public void addItem(int position, AccountDefaultSettingsVO model) {
        mDataset.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final AccountDefaultSettingsVO model = mDataset.remove(fromPosition);
        mDataset.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<AccountDefaultSettingsVO> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<AccountDefaultSettingsVO> newModels) {
        for (int i = mDataset.size() - 1; i >= 0; i--) {
            final AccountDefaultSettingsVO model = mDataset.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<AccountDefaultSettingsVO> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final AccountDefaultSettingsVO model = newModels.get(i);
            if (!mDataset.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<AccountDefaultSettingsVO> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final AccountDefaultSettingsVO model = newModels.get(toPosition);
            final int fromPosition = mDataset.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}
