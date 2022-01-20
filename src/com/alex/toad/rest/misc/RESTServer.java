package com.alex.toad.rest.misc;

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
	private String host, port, username, password, description;
	private int timeout;
	
	/**
	 * Constructor
	 */
	public RESTServer(String host, String port, String username,
			String password, int timeout, String description)
		{
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.timeout = timeout;
		this.description = description;
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

