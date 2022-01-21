package com.alex.toad.restitems.linkers;

import java.util.ArrayList;

import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ErrorTemplate.errorType;
import com.alex.toad.rest.misc.RESTGear;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.restitems.misc.RESTItemLinker;
import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.uccx.misc.UCCXError;
import com.alex.toad.uccx.misc.UCCXTools;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;
import com.alex.toad.utils.Variables.requestType;


/**********************************
 * Is the RESTItem design to link the item "Skill"
 * and the UCCX REST API without version dependencies
 * 
 * @author RATEL Alexandre
 **********************************/
public class SkillLinker extends RESTItemLinker
	{
	/**
	 * Variables
	 */
	private int level;
	
	public enum toUpdate implements ToUpdate
		{
		level,
		}
	
	/***************
	 * Constructor
	 * @throws Exception 
	 ***************/
	public SkillLinker(String name) throws Exception
		{
		super(name);
		}
	
	/***************
	 * Initialization
	 */
	public ArrayList<ErrorTemplate> doInitVersion105() throws Exception
		{
		ArrayList<ErrorTemplate> errorList = new ArrayList<ErrorTemplate>();
		
		//Nothing to init
				
		return errorList;
		}
	/**************/
	
	/***************
	 * Delete
	 */
	public void doDeleteVersion105() throws Exception
		{
		Variables.getLogger().warn("UCCX Skill deletion is not implemented");
		}
	/**************/

	/***************
	 * Injection
	 */
	public String doInjectVersion105() throws Exception
		{
		Variables.getLogger().warn("UCCX Skill injection is not implemented");
		
		return null;
		}
	/**************/
	
	/***************
	 * Update
	 */
	public void doUpdateVersion105(ArrayList<ToUpdate> tuList) throws Exception
		{		
		Variables.getLogger().warn("UCCX Skill update is not implemented");
		}
	/**************/
	
	
	/*************
	 * Get
	 */
	public ItemToInject doGetVersion105() throws Exception
		{
		Skill myS = new Skill(name);
		
		//Not used but still written
		
		return myS;//Return a Skill
		}
	/****************/

	public int getLevel()
		{
		return level;
		}

	public void setLevel(int level)
		{
		this.level = level;
		}

	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

