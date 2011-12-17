package com.ptstp.car;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;

import com.ptstp.dialogs.ErrorDialog;
import com.ptstp.dialogs.ErrorMessages;

import android.os.Bundle;

/**
 * Retrieves and displays information pertaining to a car
 * @author Sanders
 *
 */
public class CarActivity extends Activity {
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Car car = new Car();
        try {
        	car.loadCar(getIntent().getExtras().getInt("carId"));
        	CarAdapter adapter = new CarAdapter(this, car);
        	setContentView(adapter.getView(0, null, null));
        }
        catch (Exception e) {
        	ErrorDialog builder = new ErrorDialog(this);
        	builder.finishActivityOnClose();
        	AlertDialog alert = null;
        	if (e.getClass().equals(JSONException.class)) {
        		alert = builder.create(ErrorMessages.getResponseErrorMessage());
        	}
        	else {
        		alert = builder.create(e.getMessage());
        	}
        	alert.show();
        }
    }
    
}