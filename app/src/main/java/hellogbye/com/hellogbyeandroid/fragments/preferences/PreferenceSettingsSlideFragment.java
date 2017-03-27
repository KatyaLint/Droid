package hellogbye.com.hellogbyeandroid.fragments.preferences;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsRadioButtonAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSwipeItemsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 3/9/17.
 */

public class PreferenceSettingsSlideFragment extends HGBAbstractFragment {

    private String activeProfileId;
    private FontButtonView addNewPreferencesButton;
    private RecyclerView settings_preferences_drag_list;
    private View popup_preferences_layout;
    private View preferences_at_least_one_preference;
    private FontEditTextView input;
    private ArrayList<AccountDefaultSettingsVO> accountDefaultSettings;
    private PreferencesSwipeItemsAdapter mAdapter;
    private List<SettingsAttributeParamVO> accountSettingsAttributes;
    private int MIN_PREFERENCE_SIZE = 1;

    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferenceSettingsSlideFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    private void onBackPressed(){
        ((MainActivityBottomTabs) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {
                sendPreferenceChooseToServer();
            }
        });
    }

    private void sendPreferenceChooseToServer(){

            String userEmail = getActivityInterface().getPersonalUserInformation().getUserEmailLogIn();
            ConnectionManager.getInstance(getActivity()).putAccountsPreferences(userEmail, activeProfileId, new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    FontTextView my_trip_profile = ((MainActivityBottomTabs) getActivity()).getMyTripProfile();
                    my_trip_profile.setTag(activeProfileId);
                }

                @Override
                public void onError(Object data) {
                    ErrorMessage(data);
                }
            });
    }

    private void addNewPreferenceClick(){
        addNewPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((MainActivityBottomTabs) getActivity()).isFreeUser){
                    getFlowInterface().goToFragment(ToolBarNavEnum.FREE_USER_FRAGMENT.getNavNumber(), null);
                }else{
                    editSettingsPreferencesPopUp();
                }
            }
        });
    }

    private void editSettingsPreferencesPopUp() {

        HGBUtility.showAlertPopUp(getActivity(), input, popup_preferences_layout, null,
                getString(R.string.ok_button), new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        if (inputItem.length() != 0) {
                            popUpConnection(inputItem);
                        }
                    }

                    @Override
                    public void itemCanceled() {

                    }
                });

    }

    private void popUpConnection(String profileName) {
        ConnectionManager.getInstance(getActivity()).postNewPreferenceProfile(profileName, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    AccountDefaultSettingsVO accountDefault = (AccountDefaultSettingsVO) data;
                    accountDefaultSettings.add(accountDefault);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void initialize(View rootView, LayoutInflater inflater){

        addNewPreferencesButton = (FontButtonView) rootView.findViewById(R.id.add_preferences);


        settings_preferences_drag_list = (RecyclerView) rootView.findViewById(R.id.settings_preferences_drag_list);
        popup_preferences_layout = inflater.inflate(R.layout.preferences_add_new_preference, null);
        preferences_at_least_one_preference = inflater.inflate(R.layout.preferences_at_least_one_preference, null);
        input = (FontEditTextView) popup_preferences_layout.findViewById(R.id.editTextDialog);


        settings_preferences_drag_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        settings_preferences_drag_list.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        settings_preferences_drag_list.setLayoutManager(mLayoutManager);

    }


    private void setActiveAccount(){
        String userPreferenceID =  getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId();

        for (AccountDefaultSettingsVO accountDefaultSettingVO: accountDefaultSettings){
            if(accountDefaultSettingVO.getmId().equals(userPreferenceID)){
                accountDefaultSettingVO.setActiveProfile(true);
                break;
            }
        }
    }

    public interface ListLineClicked{
        void clickedItem(String itemID);
        void longClickedItem(String itemID);
        void deleteItem(String itemID);
    }

    private ListLineClicked listLineClicked = new ListLineClicked() {
        @Override
        public void clickedItem(String itemID) {
            adapterClicked(itemID);
        }

        @Override
        public void longClickedItem(String itemID) {

            adapterLongClicked(itemID);
        }

        @Override
        public void deleteItem(final String companion_id) {
            List<AccountDefaultSettingsVO> accountAttributes = new ArrayList<>();
            accountAttributes.addAll(accountDefaultSettings);

            if(accountDefaultSettings.size() <= MIN_PREFERENCE_SIZE){
                //popup
                atLeastOnePreference();
                return;
            }

            for(AccountDefaultSettingsVO accountAttribute :accountAttributes){
                if( accountAttribute.getmId().equals(companion_id)){
                    accountDefaultSettings.remove(accountAttribute);
                    deletePreference(companion_id);
                }
            }
            //  mAdapter.updateItems(accountDefaultSettings);
            mAdapter.notifyDataSetChanged();
        }


    };

    private void deletePreference(String preferenceId){
        ConnectionManager.getInstance(getActivity()).deletePreferenceProfileId(preferenceId,new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    private void atLeastOnePreference(){

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle(R.string.preferences_at_least_one_preference)
                .setView(preferences_at_least_one_preference)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((ViewGroup) preferences_at_least_one_preference.getParent()).removeView(popup_preferences_layout);
                        dialog.cancel();
                    }
                })

                .create().show();

    }

    private void adapterLongClicked(String clickedItemID){
        getSettingsAttributes(clickedItemID);
    }

    private void getSettingsAttributes(final String clickedAttributeID) {

        ConnectionManager.getInstance(getActivity()).getUserSettingsAttributes(clickedAttributeID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {

                    String accountName = "";
                    for (AccountDefaultSettingsVO accountDefaultSetting : accountDefaultSettings){
                        if(accountDefaultSetting.getmId().equals(clickedAttributeID)){
                            accountName = accountDefaultSetting.getmProfileName();
                            break;
                        }
                    }


                    accountSettingsAttributes = (List<SettingsAttributeParamVO>) data;//gson.fromJson((String) data, listType);
                    getActivityInterface().setAccountSettingsAttribute(accountSettingsAttributes);
                    Bundle args = new Bundle();
                    args.putString(HGBConstants.BUNDLE_SETTINGS_TITLE_NAME, accountName);
                    args.putString(HGBConstants.BUNDLE_SETTINGS_ATT_ID, clickedAttributeID);
                    getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_TAB_SETTINGS.getNavNumber(), args);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }



    private void adapterClicked(String clickedItemID){

        for( AccountDefaultSettingsVO accountAttribute : accountDefaultSettings){
            if(accountAttribute.getmId().equals(clickedItemID)){
                if(!accountAttribute.isActiveProfile()){
                    accountAttribute.setActiveProfile(true);
                }
            }else{
                accountAttribute.setActiveProfile(false);
            }
        }

        getActivityInterface().getPersonalUserInformation().setmTravelPreferencesProfileId(clickedItemID);
        HGBPreferencesManager hgbPrefrenceManager = HGBPreferencesManager.getInstance(getActivity());
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, clickedItemID);

        activeProfileId = clickedItemID;
        mAdapter.notifyDataSetChanged();

    }

    private void createListAdapter() {

        setActiveAccount();
        mAdapter = new PreferencesSwipeItemsAdapter(accountDefaultSettings);

        settings_preferences_drag_list.setAdapter(mAdapter);

        mAdapter.setClickedLineCB(listLineClicked);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onBackPressed();
        View rootView = inflater.inflate(R.layout.settings_list_slide_layout, container, false);
        initialize(rootView, inflater);

        addNewPreferenceClick();

        ConnectionManager.getInstance(getActivity()).getUserSettingsDefault(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                if (data != null) {
                    accountDefaultSettings = (ArrayList<AccountDefaultSettingsVO>) data;
                    createListAdapter();
                }
            }
            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });





        return rootView;
    }
}
