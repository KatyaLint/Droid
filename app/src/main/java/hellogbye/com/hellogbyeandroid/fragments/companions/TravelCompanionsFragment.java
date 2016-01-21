package hellogbye.com.hellogbyeandroid.fragments.companions;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private View popup_companion_new;
    private EditText input;


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


        View rootView = inflater.inflate(R.layout.companion_search_list, container, false);

        searchListInitialization(rootView);

        searchListAdapter = new CompanionAdapter(getActivityInterface().getCompanions(), getActivity().getApplicationContext());
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
            }
        });


        popup_companion_new = inflater.inflate(R.layout.popup_layout_with_edit_text_new, null);
        input = (EditText) popup_companion_new.findViewById(R.id.companion_editTextDialog);


        LinearLayout companion_invite_companion = (LinearLayout) rootView.findViewById(R.id.companion_invite_ll);
        companion_invite_companion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addTravelCompanion();
            }
        });

        return rootView;
    }


    private void addTravelCompanion() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(false);
        alert.setTitle(R.string.component_invite_new_companion)
                .setView(popup_companion_new)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = input.getText().toString();

//                        if (newName.length() != 0) {
//                            popUpConnection(newName);
//                        }
                        input.setText("");
                        ((ViewGroup) popup_companion_new.getParent()).removeView(popup_companion_new);
                        IBinder token = input.getWindowToken();
                        ( (InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                        dialog.cancel();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        input.setText("");

                        ((ViewGroup) popup_companion_new.getParent()).removeView(popup_companion_new);
                        IBinder token = input.getWindowToken();
                        ( (InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                        dialog.cancel();
                    }
                })
                .create().show();
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

