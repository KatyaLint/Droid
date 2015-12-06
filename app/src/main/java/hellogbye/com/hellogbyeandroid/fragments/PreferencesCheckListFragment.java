package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.PreferencesSettingsCheckListAdapter;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 12/2/15.
 */
public class PreferencesCheckListFragment extends PreferencesSettingsMainClass {

    private DynamicListView mDynamicListView;
    private String strJson;
    private String strType;
    private PreferencesSettingsCheckListAdapter preferenceSettingsListAdapter;
    private List<SettingsValuesVO> choosedItems;
    private List<SettingsValuesVO> selectedItem;
    private List<SettingsAttributesVO> accountAttributes;


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
            strJson = args.getString("setting_att_id");
            strType = args.getString("setting_type");

        }

        noBack = false;

        selectedItem = new ArrayList<>();
        choosedItems = getMyAccountAttributes();

        dragDropListInitialization(rootView);

        backOnListClicked(strType,strJson,selectedItem);

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
        for (SettingsValuesVO settingsValuesVO:choosedItems){
            for(SettingsAttributesVO accountAttributeVO :accountAttributes) {
                if (settingsValuesVO.getmID().equals(accountAttributeVO.getmId())) {
                    accountAttributeVO.setmRank(settingsValuesVO.getmRank());
                    break;
                }
            }
        }
        return accountAttributes;
    }


    private void selectedItemForServer(SettingsAttributesVO accountAttribute){
        selectedItem.clear();
        SettingsValuesVO selectedValue = new SettingsValuesVO(accountAttribute.getmId(),
                accountAttribute.getmName(),
                accountAttribute.getmDescription(),
                accountAttribute.getmRank());
        selectedItem.add(selectedValue);
        choosedItems.clear();
        choosedItems.add(selectedValue);
    }


    private void dragDropListInitialization(View rootView){
        mDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_drag_list);
        mDynamicListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FontTextView settings_flight_title = (FontTextView) view.findViewById(R.id.settings_check_name);
                String clickedItemID = settings_flight_title.getTag().toString();
                for(SettingsAttributesVO accountAttribute : accountAttributes){
                    if(accountAttribute.getmId().equals(clickedItemID)){
                        accountAttribute.setmRank("0");
                        selectedItemForServer(accountAttribute);
                    }else{
                        accountAttribute.setmRank(null);
                    }
                }
                preferenceSettingsListAdapter.notifyDataSetChanged();
            }
        });


        mDynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {
                        mDynamicListView.startDragging(position);
                        return true;
                    }
                }
        );


        getCorrectAccountAtribute(rootView);
        preferenceSettingsListAdapter = new PreferencesSettingsCheckListAdapter(getActivity(), accountAttributes);
        mDynamicListView.setAdapter(preferenceSettingsListAdapter);

        mDynamicListView.enableSwipeToDismiss(
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {

                        for (int position : reverseSortedPositions) {

                            preferenceSettingsListAdapter.remove(position);

                        }
                    }
                }
        );
    }

    private void addSmokingText(View rootView){
        FontTextView settings_title_text = (FontTextView)rootView.findViewById(R.id.settings_title);
        settings_title_text.setText(getActivity().getResources().getText(R.string.preferences_smoking));
        FontTextView settings_text = (FontTextView)rootView.findViewById(R.id.settings_text);
        settings_text.setText(getActivity().getResources().getText(R.string.preferences_smoking_prefer));
    }

    private void addStopsText(View rootView){
        FontTextView settings_title_text = (FontTextView)rootView.findViewById(R.id.settings_title);
        settings_title_text.setText(getActivity().getResources().getText(R.string.preferences_stops));
        FontTextView settings_text = (FontTextView)rootView.findViewById(R.id.settings_text);
        settings_text.setText(getActivity().getResources().getText(R.string.preferences_stops_prefer));
    }

    private void getCorrectAccountAtribute(View rootView){
        String guid = getSettingGuidSelected();
        switch (guid){
            case "5":
                addStopsText(rootView);
                accountAttributes  = correctCheckList(getActivityInterface().getAccountSettingsFlightStopAttributes());
                break;
            case "8":
                addSmokingText(rootView);
                accountAttributes  = correctCheckList(getActivityInterface().getAccountSettingsHotelSmokingClassAttributes());
                break;
        }
    }

}
