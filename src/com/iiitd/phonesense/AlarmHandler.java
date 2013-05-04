package com.iiitd.phonesense;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.iiitd.phonesense.datasense.BatteryState;
import com.iiitd.phonesense.datasense.BluetoothState;
import com.iiitd.phonesense.datasense.GSMState;
import com.iiitd.phonesense.datasense.LocationState;
import com.iiitd.phonesense.datasense.WifiState;


public class AlarmHandler extends BroadcastReceiver{

	@Override
	public void onReceive(Context CONTEXT, Intent INTENT) {
        
        int ALARM_CODE = INTENT.getExtras().getInt("ALARM_CODE");
        
        Intent CollectData = null;
        Log.d("phonesense","Alarm with code "+ALARM_CODE+" fired");
        switch(ALARM_CODE){
        		
        	case SetAlarms.BATTERY_ALARM:
        		CollectData = new Intent(CONTEXT, BatteryState.class);
        		break;
        		
        	case SetAlarms.BLUETOOTH_ALARM: 
        		CollectData = new Intent(CONTEXT, BluetoothState.class);
        		break;
        		
        	case SetAlarms.GSM_ALARM:
        		CollectData = new Intent(CONTEXT, GSMState.class);
        		break;
        		
        	case SetAlarms.LOCATION_ALARM:
        		CollectData = new Intent(CONTEXT, LocationState.class);
        		break;
        		
        	case SetAlarms.WIFI_ALARM:
        		CollectData = new Intent(CONTEXT, WifiState.class);
        		break;
        		
        	case SetAlarms.UPLOAD_ALARM:
        		CollectData = new Intent(CONTEXT, UploadService.class);
        		break;
        }
        
        CONTEXT.startService(CollectData);
	}

}
