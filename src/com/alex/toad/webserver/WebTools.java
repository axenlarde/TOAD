package com.alex.toad.webserver;

import org.apache.commons.codec.digest.DigestUtils;

import com.alex.toad.misc.Agent;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;

/**********************************
* Used to store static method for web requests
* 
* @author RATEL Alexandre
**********************************/
public class WebTools
	{
	
	/**
	 * Will return the matching security token if any
	 * @throws Exception 
	 */
	public static SecurityToken getSecurityToken(String token) throws Exception
		{
		if(UsefulMethod.isNotEmpty(token))
			{
			for(SecurityToken st : Variables.getSecurityTokenList())
				{
				if(st.getToken().equals(token))return st;
				}
			}
		
		throw new Exception("The following security token was not found : "+token);
		}
	
	
	/**
	 * Used to create a new security token and add it to the list
	 * 
	 */
	public static SecurityToken newSecurityToken(Agent agent)
		{
		/**
		 * Checking for any existing token for the same user
		 */
		for(SecurityToken st : Variables.getSecurityTokenList())
			{
			if(st.getAgent().getUser().getName().equals(agent.getUser().getName()))
				{
				Variables.getLogger().debug("Existing token found for user : "+agent.getUser().getName()+" deleting and creating new one");
				Variables.getSecurityTokenList().remove(st);
				break;
				}
			}
		
		/**
		 * Generating token hash
		 */
		String hash = DigestUtils.md5Hex(Math.random()+agent.getUser().getName()).toUpperCase();
		
		/**
		 * Creating the token
		 */
		SecurityToken token = new SecurityToken(hash, agent);
		
		/**
		 * Adding the new token to the list
		 */
		Variables.getSecurityTokenList().add(token);
		
		Variables.getLogger().debug("New security token ("+hash+") created for user : "+agent.getUser().getName());
		
		return token;
		}
	
	
	
	
	
	
	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}
