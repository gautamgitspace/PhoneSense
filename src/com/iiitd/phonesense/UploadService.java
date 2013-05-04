package com.iiitd.phonesense;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.iiitd.phonesense.util.DataHandler;
import com.iiitd.phonesense.util.FileUploader;
import com.iiitd.phonesense.util.SystemInfo;

public class UploadService extends Service{
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	
		Log.d("phonesense","Upload Service Started");
		
		String DEVICE_ID = (new SystemInfo(getApplicationContext())).getIMEI();
		
		File FileRoot = Environment.getExternalStorageDirectory();
		
		try{
			String FileName = "PhoneState_"+DEVICE_ID+".pst";
			FileUploader.ContactServer(FileRoot.getAbsolutePath()+File.separator+DataHandler.DATA_DIR+File.separator+FileName,
					                   DEVICE_ID, "file" );
		}
		catch(Exception Error){}
		
		try{
			String FileName = "BatteryState_"+DEVICE_ID+".bst";
			FileUploader.ContactServer(FileRoot.getAbsolutePath()+File.separator+DataHandler.DATA_DIR+File.separator+FileName,
					                   DEVICE_ID, "file" );
		}
		catch(Exception Error){}
		
		try{
			String FileName = "WifiState_"+DEVICE_ID+".wst";
			FileUploader.ContactServer(FileRoot.getAbsolutePath()+File.separator+DataHandler.DATA_DIR+File.separator+FileName,
					                   DEVICE_ID, "file" );
		}
		catch(Exception Error){}
		
		try{
			String FileName = "BTState_"+DEVICE_ID+".btst";
			FileUploader.ContactServer(FileRoot.getAbsolutePath()+File.separator+DataHandler.DATA_DIR+File.separator+FileName,
					                   DEVICE_ID, "file" );
		}
		catch(Exception Error){}
		
		try{
			String FileName = "GSMState_"+DEVICE_ID+".gst";
			FileUploader.ContactServer(FileRoot.getAbsolutePath()+File.separator+DataHandler.DATA_DIR+File.separator+FileName,
					                   DEVICE_ID, "file" );
		}
		catch(Exception Error){}
		
		try{
			String FileName = "LocationState_"+DEVICE_ID+".lst";
			FileUploader.ContactServer(FileRoot.getAbsolutePath()+File.separator+DataHandler.DATA_DIR+File.separator+FileName,
					                   DEVICE_ID, "file" );
		}
		catch(Exception Error){}
		
    	return START_NOT_STICKY;
    }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
