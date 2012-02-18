package com.megadevs.socialwrapper.whymca.exceptions;

@SuppressWarnings("serial")
public class NetworkErrorException extends SocialExceptions {
	
	public NetworkErrorException(String msg, Exception e) {
		message = msg;
		exception = e;
	}
}
