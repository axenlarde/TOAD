package com.alex.toad.soap.items;

import java.util.ArrayList;

import com.alex.toad.misc.BasicItem;
import com.alex.toad.misc.CollectionTools;
import com.alex.toad.utils.Variables;
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
	urllabel;
	private ArrayList<ServiceParameters> parameterList;
	
	private AgentData agentData;

	/***************
	 * Constructor
	 ***************/
	public PhoneService(String template)
		{
		super();
		
		this.template = template;
		parameterList = new ArrayList<ServiceParameters>();
		processTemplate();
		}
	
	/**
	 * The template here is composed of the followings :
	 * 'Service Name':'Service URL label' and the followings are service parameters
	 * So for them we have to process differently
	 */
	private void processTemplate()
		{
		if(template.contains(":"))
			{
			String[] tab = template.split(":");
			servicename = tab[0];
			urllabel = tab[1];
			
			if(tab.length > 2)
				{
				/**
				 * The value after the second one are parameters
				 */
				//We get what is after the second ':'
				int secondSep = template.indexOf(":", template.indexOf(":")+1)+1;
				for(String s : template.substring(secondSep, template.length()).split(":"))
					{
					if(s.contains("="))
						{
						String[] parameters = s.split("=");
						parameterList.add(new ServiceParameters(parameters[0], parameters[1]));
						}
					}
				Variables.getLogger().debug(servicename+" : found "+parameterList.size()+" parameter(s)");
				}
			}
		else
			{
			servicename = template;
			}
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
		servicename = CollectionTools.applyPattern(agentData, servicename, this, true);
		urllabel = CollectionTools.applyPattern(agentData, urllabel, this, true);
		for(ServiceParameters sp : parameterList)sp.resolve(agentData);
		}
	
	
	public String getServicename()
		{
		return servicename;
		}

	public void setServicename(String servicename)
		{
		this.servicename = servicename;
		}

	public String getUrlLabel()
		{
		return urllabel;
		}

	public void setUrlLabel(String surl)
		{
		this.urllabel = surl;
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

