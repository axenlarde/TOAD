package com.alex.toad.soap.items;

import com.alex.toad.misc.BasicItem;
import com.alex.toad.misc.CollectionTools;
import com.alex.toad.webserver.AgentData;


/**********************************
 * Used to store an IP Phone Service
 * 
 * @author RATEL Alexandre
 **********************************/
public class PhoneService extends BasicItem
	{
	/**
	 * Variables
	 */
	private String template, servicename,
	surl;
	
	private AgentData agentData;

	/***************
	 * Constructor
	 ***************/
	public PhoneService(String template)
		{
		super();
		this.template = template;
		}

	/******
	 * Method used to resolve the service variables
	 * 
	 * In this case, "resolve" means apply regex variables
	 * For instance : cucm.firstname becomes "alexandre"
	 * @throws Exception 
	 */
	public void resolve() throws Exception
		{
		template = CollectionTools.applyPattern(agentData, template, this, true);
		
		if(template.contains(":"))
			{
			String[] tab = template.split(":");
			servicename = tab[0];
			surl = tab[1];
			}
		else
			{
			servicename = template;
			}
		}
	
	
	public String getServicename()
		{
		return servicename;
		}

	public void setServicename(String servicename)
		{
		this.servicename = servicename;
		}

	public String getSurl()
		{
		return surl;
		}

	public void setSurl(String surl)
		{
		this.surl = surl;
		}

	public String getTemplate()
		{
		return template;
		}

	public void setTemplate(String template)
		{
		this.template = template;
		}

	public AgentData getAgentData()
		{
		return agentData;
		}

	public void setAgentData(AgentData agentData)
		{
		this.agentData = agentData;
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

