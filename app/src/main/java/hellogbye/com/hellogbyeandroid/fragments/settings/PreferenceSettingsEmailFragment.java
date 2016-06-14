package hellogbye.com.hellogbyeandroid.fragments.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsPreferencesCheckAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesSearchListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesSettingsMainClass;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

/**
 * Created by nyawka on 3/22/16.
 */
public class PreferenceSettingsEmailFragment extends HGBAbstractFragment {

    private DynamicListView mRecyclerView;
    private List<AccountDefaultSettingsVO> accountDefaultSettings;
    private PreferencesSettingsPreferencesCheckAdapter preferenceSettingsListAdapter;
    private View promptsView;
    private String accountAttributeCheckedID;
    private String accountAttributeCheckedFirstID;
    private boolean noBack;


    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferenceSettingsEmailFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_user_emails, container, false);



        mRecyclerView = (DynamicListView) rootView.findViewById(R.id.settings_choose_profile);

        preferenceSettingsListAdapter = new PreferencesSettingsPreferencesCheckAdapter(getActivity(), accountDefaultSettings);
        preferenceSettingsListAdapter.setEditMode(true);
        mRecyclerView.setAdapter(preferenceSettingsListAdapter);
        preferenceSettingsListAdapter.setClickedLineCB(listLineClicked);

        LayoutInflater li = LayoutInflater.from(getActivity());
        promptsView = li.inflate(R.layout.settings_save_popup, null);


        noBack = false;

        backOnListClicked();

        ((MainActivity) getActivity()).setOnSavePreferencesButtonClicked(new PreferencesSettingsMainClass.saveButtonClicked(){
            @Override
            public void onSaveClicked() {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
        getUserProfiles();

        return rootView;
    }

    public void backOnListClicked() {

        ((MainActivity) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {
                if(noBack){
                    return;
                }
                onSavePopup();
            }
        });
    }


    private void getUserProfiles() {
        ConnectionManager.getInstance(getActivity()).getUserSettingsDefault(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    accountDefaultSettings = (List<AccountDefaultSettingsVO>) data;
                    setFirstProfileChecked();
                    preferenceSettingsListAdapter.addAll(accountDefaultSettings);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void onSavePopup(){


        if(accountAttributeCheckedID == null || accountAttributeCheckedID.isEmpty()
                || accountAttributeCheckedID.equals(accountAttributeCheckedFirstID)){
            return;
        }

        HGBUtility.showAlertPopUp(getActivity(), null, promptsView,
                getResources().getString(R.string.preferences_save_pop_up),
                getResources().getString(R.string.save_button),
                new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {

                        String userEmail = getActivityInterface().getAccounts().get(0).getEmail();
                        ConnectionManager.getInstance(getActivity()).putAccountsPreferences(userEmail, accountAttributeCheckedID, new ConnectionManager.ServerRequestListener() {
                            @Override
                            public void onSuccess(Object data) {
                                ((MainActivity)getActivity()).getAccountsProfiles();
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


    private void setFirstProfileChecked(){
        AccountsVO account = getActivityInterface().getAccounts().get(0);
        String id = account.getTravelpreferenceprofile().getId();
        for( AccountDefaultSettingsVO accountAttribute : accountDefaultSettings) {
            if (accountAttribute.getmId().equals(id)) {
                accountAttribute.setChecked(true);
                accountAttributeCheckedFirstID = accountAttribute.getmId();
            }
        }
    }


    private PreferenceSettingsFragment.ListLineClicked listLineClicked = new PreferenceSettingsFragment.ListLineClicked() {
        @Override
        public void clickedItem(String itemID) {
            adapterClicked(itemID);
        }
    };

    private void adapterClicked(String clickedItemID){
        if(preferenceSettingsListAdapter.getIsEditMode()){
            for( AccountDefaultSettingsVO accountAttribute : accountDefaultSettings) {
                if(accountAttribute.getmId().equals(clickedItemID)){
                    accountAttribute.setChecked(true);
                    accountAttributeCheckedID = accountAttribute.getmId();

                }else{
                    if(accountAttribute.isChecked()){
                        accountAttribute.setChecked(false);
                    }
                }

            }
            preferenceSettingsListAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onDestroyView() {
        noBack = true;
        super.onDetach();
    }
}
