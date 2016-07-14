package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymnentGroup;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/23/15.
 */
public class TravelersFragment extends HGBAbstractFragment {


    private FontButtonView mNext;
   // private FontTextView mNextDisable;

    private ArrayList<PaymnentGroup> mGroups;
    private TravlerExpandableAdapter mAdapter;

    private ExpandableListView mRecyclerView;

    private FontTextView mPaymentTextView;
    private FontTextView mTravlerTextView;
    private FontTextView mReviewTextView;

    private ImageView mPaymentImageView;
    private ImageView mTravlerImageView;
    private ImageView mReviewImageView;

    public static Fragment newInstance(int position) {
        Fragment fragment = new TravelersFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.payment_travlers_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            String groups = args.getString(HGBConstants.BUNDLE_PARENT_LIST);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<PaymnentGroup>>() {
            }.getType();
            mGroups = gson.fromJson((String) groups, listType);
        }
        mNext = (FontButtonView) view.findViewById(R.id.traveler_next);
        mNext.setEnabled(false);
      //  mNextDisable = (FontTextView) view.findViewById(R.id.traveler_next_disable);

        mRecyclerView = (ExpandableListView) view.findViewById(R.id.traveler_recyclerView);
        mPaymentTextView = (FontTextView) view.findViewById(R.id.steps_checkout_payment_text);
        mTravlerTextView = (FontTextView) view.findViewById(R.id.steps_checkout_travler_text);
        mReviewTextView = (FontTextView) view.findViewById(R.id.steps_checkout_review_text);

        mPaymentImageView = (ImageView) view.findViewById(R.id.steps_checkout_payment_image);
        mTravlerImageView = (ImageView) view.findViewById(R.id.steps_checkout_travler_image);
        mReviewImageView = (ImageView) view.findViewById(R.id.steps_checkout_review_image);

//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);


        ConnectionManager.getInstance(getActivity()).getTravellersInforWithSolutionId(getActivityInterface().getTravelOrder().getmSolutionID(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<ArrayList<UserDataVO>> children = new ArrayList<ArrayList<UserDataVO>>();

                getFlowInterface().setListUsers((ArrayList<UserDataVO>) data);

                if(getFlowInterface().getListUsers().size() !=0 ){
                    for (int i = 0; i < getFlowInterface().getListUsers().size(); i++) {
                        ArrayList<UserDataVO> passengerChildArray = new ArrayList<>();
                        passengerChildArray.add(getFlowInterface().getListUsers().get(i));
                        children.add(passengerChildArray);
                        boolean isMissing = checkIfMissing(getFlowInterface().getListUsers().get(i));
                        mGroups.get(i).setmChildDataMissing(isMissing);
                        if(isMissing){

                            mNext.setEnabled(false);
                    /*        mNext.setVisibility(View.GONE);
                            mNextDisable.setVisibility(View.VISIBLE);*/
                        }else{

                            mNext.setEnabled(true);
                         /*   mNext.setVisibility(View.VISIBLE);
                            mNextDisable.setVisibility(View.GONE);*/
                        }
                    }

                    mAdapter = new TravlerExpandableAdapter(getActivity().getApplicationContext(), mGroups, children);
                    mRecyclerView.setAdapter(mAdapter);
                }else{

                    for (int i = 0; i < mGroups.size(); i++) {
                        ArrayList<UserDataVO> passengerChildArray = new ArrayList<>();
                        passengerChildArray.add(new UserDataVO());
                        children.add(passengerChildArray);
                        mGroups.get(i).setmChildDataMissing(true);
                    }


                    mAdapter = new TravlerExpandableAdapter(getActivity().getApplicationContext(), mGroups, children);
                    mRecyclerView.setAdapter(mAdapter);
                }


//                mAdapter.SetOnItemClickListener(new TravlerAdapter.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(View v, int position) {
//                        Bundle args = new Bundle();
//                        args.putInt("user_json_position", position);
//                        getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVLERS_DETAILS.getNavNumber(), args);
//                    }
//                });


            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectionManager.getInstance(getActivity()).getCreditCards(new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        getActivityInterface().setCreditCards((ArrayList<CreditCardItem>) data);
                        getFlowInterface().goToFragment(ToolBarNavEnum.SELECT_CREDIT_CARD.getNavNumber(), null);

                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });


            }
        });

        initCheckoutSteps();
    }

    private boolean checkIfMissing(UserDataVO child) {
        if (child.getFirstname() == null || child.getFirstname().equals("")) {
            return true;
        } else if (child.getPostalcode() == null || child.getPostalcode().equals("")) {
            return true;
        }
        if (child.getEmailaddress() == null || child.getEmailaddress().equals("")) {
            return true;
        }
        if (child.getPhone() == null || child.getPhone().equals("")) {
            return true;
        }
        if (child.getDob() == null || child.getDob().equals("")) {
            return true;
        }
        return false;


    }

    private void setNextButtonBackround(boolean isEnabled) {
        mNext.setEnabled(isEnabled);
        if (isEnabled) {
            mNext.setBackgroundResource(R.drawable.red_button);

        } else {
            mNext.setBackgroundResource(R.drawable.red_disable_button);
        }
    }

    public class TravlerExpandableAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private final ArrayList<PaymnentGroup> groupsList;
        private List<ArrayList<UserDataVO>> childrenList = new ArrayList<>();


        public TravlerExpandableAdapter(Context context, ArrayList<PaymnentGroup> groups, List<ArrayList<UserDataVO>> children) {
            this.groupsList = groups;
            this.childrenList = children;
            this.inf = LayoutInflater.from(context);

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
                convertView = inf.inflate(R.layout.payment_child_travler_item, parent, false);
                holder = new ChildViewHolder();

                holder.childNametext = (FontTextView) convertView.findViewById(R.id.travler_name_entry);
                holder.childDOB = (FontTextView) convertView.findViewById(R.id.travler_dob_entry);
                holder.childPhone = (FontTextView) convertView.findViewById(R.id.travler_phone_entry);
                holder.childAddress = (FontTextView) convertView.findViewById(R.id.travler_address_entry);
                holder.childEmail = (FontTextView) convertView.findViewById(R.id.travler_email_entry);

                holder.childNametextLabel = (FontTextView) convertView.findViewById(R.id.travler_name);
                holder.childDOBLabel = (FontTextView) convertView.findViewById(R.id.travler_dob);
                holder.childPhoneLabel = (FontTextView) convertView.findViewById(R.id.travler_phone);
                holder.childAddressLabel = (FontTextView) convertView.findViewById(R.id.travler_address);
                holder.childEmailLabel = (FontTextView) convertView.findViewById(R.id.travler_email);

                convertView.setTag(holder);
            } else {
                holder = (ChildViewHolder) convertView.getTag();
            }
            final UserDataVO child = (UserDataVO) getChild(groupPosition, childPosition);


            holder.childNametext.setText(child.getFirstname() + " " + child.getLastname());
            holder.childDOB.setText(child.getDob());
            holder.childPhone.setText(child.getPhone());
            holder.childAddress.setText(child.getAddress() + "\n" + child.getCity() + "," + child.getState() + "\n" + child.getPostalcode());
            holder.childEmail.setText(child.getEmailaddress());
            if(child.getFirstname() == null){
                holder.childNametext.setText("");
            }
            if(child.getAddress()  == null){
                holder.childAddress.setText("");
            }

            findMissingElemnts(holder, child);
            return convertView;
        }

        private void findMissingElemnts(ChildViewHolder holder, UserDataVO child) {

            if (child.getFirstname() == null || child.getFirstname().equals("")) {
                holder.childNametextLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.red_button_color));

            }
            if (child.getPostalcode() == null || child.getPostalcode().equals("")) {
                holder.childAddressLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.red_button_color));
            }
            if (child.getEmailaddress() == null || child.getEmailaddress().equals("")) {
                holder.childEmailLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.red_button_color));
            }
            if (child.getPhone() == null || child.getPhone().equals("")) {
                holder.childPhoneLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.red_button_color));
            }
            if (child.getDob() == null || child.getDob().equals("")) {
                holder.childDOBLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.red_button_color));
            }

        }


        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final GroupViewHolder holder;

            if (convertView == null) {
                convertView = inf.inflate(R.layout.payment_group_travlers_item, parent, false);

                holder = new GroupViewHolder();
                holder.groupNametext = (FontTextView) convertView.findViewById(R.id.payment_group_name);
                holder.groupEdittext = (FontTextView) convertView.findViewById(R.id.payment_group_edit);
                holder.groupMissing = (FontTextView) convertView.findViewById(R.id.payment_group_missing);


                holder.groupPricetext = (FontTextView) convertView.findViewById(R.id.payment_group_price);
                // holder.groupSelectCC = (FontTextView) convertView.findViewById(R.id.passenger_select_cc);
                // holder.groupCheckBox = (CheckBox) convertView.findViewById(R.id.payment_group_checkbox);
                holder.groupImageView = (ImageView) convertView.findViewById(R.id.payment_group_image);

                convertView.setTag(holder);
            } else {
                holder = (GroupViewHolder) convertView.getTag();

            }

            final PaymnentGroup group = (PaymnentGroup) getGroup(groupPosition);
            holder.groupNametext.setText(group.getNameText());
            holder.groupPricetext.setText(group.getTotalText());


            if (group.isSelected()) {
                holder.groupImageView.setBackgroundResource(R.drawable.expand_copy_3);
            } else {
                holder.groupImageView.setBackgroundResource(R.drawable.minimize);
            }
            holder.groupImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerView.isGroupExpanded(groupPosition)) {
                        mRecyclerView.collapseGroup(groupPosition);
                        v.setBackgroundResource(R.drawable.expand_copy_3);
                        group.setSelected(true);
                    } else {
                        mRecyclerView.expandGroup(groupPosition);
                        group.setSelected(false);
                        v.setBackgroundResource(R.drawable.minimize);
                    }
                }
            });
            holder.groupEdittext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt(HGBConstants.BUNDLE_USER_JSON_POSITION, groupPosition);
                    getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVELERS_DETAILS.getNavNumber(), args);
                }
            });

            if(group.ismChildDataMissing()){
                holder.groupMissing.setVisibility(View.VISIBLE);
            }else{
                holder.groupMissing.setVisibility(View.GONE);
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
            FontTextView groupEdittext;
            FontTextView groupMissing;
            //CheckBox groupCheckBox;
            ImageView groupImageView;


        }

        private class ChildViewHolder {


            FontTextView childNametextLabel;
            FontTextView childDOBLabel;
            FontTextView childEmailLabel;
            FontTextView childPhoneLabel;
            FontTextView childAddressLabel;

            FontTextView childNametext;
            FontTextView childDOB;
            FontTextView childEmail;
            FontTextView childPhone;
            FontTextView childAddress;

        }


    }


    private void initCheckoutSteps() {

        mPaymentTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_063345));
        mTravlerTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_063345));
        mReviewTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_777776));
        mPaymentImageView.setBackgroundResource(R.drawable.step_menu_blue_stand);
        mTravlerImageView.setBackgroundResource(R.drawable.step_menu_blue_on);
        mReviewImageView.setBackgroundResource(R.drawable.step_menu_gray);

    }


}
