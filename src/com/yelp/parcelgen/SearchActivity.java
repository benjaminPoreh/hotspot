package com.yelp.parcelgen;

import com.example.hotspot.*;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends Activity {
	
	public static final int DIALOG_PROGRESS = 42;

	private Yelp mYelp;
	private double latitude;
	private double longitude;
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton logo = (ImageButton) findViewById(R.id.hotspot_button_get_started);
        API_Static_Stuff staticStuff = new API_Static_Stuff();
        //setContentView(R.layout.main);
        mYelp = new Yelp(staticStuff.getYelpConsumerKey(), staticStuff.getYelpConsumerSecret(),
        		staticStuff.getYelpToken(), staticStuff.getYelpTokenSecret());
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		latitude = lastKnownLocation.getLatitude();
		longitude = lastKnownLocation.getLongitude();
		search(logo);
    }
    
    public void search(View v) {
    	String terms = "restaunt";
    	String location = "ll="+latitude+","+longitude;
    	showDialog(DIALOG_PROGRESS);
    	new AsyncTask<String, Void, ArrayList<Business>>() {

			@Override
			protected ArrayList<Business> doInBackground(String... params) {
				String result = mYelp.search(params[0], params[1]);
				try {
					JSONObject response = new JSONObject(result);
					if (response.has("businesses")) {
						return JsonUtil.parseJsonList(
							response.getJSONArray("businesses"), Business.CREATOR);
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
    		
    	}.execute(terms, location);
    }
    
    public void onSuccess(ArrayList<Business> businesses) {
    	// Launch BusinessesActivity with an intent that includes the received businesses
		if (businesses != null) {
			Intent intent = new Intent(SearchActivity.this, ResultsActivity.class);
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
    public double getLatitude()
    {
    	return latitude;
    }
    public double getLongitude()
    {
    	return longitude;
    }
}