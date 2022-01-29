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
	private ArrayList<Team> primarySupervisorOf;
	private ArrayList<Team> secondarySupervisorOf;
	private ArrayList<Skill> skills;
	
	/**
	 * Constructor
	 * @throws Exception 
	 */
	public UCCXAgent(String name, String lastname,
			String firstname, String telephoneNumber,
			AgentType agentType, Team team, ArrayList<Team> primarySupervisorOf,
			ArrayList<Team> secondarySupervisorOf, ArrayList<Skill> skills) throws Exception
		{
		super(itemType.agent, name);
		myAgent = new UCCXAgentLinker(name);
		this.lastname = lastname;
		this.firstname = firstname;
		this.telephoneNumber = telephoneNumber;
		this.agentType = agentType;
		this.team = team;
		this.primarySupervisorOf = primarySupervisorOf;
		this.secondarySupervisorOf = secondarySupervisorOf;
		this.skills = skills;
		}

	public UCCXAgent(String userID) throws Exception
		{
		super(itemType.agent, userID);
		myAgent = new UCCXAgentLinker(name);
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
		myAgent.setPrimarySupervisorOf(primarySupervisorOf);
		myAgent.setSecondarySupervisorOf(secondarySupervisorOf);
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
		primarySupervisorOf = myUA.getPrimarySupervisorOf();
		secondarySupervisorOf = myUA.getSecondarySupervisorOf();
		
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
		if((primarySupervisorOf != null) && (primarySupervisorOf.size() > 0))tuList.add(UCCXAgentLinker.toUpdate.primarySupervisorOf);
		if((secondarySupervisorOf != null) && (secondarySupervisorOf.size() > 0))tuList.add(UCCXAgentLinker.toUpdate.secondarySupervisorOf);
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

