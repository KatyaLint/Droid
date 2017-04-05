package hellogbye.com.hellogbyeandroid.fragments.preferences;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
//import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsDragListAdapter;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 12/2/15.
 */
public class PreferencesDragListFragment extends PreferencesSettingsMainClass {

    private DynamicListView mDynamicListView;

    private PreferencesSettingsDragListAdapter preferenceSettingsListAdapter;
    private List<SettingsValuesVO> choosedItems;
    private List<SettingsValuesVO> selectedItem;
    private List<SettingsAttributesVO> accountAttributes;
    private FontTextView settings_title_text;
    private FontTextView settings_text;


    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferencesDragListFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.settings_drag_list, container, false);
        noBack = true;

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

        selectedItem = new ArrayList<>();
        choosedItems = getMyAccountAttributes();

        dragDropListInitialization(rootView);

        backOnListClicked(strType,strId,selectedItem);

        return rootView;
    }


    private void savePreferenceAlert(final List<SettingsValuesVO> accountAttributesVO){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.settings_save_popup, null);

    /*    if( settingsAttributesVO == null || guid.equals("7")
            && firstItems.isEmpty()
            && myAccountAttribute.getAttributesVOs().isEmpty() || settingsAttributesVO.isEmpty()){
            return;
        }*/



      /*  if(settingsAttributesVO.isEmpty() && selectedItem.isEmpty()){// myAccountAttribute.getAttributesVOs().isEmpty()   && firstItems.isEmpty()){
            return;
        }*/


        HGBUtility.showAlertPopUp(getActivity(), null, promptsView,
                getResources().getString(R.string.preferences_save_pop_up),
                getResources().getString(R.string.save_button),
                new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        convertSettingsAttributesVO();
                        if(noBack){
                            return;
                        }
                        String guid = getSettingGuidSelected();
                        ConnectionManager.getInstance(getActivity()).putAttributesValues(
                                strId, strType, guid, accountAttributesVO, new ConnectionManager.ServerRequestListener() {
                                    @Override
                                    public void onSuccess(Object data) {

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


    public void backOnListClicked(final String strType,final String strId,final List<SettingsValuesVO> accountAttributesVO) {

        ((MainActivityBottomTabs) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {
                convertSettingsAttributesVO();
                if(noBack){
                    return;
                }

                savePreferenceAlert(accountAttributesVO);

             /*   String guid = getSettingGuidSelected();
                ConnectionManager.getInstance(getActivity()).putAttributesValues(
                        strId, strType, guid, accountAttributesVO, new ConnectionManager.ServerRequestListener() {
                            @Override
                            public void onSuccess(Object data) {
                                System.out.println("Kate putAttributesValues");

                            }

                            @Override
                            public void onError(Object data) {
                                ErrorMessage(data);
                            }
                        });*/
            }
        });
    }


    private void convertSettingsAttributesVO(){
        if(accountAttributes ==  null){
            return;
        }
        for(SettingsAttributesVO settingsAttributesVO :accountAttributes){
            for (SettingsValuesVO settingsValuesVO:choosedItems){
                if(settingsValuesVO.getmID().equals(settingsAttributesVO.getmId())){
                    settingsValuesVO.setmRank(settingsAttributesVO.getmRank());
                    selectedItem.add(settingsValuesVO);
                }
            }
        }
    }

    //TODO check if alwayes needed
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

    private List<SettingsAttributesVO> correctCheckList(List<SettingsAttributesVO> accountAttributes){
        for (SettingsValuesVO settingsValuesVO : choosedItems){
            for(SettingsAttributesVO accountAttributeVO :accountAttributes) {
                if (settingsValuesVO.getmID().equals(accountAttributeVO.getmId())) {
                    accountAttributeVO.setmRank(settingsValuesVO.getmRank());
                    break;
                }
            }
        }
        return accountAttributes;
    }

    private void dragDropListInitialization(View rootView){
        mDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_drag_list);
        mDynamicListView.enableDragAndDrop();
        mDynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {
                        mDynamicListView.startDragging(position);
                        noBack = false;
                        return true;
                    }
                }
        );

        getCorrectAccountAtribute();
        preferenceSettingsListAdapter = new PreferencesSettingsDragListAdapter(getActivity(), accountAttributes, false);
        mDynamicListView.setAdapter(preferenceSettingsListAdapter);
    }

    private void getCorrectAccountAtribute(){
        String guid = getSettingGuidSelected();
        switch (guid){
            case "3":

                accountAttributes  = correctCheckList(getActivityInterface().getAccountSettingsFlightCabinClassAttributes());
                settings_title_text.setText(getActivity().getResources().getText(R.string.preferences_class));
                settings_text.setText(getActivity().getResources().getText(R.string.preferences_class_prefer));
                break;

            case "6":

                accountAttributes  = correctCheckList(getActivityInterface().getAccountSettingsHotelBedTypeAttributes());
                settings_title_text.setText(getActivity().getResources().getText(R.string.preferences_bed_type));
                settings_text.setText(getActivity().getResources().getText(R.string.preferences_bed_rank));
                break;

            case "10":

                accountAttributes  = correctCheckList(getActivityInterface().getAccountFarePreferences());
                settings_title_text.setText(getActivity().getResources().getText(R.string.preferences_fare_type));
                settings_text.setText(getActivity().getResources().getText(R.string.preferences_fare_rank));
                break;

        }

    }

}
