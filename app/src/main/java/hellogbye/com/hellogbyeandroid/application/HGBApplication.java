package hellogbye.com.hellogbyeandroid.application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import io.fabric.sdk.android.Fabric;

/**
 * Created by arisprung on 8/12/15.
 */
public class HGBApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(HGBApplication.this));
    }
}
