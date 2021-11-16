package com.alex.toad.rest.misc;

import org.apache.commons.net.util.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;

import com.alex.ubc.utils.Variables.requestType;


/**
 * Is a REST Request
 *
 * @author Alexandre RATEL
 */
public class RESTRequest
	{
	/**
	 * Variables
	 */
	private String request, reply, uri, login, password, credentials;
	private requestType type;
	private Request RESTReq;
	private int timeout;
	
	
	public RESTRequest(String request, requestType type, String uri, int timeout)
		{
		super();
		this.request = request;
		this.type = type;
		this.uri = uri;
		this.timeout = timeout;
		}
	
	public RESTRequest(String request, requestType type, String uri, int timeout, String login, String password)
		{
		super();
		this.request = request;
		this.uri = uri;
		this.login = login;
		this.password = password;
		this.type = type;
		this.timeout = timeout;
		
		build();
		}

	/**
	 * Build the REST request
	 */
	private void build()
		{
		credentials = login+":"+password;
	    credentials = new String(Base64.encodeBase64(credentials.getBytes()));
		
		//Add more if needed
		}
	
	/**
	 * Send the REST request
	 */
	public String send() throws Exception
		{
		switch(type)
			{
			case GET:
				return get();
			case POST:
				return post();
			case PUT:
				return put();
			default:
				return get();
			}
		}

	private String get() throws Exception
		{
		HttpResponse resp = RESTReq.Get(uri)
		.addHeader("Authorization", "Basic "+credentials)
		.addHeader("Content-Type", "application/XML")
		.connectTimeout(timeout)
		.socketTimeout(timeout)
		.execute()
		.returnResponse();
		
		return manageHttpResponse(resp);
		}
	
	private String post() throws Exception
		{
		HttpResponse resp = RESTReq.Post(uri)
		.addHeader("Authorization", "Basic "+credentials)
		.addHeader("Content-Type", "application/XML")
		.connectTimeout(timeout)
		.socketTimeout(timeout)
		.execute()
		.returnResponse();
		
		return reply;
		}
	
	private String put() throws Exception
		{
		HttpResponse resp = RESTReq.Put(uri)
		.addHeader("Authorization", "Basic "+credentials)
		.addHeader("Content-Type", "application/XML")
		.connectTimeout(timeout)
		.socketTimeout(timeout)
		.execute()
		.returnResponse();
		
		return reply;
		}
	
	private String manageHttpResponse(HttpResponse resp) throws Exception
		{
		int code = resp.getStatusLine().getStatusCode();
		if((Integer.toString(code)).startsWith("20"))
			{
			//Success
			return EntityUtils.toString(resp.getEntity());
			}
		else
			{
			throw new Exception("Code "+code+" returned");
			}
		}
	
	public String getRequest()
		{
		return request;
		}

	public String getReply()
		{
		return reply;
		}

	public requestType getType()
		{
		return type;
		}

	public String getUri()
		{
		return uri;
		}

	public String getLogin()
		{
		return login;
		}

	public String getPassword()
		{
		return password;
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
