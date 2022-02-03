package com.alex.toad.restitems.linkers;

import java.util.ArrayList;

import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ErrorTemplate.errorType;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.restitems.misc.RESTItemLinker;
import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.uccx.misc.UCCXError;
import com.alex.toad.uccx.misc.UCCXTools;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;
import com.alex.toad.utils.Variables.requestType;


/**********************************
 * Is the RESTItem design to link the item "UCCXAgent"
 * and the UCCX REST API without version dependencies
 * 
 * @author RATEL Alexandre
 **********************************/
public class UCCXAgentLinker extends RESTItemLinker
	{
	/**
	 * Variables
	 */
	private String lastname,//Name is the userID
	firstname,
	telephoneNumber;
	private AgentType agentType;
	private Team team;
	private ArrayList<Team> primarySupervisorOf;
	private ArrayList<Team> secondarySupervisorOf;
	private ArrayList<Skill> skills;
	
	public enum toUpdate implements ToUpdate
		{
		agentType,
		team,
		primarySupervisorOf,
		secondarySupervisorOf,
		skills
		}
	
	/***************
	 * Constructor
	 * @throws Exception 
	 ***************/
	public UCCXAgentLinker(String name) throws Exception
		{
		super(name);
		}
	
	/***************
	 * Initialization
	 */
	public ArrayList<ErrorTemplate> doInitVersion105() throws Exception
		{
		ArrayList<ErrorTemplate> errorList = new ArrayList<ErrorTemplate>();
		
		//Teams
		try
			{
			team.setUUID(RESTTools.getRESTUUIDV105(itemType.team, team.getName()));
			for(Team t : primarySupervisorOf)t.setUUID(RESTTools.getRESTUUIDV105(itemType.team, t.getName()));
			for(Team t : secondarySupervisorOf)t.setUUID(RESTTools.getRESTUUIDV105(itemType.team, t.getName()));
			}
		catch (Exception e)
			{
			errorList.add(new UCCXError(this.name, "", "Not found during init : "+e.getMessage(), itemType.agent, itemType.team, errorType.notFound));
			}
		
		//Skills
		try
			{
			for(Skill s : skills)
				{
				s.setUUID(RESTTools.getRESTUUIDV105(itemType.skill, s.getName()));
				}
			}
		catch (Exception e)
			{
			errorList.add(new UCCXError(this.name, "", "Not found during init : "+e.getMessage(), itemType.agent, itemType.skill, errorType.notFound));
			}
		
				
		return errorList;
		}
	/**************/
	
	/***************
	 * Delete
	 */
	public void doDeleteVersion105() throws Exception
		{
		Variables.getLogger().warn("A UCCX Agent can only be deleted from the CUCM. So this method does nothing and should not be used");
		}
	/**************/

	/***************
	 * Injection
	 */
	public String doInjectVersion105() throws Exception
		{
		Variables.getLogger().warn("A UCCX Agent can only be created from the CUCM. So this method does nothing and should not be used");
		
		return null;
		}
	/**************/
	
	/***************
	 * Update
	 */
	public void doUpdateVersion105(ArrayList<ToUpdate> tuList) throws Exception
		{		
		String uri = "https://"+Variables.getUccxServer().getHost()+":"+Variables.getUccxServer().getPort()+"adminapi/resource/"+name;
		StringBuffer content = new StringBuffer();
		
		content.append("<resources>\r\n");//A valider
		content.append("	<resource>\r\n");
		
		if(tuList.contains(toUpdate.agentType))content.append("		<type>"+UsefulMethod.convertAgentTypeToInt(agentType)+"</type>\r\n");
		if(tuList.contains(toUpdate.team))content.append(UCCXTools.getRESTFromTeam(team));
		if(tuList.contains(toUpdate.primarySupervisorOf))
			{
			content.append("<primarySupervisorOf>");
			for(Team t : primarySupervisorOf)
				{
				content.append(UCCXTools.getRESTFromTeam(t));
				}
			content.append("</primarySupervisorOf>");
			}
		if(tuList.contains(toUpdate.secondarySupervisorOf))
			{
			content.append("<secondarySupervisorOf>");
			for(Team t : secondarySupervisorOf)
				{
				content.append(UCCXTools.getRESTFromTeam(t));
				}
			content.append("</secondarySupervisorOf>");
			}
		if(tuList.contains(toUpdate.skills))content.append(UCCXTools.getRESTFromSkills(skills));
		
		content.append("	</resource>\r\n");
		content.append("</resources>\r\n");
		
		String reply = Variables.getUccxServer().send(requestType.PUT, uri, content.toString());
		}
	/**************/
	
	
	/*************
	 * Get
	 */
	public ItemToInject doGetVersion105() throws Exception
		{
		String uri = "adminapi/resource/"+name;
		String reply = Variables.getUccxServer().send(requestType.GET, uri, "");
		
		reply = reply.replace("<resources>", "");
		reply = reply.replace("</resources>", "");
		
		UCCXAgent myUA = UCCXTools.getAgentFromRESTReply(reply);
		
		return myUA;//Return a UCCXAgent
		}
	/****************/

	public AgentType getAgentType()
		{
		return agentType;
		}

	public ArrayList<Skill> getSkills()
		{
		return skills;
		}

	public void setAgentType(AgentType agentType)
		{
		this.agentType = agentType;
		}

	public void setSkills(ArrayList<Skill> skills)
		{
		this.skills = skills;
		}

	public String getLastname()
		{
		return lastname;
		}

	public void setLastname(String lastname)
		{
		this.lastname = lastname;
		}

	public String getFirstname()
		{
		return firstname;
		}

	public void setFirstname(String firstname)
		{
		this.firstname = firstname;
		}

	public String getTelephoneNumber()
		{
		return telephoneNumber;
		}

	public void setTelephoneNumber(String telephoneNumber)
		{
		this.telephoneNumber = telephoneNumber;
		}

	public Team getTeam()
		{
		return team;
		}

	public void setTeam(Team team)
		{
		this.team = team;
		}

	public ArrayList<Team> getPrimarySupervisorOf()
		{
		return primarySupervisorOf;
		}

	public void setPrimarySupervisorOf(ArrayList<Team> primarySupervisorOf)
		{
		this.primarySupervisorOf = primarySupervisorOf;
		}

	public ArrayList<Team> getSecondarySupervisorOf()
		{
		return secondarySupervisorOf;
		}

	public void setSecondarySupervisorOf(ArrayList<Team> secondarySupervisorOf)
		{
		this.secondarySupervisorOf = secondarySupervisorOf;
		}

	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

