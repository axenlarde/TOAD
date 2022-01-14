package com.alex.toad.action;


import com.alex.toad.utils.Variables;
import com.alex.toad.webserver.WebListenerManager;

/**
 * Class used to launch the main jobs
 * 
 * @author Alexandre
 */
public class Action
	{
	/**
	 * Variables
	 */
	
	public Action()
		{
		/**
		 * We read the current user file
		 */
		try
			{
			Variables.getLogger().info("User list size : "+Variables.getUserList().size());//This trigger the file reading
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while reading the user list : "+e.getMessage(), e);
			}
		
		/**
		 * We start the web server
		 */
		try
			{
			Variables.setWebServer(new WebListenerManager());
			}
		catch(Exception e)
			{
			Variables.getLogger().error("ERROR setting up the web server Thread : "+e.getMessage(), e);
			}
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}
