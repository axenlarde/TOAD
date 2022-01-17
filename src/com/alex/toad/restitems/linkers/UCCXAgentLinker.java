package com.alex.toad.restitems.linkers;

import java.util.ArrayList;

import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.cucm.user.items.User;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.restitems.misc.RESTItemLinker;
import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.utils.Variables;


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
	private AgentType agentType;
	private User user;
	private ArrayList<Team> teams;
	private ArrayList<Skill> skills;
	
	public enum toUpdate implements ToUpdate
		{
		agentType,
		team,
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
		
		//CSS
		try
			{
			
			}
		catch (Exception e)
			{
			//errorList.add(new UCCXError(this.name, "", "Not found during init : "+e.getMessage(), itemType.agent, itemType.callingsearchspace, errorType.notFound));
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
		
		
		/***********
		 * We set the item parameters
		 */
		
		
		if(tuList.contains(toUpdate.agentType));//To be written
		if(tuList.contains(toUpdate.team));//To be written
		if(tuList.contains(toUpdate.skills));//To be written
		
		/************/
		
		}
	/**************/
	
	
	/*************
	 * Get
	 */
	public ItemToInject doGetVersion105() throws Exception
		{
		
		/******************
		 * We set the item parameters
		 */
		
		/************/
		
		
		UCCXAgent myUA = new UCCXAgent(this.getName());
		//TBW
		
		return myUA;//Return a UCCXAgent
		}
	/****************/

	public AgentType getAgentType()
		{
		return agentType;
		}

	public User getUser()
		{
		return user;
		}

	public ArrayList<Team> getTeam()
		{
		return teams;
		}

	public ArrayList<Skill> getSkills()
		{
		return skills;
		}

	public void setAgentType(AgentType agentType)
		{
		this.agentType = agentType;
		}

	public void setUser(User user)
		{
		this.user = user;
		}

	public void setTeams(ArrayList<Team> teams)
		{
		this.teams = teams;
		}

	public void setSkills(ArrayList<Skill> skills)
		{
		this.skills = skills;
		}

	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

