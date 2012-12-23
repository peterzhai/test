package com.zhai.sms.autoreplay;

import java.util.List;

import com.zhai.sms.autoreplay.adapter.SMSSettingAdapter;
import com.zhai.sms.autoreplay.db.SMSSettingDBHelper;
import com.zhai.sms.autoreplay.model.SMSSetObject;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

public class SMSSettingList extends ListActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		List<SMSSetObject> list = SMSSettingDBHelper.getInstance(this)
				.getAllSMSSettingObjects();
		SMSSettingAdapter adapter = new SMSSettingAdapter(this);
		adapter.setList(list);
		setListAdapter(adapter);
	}

}
