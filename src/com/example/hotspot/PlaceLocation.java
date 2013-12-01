package com.example.hotspot;

public class PlaceLocation
{
	private String address;
	private String display_address;
	private String city;
	private int state_code;
	private int postal_code;
	private int country_code;
	private String cross_streets;
	private String neighborhoods;
																																																																																																																																																																																		
	public PlaceLocation()
	{
		address = null;
		display_address = null;
		city = null;
		state_code = 000;
		postal_code = 0000;
		country_code = 00000;
		cross_streets = null;
		neighborhoods = null;
	}
	public void setAddress(String newAddress)
	{
		address = newAddress;
	}
	public void setDisplayAddress(String newAddress)
	{
		display_address = newAddress;
	}
	public void setCity(String newCity)
	{
		city = newCity;
	}
	public void setStateCode(int newStateCode)
	{
		state_code = newStateCode;
	}
	public void newPostalCode(int newPostalCode)
	{
		postal_code = newPostalCode;
	}
	public void setCountry_Code(int newCountryCode)
	{
		country_code = newCountryCode;
	}
	public void setCrossStreets(String newCrossStreets)
	{
		cross_streets = newCrossStreets;
	}
	public void setNeighborhoods(String newNeighborhoods)
	{
		neighborhoods = newNeighborhoods;
	}
	public String getAddress()
	{
		return address;
	}
	public String getDisplayAddress()
	{
		return display_address;
	}
	public String getCity()
	{
		return city;
	}
	public int getStateCode()
	{
		return state_code;
	}
	public int getPostalCode()
	{
		return postal_code;
	}
	public int getCountry_Code()
	{
		return country_code;
	}
	public String getCrossStreets()
	{
		return cross_streets;
	}
	public String setNeighborhoods()
	{
		return neighborhoods;
	}
}
