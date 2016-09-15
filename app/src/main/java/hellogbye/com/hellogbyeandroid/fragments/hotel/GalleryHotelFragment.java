package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.HotelGalleryImageAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;

/**
 * Created by arisprung on 9/15/16.
 */
public class GalleryHotelFragment extends HGBAbstractFragment {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private HotelGalleryImageAdapter mGalleryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hotel_gallery_layout, container, false);

//        mRecyclerView = (RecyclerView)v.findViewById(R.id.hotel_gallery_list);
//        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setHasFixedSize(false);
//
//        mGalleryAdapter = new HotelGalleryImageAdapter(mImageList);
//        mRecyclerView.setAdapter(mGalleryAdapter);
        return v;
    }
}
