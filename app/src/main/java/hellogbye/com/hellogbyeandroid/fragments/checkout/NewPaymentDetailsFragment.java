package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.creditcardadapters.AlertCheckoutAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymentChild;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymnentGroup;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 3/22/16.
 */
public class NewPaymentDetailsFragment extends HGBAbstractFragment {

    private FontTextView mTotalPrice;
    private FontButtonView mPaymentSubmit;
   // private FontTextView mPaymentDisableSubmit;
   // private FontTextView mTotalSelectCC;
    private ExpandableListView lv;
    private AlertDialog selectCCDialog;
    private ArrayList<String> itemsList;
    private FontTextView mSelectedView;
    private ArrayList<PassengersVO> passangers;
    private ExpandableListAdapter mAdapter;
    private ArrayList<PaymnentGroup> groups;
    private List<ArrayList<PaymentChild>> children;

    private FontTextView mPaymentTextView;
    private FontTextView mTravlerTextView;
    private FontTextView mReviewTextView;

    private ImageView mPaymentImageView;
    private ImageView mTravlerImageView;
    private ImageView mReviewImageView;

    private LinearLayout mTotalCCLinearLayout;
    private FontTextView mTotalCCText;
    private ImageView mTotalCCImage;
    private ImageView mTotalCCDropDown;




    public static Fragment newInstance(int position) {
        Fragment fragment = new NewPaymentDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.payment_details_layout, container, false);

        initSelectCCDialog();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTotalPrice = (FontTextView) view.findViewById(R.id.payment_total_price);
        mPaymentSubmit = (FontButtonView) view.findViewById(R.id.payment_submit);
        mPaymentSubmit.setEnabled(false);
      //  mPaymentDisableSubmit = (FontTextView) view.findViewById(R.id.payment_submit_disable);
        mPaymentTextView = (FontTextView) view.findViewById(R.id.steps_checkout_payment_text);
        mTravlerTextView = (FontTextView) view.findViewById(R.id.steps_checkout_travler_text);
        mReviewTextView = (FontTextView) view.findViewById(R.id.steps_checkout_review_text);

        mPaymentImageView = (ImageView) view.findViewById(R.id.steps_checkout_payment_image);
        mTravlerImageView= (ImageView) view.findViewById(R.id.steps_checkout_travler_image);
        mReviewImageView= (ImageView) view.findViewById(R.id.steps_checkout_review_image);


        mTotalCCText = (FontTextView) view.findViewById(R.id.passenger_select_cc);
        mTotalCCImage = (ImageView) view.findViewById(R.id.passenger_select_cc_image);
        mTotalCCDropDown = (ImageView) view.findViewById(R.id.passenger_select_cc_dropdown);
        mTotalCCLinearLayout = (LinearLayout) view.findViewById(R.id.passenger_select_cc_ll);





        lv = (ExpandableListView) view.findViewById(R.id.ex_list);
        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
        passangers = travelOrder.getPassengerses();
        mTotalPrice.setText("Total: $" + travelOrder.getmTotalPrice());
        mTotalCCLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectCCDialog != null) {
                    selectCCDialog.show();
                    LinearLayout ll = (LinearLayout)v;
                    mSelectedView = (FontTextView) ll.getChildAt(1);
                    mSelectedView.setTag("total");
                }

            }
        });


        mPaymentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendPaymentSolution();

            }
        });


        children = new ArrayList<>();
        groups = new ArrayList<>();

        for (PassengersVO passengersVO : passangers) {

            ArrayList<PaymentChild> passengerChildArray = new ArrayList<>();


            Map<String, NodesVO> hashMap = travelOrder.getItems();
            groups.add(new PaymnentGroup(passengersVO.getmName(), "$" + String.valueOf(passengersVO.getmTotalPrice()), true, passengersVO.getmItineraryItems(), getString(R.string.select_card)));

            ArrayList<String> passengerItems = passengersVO.getmItineraryItems();
            for (String passengerItem : passengerItems) {
                NodesVO nodesVO = hashMap.get(passengerItem);
                if (nodesVO != null) {
                    if (nodesVO != null) {//&& list.size() > 0) {
                        PaymentChild paymentChild;
                        if (NodeTypeEnum.HOTEL.getType().equals(nodesVO.getmType())) {
                            paymentChild = new PaymentChild(nodesVO.getmHotelName() +
                                    "\n" + HGBUtilityDate.parseDateToddMMyyyyForPayment(nodesVO.getmCheckIn()) +
                                    "-" + HGBUtilityDate.parseDateToddMMyyyyForPayment(nodesVO.getmCheckOut()) + "\n" +
                                    nodesVO.getRoomsVOs().get(0).getmRoomType() + " " +
                                    HGBUtilityDate.getDateDiffString(nodesVO.getmCheckIn(), nodesVO.getmCheckOut()),
                                    "$" + String.valueOf(nodesVO.getmMinimumAmount()), true, nodesVO.getmGuid(), passengersVO.getmPaxguid(), getString(R.string.select_card),null);
                            passengerChildArray.add(paymentChild);
                        } else if (NodeTypeEnum.FLIGHT.getType().equals(nodesVO.getmType())) {

                            paymentChild = new PaymentChild(nodesVO.getmOrigin() + "-" + nodesVO.getmDestination() + "\n" +
                                    nodesVO.getmOperatorName() + "" + nodesVO.getmEquipment() +
                                    "\n" + nodesVO.getDateOfCell(),
                                    "$" + String.valueOf(nodesVO.getCost()), true, nodesVO.getmGuid(), passengersVO.getmPaxguid(), getString(R.string.select_card),nodesVO.getParentflightid());
                            passengerChildArray.add(paymentChild);
                        }
                    }

                }
            }
            children.add(passengerChildArray);


        }

        mAdapter = new ExpandableListAdapter(groups, children);
        lv.setAdapter(mAdapter);
        lv.setGroupIndicator(null);
        initCheckoutSteps();
        getFlowInterface().getCreditCardsSelected().clear();

    }

    private void sendPaymentSolution() {

        HashSet<String> set = new HashSet<String>();
        for (String key : getFlowInterface().getBookingHashMap().keySet()) {
            set.add(key);
        }

        ConnectionManager.getInstance(getActivity()).checkoutSolutionId(getActivityInterface().getSolutionID(), set, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                Bundle args = new Bundle();
                Gson gson = new Gson();
                args.putString(HGBConstants.BUNDLE_PARENT_LIST, gson.toJson(groups));
                getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVELERS.getNavNumber(), args);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    public void initSelectCCDialog() {

        ConnectionManager.getInstance(getActivity()).getCreditCards(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                itemsList = new ArrayList<String>();

                getFlowInterface().setCreditCards((ArrayList<CreditCardItem>) data);
                for (CreditCardItem item : (ArrayList<CreditCardItem>) data) {
                    itemsList.add(item.getLast4());
                }
                itemsList.add(getString(R.string.add_card));
                itemsList.add(getString(R.string.remove_card));


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle(getResources().getString(R.string.payment_select_payment_method));

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    } });

               // final CharSequence[] list = itemsList.toArray(new String[itemsList.size()]);
                AlertCheckoutAdapter adapter = new AlertCheckoutAdapter(itemsList,getActivity().getApplicationContext());
                dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        String selectedText = itemsList.get(item);
                        //This is to set creditcard  array  final json for payment

                        if (selectedText.equals(getString(R.string.cancel))) {

                        } else if (selectedText.equals(getString(R.string.add_card))) {
                            getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
                        } else if (selectedText.equals(getString(R.string.remove_card))) {
                            if (!mSelectedView.getText().toString().equals(getString(R.string.select_card))) {
                                CreditCardItem selectedCreditCard = getCardByNumber(mSelectedView.getText().toString());
                                getFlowInterface().getCreditCardsSelected().remove(selectedCreditCard);
                                calculateCard(selectedCreditCard, mSelectedView, false);
                            }

                        } else {
                            CreditCardItem selectedCreditCard = getCardByNumber(selectedText);
                            getFlowInterface().getCreditCardsSelected().add(selectedCreditCard);
                            calculateCard(selectedCreditCard, mSelectedView, true);
                        }
                        mSelectedView = null;

                        if (getFlowInterface().getCreditCardsSelected().size() == 0) {
                            disablePaymentSolution();
                        } else {
                            enablePaymentSelection();
                        }

                        selectCCDialog.dismiss();
                    }
                });
                //Create alert dialog object via builder
                selectCCDialog = dialogBuilder.create();

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });


    }


    private void calculateCard(CreditCardItem selectedCreditCard, FontTextView mSelectedView, boolean add) {


        if (mSelectedView.getTag() instanceof PaymnentGroup) {
            mTotalCCText.setText(getString(R.string.select_card));
            mTotalCCImage.setVisibility(View.GONE);
            mTotalCCDropDown.setVisibility(View.VISIBLE);



            PaymnentGroup paymentGroup = (PaymnentGroup) mSelectedView.getTag();

            //This is to set bookingitem to each user in final json for payment
            for (int i = 0; i < passangers.size(); i++) {
                if (passangers.get(i).getmName().equals(paymentGroup.getNameText())) {
                    if (add) {
                        passangers.get(i).getmBookingItems().addAll(paymentGroup.getItems());
                    } else {
                        passangers.get(i).getmBookingItems().removeAll(paymentGroup.getItems());
                    }

                    if (add) {
                        groups.get(i).setCreditcard(selectedCreditCard.getLast4());
                        groups.get(i).setCreditcardid(selectedCreditCard.getCardtypeid());
                    } else {
                        groups.get(i).setCreditcard(getString(R.string.select_card));
                    }

                    for (int z = 0; z < children.get(i).size(); z++) {
                        if (add) {
                            children.get(i).get(z).setCreditcard(selectedCreditCard.getLast4());
                            children.get(i).get(z).setCreditcardid(selectedCreditCard.getCardtypeid());
                        } else {
                            children.get(i).get(z).setCreditcard(getString(R.string.select_card));
                        }

                    }
                }

            }


            //This is to set bookingitem array  final json for payment
            for (String strItem : paymentGroup.getItems()) {
                if (add) {
                    getFlowInterface().getBookingHashMap().put(strItem, selectedCreditCard.getToken());
                } else {
                    getFlowInterface().getBookingHashMap().remove(strItem);
                }

            }


        } else if (mSelectedView.getTag() instanceof PaymentChild) {
            mTotalCCText.setText(getString(R.string.select_card));
            mTotalCCImage.setVisibility(View.GONE);
            mTotalCCDropDown.setVisibility(View.VISIBLE);

            PaymentChild paymentchild = (PaymentChild) mSelectedView.getTag();

            //This is to set bookingitem to each user in final json for payment
            for (int i = 0; i < passangers.size(); i++) {
                if (passangers.get(i).getmPaxguid().equals(paymentchild.getPaxguid())) {

                    if (add) {
                        passangers.get(i).getmBookingItems().add(paymentchild.getGuid());
                    } else {
                        passangers.get(i).getmBookingItems().remove(paymentchild.getGuid());
                    }


                    if (groups.get(i).getNameText().equals(passangers.get(i).getmName())) {
                        groups.get(i).setCreditcard(getString(R.string.select_card));
                        for (int z = 0; z < children.get(i).size(); z++) {
                            if (paymentchild.getGuid().equals(children.get(i).get(z).getGuid())) {
                                if (add) {
                                    children.get(i).get(z).setCreditcard(selectedCreditCard.getLast4());
                                    children.get(i).get(z).setCreditcardid(selectedCreditCard.getCardtypeid());
                                } else {
                                    children.get(i).get(z).setCreditcard(getString(R.string.select_card));
                                }

                                break;
                            }

                        }
                    }
                }
            }


            //This is to set bookingitem array  final json for payment
            if (add) {
                getFlowInterface().getBookingHashMap().put(paymentchild.getGuid(), selectedCreditCard.getToken());
            } else {
                getFlowInterface().getBookingHashMap().remove(paymentchild.getGuid());
            }


        } else {
            if (add) {
                mTotalCCText.setText(selectedCreditCard.getLast4());
                mTotalCCImage.setVisibility(View.VISIBLE);
                mTotalCCDropDown.setVisibility(View.GONE);
                setCCIcon(mTotalCCImage, selectedCreditCard.getCardtypeid());
            } else {
                mTotalCCText.setText(getString(R.string.select_card));
                mTotalCCImage.setVisibility(View.GONE);
                mTotalCCDropDown.setVisibility(View.VISIBLE);
                getFlowInterface().getCreditCardsSelected().clear();
            }


            PassengersVO currentPassenger = getCurrentPassengerByName(getFlowInterface().getCurrentUser().getFirstname());

            for (PassengersVO passenger : passangers) {

                for (String passengerItem : passenger.getmItineraryItems()) {
                    if (add) {
                        getFlowInterface().getBookingHashMap().put(passengerItem, selectedCreditCard.getToken());
                        currentPassenger.getmBookingItems().addAll(passenger.getmItineraryItems());
                    } else {
                        getFlowInterface().getBookingHashMap().remove(passengerItem);
                        currentPassenger.getmBookingItems().removeAll(passenger.getmItineraryItems());
                    }
/*                    if (add) {
                        currentPassenger.getmBookingItems().addAll(passenger.getmItineraryItems());

                    } else {
                        currentPassenger.getmBookingItems().removeAll(passenger.getmItineraryItems());
                    }*/

                }


            }

            for (int i = 0; i < groups.size(); i++) {
                if (add) {
                    groups.get(i).setCreditcard(selectedCreditCard.getLast4());
                    groups.get(i).setCreditcardid(selectedCreditCard.getCardtypeid());
                } else {
                    groups.get(i).setCreditcard(getString(R.string.select_card));
                }

//                if (add) {
//                    groups.get(i).setCreditcard(selectedCreditCard.getLast4());
//                    groups.get(i).setCreditcardid(selectedCreditCard.getCardtypeid());
//                } else {
//                    groups.get(i).setCreditcard(getString(R.string.select_card));
//                }
                for (int z = 0; z < children.get(i).size(); z++) {
                    PaymentChild child = children.get(i).get(z);
                    if (add) {
                        child.setCreditcard(selectedCreditCard.getLast4());
                        child.setCreditcardid(selectedCreditCard.getCardtypeid());
                    } else {
                        child.setCreditcard(getString(R.string.select_card));
                    }

                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void setCCIcon(ImageView view, String cardid) {

        switch (cardid){
            case "1":
                view.setBackgroundResource(R.drawable.master_card);
                break;
            case "2":
                view.setBackgroundResource(R.drawable.master_card);
                break;
            case "3":
                view.setBackgroundResource(R.drawable.master_card);
                break;
            case "4":
                view.setBackgroundResource(R.drawable.visa);
                break;
        }

   /*     if (cardid.equals("1")) {
            view.setBackgroundResource(R.drawable.master_card);

        } else if (cardid.equals("2")) {
            //TODO need DISCOVERY card
            view.setBackgroundResource(R.drawable.master_card);

        } else if (cardid.equals("3")) {
            view.setBackgroundResource(R.drawable.master_card);

        } else if (cardid.equals("4")) {
            view.setBackgroundResource(R.drawable.visa);

        }*/


    }

    private PassengersVO getCurrentPassengerByName(String firstname) {

        for (PassengersVO passenger : passangers) {
            if (passenger.getmName().equals(firstname)) {
                return passenger;
            }
        }
        return null;
    }


    private CreditCardItem getCardByNumber(String selectedText) {
        for (CreditCardItem credit : getFlowInterface().getCreditCards()) {
            if (credit.getLast4().equals(selectedText)) {
                return credit;
            }
        }
        return null;
    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private final ArrayList<PaymnentGroup> groupsList;
        private List<ArrayList<PaymentChild>> childrenList = new ArrayList<>();


        public ExpandableListAdapter(ArrayList<PaymnentGroup> groups, List<ArrayList<PaymentChild>> children) {
            this.groupsList = groups;
            this.childrenList = children;
            this.inf = LayoutInflater.from(getActivity());
            // Initialize our hashmap containing our check states here
            //   this.mChildCheckStates = new HashMap<Integer, boolean[]>();


        }

        @Override
        public int getGroupCount() {
            return groupsList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childrenList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupsList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childrenList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final ChildViewHolder holder;
            if (convertView == null) {
                convertView = inf.inflate(R.layout.payment_child_item, parent, false);
                holder = new ChildViewHolder();

                holder.childNametext = (FontTextView) convertView.findViewById(R.id.payment_child_name);
                holder.childPricetext = (FontTextView) convertView.findViewById(R.id.payment_child_price);
                holder.childCheckBox = (CheckBox) convertView.findViewById(R.id.payment_child_checkbox);
                holder.childSelectCCText = (FontTextView) convertView.findViewById(R.id.passenger_select_cc);
                holder.childSelectCCImage = (ImageView) convertView.findViewById(R.id.passenger_select_cc_image);
                holder.childSelectCCDropDown = (ImageView) convertView.findViewById(R.id.passenger_select_cc_dropdown);
                holder.childSelectCCLinearLayout = (LinearLayout) convertView.findViewById(R.id.passenger_select_cc_ll);

                convertView.setTag(holder);
            } else {
                holder = (ChildViewHolder) convertView.getTag();
            }
            final PaymentChild child = (PaymentChild) getChild(groupPosition, childPosition);
            holder.childNametext.setText(child.getNameText());
            holder.childPricetext.setText(child.getTotalText());
            holder.childSelectCCText.setText(child.getCreditcard()
            );
            holder.childSelectCCLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (selectCCDialog != null) {
                        selectCCDialog.show();
                        LinearLayout ll = (LinearLayout)v;
                        mSelectedView = (FontTextView) ll.getChildAt(1);
                        mSelectedView.setTag(child);
                    }


                }
            });

            if(child.getCreditcard().equals(getString(R.string.select_card))){
                holder.childSelectCCDropDown.setVisibility(View.VISIBLE);
                holder.childSelectCCImage.setVisibility(View.GONE);
            }else{
                holder.childSelectCCDropDown.setVisibility(View.GONE);
                holder.childSelectCCImage.setVisibility(View.VISIBLE);
                setCCIcon(holder.childSelectCCImage,child.getCreditcardid());
            }
            if(child.getParentflight() != null){
                holder.childSelectCCDropDown.setVisibility(View.GONE);
                holder.childSelectCCImage.setVisibility(View.GONE);
            }else{
                holder.childSelectCCDropDown.setVisibility(View.VISIBLE);
                holder.childSelectCCImage.setVisibility(View.VISIBLE);
            }


//            holder.childCheckBox.setOnCheckedChangeListener(null);
//
//            if (mChildCheckStates.containsKey(groupPosition)) {
//            /*
//             * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
//			 * the value of the parent view (group) of this child (aka, the key),
//			 * then retrive the boolean array getChecked[]
//			*/
//                boolean getChecked[] = mChildCheckStates.get(groupPosition);
//
//                // set the check state of this position's checkbox based on the
//                // boolean value of getChecked[position]
//                holder.childCheckBox.setChecked(getChecked[childPosition]);
//
//            } else {
//
//			/*
//             * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
//			 * contain the value of the parent view (group) of this child (aka, the key),
//			 * (aka, the key), then initialize getChecked[] as a new boolean array
//			 *  and set it's size to the total number of children associated with
//			 *  the parent group
//			*/
//
//
//                boolean getChecked[] = new boolean[getChildrenCount(groupPosition)];
//                Arrays.fill(getChecked, true);
//
//                // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
//                mChildCheckStates.put(groupPosition, getChecked);
//
//                // set the check state of this position's checkbox based on the
//                // boolean value of getChecked[position]
//                holder.childCheckBox.setChecked(true);
//            }

//            holder.childCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//                    View nameHeaderPriceTextView = getNameHeaderPriceView(buttonView);
//
//
//                    childrenList.get(groupPosition).get(childPosition).setSelected(isChecked);
//                    if (isChecked) {
//
//                        boolean getChecked[] = mChildCheckStates.get(groupPosition);
//                        getChecked[childPosition] = isChecked;
//                        mChildCheckStates.put(groupPosition, getChecked);
//
//                        //  itenearySet.add(childrenList.get(groupPosition).get(childPosition).getGuid());
//
//
//                        //TODO this is a bug we need to deselct/select parent
//                        groupsList.get(groupPosition).setSelected(true);
//
//                        setGroupPrice(true, groupPosition, holder.childPricetext);
//                        setHeaderPrice(true, nameHeaderPriceTextView, holder.childPricetext.getText().toString());
//                        setTotalPrice(true, holder.childPricetext.getText().toString());
//
//                    } else {
//
//                        boolean getChecked[] = mChildCheckStates.get(groupPosition);
//                        getChecked[childPosition] = isChecked;
//                        mChildCheckStates.put(groupPosition, getChecked);
//
//                        //TODO this is a bug we need to deselct/select parent
//                        groupsList.get(groupPosition).setSelected(false);
//
//                        setGroupPrice(false, groupPosition, holder.childPricetext);
//                        setHeaderPrice(false, nameHeaderPriceTextView, holder.childPricetext.getText().toString());
//                        setTotalPrice(false, holder.childPricetext.getText().toString());
//                        //itenearySet.remove(childrenList.get(groupPosition).get(childPosition).getGuid());
//                    }
//                    notifyDataSetChanged();
//                }
//            });


//            holder.childCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    PaymnentGroup paymnentGroup = groupsList.get(groupPosition);
//                    childrenList.get(groupPosition).get(childPosition).setSelected(isChecked);
//                    if (isChecked) {
//                        double d = Double.valueOf(paymnentGroup.getTotalText()) + Double.valueOf(holder.childPricetext.getText().toString());
//                        groupsList.get(groupPosition).setTotalText(String.valueOf(d));
//
//                    } else {
//                        double d = Double.valueOf(paymnentGroup.getTotalText()) - Double.valueOf(holder.childPricetext.getText().toString());
//                        groupsList.get(groupPosition).setTotalText(String.valueOf(d));
//
//                    }
//                    notifyDataSetChanged();
//                }
//            });
            return convertView;
        }


        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final GroupViewHolder holder;

            if (convertView == null) {
                convertView = inf.inflate(R.layout.payment_group_item, parent, false);

                holder = new GroupViewHolder();
                holder.groupNametext = (FontTextView) convertView.findViewById(R.id.payment_group_name);
                holder.groupPricetext = (FontTextView) convertView.findViewById(R.id.payment_group_price);
                holder.groupSelectCC = (FontTextView) convertView.findViewById(R.id.passenger_select_cc);
                holder.groupSelectCCImage = (ImageView) convertView.findViewById(R.id.passenger_select_cc_image);
                holder.groupSelectCCDropDown = (ImageView) convertView.findViewById(R.id.passenger_select_cc_dropdown);
                holder.groupSelectCCLinearLayout = (LinearLayout) convertView.findViewById(R.id.passenger_select_cc_ll);

                // holder.groupCheckBox = (CheckBox) convertView.findViewById(R.id.payment_group_checkbox);
                holder.groupImageView = (ImageView) convertView.findViewById(R.id.payment_group_image);

                convertView.setTag(holder);
            } else {
                holder = (GroupViewHolder) convertView.getTag();

            }

            final PaymnentGroup group = (PaymnentGroup) getGroup(groupPosition);
            holder.groupNametext.setText(group.getNameText());
            holder.groupPricetext.setText(group.getTotalText());
            holder.groupSelectCC.setText(group.getCreditcard());
            holder.groupSelectCCLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectCCDialog != null) {
                        selectCCDialog.show();
                        LinearLayout ll =(LinearLayout) v;
                        mSelectedView = (FontTextView) ll.getChildAt(1);
                        mSelectedView.setTag(group);
                    }


                }
            });

            if (group.isSelected()) {
                holder.groupImageView.setBackgroundResource(R.drawable.expand_copy_3);
            } else {
                holder.groupImageView.setBackgroundResource(R.drawable.minimize);
            }
            holder.groupImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lv.isGroupExpanded(groupPosition)) {
                        lv.collapseGroup(groupPosition);
                        v.setBackgroundResource(R.drawable.expand_copy_3);
                        group.setSelected(true);
                    } else {
                        lv.expandGroup(groupPosition);
                        group.setSelected(false);
                        v.setBackgroundResource(R.drawable.minimize);
                    }
                }
            });

            if(group.getCreditcard().equals(getString(R.string.select_card))){
                holder.groupSelectCCDropDown.setVisibility(View.VISIBLE);
                holder.groupSelectCCImage.setVisibility(View.GONE);
            }else{
                holder.groupSelectCCDropDown.setVisibility(View.GONE);
                holder.groupSelectCCImage.setVisibility(View.VISIBLE);
                setCCIcon(holder.groupSelectCCImage,group.getCreditcardid());
            }


//            holder.groupCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    View nameHeaderPriceTextView = getNameHeaderPriceView(buttonView);
//                    if (isChecked) {
//                        double iSum = 0;
//                        for (PaymentChild child : childrenList.get(groupPosition)) {
//                            iSum += Double.valueOf(child.getTotalText().substring(1));
//                            // itenearySet.add(child.getGuid());
//                        }
//                        boolean getChecked[] = mChildCheckStates.get(groupPosition);
//                        if (getChecked != null) {
//                            Arrays.fill(getChecked, true);
//                            mChildCheckStates.put(groupPosition, getChecked);
//                        }
//
//
//                        groupsList.get(groupPosition).setTotalText("$" + String.valueOf(iSum));
//                        setHeaderPrice(true, nameHeaderPriceTextView, String.valueOf(iSum));
//
//                        setTotalPrice(true, String.valueOf(iSum));
//
//                    } else {
//                        boolean getChecked[] = mChildCheckStates.get(groupPosition);
//                        if (getChecked != null) {
//                            Arrays.fill(getChecked, false);
//                            mChildCheckStates.put(groupPosition, getChecked);
//                        }
//
//                        groupsList.get(groupPosition).setTotalText("$0.0");
//                        setHeaderPrice(false, nameHeaderPriceTextView, holder.groupPricetext.getText().toString());
//                        setTotalPrice(false, holder.groupPricetext.getText().toString());
//                        for (PaymentChild child : childrenList.get(groupPosition)) {
//
//                            // itenearySet.remove(child.getGuid());
//                        }
//                    }
//                    notifyDataSetChanged();
//                }
//            });


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class GroupViewHolder {
            FontTextView groupNametext;
            FontTextView groupPricetext;
            //CheckBox groupCheckBox;
            ImageView groupImageView;
            FontTextView groupSelectCC;
            ImageView groupSelectCCDropDown;
            ImageView groupSelectCCImage;
            LinearLayout groupSelectCCLinearLayout;
        }

        private class ChildViewHolder {

            FontTextView childNametext;
            FontTextView childPricetext;
            CheckBox childCheckBox;
            FontTextView childSelectCCText;
            ImageView childSelectCCImage;
            ImageView childSelectCCDropDown;
            LinearLayout childSelectCCLinearLayout;
        }


        private void setGroupPrice(boolean add, int groupPosition, FontTextView childPricetext) {
            PaymnentGroup paymnentGroup = groupsList.get(groupPosition);
            String string = childPricetext.getText().toString().substring(1);

            if (add) {
                double d = Double.valueOf(paymnentGroup.getTotalText().substring(1)) + Double.valueOf(string);
                groupsList.get(groupPosition).setTotalText("$" + String.valueOf(d));
            } else {
                double d = Double.valueOf(paymnentGroup.getTotalText().substring(1)) - Double.valueOf(string);
                groupsList.get(groupPosition).setTotalText("$" + String.valueOf(d));
            }

        }

        private View getNameHeaderPriceView(CompoundButton buttonView) {
            ViewGroup viewParent = (ViewGroup) buttonView.getParent();
            ViewParent vrr = viewParent.getParent();
            ViewGroup vrr1 = (ViewGroup) vrr.getParent();
            View v4 = vrr1.getChildAt(0);
            return v4;
        }

        private void setHeaderPrice(boolean add, View nameHeaderPriceTextView, String value) {
            FontTextView priceFontText = (FontTextView) nameHeaderPriceTextView.findViewById(R.id.checkout_name_price);
            String string = priceFontText.getText().toString().substring(1);
            if (value.contains("$")) {
                value = value.substring(1);
            }
            if (add) {
                double dTotal = Double.valueOf(string) + Double.valueOf(value);
                priceFontText.setText("$" + String.format("%.2f", dTotal));
            } else {
                double dTotal = Double.valueOf(string) - Double.valueOf(value);
                priceFontText.setText("$" + String.format("%.2f", dTotal));
            }

        }
    }

    private void setTotalPrice(boolean b, String s) {
        String strTotal = mTotalPrice.getText().toString().substring(1);
        if (s.contains("$")) {
            s = s.substring(1);
        }
        if (b) {
            double d = Double.valueOf(strTotal) + Double.valueOf(s);
            mTotalPrice.setText("$" + String.format("%.2f", d));
        } else {
            double d = Double.valueOf(strTotal) - Double.valueOf(s);
            mTotalPrice.setText("$" + String.format("%.2f", d));
        }
    }

    private void enablePaymentSelection() {
       /* mPaymentDisableSubmit.setVisibility(View.GONE);
        mPaymentSubmit.setVisibility(View.VISIBLE);*/

        mPaymentSubmit.setEnabled(true);
    }

    private void disablePaymentSolution() {
        /*mPaymentDisableSubmit.setVisibility(View.VISIBLE);
        mPaymentSubmit.setVisibility(View.GONE);*/

        mPaymentSubmit.setEnabled(false);
    }


    private void initCheckoutSteps(){

        mPaymentTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_063345));
        mTravlerTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_777776));
        mReviewTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_777776));
        mPaymentImageView.setBackgroundResource(R.drawable.step_menu_blue_on);
        mTravlerImageView.setBackgroundResource(R.drawable.step_menu_gray);
        mReviewImageView.setBackgroundResource(R.drawable.step_menu_gray);

    }


}
