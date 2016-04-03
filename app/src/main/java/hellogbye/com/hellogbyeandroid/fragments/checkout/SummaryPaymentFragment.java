package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.BuildConfig;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.TravlerAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.PaymentSummaryItem;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 12/2/15.
 */
public class SummaryPaymentFragment extends HGBAbtsractFragment {

    private ListView mListViewCC;

    private SummeryPaymentCCAdapter mAdapter;
    private ArrayList<PaymentSummaryItem> mArrayList;

    private FontTextView mProceed;
    private FontTextView mTotalPrice;
    private TravlerAdapter mTravlerAdapter;


    private RecyclerView mRecyclerViewTravlers;
    private RecyclerView.LayoutManager mLayoutManager;

    private HashMap<String, ArrayList<String>> mBookingItems;


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
        mArrayList = new ArrayList<>();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mListViewCC = (ListView) view.findViewById(R.id.select_cc_recyclerView);
        mProceed = (FontTextView) view.findViewById(R.id.cc_proceed);
        mTotalPrice = (FontTextView) view.findViewById(R.id.cc_total_price);


        mTotalPrice.setText(getActivityInterface().getTotalPrice());

        loadBookingItemList();

        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JSONObject jsonObject = new JSONObject();
                //  CreditCardItem selectedCreditCard = getSelectedCreditCrad();

                try {

                    JSONArray jsonArray = new JSONArray();
                    for (UserData userData : getActivityInterface().getListUsers()) {
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


                        //TODO this need to change once we switch new design


                        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
                        ArrayList<PassengersVO> passangers = travelOrder.getPassengerses();
                        JSONArray array = new JSONArray();
                        for (int i = 0; i < passangers.size(); i++) {

                            if (userData.getPaxid().equals(passangers.get(i).getmPaxguid())) {
                                for(String strItem :passangers.get(i).getmBookingItems()){
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


                    // bookingItems.put(selectedCreditCard.getToken(), bookingArray);
                    jsonObject.put("bookingitems", bookingItems);


                    JSONArray creditJsonArray = new JSONArray();

                    JSONObject creditObject = new JSONObject();

                    for (CreditCardItem selectedCreditCard : getActivityInterface().getCreditCardsSelected()) {
                        creditObject.put("cardnumber", selectedCreditCard.getToken());
                        creditObject.put("expirymonth", selectedCreditCard.getExpmonth());
                        creditObject.put("cvv", "123");//TODO this is hard coded needed to fix
                        creditObject.put("firstname", selectedCreditCard.getBuyerfirstname());
                        creditObject.put("lastname", selectedCreditCard.getBuyerlastname());
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
                        Toast.makeText(getActivity().getApplicationContext(),"Trip Booked",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getFragmentManager(), (String) data);
                    }
                });

            }
        });

        loadTravlerList(view);


    }


    private void loadBookingItemsToPaasenger() {

        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
        ArrayList<PassengersVO> passangers = travelOrder.getPassengerses();

        for (int i = 0; i < passangers.size(); i++) {

            for (int z = 0; z < getActivityInterface().getListUsers().size(); z++) {

                if (getActivityInterface().getListUsers().get(z).getPaxid().equals(passangers.get(i).getmPaxguid())) {
                    getActivityInterface().getListUsers().get(z).setBookingItems(passangers.get(i).getmBookingItems());

                }
            }

        }


    }

    private void loadTravlerList(View v) {

        mRecyclerViewTravlers = (RecyclerView) v.findViewById(R.id.summary_traveler_recyclerView);

        mRecyclerViewTravlers.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerViewTravlers.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewTravlers.setLayoutManager(mLayoutManager);

        mTravlerAdapter = new TravlerAdapter(getActivityInterface().getListUsers(), getActivity().getApplicationContext());
        mRecyclerViewTravlers.setAdapter(mTravlerAdapter);
    }

    private void loadBookingItemList() {

        mBookingItems = new HashMap<>();

        for (Map.Entry<String, String> entry : getActivityInterface().getBookingHashMap().entrySet()) {
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


        for (Map.Entry<String, ArrayList<String>> entry : mBookingItems.entrySet()) {
            PaymentSummaryItem paymentItem = new PaymentSummaryItem();

            CreditCardItem item = getCreditCard(entry.getKey());

            paymentItem.setName(item.getBuyerfirstname());
            paymentItem.setLast4(item.getLast4());

            double iTotal = 0;


            for (String s : entry.getValue()) {
                NodesVO node = getNodeWithGuidAndPaxID(s);
                if (NodeTypeEnum.HOTEL.getType().equals(node.getmType())) {
                    iTotal += node.getmMinimumAmount();
                } else if (NodeTypeEnum.FLIGHT.getType().equals(node.getmType())) {
                    iTotal += node.getCost();
                }
            }
            paymentItem.setTotal(String.format("%.2f", iTotal));
            mArrayList.add(paymentItem);
        }

        Log.i("", "" + mArrayList);
        mAdapter = new SummeryPaymentCCAdapter(mArrayList, R.layout.payment_summary_item, getActivity().getApplicationContext());
        mListViewCC.setAdapter(mAdapter);


    }


}
