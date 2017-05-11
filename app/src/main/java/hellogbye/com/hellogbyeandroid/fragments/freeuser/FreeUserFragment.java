/*
package hellogbye.com.hellogbyeandroid.fragments.freeuser;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.CreateAccountActivity;
import hellogbye.com.hellogbyeandroid.activities.SignUpActivity;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;

*/
/**
 * Created by nyawka on 4/14/16.
 *//*

public class FreeUserFragment extends HGBAbstractFragment {
    private FontButtonView free_user_sign_in_btn;
    private FontButtonView free_user_create_new_account_btn;

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

        free_user_sign_in_btn = (FontButtonView)rootView.findViewById(R.id.free_user_sign_in_btn);
        free_user_sign_in_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(getActivity(), CreateAccountActivity.class);
                intent.putExtra("free_user_sign_in",true);
                startActivity(intent);

            //    getFlowInterface().goToFragment(ToolBarNavEnum.HOTEL.getNavNumber(),null);

            }
        });
        free_user_create_new_account_btn = (FontButtonView)rootView.findViewById(R.id.free_user_create_new_account_btn);
        free_user_create_new_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateAccountActivity.class);
                intent.putExtra("free_user_create_user",true);
                startActivity(intent);
            }
        });

        return rootView;
    }




}




*/
