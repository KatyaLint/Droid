package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.BuildConfig;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.TravlerAdapter;
import hellogbye.com.hellogbyeandroid.adapters.creditcardadapters.TravlerCCAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 12/2/15.
 */
public class SummaryPaymentFragment extends HGBAbstractFragment {

   // private ListView mListViewCC;

//    private SummeryPaymentCCAdapter mAdapter;
//    private ArrayList<PaymentSummaryItem> mArrayList;

    private FontButtonView mProceed;


    private TravlerAdapter mTravlerAdapter;
    private TravlerCCAdapter mTravlerCCAdapter;


    private RecyclerView mRecyclerViewTravlers;
    private RecyclerView mRecyclerViewCC;
    private RecyclerView.LayoutManager mLayoutManager;

    private HashMap<String, ArrayList<String>> mBookingItems;
    private FontTextView mPaymentTextView;
    private FontTextView mTravlerTextView;
    private FontTextView mReviewTextView;

    private ImageView mPaymentImageView;
    private ImageView mTravlerImageView;
    private ImageView mReviewImageView;

    private FontTextView mHazerdiusText;
    private CheckBox mCheckBox;
    private boolean isCheckedBoxChecked;


    private static  HashMap<String, Double> cCTotalMap;

    public static Fragment newInstance(int position) {
        Fragment fragment = new SummaryPaymentFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.choose_cc_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mProceed = (FontButtonView) view.findViewById(R.id.cc_proceed);
        mProceed.setEnabled(false);
       // mProceedDisable = (FontTextView) view.findViewById(R.id.cc_proceed_disable);


        mPaymentTextView = (FontTextView) view.findViewById(R.id.steps_checkout_payment_text);
        mTravlerTextView = (FontTextView) view.findViewById(R.id.steps_checkout_travler_text);
        mReviewTextView = (FontTextView) view.findViewById(R.id.steps_checkout_review_text);

        mPaymentImageView = (ImageView) view.findViewById(R.id.steps_checkout_payment_image);
        mTravlerImageView = (ImageView) view.findViewById(R.id.steps_checkout_travler_image);
        mReviewImageView = (ImageView) view.findViewById(R.id.steps_checkout_review_image);
        mHazerdiusText = (FontTextView) view.findViewById(R.id.hazerdus_text);
        mCheckBox = (CheckBox)  view.findViewById(R.id.hazerdus_checkbox);
        mRecyclerViewCC = (RecyclerView) view.findViewById(R.id.summary_traveler_recyclerView_cc);







        SpannableString ss = new SpannableString(getString(R.string.i_have_read_and_understood_the_hazardous_nmaterial_notice));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                
                getFlowInterface().goToFragment(ToolBarNavEnum.HAZARDOUS_NOTICE.getNavNumber(), null);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 31, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        mHazerdiusText.setText(ss);
        mHazerdiusText.setMovementMethod(LinkMovementMethod.getInstance());
        mHazerdiusText.setHighlightColor(Color.TRANSPARENT);

        initCheckoutSteps();



        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JSONObject jsonObject = new JSONObject();
                try {

                    ArrayList<UserProfileVO> users = getFlowInterface().getListUsers();
                    JSONArray jsonArray = new JSONArray();
                    for (UserProfileVO userData : users) {
                        JSONObject jsonUser = new JSONObject();
                        jsonUser.put("dateofbirth", userData.getDob());
                        jsonUser.put("lastname", userData.getLastname());
                        jsonUser.put("city", userData.getCity());
                        jsonUser.put("primaryphone", userData.getPhone());
                        jsonUser.put("title", userData.getTitle().trim());
                        jsonUser.put("province", userData.getState());
                        jsonUser.put("specialrequest", "");//TODO need to add
                        jsonUser.put("paxguid", userData.getPaxid());
                        jsonUser.put("address", userData.getAddress());
                        jsonUser.put("passportcountry", userData.getCountry());//TODO need to add
                        jsonUser.put("middlename", "");//TODO need to add
                        jsonUser.put("email", userData.getEmailaddress());
                        jsonUser.put("paxmileage", 1);//TODO need to add

                        if (BuildConfig.IS_DEV) {
                            jsonUser.put("firstname", "Roofus");
                            jsonUser.put("lastname", "Summers");
                        } else {
                            jsonUser.put("firstname", userData.getFirstname());
                            jsonUser.put("lastname", userData.getLastname());
                        }

                        jsonUser.put("frequentflyernumber", "");//TODO need to add
                        jsonUser.put("postalcode", userData.getPostalcode());
                        jsonUser.put("country", userData.getCountry());
                        jsonUser.put("mealpreference", "");//TODO need to add




                        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
                        ArrayList<PassengersVO> passangers = travelOrder.getPassengerses();
                        JSONArray array = new JSONArray();
                        for (int i = 0; i < passangers.size(); i++) {

                            if (userData.getPaxid().equals(passangers.get(i).getmPaxguid())) {
                                for (String strItem : passangers.get(i).getmBookingItems()) {
                                    array.put(strItem);
                                }
                            }
                        }

                        jsonUser.put("bookingitems", array);
                        jsonArray.put(jsonUser);

                    }
                    jsonObject.put("paxdetails", jsonArray);
                    jsonObject.put("itineraryid", getActivityInterface().getSolutionID());


                    //Booking Items
                    JSONObject bookingItems = new JSONObject();
                    for (Map.Entry<String, ArrayList<String>> entry : mBookingItems.entrySet()) {
                        JSONArray bookingLocalArray = new JSONArray();
                        String strKey = entry.getKey();

                        for (String s : entry.getValue()) {
                            bookingLocalArray.put(s);
                        }
                        bookingItems.put(strKey, bookingLocalArray);
                    }
                    jsonObject.put("bookingitems", bookingItems);
                    JSONArray creditJsonArray = new JSONArray();
                    JSONObject creditObject = new JSONObject();

                    for (CreditCardItem selectedCreditCard : getFlowInterface().getCreditCardsSelected()) {
                        creditObject.put("cardnumber", selectedCreditCard.getToken());
                        creditObject.put("expirymonth", selectedCreditCard.getExpmonth());
                        ArrayList<CreditCardItem> cardList = new ArrayList<CreditCardItem>(getFlowInterface().getCreditCardsSelected());
                        for (int i = 0; i <cardList.size() ; i++) {

                            if(selectedCreditCard.getToken().equals(cardList.get(i).getToken())){
                                View view = mRecyclerViewCC.getChildAt(i);
                                EditText edit = (EditText)view.findViewById(R.id.ccv_edittext);
                                creditObject.put("cvv", edit.getText().toString());
                            }
                        }


                        if (BuildConfig.IS_DEV) {
                            creditObject.put("firstname", "Roofus");
                            creditObject.put("lastname", "Summers");
                        } else {
                            creditObject.put("firstname", selectedCreditCard.getBuyerfirstname());
                            creditObject.put("lastname", selectedCreditCard.getBuyerlastname());
                        }


                        creditObject.put("cardtype", selectedCreditCard.getCardtypeid());
                        creditObject.put("expiryyear", selectedCreditCard.getExpyear());

                        JSONObject creditAddressObject = new JSONObject();
                        creditAddressObject.put("postalcode", selectedCreditCard.getBuyerzip());
                        creditAddressObject.put("country", getActivityInterface().getCurrentUser().getCountry());
                        creditAddressObject.put("suite_apt", "");
                        creditAddressObject.put("city", getActivityInterface().getCurrentUser().getCity());
                        creditAddressObject.put("province", getActivityInterface().getCurrentUser().getState());
                        creditAddressObject.put("address", selectedCreditCard.getBuyeraddress());
                        creditObject.put("billingaddress", creditAddressObject);

                        creditJsonArray.put(creditObject);

                    }
                    jsonObject.put("carddetails", creditJsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                ConnectionManager.getInstance(getActivity()).pay(jsonObject, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        // Toast.makeText(getActivity().getApplicationContext(), "Trip Booked", Toast.LENGTH_SHORT).show();
                        getFlowInterface().goToFragment(ToolBarNavEnum.CHECKOUT_CONFIRMATION.getNavNumber(), null);

                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });

            }
        });

        loadBookingItemList();
        loadTravlerList(view);
        loadCCList(view);

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkMandatoryFields();
            }
        });



    }


    private void loadTravlerList(View v) {

        mRecyclerViewTravlers = (RecyclerView) v.findViewById(R.id.summary_traveler_recyclerView);

        mRecyclerViewTravlers.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewTravlers.setHasFixedSize(true);
        mRecyclerViewTravlers.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewTravlers.setLayoutManager(mLayoutManager);

        ArrayList<UserProfileVO> listUsers = getFlowInterface().getListUsers();
        mTravlerAdapter = new TravlerAdapter(listUsers, getActivity().getApplicationContext());
        mRecyclerViewTravlers.setAdapter(mTravlerAdapter);
    }

    private void loadCCList(View v) {


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewCC.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager   layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewTravlers.setLayoutManager(layoutManager);
        mRecyclerViewCC.setLayoutManager(mLayoutManager);

        ArrayList<CreditCardItem> list = new ArrayList<>(getFlowInterface().getCreditCardsSelected());

        mTravlerCCAdapter = new TravlerCCAdapter(cCTotalMap,list, getActivity().getApplicationContext());
        mRecyclerViewCC.setAdapter(mTravlerCCAdapter);
        mTravlerCCAdapter.setOnCheckCVV(new TravlerCCAdapter.onCheckCVV() {
            @Override
            public void onCheck() {
                checkMandatoryFields();
            }
        });
        Bundle args = getArguments();
        if (args != null) {
            mCheckBox.setChecked( args.getBoolean("i_accept"));
        }
    }

    private void loadBookingItemList() {

        mBookingItems = new HashMap<>();

        for (Map.Entry<String, String> entry : getFlowInterface().getBookingHashMap().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!mBookingItems.containsKey(value)) {
                ArrayList<String> array = new ArrayList<>();
                array.add(key);
                mBookingItems.put(value, array);


            } else {

                ArrayList<String> array = mBookingItems.get(value);
                array.add(key);
                mBookingItems.put(value, array);
            }


        }
        getCCAndTotal();

        Log.i("", "" + mBookingItems);
    }

    private void getCCAndTotal() {

        cCTotalMap = new HashMap<String, Double>();
        for (Map.Entry<String, ArrayList<String>> entry : mBookingItems.entrySet()) {

            CreditCardItem item = getCreditCard(entry.getKey());

            double iTotal = 0;


            for (String s : entry.getValue()) {
                NodesVO node = getNodeWithGuidAndPaxID(s);
                if (NodeTypeEnum.HOTEL.getType().equals(node.getmType())) {
                    iTotal += node.getmMinimumAmount();
                } else if (NodeTypeEnum.FLIGHT.getType().equals(node.getmType())) {
                    iTotal += node.getCost();
                }
            }
            cCTotalMap.put(item.getToken(),iTotal);

        }
    }


    private void initCheckoutSteps() {

        mPaymentTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        mTravlerTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        mReviewTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        mPaymentImageView.setBackgroundResource(R.drawable.step_menu_blue_stand);
        mTravlerImageView.setBackgroundResource(R.drawable.step_menu_blue_stand);
        mReviewImageView.setBackgroundResource(R.drawable.step_menu_blue_on);

    }

    @Override
    public void onStop() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onStop();

    }

    private synchronized  void checkMandatoryFields(){

        boolean isCVVEntered = checkCVVValueForList();

        if(mCheckBox.isChecked() && isCVVEntered){
            mProceed.setEnabled(true);
        }else{
            mProceed.setEnabled(false);
        }

    }

    private boolean checkCVVValueForList() {
        for (int i = 0; i <getFlowInterface().getCreditCardsSelected().size() ; i++) {

            View v = mRecyclerViewCC.getChildAt(i);
            EditText edit = (EditText)v.findViewById(R.id.ccv_edittext);
            int iLength =   edit.getText().length();
            if(iLength != 3){
                return false;
            }
        }

        return true;
    }
}
