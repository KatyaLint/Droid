package hellogbye.com.hellogbyeandroid.fragments.companions;




import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.ISwipeAdapterExecution;
import hellogbye.com.hellogbyeandroid.R;

import hellogbye.com.hellogbyeandroid.activities.ForgotPasswordActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.companion.CompanionsSwipeItemsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionsSearchItemsVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.ICompanionsSearchCB;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static hellogbye.com.hellogbyeandroid.R.layout.companion_search_not_found;

/**
 * Created by nyawka on 4/20/16.
 */
public class CompanionsTabsViewClass  extends HGBAbstractFragment implements android.support.v7.widget.SearchView.OnQueryTextListener{


  //  private SearchView mSearchView;
    private RecyclerView searchRecyclerView;
    private CompanionsSwipeItemsAdapter searchListAdapter;
    private View popup_companion_new;
    private FontEditTextView[] inputs;
/*    private FontEditTextView companion_editTextDialog_name;
    private FontEditTextView companion_editTextDialog_last_name;*/
    private FontEditTextView companion_editTextDialog;
    private LinearLayout companion_empty_view;
    private View rootView;
    private ArrayList<CompanionVO> companionsVOPending = new ArrayList<>();
    private ArrayList<CompanionVO> searchCompanionsVO = new ArrayList<>();
    private ArrayList mCurrItemsSearchList = new ArrayList();
    public static boolean isPending;
    private android.support.v7.widget.SearchView search_view;
    private ImageButton search_maginfy;
    private FontTextView titleBar;
    private ImageButton toolbar_add_companion;
    private LinearLayout companion_search_new_companion_view;
    private LinearLayout companion_search_not_found;
    private LinearLayout companion_empty_view_travel_companion_ll;


    private void searchListInitialization(){

        // mSearchView.setVisibility(View.GONE);
        search_view.setOnQueryTextListener(this);

        searchRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // 5. set item animator to DefaultAnimator
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initSearchBar() {

        ImageView searchClose = (ImageView) search_view.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        TextView searchCloseText = (TextView) search_view.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchClose.setImageResource(R.drawable.close_icon_a_1);
        searchCloseText.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        searchCloseText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));

        closeSearchBar();
        search_maginfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSearchBar();
            }
        });

        search_view.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                closeSearchBar();
                return false;
            }
        });

    }
    private void closeSearchBar() {
        titleBar.setVisibility(View.VISIBLE);
        toolbar_add_companion.setVisibility(View.VISIBLE);
       // toolbar_new_iternerary.setVisibility(View.VISIBLE);
        search_maginfy.setVisibility(View.VISIBLE);
        search_view.setVisibility(View.GONE);


        companion_search_new_companion_view.setVisibility(View.GONE);
        searchRecyclerView.setVisibility(View.VISIBLE);
    }


    private void openSearchBar() {
        companion_search_new_companion_view.setVisibility(View.VISIBLE);

        titleBar.setVisibility(View.GONE);

        toolbar_add_companion.setVisibility(View.GONE);
     //   toolbar_new_iternerary.setVisibility(View.GONE);
        search_maginfy.setVisibility(View.GONE);
        search_view.setVisibility(View.VISIBLE);
        search_view.setIconified(false);


        searchRecyclerView.setVisibility(View.INVISIBLE);

    }


    private void pendingCompanions(ArrayList<CompanionVO> companionsVO){
        companionsVOPending.clear();
        for (CompanionVO companionVO : companionsVO) {
            if(companionVO.getmConfirmationstatus().equals("Pending")){
                companionsVOPending.add(companionVO);
            }
        }
    }

    private void addInvitationsToPanding(ArrayList<CompanionVO> companionsVO){
        if(companionsVO == null){
            return;
        }
        companionsVOPending.addAll(companionsVO);
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
        getCompanions();

      //  popup_companion_new = inflater.inflate(R.layout.popup_layout_with_edit_text_new, null);
        popup_companion_new = inflater.inflate(R.layout.popup_layout_log_out, null);


/*        companion_editTextDialog_name = (FontEditTextView)popup_companion_new.findViewById(R.id.companion_editTextDialog_name);
        companion_editTextDialog_last_name = (FontEditTextView)popup_companion_new.findViewById(R.id.companion_editTextDialog_last_name);*/

        companion_editTextDialog = (FontEditTextView) popup_companion_new.findViewById(R.id.companion_editTextDialog);
        companion_editTextDialog.setVisibility(View.VISIBLE);


        inputs = new FontEditTextView[]{companion_editTextDialog };


    /*    FloatingActionButton companion_invite_companion = (FloatingActionButton) rootView.findViewById(R.id.fab);
        companion_invite_companion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addTravelCompanion();
            }
        });*/

        ImageButton toolbar_add_companion = ((MainActivityBottomTabs) getActivity()).getAddCompanionButton();
        toolbar_add_companion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTravelCompanion();
            }
        });




        return rootView;
    }


    public void setSearchView(View rootView,  final boolean addCompanionsToCNCScreen ){

        this.searchRecyclerView = (RecyclerView) rootView.findViewById(R.id.companion_travel_recycle_list);
        this.companion_empty_view = (LinearLayout)rootView.findViewById(R.id.companion_empty_view);
        this.companion_empty_view_travel_companion_ll = (LinearLayout)rootView.findViewById(R.id.companion_empty_view_travel_companion_ll);
        this.companion_search_new_companion_view = (LinearLayout) rootView.findViewById(R.id.companion_search_new_companion);
        this.companion_search_not_found = (LinearLayout)rootView.findViewById(R.id.companion_search_not_found_ll);
        FontTextView companion_invite_travel_companion = (FontTextView)rootView.findViewById(R.id.companion_invite_travel_companion);
        companion_invite_travel_companion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTravelCompanion();
            }
        });
                // this.mSearchView = searchView;
       // this.searchRecyclerView = searchRecyclerView;
       // this.companion_empty_view = companion_empty_view;

        //emptyCompanionsView();
      //  companion_search_new_companion_view = companion_search_new_companion;
        companion_search_new_companion_view.setVisibility(View.GONE);
        companion_search_not_found.setVisibility(View.GONE);

        companion_search_not_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTravelCompanion();
            }
        });




        searchListAdapter = new CompanionsSwipeItemsAdapter(getActivity().getApplicationContext(), companionsVOPending);

        searchListAdapter.addClickeListeners(new ISwipeAdapterExecution(){

            @Override
            public void clickedItem(final String guid) {
                Bundle args = new Bundle();
                boolean isSearchView = search_view.isActivated();

                if(addCompanionsToCNCScreen){
                    args.putString(HGBConstants.BUNDLE_ADD_COMPANION_ID, guid);
                    args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, false);
                    getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), args);

                }else if(!searchIsOpen){
                    args.putString(HGBConstants.USER_ID, guid);
                    getFlowInterface().goToFragment(ToolBarNavEnum.COMPANIONS_DETAILS.getNavNumber(), args);
                }


            }

            @Override
            public void deleteClicked(final String companionID) {

                deleteComapanion(companionID);

            }

            @Override
            public void confirmItem(String companionId) {
                confirmCompanion(companionId);
            }

            @Override
            public void rejectItem(String companionId) {
                rejectComapanion(companionId);
            }
        });


        search_view =  ((MainActivityBottomTabs)getActivity()).getSearchView();
        search_view.setQueryHint("Search Companions");
        search_maginfy =  ((MainActivityBottomTabs)getActivity()).getSearchMagifyImage();
        titleBar =  ((MainActivityBottomTabs)getActivity()).getTitleBar();
        toolbar_add_companion = ((MainActivityBottomTabs)getActivity()).getAddCompanionButton();
        searchListInitialization();
        initSearchBar();

        searchListAdapter.setCompanionSearchCB(new ICompanionsSearchCB() {
            @Override
            public void onAddCompanionFromSearchClicked(String companion_id) {
                CompanionVO companionSearchVO  = null;
                for(CompanionVO companionVO : searchCompanionsVO)
                {
                    if(companionVO.getCompanionUserProfile().getmUserProfileId().equals(companion_id)){
                        companionSearchVO = companionVO;
                        break;
                    }
                }
                if(companionSearchVO == null){
                    return;
                }
                companionSearchVO.setmAddedvia("Search");



                ConnectionManager.getInstance(getActivity()).postSearchCompanionAdd(companionSearchVO, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

                        CompanionVO companionVO = (CompanionVO)data;
                     //   getCompanions();
                        LayoutInflater li = LayoutInflater.from(getContext());
                        View popupView = li.inflate(R.layout.popup_layout_log_out, null);
                        HGBUtility.showAlertPopUpOneButton(getActivity(), null, popupView,
                                "Companion Request Sent", new PopUpAlertStringCB(){

                                    @Override
                                    public void itemSelected(String inputItem) {
                                        getCompanions();
                                    }

                                    @Override
                                    public void itemCanceled() {

                                    }
                                });

                      //  setNewRelationshipForCompanion(companionVO);

                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });

            }
        });
        searchRecyclerView.setAdapter(searchListAdapter);
    }


    private void setNewRelationshipForCompanion(CompanionVO companionVO){

        ConnectionManager.getInstance(getActivity()).putCompanionRelationship(companionVO.getmCompanionid(), 1,new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                getCompanions();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void rejectComapanion(String companionId) {

        ConnectionManager.getInstance(getActivity()).rejectUserCompanion(companionId, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                getCompanions();
            }

            @Override
            public void onError(Object data) {
               ErrorMessage(data);
            }
        });
    }


    private void emptyView(){
        if(isPending){
            companion_empty_view.setVisibility(View.VISIBLE);
            companion_empty_view_travel_companion_ll.setVisibility(View.GONE);
        }else{
            companion_empty_view.setVisibility(View.GONE);
            companion_empty_view_travel_companion_ll.setVisibility(View.VISIBLE);
        }
    }

    private void emptyCompanionsView(){
        if(companionsVOPending == null || companionsVOPending.isEmpty()){
            emptyView();
            searchRecyclerView.setVisibility(View.GONE);
        }else{
            mCurrItemsSearchList = new ArrayList<CompanionVO>(companionsVOPending);
            searchRecyclerView.setVisibility(View.VISIBLE);
            companion_empty_view.setVisibility(View.GONE);
            companion_empty_view_travel_companion_ll.setVisibility(View.GONE);
        }
    }


    private void getCompanionsInvitation(){
        ConnectionManager.getInstance(getActivity()).getCompanionInvitation(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if(data == null){
                    return;
                }
                ArrayList<CompanionVO> companionsInvitation = (ArrayList<CompanionVO>)data;
                getActivityInterface().setCompanionsInvintation(companionsInvitation);
                addInvitationsToPanding(companionsInvitation);
                /*if(isPending){
                    pendingCompanions(companionsInvitation);
                }*/

                emptyCompanionsView();

                searchListAdapter.updateItems(companionsVOPending);

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }



    private void getCompanions(){
        if(search_view != null) {
            search_view.setQuery("", false);
            searchIsOpen = false;
            closeSearchBar();
        }

        ConnectionManager.getInstance(getActivity()).getCompanions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                ArrayList<CompanionVO> companions =(ArrayList<CompanionVO>)data;
                getActivityInterface().setCompanions(companions);
                if(isPending){
                    pendingCompanions(companions);
                    getCompanionsInvitation();
                }else{
                    acceptedCompanions(companions);
                    emptyCompanionsView();
                }
                searchListAdapter.updateItems(companionsVOPending);

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    public void confirmCompanion(String companion_id){
        ConnectionManager.getInstance(getActivity()).confirmCompanion(companion_id, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                getCompanions();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    public void deleteComapanion(final String companion_id){

        ConnectionManager.getInstance(getActivity()).deleteUserCompanion(companion_id, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                for(CompanionVO companionVO : companionsVOPending){
                    if(companionVO.getmCompanionid().equals(companion_id)){
                        companionsVOPending.remove(companionVO);
                        break;
                    }
                }
                getCompanions();
            }

            @Override
            public void onError(Object data) {
              ErrorMessage(data);
            }
        });
    }

    private void addTravelCompanion() {
        HGBUtility.showAlertPopUp(getActivity(), companion_editTextDialog, popup_companion_new, getResources().getString(R.string.component_invite)
                ,getResources().getString(R.string.save_button),
                new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        String[] parts = inputItem.split("&");
                        ConnectionManager.getInstance(getActivity()).postCompanions("","",inputItem, new ConnectionManager.ServerRequestListener() {
                            @Override
                            public void onSuccess(Object data) {
                                getCompanions();
                            }

                            @Override
                            public void onError(Object data) {
                                ErrorMessage(data);
                            }
                        });
                    }

                    @Override
                    public void itemCanceled() {

                    }
                });

    }


/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }*/

    private boolean searchIsOpen = false;

    @Override
    public boolean onQueryTextChange(String query) {
      /*  if(mCurrItemsSearchList == null){ //list empty
            searchIsOpen = false;
            return false;
        }*/

       // searchRecyclerView.setVisibility(View.VISIBLE);
        companion_search_not_found.setVisibility(View.GONE);
        companion_empty_view_travel_companion_ll.setVisibility(View.GONE);
        if(query.isEmpty()){
            searchIsOpen = false;
            searchListAdapter.updateItems(mCurrItemsSearchList);
            searchListAdapter.animateTo(mCurrItemsSearchList);
            searchListAdapter.notifyDataSetChanged();
            searchRecyclerView.setVisibility(View.INVISIBLE);
            companion_search_new_companion_view.setVisibility(View.VISIBLE);

        }else {
            searchRecyclerView.setVisibility(View.VISIBLE);
            ConnectionManager.getInstance(getActivity()).getCompanionsForSearch(query, new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {

                    CompanionsSearchItemsVO mItemsList = (CompanionsSearchItemsVO) data;
                    searchCompanionsVO = mItemsList.getmNodes();
                    searchListAdapter.updateItems(mItemsList.getmNodes());
                    //searchListAdapter.animateTo(mItemsList.getmNodes());
                    searchListAdapter.notifyDataSetChanged();
                    searchIsOpen = true;
                    companion_search_new_companion_view.setVisibility(View.GONE);
                    if(searchCompanionsVO.isEmpty()){
                        searchRecyclerView.setVisibility(View.GONE);
                        companion_search_not_found.setVisibility(View.VISIBLE);
                    }
                    //searchRecyclerView.scrollToPosition(0);
                }

                @Override
                public void onError(Object data) {
                    ErrorMessage(data);
                }
            });


        }


        // Here is where we are going to implement our filter logic
      /*  final List<CompanionVO> filteredModelList = filter(mCurrItemsSearchList, query);
        searchListAdapter.animateTo(filteredModelList);
        searchRecyclerView.scrollToPosition(0);*/
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
