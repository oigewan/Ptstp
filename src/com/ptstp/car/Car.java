package com.ptstp.car;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ptstp.http.utils.JSONGetRequest;

/**
 * A wrapper for information on a car
 * @author Sanders
 *
 */
public class Car {

	private int id = -1;
	private String make = null;
	private String model = null;
	private int year = 1900;
	private String story = null;
	private List<Modification> mods = new ArrayList<Modification>();
	private String imgSrc = null;
	private int horsePower = 0;
	private boolean hpConfirmed = false;
	private double time = 0.0;
	private boolean timeConfirmed = false;
	private double maxMilesPerHour = 0.0;
	private String timeType = "q";
	
	/**
	 * This should be used only for testing a layout without making a GET request
	 */
	public void loadTestCar() {
		this.make = "Saturn";
		this.model = "SL2";
		this.year = 2000;
		this.story = "A car that I drove into the ground";
		this.horsePower = 150;
		this.hpConfirmed = true;
		this.time = 16.2;
		this.maxMilesPerHour = 120;
	}
	
	/**
	 * Load the info for a car by performing a GET request to the server and parsing the resulting JSON
	 * @param carId
	 * @throws JSONException
	 */
	public void loadCar(int carId) throws Exception, JSONException {
		// Build up and make the GET request
		JSONGetRequest request = new JSONGetRequest();
		request.setUrl("http://ptstp.com/api.php");
		request.addParam("id", String.valueOf(carId));
		request.addParam("type", "car");
		
		// Get the JSON
		JSONObject json = request.makeRequest();
		JSONObject car = json.getJSONObject("car");
		if (car != null) {
			// Translate the JSON items into the Java object
			this.make = car.getString("make");
			this.model = car.getString("model");
			this.year = car.getInt("year");
			this.story = car.optString("story", null);
			this.imgSrc = car.optString("photo", null);
			this.horsePower = car.optInt("power", -1);
			this.hpConfirmed = car.optBoolean("power_confirmed", false);
			this.maxMilesPerHour = car.optDouble("et", -1);
			this.time = car.optDouble("et_speed", -1);
			this.timeConfirmed = car.optBoolean("et_confirmed", false);
			this.timeType = car.optString("et_type", "q");
			
			// Create Modifications from the data in the JSON and add them to the car
			JSONArray mods = car.optJSONArray("mod");
			if (mods != null) {
				for (int i = 0; i < mods.length(); i++) {
					JSONObject jsonMod = (JSONObject) mods.get(i);
					Modification mod = new Modification();
					mod.setId(jsonMod.getInt("id"));
					mod.setType(jsonMod.getString("type"));
					mod.setDescription(jsonMod.getString("description"));
					this.mods.add(mod);
				}
			}
		}
	}
	
	/**
	 * @return The id of this car
	 */
	public int getCarId() {
		return this.id;
	}
	/**
	 * @param make The make of the car
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return The make of the car
	 */
	public String getMake() {
		return make;
	}
	/**
	 * @param make The make of the car
	 */
	public void setMake(String make) {
		this.make = make;
	}
	/**
	 * @return The model of the car
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model The model to of the car
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return The year that the car was made
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year The year that the car was made
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return The story of the car
	 */
	public String getStory() {
		return story;
	}
	/**
	 * @param story The story of the car
	 */
	public void setStory(String story) {
		this.story = story;
	}
	/**
	 * @return A list of Modifications associated with this car
	 */
	public List<Modification> getMods() {
		return mods;
	}
	/**
	 * @param mods A list of Modifications associated with this car
	 */
	public void setMods(List<Modification> mods) {
		this.mods = mods;
	}
	/**
	 * @return The URI of the image associated with this car
	 */
	public String getImgSrc() {
		return imgSrc;
	}
	/**
	 * @param imgSrc The URI of the image associated with this car
	 */
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	/**
	 * @return The horsePower of the car
	 */
	public int getHorsePower() {
		return horsePower;
	}
	/**
	 * @param horsePower The horsePower of the car
	 */
	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}
	/**
	 * @return Whether or not the horsepower of the car is confirmed
	 */
	public boolean isHpConfirmed() {
		return hpConfirmed;
	}
	/**
	 * @param hpConfirmed Whether or not the horsepower of the car is confirmed
	 */
	public void setHpConfirmed(boolean hpConfirmed) {
		this.hpConfirmed = hpConfirmed;
	}
	/**
	 * @return Racing time
	 */
	public double getTime() {
		return time;
	}
	/**
	 * @param time Racing time
	 */
	public void setTime(double quarterMileTime) {
		this.time = quarterMileTime;
	}
	/**
	 * @return Whether the racing time of the car is confirmed
	 */
	public boolean isTimeConfirmed() {
		return timeConfirmed;
	}
	/**
	 * @param timeConfirmed Whether the racing time of the car is confirmed
	 */
	public void setTimeConfirmed(boolean timeConfirmed) {
		this.timeConfirmed = timeConfirmed;
	}
	/**
	 * @return The maximum speed of the car in miles per hour
	 */
	public double getMaxMilesPerHour() {
		return maxMilesPerHour;
	}
	/**
	 * @param maxMilesPerHour The maximum speed of the car in miles per hour
	 */
	public void setMaxMilesPerHour(double maxMilesPerHour) {
		this.maxMilesPerHour = maxMilesPerHour;
	}
	
	/**
	 * @return The type of the time (quarter q, eigth e, etc)
	 */
	public String getTimeType() {
		return timeType;
	}

	/**
	 * @param timeType the timeType to set
	 */
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	
}
