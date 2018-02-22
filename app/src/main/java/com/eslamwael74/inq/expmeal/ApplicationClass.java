package com.eslamwael74.inq.expmeal;

import android.app.Application;

/**
 * Created by eslam on 2/22/2018.
 */

public class ApplicationClass extends Application {

    public static ApplicationClass instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ApplicationClass getApplicationClass() {
        return instance;
    }

}
