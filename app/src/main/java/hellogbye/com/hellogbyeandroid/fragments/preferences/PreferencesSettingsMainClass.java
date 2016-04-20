package hellogbye.com.hellogbyeandroid.fragments.preferences;

import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;

/**
 * Created by nyawka on 12/2/15.
 */
public class PreferencesSettingsMainClass extends HGBAbstractFragment {

    public boolean noBack = false;
    public void backOnListClicked(final String strType,final String strId,final List<SettingsValuesVO> accountAttributesVO) {

        ((MainActivity) getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {
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
                                HGBErrorHelper errorHelper = new HGBErrorHelper();
                                errorHelper.setMessageForError((String) data);
                                errorHelper.show(getActivity().getFragmentManager(), (String) data);
                            }
                        });
            }
        });
    }

    @Override
    public void onDestroyView() {
        noBack = true;
        super.onDetach();
    }
}



