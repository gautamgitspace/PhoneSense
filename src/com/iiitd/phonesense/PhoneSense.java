package com.iiitd.phonesense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.iiitd.phonesense.datasense.PhoneState;
import com.iiitd.phonesense.util.PhoneSenseComm;

public class PhoneSense {
	
	private static final String PREFS_NAME = "PhoneSense";
	public static String SERVER_ADDRESS = null;
	
	public static void StartDataCollection(Context CONTEXT, String SERVER_BASE_URL){
		
		SERVER_ADDRESS = SERVER_BASE_URL;
		
		SharedPreferences settings = CONTEXT.getSharedPreferences(PREFS_NAME, 0);
	    boolean init = settings.getBoolean("init", false);
	    
		if( !init ){
			String SensorList = PhoneSenseComm.HandShake(CONTEXT);
			String[] Sensors = SensorList.split(";");
			
			for( String sensor: Sensors){
				String[] SensorInfo = sensor.split(":");
				
				String SensorType = SensorInfo[0];
				int SensorFreq = Integer.parseInt(SensorInfo[1]);
				
				ActivateSensor( SensorType, SensorFreq, CONTEXT );
			}
			
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putBoolean("init", true);
		    editor.commit();
		}
	}
	
	private static void ActivateSensor( String SensorType, int SensorFreq, Context CONTEXT ){
		
		if( SensorType.equalsIgnoreCase("Wifi") )
			SetAlarms.SET_RECURRING_ALARM(SetAlarms.WIFI_ALARM, SensorFreq, CONTEXT);
		
		if( SensorType.equalsIgnoreCase("Bluetooth") )
			SetAlarms.SET_RECURRING_ALARM(SetAlarms.BLUETOOTH_ALARM, SensorFreq, CONTEXT);
		
		if( SensorType.equalsIgnoreCase("GSM") )
			SetAlarms.SET_RECURRING_ALARM(SetAlarms.GSM_ALARM, SensorFreq, CONTEXT);
		
		if( SensorType.equalsIgnoreCase("GPS") )
			SetAlarms.SET_RECURRING_ALARM(SetAlarms.LOCATION_ALARM, SensorFreq, CONTEXT);
		
		if( SensorType.equalsIgnoreCase("Phone") )
			CONTEXT.startService(new Intent(CONTEXT, PhoneState.class));
		
		if( SensorType.equalsIgnoreCase("Battery") )
			SetAlarms.SET_RECURRING_ALARM(SetAlarms.BATTERY_ALARM, SensorFreq, CONTEXT);
		
		if( SensorType.equalsIgnoreCase("Upload") )
			SetAlarms.SET_RECURRING_ALARM(SetAlarms.UPLOAD_ALARM, SensorFreq, CONTEXT);
	}
}
