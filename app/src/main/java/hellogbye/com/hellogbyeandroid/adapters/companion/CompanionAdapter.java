package hellogbye.com.hellogbyeandroid.adapters.companion;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 11/3/15.
 */
public class CompanionAdapter extends RecyclerView.Adapter<CompanionAdapter.ViewHolder> {

    private OnItemClickListener mItemClickListener;
    private ArrayList<CompanionVO> itemsData;
    private Context context;
    public CompanionAdapter(List<CompanionVO> accountSettings, Context context) {
        this.context = context;
        if(accountSettings == null){
            itemsData = new ArrayList<>();
        }else {
            itemsData = new ArrayList<>(accountSettings);
        }
    }


    public void updateItems(List<CompanionVO> accountSettings){
        this.itemsData = new ArrayList<>(accountSettings);
        notifyDataSetChanged();
    }

    @Override
    public CompanionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.companion_search_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CompanionVO item = itemsData.get(position);
        holder.companion_name.setText(item.getCampanionUserProfile().getmFirstName());
        holder.companion_name.setTag(item.getmCompanionid());
        holder.companion_request.setText(item.getmConfirmationstatus());

        if(!item.getmConfirmationstatus().equals("Accepted")) {
            holder.companion_arrow.setVisibility(View.GONE);
            holder.companion_delete.setVisibility(View.VISIBLE);
        }else{
            holder.companion_arrow.setVisibility(View.VISIBLE);
            holder.companion_delete.setVisibility(View.GONE);
        }

       HGBUtility.loadRoundedImage(context, item.getCampanionUserProfile().getmAvatar(), holder.getCompanion_image_view());


    }


    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private FontTextView companion_name;
        private FontTextView companion_request;
        private ImageView companion_image_view;
        private View itemView;
        private View companion_arrow;
        private Button companion_delete;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            companion_delete = (Button)itemView.findViewById(R.id.companion_delete);
            companion_name = (FontTextView) itemView.findViewById(R.id.companion_details_name_item);
            companion_request = (FontTextView) itemView.findViewById(R.id.companion_request);
            companion_arrow = (View)itemView.findViewById(R.id.companion_arrow);
            setCompanion_image_view((ImageView) itemView.findViewById(R.id.companion_image_view));


//TODO remove click if !"confirmationstatus": "Accepted",
            this.itemView.setOnClickListener(clickListener);
            this.companion_delete.setOnClickListener(clickListener);
        }


     public View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String request = companion_request.getText().toString();
                if(request.equals("Accepted")){
                    String userId = companion_name.getTag().toString();
                    mItemClickListener.onItemClick(userId,"");
                }
                else if(view instanceof Button){
                    String userId = companion_name.getTag().toString();
                    mItemClickListener.onItemClick(userId,"");
                }
            }
        };

//        @Override
//        public void onClick(View view) {
//
//            String userId = companion_name.getTag().toString();
//
//            mItemClickListener.onItemClick(userId,"");
//        }

        public ImageView getCompanion_image_view() {
            return companion_image_view;
        }

        public void setCompanion_image_view(ImageView companion_image_view) {
            this.companion_image_view = companion_image_view;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String guid, String position);
    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public List<CompanionVO> removeItem(int position) {
        final CompanionVO model = itemsData.remove(position);
        notifyItemRemoved(position);
        return itemsData;
    }

    public void addItem(int position, CompanionVO model) {
        itemsData.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final CompanionVO model = itemsData.remove(fromPosition);
        itemsData.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<CompanionVO> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }


    private void applyAndAnimateRemovals(List<CompanionVO> newModels) {
        for (int i = itemsData.size() - 1; i >= 0; i--) {
            final CompanionVO model = itemsData.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<CompanionVO> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final CompanionVO model = newModels.get(i);
            if (!itemsData.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<CompanionVO> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final CompanionVO model = newModels.get(toPosition);
            final int fromPosition = itemsData.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

}
