package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/23/15.
 */
public class HazardousNoticeFragment extends HGBAbstractFragment {


    private FontTextView mHazaardText;

    private FontButtonView mDone;


    public static Fragment newInstance(int position) {
        Fragment fragment = new HazardousNoticeFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hazard_notice_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHazaardText = (FontTextView) view.findViewById(R.id.hazard_text);
        mDone = (FontButtonView) view.findViewById(R.id.confirm_read);
        mHazaardText.setMovementMethod(new ScrollingMovementMethod());
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putBoolean("i_accept", true);
                getFlowInterface().goToFragment(ToolBarNavEnum.SELECT_CREDIT_CARD.getNavNumber(), args);
            }
        });

    }




}
