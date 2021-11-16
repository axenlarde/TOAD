package com.alex.toad.misc;

import java.util.ArrayList;

import com.alex.perceler.device.misc.Device;
import com.alex.perceler.utils.LanguageManagement;
import com.alex.perceler.utils.UsefulMethod;
import com.alex.perceler.utils.Variables;

/**
 * Used to send email in a dedicated thread
 *
 * @author Alexandre RATEL
 */
public class EmailManager extends Thread
	{
	/**
	 * Variables
	 */
	ArrayList<ItemToMigrate> itmList;
	
	public EmailManager(ArrayList<ItemToMigrate> itmList)
		{
		super();
		this.itmList = itmList;
		start();
		}
	
	public void run()
		{
		sendReportEmail();
		}

	private void sendReportEmail()
		{
		try
			{
			Variables.getLogger().debug("Preparing email content");
			
			StringBuffer content = new StringBuffer("");
			content.append(LanguageManagement.getString("emailreportcontent").replaceAll("\\\\r\\\\n", "\t\r\n"));
			for(ItemToMigrate itm : itmList)
				{
				content.append(itm.getInfo()+" : "+itm.getStatus()+" : "+itm.getDetailedStatus()+"\t\r\n");
				if(itm.getErrorList().size() > 0)
					{
					for(ErrorTemplate err : itm.getErrorList())
						{
						content.append("\t- "+err.getErrorDesc()+"\t\r\n");
						}
					}
				if(itm instanceof Device)
					{
					Device d = (Device)itm;
					if(d.getCliInjector().getErrorList().size() > 0)
						{
						for(ErrorTemplate err : d.getCliInjector().getErrorList())
							{
							content.append("\t- "+err.getErrorDesc()+"\t\r\n");
							}
						}
					}
				}
			content.append("\t\r\n");
			content.append(LanguageManagement.getString("emailfooter").replaceAll("\\\\r\\\\n", "\t\r\n"));

			//Variables.getLogger().debug("Email content ready to be sent : "+content.toString());
			
			UsefulMethod.sendEmailToTheAdminList(
					LanguageManagement.getString("emailreportsubject"),
					content.toString());
			
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while sending email : "+e.getMessage());
			}
		}
	
	/*2019*//*RATEL Alexandre 8)*/
	}
