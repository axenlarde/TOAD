package com.alex.toad.uccx.items;

import java.util.ArrayList;

import com.alex.toad.misc.ItemToInject;
import com.alex.toad.restitems.linkers.UCCXAgentLinker;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;
import com.alex.toad.webserver.AgentData;

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
	
	private String lastname,//Name is the userID
	firstname,
	telephoneNumber;
	private UCCXAgentLinker myAgent;
	private AgentType agentType;
	private Team team;
	private ArrayList<Skill> skills;
	
	private AgentData agentData;
	
	/**
	 * Constructor
	 */
	public UCCXAgent(String userID, String lastname,
			String firstname, String telephoneNumber,
			AgentType agentType, Team team, ArrayList<Skill> skills)
		{
		super(itemType.agent, userID);
		this.lastname = lastname;
		this.firstname = firstname;
		this.telephoneNumber = telephoneNumber;
		this.agentType = agentType;
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
		/**
		 * We pass the local variables to the linker
		 */
		myAgent.setName(name);
		myAgent.setFirstname(firstname);
		myAgent.setLastname(lastname);
		myAgent.setTelephoneNumber(telephoneNumber);
		myAgent.setAgentType(this.getAgentType());
		myAgent.setSkills(this.skills);
		myAgent.setTeam(this.team);
		/************/
		
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
	 * Method used to check if the element exists in the UCCX
	 */
	public boolean isExisting() throws Exception
		{
		UCCXAgent myUA = (UCCXAgent) myAgent.get();
		UUID = myUA.getUUID();
		firstname = myUA.getFirstname();
		lastname = myUA.getLastname();
		telephoneNumber = myUA.getTelephoneNumber();
		agentType = myUA.getAgentType();
		team = myUA.getTeam();
		skills = myUA.getSkills();
		
		Variables.getLogger().debug("Item "+this.name+" exists in the UCCX");
		return true;
		}
	
	@Override
	public void resolve() throws Exception
		{
		/**
		 * In this case, there is no pattern to apply
		 * For instance, agent firstName can only be changed from the CUCM
		 */
		//So nothing to resolve
		}

	@Override
	public void manageTuList() throws Exception
		{
		if(agentType != null)tuList.add(UCCXAgentLinker.toUpdate.agentType);
		if((skills != null) && (skills.size() > 0))tuList.add(UCCXAgentLinker.toUpdate.skills);
		if(team != null)tuList.add(UCCXAgentLinker.toUpdate.team);
		}

	public AgentData getAgentData()
		{
		return agentData;
		}

	public void setAgentData(AgentData agentData)
		{
		this.agentData = agentData;
		}

	public String getLastname()
		{
		return lastname;
		}

	public String getFirstname()
		{
		return firstname;
		}

	public String getTelephoneNumber()
		{
		return telephoneNumber;
		}

	public UCCXAgentLinker getMyAgent()
		{
		return myAgent;
		}

	public AgentType getAgentType()
		{
		return agentType;
		}

	public ArrayList<Skill> getSkills()
		{
		return skills;
		}

	public void setLastname(String lastname)
		{
		this.lastname = lastname;
		}

	public void setFirstname(String firstname)
		{
		this.firstname = firstname;
		}

	public void setTelephoneNumber(String telephoneNumber)
		{
		this.telephoneNumber = telephoneNumber;
		}

	public void setMyAgent(UCCXAgentLinker myAgent)
		{
		this.myAgent = myAgent;
		}

	public void setAgentType(AgentType agentType)
		{
		this.agentType = agentType;
		}

	public void setSkills(ArrayList<Skill> skills)
		{
		this.skills = skills;
		}

	public Team getTeam()
		{
		return team;
		}

	public void setTeam(Team team)
		{
		this.team = team;
		}
	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

