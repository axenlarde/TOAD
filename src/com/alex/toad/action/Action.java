package com.alex.toad.action;


import com.alex.wassgar.curri.CURRIHTTPServer;
import com.alex.wassgar.curri.CURRIRequest;
import com.alex.wassgar.curri.ManageCURRI;
import com.alex.wassgar.jtapi.Monitor;
import com.alex.wassgar.salesforce.ConnectionManager;
import com.alex.wassgar.salesforce.SalesForceManager;
import com.alex.wassgar.server.ListenerManager;
import com.alex.wassgar.server.Watchman;
import com.alex.wassgar.utils.UsefulMethod;
import com.alex.wassgar.utils.Variables;
import com.alex.wassgar.webserver.WebListenerManager;

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
		 * We start the SalesForce connection
		 */
		try
			{
			Variables.setsFConnectionManager(new ConnectionManager());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR setting up the salesforce thread : "+e.getMessage(), e);
			}
		
		/**
		 * We start the monitor thread
		 */
		try
			{
			/***
			 * Maybe rewrite the following as a static "MonitoringManager"
			 */
			Variables.setJtapiMonitor(new Monitor(UsefulMethod.getTargetOption("ctihost"),
					UsefulMethod.getTargetOption("ctidelay"),
					UsefulMethod.getTargetOption("ctiusername"),
					UsefulMethod.getTargetOption("ctipassword")));
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR setting up the monitor thread : "+e.getMessage(), e);
			}
		
		/**
		 * We start the client manager thread
		 */
		try
			{
			Variables.setClientMonitor(new ListenerManager());//To monitor for new connection
			Variables.setWatchman(new Watchman());//To monitor existing connection
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR setting up the listener manager thread : "+e.getMessage(), e);
			}
		
		/**
		 * We start the CURRI Server
		 */
		try
			{
			Variables.setCurriServer(new CURRIHTTPServer());
			}
		catch(Exception e)
			{
			Variables.getLogger().error("ERROR setting up the CURRI Thread : "+e.getMessage(), e);
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
		
		
		/**
		 * We now wait for events ;)
		 */
		}
	
	
	/*2019*//*RATEL Alexandre 8)*/
	}
