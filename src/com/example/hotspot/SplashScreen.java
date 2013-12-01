package com.example.hotspot;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import com.example.hotspot.R;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		Thread timer = new Thread() {
			public void run()
			{
				try 
				{
					sleep(1500);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				} 
				finally
				{
					Class mainActivityClass = null;
					try {
						 mainActivityClass = Class.forName("com.example.hotspot.MainActivity");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent openMainActivity = new Intent(SplashScreen.this, mainActivityClass);
					startActivity(openMainActivity);
				}
			}
		};
		timer.start();
	}
}