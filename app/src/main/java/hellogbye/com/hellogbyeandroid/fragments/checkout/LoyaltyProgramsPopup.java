package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by amirlubashevsky on 13/08/2017.
 */

public class LoyaltyProgramsPopup extends HGBAbstractFragment implements SearchView.OnQueryTextListener{

    private RecyclerView checkout_recycle_view;
    private CheckoutLoyltyAdapter checkoutLoyltyAdapter;
    private List<AirlinePointsProgramVO> staticAirlinePointsProgramCurrent;

    public static Fragment newInstance(int position) {
        Fragment fragment = new LoyaltyProgramsPopup();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getFlowInterface().enableFullScreen(true);
        // Inflate the layout to use as dialog or embedded fragment
        View rootView = inflater.inflate(R.layout.checkout_loyalty_program, container, false);

        checkout_recycle_view = (RecyclerView)rootView.findViewById(R.id.checkout_recycle_view);
        checkout_recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));

        //checkout_recycle_view.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

        staticAirlinePointsProgramCurrent =  getActivityInterface().getUserAirlinePointsProgram();

        checkoutLoyltyAdapter = new CheckoutLoyltyAdapter(staticAirlinePointsProgramCurrent);
   //     checkoutLoyltyAdapter.updateItems(staticAirlinePointsProgramCurrent);
        checkout_recycle_view.setAdapter(checkoutLoyltyAdapter);
        SearchView checkout_loyalty_search = (SearchView)rootView.findViewById(R.id.checkout_loyalty_search);
        checkout_loyalty_search.setIconifiedByDefault(false);

        //mSearchView.setQueryHint(getString(R.string.settings_search_hunt));
        checkout_loyalty_search.setOnQueryTextListener(this);
        setHasOptionsMenu(true);

        checkoutLoyltyAdapter.setOnItemClickListener(new CheckoutLoyltyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FontTextView checkout_item_loyalty_program = (FontTextView) view.findViewById(R.id.checkout_item_loyalty_program);
                String name = checkout_item_loyalty_program.getText().toString();
                AirlinePointsProgramVO selectedAirlineProgramm = findSelectedProgram(name);
                getActivityInterface().setSelectedProgram(selectedAirlineProgramm);
                ((MainActivityBottomTabs)getActivity()).onBackPressed();

            }});

        return rootView;
    }

    private AirlinePointsProgramVO findSelectedProgram(String name){
        for(AirlinePointsProgramVO airlinePointsProgramVO : staticAirlinePointsProgramCurrent){
            if(airlinePointsProgramVO.getProgramname().equalsIgnoreCase(name)){
                return airlinePointsProgramVO;
            }
        }
        return null;
    }

    @Override
    public boolean onQueryTextChange(String query) {

        // Here is where we are going to implement our filter logic
        final List<AirlinePointsProgramVO> filteredModelList = filter(staticAirlinePointsProgramCurrent, query);
        checkoutLoyltyAdapter.animateTo(filteredModelList);
        checkoutLoyltyAdapter.notifyDataSetChanged();
        checkout_recycle_view.scrollToPosition(0);
        checkout_recycle_view.setVisibility(View.VISIBLE);
       // mDynamicListView.setVisibility(View.GONE);
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<AirlinePointsProgramVO> filter(List<AirlinePointsProgramVO> models, String query) {
        query = query.toLowerCase();

        final List<AirlinePointsProgramVO> filteredModelList = new ArrayList<>();
        for (AirlinePointsProgramVO model : models) {
            final String text = model.getProgramname().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }

        return filteredModelList;
    }

}
