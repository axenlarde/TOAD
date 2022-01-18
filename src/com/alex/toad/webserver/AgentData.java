package com.alex.toad.webserver;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.alex.toad.misc.Office;
import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;

/**********************************
* Class used to store the agent data
* 
* @author RATEL Alexandre
**********************************/
public class AgentData
	{
	/**
	 * Variables
	 */
	private String firstName, lastName, lineNumber, deviceName, deviceType;
	private AgentType agentType;
	private ArrayList<Team> teamList;
	private ArrayList<Skill> skillList;
	private Office office;
	
	/**
	 * Constructor
	 */
	public AgentData(String firstName, String lastName, String lineNumber,
			String deviceName, String deviceType, AgentType agentType,
			ArrayList<Team> teamList, ArrayList<Skill> skillList, Office office)
		{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.lineNumber = lineNumber;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.agentType = agentType;
		this.teamList = teamList;
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
			else if(tab[1].equals("team"))
				{
				int index = Integer.parseInt(tab[2]);
				if(index>teamList.size())
					{
					throw new Exception("This team number doesn't exist");
					}
				else
					{
					return teamList.get(index).getString(tab[3]);
					}
				}
			}
		
		
		throw new Exception("ERROR : No value found");
		}
	
	public String getInfo()
		{
		return firstName+" "+lastName;
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

	public String getDeviceName()
		{
		return deviceName;
		}

	public String getDeviceType()
		{
		return deviceType;
		}

	public AgentType getAgentType()
		{
		return agentType;
		}

	public ArrayList<Team> getTeamList()
		{
		return teamList;
		}

	public ArrayList<Skill> getSkillList()
		{
		return skillList;
		}

	public Office getOffice()
		{
		return office;
		}

	

	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

