//package hellogbye.com.hellogbyeandroid.fragments.checkout;
//
//import android.app.Fragment;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ExpandableListView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.amulyakhare.textdrawable.TextDrawable;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import hellogbye.com.hellogbyeandroid.R;
//import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
//import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
//import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
//import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymentChild;
//import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymnentGroup;
//import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
//import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
//import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
//import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
//import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
//import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
//import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
//import hellogbye.com.hellogbyeandroid.views.FontTextView;
//
///**
// * Created by arisprung on 11/15/15.
// */
//public class PaymentDetailsFragemnt extends HGBAbtsractFragment {
//
//
//
//
//    private FontTextView mTotalPrice;
//    private FontTextView mPaymentSubmit;
//    private HashSet<String> itenearySet = new HashSet<String>();
//
//
//    // private ArrayList<FontTextView> mArralistView;
//
//
//    public static Fragment newInstance(int position) {
//        Fragment fragment = new PaymentDetailsFragemnt();
//        Bundle args = new Bundle();
//        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.payment_details_layout, container, false);
//
//        return v;
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mTotalPrice = (FontTextView) view.findViewById(R.id.payment_total_price);
//        mPaymentSubmit = (FontTextView) view.findViewById(R.id.payment_submit);
//
//
//        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
//        ArrayList<PassengersVO> passangers = travelOrder.getPassengerses();
//        for (PassengersVO passengersVO : passangers) {
//            View child = getActivity().getLayoutInflater().inflate(R.layout.payment_details_person_layout, null);
//            //mRootView.addView(child);
//            ImageView imageView = (ImageView) child.findViewById(R.id.checkout_image_name_round_image_view);
//            FontTextView nameFontText = (FontTextView) child.findViewById(R.id.checkout_name_text);
//            FontTextView priceFontText = (FontTextView) child.findViewById(R.id.checkout_name_price);
//
//            ExpandableListView lv = (ExpandableListView) child.findViewById(R.id.ex_list);
//
//            nameFontText.setText(passengersVO.getmName());
//            priceFontText.setText("$" + String.valueOf(passengersVO.getmTotalPrice()));
//            TextDrawable drawable2 = TextDrawable.builder()
//                    .buildRound(passengersVO.getmName().substring(0, 1), Color.GRAY);
//            imageView.setImageDrawable(drawable2);
//
//            List<ArrayList<PaymentChild>> children = new ArrayList<>();
//            ArrayList<PaymnentGroup> groups = new ArrayList<>();
//
//            boolean isHotelAdded = false;
//            boolean isFlightAdded = false;
//            ArrayList<PaymentChild> flightChildArray = null;
//            ArrayList<PaymentChild> hotelChildArray = null;
//            Set<String> set = new HashSet<String>();
//
//
//            // passanger specific get id
//            // run for all id for current passanger
//
//
//            ArrayList<PassengersVO> passangersVOs = travelOrder.getPassengerses();
//            Map<String, NodesVO> hashMap = travelOrder.getItems();
//            for (PassengersVO passenger : passangersVOs) {
//                ArrayList<String> passengerItems = passenger.getmItineraryItems();
//                for (String passengerItem : passengerItems) {
//                    NodesVO passengerNode = hashMap.get(passengerItem);
//                    if (passengerNode != null) {
//                        set.add(passengerNode.getmGuid());
//                        if (NodeTypeEnum.HOTEL.getType().equals(passengerNode.getmType())) {
//                            if (!isHotelAdded) {
//                                hotelChildArray = new ArrayList<>();
//                                groups.add(new PaymnentGroup("Hotel", "$" + String.valueOf(passengersVO.getmTotalHotelPrice()), true));
//
//                                isHotelAdded = true;
//                            }
//                        } else if (NodeTypeEnum.FLIGHT.getType().equals(passengerNode.getmType())) {
//                            if (!isFlightAdded) {
//                                flightChildArray = new ArrayList<>();
//                                groups.add(new PaymnentGroup("Flight", "$" + String.valueOf(passengersVO.getmTotalFlightPrice()), true));
//                                isFlightAdded = true;
//                            }
//                        }
//                    }
//                }
//            }
//
//
//
//            for (String strGuid : set) {
//
//                NodesVO nodesVO = getNodeWithGuidAndPaxID(strGuid, passengersVO.getmPaxguid(), travelOrder);
//
//                if (nodesVO != null) {//&& list.size() > 0) {
//                    double totalprice = passengersVO.getmTotalPrice();
////                    for (NodesVO n : list) {
////                        totalprice += n.getCost();
////                    }
//                    //  NodesVO nodesVO = list.get(0);
//                    PaymentChild paymentChild;
//                    if (NodeTypeEnum.HOTEL.getType().equals(nodesVO.getmType())) {
//                        paymentChild = new PaymentChild(nodesVO.getmHotelName() +
//                                "\n" +nodesVO.getmCheckIn() +
//                                "-" + nodesVO.getmCheckOut() + "\n" +
//                                nodesVO.getRoomsVOs().get(0).getmRoomType() + " " +
//                                HGBUtility.getDateDiffString(nodesVO.getmCheckIn(), nodesVO.getmCheckOut()),
//                                "$" + String.valueOf(nodesVO.getmMinimumAmount()), true, nodesVO.getmGuid());
//                        hotelChildArray.add(paymentChild);
//                        itenearySet.add(nodesVO.getmGuid());
//                    } else if (NodeTypeEnum.FLIGHT.getType().equals(nodesVO.getmType())) {
//                        paymentChild = new PaymentChild(nodesVO.getmOrigin() + "-" + nodesVO.getmDestination() + "\n" +
//                                nodesVO.getmOperatorName() + "" + nodesVO.getmEquipment() +
//                                "\n" + nodesVO.getDateOfCell(),
//                                "$" + String.valueOf(totalprice), true, nodesVO.getmGuid());
//                        flightChildArray.add(paymentChild);
//                        itenearySet.add(nodesVO.getmGuid());
//                    }
//                }
//            }
//            children.add(flightChildArray);
//            children.add(hotelChildArray);
//
//            lv.setAdapter(new ExpandableListAdapter(groups, children));
//            lv.setGroupIndicator(null);
//
//        }
//
//
//        mTotalPrice.setText("$" + getActivityInterface().getTravelOrder().getmTotalPrice());
//
//        mPaymentSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivityInterface().setItenerayItems(itenearySet);
//
//                ConnectionManager.getInstance(getActivity()).checkoutSolutionId(getActivityInterface().getSolutionID(), itenearySet, new ConnectionManager.ServerRequestListener() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        getActivityInterface().setTotalPrice(mTotalPrice.getText().toString());
//                        getActivityInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVLERS.getNavNumber(), null);
//                    }
//
//                    @Override
//                    public void onError(Object data) {
//                        HGBErrorHelper errorHelper = new HGBErrorHelper();
//                        errorHelper.show(getFragmentManager(), (String) data);
//                    }
//                });
//            }
//        });
//    }
//
//    public class ExpandableListAdapter extends BaseExpandableListAdapter {
//
//        private final LayoutInflater inf;
//        private final ArrayList<PaymnentGroup> groupsList;
//        private List<ArrayList<PaymentChild>> childrenList = new ArrayList<>();
//
//        // Hashmap for keeping track of our checkbox check states
//        private final HashMap<Integer, boolean[]> mChildCheckStates;
//
//        public ExpandableListAdapter(ArrayList<PaymnentGroup> groups, List<ArrayList<PaymentChild>> children) {
//            this.groupsList = groups;
//            this.childrenList = children;
//            this.inf = LayoutInflater.from(getActivity());
//            // Initialize our hashmap containing our check states here
//            this.mChildCheckStates = new HashMap<Integer, boolean[]>();
//
//
//        }
//
//        @Override
//        public int getGroupCount() {
//            return groupsList.size();
//        }
//
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            return childrenList.get(groupPosition).size();
//        }
//
//        @Override
//        public Object getGroup(int groupPosition) {
//            return groupsList.get(groupPosition);
//        }
//
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            return childrenList.get(groupPosition).get(childPosition);
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return groupPosition;
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return childPosition;
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//
//        @Override
//        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//
//            final ChildViewHolder holder;
//            if (convertView == null) {
//                convertView = inf.inflate(R.layout.payment_child_item, parent, false);
//                holder = new ChildViewHolder();
//
//                holder.childNametext = (FontTextView) convertView.findViewById(R.id.payment_child_name);
//                holder.childPricetext = (FontTextView) convertView.findViewById(R.id.payment_child_price);
//                holder.childCheckBox = (CheckBox) convertView.findViewById(R.id.payment_child_checkbox);
//
//                convertView.setTag(holder);
//            } else {
//                holder = (ChildViewHolder) convertView.getTag();
//            }
//            PaymentChild child = (PaymentChild) getChild(groupPosition, childPosition);
//            holder.childNametext.setText(child.getNameText());
//            holder.childPricetext.setText(child.getTotalText());
//
//
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
//
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
//                        itenearySet.add(childrenList.get(groupPosition).get(childPosition).getGuid());
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
//                        itenearySet.remove(childrenList.get(groupPosition).get(childPosition).getGuid());
//                    }
//                    notifyDataSetChanged();
//                }
//            });
//
//
////            holder.childCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////                @Override
////                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                    PaymnentGroup paymnentGroup = groupsList.get(groupPosition);
////                    childrenList.get(groupPosition).get(childPosition).setSelected(isChecked);
////                    if (isChecked) {
////                        double d = Double.valueOf(paymnentGroup.getTotalText()) + Double.valueOf(holder.childPricetext.getText().toString());
////                        groupsList.get(groupPosition).setTotalText(String.valueOf(d));
////
////                    } else {
////                        double d = Double.valueOf(paymnentGroup.getTotalText()) - Double.valueOf(holder.childPricetext.getText().toString());
////                        groupsList.get(groupPosition).setTotalText(String.valueOf(d));
////
////                    }
////                    notifyDataSetChanged();
////                }
////            });
//            return convertView;
//        }
//
//
//        @Override
//        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//            final GroupViewHolder holder;
//
//            if (convertView == null) {
//                convertView = inf.inflate(R.layout.payment_group_item, parent, false);
//
//                holder = new GroupViewHolder();
//                holder.groupNametext = (FontTextView) convertView.findViewById(R.id.payment_group_name);
//                holder.groupPricetext = (FontTextView) convertView.findViewById(R.id.payment_group_price);
////                holder.groupCheckBox = (CheckBox) convertView.findViewById(R.id.payment_group_checkbox);
//                holder.groupImageView = (ImageView) convertView.findViewById(R.id.payment_group_image);
//
//
//                convertView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //TODO need to fix this up not good
//                        ExpandableListView listView = (ExpandableListView) v.getParent();
//                        if (listView.isGroupExpanded(groupPosition)) {
//                            listView.collapseGroup(groupPosition);
//                            holder.groupImageView.setBackgroundResource(R.drawable.collapse);
//                        } else {
//                            listView.expandGroup(groupPosition);
//                            holder.groupImageView.setBackgroundResource(R.drawable.expand);
//                        }
//                    }
//                });
//                convertView.setTag(holder);
//            } else {
//                holder = (GroupViewHolder) convertView.getTag();
//                convertView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //TODO need to fix this up not good
//                        ExpandableListView listView = (ExpandableListView) v.getParent();
//
//                        if (listView.isGroupExpanded(groupPosition)) {
//                            listView.collapseGroup(groupPosition);
//                            holder.groupImageView.setBackgroundResource(R.drawable.expand);
//                        } else {
//                            listView.expandGroup(groupPosition);
//
//                            holder.groupImageView.setBackgroundResource(R.drawable.collapse);
//                        }
//                    }
//                });
//            }
//
//            PaymnentGroup group = (PaymnentGroup) getGroup(groupPosition);
//            holder.groupNametext.setText(group.getNameText());
//            holder.groupPricetext.setText(group.getTotalText());
//
//
//            holder.groupCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    View nameHeaderPriceTextView = getNameHeaderPriceView(buttonView);
//                    if (isChecked) {
//                        double iSum = 0;
//                        for (PaymentChild child : childrenList.get(groupPosition)) {
//                            iSum += Double.valueOf(child.getTotalText().substring(1));
//                            itenearySet.add(child.getGuid());
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
//                            itenearySet.remove(child.getGuid());
//                        }
//                    }
//                    notifyDataSetChanged();
//                }
//            });
//
//
//            return convertView;
//        }
//
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return true;
//        }
//
//        private class GroupViewHolder {
//            FontTextView groupNametext;
//            FontTextView groupPricetext;
//            CheckBox groupCheckBox;
//            ImageView groupImageView;
//
//        }
//
//        private class ChildViewHolder {
//
//            FontTextView childNametext;
//            FontTextView childPricetext;
//            CheckBox childCheckBox;
//        }
//
//
//        private void setGroupPrice(boolean add, int groupPosition, FontTextView childPricetext) {
//            PaymnentGroup paymnentGroup = groupsList.get(groupPosition);
//            String string = childPricetext.getText().toString().substring(1);
//
//            if (add) {
//                double d = Double.valueOf(paymnentGroup.getTotalText().substring(1)) + Double.valueOf(string);
//                groupsList.get(groupPosition).setTotalText("$" + String.valueOf(d));
//            } else {
//                double d = Double.valueOf(paymnentGroup.getTotalText().substring(1)) - Double.valueOf(string);
//                groupsList.get(groupPosition).setTotalText("$" + String.valueOf(d));
//            }
//
//        }
//
//        private View getNameHeaderPriceView(CompoundButton buttonView) {
//            ViewGroup viewParent = (ViewGroup) buttonView.getParent();
//            ViewParent vrr = viewParent.getParent();
//            ViewGroup vrr1 = (ViewGroup) vrr.getParent();
//            View v4 = vrr1.getChildAt(0);
//            return v4;
//        }
//
//        private void setHeaderPrice(boolean add, View nameHeaderPriceTextView, String value) {
//            FontTextView priceFontText = (FontTextView) nameHeaderPriceTextView.findViewById(R.id.checkout_name_price);
//            String string = priceFontText.getText().toString().substring(1);
//            if (value.contains("$")) {
//                value = value.substring(1);
//            }
//            if (add) {
//                double dTotal = Double.valueOf(string) + Double.valueOf(value);
//                priceFontText.setText("$" + String.format("%.2f", dTotal));
//            } else {
//                double dTotal = Double.valueOf(string) - Double.valueOf(value);
//                priceFontText.setText("$" + String.format("%.2f", dTotal));
//            }
//
//        }
//    }
//
//    private void setTotalPrice(boolean b, String s) {
//        String strTotal = mTotalPrice.getText().toString().substring(1);
//        if (s.contains("$")) {
//            s = s.substring(1);
//        }
//        if (b) {
//            double d = Double.valueOf(strTotal) + Double.valueOf(s);
//            mTotalPrice.setText("$" + String.format("%.2f", d));
//        } else {
//            double d = Double.valueOf(strTotal) - Double.valueOf(s);
//            mTotalPrice.setText("$" + String.format("%.2f", d));
//        }
//    }
//
//}
