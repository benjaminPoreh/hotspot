package com.example.hotspot_01;

import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.hotspot.R;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openMainActivity = new Intent(
							"com.example.hotspot_01.MAINACTIVITY");
					startActivity(openMainActivity);
				}
			}
		};
		timer.start();
	}
}
