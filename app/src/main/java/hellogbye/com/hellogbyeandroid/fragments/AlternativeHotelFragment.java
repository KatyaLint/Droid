package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/26/15.
 */
public class AlternativeHotelFragment extends HGBAbtsractFragment {
    private UserTravelVO mTravelDetails;
    private PassengersVO passengersVO;
    private CellsVO cellsVO;
    private NodesVO nodesVO;
    private AlternativeHotelAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private ArrayList<NodesVO> mArrayNodes;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.alternate_hotel_layout, container, false);
        mTravelDetails = getActivityInterface().getTravelOrder();
        passengersVO = mTravelDetails.getPassengerses().get(0);
        cellsVO = passengersVO.getmCells().get(0);
        nodesVO = cellsVO.getmNodes().get(1);
        mArrayNodes = new ArrayList<>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.alt_hotel_recycler_view);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        mAdapter = new AlternativeHotelAdapter(mArrayNodes);
        mRecyclerView.setAdapter(mAdapter);
        //GET ALL HOTEL NODES AND SET CURRENT ONE
        ConnectionManager.getInstance(getActivity()).getAlternateHotelsWithHotel(mTravelDetails.getmSolutionID(), passengersVO.getmPaxguid(), nodesVO.getmCheckIn(), nodesVO.getmCheckOut(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                CellsVO cellsVO = (CellsVO) data;


                for (NodesVO node : cellsVO.getmNodes()) {
                    if (!nodesVO.getmHotelCode().equals(node.getmHotelCode())) {
                        mArrayNodes.add(node);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Object data) {
                Log.d("", "");
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public class AlternativeHotelAdapter extends RecyclerView.Adapter<AlternativeHotelAdapter.ViewHolder> {
        private ArrayList<NodesVO> mArrayList;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public  class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public FontTextView mHotelName;
            public FontTextView mHotelPrice;
            public FontTextView mHotelNights;
            public ImageView mStart1ImageView;
            public ImageView mStart2ImageView;
            public ImageView mStart3ImageView;
            public ImageView mStart4ImageView;
            public ImageView mStart5ImageView;
            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                mHotelName = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_name);
                mHotelPrice = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_price);
                mHotelNights = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_nights);
                mStart1ImageView = (ImageView) itemLayoutView.findViewById(R.id.star1);
                mStart2ImageView = (ImageView) itemLayoutView.findViewById(R.id.star2);
                mStart3ImageView = (ImageView) itemLayoutView.findViewById(R.id.star3);
                mStart4ImageView = (ImageView) itemLayoutView.findViewById(R.id.star4);
                mStart5ImageView = (ImageView) itemLayoutView.findViewById(R.id.star5);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public AlternativeHotelAdapter(ArrayList<NodesVO> myDataset) {
            mArrayList = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public AlternativeHotelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alternative_hotel_item, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            NodesVO nodesVO = mArrayList.get(position);
            holder.mHotelName.setText(nodesVO.getmHotelName());
            holder.mHotelPrice.setText("$"+String.valueOf((int) nodesVO.getmMinimumAmount()));
            holder.mHotelNights.setText(HGBUtility.getDateDiffString(nodesVO.getmCheckIn(), nodesVO.getmCheckOut()));
            setStarRating(holder,nodesVO.getmStarRating());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mArrayList.size();
        }


        private void setStarRating(ViewHolder holder, float star) {

            if ("0.5".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.half_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

            } else if ("1.0".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

            } else if ("1.5".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.half_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

            } else if ("2.0".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

            } else if ("2.5".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.half_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

            } else if ("3.0".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

            } else if ("3.5".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.half_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

            } else if ("4.0".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

            } else if ("4.5".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.half_star);

            } else if ("5.0".equals(String.valueOf(star))) {
                holder.mStart1ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart2ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart3ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart4ImageView.setBackgroundResource(R.drawable.full_star);
                holder.mStart5ImageView.setBackgroundResource(R.drawable.full_star);

            }


        }
    }



}
