package hellogbye.com.hellogbyeandroid.fragments.preferences.preferencespopup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.HGBFlowInterface;
import hellogbye.com.hellogbyeandroid.activities.HGBMainInterface;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsPreferencesCheckAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.profiles.DefaultsProfilesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 9/28/16.
 */

public class UserProfilePreferences extends HGBAbstractFragment {

    private AlertDialog selectDefaultProfileDialog;
    private int radioButtonSelected = -1;
    private PreferencesSettingsPreferencesCheckAdapter mRadioPreferencesAdapter;
    private ArrayList<DefaultsProfilesVO> accountDefaultSettings;
    private boolean isDefaultProfile = false;


    public void getAccountsProfiles(Activity context,final HGBMainInterface activityInterface){
        ConnectionManager.getInstance(context).getUserProfileAccounts(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<AccountsVO> accounts = ( ArrayList<AccountsVO> )data;
                activityInterface.setAccounts(accounts);
               // ((MainActivityBottomTabs) getActivity()).editProfileTypeMainToolBar();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    public void getUserDefaultSettings(final Activity context, final HGBFlowInterface flowInterface,final HGBMainInterface activityInterface){
        // getPreferenceProfiles(final ServerRequestListener listener)
        isDefaultProfile = true;
        ConnectionManager.getInstance(context).getDefaultProfiles(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    accountDefaultSettings = (ArrayList<DefaultsProfilesVO>) data;
                    //     List<AccountDefaultSettingsVO> accountDefaultSettings = (List<AccountDefaultSettingsVO>) data;
                    accountDefaultSettings.remove(accountDefaultSettings.size()-1);
                    profilesDialog(accountDefaultSettings, context, flowInterface, activityInterface);
                }
            }
            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }



    public void getUserSettings(final Activity context, final HGBFlowInterface flowInterface,final HGBMainInterface activityInterface){
        // getPreferenceProfiles(final ServerRequestListener listener)
        isDefaultProfile = false;
        ConnectionManager.getInstance(context).getPreferenceProfiles(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    accountDefaultSettings = (ArrayList<DefaultsProfilesVO>) data;
                    //     List<AccountDefaultSettingsVO> accountDefaultSettings = (List<AccountDefaultSettingsVO>) data;
                    profilesDialog(accountDefaultSettings, context, flowInterface, activityInterface);
                }
            }
            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    private void profilesDialog(ArrayList<DefaultsProfilesVO> userProfileVOs, final Activity activity, final HGBFlowInterface flowInterface, final HGBMainInterface activityInterface) {
        LayoutInflater li = LayoutInflater.from(activity);
        View promptsView = li.inflate(R.layout.popup_custom_title, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setCustomTitle(promptsView);



        //  UserProfilesListAdapter adapter = new UserProfilesListAdapter(userProfileVOs, getContext());
       // ArrayList<AccountsVO> accounts = activityInterface.getAccounts();

        ArrayList<AccountDefaultSettingsVO> accountAttributes = new ArrayList<>();
        for(DefaultsProfilesVO defaultsProfilesVO: userProfileVOs){
            AccountDefaultSettingsVO accountDefaultSettingsVO = new AccountDefaultSettingsVO();
            accountDefaultSettingsVO.setmId(defaultsProfilesVO.getId());
            String profileName = defaultsProfilesVO.getProfilename();
            if(profileName == null){
                profileName = defaultsProfilesVO.getName();
            }
            accountDefaultSettingsVO.setmProfileName(profileName);
            accountAttributes.add(accountDefaultSettingsVO);
        }

        mRadioPreferencesAdapter = new PreferencesSettingsPreferencesCheckAdapter(activity, accountAttributes);

        View promptsViewTeest = li.inflate(R.layout.user_profile_popup_list_layout, null);
        ListView user_profile_popup_list_view = (ListView) promptsViewTeest.findViewById(R.id.user_profile_popup_list_view);
        user_profile_popup_list_view.setAdapter(mRadioPreferencesAdapter);

        LinearLayout manage_profile_ll = (LinearLayout)promptsViewTeest.findViewById(R.id.manage_profile_ll);
        manage_profile_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flowInterface.goToFragment(ToolBarNavEnum.TRAVEL_PREFERENCE.getNavNumber(), null);
                selectDefaultProfileDialog.dismiss();
            }
        });

        mRadioPreferencesAdapter.setClickedLineCB(new PreferenceSettingsFragment.ListLineClicked() {
            @Override
            public void clickedItem(String itemID) {


            }
        });

        mRadioPreferencesAdapter.setSelectedRadioButtonListener(new PreferenceSettingsFragment.ListRadioButtonClicked(){

            @Override
            public void clickedItem(int selectedPosition) {
                radioButtonSelected = selectedPosition;
            }
        });

        selectedRadioPreference(activity);


        dialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(radioButtonSelected != -1) {

                    if(isDefaultProfile){
                        final DefaultsProfilesVO selected = accountDefaultSettings.get(radioButtonSelected);
                        postDefaultProfile(String.valueOf(selected.getId()), selected.getName(), activity, activityInterface);
                    }else{
                        putAccoutToServer(activity, activityInterface);
                    }


                    selectDefaultProfileDialog.dismiss();
                }
            } });


        //Create alert dialog object via builder
        selectDefaultProfileDialog = dialogBuilder.create();
        selectDefaultProfileDialog.setView(promptsViewTeest);
        selectDefaultProfileDialog.setCancelable(false);
        selectDefaultProfileDialog.show();



        //  dialogBuilder.setView(promptsViewTeest);

    }

    private void postDefaultProfile(String profileId, String profileName, final Activity activity, final HGBMainInterface activityInterface) {
        ConnectionManager.getInstance(activity).postDefaultProfile(profileId, profileName, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    AccountDefaultSettingsVO accountDefault = (AccountDefaultSettingsVO) data;
                    putNewPreferencesForUser(activityInterface.getPersonalUserInformation().getUserEmailLogIn(), accountDefault.getmId(), activity);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });

    }

    private void putNewPreferencesForUser(final String userEmail, final String accountID,final Activity activity) {
        ConnectionManager.getInstance(activity).putAccountsPreferences(userEmail, accountID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                HGBPreferencesManager hgbPrefrenceManager = HGBPreferencesManager.getInstance(activity);
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, accountID);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void putAccoutToServer(final Activity activity,final HGBMainInterface activityInterface){
        final DefaultsProfilesVO selected = accountDefaultSettings.get(radioButtonSelected);

        String userEmail = activityInterface.getPersonalUserInformation().getUserEmailLogIn();

        ConnectionManager.getInstance(activity).putAccountsPreferences(userEmail, selected.getId(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                FontTextView my_trip_profile = ((MainActivityBottomTabs) activity).getMyTripProfile();
                my_trip_profile.setTag(selected.getId());
                //   FontTextView my_trip_profile = ((MainActivityBottomTabs) getActivity()).getMyTripProfile();
                //   my_trip_profile.setText(selected.getProfilename());
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void selectedRadioPreference(Activity activity){

        FontTextView my_trip_profile = ((MainActivityBottomTabs) activity).getMyTripProfile();

        if(my_trip_profile.getTag() == null){
            return;
        }
        String selectedTag = my_trip_profile.getTag().toString();
        mRadioPreferencesAdapter.selectedItemID(selectedTag);
    }


}
