package com.alex.toad.uccx.items;

import java.util.ArrayList;

/**********************************
* Used to describe a UCCX Team
* 
* @author RATEL Alexandre
**********************************/
public class Team
	{
	/**
	 * Variables
	 */
	private String name;
	private ArrayList<UCCXAgent> agentList;
	private ArrayList<UCCXAgent> supervisorList;
	private UCCXAgent mainSupervisor;//Can be null
	
	/**
	 * Constructor
	 */
	public Team(String name, ArrayList<UCCXAgent> agentList,
			ArrayList<UCCXAgent> supervisorList, UCCXAgent mainSupervisor)
		{
		super();
		this.name = name;
		this.agentList = agentList;
		this.supervisorList = supervisorList;
		this.mainSupervisor = mainSupervisor;
		}

	public String getName()
		{
		return name;
		}

	public ArrayList<UCCXAgent> getAgentList()
		{
		return agentList;
		}

	public ArrayList<UCCXAgent> getSupervisorList()
		{
		return supervisorList;
		}

	public UCCXAgent getMainSupervisor()
		{
		return mainSupervisor;
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

