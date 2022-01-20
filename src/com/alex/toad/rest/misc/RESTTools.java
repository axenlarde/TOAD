package com.alex.toad.rest.misc;

import java.util.ArrayList;

import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.agentStatus;
import com.alex.toad.utils.Variables.requestType;
import com.alex.toad.utils.xMLGear;


public class RESTTools
	{
	
	/**
	 * To logout a finesse user
	 * 
	 * Need a supervisor account
	 */
	public static void AgentLogout(RESTServer host, String userID) throws Exception
		{
		Variables.getLogger().debug("Agent "+userID+" logout request started");
		String uri = "https://"+host.getHost()+":"+host.getPort()+"/finesse/api/User/"+userID;
		String content = "<User>"
				+ "	<state>LOGOUT</state>"
				+ "</User>";
			
		String reply = RESTGear.send(requestType.PUT, uri, content, host.getUsername(), host.getPassword(), host.getTimeout());//To logout the agent
		
		Variables.getLogger().debug("Agent "+userID+" logout done !");
		}
	
	/**
	 * To get the given agent current status
	 * 
	 * Need administrator account
	 * 
	 * @throws Exception 
	 */
	public static agentStatus getAgentStatus(RESTServer host, String userID) throws Exception
		{
		Variables.getLogger().debug("Agent "+userID+" get status request started");
		String uri = "https://"+host.getHost()+":"+host.getPort()+"/finesse/api/User/"+userID;
		String content = "";
		
		String reply = RESTGear.send(requestType.GET, uri, content, host.getUsername(), host.getPassword(), host.getTimeout());//To logout the agent
		
		//We parse the reply
		reply = "<xml>"+reply+"</xml>";
		ArrayList<String> listParams = new ArrayList<String>();
		listParams.add("User");
		ArrayList<String[][]> parsedReply = xMLGear.getResultListTab(reply, listParams);
		agentStatus status = UsefulMethod.convertStringToAgentStatus(UsefulMethod.getItemByName("state", parsedReply.get(0)));
		
		Variables.getLogger().debug("Agent "+userID+" status retreived : "+status.name());
		return status;
		}
	
	/**
	 * Will list all UCCX agent
	 * @throws Exception 
	 */
	public static ArrayList<UCCXAgent> doListAgent(RESTServer host) throws Exception
		{
		ArrayList<UCCXAgent> agents = new ArrayList<UCCXAgent>();
		Variables.getLogger().debug("List agent request started");
		String uri = "https://"+host.getHost()+":"+host.getPort()+"adminapi/resource";
		String content = "";
		
		String reply = RESTGear.send(requestType.GET, uri, content, host.getUsername(), host.getPassword(), host.getTimeout());//To logout the agent
		
		//We parse the reply
		reply = "<xml>"+reply+"</xml>";
		ArrayList<String> listParams = new ArrayList<String>();
		listParams.add("resources");
		listParams.add("resource");
		ArrayList<String[][]> parsedReply = xMLGear.getResultListTabAndAtt(reply, listParams);
		
		for(String[][] s : parsedReply)
			{
			String[] temp = UsefulMethod.getItemByName("self", s).split("/");
			String UUID = temp[temp.length-1];//The ID is the last item
			AgentType type = (UsefulMethod.getItemByName("type", s)).equals("1")?AgentType.agent:AgentType.supervisor;
			
			//Team
			String teamName = UsefulMethod.getAttributeItemByName("team", s);
			ArrayList<Team> teams = new ArrayList<Team>();
			teams.add(new Team(teamName));
			//Manage the primary and secondary supervisor
			
			//Skill
			ArrayList<Skill> skills = new ArrayList<Skill>();
			listParams.add("skillMap");
			listParams.add("skillCompetency");
			ArrayList<String[][]> skillReply = xMLGear.getResultListTabAndAtt(reply, listParams);
			
			for(String[][] t : skillReply)
				{
				String skillName = UsefulMethod.getAttributeItemByName("skillNameUriPair", t);
				skills.add(new Skill(skillName, Integer.parseInt(UsefulMethod.getItemByName("competencelevel", t))));
				}
			
			UCCXAgent agent = new UCCXAgent(UsefulMethod.getItemByName("userID", s),
					UsefulMethod.getItemByName("lastName", s),
					UsefulMethod.getItemByName("firstName", s),
					UsefulMethod.getItemByName("extension", s),
					type,
					teams,
					skills);
			
			agents.add(agent);
			}
		
		Variables.getLogger().debug("List agent done, "+agents.size()+" agents found");
		return agents;
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}
