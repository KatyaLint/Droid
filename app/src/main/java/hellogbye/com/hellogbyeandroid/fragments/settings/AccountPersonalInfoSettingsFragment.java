package hellogbye.com.hellogbyeandroid.fragments.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.settingaccount.AccountSettingsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 8/17/15.
 */
public class AccountPersonalInfoSettingsFragment extends HGBAbstractFragment {


    private EditText input;
    private View promptsView;
    private FontTextView text;
    private String GENDER = "Gender";
    final String[] titleArray = {"Mr", "Mrs", "Miss"};
    final String SELECT_TITLE = "Select Title";
    final String[] genderArray = {"M", "F"};
    final String GENDER_TITLE = "Select Gender";

    private String SELECT_PROVINCE = "Select Province";


    private LinearLayout companion_personal_settings_email_add_another;
    private FontEditTextView companion_personal_settings_name;
    private FontEditTextView companion_personal_settings_last;
    private FontEditTextView companion_personal_settings_date_of_birth;
 //   private FontTextView companion_personal_settings_email;
    private FontTextView companion_personal_settings_gender;
    private FontEditTextView companion_personal_settings_phone_number;
    private FontEditTextView companion_personal_settings_location_street;
    private FontEditTextView companion_personal_settings_location_city;
    private FontEditTextView companion_personal_settings_location_postcode;
    private FontEditTextView companion_personal_settings_location_country;
    private FontEditTextView companion_personal_settings_location_province;

    private UserDataVO currentUser;
    private FontTextView account_done_editting;
    private UserDataVO newUser;
    private int maxValueForStateDialog;
    private String[] stateArray;
    private int countryMaxValueSize;
    private String[] countryarray;
    private String mCounterPicked;
    private LinearLayout personal_settings_change_password_ll;
    private FontEditTextView change_pswd_current_pswd;
    private FontEditTextView change_pswd_new_pswd;
    private FontEditTextView change_pswd_confirm_new_pswd;
    private BookingRequestVO bookingResponse;
    private RecyclerView account_settings_email_list;
    private Bundle bundle;

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


        bundle = new Bundle();
        View rootView = inflater.inflate(R.layout.account_personal_info_list_layout, container, false);

        currentUser = getActivityInterface().getCurrentUser();



        companion_personal_settings_email_add_another = (LinearLayout) rootView.findViewById(R.id.companion_personal_add_email_ll);

        companion_personal_settings_name = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_name);
        companion_personal_settings_name.setText(currentUser.getFirstname());

        companion_personal_settings_last = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_last);
        companion_personal_settings_last.setText(currentUser.getLastname());


        account_settings_email_list = (RecyclerView)rootView.findViewById(R.id.account_settings_email_list);
        account_settings_email_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        account_settings_email_list.setLayoutManager(mLayoutManager);


        ArrayList<AccountsVO> accounts = getActivityInterface().getAccounts();
       final String[] account_settings = new String[accounts.size()];
        for(int i=0;i<accounts.size();i++){
            account_settings[i] = accounts.get(i).getEmail();
        }


        AccountSettingsAdapter accountSettingsAdapter = new AccountSettingsAdapter(account_settings,getActivity());
        account_settings_email_list.setAdapter(accountSettingsAdapter);

      /*  companion_personal_settings_email = (FontTextView)rootView.findViewById(R.id.companion_personal_settings_email);
        companion_personal_settings_email.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCE_SETTINGS_EMAILS.getNavNumber(), null);
            }
        });*/



        accountSettingsAdapter.SetOnItemClickListener(new AccountSettingsAdapter.OnItemClickListener() {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              String account_setting = account_settings[position];
                                                              bundle.putString(HGBConstants.PERSONAL_INFO_EMAIL, account_setting);
                                                              getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCE_SETTINGS_EMAILS.getNavNumber(), bundle);
                                                          }
                                                      });

        companion_personal_settings_date_of_birth = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_date_of_birth);
        companion_personal_settings_date_of_birth.setText(HGBUtility.parseDateToddMMyyyyForPayment(currentUser.getDob()));
        companion_personal_settings_location_province = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_state);
        companion_personal_settings_location_province.setText(currentUser.getState());


        companion_personal_settings_gender = (FontTextView)rootView.findViewById(R.id.companion_personal_settings_gender);

       if(currentUser.getGender() != null && !currentUser.getGender().isEmpty()){
            GENDER = currentUser.getGender();
        }
        companion_personal_settings_gender.setText(GENDER);


        companion_personal_settings_phone_number = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_phone_number);
        companion_personal_settings_phone_number.setText(currentUser.getPhone());

        companion_personal_settings_location_street = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_location_street);
        companion_personal_settings_location_street.setText(currentUser.getAddress());

        companion_personal_settings_location_city = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_location_city);
        companion_personal_settings_location_city.setText(currentUser.getCity());

        companion_personal_settings_location_postcode = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_location_post_code);
        companion_personal_settings_location_postcode.setText(currentUser.getPostalcode());

        companion_personal_settings_location_country = (FontEditTextView)rootView.findViewById(R.id.companion_personal_settings_location_country);
        companion_personal_settings_location_country.setText(currentUser.getCountry());

        personal_settings_change_password_ll = (LinearLayout)rootView.findViewById(R.id.personal_settings_change_password_ll);

        LayoutInflater li = LayoutInflater.from(getActivity());
        promptsView = li.inflate(R.layout.popup_change_password_layout, null);

        change_pswd_current_pswd = (FontEditTextView)promptsView.findViewById(R.id.change_pswd_current_pswd);
        change_pswd_new_pswd = (FontEditTextView)promptsView.findViewById(R.id.change_pswd_new_pswd);
        change_pswd_confirm_new_pswd = (FontEditTextView) promptsView.findViewById(R.id.change_pswd_confirm_new_pswd);

        final FontEditTextView[] inputs = new FontEditTextView[]{change_pswd_current_pswd, change_pswd_new_pswd, change_pswd_confirm_new_pswd};

        getStaticBooking();



        text = (FontTextView) promptsView
                .findViewById(R.id.component_popup_text);

        personal_settings_change_password_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HGBUtility.showAlertPopAddCompanion(getActivity(), inputs, promptsView, "", new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        String[] parts = inputItem.split("&");
                        String userName = getActivityInterface().getAccounts().get(0).getEmail();
                        String prevpassword = parts[0];
                        String password = parts[1];
                        String confirmpassword = parts[2];


                        ConnectionManager.getInstance(getActivity()).postChangePasswordWithOldPassword( userName, confirmpassword, prevpassword,  password, new ConnectionManager.ServerRequestListener() {
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
                    public void itemCanceled() {

                    }
                });

            }
        });




        companion_personal_settings_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            HGBUtility.showPikerDialog(companion_personal_settings_gender, getActivity(), GENDER_TITLE, genderArray, 0, 1, null,true);
            }
        });



        companion_personal_settings_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HGBUtility.showDateDialog(getActivity(), companion_personal_settings_date_of_birth);
                //  showDateDialog();
            }
        });



        companion_personal_settings_email_add_another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCE_SETTINGS_EMAILS.getNavNumber(), null);

            }
        });


        account_done_editting = (FontTextView) rootView.findViewById(R.id.account_done_editing);
        account_done_editting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDisable) {
                    onBackPressed();
                    isDisable = true;
                }
            }
        });


        changeNewUser();


        countryMaxValueSize = getActivityInterface().getEligabileCountries().size();
        countryarray = new String[getActivityInterface().getEligabileCountries().size()];
        for (int i = 0; i < getActivityInterface().getEligabileCountries().size(); i++) {
            countryarray[i] = getActivityInterface().getEligabileCountries().get(i).getName();
        }

       // selectStates("0");


        setClickListner();
        return rootView;
    }

    boolean isDisable = false;


    private void setClickListner() {


        companion_personal_settings_location_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
                String[] countryarray = new String[countries.size()];
                for (int i = 0; i < countries.size(); i++) {
                    countryarray[i] = countries.get(i).getName();
                }

                HGBUtility.showPikerDialogEditText(companion_personal_settings_location_country, getActivity(), "Choose country",
                        countryarray, 0, countries.size() - 1, new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                for (CountryItemVO country : countries) {
                                    if (country.getName().equals(inputItem)) {
                                        companion_personal_settings_location_country.setTag(country.getCode());
                                        getStaticProvince();

                                        break;
                                    }
                                }

                            }

                            @Override
                            public void itemCanceled() {

                            }
                        }, false);


            }
        });

        companion_personal_settings_location_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStaticProvince();
            }
        });

//        companion_personal_settings_location_city.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (companion_personal_settings_location_country.getTag() != null) {
//                    mCounterPicked = companion_personal_settings_location_country.getTag().toString();
//                    selectStates(mCounterPicked);
//                }
//                HGBUtility.showPikerDialogEditText(companion_personal_settings_location_city, getActivity(), SELECT_PROVINCE, stateArray, 0, maxValueForStateDialog - 1, null,true);
//                if (companion_personal_settings_location_city.getTag() != null) {
//                  //  mStatePicked = companion_personal_settings_location_city.getTag().toString();
//                }
//                // stateDialog.show();
//            }
//        });
    }

    private void selectStates(String countryPicked){
        int countryPick = Integer.parseInt(countryPicked);
        ArrayList<CountryItemVO> countries = getActivityInterface().getEligabileCountries();
        ArrayList<ProvincesItem> province = countries.get(countryPick).getProvinces();
        maxValueForStateDialog = province.size();
        stateArray = new String[getActivityInterface().getEligabileCountries().get(countryPick).getProvinces().size()];
        ArrayList<ProvincesItem> provinces = getActivityInterface().getEligabileCountries().get(countryPick).getProvinces();
        for (int i = 0; i < provinces.size(); i++) {
            stateArray[i] = provinces.get(i).getProvincename();
        }
    }



    private void sendNewEmailToServer(final String newEmail) {
        //http://cnc.hellogbye.com/cnc/rest/UserProfile/Accounts

        ConnectionManager.getInstance(getActivity()).postUserProfileAccountsWithEmail(newEmail, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                input.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                String titleString = newEmail + " " + getResources().getString(R.string.component_confirmation_email);
                HGBUtility.showAlertPopUpOneButton(getActivity(), input, promptsView, titleString,
                        new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {

                            }

                            @Override
                            public void itemCanceled() {

                            }
                        });
            }

            @Override
            public void onError(Object data) {
                isDisable = false;
                ErrorMessage(data);
            }
        });
    }

    private void getPersonalInfoBookingOptions(){
       // http://cnc.hellogbye.com/cnc/rest/Statics/BookingOptions
        ConnectionManager.getInstance(getActivity()).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
           //     responceText.setText((String) data);
                BookingRequestVO bookingrequest = (BookingRequestVO)data;
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);

            }
        });
    }

    private void changeNewUser() {
        newUser = new UserDataVO(currentUser.getUserprofileid(), currentUser.getEmailaddress(), currentUser.getFirstname(),
                currentUser.getLastname(), currentUser.getDob(), currentUser.getCountry(),
                currentUser.getAddress(), currentUser.getCity(), currentUser.getState(),
                currentUser.getPostalcode(), currentUser.getAvatar(), currentUser.ispremiumuser(), currentUser.getPaxid(), currentUser.getPhone(),
                currentUser.getTitle());
    }





    private void onBackPressed() {
        //get all user information and send to server



        String str_companion_personal_settings_name = companion_personal_settings_name.getText().toString();
        if (!str_companion_personal_settings_name.isEmpty()) {
            newUser.setFirstname(str_companion_personal_settings_name);
        }

        String str_companion_personal_settings_last = companion_personal_settings_last.getText().toString();
        if (!str_companion_personal_settings_last.isEmpty()) {
            newUser.setLastname(str_companion_personal_settings_last);
        }


        String str_companion_personal_settings_postal= companion_personal_settings_location_postcode.getText().toString();
        if (!str_companion_personal_settings_postal.isEmpty()) {
            newUser.setPostalcode(str_companion_personal_settings_postal);
        }

        String str_companion_personal_settings_date_of_birth = companion_personal_settings_date_of_birth.getText().toString();
        if (!str_companion_personal_settings_date_of_birth.isEmpty()) {
            newUser.setDob(str_companion_personal_settings_date_of_birth);
        }

       /* String str_companion_personal_settings_email = companion_personal_settings_email.getText().toString();
        if (!str_companion_personal_settings_email.isEmpty()) {
            newUser.setEmailaddress(str_companion_personal_settings_email);
        }*/


        String str_companion_personal_settings_gender = companion_personal_settings_gender.getText().toString();
        if (!str_companion_personal_settings_gender.isEmpty()) {
            newUser.setGender(str_companion_personal_settings_gender);
        }


        String str_companion_personal_settings_phone_number = companion_personal_settings_phone_number.getText().toString();
        if (!str_companion_personal_settings_phone_number.isEmpty()) {
            newUser.setPhone(str_companion_personal_settings_phone_number);
        }

        String str_companion_personal_settings_location_street = companion_personal_settings_location_street.getText().toString();
        if (!str_companion_personal_settings_location_street.isEmpty()) {
            newUser.setAddress(str_companion_personal_settings_location_street);
        }

        String str_companion_personal_settings_location_city = companion_personal_settings_location_city.getText().toString();
        if (!str_companion_personal_settings_location_city.isEmpty()) {
            newUser.setCity(str_companion_personal_settings_location_city);
        }


        String str_companion_personal_settings_location_country = companion_personal_settings_location_country.getTag().toString();
        if (!str_companion_personal_settings_location_country.isEmpty()) {
            newUser.setCountry(str_companion_personal_settings_location_country);
        }

        String str_companion_personal_settings_location_province = companion_personal_settings_location_province.getTag().toString();
        if (!str_companion_personal_settings_location_province.isEmpty()) {
            newUser.setState(str_companion_personal_settings_location_province);
        }
        //AccountsVO account = getActivityInterface().getAccounts().get(0);
        newUser.setTitle(currentUser.getTitle());




        ConnectionManager.getInstance(getActivity()).putUserSettings(newUser, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                getFragmentManager().popBackStack();
            }

            @Override
            public void onError(Object data) {
                isDisable = false;
                ErrorMessage(data);
            }
        });
    }


    private void getStaticBooking() {

        ConnectionManager.getInstance(getActivity()).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                bookingResponse = (BookingRequestVO) data;
                Log.i("",bookingResponse.toString());

                setCountryAndState();

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void setCountryAndState() {

        for( CountryItemVO country:bookingResponse.getCountries()){

            if(country.getCode().equals(currentUser.getCountry())){
                companion_personal_settings_location_country.setText(country.getName());
                companion_personal_settings_location_country.setTag(country.getCode());
                String countryID;
                if( companion_personal_settings_location_country.getTag() == null){
                    countryID =  companion_personal_settings_location_country.getText().toString();
                    companion_personal_settings_location_country.setTag(countryID);
                    if (countryID.equals("Canada")) {
                        countryID = "CA";
                    } else if (countryID.equals("UnitedStates")) {
                        countryID = "US";
                    }
                }else{
                    countryID = (String) companion_personal_settings_location_country.getTag();
                }

                ConnectionManager.getInstance(getActivity()).getStaticBookingProvince(countryID, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        List<ProvincesItem> provinceItems = (List<ProvincesItem>) data;
                        for(ProvincesItem province: provinceItems){
                            if(currentUser.getState().equals(province.getProvincecode())){
                                companion_personal_settings_location_province.setText(province.getProvincename());
                                companion_personal_settings_location_province.setTag(province.getProvincecode());
                            }
                        }
                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });

            }
        }
    }

    private void getStaticProvince() {
        String countryID;
        if( companion_personal_settings_location_country.getTag() == null){
            countryID =  companion_personal_settings_location_country.getText().toString();
            if (countryID.equals("Canada")) {
                countryID = "CA";
            } else if (countryID.equals("UnitedStates")) {
                countryID = "US";
            }
        }else{
            countryID = (String) companion_personal_settings_location_country.getTag();
        }

        ConnectionManager.getInstance(getActivity()).getStaticBookingProvince(countryID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<ProvincesItem> provinceItems = (List<ProvincesItem>) data;
                if (provinceItems.size() > 0) {
                    setDropDownItems(provinceItems);
                    companion_personal_settings_location_province.setVisibility(View.VISIBLE);
                } else {
                    companion_personal_settings_location_province.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void setDropDownItems(final List<ProvincesItem> provinceItems) {
        String[] countryarray = new String[provinceItems.size()];
        for (int i = 0; i < provinceItems.size(); i++) {
            countryarray[i] = provinceItems.get(i).getProvincename();
        }

        HGBUtility.showPikerDialogEditText(companion_personal_settings_location_province, getActivity(), "Choose province",
                countryarray, 0, provinceItems.size() - 1, new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        for (ProvincesItem province : provinceItems) {
                            if (province.getProvincename().equals(inputItem)) {
                                companion_personal_settings_location_province.setText(province.getProvincename());
                                companion_personal_settings_location_province.setTag(province.getProvincecode());
                                break;
                            }
                        }

                    }

                    @Override
                    public void itemCanceled() {

                    }
                }, false);

    }


}





