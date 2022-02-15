package com.alex.toad.webserver;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.alex.toad.cucm.user.items.Phone;
import com.alex.toad.misc.AgentTools;
import com.alex.toad.misc.Office;
import com.alex.toad.misc.Task;
import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.actionType;
import com.alex.toad.utils.xMLGear;


/**
 * Used to Manage web requests
 *
 * @author Alexandre RATEL
 */
public class ManageWebRequest
	{
	/**
	 * Variables
	 */
	public enum webRequestType
		{
		doAuthenticate,
		search,
		getAgent,
		getTeam,
		getTask,
		addAgent,
		updateAgent,
		deleteAgent,
		listAgent,
		listTeam,
		listSkill,
		listOffice,
		listTask,
		listUCP,
		copyLogFile
		}
	
	/**
	 * Parse a web request
	 * @throws Exception 
	 */
	public synchronized static WebRequest parseWebRequest(String content) throws Exception
		{
		ArrayList<String> params = new ArrayList<String>();
		SecurityToken securityToken = new SecurityToken("", null);
		
		//We parse the request type
		params.add("request");
		ArrayList<String[][]> parsed = xMLGear.getResultListTab(content, params);
		webRequestType type = webRequestType.valueOf(UsefulMethod.getItemByName("type", parsed.get(0)));
		Variables.getLogger().debug("Web request type found : "+type.name());
		
		if(!type.equals(webRequestType.doAuthenticate))
			{
			/**
			 * doAuthenticate is the only case where the security token will be missing
			 * and therefore not verified
			 */
			//securityToken = WebTools.getSecurityToken(UsefulMethod.getItemByName("securitytoken", parsed.get(0)));
			Variables.getLogger().debug("Associated security token found is : "+securityToken);
			}
		
		return new WebRequest(content, type, securityToken);
		}
	
	/**
	 * doAuthenticate
	 */
	public synchronized static WebRequest doAuthenticate(WebRequest request)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(request.getContent(), params);
			String[][] t = parsed.get(0);
			
			String userID = UsefulMethod.getItemByName("userid", t);
			String password = UsefulMethod.getItemByName("userpassword", t);
			
			AgentData agent = AgentTools.doAuthenticate(userID, password);
			SecurityToken token = WebTools.newSecurityToken(agent);
			return WebRequestBuilder.buildDoAuthenticateReply(token.getToken());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing doAuthenticate web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildFailedWebRequest(request.getType(), "Failed to authenticate with the provided credentials");
		}
	
	public synchronized static WebRequest search(WebRequest request)
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(request.getContent(), params);
			String[][] t = parsed.get(0);
			
			String search = UsefulMethod.getItemByName("search", t);
			
			return WebRequestBuilder.buildSearchReply(AgentTools.search(search));
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing the search request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), "ERROR while processing the search request : "+e.getMessage());
			}
		}
	
	public synchronized static WebRequest getAgent(WebRequest request)
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(request.getContent(), params);
			String[][] t = parsed.get(0);
			
			String userID = UsefulMethod.getItemByName("userid", t);
			
			//We get Agent informations
			AgentData agent = AgentTools.getAgent(userID);
			
			return WebRequestBuilder.buildGetAgentReply(agent);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing getAgent web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(webRequestType.getAgent, e.getMessage());
			}
		}
	
	public synchronized static WebRequest getTeam(WebRequest request)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(request.getContent(), params);
			String[][] t = parsed.get(0);
			
			String teamName = UsefulMethod.getItemByName("team", t);
			
			Team team = AgentTools.getTeam(teamName);
			
			return WebRequestBuilder.buildGetTeamReply(team);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing get Team web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		}
	
	public synchronized static WebRequest getTask(WebRequest request)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(request.getContent(), params);
			String[][] t = parsed.get(0);
			
			String taskID = UsefulMethod.getItemByName("taskid", t);
			
			Task task = UsefulMethod.getTask(taskID);
			
			return WebRequestBuilder.buildGetTaskReply(task);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing get Team web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		}
	
	/**
	 * Add Agent
	 */
	public synchronized static WebRequest addUpdateAgent(WebRequest request)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			params.add("agent");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(request.getContent(), params);
			String[][] t = parsed.get(0);
			
			String userCreationProfile = UsefulMethod.getItemByName("usercreationprofile", t);
			String userID = UsefulMethod.getItemByName("userid", t);
			String lastName = UsefulMethod.getItemByName("lastname", t);
			String firstName = UsefulMethod.getItemByName("firstname", t);
			String lineNumber = UsefulMethod.getItemByName("number", t);
			Office office = UsefulMethod.getOffice(UsefulMethod.getItemByName("team", t));
			AgentType type = AgentType.valueOf(UsefulMethod.getItemByName("type", t));
			String deviceName = UsefulMethod.getItemByName("devicename", t);
			String deviceType = UsefulMethod.getItemByName("devicetype", t);
			Team team = new Team(UsefulMethod.getItemByName("team", t));
			boolean udpLogin = Boolean.parseBoolean(UsefulMethod.getItemByName("udplogin", t));
			
			//Primary supervisor teams
			params.add("primarysupervisorof");
			parsed = xMLGear.getResultListTab(request.getContent(), params);
			t = parsed.get(0);
			ArrayList<Team> primarySupervisorOf = new ArrayList<Team>();
			for(String[] s : t)
				{
				primarySupervisorOf.add(new Team(s[1]));
				}
			
			//Primary supervisor teams
			params.remove("primarysupervisorof");
			params.add("secondarysupervisorof");
			parsed = xMLGear.getResultListTab(request.getContent(), params);
			t = parsed.get(0);
			ArrayList<Team> secondarySupervisorOf = new ArrayList<Team>();
			for(String[] s : t)
				{
				secondarySupervisorOf.add(new Team(s[1]));
				}
			
			//Skills
			params.remove("secondarysupervisorof");
			params.add("skills");
			params.add("skill");
			parsed = xMLGear.getResultListTab(request.getContent(), params);
			ArrayList<Skill> skills = new ArrayList<Skill>();
			
			for(String[][] temp : parsed)
				{
				Skill s = AgentTools.getSkill(UsefulMethod.getItemByName("name", temp));
				s.setLevel(Integer.parseInt(UsefulMethod.getItemByName("level", temp)));
				skills.add(s);
				}
			
			if(AgentTools.isAllowed(request))//Is Allowed has to be implemented
				{
				if(request.getType().equals(webRequestType.addAgent))
					{
					Variables.getLogger().debug("Trying to create agent : "+firstName+" "+lastName+" "+type.name()+" "+office.getFullname());
					}
				else
					{
					Variables.getLogger().debug("Trying to update agent : "+firstName+" "+lastName+" "+type.name()+" "+office.getFullname());
					}
				
				String taskID = AgentTools.addUpdateAgent(
						userCreationProfile,
						userID,
						lastName,
						firstName,
						office,
						type,
						team,
						primarySupervisorOf,
						secondarySupervisorOf,
						skills,
						deviceName,
						deviceType,
						lineNumber,
						udpLogin,
						request.getType());
				
				return WebRequestBuilder.buildTaskReply(taskID, request.getType());
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing "+request.getType()+" web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		
		return WebRequestBuilder.buildFailedWebRequest(request.getType(), "Something went wrong");
		}
	
	
	/**
	 * Delete Agent
	 */
	public synchronized static WebRequest deleteAgent(WebRequest request)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			params.add("agent");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(request.getContent(), params);
			String[][] t = parsed.get(0);
			
			String userID = UsefulMethod.getItemByName("userid", t);
			
			if(AgentTools.isAllowed(request))//Is Allowed has to be implemented
				{
				Variables.getLogger().debug("Trying to delete agent : "+userID);
				String taskID = AgentTools.deleteAgent(userID);
				return WebRequestBuilder.buildTaskReply(taskID, request.getType());
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing delete Agent web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		
		return WebRequestBuilder.buildFailedWebRequest(request.getType(), "Something went wrong");
		}
	
	/**
	 * List Agent
	 */
	public synchronized static WebRequest listAgent(WebRequest request)	
		{
		try
			{
			return WebRequestBuilder.buildListAgentReply(AgentTools.listAgent());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing list Agent web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		}
	
	/**
	 * List Team
	 */
	public synchronized static WebRequest listTeam(WebRequest request)	
		{
		try
			{
			return WebRequestBuilder.buildListTeamReply(AgentTools.listTeam());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing list Team web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		}
	
	/**
	 * List Skill
	 */
	public synchronized static WebRequest listSkill(WebRequest request)	
		{
		try
			{
			return WebRequestBuilder.buildListSkillReply(AgentTools.listSkill());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing list Skill web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		}
	
	/**
	 * List Office
	 */
	public synchronized static WebRequest listOffice(WebRequest request)	
		{
		try
			{
			return WebRequestBuilder.buildListOfficeReply(Variables.getOfficeList());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing list Office web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		}
	
	/**
	 * List Task
	 */
	public synchronized static WebRequest listTask(WebRequest request)	
		{
		try
			{
			return WebRequestBuilder.buildListTaskReply(Variables.getTaskList());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing list Task web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		}
	
	/**
	 * List User Creation Profile
	 */
	public synchronized static WebRequest listUCP(WebRequest request)	
		{
		try
			{
			return WebRequestBuilder.buildListUCPReply(Variables.getUserCreationProfileList());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing list User Creation profile web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		}
	
	/**
	 * copyLogFile
	 */
	public synchronized static WebRequest copyLogFile(WebRequest request)	
		{
		try
			{
			File srcFile = new File(Variables.getMainConfigFileDirectory()+"/"+Variables.getLogFileName());
			if(srcFile.exists())
				{
				Variables.getLogger().debug("Copying the first log file");
				File dstFile = new File(UsefulMethod.getTargetOption("targetdirectory")+"/"+Variables.getLogFileName());
				FileUtils.copyFile(srcFile, dstFile);
				}
			
			//We also copy the second log file
			srcFile = new File(Variables.getMainConfigFileDirectory()+"/"+Variables.getLogFileName()+".1");
			if(srcFile.exists())
				{
				Variables.getLogger().debug("Copying the second log file");
				File dstFile = new File(UsefulMethod.getTargetOption("targetdirectory")+"/"+Variables.getLogFileName()+".1");
				FileUtils.copyFile(srcFile, dstFile);
				}
			
			return WebRequestBuilder.buildSuccessWebRequest(request.getType());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing copy log file web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(request.getType(), e.getMessage());
			}
		}
	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}
