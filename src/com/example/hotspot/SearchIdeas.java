package com.example.hotspot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SearchIdeas extends Activity
{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchable_terms);
		ImageButton[] images = new ImageButton[5];
		images[0] = (ImageButton) findViewById(R.id.coffe_search);
		images[1] = (ImageButton) findViewById(R.id.parks_search);
		images[2] = (ImageButton) findViewById(R.id.party_search);
		images[3] = (ImageButton) findViewById(R.id.restaurant_search);
		images[4] = (ImageButton) findViewById(R.id.sports_search);
		for(ImageButton currentButton: images)
		{
			currentButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Class mainActivityClass = null;
					Intent openResultsActivity = null;
					try
					{
						mainActivityClass = Class.forName("com.example.hotspot.MainActivity");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					openResultsActivity = new Intent(getApplicationContext(), mainActivityClass);
					startActivity(openResultsActivity);
				}
			});
		}
		
	}
	 public void goToMainActivity(View view)
	    {
	    	try{
	    		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	    		startActivity(intent);
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	
}
