package com.alex.toad.webserver;

import java.util.ArrayList;

import com.alex.toad.cucm.user.misc.UserCreationProfile;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.misc.Office;
import com.alex.toad.misc.Task;
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
	public static WebRequest buildSearchReply(ArrayList<UCCXAgent> agents)
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
			for(UCCXAgent ad : agents)
				{
				content.append("				<agent>\r\n");
				content.append("					<userid>"+ad.getName()+"</userid>\r\n");
				content.append("					<firstname>"+ad.getFirstname()+"</firstname>\r\n");
				content.append("					<lastname>"+ad.getLastname()+"</lastname>\r\n");
				content.append("					<number>"+ad.getTelephoneNumber()+"</number>\r\n");
				content.append("					<type>"+ad.getAgentType()+"</type>\r\n");
				content.append("					<team>"+ad.getTeam().getName()+"</team>\r\n");
				content.append("				</agent>\r\n");
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
	public static WebRequest buildGetAgentReply(AgentData agent)
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
			content.append("				<primarysupervisor>"+team.getPrimarySupervisor()+"</primarysupervisor>\r\n");
			content.append("				<supervisors>\r\n");
			for(UCCXAgent ua : team.getSecondarySupervisorList())
				{
				content.append("				<supervisor>\r\n");
				content.append("					<userid>"+ua.getName()+"</userid>\r\n");
				content.append("					<firstname>"+ua.getFirstname()+"</firstname>\r\n");
				content.append("					<lastname>"+ua.getLastname()+"</lastname>\r\n");
				content.append("				</supervisor>\r\n");
				}
			content.append("				</supervisors>\r\n");
			content.append("				<agents>\r\n");
			for(UCCXAgent a : team.getAgentList())
				{
				content.append("				<agent>\r\n");
				content.append("					<userid>"+a.getName()+"</userid>\r\n");
				content.append("					<firstname>"+a.getFirstname()+"</firstname>\r\n");
				content.append("					<lastname>"+a.getLastname()+"</lastname>\r\n");
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
	 * To build the get Task reply
	 */
	public static WebRequest buildGetTaskReply(Task task)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getTask;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<task>\r\n");
		
		try
			{
			content.append("				<taskid>"+task.getTaskID()+"</taskid>\r\n");
			content.append("				<status>"+task.getStatus()+"</status>\r\n");
			content.append("				<items>\r\n");
			for(ItemToInject iti : task.getTodoList())
				{
				content.append("					<item>\r\n");
				content.append("						<type>"+iti.getType()+"</type>\r\n");
				content.append("						<info>"+iti.getInfo()+"</info>\r\n");
				content.append("						<status>"+iti.getStatus().name()+"</status>\r\n");
				content.append("						<desc>"+iti.getDetailedStatus()+"</desc>\r\n");
				content.append("					</item>\r\n");
				}
			content.append("				</items>\r\n");
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving an agent : "+e.getMessage());
			content.append("			<agent></agent>\r\n");
			}
		
		content.append("			</task>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the task reply
	 */
	public static WebRequest buildTaskReply(String taskID, webRequestType type)
		{
		StringBuffer content = new StringBuffer();
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<taskid>"+taskID+"</taskid>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the list agent reply
	 */
	public static WebRequest buildListAgentReply(ArrayList<AgentData> agents)
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
			for(AgentData ad : agents)
				{
				content.append(buildAgent(ad));
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
	 * To build the list task reply
	 */
	public static WebRequest buildListTaskReply(ArrayList<Task> tasks)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.listTask;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<tasks>\r\n");
		
		try
			{
			for(Task t : tasks)
				{
				content.append("				<task>\r\n");
				content.append("					<taskid>"+t.getTaskID()+"</taskid>\r\n");
				content.append("					<status>"+t.getStatus().name()+"</status>\r\n");
				content.append("					<desc>"+t.getInfo()+"</desc>\r\n");
				content.append("				<task>\r\n");
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the task list : "+e.getMessage());
			content.append("				<task></task>\r\n");
			}
		
		content.append("			</tasks>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the list task reply
	 */
	public static WebRequest buildListUCPReply(ArrayList<UserCreationProfile> ucps)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.listUCP;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<ucps>\r\n");
		
		try
			{
			for(UserCreationProfile ucp : ucps)
				{
				content.append("				<ucp>\r\n");
				content.append("					<name>"+ucp.getName()+"</name>\r\n");
				content.append("				</ucp>\r\n");
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the User Creatio profile list : "+e.getMessage());
			content.append("				<ucp></ucp>\r\n");
			}
		
		content.append("			</ucps>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	
	/**
	 * To build one agent reply
	 */
	private static StringBuffer buildAgent(AgentData agent) throws Exception 
		{
		StringBuffer content = new StringBuffer();
		
		content.append("			<agent>\r\n");
		content.append("				<userid>"+agent.getUserID()+"</userid>\r\n");
		content.append("				<firstname>"+agent.getFirstName()+"</firstname>\r\n");
		content.append("				<lastname>"+agent.getLastName()+"</lastname>\r\n");
		content.append("				<number>"+agent.getLineNumber()+"</number>\r\n");
		content.append("				<office>"+agent.getOffice().getFullname()+"</office>\r\n");
		content.append("				<type>"+agent.getAgentType()+"</type>\r\n");
		content.append("				<team>"+agent.getTeam().getName()+"</team>\r\n");
		content.append("				<primarysupervisorof>\r\n");
		for(Team t : agent.getPrimarySupervisorOf())
			{
			content.append("					<team>"+t.getName()+"</team>\r\n");
			}
		content.append("				</primarysupervisorof>\r\n");
		content.append("				<secondarysupervisorof>\r\n");
		for(Team t : agent.getSecondarySupervisorOf())
			{
			content.append("					<team>"+t.getName()+"</team>\r\n");
			}
		content.append("				</secondarysupervisorof>\r\n");
		content.append("				<skills>\r\n");
		for(Skill s : agent.getSkillList())
			{
			content.append("					<skill>\r\n");
			content.append("						<name>"+s.getName()+"</name>\r\n");
			content.append("						<level>"+s.getLevel()+"</level>\r\n");
			content.append("					</skill>\r\n");
			}
		content.append("				</skills>\r\n");
		content.append("				<devices>\r\n");
		for(String s : agent.getDeviceList())
			{
			content.append("					<device>\r\n");
			content.append("						<name>"+s+"</name>\r\n");
			content.append("					</device>\r\n");
			}
		content.append("				</devices>\r\n");
		content.append("				<udps>\r\n");
		for(String s : agent.getUDPList())
			{
			content.append("					<udp>\r\n");
			content.append("						<name>"+s+"</name>\r\n");
			content.append("					</udp>\r\n");
			}
		content.append("				</udps>\r\n");
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
