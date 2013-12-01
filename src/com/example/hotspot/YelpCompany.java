package com.example.hotspot;

import java.util.HashMap;

import android.graphics.drawable.Drawable;

public class YelpCompany 
{
	private String id, name, image_url, url, mobile_url, phone, display_phone, rating_img_url, rating_img_url_small, rating_img_url_large, snippet_text, snippet_image_url, menu_provider,menu_date_updated;
	private boolean is_claimed, is_closed;
	private int review_count, distance, rating;
	private HashMap<String,String> categories;
	
	public YelpCompany() 
	{
		id = null;
		is_claimed = false;
		is_closed = false;
		name =null;
		image_url = null;
		url = null;
		mobile_url = null;
		phone = null;
		display_phone = null;
		rating_img_url=null;
		rating_img_url_small=null;
		rating_img_url_large=null;
		snippet_text=null;
		snippet_image_url=null;
		review_count = 0;
		distance = 0;
		rating = 0;
		categories = new HashMap<String,String>();
		menu_provider = null;
		menu_date_updated = null;
	}
	public YelpCompany(String companyName, String companyRating_Url, String companyMobile_Url)
	{
		this.id = null;
		name = companyName;
		this.mobile_url = companyMobile_Url;
		rating_img_url=companyRating_Url;
		is_claimed = false;
		is_closed = false;
		url = null;
		this.image_url = null;
		phone = null;
		display_phone = null;
		rating_img_url_small=null;
		rating_img_url_large=null;
		snippet_text=null;
		snippet_image_url=null;
		review_count = 0;
		distance = 0;
		rating = 0;
		categories = new HashMap<String,String>();
		menu_provider = null;
		menu_date_updated = null;
	}
	public String getName() {
		return name;
	}
	public String getImageUrl() {
		return image_url;
	}
	public String getMobileUrl() {
		return image_url;
	}
	public boolean isClaimed()
	{
		return is_claimed;
	}
	public boolean isClosed()
	{
		return is_closed;
	}
	public String getUrl()
	{
		return url;
	}
	public String getPhone()
	{
		return phone;
	}
	public String displayPhone()
	{
		return display_phone;
	}
	public String getRatingImageUrlSmall()
	{
		return rating_img_url_small;
	}
	public String getSnippetText()
	{
		return snippet_text;
	}
	public String getSnippetImageUrl()
	{
		return snippet_image_url;
	}
	public String getMenuProvider()
	{
		return menu_provider;
	}
	public String getMenuDateUpdated()
	{
		return menu_date_updated;
	}
	public String getRatingImageUrlLarge()
	{
		return rating_img_url_large;
	}
	
	public String getRatingUrl() {
		return rating_img_url;
	}
	public int getReviewCount() {
		return review_count;
	}
	public HashMap<String,String> getCatagories() {
		return categories;
	}
	public String getId() {
		return id;
	}
	public int getDistance()
	{
		return distance;
	}
}