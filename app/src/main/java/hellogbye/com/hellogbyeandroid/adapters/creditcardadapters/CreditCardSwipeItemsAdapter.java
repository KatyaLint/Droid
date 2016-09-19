package hellogbye.com.hellogbyeandroid.adapters.creditcardadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.IClickedItem;
import hellogbye.com.hellogbyeandroid.ISwipeAdapterExecution;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 3/23/16.
 */
public class CreditCardSwipeItemsAdapter extends RecyclerSwipeAdapter<CreditCardSwipeItemsAdapter.SimpleViewHolder> {


    private Context mContext;
    private ArrayList<CreditCardItem>  mDataset;
    private ISwipeAdapterExecution swipeAdapterExecution;
    private  IClickedItem clickedItemIterface;

    public void addClickeListeners(ISwipeAdapterExecution swipeAdapterExecution) {
        this.swipeAdapterExecution = swipeAdapterExecution;

    }

    public void updateItems(ArrayList<CreditCardItem> accountSettings){
        this.mDataset = new ArrayList<>(accountSettings);
        notifyDataSetChanged();

    }

    public void setClickedItemIterface(final IClickedItem clickedItemIterface) {
        this.clickedItemIterface = clickedItemIterface;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout swipe_my_companions;
        private ImageView mCardImage;
        private ImageButton mCardelete;
        private FontTextView mCardNumberText;
        private ImageView mNext;
        private RelativeLayout credit_card_layiut_rl;


        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipe_my_companions = (SwipeLayout) itemView.findViewById(R.id.swipe_my_companions);
            swipe_my_companions.setDragEdge(SwipeLayout.DragEdge.Left);
            swipe_my_companions.setShowMode(SwipeLayout.ShowMode.PullOut);
            mCardelete = (ImageButton) itemView.findViewById(R.id.companion_delete_item);

            credit_card_layiut_rl = (RelativeLayout)itemView.findViewById(R.id.credit_card_layiut_rl);
            mCardImage = (ImageView)itemView.findViewById(R.id.cc_image);
            mCardNumberText = (FontTextView)itemView.findViewById(R.id.cc_number);

         //   mNext = (ImageView)itemView.findViewById(R.id.cc_image_next);
          //   my_trip_paid = (FontTextView) itemView.findViewById(R.id.my_trip_paid);


        }


    }

    public CreditCardSwipeItemsAdapter(Context context) {
        this.mContext = context;

    }

    public void setDataSet(ArrayList objects){
        this.mDataset = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_card_layout, parent, false);
        return new SimpleViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        CreditCardItem item = mDataset.get(position);

        viewHolder.mCardNumberText.setText(item.getLast4());

        String cardID = item.getCardtypeid();
        if(cardID == null){
            return;
        }

        switch (item.getCardtypeid()){
            case "1":
                break;
            case "2":
                break;
            case "3":
                viewHolder.mCardImage.setBackgroundResource(R.drawable.master_card);
                break;
            case "4":
                viewHolder.mCardImage.setBackgroundResource(R.drawable.visa);
                break;
            default:
                viewHolder.mCardImage.setBackgroundResource(R.drawable.visa);
        }

 /*       if(item.getCardtypeid().equals("1")){
            //holder.mCardImage.setBackgroundResource(R.drawable.visa);
        }else if (item.getCardtypeid().equals("2")){
            // holder.mCardImage.setBackgroundResource(R.drawable.master_card);
        }else if (item.getCardtypeid().equals("3")){
            viewHolder.mCardImage.setBackgroundResource(R.drawable.master_card);
        }else if (item.getCardtypeid().equals("4")){
            viewHolder.mCardImage.setBackgroundResource(R.drawable.visa);
        }else {
            viewHolder.mCardImage.setBackgroundResource(R.drawable.visa);
        }*/

        viewHolder.credit_card_layiut_rl.setTag(item.getToken());
        viewHolder.credit_card_layiut_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItemIterface.clickedItem(v.getTag().toString());
            }
        });

        viewHolder.swipe_my_companions.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.mCardelete.setTag(item.getToken());
        viewHolder.mCardelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String token =  (String)view.getTag();
                mItemManger.removeShownLayouts(viewHolder.swipe_my_companions);

                mItemManger.closeAllItems();
               swipeAdapterExecution.deleteClicked(token);
            }
        });


//        viewHolder.companion_delete_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String guid =  (String)view.getTag();
//                swipeAdapterExecution.rejectItem(guid);
//            }
//        });
//
//        viewHolder.companion_confirm_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String guid =  (String)view.getTag();
//                swipeAdapterExecution.confirmItem(guid);
//            }
//        });
//
//        viewHolder.companion_rl.setTag(item.getmCompanionid());
//        viewHolder.companion_delete_item.setTag(item.getmCompanionid());
//
//
//        viewHolder.companion_rl.setOnClickListener(new SwipeLayout.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                swipeAdapterExecution.clickedItem((String)v.getTag());
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return (null != mDataset ? mDataset.size() : 0);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    public List<CreditCardItem> removeItem(int position) {
        final CreditCardItem model = mDataset.remove(position);
        notifyItemRemoved(position);
        return mDataset;
    }

    public void addItem(int position, CreditCardItem model) {
        mDataset.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final CreditCardItem model = mDataset.remove(fromPosition);
        mDataset.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<CreditCardItem> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<CreditCardItem> newModels) {
        for (int i = mDataset.size() - 1; i >= 0; i--) {
            final CreditCardItem model = mDataset.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<CreditCardItem> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final CreditCardItem model = newModels.get(i);
            if (!mDataset.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<CreditCardItem> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final CreditCardItem model = newModels.get(toPosition);
            final int fromPosition = mDataset.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}
