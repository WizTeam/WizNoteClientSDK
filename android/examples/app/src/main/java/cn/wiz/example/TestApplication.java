package cn.wiz.example;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

/**
 * for multi dex
 */
public class TestApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}
