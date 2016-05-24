package hellogbye.com.hellogbyeandroid.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HGBPreferencesManager
{

	private static final String SHARED_PREFS = "hgb_prefs";
	public static final String TOKEN = "token";
	public static final String REMMEMBER_ME = "remmember_me";
	public static final String TRAVEL_PREF_ENTRY = "travel_entry";
	public static final String HGB_NAME = "name";
	public static final String HGB_PREFRENCE_ID = "prefrence_id";
	public static final String HGB_LAST_TRAVEL_VO = "last_travel_vo";
	public static final String HGB_CNC_LIST = "cnc_list";
	public static final String HGB_CNC_FIRST_TIME= "cnc_first_time";
	public static final String HGB_FREE_USER = "free_user";
	public static final String HGB_USER_LAST_PSWD = "user_last_pswd";
	public static final String HGB_USER_LAST_EMAIL = "user_last_email";

	private SharedPreferences mSharedPreferencesFile;
	private static HGBPreferencesManager mHomeSharedPreferences = null;

	private HGBPreferencesManager(Context context)
	{
		mSharedPreferencesFile = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
	}

	public static synchronized HGBPreferencesManager getInstance(Context context)
	{

		if (mHomeSharedPreferences == null)
		{
			mHomeSharedPreferences = new HGBPreferencesManager(context);
		}

		return mHomeSharedPreferences;
	}

	public void putBooleanSharedPreferences(String key, boolean keyValue)
	{

		if (mSharedPreferencesFile != null)
		{
			Editor editor = mSharedPreferencesFile.edit();
			editor.putBoolean(key, keyValue);
			editor.apply();
		}
	}

	public void putStringSharedPreferences(String key, String keyValue)
	{

		if (mSharedPreferencesFile != null)
		{
			Editor editor = mSharedPreferencesFile.edit();
			editor.putString(key, keyValue);
			editor.apply();
		}
	}

	public void putIntSharedPreferences(String key, int keyValue)
	{

		if (mSharedPreferencesFile != null)
		{
			Editor editor = mSharedPreferencesFile.edit();
			editor.putInt(key, keyValue);
			editor.apply();
		}
	}

	public String getStringSharedPreferences(String key, String defValue)
	{

		if (mSharedPreferencesFile != null)
			return mSharedPreferencesFile.getString(key, defValue);

		return defValue;
	}

	public boolean getBooleanSharedPreferences(String key, boolean defValue)
	{

		if (mSharedPreferencesFile != null)
			return mSharedPreferencesFile.getBoolean(key, defValue);

		return defValue;
	}

	public int getIntSharedPreferences(String key, int defValue)
	{

		if (mSharedPreferencesFile != null)
			return mSharedPreferencesFile.getInt(key, defValue);

		return defValue;

	}

	public boolean contains(String key)
	{

		if (mSharedPreferencesFile != null)
			return mSharedPreferencesFile.contains(key);

		return false;

	}

	public void removeKey(String keyToRemove)
	{

		if (mSharedPreferencesFile != null)
		{

			Editor editor = mSharedPreferencesFile.edit();
			editor.remove(keyToRemove);
			editor.apply();
		}
	}

    public void deleteSharedPrefrence()
    {

        if (mSharedPreferencesFile != null)
        {

            mSharedPreferencesFile.edit().clear().commit();
        }
    }

	public void deleteSharedPrefrence(String key)
	{

		if (mSharedPreferencesFile != null)
		{

			mSharedPreferencesFile.edit().clear().commit();
		}
	}
}
