package com.iiitd.phonesense.datasense;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;

import com.iiitd.phonesense.util.DataHandler;


public class BatteryState extends Service
{
	IntentFilter ifilter;
	Intent batteryStatus;
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
    	super.onCreate();
        //tv = new TextView(this);
    	ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    	batteryStatus = this.registerReceiver(null, ifilter);
    
    	
    	//Intent batteryStatus;
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    	int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    	int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
    	float batteryPct = level / (float)scale;
    	DataHandler.Log(DataHandler.BATTERY_STATE, level+","+scale+","+chargePlug+","+batteryPct+","+System.currentTimeMillis(), getApplicationContext());
    	//Log.d("Result",""+level+"//"+chargePlug);
    	
    	return START_NOT_STICKY;
    }
    
    public void onDestroy() {
		super.onDestroy();
		
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
