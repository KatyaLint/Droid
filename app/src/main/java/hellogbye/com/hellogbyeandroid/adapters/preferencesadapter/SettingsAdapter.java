package hellogbye.com.hellogbyeandroid.adapters.preferencesadapter;

import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;

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
}
