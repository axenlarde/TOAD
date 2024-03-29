package com.alex.toad.cucm.user.items;

import com.alex.toad.axlitems.linkers.LineLinker;
import com.alex.toad.misc.CollectionTools;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.misc.SimpleRequest;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;
import com.alex.toad.webserver.AgentData;

/**********************************
 * Class used to define an item of type "Line"
 * 
 * @author RATEL Alexandre
 **********************************/

public class Line extends ItemToInject
	{
	/**
	 * Variables
	 */
	private LineLinker myLine;
	private String description,
	routePartitionName,
	usage,
	alertingName,
	asciiAlertingName,
	shareLineAppearanceCssName,
	callPickupGroupName,
	fwCallingSearchSpaceName,
	fwAllDestination,
	fwNoanDestination,
	fwBusyDestination,
	fwUnrDestination,
	voiceMailProfileName,
	fwAllVoicemailEnable,
	fwNoanVoicemailEnable,
	fwBusyVoicemailEnable,
	fwUnrVoicemailEnable;
	
	private AgentData agentData;

	/***************
	 * Constructor
	 * @throws Exception 
	 ***************/
	public Line(String name, String description,
			String routePartitionName, String alertingName,
			String asciiAlertingName, String shareLineAppearanceCssName,
			String fwCallingSearchSpaceName,
			String fwAllDestination, String fwNoanDestination,
			String fwBusyDestination, String fwUnrDestination,
			String voiceMailProfileName, String fwAllVoicemailEnable,
			String fwNoanVoicemailEnable, String fwBusyVoicemailEnable,
			String fwUnrVoicemailEnable) throws Exception
		{
		super(itemType.line, name);
		myLine = new LineLinker(name, routePartitionName);
		this.description = description;
		this.routePartitionName = routePartitionName;
		this.usage = "Device";
		this.alertingName = alertingName;
		this.asciiAlertingName = asciiAlertingName;
		this.shareLineAppearanceCssName = shareLineAppearanceCssName;
		this.fwCallingSearchSpaceName = fwCallingSearchSpaceName;
		this.fwAllDestination = fwAllDestination;
		this.fwNoanDestination = fwNoanDestination;
		this.fwBusyDestination = fwBusyDestination;
		this.fwUnrDestination = fwUnrDestination;
		this.voiceMailProfileName = voiceMailProfileName;
		this.fwAllVoicemailEnable = fwAllVoicemailEnable;
		this.fwNoanVoicemailEnable = fwNoanVoicemailEnable;
		this.fwBusyVoicemailEnable = fwBusyVoicemailEnable;
		this.fwUnrVoicemailEnable = fwUnrVoicemailEnable;
		}

	public Line(String name, String routePartitionName) throws Exception
		{
		super(itemType.line, name);
		this.routePartitionName = routePartitionName;
		myLine = new LineLinker(name,routePartitionName);
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
		myLine.setName(name);//It is the line number
		myLine.setDescription(description);
		myLine.setRoutePartitionName(routePartitionName);
		myLine.setAlertingName(alertingName);
		myLine.setAsciiAlertingName(asciiAlertingName);
		myLine.setShareLineAppearanceCssName(shareLineAppearanceCssName);
		myLine.setUsage(usage);
		myLine.setCallPickupGroupName(callPickupGroupName);
		myLine.setFwCallingSearchSpaceName(fwCallingSearchSpaceName);
		myLine.setFwAllDestination(fwAllDestination);
		myLine.setFwNoanDestination(fwNoanDestination);
		myLine.setFwBusyDestination(fwBusyDestination);
		myLine.setFwUnrDestination(fwUnrDestination);
		myLine.setVoiceMailProfileName(voiceMailProfileName);
		myLine.setFwAllVoicemailEnable(fwAllVoicemailEnable.equals("true")?true:false);
		myLine.setFwNoanVoicemailEnable(fwNoanVoicemailEnable.equals("true")?true:false);
		myLine.setFwBusyVoicemailEnable(fwBusyVoicemailEnable.equals("true")?true:false);
		myLine.setFwUnrVoicemailEnable(fwUnrVoicemailEnable.equals("true")?true:false);
		/*****************/
		
		errorList.addAll(myLine.init());
		}
	
	
	/**
	 * Method used to inject data in the CUCM using
	 * the Cisco API
	 * 
	 * It also return the item's UUID once injected
	 */
	public String doInject() throws Exception
		{	
		return myLine.inject();//Return UUID
		}

	/**
	 * Method used to delete data in the CUCM using
	 * the Cisco API
	 */
	public void doDelete() throws Exception
		{
		myLine.delete();
		}

	/**
	 * Method used to delete data in the CUCM using
	 * the Cisco API
	 */
	public void doUpdate() throws Exception
		{
		myLine.update(tuList);
		}
	
	public void doGet() throws Exception
		{
		Line myL = (Line) myLine.get();
		this.UUID = myL.getUUID();
		//Etc...
		//Has to be written
		
		Variables.getLogger().debug("Item "+this.name+" data fetch from the CUCM");
		}
	
	/**
	 * Method used to check if the element exist in the CUCM
	 */
	public boolean doExist() throws Exception
		{
		UUID = SimpleRequest.getLineUUID(name, routePartitionName);
		
		Variables.getLogger().debug("Item "+this.name+" already exist in the CUCM");
		return true;
		}
	
	public String getInfo()
		{
		return UsefulMethod.convertItemTypeToVerbose(type)+" "+name+" "+UsefulMethod.convertItemTypeToVerbose(itemType.partition)+" "+routePartitionName;
		}
	
	/**
	 * Method used to resolve pattern into real value
	 */
	public void resolve() throws Exception
		{
		name = CollectionTools.applyPattern(agentData, name, this, true);
		description = CollectionTools.applyPattern(agentData, description, this, false);
		routePartitionName = CollectionTools.applyPattern(agentData, routePartitionName, this, true);
		alertingName = CollectionTools.applyPattern(agentData, alertingName, this, false);
		asciiAlertingName = CollectionTools.applyPattern(agentData, asciiAlertingName, this, false);
		shareLineAppearanceCssName = CollectionTools.applyPattern(agentData, shareLineAppearanceCssName, this, false);
		fwCallingSearchSpaceName = CollectionTools.applyPattern(agentData, fwCallingSearchSpaceName, this, false);
		fwAllDestination = CollectionTools.applyPattern(agentData, fwAllDestination, this, false);
		fwNoanDestination = CollectionTools.applyPattern(agentData, fwNoanDestination, this, false);
		fwBusyDestination = CollectionTools.applyPattern(agentData, fwBusyDestination, this, false);
		fwUnrDestination = CollectionTools.applyPattern(agentData, fwUnrDestination, this, false);
		voiceMailProfileName = CollectionTools.applyPattern(agentData, voiceMailProfileName, this, false);
		fwAllVoicemailEnable = CollectionTools.applyPattern(agentData, fwAllVoicemailEnable, this, false);
		fwNoanVoicemailEnable = CollectionTools.applyPattern(agentData, fwNoanVoicemailEnable, this, false);
		fwBusyVoicemailEnable = CollectionTools.applyPattern(agentData, fwBusyVoicemailEnable, this, false);
		fwUnrVoicemailEnable = CollectionTools.applyPattern(agentData, fwUnrVoicemailEnable, this, false);
		callPickupGroupName = CollectionTools.applyPattern(agentData, callPickupGroupName, this, false);
		}
	
	/**
	 * Manage the content of the "To Update List"
	 */
	public void manageTuList() throws Exception
		{
		if(UsefulMethod.isNotEmpty(description))tuList.add(LineLinker.toUpdate.description);
		if(UsefulMethod.isNotEmpty(alertingName))tuList.add(LineLinker.toUpdate.alertingName);
		if(UsefulMethod.isNotEmpty(asciiAlertingName))tuList.add(LineLinker.toUpdate.asciiAlertingName);
		if(UsefulMethod.isNotEmpty(shareLineAppearanceCssName))tuList.add(LineLinker.toUpdate.shareLineAppearanceCssName);
		if(UsefulMethod.isNotEmpty(callPickupGroupName))tuList.add(LineLinker.toUpdate.callPickupGroupName);
		if(UsefulMethod.isNotEmpty(fwCallingSearchSpaceName))tuList.add(LineLinker.toUpdate.fwCallingSearchSpaceName);
		if(UsefulMethod.isNotEmpty(fwAllDestination))tuList.add(LineLinker.toUpdate.fwAllDestination);
		if(UsefulMethod.isNotEmpty(fwNoanDestination))tuList.add(LineLinker.toUpdate.fwNoanDestination);
		if(UsefulMethod.isNotEmpty(fwBusyDestination))tuList.add(LineLinker.toUpdate.fwBusyDestination);
		if(UsefulMethod.isNotEmpty(fwUnrDestination))tuList.add(LineLinker.toUpdate.fwUnrDestination);
		if(UsefulMethod.isNotEmpty(voiceMailProfileName))tuList.add(LineLinker.toUpdate.voiceMailProfileName);
		if(UsefulMethod.isNotEmpty(fwAllVoicemailEnable))tuList.add(LineLinker.toUpdate.fwAllVoicemailEnable);
		if(UsefulMethod.isNotEmpty(fwNoanVoicemailEnable))tuList.add(LineLinker.toUpdate.fwNoanVoicemailEnable);
		if(UsefulMethod.isNotEmpty(fwBusyVoicemailEnable))tuList.add(LineLinker.toUpdate.fwBusyVoicemailEnable);
		if(UsefulMethod.isNotEmpty(fwUnrVoicemailEnable))tuList.add(LineLinker.toUpdate.fwUnrVoicemailEnable);
		}

	public LineLinker getMyLine()
		{
		return myLine;
		}

	public void setMyLine(LineLinker myLine)
		{
		this.myLine = myLine;
		}

	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
		}

	public String getRoutePartitionName()
		{
		return routePartitionName;
		}

	public void setRoutePartitionName(String routePartitionName)
		{
		this.routePartitionName = routePartitionName;
		}

	public String getUsage()
		{
		return usage;
		}

	public void setUsage(String usage)
		{
		this.usage = usage;
		}

	public String getAlertingName()
		{
		return alertingName;
		}

	public void setAlertingName(String alertingName)
		{
		this.alertingName = alertingName;
		}

	public String getAsciiAlertingName()
		{
		return asciiAlertingName;
		}

	public void setAsciiAlertingName(String asciiAlertingName)
		{
		this.asciiAlertingName = asciiAlertingName;
		}

	public String getShareLineAppearanceCssName()
		{
		return shareLineAppearanceCssName;
		}

	public void setShareLineAppearanceCssName(String shareLineAppearanceCssName)
		{
		this.shareLineAppearanceCssName = shareLineAppearanceCssName;
		}

	public String getCallPickupGroupName()
		{
		return callPickupGroupName;
		}

	public void setCallPickupGroupName(String callPickupGroupName)
		{
		this.callPickupGroupName = callPickupGroupName;
		}

	public String getFwCallingSearchSpaceName()
		{
		return fwCallingSearchSpaceName;
		}

	public void setFwCallingSearchSpaceName(String fwCallingSearchSpaceName)
		{
		this.fwCallingSearchSpaceName = fwCallingSearchSpaceName;
		}

	public String getFwAllDestination()
		{
		return fwAllDestination;
		}

	public void setFwAllDestination(String fwAllDestination)
		{
		this.fwAllDestination = fwAllDestination;
		}

	public String getFwNoanDestination()
		{
		return fwNoanDestination;
		}

	public void setFwNoanDestination(String fwNoanDestination)
		{
		this.fwNoanDestination = fwNoanDestination;
		}

	public String getFwBusyDestination()
		{
		return fwBusyDestination;
		}

	public void setFwBusyDestination(String fwBusyDestination)
		{
		this.fwBusyDestination = fwBusyDestination;
		}

	public String getFwUnrDestination()
		{
		return fwUnrDestination;
		}

	public void setFwUnrDestination(String fwUnrDestination)
		{
		this.fwUnrDestination = fwUnrDestination;
		}

	public String getVoiceMailProfileName()
		{
		return voiceMailProfileName;
		}

	public void setVoiceMailProfileName(String voiceMailProfileName)
		{
		this.voiceMailProfileName = voiceMailProfileName;
		}

	public String getFwAllVoicemailEnable()
		{
		return fwAllVoicemailEnable;
		}

	public void setFwAllVoicemailEnable(String fwAllVoicemailEnable)
		{
		this.fwAllVoicemailEnable = fwAllVoicemailEnable;
		}

	public String getFwNoanVoicemailEnable()
		{
		return fwNoanVoicemailEnable;
		}

	public void setFwNoanVoicemailEnable(String fwNoanVoicemailEnable)
		{
		this.fwNoanVoicemailEnable = fwNoanVoicemailEnable;
		}

	public String getFwBusyVoicemailEnable()
		{
		return fwBusyVoicemailEnable;
		}

	public void setFwBusyVoicemailEnable(String fwBusyVoicemailEnable)
		{
		this.fwBusyVoicemailEnable = fwBusyVoicemailEnable;
		}

	public String getFwUnrVoicemailEnable()
		{
		return fwUnrVoicemailEnable;
		}

	public void setFwUnrVoicemailEnable(String fwUnrVoicemailEnable)
		{
		this.fwUnrVoicemailEnable = fwUnrVoicemailEnable;
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

