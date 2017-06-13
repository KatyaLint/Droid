package hellogbye.com.hellogbyeandroid.fragments.cnc;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import hellogbye.com.hellogbyeandroid.models.vo.cnc.CNCTutorialsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;

/**
 * Created by nyawka on 5/10/17.
 */

public class CNCTutorials {


    CNCTutorialsVO cncTutorialsVO;
    private Context mContext;
    public CNCTutorials(){

    }

    public static CNCTutorialsVO parseTutorials(Activity activity){
        CNCTutorialsVO cncTutorialsVO = null;
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<CNCTutorialsVO>() {
            }.getType();
            String strJson = HGBUtility.loadJSONFromAsset("json/exampleTutorials.txt", activity);

            cncTutorialsVO = gson.fromJson(strJson, type);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return cncTutorialsVO;
    }


}
