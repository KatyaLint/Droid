package hellogbye.com.hellogbyeandroid.fragments.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsRadioButtonAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 3/22/16.
 */
public class PreferenceSettingsEmailFragment extends HGBAbstractFragment {

    private DynamicListView mRecyclerView;
    private List<AccountDefaultSettingsVO> accountDefaultSettings;
    private PreferencesSettingsRadioButtonAdapter preferenceSettingsListAdapter;
    private View promptsView;
    private String accountAttributeCheckedID = "";
    private String accountAttributeCheckedFirstID = "";
    private boolean noBack;
    private FontEditTextView settings_email_edit;
    private FontTextView settings_remove_email_address;
    private String personalEmail = null;


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

       // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        View rootView = inflater.inflate(R.layout.account_settings_user_emails, container, false);

        Bundle args = getArguments();
        //may be should be id, and not email
        personalEmail = args.getString(HGBConstants.PERSONAL_INFO_EMAIL);

        settings_email_edit = (FontEditTextView)rootView.findViewById(R.id.settings_email_edit);

        settings_remove_email_address = (FontTextView)rootView.findViewById(R.id.settings_remove_email_address);

        if(personalEmail != null && !personalEmail.isEmpty()){
            settings_email_edit.setText(personalEmail);
            settings_email_edit.setClickable(false);
            settings_email_edit.setFocusable(false);


            settings_remove_email_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectionManager.getInstance(getActivity()).deleteUserProfileAccountsWithEmail(personalEmail,new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {

                        }

                        @Override
                        public void onError(Object data) {
                            ErrorMessage(data);

                        }
                    });
                }
            });

        }else{
            settings_remove_email_address.setVisibility(View.GONE);
        }



        mRecyclerView = (DynamicListView) rootView.findViewById(R.id.settings_choose_profile);

        preferenceSettingsListAdapter = new PreferencesSettingsRadioButtonAdapter(getActivity(), accountDefaultSettings);
        preferenceSettingsListAdapter.setSelectedRadioButtonListener(new PreferenceSettingsFragment.ListRadioButtonClicked(){

            @Override
            public void clickedItem(int selectedPosition) {
                AccountDefaultSettingsVO selected = accountDefaultSettings.get(selectedPosition);
                selected.setChecked(true);
                accountAttributeCheckedID = selected.getmId();

                //radioButtonSelected = selectedPosition;
            }
        });

        //preferenceSettingsListAdapter = new PreferencesSettingsPreferencesCheckAdapter(getActivity(), accountDefaultSettings);
       // preferenceSettingsListAdapter.setEditMode(true);
        mRecyclerView.setAdapter(preferenceSettingsListAdapter);
     //   preferenceSettingsListAdapter.setClickedLineCB(listLineClicked);

        LayoutInflater li = LayoutInflater.from(getActivity());
        promptsView = li.inflate(R.layout.settings_save_popup, null);


        noBack = false;

        backOnListClicked();

     /*   ((MainActivityBottomTabs) getActivity()).setOnSavePreferencesButtonClicked(new PreferencesSettingsMainClass.saveButtonClicked(){
            @Override
            public void onSaveClicked() {
                getActivity().onBackPressed();
            }
        });*/


        getUserProfiles();

        return rootView;
    }


    private void sendNewEmailToServer(final String newEmail) {
        //http://cnc.hellogbye.com/cnc/rest/UserProfile/Accounts

        LayoutInflater li = LayoutInflater.from(getActivity());
        promptsView = li.inflate(R.layout.popup_alert_layout, null);

        ConnectionManager.getInstance(getActivity()).postUserProfileAccountsWithEmail(newEmail, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                String titleString = newEmail + " " + getResources().getString(R.string.component_confirmation_email);
                HGBUtility.showAlertPopUpOneButton(getActivity(), null, promptsView, titleString,
                        new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                            }
                            @Override
                            public void itemCanceled() {
                            }
                        }, true);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    public void backOnListClicked() {

        ((MainActivityBottomTabs) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {
                if(noBack){
                    return;
                }
                onSavePopup();
            }
        });
    }


    private void getUserProfiles() {
        ConnectionManager.getInstance(getActivity()).getPreferenceProfiles(new ConnectionManager.ServerRequestListener() {
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

        if(personalEmail == null){ //on add new email screen
            String newUserEmail = settings_email_edit.getText().toString();
            boolean isEmailValid = HGBUtility.isValidEmail(newUserEmail);
            if(isEmailValid){
                saveDataAlert();
            }
        }
        else if(!accountAttributeCheckedID.isEmpty() && !accountAttributeCheckedID.equals(accountAttributeCheckedFirstID)){ //preferences were changed
            saveDataAlert();
        }
    }


    private void putNewPreferencesForUser(){
        ConnectionManager.getInstance(getActivity()).putAccountsPreferences(personalEmail, accountAttributeCheckedID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                if(getActivityInterface().getPersonalUserInformation().getUserEmailLogIn().equals(personalEmail)){
                    getActivityInterface().getPersonalUserInformation().setmTravelPreferencesProfileId(accountAttributeCheckedID);
                }

               // ((MainActivity)getActivity()).getAccountsProfiles();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void saveDataAlert(){
        HGBUtility.showAlertPopUp(getActivity(), null, promptsView,
                getResources().getString(R.string.preferences_save_pop_up),
                getResources().getString(R.string.save_button),
                new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        if(personalEmail ==  null){
                            String newUserEmail = settings_email_edit.getText().toString();

                            sendNewEmailToServer(newUserEmail);
                        }else{
                            putNewPreferencesForUser();
                        }
                    }

                    @Override
                    public void itemCanceled() {
                    }
                });
    }

    private void setFirstProfileChecked(){

        String id = null;
        //personalEmail
        ArrayList<AccountsVO> accounts = getActivityInterface().getAccounts();
        for(AccountsVO account:accounts){
            if(account.getEmail().equals(personalEmail)){
                id = account.getTravelpreferenceprofile().getmId();
                break;
            }
        }
       /* if(id == null){
            return;
        }*/
      //  String id = account.getTravelpreferenceprofile().getId();
        for(AccountDefaultSettingsVO accountAttribute : accountDefaultSettings) {
            if (accountAttribute.getmId().equals(id)) {
                accountAttribute.setChecked(true);
                accountAttributeCheckedFirstID = accountAttribute.getmId();
                preferenceSettingsListAdapter.selectedItemID(accountAttributeCheckedFirstID);
                break;
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

                    preferenceSettingsListAdapter.selectedItemID(accountAttributeCheckedID);
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
        super.onDestroyView();
    }
}
