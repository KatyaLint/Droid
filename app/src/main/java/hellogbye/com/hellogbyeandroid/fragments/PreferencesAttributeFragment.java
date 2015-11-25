package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import java.util.Set;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsAirlineCarriersAdapter;
import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsAttributeAdapter;
import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsFlightTabsAdapter;
import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsListAdapter;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;

/**
* Created by nyawka on 11/5/15.
*/
public class PreferencesAttributeFragment extends HGBAbtsractFragment implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private DynamicListView mDynamicListView;
    private RecyclerView hotelRecyclerView;
    private PreferenceSettingsAirlineCarriersAdapter preferenceSettingsAirlineCarriersAdapter;
    private List<SettingsAttributesVO> accountAttributes;

    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferencesAttributeFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.settings_drag_and_drop_list, container, false);

        mSearchView = (SearchView)rootView.findViewById(R.id.settings_search);
        mSearchView.setVisibility(View.GONE);
        setupSearchView();

        hotelRecyclerView = (RecyclerView) rootView.findViewById(R.id.settings_search_list);
        hotelRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // 5. set item animator to DefaultAnimator
        hotelRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        hotelRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



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


     SettingsAttributesVO myAccountAttribute = getMyAcountAttributes();
      final  PreferenceSettingsAttributeAdapter preferenceSettingsListAdapter = new PreferenceSettingsAttributeAdapter(getActivity(), myAccountAttribute.getAttributesVOs());
       // preferenceSettingsListAdapter.addAll(myAccountAttribute.getAttributesVOs());

        accountAttributes = getCorrectAttribute();

        preferenceSettingsAirlineCarriersAdapter = new PreferenceSettingsAirlineCarriersAdapter(accountAttributes);
        hotelRecyclerView.setAdapter(preferenceSettingsAirlineCarriersAdapter);

        preferenceSettingsAirlineCarriersAdapter.SetOnItemClickListener(new PreferenceSettingsFlightTabsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String guid) {
               // switchBetweenOptions(guid);
            }
        });

        mDynamicListView.setAdapter(preferenceSettingsListAdapter);

        mDynamicListView.enableSwipeToDismiss(
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            preferenceSettingsListAdapter.remove(position);
                        }
                    }
                }
        );

        return rootView;

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
            case "5":
                accountAttributes = getActivityInterface().getAccountSettingsFlightStopAttributes();
                mDynamicListView.setVisibility(View.GONE);
                mSearchView.setVisibility(View.GONE);
                break;
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
        final List<SettingsAttributesVO> filteredModelList = filter(accountAttributes, query);
        preferenceSettingsAirlineCarriersAdapter.animateTo(filteredModelList);
        hotelRecyclerView.scrollToPosition(0);
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



