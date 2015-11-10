package hellogbye.com.hellogbyeandroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/4/15.
 */
public class SettingsFlightFragment extends HGBAbtsractFragment {


   public SettingsFlightFragment(){

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.settings_flight_tab_fragment,container,false);
//        return view;
//    }


    public void setFlightData(View rootView){
        FontTextView text = (FontTextView)rootView.findViewById(R.id.setting_content_text);
        text.setText("Flight");


    }



}
