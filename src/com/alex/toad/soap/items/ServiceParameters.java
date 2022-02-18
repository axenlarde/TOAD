package com.alex.toad.soap.items;

import com.alex.toad.misc.CollectionTools;
import com.alex.toad.webserver.AgentData;

/**********************************
* Used to store a servcie parameters value
* 
* @author RATEL Alexandre
**********************************/
public class ServiceParameters
	{
	/**
	 * Variables
	 */
	private String name,value;
	
	/**
	 * Constructor
	 */
	public ServiceParameters(String name, String value)
		{
		super();
		this.name = name;
		this.value = value;
		}

	public void resolve(AgentData agentData) throws Exception
		{
		name = CollectionTools.applyPattern(agentData, name, this, true);
		value = CollectionTools.applyPattern(agentData, value, this, true);
		}
	
	public String getName()
		{
		return name;
		}

	public void setName(String name)
		{
		this.name = name;
		}

	public String getValue()
		{
		return value;
		}

	public void setValue(String value)
		{
		this.value = value;
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

