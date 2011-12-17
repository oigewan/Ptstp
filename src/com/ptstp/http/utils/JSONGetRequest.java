package com.ptstp.http.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.ptstp.dialogs.ErrorMessages;


/**
 * This class handles making a GET request and parsing the resulting data
 * into a JSONObject
 * 
 * @author John Sanders
 * @version 1.0
 *
 */
public class JSONGetRequest {
	private String url = null;
	private Map<String, String> params = new HashMap<String, String>();
	
	/**
	 * @return The base URL of the request
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * @param url The base URL of the request
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return Key/Value pairs of get parameters
	 */
	public Map<String, String> getParmeters() {
		return this.params;
	}
	
	/**
	 * @param newParams All the key/value pairs of the GET request. This will replace any existing parameters.
	 */
	public void setParms(Map<String, String> newParams) {
		this.params = newParams;
	}
	
	/**
	 * @param key
	 * @param value
	 * @return Whether or not the parameter was added
	 */
	public boolean addParam(String key, String value) {
		if (key != null && value != null) {
			key = URLEncoder.encode(key);
			value = URLEncoder.encode(value);
			this.params.put(key, value);
			return true;
		}
		return false;
	}

	/**
	 * @return The JSONObject containing the data returned by the GET request.
	 */
	public JSONObject makeRequest() throws Exception {
		JSONObject json = null;
		// Make sure we're actually requesting something from somewhere
		if (this.url != null) {
			// Build the url complete with GET parameters
			StringBuilder fullUri = new StringBuilder(this.url);
			fullUri.append('?');
			if (params != null) {
				for (Map.Entry<String, String> entry : this.params.entrySet()) {
					fullUri.append(entry.getKey());
					fullUri.append('=');
					fullUri.append(entry.getValue());
					fullUri.append('&');
				}
			}
			
			// Build out the request object
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			try {
				request.setURI(new URI(fullUri.toString()));
				HttpResponse response = client.execute(request);
				
				// Read the result into a string buffer
				BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				
				// Parse the string buffer into a JSONObject
				String jsonString = sb.toString();
				json = (JSONObject) new JSONTokener(jsonString).nextValue();
			}
			catch (URISyntaxException uriException) {
				throw new Exception(ErrorMessages.getDefaultErrorMessage(), uriException);
			} catch (ClientProtocolException protocolException) {
				throw new Exception(ErrorMessages.getNetworkErrorMessage(), protocolException);
			} catch (IOException ioException) {
				throw new Exception(ErrorMessages.getResponseErrorMessage(), ioException);
			} catch (JSONException jsonException) {
				throw new Exception(ErrorMessages.getResponseErrorMessage(), jsonException);
			}
		}
		return json;
	}
}
