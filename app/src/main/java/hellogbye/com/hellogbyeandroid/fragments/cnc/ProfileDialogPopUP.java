package hellogbye.com.hellogbyeandroid.fragments.cnc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsPreferencesCheckAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.profiles.DefaultsProfilesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 9/25/16.
 */
public class ProfileDialogPopup  extends HGBAbstractFragment {

    private AlertDialog selectDefaultProfileDialog;
    private Activity activity;
    private ArrayList<AccountDefaultSettingsVO> accountAttributes;
    private PreferencesSettingsPreferencesCheckAdapter mRadioPreferencesAdapter;
    private int radioButtonSelected = -1;
    private ArrayList<DefaultsProfilesVO> accountDefaultSettings;

    ProfileDialogPopup(Activity activity){
        this.activity = activity;
    }


    private void profilesDialog (ArrayList<DefaultsProfilesVO> userProfileVOs) {
        LayoutInflater li = LayoutInflater.from(activity);
        View promptsView = li.inflate(R.layout.popup_custom_title, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setCustomTitle(promptsView);



        //  UserProfilesListAdapter adapter = new UserProfilesListAdapter(userProfileVOs, getContext());


        accountAttributes = new ArrayList<>();
        for(DefaultsProfilesVO defaultsProfilesVO: userProfileVOs){
            AccountDefaultSettingsVO accountDefaultSettingsVO = new AccountDefaultSettingsVO();
            accountDefaultSettingsVO.setmId(defaultsProfilesVO.getId());
            accountDefaultSettingsVO.setmProfileName(defaultsProfilesVO.getProfilename());
            accountAttributes.add(accountDefaultSettingsVO);
        }

        mRadioPreferencesAdapter = new PreferencesSettingsPreferencesCheckAdapter(activity.getBaseContext(),accountAttributes);

        View promptsViewTeest = li.inflate(R.layout.user_profile_popup_list_layout, null);
        ListView user_profile_popup_list_view = (ListView) promptsViewTeest.findViewById(R.id.user_profile_popup_list_view);
        user_profile_popup_list_view.setAdapter(mRadioPreferencesAdapter);

        LinearLayout manage_profile_ll = (LinearLayout)promptsViewTeest.findViewById(R.id.manage_profile_ll);
        manage_profile_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlowInterface().goToFragment(ToolBarNavEnum.TRAVEL_PREFERENCE.getNavNumber(), null);
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

        selectedRadioPreference();


        dialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(radioButtonSelected != -1) {
                    final DefaultsProfilesVO selected = accountDefaultSettings.get(radioButtonSelected);

                    String userEmail = getActivityInterface().getPersonalUserInformation().getUserEmailLogIn();

                    ConnectionManager.getInstance(getActivity()).putAccountsPreferences(userEmail, selected.getId(), new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                            FontTextView my_trip_profile = ((MainActivityBottomTabs) getActivity()).getMyTripProfile();
                            my_trip_profile.setText(selected.getProfilename());
                        }

                        @Override
                        public void onError(Object data) {
                            ErrorMessage(data);
                        }
                    });
                }
            } });


        //Create alert dialog object via builder
        selectDefaultProfileDialog = dialogBuilder.create();
        selectDefaultProfileDialog.setView(promptsViewTeest);
        selectDefaultProfileDialog.setCancelable(false);
        selectDefaultProfileDialog.show();



        //  dialogBuilder.setView(promptsViewTeest);

    }

    private void selectedRadioPreference(){

        FontTextView my_trip_profile = ((MainActivityBottomTabs) getActivity()).getMyTripProfile();

        if(my_trip_profile.getTag() == null){
            return;
        }
        String selectedTag = my_trip_profile.getTag().toString();
        mRadioPreferencesAdapter.selectedItemID(selectedTag);
    }


    private void sendUserSelectionToServer(){



       /* AccountDefaultSettingsVO selected = accountDefaultSettings.get(radioButtonSelected);

        String userEmail = getActivityInterface().getPersonalUserInformation().getUserEmailLogIn();
        ConnectionManager.getInstance(getActivity()).putAccountsPreferences(userEmail, selected.getmId(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });*/
    }
}
