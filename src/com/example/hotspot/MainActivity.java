package com.example.hotspot;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.yelp.parcelgen.*;

import java.util.ArrayList;

public class MainActivity extends Activity {
	
	public static final int DIALOG_PROGRESS = 42;

	private Yelp mYelp;
	private API_Static_Stuff staticStuff;
	private double latitude;
	private double longitude;
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        staticStuff = new API_Static_Stuff();
        //setContentView(R.layout.main);
        mYelp = new Yelp(staticStuff.getYelpConsumerKey(), staticStuff.getYelpConsumerSecret(),
        		staticStuff.getYelpToken(), staticStuff.getYelpTokenSecret());
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		latitude = lastKnownLocation.getLatitude();
		longitude = lastKnownLocation.getLongitude();
    }
    
    public void search(View v) {
    	String terms = "restaurant";
    	Double newLatitude = new Double(latitude);
    	Double newLongitude = new Double(longitude);
    	String latitudeString = newLatitude.toString();
    	String longitudeString = newLongitude.toString();
    	new AsyncTask<String, Void, ArrayList<Business>>() {

			@Override
			protected ArrayList<Business> doInBackground(String... params) {
				Double latitudeValue = new Double(params[1]);
				double latitudeVal = latitudeValue.doubleValue();
				Double longitudeValue = new Double(params[2]);
				double longitudeVal = longitudeValue.doubleValue();
				String result = mYelp.search(params[0], latitudeVal, longitudeVal);
				try {
					JSONObject response = new JSONObject(result);
					if (response.has("businesses")) {
						return JsonUtil.parseJsonList(response.getJSONArray("businesses"), Business.CREATOR);
					}
				} catch (JSONException e) {
					return null;
				}
				return null;
			}
		
			@Override
			protected void onPostExecute(ArrayList<Business> businesses) {
				onSuccess(businesses);
			}
    		
    	}.execute(terms, latitudeString, longitudeString);
    }
    
    public void onSuccess(ArrayList<Business> businesses) {
    	// Launch BusinessesActivity with an intent that includes the received businesses
		if (businesses != null) {
			Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
			intent.putParcelableArrayListExtra(BusinessesActivity.EXTRA_BUSINESSES, businesses);
			startActivity(intent);
		} else {
			Toast.makeText(this, "An error occured during search", Toast.LENGTH_LONG).show();
		}
    }
    
    @Override
    public Dialog onCreateDialog(int id) {
    	if (id == DIALOG_PROGRESS) {
    		ProgressDialog dialog = new ProgressDialog(this);
    		dialog.setMessage("Loading...");
    		return dialog;
    	} else {
    		return null;
    	}
    }
    public void goToSearchIdeas(View view)
    {
		startActivity(new Intent(getApplicationContext(), SearchIdeas.class));
    }
}