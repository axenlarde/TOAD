package com.alex.toad.axlitems.linkers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.alex.toad.axlitems.misc.AXLItemLinker;
import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.cucm.user.items.User;
import com.alex.toad.cucm.user.misc.UserError;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ErrorTemplate.errorType;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.misc.SimpleRequest;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;
import com.cisco.axl.api._10.XFkType;


/**********************************
 * Is the AXLItem design to link the item "User"
 * and the Cisco AXL API without version dependencies
 * 
 * @author RATEL Alexandre
 **********************************/
public class UserLinker extends AXLItemLinker
	{
	/**
	 * Variables
	 */
	private String lastname,//Name is the userID
	firstname,
	telephoneNumber,
	userLocale,
	subscribeCallingSearchSpaceName,
	primaryExtension,
	ipccExtension,
	routePartition,
	password,
	serviceProfile,
	pin;
	
	private boolean isLocal;
	
	private ArrayList<String> userControlGroupList;
	private ArrayList<String> deviceList;
	private ArrayList<String> UDPList;
	private ArrayList<String> ctiUDPList;
	
	public enum toUpdate implements ToUpdate
		{
		lastname,
		firstname,
		telephoneNumber,
		userLocale,
		password,
		pin,
		subscribeCallingSearchSpaceName,
		primaryExtension,
		ipccExtension,
		serviceProfile,
		userControlGroup,
		devices,
		udps,
		ctiudps
		}
	
	/***************
	 * Constructor
	 * @throws Exception 
	 ***************/
	public UserLinker(String name) throws Exception
		{
		super(name);
		deviceList = new ArrayList<String>();
		UDPList = new ArrayList<String>();
		ctiUDPList = new ArrayList<String>();
		isLocal = true;
		}
	
	/***************
	 * Initialization
	 */
	public ArrayList<ErrorTemplate> doInitVersion105() throws Exception
		{
		ArrayList<ErrorTemplate> errorList = new ArrayList<ErrorTemplate>();
		
		//CSS
		try
			{
			SimpleRequest.getUUIDV105(itemType.callingsearchspace, this.subscribeCallingSearchSpaceName);
			}
		catch (Exception e)
			{
			errorList.add(new UserError(this.name, "", "Not found during init : "+e.getMessage(), itemType.user, itemType.callingsearchspace, errorType.notFound));
			}
		
		//Service profile
		try
			{
			if(serviceProfile != null)
				{
				SimpleRequest.getUUIDV105(itemType.serviceprofile, serviceProfile);
				}
			}
		catch (Exception e)
			{
			errorList.add(new UserError(this.name, "", "Not found during init : "+e.getMessage(), itemType.user, itemType.usercontrolgroup, errorType.notFound));
			}
		
		//Group
		try
			{
			if(userControlGroupList != null)
				{
				for(String s : userControlGroupList)
					{
					SimpleRequest.getUUIDV105(itemType.usercontrolgroup, s);
					}
				}
			}
		catch (Exception e)
			{
			errorList.add(new UserError(this.name, "", "Not found during init : "+e.getMessage(), itemType.user, itemType.usercontrolgroup, errorType.notFound));
			}
		
		//User local
		try
			{
			//Proceed only if the user local is not empty
			if((userLocale != null) && (!this.getUserLocale().equals("")))
				{
				//Here we need a SQL request to fetch the user local list
				List<Object> SQLResp = SimpleRequest.doSQLQuery("select name from typeuserlocale");
				
				boolean found = false;
				
				for(Object o : SQLResp)
					{
					Element rowElement = (Element) o;
					NodeList list = rowElement.getChildNodes();
					
					for(int i = 0; i< list.getLength(); i++)
						{
						if(list.item(i).getTextContent().equals(this.getUserLocale()))
							{
							Variables.getLogger().debug("User local found in the CUCM : "+list.item(i).getTextContent());
							found = true;
							break;
							}
						}
					if(found)break;
					}
				
				if(!found)throw new Exception("The following user local was not found in the CUCM : "+this.getUserLocale());
				}
			}
		catch (Exception e)
			{
			errorList.add(new UserError(this.name, "", "Not found during init : "+e.getMessage(), itemType.user, itemType.userlocal, errorType.notFound));
			}
				
		return errorList;
		}
	/**************/
	
	/***************
	 * Delete
	 */
	public void doDeleteVersion105() throws Exception
		{
		com.cisco.axl.api._10.RemoveUserReq deleteUserReq = new com.cisco.axl.api._10.RemoveUserReq();
		
		deleteUserReq.setUuid((SimpleRequest.getUUIDV105(itemType.user, this.getName())).getUuid());//We add the parameters to the request
		com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().removeUser(deleteUserReq);//We send the request to the CUCM
		}
	/**************/

	/***************
	 * Injection
	 */
	public String doInjectVersion105() throws Exception
		{
		com.cisco.axl.api._10.AddUserReq req = new com.cisco.axl.api._10.AddUserReq();
		com.cisco.axl.api._10.XUser params = new com.cisco.axl.api._10.XUser();
		
		/**
		 * We set the item parameters
		 */
		params.setUserid(this.getName());//Name
		params.setFirstName(this.firstname);
		params.setLastName(this.lastname);
		params.setTelephoneNumber(this.telephoneNumber);
		params.setUserLocale(new JAXBElement(new QName("userLocale"), String.class, this.userLocale));
		params.setSubscribeCallingSearchSpaceName(new JAXBElement(new QName("subscribeCallingSearchSpaceName"), com.cisco.axl.api._10.XFkType.class, SimpleRequest.getUUIDV105(itemType.callingsearchspace, subscribeCallingSearchSpaceName)));
		params.setPassword(this.password);
		params.setPin(this.pin);
		params.setHomeCluster("true");
		params.setImAndPresenceEnable("true");
		params.setServiceProfile(new JAXBElement(new QName("serviceProfile"), com.cisco.axl.api._10.XFkType.class, SimpleRequest.getUUIDV105(itemType.serviceprofile, serviceProfile)));
		
		//Primary extension
		if(UsefulMethod.isNotEmpty(primaryExtension))
			{
			com.cisco.axl.api._10.XUser.PrimaryExtension myP = new com.cisco.axl.api._10.XUser.PrimaryExtension();
			myP.setPattern(this.primaryExtension);
			myP.setRoutePartitionName(this.routePartition);
			params.setPrimaryExtension(myP);
			}
		
		//IPCC extension
		if(UsefulMethod.isNotEmpty(ipccExtension))
			{
			//params.setIpccExtension(new JAXBElement(new QName("ipccExtension"), com.cisco.axl.api._10.XFkType.class, SimpleRequest.getLineUUIDV105(this.ipccExtension, this.routePartition)));
			com.cisco.axl.api._10.XFkType extension = SimpleRequest.getLineUUIDV105(this.ipccExtension, this.routePartition);
			extension.setValue(this.ipccExtension);
			params.setIpccExtension(new JAXBElement(new QName("ipccExtension"), com.cisco.axl.api._10.XFkType.class, extension));//To test again
			//params.setIpccExtension(new JAXBElement(new QName("ipccExtension"), String.class, this.ipccExtension));//To test
			}
		
		//User groups
		if(userControlGroupList.size() > 0)
			{
			com.cisco.axl.api._10.XUser.AssociatedGroups myGroups = new com.cisco.axl.api._10.XUser.AssociatedGroups();
			
			for(String s : userControlGroupList)
				{
				com.cisco.axl.api._10.XUser.AssociatedGroups.UserGroup myGroup = new com.cisco.axl.api._10.XUser.AssociatedGroups.UserGroup();
				myGroup.setName(s);
				myGroups.getUserGroup().add(myGroup);
				}
			
			params.setAssociatedGroups(myGroups);
			}
		
		//Device
		if(deviceList.size() > 0)
			{
			com.cisco.axl.api._10.XUser.AssociatedDevices myDevices = new com.cisco.axl.api._10.XUser.AssociatedDevices();
			
			for(String s : deviceList)
				{
				myDevices.getDevice().add(s);
				}
			
			params.setAssociatedDevices(myDevices);
			}
		
		//UDP
		if(UDPList.size() > 0)
			{
			com.cisco.axl.api._10.XUser.PhoneProfiles myUDPs = new com.cisco.axl.api._10.XUser.PhoneProfiles();
			
			for(String udp : UDPList)
				{
				myUDPs.getProfileName().add(SimpleRequest.getUUIDV105(itemType.udp, udp));
				}
			
			params.setPhoneProfiles(myUDPs);
			}
		
		//ctiUDP
		if(ctiUDPList.size() > 0)
			{
			com.cisco.axl.api._10.XUser.CtiControlledDeviceProfiles myCtiUDPs = new com.cisco.axl.api._10.XUser.CtiControlledDeviceProfiles();
			
			for(String udp : ctiUDPList)
				{
				myCtiUDPs.getProfileName().add(SimpleRequest.getUUIDV105(itemType.udp, udp));
				}
			
			params.setCtiControlledDeviceProfiles(myCtiUDPs);
			}
		/************/
		
		req.setUser(params);//We add the parameters to the request
		com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().addUser(req);//We send the request to the CUCM
		
		return resp.getReturn();//Return UUID
		}
	/**************/
	
	/***************
	 * Update
	 */
	public void doUpdateVersion105(ArrayList<ToUpdate> tuList) throws Exception
		{
		com.cisco.axl.api._10.UpdateUserReq req = new com.cisco.axl.api._10.UpdateUserReq();
		
		/***********
		 * We set the item parameters
		 */
		req.setUserid(this.getName());
		req.setHomeCluster("true");
		
		if(tuList.contains(toUpdate.firstname) && isLocal)req.setFirstName(this.firstname);
		if(tuList.contains(toUpdate.lastname) && isLocal)req.setLastName(this.lastname);
		if(tuList.contains(toUpdate.telephoneNumber) && isLocal)req.setTelephoneNumber(this.telephoneNumber);
		if(tuList.contains(toUpdate.userLocale))req.setUserLocale(new JAXBElement(new QName("userLocale"), String.class, this.userLocale));
		if(tuList.contains(toUpdate.pin))req.setPin(this.pin);
		if(tuList.contains(toUpdate.password) && isLocal)req.setPassword(this.password);
		if(tuList.contains(toUpdate.subscribeCallingSearchSpaceName))req.setSubscribeCallingSearchSpaceName(new JAXBElement(new QName("subscribeCallingSearchSpaceName"), com.cisco.axl.api._10.XFkType.class, SimpleRequest.getUUIDV105(itemType.callingsearchspace, subscribeCallingSearchSpaceName)));
		if(tuList.contains(toUpdate.serviceProfile))req.setServiceProfile(new JAXBElement(new QName("serviceProfile"), com.cisco.axl.api._10.XFkType.class, SimpleRequest.getUUIDV105(itemType.serviceprofile, serviceProfile)));
		
		if(tuList.contains(toUpdate.primaryExtension))
			{
			com.cisco.axl.api._10.UpdateUserReq.PrimaryExtension myP = new com.cisco.axl.api._10.UpdateUserReq.PrimaryExtension();
			myP.setPattern(this.primaryExtension);
			myP.setRoutePartitionName(this.routePartition);
			req.setPrimaryExtension(myP);
			}
		
		if(tuList.contains(toUpdate.ipccExtension))
			{
			com.cisco.axl.api._10.XFkType extension = SimpleRequest.getLineUUIDV105(this.ipccExtension, this.routePartition);
			extension.setValue(this.ipccExtension);
			req.setIpccExtension(new JAXBElement(new QName("ipccExtension"), com.cisco.axl.api._10.XFkType.class, extension));
			}
		
		if(tuList.contains(toUpdate.userControlGroup))
			{
			if(userControlGroupList.size() > 0)
				{
				com.cisco.axl.api._10.UpdateUserReq.AssociatedGroups myGroups = new com.cisco.axl.api._10.UpdateUserReq.AssociatedGroups();
				
				for(String s : userControlGroupList)
					{
					com.cisco.axl.api._10.UpdateUserReq.AssociatedGroups.UserGroup myG = new com.cisco.axl.api._10.UpdateUserReq.AssociatedGroups.UserGroup();
					myG.setName(s);
					myGroups.getUserGroup().add(myG);
					}
				
				req.setAssociatedGroups(myGroups);
				}
			}
		
		if(tuList.contains(toUpdate.devices))
			{
			if(deviceList.size() > 0)
				{
				com.cisco.axl.api._10.UpdateUserReq.AssociatedDevices myDevices = new com.cisco.axl.api._10.UpdateUserReq.AssociatedDevices();
				
				for(String s : deviceList)
					{
					myDevices.getDevice().add(s);
					}
				
				req.setAssociatedDevices(myDevices);
				}
			}
		
		if(tuList.contains(toUpdate.udps))
			{
			if(UDPList.size() > 0)
				{
				com.cisco.axl.api._10.UpdateUserReq.PhoneProfiles myUDPs = new com.cisco.axl.api._10.UpdateUserReq.PhoneProfiles();
				
				for(String udp : UDPList)
					{
					myUDPs.getProfileName().add(SimpleRequest.getUUIDV105(itemType.udp, udp));
					}
				
				req.setPhoneProfiles(myUDPs);
				}
			}
		if(tuList.contains(toUpdate.ctiudps))
			{
			if(ctiUDPList.size() > 0)
				{
				com.cisco.axl.api._10.UpdateUserReq.CtiControlledDeviceProfiles myCtiUDPs = new com.cisco.axl.api._10.UpdateUserReq.CtiControlledDeviceProfiles();
				
				for(String udp : ctiUDPList)
					{
					myCtiUDPs.getProfileName().add(SimpleRequest.getUUIDV105(itemType.udp, udp));
					}
				
				req.setCtiControlledDeviceProfiles(myCtiUDPs);
				}
			}
		/************/
		
		com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().updateUser(req);//We send the request to the CUCM
		}
	/**************/
	
	
	/*************
	 * Get
	 */
	public ItemToInject doGetVersion105() throws Exception
		{
		com.cisco.axl.api._10.GetUserReq req = new com.cisco.axl.api._10.GetUserReq();
		
		/******************
		 * We set the item parameters
		 */
		req.setUserid(this.getName());
		/************/
		
		com.cisco.axl.api._10.GetUserRes resp = Variables.getAXLConnectionToCUCMV105().getUser(req);//We send the request to the CUCM
		
		User myU = new User(this.getName());
		myU.setUUID(resp.getReturn().getUser().getUuid());
		myU.setFirstname(resp.getReturn().getUser().getFirstName());
		myU.setLastname(resp.getReturn().getUser().getLastName());
		myU.setTelephoneNumber(resp.getReturn().getUser().getTelephoneNumber());
		myU.setPrimaryExtension(resp.getReturn().getUser().getPrimaryExtension().getPattern());
		myU.setIpccExtension(resp.getReturn().getUser().getIpccExtension().getValue());
		myU.setServiceProfile(resp.getReturn().getUser().getServiceProfile().getValue());
		
		//Get the devices
		for(String d : resp.getReturn().getUser().getAssociatedDevices().getDevice())
			{
			deviceList.add(d);
			}
		myU.setDeviceList(deviceList);
		
		//Get the UDP
		for(XFkType d : resp.getReturn().getUser().getPhoneProfiles().getProfileName())
			{
			UDPList.add(d.getValue());
			}
		myU.setUDPList(UDPList);
		
		
		//Get the ctiUDP
		for(XFkType d : resp.getReturn().getUser().getCtiControlledDeviceProfiles().getProfileName())
			{
			ctiUDPList.add(d.getValue());
			}
		myU.setCtiUDPList(ctiUDPList);
		
		return myU;//Return a User
		}
	/****************/

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

