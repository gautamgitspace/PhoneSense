package com.iiitd.phonesense.datasense;

import java.util.Vector;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.iiitd.phonesense.util.DataHandler;


public class BluetoothState extends Service
{
	IntentFilter ifilter;
	
	public Handler bluetoothCheckHandler = new Handler();
	public Handler bluetoothHandler = new Handler();
	public boolean bluetoothResultsAvailable = false;
	public Vector<String> bluetoothAPs = new Vector<String>();	
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
    	  Log.d("phonesense","In Bluetooth Service");
    	  IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    	   
    	  registerReceiver(ActionFoundReceiver, filter);
    	    
    	  StartBluetoothScan();
    
    	  return START_NOT_STICKY;
    }
	
	
	
	 private void StartBluetoothScan(){
		 
		 	final int CANCEL = 2;
			final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    	if (bluetoothAdapter != null) {
			    
				if( !bluetoothAdapter.isEnabled() )
				{
					while(!bluetoothAdapter.isEnabled());
					
					if( !bluetoothAdapter.isDiscovering() )
							bluetoothAdapter.startDiscovery();
					
					final Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
					  public void run() {
						bluetoothAdapter.cancelDiscovery();
		      			bluetoothAdapter.disable();
					  }
					}, CANCEL*60000);
				}
				
				//If Bluetooth is Enabled(by User), don't disable after scan!
				else{
					if( !bluetoothAdapter.isDiscovering() )
							bluetoothAdapter.startDiscovery();
					
					final Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
					  public void run() {
						bluetoothAdapter.cancelDiscovery();
					  }
					},CANCEL*60000);
				}
			}
	 }
	
	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){
	    
	    @Override
	    public void onReceive(Context context, Intent intent) {
	     String action = intent.getAction();
	     Log.d("phonesense","BT device found");
	     if(BluetoothDevice.ACTION_FOUND.equals(action)) {
	       BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	       //out.append("\n  Device: " + device.getName() + ", " + device);
	       
	       String tempvar=device.getName()+","+device.getAddress()+","+System.currentTimeMillis();
	       DataHandler.Log(DataHandler.BT_STATE, tempvar,getApplicationContext());
	       
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
