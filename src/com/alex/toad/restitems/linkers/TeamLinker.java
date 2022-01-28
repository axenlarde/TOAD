package com.alex.toad.restitems.linkers;

import java.util.ArrayList;

import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ErrorTemplate.errorType;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.restitems.misc.RESTItemLinker;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.misc.UCCXError;
import com.alex.toad.uccx.misc.UCCXTools;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;
import com.alex.toad.utils.Variables.requestType;


/**********************************
 * Is the RESTItem design to link the item "Team"
 * and the UCCX REST API without version dependencies
 * 
 * @author RATEL Alexandre
 **********************************/
public class TeamLinker extends RESTItemLinker
	{
	/**
	 * Variables
	 */
	private ArrayList<UCCXAgent> agentList;
	private ArrayList<UCCXAgent> supervisorList;
	private UCCXAgent mainSupervisor;//Can be null
	
	public enum toUpdate implements ToUpdate
		{
		agentList,
		supervisorList,
		mainSupervisor
		}
	
	/***************
	 * Constructor
	 * @throws Exception 
	 ***************/
	public TeamLinker(String name) throws Exception
		{
		super(name);
		}
	
	/***************
	 * Initialization
	 */
	public ArrayList<ErrorTemplate> doInitVersion105() throws Exception
		{
		ArrayList<ErrorTemplate> errorList = new ArrayList<ErrorTemplate>();
		
		//UCCXAgent
		try
			{
			for(UCCXAgent agent : agentList)
				{
				RESTTools.getRESTUUIDV105(itemType.agent, agent.getName());				
				}
			for(UCCXAgent agent : supervisorList)
				{
				RESTTools.getRESTUUIDV105(itemType.agent, agent.getName());				
				}
			RESTTools.getRESTUUIDV105(itemType.agent, mainSupervisor.getName());				
			}
		catch (Exception e)
			{
			errorList.add(new UCCXError(this.name, "", "Not found during init : "+e.getMessage(), itemType.team, itemType.agent, errorType.notFound));
			}
				
		return errorList;
		}
	/**************/
	
	/***************
	 * Delete
	 */
	public void doDeleteVersion105() throws Exception
		{
		Variables.getLogger().warn("UCCX Team deletion is not implemented");
		}
	/**************/

	/***************
	 * Injection
	 */
	public String doInjectVersion105() throws Exception
		{
		Variables.getLogger().warn("UCCX Team injection is not implemented");
		
		return null;
		}
	/**************/
	
	/***************
	 * Update
	 */
	public void doUpdateVersion105(ArrayList<ToUpdate> tuList) throws Exception
		{		
		Variables.getLogger().warn("UCCX Team update is not implemented");
		}
	/**************/
	
	
	/*************
	 * Get
	 */
	public ItemToInject doGetVersion105() throws Exception
		{
		String uri = "adminapi/team/"+name;
		String reply = Variables.getUccxServer().send(requestType.GET, uri, "");
		
		Team myT = UCCXTools.getTeamFromRESTReply(reply);
		
		return myT;//Return a Team
		}
	/****************/

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

