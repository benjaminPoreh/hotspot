package com.example.hotspot_01;

//import com.google.android.gms.location.LocationClient;

import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hotspot.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// LocationClient mLocationClient = new LocationClient(this, this,
		// this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView logo;
		logo = (ImageView) findViewById(R.id.touch_to_start);
		logo.setClickable(true);
		YelpParser parser = new YelpParser();
		API_Static_Stuff stuff = new API_Static_Stuff();
		String consumerKey = "z7ZzlA78ZY_rqzlcBoWUtw";
		String consumerSecret = "tqloDVM2LcMnWShi-cyMjZvVoeg";
		String token = "s2zRVsNYRrxZixj8HemJR4VOBFgFEe0N";
		String tokenSecret = "FwMrrVDueDvt6DOwlUMq5f2gwb0";
		Yelp yelp = new Yelp(consumerKey,consumerSecret,token,tokenSecret);
		parser.setResponse(yelp.search("food", 41.5074, -81.6053));
		TextView text = (TextView) findViewById(R.id.text1);
		text.setText(parser.getBusinessName(0));
		//text.setText(parser.getBudleKeysSize());
		logo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Class myClass = null;
				try {
					myClass = Class
							.forName("com.example.hotspot_01.ResultsActivity");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent openResultsActivity = new Intent(MainActivity.this,
						myClass);
				startActivity(openResultsActivity);
			}
		});
	}
}
