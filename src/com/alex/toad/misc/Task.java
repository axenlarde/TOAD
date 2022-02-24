package com.alex.toad.misc;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;

import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.actionType;
import com.alex.toad.utils.Variables.statusType;
import com.alex.toad.webserver.ManageWebRequest.webRequestType;
import com.alex.toad.webserver.WebRequest;



/**********************************
 * Class used to store a list of todo
 * 
 * It also allowed to launch the task
 * 
 * @author RATEL Alexandre
 **********************************/
public class Task extends Thread
	{
	/**
	 * Variables
	 */
	private ArrayList<ItemToInject> todoList;
	private statusType status;
	private boolean pause, stop, started, end;
	private int progress;
	private WebRequest request;
	private String taskID;
	
	/***************
	 * Constructor
	 ***************/
	public Task(ArrayList<ItemToInject> todoList, WebRequest request)
		{
		this.todoList = todoList;
		this.status = statusType.init;
		stop = false;
		pause = false;
		started = false;
		end = false;
		progress = 0;
		this.taskID = DigestUtils.md5Hex(System.currentTimeMillis()+Math.random()+"8)");
		this.request = request;
		}
	
	/******
	 * Used to start the build process
	 * @throws Exception 
	 */
	public void startBuildProcess() throws Exception
		{
		//Build
		Variables.getLogger().info("Beginning of the build process");
		for(ItemToInject myToDo : todoList)
			{
			myToDo.build();
			
			if(myToDo.getErrorList().size() != 0)
				{
				//Something happened during the building process so we disable the item
				Variables.getLogger().info("The following item has been disabled because some errors occurs during its preparation process : "+myToDo.getType().name()+" "+myToDo.getName());
				for(ErrorTemplate e : myToDo.getErrorList())
					{
					Variables.getLogger().debug("- "+e.getTargetName()+" "+e.getIssueName()+" "+e.getErrorDesc()+" "+e.getError().name()+" "+e.getIssueType().name());
					}
				myToDo.setStatus(statusType.disabled);
				}
			}
		Variables.getLogger().info("End of the build process");
		}
	
	public void run()
		{
		started = true;
		try
			{
			Variables.getLogger().info("Task begins");
			this.status = statusType.processing;
			
			//Execution
			for(ItemToInject myToDo : todoList)
				{
				while(pause)
					{
					this.sleep(200);
					}
				
				if(!stop)
					{
					try
						{
						if(myToDo.getStatus().equals(statusType.waiting))
							{
							if(myToDo.getStatus().equals(statusType.disabled))
								{
								Variables.getLogger().debug("The item \""+myToDo.getName()+"\" has been disabled so we do not process it");
								
								//It is not normal to get a disabled item here to better to abort
								throw new Exception(myToDo.getInfo()+" : is disabled, better to abort the whole process");
								}
							else if(myToDo.getAction().equals(actionType.inject))
								{
								myToDo.inject();
								}
							else if(myToDo.getAction().equals(actionType.delete))
								{
								myToDo.delete();
								}
							else if(myToDo.getAction().equals(actionType.update))
								{
								/**
								 * Because some dependencies may have just been injected or updated
								 * we check again if the item exists
								 */
								myToDo.isExisting();
								myToDo.update();
								}
							}
						else
							{
							Variables.getLogger().debug("The following item has not been processed because of its status \""+myToDo.getStatus().name()+"\" : "+myToDo.getName());
							myToDo.setStatus(statusType.disabled);
							}
						}
					catch (Exception e)
						{
						Variables.getLogger().error("An error occured with the item \""+myToDo.getName()+"\" : "+e.getMessage(), e);
						myToDo.setStatus(statusType.error);
						
						/**
						 * If some errors occurs we prefer to abort to avoid cascading failure
						 */
						break;
						/**********/
						}
					}
				else
					{
					Variables.getLogger().debug("The Administrator asked to stop the process");
					break;
					}
				progress++;
				}
			end = true;
			Variables.getLogger().info("Task ends");
			UsefulMethod.clean();
			defineStatus();
			}
		catch (Exception e)
			{
			Variables.getLogger().debug("ERROR : "+e.getMessage(),e);
			}
		}
	
	/**
	 * Used to find the good task status according to what happened
	 * during the process
	 */
	private void defineStatus()
		{
		for(ItemToInject myToDo : todoList)
			{
			if((myToDo.getStatus().equals(statusType.error)) || (myToDo.getErrorList().size() > 0))
				{
				this.status = statusType.error;
				return;
				}
			}
		this.status = statusType.done;
		}
	
	public String getInfo()
		{
		return request.getType().name()+" : "+progress+"/"+todoList.size()+" : "+request.getSecurityToken().getAgent().getInfo();
		}

	public ArrayList<ItemToInject> getTodoList()
		{
		return todoList;
		}

	public void setTodoList(ArrayList<ItemToInject> todoList)
		{
		this.todoList = todoList;
		}

	public statusType getStatus()
		{
		return status;
		}

	public void setStatus(statusType status)
		{
		this.status = status;
		}

	public void Stop()
		{
		this.stop = true;
		}

	public boolean isPause()
		{
		return pause;
		}

	public void setPause(boolean pause)
		{
		this.pause = pause;
		
		if(this.pause)
			{
			Variables.getLogger().debug("The user asked to pause the task");
			}
		else
			{
			Variables.getLogger().debug("The user asked to resume the task");
			}
		}

	public boolean isStop()
		{
		return stop;
		}

	public void setStop(boolean stop)
		{
		this.stop = stop;
		}

	public int getProgress()
		{
		return progress;
		}

	public boolean isStarted()
		{
		return started;
		}

	public boolean isEnd()
		{
		return end;
		}

	public void setEnd(boolean end)
		{
		this.end = end;
		}

	public String getTaskID()
		{
		return taskID;
		}
	
	
	
	
	
	
	/*2016*//*RATEL Alexandre 8)*/
	}

