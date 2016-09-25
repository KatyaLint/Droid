package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.AllImagesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static com.google.android.gms.analytics.internal.zzy.v;

/**
 * Created by arisprung on 9/15/16.
 */
public class HotelGalleryImageAdapter extends RecyclerView.Adapter<HotelGalleryImageAdapter.ViewHolder> {


    private List<AllImagesVO> itemsData;

    private OnItemClickListener mItemClickListener;
    private ImageLoader imageLoader;
    private Context mContext;

    public HotelGalleryImageAdapter(ArrayList<AllImagesVO> myDataset, Context context) {
        itemsData = myDataset;
        mContext = context;

    }

    @Override
    public HotelGalleryImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(mContext).inflate(R.layout.hotel_gallery_image_1, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
        }
        AllImagesVO image1 = null;
        AllImagesVO image2= null;
        switch (position) {
            case 0:
                 image1 = itemsData.get(0);
                 image2 = itemsData.get(1);

//
//                setLayoutParams(viewHolder.mImageView2,1);
                setLayoutParams(viewHolder.mImageView1,2);
                setLayoutParams(viewHolder.mImageView2,1);
                imageLoader.displayImage(image1.getmImage(), viewHolder.mImageView1);
                imageLoader.displayImage(image2.getmImage(), viewHolder.mImageView2);

                break;
            case 1:
                 image1 = itemsData.get(2);
                 image2 = itemsData.get(3);
//
                setLayoutParams(viewHolder.mImageView1,2);
                setLayoutParams(viewHolder.mImageView2,1);
                imageLoader.displayImage(image1.getmImage(), viewHolder.mImageView1);
                imageLoader.displayImage(image2.getmImage(), viewHolder.mImageView2);
                break;
            case 2:
                 image1 = itemsData.get(4);
                 image2 = itemsData.get(5);

                setLayoutParams(viewHolder.mImageView1,1);
                viewHolder.mRelativeLayout.setVisibility(View.VISIBLE);
                imageLoader.displayImage(image1.getmImage(), viewHolder.mImageView1);
                imageLoader.displayImage(image2.getmImage(), viewHolder.mImageViewRL);
                viewHolder.mNumberImages.setText("+"+(itemsData.size()-6));
                break;
        }







    }

    private void setLayoutParams(ImageView mImageView1, float v) {
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)
                mImageView1.getLayoutParams();
        params1.weight =v;
        mImageView1.setLayoutParams(params1);
    }



    @Override
    public int getItemCount() {
        return 3;
    }


    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView1;
        private ImageView mImageView2;
        private ImageView mImageViewRL;
      private FontTextView mNumberImages;
        private RelativeLayout mRelativeLayout;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mImageView1 = (ImageView) itemLayoutView.findViewById(R.id.gallery_image_1_1);
            mImageView2 = (ImageView) itemLayoutView.findViewById(R.id.gallery_image_1_2);
            mImageViewRL = (ImageView) itemLayoutView.findViewById(R.id.gallery_image_rl);
            mNumberImages = (FontTextView) itemLayoutView.findViewById(R.id.gallery_number_images);
            mRelativeLayout= (RelativeLayout) itemLayoutView.findViewById(R.id.second_image_rl);
            itemLayoutView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

             mItemClickListener.onItemClick();
        }

    }


    public interface OnItemClickListener {
        void onItemClick();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    boolean isEven(double num) { return ((num % 2) == 0); }
}
