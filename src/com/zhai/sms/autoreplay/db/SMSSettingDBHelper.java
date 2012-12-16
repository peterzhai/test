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
import com.zhai.sms.autoreplay.model.SMSSetObject;

public class SMSSettingDBHelper extends SQLiteOpenHelper
{

	private static final String DATABASE_NAME = "test.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "smsset";
	private static final String TABLE_NAME2 = "sms";

	private SQLiteDatabase sqLiteDatabase;

	private static SMSSettingDBHelper instance;

	public static SMSSettingDBHelper getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new SMSSettingDBHelper(context);
		}
		return instance;
	}

	public SMSSettingDBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.w("SettingDB", "@@zhai:setting SMSSettingDBHelper()!");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.w("SettingDB", "@@zhai:setting onCreate!");
		// id number key content replyContent
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME2
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,smsId INTEGER, number VARCHAR,content TEXT,replyContent TEXT,isReply BOOLEAN,receivedTime TIMESTAMP,replyTime TIMESTAMP)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, number VARCHAR,keyWord VARCHAR,replyContent TEXT)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub

	}

	public void insert(SMSSetObject smsObject)
	{
		sqLiteDatabase = getWritableDatabase();
		sqLiteDatabase.beginTransaction(); // 开始事务
		try
		{

			sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME
					+ " VALUES(null,?,?,?)", new Object[]
			{smsObject.number, smsObject.keyWord, smsObject.replyContent});

			sqLiteDatabase.setTransactionSuccessful(); // 设置事务成功完成
		} finally
		{
			sqLiteDatabase.endTransaction(); // 结束事务
		}

		sqLiteDatabase.close();

	}

	public void update(SMSSetObject smsObject)
	{
		sqLiteDatabase = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("_id", smsObject.id);
		values.put("keyWord", smsObject.keyWord);
		values.put("number", smsObject.number);
		values.put("replyContent", smsObject.replyContent);
		sqLiteDatabase.update(TABLE_NAME, values, "_id=?", new String[]
		{String.valueOf(smsObject.id)});

		sqLiteDatabase.close();
	}

	public List<SMSSetObject> getAllSMSSettingObjects()
	{
		sqLiteDatabase = getWritableDatabase();
		List<SMSSetObject> smsList = new ArrayList<SMSSetObject>();
		String sqlString = "select * from " + TABLE_NAME;
		Cursor c = sqLiteDatabase.rawQuery(sqlString, null);
		while (c.moveToNext())
		{
			SMSSetObject smsObject = new SMSSetObject();
			smsObject.id = c.getInt(c.getColumnIndex("_id"));
			smsObject.number = c.getString(c.getColumnIndex("number"));
			smsObject.keyWord = c.getString(c.getColumnIndex("keyWord"));
			smsObject.replyContent = c.getString(c
					.getColumnIndex("replyContent"));

			smsList.add(smsObject);
		}
		c.close();
		sqLiteDatabase.close();
		return smsList;
	}

	public List<SMSSetObject> getSMSSettingObjects(String num)
	{
		sqLiteDatabase = getWritableDatabase();
		List<SMSSetObject> smsList = new ArrayList<SMSSetObject>();
		String sqlString = "select * from " + TABLE_NAME
				+ " where number like ? or number=''";
		Cursor c = sqLiteDatabase.rawQuery(sqlString, new String[]
		{"%"+num+"%"});
		while (c.moveToNext())
		{
			SMSSetObject smsObject = new SMSSetObject();
			smsObject.id = c.getInt(c.getColumnIndex("_id"));
			smsObject.number = c.getString(c.getColumnIndex("number"));
			smsObject.keyWord = c.getString(c.getColumnIndex("keyWord"));
			smsObject.replyContent = c.getString(c
					.getColumnIndex("replyContent"));

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
