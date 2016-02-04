package hellogbye.com.hellogbyeandroid.fragments.settings;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.LoginTest;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.settingaccount.AccountSettingsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.BookingRequest;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 8/17/15.
 */
public class AccountSettingsFragment extends HGBAbtsractFragment {


    private ImageView account_details_image;
    private FontTextView account_settings_details_name;
    private FontTextView account_settings_details_city;

    public AccountSettingsFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new AccountSettingsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    private void getUserData(){
        ConnectionManager.getInstance(getActivity()).getUserProfile(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserData mCurrentUser = (UserData) data;
                getActivityInterface().setCurrentUser(mCurrentUser);
                initializeUserData();

            }

            @Override
            public void onError(Object data) {

            }
        });
}

    private void initializeUserData(){
        UserData currentUser = getActivityInterface().getCurrentUser();

        HGBUtility.loadRoundedImage(getActivity().getApplicationContext(),currentUser.getAvatar(),account_details_image);

        String userName = currentUser.getTitle() +" "+ currentUser.getFirstname() + " " + currentUser.getLastname();
        account_settings_details_name.setText(userName);


        String userCity = currentUser.getCity() + " " + currentUser.getAddress();
        account_settings_details_city.setText(userCity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.account_settings_main_layout, container, false);



        String[] account_settings = getResources().getStringArray(R.array.settings_account);

        RecyclerView account_settings_preferences_list = (RecyclerView)rootView.findViewById(R.id.account_settings_preferences_list);

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View promptsView = li.inflate(R.layout.popup_layout_log_out, null);
        final EditText input = (EditText) promptsView
                .findViewById(R.id.companion_editTextDialog);

        final FontTextView text = (FontTextView) promptsView
                .findViewById(R.id.component_popup_text);


        LinearLayout btn_account_logout_button = (LinearLayout)rootView.findViewById(R.id.account_logout_button);
        btn_account_logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
             //logout
                //popup
                text.setVisibility(View.VISIBLE);
                text.setText(getResources().getString(R.string.component_log_sure));

                HGBUtility.showAlertPopUp(getActivity(), input, promptsView, getResources().getString(R.string.component_log_out),
                        new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                getActivityInterface().gotToStartMenuActivity();
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        });
            }

        });


        AccountSettingsAdapter accountSettingsAdapter = new AccountSettingsAdapter(account_settings,getActivity());
        account_settings_preferences_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //account_settings_preferences_list.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        account_settings_preferences_list.setLayoutManager(mLayoutManager);

        account_settings_preferences_list.setAdapter(accountSettingsAdapter);

        account_details_image = (ImageView) rootView.findViewById(R.id.account_details_image);


        account_settings_details_name = (FontTextView) rootView.findViewById(R.id.account_settings_details_name);

        account_settings_details_city = (FontTextView) rootView.findViewById(R.id.account_settings_details_city);

        getUserData();
      //  initializeUserData();

        accountSettingsAdapter.SetOnItemClickListener(new AccountSettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                switch (position){
                    case 0:
                        getActivityInterface().goToFragment(ToolBarNavEnum.COMPANIONS_PERSONAL_DETAILS.getNavNumber(), null);
                        //AccountPersonalInfoSettingsFragment
                        //personal information
                        break;
                    case 1:
                        //emails
                        break;
                    case 2:
                        //manage payment cards
                        break;
                    case 3:
                        //membership
                        break;
                    case 4:
                        //help & feedback
                        break;
                }



            }
        });


        getActivityInterface().loadJSONFromAsset();
        //getCountries();

        return rootView;
    }


    private void getCountries(){
        ConnectionManager.getInstance(getActivity()).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                //responceText.setText((String) data);
                BookingRequest bookingrequest = (BookingRequest)data;
                getActivityInterface().setEligabileCountries(bookingrequest.getCountries());
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                try {
                    errorHelper.show(getFragmentManager(), (String) data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
