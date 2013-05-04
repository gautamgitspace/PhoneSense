package com.iiitd.phonesense.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class DataHandler {
	
	public static final int PHONE_STATE = 1;
	public static final int BATTERY_STATE = 2;
	public static final int WIFI_STATE = 3;
	public static final int BT_STATE = 4;
	public static final int GSM_STATE = 5;
	public static final int LOCATION_STATE = 6;
	// .... ADD More Codes for Different Datatypes
	
	
	public static final String DATA_DIR = "PhoneSense";
	private static BufferedWriter DATA_WRITER;

	public static void Log( int DATA_TYPE, String Data, Context CONTEXT ){
		
		InitializeStorageMedia();
		
	    String ID = (new SystemInfo(CONTEXT)).getIMEI();
		
		String FILE_NAME = null;
		switch(DATA_TYPE){
			
			case 1: FILE_NAME = "PhoneState_"+ID+".pst";
								break;
			case 2: FILE_NAME = "BatteryState_"+ID+".bst";
								break;
			case 3: FILE_NAME = "WifiState_"+ID+".wst";
								break;
			case 4: FILE_NAME = "BTState_"+ID+".btst";
								break;
			case 5: FILE_NAME = "GSMState_"+ID+".gst";
								break;
			case 6: FILE_NAME = "LocationState_"+ID+".lst";
								break;
			// ... Add More cases for different DataTypes
		}
		
		InitilazeFileHandler(FILE_NAME, ID);
		
		WriteToFile(DATA_WRITER, Data);
		
	}
	
	
	private static void InitializeStorageMedia(){
		File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), 
				                        DATA_DIR);
	 
	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("phonesense", "Failed to create Directory !");
	        }
	    }
	}
	
	
	private static void InitilazeFileHandler(String FileName, String ID){
		
		try
		{
			File root = Environment.getExternalStorageDirectory();
			File dir = new File (root.getAbsolutePath() + File.separator + DATA_DIR );
			if( dir.canWrite() )
			{	
				File FileHandle = new File(dir, FileName);
				DATA_WRITER = new BufferedWriter(new FileWriter(FileHandle, true));
			}
		}
		catch( IOException Error){}
		
	}
	
	
	private static void WriteToFile(BufferedWriter DATA_WRIER, String Data){
		try
		{
    		DATA_WRITER.append(Data);
    		DATA_WRITER.newLine();
    		DATA_WRITER.flush();
    		DATA_WRITER.close();
		}
		catch( IOException Error){}
	}
}
