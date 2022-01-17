package com.alex.toad.webserver;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.alex.toad.misc.Agent;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.misc.Task;
import com.alex.toad.risport.RisportTools;
import com.alex.toad.uccx.items.Skill;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.webserver.ManageWebRequest.webRequestType;



/**
 * Used to build web request
 *
 * @author Alexandre RATEL
 */
public class WebRequestBuilder
	{
	
	/**
	 * To build a failed web reply
	 */
	public static WebRequest buildFailedWebRequest(webRequestType type, String errorMessage)
		{
		StringBuffer content = new StringBuffer();
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<error>"+errorMessage+"</error>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the search request reply
	 */
	public static WebRequest buildSearchReply(String search)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.search;
		
		ArrayList<BasicOffice> ol = new ArrayList<BasicOffice>();
		ArrayList<String> lookForDuplicate = new ArrayList<String>();
		ArrayList<BasicDevice> dl = new ArrayList<BasicDevice>();
		
		try
			{
			/**
			 * First we look for offices
			 */
			Variables.getLogger().debug("We look for office matching : "+search);
			for(BasicOffice o : Variables.getOfficeList())
				{
				if((o.getName().toLowerCase().contains(search.toLowerCase())) ||
						(o.getNewName().toLowerCase().contains(search.toLowerCase())) ||
						(o.getIdcomu().toLowerCase().contains(search.toLowerCase())) ||
						(o.getVoiceIPRange().getSubnet().contains(search)) ||
						(o.getDataIPRange().getSubnet().contains(search)))
					{
					Variables.getLogger().debug("Office found : "+o.getInfo());
					
					//Then we look for device associated to this office
					if(o.getDeviceList().size() == 0)
						{
						for(BasicDevice d : Variables.getDeviceList())
							{
							if(d.getOfficeid().equals(o.getIdcomu()))
								{
								o.getDeviceList().add(d);
								}
							}
						}
					
					ol.add(o);
					}
				}
			
			/**
			 * If no office were found we search for phones using IP
			 */
			if(ol.isEmpty())
				{
				Variables.getLogger().debug("No office found so we look for phone using IP");
				/**
				 * We fetch the phone using the given IP
				 * 
				 * We ask the CUCM only if it looks like an IP
				 * (At least 3 number : ex : 10.0.0.)
				 */
				
				if(Pattern.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}.*", search.toLowerCase()))
					{
					ArrayList<BasicPhone> phoneList = RisportTools.getDeviceByIP(search.toLowerCase());
					for(BasicPhone bp : phoneList)
						{
						boolean officeFound = false;
						//We ask the CUCM for the phone's device pool
						String devicePoolName = OfficeTools.getDevicePoolFromPhoneName(bp.getName());
						if(devicePoolName != null)
							{
							Variables.getLogger().debug("Looking for the office corresponding to devicePool "+devicePoolName);
							
							String officeID = devicePoolName.replaceAll(UsefulMethod.getTargetOption("devicepoolprefix"), "");//We strip the device pool prefix to get the office ID
							
							//We first check if the office is not already in the list
							for(BasicOffice bo : ol)
								{
								if(bo.getIdcomu().equals(officeID))
									{
									officeFound = true;
									break;
									}
								}
							
							//Then we look for the corresponding office
							if(!officeFound)
								{
								for(BasicOffice o : Variables.getOfficeList())
									{
									if(o.getIdcomu().equals(officeID))
										{
										Variables.getLogger().debug("Office found : "+o.getInfo());
										//Then we look for device associated to this office
										if(o.getDeviceList().size() == 0)
											{
											for(BasicDevice d : Variables.getDeviceList())
												{
												if(d.getOfficeid().equals(o.getIdcomu()))
													{
													o.getDeviceList().add(d);
													}
												}
											}
										ol.add(o);
										officeFound = true;
										break;
										}
									}
								if(!officeFound)
									{
									Variables.getLogger().debug("the office was not found in the database so we create a simple office just to allow to reset the phones");
									BasicOffice unknownOffice = new BasicOffice(officeID);
									ol.add(unknownOffice);

									/**
									 * In addition we add the unknown office to the office list. this way if the user choose to use it, it will exist.
									 * We add it only in memory, not in the database file. So it is only for temporary usage
									 */
									Variables.getOfficeList().add(unknownOffice);
									}
								}
							}
						else
							{
							Variables.getLogger().debug("Phone "+bp.getName()+" returned a null device pool");
							}
						}
					}
				else
					{
					Variables.getLogger().debug("The search word was not an IP address so we do not ask the CUCM to look for it : "+search );
					}
				}
			else
				{
				Variables.getLogger().debug("At least one office were found so we do not look for phones using IP");
				}
			
			for(BasicOffice o : ol)
				{
				for(BasicDevice d : o.getDeviceList())
					{
					lookForDuplicate.add(d.getId());
					}
				}
			
			/**
			 * Then we look for devices
			 */
			Variables.getLogger().debug("Then we look for unique device matching : "+search);
			for(BasicDevice d : Variables.getDeviceList())
				{
				if(((d.getName().toLowerCase().contains(search.toLowerCase())) ||
						d.getIp().contains(search)) &&
						(!lookForDuplicate.contains(d.getId())))
					{
					for(BasicOffice o : Variables.getOfficeList())
						{
						if(d.getOfficeid().equals(o.getIdcomu()))
							{
							d.setOfficename(o.getName());
							break;
							}
						}
					dl.add(d);
					Variables.getLogger().debug("Device found : "+d.getInfo());
					}
				}
			if(dl.isEmpty())Variables.getLogger().debug("No device found");
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while building the search reply for '"+search+"' : "+e.getMessage());
			}
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<offices>\r\n");
		
		//offices
		if(ol.size() != 0)
			{
			for(BasicOffice o : ol)
				{
				content.append("				<office>\r\n");
				content.append("					<id>"+o.getId()+"</id>\r\n");
				content.append("					<idcomu>"+o.getIdcomu()+"</idcomu>\r\n");
				content.append("					<name>"+o.getName()+"</name>\r\n");
				content.append("					<newname>"+o.getNewName()+"</newname>\r\n");
				content.append("					<status>"+o.getStatus().name()+"</status>\r\n");
				content.append("					<devices>\r\n");
				
				if(o.getDeviceList().size()!=0)
					{
					for(BasicDevice d : o.getDeviceList())
						{
						content.append("						<device>\r\n");
						content.append("							<id>"+d.getId()+"</id>\r\n");
						content.append("							<name>"+d.getName()+"</name>\r\n");
						content.append("							<type>"+d.getType()+"</type>\r\n");
						content.append("							<ip>"+d.getIp()+"</ip>\r\n");
						content.append("							<status>"+d.getStatus().name()+"</status>\r\n");
						content.append("						</device>\r\n");
						}
					}
				content.append("					</devices>\r\n");
				content.append("				</office>\r\n");
				}
			}
		content.append("			</offices>\r\n");
		
		//Devices
		content.append("			<devices>\r\n");
		
		if(dl.size() != 0)
			{
			for(BasicDevice d : dl)
				{
				content.append("				<device>\r\n");
				content.append("					<id>"+d.getId()+"</id>\r\n");
				content.append("					<name>"+d.getName()+"</name>\r\n");
				content.append("					<type>"+d.getType()+"</type>\r\n");
				content.append("					<ip>"+d.getIp()+"</ip>\r\n");
				content.append("					<officeid>"+d.getOfficeid()+"</officeid>\r\n");
				content.append("					<officename>"+d.getOfficename()+"</officename>\r\n");
				content.append("					<status>"+d.getStatus().name()+"</status>\r\n");
				content.append("				</device>\r\n");
				}
			}
		else
			{
			//content.append("				<device></device>\r\n");
			}
		content.append("			</devices>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the requested request
	 * getOfficeList
	 */
	public static WebRequest buildGetAgentReply(Agent agent)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getAgent;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<agent>\r\n");
		
		try
			{
			content.append("				<userid>"+agent.getUser().getName()+"</userid>\r\n");
			content.append("				<firstname>"+agent.getUser().getFirstname()+"</firstname>\r\n");
			content.append("				<lastname>"+agent.getUser().getLastname()+"</lastname>\r\n");
			content.append("				<number>"+agent.getUser().getTelephoneNumber()+"</number>\r\n");
			content.append("				<type>"+agent.getAgent().getAgentType().name()+"</type>\r\n");
			content.append("				<teams>\r\n");
			for(Team t : agent.getAgent().getTeams())
				{
				content.append("					<team>"+t.getName()+"</team>\r\n");
				}
			content.append("				</teams>\r\n");
			content.append("				<skills>\r\n");
			for(Skill s : agent.getAgent().getSkills())
				{
				content.append("					<skill>"+s.getName()+"</skill>\r\n");
				}
			content.append("				</skills>\r\n");
			content.append("				<devices>\r\n");
			for(ItemToInject iti : agent.getDeviceList())
				{
				content.append("					<device>\r\n");
				content.append("						<type>"+iti.getType().name()+"</type>\r\n");
				content.append("						<type>"+iti.getName()+"</type>\r\n");
				content.append("					</device>\r\n");
				}
			content.append("				</devices>\r\n");
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving an agent : "+e.getMessage());
			}
		
		content.append("			</agent>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the requested request
	 * getDeviceList
	 */
	public static WebRequest buildGetTeamReply(Team team)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getTeam;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<team>\r\n");
		
		try
			{
			content.append("				<name>"+team.getName()+"</name>\r\n");
			content.append("				<primarysupervisor>"+team.getName()+"</primarysupervisor>\r\n");
			content.append("				<supervisors>\r\n");
			for(UCCXAgent ua : team.getSupervisorList())
				{
				content.append("				<supervisor>\r\n");
				content.append("					<userid>"+ua.getUser().getName()+"</userid>\r\n");
				content.append("					<firstname>"+ua.getUser().getFirstname()+"</firstname>\r\n");
				content.append("					<lastname>"+ua.getUser().getLastname()+"</lastname>\r\n");
				content.append("				</supervisor>\r\n");
				}
			content.append("				</supervisors>\r\n");
			content.append("				<agents>\r\n");
			for(UCCXAgent a : team.getAgentList())
				{
				content.append("				<agent>\r\n");
				content.append("					<userid>"+a.getUser().getName()+"</userid>\r\n");
				content.append("					<firstname>"+a.getUser().getFirstname()+"</firstname>\r\n");
				content.append("					<lastname>"+a.getUser().getLastname()+"</lastname>\r\n");
				content.append("				</agent>\r\n");
				}
			content.append("				</agents>\r\n");
			
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving a team : "+e.getMessage());
			}
		
		content.append("			</team>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the requested request
	 * getTaskList
	 */
	public static WebRequest buildGetTaskListReply()
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getTaskList;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		
		try
			{
			if(Variables.getTaskList().size()==0)throw new Exception("The tasklist is empty");
			
			for(Task t : Variables.getTaskList())
				{
				StringBuffer temp = new StringBuffer();
				temp.append("				<task>\r\n");
				temp.append(getTask("				", t));
				temp.append("				</task>\r\n");
				content.append(temp);
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the task status : "+e.getMessage());
			content.append("				<task></task>\r\n");
			}
		
		content.append("			</tasks>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the requested request
	 * getOffice
	 */
	public static WebRequest buildGetOfficeReply(String officeID)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getOffice;
		boolean found = false;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		
		try
			{
			for(BasicOffice o : Variables.getOfficeList())
				{
				if(o.getId().equals(officeID))
					{
					StringBuffer temp = new StringBuffer();
					temp.append("			<office>\r\n");
					temp.append(getOffice("			", o));
					temp.append("				<devices>\r\n");
					
					//Then we look for device associated to this office
					if(o.getDeviceList().size() == 0)
						{
						for(BasicDevice d : Variables.getDeviceList())
							{
							if(d.getOfficeid().equals(o.getIdcomu()))
								{
								o.getDeviceList().add(d);
								}
							}
						}
					
					if(o.getDeviceList().size()!=0)
						{
						for(BasicDevice d : o.getDeviceList())
							{
							temp.append("					<device>\r\n");
							temp.append("						<id>"+d.getId()+"</id>\r\n");
							temp.append("						<name>"+d.getName()+"</name>\r\n");
							temp.append("						<type>"+d.getType()+"</type>\r\n");
							temp.append("						<ip>"+d.getIp()+"</ip>\r\n");
							temp.append("						<status>"+d.getStatus().name()+"</status>\r\n");
							temp.append("					</device>\r\n");
							}
						}
					else
						{
						//content.append("						<device></device>\r\n");
						}
					
					temp.append("				</devices>\r\n");
					temp.append("			</office>\r\n");
					found = true;
					content.append(temp);
					break;
					}
				}
			if(!found)throw new Exception("The following office was not found : "+officeID);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the office list : "+e.getMessage());
			content.append("			<office></office>\r\n");
			}
		
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the requested request
	 * getDevice
	 */
	public static WebRequest buildGetDeviceReply(String deviceID)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getDevice;
		boolean found = false;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		
		try
			{
			for(BasicDevice d : Variables.getDeviceList())
				{
				if(d.getId().equals(deviceID))
					{
					StringBuffer temp = new StringBuffer();
					temp.append("			<device>\r\n");
					temp.append(getDevice("			", d));
					temp.append("			</device>\r\n");
					found = true;
					content.append(temp);
					break;
					}
				}
			
			if(!found)throw new Exception("The following office was not found : "+deviceID);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the device list : "+e.getMessage());
			content.append("			<device></device>\r\n");
			}
		
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the requested request
	 * getTask
	 */
	public static WebRequest buildAddAgentReply(String taskID)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getTask;
		boolean found = false;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		
		try
			{
			if(taskID.equals(""))
				{
				//We return the current task
				if(Variables.getTaskList().size() != 0)
					{
					content.append("			<task>\r\n");
					content.append(getTask("			", Variables.getTaskList().get(0)));
					content.append("			</task>\r\n");
					}
				else
					{
					throw new Exception("No task in progress to return");
					}
				}
			else
				{
				for(Task t : Variables.getTaskList())
					{
					if(t.getTaskId().equals(taskID))
						{
						StringBuffer temp = new StringBuffer();
						temp.append("			<task>\r\n");
						temp.append(getTask("			", t));
						temp.append("			</task>\r\n");
						found = true;
						content.append(temp);
						break;
						}
					}
				if(!found)throw new Exception("The following task was not found : "+taskID);
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the task status : "+e.getMessage());
			content.append("			<task></task>\r\n");
			}
		
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the requested request
	 * newTask
	 */
	public static WebRequest buildNewTaskReply(String taskID)
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.newTask;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<taskid>"+taskID+"</taskid>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To create one office
	 */
	public static String getOffice(String tabs, BasicOffice o)
		{
		StringBuffer content = new StringBuffer();
		
		content.append(tabs+"	<id>"+o.getId()+"</id>\r\n");
		content.append(tabs+"	<idcomu>"+o.getIdcomu()+"</idcomu>\r\n");
		content.append(tabs+"	<idcaf>"+o.getIdCAF()+"</idcaf>\r\n");
		content.append(tabs+"	<name>"+o.getName()+"</name>\r\n");
		content.append(tabs+"	<shortname>"+o.getShortname()+"</shortname>\r\n");
		content.append(tabs+"	<newname>"+o.getNewName()+"</newname>\r\n");
		content.append(tabs+"	<officetype>"+o.getOfficeType()+"</officetype>\r\n");
		content.append(tabs+"	<voiceiprange>"+o.getVoiceIPRange().getCIDRFormat()+"</voiceiprange>\r\n");
		content.append(tabs+"	<dataiprange>"+o.getDataIPRange().getCIDRFormat()+"</dataiprange>\r\n");
		content.append(tabs+"	<newvoiceiprange>"+o.getNewVoiceIPRange().getCIDRFormat()+"</newvoiceiprange>\r\n");
		content.append(tabs+"	<newdataiprange>"+o.getNewDataIPRange().getCIDRFormat()+"</newdataiprange>\r\n");
		
		return content.toString();
		}
	
	
	/**
	 * To create one device
	 */
	public static String getDevice(String tabs, BasicDevice d)
		{
		StringBuffer content = new StringBuffer();
		
		content.append(tabs+"	<id>"+d.getId()+"</id>\r\n");
		content.append(tabs+"	<name>"+d.getName()+"</name>\r\n");
		content.append(tabs+"	<type>"+d.getType()+"</type>\r\n");
		content.append(tabs+"	<ip>"+d.getIp()+"</ip>\r\n");
		content.append(tabs+"	<mask>"+d.getMask()+"</mask>\r\n");
		content.append(tabs+"	<gateway>"+d.getGateway()+"</gateway>\r\n");
		content.append(tabs+"	<newip>"+d.getNewip()+"</newip>\r\n");
		content.append(tabs+"	<newmask>"+d.getNewmask()+"</newmask>\r\n");
		content.append(tabs+"	<newgateway>"+d.getNewgateway()+"</newgateway>\r\n");
		content.append(tabs+"	<officeid>"+d.getOfficeid()+"</officeid>\r\n");
		
		return content.toString();
		}
	
	/**
	 * To create one task
	 */
	public static String getTask(String tabs, Task t)
		{
		StringBuffer content = new StringBuffer();
		
		content.append(tabs+"	<id>"+t.getTaskId()+"</id>\r\n");
		content.append(tabs+"	<overallstatus>"+t.getStatus()+"</overallstatus>\r\n");
		content.append(tabs+"	<itemlist>\r\n");
		
		for(ItemToMigrate itm : t.getTodoList())
			{
			content.append(tabs+"	<item>\r\n");
			content.append(tabs+"		<id>"+itm.getId()+"</id>\r\n");
			content.append(tabs+"		<type>"+itm.getType()+"</type>\r\n");
			content.append(tabs+"		<info>"+itm.getInfo()+"</info>\r\n");
			content.append(tabs+"		<status>"+itm.getStatus().name()+"</status>\r\n");
			content.append(tabs+"		<desc>"+itm.getDetailedStatus()+"</desc>\r\n");
			content.append(tabs+"	</item>\r\n");
			}
		
		content.append(tabs+"	</itemlist>\r\n");
		
		return content.toString();
		}
	
	/**
	 * To build the requested request
	 * success
	 */
	/*
	public static WebRequest buildSuccess()
		{
		StringBuffer content = new StringBuffer();
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>success</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<success></success>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), webRequestType.success);
		}
	*/
	/**
	 * To build the requested request
	 * error
	 */
	/*
	public static WebRequest buildError(String message)
		{
		StringBuffer content = new StringBuffer();
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>error</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<error>"+message+"</error>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), webRequestType.error);
		}
		*/
	
	/*2022*//*RATEL Alexandre 8)*/
	}
