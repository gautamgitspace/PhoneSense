package com.iiitd.phonesense.datasense;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.iiitd.phonesense.util.DataHandler;


public class LocationState extends Service implements LocationListener
{
	IntentFilter ifilter;
	
	LocationManager Locationmanager;
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) 
    {
    	Log.d("phonesense","In GPS Service");
    	
    	Locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	Locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
    	
    	return START_NOT_STICKY;

    }
	
	public void onLocationChanged(Location arg0) {
        String lat = String.valueOf(arg0.getLatitude());
        String lon = String.valueOf(arg0.getLongitude());
        long  timestamp=arg0.getTime();
        Log.d("LocationState", "location changed: lat="+lat+", lon="+lon);
        DataHandler.Log(DataHandler.LOCATION_STATE, lat+","+lon+","+timestamp,getApplicationContext());
        this.stopSelf(START_NOT_STICKY);
    }
	
    public void onProviderDisabled(String arg0) {
        Log.d("LocationState", "provider disabled " + arg0);
    }
    public void onProviderEnabled(String arg0) {
        Log.d("LocationState", "provider enabled " + arg0);
    }
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        Log.d("LocationState", "status changed to " + arg0 + " [" + arg1 + "]");
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
