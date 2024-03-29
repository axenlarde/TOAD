package com.alex.toad.soap.items;

import com.alex.toad.misc.BasicItem;
import com.alex.toad.misc.CollectionTools;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables.sdType;
import com.alex.toad.webserver.AgentData;

/**********************************
 * Used to store a speed dial config
 * 
 * @author RATEL Alexandre
 **********************************/
public class SpeedDial extends BasicItem
	{
	/**
	 * Variables
	 */
	private String template, partition,
	number,
	description;
	
	private boolean pickup;
	private sdType type;
	private int position;
	
	private AgentData agentData;
	
	/***************
	 * Constructor
	 ***************/
	public SpeedDial(String template, int position)
		{
		super();
		this.template = template;
		this.position = position;
		type = sdType.sd;
		}
	
	public void resolve() throws Exception
		{
		template = CollectionTools.applyPattern(agentData, template, this, true);
		
		if(template.contains(":"))
			{
			String[] tab = template.split(":");
			number = tab[0];
			description = tab[1];
			
			if(tab.length == 3)
				{
				pickup = (tab[2].equals("true"))?true:false;
				type = sdType.blf;
				partition = UsefulMethod.getTargetOption("didpartition");//Temp
				}
			else
				{
				type = sdType.sd;
				partition = "";
				}
			}
		else
			{
			throw new Exception("ERROR : The speed dial pattern should contain the separator \":\"");
			}
		}

	public String getPartition()
		{
		return partition;
		}

	public void setPartition(String partition)
		{
		this.partition = partition;
		}

	public String getNumber()
		{
		return number;
		}

	public void setNumber(String number)
		{
		this.number = number;
		}

	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
		}

	public sdType getType()
		{
		return type;
		}

	public void setType(sdType type)
		{
		this.type = type;
		}

	public int getPosition()
		{
		return position;
		}

	public void setPosition(int position)
		{
		this.position = position;
		}

	public boolean isPickup()
		{
		return pickup;
		}

	public void setPickup(boolean pickup)
		{
		this.pickup = pickup;
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

