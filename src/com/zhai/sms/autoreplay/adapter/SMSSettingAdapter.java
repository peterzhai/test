package com.zhai.sms.autoreplay.adapter;

import java.util.List;

import com.zhai.sms.autoreplay.R;
import com.zhai.sms.autoreplay.db.SMSSettingDBHelper;
import com.zhai.sms.autoreplay.model.SMSObject;
import com.zhai.sms.autoreplay.model.SMSSetObject;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SMSSettingAdapter extends BaseAdapter
{
	private List<SMSSetObject> mList;
	private Context mContext;
	private LayoutInflater mInflater;

	public SMSSettingAdapter(Context context)
	{
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		// TODO Auto-generated constructor stub
	}

	public void setList(List<SMSSetObject> list)
	{
		mList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return arg0;
	}

	class ViewHolder
	{
		TextView numberTv, keyTv, contentTv;
		ToggleButton toggleButton;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		final SMSSetObject object = mList.get(position);

		if (view == null)
		{
			viewHolder = new ViewHolder();
			view = mInflater.inflate(R.layout.smsset_row, null);
			viewHolder.numberTv = (TextView) view.findViewById(R.id.number_tv);
			viewHolder.keyTv = (TextView) view.findViewById(R.id.key_tv);
			viewHolder.contentTv = (TextView) view
					.findViewById(R.id.content_tv);
			viewHolder.toggleButton = (ToggleButton) view
					.findViewById(R.id.toggleButton);
			viewHolder.toggleButton
					.setOnCheckedChangeListener(new OnCheckedChangeListener()
					{

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked)
						{
							object.isUse = isChecked;
							SMSSettingDBHelper.getInstance(mContext).update(
									object);
						}
					});
			view.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.toggleButton.setChecked(object.isUse);

		viewHolder.numberTv.setText(object.number);
		viewHolder.contentTv.setText(object.replyContent);
		viewHolder.keyTv.setText(object.keyWord);

		return view;
	}

}
