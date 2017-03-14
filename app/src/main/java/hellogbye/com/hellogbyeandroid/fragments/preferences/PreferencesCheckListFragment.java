package hellogbye.com.hellogbyeandroid.fragments.preferences;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsCheckListAdapter;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 12/2/15.
 */
public class PreferencesCheckListFragment extends PreferencesSettingsMainClass {

    private DynamicListView mDynamicListView;
  /*  private String strJson;
    private String strType;*/
    private PreferencesSettingsCheckListAdapter preferenceSettingsListAdapter;
   // private List<SettingsValuesVO> choosedItems;
   // private List<SettingsValuesVO> selectedItem;

    private FontTextView settings_title_text;
    private FontTextView settings_text;
    protected String guid;


    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferencesCheckListFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.settings_drag_list, container, false);
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

        selectedItem = new ArrayList<>();
        firstItems = getMyAccountAttributes();
        settingsAttributesVO = new ArrayList<>();

        dragDropListInitialization(rootView);

        backOnListClicked();

        return rootView;
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
        for (SettingsValuesVO settingsValuesVO : firstItems){
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



    private void multipleCheckView( String clickedItemID){
        for(SettingsAttributesVO accountAttribute : accountAttributesTemp){
            if(accountAttribute.getmId().equals(clickedItemID)){

                if(accountAttribute.isChecked()){
                    accountAttribute.setChecked(false);
                    settingsAttributesVO.remove(accountAttribute);

                }else{
                    accountAttribute.setChecked(true);
                    settingsAttributesVO.add(accountAttribute);

                }
                accountAttribute.setmRank("0");
            }
        }
    }




    private void dragDropListInitialization(View rootView){
        mDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_drag_list);

        mDynamicListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //    FontTextView settings_flight_title = (FontTextView) view.findViewById(R.id.settings_check_name);
                FontCheckedTextView settings_flight_title = (FontCheckedTextView) view.findViewById(R.id.setting_check_image);
                String clickedItemID = settings_flight_title.getTag().toString();
                multipleCheckView(clickedItemID);
                preferenceSettingsListAdapter.notifyDataSetChanged();
            }
        });


        getCorrectAccountAtribute();

        preferenceSettingsListAdapter = new PreferencesSettingsCheckListAdapter(getActivity(), accountAttributesTemp);

        mDynamicListView.setAdapter(preferenceSettingsListAdapter);

    }


    private void getCorrectAccountAtribute(){
        guid = getSettingGuidSelected();
        switch (guid){
      /*      case "5":
                addStopsText();
                accountAttributesTemp  = correctCheckList(getActivityInterface().getAccountSettingsFlightStopAttributes());
                break;*/
            case "7":
                addStarPreferenceText();
                accountAttributesTemp  = correctCheckList(getActivityInterface().getAccountSettingsHotelStarAttributes());
                break;
       /*     case "8":
                addSmokingText();
                accountAttributesTemp  = correctCheckList(getActivityInterface().getAccountSettingsHotelSmokingClassAttributes());
                break;*/


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
    private void addStarPreferenceText() {
        settings_title_text.setText(getActivity().getResources().getText(R.string.preferences_stars));
        settings_text.setText(getActivity().getResources().getText(R.string.preferences_stars_prefer));
    }

}
