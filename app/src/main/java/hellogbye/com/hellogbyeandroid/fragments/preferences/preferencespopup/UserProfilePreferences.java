package hellogbye.com.hellogbyeandroid.fragments.preferences.preferencespopup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.HGBFlowInterface;
import hellogbye.com.hellogbyeandroid.activities.HGBMainInterface;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsPreferencesCheckAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsRadioButtonAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.fragments.cnc.CNCSignalRFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.PersonalUserInformationVO;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.profiles.DefaultsProfilesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 9/28/16.
 */

public class UserProfilePreferences extends HGBAbstractFragment {

    private AlertDialog selectDefaultProfileDialog;
    private int radioButtonSelected = -1;
    private PreferencesSettingsRadioButtonAdapter mRadioPreferencesAdapter;
    private ArrayList<DefaultsProfilesVO> accountDefaultSettings;
    private boolean isDefaultProfile = false;
    private DefaultsProfilesVO notChoosenProfileVO;
    private CNCSignalRFragment.IProfileUpdated miProfileUpdated;
/*    private static UserProfilePreferences singleton = new UserProfilePreferences( );

    public UserProfilePreferences(){

    }


    public static UserProfilePreferences getInstance( ) {
        return singleton;
    }*/

    public String getActiveAccount(HGBMainInterface hgbMainInterface){
        String userPreferenceID = hgbMainInterface.getPersonalUserInformation().getmTravelPreferencesProfileId();

      //  ArrayList<AccountsVO> acountsProfiles = hgbMainInterface.getAccounts();

        List<DefaultsProfilesVO> accountDefaultSettings = hgbMainInterface.getDefaultsProfilesVOs();
        for(DefaultsProfilesVO defaultsProfilesVO : accountDefaultSettings){

            if(defaultsProfilesVO.getId().equals(userPreferenceID)){
                return defaultsProfilesVO.getProfilename();
               // hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileName(defaultsProfilesVO.getProfilename());
            }
        }

    /*    List<AccountDefaultSettingsVO> accountDefaultSettingsProfile = hgbMainInterface.getAccountDefaultSettingsVOs();
        if(accountDefaultSettingsProfile != null ) {

            for (AccountDefaultSettingsVO account : accountDefaultSettingsProfile) {
                String accountProfile = account.getmProfileName();

                if (accountProfile != null && account.getmId().equals(userPreferenceID)) {
                    String name = accountProfile;
                    System.out.println("Kate name getActiveAccount =" + name);
                    return name;
                }
            }
        }*/
        return null;
    }



    public void getAccountsProfiles(Activity context, final HGBMainInterface activityInterface){
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



    public void getUserSettings(final Activity context, final HGBFlowInterface flowInterface, final HGBMainInterface activityInterface, CNCSignalRFragment.IProfileUpdated iProfileUpdated,
                                final boolean isDialog){
        // getPreferenceProfiles(final ServerRequestListener listener)
        isDefaultProfile = false;
        miProfileUpdated = iProfileUpdated;
        ConnectionManager.getInstance(context).getPreferenceProfiles(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    accountDefaultSettings = (ArrayList<DefaultsProfilesVO>) data;
                    activityInterface.setDefaultsProfilesVOs(accountDefaultSettings);
                    if(isDialog) {
                        //     List<AccountDefaultSettingsVO> accountDefaultSettings = (List<AccountDefaultSettingsVO>) data;
                        profilesDialog(accountDefaultSettings, context, flowInterface, activityInterface);
                    }else{
                        miProfileUpdated.profileUpdated("");
                    }
                }
            }
            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    private void profilesDialog(final ArrayList<DefaultsProfilesVO> userProfileVOs, final Activity activity, final HGBFlowInterface flowInterface, final HGBMainInterface activityInterface) {
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

        mRadioPreferencesAdapter = new PreferencesSettingsRadioButtonAdapter(activity, accountAttributes);

       // mRadioPreferencesAdapter.selectedItemID(accountAttributes.get(0).getmId());

        mRadioPreferencesAdapter.selectedItemID(accountAttributes.get(0).getmId());

        radioButtonSelected = 1;
        mRadioPreferencesAdapter.notifyDataSetChanged();

        View promptsViewTeest = li.inflate(R.layout.user_profile_popup_list_layout, null);

        ListView user_profile_popup_list_view = (ListView) promptsViewTeest.findViewById(R.id.user_profile_popup_list_view);
        user_profile_popup_list_view.setAdapter(mRadioPreferencesAdapter);

        LinearLayout manage_profile_ll = (LinearLayout)promptsViewTeest.findViewById(R.id.manage_profile_ll);
        manage_profile_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectDefaultProfileDialog.dismiss();
                flowInterface.goToFragment(ToolBarNavEnum.TRAVEL_PREFERENCE.getNavNumber(), null);
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

    /*    String profileName = ((MainActivityBottomTabs) getActivity()).getHGBSaveDataClass().getPersonalUserInformation().getmTravelPreferencesProfileName();
        System.out.println("Kate profileIDrrr profileName=" + profileName);

*/

        selectedRadioPreference(activity);


        dialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if(radioButtonSelected != -1) {

                    if(isDefaultProfile){

                        final DefaultsProfilesVO selected = accountDefaultSettings.get(radioButtonSelected);
                        postDefaultProfile(String.valueOf(selected.getId()), selected.getName(), activity, activityInterface, userProfileVOs);
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



    private void postDefaultProfile(final String profileId,final String profileName, final Activity activity, final HGBMainInterface activityInterface,final ArrayList<DefaultsProfilesVO> userProfileVOs) {

        ConnectionManager.getInstance(activity).postDefaultProfile(profileId, profileName, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {

                    AccountDefaultSettingsVO accountDefault = (AccountDefaultSettingsVO) data;
                    HGBPreferencesManager hgbPrefrenceManager = HGBPreferencesManager.getInstance(getContext());
                    String logInEmail = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, "");
                    putNewPreferencesForUser(logInEmail, accountDefault.getmId(), activity, userProfileVOs);

                    for(DefaultsProfilesVO userProfile : userProfileVOs){
                        if(!userProfile.getId().equals(profileId)){
                            notChoosenProfileVO = userProfile;
                            return;
                        }
                    }
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    private void postNotChoosenDefaultProfile(final Activity activity, ArrayList<DefaultsProfilesVO> userProfileVOs){
        ConnectionManager.getInstance(activity).postDefaultProfile(notChoosenProfileVO.getId(), notChoosenProfileVO.getName(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                AccountDefaultSettingsVO accountDefault = (AccountDefaultSettingsVO) data;
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    private void putNewPreferencesForUser(final String userEmail, final String accountID, final Activity activity, final ArrayList<DefaultsProfilesVO> userProfileVOs) {
        ConnectionManager.getInstance(activity).putAccountsPreferences(userEmail, accountID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                HGBPreferencesManager hgbPrefrenceManager = HGBPreferencesManager.getInstance(getContext());
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, accountID);
                postNotChoosenDefaultProfile(activity, userProfileVOs);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void putAccoutToServer(final Activity activity, final HGBMainInterface activityInterface){
        final DefaultsProfilesVO selected = accountDefaultSettings.get(radioButtonSelected);

        String userEmail = activityInterface.getPersonalUserInformation().getUserEmailLogIn();

        ConnectionManager.getInstance(activity).putAccountsPreferences(userEmail, selected.getId(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                FontTextView my_trip_profile = ((MainActivityBottomTabs) activity).getMyTripProfile();

                my_trip_profile.setTag(selected.getId());

                ((MainActivityBottomTabs) activity).getHGBSaveDataClass().getPersonalUserInformation().setmTravelPreferencesProfileId(selected.getId());
                ((MainActivityBottomTabs) activity).getHGBSaveDataClass().getPersonalUserInformation().setmTravelPreferencesProfileName(selected.getProfilename());

                miProfileUpdated.profileUpdated(selected.getProfilename());

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
