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
import hellogbye.com.hellogbyeandroid.adapters.PreferencesSettingsDragListAdapter;
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
public class PreferencesDragListFragment extends PreferencesSettingsMainClass {

    private DynamicListView mDynamicListView;
    private String strJson;
    private String strType;
    private PreferencesSettingsDragListAdapter preferenceSettingsListAdapter;
    private List<SettingsValuesVO> choosedItems;
    private List<SettingsValuesVO> selectedItem;
    private List<SettingsAttributesVO> accountAttributes;


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



    public void backOnListClicked(final String strType,final String strId,final List<SettingsValuesVO> accountAttributesVO) {

        ((MainActivity) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {
                converSettingsAttributesVO();
                System.out.println("Kate onBack");
                if(noBack){
                    System.out.println("Kate no onBack");
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
                                HGBErrorHelper errorHelper = new HGBErrorHelper();
                            }
                        });
            }
        });
    }


    private void converSettingsAttributesVO(){
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
        mDynamicListView.enableDragAndDrop();
        mDynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {
                        mDynamicListView.startDragging(position);

//                        converSettingsAttributesVO();
                        return true;
                    }
                }
        );



        getCorrectAccountAtribute(rootView);
        preferenceSettingsListAdapter = new PreferencesSettingsDragListAdapter(getActivity(), accountAttributes);
        mDynamicListView.setAdapter(preferenceSettingsListAdapter);

//        mDynamicListView.enableSwipeToDismiss(
//                new OnDismissCallback() {
//                    @Override
//                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
//
//                        for (int position : reverseSortedPositions) {
//
//                            preferenceSettingsListAdapter.remove(position);
//
//                        }
//                    }
//                }
//        );
    }

    private void selectedItemForServer() {

    }

    private void addClassText(View rootView){
        FontTextView settings_title_text = (FontTextView)rootView.findViewById(R.id.settings_title);
        settings_title_text.setText(getActivity().getResources().getText(R.string.preferences_class));
        FontTextView settings_text = (FontTextView)rootView.findViewById(R.id.settings_text);
        settings_text.setText(getActivity().getResources().getText(R.string.preferences_class_prefer));
    }


    private void getCorrectAccountAtribute(View rootView){
        String guid = getSettingGuidSelected();
        switch (guid){
            case "3":
                addClassText(rootView);
                accountAttributes  = correctCheckList(getActivityInterface().getAccountSettingsFlightCabinClassAttributes());
                break;

        }
    }

}
