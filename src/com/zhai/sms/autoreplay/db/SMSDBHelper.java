package com.zhai.sms.autoreplay.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zhai.sms.autoreplay.model.SMSObject;

public class SMSDBHelper extends SQLiteOpenHelper
{

	private static final String DATABASE_NAME = "test.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "sms";
	private static final String TABLE_NAME2 = "smsset";

	private SQLiteDatabase sqLiteDatabase;

	private static SMSDBHelper instance;

	public static SMSDBHelper getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new SMSDBHelper(context);
		}
		return instance;
	}

	public SMSDBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

		Log.w("SMSDB", "@@zhai:sms db onCreate!");
		// id smsId number content replyContent isReply receivedTime replyTime
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,smsId INTEGER, number VARCHAR,content TEXT,replyContent TEXT,isReply BOOLEAN,receivedTime TIMESTAMP,replyTime TIMESTAMP)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME2
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, number VARCHAR,keyWord VARCHAR,replyContent TEXT,isUse INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub

	}

	public void insert(SMSObject smsObject)
	{
		sqLiteDatabase = getWritableDatabase();
		sqLiteDatabase.beginTransaction(); // 开始事务
		try
		{

			sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME
					+ " VALUES(null,?,?,?,?,?,?,?)", new Object[]
			{smsObject.smsId, smsObject.number, smsObject.content,
					smsObject.replyContent, smsObject.isReply,
					smsObject.receivedTime, smsObject.replyTime});

			sqLiteDatabase.setTransactionSuccessful(); // 设置事务成功完成
		} finally
		{
			sqLiteDatabase.endTransaction(); // 结束事务
		}
		sqLiteDatabase.close();

	}

	public void update(SMSObject smsObject)
	{
		sqLiteDatabase = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("_id", smsObject.id);
		values.put("smsId", smsObject.smsId);
		values.put("number", smsObject.number);
		values.put("content", smsObject.content);
		values.put("replyContent", smsObject.replyContent);
		values.put("isReply", smsObject.isReply);
		values.put("receivedTime", smsObject.receivedTime);
		values.put("replyTime", smsObject.replyTime);
		sqLiteDatabase.update(TABLE_NAME, values, "_id=?", new String[]
		{String.valueOf(smsObject.id)});
		sqLiteDatabase.close();

	}

	public List<SMSObject> getAllSmsObjects()
	{
		sqLiteDatabase = getWritableDatabase();
		List<SMSObject> smsList = new ArrayList<SMSObject>();
		String sqlString = "select * from " + TABLE_NAME
				+ " order by receivedTime desc";
		Cursor c = sqLiteDatabase.rawQuery(sqlString, null);
		while (c.moveToNext())
		{
			SMSObject smsObject = new SMSObject();
			smsObject.id = c.getInt(c.getColumnIndex("_id"));
			smsObject.smsId = c.getInt(c.getColumnIndex("smsId"));
			smsObject.number = c.getString(c.getColumnIndex("number"));
			smsObject.content = c.getString(c.getColumnIndex("content"));
			smsObject.replyContent = c.getString(c
					.getColumnIndex("replyContent"));
			smsObject.receivedTime = c.getString(c
					.getColumnIndex("receivedTime"));
			smsObject.replyTime = c.getString(c.getColumnIndex("replyTime"));

			smsList.add(smsObject);
		}
		c.close();
		sqLiteDatabase.close();
		return smsList;

	}

	public void close()
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.close();
		}
	}

}
