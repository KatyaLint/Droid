package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.CNCAdapter;
import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/3/15.
 */
public class CNCFragment extends HGBAbtsractFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CNCItem> mArrayList;
    private CNCAdapter mAdapter;
    private EditText mEditText;
    private ImageView mMicImageView;
    private FontTextView mSendTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cnc_fragment_layout, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cnc_recycler_view);
        mEditText = (EditText) rootView.findViewById(R.id.cnc_edit_text);
        mMicImageView = (ImageView) rootView.findViewById(R.id.cnc_mic);
        mSendTextView = (FontTextView)rootView.findViewById(R.id.cnc_send);

        mSendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMessage = mEditText.getText().toString();
                handleMessage(strMessage);
            }
        });


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    setEditorState(true);
                } else {
                    setEditorState(false);
                }
            }
        });

        mMicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityInterface().openVoiceToTextControl();
            }
        });


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArrayList = new ArrayList<>();
        mArrayList.add(new CNCItem("Yo", CNCAdapter.ME_ITEM));
        mArrayList.add(new CNCItem("How are you?", CNCAdapter.HGB_ITEM));
        mAdapter = new CNCAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void handleMessage(String strMessage) {

        mArrayList.add(new CNCItem(strMessage.trim(),CNCAdapter.ME_ITEM));
        mAdapter.notifyDataSetChanged();

    }

    private void setEditorState(boolean b) {

        if (b) {

            mMicImageView.setVisibility(View.GONE);
            mSendTextView.setVisibility(View.VISIBLE);

        } else {
            mMicImageView.setVisibility(View.VISIBLE);
            mSendTextView.setVisibility(View.GONE);
        }
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new CNCFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }
}
