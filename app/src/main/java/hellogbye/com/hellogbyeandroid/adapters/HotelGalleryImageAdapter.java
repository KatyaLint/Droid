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
import hellogbye.com.hellogbyeandroid.models.vo.flights.AllImagesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 9/15/16.
 */
public class HotelGalleryImageAdapter extends RecyclerView.Adapter<HotelGalleryImageAdapter.ViewHolder> {


    private List<AllImagesVO> itemsData;

    private OnItemClickListener mItemClickListener;
    private ImageLoader imageLoader;
    public HotelGalleryImageAdapter(ArrayList<AllImagesVO> myDataset) {
        itemsData = myDataset;
    }

    @Override
    public HotelGalleryImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_gallery_item, null);


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        if(imageLoader == null){
            imageLoader = ImageLoader.getInstance();
        }
        AllImagesVO image = itemsData.get(position);


        imageLoader.displayImage(image.getmImage(), viewHolder.mImageView);


    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }


    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mImageView = (ImageView) itemLayoutView.findViewById(R.id.gallery_image);

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
