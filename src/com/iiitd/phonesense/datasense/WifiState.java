package com.iiitd.phonesense.datasense;

import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.IBinder;
import android.util.Log;

import com.iiitd.phonesense.util.DataHandler;


public class WifiState extends Service
{
    private static WifiManager wifi;
	private static WifiLock wifiLock;
	private boolean OrigWifiState;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.d("phonesense","Wifi Service Started");

    	wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiLock = wifi.createWifiLock("phonesense");
        OrigWifiState = wifi.isWifiEnabled();
        
        if( OrigWifiState != true ){
        	wifi.setWifiEnabled(true);
        }

        registerReceiver(WiFiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifiLock.acquire();
        wifi.startScan();
          
	    return START_NOT_STICKY;
	}
	
	BroadcastReceiver WiFiScanReceiver = new BroadcastReceiver() {        
  		
		@Override
  		public void onReceive(Context c, Intent intent){
  		 
			try
  		  	{
  			  List<ScanResult> results = wifi.getScanResults();   
  			  Log.d("phonesense","Scan Results are Available");
  			  long SysTime = System.currentTimeMillis( );
  			  
  			  for (ScanResult result : results) {
  				  
  				  //No Need to put '\n' at the end. Data handler will take care of that
  				  String WifiRecord = SysTime + "," +
  				                      result.BSSID + "," +
  				                      result.SSID;
  				  
  				  DataHandler.Log(DataHandler.WIFI_STATE, WifiRecord, getApplicationContext());
  	          	  Log.d("phonesense","In Scan receiver, added access point  : " + result.SSID);
  			  }
  			  
  			  unregisterReceiver(WiFiScanReceiver);
  			  if( OrigWifiState == false ) wifi.setWifiEnabled(false);
  			  
  			  wifiLock.release();
  		  }
  		  catch (Exception e)
  		  {
  			 Log.d("WifiState","Error in Wifi scan receiver : " + e.getMessage());
  		  }	    
  	   } 	
    };
    
	
    public void onDestroy() {
		super.onDestroy();
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
