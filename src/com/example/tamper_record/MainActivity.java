package com.example.tamper_record;


import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.app.*;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RemoteViews.RemoteView;
import android.widget.TextView;
import android.widget.TimePicker;

import android.app.Activity;

public class MainActivity extends Activity
{
	static MainActivity instance;
	Button setSms;
	Button setCall;

	public static Uri mSmsUri = Uri.parse("content://sms");

	public void setSms()
	{
		setSms.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new InsertSms(MainActivity.this).SmsDialog();
			}
		});
	}

	public void setCall()
	{
		setCall.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				new InsertCall(MainActivity.this).CallDialog();
			}
		});
	}
	
	public void widget_init()
	{
		setSms = (Button) findViewById(R.id.insert_sms);
		setCall = (Button) findViewById(R.id.insert_call);
		setSms();
		setCall();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instance = this;
		widget_init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
