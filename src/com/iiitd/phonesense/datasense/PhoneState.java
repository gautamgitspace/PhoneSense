package com.iiitd.phonesense.datasense;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.iiitd.phonesense.util.DataHandler;


public class PhoneState extends Service
{
	TelephonyManager TelephonyHandler;
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
		Log.d("phonesense","In PhoneState Service");
		
    	TelephonyHandler = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    	TelephonyHandler.listen( PhoneStateHandler, PhoneStateListener.LISTEN_CALL_STATE);
    	
    	return START_NOT_STICKY;
    }
    
    public void onDestroy() {
		super.onDestroy();
		TelephonyHandler.listen( PhoneStateHandler, PhoneStateListener.LISTEN_NONE);
	}
	
	private PhoneStateListener PhoneStateHandler = new PhoneStateListener() {

	    @Override
	    public void onCallStateChanged(int state, String incomingNumber) {
	        super.onCallStateChanged(state, incomingNumber);
	        
	        if(incomingNumber.equalsIgnoreCase(""))
    			incomingNumber="NULL";
	        
	        switch (state) {
	        case TelephonyManager.CALL_STATE_OFFHOOK:
	        	   	DataHandler.Log(DataHandler.PHONE_STATE, System.currentTimeMillis()+",OFFHOOK_"+incomingNumber, getApplicationContext());
	                break;
	                
	        case TelephonyManager.CALL_STATE_RINGING:
	        		DataHandler.Log(DataHandler.PHONE_STATE, System.currentTimeMillis()+",RINGING_"+incomingNumber, getApplicationContext());
	                break;
	                
	        case TelephonyManager.CALL_STATE_IDLE:
	        		DataHandler.Log(DataHandler.PHONE_STATE, System.currentTimeMillis()+",IDLE_"+incomingNumber, getApplicationContext());
	                break;
	        }
	    }

	};

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
