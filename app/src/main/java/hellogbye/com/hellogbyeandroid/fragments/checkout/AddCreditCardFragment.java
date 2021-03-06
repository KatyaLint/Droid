package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.devmarvel.creditcardentry.library.CreditCardForm;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
//import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.CreditCardSessionItem;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * Created by arisprung on 11/24/15.
 */
public class AddCreditCardFragment extends HGBAbstractFragment implements TextWatcher {

    private CreditCardForm mCardNumber;
    private FontEditTextView mCardExpiryMonth;
    private FontEditTextView mCardExpiryYear;
    private FontEditTextView mCardCCV;
    private FontEditTextView mCardFirstName;
    private FontEditTextView mCardLastName;
    private FontEditTextView mCardStreet;
    private FontTextView mCardCountry;
    private FontEditTextView mCardCity;
    private FontTextView mCardProvince;
    private FontEditTextView mCardPostal;
    private FontButtonView mSave;
    private FontTextView mScan;
    private CheckBox mBillingCheckbox;
    private CreditCardItem mCurrentCard;

    private NumberPicker countryPicker;
    private NumberPicker statePicker;
    private AlertDialog countryDialog;
    private AlertDialog stateDialog;

    private final int NUMBER_OF_FUTURE_YEARS = 14;

    private ArrayList<String> listOfPattern;
    private final int MY_SCAN_REQUEST_CODE = 123;

    private ProgressDialog progressDialog;
    private CreditCardSessionItem creditCardItemSession;
    private BookingRequestVO bookingResponse;

    // final String[] monthArray = {"1", "2", "3", "4","5","6","7","8","9","10","11","12"};
    private ArrayList<String> yearArray;
    private String[] monthArray;
    private List<ProvincesItem> provinceItems;

    private String mProgessDialogString = "Adding Credit Card";

    public static Fragment newInstance(int position) {
        Fragment fragment = new AddCreditCardFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_credit_card_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        yearArray = HGBUtilityDate.getYears(NUMBER_OF_FUTURE_YEARS);
        ArrayList<String> months = HGBUtilityDate.getMonths();
        monthArray = new String[months.size()];
        monthArray = months.toArray(monthArray);

        bookingResponse = getActivityInterface().getBookingRequest();
        init(view);

        listOfPattern = new ArrayList<String>();
        String ptAmeExp = "^3[47][0-9]{5,}$";
        listOfPattern.add(ptAmeExp);
        String ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
        listOfPattern.add(ptDiscover);
        String ptMasterCard = "^5[1-5][0-9]{5,}$";
        listOfPattern.add(ptMasterCard);
        String ptVisa = "^4[0-9]{6,}$";
        listOfPattern.add(ptVisa);

        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent(getActivity().getApplicationContext(), CardIOActivity.class);

                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, true); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_GUIDE_COLOR,0x000000); // default:  0x000000
                startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCCToServer();

            }
        });


        mCardCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
                String[] countryarray = new String[countries.size()];
                for (int i = 0; i < countries.size(); i++) {
                    countryarray[i] = countries.get(i).getName();
                }

                HGBUtility.showPikerDialog(0, mCardCountry, getActivity(), "Choose country",
                        countryarray, 0, countries.size() - 1, new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                for (CountryItemVO countrie : countries) {
                                    if (countrie.getName().equals(inputItem)) {
                                        mCardCountry.setTag(countrie.getCode());
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


        mCardProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStaticProvince();
            }
        });

        mCardExpiryMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HGBUtility.showPikerDialogEditText(mCardExpiryMonth, getActivity(), "Choose Month",
                        monthArray, 0, monthArray.length - 1, new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {

                                mCardExpiryMonth.setText(inputItem);
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        }, false);
            }
        });

        mCardExpiryYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] yearArr = new String[yearArray.size()];
                yearArr = yearArray.toArray(yearArr);


                HGBUtility.showPikerDialogEditText(mCardExpiryYear, getActivity(), "Choose Year",
                        yearArr, 0, yearArr.length - 1, new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {

                                mCardExpiryYear.setText(inputItem);
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        }, false);
            }
        });


        mBillingCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    addCurrentUserFields();

                } else {
                    clearFields();
                }
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            String isFillPayment = args.getString(HGBConstants.PAYMENT_FILL_CREDIT_CARD);
            if (isFillPayment != null) {
                fillPaymentDetails(isFillPayment);
                mProgessDialogString = "Updating Credit Card";
            }
        }


    }

    private void addCurrentUserFields() {
        UserProfileVO currentUser = getActivityInterface().getCurrentUser();
        mCardStreet.setText(currentUser.getAddress());
        mCardCountry.setText(currentUser.getCountry());
        mCardFirstName.setText(currentUser.getFirstname());
        mCardLastName.setText(currentUser.getLastname());
        mCardProvince.setText(currentUser.getState());
        mCardPostal.setText(currentUser.getPostalcode());
        mCardCity.setText(currentUser.getCity());
    }

    private boolean isFillPayment = false;

    private void fillPaymentDetails(String creditCardToken) {
        mSave.setEnabled(true);
        mCurrentCard = new CreditCardItem();

        isFillPayment = true;
        mBillingCheckbox.setVisibility(View.GONE);
        ArrayList<CreditCardItem> creditCards = getFlowInterface().getCreditCards();

        for (CreditCardItem creditCard : creditCards) {
            if (creditCard.getToken() != null && creditCard.getToken().equals(creditCardToken)) {
                mCurrentCard = creditCard;
                break;
            }
        }

        String cardNumber = hideCardNumberWithStars(mCurrentCard.getToken());
        mCardNumber.setTag(mCurrentCard.getToken());
        mCardNumber.setCardNumber(cardNumber, false);
        mCardNumber.setEnabled(false);
        mCardExpiryMonth.setText(mCurrentCard.getExpmonth());
        mCardExpiryYear.setText(mCurrentCard.getExpyear());
        mCardCCV.setText(mCurrentCard.getCvv());
        mCardCCV.setEnabled(false);
        mSave.setEnabled(true);
        addFields();

    }


    private String hideCardNumberWithStars(String cardNumber) {

        // remove all non-digits characters
        String processed = cardNumber.replaceAll("\\D", "");
        // insert a space after all groups of 4 digits that are followed by another digit
        processed = processed.replaceAll("(\\d{4})(?=\\d)", "$1 ");

        String endString = processed.substring(processed.length() - 4);
        String beginingString = processed.substring(0, processed.length() - 4);

        String replacedProcessed = beginingString.replaceAll("[0-9]", "$*");
        replacedProcessed = replacedProcessed + endString;

        return replacedProcessed;
    }


    private void addFields() {

        mCardStreet.setText(mCurrentCard.getBuyeraddress());
        mCardCountry.setText(mCurrentCard.getBillingcountry());
        mCardFirstName.setText(mCurrentCard.getBuyerfirstname());
        mCardLastName.setText(mCurrentCard.getBuyerlastname());
        mCardProvince.setText(mCurrentCard.getBillingprovince());
        mCardPostal.setText(mCurrentCard.getBuyerzip());
        mCardCity.setText(mCurrentCard.getBillingcity());
    }

    private void clearFields() {
        mCardStreet.setText("");
        mCardCountry.setText("");
        mCardFirstName.setText("");
        mCardLastName.setText("");
        mCardProvince.setText("");
        mCardPostal.setText("");
        mCardCity.setText("");
    }

    private void init(View view) {
        mCardNumber = (CreditCardForm) view.findViewById(R.id.cc_number);
//        mCardNumber.validate();
        mCardExpiryMonth = (FontEditTextView) view.findViewById(R.id.cc_expiry_month);
        mCardExpiryYear = (FontEditTextView) view.findViewById(R.id.cc_expiry_year);
        mCardCCV = (FontEditTextView) view.findViewById(R.id.cc_ccv);
        mCardFirstName = (FontEditTextView) view.findViewById(R.id.cc_first_name);
        mCardLastName = (FontEditTextView) view.findViewById(R.id.cc_last_name);
        mCardStreet = (FontEditTextView) view.findViewById(R.id.cc_billint_st);
        mCardCountry = (FontTextView) view.findViewById(R.id.cc_country);
        mCardCity = (FontEditTextView) view.findViewById(R.id.cc_billing_city);
        mCardProvince = (FontTextView) view.findViewById(R.id.cc_billing_province);
        mCardPostal = (FontEditTextView) view.findViewById(R.id.cc_billing_postal);
        mSave = (FontButtonView) view.findViewById(R.id.cc_save);
        mScan = (FontTextView) view.findViewById(R.id.cc_scan);
        ///  mCardNumber.addTextChangedListener(new FourDigitCardFormatWatcher());
        mBillingCheckbox = (CheckBox) view.findViewById(R.id.add_cc_checkboox);
        // mCardExpiry.addTextChangedListener(new TwoDigitsCardTextWatcher(mCardExpiry));

        //  mCardNumber.addTextChangedListener(this);
        mCardExpiryMonth.addTextChangedListener(this);
        mCardExpiryYear.addTextChangedListener(this);
        mCardCCV.addTextChangedListener(this);
        mCardFirstName.addTextChangedListener(this);
        mCardLastName.addTextChangedListener(this);
        mCardStreet.addTextChangedListener(this);
        mCardCountry.addTextChangedListener(this);
        mCardCity.addTextChangedListener(this);
        mCardProvince.addTextChangedListener(this);
        mCardPostal.addTextChangedListener(this);
        mSave.setEnabled(false);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                mCardNumber.setCardNumber(scanResult.getFormattedCardNumber(), false);

                if (scanResult.isExpiryValid()) {
                    mCardExpiryMonth.setText(""+scanResult.expiryMonth);
                    mCardExpiryYear.setText(""+scanResult.expiryYear);
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    mCardCCV.setText(scanResult.cvv);
                }

                if (scanResult.postalCode != null) {
                    mCardPostal.setText(scanResult.postalCode);
                }
            }

        }
    }


    private void sendCCToServer() {


        progressDialog = ProgressDialog.show(getActivity(), "", mProgessDialogString);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        //IF new Card need to initiailize
        if (mCurrentCard == null) {
            mCurrentCard = new CreditCardItem();
        }

        mCurrentCard.setBuyerfirstname(mCardFirstName.getText().toString());
        mCurrentCard.setBuyerlastname(mCardLastName.getText().toString());
        mCurrentCard.setBuyerzip(mCardPostal.getText().toString());
        mCurrentCard.setBuyeraddress(mCardStreet.getText().toString());
        if(mCardNumber.getCreditCard().getCardType()!=null){
            mCurrentCard.setCardtypeid(getCCType(mCardNumber.getCreditCard().getCardType().name));
        }


//        String expDate = mCardExpiry.getText().toString();
//        if (expDate.contains("/")) {
//            String[] parts = expDate.split("/");
//            String partMonth = parts[0];
//            String partYear = parts[1];
//            creditCardItem.setExpmonth(partMonth);
//            creditCardItem.setExpyear(partYear);
//        }

        mCurrentCard.setExpmonth(mCardExpiryMonth.getText().toString());
        mCurrentCard.setExpyear(mCardExpiryYear.getText().toString());


        String last4 = mCardNumber.getCreditCard().getCardNumber().toString();
        last4 = last4.replaceAll("\\s+", "");
        last4 = last4.substring(last4.length() - 4, last4.length());
        final String strCardNumber = mCardNumber.getCreditCard().getCardNumber().replaceAll("\\s+", "");
        mCurrentCard.setCardNumber(strCardNumber);
        if (isFillPayment) {
            last4 = mCardNumber.getTag().toString();
            mCurrentCard.setCardNumber(last4);
            last4 = last4.substring(last4.length() - 4, last4.length());

        }


        //  last4 = last4.substring(last4.length() - 5, last4.length());


        mCurrentCard.setLast4(last4);
        mCurrentCard.setCvv(mCardCCV.getText().toString());


        // CcnTypeEnum hh=  mCardNumber.validate();

            if (!isFillPayment) {
                if (true) {//TODO this is a work around for AMEX
                ConnectionManager.getInstance(getActivity()).getCCSession(new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        creditCardItemSession = (CreditCardSessionItem) data;

                        if (creditCardItemSession == null) {
                        /*HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getActivity().getFragmentManager(), "There was a problem please try again");*/
                            ErrorMessage("There was a problem please try again");

                        } else {
                            mCurrentCard.setNickname(creditCardItemSession.getNickname());
                            mCurrentCard.setToken(creditCardItemSession.getToken());


                            addCreditCard(mCurrentCard);

                        }


                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });
                } else {
                    progressDialog.hide();
                    ErrorMessage("The Credit Card number is not valid please try again");
           /* HGBErrorHelper errorHelper = new HGBErrorHelper();
            errorHelper.setMessageForError("The Credit Card number is not valid please try again");
            errorHelper.show(getActivity().getFragmentManager(), "Problem with Credit Card");*/
                }

            } else {
                addCreditCard(mCurrentCard);
            }




    }


    private void addCreditCard(final CreditCardItem creditCardItem) {

        if (isFillPayment) {
            creditCardItem.setUpdateCard(true);
        } else {
            creditCardItem.setUpdateCard(false);
        }

        ConnectionManager.getInstance(getActivity()).addCreditCard(creditCardItem, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                JSONObject json;
                if (isFillPayment) {
                    json = createJsonObjectForEditCreditCard(creditCardItem, data);
                    ConnectionManager.getInstance(getActivity()).UpdateCreditCardHelloGbye(json, new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                            progressDialog.hide();
                            Toast.makeText(getActivity().getApplicationContext(), "Credit Card Updated", Toast.LENGTH_SHORT).show();
                            ((MainActivityBottomTabs) getActivity()).onBackPressed();
                            //getFragmentManager().popBackStack();

                        }

                        @Override
                        public void onError(Object data) {

                            progressDialog.hide();
                            ErrorMessage(data);

                        }
                    });

                } else {
                    json = createJsonObjectForAddCreditCard(creditCardItem, data);
                    ConnectionManager.getInstance(getActivity()).AddCreditCardHelloGbye(json, new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                            progressDialog.hide();
                            ((MainActivityBottomTabs) getActivity()).onBackPressed();
                            //getFragmentManager().popBackStack();s

                        }

                        @Override
                        public void onError(Object data) {

                            progressDialog.hide();
                            ErrorMessage(data);

                        }
                    });
                }


                if (json == null) {
                    ErrorMessage("Something went wrong");
                    return;
                }




              /*  }
            catch (JSONException e) {
                    Log.e("JSON exception", e.getMessage());
                    e.printStackTrace();
                    progressDialog.hide();
                    ErrorMessage(e);
                }
                Log.d("JSON", jsonObj.toString());*/
            }

            @Override
            public void onError(Object data) {
                Log.e("", "");
                progressDialog.hide();
                ErrorMessage(data);

            }
        });
    }


    private String getCCType(String cardType) {
        if ("MASTERCARD".equalsIgnoreCase(cardType)) {
            return "3";
        } else if ("VISA".equalsIgnoreCase(cardType)) {
            return "4";
        } else if ("American Express".equalsIgnoreCase(cardType)) {
            return "1";
        } else if ("DISCOVER".equalsIgnoreCase(cardType)) {
            return "2";
        }

        return "0";
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
        //String countryID;
    /*    if( mCardCountry.getTag() == null){
            countryID =  mCardCountry.getText().toString();
            if (countryID.equals("Canada")) {
                countryID = "CA";
            } else if (countryID.equals("UnitedStates")) {
                countryID = "US";
            }
        }else{

        }*/
        String countryID = (String) mCardCountry.getTag();
        ConnectionManager.getInstance(getActivity()).getStaticBookingProvince(countryID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                provinceItems = (List<ProvincesItem>) data;
                if (provinceItems.size() > 0) {
                    setDropDownItems(provinceItems);
                    mCardProvince.setVisibility(View.VISIBLE);
                } else {
                    mCardProvince.setVisibility(View.GONE);
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

        HGBUtility.showPikerDialog(0, mCardProvince, getActivity(), "Choose province",
                countryarray, 0, provinceItems.size() - 1, new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        for (ProvincesItem province : provinceItems) {
                            if (province.getProvincename().equals(inputItem)) {
                                mCardProvince.setText(province.getProvincename());
                                mCardProvince.setTag(province.getProvincecode());
                                break;
                            }
                        }

                    }

                    @Override
                    public void itemCanceled() {

                    }
                }, false);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (isFillPayment) {
            return;
        }
        if (checkAddCardEnabled()) {
            //  mSave.setBackgroundResource(R.drawable.red_button);
            mSave.setEnabled(true);
        } else {
            //mSave.setBackgroundResource(R.drawable.red_disable_button);
            mSave.setEnabled(false);
        }
        // mSave.setPadding(0, 30, 0, 30);
    }

    private boolean checkAddCardEnabled() {

        if (mCardStreet.getText().toString().equals("")) {
            return false;
        } else if (mCardCountry.getText().toString().equals("")) {
            return false;
        } else if (mCardFirstName.getText().toString().equals("")) {
            return false;
        } else if (mCardLastName.getText().toString().equals("")) {
            return false;
        } else if (provinceItems != null && provinceItems.size() != 0) {
            if (mCardProvince.getText().toString().equals("")) {
                return false;
            }
        } else if (mCardPostal.getText().toString().equals("")) {
            return false;
        } else if (mCardCity.getText().toString().equals("")) {
            return false;
        } else if (mCardNumber.getCreditCard().getCardNumber().toString().equals("")) {
            return false;
        } else if (mCardExpiryMonth.getText().toString().equals("")) {
            return false;
        } else if (mCardExpiryYear.getText().toString().equals("")) {
            return false;
        } else if (mCardCCV.getText().toString().equals("")) {
            return false;
        }

        return true;

    }

    /**
     * Formats the watched EditText to a credit card number
     */
    public static class FourDigitCardFormatWatcher implements TextWatcher {

        // Change this to what you want... ' ', '-' etc..
        private static final char space = ' ';

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Remove spacing char
            if (s.length() > 0 && (s.length() % 5) == 0) {
                final char c = s.charAt(s.length() - 1);
                if (space == c) {
                    s.delete(s.length() - 1, s.length());
                }
            }
            // Insert char where needed.
            if (s.length() > 0 && (s.length() % 5) == 0) {
                char c = s.charAt(s.length() - 1);
                // Only if its a digit where there should be a space we insert a space
                if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                    s.insert(s.length() - 1, String.valueOf(space));
                }
            }
        }
    }


    private boolean verifyValidCCNumber(CreditCardItem creditCardItem) {

        //American Express
        if (creditCardItem.getCardtypeid().equals("1")) {
            if (mCardNumber.getCreditCard().getCardNumber().length() == 15) {
                return true;
            } else {
                return false;
            }

        }
        //Discover
        else if (creditCardItem.getCardtypeid().equals("2")) {

        }
        //MasterCard
        else if (creditCardItem.getCardtypeid().equals("3")) {
            if (mCardNumber.getCreditCard().getCardNumber().length() == 16) {
                return true;
            } else {
                return false;
            }
        }
        //Visa
        else if (creditCardItem.getCardtypeid().equals("4")) {
            if (mCardNumber.getCreditCard().getCardNumber().length() == 13 || mCardNumber.getCreditCard().getCardNumber().length() == 16) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }


    private JSONObject createJsonObjectForEditCreditCard(final CreditCardItem creditCardItem, Object data) {
        JSONObject json = null;
        try {
            JSONObject jsonObj = XML.toJSONObject((String) data);
            JSONObject json1 = jsonObj.getJSONObject("soap:Envelope");
            JSONObject json2 = json1.getJSONObject("soap:Body");
            JSONObject json3 = json2.getJSONObject("UpdateCOFResponse");
            //  JSONObject json4 = json3.getJSONObject("UpdateCOFResult");
            String strXML = json3.getString("UpdateCOFResult");

            JSONObject jsonObj1 = XML.toJSONObject(strXML);
            JSONObject jsonObj2 = jsonObj1.getJSONObject("Response");
            JSONObject jsonObj3 = jsonObj2.getJSONObject("UpdateCOF");
            String strToken = jsonObj3.getString("Token");
/*            JSONObject json5 = json4.getJSONObject("TokenData");
            String strToken = json5.getString("Token");
            String strNickName = json5.getString("NickName");
            String strLast4 = json5.getString("Last4");*/

            json = new JSONObject();
            json.put("Last4", creditCardItem.getLast4());
            json.put("NickName", jsonObj3.getString("NickName"));
            json.put("Token", jsonObj3.getString("Token"));
            json.put("CardNumber", creditCardItem.getCardNumber());
            json.put("ExpirationMonth", jsonObj3.getString("ExpMonth"));
            json.put("ExpirationYear", jsonObj3.getString("ExpYear"));
            json.put("CardType", jsonObj3.getString("CardTypeID"));
            json.put("FirstName", jsonObj3.getString("FirstName"));
            json.put("LastName", jsonObj3.getString("LastName"));
            json.put("StreetAddress", jsonObj3.getString("StreetAddress"));
            json.put("ZipCode", jsonObj3.getString("ZipCode"));
            json.put("BillingCity", mCardCity.getText().toString());
            json.put("BillingCountry", mCardCountry.getText().toString());
            json.put("BillingSuite", "");
            json.put("BillingProvince", mCardProvince.getText().toString());
            Log.d("JSON", jsonObj.toString());
        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
            e.printStackTrace();
            progressDialog.hide();
            ErrorMessage(e.toString());
        }
        return json;

    }

    private JSONObject createJsonObjectForAddCreditCard(final CreditCardItem creditCardItem, Object data) {
        JSONObject json = null;
        try {
            JSONObject jsonObj = XML.toJSONObject((String) data);
            JSONObject json1 = jsonObj.getJSONObject("soap:Envelope");
            JSONObject json2 = json1.getJSONObject("soap:Body");
            JSONObject json3 = json2.getJSONObject("AddCOFResponse");
            String strXML = json3.getString("AddCOFResult");

            JSONObject jsonObj1 = XML.toJSONObject(strXML);
            JSONObject jsonObj2 = jsonObj1.getJSONObject("Response");
            JSONObject jsonObj3 = jsonObj2.getJSONObject("AddCOF");

            String strToken = jsonObj3.getString("Token");
            String strNickName = jsonObj3.getString("NickName");
            String strLast4 = jsonObj3.getString("Last4");

            json = new JSONObject();
            json.put("Last4", creditCardItem.getLast4());
            json.put("NickName", strNickName);
            json.put("Token", strToken);
            json.put("CardNumber", creditCardItem.getCardNumber());
            int iMonth =  Integer.valueOf(creditCardItem.getExpmonth());
            json.put("ExpirationMonth", iMonth);
            int iYear =  Integer.valueOf(creditCardItem.getExpyear());
            json.put("ExpirationYear", iYear);
            int iType =  Integer.valueOf(creditCardItem.getCardtypeid());
            json.put("CardType",iType);
            json.put("FirstName", creditCardItem.getBuyerfirstname());
            json.put("LastName", creditCardItem.getBuyerlastname());
            json.put("StreetAddress", creditCardItem.getBuyeraddress());
            json.put("ZipCode", creditCardItem.getBuyerzip());
            json.put("BillingCity", mCardCity.getText().toString());
            json.put("BillingCountry", mCardCountry.getText().toString());
            json.put("BillingSuite", "");
            json.put("BillingProvince", mCardProvince.getText().toString());
            Log.d("JSON", jsonObj.toString());
        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
            e.printStackTrace();
            progressDialog.hide();
            ErrorMessage(e);
        }
        return json;

    }
}
