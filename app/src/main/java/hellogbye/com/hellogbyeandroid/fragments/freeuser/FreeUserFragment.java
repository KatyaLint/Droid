package hellogbye.com.hellogbyeandroid.fragments.freeuser;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.LoginActivity;
import hellogbye.com.hellogbyeandroid.activities.SignUpActivity;

import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;

import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 4/14/16.
 */
public class FreeUserFragment extends HGBAbstractFragment {


    private FontTextView free_user_free;
    private FontTextView free_user_already_a_member;
    private FontTextView free_user_premium;

    public static Fragment newInstance(int position) {
        Fragment fragment = new FreeUserFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.free_user_layout, container, false);

        free_user_free = (FontTextView)rootView.findViewById(R.id.free_user_free);
        free_user_free.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            //    getFlowInterface().goToFragment(ToolBarNavEnum.HOTEL.getNavNumber(),null);
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        free_user_already_a_member = (FontTextView)rootView.findViewById(R.id.free_user_already_a_member);
        free_user_already_a_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        free_user_premium = (FontTextView)rootView.findViewById(R.id.free_user_premium);
        free_user_premium.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
