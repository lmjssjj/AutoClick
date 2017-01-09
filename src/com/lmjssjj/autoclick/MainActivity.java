package com.lmjssjj.autoclick;

import com.example.autoclick.R;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void press(View v){
		Intent intent = new Intent(this,AutoClickServer.class);
		intent.putExtra(AutoClickServer.KEY_X, 360f);
		intent.putExtra(AutoClickServer.KEY_Y, 360f);
		intent.putExtra(AutoClickServer.TIME, 100);
		startService(intent);
		moveTaskToBack(false);
	}

	public void clickeMe(View view) {
		Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show();
	}

	public void simulate(View view) {
		//setSimulateClick(button, 160, 100);
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
