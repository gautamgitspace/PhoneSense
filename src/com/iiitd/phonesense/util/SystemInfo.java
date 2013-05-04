package com.iiitd.phonesense.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class SystemInfo {
	
	private Context SystemContext;
	
	public SystemInfo(Context CONTEXT){
		this.SystemContext = CONTEXT;
	}

	public String getIMEI(){
		TelephonyManager TM = (TelephonyManager) SystemContext.getSystemService(Context.TELEPHONY_SERVICE);
		return TM.getDeviceId();
	}
	
	public String getEmail(){
		AccountManager accountManager = AccountManager.get(SystemContext); 
	    Account account = getAccount(accountManager);

	    if (account == null) {
	      return null;
	    } else {
	      return account.name;
	    }
	}
	
	public String getCellNumber() {
		TelephonyManager TM = (TelephonyManager) SystemContext.getSystemService(Context.TELEPHONY_SERVICE);
		return TM.getLine1Number();
	}
	
	public String getWifiMAC(){
		WifiManager WM = (WifiManager) SystemContext.getSystemService(Context.WIFI_SERVICE);
		return WM.getConnectionInfo().getMacAddress();
	}
	
	public String getBtMAC(){
		BluetoothAdapter btAdapt = BluetoothAdapter.getDefaultAdapter();
    	return btAdapt.getAddress();
	}
	
	private static Account getAccount(AccountManager accountManager) {
	    Account[] accounts = accountManager.getAccountsByType("com.google");
	    Account account;
	    if (accounts.length > 0) {
	      account = accounts[0];      
	    } else {
	      account = null;
	    }
	    return account;
	  }
	
}
