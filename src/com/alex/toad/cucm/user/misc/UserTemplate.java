package com.alex.toad.cucm.user.misc;

import com.alex.toad.utils.Variables.actionType;
import com.alex.toad.utils.Variables.itemType;

/**********************************
 * Used to store a template from the User Creation profile File
 * 
 * @author RATEL Alexandre
 **********************************/
public class UserTemplate
	{
	/**
	 * Variables
	 */
	private itemType type;
	private String target;
	private actionType action;
	
	/***************
	 * Constructor
	 ***************/
	public UserTemplate(itemType type, String target, actionType action)
		{
		super();
		this.type = type;
		this.target = target;
		this.action = action;
		}
	
	public String getInfo()
		{
		return type.name()+" "+target+" "+action.name();
		}

	public itemType getType()
		{
		return type;
		}

	public void setType(itemType type)
		{
		this.type = type;
		}

	public String getTarget()
		{
		return target;
		}

	public void setTarget(String target)
		{
		this.target = target;
		}

	public actionType getAction()
		{
		return action;
		}

	public void setAction(actionType action)
		{
		this.action = action;
		}
	
	
	
	
	/*2016*//*RATEL Alexandre 8)*/
	}

