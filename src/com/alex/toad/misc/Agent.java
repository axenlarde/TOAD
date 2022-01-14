package com.alex.toad.misc;

import com.alex.toad.cucm.user.items.User;
import com.alex.toad.uccx.items.UCCXAgent;

/**********************************
* Used to describe an agent with its
* CUCM and UCCX dependencies
* 
* @author RATEL Alexandre
**********************************/
public class Agent
	{
	/**
	 * Variables
	 */
	private UCCXAgent agent;
	private User user;
	
	/**
	 * Constructor
	 */
	public Agent(UCCXAgent agent, User user)
		{
		super();
		this.agent = agent;
		this.user = user;
		}

	public UCCXAgent getAgent()
		{
		return agent;
		}

	public User getUser()
		{
		return user;
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

