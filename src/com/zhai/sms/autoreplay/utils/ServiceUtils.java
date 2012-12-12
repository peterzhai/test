package com.zhai.sms.autoreplay.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.util.Log;

public class ServiceUtils
{
	public static final String TAG = ServiceUtils.class.getSimpleName();

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param context
	 * @param className
	 *            判断的服务名字：包名+类名
	 * @return true 在运行, false 不在运行
	 */

	public static boolean isServiceRunning(Context context, String className)
	{

		boolean isRunning = false;

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		if (!(serviceList.size() > 0))
		{

			return false;

		}

		for (int i = 0; i < serviceList.size(); i++)
		{

			if (serviceList.get(i).service.getClassName().equals(className) == true)
			{

				isRunning = true;

				// activityManager.killBackgroundProcesses(className);
				break;

			}

		}
		Log.i(TAG, "@@zhai:" + className + "-" + isRunning);

		return isRunning;

	}
}
