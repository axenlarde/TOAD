package com.alex.toad.misc;

import java.util.ArrayList;

import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;

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
			String firstName, AgentType agentType, ArrayList<Team> teams,
			ArrayList<Skill> skills, String deviceType, String deviceName, String deviceModel)
		{
		Agent agent;
		
		/**
		 * To create a new agent we will create both the CUCM and the UCCX part
		 * We will follow the user creation profile to proceed
		 */
		/**
		 * CUCM part
		 */
		
		
		
		/**
		 * UCCX part
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
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

