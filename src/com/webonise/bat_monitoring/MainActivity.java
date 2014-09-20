package com.webonise.bat_monitoring;

import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity implements WebserviceInterface{
	static final String SENDER_ID = "482695649816";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		registerGCMService();
		initViews();
	}

	private void registerGCMService() {
		AsyncTask<Void, Void, Void> mRegisterTask;
		
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		System.out.println("================"+regId);
		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
			GCMRegistrar.register(this, SENDER_ID);
			System.out.println("=================  IN IF  ===================="+regId);
		} else {
			// Device is already registered on GCM
			System.out.println("=================  IN ELSE  ===================="+regId);
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.		
				System.out.println("=================  IN ELSE  IF ===================="+regId);
				Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
			} else {

				System.out.println("=================  IN IF ELSE  ===================="+regId);
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				
				registerToserver();
				
			}
		}
		
	}

	private void registerToserver() {
		String url ="";
		WebService webService = new WebService(this);
		webService.registerGCMToServer(url, true);		
	}

	private void initViews() {
		
		String url = "";
		/*List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userName", username.trim()));*/
		WebService webService = new WebService(this);
		webService.GETStringRequest(url, this, true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		
	}
	
	public static String requestGETUrl(List<NameValuePair> params, String baseUrl) {
		if ( params != null && !params.isEmpty() ) {

			baseUrl += "?";

			Iterator<NameValuePair> iter = params.iterator();
			while ( iter.hasNext() ) {

				NameValuePair nvp = iter.next();
				baseUrl += nvp.getName();
				baseUrl += "=";
				baseUrl += nvp.getValue();
				if ( iter.hasNext() )
					baseUrl += "&";
			}
		}
		return baseUrl;
	}
}
