package hellogbye.com.hellogbyeandroid;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;

/**
 * Created by arisprung on 9/20/15.
 */
public interface HGBMainInterface {

    void openVoiceToTextControl();

    void setTravelOrder(UserTravelVO travelorder);

    void setCNCItems(ArrayList<CNCItem> cncItemArrayList);

    void setSolutionID(String userID);

    void setHomeImage(String id);

    String getSolutionID();

    void setAlternativeFlights(List<NodesVO> alternativeFlightsVO);

    UserTravelVO getTravelOrder();

    ArrayList<CNCItem> getCNCItems();

    List<NodesVO> getAlternativeFlights();

    void addCNCItem(CNCItem cncitem);

    void callRefreshItinerary(final int fragment);

    CostumeToolBar getToolBar();

    void goToFragment(int fragment, Bundle bundle);

    void continueFlow(int fragment);


    void setAccountSettingsAttribute(List<SettingsAttributeParamVO> settingsAttribute);
    List<SettingsAttributeParamVO> getAccountSettingsAttribute();

    void setAccountSettingsAttributeSpecific(List<SettingsAttributesVO> settingsAttribute);
    List<SettingsAttributesVO> getAccountSettingsAttributeSpecific();
}
