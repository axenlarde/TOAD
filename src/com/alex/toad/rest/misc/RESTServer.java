package com.alex.toad.rest.misc;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.net.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.alex.toad.utils.Variables.requestType;

/**********************************
* Used to store necessary data for a REST call 
* 
* @author RATEL Alexandre
**********************************/
public class RESTServer
	{
	/**
	 * Variables
	 */
	private String host, port, username, password, description, credentials, baseUri;
	private int timeout;
	private HttpClient httpClient;
	
	/**
	 * Constructor
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public RESTServer(String host, String port, String username,
			String password, int timeout, String description) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
		{
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.timeout = timeout;
		this.description = description;
		
		init();
		}
	
	private void init() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
		{
		Logger logger = Logger.getLogger("org.apache.http");
	    logger.setLevel(Level.ERROR);
		
		credentials = username+":"+password;
	    credentials = new String(Base64.encodeBase64(credentials.getBytes()));
		
	    baseUri = "https://"+host+":"+port+"/";
	    
	    //We allow self signed certificate
	    httpClient = HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
		}
	
	public String send(requestType type, String uri, String body) throws Exception
		{
		String response = null; 
		switch(type)
			{
			case GET: response = get(uri);break;
			case POST: response = post(uri, body);break;
			case PUT: response = put(uri, body);break;
			default: throw new Exception("Failed to process the REST request");
			}
		
		if(response == null)
			{
			throw new Exception("Failed to process the REST request");
			}
		else
			{
			//We remove the first XML tag (<?xml version="1.0" encoding="UTF-8"?>)
			return response.substring(response.indexOf("?>")+2);
			}
		}
	
	
	private String get(String uri) throws Exception
		{
		//HttpUriRequest request = new HttpGet(baseUri+uri);
		HttpUriRequest request = new HttpGet(baseUri+uri);
	    request.addHeader("Authorization", "Basic "+credentials);
	    request.addHeader("Content-Type", "application/XML");
	    
        HttpResponse response = httpClient.execute(request);
        
		int responseCode = response.getStatusLine().getStatusCode();
		if(responseCode != 200)throw new Exception("REST request failed code : "+responseCode); 
		
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "UTF-8");
		}
	
	private String post(String uri, String body) throws Exception
		{
		HttpPost request = new HttpPost(baseUri+uri);
	    request.addHeader("Authorization", "Basic "+credentials);
	    request.addHeader("Content-Type", "application/XML");
	    request.setEntity(new StringEntity(body));
	    
        HttpResponse response = httpClient.execute(request);
        
		int responseCode = response.getStatusLine().getStatusCode();
		if(responseCode != 200)throw new Exception("REST request failed code : "+responseCode); 
		
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "UTF-8");
		}
	
	private String put(String uri, String body) throws Exception
		{
		HttpPut request = new HttpPut(baseUri+uri);
	    request.addHeader("Authorization", "Basic "+credentials);
	    request.addHeader("Content-Type", "application/XML");
	    request.setEntity(new StringEntity(body));
	    
        HttpResponse response = httpClient.execute(request);
        
		int responseCode = response.getStatusLine().getStatusCode();
		if(responseCode != 200)throw new Exception("REST request failed code : "+responseCode); 
		
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "UTF-8");
		}

	public String getHost()
		{
		return host;
		}

	public String getPort()
		{
		return port;
		}

	public String getUsername()
		{
		return username;
		}

	public String getPassword()
		{
		return password;
		}

	public String getDescription()
		{
		return description;
		}

	public int getTimeout()
		{
		return timeout;
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

