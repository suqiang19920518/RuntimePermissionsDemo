package com.example.administrator.runtimepermissionsdemo.utils;

import android.content.Context;
import android.os.PowerManager;

/**
 * 
 *屏幕唤醒工具
 */
public class WakeLockUtil {
	
	private static WakeLockUtil instance;
	PowerManager.WakeLock mWakeLock;
	
	private WakeLockUtil(){
		
	}
	
	public static WakeLockUtil getInstance(){
		if(null == instance){
			instance = new WakeLockUtil();
		}
		return instance;
	}

	@SuppressWarnings("deprecation")
	public void keepCreenWake(Context cx){
		if(mWakeLock == null){
			PowerManager pm = (PowerManager) cx.getSystemService(Context.POWER_SERVICE);
			mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
	                "XYTEST");
			mWakeLock.acquire();	
		}	  
	}
	
	public void cancleCreenWake(){
		if(mWakeLock != null){
		   mWakeLock.release();
		   mWakeLock = null;
		}
	}

}
