package com.lianzong.logistics.app.utils;

import android.text.TextUtils;
import android.util.Log;

import com.lianzong.logistics.app.LogisticsApplication;

/**
 * Created by wu_shenglong on 2015/7/24.
 */
public class MyLog{
    private static final String TAG = "Logistics";
    private static final boolean debug = TextUtils.equals(LogisticsApplication.sVersionType, "DEBUG");

    public static void i(String model, String msg) {
        if (debug) {
            Log.i(TAG, getLogMessage(model, msg));
        }
    }

    public static void d(String model, String msg) {
        if (debug) {
            Log.d(TAG, getLogMessage(model, msg));
        }
    }

    public static void w(String model, String msg) {
        if (debug) {
            Log.w(TAG, getLogMessage(model, msg));
        }
    }

    public static void e(String model, String msg) {
        if (debug) {
            Log.e(TAG, getLogMessage(model, msg));
        }
    }

    private static String getLogMessage(String model, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(model).append("]\t").append(msg);
        return sb.toString();
    }
}
