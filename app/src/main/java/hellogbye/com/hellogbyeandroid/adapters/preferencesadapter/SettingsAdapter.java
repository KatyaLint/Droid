package hellogbye.com.hellogbyeandroid.adapters.preferencesadapter;

import java.util.List;

import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;

/**
 * Created by nyawka on 3/9/16.
 */
public interface SettingsAdapter {
    void setEditMode(boolean b);

    void notifyDataSetChanged();

    boolean getIsEditMode();

    void setClickedLineCB(PreferenceSettingsFragment.ListLineClicked listLineClicked);

    void setSelectedRadioButtonListener(PreferenceSettingsFragment.ListRadioButtonClicked listRadioButtonClicked);

    void selectedItemID(String id);

    void updateItems(List<AccountDefaultSettingsVO> accountAttributes);

    void updateItem(AccountDefaultSettingsVO accountAttribute);
}
