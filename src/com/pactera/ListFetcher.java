package com.pactera;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.AsyncTask;

/**
 * Async Task to fetch JSON Data as List
 * @author 
 *
 */
public class ListFetcher extends AsyncTask<Void, Void, FactsData> {
	
	
	/*
	 * Read timeout for network operations
	 */
	private static final int TIMEOUT = 5000;
	
	private static final String SERVER_URL = "https://dl.dropboxusercontent.com/u/746330/facts.json";
	
	/*
	 * Will be notified on list fetch  
	 */
	private ListDataFetchListener mListener = null; 
	
	public ListFetcher(ListDataFetchListener listener)
	{
		mListener = listener;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected FactsData doInBackground(Void... params) 
	{
		HttpURLConnection c = null;
	    try 
	    {
	        URL u = new URL(SERVER_URL);
	        c = (HttpURLConnection) u.openConnection();
	        c.setRequestMethod("GET");
	        c.setRequestProperty("Content-length", "0");
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	        c.setConnectTimeout(TIMEOUT);
	        c.setReadTimeout(TIMEOUT);
	        c.connect();
	        int status = c.getResponseCode();
			if(status == 200) 
			{
				try 
				{
					//Read the server response and attempt to parse it as JSON
					Reader reader = new InputStreamReader(c.getInputStream());
					GsonBuilder gsonBuilder = new GsonBuilder();
					Gson gson = gsonBuilder.create();
					FactsData facts = gson.fromJson(reader, FactsData.class);
					c.disconnect();
					return facts;
					
				} 
				catch (Exception ex) 
				{
					
					ex.printStackTrace();
				}
			} 
		} 
	    catch(Exception ex) 
	    {
			ex.printStackTrace();
		}
		return null;
	} 
	
	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(FactsData result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result == null)
		{
			return;
		}
		mListener.onListFetch(result);
	}

}
