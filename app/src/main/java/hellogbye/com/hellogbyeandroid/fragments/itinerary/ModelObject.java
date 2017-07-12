package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import hellogbye.com.hellogbyeandroid.R;

/**
 * Created by nyawka on 6/15/17.
 */

public enum ModelObject {

    RED("sdfs", R.layout.view_red),
    BLUE("sfsdf", R.layout.view_blue),
    GREEN("sdsf", R.layout.view_green);



    private String mTitleResId;
    private int mLayoutResId;

    ModelObject(String titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public String getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
