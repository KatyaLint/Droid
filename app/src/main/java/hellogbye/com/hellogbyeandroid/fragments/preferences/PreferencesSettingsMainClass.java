package hellogbye.com.hellogbyeandroid.fragments.preferences;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 12/2/15.
 */
public class PreferencesSettingsMainClass extends HGBAbstractFragment {

    public boolean noBack = false;
    protected String strType;
    protected String strId;
    protected List<SettingsValuesVO> selectedItem;
    protected List<SettingsValuesVO> firstItems;
    protected SettingsAttributesVO myAccountAttribute;
    protected static int MAX_CHOOSEN_NUMBER = 10;
    protected List<SettingsAttributesVO> accountAttributesTemp;
    protected List<SettingsAttributesVO> settingsAttributesVO;


    public interface saveButtonClicked{
        void onSaveClicked();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainActivity) getActivity()).setOnSavePreferencesButtonClicked(new saveButtonClicked(){
            @Override
            public void onSaveClicked() {
               // savePreferenceAlert();
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }



    public void backOnListClicked() {

        ((MainActivity) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {
                if(noBack){
                    return;
                }

                savePreferenceAlert();
            }
        });
    }

    private void savePreferenceAlert(){
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.settings_save_popup, null);


        if(this.getClass().toString().equals(PreferencesSearchListFragment.class.toString())
            && firstItems.isEmpty()
            && myAccountAttribute.getAttributesVOs().isEmpty()){
            return;
        }

        HGBUtility.showAlertPopUp(getActivity(), null, promptsView,
                getResources().getString(R.string.preferences_save_pop_up),
                getResources().getString(R.string.save_button),
                new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        convertData();
                        savePreferencesData();
                    }

                    @Override
                    public void itemCanceled() {

                        if(this.getClass().toString().equals(PreferencesSearchListFragment.class.toString())){
                            myAccountAttribute.getAttributesVOs().clear();
                            myAccountAttribute.setAttributesVOs(firstItems);
                            checkSlectedList();
                        }else{
                            canceledItems();
                        }
                        noBack = true;
                    }
                });

    }



    private void canceledItems(){
        for(SettingsAttributesVO settingsAttributeVO:settingsAttributesVO){
            settingsAttributeVO.setChecked(false);
        }
    }


    private void checkSlectedList(){
        if(accountAttributesTemp.size() != firstItems.size()){
            if(firstItems.isEmpty()){
                for (SettingsAttributesVO settingsAttributeVO : accountAttributesTemp){
                    settingsAttributeVO.setChecked(false);
                }
            }else{
                for (SettingsAttributesVO settingsAttributeVO : accountAttributesTemp) {
                    for (SettingsValuesVO settingsValuesVO : firstItems){
                        if(settingsAttributeVO.getmId().equals(settingsValuesVO.getmID())){
                           settingsAttributeVO.setChecked(true);
                        }else{
                            settingsAttributeVO.setChecked(false);
                        }
                    }

                }
            }
        }
    }

    private void selectedItemForServer(){

        selectedItem.clear();
        for(SettingsAttributesVO settingsAttributeVO :settingsAttributesVO) {

            if(settingsAttributeVO.isChecked()) {
                SettingsValuesVO selectedValue = new SettingsValuesVO(settingsAttributeVO.getmId(),
                        settingsAttributeVO.getmName(),
                        settingsAttributeVO.getmDescription(),
                        settingsAttributeVO.getmRank());
                selectedItem.add(selectedValue);
            }
        }
        myAccountAttribute = new SettingsAttributesVO();
        myAccountAttribute.setAttributesVOs(selectedItem);
    }

    private void convertData(){
        if(this.getClass().toString().equals(PreferencesCheckListFragment.class.toString())){
            selectedItemForServer();
        }
        /*else if(this.getClass().toString().equals(PreferencesSearchListFragment.class.toString())){
            myAccountAttribute.getAttributesVOs().clear();
            myAccountAttribute.setAttributesVOs(firstItems);
            checkSlectedList();
        }*/
    }


    private void savePreferencesData(){
        String guid = getSettingGuidSelected();

        ConnectionManager.getInstance(getActivity()).putAttributesValues(
                strId, strType, guid, selectedItem, new ConnectionManager.ServerRequestListener() {
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
    public void onDestroyView() {
        noBack = true;
        super.onDetach();
    }
}



