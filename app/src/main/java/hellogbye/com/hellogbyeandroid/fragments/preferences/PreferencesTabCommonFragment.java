package hellogbye.com.hellogbyeandroid.fragments.preferences;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsDragListAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 9/18/16.
 */
public class PreferencesTabCommonFragment extends HGBAbstractFragment {


    private View rootView;
    private String strJson;
    private ArrayList<SettingsAttributesVO> accountFlightSettings ;
    private ArrayList<SettingsAttributesVO> accountHotelSettings;
    private DynamicListView mFlightDynamicListView;
    private DynamicListView mHotelDynamicListView;
    private Bundle args = new Bundle();
    private FontTextView settings_title_flight_text;
    private FontTextView settings_flight_text;
    private FontTextView settings_title_hotel_text;
    private FontTextView settings_hotel_text;

    public View createViewForTab(int layoutID, Context context, boolean isFlightTab){
        LayoutInflater inflater = LayoutInflater.from(context);
        rootView = inflater.inflate(layoutID,null, false);

        Bundle args = getArguments();
        if (args != null) {
            strJson = args.getString(HGBConstants.BUNDLE_SETTINGS_ATT_ID);

            String titleName =  args.getString(HGBConstants.BUNDLE_SETTINGS_TITLE_NAME);
            FontTextView titleBar = ((MainActivityBottomTabs) getActivity()).getTitleBar();
            titleBar.setText(titleName);
        }



        initializePreferenceData();
        initializeFlightFontText(rootView);
        if(isFlightTab){
            initializeFlightRecycle(rootView, accountFlightSettings);
            addFlightPreferenceText();
        }else{

            removeBedAndSmoking();
            initializeHotelRecycle(rootView, accountHotelSettings);
            addHotelPreferenceText();
        }


        showCorrectView(isFlightTab);
        return rootView;
    }

    private void removeBedAndSmoking(){
        ArrayList<SettingsAttributesVO> accountHotelList = new ArrayList<>(accountHotelSettings);
        for(SettingsAttributesVO accountHotel: accountHotelSettings){
            if(accountHotel.getmName().equals("Bed Types") || accountHotel.getmName().equals("Smoking")){
                accountHotelList.remove(accountHotel);
            }
        }
        accountHotelSettings = new ArrayList<>(accountHotelList);
    }

    private void initializeFlightFontText(View rootView){
        settings_title_flight_text = (FontTextView)rootView.findViewById(R.id.settings_item_title);
        settings_flight_text = (FontTextView)rootView.findViewById(R.id.settings_item_text);
        settings_title_hotel_text = (FontTextView)rootView.findViewById(R.id.settings_item_title);
        settings_hotel_text = (FontTextView)rootView.findViewById(R.id.settings_item_text);
    }


    private void addFlightPreferenceText(){
        settings_title_flight_text.setText(getActivity().getResources().getText(R.string.preferences_flight_preferences));
        settings_flight_text.setText(getActivity().getResources().getText(R.string.preferences_flight_prefer));
    }


    private void addHotelPreferenceText(){
        settings_title_hotel_text.setText(getActivity().getResources().getText(R.string.preferences_hotel_preferences));
        settings_hotel_text.setText(getActivity().getResources().getText(R.string.preferences_hotel_prefer));
    }

    private void showCorrectView(boolean mIsFlightViewShown) {
        if (mIsFlightViewShown) {
            addFlightPreferenceText();
        } else {
            addHotelPreferenceText();
        }
    }


    private void initializePreferenceData(){

        List<SettingsAttributeParamVO> accountSettings = getActivityInterface().getAccountSettingsAttribute();
        for (SettingsAttributeParamVO accountSetting : accountSettings) {
            if (accountSetting.getmName().equalsIgnoreCase(NodeTypeEnum.FLIGHT.toString())) {
                accountFlightSettings = accountSetting.getAttributesVOs();
            } else if (accountSetting.getmName().equalsIgnoreCase(NodeTypeEnum.HOTEL.toString())) {
                accountHotelSettings = accountSetting.getAttributesVOs();

            }
        }
    }

    private void initializeFlightRecycle(View rootView,  List<SettingsAttributesVO> accountFlightSettings) {

        mFlightDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_drag_list);
        mFlightDynamicListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FontTextView settings_flight_title = (FontTextView) view.findViewById(R.id.setting_preferences_title_drag);
                String clickedItemID = settings_flight_title.getTag().toString();
                String titleName = settings_flight_title.getText().toString();
                switchBetweenOptions(clickedItemID, titleName);
            }
        });

        PreferencesSettingsDragListAdapter mTabsAdapter = new PreferencesSettingsDragListAdapter(getActivity(),accountFlightSettings,true);
        mFlightDynamicListView.setAdapter(mTabsAdapter);
        mFlightDynamicListView.enableDragAndDrop();
        mFlightDynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {
                        mFlightDynamicListView.startDragging(position);
                        return true;
                    }
                }
        );
    }

    private void initializeHotelRecycle(View rootView, ArrayList<SettingsAttributesVO> accountFlightSettings) {
        mHotelDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_hotel_drag_list);
        mHotelDynamicListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FontTextView settings_flight_title = (FontTextView) view.findViewById(R.id.setting_preferences_title_drag);
                String clickedItemID = settings_flight_title.getTag().toString();
                String titleName = settings_flight_title.getText().toString();
                switchBetweenOptions(clickedItemID, titleName);
            }
        });
        System.out.println("Kate initializeHotelRecycle accountFlightSettings =" + accountFlightSettings.size());
        PreferencesSettingsDragListAdapter mTabsAdapter = new PreferencesSettingsDragListAdapter(getActivity(),accountFlightSettings,true);
        mHotelDynamicListView.setAdapter(mTabsAdapter);
        mHotelDynamicListView.enableDragAndDrop();
        mHotelDynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {
                        mHotelDynamicListView.startDragging(position);
                        return true;
                    }
                }
        );
    }

    private void switchBetweenOptions(String guid, String titleName) {
        List<SettingsAttributesVO> accountAttributes = null;
        boolean goToNewFragment = false;
        String type = null;
        switch (guid) {
            case "1":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightCarrierAttributes();
                break;
            case "2":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightAircraftAttributes();
                break;
            case "3":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightCabinClassAttributes();
                break;
            case "9":
                type = "HTL";
                accountAttributes = getActivityInterface().getAccountSettingsHotelChainAttributes();
                break;
            case "5":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightStopAttributes();
                break;
            case "6":
                type = "HTL";
                accountAttributes = getActivityInterface().getAccountSettingsHotelBedTypeAttributes();
                break;
            case "7":
                type = "HTL";
                accountAttributes = getActivityInterface().getAccountSettingsHotelStarAttributes();
                break;
            case "8":
                type = "HTL";
                accountAttributes = getActivityInterface().getAccountSettingsHotelSmokingClassAttributes();
                break;
            case "10":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountFarePreferences();
                break;
        }

        setSettingGuidSelected(guid);
        if (accountAttributes != null) {
            goToNewFragment = true;
        } else {
            getSettingsAttributes(guid, type, titleName);
        }

        if(goToNewFragment){
            gotToSelectedFragment(guid,type, titleName);
        }

    }

    private void gotToSelectedFragment(String guid, String type, String titleName){
        args.putString(HGBConstants.BUNDLE_SETTINGS_ATT_ID, strJson);
        args.putString(HGBConstants.BUNDLE_SETTINGS_TYPE, type);
        args.putString(HGBConstants.BUNDLE_SETTINGS_TITLE_NAME, titleName);

        switch (guid){
            case "1":
            case "2":
            case "9":
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_SEARCH_LIST_SETTINGS.getNavNumber(), args);
                break;
            case "5":
            case "8":
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_CHECK_AS_RADIO_SETTINGS.getNavNumber(), args);
                break;
            case "7":
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_CHECK_LIST_SETTINGS.getNavNumber(), args);
                break;
            case "3":
            case "6":
            case "10":
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_DRAG_LIST_SETTINGS.getNavNumber(), args);
                break;


        }
    }

    private void getSettingsAttributes(String clickedAttributeID, final String type, final String titleName) {

        ConnectionManager.getInstance(getActivity()).getUserSettingAttributesForAttributeID(clickedAttributeID, type, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    List<SettingsAttributesVO> acountSettingsAttributes = (List<SettingsAttributesVO>) data; //gson.fromJson((String) data, listType);
                    String settingsGuid = getSettingGuidSelected();
                    switch (settingsGuid) {
                        case "1":
                            getActivityInterface().setAccountSettingsFlightCarrierAttributes(acountSettingsAttributes);
                            break;
                        case "2":
                            getActivityInterface().setAccountSettingsFlightAircraftAttributes(acountSettingsAttributes);
                            break;
                        case "3":
                            getActivityInterface().setAccountSettingsFlightCabinClassAttributes(acountSettingsAttributes);
                            break;
                        case "5":
                            getActivityInterface().setAccountSettingsFlightStopAttributes(acountSettingsAttributes);
                            break;
                        case "6":
                            getActivityInterface().setAccountSettingsHotelBedTypeAttributes(acountSettingsAttributes);
                            break;
                        case "7":
                            getActivityInterface().setAccountSettingsHotelStarAttributes(acountSettingsAttributes);
                            break;
                        case "8":
                            getActivityInterface().setAccountSettingsHotelSmokingAttributes(acountSettingsAttributes);
                            break;
                        case "9":
                            getActivityInterface().setAccountSettingsHotelChainAttributes(acountSettingsAttributes);
                            break;
                        case "10":
                            getActivityInterface().setAccountFarePreferences(acountSettingsAttributes);
                            break;
                    }
                    gotToSelectedFragment(settingsGuid, type, titleName);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

}
