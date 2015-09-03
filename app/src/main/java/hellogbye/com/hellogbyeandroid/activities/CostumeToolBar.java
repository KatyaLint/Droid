package hellogbye.com.hellogbyeandroid.activities;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

public class CostumeToolBar extends Toolbar {

    private ImageButton keyBoardImage;
    private ImageButton purchaseButton;
    private ImageButton favoriteButton;
    private FontTextView editPreferense;
    private ImageView homeTitleImage;
    private FontTextView titleText;
    private Toolbar mToolbar;

    public CostumeToolBar(Context context) {
        super(context);
    }

    public CostumeToolBar(Context context, AttributeSet attrs) {
        super(context,attrs);
    }


    public CostumeToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs, defStyleAttr);
    }


    public void initToolBarItems(){
        if(homeTitleImage == null) {
            homeTitleImage = (ImageView) findViewById(R.id.home_image);
        }
        if(titleText == null) {
            titleText = (FontTextView) findViewById(R.id.titleBar);
        }
        if(keyBoardImage == null) {
            keyBoardImage = (ImageButton) findViewById(R.id.keyboard);
        }
        if(purchaseButton == null) {
            purchaseButton = (ImageButton) findViewById(R.id.purchaseButton);
        }
        if(favoriteButton == null) {
            favoriteButton = (ImageButton) findViewById(R.id.favority);
        }
        if(editPreferense == null) {
            editPreferense = (FontTextView) findViewById(R.id.editPreference);
        }
    }


    public void updateToolBarView(int position){

        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);

        homeTitleImage.setVisibility(View.GONE);
        titleText.setVisibility(View.GONE);
        keyBoardImage.setVisibility(View.GONE);
        purchaseButton.setVisibility(View.GONE);
        favoriteButton.setVisibility(View.GONE);
        editPreferense.setVisibility(View.GONE);


        switch (navBar) {
            case HOME:
                homeTitleImage.setVisibility(View.VISIBLE);
                keyBoardImage.setVisibility(View.VISIBLE);

                break;
            case ITINARERY:
                titleText.setVisibility(View.VISIBLE);
                purchaseButton.setVisibility(View.VISIBLE);
                favoriteButton.setVisibility(View.VISIBLE);
                break;
            case HISTORY:
                titleText.setVisibility(View.VISIBLE);
                break;
            case TRIPS:
                titleText.setVisibility(View.VISIBLE);
                break;
            case COMPANIONS:
                titleText.setVisibility(View.VISIBLE);
                break;
            case PREFERENCE:
                titleText.setVisibility(View.VISIBLE);
                editPreferense.setVisibility(View.VISIBLE);
                break;
            case ACCOUNT:
                titleText.setVisibility(View.VISIBLE);
                break;
            case HELP:
                titleText.setVisibility(View.VISIBLE);
                break;


        }
        String selectedItem = navBar.getNavTitle();
        //setTitle(selectedItem);
        titleText.setText(selectedItem);
    }


    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public void setmToolbar(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
    }
}

