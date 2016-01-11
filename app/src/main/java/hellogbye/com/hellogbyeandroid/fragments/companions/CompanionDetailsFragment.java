package hellogbye.com.hellogbyeandroid.fragments.companions;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.MyTripPinnedAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.fragments.MyTripsFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionUserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.PinnedHeaderListView;

/**
 * Created by nyawka on 1/7/16.
 */
public class CompanionDetailsFragment  extends HGBAbtsractFragment {

    public static Fragment newInstance(int position) {
        Fragment fragment = new CompanionDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.companion_detail_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String user_id = "";
        Bundle args = getArguments();
        if (args != null) {
            user_id = args.getString("user_id");
        }

        CompanionVO companionVO = null;
        ArrayList<CompanionVO> companions = getActivityInterface().getCompanions();
        for(CompanionVO companion : companions){
            if(companion.getmCompanionid().equals(user_id)){
                companionVO = companion;
            }
        }

        CompanionUserProfileVO profileData = companionVO.getCampanionUserProfile();

        FontTextView companion_title = (FontTextView) view.findViewById(R.id.companion_title);
        companion_title.setText(profileData.getmTitle());

        FontTextView companion_name_details = (FontTextView) view.findViewById(R.id.companion_name_details);
        companion_name_details.setText(profileData.getmFirstName());


        FontTextView companion_second_name = (FontTextView) view.findViewById(R.id.companion_second_name);
        companion_second_name.setText(profileData.getmLastName());

        FontTextView companion_email_address = (FontTextView) view.findViewById(R.id.companion_email_address);
        companion_email_address.setText(profileData.getmEmailAddress());

        FontTextView companion_details_name = (FontTextView) view.findViewById(R.id.companion_details_name);
        companion_details_name.setText(profileData.getmFirstName() + " " + profileData.getmLastName());



        String addressTitle = "";
        if(profileData.getmCity() != null){
            addressTitle = profileData.getmCity();
        }
        if(profileData.getmCountry() != null){
            addressTitle = "," + profileData.getmCountry();
        }
        if(profileData.getmState() != null){
            addressTitle = ", " + profileData.getmState();
        }
        FontTextView companion_details_city = (FontTextView) view.findViewById(R.id.companion_details_city);
        companion_details_city.setText(addressTitle);


        ImageView companion_details_image = (ImageView) view.findViewById(R.id.companion_details_image);
        HGBUtility.loadRoundedImage(null,profileData.getmAvatar(),companion_details_image);
       // MyTripPinnedAdapter sectionedAdapter = new MyTripPinnedAdapter(mItemsList);

    }

}
