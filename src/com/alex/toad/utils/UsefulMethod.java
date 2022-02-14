package com.alex.toad.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Level;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.alex.toad.cucm.user.misc.UserCreationProfile;
import com.alex.toad.cucm.user.misc.UserTemplate;
import com.alex.toad.misc.DidRange;
import com.alex.toad.misc.Office;
import com.alex.toad.misc.OfficeSetting;
import com.alex.toad.misc.Range;
import com.alex.toad.misc.SimpleRequest;
import com.alex.toad.misc.Task;
import com.alex.toad.misc.UsedItemList;
import com.alex.toad.misc.ValueMatcher;
import com.alex.toad.misc.storedUUID;
import com.alex.toad.rest.misc.RESTServer;
import com.alex.toad.uccx.items.Team;
import com.alex.toad.uccx.items.UCCXAgent.AgentType;
import com.alex.toad.utils.Variables.SubstituteType;
import com.alex.toad.utils.Variables.UCCXRESTVersion;
import com.alex.toad.utils.Variables.actionType;
import com.alex.toad.utils.Variables.agentStatus;
import com.alex.toad.utils.Variables.cucmAXLVersion;
import com.alex.toad.utils.Variables.itemType;



/**********************************
 * Class used to store the useful static methods
 * 
 * @author RATEL Alexandre
 **********************************/
public class UsefulMethod
	{
	
	/*****
	 * Method used to read the main config file
	 * @throws Exception 
	 */
	public static ArrayList<String[][]> readMainConfigFile(String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			file = xMLReader.fileRead("./"+fileName);
			
			listParams.add("config");
			return xMLGear.getResultListTab(file, listParams);
			}
		catch(FileNotFoundException fnfexc)
			{
			fnfexc.printStackTrace();
			throw new FileNotFoundException("The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the file : "+fileName+" : "+exc.getMessage());
			}
		
		}
	
	
	/***************************************
	 * Method used to get a specific value
	 * in the user preference XML File
	 ***************************************/
	public synchronized static String getTargetOption(String node) throws Exception
		{
		for(String[] s : Variables.getMainConfig().get(0))
			{
			if(s[0].equals(node))return s[1];
			}
		
		/***********
		 * If this point is reached, the option looked for was not found
		 */
		throw new Exception("Option \""+node+"\" not found"); 
		}
	/*************************/
	
	
	
	/************************
	 * Check if java version
	 * is correct
	 ***********************/
	public static void checkJavaVersion()
		{
		try
			{
			String jVer = new String(System.getProperty("java.version"));
			Variables.getLogger().info("Detected JRE version : "+jVer);
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().info("ERROR : It has not been possible to detect JRE version",exc);
			}
		}
	/***********************/
	
	
	/******
	 * Method used to initialize the AXL Connection to the CUCM
	 */
	public static synchronized void initAXLConnectionToCUCM() throws Exception
		{
		Variables.getLogger().debug("CUCM AXL connection initialization started");
		try
			{
			UsefulMethod.disableSecurity();//We first turned off security
			
			if(Variables.getCUCMVersion().equals(cucmAXLVersion.version105))
				{
				com.cisco.axlapiservice10.AXLAPIService axlService = new com.cisco.axlapiservice10.AXLAPIService();
				com.cisco.axlapiservice10.AXLPort axlPort = axlService.getAXLPort();
				
				// Set the URL, user, and password on the JAX-WS client
				String validatorUrl = "https://"+UsefulMethod.getTargetOption("axlhost")+":"+UsefulMethod.getTargetOption("axlport")+"/axl/";
				
				((BindingProvider) axlPort).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, validatorUrl);
				((BindingProvider) axlPort).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, UsefulMethod.getTargetOption("axlusername"));
				((BindingProvider) axlPort).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, UsefulMethod.getTargetOption("axlpassword"));
				
				Variables.setAXLConnectionToCUCMV105(axlPort);
				}
			
			Variables.getLogger().debug("WSDL Initialization done");
			
			/**
			 * We now check if the CUCM is reachable by asking him its version
			 */
			SimpleRequest.getCUCMVersion();
			Variables.setCUCMReachable(true);
			Variables.getLogger().debug("CUCM AXL connection initialization done");
			}
		catch (Exception e)
			{
			Variables.getLogger().error("Error while initializing CUCM connection : "+e.getMessage(),e);
			Variables.setCUCMReachable(false);
			throw e;
			}
		}
	
	/**
	 * Method used to initialize the UCCX REST connection
	 * @throws Exception 
	 */
	public static RESTServer initUCCXConnection() throws Exception
		{
		return new RESTServer(UsefulMethod.getTargetOption("uccxhost"),
				UsefulMethod.getTargetOption("uccxport"),
				UsefulMethod.getTargetOption("uccxusername"),
				UsefulMethod.getTargetOption("uccxpassword"),
				Integer.parseInt(UsefulMethod.getTargetOption("uccxresttimeout")),
				"UCCX server");
		}
	
	/**
	 * Method used to init the userlocal arraylist
	 */
	public static ArrayList<String> initUserLocalList()
		{
		ArrayList<String> userLocal = new ArrayList<String>();
		userLocal.add("French France");
		userLocal.add("Italian Italy");
		userLocal.add("English United States");
		
		return userLocal;
		}
	
	/**
	 * Method used to init the country arraylist
	 */
	public static ArrayList<String> initCountryList()
		{
		ArrayList<String> country = new ArrayList<String>();
		country.add("France");
		country.add("Italy");
		country.add("United Kingdom");
		
		return country;
		}
	
	
	/**
	 * Method used when the application failed to 
	 * initialize
	 */
	public static void failedToInit(Exception exc)
		{
		exc.printStackTrace();
		JOptionPane.showMessageDialog(null,"Application failed to init :\r\n"+exc.getMessage()+"\r\nThe software will now exit","ERROR",JOptionPane.ERROR_MESSAGE);
		Variables.getLogger().error(exc.getMessage());
		Variables.getLogger().error("Application failed to init : System.exit(0)");
		System.exit(0);
		}
	
	/**
	 * Initialization of the internal variables from
	 * what we read in the configuration file
	 * @throws Exception 
	 */
	public static void initInternalVariables() throws Exception
		{
		/***********
		 * Logger
		 */
		String level = UsefulMethod.getTargetOption("log4j");
		if(level.compareTo("DEBUG")==0)
			{
			Variables.getLogger().setLevel(Level.DEBUG);
			}
		else if (level.compareTo("INFO")==0)
			{
			Variables.getLogger().setLevel(Level.INFO);
			}
		else if (level.compareTo("ERROR")==0)
			{
			Variables.getLogger().setLevel(Level.ERROR);
			}
		else
			{
			//Default level is INFO
			Variables.getLogger().setLevel(Level.INFO);
			}
		Variables.getLogger().info("Log level found in the configuration file : "+Variables.getLogger().getLevel().toString());
		/*************/
		
		/************
		 * Etc...
		 */
		//If needed, just write it here
		/*************/
		}
	
	/**
	 * Used to return the file content regarding the data source (xml file or database file)
	 * @throws Exception 
	 */
	public static String getFlatFileContent(String fileName) throws Exception
		{
		return xMLReader.fileRead(Variables.getMainConfigFileDirectory()+"\\"+fileName);
		}
	
	/************
	 * Method used to read the office list file
	 * @throws Exception 
	 */
	public static ArrayList<Office> readOfficeFile(String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		ArrayList<String[][]> result;
		ArrayList<ArrayList<String[][]>> extendedList;
		ArrayList<Office> myOfficeList = new ArrayList<Office>();
		
		try
			{
			Variables.getLogger().info("Reading of the office list file : "+fileName);
			file = UsefulMethod.getFlatFileContent(fileName);
			
			listParams.add("offices");
			listParams.add("office");
			result = xMLGear.getResultListTab(file, listParams);
			extendedList = xMLGear.getResultListTabExt(file, listParams);
			
			
			//We create an ArrayList containing the offices
			for(int i=0; i<result.size(); i++)
				{
				String[][] tab = result.get(i);
				ArrayList<String[][]> tabE = extendedList.get(i);
				
				ArrayList<DidRange> didRanges = new ArrayList<DidRange>();
				//ArrayList<PhoneService> serviceList = new ArrayList<PhoneService>();
				//ArrayList<SpeedDial> sdList = new ArrayList<SpeedDial>();
				ArrayList<OfficeSetting> settings = new ArrayList<OfficeSetting>();
				
				for(int j=0; j<tab.length; j++)
					{
					if(tab[j][0].equals("didranges"))
						{
						for(String[] s : tabE.get(j))
							{
							//Here we process the multiple did ranges and regex cases
							DidRange myRange = new DidRange(s[1]);
							ArrayList<DidRange> myLR = new ArrayList<DidRange>();
							if(myRange.getPattern() == null)
								{
								//So we have to process a regex based on a range
								for(String str : getRegexFromRange(myRange.getFirst(), myRange.getLast()))
									{
									Variables.getLogger().debug("Resulting regex for the range "+myRange.getFirst()+" > "+myRange.getLast()+" : "+str);
									myLR.add(new DidRange(str));
									}
								}
							else
								{
								myLR.add(myRange);
								}
								
							didRanges.addAll(myLR);
							}
						}
					else if(tab[j][0].equals("setting"))
						{
						settings.add(new OfficeSetting(
								UsefulMethod.getItemByName("targetname", tabE.get(j)),
								UsefulMethod.getItemByName("value", tabE.get(j))
								));
						}
					}
				
				myOfficeList.add(new Office(
						UsefulMethod.getItemByName("name", tab),
						UsefulMethod.getItemByName("templatename", tab),
						UsefulMethod.getItemByName("fullname", tab),
						UsefulMethod.getItemByName("teamname", tab),
						UsefulMethod.getItemByName("audiobandwidth", tab),
						UsefulMethod.getItemByName("videobandwidth", tab),
						UsefulMethod.getItemByName("softkeytemplate", tab),
						UsefulMethod.getItemByName("callmanagergroup", tab),
						UsefulMethod.getItemByName("datetimegroup", tab),
						UsefulMethod.getItemByName("mohnumber", tab),
						UsefulMethod.getItemByName("internalprefix", tab),
						UsefulMethod.getItemByName("e164", tab),
						UsefulMethod.getItemByName("receptionnumber", tab),
						UsefulMethod.getItemByName("aargroup", tab),
						UsefulMethod.getItemByName("voicemailtemplate", tab),
						UsefulMethod.getItemByName("voicemailprofile", tab),
						UsefulMethod.getItemByName("voicenetwork", tab),
						UsefulMethod.getItemByName("userlocal", tab),
						UsefulMethod.getItemByName("country", tab),
						didRanges,
						settings
						));
				}
			
			Variables.getLogger().debug("Office found :");
			for(Office o : myOfficeList)
				{
				Variables.getLogger().debug(o.getName());
				}
			
			return myOfficeList;
			}
		catch(FileNotFoundException fnfexc)
			{
			throw new FileNotFoundException("The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			throw new Exception("ERROR with the "+fileName+" file : "+exc.getMessage());
			}
		}
	
	/**
	 * Used to init the substitute list
	 * @throws Exception 
	 */
	public static ArrayList<ValueMatcher> initSubstituteList(ArrayList<String[][]> list) throws Exception
		{
		ArrayList<ValueMatcher> substituteList = new ArrayList<ValueMatcher>();
		
		for(String[][] tabS : list)
			{
			substituteList.add(new ValueMatcher(
					UsefulMethod.getItemByName("match", tabS),
					UsefulMethod.getItemByName("replaceby", tabS),
					UsefulMethod.convertStringToSubstituteType(UsefulMethod.getItemByName("type", tabS))));
			}
		
		return substituteList;
		}
	
	/**
	 * Used to init the userCreationProfile list
	 * @throws Exception 
	 */
	public static ArrayList<UserCreationProfile> initUserCreationprofileList() throws Exception
		{
		ArrayList<UserCreationProfile> ucpList = new ArrayList<UserCreationProfile>();
		
		ArrayList<String> listParams = new ArrayList<String>();
		listParams.add("profiles");
		listParams.add("profile");
		
		ArrayList<ArrayList<String[][]>> extList = xMLGear.getResultListTabExt(UsefulMethod.getFlatFileContent(Variables.getUserCreationProfileFileName()), listParams);
		ArrayList<String[][]> sdList = xMLGear.getResultListTab(UsefulMethod.getFlatFileContent(Variables.getUserCreationProfileFileName()), listParams);
		
		for(int i=0; i<sdList.size(); i++)
			{
			String ProfileName = UsefulMethod.getItemByName("name", sdList.get(i));
			ArrayList<UserTemplate> utList = new ArrayList<UserTemplate>();
			
			for(int j=1; j<sdList.get(i).length; j++)
				{
				utList.add(new UserTemplate(itemType.valueOf(sdList.get(i)[j][0]),
						UsefulMethod.getItemByName("target", extList.get(i).get(j)),
						actionType.valueOf(UsefulMethod.getItemByName("action", extList.get(i).get(j)))
						));
				}
			ucpList.add(new UserCreationProfile(ProfileName, utList));
			}
		
		return ucpList;
		}
	
	/*******
	 * Used to initialize the used number list by asking the CUCM
	 */
	public static UsedItemList initNumberList(String range) throws Exception
		{
		try
			{
			Variables.getLogger().debug("Initialisation of UsedNumberList");
			ArrayList<String> myUsedNumberList = new ArrayList<String>();
			
			String[] tab = range.split(":");
			String firstNumber = tab[0];
			String lastNumber = tab[1];
			
			List<Object> SQLResp = SimpleRequest.doSQLQuery("select dnorpattern from numplan where dnorpattern between '"+firstNumber+"' and '"+lastNumber+"'");
			
			for(Object o : SQLResp)
				{
				Element rowElement = (Element) o;
				NodeList list = rowElement.getChildNodes();
				
				for(int i = 0; i< list.getLength(); i++)
					{
					if(list.item(i).getNodeName().equals("dnorpattern"))myUsedNumberList.add(list.item(i).getTextContent());
					}
				}
			
			return new UsedItemList(range, myUsedNumberList);
			}
		catch(Exception e)
			{
			throw new Exception("Error while initializing the used number list : "+e.getMessage());
			}
		}
	
	/**
	 * Used to initialize the used userID list
	 * @throws Exception 
	 */
	public static UsedItemList initUserIDList(String prefix) throws Exception
		{
		try
			{
			ArrayList<String> usedUserIdList = new ArrayList<String>();
			
			List<Object> SQLResp = SimpleRequest.doSQLQuery("select userid from enduser where userid like '"+prefix+"%'");
			
			for(Object o : SQLResp)
				{
				Element rowElement = (Element) o;
				NodeList list = rowElement.getChildNodes();
				
				for(int i = 0; i< list.getLength(); i++)
					{
					if(list.item(i).getNodeName().equals("userid"))usedUserIdList.add(list.item(i).getTextContent());
					}
				}
			
			return new UsedItemList(prefix, usedUserIdList);
			}
		catch(Exception e)
			{
			throw new Exception("Error while initializing the used userID list : "+e.getMessage());
			}
		}
	
	/**
	 * Used to convert a String into SubstituteType
	 * @throws Exception 
	 */
	public static SubstituteType convertStringToSubstituteType(String type) throws Exception
		{
		if(type.equals("phone"))
			{
			return SubstituteType.phone;
			}
		else if(type.equals("pbt"))
			{
			return SubstituteType.pbt;
			}
		else if(type.equals("css"))
			{
			return SubstituteType.css;
			}
		else if(type.equals("profile"))
			{
			return SubstituteType.profile;
			}
		else if(type.equals("misc"))
			{
			return SubstituteType.misc;
			}
		else
			{
			throw new Exception("Substitute type not found");
			}
		}
	
	
	/**
	 * Method which convert a string into cucmAXLVersion
	 */
	public static cucmAXLVersion convertStringToCUCMAXLVersion(String version)
		{
		if(version.contains("105"))
			{
			return cucmAXLVersion.version105;
			}
		else if(version.contains("110"))
			{
			return cucmAXLVersion.version110;
			}
		else if(version.contains("115"))
			{
			return cucmAXLVersion.version115;
			}
		else if(version.contains("120"))
			{
			return cucmAXLVersion.version120;
			}
		else if(version.contains("125"))
			{
			return cucmAXLVersion.version125;
			}
		else
			{
			return cucmAXLVersion.version105;
			}
		}
	
	/**
	 * Method which convert a string into UCCXRESTVersion
	 */
	public static UCCXRESTVersion convertStringToUCCXRESTVersion(String version)
		{
		if(version.contains("105"))
			{
			return UCCXRESTVersion.version105;
			}
		else if(version.contains("110"))
			{
			return UCCXRESTVersion.version110;
			}
		else if(version.contains("115"))
			{
			return UCCXRESTVersion.version115;
			}
		else if(version.contains("120"))
			{
			return UCCXRESTVersion.version120;
			}
		else if(version.contains("125"))
			{
			return UCCXRESTVersion.version125;
			}
		else
			{
			return UCCXRESTVersion.version105;
			}
		}
	
	
	/**************
	 * Method aims to get a template item value by giving its name
	 * @throws Exception 
	 *************/
	public static String getItemByName(String name, String[][] itemDetails) throws Exception
		{
		for(int i=0; i<itemDetails.length; i++)
			{
			if(itemDetails[i][0].equalsIgnoreCase(name))
				{
				return itemDetails[i][1];
				}
			}
		//throw new Exception("Item not found : "+name);
		Variables.getLogger().debug("Item not found : "+name);
		return "";
		}
	
	/**************
	 * Method aims to get a template item attribute value by giving its name
	 * @throws Exception 
	 *************/
	public static String getAttributeItemByName(String name, String[][] itemDetails) throws Exception
		{
		for(int i=0; i<itemDetails.length; i++)
			{
			if(itemDetails[i][0].equals(name))
				{
				return itemDetails[i][2];
				}
			}
		//throw new Exception("Item not found : "+name);
		Variables.getLogger().debug("Item not found : "+name);
		return "";
		}
	
	/**********************************************************
	 * Method used to disable security in order to accept any
	 * certificate without trusting it
	 */
	public static void disableSecurity()
		{
		try
        	{
            X509TrustManager xtm = new HttpsTrustManager();
            TrustManager[] mytm = { xtm };
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, mytm, null);
            SSLSocketFactory sf = ctx.getSocketFactory();

            HttpsURLConnection.setDefaultSSLSocketFactory(sf);
            
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
            	{
                public boolean verify(String hostname, SSLSession session)
                	{
                    return true;
                	}
            	}
            );
        	}
        catch (Exception e)
        	{
            e.printStackTrace();
        	}
		}
	
	
	
	/********************************************
	 * Method used to init the class eMailsender
	 * @throws Exception 
	 ********************************************/
	public synchronized static void initEMailServer() throws Exception
		{
		Variables.seteMSender(new eMailSender(UsefulMethod.getTargetOption("smtpemailport"),
				 UsefulMethod.getTargetOption("smtpemailprotocol"),
				 UsefulMethod.getTargetOption("smtpemailserver"),
				 UsefulMethod.getTargetOption("smtpemail"),
				 UsefulMethod.getTargetOption("smtpemailpassword")));
		}
	
	/**
	 * Method used to send an email to the admin group
	 */
	public synchronized static void sendEmailToTheAdminList(String desc, String siteName, String content)
		{
		try
			{
			String adminEmails = UsefulMethod.getTargetOption("smtpemailadmin");
			
			String subject = LanguageManagement.getString("emailreportsubject")+siteName;
			String eMailDesc = desc+" - "+siteName;
			
			if(adminEmails.contains(";"))
				{
				//There are many emails to send
				String[] adminList = adminEmails.split(";");
				for(String s : adminList)
					{
					Variables.geteMSender().send(s,
							subject,
							content,
							eMailDesc);
					}
				}
			else
				{
				//There is only one email to send
				Variables.geteMSender().send(adminEmails,
						subject,
						content,
						eMailDesc);
				}
			
			JOptionPane.showMessageDialog(null,LanguageManagement.getString("emailsentsuccess"),"",JOptionPane.INFORMATION_MESSAGE);
			}
		catch (Exception e)
			{
			e.printStackTrace();
			Variables.getLogger().error("",e);
			Variables.getLogger().error("Failed to send emails to the admin list : "+e.getMessage());
			}
		}
	
	
	/**
	 * Method used to find the file name from a file path
	 * For intance :
	 * C:\JAVAELILEX\YUZA\Maquette_CNAF_TA_FichierCollecteDonneesTelephonie_v1.7_mac.xls
	 * gives :
	 * Maquette_CNAF_TA_FichierCollecteDonneesTelephonie_v1.7_mac.xls
	 */
	public static String extractFileName(String fullFilePath)
		{
		String[] tab =  fullFilePath.split("\\\\");
		return tab[tab.length-1];
		}
	
	/***
	 * Method used to get the AXL version from the CUCM
	 * We contact the CUCM using a very basic request and therefore get the version
	 * @throws Exception 
	 */
	public static cucmAXLVersion getAXLVersionFromTheCUCM() throws Exception
		{
		/**
		 * In this method version we just read the version from the configuration file
		 * This has to be improved to match the method description
		 **/
		cucmAXLVersion AXLVersion;
		
		AXLVersion = UsefulMethod.convertStringToCUCMAXLVersion("version"+getTargetOption("axlversion"));
		
		return AXLVersion;
		}
	
	/**
	 * Methos used to check if a value is null or empty
	 */
	public static boolean isNotEmpty(String s)
		{
		if((s == null) || (s.equals("")))
			{
			return false;
			}
		else
			{
			return true;
			}
		}
	
	/**
	 * Methos used to check if a value is null or empty
	 */
	public static boolean isNotEmpty(ArrayList<String> as)
		{
		if((as == null) || (as.size() == 0))
			{
			return false;
			}
		else
			{
			return true;
			}
		}
	
	/******
	 * Method used to determine if the fault description means
	 * that the item was not found or something else
	 * If it is not found we return true
	 * For any other reason we return false
	 * @param faultDesc
	 * @return
	 */
	public static boolean itemNotFound(String faultDesc)
		{
		ArrayList<String> faultDescList = new ArrayList<String>();
		faultDescList.add("was not found");
		faultDescList.add("Not Found");
		//Add some more reason
		
		for(String s : faultDescList)
			{
			if(faultDesc.contains(s))return true;
			}
		
		return false;
		}
	
	/**
	 * Method used to read the user file
	 */
	private static ArrayList<String[][]> readUserFile(String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			file = xMLReader.fileRead("./"+fileName);
			
			listParams.add("users");
			listParams.add("user");
			return xMLGear.getResultListTab(file, listParams);
			}
		catch(FileNotFoundException fnfexc)
			{
			fnfexc.printStackTrace();
			throw new FileNotFoundException("The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the file : "+fileName+" : "+exc.getMessage());
			}
		}
	
	
	/**
	 * Used to get a formatted URL
	 */
	public static String getFormattedURL(String url, String ID)
		{
		Variables.getLogger().debug("URL before : "+url);
		String s = url.replace("*", ID);
		Variables.getLogger().debug("URL after : "+s);
		return s;
		}
	
	
	/************
	 * Method used to read an advanced configuration file
	 * @throws Exception 
	 */
	public static ArrayList<ArrayList<String[][]>> readExtFile(String param, String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			Variables.getLogger().info("Reading of the file : "+fileName);
			file = xMLReader.fileRead(Variables.getMainConfigFileDirectory()+"/"+fileName);
			
			listParams.add(param);
			return xMLGear.getResultListTabExt(file, listParams);
			}
		catch(FileNotFoundException fnfexc)
			{
			fnfexc.printStackTrace();
			throw new FileNotFoundException("The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the file : "+exc.getMessage());
			}
		}
	
	/**********
	 * Method used to convert a value from the collection file
	 * into a value expected by the CUCM using the substitute list
	 */
	public static String findSubstitute(SubstituteType type, String toConvert) throws Exception
		{
		for(ValueMatcher vm : Variables.getSubstituteList())
			{
			if(vm.getType().equals(type))
				{
				if(vm.getCollectionName().equals(toConvert))
					{
					Variables.getLogger().debug(toConvert+" converted into : "+vm.getConvertName());
					return vm.getConvertName();
					}
				}
			}
		throw new Exception("Impossible to convert \""+toConvert+"\"");
		}
	
	/*****
	 * Method used to find the substitute corresponding to 
	 * the provided value
	 * @throws Exception 
	 */
	public static String findSubstitute(String s, SubstituteType type) throws Exception
		{
		for(ValueMatcher vm : Variables.getSubstituteList())
			{
			if(vm.getType().equals(type))
				{
				if(vm.getCollectionName().equals(s))
					{
					return vm.getConvertName();
					}
				}
			}
		
		Variables.getLogger().debug("No substitute of type \""+type.name()+"\" have been found for the string : "+s);
		return s;
		}
	
	/******
	 * Used to convert itemType values into more verbose ones
	 */
	public static String convertItemTypeToVerbose(itemType type)
		{
		switch(type)
			{
			case location:return "Location";
			case region:return "Region";
			case srstreference:return "SRST Reference";
			case devicepool:return "Device Pool";
			case commondeviceconfig:return "Common Device Configuration";
			case conferencebridge:return "Conference Bridge";
			case mediaresourcegroup:return "Media Resource Group";
			case mediaresourcegrouplist:return "Media Resource Group List";
			case partition:return "Partition";
			case callingsearchspace:return "Calling Search Space";
			case trunksip:return "SIP Trunk";
			case vg:return "Voice Gateway";
			case routegroup:return "Route Group";
			case translationpattern:return "Translation Pattern";
			case callingpartytransformationpattern:return "Calling Party Transformation Pattern";
			case calledpartytransformationpattern:return "Called Party Transformation Pattern";
			case physicallocation:return "Physical Location";
			case devicemobilityinfo:return "Device Mobility Info";
			case devicemobilitygroup:return "Device Mobility group";
			case datetimesetting:return "Date Time Settings";
			case callmanagergroup:return "Call Manager group";
			case phone:return "Phone";
			case udp:return "User device profile";
			case user:return "End User";
			case line:return "Line";
			case voicemail:return "Voicemail";
			case telecasterservice:return "Phone Service";
			case siptrunksecurityprofile:return "Sip Trunk Security Profile";
			case sipprofile:return "SIP profile";
			case phonetemplatename:return "Phone Button Template";
			case linegroup:return "Line Group";
			case huntlist:return "Hunt List";
			case huntpilot:return "Hunt Pilot";
			case callpickupgroup:return "Call Pickup Group";
			case udplogin:return "UDP Login";
			case aargroup:return "AAR Group";
			case usercontrolgroup:return "Access Control Group";
			case analog:return "Analog Port";
			case gateway:return "Gateway";
			default:return type.name();
			}
		}
	
	/************
	 * Method used to read a simple configuration file
	 * @throws Exception 
	 */
	public static ArrayList<String> readFile(String param, String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			Variables.getLogger().info("Reading the file : "+fileName);
			file = getFlatFileContent(fileName);
			
			listParams.add(param);
			
			return xMLGear.getResultList(file, listParams);
			}
		catch(FileNotFoundException fnfexc)
			{
			fnfexc.printStackTrace();
			throw new FileNotFoundException("ERROR : The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the "+fileName+" file : "+exc.getMessage());
			}
		}
	
	/************
	 * Method used to read a configuration file
	 * @throws Exception 
	 */
	public static ArrayList<String[][]> readFileTab(String param, String fileName) throws Exception
		{
		String file;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			Variables.getLogger().info("Reading of the "+param+" file : "+fileName);
			file = getFlatFileContent(fileName);
			
			listParams.add(param);
			return xMLGear.getResultListTab(file, listParams);
			}
		catch(FileNotFoundException fnfexc)
			{
			fnfexc.printStackTrace();
			throw new FileNotFoundException("The "+fileName+" file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			Variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the "+param+" file : "+exc.getMessage());
			}
		}
	
	/**
	 * Method used to convert a range of DID number into
	 * An arraylist of regex
	 * @param first
	 * @param end
	 * @return
	 */
	public static ArrayList<String> getRegexFromRange(String firstNumber, String lastNumber)
		{
		int start = Integer.parseInt(firstNumber);
		int end = Integer.parseInt(lastNumber);
		
		final LinkedList<Range> left = leftBounds(start, end);
		final Range lastLeft = left.removeLast();
		final LinkedList<Range> right = rightBounds(lastLeft.getStart(), end);
		final Range firstRight = right.removeFirst();

		LinkedList<Range> merged = new LinkedList<>();
		merged.addAll(left);
		if (!lastLeft.overlaps(firstRight))
			{
			merged.add(lastLeft);
			merged.add(firstRight);
			}
		else
			{
			merged.add(Range.join(lastLeft, firstRight));
			}
		merged.addAll(right);

		//merged.stream().map(Range::toRegex).forEach(System.out::println);
		
		ArrayList<String> list = new ArrayList<String>();
		for(Range r : merged)
			{
			list.add(r.toRegex());
			}
		
		return list;
		}
	
	/**
	 * Used to by getRegexFromRange
	 * @param start
	 * @param end
	 * @return
	 */
	private static LinkedList<Range> leftBounds(int start, int end)
		{
	    final LinkedList<Range> result = new LinkedList<>();
	    while (start < end)
	    	{
	        final Range range = Range.fromStart(start);
	        result.add(range);
	        start = range.getEnd()+1;
	    	}
	    return result;
		}

	/**
	 * Used to by getRegexFromRange
	 * @param start
	 * @param end
	 * @return
	 */
	private static LinkedList<Range> rightBounds(int start, int end)
		{
	    final LinkedList<Range> result = new LinkedList<>();
	    while (start < end)
	    	{
	        final Range range = Range.fromEnd(end);
	        result.add(range);
	        end = range.getStart()-1;
	    	}
	    Collections.reverse(result);
	    return result;
		}
	
	/**
	 *  Look for the corresponding office
	 *  Will try to compare to the office fullname and associated Team name
	 * @throws Exception 
	 */
	public static Office getOffice(String name) throws Exception
		{
		for(Office o : Variables.getOfficeList())
			{
			if((o.getFullname().toLowerCase().equals(name.toLowerCase())) || (o.getTeam().toLowerCase().equals(name.toLowerCase())))
				{
				Variables.getLogger().debug("Office found : "+o.getFullname());
				return o;
				}
			}
		
		throw new Exception("Office not found : "+name);
		}
	
	/**
	 *  Get an office in the office list
	 * @throws Exception 
	 */
	public static Task getTask(String taskID) throws Exception
		{
		for(Task t : Variables.getTaskList())
			{
			if(t.getTaskID().equals(taskID)) return t;
			}
		
		throw new Exception("Task not found : "+taskID);
		}
	
	/**
	 * Search for a User Creation Profile in the User Creation Profile list
	 * @throws Exception
	 */
	public static UserCreationProfile getUserCreationProfile(String UCPName) throws Exception
		{
		for(UserCreationProfile ucp : Variables.getUserCreationProfileList())
			{
			if(ucp.getName().equals(UCPName)) return ucp;
			}
		
		throw new Exception("User Creation Profile not found : "+UCPName);
		}
	
	/**
	 * Convert string status to agentStatus
	 */
	public static agentStatus convertStringToAgentStatus(String status)
		{
		try
			{
			return agentStatus.valueOf(status);
			}
		catch (Exception e)
			{
			Variables.getLogger().error("Failed to convert the following status : "+status+" returning the default status",e);
			}
		return agentStatus.UNKNOWN;
		}
	
	public static int convertAgentTypeToInt(AgentType type)
		{
		if(type.equals(AgentType.agent))return 1;
		else return 2;
		}
	
	/**
	 * Used to clean what needs to be after a task ends
	 */
	public static void clean()
		{
		Variables.setUuidList(new ArrayList<storedUUID>());//We clean the UUID list
		Variables.getLogger().info("UUID list cleared");
		Variables.setUsedUserIdList(null);//We clean the UsedUserIDList
		Variables.getLogger().info("Used userID list cleared");
		Variables.setUsedNumberList(null);//We clean the UsedNumberList
		Variables.getLogger().info("Used number list cleared");
		}
	
	/**
	 * Used to retrieve the NumberList to use
	 * @throws Exception 
	 */
	public static UsedItemList getUsedNumberList(String range) throws Exception
		{
		/**
		 * We check if the list must be initialized
		 */
		if(Variables.getUsedNumberList() == null)
			{
			ArrayList<UsedItemList> list = new ArrayList<UsedItemList>();
			UsedItemList uil = UsefulMethod.initNumberList(range);
			list.add(uil);
			Variables.setUsedNumberList(list);
			return uil;
			}
		
		for(UsedItemList uil : Variables.getUsedNumberList())
			{
			if(uil.getPattern().equals(range))
				{
				return uil;
				}
			}
		
		/**
		 * No list were found for the given range so we create one
		 */
		UsedItemList uil = UsefulMethod.initNumberList(range);
		Variables.getUsedNumberList().add(uil);
		return uil;
		}
	
	/**
	 * Used to retrieve the UserID list to use
	 * @throws Exception 
	 */
	public static UsedItemList getUsedUserIDList(String prefix) throws Exception
		{
		/**
		 * We check if the list must be initialized
		 */
		if(Variables.getUsedUserIdList() == null)
			{
			ArrayList<UsedItemList> list = new ArrayList<UsedItemList>();
			UsedItemList uil = UsefulMethod.initUserIDList(prefix);
			list.add(uil);
			Variables.setUsedUserIdList(list);
			return uil;
			}
		
		for(UsedItemList uil : Variables.getUsedUserIdList())
			{
			if(uil.getPattern().equals(prefix))
				{
				return uil;
				}
			}
		
		/**
		 * No list were found for the given prefix so we create one
		 */
		UsedItemList uil = UsefulMethod.initUserIDList(prefix);
		Variables.getUsedUserIdList().add(uil);
		return uil;
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

