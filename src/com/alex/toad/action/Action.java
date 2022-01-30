package com.alex.toad.action;

import com.alex.toad.misc.CollectionTools;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.webserver.AgentData;
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
		//Temp
		/*
		try
			{
			
			
			
			}
		catch (Exception e1)
			{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
		
		System.exit(0);*/
		//Temp
		
		
		
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
