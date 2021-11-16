package com.alex.toad.webserver;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.alex.toad.misc.Office;
import com.alex.toad.uccx.misc.Skill;
import com.alex.toad.utils.Variables.AgentType;

/**********************************
* Class used to store web user data
* 
* @author RATEL Alexandre
**********************************/
public class WebUser
	{
	/**
	 * Variables
	 */
	private String firstName, lastName, lineNumber, team, deviceMac, deviceType;
	private AgentType agentType; 
	private ArrayList<Skill> skillList;
	private Office office;
	
	/***************
	 * Constructeur
	 ***************/
	public WebUser(String firstName, String lastName, String lineNumber,
			String team, String deviceMac, String deviceType,
			AgentType agentType, ArrayList<Skill> skillList, Office office)
		{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.lineNumber = lineNumber;
		this.team = team;
		this.deviceMac = deviceMac;
		this.deviceType = deviceType;
		this.agentType = agentType;
		this.skillList = skillList;
		this.office = office;
		}
	
	/******
	 * Used to return a value based on the string provided
	 * @throws Exception 
	 */
	public String getString(String s) throws Exception
		{
		String tab[] = s.split("\\.");
		
		if(tab.length == 2)
			{
			for(Field f : this.getClass().getDeclaredFields())
				{
				if(f.getName().equals(tab[1]))
					{
					return (String) f.get(this);
					}
				}
			}
		else if(tab.length == 4)
			{
			//Here we treat the particular cases
			if(tab[1].equals("skill"))
				{
				int index = Integer.parseInt(tab[2]);
				if(index>skillList.size())
					{
					throw new Exception("This skill number doesn't exist");
					}
				else
					{
					return skillList.get(index).getString(tab[3]);
					}
				}
			}
		
		throw new Exception("ERROR : No value found");
		}

	public String getFirstName()
		{
		return firstName;
		}

	public String getLastName()
		{
		return lastName;
		}

	public String getLineNumber()
		{
		return lineNumber;
		}

	public String getTeam()
		{
		return team;
		}

	public String getDeviceMac()
		{
		return deviceMac;
		}

	public String getDeviceType()
		{
		return deviceType;
		}

	public AgentType getAgentType()
		{
		return agentType;
		}

	public ArrayList<Skill> getSkillList()
		{
		return skillList;
		}

	public Office getOffice()
		{
		return office;
		}
	
	
	/*2021*//*RATEL Alexandre 8)*/
	}

