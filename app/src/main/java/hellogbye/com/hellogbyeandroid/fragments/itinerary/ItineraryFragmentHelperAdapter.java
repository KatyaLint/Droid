package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.concurrent.atomic.AtomicInteger;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.TitleNameChange;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by nyawka on 6/26/17.
 */

public class ItineraryFragmentHelperAdapter extends HGBAbstractFragment implements TitleNameChange {

    private HorizontalScrollView scroll_view_grid;
    private ScrollView scrollVertical;

    public static Fragment newInstance(int position) {
        Fragment fragment = new ItineraryFragmentHelperAdapter();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }
    public ItineraryFragmentHelperAdapter() {



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_grid_main_layout_pager_adapter, container, false);


        HorizontalScrollView hsv1 = (HorizontalScrollView) rootView.findViewById(R.id.hsv1);
        hsv1.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Kate horizontal sc");
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);

                int action = event.getAction();
                System.out.println("Kate scroll action = " + action);
                switch (action) {
                    case MotionEvent.ACTION_SCROLL:
                        System.out.println("Kate scroll");

                        break;
                    case MotionEvent.ACTION_UP:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);

                        break;
                }
                return false;
            }
        });





        ViewPager viewPager1 = (ViewPager) rootView.findViewById(R.id.myviewpager1);
        ItineraryPagerAdapter myPagerAdapter1 = new ItineraryPagerAdapter(getContext());
        viewPager1.setAdapter(myPagerAdapter1);


        viewPager1.setOnTouchListener(new ViewPager.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                System.out.println("Kate scroll action = " + action);
                switch (action) {
                    case MotionEvent.ACTION_SCROLL:
                        System.out.println("Kate scroll");

                        break;
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });


       /* viewPager1.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/


        ViewPager viewPager2 = (ViewPager) rootView.findViewById(R.id.myviewpager2);
        ItineraryPagerAdapter myPagerAdapter2 = new ItineraryPagerAdapter(getContext());
        viewPager2.setAdapter(myPagerAdapter2);


        ViewPager viewPager3 = (ViewPager) rootView.findViewById(R.id.myviewpager3);
        ItineraryPagerAdapter myPagerAdapter3 = new ItineraryPagerAdapter(getContext());
        viewPager3.setAdapter(myPagerAdapter3);

        ViewPager viewPager4 = (ViewPager) rootView.findViewById(R.id.myviewpager4);
        ItineraryPagerAdapter myPagerAdapter4 = new ItineraryPagerAdapter(getContext());
        viewPager4.setAdapter(myPagerAdapter4);

        return rootView;
    }







    @Override
    public void titleChangeName() {

    }
}
