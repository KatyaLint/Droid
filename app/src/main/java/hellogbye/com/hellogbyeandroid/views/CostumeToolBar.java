package hellogbye.com.hellogbyeandroid.views;

import android.content.Context;

import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;

public class CostumeToolBar extends Toolbar {

  //  private ImageButton keyBoardImage;
    private ImageButton up_bar_favorite;
    private ImageButton favoriteButton;
    private FontTextView editPreferense;
    private ImageView homeTitleImage;
    private FontTextView titleText;
    private Toolbar mToolbar;
    private Context mContext;
    private ImageButton edit_preferences;
    private LinearLayout preferences_edit_mode;
    private ImageButton check_preferences;
    //private ImageButton my_trips_button;
   // private FontTextView my_trip_edit_button;
    private ImageView my_trips_image_profile;
    private FontTextView my_trip_profile;
    private FontTextView itirnarary_title_Bar;
    private LinearLayout tool_bar_profile_name;
    private ImageButton toolbar_go_to_iternerary;
    private FontTextView preference_save_changes;

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

        if(my_trip_profile == null){
            my_trip_profile = (FontTextView)findViewById(R.id.my_trip_profile);
        }
        if (titleText == null) {
            titleText = (FontTextView) findViewById(R.id.titleBar);
        }
        if(my_trips_image_profile == null){
            my_trips_image_profile = (ImageView)findViewById(R.id.my_trips_image_profile);
        }
        /*if (keyBoardImage == null) {
            keyBoardImage = (ImageButton) findViewById(R.id.keyboard);
        }*/
        if (up_bar_favorite == null) {
            up_bar_favorite = (ImageButton) findViewById(R.id.up_bar_favorite);

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
        if(preference_save_changes == null) {
            preference_save_changes = (FontTextView)findViewById(R.id.preference_save_changes);
        }
//        if(my_trips_button == null){
//            my_trips_button = (ImageButton)findViewById(R.id.my_trips_button);
//        }
//        if(my_trip_edit_button == null){
//            my_trip_edit_button = (FontTextView) findViewById(R.id.my_trip_edit_button);
//        }
        if(itirnarary_title_Bar == null){
            itirnarary_title_Bar = (FontTextView)findViewById(R.id.itirnarary_title_Bar);
        }if(tool_bar_profile_name == null){
            tool_bar_profile_name = (LinearLayout)findViewById(R.id.tool_bar_profile_name);
        }
        if(toolbar_go_to_iternerary == null){
            toolbar_go_to_iternerary = (ImageButton) findViewById(R.id.toolbar_go_to_iternerary);
        }

    }


    public void updateToolBarView(int position) {

        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);
        String selectedItem = navBar.getNavTitle();
        tool_bar_profile_name.setVisibility(View.GONE);
   //     homeTitleImage.setVisibility(View.GONE);
     //   my_trip_profile.setVisibility(View.GONE);
        titleText.setVisibility(View.GONE);
    //    keyBoardImage.setVisibility(View.GONE);
        up_bar_favorite.setVisibility(View.GONE);
        favoriteButton.setVisibility(View.GONE);
        editPreferense.setVisibility(View.GONE);
        edit_preferences.setVisibility(View.GONE);
        preferences_edit_mode.setVisibility(View.GONE);
        check_preferences.setVisibility(View.GONE);
//        my_trips_button.setVisibility(View.GONE);
       // my_trip_edit_button.setVisibility(View.GONE);
     //   my_trips_image_profile.setVisibility(View.GONE);
        itirnarary_title_Bar.setVisibility(View.GONE);
        toolbar_go_to_iternerary.setVisibility(View.GONE);
        preference_save_changes.setVisibility(View.GONE);

        switch (navBar) {
         //   case HOME:
     /*           tool_bar_profile_name.setVisibility(View.VISIBLE);
                toolbar_go_to_iternerary.setVisibility(View.VISIBLE);*/
//             //   homeTitleImage.setVisibility(View.VISIBLE);
//                my_trip_profile.setVisibility(View.VISIBLE);
//                keyBoardImage.setVisibility(View.VISIBLE);
//
         //       break;
            case CNC:
                tool_bar_profile_name.setVisibility(View.VISIBLE);
                toolbar_go_to_iternerary.setVisibility(View.VISIBLE);
                break;
            case ITINARERY:
               // titleText.setVisibility(View.VISIBLE);
            //    up_bar_favorite.setVisibility(View.VISIBLE);
                up_bar_favorite.setVisibility(View.VISIBLE);
                itirnarary_title_Bar.setVisibility(View.VISIBLE);
            //    itirnarary_title_Bar.setText(selectedItem);
                break;
//            case HISTORY:
//                titleText.setVisibility(View.VISIBLE);
//                keyBoardImage.setVisibility(View.INVISIBLE);
//                break;

            case PREFERENCES_CHECK_LIST_SETTINGS:
            case PREFERENCES_SEARCH_LIST_SETTINGS:
            case PREFERENCES_SPECIFIC_LIST_SETTINGS:
            case PREFERENCES_DRAG_LIST_SETTINGS:
            case PREFERENCE_SETTINGS_EMAILS:
            case PREFERENCES_CHECK_AS_RADIO_SETTINGS:
                preference_save_changes.setVisibility(View.VISIBLE);
                titleText.setVisibility(View.VISIBLE);
                edit_preferences.setVisibility(View.GONE);
            break;

            case TRIPS:
            case COMPANIONS:
            case ALL_COMPANIONS_VIEW:
            case COMPANIONS_PERSONAL_DETAILS:
            case HELP:
            case ACCOUNT:
            case PREFERENCES_TAB_SETTINGS:
            case ALTERNATIVE_FLIGHT:
            case HOTEL:
            case COMPANION_HELP_FEEDBACK:
            case ALTERNATIVE_FLIGHT_DETAILS:
            case ADD_CREDIT_CARD:
            case PAYMENT_DETAILS:
            case HAZARDOUS_NOTICE:
            case SELECT_CREDIT_CARD:
            case PAYMENT_TRAVELERS:
            case CREDIT_CARD_LIST:
                titleText.setVisibility(View.VISIBLE);
                break;

            case PREFERENCE:
            //    preference_save_changes.setVisibility(View.VISIBLE);
                titleText.setVisibility(View.VISIBLE);
                preferences_edit_mode.setVisibility(View.VISIBLE);
                edit_preferences.setVisibility(View.VISIBLE);
                break;

        }

        titleText.setText(selectedItem);
    }


    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public void setmToolbar(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
    }
}

