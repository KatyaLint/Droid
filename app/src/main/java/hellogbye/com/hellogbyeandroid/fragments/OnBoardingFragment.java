package hellogbye.com.hellogbyeandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.TravelPreference;
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


        final String strTravelPref = getArguments().getString("travel_prefrence");
        Gson gson = new Gson();
        TravelPreference pref = gson.fromJson(strTravelPref, TravelPreference.class);

        mTitleTextView.setText(pref.getName());
        mDescTextView.setText(pref.getDescription());
//        mImage.setBackgroundResource(image);//TODO need to set image once we get it form server!!

        return rootView;
    }
}