package com.zhai.sms.autoreplay.receiver;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.zhai.sms.autoreplay.db.SMSDBHelper;
import com.zhai.sms.autoreplay.db.SMSSettingDBHelper;
import com.zhai.sms.autoreplay.model.SMSObject;
import com.zhai.sms.autoreplay.model.SMSSetObject;
import com.zhai.sms.autoreplay.utils.SMSUtils;

public class SMSBroadcastReceiver extends BroadcastReceiver
{
	private static final String tag = SMSBroadcastReceiver.class
			.getSimpleName();

	private static final String strACT = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(final Context context, Intent intent)
	{
		if (intent.getAction().equals(strACT))
		{
			// StringBuilder sb = new StringBuilder();
			Bundle bundle = intent.getExtras();
			String tel = null;
			StringBuilder message = new StringBuilder();
			if (bundle != null)
			{
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] msg = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++)
				{
					msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (final SmsMessage currMsg : msg)
				{
					// sb.append("From:");
					// sb.append(currMsg.getDisplayOriginatingAddress());
					// sb.append("\nMessage:");
					// sb.append(currMsg.getDisplayMessageBody());

					tel = currMsg.getDisplayOriginatingAddress();

					message.append(currMsg.getDisplayMessageBody());

				}

				List<SMSSetObject> settings = SMSSettingDBHelper.getInstance(
						context).getAllSMSSettingObjects();

				for (SMSSetObject object : settings)
				{
					Log.w(tag, "@@zhai:setting:" + object.number + " "
							+ object.replyContent);

					if (tel.contains(object.number)
							&& message.toString().contains(object.keyWord))
					{
						SMSUtils.sendSMS(context, object.number,
								object.replyContent);
					}
				}

				SMSObject smsObject = new SMSObject();
				smsObject.content = message.toString();
				smsObject.number = tel;
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy年MM月dd日   HH:mm:ss");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				smsObject.receivedTime = formatter.format(curDate);
				SMSDBHelper.getInstance(context).insert(smsObject);
			}
			Toast.makeText(context, message.toString(), Toast.LENGTH_LONG)
					.show();
			// Intent i = new Intent(context, DBDemo.class);
			// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(i);
		}
	}

}
