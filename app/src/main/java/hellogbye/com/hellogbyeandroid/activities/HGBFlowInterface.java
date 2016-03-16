package hellogbye.com.hellogbyeandroid.activities;

import android.os.Bundle;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;

/**
 * Created by nyawka on 3/16/16.
 */
public interface HGBFlowInterface {

    void callRefreshItinerary(final int fragment);
    CostumeToolBar getToolBar();

    void goToFragment(int fragment, Bundle bundle);

    void continueFlow(int fragment);
    void loadJSONFromAsset();
    void gotToStartMenuActivity();
    void setHomeImage(String id);

}
