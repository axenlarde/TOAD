package com.alex.toad.cucm.user.misc;

import java.util.ArrayList;

import com.alex.toad.cucm.user.items.AppUser;
import com.alex.toad.cucm.user.items.DeviceProfile;
import com.alex.toad.cucm.user.items.Line;
import com.alex.toad.cucm.user.items.Phone;
import com.alex.toad.cucm.user.items.UdpLogin;
import com.alex.toad.cucm.user.items.User;
import com.alex.toad.misc.EmptyValueException;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.soap.items.PhoneLine;
import com.alex.toad.soap.items.PhoneService;
import com.alex.toad.soap.items.SpeedDial;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.actionType;
import com.alex.toad.utils.Variables.itemType;
import com.alex.toad.webserver.AgentData;


/**********************************
 * Class used to store static method
 * used for the site injection
 * 
 * @author RATEL Alexandre
 **********************************/
public class UserTools
	{
	/**
	 * Method used to build the user injection list
	 * 
	 * !!!! Very important and complex algorithm !!!!
	 * !!!! Modify with care !!!!!
	 */
	public static ArrayList<ItemToInject> getUserItemList(AgentData ad, actionType action, UserCreationProfile ucp, boolean udpLogin) throws Exception
		{
		ArrayList<ItemToInject> itemList = new ArrayList<ItemToInject>();
		
		Variables.getLogger().debug(ad.getInfo()+" : Starting preparation process");
		
		try
			{
			/**
			 * According to the user creation profile we add to the itemList
			 * the item asked by the profile
			 */
			for(UserTemplate ut : ucp.getTemplateList())
				{
				/*****
				 * In case of deletion, we transform inject and update task into delete task
				 */
				actionType at = (action.equals(actionType.delete))?action:ut.getAction();
				
				if((at.equals(actionType.delete)) && (ut.getAction().equals(actionType.update)))
					{
					Variables.getLogger().debug("We do not add this to the user because it is an update item and this is a deletion task : "+ut.getInfo());
					}
				else
					{
					if(ut.getType().equals(itemType.udp))
						{
						DeviceProfile dpTemplate = (DeviceProfile)getTemplate(ut.getType(), ut.getTarget());
						ArrayList<ItemToInject> udpList = prepareUDP(ad, dpTemplate, at);
						if((udpList != null) && (udpList.size() > 0))
							{
							itemList.addAll(udpList);
							Variables.getLogger().debug("UDP prepared for the user : "+ad.getInfo());
							}
						}
					else if((ut.getType().equals(itemType.udplogin)) && udpLogin)
						{
						UdpLogin udpLoginTemplate = (UdpLogin)getTemplate(ut.getType(), ut.getTarget());
						UdpLogin ul = prepareUdpLogin(ad, udpLoginTemplate, at);
						
						if(ul != null)
							{
							itemList.add(ul);
							Variables.getLogger().debug("UDPLogin prepared for the user : "+ad.getInfo());
							}
						}
					else if(ut.getType().equals(itemType.phone))
						{
						ArrayList<ItemToInject> phoneList = preparePhone(ad, (Phone)getTemplate(ut.getType(), ut.getTarget()), at);
						if((phoneList != null) && (phoneList.size() > 0))
							{
							itemList.addAll(phoneList);
							Variables.getLogger().debug("Phone prepared for the user : "+ad.getInfo());
							}
						}
					else if(ut.getType().equals(itemType.user))
						{
						User uTemplate = (User)getTemplate(ut.getType(), ut.getTarget());
						itemList.add(prepareUser(ad, uTemplate, at));
						
						Variables.getLogger().debug("User prepared for : "+ad.getInfo());
						}
					else if(ut.getType().equals(itemType.appuser))
						{
						AppUser uTemplate = (AppUser)getTemplate(ut.getType(), ut.getTarget());
						itemList.add(prepareAppUser(ad, uTemplate, at));
						
						Variables.getLogger().debug("AppUser prepared for : "+ad.getInfo());
						}
					}
				}
			}
		catch (EmptyValueException eve)
			{
			Variables.getLogger().debug(ad.getInfo()+" : an empty value exception has been raised : "+eve.getMessage());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			throw new Exception(ad.getInfo()+" : Error while filling the user list : "+e.getMessage());
			}
			
		Variables.getLogger().debug("User preparation process ends");
		
		Variables.getLogger().debug("Item list size : "+itemList.size());
		return itemList;
		}
	
	/**
	 * Method used to create a UDP profile 
	 * @throws Exception 
	 */
	public static ArrayList<ItemToInject> prepareUDP(AgentData ad, DeviceProfile template, actionType action) throws Exception
		{
		Variables.getLogger().debug("Preparing a UDP for user "+ad.getInfo());
		
		ArrayList<ItemToInject> list = new ArrayList<ItemToInject>();
		
		/**
		 * First we create the lines (One or more)
		 */
		ArrayList<PhoneLine> lineList = new ArrayList<PhoneLine>();
		
		for(int y=0; y<template.getLineList().size(); y++)
			{
			Line line = prepareLine(ad, template.getLineList().get(y), action);//The line to inject
			if(line != null)
				{
				list.add(line);
				lineList.add(preparePhoneLine(ad, template.getLineList().get(y), line.getName()));//The line used to be linked with the device
				}
			}
		
		/**
		 * Second we manage the services
		 */
		ArrayList<PhoneService> serviceList = new ArrayList<PhoneService>();
		
		for(PhoneService s : template.getServiceList())
			{
			PhoneService myService = prepareService(ad, s);
			if(myService != null)serviceList.add(myService);
			}
		
		/**
		 * Then we manage the Speed Dials
		 */
		ArrayList<SpeedDial> sdList = new ArrayList<SpeedDial>();
		
		for(SpeedDial sd : template.getSdList())
			{
			SpeedDial mySD = prepareSD(ad, sd);
			if(mySD != null)sdList.add(mySD);
			}
		
		
		/***************
		 * Finally we have to create the Device Profile
		 */
		DeviceProfile myUDP = new DeviceProfile(template.getTargetName(),
				template.getName(),
				template.getDescription(),
				template.getProductType(),
				template.getProtocol(),
				template.getPhoneButtonTemplate(),
				serviceList,
				lineList,
				sdList);
		
		//We don't put a try/catch here because we want the whole injection to be interrupted in case of exception
		myUDP.setAgentData(ad);
		myUDP.setAction(action);
		myUDP.resolve();
		list.add(myUDP);
		
		return list;
		}
	
	/**
	 * Method used to create a phone
	 * 
	 * If something goes wrong during the resolve process we want the preparation process
	 * to abort. For instance, this way we avoid to inject a line without the associated phone
	 * 
	 * @throws Exception 
	 */
	public static ArrayList<ItemToInject> preparePhone(AgentData ad, Phone template, actionType action) throws Exception
		{
		Variables.getLogger().debug("Preparing a Phone");
		
		ArrayList<ItemToInject> list = new ArrayList<ItemToInject>();
		
		/**
		 * First we create the lines (One or more)
		 */
		ArrayList<PhoneLine> lineList = new ArrayList<PhoneLine>();
		
		for(int y=0; y<template.getLineList().size(); y++)
			{
			Line line = prepareLine(ad, template.getLineList().get(y), action);//The line to inject
			if(line != null)
				{
				list.add(line);
				lineList.add(preparePhoneLine(ad, template.getLineList().get(y), line.getName()));//The line used to be linked with the device
				}
			}
		
		/**
		 * Second we manage the services
		 */
		ArrayList<PhoneService> serviceList = new ArrayList<PhoneService>();
		
		for(PhoneService s : template.getServiceList())
			{
			PhoneService myService = prepareService(ad, s);
			if(myService != null)serviceList.add(myService);
			}
		
		/**
		 * Then we manage the Speed Dials
		 */
		ArrayList<SpeedDial> sdList = new ArrayList<SpeedDial>();
		
		for(SpeedDial sd : template.getSdList())
			{
			SpeedDial mySD = prepareSD(ad, sd);
			if(mySD != null)sdList.add(mySD);
			}
		
		/***************
		 * Last we have to create the Phone
		 */
		Phone myPhone = new Phone(template.getTargetName(),
				template.getName(),
				template.getDescription(),
				template.getProductType(),
				template.getProtocol(),
				template.getPhoneButtonTemplate(),
				template.getPhoneCss(),
				template.getDevicePool(),
				template.getLocation(),
				template.getCommonDeviceConfigName(),
				template.getAarNeighborhoodName(),
				template.getAutomatedAlternateRoutingCssName(),
				template.getSubscribeCallingSearchSpaceName(),
				template.getRerouteCallingSearchSpaceName(),
				serviceList,
				lineList,
				template.getEnableExtensionMobility(),
				sdList,
				template.getCommonPhoneConfigName(),
				template.getSecurityProfileName(),
				template.getDeviceMobilityMode());
		
		myPhone.setAgentData(ad);
		myPhone.setAction(action);//It is important to set the action before resolving
		
		try
			{
			myPhone.resolve();
			}
		catch (Exception e)
			{
			Variables.getLogger().debug(ad.getInfo()+" : The phone has not been added because an important value was empty : "+e.getMessage());
			return null;
			}
		
		list.add(myPhone);
		
		return list;
		}
	
	/***********
	 * Method used to prepare a User object
	 * @throws Exception 
	 */
	public static User prepareUser(AgentData ad, User template, actionType action) throws Exception
		{
		Variables.getLogger().debug("Preparing a User");
		
		User myUser = new User(template.getTargetName(),
				template.getName(),
				template.getDeviceList(),
				template.getUDPList(),
				template.getCtiUDPList(),
				template.getUserControlGroupList(),
				template.getLastname(),
				template.getFirstname(),
				template.getTelephoneNumber(),
				template.getUserLocale(),
				template.getSubscribeCallingSearchSpaceName(),
				template.getPrimaryExtension(),
				template.getIpccExtension(),
				template.getRoutePartition(),
				template.getPin(),
				template.getPassword());
		
		//We don't put a try/catch here because we want the whole injection to be interrupted in case of exception
		myUser.setAgentData(ad);
		myUser.setAction(action);
		myUser.resolve();
		
		return myUser;
		}
	
	/***********
	 * Method used to prepare an AppUser object
	 * @throws Exception 
	 */
	public static AppUser prepareAppUser(AgentData ad, AppUser template, actionType action) throws Exception
		{
		Variables.getLogger().debug("Preparing an AppUser");
		
		AppUser myAppUser = new AppUser(template.getName(),
				template.getTargetName(),
				template.getPassword(),
				template.getUserControlGroupList(),
				template.getDeviceList(),
				template.getCtiUDPList());
		
		//We don't put a try/catch here because we want the whole injection to be interrupted in case of exception
		myAppUser.setAgentData(ad);
		myAppUser.setAction(action);
		
		try
			{
			myAppUser.resolve();
			}
		catch (Exception e)
			{
			Variables.getLogger().debug(ad.getInfo()+" : The AppUser has not been added because an important value was empty : "+e.getMessage());
			return null;
			}
		
		return myAppUser;
		}
	
	/**
	 * Method used to return add a line to an itemToInject list
	 * @throws Exception 
	 */
	public static Line prepareLine(AgentData ad, PhoneLine l, actionType action) throws Exception
		{
		Variables.getLogger().debug("Preparing a Line");
		
		Line myLine = new Line(l.getLineNumber(),
				l.getDescription(),
				l.getRoutePartition(),
				l.getAlertingName(),
				l.getAsciiAlertingName(),
				l.getLineCSS(),
				l.getFwCallingSearchSpaceName(),
				l.getFwAllDestination(),
				l.getFwNoanDestination(),
				l.getFwBusyDestination(),
				l.getFwUnrDestination(),
				l.getVoiceMailProfileName(),
				l.getFwAllVoicemailEnable(),
				l.getFwNoanVoicemailEnable(),
				l.getFwBusyVoicemailEnable(),
				l.getFwUnrVoicemailEnable());
		
		myLine.setAgentData(ad);
		myLine.setAction(action);
		
		try
			{
			myLine.resolve();
			}
		catch (EmptyValueException eve)
			{
			Variables.getLogger().debug(ad.getInfo()+" : The line has not been added because an important value was empty : "+eve.getMessage());
			return null;
			}
		
		return myLine;
		}
	
	public static PhoneLine preparePhoneLine(AgentData ad, PhoneLine l, String lineNumber) throws Exception
		{
		PhoneLine myLine = new PhoneLine(l.getDescription(),
				l.getLineLabel(),
				l.getAsciiLineLabel(),
				l.getLineDisplay(),
				l.getLineDisplayAscii(),
				l.getExternalPhoneNumberMask(),
				lineNumber,
				l.getRoutePartition(),
				l.getLineCSS(),
				l.getAlertingName(),
				l.getAsciiAlertingName(),
				l.getFwCallingSearchSpaceName(),
				l.getFwAllDestination(),
				l.getFwNoanDestination(),
				l.getFwBusyDestination(),
				l.getFwUnrDestination(),
				l.getVoiceMailProfileName(),
				l.getFwAllVoicemailEnable(),
				l.getFwNoanVoicemailEnable(),
				l.getFwBusyVoicemailEnable(),
				l.getFwUnrVoicemailEnable(),
				l.getLineIndex());
		
		myLine.setAgentData(ad);
		
		try
			{
			myLine.resolve();
			}
		catch (EmptyValueException eve)
			{
			Variables.getLogger().debug(ad.getInfo()+" : The line has not been added because an important value was empty : "+eve.getMessage());
			return null;
			}
		
		return myLine;
		}
	
	/**
	 * Used to prepare a service
	 */
	public static PhoneService prepareService(AgentData ad, PhoneService s) throws Exception
		{
		Variables.getLogger().debug("Preparing a Phone Service");
		
		PhoneService myService = new PhoneService(s.getTemplate());
		
		myService.setAgentData(ad);
		
		try
			{
			myService.resolve();
			}
		catch (EmptyValueException eve)
			{
			Variables.getLogger().debug(ad.getInfo()+" : The service has not been added because an important value was empty : "+eve.getMessage());
			return null;
			}
		
		return myService;	
		}
	
	/**
	 * Used to prepare a service
	 */
	public static SpeedDial prepareSD(AgentData ad, SpeedDial sd) throws Exception
		{
		Variables.getLogger().debug("Preparing a Speed Dial");
		
		SpeedDial mySD = new SpeedDial(sd.getTemplate(), sd.getPosition());
		
		mySD.setAgentData(ad);
		
		try
			{
			mySD.resolve();
			}
		catch (EmptyValueException eve)
			{
			Variables.getLogger().debug(ad.getInfo()+" : The speedDial has not been added because an important value was empty : "+eve.getMessage());
			return null;
			}
		
		return mySD;	
		}
	
	/**
	 * Used to prepare a UDPLogin
	 */
	public static UdpLogin prepareUdpLogin(AgentData ad, UdpLogin udpLoginTemplate, actionType action) throws Exception
		{
		Variables.getLogger().debug("Preparing a UDPLogin");
		
		UdpLogin ul = new UdpLogin(udpLoginTemplate.getName(),
				udpLoginTemplate.getDeviceName(),
				udpLoginTemplate.getDeviceProfile());
		
		ul.setAgentData(ad);
		ul.setAction(action);
		
		try
			{
			ul.resolve();
			}
		catch (EmptyValueException eve)
			{
			Variables.getLogger().debug(ad.getInfo()+" : The UDPLogin has not been added because an important value was empty : "+eve.getMessage());
			return null;
			}
		
		return ul;	
		}
	
	/******
	 * Method used to retrieve the template to use from the template list
	 * @throws Exception 
	 */
	public static ItemToInject getTemplate(itemType type, String targetName) throws Exception
		{
		for(ItemToInject item : Variables.getUserTemplateList())
			{
			if(item.getType().equals(type))
				{
				if(type.equals(itemType.udp))
					{
					if(((DeviceProfile) item).getTargetName().equals(targetName))
						{
						return (DeviceProfile) item;
						}
					}
				else if(type.equals(itemType.phone))
					{
					if(((Phone) item).getTargetName().equals(targetName))
						{
						return (Phone) item;
						}
					}
				else if(type.equals(itemType.user))
					{
					if(((User) item).getTargetName().equals(targetName))
						{
						return (User) item;
						}
					}
				else if(type.equals(itemType.udplogin))
					{
					if(((UdpLogin) item).getTargetName().equals(targetName))
						{
						return (UdpLogin) item;
						}
					}
				else if(type.equals(itemType.appuser))
					{
					if(((AppUser) item).getTargetName().equals(targetName))
						{
						return (AppUser) item;
						}
					}
				}
			}
		throw new Exception("ERROR : Template not found for type \""+type.name()+"\" and targetName \""+targetName+"\"");
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

