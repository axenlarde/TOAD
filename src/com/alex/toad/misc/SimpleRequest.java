package com.alex.toad.misc;

import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.alex.toad.soap.items.PhoneService;
import com.alex.toad.soap.items.ServiceParameters;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.cucmAXLVersion;
import com.alex.toad.utils.Variables.itemType;
import com.cisco.axl.api._10.GetCCMVersionReq;
import com.cisco.axl.api._10.GetCCMVersionRes;

/**********************************
 * Class used to contain static method for
 * simple common AXL request to the CUCM
 * 
 * @author RATEL Alexandre
 **********************************/
public class SimpleRequest
	{
	
	
	
	/**************
	 * Method aims to return the Version of the CUCM of the asked item
	 *************/
	public static String getCUCMVersion() throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			com.cisco.axl.api._10.GetCCMVersionReq req = new com.cisco.axl.api._10.GetCCMVersionReq();
			com.cisco.axl.api._10.GetCCMVersionRes resp = Variables.getAXLConnectionToCUCMV105().getCCMVersion(req);//We send the request to the CUCM
			
			Variables.getLogger().info("CUCM Version : "+resp.getReturn().getComponentVersion().getVersion());
			return resp.getReturn().getComponentVersion().getVersion();
			}
		else
			{
			com.cisco.axl.api._10.GetCCMVersionReq req = new GetCCMVersionReq();
			GetCCMVersionRes resp = Variables.getAXLConnectionToCUCMV105().getCCMVersion(req);//We send the request to the CUCM
			
			Variables.getLogger().info("CUCM Version : "+resp.getReturn().getComponentVersion().getVersion());
			return resp.getReturn().getComponentVersion().getVersion();
			}
		}
	
	/*****
	 * Used to get the string value of an UUID item
	 */
	public static String getUUID(itemType type, String itemName) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			return getUUIDV105(type, itemName).getUuid();
			}
		else
			{
			return getUUIDV105(type, itemName).getUuid();
			}
		}
	
	/**
	 * Method used to find a UUID from the CUCM
	 * 
	 * In addition it stores all the UUID found to avoid to
	 * Interrogate the CUCM twice
	 * @throws Exception 
	 */
	public static com.cisco.axl.api._10.XFkType getUUIDV105(itemType type, String itemName) throws Exception
		{
		Variables.getLogger().debug("Getting UUID from CUCM : "+type+" "+itemName);
		
		if((itemName == null) || (itemName.equals("")))
			{
			return getXFKV105("", itemName, type);
			}
		
		String id = type.name()+itemName;
		
		for(storedUUID s : Variables.getUuidList())
			{
			if(s.getComparison().equals(id))
				{
				Variables.getLogger().debug("UUID known");
				return getXFKWithoutStoringItV105(s.getUUID(), itemName, type);
				}
			}
		
		if(type.equals(itemType.location))
			{
			com.cisco.axl.api._10.GetLocationReq req = new com.cisco.axl.api._10.GetLocationReq();
			com.cisco.axl.api._10.RLocation returnedTags = new com.cisco.axl.api._10.RLocation();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetLocationRes resp = Variables.getAXLConnectionToCUCMV105().getLocation(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getLocation().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.region))
			{
			com.cisco.axl.api._10.GetRegionReq req = new com.cisco.axl.api._10.GetRegionReq();
			com.cisco.axl.api._10.RRegion returnedTags = new com.cisco.axl.api._10.RRegion();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetRegionRes resp = Variables.getAXLConnectionToCUCMV105().getRegion(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getRegion().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.partition))
			{
			com.cisco.axl.api._10.GetRoutePartitionReq req = new com.cisco.axl.api._10.GetRoutePartitionReq();
			com.cisco.axl.api._10.RRoutePartition returnedTags = new com.cisco.axl.api._10.RRoutePartition();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetRoutePartitionRes resp = Variables.getAXLConnectionToCUCMV105().getRoutePartition(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getRoutePartition().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.callingsearchspace))
			{
			com.cisco.axl.api._10.GetCssReq req = new com.cisco.axl.api._10.GetCssReq();
			com.cisco.axl.api._10.RCss returnedTags = new com.cisco.axl.api._10.RCss();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetCssRes resp = Variables.getAXLConnectionToCUCMV105().getCss(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getCss().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.conferencebridge))
			{
			com.cisco.axl.api._10.GetConferenceBridgeReq req = new com.cisco.axl.api._10.GetConferenceBridgeReq();
			com.cisco.axl.api._10.RConferenceBridge returnedTags = new com.cisco.axl.api._10.RConferenceBridge();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetConferenceBridgeRes resp = Variables.getAXLConnectionToCUCMV105().getConferenceBridge(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getConferenceBridge().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.devicepool))
			{
			com.cisco.axl.api._10.GetDevicePoolReq req = new com.cisco.axl.api._10.GetDevicePoolReq();
			com.cisco.axl.api._10.RDevicePool returnedTags = new com.cisco.axl.api._10.RDevicePool();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetDevicePoolRes resp = Variables.getAXLConnectionToCUCMV105().getDevicePool(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getDevicePool().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.mediaresourcegroup))
			{
			com.cisco.axl.api._10.GetMediaResourceGroupReq req = new com.cisco.axl.api._10.GetMediaResourceGroupReq();
			com.cisco.axl.api._10.RMediaResourceGroup returnedTags = new com.cisco.axl.api._10.RMediaResourceGroup();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetMediaResourceGroupRes resp = Variables.getAXLConnectionToCUCMV105().getMediaResourceGroup(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getMediaResourceGroup().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.mediaresourcegrouplist))
			{
			com.cisco.axl.api._10.GetMediaResourceListReq req = new com.cisco.axl.api._10.GetMediaResourceListReq();
			com.cisco.axl.api._10.RMediaResourceList returnedTags = new com.cisco.axl.api._10.RMediaResourceList();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetMediaResourceListRes resp = Variables.getAXLConnectionToCUCMV105().getMediaResourceList(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getMediaResourceList().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.physicallocation))
			{
			com.cisco.axl.api._10.GetPhysicalLocationReq req = new com.cisco.axl.api._10.GetPhysicalLocationReq();
			com.cisco.axl.api._10.RPhysicalLocation returnedTags = new com.cisco.axl.api._10.RPhysicalLocation();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetPhysicalLocationRes resp = Variables.getAXLConnectionToCUCMV105().getPhysicalLocation(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getPhysicalLocation().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.routegroup))
			{
			com.cisco.axl.api._10.GetRouteGroupReq req = new com.cisco.axl.api._10.GetRouteGroupReq();
			com.cisco.axl.api._10.RRouteGroup returnedTags = new com.cisco.axl.api._10.RRouteGroup();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetRouteGroupRes resp = Variables.getAXLConnectionToCUCMV105().getRouteGroup(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getRouteGroup().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.srstreference))
			{
			com.cisco.axl.api._10.GetSrstReq req = new com.cisco.axl.api._10.GetSrstReq();
			com.cisco.axl.api._10.RSrst returnedTags = new com.cisco.axl.api._10.RSrst();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetSrstRes resp = Variables.getAXLConnectionToCUCMV105().getSrst(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getSrst().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.trunksip))
			{
			com.cisco.axl.api._10.GetSipTrunkReq req = new com.cisco.axl.api._10.GetSipTrunkReq();
			com.cisco.axl.api._10.RSipTrunk returnedTags = new com.cisco.axl.api._10.RSipTrunk();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetSipTrunkRes resp = Variables.getAXLConnectionToCUCMV105().getSipTrunk(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getSipTrunk().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.vg))
			{
			com.cisco.axl.api._10.GetGatewayReq req = new com.cisco.axl.api._10.GetGatewayReq();
			com.cisco.axl.api._10.RGateway returnedTags = new com.cisco.axl.api._10.RGateway();
			req.setDomainName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetGatewayRes resp = Variables.getAXLConnectionToCUCMV105().getGateway(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getGateway().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.datetimesetting))
			{
			com.cisco.axl.api._10.GetDateTimeGroupReq req = new com.cisco.axl.api._10.GetDateTimeGroupReq();
			com.cisco.axl.api._10.RDateTimeGroup returnedTags = new com.cisco.axl.api._10.RDateTimeGroup();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetDateTimeGroupRes resp = Variables.getAXLConnectionToCUCMV105().getDateTimeGroup(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getDateTimeGroup().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.phone))
			{
			com.cisco.axl.api._10.GetPhoneReq req = new com.cisco.axl.api._10.GetPhoneReq();
			com.cisco.axl.api._10.RPhone returnedTags = new com.cisco.axl.api._10.RPhone();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetPhoneRes resp = Variables.getAXLConnectionToCUCMV105().getPhone(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getPhone().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.udp))
			{
			com.cisco.axl.api._10.GetDeviceProfileReq req = new com.cisco.axl.api._10.GetDeviceProfileReq();
			com.cisco.axl.api._10.RDeviceProfile returnedTags = new com.cisco.axl.api._10.RDeviceProfile();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetDeviceProfileRes resp = Variables.getAXLConnectionToCUCMV105().getDeviceProfile(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getDeviceProfile().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.user))
			{
			com.cisco.axl.api._10.GetUserReq req = new com.cisco.axl.api._10.GetUserReq();
			com.cisco.axl.api._10.RUser returnedTags = new com.cisco.axl.api._10.RUser();
			req.setUserid(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetUserRes resp = Variables.getAXLConnectionToCUCMV105().getUser(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getUser().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.appuser))
			{
			com.cisco.axl.api._10.GetAppUserReq req = new com.cisco.axl.api._10.GetAppUserReq();
			com.cisco.axl.api._10.RAppUser returnedTags = new com.cisco.axl.api._10.RAppUser();
			req.setUserid(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetAppUserRes resp = Variables.getAXLConnectionToCUCMV105().getAppUser(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getAppUser().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.phonetemplatename))
			{
			com.cisco.axl.api._10.GetPhoneButtonTemplateReq req = new com.cisco.axl.api._10.GetPhoneButtonTemplateReq();
			com.cisco.axl.api._10.RPhoneButtonTemplate returnedTags = new com.cisco.axl.api._10.RPhoneButtonTemplate();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetPhoneButtonTemplateRes resp = Variables.getAXLConnectionToCUCMV105().getPhoneButtonTemplate(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getPhoneButtonTemplate().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.callmanagergroup))
			{
			com.cisco.axl.api._10.GetCallManagerGroupReq req = new com.cisco.axl.api._10.GetCallManagerGroupReq();
			com.cisco.axl.api._10.RCallManagerGroup returnedTags = new com.cisco.axl.api._10.RCallManagerGroup();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetCallManagerGroupRes resp = Variables.getAXLConnectionToCUCMV105().getCallManagerGroup(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getCallManagerGroup().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.devicemobilitygroup))
			{
			com.cisco.axl.api._10.GetDeviceMobilityGroupReq req = new com.cisco.axl.api._10.GetDeviceMobilityGroupReq();
			com.cisco.axl.api._10.RDeviceMobilityGroup returnedTags = new com.cisco.axl.api._10.RDeviceMobilityGroup();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetDeviceMobilityGroupRes resp = Variables.getAXLConnectionToCUCMV105().getDeviceMobilityGroup(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getDeviceMobilityGroup().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.telecasterservice))
			{
			com.cisco.axl.api._10.GetIpPhoneServicesReq req = new com.cisco.axl.api._10.GetIpPhoneServicesReq();
			com.cisco.axl.api._10.RIpPhoneServices returnedTags = new com.cisco.axl.api._10.RIpPhoneServices();
			req.setServiceName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetIpPhoneServicesRes resp = Variables.getAXLConnectionToCUCMV105().getIpPhoneServices(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getIpPhoneServices().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.commondeviceconfig))
			{
			com.cisco.axl.api._10.GetCommonDeviceConfigReq req = new com.cisco.axl.api._10.GetCommonDeviceConfigReq();
			com.cisco.axl.api._10.RCommonDeviceConfig returnedTags = new com.cisco.axl.api._10.RCommonDeviceConfig();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetCommonDeviceConfigRes resp = Variables.getAXLConnectionToCUCMV105().getCommonDeviceConfig(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getCommonDeviceConfig().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.siptrunksecurityprofile))
			{
			com.cisco.axl.api._10.GetSipTrunkSecurityProfileReq req = new com.cisco.axl.api._10.GetSipTrunkSecurityProfileReq();
			com.cisco.axl.api._10.RSipTrunkSecurityProfile returnedTags = new com.cisco.axl.api._10.RSipTrunkSecurityProfile();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetSipTrunkSecurityProfileRes resp = Variables.getAXLConnectionToCUCMV105().getSipTrunkSecurityProfile(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getSipTrunkSecurityProfile().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.sipprofile))
			{
			com.cisco.axl.api._10.GetSipProfileReq req = new com.cisco.axl.api._10.GetSipProfileReq();
			com.cisco.axl.api._10.RSipProfile returnedTags = new com.cisco.axl.api._10.RSipProfile();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetSipProfileRes resp = Variables.getAXLConnectionToCUCMV105().getSipProfile(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getSipProfile().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.linegroup))
			{
			com.cisco.axl.api._10.GetLineGroupReq req = new com.cisco.axl.api._10.GetLineGroupReq();
			com.cisco.axl.api._10.RLineGroup returnedTags = new com.cisco.axl.api._10.RLineGroup();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetLineGroupRes resp = Variables.getAXLConnectionToCUCMV105().getLineGroup(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getLineGroup().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.huntlist))
			{
			com.cisco.axl.api._10.GetHuntListReq req = new com.cisco.axl.api._10.GetHuntListReq();
			com.cisco.axl.api._10.RHuntList returnedTags = new com.cisco.axl.api._10.RHuntList();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetHuntListRes resp = Variables.getAXLConnectionToCUCMV105().getHuntList(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getHuntList().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.callpickupgroup))
			{
			com.cisco.axl.api._10.GetCallPickupGroupReq req = new com.cisco.axl.api._10.GetCallPickupGroupReq();
			com.cisco.axl.api._10.RCallPickupGroup returnedTags = new com.cisco.axl.api._10.RCallPickupGroup();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetCallPickupGroupRes resp = Variables.getAXLConnectionToCUCMV105().getCallPickupGroup(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getCallPickupGroup().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.voicemail))
			{
			com.cisco.axl.api._10.GetVoiceMailProfileReq req = new com.cisco.axl.api._10.GetVoiceMailProfileReq();
			com.cisco.axl.api._10.RVoiceMailProfile returnedTags = new com.cisco.axl.api._10.RVoiceMailProfile();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetVoiceMailProfileRes resp = Variables.getAXLConnectionToCUCMV105().getVoiceMailProfile(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getVoiceMailProfile().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.aargroup))
			{
			com.cisco.axl.api._10.GetAarGroupReq req = new com.cisco.axl.api._10.GetAarGroupReq();
			com.cisco.axl.api._10.RAarGroup returnedTags = new com.cisco.axl.api._10.RAarGroup();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetAarGroupRes resp = Variables.getAXLConnectionToCUCMV105().getAarGroup(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getAarGroup().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.usercontrolgroup))
			{
			com.cisco.axl.api._10.GetUserGroupReq req = new com.cisco.axl.api._10.GetUserGroupReq();
			com.cisco.axl.api._10.RUserGroup returnedTags = new com.cisco.axl.api._10.RUserGroup();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetUserGroupRes resp = Variables.getAXLConnectionToCUCMV105().getUserGroup(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getUserGroup().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.gateway))
			{
			com.cisco.axl.api._10.GetGatewayReq req = new com.cisco.axl.api._10.GetGatewayReq();
			com.cisco.axl.api._10.RGateway returnedTags = new com.cisco.axl.api._10.RGateway();
			req.setDomainName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetGatewayRes resp = Variables.getAXLConnectionToCUCMV105().getGateway(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getGateway().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.commonPhoneConfig))
			{
			com.cisco.axl.api._10.GetCommonPhoneConfigReq req = new com.cisco.axl.api._10.GetCommonPhoneConfigReq();
			com.cisco.axl.api._10.RCommonPhoneConfig returnedTags = new com.cisco.axl.api._10.RCommonPhoneConfig();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetCommonPhoneConfigRes resp = Variables.getAXLConnectionToCUCMV105().getCommonPhoneConfig(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getCommonPhoneConfig().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.securityProfile))
			{
			com.cisco.axl.api._10.GetPhoneSecurityProfileReq req = new com.cisco.axl.api._10.GetPhoneSecurityProfileReq();
			com.cisco.axl.api._10.RPhoneSecurityProfile returnedTags = new com.cisco.axl.api._10.RPhoneSecurityProfile();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetPhoneSecurityProfileRes resp = Variables.getAXLConnectionToCUCMV105().getPhoneSecurityProfile(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getPhoneSecurityProfile().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.softkeytemplate))
			{
			com.cisco.axl.api._10.GetSoftKeyTemplateReq req = new com.cisco.axl.api._10.GetSoftKeyTemplateReq();
			com.cisco.axl.api._10.RSoftKeyTemplate returnedTags = new com.cisco.axl.api._10.RSoftKeyTemplate();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetSoftKeyTemplateRes resp = Variables.getAXLConnectionToCUCMV105().getSoftKeyTemplate(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getSoftKeyTemplate().getUuid(), itemName, type);
			}
		else if(type.equals(itemType.serviceprofile))
			{
			com.cisco.axl.api._10.GetServiceProfileReq req = new com.cisco.axl.api._10.GetServiceProfileReq();
			com.cisco.axl.api._10.RServiceProfile returnedTags = new com.cisco.axl.api._10.RServiceProfile();
			req.setName(itemName);
			returnedTags.setUuid("");
			req.setReturnedTags(returnedTags);
			com.cisco.axl.api._10.GetServiceProfileRes resp = Variables.getAXLConnectionToCUCMV105().getServiceProfile(req);//We send the request to the CUCM
			return getXFKV105(resp.getReturn().getServiceProfile().getUuid(), itemName, type);
			}
		
		throw new Exception("ItemType \""+type+"\" not found");
		}
	
	/***************
	 * Store and return an XFKType item from an UUID
	 */
	private static com.cisco.axl.api._10.XFkType getXFKV105(String UUID, String itemName, itemType type)
		{
		com.cisco.axl.api._10.XFkType xfk = new com.cisco.axl.api._10.XFkType();
		//UUID = UUID.toLowerCase();//Temp
		Variables.getUuidList().add(new storedUUID(UUID, itemName, type));//We add the item to the uuid stored slist
		xfk.setUuid(UUID);
		Variables.getLogger().debug("Returned UUID from CUCM : "+xfk.getUuid());
		return xfk;
		}
	
	/***************
	 * return an XFKType item
	 */
	private static com.cisco.axl.api._10.XFkType getXFKWithoutStoringItV105(String UUID, String itemName, itemType type)
		{
		com.cisco.axl.api._10.XFkType xfk = new com.cisco.axl.api._10.XFkType();
		xfk.setUuid(UUID);
		Variables.getLogger().debug("Returned UUID from CUCM : "+xfk.getUuid());
		return xfk;
		}
	
	
	/**
	 * Method used to reach the method of the good version
	 */
	public static List<Object> doSQLQuery(String request) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			return doSQLQueryV105(request);
			}
		
		throw new Exception("Unsupported AXL Version");
		}
	
	/**
	 * Method used to reach the method of the good version
	 */
	public static void doSQLUpdate(String request) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			doSQLUpdateV105(request);
			}
		else
			{
			throw new Exception("Unsupported AXL Version");
			}
		}
	
	/***
	 * Method used to launch a SQL request to the CUCM and get
	 * a result as an ArrayList<String>
	 * 
	 * each "String" is a list of result
	 */
	private static List<Object> doSQLQueryV105(String request) throws Exception
		{
		Variables.getLogger().debug("SQL request sent : "+request);
		
		com.cisco.axl.api._10.ExecuteSQLQueryReq req = new com.cisco.axl.api._10.ExecuteSQLQueryReq();
		req.setSql(request);
		com.cisco.axl.api._10.ExecuteSQLQueryRes resp = Variables.getAXLConnectionToCUCMV105().executeSQLQuery(req);//We send the request to the CUCM
		
		List<Object> myList = resp.getReturn().getRow();
		
		return myList;
		}
	
	/***
	 * Method used to launch a SQL request to the CUCM and get
	 * a result as an ArrayList<String>
	 * 
	 * each "String" is a list of result
	 */
	private static void doSQLUpdateV105(String request) throws Exception
		{
		Variables.getLogger().debug("SQL request sent : "+request);
		
		com.cisco.axl.api._10.ExecuteSQLUpdateReq req = new com.cisco.axl.api._10.ExecuteSQLUpdateReq();
		req.setSql(request);
		com.cisco.axl.api._10.ExecuteSQLUpdateRes resp = Variables.getAXLConnectionToCUCMV105().executeSQLUpdate(req);//We send the request to the CUCM
		}
	
	/***********
	 * Method used to find the UUID of a
	 * set DigitDiscard pattern
	 * 
	 * For instance : "PreDot"
	 */
	public static String getDigitDiscardUUID(String digitDiscardName)
		{
		if(!digitDiscardName.equals(""))
			{
			try
				{
				List<Object> SQLResp = SimpleRequest.doSQLQuery("select pkid from digitdiscardinstruction where name='"+digitDiscardName+"'");
				
				for(Object o : SQLResp)
					{
					Element rowElement = (Element) o;
					NodeList list = rowElement.getChildNodes();
					
					for(int i = 0; i< list.getLength(); i++)
						{
						if(list.item(i).getNodeName().equals("pkid"))
							{
							Variables.getLogger().debug("Digitdiscardinstruction "+digitDiscardName+" UUID found : "+list.item(i).getTextContent());
							return list.item(i).getTextContent();
							}
						}
					
					}
				}
			catch (Exception e)
				{
				e.printStackTrace();
				Variables.getLogger().error("Digitdiscardinstruction \""+digitDiscardName+"\" has not been found. We return null instead : "+e.getMessage());
				}
			}
		else
			{
			Variables.getLogger().debug("Digitdiscardinstruction was empty. We return null instead");
			}
		
		
		return null;
		}
	
	/*********************************************
	 * Dedicated method to get the UUID of a line
	 * @throws Exception 
	 */
	public static String getLineUUID(String lineNumber, String partitionName) throws Exception
		{
		if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
			{
			return getLineUUIDV105(lineNumber, partitionName).getUuid();
			}
		else
			{
			return getLineUUIDV105(lineNumber, partitionName).getUuid();
			}
		}
	
	public static com.cisco.axl.api._10.XFkType getLineUUIDV105(String lineNumber, String partitionName) throws Exception
		{
		Variables.getLogger().debug("Get Line UUID from CUCM : "+lineNumber+" "+partitionName);
		
		if((lineNumber == null) || (lineNumber.equals("")) || (partitionName == null) || (partitionName.equals("")))
			{
			return getXFKV105("", lineNumber, itemType.line);
			}
		
		String id = itemType.line.name()+lineNumber;
		
		for(storedUUID s : Variables.getUuidList())
			{
			if(s.getComparison().equals(id))
				{
				Variables.getLogger().debug("UUID known");
				return getXFKWithoutStoringItV105(s.getUUID(), lineNumber, itemType.line);
				}
			}
		
		com.cisco.axl.api._10.GetLineReq req = new com.cisco.axl.api._10.GetLineReq();
		com.cisco.axl.api._10.RLine returnedTags = new com.cisco.axl.api._10.RLine();
		
		req.setPattern(lineNumber);
		req.setRoutePartitionName(new JAXBElement(new QName("routePartitionName"), com.cisco.axl.api._10.XFkType.class, SimpleRequest.getUUIDV105(itemType.partition, partitionName)));
		returnedTags.setUuid("");
		req.setReturnedTags(returnedTags);
		com.cisco.axl.api._10.GetLineRes resp = Variables.getAXLConnectionToCUCMV105().getLine(req);//We send the request to the CUCM
		return getXFKV105(resp.getReturn().getLine().getUuid(), lineNumber, itemType.line);
		}
	
	/**
	 * To make a user authenticate by the CUCM 
	 */
	public static boolean doAuthenticate(String userID, String password)
		{
		try
			{
			com.cisco.axl.api._10.DoAuthenticateUserReq req = new com.cisco.axl.api._10.DoAuthenticateUserReq();
			
			req.setUserid(userID);
			req.setPassword(password);
			
			com.cisco.axl.api._10.DoAuthenticateUserRes resp = Variables.getAXLConnectionToCUCMV105().doAuthenticateUser(req);
			
			return Boolean.parseBoolean(resp.getReturn().getUserAuthenticated());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while authenticating user "+userID+" : "+e.getMessage(),e);
			}
		
		return false;
		}
	
	/***********
	 * Method used to send a sql request and return only the first replied value
	 * Supposed that the request is really accurate and should only return one value
	 * @throws Exception 
	 */
	public static String getSimpleResponse(String request, String valueToReturn) throws Exception
		{
		List<Object> SQLResp = SimpleRequest.doSQLQuery(request);
		
		for(Object o : SQLResp)
			{
			Element rowElement = (Element) o;
			NodeList list = rowElement.getChildNodes();
			
			for(int i = 0; i< list.getLength(); i++)
				{
				if(list.item(i).getNodeName().equals(valueToReturn))
					{
					Variables.getLogger().debug("Value found and returned for "+valueToReturn+" : "+list.item(i).getTextContent());
					return list.item(i).getTextContent();
					}
				}
			}
		
		throw new EmptyValueException("Nothing found for value '"+valueToReturn+"' and request : "+request);
		}
	
	/**
	 * To update phone service parameters
	 * @throws Exception 
	 */
	public static void updateServiceParameters(PhoneService service, String deviceName) throws Exception
		{
		if((service.getParameterList() != null) && (service.getParameterList().size() > 0))
			{
			Variables.getLogger().debug("Updating phone service parameters for service '"+service.getServicename()+"' onphone '"+deviceName+"'");
			
			//Before updating we check that there is something to update
			try
				{
				SimpleRequest.getSimpleResponse("select p.pkid from telecastersubscribedparameter p, telecastersubscribedservice s, device d where d.pkid=s.fkdevice and s.pkid=p.fktelecastersubscribedservice and d.name='"+deviceName+"'", "pkid");
				}
			catch (Exception e)
				{
				//Means that we found no value to update so we must create value instead
				Variables.getLogger().debug("We found no parameter to update for server '"+service.getServicename()+"' so we create them instead");
				createServiceParameters(service, deviceName);
				}
			
			//We get the service UUID
			String serviceUuid = SimpleRequest.getUUIDV105(itemType.telecasterservice, service.getServicename()).getUuid().replaceAll("\\{|\\}", "").toLowerCase();
			StringBuffer parameters = new StringBuffer("");
			
			//We update each parameter
			for(ServiceParameters parameter : service.getParameterList())
				{
				//We get the parameter UUID
				String query = "select pkid from telecasterserviceparameter where name ='"+parameter.getName()+"'";
				String parameterUuid = SimpleRequest.getSimpleResponse(query, "pkid");
				
				query = "UPDATE telecastersubscribedparameter SET value = '" + parameter.getValue() + "' WHERE fktelecasterserviceparameter = '" + parameterUuid + "' AND fktelecastersubscribedservice = " +
		                "(SELECT ss.pkid FROM telecastersubscribedservice ss INNER JOIN telecasterservice s ON ss.fktelecasterservice = s.pkid AND s.pkid = '" + serviceUuid + "' "
		                +"INNER JOIN device d ON ss.fkdevice = d.pkid AND d.name = '" + deviceName + "')";
				SimpleRequest.doSQLUpdate(query);//We send the request to update the parameter
				
				parameters.append(parameter.getName()+"="+parameter.getValue()+"&");
				}
			
			//We update the sUrl with the parameter value
			String queryUrl = "UPDATE telecastersubscribedservice SET serviceurl = '"+service.getSurl()+parameters.toString().substring(0, parameters.length()-1)+"' WHERE fkdevice = (SELECT pkid FROM device WHERE name = '" + deviceName
	            + "') AND fktelecasterservice = '" + serviceUuid + "'";
			SimpleRequest.doSQLUpdate(queryUrl);//We send the second request
			Variables.getLogger().debug("Phone service parameter updated for phone "+deviceName);
			}
		else
			{
			Variables.getLogger().debug("No parameters to update for service '"+service.getServicename()+"' on phone "+deviceName);
			}
		}
	
	/**
	 * To create phone service parameters
	 * @throws Exception 
	 */
	public static void createServiceParameters(PhoneService service, String deviceName) throws Exception
		{
		if((service.getParameterList() != null) && (service.getParameterList().size() > 0))
			{
			Variables.getLogger().debug("Creating phone service parameters for service '"+service.getServicename()+"' onphone '"+deviceName+"'");
			
			//Before creating we check that there is not already something
			try
				{
				SimpleRequest.getSimpleResponse("select p.pkid from telecastersubscribedparameter p, telecastersubscribedservice s, device d where d.pkid=s.fkdevice and s.pkid=p.fktelecastersubscribedservice and d.name='"+deviceName+"'", "pkid");
				//Reaching this point means that there is already some value which is not normal
				Variables.getLogger().error("Some existing parameters were found for service '"+service.getServicename()+"' : aborting creation");
				return;
				}
			catch (EmptyValueException e)
				{
				//It means that there is no existing parameters so we can create them
				}
			
			//We get the telecastersubscribedservice UUID
			String tssUUID = SimpleRequest.getSimpleResponse("select s.pkid from telecastersubscribedservice s, device d where d.pkid=s.fkdevice and d.name='"+deviceName+"' and s.servicename='"+service.getServicename()+"'", "pkid");
			StringBuffer parameters = new StringBuffer("");
			
			//We create each parameter
			for(ServiceParameters parameter : service.getParameterList())
				{
				//We get the parameter UUID
				String paramUUID = SimpleRequest.getSimpleResponse("select s.pkid from telecasterserviceparameter s where s.name = '"+parameter.getName()+"'","pkid");
				
				String query = "INSERT INTO telecastersubscribedparameter (fktelecastersubscribedservice, fktelecasterserviceparameter, value) VALUES ('"+tssUUID+"', '"+paramUUID+"', '"+parameter.getValue()+"')";
				SimpleRequest.doSQLUpdate(query);//We send the request to create the parameter
				
				parameters.append(parameter.getName()+"="+parameter.getValue()+"&");
				}
			
			//We update the sUrl with the parameter value
			String queryUrl = "UPDATE telecastersubscribedservice SET serviceurl = '"+service.getSurl()+parameters.toString().substring(0, parameters.length()-1)+"' WHERE fkdevice = (SELECT pkid FROM device WHERE name = '" + deviceName
	            + "') AND servicename = '"+service.getServicename()+"'";
			SimpleRequest.doSQLUpdate(queryUrl);//We send the second request
			
			Variables.getLogger().debug("Phone service parameter created for phone "+deviceName);
			}
		else
			{
			Variables.getLogger().debug("No parameters to update for service '"+service.getServicename()+"' on phone "+deviceName);
			}
		}
			
	
	/*2022*//*RATEL Alexandre 8)*/
	}

