package com.example.tamper_record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;
import android.view.LayoutInflater;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.provider.CallLog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.Activity;
import android.content.ContentValues;

public class InsertCall
{
	static LinearLayout setInfo;
	LayoutInflater inflater;
	EditText inputNum, inputMinutes, inputSeconds;
	RadioButton rx_bt, rx_miss_bt;
	TimePicker timepicker;
	DatePicker datepicker;
	Context context;

	public static final int INCOMING_TYPE = 1;
	public static final int OUTGOING_TYPE = 2;
	public static final int MISSED_TYPE = 3;

	public static Uri mSmsUri = Uri.parse("content://sms");

	InsertCall(Context context)
	{
		this.context = context;
	}

	private void insertCall() throws ParseException
	{
		int duration = Integer.parseInt(inputMinutes.getText().toString()) * 60
				+ Integer.parseInt(inputSeconds.getText().toString());
		Calendar calendar = Calendar.getInstance();
		calendar.set(datepicker.getYear(), datepicker.getMonth(),
				datepicker.getDayOfMonth(), timepicker.getCurrentHour(),
				timepicker.getCurrentMinute());

		ContentValues values = new ContentValues();
		values.put(CallLog.Calls.NUMBER, inputNum.getText().toString());       			// Set contacts number
		values.put(CallLog.Calls.DATE, calendar.getTime().getTime()); 					// Set communication date 
		values.put(CallLog.Calls.DURATION, duration);       							// Set communication duration
		values.put(CallLog.Calls.NEW, 1); 												// Set the call type is unread

		if (rx_bt.isChecked())
		{
			values.put(CallLog.Calls.TYPE, INCOMING_TYPE); 								// Set the contacts Type by the radio button
		} else if (rx_miss_bt.isChecked())
		{
			values.put(CallLog.Calls.TYPE, MISSED_TYPE);
		} else
		{
			values.put(CallLog.Calls.TYPE, OUTGOING_TYPE);
		}
		context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
	}

	void CallDialog()
	{
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setInfo = (LinearLayout) inflater.inflate(R.layout.set_call, null);
		inputNum = (EditText) setInfo.findViewById(R.id.input_callnum);
		inputMinutes = (EditText) setInfo.findViewById(R.id.input_minites);
		inputSeconds = (EditText) setInfo.findViewById(R.id.input_seconds);
		timepicker = (TimePicker) setInfo.findViewById(R.id.timepicker);
		datepicker = (DatePicker) setInfo.findViewById(R.id.datepicker);
		rx_bt = (RadioButton) setInfo.findViewById(R.id.rx);
		rx_miss_bt = (RadioButton) setInfo.findViewById(R.id.rx_miss);

		new AlertDialog.Builder(context).setView(setInfo)
				.setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						try
						{
							insertCall();
							Toast.makeText(context, "添加通话记录成功!",
									Toast.LENGTH_SHORT).show();
							MainActivity.instance.finish();
						} catch (ParseException e)
						{
							e.printStackTrace();
						}
					}
				}).setNegativeButton("取消", null).show();
	}

}
