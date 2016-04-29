package com.infoprima.android.shopspree;


import com.android.volley.toolbox.NetworkImageView;
import com.infoprima.android.shopspree.R;
import com.infoprima.android.tools.AppController;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SingleProductActivity extends Activity {
	
	private TextView textView;
    private ProgressDialog pDialog;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	/*Not working
	pDialog = new ProgressDialog(this);
    // Showing progress dialog loading image
    pDialog.setMessage("Loading...");
    pDialog.show();
    */
	setContentView(R.layout.single_product);
	
    // Get the message from the intent
    Intent intent = getIntent();
    String name = intent.getStringExtra(Tab1.TAG_NAME);
    String imgurl = intent.getStringExtra(Tab1.TAG_IMGURL);
    
        
    // Create the text view
    textView = (TextView) findViewById(R.id.product_name);
    textView.setTextSize(40);
    textView.setText(name);
    
    
    NetworkImageView nv = (NetworkImageView) findViewById(R.id.ivImage);
    nv.setImageUrl(imgurl, AppController.getInstance().getImageLoader()); //AppController code.

    //Toast.makeText(this, "Image file URL : " +imgurl, Toast.LENGTH_LONG).show();	//Works
	}
	

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
    	//hidePDialog();		//Not working
    	//Toast.makeText(this, "Why?, early burst?... ", Toast.LENGTH_LONG).show();
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
	
    /*not working	
	protected void onPreExecute() {
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
	}	
	protected void onPostExecute() {
		hidePDialog();
		
	}
	*/	
}

