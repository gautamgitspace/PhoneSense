package com.iiitd.phonesense;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class SetAlarms {
	
	public static final int WIFI_ALARM = 1;
	public static final int BLUETOOTH_ALARM = 2;
	public static final int BATTERY_ALARM = 3;
	public static final int GSM_ALARM = 4;
	public static final int LOCATION_ALARM = 5;
	public static final int UPLOAD_ALARM = 6;

	public static void SET_RECURRING_ALARM( int ALARM_CODE, int FREQ_MIN, Context CONTEXT ){
		
		Intent DataSense = new Intent(CONTEXT, AlarmHandler.class);
		DataSense.putExtra("ALARM_CODE", ALARM_CODE);
	    PendingIntent DataCollectIntent = PendingIntent.getBroadcast(CONTEXT,
	            						  ALARM_CODE, DataSense, PendingIntent.FLAG_CANCEL_CURRENT);
	    
	    AlarmManager ALARM = (AlarmManager) CONTEXT.getSystemService(Context.ALARM_SERVICE);
	    
	    ALARM.setRepeating(AlarmManager.RTC_WAKEUP,
	            System.currentTimeMillis(),
	            FREQ_MIN*60*1000,
	    		DataCollectIntent);
	    
	    Log.d("phonesense","Alarm with code "+ALARM_CODE+" set with Freq "+FREQ_MIN);
	}
	
}
