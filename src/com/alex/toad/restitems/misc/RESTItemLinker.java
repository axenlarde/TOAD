package com.alex.toad.restitems.misc;

import java.util.ArrayList;

import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.UCCXRESTVersion;


/**********************************
 * Class used to define a RESTItem
 * 
 * A RESTItem is intent to link the UCCX REST API with the
 * software. Indeed the REST API is changing over time
 * and the RESTItem should be modified as less as possible
 * 
 * @author RATEL Alexandre
 **********************************/
public abstract class RESTItemLinker implements RESTItemLinkerImpl
	{
	/**
	 * Variables
	 */
	protected String name;
	
	
	/**
	 * Constructor
	 * @throws Exception 
	 */
	public RESTItemLinker(String name) throws Exception
		{
		this.name = name;
		//init();
		}
	
	/****
	 * We initialize here what is needed
	 */
	public ArrayList<ErrorTemplate> init() throws Exception
		{
		if(Variables.getUCCXVersion().equals(UCCXRESTVersion.version105))
			{
			return doInitVersion105();
			}
		else
			{
			throw new Exception("Unsupported REST Version");
			}
		}
	
	
	/**
	 * Used to define the injection process for this item
	 */
	public String inject() throws Exception
		{
		if(Variables.getUCCXVersion().equals(UCCXRESTVersion.version105))
			{
			return doInjectVersion105();
			}
		else
			{
			throw new Exception("Unsupported REST Version");
			}
		}

	/**
	 * Used to define the deletion process for this item
	 */
	public void delete() throws Exception
		{
		if(Variables.getUCCXVersion().equals(UCCXRESTVersion.version105))
			{
			doDeleteVersion105();
			}
		else
			{
			throw new Exception("Unsupported REST Version");	
			}
		}
	
	/**
	 * Used to define the update process for this item
	 */
	public void update(ArrayList<ToUpdate> tulist) throws Exception
		{
		if(Variables.getUCCXVersion().equals(UCCXRESTVersion.version105))
			{
			doUpdateVersion105(tulist);
			}
		else
			{
			throw new Exception("Unsupported REST Version");
			}
		}
	
	public ItemToInject get() throws Exception
		{
		if(Variables.getUCCXVersion().equals(UCCXRESTVersion.version105))
			{
			return doGetVersion105();
			}
		else
			{
			throw new Exception("Unsupported REST Version");
			}
		}

	public String getName()
		{
		return name;
		}

	public void setName(String name)
		{
		this.name = name;
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

