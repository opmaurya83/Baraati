package com.nectarbits.baraati.Chat;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.digits.sdk.android.Digits;
import com.nectarbits.baraati.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBSettings;
import com.nectarbits.baraati.Chat.utils.ActivityLifecycleHandler;
import com.nectarbits.baraati.Chat.utils.StringObfuscator;
import com.nectarbits.baraati.Chat.utils.helpers.SharedHelper;
import com.nectarbits.baraati.Chat.utils.image.ImageLoaderUtils;
import com.quickblox.q_municate_db.managers.DataManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.BuildConfig;
import io.fabric.sdk.android.Fabric;

public class App extends MultiDexApplication {

    private static App instance;
    private SharedHelper appSharedHelper;

    public static App getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

       // initFabric();
        initApplication();
        registerActivityLifecycleCallbacks(new ActivityLifecycleHandler());
    }

    private void initFabric(){
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                StringObfuscator.getTwitterConsumerKey(),
                StringObfuscator.getTwitterConsumerSecret());

        Fabric.with(this,
                crashlyticsKit,
                new TwitterCore(authConfig),
                new Digits.Builder().withTheme(R.style.AppTheme).build());
    }

    private void initApplication() {
        instance = this;

        initQb();
        initDb();
        initImageLoader(this);
    }

    private void initQb() {
        QBChatService.setDebugEnabled(StringObfuscator.getDebugEnabled());

        QBSettings.getInstance().init(getApplicationContext(),
                StringObfuscator.getApplicationId(),
                StringObfuscator.getAuthKey(),
                StringObfuscator.getAuthSecret());
        QBSettings.getInstance().setAccountKey(StringObfuscator.getAccountKey());
    }

    private void initDb() {
        DataManager.init(this);
    }

    private void initImageLoader(Context context) {
        ImageLoader.getInstance().init(ImageLoaderUtils.getImageLoaderConfiguration(context));
    }

    public synchronized SharedHelper getAppSharedHelper() {
        return appSharedHelper == null
                ? appSharedHelper = new SharedHelper(this)
                : appSharedHelper;
    }
}