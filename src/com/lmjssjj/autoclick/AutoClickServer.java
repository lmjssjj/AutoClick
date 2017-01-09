package com.lmjssjj.autoclick;

import android.app.Instrumentation;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AutoClickServer extends Service {

	public static final String KEY_X = "key_x";
	public static final String KEY_Y = "key_y";
	public static final String TIME = "time";

	// private ThreadPoolExecutor tpe = new ThreadPoolExecutor

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		int time = intent.getIntExtra(TIME, 1);
		float x = intent.getFloatExtra(KEY_X, 0);
		float y = intent.getFloatExtra(KEY_Y, 0);

		sendKeyEvent(time, x, y);

		return super.onStartCommand(intent, flags, startId);
	}

	public void sendKeyEvent(final int time, final float x, final float y) {
		new Thread() { // 不可在主线程中调用
			public void run() {
				try {

					for (int i = 0; i < time; i++) {

						Instrumentation inst = new Instrumentation();
						long downTime = SystemClock.uptimeMillis();
						final MotionEvent downEvent = MotionEvent.obtain(
								downTime, downTime, MotionEvent.ACTION_DOWN, x,
								y, 0);
						Log.v("lmjssjj", "downX:"+x);
						Log.v("lmjssjj", "downY:"+y);
						
						inst.sendPointerSync(downEvent);
						downTime += 1;
						final MotionEvent upEvent = MotionEvent.obtain(
								downTime, downTime, MotionEvent.ACTION_UP, x,
								y, 0);
						inst.sendPointerSync(upEvent);
						Log.v("lmjssjj", "upX:"+x);
						Log.v("lmjssjj", "upY:"+y);
						downEvent.recycle();
						upEvent.recycle();
						Log.v("lmjssjj", "time:"+i);
						sleep(2);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();
	}

	public void sendKeyEvent(final int KeyCode) {  
	      new Thread() {     //不可在主线程中调用  
	           public void run() {  
	               try {  
	                   Instrumentation inst = new Instrumentation();  
	                   inst.sendKeyDownUpSync(KeyCode);  
	              } catch (Exception e) {  
	                   e.printStackTrace();  
	               }  
	            }  
	    
	      };
	}
	
	@SuppressWarnings("unused")
	private void setSimulateClick(View view, float x, float y) {
		long downTime = SystemClock.uptimeMillis();
		final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
				MotionEvent.ACTION_DOWN, x, y, 0);
		downTime += 1000;
		final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
				MotionEvent.ACTION_UP, x, y, 0);
		view.onTouchEvent(downEvent);
		view.onTouchEvent(upEvent);
		downEvent.recycle();
		upEvent.recycle();
	}

}
