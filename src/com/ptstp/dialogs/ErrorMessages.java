package com.ptstp.dialogs;

public class ErrorMessages {
	public static String getDefaultErrorMessage()
	{
		return "Something bad happened, so we can\'t do what you asked. Please try again later.";
	}
	
	public static String getNetworkErrorMessage()
	{
		return "The PtStp server is currently unavailable, so we can't show you want you wanted. Feel free to try again later.";
	}
	
	public static String getResponseErrorMessage()
	{
		return "We got something back from the PtStp server, but it made no sense. Please notify us of the problem at test@me.com";
	}
}
