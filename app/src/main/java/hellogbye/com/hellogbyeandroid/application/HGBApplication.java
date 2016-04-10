package hellogbye.com.hellogbyeandroid.application;

import android.app.Application;
import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.okhttp.OkHttpClient;

import hellogbye.com.hellogbyeandroid.BuildConfig;
import hellogbye.com.hellogbyeandroid.network.OkHttpStack;
import io.fabric.sdk.android.Fabric;

/**
 * Created by arisprung on 8/12/15.
 */
public class HGBApplication extends Application {
    private static HGBApplication instance;
    public final static int TIMEOUT_TIME = 60000;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fabric.with(this, new Crashlytics());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(HGBApplication.this));
        if (BuildConfig.IS_DEV) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build());
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
