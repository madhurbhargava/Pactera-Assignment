package com.pactera;


import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Main List Activity of the application
 * @author 
 *
 */
public class MainActivity extends AppCompatActivity implements ListDataFetchListener
{
	
	ListView list = null;

	/*
	 * (non-Javadoc)
	 * @see android.support.v7.app.AppCompatActivity#onCreate(android.os.Bundle)
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.list);
        if(isNetworkAvailable())
    	{
    		new ListFetcher(this).execute(new Void[]{});
    	}
    	else
    	{
    		Toast.makeText(this, getResources().getString(R.string.error_internet), 
    				Toast.LENGTH_LONG).show();
    	}
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
        	if(isNetworkAvailable())
        	{
        		new ListFetcher(this).execute(new Void[]{});
        	}
        	else
        	{
        		Toast.makeText(this, getResources().getString(R.string.error_internet), 
        				Toast.LENGTH_LONG).show();
        	}
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * (non-Javadoc)
     * @see com.pactera.ListDataFetchListener#onListFetch(com.pactera.FactsData)
     */
	@Override
	public void onListFetch(FactsData listData) {
		ListItemAdapter adapter = new ListItemAdapter(this, listData.getTopics());
        list.setAdapter(adapter);
        
        this.getSupportActionBar().setTitle(listData.getTitle());
	}
	
	/*
	 * Check network connectivity
	 */
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
