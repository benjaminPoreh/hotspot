package com.example.hotspot_01;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.hotspot.R;

public class ResultsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_main);
		// company stuff
		CompanyArray companies = new CompanyArray();
		Company[] randCompanies = new Company[3];
		populateCompanyArray pca = new populateCompanyArray(companies);
		randCompanies = companies.threeRandomCompanies(companies.getSize());
		String a = randCompanies[0].toString();
		String b = randCompanies[1].toString();
		String c = randCompanies[2].toString();
		generateOptions(randCompanies);
		// end of company stuff
		ImageButton findButton;
		findButton = (ImageButton) findViewById(R.id.touch_to_start);
		findButton.setClickable(true);
		findButton.setOnClickListener(new View.OnClickListener() {
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
				Intent openResultsActivity = new Intent(ResultsActivity.this,
						myClass);
				startActivity(openResultsActivity);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void generateOptions(Company[] currentComps) {
		ImageView[] images = new ImageView[3];
		images[0] = (ImageView) findViewById(R.id.logo_1);
		images[1] = (ImageView) findViewById(R.id.logo_2);
		images[2] = (ImageView) findViewById(R.id.logo_3);
		TextView[] names = new TextView[3];
		names[0] = (TextView) findViewById(R.id.company_name_1);
		names[1] = (TextView) findViewById(R.id.company_name_2);
		names[2] = (TextView) findViewById(R.id.company_name_3);
		TextView[] attires = new TextView[3];
		attires[0] = (TextView) findViewById(R.id.attire_b_1);
		attires[1] = (TextView) findViewById(R.id.attire_b_2);
		attires[2] = (TextView) findViewById(R.id.attire_b_3);
		TextView[] prices = new TextView[3];
		prices[0] = (TextView) findViewById(R.id.price_b_1);
		prices[1] = (TextView) findViewById(R.id.price_b_2);
		prices[2] = (TextView) findViewById(R.id.price_b_3);
		for (int x = 0; x < currentComps.length; x++) {
			images[x].setImageResource(currentComps[x].getLogoId());
			generateTextView(currentComps[x].getName(), names[x]);// for company
																	// names
			generateTextView(currentComps[x].getAttire(), attires[x]);
			addDollarSigns(currentComps[x].getExpenseLevel(), prices[x]);
		}
	}

	private void generateTextView(String name, TextView view) {
		CharSequence newName = name.subSequence(0, name.length());
		view.setText(newName);
	}

	private void addDollarSigns(int num, TextView view) {
		String name;
		switch (num) {
		case 1:
			name = "$";
			view.setText(name.subSequence(0, name.length()));
			break;
		case 2:
			name = "$$";
			view.setText(name.subSequence(0, name.length()));
			break;
		case 3:
			name = "$$$";
			view.setText(name.subSequence(0, name.length()));
			break;
		case 4:
			name = "$$$$";
			view.setText(name.subSequence(0, name.length()));
			break;
		case 5:
			name = "$$$$$";
			view.setText(name.subSequence(0, name.length()));
			break;
		default:
			break;
		}

	}
}
