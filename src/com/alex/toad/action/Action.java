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
		//Temp
		
		String content = "<resources>\r\n"
				+ "  <resource>\r\n"
				+ "    <self>https://uccx-server/adminapi/resource/agent1333</self>\r\n"
				+ "    <userID>agent1333</userID>\r\n"
				+ "    <firstName></firstName>\r\n"
				+ "    <lastName>agent1333</lastName>\r\n"
				+ "    <extension>2244333</extension>\r\n"
				+ "    <alias>uccx</alias>\r\n"
				+ "    <skillMap>\r\n"
				+ "    	<skillCompetency>\r\n"
				+ "    		<competencelevel>5</competencelevel>\r\n"
				+ "			<skillNameUriPair name=\"OBAgentSkill\">\r\n"
				+ "				<refURL>https://uccx-server/adminapi/skill/3</refURL>\r\n"
				+ "			</skillNameUriPair>\r\n"
				+ "		</skillCompetency>\r\n"
				+ "    </skillMap>\r\n"
				+ "    <autoAvailable>true</autoAvailable>\r\n"
				+ "    <type>1</type>\r\n"
				+ "    <team name=\"Default\">\r\n"
				+ "    	<refURL>https://uccx-server/adminapi/team/1</refURL>\r\n"
				+ "    </team>\r\n"
				+ "    <primarySupervisorOf/>\r\n"
				+ "    <secondarySupervisorOf/>\r\n"
				+ "  </resource>\r\n"
				+ "</resources>";
		
		
		
		
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
