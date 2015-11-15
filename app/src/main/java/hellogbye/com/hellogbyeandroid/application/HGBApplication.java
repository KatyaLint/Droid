package hellogbye.com.hellogbyeandroid.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import hellogbye.com.hellogbyeandroid.BuildConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by arisprung on 8/12/15.
 */
public class HGBApplication extends Application {
    private static HGBApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fabric.with(this, new Crashlytics());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(HGBApplication.this));
        if (BuildConfig.IS_DEV) {
            Stetho.initializeWithDefaults(this);
        }
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    public static HGBApplication getInstance() {
        return instance;
    }
}
