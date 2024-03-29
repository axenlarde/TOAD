package com.alex.toad.cucm.user.items;

import com.alex.toad.axlitems.linkers.UdpLoginLinker;
import com.alex.toad.misc.CollectionTools;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;
import com.alex.toad.webserver.AgentData;

/**********************************
 * Class used to define an item of type "UDP Login"
 * 
 * @author RATEL Alexandre
 **********************************/

public class UdpLogin extends ItemToInject
	{
	/**
	 * Variables
	 */
	private UdpLoginLinker myUDPLogin;
	private String targetName,
	deviceName,
	deviceProfile;//UserID is the name
	
	private AgentData agentData;

	/***************
	 * Constructor
	 * @throws Exception 
	 ***************/
	public UdpLogin(String name,
			String deviceName, String deviceProfile) throws Exception
		{
		super(itemType.udplogin, name);
		this.myUDPLogin = new UdpLoginLinker(name);
		this.deviceName = deviceName;
		this.deviceProfile = deviceProfile;
		}
	
	public UdpLogin(String targetName, String name,
			String deviceName, String deviceProfile) throws Exception
		{
		super(itemType.udplogin, name);
		this.myUDPLogin = new UdpLoginLinker(name);
		this.targetName = targetName;
		this.deviceName = deviceName;
		this.deviceProfile = deviceProfile;
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
		myUDPLogin.setName(name);//It is the userID
		myUDPLogin.setDeviceName(deviceName);
		myUDPLogin.setDeviceProfile(deviceProfile);
		/**************/
		
		errorList.addAll(myUDPLogin.init());
		}
	
	
	/**
	 * Method used to inject data in the CUCM using
	 * the Cisco API
	 * 
	 * It also return the item's UUID once injected
	 */
	public String doInject() throws Exception
		{	
		return myUDPLogin.inject();//Return UUID
		}

	/**
	 * Method used to delete data in the CUCM using
	 * the Cisco API
	 */
	public void doDelete() throws Exception
		{
		myUDPLogin.delete();
		}

	/**
	 * Method used to delete data in the CUCM using
	 * the Cisco API
	 */
	public void doUpdate() throws Exception
		{
		myUDPLogin.update(tuList);
		}
	
	public void doGet() throws Exception
		{
		
		Variables.getLogger().debug("Item "+this.name+" data fetch from the CUCM");
		}
	
	/**
	 * Method used to check if the element exist in the CUCM
	 */
	public boolean doExist() throws Exception
		{
		//Nothing to get
		return false;
		}
	
	public String getInfo()
		{
		return name+" "
		+deviceName+" "
		+deviceProfile;
		}
	
	/**
	 * Method used to resolve pattern into real value
	 */
	public void resolve() throws Exception
		{
		name = CollectionTools.applyPattern(agentData, name, this, true);
		deviceName = CollectionTools.applyPattern(agentData, deviceName, this, true);
		deviceProfile = CollectionTools.applyPattern(agentData, deviceProfile, this, true);
		}
	
	/**
	 * Manage the content of the "To Update List"
	 */
	public void manageTuList() throws Exception
		{
		if(UsefulMethod.isNotEmpty(name))tuList.add(UdpLoginLinker.toUpdate.userID);
		if(UsefulMethod.isNotEmpty(deviceName))tuList.add(UdpLoginLinker.toUpdate.deviceName);
		if(UsefulMethod.isNotEmpty(deviceProfile))tuList.add(UdpLoginLinker.toUpdate.deviceProfile);
		}

	public UdpLoginLinker getMyUDPLogin()
		{
		return myUDPLogin;
		}

	public void setMyUDPLogin(UdpLoginLinker myUDPLogin)
		{
		this.myUDPLogin = myUDPLogin;
		}

	public String getDeviceName()
		{
		return deviceName;
		}

	public void setDeviceName(String deviceName)
		{
		this.deviceName = deviceName;
		}

	public String getDeviceProfile()
		{
		return deviceProfile;
		}

	public void setDeviceProfile(String deviceProfile)
		{
		this.deviceProfile = deviceProfile;
		}

	public AgentData getAgentData()
		{
		return agentData;
		}

	public void setAgentData(AgentData agentData)
		{
		this.agentData = agentData;
		}

	public String getTargetName()
		{
		return targetName;
		}

	public void setTargetName(String targetName)
		{
		this.targetName = targetName;
		}

	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

