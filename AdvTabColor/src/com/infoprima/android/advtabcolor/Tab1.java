package com.infoprima.android.advtabcolor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//volley imports works
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



//public class Tab1 extends Fragment {
public class Tab1 extends ListFragment {	

	//private String mUrl = "http://10.0.2.2:8000/api/product/";	
	private String mUrl = "";	

	private String mUser = "myauth";
	private String mPassword = "mypassword";
	
	/*RHCLOUD
	private String mUrl = "https://estore-drindo.rhcloud.com/api/product/";	
	private String mUser = "myauth";
	private String mPassword = "mypassword";
	*/
	
    private static final String TAG_USERS = "users";
    private static final String TAG_ID = "id";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_ISSTAFF = "is_staff";
    private static final String TAG_URL = "url";
    
    private static final String TAG_NAME = "name";
    private static final String TAG_IMGTHUMB = "imgthumb";
    private static final String TAG_IMGFILE = "imgfile";
    
    ArrayList<HashMap<String, String>> productList;
    
    JSONArray users = null;
    ArrayList<HashMap<String, String>> usersList;
    //JSONArray usersList = null;
    

    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1,container,false);

        
        //Toast.makeText(getActivity(), "Stairway to heaven ...", Toast.LENGTH_SHORT).show();
        
        //****Async in action
    	//new LongRunningGetIO().execute();
        
    	GetConnectionTask1 task = new GetConnectionTask1();
    	try {

    		
    		//read preference host android:key="host_list"
    		SharedPreferences prefs = PreferenceManager
    				.getDefaultSharedPreferences(getActivity());
    		String host = prefs.getString("host_list", "");
    		String usbip = prefs.getString("ipaddress", "");
    		String ipappend = "http://"+usbip+":8000/api/product/";

    		if (host.equals("emulated")) {
    			mUrl = "http://10.0.2.2:8000/api/product/";
            } else if (host.equals("viausb")) {
            	mUrl = ipappend;
        	} else {
        		mUrl = "https://estore-drindo.rhcloud.com/api/product/";
        	}

    		
    		
			task.execute(new URL(mUrl));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}        
        
    	
    	
    	
        
        return v;
    }
    

    
    
    private class GetConnectionTask1 extends AsyncTask <URL, Void, String> {
    	
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}

		
		
    	@Override
    	protected void onPostExecute(String results) {
    		
			dialog.cancel();
			//adapter.notifyDataSetChanged();
			
    		//setProgressBarIndeterminateVisibility(false);
    		//String message = "Fetch JSON data from eStore...";//Works
	        //textView.setText(message);
    		
      	  if (results=="Network error.") {
      		Toast.makeText(getActivity(), "Fetch failed miserably, caused by Network Error", Toast.LENGTH_LONG).show();
      	  } 
    	  if (results!=null) {
    		    //**************textView.setText(results);
    		  //(done)Toast.makeText(getActivity(), "json String will be parse later here", Toast.LENGTH_LONG).show();
    		  
    		  
    		  //**
    		  try {
    			productList = new ArrayList<HashMap<String, String>>();
				JSONArray jsonArray = new JSONArray(results);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject c = jsonArray.getJSONObject(i);
					String name = "name: " + c.getString(TAG_NAME);
					String imgthumb = "imgthumb url: " + c.getString(TAG_IMGTHUMB);
					String imgfile = "imgfile url: " + c.getString(TAG_IMGFILE);
					HashMap<String, String> product = new HashMap<String, String>();
					product.put(TAG_NAME, name);
					product.put(TAG_IMGTHUMB, imgthumb);
					product.put(TAG_IMGFILE, imgfile);
					
					productList.add(product);
					
				}
			    ListAdapter adapter = new SimpleAdapter(
			    		getActivity(), productList,
			            R.layout.tab1_list, new String[] { TAG_NAME ,TAG_IMGTHUMB ,TAG_IMGFILE}, new int[] 
			            		{ R.id.name ,R.id.imgthumb ,R.id.imgfile });
				
			    setListAdapter(adapter);
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getActivity(), "json String parse error", Toast.LENGTH_LONG).show();
			}
    		  
    		  
    		  
    		  
    		  //**
    		  
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
    
    //On list click
    int mCurCheckPosition = 0;
    private MainActivity myActivity = null;
    @Override
    public void onAttach(Activity myActivity) {
    	super.onAttach(myActivity);
    	this.myActivity = (MainActivity)myActivity;
    }
    
    
    @Override
    public void onActivityCreated(Bundle savedState) {
    	super.onActivityCreated(savedState);
    	ListView lv = getListView();
    	//Toast.makeText(getActivity(), "Hello, I am survive...", Toast.LENGTH_LONG).show();   //Works
    	lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	lv.setSelection(mCurCheckPosition);
    	

    	
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
    	//Toast.makeText(getActivity(), "Hey, you just click an item...:"+pos, Toast.LENGTH_LONG).show();  //Works
    	Toast.makeText(getActivity(), "Hey, you just click an item...:"+id, Toast.LENGTH_LONG).show();   //works

    	
    }
    
    @Override
    public void onDetach() {
    super.onDetach();
    myActivity = null;
    }

    
    
    
    
}


