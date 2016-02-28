package hellogbye.com.hellogbyeandroid.fragments.companions;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.StartingMenuActivity;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportResultsVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionUserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionStaticRelationshipTypesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 1/7/16.
 */
public class CompanionDetailsFragment  extends HGBAbtsractFragment {


    private CompanionVO companionVO;

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

        ArrayList<CompanionVO> companions = getActivityInterface().getCompanions();
        for(CompanionVO companion : companions){
            if(companion.getmCompanionid().equals(user_id)){
                companionVO = companion;
                break;
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


        FontTextView companion_added_date = (FontTextView) view.findViewById(R.id.companion_added_date);
        companion_added_date.setText(companionVO.getmAddedatetime());

//       final Button companion_add_relationship = (Button) view.findViewById(R.id.companion_add_relationship);
//        companion_add_relationship.setText(companionVO.getRelationshiptype());


        final FontTextView companion_add_relationship = (FontTextView) view.findViewById(R.id.companion_add_relationship);
        companion_add_relationship.setText(companionVO.getRelationshiptype());


        LayoutInflater li = LayoutInflater.from(getActivity());
       final View promptsView = li.inflate(R.layout.popup_layout_with_edit_text_new, null);



        FontTextView text = (FontTextView) promptsView
                .findViewById(R.id.component_popup_text);



        companion_add_relationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<CompanionStaticRelationshipTypesVO> componentsDescription = getActivityInterface().getCompanionsStaticRelationshipTypes();
                if (componentsDescription == null) {
                    getRelationshipTypes();
                } else {


                    String[] titleArray = new String[componentsDescription.size()];
                    for (int j = 0; j < componentsDescription.size(); j++) {
                        titleArray[j] = componentsDescription.get(j).getmDescription();
                    }

                    HGBUtility.showPikerDialog(companion_add_relationship, getActivity(), "Choose url",
                            titleArray, 0, componentsDescription.size() - 1, new PopUpAlertStringCB() {

                                @Override
                                public void itemSelected(String inputItem) {
                                    int companionIdRelationship = 0;
                                    for (CompanionStaticRelationshipTypesVO componentsDescriptionVO:componentsDescription) {
                                        if(componentsDescriptionVO.getmDescription().equals(inputItem)){
                                            companionIdRelationship = componentsDescriptionVO.getmId();
                                            break;
                                        }

                                    }
                                    setNewRelationshipForCompanion(companionIdRelationship);
                                }

                                @Override
                                public void itemCanceled() {

                                }
                            }, true);
                }
            }});





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
        HGBUtility.loadRoundedImage(getActivity().getApplicationContext(),profileData.getmAvatar(),companion_details_image);
       // MyTripPinnedAdapter sectionedAdapter = new MyTripPinnedAdapter(mItemsList);

    }


    private void setNewRelationshipForCompanion(int relationshiptypeId){
        String paxID  = companionVO.getmCompanionid();
        ConnectionManager.getInstance(getActivity()).putCompanionRelationship(paxID, relationshiptypeId,new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                System.out.println("Kate onSuccess");
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.show(getFragmentManager(), (String) data);
                System.out.println("Kate onError");
            }
        });
    }
    private void getRelationshipTypes(){
        ConnectionManager.getInstance(getActivity()).getStaticCompanionsRelationTypesVO( new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<CompanionStaticRelationshipTypesVO> companionStaticRelationshipTypesVOs = (ArrayList<CompanionStaticRelationshipTypesVO> )data;
                getActivityInterface().setCompanionsStaticRelationshipTypes(companionStaticRelationshipTypesVOs);
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }

}
