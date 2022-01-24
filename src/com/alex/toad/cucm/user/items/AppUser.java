package com.alex.toad.cucm.user.items;

import java.util.ArrayList;

import com.alex.toad.axlitems.linkers.AppUserLinker;
import com.alex.toad.axlitems.linkers.UserLinker;
import com.alex.toad.misc.CollectionTools;
import com.alex.toad.misc.EmptyValueException;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;
import com.alex.toad.webserver.AgentData;


/**********************************
 * Class used to define an item of type "User"
 * 
 * @author RATEL Alexandre
 **********************************/

public class AppUser extends ItemToInject
	{
	/**
	 * Variables
	 */
	private AppUserLinker myAppUser;
	private String targetName,
	password;
	
	private ArrayList<String> userControlGroupList;
	private ArrayList<String> deviceList;
	private ArrayList<String> ctiUDPList;
	
	private AgentData agentData;

	/**
	 * Constructor
	 * @throws Exception 
	 */
	public AppUser(String name,
			String targetName, String password,
			ArrayList<String> userControlGroupList,
			ArrayList<String> deviceList, ArrayList<String> ctiUDPList) throws Exception
		{
		super(itemType.appuser, name);
		myAppUser = new AppUserLinker(name);
		this.targetName = targetName;
		this.password = password;
		this.userControlGroupList = userControlGroupList;
		this.deviceList = deviceList;
		this.ctiUDPList = ctiUDPList;
		}
	
	public AppUser(String name) throws Exception
		{
		super(itemType.appuser, name);
		myAppUser = new AppUserLinker(name);
		}

	/***********
	 * Method used to prepare the item for the injection
	 * by gathering the needed UUID from the CUCM 
	 */
	public void doBuild() throws Exception
		{
		/**
		 * We pass the local variables to the linker
		 */
		myAppUser.setName(this.getName());
		myAppUser.setUserControlGroupList(userControlGroupList);
		myAppUser.setPassword(password);
		myAppUser.setDeviceList(deviceList);
		myAppUser.setCtiUDPList(ctiUDPList);
		/***********/
		
		errorList.addAll(myAppUser.init());
		}
	
	
	/**
	 * Method used to inject data in the CUCM using
	 * the Cisco API
	 * 
	 * It also return the item's UUID once injected
	 */
	public String doInject() throws Exception
		{
		return myAppUser.inject();//Return UUID
		}

	/**
	 * Method used to delete data in the CUCM using
	 * the Cisco API
	 */
	public void doDelete() throws Exception
		{
		myAppUser.delete();
		}

	/**
	 * Method used to delete data in the CUCM using
	 * the Cisco API
	 */
	public void doUpdate() throws Exception
		{
		myAppUser.update(tuList);
		}
	
	/**
	 * Method used to check if the element exist in the CUCM
	 */
	public boolean isExisting() throws Exception
		{
		AppUser myU = (AppUser) myAppUser.get();
		UUID = myU.getUUID();
		deviceList = myU.getDeviceList();
		ctiUDPList = myU.getCtiUDPList();
		
		Variables.getLogger().debug("Item "+this.name+" exists in the CUCM");
		return true;
		}
	
	public String getInfo()
		{
		return name+" "
		+UUID;
		}
	
	/**
	 * Method used to resolve pattern into real value
	 */
	public void resolve() throws Exception
		{
		name = CollectionTools.applyPattern(agentData, name, this, true);
		password = CollectionTools.applyPattern(agentData, password, this, false);
		
		ArrayList<String> ucgList = new ArrayList<String>();
		for(String s : userControlGroupList)
			{
			try
				{
				ucgList.add(CollectionTools.applyPattern(agentData, s, this, true));
				}
			catch(EmptyValueException eve)
				{
				Variables.getLogger().debug("User "+name+" the user group "+s+
						" has not been added because it returned an empty value : "+eve.getMessage());
				}
			}
		this.userControlGroupList = ucgList;
		
		resolveDevices(agentData);
		}
	
	/**
	 * Used to resolve only the devices and UDPs
	 * @throws Exception 
	 */
	public void resolveDevices(AgentData agentData) throws Exception
		{
		//Devices
		ArrayList<String> dList = new ArrayList<String>();
		for(int i=0; i<deviceList.size(); i++)
			{
			try
				{
				dList.add(CollectionTools.applyPattern(agentData, deviceList.get(i), this, true));
				}
			catch(EmptyValueException eve)
				{
				Variables.getLogger().debug("User "+name+" the device "+i+" "+deviceList.get(i)+
						" has not been added because it returned an empty value : "+eve.getMessage());
				}
			}
		
		//ctiUDP
		ArrayList<String> ctiudpList = new ArrayList<String>();
		for(int i=0; i<ctiUDPList.size(); i++)
			{
			try
				{
				ctiudpList.add(CollectionTools.applyPattern(agentData, ctiUDPList.get(i), this, true));
				}
			catch(EmptyValueException eve)
				{
				Variables.getLogger().debug("User "+name+" the ctiUDP "+i+" "+ctiUDPList.get(i)+
						" has not been added because it returned an empty value : "+eve.getMessage());
				}
			}
		
		deviceList = dList;
		ctiUDPList = ctiudpList;
		}
	
	/**
	 * Manage the content of the "To Update List"
	 */
	public void manageTuList() throws Exception
		{
		if(UsefulMethod.isNotEmpty(password))tuList.add(UserLinker.toUpdate.password);
		if((userControlGroupList != null) && (userControlGroupList.size() != 0))tuList.add(UserLinker.toUpdate.userControlGroup);
		if((deviceList != null) && (deviceList.size() != 0))tuList.add(UserLinker.toUpdate.devices);
		if((ctiUDPList != null) && (ctiUDPList.size() != 0))tuList.add(UserLinker.toUpdate.ctiudps);
		}

	public AppUserLinker getMyAppUser()
		{
		return myAppUser;
		}

	public void setMyAppUser(AppUserLinker myAppUser)
		{
		this.myAppUser = myAppUser;
		}

	public String getTargetName()
		{
		return targetName;
		}

	public void setTargetName(String targetName)
		{
		this.targetName = targetName;
		}

	public String getPassword()
		{
		return password;
		}

	public void setPassword(String password)
		{
		this.password = password;
		}

	public ArrayList<String> getUserControlGroupList()
		{
		return userControlGroupList;
		}

	public void setUserControlGroupList(ArrayList<String> userControlGroupList)
		{
		this.userControlGroupList = userControlGroupList;
		}

	public ArrayList<String> getDeviceList()
		{
		return deviceList;
		}

	public void setDeviceList(ArrayList<String> deviceList)
		{
		this.deviceList = deviceList;
		}

	public ArrayList<String> getCtiUDPList()
		{
		return ctiUDPList;
		}

	public void setCtiUDPList(ArrayList<String> ctiUDPList)
		{
		this.ctiUDPList = ctiUDPList;
		}

	public AgentData getAgentData()
		{
		return agentData;
		}

	public void setAgentData(AgentData agentData)
		{
		this.agentData = agentData;
		}

	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

