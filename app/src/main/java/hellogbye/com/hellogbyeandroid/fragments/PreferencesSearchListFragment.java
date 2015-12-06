package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


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
import android.widget.AdapterView;
import android.widget.SearchView;


import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsAirlineCarriersAdapter;
import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsAttributeAdapter;
import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsFlightTabsAdapter;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;

/**
* Created by nyawka on 11/5/15.
*/
public class PreferencesSearchListFragment extends PreferencesSettingsMainClass implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private DynamicListView mDynamicListView;
    private RecyclerView searchRecyclerView;
    private PreferenceSettingsAirlineCarriersAdapter searchListAdapter;

    private SettingsAttributesVO myAccountAttribute;
    private PreferenceSettingsAttributeAdapter preferenceSettingsListAdapter;
    private String strJson;
    private String strType;
    private List<SettingsValuesVO> myAccountAttributeList;

    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferencesSearchListFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }


    private void searchListInitialization(View rootView){
        mSearchView = (SearchView)rootView.findViewById(R.id.settings_search);
        mSearchView.setVisibility(View.GONE);
        setupSearchView();
        searchRecyclerView = (RecyclerView) rootView.findViewById(R.id.settings_search_list);
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // 5. set item animator to DefaultAnimator
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    private void dragDropListInitialization(View rootView){
        mDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_drag_drop_list);
        mDynamicListView.setVisibility(View.GONE);
        mDynamicListView.enableDragAndDrop();

        mDynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {
                        mDynamicListView.startDragging(position);
                        return true;
                    }
                }
        );

        myAccountAttribute = getMyAcountAttributes();


        preferenceSettingsListAdapter = new PreferenceSettingsAttributeAdapter(getActivity(), myAccountAttribute.getAttributesVOs());
        mDynamicListView.setAdapter(preferenceSettingsListAdapter);

        mDynamicListView.enableSwipeToDismiss(
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {

                        for (int position : reverseSortedPositions) {

                            SettingsValuesVO item = preferenceSettingsListAdapter.remove(position);
                            addToSearchList(item);

                        }

                        List<SettingsValuesVO> myAccountVOs = myAccountAttribute.getAttributesVOs();

                    }
                }
        );
    }


    List<SettingsAttributesVO> accountAttributesTemp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.settings_search_drag_list, container, false);
        Bundle args = getArguments();
        if (args != null) {
            strJson = args.getString("setting_att_id");
            strType = args.getString("setting_type");
        }
        noBack = false;

        searchListInitialization(rootView);

        dragDropListInitialization(rootView);

        List<SettingsAttributesVO> accountAttributes = getCorrectAttribute();


        accountAttributesTemp = new ArrayList<>(accountAttributes);
        myAccountAttributeList = myAccountAttribute.getAttributesVOs();

        for (SettingsValuesVO myAccount : myAccountAttributeList) {
            for (SettingsAttributesVO accountAttribute : accountAttributes) {
                if (accountAttribute.getmId().equals(myAccount.getmID())) {
                    accountAttributesTemp.remove(accountAttribute);
                }
            }
        }

        searchListAdapter = new PreferenceSettingsAirlineCarriersAdapter(accountAttributesTemp);
        searchRecyclerView.setAdapter(searchListAdapter);
        searchListAdapter.SetOnItemClickListener(new PreferenceSettingsFlightTabsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String guid, final String position) {

                for (SettingsAttributesVO accountAttribute: accountAttributesTemp){
                    if(accountAttribute.getmId().equals(guid)){
                        List<SettingsValuesVO> myAccountAttributeVO = myAccountAttribute.getAttributesVOs();
                        int intRank = myAccountAttributeVO.size()+1;
                        SettingsValuesVO valuesVO = new SettingsValuesVO(accountAttribute.getmId(),accountAttribute.getmName(),accountAttribute.getmDescription(),""+intRank);
                        myAccountAttribute.getAttributesVOs().add(valuesVO);
                      //  changedAccountAttributes.add(valuesVO);
                        removeFromSearchList(guid);


                        preferenceSettingsListAdapter.notifyDataSetChanged();
                        break;
                    }
                }

            }
        });

        backOnListClicked(strType,strJson,myAccountAttribute.getAttributesVOs());

//        ((MainActivity) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
//            public void doBack() {
//
//                  String guid = getSettingGuidSelected();
//                List<SettingsValuesVO> accountAttributesVO = myAccountAttribute.getAttributesVOs();
//                        ConnectionManager.getInstance(getActivity()).putAttributesValues(
//                                strJson, strType, guid, accountAttributesVO, new ConnectionManager.ServerRequestListener() {
//                                    @Override
//                                    public void onSuccess(Object data) {
//                                    }
//                                    @Override
//                                    public void onError(Object data) {
//                                        HGBErrorHelper errorHelper = new HGBErrorHelper();
//                                    }
//                                });
//                }
//            //}
//        });
//
//
        return rootView;
//
    }

    private void addToSearchList(SettingsValuesVO item ){
        SettingsAttributesVO settingsAttributesVO = new SettingsAttributesVO();
        settingsAttributesVO.setmId(item.getmID());
        settingsAttributesVO.setmDescription(item.getmDescription());
        settingsAttributesVO.setmName(item.getmName());
        settingsAttributesVO.setmRank(item.getmRank());
        accountAttributesTemp.add(settingsAttributesVO);
        searchListAdapter.updateItems(accountAttributesTemp);
    }

    private void removeFromSearchList(String itemId){
        for(SettingsAttributesVO accountAttribute:accountAttributesTemp){
            if(accountAttribute.getmId().equals(itemId)){
                accountAttributesTemp.remove(accountAttribute);
                break;
            }
        }
        searchListAdapter.updateItems(accountAttributesTemp);
        //searchListAdapter.notifyDataSetChanged();
    }


    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint(getString(R.string.settings_search_hunt));
    }

    private List<SettingsAttributesVO> getCorrectAttribute(){
        String guid = getSettingGuidSelected();
        List<SettingsAttributesVO> accountAttributes = null;
        switch (guid){
//            case "5":
//                accountAttributes = getActivityInterface().getAccountSettingsFlightStopAttributes();
//                mDynamicListView.setVisibility(View.GONE);
//                mSearchView.setVisibility(View.GONE);
//                break;
            case "1":
                accountAttributes = getActivityInterface().getAccountSettingsFlightCarrierAttributes();
                mDynamicListView.setVisibility(View.VISIBLE);
                mSearchView.setVisibility(View.VISIBLE);
                break;

        }
        return accountAttributes;
    }


    private SettingsAttributesVO getMyAcountAttributes() {
        List<SettingsAttributeParamVO> myAttributes = getActivityInterface().getAccountSettingsAttribute();
        String guid = getSettingGuidSelected();
        for (SettingsAttributeParamVO attribute : myAttributes) {
            ArrayList<SettingsAttributesVO> attributes = attribute.getAttributesVOs();
            for (SettingsAttributesVO attributeVO : attributes) {
                if (attributeVO.getmId().equals(guid)) {
                    return attributeVO;
                }
            }
        }
        return null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);;
    }

    @Override
    public boolean onQueryTextChange(String query) {

        // Here is where we are going to implement our filter logic
        final List<SettingsAttributesVO> filteredModelList = filter(accountAttributesTemp, query);
        searchListAdapter.animateTo(filteredModelList);
        searchRecyclerView.scrollToPosition(0);
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<SettingsAttributesVO> filter(List<SettingsAttributesVO> models, String query) {
        query = query.toLowerCase();

        final List<SettingsAttributesVO> filteredModelList = new ArrayList<>();
        for (SettingsAttributesVO model : models) {
            final String text = model.getmName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}



