package hellogbye.com.hellogbyeandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


public class AlternativeHotelRoomAdapter extends RecyclerView.Adapter<AlternativeHotelRoomAdapter.ViewHolder> {


    private List<RoomsVO> itemsData;

    private OnItemClickListener mItemClickListener;
    private  ImageLoader imageLoader;
    // Provide a suitable constructor (depends on the kind of dataset)
    public AlternativeHotelRoomAdapter(ArrayList<RoomsVO> myDataset) {
        itemsData = myDataset;
    }

    @Override
    public AlternativeHotelRoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alt_room_item_layout, null);


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {


        RoomsVO room = itemsData.get(position);
        if(imageLoader == null){
            imageLoader = ImageLoader.getInstance();
        }

        if(room.getmImages().size()> 0 ){
            imageLoader.displayImage(room.getmImages().get(0), viewHolder.mRoomImageView);
        }
        else{
            viewHolder.mRoomImageView.setBackgroundResource(R.drawable.bubbles_red_background);
        }
//            DisplayImageOptions opts = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                    .postProcessor(new BitmapProcessor() {
//                        @Override
//                        public Bitmap process(Bitmap bmp) {
//                            return Bitmap.createScaledBitmap(bmp,800, 500, false);
//                        }
//                    })
//                    .build();
//            imageLoader.displayImage(room.getmImages().get(0),viewHolder.mRoomImageView,opts,new SimpleImageLoadingListener() {
//                @Override
//                public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
//                    // Do whatever you want with Bitmap
//                    viewHolder.mCardLayout.setVisibility(View.VISIBLE);
//                }
//
//
//            });


        viewHolder.mRoomPriceFontTextView.setText(""+room.getmCost());
        viewHolder.mRoomTypeFontTextView.setText("" + room.getmRoomType());

        if(room.ismIsAlternative()){
            viewHolder.mRoomSelectedImageView.setVisibility(View.GONE);
        }else{
            viewHolder.mRoomSelectedImageView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }


    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mRoomImageView;
        private FontTextView mRoomPriceFontTextView;
        private FontTextView mRoomTypeFontTextView;
        private ImageView mRoomSelectedImageView;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mRoomImageView = (ImageView) itemLayoutView.findViewById(R.id.room_image);
            mRoomTypeFontTextView = (FontTextView) itemLayoutView.findViewById(R.id.room_type);
            mRoomPriceFontTextView= (FontTextView) itemLayoutView.findViewById(R.id.room_price);
            mRoomSelectedImageView = (ImageView)itemLayoutView.findViewById(R.id.room_selected);

            itemLayoutView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            //  mItemClickListener.onItemClick();
        }

    }


    public interface OnItemClickListener {
        void onItemClick();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
