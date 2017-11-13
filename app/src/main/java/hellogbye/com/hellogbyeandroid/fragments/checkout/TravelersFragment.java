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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.activities.RefreshComplete;
import hellogbye.com.hellogbyeandroid.adapters.checkout.IExpandableViewSelected;
import hellogbye.com.hellogbyeandroid.adapters.checkout.TravlerExpandableAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymnentGroup;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import pl.droidsonroids.gif.GifImageView;

import static android.media.CamcorderProfile.get;
import static hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum.PAYMENT_DETAILS;

/**
 * Created by arisprung on 11/23/15.
 */
public class TravelersFragment extends HGBAbstractFragment {


    private FontButtonView traveler_next;
   // private FontTextView mNextDisable;

    private ArrayList<PassengersVO> passangers;
    private TravlerExpandableAdapter mAdapter;

    private ExpandableListView mRecyclerView;

    private FontTextView mPaymentTextView;
    private FontTextView mTravlerTextView;
    private FontTextView mReviewTextView;

    private ImageView mPaymentImageView;
    private ImageView mTravlerImageView;
    private ImageView mReviewImageView;
    private GifImageView gif_update_availability;

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
        getFlowInterface().enableFullScreen(false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();

        passangers = travelOrder.getPassengerses();
        traveler_next = (FontButtonView) view.findViewById(R.id.traveler_next);
        traveler_next.setEnabled(false);
      //  mNextDisable = (FontTextView) view.findViewById(R.id.traveler_next_disable);

        mRecyclerView = (ExpandableListView) view.findViewById(R.id.traveler_recyclerView);
        mPaymentTextView = (FontTextView) view.findViewById(R.id.steps_checkout_payment_text);
        mTravlerTextView = (FontTextView) view.findViewById(R.id.steps_checkout_travler_text);
        mReviewTextView = (FontTextView) view.findViewById(R.id.steps_checkout_review_text);

        mPaymentImageView = (ImageView) view.findViewById(R.id.steps_checkout_payment_image);
        mTravlerImageView = (ImageView) view.findViewById(R.id.steps_checkout_travler_image);
        mReviewImageView = (ImageView) view.findViewById(R.id.steps_checkout_review_image);

        gif_update_availability = (GifImageView)view.findViewById(R.id.gif_update_availability);

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

        traveler_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traveler_next.setEnabled(false);
                gif_update_availability.setVisibility(View.VISIBLE);
                ((MainActivityBottomTabs)getActivity()).updateAvailability(new RefreshComplete() {
                    @Override
                    public void onRefreshSuccess(Object data) {
                        traveler_next.setEnabled(true);
                        getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_DETAILS.getNavNumber(), null);
                        gif_update_availability.setVisibility(View.GONE);
                    }

                    @Override
                    public void onRefreshError(Object data) {
                        gif_update_availability.setVisibility(View.GONE);
                        traveler_next.setEnabled(true);
                    }
                });




            }
        });

//        ImageButton newIteneraryImageButton = ((MainActivityBottomTabs) getActivity()).getToolbar_new_iterneraryCnc_Chat_Message();
//        newIteneraryImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), null);
//            }
//        });

        initCheckoutSteps();


    }





    private void setChildrenToView(){
        List<ArrayList<UserProfileVO>> children = new ArrayList<ArrayList<UserProfileVO>>();

        ArrayList<UserProfileVO> userList = getFlowInterface().getListUsers();
        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
        String currency = travelOrder.getmCurrency();
        //  getFlowInterface().setListUsers((ArrayList<UserProfileVO>) data);

        if(userList.size() !=0 ){
            for (int i = 0; i < userList.size(); i++) {
                UserProfileVO user = userList.get(i);
                for (int z = 0; z <passangers.size() ; z++) {
                    if(passangers.get(z).getmPaxguid().equalsIgnoreCase(user.getPaxid())){
                        ArrayList<UserProfileVO> passengerChildArray = new ArrayList<>();
                        UserProfileVO userInfo = userList.get(z);
                        passengerChildArray.add(userInfo);

                        children.add(passengerChildArray);
                        boolean isMissing = checkIfMissing(userInfo);
                        passangers.get(z).setmChildDataMissing(isMissing);
                        passangers.get(z).setmName(userInfo.getFirstname());
                        passangers.get(z).setCurrency(currency);
                        traveler_next.setEnabled(!isMissing);

                        //TODO talk with ARIK getAirlinePointsProgram
//                        ((MainActivityBottomTabs)getActivity()).getAirlinePointsProgram(passangers.get(z).getmPaxguid(),userInfo.getUserprofileid(), new RefreshComplete(){
//
//                            @Override
//                            public void onRefreshSuccess(Object data) {
//                                System.out.println("Kate Talk to Arik");
//                            }
//
//                            @Override
//                            public void onRefreshError(Object data) {
//
//                            }
//                        });


                    }

                }
            }

//            mAdapter = new TravlerExpandableAdapter(getActivity().getApplicationContext(), passangers, children);
//            mRecyclerView.setAdapter(mAdapter);
        }else{

            for (int i = 0; i < passangers.size(); i++) {
                ArrayList<UserProfileVO> passengerChildArray = new ArrayList<>();
                passengerChildArray.add(new UserProfileVO());
                children.add(passengerChildArray);
                passangers.get(i).setmChildDataMissing(true);
            }

//            mAdapter = new TravlerExpandableAdapter(getActivity().getApplicationContext(), passangers, children);
//            mRecyclerView.setAdapter(mAdapter);
        }



        mAdapter = new TravlerExpandableAdapter(getActivity().getApplicationContext(), passangers, children);
        mRecyclerView.setAdapter(mAdapter);

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

              //TODO when changing passanger data should change it in passengers kate
                Bundle args = new Bundle();
                args.putString(HGBConstants.BUNDLE_USER_JSON_POSITION, passangers.get(groupPosition).getmName());
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
        }
//        else if (child.getPostalcode() == null || child.getPostalcode().equals("")) {
//            return true;
//        }
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
        traveler_next.setEnabled(isEnabled);
        if (isEnabled) {
            traveler_next.setBackgroundResource(R.drawable.red_button);

        } else {
            traveler_next.setBackgroundResource(R.drawable.red_disable_button);
        }
    }


    private void initCheckoutSteps() {

        mPaymentTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        mTravlerTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        mReviewTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_565656));
        mPaymentImageView.setBackgroundResource(R.drawable.step_menu_blue_on);
        mTravlerImageView.setBackgroundResource(R.drawable.step_menu_gray);
        mReviewImageView.setBackgroundResource(R.drawable.step_menu_gray);

    }


}
