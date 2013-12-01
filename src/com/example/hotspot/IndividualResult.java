package com.example.hotspot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class IndividualResult extends Activity 
{
	private Company individualCompany;
	public IndividualResult(Company company)
	{
		individualCompany = company;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.individualized_results);
		TextView companyNameView = (TextView) findViewById(R.id.company_name);
		String companyName = individualCompany.getName();
		companyNameView.setText(companyName.subSequence(0,companyName.length()));
		ImageButton findButton;
		findButton = (ImageButton) findViewById(R.id.touch_to_start);
		findButton.setClickable(true);
		findButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Class resultsActivityClass = null;
				Intent openResultsActivity = null;
				try
				{
					resultsActivityClass = Class.forName("com.example.hotspot.ResultsActivity");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				openResultsActivity = new Intent(IndividualResult.this, resultsActivityClass);
				startActivity(openResultsActivity);
			}
		});
	}
	public void setCompany(Company company)
	{
		individualCompany = company;
	}
	public Company getCompany()
	{
		return individualCompany;
	}
}
