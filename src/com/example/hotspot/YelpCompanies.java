package com.example.hotspot;

import java.util.Random;
import android.os.Bundle;
import com.yelp.parcelgen.*;

public class YelpCompanies
{
	private YelpCompany[] companies;
	private Bundle bundledCompanies;
	private YelpParser parser;
	public YelpCompanies(YelpParser parser)
	{
		this.parser=parser;
		bundledCompanies = parser.getYelpBundle();
		companies = new YelpCompany[parser.getBudleKeysSize()];
		for(int counter = 0; counter<parser.getBudleKeysSize();counter++)
		{
			String name = parser.getBusinessName(counter);
			String rating_url = parser.getRatingURL(counter);
			String mobile_url = parser.getBusinessMobileURL(counter);
			
			YelpCompany currentCompany = new YelpCompany(name, rating_url, mobile_url);
			companies[counter] = currentCompany;
		}
	}
	public YelpCompany[] getThreeRandomCompanies()
	{
		YelpCompany[] threeRandomCompanies = new YelpCompany[3];
		for(int counter = 0; counter<3;counter++)
		{
			Random random = new Random();
			int integer = (int) (random.nextDouble()*parser.getBudleKeysSize());
			threeRandomCompanies[counter] = companies[integer];
		}
	
		bundledCompanies.get(threeRandomCompanies[0].getName());
		
		return threeRandomCompanies;
	}
}
