package hellogbye.com.hellogbyeandroid.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.List;

import hellogbye.com.hellogbyeandroid.R;


import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.PreferencesSettingsPreferencesCheckAdapter;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AcountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 8/17/15.
 */
public class PreferenceSettingsFragment extends HGBAbtsractFragment {


    private DynamicListView mDynamicListView;
    private PreferencesSettingsPreferencesCheckAdapter mAdapter;
    private List<AcountDefaultSettingsVO> accountDefaultSettings;
    private List<SettingsAttributeParamVO> accountSettingsAttributes;
    private EditText input;
    private View popup_preferences_layout;
    private ImageButton edit_preferences;


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


    private void createListAdapter() {

        mDynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mAdapter.getIsEditMode()){
                    FontTextView settings_flight_title = (FontTextView) view.findViewById(R.id.settings_check_name);
                    String clickedItemID = settings_flight_title.getTag().toString();

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
                    System.out.println("Kate delete pref");
                }else {
                    FontTextView settings_flight_title = (FontTextView) view.findViewById(R.id.settings_check_name);
                    String clickedItemID = settings_flight_title.getTag().toString();
                    getSettingsAttributes(clickedItemID);
                }
            }
        });

        mAdapter = new PreferencesSettingsPreferencesCheckAdapter(getActivity(), accountDefaultSettings);
        mDynamicListView.setAdapter(mAdapter);

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
            public void onItemClick(String guid) {
                mAdapter.setEditMode(true);
                mAdapter.notifyDataSetChanged();
                System.out.println("Kate preferences clicked");
            }
        });



//        View toolbar_layout = inflater.inflate(R.layout.toolbar_layout, container, false);
//
//        edit_preferences = (ImageButton)toolbar_layout.findViewById(R.id.edit_preferences);
//        edit_preferences.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("Kate edit_preferences 2");
//            }
//        });

        mDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_preferences_drag_list);
        popup_preferences_layout = inflater.inflate(R.layout.preferences_add_new_preference, null);
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


    View.OnClickListener editPreferencesClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            System.out.println("Kate edit preferences");
        }
    };

    private void editSettingsPreferencesPopUp() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

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
                        dialog.cancel();


                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        input.setText("");

                        ((ViewGroup) popup_preferences_layout.getParent()).removeView(popup_preferences_layout);
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
                    List<AcountDefaultSettingsVO> accountDefault = (List<AcountDefaultSettingsVO>) data;
                    accountDefaultSettings.add(accountDefault.get(0));
                }
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });

    }


}
