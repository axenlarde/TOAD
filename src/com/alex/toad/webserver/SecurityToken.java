package com.alex.toad.webserver;

import com.alex.toad.misc.Agent;

/**********************************
* Used to store a security token
* 
* @author RATEL Alexandre
**********************************/
public class SecurityToken
	{
	/**
	 * Variables
	 */
	private String token;
	private AgentData agent;
	
	/**
	 * Constructor
	 */
	public SecurityToken(String token, AgentData agent)
		{
		super();
		this.token = token;
		this.agent = agent;
		}

	public String getToken()
		{
		return token;
		}

	public AgentData getAgent()
		{
		return agent;
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

