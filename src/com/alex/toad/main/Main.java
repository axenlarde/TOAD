package com.alex.toad.main;

import org.apache.log4j.Level;

import com.alex.toad.action.Action;
import com.alex.toad.utils.InitLogging;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;

/**********************************
 * TOAD Main Class 15/11/2021
 * 
 * @author RATEL Alexandre
 **********************************/
public class Main
	{
	/**
	 * Variables
	 */
	
	
	/***************
	 * Constructor
	 ***************/
	public Main()
		{
		//Set the software name
		Variables.setSoftwareName("TOAD");
		//Set the software version
		Variables.setSoftwareVersion("1.0");
		
		/****************
		 * Initialization of the logging
		 */
		Variables.setLogger(InitLogging.init(Variables.getSoftwareName()+"_LogFile.txt"));
		Variables.getLogger().info("\r\n");
		Variables.getLogger().info("#### Entering application");
		Variables.getLogger().info("## Welcome to : "+Variables.getSoftwareName()+" version "+Variables.getSoftwareVersion());
		Variables.getLogger().info("## Author : RATEL Alexandre : 2021");
		/*******/
		
		/******
		 * Initialization of the variables
		 */
		new Variables();
		/************/
		
		/**********
		 * We check if the java version is compatible
		 */
		UsefulMethod.checkJavaVersion();
		/***************/
		
		/**********************
		 * Reading of the configuration file
		 */
		try
			{
			//Config files reading
			Variables.setMainConfig(UsefulMethod.readMainConfigFile(Variables.getConfigFileName()));
			}
		catch(Exception exc)
			{
			UsefulMethod.failedToInit(exc);
			}
		/********************/
		
		/*****************
		 * Setting of the inside variables from what we read in the configuration file
		 */
		try
			{
			UsefulMethod.initInternalVariables();
			}
		catch(Exception exc)
			{
			Variables.getLogger().error(exc.getMessage());
			Variables.getLogger().setLevel(Level.INFO);
			}
		/*********************/
		
		/****************
		 * Init email server
		 */
		try
			{
			UsefulMethod.initEMailServer();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			Variables.getLogger().error("Failed to init the eMail server : "+e.getMessage());
			}
		/*************/
				
		/*******************
		 * Start main interface
		 */
		try
			{
			Variables.getLogger().info("End init, Launching main process");
			new Action();
			}
		catch (Exception exc)
			{
			UsefulMethod.failedToInit(exc);
			}
		/******************/
		
		//End of the main class
		}
	
	public static void main(String[] args)
		{
		new Main();
		}

	/*2021*//*RATEL Alexandre 8)*/
	}
