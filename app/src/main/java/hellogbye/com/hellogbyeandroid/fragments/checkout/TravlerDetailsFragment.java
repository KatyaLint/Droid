package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/24/15.
 */
public class TravlerDetailsFragment extends HGBAbtsractFragment {


    private FontTextView mTitle;
    private EditText mFirstName;
    private EditText mLastName;
    private FontTextView mDOB;
    private FontTextView mSave;

    private FontTextView mGender;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mAddress;
    private EditText mCity;
    private FontTextView mCountry;
    private EditText mPostalCode;
    private FontTextView mState;
    private UserData mUser;
    private AlertDialog mCountryDialog;
    private AlertDialog stateDialog;

    private NumberPicker countryPicker;
    private NumberPicker statePicker;
    private NumberPicker genderPicker;
    private NumberPicker titlePicker;
    private String SELECT_PROVINCE = "Select Province";

   // private HashMap<String, ArrayList<ProvincesItem>> list = new HashMap<>();



    private AlertDialog countryDialog;
    private int maxValueForStateDialog;
    private String[] stateArray;
    private int countryMaxValueSize;
    private String[] countryarray;
    private String mCounterPicked = "0";
    private String mStatePicked ="0";


    public static Fragment newInstance(int position) {
        Fragment fragment = new TravlerDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.travler_detail_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFlowInterface().loadJSONFromAsset();
        mTitle = (FontTextView) view.findViewById(R.id.travel_detail_title);
        mFirstName = (EditText) view.findViewById(R.id.travel_detail_first_name);
        mLastName = (EditText) view.findViewById(R.id.travel_detail_last_name);
        mDOB = (FontTextView) view.findViewById(R.id.travel_detail_dob);
        mGender = (FontTextView) view.findViewById(R.id.travel_detail_gender);
        mEmail = (EditText) view.findViewById(R.id.travel_detail_email);
        mPhone = (EditText) view.findViewById(R.id.travel_detail_phone);
        mAddress = (EditText) view.findViewById(R.id.travel_detail_adress);
        mCity = (EditText) view.findViewById(R.id.travel_detail_city);
        mCountry = (FontTextView) view.findViewById(R.id.travel_detail_country);
        mPostalCode = (EditText) view.findViewById(R.id.travel_detail_postal_code);
        mState = (FontTextView) view.findViewById(R.id.travel_detail_province);
        mSave = (FontTextView) view.findViewById(R.id.travler_detail_save);

        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt("user_json_position");
            mUser = getActivityInterface().getListUsers().get(position);
        }

        if (mUser != null) {
            initUser();
        }

        setSaveButton();

       // buildCountryDialog();




        countryMaxValueSize = getActivityInterface().getEligabileCountries().size();
        countryarray = new String[getActivityInterface().getEligabileCountries().size()];
        for (int i = 0; i < getActivityInterface().getEligabileCountries().size(); i++) {
            countryarray[i] = getActivityInterface().getEligabileCountries().get(i).getName();
        }
        mCountry.setTag(0);
        mState.setTag(0);
        selectStates("0");



//        HGBUtility.buildStateDialog(mState, getActivity(), maxValueForStateDialog, stateArray, SELECT_PROVINCE);
//        HGBUtility.buildStateDialog(mCountry, getActivity(), countryMaxValueSize, countryarray, SELECT_PROVINCE);

      //  buildStateDialog();
        setClickListner();

//TODO need to load provnices from API not file
//        ConnectionManager.getInstance(getActivity()).getBookingOptions(new ConnectionManager.ServerRequestListener() {
//            @Override
//            public void onSuccess(Object data) {
//
//
//
//
//
//            }
//
//            @Override
//            public void onError(Object data) {
//                HGBErrorHelper errorHelper = new HGBErrorHelper();
//                errorHelper.show(getFragmentManager(), (String) data);
//            }
//        });

    }
    final String[] titleArray = {"Mr", "Mrs", "Miss"};
    final String SLECT_TITLE ="Select Title";


    private void selectStates(String countryPicked){
        int countryPick = Integer.parseInt(countryPicked);
        maxValueForStateDialog = getActivityInterface().getEligabileCountries().get(countryPick).getProvinces().size();
        stateArray = new String[getActivityInterface().getEligabileCountries().get(countryPick).getProvinces().size()];
        ArrayList<ProvincesItem> provinces = getActivityInterface().getEligabileCountries().get(countryPick).getProvinces();
        for (int i = 0; i < provinces.size(); i++) {
            stateArray[i] = provinces.get(i).getName();
        }
    }

    private void setClickListner() {

        mCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   HGBUtility.showPikerDialog(mCountry, getActivity(), SELECT_PROVINCE,stateArray, 0,maxValueForStateDialog);

                HGBUtility.showPikerDialog(mCountry,getActivity(), SELECT_PROVINCE,
                        countryarray, 0, countryMaxValueSize -1,null, true);
                if(mCountry.getTag() != null) {
                    mCounterPicked = mCountry.getTag().toString();
                }
               // countryDialog.show();
            }
        });

        mState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCountry.getTag() != null) {
                    mCounterPicked = mCountry.getTag().toString();
                    selectStates(mCounterPicked);
                }
                HGBUtility.showPikerDialog(mState, getActivity(), SELECT_PROVINCE, stateArray, 0,maxValueForStateDialog - 1, null, true);
                if(mState.getTag() != null){
                    mStatePicked = mState.getTag().toString();
                }
               // stateDialog.show();
            }
        });

        mGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  showGenderDialog();
                HGBUtility.showPikerDialog(mTitle, getActivity(),GENDER_TITLE,genderArray,0,2, null, true);
            }
        });

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HGBUtility.showPikerDialog(mTitle, getActivity(),SLECT_TITLE,titleArray,0,1, null, true);



               // showTitleDialog();
            }
        });

        mDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HGBUtility.showDateDialog(getActivity(),mDOB);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUser.setTitle(mTitle.getText().toString());
                mUser.setGender(mGender.getText().toString());
                mUser.setAddress(mAddress.getText().toString());

                if (mCountry.getText().toString().equals("Canada")) {
                    mUser.setCountry("CN");
                } else if (mCountry.getText().toString().equals("UnitedStates")) {
                    mUser.setCountry("US");
                }

                mUser.setState(getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().get(statePicker.getValue()).getCode());
                mUser.setPostalcode(mPostalCode.getText().toString());
                mUser.setDob(mDOB.getText().toString());
                mUser.setCity(mCity.getText().toString());
                mUser.setFirstname(mFirstName.getText().toString());
                mUser.setLastname(mLastName.getText().toString());
                mUser.setPhone(mPhone.getText().toString());
                mUser.setEmailaddress(mEmail.getText().toString());

                if (mUser.getUserprofileid().equals(mUser.getPaxid())) {

                    Toast.makeText(getActivity().getApplicationContext(), "Please change your data on profile page", Toast.LENGTH_SHORT).show();

                } else {
                    ConnectionManager.getInstance(getActivity()).putCompanion(mUser.getPaxid(), mUser, new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                            getFragmentManager().popBackStack();
                        }

                        @Override
                        public void onError(Object data) {
                            Toast.makeText(getActivity().getApplicationContext(), "There was a problem saving your information please try again", Toast.LENGTH_SHORT).show();
                            HGBErrorHelper errorHelper = new HGBErrorHelper();
                            errorHelper.show(getFragmentManager(), (String) data);
                        }
                    });
                }


            }
        });
    }

    private void setSaveButton() {
        if (HGBUtility.isUserDataValid(mUser)) {
            mSave.setBackgroundResource(R.drawable.red_button);
            mSave.setEnabled(true);
        } else {
            mSave.setBackgroundResource(R.drawable.red_disable_button);
            mSave.setEnabled(false);
        }
        mSave.setPadding(0, 30, 0, 30);

    }

//    private void showDateDialog() {
//
//        final Calendar c = Calendar.getInstance();
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
//                new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year,
//                                          int monthOfYear, int dayOfMonth) {
//                        mDOB.setText(dayOfMonth + "/"
//                                + (monthOfYear + 1) + "/" + year);
//
//                    }
//                }, mYear, mMonth, mDay);
//        dpd.show();
//    }


//    private void showTitleDialog() {
//        View v1 = getActivity().getLayoutInflater().inflate(R.layout.picker_dialog, null);
//
//        titlePicker = (NumberPicker) v1.findViewById(R.id.np);
//        titlePicker.setMinValue(0);
//
//        titlePicker.setMaxValue(2);
//        final String[] titleArray = {"Mr", "Mrs", "Miss"};
//        titlePicker.setDisplayedValues(titleArray);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(v1);
//        builder.setTitle("Select Title");
//        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                mTitle.setText(titleArray[titlePicker.getValue()]);
//                return;
//            }
//        });
//        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                return;
//            }
//        });
//
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    final String[] genderArray = {"M", "F"};
    final String GENDER_TITLE = "Select Gender";

//    private void showGenderDialog() {
//
//        View v1 = getActivity().getLayoutInflater().inflate(R.layout.picker_dialog, null);
//
//        genderPicker = (NumberPicker) v1.findViewById(R.id.np);
//        genderPicker.setMinValue(0);
//
//        genderPicker.setMaxValue(1);
//        final String[] genderArray = {"M", "F"};
//        genderPicker.setDisplayedValues(genderArray);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(v1);
//        builder.setTitle("Select Gender");
//        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                mGender.setText(genderArray[genderPicker.getValue()]);
//                return;
//            }
//        });
//        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                return;
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//    }

//    private void buildCountryDialog() {
//        View v1 = getActivity().getLayoutInflater().inflate(R.layout.picker_dialog, null);
//
//        countryPicker = (NumberPicker) v1.findViewById(R.id.np);
//        countryPicker.setMinValue(0);
//        countryPicker.setMaxValue(getActivityInterface().getEligabileCountries().size() - 1);
//        final String[] countryarray = new String[getActivityInterface().getEligabileCountries().size()];
//        for (int i = 0; i < getActivityInterface().getEligabileCountries().size(); i++) {
//            countryarray[i] = getActivityInterface().getEligabileCountries().get(i).getName();
//        }
//        countryPicker.setDisplayedValues(countryarray);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(v1);
//        builder.setTitle("Select Country");
//        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                mCountry.setText(countryarray[countryPicker.getValue()]);
//
//
//                maxValueForStateDialog = getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().size();
//                stateArray = new String[getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().size()];
//                for (int i = 0; i < getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().size(); i++) {
//                    stateArray[i] = getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().get(i).getName();
//                }
//
//                HGBUtility.buildStateDialog(mState, getActivity(), maxValueForStateDialog, stateArray, SELECT_PROVINCE);
//
//
////                buildStateDialog();
//                return;
//            }
//        });
//        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                return;
//            }
//        });
//
//        countryDialog = builder.create();
//    }

//    private void buildStateDialog() {
//        View v1 = getActivity().getLayoutInflater().inflate(R.layout.picker_dialog, null);
//
//        statePicker = (NumberPicker) v1.findViewById(R.id.np);
//        statePicker.setMinValue(0);
//
//        statePicker.setMaxValue(getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().size() - 1);
//        final String[] stateArray = new String[getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().size()];
//        for (int i = 0; i < getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().size(); i++) {
//            stateArray[i] = getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().get(i).getName();
//        }
//        statePicker.setDisplayedValues(stateArray);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(v1);
//        builder.setTitle("Select Province");
//        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                mState.setText(stateArray[statePicker.getValue()]);
//                return;
//            }
//        });
//        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                return;
//            }
//        });
//
//
//        stateDialog = builder.create();
//
//    }

    private void initUser() {

        mTitle.setText(mUser.getTitle());
        mFirstName.setText(mUser.getFirstname());
        mLastName.setText(mUser.getLastname());
        mDOB.setText(HGBUtility.parseDateToddMMyyyyForPayment(mUser.getDob()));
//        mGender.setText(mUser.());
        mEmail.setText(mUser.getEmailaddress());
        mPhone.setText(mUser.getPhone());
        mAddress.setText(mUser.getAddress());
        mCity.setText(mUser.getCity());

        if (mUser.getCountry().equals("CA")) {
            mCountry.setText("Canada");
        } else if (mUser.getCountry().equals("US")) {
            mCountry.setText("UnitedStates");
        }

        mPostalCode.setText(mUser.getPostalcode());
        mState.setText(mUser.getState());


        setTextListner();

    }

    private void setTextListner() {

        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setFirstname(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setLastname(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setCity(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setState(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setCountry(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mPostalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setPostalcode(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }


}
