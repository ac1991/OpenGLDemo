package com.example.demo1.utils;

import android.content.Context;
import android.util.Log;

public class Logger {

	// 用于开发者的log
	protected static boolean mLogger_dev = true;
	private static Context mContext;
	public static final String TAG = "OpenGL";

	public static void setDebugLogging(boolean enabled) {
		mLogger_dev = enabled;
	}

	public static void d_dev( String msg) {
		if (mLogger_dev) {
			Log.d(TAG, msg);
		}
	}

	public static void i_dev(String msg) {
		if (mLogger_dev) {
			Log.i(TAG, msg);
		}
	}

	public static void w_dev(String msg) {
		if (mLogger_dev) {
			Log.w(TAG, msg);
//			Logger.writeLog(null, "warn:" + msg, 2);
		}
	}

	public static void e_dev(String msg) {
		if (mLogger_dev) {
			Log.e(TAG, msg);
		}
	}

}
