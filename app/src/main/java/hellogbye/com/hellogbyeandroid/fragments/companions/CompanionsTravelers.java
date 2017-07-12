package hellogbye.com.hellogbyeandroid.fragments.companions;




import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.ISwipeAdapterExecution;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.companion.CompanionsSwipeItemsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.itinerary.ItineraryFragment;
import hellogbye.com.hellogbyeandroid.fragments.itinerary.ItineraryFragmentAdapter;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;


/**
 * Created by nyawka on 4/20/16.
 */
public class CompanionsTravelers extends CompanionsTabsViewClass {



    public static Fragment newInstance(int position) {
        Fragment fragment = new CompanionsTravelers();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public CompanionsTravelers() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = createViewForTab(R.layout.companions_travel, getContext(),false);
        boolean addCompanionsToCNCScreen = addingCompanionsToCNCView();

        setSearchView(rootView, addCompanionsToCNCScreen);


        return rootView;
    }

    private boolean addingCompanionsToCNCView() {
        Bundle args = getArguments();
        if (args != null) {
            String className = args.getString("class_name");
            if (ItineraryFragmentAdapter.class.toString().equals(className)) {

                return true;
            }

        }
        return false;
    }


}
