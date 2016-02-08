package hellogbye.com.hellogbyeandroid.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

public class CostumeToolBar extends Toolbar {

    private ImageButton keyBoardImage;
    private ImageButton purchaseButton;
    private ImageButton favoriteButton;
    private FontTextView editPreferense;
    private ImageView homeTitleImage;
    private FontTextView titleText;
    private Toolbar mToolbar;
    private Context mContext;
    private ImageButton edit_preferences;
    private LinearLayout preferences_edit_mode;
    private ImageButton check_preferences;
    private ImageButton my_trips_button;

    public CostumeToolBar(Context context) {
        super(context);
        mContext = context;
    }

    public CostumeToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    public CostumeToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    public void initToolBarItems() {


        if (homeTitleImage == null) {
            homeTitleImage = (ImageView) findViewById(R.id.home_image);
        }
        if (titleText == null) {
            titleText = (FontTextView) findViewById(R.id.titleBar);
        }
        if (keyBoardImage == null) {
            keyBoardImage = (ImageButton) findViewById(R.id.keyboard);
        }
        if (purchaseButton == null) {
            purchaseButton = (ImageButton) findViewById(R.id.purchaseButton);

        }
        if (favoriteButton == null) {
            favoriteButton = (ImageButton) findViewById(R.id.favority);
        }
        if (editPreferense == null) {
            preferences_edit_mode = (LinearLayout)findViewById(R.id.preferences_edit_mode);
            edit_preferences = (ImageButton)findViewById(R.id.edit_preferences);
            check_preferences = (ImageButton)findViewById(R.id.check_preferences);
            editPreferense = (FontTextView) findViewById(R.id.editPreference);
        }
        if(my_trips_button == null){
            my_trips_button = (ImageButton)findViewById(R.id.my_trips_button);
        }
    }


    public void updateToolBarView(int position) {

        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);

        homeTitleImage.setVisibility(View.GONE);
        titleText.setVisibility(View.GONE);
        keyBoardImage.setVisibility(View.GONE);
        purchaseButton.setVisibility(View.GONE);
        favoriteButton.setVisibility(View.GONE);
        editPreferense.setVisibility(View.GONE);
        edit_preferences.setVisibility(View.GONE);
        preferences_edit_mode.setVisibility(View.GONE);
        check_preferences.setVisibility(View.GONE);
        my_trips_button.setVisibility(View.GONE);

        switch (navBar) {
            case HOME:
                homeTitleImage.setVisibility(View.VISIBLE);
                keyBoardImage.setVisibility(View.VISIBLE);

                break;
            case ITINARERY:
                titleText.setVisibility(View.VISIBLE);
                purchaseButton.setVisibility(View.VISIBLE);
                favoriteButton.setVisibility(View.GONE);
                keyBoardImage.setVisibility(View.GONE);
                break;
            case HISTORY:
                titleText.setVisibility(View.VISIBLE);
                keyBoardImage.setVisibility(View.INVISIBLE);
                break;
            case TRIPS:
                titleText.setVisibility(View.VISIBLE);
                keyBoardImage.setVisibility(View.INVISIBLE);
                my_trips_button.setVisibility(View.VISIBLE);
                break;
            case COMPANIONS:
                titleText.setVisibility(View.VISIBLE);
                keyBoardImage.setVisibility(View.INVISIBLE);
                break;
            case PREFERENCE:
            case PREFERENCES_TAB_SETTINGS:
            case PREFERENCES_SEARCH_LIST_SETTINGS:
            case PREFERENCES_SPECIFIC_LIST_SETTINGS:

                titleText.setVisibility(View.VISIBLE);
         //       editPreferense.setVisibility(View.VISIBLE);
        //        keyBoardImage.setVisibility(View.INVISIBLE);
                check_preferences.setVisibility(View.GONE);
                preferences_edit_mode.setVisibility(View.VISIBLE);
                edit_preferences.setVisibility(View.VISIBLE);
                break;
            case ACCOUNT:
                titleText.setVisibility(View.VISIBLE);
                keyBoardImage.setVisibility(View.INVISIBLE);
                break;
            case HELP:
                titleText.setVisibility(View.VISIBLE);
                keyBoardImage.setVisibility(View.INVISIBLE);
                break;
            case CNC:
                titleText.setVisibility(View.INVISIBLE);
                homeTitleImage.setVisibility(View.VISIBLE);
                keyBoardImage.setVisibility(View.VISIBLE);
                break;
            case COMPANIONS_PERSONAL_DETAILS:
                titleText.setVisibility(View.VISIBLE);
                break;


        }
        String selectedItem = navBar.getNavTitle();
        //setTitle(selectedItem);
        titleText.setText(selectedItem);

//        keyBoardImage.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String id = (String) v.getTag();
//
//                if (id.equals("keyboard")) {
//                    Intent intent2 = new Intent(HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION);
//                    intent2.putExtra(HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION, HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION_KEYBOARD_ACTION);
//                    mContext.sendBroadcast(intent2);
//                    keyBoardImage.setBackgroundResource(R.drawable.app_bar_microphone_icn);
//                    keyBoardImage.setTag("mic");
//                } else if (id.equals("mic")) {
//                    Intent intent1 = new Intent(HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION);
//                    intent1.putExtra(HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION, HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION_MIC_ACTION);
//                    mContext.sendBroadcast(intent1);
//                    keyBoardImage.setBackgroundResource(R.drawable.keyboard_icon);
//                    keyBoardImage.setTag("keyboard");
//
//                }
//
//
//
//            }
//        });

    }


    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public void setmToolbar(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
    }
}

