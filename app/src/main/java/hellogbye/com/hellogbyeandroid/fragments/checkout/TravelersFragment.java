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
import hellogbye.com.hellogbyeandroid.adapters.checkout.IExpandableViewSelected;
import hellogbye.com.hellogbyeandroid.adapters.checkout.TravlerExpandableAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymnentGroup;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
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

        setChildrenToView();

 /*       ConnectionManager.getInstance(getActivity()).getTravellersInforWithSolutionId(getActivityInterface().getTravelOrder().getmSolutionID(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });*/

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

    private void setChildrenToView(){
        List<ArrayList<UserProfileVO>> children = new ArrayList<ArrayList<UserProfileVO>>();

        ArrayList<UserProfileVO> userList = getFlowInterface().getListUsers();
      //  getFlowInterface().setListUsers((ArrayList<UserProfileVO>) data);

        if(userList.size() !=0 ){
            for (int i = 0; i < userList.size(); i++) {
                UserProfileVO user = userList.get(i);
                for (int z = 0; z <mGroups.size() ; z++) {

                    String name1 = mGroups.get(z).getNameText();
                    String name2 = user.getFirstname();

                    if(mGroups.get(z).getNameText().equals(user.getFirstname())){
                        ArrayList<UserProfileVO> passengerChildArray = new ArrayList<>();
                        UserProfileVO userInfo = userList.get(z);
                        passengerChildArray.add(userInfo);
                        children.add(passengerChildArray);
                        boolean isMissing = checkIfMissing(userInfo);
                        mGroups.get(z).setmChildDataMissing(isMissing);
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

                }

            }

            mAdapter = new TravlerExpandableAdapter(getActivity().getApplicationContext(), mGroups, children);
            mRecyclerView.setAdapter(mAdapter);
        }else{

            for (int i = 0; i < mGroups.size(); i++) {
                ArrayList<UserProfileVO> passengerChildArray = new ArrayList<>();
                passengerChildArray.add(new UserProfileVO());
                children.add(passengerChildArray);
                mGroups.get(i).setmChildDataMissing(true);
            }

            mAdapter = new TravlerExpandableAdapter(getActivity().getApplicationContext(), mGroups, children);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setGroupViewClickedInterface(new IExpandableViewSelected(){

            @Override
            public boolean groupClicked(int position) {
                if(mRecyclerView.isGroupExpanded(position)) {
                    mRecyclerView.collapseGroup(position);

                    return true;
                }else{
                    mRecyclerView.expandGroup(position);
                    return false;
                }
            }

            @Override
            public void groupEditClicked(int groupPosition) {
                Bundle args = new Bundle();
                args.putInt(HGBConstants.BUNDLE_USER_JSON_POSITION, groupPosition);
                getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVELERS_DETAILS.getNavNumber(), args);
            }
        });


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


    private boolean checkIfMissing(UserProfileVO child) {
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


    private void initCheckoutSteps() {

        mPaymentTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        mTravlerTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        mReviewTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_565656));
        mPaymentImageView.setBackgroundResource(R.drawable.step_menu_blue_stand);
        mTravlerImageView.setBackgroundResource(R.drawable.step_menu_blue_on);
        mReviewImageView.setBackgroundResource(R.drawable.step_menu_gray);

    }


}
