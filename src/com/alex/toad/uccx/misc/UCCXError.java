package com.alex.toad.uccx.misc;

import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.utils.Variables.itemType;

/**********************************
 * To store a UCCX error
 * 
 * @author RATEL Alexandre
 **********************************/
public class UCCXError extends ErrorTemplate
	{

	/***************
	 * Constructor
	 ***************/
	public UCCXError(String targetName, String issueName, String errorDesc,
			itemType tagetType, itemType issueType, errorType error)
		{
		super(targetName, issueName, errorDesc, tagetType, issueType, error);
		// TODO Auto-generated constructor stub
		}

	/***************
	 * Constructor
	 ***************/
	public UCCXError(String targetName, String issueName, String errorDesc,
			String advice, itemType tagetType, itemType issueType,
			errorType error, boolean warning)
		{
		super(targetName, issueName, errorDesc, advice, tagetType, issueType, error,
				warning);
		// TODO Auto-generated constructor stub
		}

	/***************
	 * Constructor
	 ***************/
	public UCCXError(String errorDesc)
		{
		super(errorDesc);
		// TODO Auto-generated constructor stub
		}

	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

