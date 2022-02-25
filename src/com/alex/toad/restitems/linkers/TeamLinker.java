package com.alex.toad.restitems.linkers;

import java.util.ArrayList;

import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ErrorTemplate.errorType;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.restitems.linkers.UCCXAgentLinker.toUpdate;
import com.alex.toad.restitems.misc.RESTItemLinker;
import com.alex.toad.uccx.items.CSQ;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
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
	private ArrayList<UCCXAgent> secondarySupervisorList;
	private UCCXAgent primarySupervisor;//Can be null
	private ArrayList<CSQ> csqList;
	
	public enum toUpdate implements ToUpdate
		{
		secondarySupervisorList,
		primarySupervisor,
		agentList,
		csqList
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
			/*As the agent may not exist yet at the init stage, we should not check for it
			if(agentList != null)
				{
				for(UCCXAgent agent : agentList)
					{
					RESTTools.getRESTUUIDV105(itemType.agent, agent.getName());				
					}
				}
			if(secondarySupervisorList != null)
				{
				for(UCCXAgent agent : secondarySupervisorList)
					{
					RESTTools.getRESTUUIDV105(itemType.agent, agent.getName());				
					}
				}
			if(primarySupervisor != null)RESTTools.getRESTUUIDV105(itemType.agent, primarySupervisor.getName());				
			*/
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
		String teamID = RESTTools.getRESTUUIDV105(itemType.team, name);
		String uri = "adminapi/team/"+teamID;
		StringBuffer content = new StringBuffer();
		
		content.append("	<team>\r\n");
		
		/**
		 * The following cannot be updated here but is mandatory
		 * for the server to accept the request
		 */
		content.append("		<teamId>"+teamID+"</teamId>\r\n");
		content.append("		<teamname>"+name+"</teamname>\r\n");
		/******/
		
		if(tuList.contains(toUpdate.primarySupervisor))content.append(UCCXTools.getRESTPrimarySupFromAgent(primarySupervisor));
		if(tuList.contains(toUpdate.secondarySupervisorList))
			{
			content.append("		<secondarySupervisors>\r\n");
			for(UCCXAgent ua : secondarySupervisorList)
				{
				content.append(UCCXTools.getRESTSecondarySupFromAgent(ua));
				}
			content.append("		</secondarySupervisors>\r\n");
			}
		
		if(tuList.contains(toUpdate.agentList))
			{
			content.append("		<resources>\r\n");
			for(UCCXAgent ua : agentList)
				{
				content.append(UCCXTools.getRESTResourceFromAgent(ua));
				}
			content.append("		</resources>\r\n");
			}
		
		if(tuList.contains(toUpdate.csqList))
			{
			content.append("		<csqs>\r\n");
			for(CSQ q : csqList)
				{
				content.append(UCCXTools.getRESTCSQFromCSQ(q));
				}
			content.append("		</csqs>\r\n");
			}
		
		content.append("	</team>\r\n");
		
		String reply = Variables.getUccxServer().send(requestType.PUT, uri, content.toString());
		}
	/**************/
	
	
	/*************
	 * Get
	 */
	public ItemToInject doGetVersion105() throws Exception
		{
		return RESTTools.doGetTeam(this.name);
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

