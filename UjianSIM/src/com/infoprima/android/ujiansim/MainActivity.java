package com.infoprima.android.ujiansim;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.infoprima.android.ujiansim.StartTestActivity;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends AppCompatActivity implements OnClickListener {
	
    Toolbar toolbar;
    static Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

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
	
	/**
	 * A placeholder fragment containing a simple view.
	 */

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			
						
			return rootView;
		}

		
	    @Override
	    public void onActivityCreated(Bundle savedState) {
	    	super.onActivityCreated(savedState);


			  button1 = (Button) getActivity().findViewById(R.id.buttona);
		        button1.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						//Toast.makeText(getActivity(), "Button1 clicked! ", Toast.LENGTH_SHORT).show();
						
	                    Intent in = new Intent(getActivity(), StartTestActivity.class);
	                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                    in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	                    startActivity(in);
	                    
						
						}
		        });

		}
	    
    	
        
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}
