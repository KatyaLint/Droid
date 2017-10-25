package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/23/15.
 */
public class CheckoutConfirmationFailedFragment extends HGBAbstractFragment {


    private FontTextView mEmail;
    private FontButtonView mDone;


    public static Fragment newInstance(int position) {
        Fragment fragment = new CheckoutConfirmationFailedFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.checkout_complete_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmail = (FontTextView) view.findViewById(R.id.confirm_email);

        String userEmail =  getActivityInterface().getCurrentUser().getEmailaddress();
    if(userEmail != null || !userEmail.isEmpty()) {
        SpannableString content = new SpannableString(userEmail);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        mEmail.setText(content);
    }

        mDone = (FontButtonView) view.findViewById(R.id.confirm_done);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlowInterface().goToFragment(ToolBarNavEnum.CNC.getNavNumber(), null);
            }
        });

    }
}
