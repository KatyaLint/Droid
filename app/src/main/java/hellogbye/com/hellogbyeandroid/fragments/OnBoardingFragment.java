package hellogbye.com.hellogbyeandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 9/9/15.
 */
public class OnBoardingFragment extends Fragment {

    private FontTextView mTitleTextView;
    private FontTextView mDescTextView;
    private ImageView mImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.onboarding_fragment_layout, container, false);
        mTitleTextView = (FontTextView)rootView.findViewById(R.id.type_travler);
        mDescTextView = (FontTextView)rootView.findViewById(R.id.type_travler_desc);
        mImage = (ImageView)rootView.findViewById(R.id.travel_icon);

        String strTitle = "";
        String strDesc = "";
        int image = 0;

        final int position = getArguments().getInt("position");
        switch (position) {
            case 0:
                strDesc = "Efficiency is your middle name, and if it isnt it should be. You have no prefrence on your stay. Its all about the journey there.";
                strTitle = "Adventorus Traveler";
                image = R.drawable.adventurous_traveler_icn;
                break;
            case 1:
                strTitle = "Default Traveler";
                strDesc = "Your easy-going and if the deal is reasonable and stress free youll be happy. You arent traveliing on an empty wallet.";
                image = R.drawable.default_traveller_icon;
                break;
            case 2:
                strTitle = "Frequent Traveler";
                strDesc = "You travel so often , airplane seats feel like an old friend. You dont mind paying a little more for an upgrade.";
                image = R.drawable.frequent_traveler_icn;
                break;
            case 3:
                strTitle = "Luxury Traveler";
                strDesc = "Cost isnt an issue wether it comes to inflight wifi or silk sheets. As they say, when in Rome.";
                image = R.drawable.luxury_traveler_icn;
                break;

        }

        mTitleTextView.setText(strTitle);
        mDescTextView.setText(strDesc);
        mImage.setBackgroundResource(image);

        return rootView;
    }
}