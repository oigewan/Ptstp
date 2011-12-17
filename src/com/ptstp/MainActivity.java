package com.ptstp;

import com.ptstp.car.CarActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * The main switchboard for the app.  Created and shown on startup
 * @author Sanders
 *
 */
public class MainActivity extends Activity {

	/* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    /**
     * onClick callback for the test car button
     * @param view
     */
    public void showTestCar(View view) {
    	final ProgressDialog progress = ProgressDialog.show(this, "", "Searching...", false);
    	new Thread(new Runnable(){
            public void run(){
                try {
                    Thread.sleep(1000);
                    Intent intent = new Intent();
                	intent.setClass(MainActivity.this, CarActivity.class);
                	intent.putExtra("carId", 1);
                	startActivity(intent);
                	progress.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
}
