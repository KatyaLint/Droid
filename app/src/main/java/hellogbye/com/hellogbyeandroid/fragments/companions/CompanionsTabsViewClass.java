package hellogbye.com.hellogbyeandroid.fragments.companions;




import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;

/**
 * Created by nyawka on 4/20/16.
 */
public class CompanionsTabsViewClass  extends HGBAbstractFragment  implements SearchView.OnQueryTextListener{


    private SearchView mSearchView;
    private RecyclerView searchRecyclerView;
    private CompanionsSwipeItemsAdapter searchListAdapter;
    private View popup_companion_new;
    private FontEditTextView[] inputs;
    private FontEditTextView companion_editTextDialog_name;
    private FontEditTextView companion_editTextDialog_last_name;
    private FontEditTextView companion_editTextDialog;
    private LinearLayout companion_empty_view;
    private View rootView;
    private ArrayList<CompanionVO> companionsVOPending;
    public  static boolean isPending;

    public void isPendingTabs(boolean isPending){
        this.isPending = isPending;
    }

    private void searchListInitialization(){

        // mSearchView.setVisibility(View.GONE);
        mSearchView.setOnQueryTextListener(this);

        searchRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // 5. set item animator to DefaultAnimator
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void pendingCompanions(ArrayList<CompanionVO>  companionsVO){
        companionsVOPending.clear();
        for (CompanionVO companionVO : companionsVO) {
            if(companionVO.getmConfirmationstatus().equals("Pending")){
                companionsVOPending.add(companionVO);
            }
        }

    }

    private void acceptedCompanions(ArrayList<CompanionVO>  companionsVO){
        companionsVOPending.clear();
        for (CompanionVO companionVO:companionsVO) {
            if(companionVO.getmConfirmationstatus().equals("Accepted")){
                companionsVOPending.add(companionVO);
            }
        }

    }




    public View createViewForTab(int layoutID, Context context, boolean isPending){
        LayoutInflater inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(layoutID,null, false);
        companionsVOPending = new ArrayList<>();
        this.isPending = isPending;
        ArrayList<CompanionVO>  companionsVO = getActivityInterface().getCompanions();

        if(isPending){
            pendingCompanions(companionsVO);
        }else{
            acceptedCompanions(companionsVO);
        }


        popup_companion_new = inflater.inflate(R.layout.popup_layout_with_edit_text_new, null);

        companion_editTextDialog_name = (FontEditTextView)popup_companion_new.findViewById(R.id.companion_editTextDialog_name);
        companion_editTextDialog_last_name = (FontEditTextView)popup_companion_new.findViewById(R.id.companion_editTextDialog_last_name);
        companion_editTextDialog = (FontEditTextView) popup_companion_new.findViewById(R.id.companion_editTextDialog);
        inputs = new FontEditTextView[]{companion_editTextDialog_name, companion_editTextDialog_last_name, companion_editTextDialog };

        FloatingActionButton companion_invite_companion = (FloatingActionButton) rootView.findViewById(R.id.fab);
        companion_invite_companion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addTravelCompanion();
            }
        });


        return rootView;
    }


    public void setSearchView(RecyclerView searchRecyclerView, LinearLayout companion_empty_view, SearchView searchView){

        this.mSearchView = searchView;
        this.searchRecyclerView = searchRecyclerView;
        this.companion_empty_view = companion_empty_view;

        emptyCompanionsView();
        searchListInitialization();

        searchListAdapter = new CompanionsSwipeItemsAdapter(getActivity().getApplicationContext(), companionsVOPending);
        searchListAdapter.addClickeListeners(new ISwipeAdapterExecution(){

            @Override
            public void clickedItem(final String guid) {
                Bundle args = new Bundle();
                args.putString("user_id", guid);
                getFlowInterface().goToFragment(ToolBarNavEnum.COMPANIONS_DETAILS.getNavNumber(), args);

            }

            @Override
            public void deleteClicked(final String companionID) {

                deleteComapanion(companionID);

            }
        });
        searchRecyclerView.setAdapter(searchListAdapter);

    }


    private void emptyCompanionsView(){
        if(companionsVOPending == null || companionsVOPending.isEmpty()){
            companion_empty_view.setVisibility(View.VISIBLE);
            searchRecyclerView.setVisibility(View.GONE);
        }else{
            searchRecyclerView.setVisibility(View.VISIBLE);
            companion_empty_view.setVisibility(View.GONE);
        }
    }

    private void getCompanions(){
        ConnectionManager.getInstance(getActivity()).getCompanions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                ArrayList<CompanionVO> companions =(ArrayList<CompanionVO>)data;
                getActivityInterface().setCompanions(companions);

                if(isPending){
                    pendingCompanions(companions);
                }else{
                    acceptedCompanions(companions);
                }

                emptyCompanionsView();

                searchListAdapter.updateItems(companionsVOPending);

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getActivity().getFragmentManager(), (String) data);
            }
        });
    }


    public void deleteComapanion(String comapnion_id){

        ConnectionManager.getInstance(getActivity()).deleteUserCompanion(comapnion_id, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                getCompanions();
                //deleteCompanion.companionDeleted();

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getActivity().getFragmentManager(), (String) data);
            }
        });
    }

    private void addTravelCompanion() {

        HGBUtility.showAlertPopAddCompanion(getActivity(),inputs,popup_companion_new,getResources().getString(R.string.component_invite_new_companion),
                new PopUpAlertStringCB(){

                    @Override
                    public void itemSelected(String inputItem) {
                        //go to server
                        String[] parts = inputItem.split("&");
                        ConnectionManager.getInstance(getActivity()).postCompanions(parts[0],parts[1],parts[2], new ConnectionManager.ServerRequestListener() {
                            @Override
                            public void onSuccess(Object data) {
                                getCompanions();
                            }

                            @Override
                            public void onError(Object data) {
                                HGBErrorHelper errorHelper = new HGBErrorHelper();
                                errorHelper.setMessageForError((String) data);
                                errorHelper.show(getActivity().getFragmentManager(), (String) data);
                            }
                        });
                    }

                    @Override
                    public void itemCanceled() {

                    }
                });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onQueryTextChange(String query) {

        // Here is where we are going to implement our filter logic
        final List<CompanionVO> filteredModelList = filter(companionsVOPending, query);
        searchListAdapter.animateTo(filteredModelList);
        searchRecyclerView.scrollToPosition(0);
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<CompanionVO> filter(List<CompanionVO> models, String query) {
        query = query.toLowerCase();

        final List<CompanionVO> filteredModelList = new ArrayList<>();
        for (CompanionVO model : models) {
            final String text = model.getCompanionUserProfile().getmFirstName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
