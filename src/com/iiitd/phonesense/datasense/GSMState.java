package com.iiitd.phonesense.datasense;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.IBinder;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.iiitd.phonesense.RSSIListener;
import com.iiitd.phonesense.util.DataHandler;


public class GSMState extends Service
{
	IntentFilter ifilter;
	
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
		Log.d("phonesense","In GSM Service");
    	
    	try
		{			
					
			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			GsmCellLocation cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();        	
	    
			String networkOperator = telephonyManager.getNetworkOperator();
			String mcc = networkOperator.substring(0, 3);
			String mnc = networkOperator.substring(3);
   		
			int cellID = cellLocation.getCid();
			int lac = cellLocation.getLac();
			//mcc,mnc,cellid,lac,networktype,timestamp
			String GSMresult; 
			GSMresult="";

			List<NeighboringCellInfo> NeighboringList = telephonyManager.getNeighboringCellInfo();           
    	
			String neighCellIDs = "[";
			for(int i=0; i < NeighboringList.size(); i++)
			{
				neighCellIDs = neighCellIDs + NeighboringList.get(i).getLac() + "," + NeighboringList.get(i).getCid() + ";";        		
			}
			neighCellIDs = neighCellIDs +"]";
			
			//mcc,mnc,cellid,lac,networktype,networkstatus,neighids,rssi,timestamp
			ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			State mobile = conMan.getNetworkInfo(0).getState();
			GSMresult=mcc+","+mnc+","+cellID+","+lac+","+String.valueOf(telephonyManager.getNetworkType())+","+mobile.toString()+","+neighCellIDs+","+RSSIListener.RSSI+","+System.currentTimeMillis();
			DataHandler.Log(DataHandler.GSM_STATE, GSMresult,getApplicationContext());
    	
		}
		catch (Exception e)
		{
			Log.d("GSMState","Exception caught while fetching GSM data : " + e.getMessage());
		}
       
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
