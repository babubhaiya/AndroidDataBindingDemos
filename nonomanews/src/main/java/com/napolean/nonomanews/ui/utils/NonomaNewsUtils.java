package com.napolean.nonomanews.ui.utils;

import android.widget.Toast;

import com.napolean.nonomanews.NonomaNewsApp;

/**
 * Created by ravi on 5/31/17.
 */

public class NonomaNewsUtils {

    public static void showToast(String iMessage){
        Toast.makeText(NonomaNewsApp.getContext(), iMessage, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String iMessage){
        Toast.makeText(NonomaNewsApp.getContext(), iMessage, Toast.LENGTH_SHORT).show();
    }

    public static void showDebugToast(String iMessage) {
        if (NonomaNewsConstants.DEBUG_MODE)
            Toast.makeText(NonomaNewsApp.getContext(), iMessage, Toast.LENGTH_SHORT).show();
    }



}
