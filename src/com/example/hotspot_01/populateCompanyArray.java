package com.example.hotspot_01;

import android.graphics.drawable.Drawable;
import com.example.hotspot.R;

public class populateCompanyArray {
	private CompanyArray companyArray;

	public populateCompanyArray(CompanyArray ca) {
		companyArray = ca;
		run();
	}

	private void run() {
		Company chipotle = new Company("Chipotle", "casual", "Fast Food Chain",
				3, 2, R.drawable.chipotle_logo);
		Company bob_evans = new Company("Bob Evans", "casual",
				"Sit Down Chain", 3, 2, R.drawable.bob_evans_logo);
		Company dennys = new Company("Denny's", "very casual", "Diner Chain", 3,
				2, R.drawable.dennys_logo);
		Company starbucks = new Company("Starbucks", "very casual",
				"Coffee Shop", 2, 2, R.drawable.starbucks_logo);
		Company browns = new Company("Cleveland Browns", "very casual",
				"Sports Game", 4, 2, R.drawable.browns_game_logo);
		Company indians = new Company("Cleveland Indians", "very casual",
				"Sports Game", 4, 4, R.drawable.indians_game_logo);
		Company cavs = new Company("Cleveland Cavaliers", "very casual",
				"Sports Game", 1, 4, R.drawable.cavs_game_logo);
		Company cpk = new Company("California Pizza Kitchen", "semi casual",
				"Pizza Restaurant", 3, 5, R.drawable.cpk_logo);
		Company burger_king = new Company("Burger King", "casual",
				"Fast Food Restaurant", 2, 1, R.drawable.burger_king_logo);
		Company donatos = new Company("Donatos Pizza", "casual",
				"Pizza Restaurant", 3, 2, R.drawable.donatos_pizza_logo);
		Company dominos = new Company("Domino's Pizza", "casual",
				"Pizza Restaurant", 3, 2, R.drawable.dominos_pizza_logo);
		Company long_john_silvers = new Company("Long John Silvers", "casual",
				"Sea Food Restaurant", 2, 1, R.drawable.long_john_silvers_logo);
		Company kfc = new Company("KFC", "casual", "Fast Food Restaurant", 2,
				1, R.drawable.kfc_logo);
		Company papa_johns = new Company("Papa Johns", "casual",
				"Pizza Restaurant", 3, 2, R.drawable.papa_johns_pizza_logo);
		Company pizza_hot = new Company("Pizza Hut", "casual",
				"Pizza Restaurant", 3, 2, R.drawable.pizza_hot_logo);
		Company taco_bell = new Company("Taco Bell", "casual",
				"Fast Food Restaurant", 2, 1, R.drawable.taco_bell_logo);
		Company subway = new Company("Subway", "casual",
				"Fast Food Restaurant", 2, 1, R.drawable.subway_logo);
		Company qdoba = new Company("Qdoba", "casual", "Fast Food Restaurant",
				3, 2, R.drawable.qdoba_logo);
		Company cicis_pizza = new Company("Cici's Pizza", "casual",
				"Pizza Restaurant", 3, 2, R.drawable.cicis_pizza_logo);

		companyArray.addNewCompany(chipotle);
		companyArray.addNewCompany(bob_evans);
		companyArray.addNewCompany(dennys);
		companyArray.addNewCompany(starbucks);
		companyArray.addNewCompany(browns);
		companyArray.addNewCompany(indians);
		companyArray.addNewCompany(cavs);
		companyArray.addNewCompany(cpk);
		companyArray.addNewCompany(burger_king);
		companyArray.addNewCompany(donatos);
		companyArray.addNewCompany(dominos);
		companyArray.addNewCompany(long_john_silvers);
		companyArray.addNewCompany(kfc);
		companyArray.addNewCompany(papa_johns);
		companyArray.addNewCompany(pizza_hot);
		companyArray.addNewCompany(taco_bell);
		companyArray.addNewCompany(subway);
		companyArray.addNewCompany(qdoba);
		companyArray.addNewCompany(cicis_pizza);
	}

}
