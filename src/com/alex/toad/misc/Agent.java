package com.alex.toad.misc;

import java.util.ArrayList;

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
	private ArrayList<ItemToInject> deviceList;
	
	/**
	 * Constructor
	 */
	public Agent(UCCXAgent agent, User user, ArrayList<ItemToInject> deviceList)
		{
		super();
		this.agent = agent;
		this.user = user;
		this.deviceList = deviceList;
		}

	public UCCXAgent getAgent()
		{
		return agent;
		}

	public User getUser()
		{
		return user;
		}

	public ArrayList<ItemToInject> getDeviceList()
		{
		return deviceList;
		}
	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

