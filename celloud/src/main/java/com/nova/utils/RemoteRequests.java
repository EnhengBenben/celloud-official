package com.nova.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

public class RemoteRequests {
	Logger log = Logger.getLogger(RemoteRequests.class);

	public void run(String requestUrl) {
		HttpURLConnection conn = null;
		log.info("requestUrl:" + requestUrl);
		try {
			conn = (HttpURLConnection) new URL(requestUrl).openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.connect();
			conn.getInputStream();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String pdf(String requestUrl) {
		HttpURLConnection conn = null;
		log.info("requestUrl:" + requestUrl);
		BufferedReader br = null;
		String result = null;
		try {
			conn = (HttpURLConnection) new URL(requestUrl).openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(240000);
			conn.connect();
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			StringBuffer buff = new StringBuffer();
			String lineStr = null;
			while ((lineStr = br.readLine()) != null) {
				buff.append(lineStr);
			}
			result = buff.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return result;
	}
}