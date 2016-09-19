package hellogbye.com.hellogbyeandroid.adapters.userprofilesadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.profiles.DefaultsProfilesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


public class UserProfilesListAdapter extends BaseAdapter {

    //private final Typeface textFont;
    private ArrayList<DefaultsProfilesVO> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public UserProfilesListAdapter(ArrayList<DefaultsProfilesVO> data, Context context) {
        mData = data;
        this.mContext = context;
      //  textFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "dinnextltpro_medium.otf");
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.user_profile_list_layout, null);
        }

        DefaultsProfilesVO item = mData.get(position);
        FontTextView text = (FontTextView) convertView.findViewById(R.id.user_profile_text);
      //  ImageView image = (ImageView) convertView.findViewById(R.id.user_profile_image);

        RadioButton user_profile_check_radio_btn = (RadioButton) convertView.findViewById(R.id.user_profile_check_radio_btn);

    /*    if(selectedPreferebcesID.equals(attribute.getmId())){
            selectedPosition = position;
            selectedPreferebcesID = "";

        }*/

  /*      user_profile_check_radio_btn.setChecked(position == selectedPosition);
        user_profile_check_radio_btn.setTag(position);

        user_profile_check_radio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedPosition = (Integer)view.getTag();
                listRadioButtonClickedClicked.clickedItem(selectedPosition);
                notifyDataSetChanged();

            }
        });
        */
        text.setText(item.getProfilename());

  /*      if(position == 0){
            image.setBackgroundResource(R.drawable.money_saver);
            text.setTypeface(textFont);
        }else if(position == 1){
            image.setBackgroundResource(R.drawable.time_saver);
            text.setTypeface(textFont);
        }
        else{
            image.setBackgroundResource(R.drawable.create_your_own_profile_icon);
            text.setTypeface(textFont);
        }*/

        return convertView;
    }
}