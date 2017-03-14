package hellogbye.com.hellogbyeandroid.fragments.preferences;


import android.os.Bundle;


import android.support.annotation.Nullable;


import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import android.widget.TextView;


import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferenceSettingsAirlineCarriersAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferenceSettingsFlightTabsAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsSearchCheckListAdapter;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
* Created by nyawka on 11/5/15.
*/
public class PreferencesSearchListFragment extends PreferencesSettingsMainClass implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private DynamicListView mDynamicListView;
    private RecyclerView searchRecyclerView;
    private PreferenceSettingsAirlineCarriersAdapter searchListAdapter;
    private PreferencesSettingsSearchCheckListAdapter preferenceSettingsListAdapter;

   // private String strJson;
//    private String strType;
  //  private List<SettingsValuesVO> myAccountAttributeList;

//    private FontTextView my_settings;
  //  private FontTextView popular_settings;
    private FontTextView settings_item_title;
    private FontTextView settings_item_text;

    private LinearLayout settings_empty_view_layout;
    private String strTitleName;


    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferencesSearchListFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }


    private void searchListInitialization(View rootView){
        mSearchView = (SearchView)rootView.findViewById(R.id.settings_search);

     /*   mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRecyclerView.setVisibility(View.VISIBLE);
                mDynamicListView.setVisibility(View.GONE);

            }
        });*/



       // mSearchView.setVisibility(View.GONE);
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
     //   mDynamicListView.setVisibility(View.GONE);
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
        preferenceSettingsListAdapter = new PreferencesSettingsSearchCheckListAdapter(getActivity(), myAccountAttribute.getAttributesVOs());
        mDynamicListView.setAdapter(preferenceSettingsListAdapter);
        mDynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                FontTextView settings_flight_title = (FontTextView) view.findViewById(R.id.settings_search_check_name);
                String id = settings_flight_title.getTag().toString();
                SettingsValuesVO item = preferenceSettingsListAdapter.remove(position);

                if(preferenceSettingsListAdapter.isEmpty()){
                    settings_empty_view_layout.setVisibility(View.VISIBLE);
                }else{
                    settings_empty_view_layout.setVisibility(View.GONE);
                }

                addToSearchList(item);
                //remove from list
            }});
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.settings_search_drag_list, container, false);
        Bundle args = getArguments();
        if (args != null) {
            strId = args.getString(HGBConstants.BUNDLE_SETTINGS_ATT_ID);

            strType = args.getString(HGBConstants.BUNDLE_SETTINGS_TYPE);
            String strTitleName = args.getString(HGBConstants.BUNDLE_SETTINGS_TITLE_NAME);
            FontTextView titleBar = ((MainActivityBottomTabs) getActivity()).getTitleBar();
            titleBar.setText(strTitleName);

        }



        noBack = false;
    //    my_settings = (FontTextView)rootView.findViewById(R.id.my_flights_settings);
       // popular_settings = (FontTextView)rootView.findViewById(R.id.popular_settings);

        settings_item_title = (FontTextView)rootView.findViewById(R.id.settings_item_title);
        settings_item_text = (FontTextView)rootView.findViewById(R.id.settings_item_text);

        searchListInitialization(rootView);

        dragDropListInitialization(rootView);

        List<SettingsAttributesVO> accountAttributes = getCorrectAttribute();


        accountAttributesTemp = new ArrayList<>(accountAttributes);

        firstItems = myAccountAttribute.getAttributesVOs();

        selectedItem = new ArrayList<>( );

        for (SettingsValuesVO myAccount : firstItems) {
            for (SettingsAttributesVO accountAttribute : accountAttributes) {
                if (accountAttribute.getmId().equals(myAccount.getmID())) {
                   // accountAttributesTemp.remove(accountAttribute);
                    accountAttribute.setChecked(true);

                }
            }
        }


        searchListAdapter = new PreferenceSettingsAirlineCarriersAdapter(accountAttributesTemp);
        searchRecyclerView.setAdapter(searchListAdapter);
        searchListAdapter.SetOnItemClickListener(new PreferenceSettingsFlightTabsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String guid, final String position) {

                if(myAccountAttribute.getAttributesVOs().size() >= MAX_CHOOSEN_NUMBER){
                    maxChoosenNumberAlert();
                    return;
                }



                if(!itemAlreadyAdded(guid)){

                    for (SettingsAttributesVO accountAttribute: accountAttributesTemp){
                        if(accountAttribute.getmId().equals(guid)){
                            List<SettingsValuesVO> myAccountAttributeVO = myAccountAttribute.getAttributesVOs();

                            int intRank = myAccountAttributeVO.size()+1;
                            SettingsValuesVO valuesVO = new SettingsValuesVO(accountAttribute.getmId(),accountAttribute.getmName(),accountAttribute.getmDescription(),""+intRank);
                            myAccountAttribute.getAttributesVOs().add(valuesVO);
                            selectedItem.add(valuesVO);

                            break;
                        }
                    }
                }
                removeFromSearchList(guid);
                preferenceSettingsListAdapter.notifyDataSetChanged();

            }

        });

        backOnListClicked();


        searchRecyclerView.setVisibility(View.GONE);
        mDynamicListView.setVisibility(View.VISIBLE);


        settings_empty_view_layout = (LinearLayout)rootView.findViewById(R.id.settings_empty_view_layout);
        TextView settings_empty_view_text = (TextView) rootView.findViewById(R.id.settings_empty_view_text);
        if(strType.equals("FLT")){
            settings_empty_view_text.setText(getString(R.string.settings_empty_view_aircraft));
        }else{
            settings_empty_view_text.setText(getString(R.string.settings_empty_view_hotel));
        }
        if(firstItems.isEmpty()){
            settings_empty_view_layout.setVisibility(View.VISIBLE);
        }

        return rootView;

    }

    private void maxChoosenNumberAlert() {
        LayoutInflater li = LayoutInflater.from(getActivity());
        final View promptsView = li.inflate(R.layout.popup_layout_log_out, null);
        final EditText input = (EditText) promptsView
                .findViewById(R.id.companion_editTextDialog);
        input.setVisibility(View.GONE);
        final FontTextView text = (FontTextView) promptsView
                .findViewById(R.id.component_popup_logout_text);


        text.setVisibility(View.VISIBLE);
        text.setText(getResources().getString(R.string.settings_more_then_allowed_selected));

        HGBUtility.showAlertPopUpOneButton(getActivity(), input, promptsView, getResources().getString(R.string.settings_more_then_allowed_selected_title),
                new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {

                    }

                    @Override
                    public void itemCanceled() {

                    }
                });




    }


    private boolean itemAlreadyAdded(String guid){
        List<SettingsValuesVO> attributes = myAccountAttribute.getAttributesVOs();
        for(SettingsValuesVO attribute :attributes){
            if(attribute.getmID().equals(guid)){
                return true;
            }
        }
        return false;
    }
    private void addToSearchList(SettingsValuesVO item ){
     /*   SettingsAttributesVO settingsAttributesVO = new SettingsAttributesVO();
        settingsAttributesVO.setmId(item.getmID());
        settingsAttributesVO.setmDescription(item.getmDescription());
        settingsAttributesVO.setmName(item.getmName());
        settingsAttributesVO.setmRank(item.getmRank());
        settingsAttributesVO.setChecked(false);
        accountAttributesTemp.add(settingsAttributesVO);
        */

        for (SettingsAttributesVO settingsAttributeVO : accountAttributesTemp){
            if(settingsAttributeVO.getmId().equals(item.getmID())){
                settingsAttributeVO.setChecked(false);
            }
        }


        searchListAdapter.updateItems(accountAttributesTemp);
    }

    private void removeFromSearchList(String itemId){
        for(SettingsAttributesVO accountAttribute:accountAttributesTemp){
            if(accountAttribute.getmId().equals(itemId)){
                if(accountAttribute.isChecked()) {
                    accountAttribute.setChecked(false);
                }else{
                    accountAttribute.setChecked(true);
                }
           //     accountAttributesTemp.remove(accountAttribute);
                break;
            }
        }
        //searchListAdapter.updateItems(accountAttributesTemp);
        searchListAdapter.notifyDataSetChanged();
    }


    private void setupSearchView() {
      /*  mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);*/


        mSearchView.setIconifiedByDefault(false);

        mSearchView.setQueryHint(getString(R.string.settings_search_hunt));
        mSearchView.setOnQueryTextListener(this);
    }




    private List<SettingsAttributesVO> getCorrectAttribute(){
        String guid = getSettingGuidSelected();
        List<SettingsAttributesVO> accountAttributes = null;
        mDynamicListView.setVisibility(View.VISIBLE);
        mSearchView.setVisibility(View.VISIBLE);
        String titleName;
        switch (guid){
            case "9":
                accountAttributes = getActivityInterface().getAccountSettingsHotelChainAttributes();
          //      my_settings.setText(R.string.my_settings_hotel);
        //        popular_settings.setText(R.string.popular_settings_hotel);
                settings_item_title.setText(R.string.preferences_prefered_hotel_chain);
                settings_item_text.setText(R.string.preferences_prefered_hotel_favorite);
                break;
            case "2":
                accountAttributes = getActivityInterface().getAccountSettingsFlightAircraftAttributes();
           //     my_settings.setText(R.string.my_settings_flight);
         //       popular_settings.setText(R.string.popular_settings_flight);
                settings_item_title.setText(R.string.preferences_prefered_aircraft);
                settings_item_text.setText(R.string.preferences_prefered_aircraft_favorite);
                break;
            case "1":
                accountAttributes = getActivityInterface().getAccountSettingsFlightCarrierAttributes();
      //          my_settings.setText(R.string.my_settings_flight);
          //      popular_settings.setText(R.string.popular_settings_flight);

                settings_item_title.setText(R.string.preferences_prefered_flight_carrier);
                settings_item_text.setText(R.string.preferences_prefered_flight_carrier_favorite);
                break;

        }
       /* FontTextView titleBar = ((MainActivityBottomTabs) getActivity()).getTitleBar();
        titleBar.setText(titleName);*/
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
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {

        // Here is where we are going to implement our filter logic
        final List<SettingsAttributesVO> filteredModelList = filter(accountAttributesTemp, query);
        searchListAdapter.animateTo(filteredModelList);
        searchListAdapter.notifyDataSetChanged();
        searchRecyclerView.scrollToPosition(0);
        searchRecyclerView.setVisibility(View.VISIBLE);
        mDynamicListView.setVisibility(View.GONE);
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



