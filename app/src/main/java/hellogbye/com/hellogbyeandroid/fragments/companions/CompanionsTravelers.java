package hellogbye.com.hellogbyeandroid.fragments.companions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.itinerary.ItineraryFragmentComposeView;
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
            Boolean className = args.getBoolean(HGBConstants.BUNDLE_ADD_COMPANION_CNC);
            if(className){
                return true;
            }
           /* if (ItineraryFragmentComposeView.class.toString().equals(className)) {

                return true;
            }*/

        }
        return false;
    }


}
