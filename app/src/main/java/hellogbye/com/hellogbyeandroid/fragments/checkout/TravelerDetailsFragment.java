package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.CreateAccountActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static hellogbye.com.hellogbyeandroid.R.id.position;
import static hellogbye.com.hellogbyeandroid.R.id.preference_save_changes;

/**
 * Created by arisprung on 11/24/15.
 */
public class TravelerDetailsFragment extends HGBAbstractFragment {


    private FontEditTextView mTitle;
    private FontEditTextView mSeatType;
    private FontEditTextView mFirstName;
    private FontEditTextView mLastName;
    private FontEditTextView mDOB;
   // private FontTextView mSave;
    private     String[] seatArray = {"No Preference","Window","Aisle"};
    private AlertDialog mSeatTypeDialog;
   // private FontTextView mGender;
    private FontEditTextView mEmail;
    private FontEditTextView mPhone;
    /*private FontEditTextView mAddress;
    private FontEditTextView mCity;
    private FontTextView mCountry;
    private FontEditTextView mPostalCode;
    private FontTextView mState;*/
    private UserProfileVO mUser;

    private String[] stateArray;

    private BookingRequestVO bookingResponse;
    private FontEditTextView travel_detail_middle_name;
    private String SELECT_TITLE = "Select Title";

    public static Fragment newInstance(int position) {
        Fragment fragment = new TravelerDetailsFragment();
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

     //   getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mTitle = (FontEditTextView) view.findViewById(R.id.travel_detail_title);
        mFirstName = (FontEditTextView) view.findViewById(R.id.travel_detail_first_name);
        mSeatType= (FontEditTextView) view.findViewById(R.id.travel_seat_type);
        mLastName = (FontEditTextView) view.findViewById(R.id.travel_detail_last_name);
        travel_detail_middle_name = (FontEditTextView) view.findViewById(R.id.travel_detail_middle_name);
        mDOB = (FontEditTextView) view.findViewById(R.id.travel_detail_dob);
  //      mGender = (FontTextView) view.findViewById(R.id.travel_detail_gender);
        mEmail = (FontEditTextView) view.findViewById(R.id.travel_detail_email);
        mPhone = (FontEditTextView) view.findViewById(R.id.travel_detail_phone);
    /*    mAddress = (FontEditTextView) view.findViewById(R.id.travel_detail_adress);
        mCity = (FontEditTextView) view.findViewById(R.id.travel_detail_city);
        mCountry = (FontTextView) view.findViewById(R.id.travel_detail_country);
        mPostalCode = (FontEditTextView) view.findViewById(R.id.travel_detail_postal_code);
        mState = (FontTextView) view.findViewById(R.id.travel_detail_province);*/
       // mSave = (FontTextView) view.findViewById(R.id.travler_detail_save);

        //getStaticBooking();
        bookingResponse = getActivityInterface().getBookingRequest();

        Bundle args = getArguments();
        if (args != null) {
            String  currentUser = args.getString(HGBConstants.BUNDLE_USER_JSON_POSITION);
            for(UserProfileVO user : getFlowInterface().getListUsers()){
                if(user.getFirstname().equalsIgnoreCase(currentUser)){
                    mUser = user;
                    break;
                }
            }
        }

        if (mUser != null) {
            initUser();
        }

      //  setSaveButton();
        setClickListner();
        buildSeatTypeDialog();

    }

/*    final String[] titleArray = {"Mr", "Mrs", "Miss"};*/



    private void selectStates(String countryPicked) {
        int countryPick = Integer.parseInt(countryPicked);
        ArrayList<CountryItemVO> countries = getActivityInterface().getBookingRequest().getCountries();
        stateArray = new String[countries.get(countryPick).getProvinces().size()];
        ArrayList<ProvincesItem> provinces = countries.get(countryPick).getProvinces();
        for (int i = 0; i < provinces.size(); i++) {
            stateArray[i] = provinces.get(i).getProvincename();
        }
    }

    private void setClickListner() {

/*        mCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
                String[] countryarray = new String[countries.size()];
                for (int i = 0; i < countries.size(); i++) {
                    countryarray[i] = countries.get(i).getName();
                }

                HGBUtility.showPikerDialog(0,mCountry, getActivity(), "Choose country",
                        countryarray, 0, countries.size() - 1, new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                for (CountryItemVO countrie : countries) {
                                    if (countrie.getName().equals(inputItem)) {
                                        mCountry.setTag(countrie.getCode());
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

        mState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStaticProvince();
            }
        });*/

     /*   mGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  showGenderDialog();
                HGBUtility.showPikerDialog(0,mGender, getActivity(), getResources().getString(R.string.select_gender), getResources().getStringArray(R.array.gender_array), 0, 1, null, true);
            }
        });
*/
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HGBUtility.showPikerDialogEditText(mTitle, getActivity(), SELECT_TITLE, getResources().getStringArray(R.array.title_array), 0, 2, null, true);


                // showTitleDialog();
            }
        });



        mSeatType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeatTypeDialog.show();
            }
        });

        mDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HGBUtility.showDateDialog(getActivity(), mDOB);
            }
        });

        ((MainActivityBottomTabs)getActivity()).getPreferencesSaveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUser.setTitle(mTitle.getText().toString());
               // mUser.setGender(mGender.getText().toString());
            //    mUser.setAddress(mAddress.getText().toString());

              /*  if (mCountry.getText().toString().equals("Canada")) {
                    mUser.setCountry("CN");
                } else if (mCountry.getText().toString().equals("UnitedStates")) {
                    mUser.setCountry("US");
                }else{
                    mUser.setCountry(mCountry.getText().toString());
                }*/

/*                mUser.setState(mState.getText().toString());
                mUser.setPostalcode(mPostalCode.getText().toString());
                mUser.setCity(mCity.getText().toString());*/

                String dobString = mDOB.getText().toString();
                String dob = HGBUtilityDate.parseDateToyyyyMMddTHHmmss(dobString);

                mUser.setDob(dob);

                mUser.setFirstname(mFirstName.getText().toString());
                mUser.setLastname(mLastName.getText().toString());
                mUser.setPhone(mPhone.getText().toString());
                mUser.setEmailaddress(mEmail.getText().toString());
                mUser.setMiddlename(travel_detail_middle_name.getText().toString());

                ((MainActivityBottomTabs) getActivity()).onBackPressed();



//                ConnectionManager.getInstance(getActivity()).putCompanion(mUser.getPaxid(), mUser, new ConnectionManager.ServerRequestListener() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        getFragmentManager().popBackStack();
//
//                    }
//
//                    @Override
//                    public void onError(Object data) {
//                        Toast.makeText(getActivity().getApplicationContext(), "There was a problem saving your information please try again", Toast.LENGTH_SHORT).show();
//                        ErrorMessage(data);
//                    }
//                });


            }
        });
    }
//
//      private void setSaveButton() {
//        if (HGBUtility.isUserDataValid(mUser)) {
//            mSave.setEnabled(true);
//        } else {
//            mSave.setEnabled(false);
//        }
//        //    mSave.setPadding(0, 30, 0, 30);
//
//    }


    /*final String[] genderArray = {"M", "F"};*/
    /*final String GENDER_TITLE = "Select Gender";*/


    private void initUser() {

        mTitle.setText(mUser.getTitle());
        mFirstName.setText(mUser.getFirstname());
        mLastName.setText(mUser.getLastname());
        mDOB.setText(HGBUtilityDate.parseDateToddMMyyyyForPayment(mUser.getDob()));
        mEmail.setText(mUser.getEmailaddress());
        mPhone.setText(mUser.getPhone());
        if(mUser.getMiddlename() != null) {
            travel_detail_middle_name.setText(mUser.getMiddlename());
        }
     /*   mAddress.setText(mUser.getAddress());
        mCity.setText(mUser.getCity());

        if (mUser.getCountry().equals("CA")) {
            mCountry.setText("Canada");
        } else if (mUser.getCountry().equals("US")) {
            mCountry.setText("UnitedStates");
        }else{
            mCountry.setText(mUser.getCountry());
        }

        mPostalCode.setText(mUser.getPostalcode());
        mState.setText(mUser.getState());*/

/*        ConnectionManager.getInstance(getActivity()).getStaticBookingProvince(mUser.getCountry(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<ProvincesItem> provinceItems = (List<ProvincesItem>) data;
//                if (provinceItems.size() > 0) {
//
//                } else {
//
//                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });*/


        setTextListner();

    }

    private void setTextListner() {

        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // mUser.setFirstname(s.toString());
               // setSaveButton();
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
            //    mUser.setLastname(s.toString());
             //   setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        travel_detail_middle_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             //  mUser.setMiddlename(s.toString());
              ///  setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

     /*   mCity.addTextChangedListener(new TextWatcher() {
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
*/

    }

    private void getStaticBooking() {

        ConnectionManager.getInstance(getActivity()).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                bookingResponse = (BookingRequestVO) data;

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);

            }
        });
    }

    private void getStaticProvince() {
      /*  String countryID;
        if (mCountry.getTag() == null) {
            countryID = mCountry.getText().toString();
            if (countryID.equals("Canada")) {
                countryID = "CA";
            } else if (countryID.equals("UnitedStates")) {
                countryID = "US";
            }
        } else {
            countryID = (String) mCountry.getTag();
        }*/
    /*    String  countryID = (String) mCountry.getTag();
        ConnectionManager.getInstance(getActivity()).getStaticBookingProvince(countryID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<ProvincesItem> provinceItems = (List<ProvincesItem>) data;
                if (provinceItems.size() > 0) {
                    setDropDownItems(provinceItems);
                    mState.setVisibility(View.VISIBLE);
                } else {
                    mState.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });*/
    }


    private void setDropDownItems(final List<ProvincesItem> provinceItems) {
/*        String[] countryarray = new String[provinceItems.size()];
        for (int i = 0; i < provinceItems.size(); i++) {
            countryarray[i] = provinceItems.get(i).getProvincename();
        }

        HGBUtility.showPikerDialog(0,mState, getActivity(), "Choose province",
                countryarray, 0, provinceItems.size() - 1, new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        for (ProvincesItem province : provinceItems) {
                            if (province.getProvincename().equals(inputItem)) {
                                mState.setText(province.getProvincename());
                                mState.setTag(province.getProvincecode());
                                break;
                            }
                        }

                    }

                    @Override
                    public void itemCanceled() {

                    }
                }, false);*/

    }

    private void buildSeatTypeDialog() {

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                getActivity(), R.layout.dialog_radio, seatArray);
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Preferred Seat Type");
        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                mSeatType.setText(seatArray[item]);

                mSeatTypeDialog.hide();
            }
        });
        mSeatTypeDialog = builder.create();
    }


}
