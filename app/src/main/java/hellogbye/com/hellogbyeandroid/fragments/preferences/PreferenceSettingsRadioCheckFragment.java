package hellogbye.com.hellogbyeandroid.fragments.preferences;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
//import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsCheckListAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsRadioButtonAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsRadioButtonAsCheckBoxAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 3/22/16.
 */
public class PreferenceSettingsRadioCheckFragment extends PreferencesSettingsMainClass{

    private PreferencesSettingsRadioButtonAsCheckBoxAdapter preferenceSettingsListAdapter;
    private FontTextView settings_title_text;
    private FontTextView settings_text;
    private DynamicListView mDynamicListView;
    protected String guid;
    private List<SettingsValuesVO> itemsInList;

    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferenceSettingsRadioCheckFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_drag_list, container, false);

        Bundle args = getArguments();
        if (args != null) {
            strId = args.getString(HGBConstants.BUNDLE_SETTINGS_ATT_ID);
            strType = args.getString(HGBConstants.BUNDLE_SETTINGS_TYPE);
            String strTitleName = args.getString(HGBConstants.BUNDLE_SETTINGS_TITLE_NAME);
            FontTextView titleBar = ((MainActivityBottomTabs) getActivity()).getTitleBar();
            titleBar.setText(strTitleName);
        }

        settings_title_text = (FontTextView)rootView.findViewById(R.id.settings_item_title);
        settings_text = (FontTextView)rootView.findViewById(R.id.settings_item_text);
        noBack = false;


        settingsAttributesVO = new ArrayList<SettingsAttributesVO>();
        accountAttributesTemp = new ArrayList<>();

        itemsInList = getMyAccountAttributes();

     //   selectedItem = getMyAccountAttributes();

        dragDropListInitialization(rootView);

        backOnListClicked();

        return rootView;
    }



    private List<SettingsValuesVO> getMyAccountAttributes() {
        List<SettingsAttributeParamVO> myAttributes = getActivityInterface().getAccountSettingsAttribute();
        String guid = getSettingGuidSelected();
        for (SettingsAttributeParamVO attribute : myAttributes) {
            ArrayList<SettingsAttributesVO> attributes = attribute.getAttributesVOs();
            for (SettingsAttributesVO attributeVO : attributes) {
                if (attributeVO.getmId().equals(guid)) {
                    return attributeVO.getAttributesVOs();
                }
            }
        }
        return null;
    }

    private void dragDropListInitialization(View rootView){
        mDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_drag_list);
        getCorrectAccountAtribute();

        preferenceSettingsListAdapter = new PreferencesSettingsRadioButtonAsCheckBoxAdapter(getActivity(), accountAttributesTemp);

        for(SettingsAttributesVO account : accountAttributesTemp){
            if(account.isChecked()){
                preferenceSettingsListAdapter.selectedPreferebcesID = account.getmId();
            }
        }

        preferenceSettingsListAdapter.setSelectedRadioButtonListener(new PreferenceSettingsFragment.ListRadioButtonClicked(){

            @Override
            public void clickedItem(int selectedPosition) {

                SettingsAttributesVO selected = accountAttributesTemp.get(selectedPosition);
                selected.setChecked(true);
                for(SettingsAttributesVO settingsAttributeVO: accountAttributesTemp ){
                    if(settingsAttributeVO.getmId().equals(selected.getmId())){
                        settingsAttributeVO.setChecked(true);
                    }else{
                        settingsAttributeVO.setChecked(false);
                    }
                }

                settingsAttributesVO.clear();
                settingsAttributesVO.add(selected);

                preferenceSettingsListAdapter.notifyDataSetChanged();
            }
        });

        mDynamicListView.setAdapter(preferenceSettingsListAdapter);


    }

    private List<SettingsAttributesVO> correctCheckList(List<SettingsAttributesVO> accountAttributes){
        for (SettingsValuesVO settingsValuesVO : itemsInList){
            for(SettingsAttributesVO accountAttributeVO :accountAttributes) {
                if (settingsValuesVO.getmID().equals(accountAttributeVO.getmId())) {
                    accountAttributeVO.setmRank(settingsValuesVO.getmRank());
                    accountAttributeVO.setChecked(true);
                    settingsValuesVO.setChecked(true);
                    break;
                }
            }
        }
        return accountAttributes;
    }


    private void getCorrectAccountAtribute(){
        guid = getSettingGuidSelected();
        switch (guid){
            case "5":
                addStopsText();
                accountAttributesTemp  = correctCheckList(getActivityInterface().getAccountSettingsFlightStopAttributes());
                break;
            case "8":
                addSmokingText();
                accountAttributesTemp  = correctCheckList(getActivityInterface().getAccountSettingsHotelSmokingClassAttributes());
                break;


        }
    }

    private void addSmokingText(){
        settings_title_text.setText(getActivity().getResources().getText(R.string.preferences_smoking));
        settings_text.setText(getActivity().getResources().getText(R.string.preferences_smoking_prefer));
    }

    private void addStopsText(){

        settings_title_text.setText(getActivity().getResources().getText(R.string.preferences_stops));
        settings_text.setText(getActivity().getResources().getText(R.string.preferences_stops_prefer));
    }


   /* public void backOnListClicked() {

        ((MainActivity) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {
                if(noBack){
                    return;
                }
                savePreferenceAlert();
              //  onSavePopup();
            }
        });
    }
*/


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        noBack = true;
    }
}
