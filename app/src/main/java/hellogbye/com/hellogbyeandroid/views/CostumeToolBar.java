package hellogbye.com.hellogbyeandroid.views;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.searchAdapter.SearchCustomAdapter;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;

public class CostumeToolBar extends Toolbar {

    private ImageButton up_bar_favorite;
    private ImageButton new_itinerary;
    private ImageButton favoriteButton;
    private ImageButton search_maginfy;
    private FontTextView editPreferense;
    private FontTextView titleText;
    private Toolbar mToolbar;
    private ImageButton edit_preferences;
    private LinearLayout preferences_edit_mode;
    private ImageButton check_preferences;
    private ImageView my_trips_image_profile;
    private FontTextView my_trip_profile;
    private FontTextView itirnarary_title_Bar;
    private LinearLayout tool_bar_profile_name;
    private ImageButton toolbar_new_iternerary_cnc;
    private FontTextView preference_save_changes;
    private SearchView search_view;
    private ImageButton toolbar_add_companion;

    private ArrayList<String> mResultString;
    private AutoCompleteTextView customerAutoComplete;
    private SearchCustomAdapter customerAdapter;
    private LinearLayout search_view_ll;

    public CostumeToolBar(Context context) {
        super(context);
    }

    public CostumeToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CostumeToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void initToolBarItems() {

        if (search_view_ll == null) {
            search_view_ll = (LinearLayout) findViewById(R.id.search_layout);
        }

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
        if(toolbar_new_iternerary_cnc == null){
            toolbar_new_iternerary_cnc = (ImageButton) findViewById(R.id.toolbar_new_iternerary_cnc);
        }

        if(new_itinerary == null){
            new_itinerary = (ImageButton) findViewById(R.id.toolbar_new_iternerary);
        }
        if(search_maginfy == null){
            search_maginfy = (ImageButton) findViewById(R.id.search_maginfy);
        }
        if(toolbar_add_companion == null){
            toolbar_add_companion = (ImageButton)findViewById(R.id.toolbar_add_companion);
        }

        initSearchBar();


    }

    private void initSearchBar() {
      /*  if(search_view == null){
            search_view = (SearchView) findViewById(R.id.search);
        }*/


        mResultString = new ArrayList<>();


        ImageView searchClose = (ImageView) findViewById(R.id.search_close);

        AutoCompleteTextView searchCloseText = (AutoCompleteTextView) findViewById(R.id.search);


      //  TextView searchCloseText = (TextView) search_view.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchCloseText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent("search_query");
                intent.putExtra("query_type", "submit");
//                intent.putExtra("query", query);
//                sendBroadcast(intent);//TODO send result to whoever is listning
            }
        });


      //  searchClose.setImageResource(R.drawable.close_icon_a_1);
//        int id = search_view.getContext()
//                .getResources()
//                .getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView) search_view.findViewById(id);

        search_maginfy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchBar();
            }
        });

        searchClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSearchBar();
            }
        });


        customerAutoComplete = (AutoCompleteTextView) findViewById(R.id.search);
        customerAdapter = new SearchCustomAdapter(getContext(), R.layout.search_autocomplete_result, mResultString);
        customerAutoComplete.setAdapter(customerAdapter);
        customerAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //TODO refresh adapter after getting results from server

              /*  mResultString.add(charSequence.toString());
                customerAdapter.updateListData(mResultString);
                System.out.println("Kate onTextChanged charSequence =" + charSequence);*/
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }


    private void closeSearchBar() {
        titleText.setVisibility(View.VISIBLE);
        new_itinerary.setVisibility(View.VISIBLE);
        search_maginfy.setVisibility(View.VISIBLE);
        search_view_ll.setVisibility(View.GONE);

    }


    private void openSearchBar() {
        titleText.setVisibility(View.GONE);
        new_itinerary.setVisibility(View.GONE);
        search_maginfy.setVisibility(View.GONE);
        search_view_ll.setVisibility(View.VISIBLE);
    }


    public void updateToolBarView(int position) {

        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);
        String selectedItem = navBar.getNavTitle();
        tool_bar_profile_name.setVisibility(View.GONE);
        titleText.setVisibility(View.GONE);
        up_bar_favorite.setVisibility(View.GONE);
        favoriteButton.setVisibility(View.GONE);
        editPreferense.setVisibility(View.GONE);
        edit_preferences.setVisibility(View.GONE);
        preferences_edit_mode.setVisibility(View.GONE);
        check_preferences.setVisibility(View.GONE);
        itirnarary_title_Bar.setVisibility(View.GONE);
        toolbar_new_iternerary_cnc.setVisibility(View.GONE);
        preference_save_changes.setVisibility(View.GONE);
        new_itinerary.setVisibility(View.GONE);
        search_maginfy.setVisibility(View.GONE);
        search_view_ll.setVisibility(View.GONE);
        toolbar_add_companion.setVisibility(View.GONE);

        switch (navBar) {
            case CNC:
                tool_bar_profile_name.setVisibility(View.VISIBLE);
                toolbar_new_iternerary_cnc.setVisibility(View.VISIBLE);
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
                titleText.setVisibility(View.VISIBLE);
                edit_preferences.setVisibility(View.GONE);
            break;

            case TRIPS:
                titleText.setVisibility(View.VISIBLE);
                new_itinerary.setVisibility(View.VISIBLE);
                search_maginfy.setVisibility(View.VISIBLE);
                break;

/*            case SELECT_HOTEL_FRAGMENT:
                titleText.setVisibility(View.VISIBLE);
                search_maginfy.setVisibility(View.VISIBLE);

                break;*/

            case COMPANIONS:
                toolbar_add_companion.setVisibility(View.VISIBLE);
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
                titleText.setVisibility(View.VISIBLE);
                break;

            case TRAVEL_PREFERENCE:
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

    public void setSearchAutocomplete(ArrayList<String> list){
        customerAdapter.updateListData(list);
    }

}

