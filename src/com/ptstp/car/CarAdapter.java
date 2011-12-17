package com.ptstp.car;

import com.ptstp.R;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Used to display information about a car on the screen.
 * @author Sanders
 *
 */
public class CarAdapter extends BaseAdapter {
	
	private final Activity activity;
	private Car car;

	/**
	 * @param activity
	 * @param car
	 */
	public CarAdapter(Activity activity, Car car) {
		this.activity = activity;
		this.car = car;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		return this.car;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return this.car.getCarId();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Get the Java classes necessary to interface with the various layouts inside the car.xml file
		LayoutInflater carInflator = activity.getLayoutInflater();
		ScrollView carContainer = (ScrollView) carInflator.inflate(R.layout.car, null);
		
		ScrollView car = (ScrollView) carContainer.findViewById(R.id.carScrollLayout);
		RelativeLayout carDetails = (RelativeLayout) car.findViewById(R.id.carDetails);
		LinearLayout carLayout = (LinearLayout) car.findViewById(R.id.carLayout);
		
		//Set what info will display inside the "carDetails" layout
		TextView carYear = (TextView) carDetails.findViewById(R.id.carYear);
		carYear.setText(new Integer(this.car.getYear()).toString());
		carYear.setFocusable(true);
		
		TextView carMake = (TextView) carDetails.findViewById(R.id.carMake);
		carMake.setText(this.car.getMake());
		
		TextView carModel = (TextView) carDetails.findViewById(R.id.carModel);
		carModel.setText(this.car.getModel());
		
		//Set what info will display inside the "carLayout" layout
		if (this.car.getHorsePower() > -1) {
			TextView hp = (TextView) carLayout.findViewById(R.id.carHP);
			hp.setText(new Integer(this.car.getHorsePower()).toString() + " hp");
		}
		
		TextView hpConfirmed = (TextView) carLayout.findViewById(R.id.carHPConfirmed);
		if (this.car.isHpConfirmed()) {
			hpConfirmed.setTextColor(carContainer.getResources().getColor(R.color.green));
			hpConfirmed.setText(R.string.confirmed);
		}
		
		if (this.car.getTime() > 0) {
			TextView carTime = (TextView) carLayout.findViewById(R.id.carTime);
			carTime.setText(new Double(this.car.getTime()).toString() + " sec");
		}
		
		TextView carTimeType = (TextView) carLayout.findViewById(R.id.timeTypeText);
		if ("q".equals(this.car.getTimeType())) {
			carTimeType.setText(R.string.quarterMile);
		}
		else if ("e".equals(this.car.getTimeType())) {
			carTimeType.setText(R.string.eigthMile);
		}
		
		TextView timeConfirmed = (TextView) carLayout.findViewById(R.id.carTimeConfirmed);
		if (this.car.isTimeConfirmed()) {
			timeConfirmed.setTextColor(carContainer.getResources().getColor(R.color.green));
			timeConfirmed.setText(R.string.confirmed);
		}
		
		TextView mph = (TextView) carLayout.findViewById(R.id.carMPH);
		mph.setText(new Double(this.car.getMaxMilesPerHour()).toString() + " mph");
		
		TextView Story = (TextView) car.findViewById(R.id.carStory);
		Story.setText(this.car.getStory());
		
		ImageView image = (ImageView) car.findViewById(R.id.carImage);
		image.setImageURI(Uri.parse(this.car.getImgSrc()));
		
		// Add the modifications to the view one at a time
		for (Modification mod : this.car.getMods()) {
			LinearLayout modItem = (LinearLayout) carInflator.inflate(R.layout.mod_list_item, null);
			LinearLayout modLayout = (LinearLayout) modItem.findViewById(R.id.modLayout);
			
			TextView title = (TextView) modLayout.findViewById(R.id.carModTitle);
			title.setText(mod.getType());
			
			TextView modDescription = (TextView) modLayout.findViewById(R.id.carModItem);
			modDescription.setText(mod.getDescription());
			carLayout.addView(modLayout);
		}
		car.setScrollbarFadingEnabled(true);
		this.notifyDataSetChanged();
		
		return car;
	}

}
