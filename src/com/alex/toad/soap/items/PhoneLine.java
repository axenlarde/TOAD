package com.alex.toad.soap.items;

import com.alex.toad.misc.BasicItem;
import com.alex.toad.misc.CollectionTools;
import com.alex.toad.webserver.AgentData;

/**********************************
 * Class used to store Phone Line parameters
 * 
 * @author RATEL Alexandre
 **********************************/
public class PhoneLine extends BasicItem
	{
	/***
	 * Variables
	 */
	private String description,
	lineLabel,
	asciiLineLabel,
	lineDisplay,
	lineDisplayAscii,
	externalPhoneNumberMask,
	lineNumber,
	routePartition,
	lineCSS,
	alertingName,
	asciiAlertingName,
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
	
	private int lineIndex;
	private AgentData agentData;

	/***************
	 * Constructor
	 ***************/
	public PhoneLine(String lineNumber, String routePartition)
		{
		super();
		this.lineNumber = lineNumber;
		this.routePartition = routePartition;
		}
	
	public PhoneLine(String description, String lineLabel,
			String asciiLineLabel, String lineDisplay, String lineDisplayAscii,
			String externalPhoneNumberMask,
			String lineNumber, String routePartition, String lineCSS,
			String alertingName,
			String asciiAlertingName,
			String fwCallingSearchSpaceName, String fwAllDestination,
			String fwNoanDestination, String fwBusyDestination,
			String fwUnrDestination, String voiceMailProfileName,
			String fwAllVoicemailEnable, String fwNoanVoicemailEnable,
			String fwBusyVoicemailEnable, String fwUnrVoicemailEnable,
			int lineIndex)
		{
		super();
		
		this.description = description;
		this.lineLabel = lineLabel;
		this.asciiLineLabel = asciiLineLabel;
		this.lineDisplay = lineDisplay;
		this.lineDisplayAscii = lineDisplayAscii;
		this.externalPhoneNumberMask = externalPhoneNumberMask;
		this.lineNumber = lineNumber;
		this.routePartition = routePartition;
		this.lineCSS = lineCSS;
		this.alertingName = alertingName;
		this.asciiAlertingName = asciiAlertingName;
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
		this.lineIndex = lineIndex;
		}

	/******
	 * Method used to resolve the line variables
	 * 
	 * In this case, "resolve" means apply regex variables
	 * For instance : file.firstname becomes "alexandre"
	 * @throws Exception 
	 */
	public void resolve() throws Exception
		{
		lineLabel = CollectionTools.applyPattern(agentData, lineLabel, this, false);
		asciiLineLabel = CollectionTools.applyPattern(agentData, asciiLineLabel, this, false);
		lineDisplay = CollectionTools.applyPattern(agentData, lineDisplay, this, false);
		lineDisplayAscii = CollectionTools.applyPattern(agentData, lineDisplayAscii, this, false);
		externalPhoneNumberMask = CollectionTools.applyPattern(agentData, externalPhoneNumberMask, this, false);
		lineNumber = CollectionTools.applyPattern(agentData, lineNumber, this, true);
		routePartition = CollectionTools.applyPattern(agentData, routePartition, this, true);
		lineCSS = CollectionTools.applyPattern(agentData, lineCSS, this, false);
		description = CollectionTools.applyPattern(agentData, description, this, false);
		alertingName = CollectionTools.applyPattern(agentData, alertingName, this, false);
		asciiAlertingName = CollectionTools.applyPattern(agentData, asciiAlertingName, this, false);
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
		}

	public String getLineLabel()
		{
		return lineLabel;
		}

	public void setLineLabel(String lineLabel)
		{
		this.lineLabel = lineLabel;
		}

	public String getAsciiLineLabel()
		{
		return asciiLineLabel;
		}

	public void setAsciiLineLabel(String asciiLineLabel)
		{
		this.asciiLineLabel = asciiLineLabel;
		}

	public String getLineDisplay()
		{
		return lineDisplay;
		}

	public void setLineDisplay(String lineDisplay)
		{
		this.lineDisplay = lineDisplay;
		}

	public String getLineDisplayAscii()
		{
		return lineDisplayAscii;
		}

	public void setLineDisplayAscii(String lineDisplayAscii)
		{
		this.lineDisplayAscii = lineDisplayAscii;
		}

	public String getExternalPhoneNumberMask()
		{
		return externalPhoneNumberMask;
		}

	public void setExternalPhoneNumberMask(String externalPhoneNumberMask)
		{
		this.externalPhoneNumberMask = externalPhoneNumberMask;
		}

	public String getLineNumber()
		{
		return lineNumber;
		}

	public void setLineNumber(String lineNumber)
		{
		this.lineNumber = lineNumber;
		}

	public String getRoutePartition()
		{
		return routePartition;
		}

	public void setRoutePartition(String routePartition)
		{
		this.routePartition = routePartition;
		}

	public int getLineIndex()
		{
		return lineIndex;
		}

	public void setLineIndex(int lineIndex)
		{
		this.lineIndex = lineIndex;
		}

	public String getLineCSS()
		{
		return lineCSS;
		}

	public void setLineCSS(String lineCSS)
		{
		this.lineCSS = lineCSS;
		}

	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
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

