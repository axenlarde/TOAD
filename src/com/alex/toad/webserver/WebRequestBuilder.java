package com.alex.toad.webserver;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.alex.perceler.action.Task;
import com.alex.perceler.device.misc.BasicDevice;
import com.alex.perceler.device.misc.BasicPhone;
import com.alex.perceler.misc.ItemToMigrate;
import com.alex.perceler.misc.SimpleItem;
import com.alex.perceler.office.misc.BasicOffice;
import com.alex.perceler.office.misc.IPRange;
import com.alex.perceler.office.misc.OfficeTools;
import com.alex.perceler.risport.RisportTools;
import com.alex.perceler.utils.UsefulMethod;
import com.alex.perceler.utils.Variables;
import com.alex.perceler.webserver.ManageWebRequest.webRequestType;

/**
 * Used to build web request
 *
 * @author Alexandre RATEL
 */
public class WebRequestBuilder
	{
	
	public static WebRequest buildWebRequest(webRequestType type, Object obj)
		{
		switch(type)
			{
			case search:return buildSearchReply((String)obj);
			case getOfficeList:return buildGetOfficeListReply();
			case getDeviceList:return buildGetDeviceListReply();
			case getTaskList:return buildGetTaskListReply();
			case getOffice:return buildGetOfficeReply((String)obj);
			case getDevice:return buildGetDeviceReply((String)obj);
			case getTask:return buildGetTaskReply((String)obj);
			case newTask:return buildNewTaskReply((String)obj);
			case success:return buildSuccess();
			case error:return buildError((String)obj);
			default:return null;
			}
		}
	
	/**
	 * To build the requested request
	 * getOfficeList
	 */
	private static WebRequest buildSearchReply(String search)
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
	private static WebRequest buildGetOfficeListReply()
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getOfficeList;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<offices>\r\n");
		
		try
			{
			for(BasicOffice o : Variables.getOfficeList())
				{
				StringBuffer temp = new StringBuffer();
				temp.append("				<office>\r\n");
				temp.append(getOffice("				", o));
				temp.append("					<devices>\r\n");
				
				if(o.getDeviceList().size()!=0)
					{
					for(BasicDevice d : o.getDeviceList())
						{
						StringBuffer temp2 = new StringBuffer();
						temp2.append("						<device>\r\n");
						temp2.append("							<id>"+d.getId()+"</id>\r\n");
						temp2.append("							<name>"+d.getName()+"</name>\r\n");
						temp2.append("							<type>"+d.getType()+"</type>\r\n");
						temp2.append("							<ip>"+d.getIp()+"</ip>\r\n");
						temp2.append("							<status>"+d.getStatus().name()+"</status>\r\n");
						temp2.append("						</device>\r\n");
						temp.append(temp2);
						}
					}
				else
					{
					//content.append("						<device></device>\r\n");
					}
				
				content.append("					</devices>\r\n");
				content.append("				</office>\r\n");
				content.append(temp);
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the office list : "+e.getMessage());
			content.append("				<office></office>\r\n");
			}
		
		content.append("			</offices>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the requested request
	 * getDeviceList
	 */
	private static WebRequest buildGetDeviceListReply()
		{
		StringBuffer content = new StringBuffer();
		webRequestType type = webRequestType.getDeviceList;
		
		content.append("<xml>\r\n");
		content.append("	<reply>\r\n");
		content.append("		<type>"+type.name()+"</type>\r\n");
		content.append("		<content>\r\n");
		content.append("			<devices>\r\n");
		
		try
			{
			for(BasicDevice d : Variables.getDeviceList())
				{
				StringBuffer temp = new StringBuffer();
				temp.append("				<device>\r\n");
				temp.append(getDevice("				", d));
				temp.append("				</device>\r\n");
				content.append(temp);
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving the device list : "+e.getMessage());
			content.append("				<device></device>\r\n");
			}
		
		content.append("			</devices>\r\n");
		content.append("		</content>\r\n");
		content.append("	</reply>\r\n");
		content.append("</xml>\r\n");
		
		return new WebRequest(content.toString(), type);
		}
	
	/**
	 * To build the requested request
	 * getTaskList
	 */
	private static WebRequest buildGetTaskListReply()
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
	private static WebRequest buildGetOfficeReply(String officeID)
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
	private static WebRequest buildGetDeviceReply(String deviceID)
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
	private static WebRequest buildGetTaskReply(String taskID)
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
	private static WebRequest buildNewTaskReply(String taskID)
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
	private static String getOffice(String tabs, BasicOffice o)
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
	private static String getDevice(String tabs, BasicDevice d)
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
	private static String getTask(String tabs, Task t)
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
	private static WebRequest buildSuccess()
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
	
	/**
	 * To build the requested request
	 * error
	 */
	private static WebRequest buildError(String message)
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
	
	/*2020*//*RATEL Alexandre 8)*/
	}
