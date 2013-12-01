package com.example.hotspot;

import android.graphics.drawable.Drawable;

public class Company {
	private String name;
	private String Attire;
	private Drawable logo;
	private String type;
	private int numStars;// how good it is out of 5
	private int expenseLevel;// how expensive is the store out of 5
	private int logo_id;

	public Company(String name, String type) {
		this.name = name;
		this.type = type;
		Attire = null;
		logo = null;
		numStars = 0;
		expenseLevel = 0;
		logo_id = 0;
	}

	public Company(String name, String Attire, String type) {
		this.name = name;
		this.type = type;
		this.Attire = Attire;
		logo = null;
		numStars = 0;
		expenseLevel = 0;
		logo_id = 0;
	}

	public Company(String name, String Attire, String type, int numStars) {
		this.name = name;
		this.type = type;
		this.Attire = Attire;
		logo = null;
		this.numStars = numStars;
		expenseLevel = 0;
		logo_id = 0;
	}

	public Company(String name, String Attire, String type, int numStars,
			int expenseLevel) {
		this.name = name;
		this.type = type;
		this.Attire = Attire;
		logo = null;
		this.numStars = numStars;
		this.expenseLevel = expenseLevel;
		logo_id = 0;
	}

	public Company(String name, String Attire, String type, int numStars,
			int expenseLevel, Drawable logo) {
		this.name = name;
		this.type = type;
		this.Attire = Attire;
		this.logo = logo;
		this.numStars = numStars;
		this.expenseLevel = expenseLevel;
		logo_id = 0;
	}

	public Company(String name, String Attire, String type, int numStars,
			int expenseLevel, int logo_id) {
		this.name = name;
		this.type = type;
		this.Attire = Attire;
		this.logo = null;
		this.numStars = numStars;
		this.expenseLevel = expenseLevel;
		this.logo_id = logo_id;
	}

	public String getName() {
		return name;
	}

	public String getAttire() {
		return Attire;
	}

	public Drawable getLogo() {
		return logo;
	}

	public int getNumStars() {
		return numStars;
	}

	public int getExpenseLevel() {
		return expenseLevel;
	}

	public String getType() {
		return type;
	}

	public int getLogoId() {
		return logo_id;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setAttire(String newAttire) {
		Attire = newAttire;
	}

	public void setLogo(Drawable newLogo) {
		logo = newLogo;
	}

	public void setNumStars(int numStars) {
		this.numStars = numStars;
	}

	public void setExpenseLevel(int expenseLevel) {
		this.expenseLevel = expenseLevel;
	}

	public void setType(String newType) {
		type = newType;
	}

	public void setLogoId(int logo_id) {
		this.logo_id = logo_id;
	}

	public String toString() {
		return name;
	}
	/*
	 * import java.util.HashMap;


} 


	 */
}
