package com.alex.toad.axlitems.linkers;

import java.util.ArrayList;

import com.alex.toad.axlitems.misc.AXLItemLinker;
import com.alex.toad.axlitems.misc.ToUpdate;
import com.alex.toad.cucm.user.items.AppUser;
import com.alex.toad.cucm.user.misc.UserError;
import com.alex.toad.misc.ErrorTemplate;
import com.alex.toad.misc.ErrorTemplate.errorType;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.misc.SimpleRequest;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;


/**********************************
 * Is the AXLItem design to link the item "User"
 * and the Cisco AXL API without version dependencies
 * 
 * @author RATEL Alexandre
 **********************************/
public class AppUserLinker extends AXLItemLinker
	{
	/**
	 * Variables
	 */
	private String password;
	
	private ArrayList<String> userControlGroupList;
	private ArrayList<String> deviceList;
	private ArrayList<String> ctiUDPList;
	
	public enum toUpdate implements ToUpdate
		{
		password,
		userControlGroup,
		devices,
		ctiudps
		}
	
	/***************
	 * Constructor
	 * @throws Exception 
	 ***************/
	public AppUserLinker(String name) throws Exception
		{
		super(name);
		deviceList = new ArrayList<String>();
		ctiUDPList = new ArrayList<String>();
		}
	
	/***************
	 * Initialization
	 */
	public ArrayList<ErrorTemplate> doInitVersion105() throws Exception
		{
		ArrayList<ErrorTemplate> errorList = new ArrayList<ErrorTemplate>();
		
		//Group
		try
			{
			for(String s : userControlGroupList)
				{
				SimpleRequest.getUUIDV105(itemType.usercontrolgroup, s);
				}
			}
		catch (Exception e)
			{
			errorList.add(new UserError(this.name, "", "Not found during init : "+e.getMessage(), itemType.appuser, itemType.usercontrolgroup, errorType.notFound));
			}
				
		return errorList;
		}
	/**************/
	
	/***************
	 * Delete
	 */
	public void doDeleteVersion105() throws Exception
		{
		com.cisco.axl.api._10.RemoveAppUserReq deleteAppUserReq = new com.cisco.axl.api._10.RemoveAppUserReq();
		
		deleteAppUserReq.setUuid((SimpleRequest.getUUIDV105(itemType.appuser, this.getName())).getUuid());//We add the parameters to the request
		com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().removeAppUser(deleteAppUserReq);//We send the request to the CUCM
		}
	/**************/

	/***************
	 * Injection
	 */
	public String doInjectVersion105() throws Exception
		{
		com.cisco.axl.api._10.AddAppUserReq req = new com.cisco.axl.api._10.AddAppUserReq();
		com.cisco.axl.api._10.XAppUser params = new com.cisco.axl.api._10.XAppUser();
		
		/**
		 * We set the item parameters
		 */
		params.setUserid(this.getName());//Name
		params.setPassword(this.password);
		
		//User groups
		if(userControlGroupList.size() > 0)
			{
			com.cisco.axl.api._10.XAppUser.AssociatedGroups myGroups = new com.cisco.axl.api._10.XAppUser.AssociatedGroups();
			
			for(String s : userControlGroupList)
				{
				com.cisco.axl.api._10.XAppUser.AssociatedGroups.UserGroup myGroup = new com.cisco.axl.api._10.XAppUser.AssociatedGroups.UserGroup();
				myGroup.setName(s);
				myGroups.getUserGroup().add(myGroup);
				}
			
			params.setAssociatedGroups(myGroups);
			}
		
		//Device
		if(deviceList.size() > 0)
			{
			com.cisco.axl.api._10.XAppUser.AssociatedDevices myDevices = new com.cisco.axl.api._10.XAppUser.AssociatedDevices();
			
			for(String s : deviceList)
				{
				myDevices.getDevice().add(s);
				}
			
			params.setAssociatedDevices(myDevices);
			}
		
		//ctiUDP
		if(ctiUDPList.size() > 0)
			{
			com.cisco.axl.api._10.XAppUser.CtiControlledDeviceProfiles myCtiUDPs = new com.cisco.axl.api._10.XAppUser.CtiControlledDeviceProfiles();
			
			for(String udp : ctiUDPList)
				{
				myCtiUDPs.getDeviceProfile().add(udp);
				}
			
			params.setCtiControlledDeviceProfiles(myCtiUDPs);
			}
		/************/
		
		req.setAppUser(params);//We add the parameters to the request
		com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().addAppUser(req);//We send the request to the CUCM
		
		return resp.getReturn();//Return UUID
		}
	/**************/
	
	/***************
	 * Update
	 */
	public void doUpdateVersion105(ArrayList<ToUpdate> tuList) throws Exception
		{
		/**
		 * We add the phone and udps to the existing ones
		 * So first we fetch them
		 */
		doGetVersion105();//will add the new devices and udps to the existing ones
		
		com.cisco.axl.api._10.UpdateAppUserReq req = new com.cisco.axl.api._10.UpdateAppUserReq();
		
		/***********
		 * We set the item parameters
		 */
		req.setUserid(this.getName());
		
		if(tuList.contains(toUpdate.password))req.setPassword(this.password);
		
		if(tuList.contains(toUpdate.userControlGroup))
			{
			if(userControlGroupList.size() > 0)
				{
				com.cisco.axl.api._10.UpdateAppUserReq.AssociatedGroups myGroups = new com.cisco.axl.api._10.UpdateAppUserReq.AssociatedGroups();
				
				for(String s : userControlGroupList)
					{
					com.cisco.axl.api._10.UpdateAppUserReq.AssociatedGroups.UserGroup myG = new com.cisco.axl.api._10.UpdateAppUserReq.AssociatedGroups.UserGroup();
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
				com.cisco.axl.api._10.UpdateAppUserReq.AssociatedDevices myDevices = new com.cisco.axl.api._10.UpdateAppUserReq.AssociatedDevices();
				
				for(String s : deviceList)
					{
					myDevices.getDevice().add(s);
					}
				
				req.setAssociatedDevices(myDevices);
				}
			}
		
		if(tuList.contains(toUpdate.ctiudps))
			{
			if(ctiUDPList.size() > 0)
				{
				com.cisco.axl.api._10.UpdateAppUserReq.CtiControlledDeviceProfiles myCtiUDPs = new com.cisco.axl.api._10.UpdateAppUserReq.CtiControlledDeviceProfiles();
				
				for(String udp : ctiUDPList)
					{
					myCtiUDPs.getDeviceProfile().add(udp);
					}
				
				req.setCtiControlledDeviceProfiles(myCtiUDPs);
				}
			}
		/************/
		
		com.cisco.axl.api._10.StandardResponse resp = Variables.getAXLConnectionToCUCMV105().updateAppUser(req);//We send the request to the CUCM
		}
	/**************/
	
	
	/*************
	 * Get
	 */
	public ItemToInject doGetVersion105() throws Exception
		{
		com.cisco.axl.api._10.GetAppUserReq req = new com.cisco.axl.api._10.GetAppUserReq();
		
		/******************
		 * We set the item parameters
		 */
		req.setUserid(this.getName());
		/************/
		
		com.cisco.axl.api._10.GetAppUserRes resp = Variables.getAXLConnectionToCUCMV105().getAppUser(req);//We send the request to the CUCM
		
		AppUser myU = new AppUser(this.getName());
		myU.setUUID(resp.getReturn().getAppUser().getUuid());
		
		//Get the devices
		for(String d : resp.getReturn().getAppUser().getAssociatedDevices().getDevice())
			{
			deviceList.add(d);
			}
		myU.setDeviceList(deviceList);
		
		//Get the ctiUDP
		for(String d : resp.getReturn().getAppUser().getCtiControlledDeviceProfiles().getDeviceProfile())
			{
			ctiUDPList.add(d);
			}
		myU.setCtiUDPList(ctiUDPList);
		
		return myU;//Return an AppUser
		}
	/****************/

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
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

