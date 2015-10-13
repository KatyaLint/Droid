package hellogbye.com.hellogbyeandroid.fragments;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

/**
 * Created by arisprung on 9/30/15.
 */
public class HotelFragment extends HGBAbtsractFragment {

    private MapFragment fragment;
    private GoogleMap mMap;
    private TableLayout mTableLayout;
    ArrayList<String> mImageList;
    private SlidingUpPanelLayout mSlidingPanels;
    private static final String TAG = "HotelFragment";
    private boolean mStartedSliding;
    private ScrollView mScrollView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hotel_main_layout, container, false);
        mTableLayout = (TableLayout) rootView.findViewById(R.id.tableLayout1);
        mSlidingPanels = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
        mSlidingPanels.setCoveredFadeColor(Color.TRANSPARENT);
        mSlidingPanels.setParalaxOffset(100);
        mScrollView= (ScrollView)rootView.findViewById(R.id.detail_scroll_view);
//        Animation  mAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                0.0f, Animation.RELATIVE_TO_SELF, -5.0f);
//        mAnimation.setDuration(5000);
//        mAnimation.setFillAfter(true);
//        mScrollView.setAnimation(mAnimation);

        mSlidingPanels.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
//                Log.i(TAG, "main height=" + mSlidingPanels.findViewById(R.id.main).getHeight());
//                if(!mStartedSliding){
//                    mStartedSliding = true;
//                    mSlidingPanels.setPanelHeight(200);
//                }

            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");
  //              Log.i(TAG, "main height=" + mSlidingPanels.findViewById(R.id.main).getHeight());
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");
      ///          Log.i(TAG, "main height=" + mSlidingPanels.findViewById(R.id.main).getHeight());
            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
      //          Log.i(TAG, "main height=" + mSlidingPanels.findViewById(R.id.main).getHeight());
            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });

        mImageList = new ArrayList<>();
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        mImageList.add("http://media.expedia.com/hotels/1000000/50000/41300/41247/41247_43_b.jpg");
        buildTable(mImageList.size() / 2);//
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();

        fragment = ((MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map));//(SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap = fragment.getMap();

        if (mMap != null) {
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.setMyLocationEnabled(true);


        } else {
            Log.d("DEBUG", "map is null");
        }


    }


    private void buildTable(int cols) {

        // outer for loop
        for (int i = 1; i <= 2; i++) {
            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for (int j = 1; j <= cols; j++) {
                ImageView tv = new ImageView(getActivity());
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                tv.setPadding(5, 5, 5, 5);
                if (mImageList.size() > 0) {
                    String strValue = mImageList.remove(0);
                    HGBUtility.loadImage(getActivity().getApplicationContext(), strValue, tv);
                    row.addView(tv);
                }
            }
            mTableLayout.addView(row);
        }
    }

}
