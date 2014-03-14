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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.Activity;

public class InsertSms
{
	
	static LinearLayout setInfo;
	LayoutInflater inflater;
	EditText inputNum, inputBody;
	RadioButton rx_bt;
	TimePicker timepicker;
	DatePicker datepicker;
	Context context;
	static boolean isComplete = false;
	
	public static Uri mSmsUri = Uri.parse("content://sms");
	
	InsertSms(Context context)
	{
		this.context = context;
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
		} else
		{
			values.put("type", 2);
		}
		values.put("service_center", "+8613010776500");
		context.getContentResolver().insert(mSmsUri, values);
	}
	
	 void SmsDialog()
	{
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setInfo = (LinearLayout)inflater.inflate(R.layout.set_sms, null);
		inputNum = (EditText) setInfo.findViewById(R.id.input_num);
		inputBody = (EditText) setInfo.findViewById(R.id.input_body);
		timepicker = (TimePicker) setInfo.findViewById(R.id.timepicker);
		datepicker = (DatePicker) setInfo.findViewById(R.id.datepicker);
		rx_bt = (RadioButton) setInfo.findViewById(R.id.rx);

		new AlertDialog.Builder(context)
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
									Toast.makeText(context,
											"添加短信成功!",
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
