package hellogbye.com.hellogbyeandroid.fragments.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.settingaccount.AccountSettingsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 8/17/15.
 */
public class AccountSettingsFragment extends HGBAbtsractFragment {


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_settings_main_layout, container, false);

        UserData currentUser = getActivityInterface().getCurrentUser();

        String[] account_settings = getResources().getStringArray(R.array.settings_account);

        RecyclerView account_settings_preferences_list = (RecyclerView)rootView.findViewById(R.id.account_settings_preferences_list);


        AccountSettingsAdapter accountSettingsAdapter = new AccountSettingsAdapter(account_settings,getActivity());
        account_settings_preferences_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        account_settings_preferences_list.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        account_settings_preferences_list.setLayoutManager(mLayoutManager);

        account_settings_preferences_list.setAdapter(accountSettingsAdapter);

        ImageView account_details_image = (ImageView) rootView.findViewById(R.id.account_details_image);
        HGBUtility.loadRoundedImage(getActivity().getApplicationContext(),currentUser.getAvatar(),account_details_image);


        FontTextView account_settings_details_name = (FontTextView) rootView.findViewById(R.id.account_settings_details_name);
        String userName = currentUser.getTitle() +" "+ currentUser.getFirstname() + " " + currentUser.getLastname();
        account_settings_details_name.setText(userName);


        FontTextView account_settings_details_city = (FontTextView) rootView.findViewById(R.id.account_settings_details_city);
        String userCity = currentUser.getCity() + " " + currentUser.getAddress();
        account_settings_details_city.setText(userCity);


        accountSettingsAdapter.SetOnItemClickListener(new AccountSettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("Kate position =" + position);

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


        return rootView;
    }
}
