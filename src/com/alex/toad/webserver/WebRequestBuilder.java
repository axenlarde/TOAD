package com.alex.toad.webserver;

import java.util.ArrayList;

import com.alex.toad.misc.Agent;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.misc.Office;
import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.utils.Variables;
import com.alex.toad.webserver.ManageWebRequest.webRequestType;



/**
 * Used to build web request
 *
 * @author Alexandre RATEL
 */
public class WebRequestBuilder
	{
	
	/**
	 * To build a successful web reply
	 */
	public static WebRequest buildSuccessWebRequest(webRequestType type)
		{
		StringBuffer content = new StringBuffer();
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			</success>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build a failed web reply
	 */
	public static WebRequest buildFailedWebRequest(webRequestType type, String errorMessage)
		{
		StringBuffer content = new StringBuffer();
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<error>"+errorMessage+"</error>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the doAuthenticate reply
	 */
	public static WebRequest buildDoAuthenticateReply(String token)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.doAuthenticate;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<securitytoken>"+token+"</securitytoken>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the search request reply
	 */
	public static WebRequest buildSearchReply(ArrayList<Agent> agents)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.search;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<agents>\r\n");
		
		try
			{
			for(Agent a : agents)
				{
				content.append(buildAgent(a));
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while searching for agents : "+e.getMessage());
			content.append("			<agent></agent>\r\n");
			}
		
		content.append("			</agents>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the get agent reply
	 */
	public static WebRequest buildGetAgentReply(Agent agent)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getAgent;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		
		try
			{
			content.append(buildAgent(agent));
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving an agent : "+e.getMessage());
			content.append("			<agent></agent>\r\n");
			}
		
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the get team reply
	 */
	public static WebRequest buildGetTeamReply(Team team)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getTeam;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<team>\r\n");
		
		try
			{
			content.append("				<name>"+team.getName()+"</name>\r\n");
			content.append("				<primarysupervisor>"+team.getName()+"</primarysupervisor>\r\n");
			content.append("				<supervisors>\r\n");
			for(UCCXAgent ua : team.getSupervisorList())
				{
				content.append("				<supervisor>\r\n");
				content.append("					<userid>"+ua.getUser().getName()+"</userid>\r\n");
				content.append("					<firstname>"+ua.getUser().getFirstname()+"</firstname>\r\n");
				content.append("					<lastname>"+ua.getUser().getLastname()+"</lastname>\r\n");
				content.append("				</supervisor>\r\n");
				}
			content.append("				</supervisors>\r\n");
			content.append("				<agents>\r\n");
			for(UCCXAgent a : team.getAgentList())
				{
				content.append("				<agent>\r\n");
				content.append("					<userid>"+a.getUser().getName()+"</userid>\r\n");
				content.append("					<firstname>"+a.getUser().getFirstname()+"</firstname>\r\n");
				content.append("					<lastname>"+a.getUser().getLastname()+"</lastname>\r\n");
				content.append("				</agent>\r\n");
				}
			content.append("				</agents>\r\n");
			
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving a team : "+e.getMessage());
			}
		
		content.append("			</team>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the add agent reply
	 */
	public static WebRequest buildAddAgentReply(String userID)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.addAgent;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<userid>"+userID+"</userid>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the update agent reply
	 */
	public static WebRequest buildUpdateAgentReply(String userID)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.updateAgent;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<userid>"+userID+"</userid>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the list agent reply
	 */
	public static WebRequest buildListAgentReply(ArrayList<Agent> agents)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.listAgent;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<agents>\r\n");
		
		try
			{
			for(Agent a : agents)
				{
				content.append(buildAgent(a));
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the agent list : "+e.getMessage());
			content.append("			<agent></agent>\r\n");
			}
		
		content.append("			</agents>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	
	/**
	 * To build the list Team reply
	 */
	public static WebRequest buildListTeamReply(ArrayList<Team> teams)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.listTeam;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<teams>\r\n");
		
		try
			{
			for(Team t : teams)
				{
				content.append("				<team>"+t.getName()+"</team>\r\n");
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the team list : "+e.getMessage());
			}
		
		content.append("			</teams>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the list skill reply
	 */
	public static WebRequest buildListSkillReply(ArrayList<Skill> skills)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.listSkill;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<skills>\r\n");
		
		try
			{
			for(Skill s : skills)
				{
				content.append("				<skill>"+s.getName()+"</skill>\r\n");
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the skill list : "+e.getMessage());
			}
		
		content.append("			</skills>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the list office reply
	 */
	public static WebRequest buildListOfficeReply(ArrayList<Office> offices)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.listOffice;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<offices>\r\n");
		
		try
			{
			for(Office o : offices)
				{
				content.append("				<office>"+o.getFullname()+"</office>\r\n");
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the office list : "+e.getMessage());
			}
		
		content.append("			</offices>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	
	/**
	 * To build one agent reply
	 */
	private static StringBuffer buildAgent(Agent agent) throws Exception 
		{
		StringBuffer content = new StringBuffer();
		
		content.append("			<agent>\r\n");
		content.append("				<userid>"+agent.getUser().getName()+"</userid>\r\n");
		content.append("				<firstname>"+agent.getUser().getFirstname()+"</firstname>\r\n");
		content.append("				<lastname>"+agent.getUser().getLastname()+"</lastname>\r\n");
		content.append("				<number>"+agent.getUser().getTelephoneNumber()+"</number>\r\n");
		content.append("				<office>"+agent.getOffice()+"</office>\r\n");
		content.append("				<type>"+agent.getAgent().getAgentType().name()+"</type>\r\n");
		content.append("				<teams>\r\n");
		for(Team t : agent.getAgent().getTeams())
			{
			content.append("					<team>"+t.getName()+"</team>\r\n");
			}
		content.append("				</teams>\r\n");
		content.append("				<skills>\r\n");
		for(Skill s : agent.getAgent().getSkills())
			{
			content.append("					<skill>"+s.getName()+"</skill>\r\n");
			}
		content.append("				</skills>\r\n");
		content.append("				<devices>\r\n");
		for(ItemToInject iti : agent.getDeviceList())
			{
			content.append("					<device>\r\n");
			content.append("						<type>"+iti.getType().name()+"</type>\r\n");
			content.append("						<type>"+iti.getName()+"</type>\r\n");
			content.append("					</device>\r\n");
			}
		content.append("				</devices>\r\n");
		content.append("			</agent>\r\n");
		
		return content;
		}
	
	/**
	 * To build the requested request
	 * success
	 */
	/*
	public static WebRequest buildSuccess()
		{
		StringBuffer content = new StringBuffer();
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>success</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<success></success>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), webRequestType.success);
		}
	*/
	/**
	 * To build the requested request
	 * error
	 */
	/*
	public static WebRequest buildError(String message)
		{
		StringBuffer content = new StringBuffer();
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>error</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<error>"+message+"</error>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), webRequestType.error);
		}
		*/
	
	/*2022*//*RATEL Alexandre 8)*/
	}
