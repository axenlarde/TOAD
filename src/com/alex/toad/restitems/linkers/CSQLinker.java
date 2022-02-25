package com.alex.toad.restitems.linkers;

import java.util.ArrayList;

import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.restitems.misc.RESTItemLinker;
import com.alex.toad.utils.Variables;


/**********************************
 * Is the RESTItem design to link the item "CSQ"
 * and the UCCX REST API without version dependencies
 * 
 * @author RATEL Alexandre
 **********************************/
public class CSQLinker extends RESTItemLinker
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
	public CSQLinker(String name) throws Exception
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
		Variables.getLogger().warn("UCCX CSQ deletion is not implemented");
		}
	/**************/

	/***************
	 * Injection
	 */
	public String doInjectVersion105() throws Exception
		{
		Variables.getLogger().warn("UCCX CSQ injection is not implemented");
		
		return null;
		}
	/**************/
	
	/***************
	 * Update
	 */
	public void doUpdateVersion105(ArrayList<ToUpdate> tuList) throws Exception
		{		
		Variables.getLogger().warn("UCCX CSQ update is not implemented");
		}
	/**************/
	
	
	/*************
	 * Get
	 */
	public ItemToInject doGetVersion105() throws Exception
		{
		return RESTTools.doGetCSQ(this.name);
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

