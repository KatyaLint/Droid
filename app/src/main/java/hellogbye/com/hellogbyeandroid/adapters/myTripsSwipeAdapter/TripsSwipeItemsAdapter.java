package hellogbye.com.hellogbyeandroid.adapters.myTripsSwipeAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.ISwipeAdapterExecution;
import hellogbye.com.hellogbyeandroid.R;
/*import hellogbye.com.hellogbyeandroid.fragments.mytrips.TripsFragment;*/
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;


/**
 * Created by nyawka on 3/23/16.
 */
public class TripsSwipeItemsAdapter extends RecyclerSwipeAdapter<TripsSwipeItemsAdapter.SimpleViewHolder> {

    private ISwipeAdapterExecution swipeAdapterExecution;
    private Context mContext;
    private ArrayList<MyTripItem> mDataset;
    private int tabPosition;
    public void addClickeListeners(ISwipeAdapterExecution swipeAdapterExecution) {
        this.swipeAdapterExecution = swipeAdapterExecution;
    }

    public void setTabPosition(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private final SwipeLayout swipeLayout;


        private final FontTextView my_trip_name;
        private final FontTextView my_trip_dates;
        private final FontTextView my_trip_paid;
        private final RoundedImageView my_trip_user_image;
        private final RelativeLayout mytrips_rl;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_my_trip);
            swipeLayout.setSwipeEnabled(false);
            mytrips_rl = (RelativeLayout)itemView.findViewById(R.id.mytrips_rl);
            my_trip_user_image = (RoundedImageView) itemView.findViewById(R.id.my_trip_user_image);
            my_trip_name = (FontTextView) itemView.findViewById(R.id.my_trip_name);
            my_trip_dates = (FontTextView) itemView.findViewById(R.id.my_trip_dates);
            my_trip_paid = (FontTextView) itemView.findViewById(R.id.my_trip_paid);


        }
    }

    public TripsSwipeItemsAdapter(Context context, ArrayList objects) {
        this.mContext = context;
        this.mDataset = objects;
    }

    public void updateDataSet(ArrayList<MyTripItem> mDataset){
        this.mDataset = mDataset;

    }
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytrip_item_swipe_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        MyTripItem item = mDataset.get(position);
        String itemName = mDataset.get(position).getName();
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {

            }
        });


/*        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String position =  (String)view.getTag();

                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
           //     mDataset.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, mDataset.size());
                mItemManger.closeAllItems();
                swipeAdapterExecution.deleteClicked(position);
                //Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
            }
        });*/


        HGBUtility.loadRoundedImage( item.getTripimage(),  viewHolder.my_trip_user_image, R.drawable.city_avatar_a_2);
        viewHolder.my_trip_name.setText(item.getName());
        viewHolder.my_trip_dates.setText(HGBUtilityDate.parseDateToddMMyyyyMyTrip(item.getStartdate())+" - "+HGBUtilityDate.parseDateToddMMyyyyMyTrip(item.getEnddate()));

        viewHolder.mytrips_rl.setTag(item.getSolutionid());

        viewHolder.mytrips_rl.setOnClickListener(new SwipeLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeAdapterExecution.clickedItem((String)v.getTag());

            }
        });

        if(tabPosition == 2 || tabPosition == 1){
            return;
        }

        if(item.getPaymentstatus().equals("UPD")){
            viewHolder.my_trip_paid.setText("UNPAID");
            viewHolder.my_trip_paid.setTextColor(mContext.getResources().getColor(R.color.COLOR_EE3A3C));
        }else if(item.getPaymentstatus().equals("FPD")){
            viewHolder.my_trip_paid.setText("PAID");
            viewHolder.my_trip_paid.setTextColor(mContext.getResources().getColor(R.color.paid_green));
        }else if((item.getPaymentstatus().equals("FPD"))){
            viewHolder.my_trip_paid.setText("PARTIALLY PAID");
            viewHolder.my_trip_paid.setTextColor(mContext.getResources().getColor(R.color.paid_green));
        }


//        viewHolder.textViewPos.setText((position + 1) + ".");
//        viewHolder.textViewData.setText(item);
       // mItemManger.bind(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    public void animateTo(List<MyTripItem> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<MyTripItem> newModels) {
        for (int i = mDataset.size() - 1; i >= 0; i--) {
            final MyTripItem model = mDataset.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<MyTripItem> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final MyTripItem model = newModels.get(i);
            if (!mDataset.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<MyTripItem> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final MyTripItem model = newModels.get(toPosition);
            final int fromPosition = mDataset.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
    public List<MyTripItem> removeItem(int position) {
        final MyTripItem model = mDataset.remove(position);
        notifyDataSetChanged();
        return mDataset;
    }

    public void addItem(int position, MyTripItem model) {
        mDataset.add(position, model);
        notifyDataSetChanged();
    }

    public void moveItem(int fromPosition, int toPosition) {
        final MyTripItem model = mDataset.remove(fromPosition);
        mDataset.add(toPosition, model);
        notifyDataSetChanged();
    }


}
