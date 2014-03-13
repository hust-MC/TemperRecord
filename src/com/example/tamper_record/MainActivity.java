package com.example.tamper_record;

import java.sql.Date;
import java.text.ParseException;

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

import java.util.*;
import java.text.*;

public class MainActivity extends Activity
{
	static LinearLayout setInfo;
	EditText inputNum, inputBody;
	Button setSms;
	RadioButton rx_bt;
	RadioButton tx_bt;
	RadioGroup group;
	TimePicker timepicker;
	DatePicker datepicker;

	public static Uri mSmsUri = Uri.parse("content://sms");

	public void setSms()
	{
		setSms.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				setInfo = (LinearLayout) getLayoutInflater().inflate(
						R.layout.setinfo, null);
				inputNum = (EditText) setInfo.findViewById(R.id.input_num);
				inputBody = (EditText) setInfo.findViewById(R.id.input_body);
				timepicker = (TimePicker) setInfo.findViewById(R.id.timepicker);
				datepicker = (DatePicker) setInfo.findViewById(R.id.datepicker);
				rx_bt = (RadioButton) setInfo.findViewById(R.id.rx);

				new AlertDialog.Builder(MainActivity.this)
						.setView(setInfo)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog,
											int which)
									{
										try
										{
											insertsms();
											Toast.makeText(MainActivity.this,
													"添加短信成功!",
													Toast.LENGTH_SHORT).show();
											finish();
										} catch (ParseException e)
										{
											e.printStackTrace();
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});
	}

	public void widget_init()
	{
		setSms = (Button) findViewById(R.id.set_info);
		setSms();
	}

	private void insertsms() throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(datepicker.getYear(), datepicker.getMonth(),
				datepicker.getDayOfMonth(), timepicker.getCurrentHour(),
				timepicker.getCurrentMinute());
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		ContentValues values = new ContentValues();
		values.put("address", inputNum.getText().toString());
		values.put("body", inputBody.getText().toString());
		values.put("date", calendar.getTime().getTime());
		values.put("read", 0);

		if (rx_bt.isChecked())
		{
			values.put("type", 1);
		}
		else
		{
			values.put("type", 2);
		}
		values.put("service_center", "+8613010776500");
		getContentResolver().insert(mSmsUri, values);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
