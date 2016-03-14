package com.ud.mdb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utlity {

	public static boolean isOnline(Context ctx) {
		
			ConnectivityManager CM = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = CM.getActiveNetworkInfo();
			if(netInfo!=null && netInfo.isConnectedOrConnecting()){
				return true;
			}
			return false;
		}
	

}
