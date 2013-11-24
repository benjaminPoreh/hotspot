package com.example.hotspot_01;

import java.util.ArrayList;
import java.util.Random;

public class CompanyArray {
	private ArrayList<Company> Companies;

	public CompanyArray() {
		Companies = new ArrayList<Company>();
	}

	public Company getCompany(String companyName) {
		for (int x = 0; x < Companies.size(); x++) {
			if (Companies.get(x).getName().equals(companyName)) {
				return (Company) Companies.get(x);
			}
		}
		return null;
	}

	public void addNewCompany(Company c) {
		Companies.add(c);
	}

	public Company[] threeRandomCompanies(int numCompanies) {
		Company[] companies = new Company[3];
		Random rand = new Random();
		for (int x = 0; x < 3; x++) {
			double random = rand.nextDouble() * numCompanies;
			companies[x] = Companies.get((int) random);
			System.out.println(random);
		}
		return companies;
	}

	public int getSize() {
		return Companies.size();
	}
}
