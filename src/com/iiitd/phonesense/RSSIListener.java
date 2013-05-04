package com.iiitd.phonesense;


import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

public class RSSIListener extends PhoneStateListener 
{
	public static int RSSI = -1;
        public static String cellID="", lac="";
        Thread signallog, idlog;
	TelephonyManager telephonyManager;
	

	public RSSIListener (TelephonyManager telephonyManager) 
	{
		super();
		this.telephonyManager = telephonyManager;
	
	}

	/* Get the Signal strength from the provider, each time there is an update */
	@Override
	public void onSignalStrengthsChanged(SignalStrength signalStrength)
	{
		super.onSignalStrengthsChanged(signalStrength);
		RSSI = signalStrength.getGsmSignalStrength();
		
		
        idlog.start();
        try {
              idlog.join();
         } catch (InterruptedException ignore) {
        }
	} 

	@Override
	public void onCellLocationChanged(CellLocation location)
	{
		super.onCellLocationChanged(location);
		GsmCellLocation cellLocation = (GsmCellLocation) location;
		cellID = String.valueOf(cellLocation.getCid());
		lac = String.valueOf(cellLocation.getLac());
              
                idlog.start();
                try {
                     idlog.join();
                 } catch (InterruptedException ignore) {
                 }
	}

   
	
        
        
};
