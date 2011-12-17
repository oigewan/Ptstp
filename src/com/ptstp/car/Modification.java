package com.ptstp.car;

import org.json.JSONException;
import org.json.JSONObject;

import com.ptstp.http.utils.JSONGetRequest;

public class Modification {
	private int id = -1;
	private String type = null;
	private String description = null;
	
	/**
	 * Load the info for a modification by performing a GET request to the server and parsing the resulting JSON
	 * @param modId
	 * @throws JSONException
	 */
	public void loadMod(int modId) throws Exception, JSONException {
		JSONGetRequest request = new JSONGetRequest();
		request.setUrl("http://ptstp.com/mod.php");
		request.addParam("id", String.valueOf(modId));
		JSONObject json = request.makeRequest();
		JSONObject mod = json.getJSONObject("mod");
		if (mod != null) {
			this.id = mod.getInt("id");
			this.type = mod.getString("type");
			this.description = mod.optString("description", null);
		}
	}
	
	/**
	 * @return The mod ID
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The mod ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return The mod type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The mod type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return The description of the modification
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description of the modification
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
