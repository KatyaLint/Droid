package hellogbye.com.hellogbyeandroid.fragments.alternative;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.vo.flights.FairclassPreferencesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/27/16.
 */

public class FareClassFragment extends HGBAbstractFragment {

    public static Fragment newInstance(int position) {
        Fragment fragment = new FareClassFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.flight_details_fare_class_details, container, false);

        String strValue = getArguments().getString("alternative_rooms", "");
        Type fareClassType = new TypeToken<FairclassPreferencesVO>() {
        }.getType();

        Gson gson = new Gson();
        FairclassPreferencesVO fareClass = gson.fromJson(strValue, fareClassType);


        ImageView flight_details_fareclass_image = (ImageView)rootView.findViewById(R.id.flight_details_fareclass_image);
        if(fareClass.getFareclass().equals("Economy")){
            flight_details_fareclass_image.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.tourist_class_big));
        }else{
            flight_details_fareclass_image.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.business_class_big));
        }

        FontTextView flight_details_fare_class_type = (FontTextView)rootView.findViewById(R.id.flight_details_fare_class_type);
        flight_details_fare_class_type.setText(fareClass.getFareclass() + " " + fareClass.getFarepreference());

        FontTextView flight_details_fare_class_price = (FontTextView)rootView.findViewById(R.id.flight_details_fare_class_price);
        flight_details_fare_class_price.setText("$"+fareClass.getCost());

        FontTextView flight_details_fare_class_currency = (FontTextView)rootView.findViewById(R.id.flight_details_fare_class_currency);
        flight_details_fare_class_currency.setText(fareClass.getCurrencyType());

        FontTextView flight_details_cancelation_title = (FontTextView)rootView.findViewById(R.id.flight_details_cancelation_title);
        flight_details_cancelation_title.setText(fareClass.getFarepreference());

        return rootView;
    }

}
