package com.alex.toad.uccx.items;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.alex.toad.misc.ItemToInject;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.restitems.linkers.TeamLinker;
import com.alex.toad.utils.UsefulMethod;
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
	private ArrayList<UCCXAgent> agentList;//Not used at the moment
	private ArrayList<UCCXAgent> secondarySupervisorList;
	private UCCXAgent primarySupervisor;//Can be null
	
	/**
	 * Constructor
	 * @throws Exception 
	 */
	public Team(String name) throws Exception
		{
		super(itemType.team, name);
		myTeam = new TeamLinker(name);
		}
	
	public Team(String name, ArrayList<UCCXAgent> agentList,
			ArrayList<UCCXAgent> secondarySupervisorList, UCCXAgent primarySupervisor) throws Exception
		{
		super(itemType.team, name);
		myTeam = new TeamLinker(name);
		this.agentList = agentList;
		this.secondarySupervisorList = secondarySupervisorList;
		this.primarySupervisor = primarySupervisor;
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
		myTeam.setSecondarySupervisorList(secondarySupervisorList);
		myTeam.setPrimarySupervisor(primarySupervisor);
		/************/
		
		errorList.addAll(myTeam.init());
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
		}
	
	public void doGet() throws Exception
		{
		Team myT = (Team) myTeam.get();
		UUID = myT.getUUID();
		agentList = myT.getAgentList();
		secondarySupervisorList = myT.getSecondarySupervisorList();
		primarySupervisor = myT.getPrimarySupervisor();
		
		Variables.getLogger().debug("Item "+this.name+" data fetch from the UCCX");
		}

	@Override
	public boolean doExist() throws Exception
		{
		UUID = RESTTools.getRESTUUIDV105(type, name);
		
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
		if(primarySupervisor != null)tuList.add(TeamLinker.toUpdate.primarySupervisor);
		if((secondarySupervisorList != null) && (secondarySupervisorList.size()>0))tuList.add(TeamLinker.toUpdate.secondarySupervisorList);
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

	public ArrayList<UCCXAgent> getSecondarySupervisorList()
		{
		return secondarySupervisorList;
		}

	public void setSecondarySupervisorList(
			ArrayList<UCCXAgent> secondarySupervisorList)
		{
		this.secondarySupervisorList = secondarySupervisorList;
		}

	public UCCXAgent getPrimarySupervisor()
		{
		return primarySupervisor;
		}

	public void setPrimarySupervisor(UCCXAgent primarySupervisor)
		{
		this.primarySupervisor = primarySupervisor;
		}

	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

