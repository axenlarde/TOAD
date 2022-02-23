package com.alex.toad.misc;

import java.util.ArrayList;

import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.webserver.ManageWebRequest.webRequestType;
import com.alex.toad.webserver.WebRequest;

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
	public static String addNewTask(ArrayList<ItemToInject> todoList, WebRequest request) throws Exception
		{
		try
			{
			//First we clear finished tasks and run GC
			Variables.getLogger().debug("Clearing task list");
			if(Variables.getTaskList().size()>0)
				{
				System.gc();
				}
			Variables.getLogger().debug("Task list cleared");
			
			if(Variables.getTaskList().size() > Integer.parseInt(UsefulMethod.getTargetOption("maxtaskthread")))
				{
				clearStaleTask();//We keep only a certain amount of task in the history
				}
			
			int alive = 0;
			for(Task t : Variables.getTaskList())
				{
				if(t.isAlive())alive++;
				}
			
			if(alive < Integer.parseInt(UsefulMethod.getTargetOption("maxtaskthread")))
				{
				if(todoList.size() != 0)
					{
					Task t = new Task(todoList, request);
					Variables.getTaskList().add(t);
					t.startBuildProcess();
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
				throw new Exception("Max concurent task reached. You cannot start more task for the moment");
				}
			}
		catch (Exception e)
			{
			throw new Exception("ERROR while adding a new task : "+e.getMessage());
			}
		}
	
	/**
	 * To clear stale tasks
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private static void clearStaleTask() throws NumberFormatException, Exception
		{
		if(Variables.getTaskList().size() > Integer.parseInt(UsefulMethod.getTargetOption("maxtaskthread")))
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

