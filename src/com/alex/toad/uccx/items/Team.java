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
	private ArrayList<UCCXAgent> agentList;
	private ArrayList<UCCXAgent> secondarySupervisorList;
	private UCCXAgent primarySupervisor;//Can be null
	private ArrayList<CSQ> csqList;
	
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
		myTeam.setAgentList(agentList);
		myTeam.setCsqList(csqList);
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
		primarySupervisor = myT.getPrimarySupervisor();
		secondarySupervisorList = myT.getSecondarySupervisorList();
		agentList = myT.getAgentList();
		csqList = myT.getCsqList();
		
		Variables.getLogger().debug("Item "+this.name+" data fetch from the UCCX");
		}

	@Override
	public boolean doExist() throws Exception
		{
		UUID = RESTTools.getRESTUUIDV105(type, name);
		
		Variables.getLogger().debug("Item "+this.name+" exists in the UCCX");
		return true;
		}
	
	public String getInfo()
		{
		return UsefulMethod.convertItemTypeToVerbose(type)+" "+name;
		}

	@Override
	public void resolve() throws Exception
		{
		//Not implemented
		}

	@Override
	public void manageTuList() throws Exception
		{
		if(primarySupervisor != null)tuList.add(TeamLinker.toUpdate.primarySupervisor);
		if((secondarySupervisorList != null) && (secondarySupervisorList.size()>0))tuList.add(TeamLinker.toUpdate.secondarySupervisorList);
		if((agentList != null) && (agentList.size()>0))tuList.add(TeamLinker.toUpdate.agentList);
		if((csqList != null) && (csqList.size()>0))tuList.add(TeamLinker.toUpdate.csqList);
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

	public ArrayList<CSQ> getCsqList()
		{
		return csqList;
		}

	public void setCsqList(ArrayList<CSQ> csqList)
		{
		this.csqList = csqList;
		}

	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

