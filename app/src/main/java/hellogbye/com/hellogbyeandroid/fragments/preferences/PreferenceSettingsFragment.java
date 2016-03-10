package hellogbye.com.hellogbyeandroid.fragments.preferences;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;


import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsPreferencesCheckAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsRadioButtonAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.SettingsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AcountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.PinnedHeaderListView;

/**
 * Created by arisprung on 8/17/15.
 */
public class PreferenceSettingsFragment extends HGBAbtsractFragment {


    private DynamicListView mDynamicListView;
    private SettingsAdapter mAdapter;
    private List<AcountDefaultSettingsVO> accountDefaultSettings;
    private List<SettingsAttributeParamVO> accountSettingsAttributes;
    private EditText input;
    private View popup_preferences_layout;
    private static int MIN_PREFERENCE_SIZE = 1;
    private View preferences_at_least_one_preference;
    private String edit_mode;
    private ListView tempListView;

    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferenceSettingsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void adapterClicked(String clickedItemID){
        if(mAdapter.getIsEditMode()){


            // List<SettingsAttributeParamVO> accountAttributes = getActivityInterface().getAccountSettingsAttribute();
            for( AcountDefaultSettingsVO accountAttribute:accountDefaultSettings){
                if(accountAttribute.getmId().equals(clickedItemID)){
                    if(accountAttribute.isChecked()){
                        accountAttribute.setChecked(false);
                    }else{
                        accountAttribute.setChecked(true);
                    }
                    break;
                }

            }
            mAdapter.notifyDataSetChanged();

        }else {
            getSettingsAttributes(clickedItemID);
        }
    }


public interface ListLineClicked{
    void clickedItem(String itemID);
}

    private void createListAdapter() {

        //Factory implementation :)
        Bundle args = getArguments();
        if (args != null) {
            edit_mode = args.getString("edit_mode");
            if (edit_mode != null && edit_mode.equals("true")) {
                mAdapter = new PreferencesSettingsRadioButtonAdapter(getActivity(), accountDefaultSettings);
                mAdapter.setClickedLineCB(new ListLineClicked(){

                    @Override
                    public void clickedItem(String itemID) {
                        adapterClicked(itemID);
                    }
                });
                mDynamicListView.setAdapter((PreferencesSettingsRadioButtonAdapter)mAdapter);

            }else{
                mAdapter = new PreferencesSettingsPreferencesCheckAdapter(getActivity(), accountDefaultSettings);
                mDynamicListView.setAdapter((PreferencesSettingsPreferencesCheckAdapter)mAdapter);
                mAdapter.setClickedLineCB(new ListLineClicked(){

                    @Override
                    public void clickedItem(String itemID) {
                        adapterClicked(itemID);
                    }
                });
            }
        }

    }

    private void getSettingsAttributes(final String clickedAttributeID) {
        ConnectionManager.getInstance(getActivity()).getUserSettingsAttributes(clickedAttributeID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {

                    accountSettingsAttributes = (List<SettingsAttributeParamVO>) data;//gson.fromJson((String) data, listType);
                    getActivityInterface().setAccountSettingsAttribute(accountSettingsAttributes);
                    Bundle args = new Bundle();
                    args.putString("setting_att_id", clickedAttributeID);
                    getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_TAB_SETTINGS.getNavNumber(), args);
                }
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(String guid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_list_layout, container, false);

        ((MainActivity) getActivity()).setEditClickCB(new OnItemClickListener(){
            @Override
            public void onItemClick(String option) {

                if(option.equals("delete")){
                    List<AcountDefaultSettingsVO> accountAttributes = new ArrayList<AcountDefaultSettingsVO>();
                    accountAttributes.addAll(accountDefaultSettings);
                    for(AcountDefaultSettingsVO accountAttribute :accountAttributes){
                        if(accountAttribute.isChecked()){
                            if(accountDefaultSettings.size() <= MIN_PREFERENCE_SIZE){
                                //popup
                                atLeastOnePreference();
                                break;
                            }
                            accountDefaultSettings.remove(accountAttribute);
                            deletePreference(accountAttribute.getmId());
                        }
                    }
                }
                else if (option.equals("edit")){
                    mAdapter.setEditMode(true);
                }else if(option.equals("done")){
                    mAdapter.setEditMode(false);
                }
                mAdapter.notifyDataSetChanged();
            }
        });


        mDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_preferences_drag_list);
        popup_preferences_layout = inflater.inflate(R.layout.preferences_add_new_preference, null);
        preferences_at_least_one_preference = inflater.inflate(R.layout.preferences_at_least_one_preference, null);


        tempListView = (ListView)rootView.findViewById(R.id.tempListView);

        Button addNewPreferencesButton = (Button) rootView.findViewById(R.id.add_preferences);
        input = (EditText) popup_preferences_layout.findViewById(R.id.editTextDialog);

        addNewPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSettingsPreferencesPopUp();
            }
        });

        ConnectionManager.getInstance(getActivity()).getUserSettingsDefault(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {

                    accountDefaultSettings = (List<AcountDefaultSettingsVO>) data;
                    createListAdapter();

                }
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });




        return rootView;
    }


    private void deletePreference(String preferenceId){
        ConnectionManager.getInstance(getActivity()).deletePreferenceProfileId(preferenceId,new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });

    }


    private void atLeastOnePreference(){

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle(R.string.preferences_at_least_one_preference)
                .setView(preferences_at_least_one_preference)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((ViewGroup) preferences_at_least_one_preference.getParent()).removeView(popup_preferences_layout);
                        dialog.cancel();
                    }
                })

                .create().show();

    }

    private void editSettingsPreferencesPopUp() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(false);
        alert.setTitle(R.string.preferences_add_new_preferences)
                .setView(popup_preferences_layout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = input.getText().toString();
                        if (newName.length() != 0) {
                            popUpConnection(newName);
                        }
                        input.setText("");
                        ((ViewGroup) popup_preferences_layout.getParent()).removeView(popup_preferences_layout);
                        IBinder token = input.getWindowToken();
                        ( (InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                        dialog.cancel();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        input.setText("");

                        ((ViewGroup) popup_preferences_layout.getParent()).removeView(popup_preferences_layout);
                        IBinder token = input.getWindowToken();
                        ( (InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                        dialog.cancel();
                    }
                })
                .create().show();
    }


    private void popUpConnection(String profileName) {
        ConnectionManager.getInstance(getActivity()).postNewPreferenceProfile(profileName, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    AcountDefaultSettingsVO accountDefault = (AcountDefaultSettingsVO) data;
                    accountDefaultSettings.add(accountDefault);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });

    }


}
