package com.megadevs.atss.network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/** Network utils class */
public class Utils {
	
	public static final int BUFFER_SIZE = 2*1024;

	public static byte[] getBytesFromUrl(String URL) throws IOException{
		URL url = new URL(URL);
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		InputStream is = null;
		try {
		  is = url.openStream ();
		  byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
		  int n;

		  while ( (n = is.read(byteChunk)) > 0 ) {
		    bais.write(byteChunk, 0, n);
		  }
		}
		catch (IOException e) {
		  System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
		  e.printStackTrace ();
		  // Perform any other exception handling that's appropriate.
		}
		finally {
		  if (is != null) { is.close(); }
		  
		}
		return bais.toByteArray();
		
		
	}
	
	/**<b><br>ATTENZIONE,  PREFERIBILE USARE IL METODO executeHTTPUrlPost</b><br><br>
	 * Read data from  HTTP url
	 * @param myurl 
	 * @return response data
	 * @throws URISyntaxException bad URL
	 * @throws IOException 
	 * @throws FakeConnectivityException 
	 */
	public static HTTPResult executeHTTPUrl(String myurl) throws IOException, FakeConnectivityException, URISyntaxException {

		HTTPResult ris = new HTTPResult();

		//Result data
		String data="";

		//Prepare url
		String composeUrl="//"+myurl;
		URLEncoder.encode(composeUrl, "UTF-8");
		URI	tmpUri = new URI("http",composeUrl,null);

		//creo URI per quotare i caratteri speciali
		composeUrl = tmpUri.toString(); //estraggo come stringa
		System.out.println("compose url: "+composeUrl);
		URL url = new URL(composeUrl); //creo URL da aprire

		//Apro connessione
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);

		
		/*
		//Leggo l'esito dello script
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						connection.getInputStream()));

		
		String decodedString;
		while ((decodedString = in.readLine()) != null) {
			data = data+decodedString+"\n";
			if(!in.ready()){
				break;
			}
		}
		in.close();

		data=data.trim();
		*/
		data=readInputStreamAsString(connection.getInputStream());
		if(data==null)data="";


		//controllo delle fake-connections
		if(checkFakeConnection(data)) throw new FakeConnectivityException();

		ris.setData(data);
		ris.setHeader(connection.getHeaderFields());

		return ris;
	}


	/**
	 * Read data from an HTTP post request.
	 * @param targetURL the target page
	 * @param urlParameters the post parameters
	 * @return HTTP response
	 * @throws IOException 
	 * @throws FakeConnectivityException 
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * */
	public static HTTPResult executeHTTPUrlPost(String targetURL, HTTPPostParameters params, Map<String,String> cookies) throws FakeConnectivityException, IOException {
		HTTPResult ris = new HTTPResult();
		try{
			ClientHttpRequest client = new ClientHttpRequest(targetURL);

			//Set cookies
			if(cookies!=null){
				client.setCookies(cookies);
				//System.out.println("Set cookies to: "+cookies);
			}

			InputStream is;

			if(params == null)
				is = client.post();
			else if(params.getParamCount()>0){
				is = client.post(params.getParams()); 	
			}
			else 
				is = client.post();

			String data=readInputStreamAsString(is);
			
			/*
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			String line;
			//String data="";
			int stringBufferSize = client.getConnection().getContentLength();
			if(stringBufferSize<=0)stringBufferSize = 100;
			System.out.println("StringBuilder created with capacity of "+stringBufferSize);

			StringBuilder data = new StringBuilder(stringBufferSize);

			while((line = rd.readLine()) != null) {
				data.append(line+"\n");
				//if(!rd.ready())	break;
				//data=data+line+"\n";
			}
			rd.close();
			*/

			//check fake-connections
			if(checkFakeConnection(data)) throw new FakeConnectivityException();

			//save result
			ris.setData(data);
			//System.out.println(ris.getData());
			ris.setHeader(client.getConnection().getHeaderFields());
			System.out.println("End network");
		}catch(IOException e){
			throw new IOException("no conn");
		}
		return ris;
	}

	
	/**
	 * Read an input stream into a string
	 * @param in
	 * @return
	 * @throws IOException
	 */
	/*
	public static String readInputStreamAsString(InputStream in) throws IOException {

		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		final byte[] buffer = new byte[512];

		int result = bis.read(buffer);
		while(result != -1) {
			//byte b = (byte)result;
			buf.write(buffer,0,result);
			result = bis.read(buffer);
		}        
		return buf.toString();
	}*/
	
	public static String readInputStreamAsString(InputStream is) throws IOException{
		final char[] buffer = new char[BUFFER_SIZE];
		StringBuilder out = new StringBuilder(BUFFER_SIZE);
		Reader in = new InputStreamReader(is, "UTF-8");
		int read;
		do {
		  read = in.read(buffer, 0, buffer.length);
		  if (read>0) {
		    out.append(buffer, 0, read);
		  }
		} while (read>=0);
		return out.toString();
	}


	//*******************************DEPRECATO***************************************
	//	/**
	//	 * Read data from an HTTP post request.
	//	 * @param targetURL the target page
	//	 * @param urlParameters the post parameters
	//	 * @return HTTP response
	//	 * @throws IOException 
	//	 * @throws FakeConnectivityException 
	//	 * @throws URISyntaxException 
	//	 * */
	//	public static HTTPResult executeHTTPUrlPost(String targetURL, HTTPPostParameters params) throws IOException, FakeConnectivityException, URISyntaxException {
	//
	//		HTTPResult ris = new HTTPResult();
	//
	//		URL url;
	//		HttpURLConnection connection = null;  
	//		
	//		
	//		String urlParameters="";
	//		if(params!=null){
	//			urlParameters = params.generateHTTPPostParameters();
	//		}
	//
	//		//Create connection
	//		String composeUrl="//"+targetURL;
	//		URLEncoder.encode(composeUrl, "UTF-8");
	//		URLEncoder.encode(urlParameters, "UTF-8");
	//		URI	tmpUri = new URI("http",composeUrl,null);
	//
	//		//creo URI per quotare i caratteri speciali
	//		composeUrl = tmpUri.toString(); //estraggo come stringa
	//		
	//		System.out.println("\nURL request: "+composeUrl);
	//		System.out.println("\nURL params: "+urlParameters);
	//		
	//		url = new URL(composeUrl);
	//		connection = (HttpURLConnection)url.openConnection();
	//		connection.setRequestMethod("POST");
	//		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	//		connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
	//
	//		//connection.setRequestProperty("Content-Language", "en-US");  
	//		//Aggiunte per consistenza 
	//		//connection.setRequestProperty( "User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; it; rv:1.9.2.12) Gecko/20101027 Ubuntu/9.10 (karmic) Firefox/3.6.12");
	//		//connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	//		//connection.setRequestProperty("Accept-Language" ,"en-US,en;q=0.8");
	//		//connection.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");	
	//
	//		connection.setUseCaches (false);
	//		connection.setDoInput(true);
	//		connection.setDoOutput(true);
	//
	//		//Send request
	//		DataOutputStream wr = new DataOutputStream (
	//				connection.getOutputStream ());
	//		wr.writeBytes (urlParameters);
	//		wr.flush ();
	//		wr.close ();
	//
	//		//Get Response	
	//		InputStream is = connection.getInputStream();
	//		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	//
	//		String line;
	//		String data="";
	//		while((line = rd.readLine()) != null) {
	//			data=data+line+"\n";
	//		}
	//		rd.close();
	//
	//		//controllo delle fake-connections
	//		if(checkFakeConnection(data)) throw new FakeConnectivityException();
	//
	//		ris.setData(data);
	//		ris.setHeader(connection.getHeaderFields());
	//
	//		return ris;
	//	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int)length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "+file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	/**
	 * Check if passed data is the result of a fake connection
	 * @param data data to test
	 * @return true=fake
	 */
	private static boolean checkFakeConnection(String data) {
		// TODO fare un metodo per controllare se la risposta arriva da una form di login di un accesspoint o altro...
		return false;
	}


	///TEST MAIN 
	public static void main(String[] args){
		try {
			//HTTPResult r = executeHTTPUrl("www.google.it");
			HTTPPostParameters p = new HTTPPostParameters();
			p.addParam("campo", "asdas33d");
			p.addParam("campo2", "a");
			p.addParam("prova.txt", new File("/home/ziby/Documenti/prova.txt"));
			HTTPResult r = executeHTTPUrlPost("http://www.zibysoft.altervista.org/prova.php",p,null);
			System.out.println("\nHeader:\n"+r.getHeader().toString());
			System.out.println();
			System.out.println("Data:\n"+r.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FakeConnectivityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
