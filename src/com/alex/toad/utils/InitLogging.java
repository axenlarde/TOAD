
package com.alex.toad.utils;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import com.alex.toad.main.Main;



/*********************************************
 * Class used to initiate logging
 * 
 * @author RATEL Alexandre
 *********************************************/
public class InitLogging
	{
	
	public static Logger init(String logFileName)
		{
		Logger logger = Logger.getLogger(Main.class);
		PatternLayout myPattern = new PatternLayout("%d{dd/MM/yyyy - HH:mm:ss} - [%p] (%C:%L) - %m%n");
		
		//File appender setup
		RollingFileAppender myR = new RollingFileAppender();
		myR.setName("myFileAppender");
		myR.setFile(logFileName);
		myR.setMaxFileSize("10000KB");
		myR.setMaxBackupIndex(10);
		myR.setLayout(myPattern);
		myR.setEncoding("UTF-8");
		myR.activateOptions();
		
		//Console appender setup
		ConsoleAppender myC = new ConsoleAppender();
		myC.setLayout(myPattern);
		myC.activateOptions();
		
		//Other appender setup
		
		
		//We finally add the appenders to the logger
		BasicConfigurator.configure(myC);
		logger.addAppender(myR);
		
		return logger;
		}
	
	public static Logger infoModeInit(String logFileName)
		{
		Logger logger = Logger.getLogger("");
		
		PatternLayout myPattern = new PatternLayout("%d{dd/MM/yyyy - HH:mm:ss} - %m%n");
		
		//File appender setup
		RollingFileAppender myR = new RollingFileAppender();
		myR.setName("myFileAppender");
		myR.setFile(logFileName);
		myR.setMaxFileSize("1000KB");
		myR.setMaxBackupIndex(7);
		myR.setLayout(myPattern);
		myR.activateOptions();
		
		//We finally add the appenders to the logger
		logger.addAppender(myR);
		
		return logger;
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
