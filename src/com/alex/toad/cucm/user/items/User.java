package com.alex.toad.cucm.user.items;

import java.util.ArrayList;

import com.alex.toad.axlitems.linkers.UserLinker;
import com.alex.toad.misc.CollectionTools;
import com.alex.toad.misc.EmptyValueException;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.misc.SimpleRequest;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.UserSource;
import com.alex.toad.utils.Variables.actionType;
import com.alex.toad.utils.Variables.itemType;
import com.alex.toad.utils.Variables.statusType;
import com.alex.toad.webserver.AgentData;


/**********************************
 * Class used to define an item of type "User"
 * 
 * @author RATEL Alexandre
 **********************************/

public class User extends ItemToInject
	{
	/**
	 * Variables
	 */
	private UserLinker myUser;
	private String targetName,
	lastname,//Name is the userID
	firstname,
	telephoneNumber,
	userLocale,
	subscribeCallingSearchSpaceName,
	primaryExtension,
	ipccExtension,
	routePartition,
	pin,
	password,
	serviceProfile;
	
	private ArrayList<String> userControlGroupList;
	private ArrayList<String> deviceList;
	private ArrayList<String> UDPList;
	private ArrayList<String> ctiUDPList;
	
	boolean isLocal;
	
	private AgentData agentData; 

	/***************
	 * Constructor
	 * @throws Exception
	 ***************/
	public User(String targetName,
			String name,
			ArrayList<String> deviceList,
			ArrayList<String> UDPList,
			ArrayList<String> ctiUDPList,
			ArrayList<String> userControlGroupList,
			String lastname,
			String firstname,
			String telephoneNumber,
			String userLocale,
			String subscribeCallingSearchSpaceName,
			String primaryExtension,
			String ipccExtension,
			String routePartition,
			String pin,
			String password,
			String serviceProfile) throws Exception
		{
		super(itemType.user, name);
		this.targetName = targetName;
		myUser = new UserLinker(name);
		this.deviceList = deviceList;
		this.UDPList = UDPList;
		this.ctiUDPList = ctiUDPList;
		this.lastname = lastname;
		this.firstname = firstname;
		this.telephoneNumber = telephoneNumber;
		this.userLocale = userLocale;
		this.subscribeCallingSearchSpaceName = subscribeCallingSearchSpaceName;
		this.primaryExtension = primaryExtension;
		this.ipccExtension = ipccExtension;
		this.routePartition = routePartition;
		this.userControlGroupList = userControlGroupList;
		this.pin = pin;
		this.password = password;
		this.serviceProfile = serviceProfile;
		}

	public User(String name,
			String deviceName,
			String UDPName,
			String ctiUDPName,
			String userControlGroupName,
			String lastname,
			String firstname,
			String telephoneNumber,
			String userLocale,
			String subscribeCallingSearchSpaceName,
			String primaryExtension,
			String ipccExtension,
			String routePartition,
			String pin,
			String password,
			String serviceProfile) throws Exception
		{
		super(itemType.user, name);
		myUser = new UserLinker(name);
		deviceList = new ArrayList<String>();
		if(!((deviceName == null) || (deviceName.equals(""))))deviceList.add(deviceName);
		UDPList = new ArrayList<String>();
		if(!((UDPName == null) || (UDPName.equals(""))))UDPList.add(UDPName);
		ctiUDPList = new ArrayList<String>();
		if(!((ctiUDPName == null) || (ctiUDPName.equals(""))))ctiUDPList.add(ctiUDPName);
		userControlGroupList = new ArrayList<String>();
		if(!((userControlGroupName == null) || (userControlGroupName.equals(""))))userControlGroupList.add(userControlGroupName);
		this.lastname = lastname;
		this.firstname = firstname;
		this.telephoneNumber = telephoneNumber;
		this.userLocale = userLocale;
		this.subscribeCallingSearchSpaceName = subscribeCallingSearchSpaceName;
		this.primaryExtension = primaryExtension;
		this.ipccExtension = ipccExtension;
		this.routePartition = routePartition;
		this.pin = pin;
		this.password = password;
		this.serviceProfile = serviceProfile;
		}
	
	public User(String name) throws Exception
		{
		super(itemType.user, name);
		myUser = new UserLinker(name);
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
		myUser.setName(this.getName());
		myUser.setFirstname(firstname);
		myUser.setLastname(lastname);
		myUser.setTelephoneNumber(telephoneNumber);
		myUser.setUserLocale(userLocale);
		myUser.setSubscribeCallingSearchSpaceName(subscribeCallingSearchSpaceName);
		myUser.setPrimaryExtension(primaryExtension);
		myUser.setIpccExtension(ipccExtension);
		myUser.setRoutePartition(routePartition);
		myUser.setUserControlGroupList(userControlGroupList);
		myUser.setPassword(password);
		myUser.setPin(pin);
		myUser.setDeviceList(deviceList);
		myUser.setUDPList(UDPList);
		myUser.setCtiUDPList(ctiUDPList);
		myUser.setServiceProfile(serviceProfile);
		/***********/
		
		/**
		 * If the user is an LDAP one we will update it instead of inject it
		 */
		isLocal = SimpleRequest.isUserLocal(name);
		myUser.setLocal(isLocal);
		
		if((this.status == statusType.injected) && 
			!isLocal &&
			!(this.action.equals(actionType.delete)))
			{
			setStatus(statusType.waiting);
			setAction(actionType.update);
			}
		
		errorList.addAll(myUser.init());
		}
	
	
	/**
	 * Method used to inject data in the CUCM using
	 * the Cisco API
	 * 
	 * It also return the item's UUID once injected
	 */
	public String doInject() throws Exception
		{
		return myUser.inject();//Return UUID
		}

	/**
	 * Method used to delete data in the CUCM using
	 * the Cisco API
	 */
	public void doDelete() throws Exception
		{
		myUser.delete();
		
		/***********
		 * We trigger an agent refresh to make the UCCX discover that 
		 * the agent has been deleted
		 */
		RESTTools.doAgentRefresh();
		/**************/
		}

	/**
	 * Method used to delete data in the CUCM using
	 * the Cisco API
	 */
	public void doUpdate() throws Exception
		{
		myUser.update(tuList);
		}
	
	public void doGet() throws Exception
		{
		User myU = (User) myUser.get();
		UUID = myU.getUUID();
		firstname = myU.getFirstname();
		lastname = myU.getLastname();
		telephoneNumber = myU.getTelephoneNumber();
		primaryExtension = myU.getPrimaryExtension();
		ipccExtension = myU.getIpccExtension();
		deviceList = myU.getDeviceList();
		UDPList = myU.getUDPList();
		ctiUDPList = myU.getCtiUDPList();
		serviceProfile = myU.getServiceProfile();
		
		Variables.getLogger().debug("Item "+this.name+" data fetch from the CUCM");
		}
	
	/**
	 * Method used to check if the element exist in the CUCM
	 */
	public boolean doExist() throws Exception
		{
		UUID = SimpleRequest.getUUID(type, name);
		
		Variables.getLogger().debug("Item "+this.name+" exists in the CUCM");
		return true;
		}
	
	public String getInfo()
		{
		return UsefulMethod.convertItemTypeToVerbose(type)+" "+name;
		}
	
	/**
	 * Method used to resolve pattern into real value
	 */
	public void resolve() throws Exception
		{
		name = CollectionTools.applyPattern(agentData, name, this, true);
		lastname = CollectionTools.applyPattern(agentData, lastname, this, true);
		firstname = CollectionTools.applyPattern(agentData, firstname, this, false);
		telephoneNumber = CollectionTools.applyPattern(agentData, telephoneNumber, this, false);
		userLocale = CollectionTools.applyPattern(agentData, userLocale, this, false);
		subscribeCallingSearchSpaceName = CollectionTools.applyPattern(agentData, subscribeCallingSearchSpaceName, this, false);
		primaryExtension = CollectionTools.applyPattern(agentData, primaryExtension, this, false);
		ipccExtension = CollectionTools.applyPattern(agentData, ipccExtension, this, false);
		routePartition = CollectionTools.applyPattern(agentData, routePartition, this, false);
		pin = CollectionTools.applyPattern(agentData, pin, this, false);
		password = CollectionTools.applyPattern(agentData, password, this, false);
		serviceProfile = CollectionTools.applyPattern(agentData, serviceProfile, this, false);
		
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
		
		//UDP
		ArrayList<String> udpList = new ArrayList<String>();
		for(int i=0; i<UDPList.size(); i++)
			{
			try
				{
				udpList.add(CollectionTools.applyPattern(agentData, UDPList.get(i), this, true));
				}
			catch(EmptyValueException eve)
				{
				Variables.getLogger().debug("User "+name+" the UDP "+i+" "+UDPList.get(i)+
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
		UDPList = udpList;
		ctiUDPList = ctiudpList;
		}
	
	/**
	 * Manage the content of the "To Update List"
	 */
	public void manageTuList() throws Exception
		{
		if(UsefulMethod.isNotEmpty(lastname))tuList.add(UserLinker.toUpdate.lastname);
		if(UsefulMethod.isNotEmpty(firstname))tuList.add(UserLinker.toUpdate.firstname);
		if(UsefulMethod.isNotEmpty(telephoneNumber))tuList.add(UserLinker.toUpdate.telephoneNumber);
		if(UsefulMethod.isNotEmpty(userLocale))tuList.add(UserLinker.toUpdate.userLocale);
		if(UsefulMethod.isNotEmpty(subscribeCallingSearchSpaceName))tuList.add(UserLinker.toUpdate.subscribeCallingSearchSpaceName);
		if(UsefulMethod.isNotEmpty(primaryExtension))tuList.add(UserLinker.toUpdate.primaryExtension);
		if(UsefulMethod.isNotEmpty(ipccExtension))tuList.add(UserLinker.toUpdate.ipccExtension);
		if(UsefulMethod.isNotEmpty(pin))tuList.add(UserLinker.toUpdate.pin);
		if(UsefulMethod.isNotEmpty(password))tuList.add(UserLinker.toUpdate.password);
		if(UsefulMethod.isNotEmpty(serviceProfile))tuList.add(UserLinker.toUpdate.serviceProfile);
		if((userControlGroupList != null) && (userControlGroupList.size() != 0))tuList.add(UserLinker.toUpdate.userControlGroup);
		if((deviceList != null) && (deviceList.size() != 0))tuList.add(UserLinker.toUpdate.devices);
		if((UDPList != null) && (UDPList.size() != 0))tuList.add(UserLinker.toUpdate.udps);
		if((ctiUDPList != null) && (ctiUDPList.size() != 0))tuList.add(UserLinker.toUpdate.ctiudps);
		}

	public String getLastname()
		{
		return lastname;
		}

	public void setLastname(String lastname)
		{
		this.lastname = lastname;
		}

	public String getFirstname()
		{
		return firstname;
		}

	public void setFirstname(String firstname)
		{
		this.firstname = firstname;
		}

	public ArrayList<String> getDeviceList()
		{
		return deviceList;
		}

	public void setDeviceList(ArrayList<String> deviceList)
		{
		this.deviceList = deviceList;
		}

	public ArrayList<String> getUDPList()
		{
		return UDPList;
		}

	public void setUDPList(ArrayList<String> uDPList)
		{
		UDPList = uDPList;
		}

	public String getPin()
		{
		return pin;
		}

	public void setPin(String pin)
		{
		this.pin = pin;
		}

	public UserLinker getMyUser()
		{
		return myUser;
		}

	public void setMyUser(UserLinker myUser)
		{
		this.myUser = myUser;
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

	public String getSubscribeCallingSearchSpaceName()
		{
		return subscribeCallingSearchSpaceName;
		}

	public void setSubscribeCallingSearchSpaceName(
			String subscribeCallingSearchSpaceName)
		{
		this.subscribeCallingSearchSpaceName = subscribeCallingSearchSpaceName;
		}

	public String getPrimaryExtension()
		{
		return primaryExtension;
		}

	public void setPrimaryExtension(String primaryExtension)
		{
		this.primaryExtension = primaryExtension;
		}

	public String getRoutePartition()
		{
		return routePartition;
		}

	public void setRoutePartition(String routePartition)
		{
		this.routePartition = routePartition;
		}

	public ArrayList<String> getUserControlGroupList()
		{
		return userControlGroupList;
		}

	public void setUserControlGroupList(ArrayList<String> userControlGroupList)
		{
		this.userControlGroupList = userControlGroupList;
		}

	public String getTelephoneNumber()
		{
		return telephoneNumber;
		}

	public void setTelephoneNumber(String telephoneNumber)
		{
		this.telephoneNumber = telephoneNumber;
		}

	public String getUserLocale()
		{
		return userLocale;
		}

	public void setUserLocale(String userLocale)
		{
		this.userLocale = userLocale;
		}

	public String getIpccExtension()
		{
		return ipccExtension;
		}

	public void setIpccExtension(String ipccExtension)
		{
		this.ipccExtension = ipccExtension;
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

	public String getServiceProfile()
		{
		return serviceProfile;
		}

	public void setServiceProfile(String serviceProfile)
		{
		this.serviceProfile = serviceProfile;
		}

	public boolean isLocal()
		{
		return isLocal;
		}

	public void setLocal(boolean isLocal)
		{
		this.isLocal = isLocal;
		}

	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

