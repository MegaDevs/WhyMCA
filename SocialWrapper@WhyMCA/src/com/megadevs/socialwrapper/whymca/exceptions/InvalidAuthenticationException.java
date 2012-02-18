package com.megadevs.socialwrapper.whymca.exceptions;

@SuppressWarnings("serial")
public class InvalidAuthenticationException extends SocialExceptions {

	public InvalidAuthenticationException(String msg, Exception e) {
		message = msg;
		exception = e;
	}
}
