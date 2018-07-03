package com.insurance.insuranceapp.Utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.insurance.insuranceapp.Datamodel.ImagesSaveInfo;
import com.insurance.insuranceapp.Datamodel.Triggers;

import com.insurance.insuranceapp.Datamodel.UserAccountInfo;

public class InsApp extends MultiDexApplication {

    private boolean networkStatus = true;

    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(UserAccountInfo.class);
        configurationBuilder.addModelClasses(Triggers.class);
        configurationBuilder.addModelClasses(ImagesSaveInfo.class);
        ActiveAndroid.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public boolean isNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(boolean networkStatus) {
        this.networkStatus = networkStatus;
    }
}