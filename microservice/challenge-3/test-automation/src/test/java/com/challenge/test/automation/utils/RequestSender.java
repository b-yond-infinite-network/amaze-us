package com.challenge.test.automation.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible to send HTTP request with or without request body (payload)
 *
 */
public class RequestSender {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RequestSender.class);

	/**
	 * @param requestUrl
	 * @param requestMethod like GET, POST, PUT or DELETE
	 * @param payload is a request body
	 * @return HTTP response
	 * @throws IOException
	 */
	public static String Send(String requestUrl, String requestMethod, String payload) throws IOException {
		LOGGER.info("Preparing a '{}' request with '{}' to send with payload, {}", requestMethod
				, requestUrl, payload);
		URL url = new URL(requestUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(requestMethod);
		con.setRequestProperty("Content-Type", "application/json");
		con.setConnectTimeout(5000);
		con.setDoOutput(true);
		
		OutputStream os = con.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");    
		osw.write(payload);
		osw.flush();
		osw.close();
		os.close();  //don't forget to close the OutputStream

		// read the content like below
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		
		return content.toString();
	}
	
	/**
	 * @param requestUrl
	 * @param requestMethod like GET, POST, PUT or DELETE
	 * @return HTTP response
	 * @throws IOException
	 */
	public static String Send(String requestUrl, String requestMethod) throws IOException {
		LOGGER.info("Preparing a '{}' request with '{}' to send.", requestMethod
				, requestUrl);
		URL url = new URL(requestUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(requestMethod);
		con.setRequestProperty("Content-Type", "application/json");
		con.setConnectTimeout(5000);

		// read the content like below
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		
		return content.toString();
	}

}
