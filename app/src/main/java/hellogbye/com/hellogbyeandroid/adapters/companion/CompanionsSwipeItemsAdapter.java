package hellogbye.com.hellogbyeandroid.adapters.companion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.ISwipeAdapterExecution;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.mytrips.TripsFragment;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;


/**
 * Created by nyawka on 3/23/16.
 */
public class CompanionsSwipeItemsAdapter extends RecyclerSwipeAdapter<CompanionsSwipeItemsAdapter.SimpleViewHolder> {


    private Context mContext;
    private ArrayList<CompanionVO> mDataset;
    private static ISwipeAdapterExecution swipeAdapterExecution;

    public void addClickeListeners(ISwipeAdapterExecution swipeAdapterExecution) {
        this.swipeAdapterExecution = swipeAdapterExecution;
    }

    public void updateItems(ArrayList<CompanionVO> accountSettings){
        this.mDataset = new ArrayList<>(accountSettings);
        notifyDataSetChanged();

    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout swipe_my_companions;
        private ImageButton companion_delete_item;
        private RelativeLayout companion_rl;
        private FontTextView companion_details_name_item;
        private FontTextView companion_request;
        private RoundedImageView companion_image_view;
        private View companion_arrow;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipe_my_companions = (SwipeLayout) itemView.findViewById(R.id.swipe_my_companions);
            swipe_my_companions.setDragEdge(SwipeLayout.DragEdge.Left);
            swipe_my_companions.setShowMode(SwipeLayout.ShowMode.PullOut);

            companion_delete_item = (ImageButton) itemView.findViewById(R.id.companion_delete_item);
            companion_rl = (RelativeLayout)itemView.findViewById(R.id.companion_rl);
            companion_image_view = (RoundedImageView) itemView.findViewById(R.id.companion_image_view);
            companion_details_name_item = (FontTextView) itemView.findViewById(R.id.companion_details_name_item);
            companion_request = (FontTextView) itemView.findViewById(R.id.companion_request);
            companion_arrow = (View)itemView.findViewById(R.id.companion_arrow);
//            my_trip_paid = (FontTextView) itemView.findViewById(R.id.my_trip_paid);


            companion_rl.setOnClickListener(new SwipeLayout.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swipeAdapterExecution.clickedItem((String)v.getTag());

                }
            });
        }
    }

    public CompanionsSwipeItemsAdapter(Context context, ArrayList objects) {
        this.mContext = context;
        this.mDataset = objects;
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.companion_search_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        CompanionVO item = mDataset.get(position);


        viewHolder.companion_details_name_item.setText(item.getCompanionUserProfile().getmFirstName());
        viewHolder.companion_details_name_item.setTag(item.getmCompanionid());


        if(!item.getmConfirmationstatus().equals("Accepted")) {
            viewHolder.companion_arrow.setVisibility(View.GONE);
            viewHolder.companion_request.setText(item.getmConfirmationstatus());

        }else{
            viewHolder.companion_arrow.setVisibility(View.VISIBLE);
            viewHolder.companion_request.setText(item.getCompanionUserProfile().getmEmailAddress());
        }

        HGBUtility.loadRoundedImage(item.getCompanionUserProfile().getmAvatar(), viewHolder.companion_image_view, R.drawable.profile_image);


        viewHolder.swipe_my_companions.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipe_my_companions.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {

            }
        });


        viewHolder.companion_delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String guid =  (String)view.getTag();
                System.out.println("Kate delete companion_delete_item guid =" + guid);
                mItemManger.removeShownLayouts(viewHolder.swipe_my_companions);

                mItemManger.closeAllItems();
                swipeAdapterExecution.deleteClicked(guid);
            }
        });


        viewHolder.companion_rl.setTag(item.getmCompanionid());
        System.out.println("Kate position = " + position);

        System.out.println("Kate comapanionId = " + item.getmCompanionid());
        viewHolder.companion_delete_item.setTag(item.getmCompanionid());

    }

    @Override
    public int getItemCount() {
        return (null != mDataset ? mDataset.size() : 0);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    public List<CompanionVO> removeItem(int position) {
        final CompanionVO model = mDataset.remove(position);
        notifyItemRemoved(position);
        return mDataset;
    }

    public void addItem(int position, CompanionVO model) {
        mDataset.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final CompanionVO model = mDataset.remove(fromPosition);
        mDataset.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<CompanionVO> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<CompanionVO> newModels) {
        for (int i = mDataset.size() - 1; i >= 0; i--) {
            final CompanionVO model = mDataset.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<CompanionVO> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final CompanionVO model = newModels.get(i);
            if (!mDataset.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<CompanionVO> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final CompanionVO model = newModels.get(toPosition);
            final int fromPosition = mDataset.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}