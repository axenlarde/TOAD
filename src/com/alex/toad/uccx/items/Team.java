package com.alex.toad.uccx.items;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.alex.toad.misc.ItemToInject;
import com.alex.toad.utils.Variables.itemType;

/**********************************
* Used to describe a UCCX Team
* 
* @author RATEL Alexandre
**********************************/
public class Team extends ItemToInject
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
	public Team(String name)
		{
		super(itemType.team, name);
		}
	
	public Team(String name, ArrayList<UCCXAgent> agentList,
			ArrayList<UCCXAgent> supervisorList, UCCXAgent mainSupervisor)
		{
		super(itemType.team, name);
		this.agentList = agentList;
		this.supervisorList = supervisorList;
		this.mainSupervisor = mainSupervisor;
		}
	
	public String getString(String s) throws Exception
		{
		String tab[] = s.split("\\.");
		
		if(tab.length == 2)
			{
			for(Field f : this.getClass().getDeclaredFields())
				{
				if(f.getName().toLowerCase().equals(tab[1].toLowerCase()))
					{
					return (String) f.get(this);
					}
				}
			}
		throw new Exception("ERROR : No value found");
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

	@Override
	public void doBuild() throws Exception
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public String doInject() throws Exception
		{
		// TODO Auto-generated method stub
		return null;
		}

	@Override
	public void doDelete() throws Exception
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public void doUpdate() throws Exception
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public boolean isExisting() throws Exception
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public void resolve() throws Exception
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public void manageTuList() throws Exception
		{
		// TODO Auto-generated method stub
		
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

