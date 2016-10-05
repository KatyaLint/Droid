package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static com.google.android.gms.analytics.internal.zzy.m;

/**
 * Created by arisprung on 9/28/16.
 */

public class AlternativeHotelFragment extends android.support.v4.app.Fragment
{
    private OnLinearLayoutClickListener onLinearLayoutClickListner;

    private String mPrice;
    private String mHotelName;
    private String mHotelCode;
    private float mStar;
    private boolean mMyHotel;
    private boolean isSelected;

    private int mPosition;
    private View mView;

    private FontTextView mHotelPriceTextView;
    private FontTextView mHotelNameTextView;
    private FontTextView mSelectTextView;
    private  View mTopView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.alternative_hotel_item, container, false);
        mHotelNameTextView = (FontTextView)view.findViewById(R.id.alt_hotel_name);
        mHotelPriceTextView = (FontTextView)view.findViewById(R.id.alt_hotel_price);
        mSelectTextView = (FontTextView)view.findViewById(R.id.select);
        mTopView = view.findViewById(R.id.top_selected_view);
        mView = view;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPrice = getArguments() != null ? getArguments().getString("hotel_price") : "";
        mPosition  = getArguments() != null ? getArguments().getInt("position") : 0;
        mHotelName = getArguments() != null ? getArguments().getString("hotel_name") : "";
        mStar = getArguments() != null ? getArguments().getFloat("hotel_star") : 5;
        mMyHotel= getArguments() != null ? getArguments().getBoolean("my_hotel") : false;
        isSelected = getArguments() != null ? getArguments().getBoolean("hotel_selected") : false;
        mHotelCode = getArguments() != null ? getArguments().getString("hotel_code") : "";
        if (isSelected) {
            mTopView.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.COLOR_EE3A3C));
            mHotelNameTextView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.COLOR_003D4C));
            mHotelPriceTextView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.COLOR_003D4C));
            setStarRating(true, mView,mStar);
        } else {
            mTopView.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.COLOR_565656));
            mHotelNameTextView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.COLOR_565656));
            mHotelPriceTextView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.COLOR_565656));
            setStarRating(false, mView, mStar);
        }

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLinearLayoutClickListner.onItemClick(view,mPosition);
            }
        });

        mHotelNameTextView.setText(mHotelName);
        mHotelPriceTextView.setText(mPrice);
        if (mMyHotel) {

            mSelectTextView.setText("MY HOTEL");
            mSelectTextView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.COLOR_EE3A3C));
            mSelectTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check, 0, 0, 0);


        } else {
            mSelectTextView.setText("SELECTED");
            mSelectTextView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.COLOR_565656));
            mSelectTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }


    }

    private void setStarRating(boolean isSelected, View view, float star) {

        if ("0.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_half,R.drawable.star_blue_out,
                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_half,R.drawable.gray_star_out,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }


        } else if ("1.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_out,
                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_out,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }


        } else if ("1.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_half,
                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_half,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }


        } else if ("2.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }

        } else if ("2.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_half, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_half, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }

        } else if ("3.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }

        } else if ("3.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_half,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_half,R.drawable.gray_star_out);
            }


        } else if ("4.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_full,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_out);
            }


        } else if ("4.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_full,R.drawable.star_blue_half);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_half);
            }


        } else if ("5.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_full,R.drawable.star_blue_full);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_full);
            }


        }
    }

    private void starHolder(View view, int firstStar, int secondStar, int thirdStar, int fourStar, int fiveStar){
        view.findViewById(R.id.star1).setBackgroundResource(firstStar);
        view.findViewById(R.id.star2).setBackgroundResource(secondStar);
        view.findViewById(R.id.star3).setBackgroundResource(thirdStar);
        view.findViewById(R.id.star4).setBackgroundResource(fourStar);
        view.findViewById(R.id.star5).setBackgroundResource(fiveStar);
    }

    public String getmHotelCode() {
        return mHotelCode;
    }

    public interface OnLinearLayoutClickListener {
        void onItemClick(View view , int position);
    }



    public void SetOnItemClickListener(final OnLinearLayoutClickListener mItemClickListener) {
        onLinearLayoutClickListner = mItemClickListener;
    }
}