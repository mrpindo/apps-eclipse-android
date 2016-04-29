package com.infoprima.android.advtabcolor;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.infoprima.android.tools.AppController;
import com.infoprima.android.tools.CustomListAdapter;
import com.infoprima.android.tools.Product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

//survival guide:
/*
// *file:///home/endik/Documents/WorkSpace/Android/Book_Resources
// * /Android%20Custom%20ListView%20with%20Image%20and%20Text%20using%20Volley.html 
// * 
 */


public class Tab3 extends ListFragment  {

	//private String mUrl = "http://10.0.2.2:8000/api/product/";	
	private String mUrl = "";	
	private String mUser = "myauth";
	private String mPassword = "mypassword";
	
	/*RHCLOUD
	private String mUrl = "https://estore-drindo.rhcloud.com/api/product/";	
	private String mUser = "myauth";
	private String mPassword = "mypassword";
	*/

	private List<Product> productList = new ArrayList<Product>();
    private ListView listView;
    private CustomListAdapter adapter;
    private ProgressDialog pDialog;
    
    static final String TAG_NAME = "name";
    static final String TAG_IMGURL = "imgurl";
	
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_3,container,false);
        return v;
    }
       
    @Override
    public void onActivityCreated(Bundle savedState) {
    	super.onActivityCreated(savedState);
    
        listView = (ListView) getView().findViewById(android.R.id.list);
        adapter = new CustomListAdapter(getActivity(), productList);
        listView.setAdapter(adapter);
        
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

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
    		
    		fetch();		//do the volley task, fetching json    		
    		
    	//OnItemClick
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
    	//listView.setOnItemClickListener(new OnItemClickListener() {
    			@Override
    			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	


    				Product p = (Product) listView.getItemAtPosition(position);
    				String name = p.getName();
    				String price = p.getPrice();
    				String imgurl = p.getImgUrl();

    				// Starting single product activity
                    Intent in = new Intent(getActivity(),
                            SingleProductActivity.class);
                    		in.putExtra(TAG_NAME, name);
                    		in.putExtra(TAG_IMGURL, imgurl);
                    		startActivity(in);
                    	
    			}
    			});
    		
     }
    
    private void fetch() {
    	JsonArrayRequest request = new JsonArrayRequest(mUrl,
                new Response.Listener<JSONArray>() {
    			
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                    	hidePDialog();
                        for(int i = 0; i < jsonArray.length(); i++) {
                        	
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //mEntries.add(jsonObject.toString());				//Failed!
                                Product product = new Product();
                                product.setThumbnailUrl(jsonObject.getString("imgthumb"));
                                product.setImgUrl(jsonObject.getString("imgfile"));
                                product.setName(jsonObject.getString("name"));
                                product.setSku("sku: "+jsonObject.getString("sku"));
                                product.setDescription("Desc: "+jsonObject.getString("description"));
                                product.setPrice("Rp."+jsonObject.getString("price"));
                                product.setQuantity("Qty: "+jsonObject.getString("quantity"));
                                
                                productList.add(product);
                            }
                            catch(JSONException e) {
                                Toast.makeText(getActivity(), "Error parsing json: "+e, Toast.LENGTH_LONG).show();
                            }
                        }
                        // IMPORTANT! notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                        hidePDialog();
                    }
                }
                );
    	
    	//The following addRequestQueue must open, to JsonArrayRequest works as expected
    	AppController.getInstance().addToRequestQueue(request);
    	
    }
     
    	@Override
    	public void onDestroy() {
    		super.onDestroy();
    		hidePDialog();
    	}
 
    	private void hidePDialog() {
        if (pDialog != null) {
        		pDialog.dismiss();
        		pDialog = null;
        	}
    	}
    	
    	
    }
    
    
    
