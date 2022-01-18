package com.alex.toad.misc;

import java.util.ArrayList;

import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.webserver.AgentData;
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
	public static boolean doAuthenticate(String userid, String password)
		{
		
		//TBW
		
		return false;
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
	public static Agent getAgent(String CUCMID)
		{
		Agent agent;
		
		/**
		 * The agent will contain a CUCM User, an associated phone and a UCCX agent profile
		 * So we have to build that up and retrieve all the informations from the CUCM and UCCX server
		 */
		/**
		 * User
		 */
		
		
		/**
		 * Phone
		 */
		
		
		/**
		 * UCCX Agent
		 * Should also contain skills and Teams
		 */
		
		
		
		return agent;
		}
	
	
	/**
	 * Used to create a new agent
	 * Will return the agent created to allow
	 * to display informations for the user
	 */
	public static Agent addAgent(String lastName,
			String firstName, Office office, AgentType agentType, ArrayList<Team> teams,
			ArrayList<Skill> skills, String deviceName, String deviceModel)
		{
		Agent agent;
		
		
		AgentData agentData = new AgentData(firstName, lastName, firstName, deviceName, deviceModel, agentType, teams, skills, office);
		
		/**
		 * CUCM part
		 * We read the creation profile to add the requested item to the list
		 */
		
		
		
		
		
		
		
		return agent;
		}
	
	/**
	 * Used to update an existing agent
	 * Will return the agent created to allow
	 * to display informations for the user
	 */
	public static Agent updateAgent(String CUCMID, String lastName,
			String firstName, AgentType agentType, ArrayList<Team> teams,
			ArrayList<Skill> skills)
		{
		Agent agent;
		
		
		
		return agent;
		}
	
	/**
	 * Used to delete an agent based on the user ID
	 */
	public static void deleteAgent(String CUCMID)
		{
		
		//TBW
		
		
		
		}
	
	/**
	 * To get all the agent
	 */
	public static ArrayList<Agent> listAgent()
		{
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		
		
		return agents;
		}
	
	
	/**
	 * To get a Team
	 */
	public static Team getTeam(String teamName)
		{
		Team team;
		
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
	 */
	public static Skill getSkill(String skillName)
		{
		Skill skill;
		
		
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
	 * This method is not mandatory. It is just an extra layer of security
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

