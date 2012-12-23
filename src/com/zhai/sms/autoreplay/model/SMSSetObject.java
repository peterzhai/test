package com.zhai.sms.autoreplay.model;

import java.io.Serializable;

public class SMSSetObject implements Serializable
{

//	id number keyWord replyContent

	public int id;
	public String number;
	public String keyWord;
	public String replyContent;
	public boolean isUse;
}
