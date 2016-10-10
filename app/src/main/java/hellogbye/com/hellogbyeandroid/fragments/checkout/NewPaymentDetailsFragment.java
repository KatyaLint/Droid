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
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymentChild;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymnentGroup;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static hellogbye.com.hellogbyeandroid.utilities.HGBUtility.setCCIcon;

/**
 * Created by arisprung on 3/22/16.
 */
public class NewPaymentDetailsFragment extends HGBAbstractFragment {

    private FontTextView mTotalPrice;
    private FontButtonView mPaymentSubmit;
    private ExpandableListView lv;
    private AlertDialog selectCCDialog;
    private ArrayList<CreditCardItem> itemsList;
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
/*                if (nodesVO != null) {*/
                    if (nodesVO != null) {//&& list.size() > 0) {
                        PaymentChild paymentChild;
                        if (NodeTypeEnum.HOTEL.getType().equals(nodesVO.getmType())) {
                            paymentChild = new PaymentChild(nodesVO.getmHotelName() +
                                    "\n" + HGBUtilityDate.parseDateToMMddyyyyForPayment(nodesVO.getmCheckIn()) +
                                    "-" + HGBUtilityDate.parseDateToMMddyyyyForPayment(nodesVO.getmCheckOut()) + "\n" +
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

   //             }
            }
            children.add(passengerChildArray);
        }


        mAdapter = new ExpandableListAdapter(groups, children);
        lv.setAdapter(mAdapter);
        lv.setGroupIndicator(null);
        initCheckoutSteps();
        getFlowInterface().getCreditCardsSelected().clear();

    }


    private void getUsersList(){
        ConnectionManager.getInstance(getActivity()).getTravellersInforWithSolutionId(getActivityInterface().getTravelOrder().getmSolutionID(),
                new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<ArrayList<UserProfileVO>> children = new ArrayList<ArrayList<UserProfileVO>>();
                getFlowInterface().setListUsers((ArrayList<UserProfileVO>) data);
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

    private void sendPaymentSolution() {

        HashSet<String> set = new HashSet<String>();
        for (String key : getFlowInterface().getBookingHashMap().keySet()) {
            set.add(key);
        }

        ConnectionManager.getInstance(getActivity()).checkoutSolutionId(getActivityInterface().getSolutionID(), set, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                getUsersList();
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

                itemsList = new ArrayList<>();
                getFlowInterface().setCreditCards((ArrayList<CreditCardItem>) data);
                for (CreditCardItem item : (ArrayList<CreditCardItem>) data) {
                    itemsList.add(item);
                }

                CreditCardItem cAdd = new CreditCardItem();
                cAdd.setLast4(getActivity().getResources().getString(R.string.add_card));
                CreditCardItem cLast = new CreditCardItem();
                cLast.setLast4(getActivity().getResources().getString(R.string.remove_card));
                itemsList.add(cAdd);
                itemsList.add(cLast);


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle(getResources().getString(R.string.payment_select_payment_method));

                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    } });

                AlertCheckoutAdapter adapter = new AlertCheckoutAdapter(itemsList,getActivity().getApplicationContext());
                dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        CreditCardItem selectedText = itemsList.get(item);
                        if(selectedText == null){
                            ErrorMessage("Wrong credit Card");
                            return;
                        }
                        //This is to set creditcard  array  final json for payment

                        if ( selectedText.getLast4().equals(getString(R.string.cancel))) {

                        } else if (selectedText.getLast4().equals(getString(R.string.add_card))) {
                            getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
                        } else if (selectedText.getLast4().equals(getString(R.string.remove_card))) {
                            if (!mSelectedView.getText().toString().equals(getString(R.string.select_card))) {
                                CreditCardItem selectedCreditCard = getCardByNumber(mSelectedView.getText().toString());
                                getFlowInterface().getCreditCardsSelected().remove(selectedCreditCard);
                                calculateCard(selectedCreditCard, mSelectedView, false);
                            }

                        } else {
                            CreditCardItem selectedCreditCard = getCardByNumber(selectedText.getLast4());
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
                HGBUtility.setCCIcon(mTotalCCImage, selectedCreditCard.getCardtypeid());
            } else {
                mTotalCCText.setText(getString(R.string.select_card));
                mTotalCCImage.setVisibility(View.GONE);
                mTotalCCDropDown.setVisibility(View.VISIBLE);
                getFlowInterface().getCreditCardsSelected().clear();
            }


            PassengersVO currentPassenger = getCurrentPassengerByName(getActivityInterface().getCurrentUser().getFirstname());

            for (PassengersVO passenger : passangers) {

                for (String passengerItem : passenger.getmItineraryItems()) {
                    if (add) {
                        getFlowInterface().getBookingHashMap().put(passengerItem, selectedCreditCard.getToken());
                        currentPassenger.getmBookingItems().addAll(passenger.getmItineraryItems());
                    } else {
                        getFlowInterface().getBookingHashMap().remove(passengerItem);
                        currentPassenger.getmBookingItems().removeAll(passenger.getmItineraryItems());
                    }

                }
            }

            for (int i = 0; i < groups.size(); i++) {
                if (add) {
                    groups.get(i).setCreditcard(selectedCreditCard.getLast4());
                    groups.get(i).setCreditcardid(selectedCreditCard.getCardtypeid());
                } else {
                    groups.get(i).setCreditcard(getString(R.string.select_card));
                }

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
            if (selectedText.equals(credit.getLast4())) {
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
            if(child.getParentflight() == null){
                holder.childSelectCCLinearLayout.setVisibility(View.VISIBLE);

            }else{
                holder.childSelectCCLinearLayout.setVisibility(View.INVISIBLE);
            }

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

        mPaymentTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        mTravlerTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_565656));
        mReviewTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_565656));
        mPaymentImageView.setBackgroundResource(R.drawable.step_menu_blue_on);
        mTravlerImageView.setBackgroundResource(R.drawable.step_menu_gray);
        mReviewImageView.setBackgroundResource(R.drawable.step_menu_gray);

    }


}
