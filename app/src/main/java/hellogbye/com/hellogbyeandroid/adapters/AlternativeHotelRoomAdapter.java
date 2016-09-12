package hellogbye.com.hellogbyeandroid.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


public class AlternativeHotelRoomAdapter extends  RecyclerView.Adapter<AlternativeHotelRoomAdapter.ViewHolder> {


    private  List<RoomsVO> itemsData;

    private OnItemClickListener mItemClickListener;
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
        ImageLoader imageLoader = ImageLoader.getInstance();
        if(room.getmImages().size()>0){
            //imageLoader.displayImage(room.getmImages().get(0),viewHolder.mRoomImageView);
            imageLoader.loadImage(room.getmImages().get(0), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    // Do whatever you want with Bitmap

                    viewHolder.mRoomImageView.setImageBitmap(loadedImage);
                }
            });


//            ImageSize targetSize = new ImageSize(300, 180); // result Bitmap will be fit to this size
//            imageLoader.loadImage(room.getmImages().get(0), targetSize, new SimpleImageLoadingListener() {
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    viewHolder.mRoomImageView.setImageBitmap(loadedImage);
//                }
//            });

        }else{
            //TODO fix default image
        }

        viewHolder.mRoomPriceFontTextView.setText(""+room.getmCost());
     //   viewHolder.mRoomTypeFontTextView.setText(""+room.getmType());


    }

    @Override
    public int getItemCount() {
        return (null != itemsData ? itemsData.size() : 0);
    }



    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mRoomImageView;
        private FontTextView  mRoomPriceFontTextView;
        private FontTextView  mRoomTypeFontTextView;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mRoomImageView = (ImageView)itemLayoutView.findViewById(R.id.room_image);
            mRoomPriceFontTextView = (FontTextView) itemLayoutView.findViewById(R.id.room_price);
         //   mRoomTypeFontTextView= (FontTextView) itemLayoutView.findViewById(R.id.room_type);


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
