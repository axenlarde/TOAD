package com.alex.toad.misc;

/**********************************
 * Interface design to force to define the following method
 * 
 * @author RATEL Alexandre
 **********************************/
public interface ItemToInjectImpl
	{
	public void doBuild() throws Exception; //Used to prepare the item for the injection by gathering the needed UUID
	
	public String doInject() throws Exception; //Return the UUID of the injected item
	
	public void doDelete() throws Exception; //Delete the item
	
	public void doUpdate() throws Exception; //Update the item
	
	public void doGet() throws Exception; //Get item's data from the server
	
	public boolean doExist() throws Exception; //Check if the item exists
		
	public String getInfo(); //Get item's info
	
	public void resolve() throws Exception; //Resolve the item content using the xml templates
	
	public void manageTuList() throws Exception;
	
	/*2022*//*RATEL Alexandre 8)*/
	}

