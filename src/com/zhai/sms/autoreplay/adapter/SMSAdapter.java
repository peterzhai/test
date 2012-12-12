package com.zhai.sms.autoreplay.adapter;

import java.util.List;

import com.zhai.sms.autoreplay.R;
import com.zhai.sms.autoreplay.model.SMSObject;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SMSAdapter extends BaseAdapter
{
	private List<SMSObject> mList;
	private Context mContext;
	private LayoutInflater mInflater;

	public SMSAdapter(Context context)
	{
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		// TODO Auto-generated constructor stub
	}

	public void setList(List<SMSObject> list)
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

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		if (view == null)
		{
			view = mInflater.inflate(R.layout.sms_row, null);
		}

		SMSObject object = mList.get(position);

		TextView numberTv = (TextView) view.findViewById(R.id.number_tv);
		TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
		TextView receivedTime = (TextView) view
				.findViewById(R.id.receivedtime_tv);
		TextView isReplyTvTv = (TextView) view.findViewById(R.id.isreply_tv);
		numberTv.setText(object.number);
		contentTv.setText(object.content);
		if (!TextUtils.isEmpty(object.receivedTime))
		{
			receivedTime.setText(object.receivedTime);
		}

		if (object.isReply)
		{
			isReplyTvTv.setText("已设置回复");
		} else
		{
			isReplyTvTv.setText("未设置回复");
		}

		return view;
	}

}
