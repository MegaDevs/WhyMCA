package com.megadevs.socialwrapper.whymca.exceptions;

@SuppressWarnings("serial")
public class MissingLoginCredentialsException extends SocialExceptions {

	public MissingLoginCredentialsException(String msg, Exception e) {
		message = msg;
		exception = e;
	}
}
