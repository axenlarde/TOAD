package com.alex.toad.risport;

import java.util.ArrayList;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.bouncycastle.util.IPAddress;

import com.alex.perceler.device.misc.BasicPhone;
import com.alex.perceler.utils.UsefulMethod;
import com.alex.perceler.utils.Variables;
import com.cisco.schemas.ast.soap.ArrayOfCmDevice;
import com.cisco.schemas.ast.soap.ArrayOfCmNode;
import com.cisco.schemas.ast.soap.ArrayOfSelectItem;
import com.cisco.schemas.ast.soap.CmDevice;
import com.cisco.schemas.ast.soap.CmNode;
import com.cisco.schemas.ast.soap.CmSelectBy;
import com.cisco.schemas.ast.soap.CmSelectionCriteria;
import com.cisco.schemas.ast.soap.SelectCmDevice;
import com.cisco.schemas.ast.soap.SelectCmDeviceReturn;
import com.cisco.schemas.ast.soap.SelectItem;

/**
 * Static method used for risport request
 *
 * @author Alexandre RATEL
 */
public class RisportTools
	{
	
	/**
	 * To get the deviceList from the RIS Response
	 */
	private static ArrayList<BasicPhone> getDeviceFromRISResponse(SelectCmDeviceReturn selectResponse)
		{
		ArrayList<BasicPhone> pl = new ArrayList<BasicPhone>();
		
		try
			{
			Variables.getLogger().debug("Parsing RIS reply");
			
			if(selectResponse.getSelectCmDeviceResult().getTotalDevicesFound() != 0)
				{
				for(CmNode node : selectResponse.getSelectCmDeviceResult().getCmNodes().getItem())
					{
					Variables.getLogger().debug("RIS Result for node : "+node.getName());
					if(node.getCmDevices() != null)
						{
						ArrayOfCmDevice dl = node.getCmDevices();
						if(dl != null)
							{
							for(CmDevice d : dl.getItem())
								{
								pl.add(new BasicPhone(d.getName(),
										d.getDescription(),
										d.getModel().toString(),
										d.getIPAddress().getItem().get(0).getIP(),
										d.getStatus().name().toLowerCase().replace("_", "")));
								
								Variables.getLogger().debug("Found "+d.getName()+" : "+d.getStatus());
								}
							}
						else
							{
							Variables.getLogger().debug("no device for this node");
							}
						}
					else
						{
						Variables.getLogger().debug("no device for this node");
						}
					}
				}
			else
				{
				Variables.getLogger().debug("no result found on any node");
				}
			
			Variables.getLogger().debug(pl.size()+" phone found");
			return pl;
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR : "+e.getMessage(),e);
			}
		
		return pl;
		}
	
	
	/**
	 * Used to get the status of the given phones using risport
	 */
	public static ArrayList<BasicPhone> doPhoneSurvey(ArrayList<BasicPhone> phoneList)
		{
		try
			{
			if(phoneList.size() == 0)throw new Exception("Phone list cannot be empty");
			/**
			 * We limit the number of phone to request at the same time because
			 * we know the RIS service works better with smaller request
			 * Especially in large cluster
			 */
			int maxPhoneRequest = Integer.parseInt(UsefulMethod.getTargetOption("rismaxphonerequest"));
			ArrayList<BasicPhone> result = new ArrayList<BasicPhone>();
			ArrayList<BasicPhone> temp = new ArrayList<BasicPhone>();
			int index = 0;
			while(index < phoneList.size())
				{
				temp.add(phoneList.get(index));
				index++;
				if((temp.size()==maxPhoneRequest) || (index == phoneList.size()))
					{
					CmSelectionCriteria criteria = buildRISRequest(temp);
					
					//make selectCmDevice request
					Variables.getLogger().debug("Sending RIS request");
					SelectCmDeviceReturn selectResponse = Variables.getRisConnection().selectCmDevice("",criteria);
					result.addAll(getDeviceFromRISResponse(selectResponse));
					temp.clear();
					}
				}
			return result;
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while retrieving phone status using RIS : "+e.getMessage());
			}
		return null;
		}
	
	/**
	 * To build a RIS request
	 */
	private static CmSelectionCriteria buildRISRequest(ArrayList<BasicPhone> phoneList)
		{
		SelectCmDevice sxmlParams = new SelectCmDevice();
		CmSelectionCriteria criteria = new CmSelectionCriteria();
		long maxNum;
		try
			{
			maxNum = Long.parseLong(UsefulMethod.getTargetOption("rismaxphonerequest"));
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR : "+e.getMessage(),e);
			maxNum = 500;
			}
		long modelNum = 255;
		ArrayOfSelectItem items = new ArrayOfSelectItem();
		
		for(BasicPhone p : phoneList)
			{
			SelectItem item = new SelectItem();
			item.setItem(p.getName());
			items.getItem().add(item);
			Variables.getLogger().debug("RIS : adding the following phone to the request : "+p.getName());
			}
		
		criteria.setMaxReturnedDevices(maxNum);
		criteria.setModel(modelNum);
		criteria.setDeviceClass("Phone");
		criteria.setStatus("Registered");
		criteria.setSelectBy(CmSelectBy.NAME);
		criteria.setSelectItems(items);
		sxmlParams.setCmSelectionCriteria(criteria);
		
		return criteria;
		}
	
	
	public static ArrayList<BasicPhone> getDeviceByIP(String IP)
		{
		ArrayList<BasicPhone> result = new ArrayList<BasicPhone>();
		
		try
			{
			/***
			 * If the given IP is a correct one we keep it unchanged,
			 * but if it is an incomplete one, we add a * to create a search mask
			 */
			String IPToAdd = (InetAddressValidator.getInstance().isValidInet4Address(IP))?IP:IP+"*";
			
			SelectCmDevice sxmlParams = new SelectCmDevice();
			CmSelectionCriteria criteria = new CmSelectionCriteria();
			long maxNum;
			
			try
				{
				maxNum = Long.parseLong(UsefulMethod.getTargetOption("rismaxphonerequest"));
				}
			catch (Exception e)
				{
				Variables.getLogger().error("ERROR : "+e.getMessage(),e);
				maxNum = 500;
				}
			
			long modelNum = 255;
			ArrayOfSelectItem items = new ArrayOfSelectItem();
			SelectItem item = new SelectItem();
			item.setItem(IPToAdd);
			items.getItem().add(item);
			Variables.getLogger().debug("RIS : adding the following IP to the request : "+IPToAdd);
				
			criteria.setMaxReturnedDevices(maxNum);
			criteria.setModel(modelNum);
			criteria.setDeviceClass("Phone");
			criteria.setStatus("Registered");
			criteria.setSelectBy(CmSelectBy.IPV_4_ADDRESS);
			criteria.setSelectItems(items);
			sxmlParams.setCmSelectionCriteria(criteria);
			
			Variables.getLogger().debug("Sending RIS request");
			SelectCmDeviceReturn selectResponse = Variables.getRisConnection().selectCmDevice("",criteria);
			result.addAll(getDeviceFromRISResponse(selectResponse));
			
			if(result.size() == maxNum)Variables.getLogger().warn("The phone list returned for the IP "+IPToAdd+" returned exactly "+maxNum+" phones, so the result is maybe trunkated and therefore we are maybe missing some phones");
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while making a phone search by IP using RIS : "+e.getMessage(), e);
			}
		
		return result;
		}
	
	/*2019*//*RATEL Alexandre 8)*/
	}
