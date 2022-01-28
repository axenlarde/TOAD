package com.alex.toad.uccx.misc;

import java.util.ArrayList;

import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.xMLGear;

/**********************************
* Used to store static method about UCCX
* 
* @author RATEL Alexandre
**********************************/
public class UCCXTools
	{
	/**
	 * Parse the REST reply and return a UCCX Agent
	 * 
	 * Should starts and ends with <resource>
	 * @throws Exception 
	 */
	public static UCCXAgent getAgentFromRESTReply(String resource) throws Exception
		{
		//We parse the XML
		resource = "<xml>"+resource+"</xml>";
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
				new Team(UsefulMethod.getAttributeItemByName("team", s)),
				skills);
		
		agent.setUUID(UUID);
		
		return agent;
		}
	
	/**
	 * Parse the REST reply and return a Team
	 * @throws Exception 
	 */
	public static Team getTeamFromRESTReply(String content) throws Exception
		{
		//We parse the XML
		content = "<xml>"+content+"</xml>";
		ArrayList<String> listParams = new ArrayList<String>();
		listParams.add("team");
		ArrayList<String[][]> parsedReply = xMLGear.getResultListTab(content, listParams);
		
		String[][] s = parsedReply.get(0);//To ease the following
		
		Team team = new Team(UsefulMethod.getItemByName("teamname", s));
		
		team.setUUID(UsefulMethod.getItemByName("teamId", s));
		
		return team;
		}
	
	/**
	 * Parse the REST reply and return a Skill
	 * @throws Exception 
	 */
	public static Skill getSkillFromRESTReply(String content) throws Exception
		{
		//We parse the XML
		content = "<xml>"+content+"</xml>";
		ArrayList<String> listParams = new ArrayList<String>();
		
		//Because sometime the skill tag contains upercase we have to test
		if(content.contains("<Skill>"))listParams.add("Skill");
		else listParams.add("skill");
		
		ArrayList<String[][]> parsedReply = xMLGear.getResultListTab(content, listParams);
		
		String[][] s = parsedReply.get(0);//To ease the following
		
		Skill skill = new Skill(UsefulMethod.getItemByName("skillName", s));
		
		skill.setUUID(UsefulMethod.getItemByName("skillId", s));
		
		return skill;
		}
	
	/**
	 * Return the XML form of a Team
	 * @throws Exception 
	 */
	public static String getRESTFromTeam(Team team) throws Exception
		{
		StringBuffer content = new StringBuffer("");
		
		content.append("		<team name=\""+team.getName()+"\">\r\n");
		content.append("			<refURL>https://"+Variables.getUccxServer().getHost()+"/adminapi/team/"+team.getUUID()+"</refURL>\r\n");
		content.append("		</team>");
		
		return content.toString();
		}
	
	/**
	 * Return the XML form of a list of skills (SkillMap)
	 * @throws Exception 
	 */
	public static String getRESTFromSkills(ArrayList<Skill> skills) throws Exception
		{
		StringBuffer content = new StringBuffer("");
		
		content.append("		<skillMap>\r\n");
		
		for(Skill s : skills)
			{
			content.append("			<skillCompetency>\r\n");
			content.append("				<competencelevel>"+s.getLevel()+"</competencelevel>\r\n");
			content.append("				<skillNameUriPair name=\""+s.getName()+"\">\r\n");
			content.append("					<refURL>https://"+Variables.getUccxServer().getHost()+"/adminapi/skill/"+s.getUUID()+"</refURL>\r\n");
			content.append("				</skillNameUriPair>\r\n");
			content.append("			</skillCompetency>\r\n");
			}
		
		content.append("		</skillMap>\r\n");
		
		return content.toString();
		}
	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

