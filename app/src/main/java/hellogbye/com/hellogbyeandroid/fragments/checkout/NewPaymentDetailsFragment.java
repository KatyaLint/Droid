package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import hellogbye.com.hellogbyeandroid.R;

import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
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
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 3/22/16.
 */
public class NewPaymentDetailsFragment extends HGBAbtsractFragment {

   // private View rootView;
    private LinearLayout mRootView;
    private FontTextView mTotalPrice;
    private FontTextView mPaymentSubmit;
    private FontTextView mTotalSelectCC;
    private ExpandableListView lv;
    private AlertDialog selectCCDialog;
    private ArrayList<String> itemsList;
    private FontTextView mSelectedView;


    public static Fragment newInstance(int position) {
        Fragment fragment = new NewPaymentDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSelectCCDialog();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.payment_details_layout, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // mRootView = (LinearLayout) view.findViewById(R.id.payment_details_root);
        mTotalPrice = (FontTextView) view.findViewById(R.id.payment_total_price);
        mPaymentSubmit = (FontTextView) view.findViewById(R.id.payment_submit);
        lv = (ExpandableListView) view.findViewById(R.id.ex_list);
        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
        ArrayList<PassengersVO> passangers = travelOrder.getPassengerses();
        mTotalPrice.setText("$"+travelOrder.getmTotalPrice());
        mTotalSelectCC = (FontTextView)view.findViewById(R.id.total_select_cc);
        mTotalSelectCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCCDialog.show();
                mSelectedView = (FontTextView)v;
            }
        });

        List<ArrayList<PaymentChild>> children = new ArrayList<>();
        ArrayList<PaymnentGroup> groups = new ArrayList<>();

        for (PassengersVO passengersVO : passangers) {
//            View child = getActivity().getLayoutInflater().inflate(R.layout.payment_details_person_layout, null);
//            mRootView.addView(child);
            ArrayList<PaymentChild> passengerChildArray = new ArrayList<>();


            Map<String, NodesVO> hashMap = travelOrder.getItems();
            groups.add(new PaymnentGroup(passengersVO.getmName(), "$" + String.valueOf(passengersVO.getmTotalPrice()), true));

            ArrayList<String> passengerItems = passengersVO.getmItineraryItems();
            for (String passengerItem : passengerItems) {
                NodesVO nodesVO = hashMap.get(passengerItem);
                if (nodesVO != null) {
                    if (nodesVO != null) {//&& list.size() > 0) {
                        double totalprice = passengersVO.getmTotalPrice();
                        PaymentChild paymentChild;
                        if (NodeTypeEnum.HOTEL.getType().equals(nodesVO.getmType())) {
                            paymentChild = new PaymentChild(nodesVO.getmHotelName() +
                                    "\n" + nodesVO.getmCheckIn() +
                                    "-" + nodesVO.getmCheckOut() + "\n" +
                                    nodesVO.getRoomsVOs().get(0).getmRoomType() + " " +
                                    HGBUtility.getDateDiffString(nodesVO.getmCheckIn(), nodesVO.getmCheckOut()),
                                    "$" + String.valueOf(nodesVO.getmMinimumAmount()), true, nodesVO.getmGuid());
                            passengerChildArray.add(paymentChild);
                            //  itenearySet.add(nodesVO.getmGuid());
                        } else if (NodeTypeEnum.FLIGHT.getType().equals(nodesVO.getmType())) {
                            paymentChild = new PaymentChild(nodesVO.getmOrigin() + "-" + nodesVO.getmDestination() + "\n" +
                                    nodesVO.getmOperatorName() + "" + nodesVO.getmEquipment() +
                                    "\n" + nodesVO.getDateOfCell(),
                                    "$" + String.valueOf(nodesVO.getCost()), true, nodesVO.getmGuid());
                            passengerChildArray.add(paymentChild);
                            //  itenearySet.add(nodesVO.getmGuid());
                        }
                    }

                }
            }
            children.add(passengerChildArray);


        }
        lv.setAdapter(new ExpandableListAdapter(groups, children));
        lv.setGroupIndicator(null);
    }

    public void initSelectCCDialog()
    {

        ConnectionManager.getInstance(getActivity()).getCreditCards(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                itemsList = new ArrayList<String>();

                getActivityInterface().setCreditCards((ArrayList<CreditCardItem>) data);
                for(CreditCardItem item : (ArrayList<CreditCardItem>) data){
                    itemsList.add(item.getLast4());
                }
                itemsList.add(getString(R.string.add_card));
                itemsList.add(getString(R.string.remove_card));
                itemsList.add(getString(R.string.cancel));

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Select Payment Method");

                final CharSequence[] list = itemsList.toArray(new String[itemsList.size()]);
                dialogBuilder.setItems(list, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        String selectedText = list[item].toString();
                        if(selectedText.equals(getString(R.string.cancel))){

                        }else if(selectedText.equals(getString(R.string.add_card))){
                            getActivityInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
                        }else if(selectedText.equals(getString(R.string.remove_card))){
                           // getActivityInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
                            mSelectedView.setText(R.string.select_card);
                        }else{
                            if(mSelectedView != null){
                                mSelectedView.setText(selectedText);

                            }

                        }
                        mSelectedView = null;

                        selectCCDialog.dismiss();
                    }
                });
                //Create alert dialog object via builder
                selectCCDialog = dialogBuilder.create();

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });



    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private final ArrayList<PaymnentGroup> groupsList;
        private List<ArrayList<PaymentChild>> childrenList = new ArrayList<>();

        // Hashmap for keeping track of our checkbox check states
       // private final HashMap<Integer, boolean[]> mChildCheckStates;

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
                holder.childSelectCC = (FontTextView) convertView.findViewById(R.id.passenger_item_select_cc);

                convertView.setTag(holder);
            } else {
                holder = (ChildViewHolder) convertView.getTag();
            }
            PaymentChild child = (PaymentChild) getChild(groupPosition, childPosition);
            holder.childNametext.setText(child.getNameText());
            holder.childPricetext.setText(child.getTotalText());
            holder.childSelectCC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCCDialog.show();
                    mSelectedView =(FontTextView) v;
                }
            });


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
               // holder.groupCheckBox = (CheckBox) convertView.findViewById(R.id.payment_group_checkbox);
                holder.groupImageView = (ImageView) convertView.findViewById(R.id.payment_group_image);

                convertView.setTag(holder);
            } else {
                holder = (GroupViewHolder) convertView.getTag();

            }
            holder.groupImageView .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO need to fix this up not good


                    if (lv.isGroupExpanded(groupPosition)) {
                        lv.collapseGroup(groupPosition);
                        v.setBackgroundResource(R.drawable.expand);
                    } else {
                        lv.expandGroup(groupPosition);

                        v.setBackgroundResource(R.drawable.collapse);
                    }
                }
            });
            PaymnentGroup group = (PaymnentGroup) getGroup(groupPosition);
            holder.groupNametext.setText(group.getNameText());
            holder.groupPricetext.setText(group.getTotalText());

            holder.groupSelectCC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCCDialog.show();
                    mSelectedView = (FontTextView)v;
                }
            });


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

        }

        private class ChildViewHolder {

            FontTextView childNametext;
            FontTextView childPricetext;
            CheckBox childCheckBox;
            FontTextView childSelectCC;
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


}
