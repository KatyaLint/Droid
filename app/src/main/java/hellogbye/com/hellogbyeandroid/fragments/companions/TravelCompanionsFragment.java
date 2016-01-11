package hellogbye.com.hellogbyeandroid.fragments.companions;

import android.app.Fragment;
import android.os.Bundle;
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
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.companion.CompanionAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;

/**
 * Created by arisprung on 8/17/15.
 */
public class TravelCompanionsFragment extends HGBAbtsractFragment implements SearchView.OnQueryTextListener{

    private SearchView mSearchView;
    private RecyclerView searchRecyclerView;
    private ArrayList<CompanionVO> companionsVO;
    private CompanionAdapter searchListAdapter;
    private List<CompanionVO> itemCompanionTemp = new ArrayList<>();


    public TravelCompanionsFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new TravelCompanionsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ConnectionManager.getInstance(getActivity()).getCompanions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<CompanionVO> companions =(ArrayList<CompanionVO>)data;
                companionsVO = companions;
                getActivityInterface().setCompanions(companions);
                searchListAdapter.updateItems(companions);
                searchListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });



//        PreferencesSettingsSearchCheckListAdapter searchAdapter =  new PreferencesSettingsSearchCheckListAdapter(getActivity(), myAccountAttribute.getAttributesVOs());

        View rootView = inflater.inflate(R.layout.companion_search_list, container, false);
        int i = getArguments().getInt(HGBConstants.ARG_NAV_NUMBER);
       // String strFrag = getResources().getStringArray(R.array.nav_draw_array)[i];
//        String strFrag = ToolBarNavEnum.getNavNameByPosition(i);
//
//        TextView textView = (TextView)rootView.findViewById(R.id.text);
//        textView.setText(strFrag);
        searchListInitialization(rootView);
     //   companions = getActivityInterface().getCompanions();
        searchListAdapter = new CompanionAdapter(getActivityInterface().getCompanions());
        searchRecyclerView.setAdapter(searchListAdapter);

        searchListAdapter.SetOnItemClickListener(new CompanionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final String guid, final String position) {
                for(CompanionVO companion:companionsVO){
                    if(companion.getmCompanionid().equals(guid)){
                        Bundle args = new Bundle();
                        args.putString("user_id", guid);
                        getActivityInterface().goToFragment(ToolBarNavEnum.COMPANIONS_DETAILS.getNavNumber(),args);
                    }
                }


//                for (SettingsAttributesVO accountAttribute: accountAttributesTemp){
//                    if(accountAttribute.getmId().equals(guid)){
//                        List<SettingsValuesVO> myAccountAttributeVO = myAccountAttribute.getAttributesVOs();
//                        int intRank = myAccountAttributeVO.size()+1;
//                        SettingsValuesVO valuesVO = new SettingsValuesVO(accountAttribute.getmId(),accountAttribute.getmName(),accountAttribute.getmDescription(),""+intRank);
//                        myAccountAttribute.getAttributesVOs().add(valuesVO);
//                        //  changedAccountAttributes.add(valuesVO);
//                        removeFromSearchList(guid);
//
//
//                        preferenceSettingsListAdapter.notifyDataSetChanged();
//                        break;
//                    }
//                }

            }
        });


    //    getActivity().setTitle(strFrag);
        return rootView;
    }


    private void searchListInitialization(View rootView){
        mSearchView = (SearchView)rootView.findViewById(R.id.companion_search_view);
        // mSearchView.setVisibility(View.GONE);
        setupSearchView();
        searchRecyclerView = (RecyclerView) rootView.findViewById(R.id.companion_search_list);
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // 5. set item animator to DefaultAnimator
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint(getString(R.string.settings_search_hunt));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);;
    }


//    private void addToSearchList(CompanionVO item ){
//
//        itemCompanionTemp.add(item);
//        searchListAdapter.updateItems(itemCompanionTemp);
//    }

    @Override
    public boolean onQueryTextChange(String query) {

        // Here is where we are going to implement our filter logic
        final List<CompanionVO> filteredModelList = filter(companionsVO, query);
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
            final String text = model.getCampanionUserProfile().getmFirstName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}

