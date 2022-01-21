package com.alex.toad.uccx.items;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.alex.toad.misc.ItemToInject;
import com.alex.toad.restitems.linkers.TeamLinker;
import com.alex.toad.utils.Variables;
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
	private TeamLinker myTeam;
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
		/*String tab[] = s.split("\\.");
		
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
			*/
		
		for(Field f : this.getClass().getDeclaredFields())
			{
			if(f.getName().toLowerCase().equals(s.toLowerCase()))
				{
				return (String) f.get(this);
				}
			}
		
		throw new Exception("ERROR : No value found");
		}

	@Override
	public void doBuild() throws Exception
		{
		/**
		 * We pass the local variables to the linker
		 */
		myTeam.setAgentList(agentList);
		myTeam.setSupervisorList(supervisorList);
		myTeam.setMainSupervisor(mainSupervisor);
		/************/
		}

	@Override
	public String doInject() throws Exception
		{
		return myTeam.inject();//Return UUID
		//Not implemented
		}

	@Override
	public void doDelete() throws Exception
		{
		myTeam.delete();
		//Not implemented
		}

	@Override
	public void doUpdate() throws Exception
		{
		myTeam.update(tuList);
		//Not implemented
		}

	@Override
	public boolean isExisting() throws Exception
		{
		Team myT = (Team) myTeam.get();
		agentList = myT.getAgentList();
		supervisorList = myT.getSupervisorList();
		mainSupervisor = myT.getMainSupervisor();
		
		Variables.getLogger().debug("Item "+this.name+" exists in the UCCX");
		return true;
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

	public TeamLinker getMyTeam()
		{
		return myTeam;
		}

	public void setMyTeam(TeamLinker myTeam)
		{
		this.myTeam = myTeam;
		}

	public ArrayList<UCCXAgent> getAgentList()
		{
		return agentList;
		}

	public void setAgentList(ArrayList<UCCXAgent> agentList)
		{
		this.agentList = agentList;
		}

	public ArrayList<UCCXAgent> getSupervisorList()
		{
		return supervisorList;
		}

	public void setSupervisorList(ArrayList<UCCXAgent> supervisorList)
		{
		this.supervisorList = supervisorList;
		}

	public UCCXAgent getMainSupervisor()
		{
		return mainSupervisor;
		}

	public void setMainSupervisor(UCCXAgent mainSupervisor)
		{
		this.mainSupervisor = mainSupervisor;
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

