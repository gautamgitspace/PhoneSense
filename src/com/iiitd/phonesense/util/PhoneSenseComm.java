package com.iiitd.phonesense.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.iiitd.phonesense.PhoneSense;

import android.content.Context;

public class PhoneSenseComm {
	
	public static String HandShake(Context CONTEXT){
		
		String SERVER_URL = PhoneSense.SERVER_ADDRESS + "/handshake";
		
		HttpParams httpParameters = new BasicHttpParams();
	    
	    int timeoutConnection = 3000;
	    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
	    int timeoutSocket = 3000;
	    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	    
	    HttpClient Request = new DefaultHttpClient();
	    
	    HttpPost POST = new HttpPost(SERVER_URL);
	    
	    SystemInfo INFO = new SystemInfo(CONTEXT);
        List<NameValuePair> RequestParams = new ArrayList<NameValuePair>();
        RequestParams.add(new BasicNameValuePair("deviceid", INFO.getIMEI()));
        RequestParams.add(new BasicNameValuePair("email", INFO.getEmail()));
        RequestParams.add(new BasicNameValuePair("cellnumber", INFO.getCellNumber()));
        RequestParams.add(new BasicNameValuePair("wifimac", INFO.getWifiMAC()));
        RequestParams.add(new BasicNameValuePair("btmac", INFO.getBtMAC()));
        try
        {
        	POST.setEntity(new UrlEncodedFormEntity(RequestParams));
        }
        catch (Exception Error){}
        
        String Result = null;
	    try{
	    	HttpResponse Response = Request.execute(POST);
	    	HttpEntity Entity = Response.getEntity();
            Result = EntityUtils.toString(Entity);
	    }
	    catch(Exception Error){}
	    
	    return Result;
		
	}
}
