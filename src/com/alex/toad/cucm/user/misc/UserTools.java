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
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
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
						Phone pTemplate = (Phone)getTemplate(ut.getType(), ut.getTarget());
						ArrayList<ItemToInject> phoneList = preparePhone(ad, pTemplate, at);
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
					else if(ut.getType().equals(itemType.agent))
						{
						UCCXAgent uaTemplate = (UCCXAgent)getTemplate(ut.getType(), ut.getTarget());
						ArrayList<ItemToInject> agentList = prepareAgent(ad, uaTemplate, at);
						if((agentList != null) && (agentList.size() > 0))
							{
							itemList.addAll(prepareAgent(ad, uaTemplate, at));
							Variables.getLogger().debug("Agent prepared for : "+ad.getInfo());
							}
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
				sdList,
				template.getMohAudioSourceId(),
				template.getSoftkeyTemplateName());
		
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
				template.getDeviceMobilityMode(),
				template.getMohAudioSourceId(),
				template.getSoftkeyTemplateName());
		
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
				template.getPassword(),
				template.getServiceProfile());
		
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
		
		myAppUser.setAgentData(ad);
		myAppUser.setAction(action);
		
		//We don't put a try/catch here because we want the whole injection to be interrupted in case of exception
		myAppUser.resolve();
		
		return myAppUser;
		}
	
	/***********
	 * Method used to prepare an Agent object
	 * @throws Exception 
	 */
	public static ArrayList<ItemToInject> prepareAgent(AgentData ad, UCCXAgent template, actionType action) throws Exception
		{
		Variables.getLogger().debug("Preparing an Agent");
		ArrayList<ItemToInject> list = new ArrayList<ItemToInject>();
		
		UCCXAgent agent = new UCCXAgent(template.getName(),
				template.getLastname(),
				template.getFirstname(),
				template.getTelephoneNumber(),
				ad.getAgentType(),
				ad.getTeam(),
				ad.getSkillList());
		
		agent.setAgentData(ad);
		agent.setAction(action);
		
		//We don't put a try/catch here because we want the whole injection to be interrupted in case of exception
		agent.resolve();
		
		list.add(agent);
		
		/**
		 * Agent type cannot be updated from the agent so we create a type update request
		 * 
		 * Unfortunately modifying the user role is not available through the API
		 * A workaround is to do it programmatically through the normal web admin portal but it is
		 * really complex
		 * As modifying the role is not asked yet, we skip this step for now
		 */
		//TBW
		
		/**
		 * team cannot be updated from the agent so we create team update items
		 * 
		 * We allow this only if the agent is a supervisor
		 */
		if(agent.getAgentType().equals(AgentType.supervisor))
			{
			/**
			 * We might get a team with no primary supervisor
			 * In this case the followings will failed
			 * So we add the try catch just to handle that properly
			 */
			try
				{
				/**
				 * We get the current configuration of the agent to compare
				 */
				UCCXAgent kagemusha = new UCCXAgent(agent.getName());
				kagemusha.get();
				/*****/
				
				/**
				 * We cannot remove a primary supervisor from a team because
				 * a team must have a primary supervisor
				 * So removing a supervisor just means putting somebody else in place 
				 */
				ArrayList<Team> primTeamToAdd = new ArrayList<Team>();
				
				//To check for primTeam to add
				for(Team te : ad.getPrimarySupervisorOf())
					{
					boolean found = false;
					for(Team t : kagemusha.getPrimarySupervisorOf())
						{
						if(t.getName().equals(te.getName()))
							{
							//The supervisor supervise this team already
							found = true;
							break;
							}
						}
					if(!found)
						{
						//The team was not found so it must be added
						primTeamToAdd.add(te);
						}
					}
				
				for(Team t : primTeamToAdd)
					{
					t.get();
					t.setPrimarySupervisor(agent);
					/***
					 * We then get all the UCCXAgent firstname and lastname (Needed for update)
					 */
					for(UCCXAgent ua : t.getSecondarySupervisorList())ua.get();
					for(UCCXAgent ua : t.getAgentList())ua.get();
					/*********/
					t.setAction(action);
					list.add(t);
					}
				
				/**
				 * About secondary supervisor we need to get the current status of the supervisor
				 * to eventually remove him from a team and add him to a new one
				 */
				ArrayList<Team> teamsToRemove = new ArrayList<Team>();
				ArrayList<Team> teamsToAdd = new ArrayList<Team>();
				
				//To check for team to remove
				for(Team t : kagemusha.getSecondarySupervisorOf())
					{
					boolean found = false;
					for(Team te : ad.getSecondarySupervisorOf())
						{
						if(t.getName().equals(te.getName()))
							{
							//The supervisor still supervise this team
							found = true;
							break;
							}
						}
					if(!found)
						{
						//The team was not found so it must been removed from this team
						teamsToRemove.add(t);
						}
					}
				
				//To check for team to add
				for(Team te : ad.getSecondarySupervisorOf())
					{
					boolean found = false;
					for(Team t : kagemusha.getSecondarySupervisorOf())
						{
						if(t.getName().equals(te.getName()))
							{
							//The supervisor supervise this team already
							found = true;
							break;
							}
						}
					if(!found)
						{
						//The team was not found so it must be added
						teamsToAdd.add(te);
						}
					}
				
				//We update the teams where the supervisor must be removed
				for(Team t : teamsToRemove)
					{
					t.get();//To get the current team data
					
					/**
					 * We remove the supervisor from the team
					 */
					for(UCCXAgent ua : t.getSecondarySupervisorList())
						{
						if(ua.getName().equals(agent.getName()))
							{
							t.getSecondarySupervisorList().remove(ua);
							break;
							}
						}
					/***
					 * We then get all the UCCXAgent firstname and lastname (Needed for update)
					 */
					t.getPrimarySupervisor().get();
					for(UCCXAgent ua : t.getSecondarySupervisorList())ua.get();
					for(UCCXAgent ua : t.getAgentList())ua.get();
					/*********/
					t.setAction(action);
					list.add(t);
					}
				
				//We update the teams where the supervisor must be added
				for(Team t : teamsToAdd)
					{
					/**
					 * You cannot update the secondarysupervisor without providing the current primary
					 * supervisor. So we fetch it
					 */
					t.get();//To get the primary supervisor ID
					/***
					 * We then get all the UCCXAgent firstname and lastname (Needed for update)
					 */
					t.getPrimarySupervisor().get();
					for(UCCXAgent ua : t.getSecondarySupervisorList())ua.get();
					for(UCCXAgent ua : t.getAgentList())ua.get();
					/*********/
					t.getSecondarySupervisorList().add(agent);//We add the supervisor to the team
					t.setAction(action);
					list.add(t);
					}
				}
			catch (Exception e)
				{
				Variables.getLogger().error("Something went wrong while managing team supervisor. Surely a team with an empty primary supervisor : "+e.getMessage(),e);
				}
			}
		
		return list;
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
		
		//We don't put a try/catch here because we want the whole injection to be interrupted in case of exception
		myLine.resolve();
		
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
		
		//We don't put a try/catch here because we want the whole injection to be interrupted in case of exception
		myLine.resolve();
		
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
		
		//We don't put a try/catch here because we want the whole injection to be interrupted in case of exception
		myService.resolve();
		
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
		
		//We don't put a try/catch here because we want the whole injection to be interrupted in case of exception
		mySD.resolve();
		
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
		catch (Exception e)
			{
			Variables.getLogger().debug(ad.getInfo()+" : The udplogin has not been added because an important value was empty : "+e.getMessage());
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
				else if(type.equals(itemType.agent))
					{
					if(((UCCXAgent) item).getTargetName().equals(targetName))
						{
						return (UCCXAgent) item;
						}
					}
				}
			}
		throw new Exception("ERROR : Template not found for type \""+type.name()+"\" and targetName \""+targetName+"\"");
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

