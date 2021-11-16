package com.alex.toad.misc;

import com.alex.toad.utils.Variables.multipleRequestType;

public class MultipleValueRequiredException extends Exception
	{
	/**
	 * Variables
	 */
	private multipleRequestType requestType;
	
	/**
	 * Constructor
	 */
	public MultipleValueRequiredException(multipleRequestType requestType)
		{
		super();
		this.requestType = requestType;
		}

	public multipleRequestType getRequestType()
		{
		return requestType;
		}
	
	
	/*2018*//*RATEL Alexandre 8)*/
	}
