package com.zhai.sms.autoreplay.utils;

import java.util.List;

import com.zhai.sms.autoreplay.model.SMSSetObject;

public class SMSFliter
{
	public static SMSSetObject FliterNum(List<SMSSetObject> list, String num)
	{
		for (int i = 0; i < list.size(); i++)
		{
			SMSSetObject setObject = list.get(i);
			if (setObject.number.equals(num))
			{
				return setObject;
			}
		}
		return null;
	}

}
