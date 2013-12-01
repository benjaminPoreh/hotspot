package com.example.hotspot;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yelp.parcelgen.Business;
import com.yelp.parcelgen.BusinessesActivity;
import com.yelp.parcelgen.JsonUtil;

public class ResultsActivity extends Activity {
	public static final String EXTRA_BUSINESSES = "businesses";
	public static final int DIALOG_PROGRESS = 42;

	private Yelp mYelp;
	private double latitude;
	private double longitude;
	private ArrayList<Business> Businesses;
	private BusinessArray bizArray;
	private DrawableManager drawableManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_main);
		drawableManager = new DrawableManager();
		// company stuff
		Businesses = getIntent().getParcelableArrayListExtra(EXTRA_BUSINESSES);
		bizArray = new BusinessArray(Businesses);
		generateOptions();
		
	}
	private void generateOptions() {
		ImageView[] images = new ImageView[3];
		images[0] = (ImageView) findViewById(R.id.logo_1);
		images[1] = (ImageView) findViewById(R.id.logo_2);
		images[2] = (ImageView) findViewById(R.id.logo_3);
		TextView[] names = new TextView[3];
		names[0] = (TextView) findViewById(R.id.company_name_1);
		names[1] = (TextView) findViewById(R.id.company_name_2);
		names[2] = (TextView) findViewById(R.id.company_name_3);
		ImageButton[] ratings = new ImageButton[3];
		ratings[0] = (ImageButton) findViewById(R.id.rating_image_1);
		ratings[1] = (ImageButton) findViewById(R.id.rating_image_2);
		ratings[2] = (ImageButton) findViewById(R.id.rating_image_3);
		Business[] businessesRandArray = bizArray.threeRandomCompanies();
		int size = businessesRandArray.length;
		for (int x = 0; x < size; x++) {
			drawableManager.fetchDrawableOnThread(businessesRandArray[x].getImageUrl(),images[x]);
			drawableManager.fetchDrawableOnThread(businessesRandArray[x].getRatingImageUrl(),ratings[x]);
			//images[x].setImageDrawable(logoImage);
			//ratings[x].setImageDrawable(ratingImage);
			names[x].setText(businessesRandArray[x].getName().subSequence(0,businessesRandArray[x].getName().length()));// for company
		}
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
			Intent intent = new Intent(ResultsActivity.this, ResultsActivity.class);
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
}

