package com.alex.toad.webserver;

import java.io.File;
import java.net.Authenticator.RequestorType;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.alex.toad.misc.Agent;
import com.alex.toad.misc.AgentTools;
import com.alex.toad.misc.Task;
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
		addAgent,
		updateAgent,
		deleteAgent,
		listAgent,
		listTeam,
		listSkill,
		listOffice,
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
			securityToken = WebTools.getSecurityToken(UsefulMethod.getItemByName("securitytoken", parsed.get(0)));
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
			
			if(AgentTools.doAuthenticate(userID, password))return WebRequestBuilder.buildSuccessWebRequest(request.getType());
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
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(content, params);
			String[][] t = parsed.get(0);
			
			String userID = UsefulMethod.getItemByName("userid", t);
			
			//We get Agent informations
			Agent agent = AgentTools.getAgent(userID);
			
			return WebRequestBuilder.buildGetAgentReply(agent);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing getAgent web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildFailedWebRequest(webRequestType.getAgent, e.getMessage());
			}
		}
	
	public synchronized static WebRequest getDeviceList(WebRequest request)	
		{
		try
			{
			return WebRequestBuilder.buildWebRequest(webRequestType.getDeviceList, null);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing getDeviceList web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildWebRequest(webRequestType.error, null);
		}
	
	/**
	 * getTaskList
	 */
	public synchronized static WebRequest getTaskList()	
		{
		try
			{
			return WebRequestBuilder.buildWebRequest(webRequestType.getTaskList, null);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing getTaskList web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildWebRequest(webRequestType.error, null);
		}
	
	/**
	 * getOffice
	 */
	public synchronized static WebRequest getOffice(String content)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(content, params);
			String[][] t = parsed.get(0);
			
			String officeID = UsefulMethod.getItemByName("officeid", t);
			return WebRequestBuilder.buildWebRequest(webRequestType.getOffice, officeID);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing getOffice web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildWebRequest(webRequestType.error, null);
		}
	
	/**
	 * getDevice
	 */
	public synchronized static WebRequest getDevice(String content)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(content, params);
			String[][] t = parsed.get(0);
			
			String deviceID = UsefulMethod.getItemByName("deviceid", t);
			return WebRequestBuilder.buildWebRequest(webRequestType.getDevice, deviceID);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing getDevice web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildWebRequest(webRequestType.error, null);
		}
	
	/**
	 * getTask
	 */
	public synchronized static WebRequest getTask(String content)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(content, params);
			String[][] t = parsed.get(0);
			
			String taskID = UsefulMethod.getItemByName("taskid", t);
			return WebRequestBuilder.buildWebRequest(webRequestType.getTask, taskID);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing getTask web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildWebRequest(webRequestType.error, null);
		}
	
	/**
	 * newTask
	 */
	public synchronized static WebRequest newTask(String content)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(content, params);
			String[][] tab = parsed.get(0);
			
			actionType action = actionType.valueOf(UsefulMethod.getItemByName("action", tab));
			String ownerid = UsefulMethod.getItemByName("ownerid", tab);
			
			params.add("itemlist");
			parsed = xMLGear.getResultListTab(content, params);
			tab = parsed.get(0);
			ArrayList<String> idList = new ArrayList<String>();
			
			for(String[] t : tab)
				{
				idList.add(t[1]);
				}
			
			String taskID = TaskManager.addNewTask(idList, action, ownerid);
			
			return WebRequestBuilder.buildWebRequest(webRequestType.newTask, taskID);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing newTask web request : "+e.getMessage(),e);
			return WebRequestBuilder.buildWebRequest(webRequestType.error, e.getMessage());
			}
		}
	
	/**
	 * setTask
	 */
	public synchronized static WebRequest setTask(String content)	
		{
		try
			{
			ArrayList<String> params = new ArrayList<String>();
			params.add("request");
			params.add("content");
			params.add("task");
			
			ArrayList<String[][]> parsed = xMLGear.getResultListTab(content, params);
			String[][] tab = parsed.get(0);
			
			String taskID = UsefulMethod.getItemByName("taskid", tab);
			taskActionType action = taskActionType.valueOf(UsefulMethod.getItemByName("action", tab));
			
			boolean found = false;
			for(Task t : Variables.getTaskList())
				{
				if(t.getTaskId().equals(taskID))
					{
					t.act(action);
					found = true;
					break;
					}
				}
			
			if(found)
				{
				return WebRequestBuilder.buildWebRequest(webRequestType.success, taskID);
				}
			else
				{
				Variables.getLogger().debug("The following task ID was not found : "+taskID);
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing setask web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildWebRequest(webRequestType.error, null);
		}
	
	/**
	 * copyLogFile
	 */
	public synchronized static WebRequest copyLogFile()	
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
			
			return WebRequestBuilder.buildWebRequest(webRequestType.success, null);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing getTask web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildWebRequest(webRequestType.error, null);
		}
	
	/**
	 * newDevice
	 */
	public synchronized static WebRequest newDevice(String content)	
		{
		try
			{
			//To be written
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing newDevice web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildWebRequest(webRequestType.error, null);
		}
	
	/**
	 * newOffice
	 */
	public synchronized static WebRequest newOffice(String content)	
		{
		try
			{
			//To be written
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while processing newOffice web request : "+e.getMessage(),e);
			}
		
		return WebRequestBuilder.buildWebRequest(webRequestType.error, null);
		}
	
	/*2019*//*RATEL Alexandre 8)*/
	}
