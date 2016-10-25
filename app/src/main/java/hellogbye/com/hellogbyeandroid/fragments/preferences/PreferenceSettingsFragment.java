package hellogbye.com.hellogbyeandroid.fragments.preferences;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;


//import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsPreferencesCheckAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsRadioButtonAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.SettingsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 8/17/15.
 */
public class PreferenceSettingsFragment extends HGBAbstractFragment {


    private DynamicListView mDynamicListView;
    private SettingsAdapter mAdapter;
    private List<AccountDefaultSettingsVO> accountDefaultSettings;
    private List<SettingsAttributeParamVO> accountSettingsAttributes;
    private FontEditTextView input;
    private View popup_preferences_layout;
    private int MIN_PREFERENCE_SIZE = 1;
    private View preferences_at_least_one_preference;
    private String edit_mode;
    private int radioButtonSelected = -1;
    private AccountDefaultSettingsVO accountDefaultSettingsVO;
    private Button addNewPreferencesButton;
    private FontTextView edit_preferences_imagebtn;
    private ImageButton check_preferences;
    private ImageButton tool_bar_delete_preferences;

    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferenceSettingsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }


    private void onBackPressed(){
        ((MainActivityBottomTabs) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {
                if(radioButtonSelected != -1) {
                    AccountDefaultSettingsVO selected = accountDefaultSettings.get(radioButtonSelected);
                    final String selectedID = selected.getmId();
                    String userEmail = getActivityInterface().getPersonalUserInformation().getUserEmailLogIn();
                    ConnectionManager.getInstance(getActivity()).putAccountsPreferences(userEmail, selectedID, new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                            FontTextView my_trip_profile = ((MainActivityBottomTabs) getActivity()).getMyTripProfile();
                            my_trip_profile.setTag(selectedID);
                        }

                        @Override
                        public void onError(Object data) {
                            ErrorMessage(data);
                        }
                    });
                }
            }
        });
    }



    private void adapterClicked(String clickedItemID){
        if(mAdapter.getIsEditMode()){
            for( AccountDefaultSettingsVO accountAttribute : accountDefaultSettings){
                if(accountAttribute.getmId().equals(clickedItemID)){
                    if(accountAttribute.isChecked()){
                        accountAttribute.setChecked(false);
                    }else{
                        accountAttribute.setChecked(true);
                    }
                    break;
                }

            }
            mAdapter.notifyDataSetChanged();

        }else {
            getSettingsAttributes(clickedItemID);
        }
    }



    public interface ListLineClicked{
        void clickedItem(String itemID);
    }

    public interface ListRadioButtonClicked{
        void clickedItem(int slectedPosition);
    }

    private ListLineClicked listLineClicked = new ListLineClicked() {
        @Override
        public void clickedItem(String itemID) {
            adapterClicked(itemID);
        }
    };

    private void selectedRadioPreference(){

        FontTextView my_trip_profile = ((MainActivityBottomTabs) getActivity()).getMyTripProfile();
     /*   if(my_trip_profile == null || mAdapter == null){
            return;
        }*/
        if(my_trip_profile.getTag() == null){
            return;
        }
        String selectedTag = my_trip_profile.getTag().toString();
        mAdapter.selectedItemID(selectedTag);
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

    private void createListAdapter() {


        setActiveAccount();


        //Factory implementation :)
        Bundle args = getArguments();
        if (args != null) {
            edit_mode = args.getString("edit_mode");
            if (edit_mode != null && edit_mode.equals("true")) {
                mAdapter = new PreferencesSettingsRadioButtonAdapter(getActivity(), accountDefaultSettings);
                mDynamicListView.setAdapter((PreferencesSettingsRadioButtonAdapter)mAdapter);

            }else{
                mAdapter = new PreferencesSettingsPreferencesCheckAdapter(getActivity(), accountDefaultSettings);
                mDynamicListView.setAdapter((PreferencesSettingsPreferencesCheckAdapter)mAdapter);
            }

            mAdapter.setSelectedRadioButtonListener(new ListRadioButtonClicked(){

                @Override
                public void clickedItem(int selectedPosition) {
                    radioButtonSelected = selectedPosition;
                }
            });

            selectedRadioPreference();

        //    mAdapter.notifyDataSetChanged();
            mAdapter.setClickedLineCB(listLineClicked);
        }
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

    public interface OnItemClickListener {
        void onItemClick(String guid);
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


    private void editPreference(){
        edit_preferences_imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPreferencesButton.setVisibility(View.INVISIBLE);
                if (check_preferences.getVisibility() == View.GONE) { //edit mode

                    List<AccountDefaultSettingsVO> accountAttributes = new ArrayList<AccountDefaultSettingsVO>();
                    for(AccountDefaultSettingsVO accountAttribute :accountDefaultSettings){
                        if(!accountAttribute.isActiveProfile()){
                            accountAttributes.add(accountAttribute);
                        }else{
                            accountDefaultSettingsVO = accountAttribute;
                        }
                    }

                    mAdapter.updateItems(accountAttributes);
                    tool_bar_delete_preferences.setVisibility(View.VISIBLE);
                    edit_preferences_imagebtn.setVisibility(View.GONE);
                    check_preferences.setVisibility(View.VISIBLE);
                    mAdapter.setEditMode(true);
                }
            }
        });
    }

    private void deletePreferences(){
        tool_bar_delete_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccountDefaultSettingsVO> accountAttributes = new ArrayList<AccountDefaultSettingsVO>();
                accountAttributes.addAll(accountDefaultSettings);
                for(AccountDefaultSettingsVO accountAttribute :accountAttributes){
                    if(accountAttribute.isChecked()){
                        if(accountDefaultSettings.size() <= MIN_PREFERENCE_SIZE){
                            //popup
                            atLeastOnePreference();
                            break;
                        }
                        accountDefaultSettings.remove(accountAttribute);
                        deletePreference(accountAttribute.getmId());
                    }
                }
                //  mAdapter.updateItems(accountDefaultSettings);
                mAdapter.notifyDataSetChanged();
                // deletePreference(String preferenceId)

            }
        });
    }

    private void doneEditingPreference(){
        check_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //done mode
                addNewPreferencesButton.setVisibility(View.VISIBLE);
                check_preferences.setVisibility(View.GONE);
                tool_bar_delete_preferences.setVisibility(View.GONE);
                //  edit_preferences_imagebtn. //setBackgroundResource(R.drawable.edit_img);
                edit_preferences_imagebtn.setVisibility(View.VISIBLE);
                if(accountDefaultSettingsVO != null){
                    List<AccountDefaultSettingsVO> accountAttributes = new ArrayList<AccountDefaultSettingsVO>();
                    accountAttributes.add(0,accountDefaultSettingsVO);
                    accountAttributes.addAll(accountDefaultSettings);

                    mAdapter.updateItems(accountAttributes);
                    //   accountDefaultSettings.add(accountDefaultSettingsVO);
                }
                //       mAdapter.updateItems(accountDefaultSettings);
                mAdapter.setEditMode(false);

                //  mAdapter.notifyDataSetChanged();
                //   editClickCB.onItemClick("done");
            }
        });
    }

    private void initialize(View rootView, LayoutInflater inflater){
        edit_preferences_imagebtn = ((MainActivityBottomTabs)getActivity()).getEditPreferenceBtn();
        check_preferences = ((MainActivityBottomTabs)getActivity()).getCheckPreferenceButton();
        tool_bar_delete_preferences = ((MainActivityBottomTabs)getActivity()).getToolBarDeletePreferences();

        addNewPreferencesButton = (Button) rootView.findViewById(R.id.add_preferences);

        check_preferences.setVisibility(View.GONE);
        edit_preferences_imagebtn.setVisibility(View.VISIBLE);

        mDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_preferences_drag_list);
        popup_preferences_layout = inflater.inflate(R.layout.preferences_add_new_preference, null);
        preferences_at_least_one_preference = inflater.inflate(R.layout.preferences_at_least_one_preference, null);
        input = (FontEditTextView) popup_preferences_layout.findViewById(R.id.editTextDialog);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onBackPressed();

        View rootView = inflater.inflate(R.layout.settings_list_layout, container, false);

        initialize(rootView, inflater);


        addNewPreferenceClick();
        editPreference();
        deletePreferences();
        doneEditingPreference();

        ConnectionManager.getInstance(getActivity()).getUserSettingsDefault(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    accountDefaultSettings = (List<AccountDefaultSettingsVO>) data;
                  //  System.out.println("Kate accountDefaultSettings =" + accountDefaultSettings.size());
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

    private void editSettingsPreferencesPopUp() {

        HGBUtility.showAlertPopUp(getActivity(), input, popup_preferences_layout, getString(R.string.preferences_add_button),
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


}
