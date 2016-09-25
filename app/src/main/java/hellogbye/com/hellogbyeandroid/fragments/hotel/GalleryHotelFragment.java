package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.ImageGalleryActivity;
import hellogbye.com.hellogbyeandroid.adapters.HotelGalleryImageAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;


/**
 * Created by arisprung on 9/15/16.
 */
public class GalleryHotelFragment extends HGBAbstractFragment {

    private RecyclerView mRecyclerView;
    private HotelGalleryImageAdapter mGalleryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hotel_gallery_layout, container, false);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.hotel_gallery_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGalleryAdapter = new HotelGalleryImageAdapter(getLegWithGuid(getActivityInterface().getTravelOrder()).getAllImagesVOs(),getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mGalleryAdapter);
        mGalleryAdapter.SetOnItemClickListener(new HotelGalleryImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Gson gson = new Gson();
                String json = gson.toJson(getLegWithGuid(getActivityInterface().getTravelOrder()).getAllImagesVOs());
                Intent intent = new Intent(getActivity().getApplicationContext(),ImageGalleryActivity.class);
                intent.putExtra("images",json);
                startActivity(intent);
            }
        });
        return v;
    }
}
