package hellogbye.com.hellogbyeandroid.fragments.companions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionStaticRelationshipTypesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionUserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by amirlubashevsky on 31/07/2017.
 */

public class CompanionAddNewCompanion  extends HGBAbstractFragment {
    private FontTextView companion_gender_popup;
    private FontEditTextView companion_first_name;
    private FontEditTextView companion_last_name;
    private FontTextView companion_add_new_date_of_birth;
    private FontTextView companion_add_new_relationship;
    private FontTextView companion_add_new_companion_btn;
    private FontCheckedTextView companion_add_new_over_18;
    private FontEditTextView companion_email;
    private ArrayList<CompanionStaticRelationshipTypesVO> componentsDescription;

    public static Fragment newInstance(int position) {
        Fragment fragment = new CompanionAddNewCompanion();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);

        return fragment;
    }

    private void initialization(View view){

        companion_gender_popup = (FontTextView)view.findViewById(R.id.companion_gender_popup);

        companion_gender_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HGBUtility.showPikerDialog(0, companion_gender_popup, getActivity(), getActivity().getString(R.string.select_title), getResources().getStringArray(R.array.title_array), 0, 2, null, true);
            }
        });


        companion_first_name = (FontEditTextView)view.findViewById(R.id.companion_first_name);

        companion_last_name = (FontEditTextView)view.findViewById(R.id.companion_last_name);
        companion_add_new_date_of_birth = (FontTextView)view.findViewById(R.id.companion_add_new_date_of_birth);
        companion_add_new_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HGBUtility.showDateDialog(getActivity(), companion_add_new_date_of_birth);
            }
        });
        companion_add_new_relationship = (FontTextView)view.findViewById(R.id.companion_add_new_relationship);

        companion_add_new_companion_btn = (FontTextView)view.findViewById(R.id.companion_add_new_companion_btn);
        companion_add_new_companion_btn.setOnClickListener(sendNewCompanionInfoListener);

        companion_add_new_over_18 = (FontCheckedTextView)view.findViewById(R.id.companion_add_new_over_18);
        companion_add_new_over_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companion_add_new_over_18.isChecked()){
                    companion_add_new_over_18.setChecked(false);
                }else{
                    companion_add_new_over_18.setChecked(true);
                }
            }
        });

        companion_email = (FontEditTextView)view.findViewById(R.id.companion_email);

        companion_add_new_relationship.setOnClickListener(chooseRelashionshipListener);
        componentsDescription = getActivityInterface().getCompanionsStaticRelationshipTypes();
        companion_add_new_relationship.setText( componentsDescription.get(0).getmDescription());
        companion_add_new_relationship.setTag(componentsDescription.get(0).getmId());
    }

    View.OnClickListener chooseRelashionshipListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            relationsheeps();
        }
    };

    private void relationsheeps(){

        String[] titleArray = new String[componentsDescription.size()];
        for (int j = 0; j < componentsDescription.size(); j++) {
            titleArray[j] = componentsDescription.get(j).getmDescription();
        }

        HGBUtility.showPikerDialog(0,companion_add_new_relationship, getActivity(),getResources().getString( R.string.preferences_relationship_choose),
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
                        companion_add_new_relationship.setTag(companionIdRelationship);
                    }

                    @Override
                    public void itemCanceled() {

                    }
                }, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.companion_add_new_companion, container, false);
        initialization(rootView);
        return rootView;
    }



    View.OnClickListener sendNewCompanionInfoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            CompanionVO companionVO = new CompanionVO();
            companionVO.setmAddedvia("Email");
            companionVO.setmIsAdult(companion_add_new_over_18.isChecked());
            companionVO.setRelationshiptypeid(companion_add_new_relationship.getTag().toString());

            CompanionUserProfileVO companionUserProfileVO = new CompanionUserProfileVO();
            companionUserProfileVO.setmDoB(companion_add_new_date_of_birth.getText().toString());
            companionUserProfileVO.setmEmailAddress(companion_email.getText().toString());
            companionUserProfileVO.setmFirstName(companion_first_name.getText().toString());
            companionUserProfileVO.setmLastName(companion_last_name.getText().toString());
            companionUserProfileVO.setmGender(companion_gender_popup.getText().toString());

            companionVO.setCompanionUserProfile(companionUserProfileVO);

            ((MainActivityBottomTabs)getActivity()).postCompanion(companionVO);
        }
    };



}
