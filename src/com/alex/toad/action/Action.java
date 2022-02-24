package com.alex.toad.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.actionType;
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
		
		System.exit(0);
		*/
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
