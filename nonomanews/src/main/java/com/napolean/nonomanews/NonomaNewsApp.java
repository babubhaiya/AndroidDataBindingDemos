package com.napolean.nonomanews;

import android.app.Application;
import android.content.Context;

/**
 * Created by ravi on 5/23/17.
 */

public class NonomaNewsApp extends Application {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
