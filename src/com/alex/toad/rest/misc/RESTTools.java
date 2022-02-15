package com.alex.toad.rest.misc;

import java.util.ArrayList;

import com.alex.toad.misc.storedUUID;
import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.misc.UCCXTools;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.agentStatus;
import com.alex.toad.utils.Variables.itemType;
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
		String uri = "finesse/api/User/"+userID;
		String content = "<User>"
				+ "	<state>LOGOUT</state>"
				+ "</User>";
			
		String reply = Variables.getUccxServer().send(requestType.PUT, uri, content);
		
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
		String uri = "finesse/api/User/"+userID;
		String content = "";
		
		String reply = Variables.getUccxServer().send(requestType.GET, uri, content);
		
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
		String uri = "adminapi/resource";
		String content = "";
		
		String reply = Variables.getUccxServer().send(requestType.GET, uri, content);
		
		ArrayList<String> resources = xMLGear.getTextOccurences(reply, "resource");//Will return all the agent in an arraylist
		
		for(String resource : resources)
			{
			agents.add(UCCXTools.getAgentFromRESTReply(resource));
			}
		
		Variables.getLogger().debug("List agent done, "+agents.size()+" agents found");
		return agents;
		}
	
	/**
	 * Will list all UCCX team
	 * @throws Exception 
	 */
	public static ArrayList<Team> doListTeam(RESTServer host) throws Exception
		{
		ArrayList<Team> teams = new ArrayList<Team>();
		Variables.getLogger().debug("List teams request started");
		String uri = "adminapi/team";
		String content = "";
		
		String reply = Variables.getUccxServer().send(requestType.GET, uri, content);
		
		ArrayList<String> teamList = xMLGear.getTextOccurences(reply, "team");//Will return all the teams in an arraylist
		
		for(String team : teamList)
			{
			Team t = UCCXTools.getTeamFromRESTReply(team);
			teams.add(t);
			
			/**
			 * We also add the team to the UUID list to be able to look for it later
			 */
			Variables.getUuidList().add(new storedUUID(t.getUUID(), t.getName(), itemType.team));
			}
		
		Variables.getLogger().debug("List team done, "+teams.size()+" teams found");
		for(Team t : teams)
			{
			Variables.getLogger().debug(t.getName());
			}
		return teams;
		}
	
	/**
	 * Will list all UCCX skills
	 * @throws Exception 
	 */
	public static ArrayList<Skill> doListSkill(RESTServer host) throws Exception
		{
		ArrayList<Skill> skills = new ArrayList<Skill>();
		Variables.getLogger().debug("List skills request started");
		String uri = "adminapi/skill";
		String content = "";
		
		String reply = Variables.getUccxServer().send(requestType.GET, uri, content);
		
		ArrayList<String> skillList = xMLGear.getTextOccurences(reply, "skill");//Will return all the skills in an arraylist
		
		for(String skill : skillList)
			{
			Skill s = UCCXTools.getSkillFromRESTReply(skill);
			skills.add(s);
			
			/**
			 * We also add the team to the UUID list to be able to look for it later
			 */
			Variables.getUuidList().add(new storedUUID(s.getUUID(), s.getName(), itemType.skill));
			}
		
		Variables.getLogger().debug("List skill done, "+skills.size()+" skills found");
		for(Skill s : skills)
			{
			Variables.getLogger().debug(s.getName());
			}
		return skills;
		}
	
	/**
	 * Method used to find a UUID from the UCCX
	 * 
	 * In addition it stores all the UUID found to avoid to
	 * Interrogate the server twice
	 * @throws Exception 
	 */
	public static String getRESTUUIDV105(itemType type, String itemName) throws Exception
		{
		Variables.getLogger().debug("Getting UUID from UCCX : "+type+" "+itemName);
		
		if((itemName == null) || (itemName.equals("")))
			{
			return "";
			}
		
		String id = type.name()+itemName;
		
		for(storedUUID s : Variables.getUuidList())
			{
			if(s.getComparison().equals(id))
				{
				Variables.getLogger().debug("UUID known");
				Variables.getLogger().debug("Returned UUID from UUID History : "+s.getUUID());
				return s.getUUID();
				}
			}
		
		if(type.equals(itemType.agent))
			{
			String uri = "adminapi/resource/"+itemName;
			String reply = Variables.getUccxServer().send(requestType.GET, uri, "");
			
			reply = "<xml>"+reply+"</xml>";
			ArrayList<String> listParams = new ArrayList<String>();
			listParams.add("resource");
			ArrayList<String[][]> parsedReply = xMLGear.getResultListTab(reply, listParams);
			String[][] s = parsedReply.get(0);//To ease the following
			
			//UUID
			String[] temp = UsefulMethod.getItemByName("self", s).split("/");
			return getRESTReply(temp[temp.length-1],itemName, type);//The ID is the last item
			}
		else if(type.equals(itemType.team))
			{
			/**
			 * Because the getTeam works only with the team ID, we cannot get it using the team name.
			 * So first we list all the teams, then find the one we are looking for
			 */
			ArrayList<Team> teams = doListTeam(Variables.getUccxServer());
			for(Team t : teams)
				{
				if(t.getName().equals(itemName))return getRESTReply(t.getUUID(), itemName, type);
				}
			}
		else if(type.equals(itemType.skill))
			{
			/**
			 * Because the getSkill works only with the skill ID, we cannot get it using the skill name.
			 * So first we list all the skills, then find the one we are looking for
			 */
			ArrayList<Skill> skills = doListSkill(Variables.getUccxServer());
			for(Skill s : skills)
				{
				if(s.getName().equals(itemName))return getRESTReply(s.getUUID(), itemName, type);
				}
			}
		
		throw new Exception("ItemType \""+type+"\" not found");
		}
	
	/**
	 * Get a team from the UCCX server
	 * @throws Exception 
	 */
	public static Team doGetTeam(String teamName) throws Exception
		{
		for(Team t : doListTeam(Variables.getUccxServer()))
			{
			if(t.getName().equals(teamName))return t;
			}
		
		throw new Exception("Team not found : "+teamName);
		}
	
	/**
	 * Get a skill from the UCCX server
	 * @throws Exception 
	 */
	public static Skill doGetSkill(String skillName) throws Exception
		{
		for(Skill s : doListSkill(Variables.getUccxServer()))
			{
			if(s.getName().equals(skillName))return s;
			}
		
		throw new Exception("Skill not found : "+skillName);
		}
	
	/**
	 * Add the UUID to the history
	 */
	private static String getRESTReply(String UUID, String itemName, itemType type) throws Exception
		{
		Variables.getUuidList().add(new storedUUID(UUID, itemName, type));//We add the item to the uuid stored slist
		Variables.getLogger().debug("Returned UUID from UCCX : "+UUID);
		return UUID;
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}
