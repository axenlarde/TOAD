package com.alex.toad.misc;

import java.util.ArrayList;


import com.alex.toad.cucm.user.items.DeviceProfile;
import com.alex.toad.cucm.user.items.Line;
import com.alex.toad.cucm.user.items.Phone;
import com.alex.toad.cucm.user.items.User;
import com.alex.toad.cucm.user.misc.UserCreationProfile;
import com.alex.toad.cucm.user.misc.UserTools;
import com.alex.toad.soap.items.PhoneLine;
import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.actionType;
import com.alex.toad.webserver.AgentData;
import com.alex.toad.webserver.ManageWebRequest.webRequestType;
import com.alex.toad.webserver.WebRequest;

/**********************************
* Class used to gather static method about agents
* 
* @author RATEL Alexandre
**********************************/
public class AgentTools
	{
	
	/**
	 * Will authenticate the user through AXL
	 * So the user and credentials must be valid in the CUCM
	 */
	public static AgentData doAuthenticate(String userid, String password) throws Exception
		{
		if(SimpleRequest.doAuthenticate(userid, password))
			{
			//Successful
			return new AgentData(userid);
			}
		else
			{
			//Failed
			throw new Exception(userid+" : Failed to authenticate");
			}
		}
	
	/**
	 * Will look for a list of agents matching the search string
	 */
	public static ArrayList<Agent> search(String search)
		{
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		//TBW
		
		return agents;
		}
	
	
	
	/**
	 * Will get the data about agent, contacting CUCM and UCCX for that
	 */
	public static AgentData getAgent(String userID) throws Exception
		{
		AgentData agentData = new AgentData(userID);
		
		/**
		 * We get the User from the CUCM
		 */
		User myUser = new User(userID);
		myUser.isExisting();
		agentData.setFirstName(myUser.getFirstname());
		agentData.setLastName(myUser.getLastname());
		agentData.setLineNumber(myUser.getIpccExtension());
		
		/**
		 * We get the associated devices from the CUCM
		 */
		agentData.setDeviceList(myUser.getDeviceList());
		agentData.setUDPList(myUser.getUDPList());
		
		/**
		 * We get the agent Teams and skills from the UCCX 
		 */
		UCCXAgent agent = new UCCXAgent(userID);
		agent.isExisting();
		agentData.setAgentType(agent.getAgentType());
		agentData.setSkillList(agent.getSkills());
		agentData.setTeamList(agent.getTeams());
		
		return agentData;
		}
	
	
	/**
	 * Used to create a new agent
	 * Will return the taskID
	 */
	public static String addAgent(String lastName,
			String firstName, Office office, AgentType agentType, ArrayList<Team> teams,
			ArrayList<Skill> skills, String deviceName, String deviceModel, boolean udpLogin) throws Exception
		{
		AgentData agentData = new AgentData("", firstName, lastName, "", deviceName, deviceModel, agentType, teams, skills, office);
		ArrayList<ItemToInject> itil = new ArrayList<ItemToInject>();//The Item to Inject List
		
		/**
		 * CUCM items
		 * Listed in the User Creation Profile : Phone, Line, UDP and so on...
		 */
		UserCreationProfile ucp = UsefulMethod.getUserCreationProfile(UsefulMethod.getTargetOption("addagentusercreationprofilename"));
		Variables.getLogger().debug("The User Creation Profile used for Agent creation is : "+ucp.getName());
		
		//All the User Creation Profile items are now added to the injection list 
		itil.addAll(UserTools.getUserItemList(agentData, actionType.inject, ucp, udpLogin));
		
		/**
		 * UCCX items
		 * To create the Agent
		 */
		UCCXAgent agent = new UCCXAgent(deviceName, lastName, firstName, deviceModel, agentType, teams, skills);
		agent.setAgentData(agentData);
		agent.setAction(actionType.inject);
		agent.resolve();
		
		itil.add(agent);
		
		/**
		 * We now launch the injection process
		 */
		String taskID = TaskManager.addNewTask(itil, webRequestType.addAgent);
		Variables.getLogger().debug(agentData.getInfo()+" : Add agent task started, task ID is : "+taskID);
		
		return taskID;
		}
	
	/**
	 * Used to update an existing agent
	 * Will return the taskID
	 * @throws Exception 
	 */
	public static String updateAgent(String userID, String lastName,
			String firstName, Office office, AgentType agentType, ArrayList<Team> teams,
			ArrayList<Skill> skills, String deviceName, boolean udpLogin) throws Exception
		{
		AgentData agentData = new AgentData(userID, firstName, lastName, "", deviceName, "", agentType, teams, skills, office);
		ArrayList<ItemToInject> itil = new ArrayList<ItemToInject>();//The Item to Inject List
		
		/**
		 * CUCM items
		 * Listed in the User Creation Profile : Phone, Line, UDP and so on...
		 */
		UserCreationProfile ucp = UsefulMethod.getUserCreationProfile(UsefulMethod.getTargetOption("updateAgent"));
		Variables.getLogger().debug("The User Creation Profile used for Agent update is : "+ucp.getName());
		
		//All the User Creation Profile items are now added to the injection list 
		itil.addAll(UserTools.getUserItemList(agentData, actionType.update, ucp, udpLogin));
		
		/**
		 * UCCX items
		 * To create the Agent
		 */
		UCCXAgent agent = new UCCXAgent(deviceName, lastName, firstName, "", agentType, teams, skills);
		agent.setAgentData(agentData);
		agent.setAction(actionType.update);
		agent.resolve();
		
		itil.add(agent);
		
		/**
		 * We now launch the injection process
		 */
		String taskID = TaskManager.addNewTask(itil, webRequestType.updateAgent);
		Variables.getLogger().debug(agentData.getInfo()+" : Update agent task started, task ID is : "+taskID);

		return taskID;
		}
	
	/**
	 * Used to delete an agent based on the user ID
	 * @throws Exception 
	 */
	public static String deleteAgent(String userID) throws Exception
		{
		AgentData agentData = new AgentData(userID);
		ArrayList<ItemToInject> itil = new ArrayList<ItemToInject>();//The Item to delete List
		
		/**
		 * We get the User from the CUCM
		 */
		User myUser = new User(userID);
		myUser.isExisting();//Will trigger information fetch
		agentData.setFirstName(myUser.getFirstname());
		agentData.setLastName(myUser.getLastname());
		agentData.setLineNumber(myUser.getIpccExtension());
		
		myUser.setAction(actionType.delete);
		itil.add(myUser);
		
		/**
		 * We get the associated devices from the CUCM
		 * 
		 * In addition, we want to be sure that UDPList and ctiUDPList contains the same items
		 * if not we just add the missing one to the list to be sure
		 * to delete all the associated udp
		 */
		agentData.setDeviceList(myUser.getDeviceList());
		agentData.setUDPList(myUser.getUDPList());
		
		for(String ctiudp : myUser.getCtiUDPList())
			{
			if(!agentData.getUDPList().contains(ctiudp))agentData.getUDPList().add(ctiudp);
			}
		
		/**
		 * We then create the associated axl items
		 */
		for(String d : agentData.getDeviceList())
			{
			Phone phone = new Phone(d);//The phone name is sufficient to delete it
			phone.isExisting();//Will trigger information fetch
			phone.setAction(actionType.delete);
			itil.add(phone);
			//We also get the associated line
			for(PhoneLine pl : phone.getLineList())
				{
				Line l = new Line(pl.getLineNumber(), pl.getRoutePartition());
				l.setAction(actionType.delete);
				itil.add(l);
				}
			}
		
		for(String udp : agentData.getUDPList())
			{
			DeviceProfile dp = new DeviceProfile(udp);//The udp name is sufficient to delete it
			dp.isExisting();
			dp.setAction(actionType.delete);
			itil.add(dp);
			//We also get the associated line
			for(PhoneLine pl : dp.getLineList())
				{
				Line l = new Line(pl.getLineNumber(), pl.getRoutePartition());
				l.setAction(actionType.delete);
				itil.add(l);
				}
			}
		
		/**
		 * Nothing to do from the UCCX side
		 * Deleting the user from the CUCM also delete it from the UCCX
		 */
		
		/**
		 * We now launch the injection process
		 */
		String taskID = TaskManager.addNewTask(itil, webRequestType.deleteAgent);
		Variables.getLogger().debug(agentData.getInfo()+" : delete agent task started, task ID is : "+taskID);
		
		return taskID;
		}
	
	/**
	 * To get all the agent
	 */
	public static ArrayList<AgentData> listAgent()
		{
		ArrayList<AgentData> agents = new ArrayList<AgentData>();
		
		
		
		return agents;
		}
	
	
	/**
	 * To get a Team
	 * @throws Exception 
	 */
	public static Team getTeam(String teamName) throws Exception
		{
		Team team = new Team(teamName);
		team.isExisting();//Will trigger information fetch
		return team;
		}
	
	/**
	 * To get all the team
	 */
	public static ArrayList<Team> listTeam()
		{
		ArrayList<Team> teams = new ArrayList<Team>();
		
		
		
		return teams;
		}
	
	/**
	 * To get a skill
	 * @throws Exception 
	 */
	public static Skill getSkill(String skillName) throws Exception
		{
		Skill skill = new Skill(skillName);
		skill.isExisting();//Will trigger information fetch
		return skill;
		}
	
	public static ArrayList<Skill> listSkill()
		{
		ArrayList<Skill> skills = new ArrayList<Skill>();
		
		
		return skills;
		}
	
	/**
	 * To check if the request should be allowed
	 * 
	 * Based on the securityToken provided
	 * Throw an exception if not allowed
	 * 
	 * This method is not mandatory because the user already
	 * sees only allowed data
	 * 
	 * So It is just an extra layer of security in case somebody
	 * craft a request containing not allowed items
	 * 
	 * Therefore it will be written later
	 */
	public static boolean isAllowed(WebRequest request) throws Exception
		{
		
		//TBW
		//throw new Exception("Request not allowed");
		
		return true;
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

