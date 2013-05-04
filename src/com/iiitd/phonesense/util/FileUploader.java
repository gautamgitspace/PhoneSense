package com.iiitd.phonesense.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.iiitd.phonesense.PhoneSense;

import android.util.Log;


public class FileUploader {
	
	private static String CONN_URL = PhoneSense.SERVER_ADDRESS + "/upload";
	
	public static String ContactServer( String FilePath , String DEVICE_ID , String Key )
	{
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000000);
		HttpConnectionParams.setSoTimeout(httpParameters, 100000000);
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(CONN_URL+"?deviceid="+DEVICE_ID);

		MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		FileBody bin = new FileBody( new File(FilePath) );
		reqEntity.addPart(Key, bin);
		
		httppost.setEntity(reqEntity);
		HttpResponse response = null;
		String Data = "";
		try
		{
			response = httpclient.execute(httppost);
			//AppLog.logger(EntityUtils.toString(response.getEntity()));
			BufferedReader BuffRead = new BufferedReader( new InputStreamReader(response.getEntity().getContent(),"UTF-8") );
			
			Log.d("phonesense","Response from server!");
			
			Data = BuffRead.readLine();
		}
		catch(Exception error){ return null; }
		
		return Data;
	}
}