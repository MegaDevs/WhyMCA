package com.megadevs.atss.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ATSSRemote {

	public static final String SERVER_ADDRESS = "http://www.megadevs.com/atss/";

	public static final String CALLS = "call.php";
	public static final String PICS = "push_pics.php";

	//	push_pics.php?theftId=<theft_id>&pics[]=<pic1_stream>&pics[]=<picN_stream> 

	public static void invokePicsAPI(String theftId, byte[] source) throws FakeConnectivityException, IOException{
		String remoteAddress = SERVER_ADDRESS + PICS;
		String img = Base64.encodeBytes(source);

		HTTPPostParameters params = new HTTPPostParameters();
		params.addParam("theftId", theftId);
		params.addParam("pics[]", img);

		HTTPResult r = Utils.executeHTTPUrlPost(remoteAddress, params, null);
		System.out.println(r.getData());

	}

	public static void invokeCallsAPI(String name, ArrayList<String> smsNumbers, ArrayList<String>voiceNumbers) throws FakeConnectivityException, IOException{
		String remoteAddress = SERVER_ADDRESS + CALLS;
		remoteAddress +="?name="+name;

		if(smsNumbers!=null && smsNumbers.size()>0){
			for(String s:smsNumbers){
				remoteAddress+= "&smsNumbers[]=%2B39"+s;
			}
		}
		if(voiceNumbers!=null && voiceNumbers.size()>0){
			for(String s:voiceNumbers){
				remoteAddress+= "&voiceNumbers[]=%2B"+s;
			}
		}
		System.out.println("Remote addr:"+remoteAddress);

		//remoteAddress
		//HTTPPostParameters params = new HTTPPostParameters();
		//params.addParam("userId", UserID);
		HTTPResult r = Utils.executeHTTPUrlPost(remoteAddress, null, null);
		System.out.println(r.getData());
	}



	public static void main(String[] args) {
		
		File f = new File("/home/ziby/Pictures/NinePlanets.jpg");
		try {
			invokePicsAPI("diomerda666",Utils.getBytesFromFile(f));
		} catch (FakeConnectivityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		ArrayList<String> n =  new ArrayList<String>();
		n.add("3491696919");

		try {
			ATSSRemote.invokeCallsAPI("stefano",n,null);
		} catch (FakeConnectivityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}


}
