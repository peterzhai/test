package com.zhai.sms.autoreplay;

import java.util.List;

import org.w3c.dom.Text;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhai.sms.autoreplay.adapter.SMSAdapter;
import com.zhai.sms.autoreplay.db.SMSDBHelper;
import com.zhai.sms.autoreplay.db.SMSSettingDBHelper;
import com.zhai.sms.autoreplay.model.SMSObject;
import com.zhai.sms.autoreplay.model.SMSSetObject;
import com.zhai.sms.autoreplay.receiver.SMSBroadcastReceiver;
import com.zhai.sms.autoreplay.utils.Constant;

public class SMSAutoReplyActivity extends Activity
{
	private ListView mListView;
	private SMSBroadCast mBroadcastReceiver;

	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			Log.w("SMSAuto", "@@zhai:hand message!");
			initListView();
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// if (!ServiceUtils.isServiceRunning(this, SMSService.class.getName()))
		// {
		// Intent intent = new Intent(this, SMSService.class);
		// startService(intent);
		// }

		mListView = (ListView) findViewById(R.id.listView);

		initListView();
		
		mBroadcastReceiver = new SMSBroadCast();
		
		registerReceiver(mBroadcastReceiver, new IntentFilter(Constant.INSERT_FRESH));

	}

	private void initListView()
	{

		Log.w("SMSAuto", "@@zhai:init listview!");
		List<SMSObject> list = SMSDBHelper.getInstance(this).getAllSmsObjects();

		SMSAdapter adapter = new SMSAdapter(this);
		adapter.setList(list);

		mListView.setAdapter(adapter);
	}

	class SMSBroadCast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			Log.w("SMSAuto", "@@zhai:onReceive:"+intent.getAction());
			if (intent.getAction().equals(Constant.INSERT_FRESH))
			{
				mHandler.sendEmptyMessage(0);
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		this.getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.item1:
			View view = LayoutInflater.from(this).inflate(R.layout.add_reply,
					null);
			final EditText numEditText = (EditText) view
					.findViewById(R.id.num_editText);
			final EditText keyEditText = (EditText) view
					.findViewById(R.id.key_editText);
			final EditText reConText = (EditText) view
					.findViewById(R.id.re_content_editText);
			AlertDialog dialog = new AlertDialog.Builder(this).setView(view)
					.setPositiveButton("取消", new OnClickListener()
					{

						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							// TODO Auto-generated method stub

						}
					}).setNegativeButton("确定", new OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							String number = numEditText.getText().toString();
							String key = keyEditText.getText().toString();
							String content = reConText.getText().toString();

							if (TextUtils.isEmpty(key.trim()))
							{
								Toast.makeText(SMSAutoReplyActivity.this,
										"关键字不能为空", Toast.LENGTH_SHORT).show();
								return;
							}

							if (TextUtils.isEmpty(content.trim()))
							{
								Toast.makeText(SMSAutoReplyActivity.this,
										"回复内容不能为空", Toast.LENGTH_SHORT).show();
								return;
							}

							SMSSetObject smsSetObject = new SMSSetObject();
							smsSetObject.number = number.trim();
							smsSetObject.keyWord = key;
							smsSetObject.replyContent = content;
							SMSSettingDBHelper.getInstance(
									SMSAutoReplyActivity.this).insert(
									smsSetObject);

						}
					}).create();

			dialog.show();
			break;
		case R.id.item2:
			break;

		case R.id.item3:
			Intent intent = new Intent(this, SMSSettingList.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	protected void onDestroy()
	{
		if (mBroadcastReceiver!=null)
		{
			unregisterReceiver(mBroadcastReceiver);
		}
		
		super.onDestroy();
	}

}