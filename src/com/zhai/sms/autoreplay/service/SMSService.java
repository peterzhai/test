package com.zhai.sms.autoreplay.service;

import com.zhai.sms.autoreplay.receiver.SMSBroadcastReceiver;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class SMSService extends Service
{
	
	private static final String TAG = SMSService.class.getSimpleName();
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.w(TAG, "@@zhai:onStartCommand...");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		Log.w(TAG, "@@zhai:onBind...");
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId)
	{
		Log.w(TAG, "@@zhai:onStart...");
		
//		IntentFilter intentFilter = new IntentFilter();
//		intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
//		intentFilter.setPriority(Integer.MAX_VALUE);  
//		registerReceiver(new SMSBroadcastReceiver(), intentFilter);
		super.onStart(intent, startId);
	}
	
	@Override
	public void onCreate()
	{
		Log.w(TAG, "@@zhai:onCreate...");
		super.onCreate();
	}

	
}
