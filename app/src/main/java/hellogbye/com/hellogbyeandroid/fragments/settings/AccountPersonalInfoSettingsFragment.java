package hellogbye.com.hellogbyeandroid.fragments.settings;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.settingaccount.AccountSettingsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.AddCreditCardFragment;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 8/17/15.
 */
public class AccountPersonalInfoSettingsFragment extends HGBAbtsractFragment {



    public AccountPersonalInfoSettingsFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new AccountPersonalInfoSettingsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_personal_info_list_layout, container, false);

        UserData currentUser = getActivityInterface().getCurrentUser();

        final FontTextView companion_personal_settings_status = (FontTextView)rootView.findViewById(R.id.companion_personal_settings_status);
        companion_personal_settings_status.setText(currentUser.getTitle());

        FontEditTextView companion_personal_settings_name = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_name);
        companion_personal_settings_name.setText(currentUser.getFirstname());

        FontEditTextView companion_personal_settings_last = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_last);
        companion_personal_settings_last.setText(currentUser.getLastname());

        FontEditTextView companion_personal_settings_email = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_email);
        companion_personal_settings_email.setText(currentUser.getEmailaddress());

        FontEditTextView companion_personal_settings_date_of_birth = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_date_of_birth);
        companion_personal_settings_date_of_birth.setText(currentUser.getDob());

       final FontTextView companion_personal_settings_gender = (FontTextView)rootView.findViewById(R.id.companion_personal_settings_gender);
        companion_personal_settings_gender.setText(currentUser.getDob());


        FontEditTextView companion_personal_settings_phone_number = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_phone_number);
        companion_personal_settings_phone_number.setText(currentUser.getPhone());

        FontEditTextView companion_personal_settings_location_street = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_location_street);
        companion_personal_settings_location_street.setText(currentUser.getAddress());

        FontEditTextView companion_personal_settings_location_city = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_location_city);
        companion_personal_settings_location_city.setText(currentUser.getCity());

        FontEditTextView companion_personal_settings_location_mikud = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_location_mikud);
        companion_personal_settings_location_mikud.setText(currentUser.getPostalcode());

        FontEditTextView companion_personal_settings_location_country = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_location_country);
        companion_personal_settings_location_country.setText(currentUser.getCountry());

        final String[] titleArray = {"Mr", "Mrs", "Miss"};
        final String SLECT_TITLE ="Select Title";

        companion_personal_settings_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HGBUtility.showPikerDialog(companion_personal_settings_status, getActivity(),SLECT_TITLE,titleArray,
                        0,2);
            }
        });

        final String[] genderArray = {"M", "F"};
        final String GENDER_TITLE = "Select Gender";

        companion_personal_settings_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HGBUtility.showPikerDialog(companion_personal_settings_gender, getActivity(),GENDER_TITLE,genderArray,0,1);
            }
        });





        return rootView;
    }



}
