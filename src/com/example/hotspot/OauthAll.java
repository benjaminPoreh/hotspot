package com.example.hotspot;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.builder.api.*;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import meetup.*;
public class OauthAll {
	//private List<Event> events;
	//this will work for all of the api s
	public OauthAll()
	{
		/*ClientSettings settings = new ClientSettings();
		settings.setMeetupKey("bve6a5sg6tllb8itckm88j8vpd");   

		Client c = new Client();
		c.setClientSettings(settings);

		EventSearchCriteria criteria = new EventSearchCriteria();
		criteria.setZipCode("44106");
		criteria.setRadiusInMiles(2);

		events = c.getEvents(criteria);
	}
	public List<Event> getEvents()
	{
		return events;
	}*/
	}
	public String getInternetData() throws Exception
	{
		BufferedReader in = null;
		String data = null;
		try{
			HttpClient client = new DefaultHttpClient();
			URI website = new URI("http://www.mybringback.com");
			HttpGet request = new HttpGet();
			request.setURI(website);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String l = "";
			String nl = System.getProperty("line.separator");
			while((l = in.readLine())!= null)
			{
				sb.append(l + nl);
			}
			in.close();
			data = sb.toString();
			return data;
		}
		finally
		{
			if(in != null)
			{
				try
				{
					in.close();
					return data;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}