package hellogbye.com.hellogbyeandroid.views;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.content.Context;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.application.HGBApplication;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

public class CostumeToolBar extends Toolbar {

    private ImageButton up_bar_favorite;
    private ImageButton toolbar_new_iternerary;
    private ImageButton favoriteButton;
    private ImageButton search_maginfy;
    private FontTextView editPreferense;
    private FontTextView titleBar;
    private Toolbar mToolbar;
    private FontTextView edit_preferences;
    private LinearLayout preferences_edit_mode;
    private ImageButton check_preferences;
    private ImageView my_trips_image_profile;
    private FontTextView my_trip_profile;
    private FontTextView itirnarary_title_Bar;
    //private LinearLayout tool_bar_profile_name;
    private ImageButton toolbar_new_iternerary_cnc;
    private FontTextView preference_save_changes;
    private SearchView search_view_tool_bar;
    private AutoCompleteTextView auto_complete;
    private Activity mActivity;
    private ImageButton toolbar_add_companion;
    private int mSelectedFragment;
    private Context mContext;


    private ImageButton tool_bar_delete_preferences;
    private ImageButton toolbar_profile_popup;

    public CostumeToolBar(Context context) {
        super(context);
        mContext = context;
    }

    public CostumeToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CostumeToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void initToolBarItems() {

        if(my_trip_profile == null){
            my_trip_profile = (FontTextView)findViewById(R.id.my_trip_profile);
        }
        if (titleBar == null) {
            titleBar = (FontTextView) findViewById(R.id.titleBar);
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
            edit_preferences = (FontTextView)findViewById(R.id.edit_preferences);
            check_preferences = (ImageButton)findViewById(R.id.check_preferences);
            editPreferense = (FontTextView) findViewById(R.id.editPreference);
            tool_bar_delete_preferences = (ImageButton)findViewById(R.id.tool_bar_delete_preferences);
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
        }
/*        if(tool_bar_profile_name == null){
            tool_bar_profile_name = (LinearLayout)findViewById(R.id.tool_bar_profile_name);
        }*/
        if(toolbar_new_iternerary_cnc == null){
            toolbar_new_iternerary_cnc = (ImageButton) findViewById(R.id.toolbar_new_iternerary_cnc);
        }

        if(toolbar_new_iternerary == null){
            toolbar_new_iternerary = (ImageButton) findViewById(R.id.toolbar_new_iternerary);
        }
        if(search_maginfy == null){
            search_maginfy = (ImageButton) findViewById(R.id.search_maginfy);
        }
        if(toolbar_add_companion == null){
            toolbar_add_companion = (ImageButton)findViewById(R.id.toolbar_add_companion);
        }
        if(search_view_tool_bar == null){
            search_view_tool_bar = (SearchView) findViewById(R.id.search_view_tool_bar);
        }
        if(auto_complete == null){
            auto_complete = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        }
        if(toolbar_profile_popup == null){
            toolbar_profile_popup = (ImageButton)findViewById(R.id.toolbar_profile_popup);
        }


        initSearchBar();
//        initAutoComplete();


    }

    private void initSearchBar() {

        ImageView searchClose = (ImageView) search_view_tool_bar.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        TextView searchCloseText = (TextView) search_view_tool_bar.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchClose.setImageResource(R.drawable.close_icon_a_1);
//        int id = search_view.getContext()
//                .getResources()
//                .getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView) search_view.findViewById(id);
        searchCloseText.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        searchCloseText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_003D4C));
        search_maginfy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openAutoComplete();
             /*   ToolBarNavEnum navBar = ToolBarNavEnum.getNav(mSelectedFragment);
                switch (navBar) {

                    case TRIPS:
                    case COMPANIONS:
                        openSearchBar();
                        break;
                    case SELECT_HOTEL_FRAGMENT:
                        openAutoComplete();
                        break;
                }*/
            }
        });

        search_view_tool_bar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                closeSearchBar();
                return false;
            }
        });


    }

/*    private void initAutoComplete(){
        if(auto_complete == null){
            auto_complete = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        }
    }*/

    private void closeSearchBar() {
        titleBar.setVisibility(View.VISIBLE);
        toolbar_new_iternerary_cnc.setVisibility(View.VISIBLE);
        search_maginfy.setVisibility(View.VISIBLE);
        search_view_tool_bar.setVisibility(View.GONE);

    }


    private void openSearchBar() {
        titleBar.setVisibility(View.GONE);
        toolbar_new_iternerary_cnc.setVisibility(View.GONE);
        search_maginfy.setVisibility(View.GONE);
        search_view_tool_bar.setVisibility(View.VISIBLE);
        search_view_tool_bar.setIconified(false);

    }

    public void closeAutoComplete() {
        titleBar.setVisibility(View.VISIBLE);
        search_maginfy.setVisibility(View.VISIBLE);
        auto_complete.setVisibility(View.GONE);
        HGBUtility.hideKeyboard(HGBApplication.getInstance(),auto_complete);

    }


    private void openAutoComplete() {
        titleBar.setVisibility(View.GONE);
        search_maginfy.setVisibility(View.GONE);
        auto_complete.setVisibility(View.VISIBLE);
        auto_complete.requestFocus();
        HGBUtility.showKeyboard(HGBApplication.getInstance(),auto_complete);

    }


    public void updateToolBarView(int position) {
        mSelectedFragment = position;
        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);
        String selectedItem = navBar.getNavTitle();
   //     tool_bar_profile_name.setVisibility(View.GONE);
        titleBar.setVisibility(View.GONE);
        up_bar_favorite.setVisibility(View.GONE);
        favoriteButton.setVisibility(View.GONE);
        editPreferense.setVisibility(View.GONE);
        edit_preferences.setVisibility(View.GONE);
        preferences_edit_mode.setVisibility(View.GONE);
        tool_bar_delete_preferences.setVisibility(GONE);
        check_preferences.setVisibility(View.GONE);
        itirnarary_title_Bar.setVisibility(View.GONE);
        toolbar_new_iternerary_cnc.setVisibility(View.GONE);
        preference_save_changes.setVisibility(View.GONE);
        toolbar_new_iternerary.setVisibility(View.GONE);
        search_maginfy.setVisibility(View.GONE);
        search_view_tool_bar.setVisibility(View.GONE);
        auto_complete.setVisibility(View.GONE);
        toolbar_add_companion.setVisibility(View.GONE);
        toolbar_profile_popup.setVisibility(View.GONE);


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
           //     tool_bar_profile_name.setVisibility(View.VISIBLE);
                itirnarary_title_Bar.setVisibility(VISIBLE);
                toolbar_new_iternerary_cnc.setVisibility(View.VISIBLE);
                toolbar_profile_popup.setVisibility(View.VISIBLE);
                break;
            case ITINARERY:
                up_bar_favorite.setVisibility(View.VISIBLE);
                itirnarary_title_Bar.setVisibility(View.VISIBLE);
                break;
            case PREFERENCES_CHECK_LIST_SETTINGS:
            case PREFERENCES_SEARCH_LIST_SETTINGS:
            case PREFERENCES_SPECIFIC_LIST_SETTINGS:
            case PREFERENCES_DRAG_LIST_SETTINGS:
            case PREFERENCE_SETTINGS_EMAILS:
            case PREFERENCES_CHECK_AS_RADIO_SETTINGS:
                preference_save_changes.setVisibility(View.VISIBLE);
                titleBar.setVisibility(View.VISIBLE);
                edit_preferences.setVisibility(View.GONE);
            break;

            case TRIPS:
                titleBar.setVisibility(View.VISIBLE);
                toolbar_new_iternerary.setVisibility(View.VISIBLE);
                search_maginfy.setVisibility(View.VISIBLE);
                break;

            case COMPANIONS:
                toolbar_add_companion.setVisibility(View.VISIBLE);
                search_maginfy.setVisibility(View.VISIBLE);
            case ALL_COMPANIONS_VIEW:
            case COMPANIONS_PERSONAL_DETAILS:
            case HELP:
            case ACCOUNT:
            case PREFERENCES_TAB_SETTINGS:
            case HOTEL:
            case COMPANION_HELP_FEEDBACK:
            case ALTERNATIVE_FLIGHT_DETAILS:
            case ADD_CREDIT_CARD:
            case PAYMENT_DETAILS:
            case HAZARDOUS_NOTICE:
            case SELECT_CREDIT_CARD:
            case PAYMENT_TRAVELERS:
            case CREDIT_CARD_LIST:
            case PAYMENT_TRAVELERS_DETAILS:
            case NOTIFICATIONS:
            case PREFERENCES_MEMBERSHIP:
            case ALTERNATIVE_FLIGHT_ONE_WAY_TRIP:
            case ALTERNATIVE_FLIGHT_ROUND_TRIP:
            case COMPANIONS_DETAILS:
                titleBar.setVisibility(View.VISIBLE);
                break;
            case SELECT_HOTEL_FRAGMENT:
                search_maginfy.setVisibility(View.VISIBLE);
                titleBar.setVisibility(View.VISIBLE);
                break;

            case TRAVEL_PREFERENCE:
                titleBar.setVisibility(View.VISIBLE);
                preferences_edit_mode.setVisibility(View.VISIBLE);
                edit_preferences.setVisibility(View.VISIBLE);
                break;
            case SELECT_ROOM_FRAGMENT:
                titleBar.setVisibility(View.VISIBLE);
                break;

        }

        titleBar.setText(selectedItem);
    }


    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public void setmToolbar(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
    }
}

