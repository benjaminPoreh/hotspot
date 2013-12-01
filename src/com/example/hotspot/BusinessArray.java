package com.example.hotspot;

import java.util.ArrayList;
import java.util.Random;

import com.yelp.parcelgen.Business;

public class BusinessArray {
	private ArrayList<Business> Businesses;

	public BusinessArray() 
	{
		Businesses = new ArrayList<Business>();
	}
	public BusinessArray(ArrayList<Business> bizArray) 
	{
		Businesses = bizArray;
	}

	public Business getBusiness(String companyName) {
		for (int x = 0; x < Businesses.size(); x++) {
			if (Businesses.get(x).getName().equals(companyName)) {
				return (Business) Businesses.get(x);
			}
		}
		return null;
	}

	public void addNewBusiness(Business c) {
		Businesses.add(c);
	}

	public Business[] threeRandomCompanies() {
		Business[] companies = new Business[3];
		Random rand = new Random();
		for (int x = 0; x < 3; x++) {
			double random = rand.nextDouble() * Businesses.size();
			companies[x] = Businesses.get((int) random);
		}
		return companies;
	}

	public int getSize() {
		return Businesses.size();
	}
}
