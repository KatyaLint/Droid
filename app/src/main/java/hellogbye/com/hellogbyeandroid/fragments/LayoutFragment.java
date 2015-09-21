//package hellogbye.com.hellogbyeandroid.gridview;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.lucasr.twowayview.ItemClickSupport;
//import org.lucasr.twowayview.widget.DividerItemDecoration;
//import org.lucasr.twowayview.widget.TwoWayView;
//
//import hellogbye.com.hellogbyeandroid.R;
//
//import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
//import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
//import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
//
///**
// * Created by nyawka on 9/6/15.
// */
//public class LayoutFragment extends Fragment {
//    private TwoWayView mRecyclerView;
//    private TextView mPositionText;
//    private TextView mCountText;
//    private TextView mStateText;
//    private Toast mToast;
//    private static final String ARG_LAYOUT_ID = "layout_id";
//    private int mLayoutId;
//
//    public static Fragment newInstance(int layoutId) {
//        Fragment fragment = new LayoutFragment();
//
//        Bundle args = new Bundle();
//        args.putInt(ARG_LAYOUT_ID, layoutId);
//        fragment.setArguments(args);
//
//        return fragment;
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mLayoutId = R.layout.layout_staggered_grid;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.layout_staggered_grid, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        final Activity activity = getActivity();
//
//        mToast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
//        mToast.setGravity(Gravity.CENTER, 0, 0);
//
//        mRecyclerView = (TwoWayView) view.findViewById(R.id.list);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLongClickable(true);
//
//        mPositionText = (TextView) view.getRootView().findViewById(R.id.position);
//        mCountText = (TextView) view.getRootView().findViewById(R.id.count);
//
//        mStateText = (TextView) view.getRootView().findViewById(R.id.state);
//        updateState(SCROLL_STATE_IDLE);
//
//        final ItemClickSupport itemClick = ItemClickSupport.addTo(mRecyclerView);
//
//        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView parent, View child, int position, long id) {
//                mToast.setText("Item clicked: " + position);
//                mToast.show();
//            }
//        });
//
//        itemClick.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(RecyclerView parent, View child, int position, long id) {
//                mToast.setText("Item long pressed: " + position);
//                mToast.show();
//                return true;
//            }
//        });
//
//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
//                updateState(scrollState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + mRecyclerView.getFirstVisiblePosition());
//                mCountText.setText("Count: " + mRecyclerView.getChildCount());
//            }
//        });
//
//        final Drawable divider = getResources().getDrawable(R.drawable.divider);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(divider));
//
//        mRecyclerView.setAdapter(new LayoutAdapter(activity, mRecyclerView, mLayoutId));
//    }
//
//    private void updateState(int scrollState) {
//        String stateName = "Undefined";
//        switch(scrollState) {
//            case SCROLL_STATE_IDLE:
//                stateName = "Idle";
//                break;
//
//            case SCROLL_STATE_DRAGGING:
//                stateName = "Dragging";
//                break;
//
//            case SCROLL_STATE_SETTLING:
//                stateName = "Flinging";
//                break;
//        }
//
//        mStateText.setText(stateName);
//    }
//
//
//
//}
