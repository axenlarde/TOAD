package com.alex.toad.uccx.items;

import java.util.ArrayList;

import com.alex.toad.axlitems.linkers.UserLinker;
import com.alex.toad.cucm.user.items.User;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.restitems.linkers.UCCXAgentLinker;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;

/**********************************
* Used to describe a UCCX agent
* 
* @author RATEL Alexandre
**********************************/
public class UCCXAgent extends ItemToInject
	{
	/**
	 * Variables
	 */
	public enum AgentType
		{
		agent,
		supervisor
		};
	
	private UCCXAgentLinker myAgent;
	private AgentType agentType;
	private User user;
	private Team team;
	private ArrayList<Skill> skills;
	/**
	 * Constructor
	 */
	public UCCXAgent(AgentType type, User user, Team team, ArrayList<Skill> skills)
		{
		super(itemType.agent, user.getName());
		this.agentType = type;
		this.user = user;
		this.team = team;
		this.skills = skills;
		}
	
	public UCCXAgent(String userID)
		{
		super(itemType.agent, userID);
		}
	
	/**
	 * To prepare the item
	 */
	public void doBuild() throws Exception
		{
		//TBW
		errorList.addAll(myAgent.init());
		}

	/**
	 * To inject the item
	 */
	public String doInject() throws Exception
		{
		return myAgent.inject();//Return UUID
		/**
		 * theoretically, a UCCX Agent can be created only from the CUCM
		 * using AXL. So this method might never be used
		 */
		}

	public void doDelete() throws Exception
		{
		myAgent.delete();
		/**
		 * theoretically, a UCCX Agent can be deleted only from the CUCM
		 * using AXL. So this method might never be used
		 */
		}

	public void doUpdate() throws Exception
		{
		myAgent.update(tuList);
		}

	/**
	 * Method used to check if the element exist in the UCCX
	 */
	public boolean isExisting() throws Exception
		{
		UCCXAgent myUA = (UCCXAgent) myAgent.get();
		this.UUID = myUA.getUUID();
		//Has to be written
		
		Variables.getLogger().debug("Item "+this.name+" already exist in the UCCX");
		return true;
		}

	public String getInfo()
		{
		return name+" "
		+UUID;
		}
	
	@Override
	public void resolve() throws Exception
		{
		//TBW
		myAgent.setName(this.getName());
		myAgent.setAgentType(this.getAgentType());
		myAgent.setSkills(this.skills);
		myAgent.setTeam(this.team);
		}

	@Override
	public void manageTuList() throws Exception
		{
		if(agentType != null)tuList.add(UCCXAgentLinker.toUpdate.agentType);
		if((skills != null) && (skills.size() > 0))tuList.add(UCCXAgentLinker.toUpdate.skills);
		if(team != null)tuList.add(UCCXAgentLinker.toUpdate.team);
		}
	
	public AgentType getAgentType()
		{
		return agentType;
		}
	public User getUser()
		{
		return user;
		}
	public Team getTeam()
		{
		return team;
		}
	public ArrayList<Skill> getSkills()
		{
		return skills;
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

