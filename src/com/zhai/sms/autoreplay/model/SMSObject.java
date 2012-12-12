package com.zhai.sms.autoreplay.model;

import java.io.Serializable;

public class SMSObject implements Serializable
{
	
//	id smsId number content replyContent isReply receivedTime replyTime
	public int id;
	public int smsId;
	public String number;
	public String content;
	public String replyContent;
	public boolean isReply;
	public String receivedTime;
	public String replyTime;

}
