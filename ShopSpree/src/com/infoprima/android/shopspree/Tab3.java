package com.infoprima.android.shopspree;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import com.infoprima.android.shopspree.R;

import android.app.Activity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;




public class Tab3 extends Fragment implements OnClickListener {
	private TextView textView;
	private TextView textView1;

	//default to localhost (http://10.0.2.2:8000/)
	String mUrl = "http://10.0.2.2:8000/api/product/";	
	String mUser = "padangcuisine@gmail.com";
	String mPassword = "password";

		
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3,container,false);
        
        Button button;
        Button button2;  
        ToggleButton mToggleButton;
        
        textView1 = (TextView) v.findViewById(R.id.textView1);
        textView = (TextView) v.findViewById(R.id.JsontextView);
        mToggleButton = (ToggleButton) v.findViewById(R.id.toggleButton1);
        mToggleButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (((ToggleButton)v).isChecked()) {
		        	String message = "RHCLOUD server...";
		            textView1.setText(message);
		        	mUrl = "https://estore-drindo.rhcloud.com/api/product/";	
		        	mUser = "padangcuisine@gmail.com";
		        	mPassword = "password123";
				} else {
		        	String message = "Localhost server...";
		            textView1.setText(message);
		            
		        	mUrl = "http://10.0.2.2:8000/api/product/";	
		        	mUser = "padangcuisine@gmail.com";
		        	mPassword = "password";
				}
			}
        });

        // Watch for button clicks.

        button2 = (Button) v.findViewById(R.id.button2);
        //button2.setOnClickListener(this);
        button2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		        String message = "JsonString cleared	...";
		        textView.setText(message);
			}
        });

        button = (Button) v.findViewById(R.id.button1);
        //button.setOnClickListener(mClickListener);
        button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		        //String message = "Fetch JSON data from eStore...";	//Works!
		        //textView.setText(message);
		      	//setProgressBarIndeterminateVisibility(true);	

				/*  Works beautifully  */
	        	//textView = (TextView) v.findViewById(R.id.JsontextView);
	        	//String message = "Fetch JSON data from eStore...";
	            //textView.setText(message);
	            
	        	
	        	//new LongRunningGetIO().execute();
	        	GetConnectionTask task = new GetConnectionTask();
	        	try {
					//task.execute(new URL("http://10.0.2.2:8000/api/product/"));
					task.execute(new URL(mUrl));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

				
			}
        });



        
        
        
        return v;
    }

    
    
    @Override
    public void onClick(View v) {
    	//TODO
    }



    private class GetConnectionTask extends AsyncTask <URL, Void, String> {

    	@Override
    	protected void onPostExecute(String results) {
    		//setProgressBarIndeterminateVisibility(false);
    		//String message = "Fetch JSON data from eStore...";//Works
	        //textView.setText(message);
    	  if (results!=null) {
    		    textView.setText(results);
    	  }
    	}
    	
		@Override
		protected String doInBackground(URL... urls) {
			/**/
		  	Authenticator.setDefault(new Authenticator(){
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		    	return new PasswordAuthentication(mUser,mPassword.toCharArray());	
		}});
		 /**/


		InputStream in = null;
		HttpURLConnection aHttpURLConnection;
		try {
			aHttpURLConnection = (HttpURLConnection) urls[0].openConnection();
			in = aHttpURLConnection.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block    Needs exception handling for network error!
			e.printStackTrace();
			return "Network error.";		//Works!
			
		}
		
		return convertInputStreamToString(in);
		}
    	
		
		private String convertInputStreamToString(InputStream in) {
			BufferedReader reader = null;
			String JsonStr = null;		
			//InputStreamReader isw = new InputStreamReader(in);

		    StringBuffer buffer = new StringBuffer();
		    if (in == null) {
		    //Nothing to do.
		        JsonStr = null;
		    }

		    reader = new BufferedReader(new InputStreamReader(in));
		    String line;
		    try {
				while ((line = reader.readLine()) != null) {
				//Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
				//But it does make debugging a *lot* easier if you print out the completed
				//buffer for debugging.
				buffer.append(line + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "Error...";
			}
		    if (buffer.length() == 0) {
		        //Stream was empty. No point in parsing.
		            JsonStr = null;
		        }
		    
		    JsonStr = buffer.toString();
			return JsonStr;
			
			
		}
		
    }





}
