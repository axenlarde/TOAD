package com.alex.toad.uccx.misc;

import java.util.ArrayList;

import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.xMLGear;
import com.alex.toad.utils.Variables.actionType;
import com.alex.toad.webserver.AgentData;

/**********************************
* Used to store static method about UCCX
* 
* @author RATEL Alexandre
**********************************/
public class UCCXTools
	{
	
	/**
	 * Used to create a UCCX Agent item
	 */
	public static UCCXAgent getAgent(AgentData ad, actionType action)
		{
		return null;
		}
	
	
	
	/**
	 * Parse the REST reply and return a UCCX Agent
	 * 
	 * Should start and end with <resource>
	 * @throws Exception 
	 */
	public static UCCXAgent getAgentFromRESTReply(String resource) throws Exception
		{
		//We parse the XML resource
		resource = "<xml>\r\n"+resource+"\r\n</xml>";
		ArrayList<String> listParams = new ArrayList<String>();
		listParams.add("resource");
		ArrayList<String[][]> parsedReply = xMLGear.getResultListTabAndAtt(resource, listParams);
		listParams.add("skillMap");
		listParams.add("skillCompetency");
		ArrayList<String[][]> skillReply = xMLGear.getResultListTabAndAtt(resource, listParams);
		
		String[][] s = parsedReply.get(0);//To ease the following
		
		//UUID and Type
		String[] temp = UsefulMethod.getItemByName("self", s).split("/");
		String UUID = temp[temp.length-1];//The ID is the last item
		AgentType type = (UsefulMethod.getItemByName("type", s)).equals("1")?AgentType.agent:AgentType.supervisor;
		
		//Team
		String teamName = UsefulMethod.getAttributeItemByName("team", s);
		ArrayList<Team> teams = new ArrayList<Team>();
		teams.add(new Team(teamName));
		//Manage the primary and secondary supervisor
		//TBW
		
		//Skill
		ArrayList<Skill> skills = new ArrayList<Skill>();
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
		
		return agent;
		}
	
	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

