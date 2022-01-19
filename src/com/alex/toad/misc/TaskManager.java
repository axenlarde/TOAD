package com.alex.toad.misc;

import java.util.ArrayList;

import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.webserver.ManageWebRequest.webRequestType;

/**********************************
* To start new tasks
* 
* @author RATEL Alexandre
**********************************/
public class TaskManager
	{
	
	/**
	 * To start a new task
	 * 
	 * Will return the task ID
	 */
	public static String addNewTask(ArrayList<ItemToInject> todoList, webRequestType type) throws Exception
		{
		try
			{
			//First we clear finished tasks and run GC
			Variables.getLogger().debug("Clearing task list");
			if(Variables.getTaskList().size()>0)
				{
				clearStaleTask();
				System.gc();
				}
			Variables.getLogger().debug("Task list cleared");
			
			int sum = 0;
			for(Task t : Variables.getTaskList())
				{
				if(t.isAlive())sum++;
				}
			
			if(sum < Integer.parseInt(UsefulMethod.getTargetOption("maxtaskthread")))
				{
				if(todoList.size() != 0)
					{
					Task t = new Task(todoList, type);
					Variables.getTaskList().add(t);
					t.start();
					return t.getTaskID();
					}
				else
					{
					throw new Exception("The item list was empty. No task could be started");
					}
				}
			else
				{
				throw new Exception("Max concurent task reached. You cannot start more task");
				}
			}
		catch (Exception e)
			{
			throw new Exception("ERROR while adding a new task : "+e.getMessage());
			}
		}
	
	/**
	 * To clear stale tasks
	 */
	private static void clearStaleTask()
		{
		for(Task t : Variables.getTaskList())
			{
			if(!t.isAlive())
				{
				Variables.getTaskList().remove(t);
				clearStaleTask();
				break;
				}
			}
		}
	
	/**
	 * To get a task using its ID
	 */
	public static Task getTask(String ID)
		{
		for(Task t : Variables.getTaskList())
			{
			if(t.getTaskID().equals(ID))return t;
			}
		return null;
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

