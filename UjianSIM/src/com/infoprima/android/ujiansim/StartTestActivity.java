package com.infoprima.android.ujiansim;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartTestActivity extends Activity {
	
	private static TextView mTextField;
	private static TextView mTestNumber;
    private ProgressDialog pDialog;
    CountDownTimer timer;
    static Button buttona;
    static Button buttonb;
    static Button buttonc;
    static Button buttond;
    static Button buttonr;
    

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.start_test2);
	
	TimeIt();			//Call CountDownTimer

	buttona = (Button) findViewById(R.id.buttona);
	buttonb = (Button) findViewById(R.id.buttonb);
	buttonc = (Button) findViewById(R.id.buttonc);
	buttond = (Button) findViewById(R.id.buttond);
	buttonr = (Button) findViewById(R.id.buttonr);
	
    buttona.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			//Toast.makeText(StartTestActivity.this, "Button A clicked! ", Toast.LENGTH_SHORT).show();
			buttona.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
			buttonb.setEnabled(false);
			buttonc.setEnabled(false);
			buttond.setEnabled(false);
		}
    });
    buttonb.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			//Toast.makeText(StartTestActivity.this, "Button B clicked! ", Toast.LENGTH_SHORT).show();
			buttonb.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
			buttona.setEnabled(false);
			buttonc.setEnabled(false);
			buttond.setEnabled(false);
		}
    });
    buttonc.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			//Toast.makeText(StartTestActivity.this, "Button C clicked! ", Toast.LENGTH_SHORT).show();
			buttonc.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
			buttona.setEnabled(false);
			buttonb.setEnabled(false);
			buttond.setEnabled(false);
		}
    });
    buttond.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			//Toast.makeText(StartTestActivity.this, "Button D clicked! ", Toast.LENGTH_SHORT).show();
			buttond.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
			buttona.setEnabled(false);
			buttonb.setEnabled(false);
			buttonc.setEnabled(false);
		}
    });
    buttonr.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			//Toast.makeText(StartTestActivity.this, "Button Reset clicked! ", Toast.LENGTH_SHORT).show();
			ResetButton();
		}
    });

    
	}

    private int counter = 2;
	private void TimeIt() {

   	 	
		mTextField = (TextView) this.findViewById(R.id.mText);
		mTestNumber = (TextView) this.findViewById(R.id.test_number);
		mTestNumber.setText("No soal...: 1");
		//timer = new CountDownTimer(30000, 1000) {
		timer = new CountDownTimer(11000, 1000) {
			
		     public void onTick(long millisUntilFinished) {
		         mTextField.setText("Sisa waktu : " + millisUntilFinished / 1000);

		         if ((millisUntilFinished / 1000) < 2 ) {
		        	 //to check whether ProgressDialog is shown
		        	 if (pDialog != null && pDialog.isShowing()) {
		        		 //Do nothing
		        	 } else {
		        		 if (counter < 5) {
				             pDialog = new ProgressDialog(StartTestActivity.this);
				             //pDialog.setMessage("Next question...");
				             pDialog.setMessage("Nomor Soal Berikut...: "+counter);
			        		 pDialog.show();	 
		        		 } else {
		        			 
				             pDialog = new ProgressDialog(StartTestActivity.this);
				             pDialog.setMessage("Ujian Selesai...");
			        		 pDialog.show();	 
		        			 
		        		 }
		        	 }
		         }
		     }

		     public void onFinish() {
		         mTextField.setText("Selesai!");
		         hidePDialog();
		         ResetButton();
		         //repeat timer
		         if (counter < 5) {
		        	 //Toast.makeText(StartTestActivity.this, "Nomor soal...:  "+counter, Toast.LENGTH_SHORT).show();
		        	 timer.start();
	        		 mTestNumber.setText("No soal...: "+counter);
		         }
		     	counter = counter + 1;
		     }
		     
		  }.start();

	
	}
	
	
	
	
	
	//when back button pressed
	  @Override
	  public void onBackPressed() {
		super.onBackPressed(); 
		timer.cancel();
		finish();
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
	
	
	private void ResetButton() {
		buttona.setEnabled(true);
		buttonb.setEnabled(true);
		buttonc.setEnabled(true);
		buttond.setEnabled(true);
		
		buttona.getBackground().clearColorFilter();
		buttonb.getBackground().clearColorFilter();
		buttonc.getBackground().clearColorFilter();
		buttond.getBackground().clearColorFilter();
	}

	
	//Cancel test when orientation changed, 
	//also add in manifest: android:configChanges="orientation|screenSize"
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "Display changed to landscape, Test aborted", Toast.LENGTH_SHORT).show();
			timer.cancel();
			finish();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "Display changed to portrait, Test aborted", Toast.LENGTH_SHORT).show();
			timer.cancel();
			finish();
	    }
	}
	
	

}
