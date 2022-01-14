package com.alex.toad.restitems.misc;

import java.util.ArrayList;

import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ItemToInject;


/**********************************
 * Interface of an AXLItem
 * 
 * @author RATEL Alexandre
 **********************************/
public interface RESTItemLinkerImpl
	{
	/**
	 * Injection
	 */
	public String inject() throws Exception; //Return the UUID of the injected item
	
	//Used in addition of the previous one to force the developer to implement a method dedicated to the good version
	public String doInjectVersion105() throws Exception;
	/**************/
	
	/***********
	 * Deletion
	 */
	public void delete() throws Exception; //Delete the item in the CUCM
	
	//Used in addition of the previous one to force the developer to implement a method dedicated to the good version
	public void doDeleteVersion105() throws Exception;
	/***************/
	
	/***********
	 * Initialization
	 */
	public ArrayList<ErrorTemplate> init() throws Exception; //Initialize the item
	
	//Used in addition of the previous one to force the developer to implement a method dedicated to the good version
	public ArrayList<ErrorTemplate> doInitVersion105() throws Exception;
	/***************/
	
	/***********
	 * Update
	 */
	public void update(ArrayList<ToUpdate> tulist) throws Exception; //Initialize the item
	
	//Used in addition of the previous one to force the developer to implement a method dedicated to the good version
	public void doUpdateVersion105(ArrayList<ToUpdate> tulist) throws Exception;
	/***************/
	
	/***********
	 * Get
	 */
	public ItemToInject get() throws Exception; //Initialize the item
	
	//Used in addition of the previous one to force the developer to implement a method dedicated to the good version
	public ItemToInject doGetVersion105() throws Exception;
	/***************/
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

