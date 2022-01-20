package com.alex.toad.utils;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.alex.toad.cucm.user.items.User;
import com.alex.toad.cucm.user.misc.TemplateUserReader;
import com.alex.toad.cucm.user.misc.UserCreationProfile;
import com.alex.toad.misc.ItemToInject;
import com.alex.toad.misc.Office;
import com.alex.toad.misc.Task;
import com.alex.toad.misc.ValueMatcher;
import com.alex.toad.misc.storedUUID;
import com.alex.toad.webserver.SecurityToken;
import com.alex.toad.webserver.WebListenerManager;




/**********************************
 * Used to store static variables
 * 
 * @author RATEL Alexandre
 **********************************/
public class Variables
	{
	/**
	 * Variables
	 */
	
	/**	ENUM	**/
	/***
	 * itemType :
	 * Is used to give a type to the request ready to be injected
	 * This way we can manage or sort them more easily
	 * 
	 * The order is important here, indeed, it will define later
	 * how the items are injected
	 */
	public enum itemType
		{
		location,
		region,
		srstreference,
		devicepool,
		commondeviceconfig,
		commonPhoneConfig,
		securityProfile,
		conferencebridge,
		mediaresourcegroup,
		mediaresourcegrouplist,
		partition,
		callingsearchspace,
		trunksip,
		vg,
		routegroup,
		routelist,
		translationpattern,
		callingpartytransformationpattern,
		calledpartytransformationpattern,
		physicallocation,
		devicemobilityinfo,
		devicemobilitygroup,
		datetimesetting,
		callmanagergroup,
		phone,
		udp,
		user,
		line,
		voicemail,
		telecasterservice,
		siptrunksecurityprofile,
		sipprofile,
		phonetemplatename,
		linegroup,
		huntlist,
		huntpilot,
		callpickupgroup,
		udplogin,
		aargroup,
		usercontrolgroup,
		analog,
		gateway,
		sqlRequest,
		associateanalog,
		userlocal,
		softkeytemplate,
		agent,//UCCX
		team,//UCCX
		skill,//UCCX
		unknown
		};
	
	public enum cucmAXLVersion
		{
		version80,
		version85,
		version90,
		version91,
		version100,
		version105,
		version110,
		version115,
		version120,
		version125
		};
		
	public enum UCCXRESTVersion
		{
		version80,
		version85,
		version90,
		version91,
		version100,
		version105,
		version110,
		version115,
		version120,
		version125
		};
	
	/********************************************
	 * actionType :
	 * Is used to set the type of action is going to do a 
	 ***************************************/
	public enum actionType
		{
		inject,
		delete,
		update
		};
	
	/********************************************
	 * statusType :
	 * Is used to set the status of a request as followed :
	 * - init : the request has to be built
	 * - waiting : The request is ready to be injected. We gonna reach this status after the request has been built or has been deleted
	 * - processing : The injection or the deletion of the request is currently under progress
	 * - disabled : The request has not to be injected
	 * - injected : The request has been injected with success
	 * - error : Something went wrong and an exception has been thrown
	 ***************************************/
	public enum statusType
		{
		injected,
		error,
		processing,
		waiting,
		disabled,
		init,
		deleted,
		updated
		};
		
	/********************************************
	 * SDType :
	 * Is used to set the type of the Speed dial :
	 * - simple SD
	 * - BLF
	 ***************************************/
	public enum sdType
		{
		sd,
		blf,
		};
		
	/********************************************
	 * substituteType :
	 * Is used to know what is the current data source to use
	 ***************************************/
	public enum SubstituteType
		{
		phone,
		pbt,
		css,
		profile,
		misc
		};
		
	/********************************************
	 * userSource :
	 * CUCM Corporate directory user source
	 ***************************************/
	public enum UserSource
		{
		external,//LDAP for instance
		internal//CUCM Internal user
		};
	
	/********************************************
	 * multipleRequestType :
	 * Used to specify the multiple request type
	 ***************************************/
	public enum multipleRequestType
		{
		officedidall
		};
		
	/**	MISC	**/
	private static String softwareName;
	private static String softwareVersion;
	private static cucmAXLVersion CUCMVersion;
	private static UCCXRESTVersion UCCXVersion;
	private static Logger logger;
	private static ArrayList<String> country;
	private static ArrayList<Office> OfficeList;
	private static eMailSender eMSender;
	private static String mainConfigFileDirectory;
	private static ArrayList<String[][]> mainConfig;
	private static String configFileName;
	private static String userFileName;
	private static String matcherFileName;
	private static String userTemplateFileName;
	private static String officeListFileName;
	private static String substitutesFileName;
	private static String userCreationProfileFileName;
	private static ArrayList<String> matcherList;
	private static ArrayList<ValueMatcher> substituteList;
	private static ArrayList<storedUUID> uuidList;
	private static boolean CUCMReachable;
	private static boolean advancedLogs;
	private static boolean pause;
	private static UserSource userSource;
	private static ArrayList<UserCreationProfile> userCreationProfileList;
	private static String logFileName;
	private static ArrayList<Task> taskList;
	
	/** Templates **/
	private static ArrayList<ItemToInject> userTemplateList;//User
	
	/**	Language management	**/
	public enum language{english,french};
	private static String languageFileName;
	private static ArrayList<ArrayList<String[][]>> languageContentList;
	
	/**	AXL	**/
	private static com.cisco.axlapiservice10.AXLPort AXLConnectionToCUCMV105;//Connection to CUCM version 105
	
	/** REST **/
	
	
	
	/** Web Management **/
	private static WebListenerManager webServer;
	private static ArrayList<SecurityToken> securityTokenList;
	
	/**************
     * Constructor
     **************/
	public Variables()
		{
		configFileName = "configFile.xml";
		userFileName = "userFile.xml";
		languageFileName = "languages.xml";
		mainConfigFileDirectory = ".";
		country = UsefulMethod.initCountryList();
		uuidList = new ArrayList<storedUUID>();
		matcherFileName = "matchers.xml";
		userTemplateFileName = "templateUser.xml";
		officeListFileName = "officeList.xml";
		substitutesFileName = "substitutes.xml";
		userCreationProfileFileName = "userCreationProfiles.xml";
		userSource = UserSource.internal;
		}

	public static String getSoftwareName()
		{
		return softwareName;
		}

	public static void setSoftwareName(String softwareName)
		{
		Variables.softwareName = softwareName;
		}

	public static String getSoftwareVersion()
		{
		return softwareVersion;
		}

	public static void setSoftwareVersion(String softwareVersion)
		{
		Variables.softwareVersion = softwareVersion;
		}

	public static cucmAXLVersion getCUCMVersion()
		{
		if(CUCMVersion == null)
			{
			//It has to be initiated
			try
				{
				CUCMVersion = UsefulMethod.convertStringToCUCMAXLVersion(UsefulMethod.getTargetOption("axlversion"));
				Variables.getLogger().info("CUCM version : "+Variables.getCUCMVersion());
				}
			catch(Exception e)
				{
				getLogger().debug("The AXL version couldn't be parsed. We will use the default version instead", e);
				CUCMVersion = cucmAXLVersion.version105;
				}
			}
		
		return CUCMVersion;
		}
	
	public static UCCXRESTVersion getUCCXVersion()
		{
		if(UCCXVersion == null)
			{
			//It has to be initiated
			try
				{
				UCCXVersion = UsefulMethod.convertStringToUCCXRESTVersion(UsefulMethod.getTargetOption("uccxversion"));
				Variables.getLogger().info("UCCX version : "+Variables.getUCCXVersion());
				}
			catch(Exception e)
				{
				getLogger().debug("The UCCX version couldn't be parsed. We will use the default version instead", e);
				UCCXVersion = UCCXRESTVersion.version105;
				}
			}
		
		return UCCXVersion;
		}
	
	public static void setUCCXVersion(UCCXRESTVersion uCCXVersion)
		{
		UCCXVersion = uCCXVersion;
		}

	public static void setCUCMVersion(cucmAXLVersion cUCMVersion)
		{
		CUCMVersion = cUCMVersion;
		}

	public synchronized static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		Variables.logger = logger;
		}

	public synchronized static eMailSender geteMSender()
		{
		return eMSender;
		}

	public static void seteMSender(eMailSender eMSender)
		{
		Variables.eMSender = eMSender;
		}

	public static String getMainConfigFileDirectory()
		{
		return mainConfigFileDirectory;
		}

	public static void setMainConfigFileDirectory(String mainConfigFileDirectory)
		{
		Variables.mainConfigFileDirectory = mainConfigFileDirectory;
		}

	public static String getConfigFileName()
		{
		return configFileName;
		}

	public static void setConfigFileName(String configFileName)
		{
		Variables.configFileName = configFileName;
		}

	public static String getUserFileName()
		{
		return userFileName;
		}

	public static void setUserFileName(String userFileName)
		{
		Variables.userFileName = userFileName;
		}

	public static boolean isCUCMReachable()
		{
		return CUCMReachable;
		}

	public static void setCUCMReachable(boolean cUCMReachable)
		{
		CUCMReachable = cUCMReachable;
		}

	public static String getLanguageFileName()
		{
		return languageFileName;
		}

	public static void setLanguageFileName(String languageFileName)
		{
		Variables.languageFileName = languageFileName;
		}

	public static ArrayList<ArrayList<String[][]>> getLanguageContentList() throws Exception
		{
		if(languageContentList == null)
			{
			Variables.getLogger().debug("Initialisation of languageContentList");
			Variables.setLanguageContentList(UsefulMethod.readExtFile("language", Variables.getLanguageFileName()));
			}
		
		return languageContentList;
		}

	public static void setLanguageContentList(ArrayList<ArrayList<String[][]>> languageContentList)
		{
		Variables.languageContentList = languageContentList;
		}

	public static ArrayList<String[][]> getMainConfig()
		{
		return mainConfig;
		}

	public static void setMainConfig(ArrayList<String[][]> mainConfig)
		{
		Variables.mainConfig = mainConfig;
		}

	public static ArrayList<storedUUID> getUuidList()
		{
		return uuidList;
		}

	public static void setUuidList(ArrayList<storedUUID> uuidList)
		{
		Variables.uuidList = uuidList;
		}

	public static com.cisco.axlapiservice10.AXLPort getAXLConnectionToCUCMV105() throws Exception
		{
		if(AXLConnectionToCUCMV105 == null)
			{
			UsefulMethod.initAXLConnectionToCUCM();
			}
		return AXLConnectionToCUCMV105;
		}

	public static void setAXLConnectionToCUCMV105(com.cisco.axlapiservice10.AXLPort aXLConnectionToCUCMV105)
		{
		AXLConnectionToCUCMV105 = aXLConnectionToCUCMV105;
		}

	public static boolean isAdvancedLogs()
		{
		return advancedLogs;
		}

	public static void setAdvancedLogs(boolean advancedLogs)
		{
		Variables.advancedLogs = advancedLogs;
		}

	public static WebListenerManager getWebServer()
		{
		return webServer;
		}

	public static void setWebServer(WebListenerManager webServer)
		{
		Variables.webServer = webServer;
		}

	public static ArrayList<String> getCountry()
		{
		return country;
		}

	public static void setCountry(ArrayList<String> country)
		{
		Variables.country = country;
		}

	public static ArrayList<Office> getOfficeList() throws Exception
		{
		if(OfficeList == null)
			{
			Variables.getLogger().debug("Initialisation of OfficeList");
			Variables.setOfficeList(UsefulMethod.readOfficeFile(Variables.getOfficeListFileName()));
			}
		
		return OfficeList;
		}

	public static void setOfficeList(ArrayList<Office> officeList)
		{
		OfficeList = officeList;
		}

	public static String getMatcherFileName()
		{
		return matcherFileName;
		}

	public static void setMatcherFileName(String matcherFileName)
		{
		Variables.matcherFileName = matcherFileName;
		}

	public static String getUserTemplateFileName()
		{
		return userTemplateFileName;
		}

	public static void setUserTemplateFileName(String userTemplateFileName)
		{
		Variables.userTemplateFileName = userTemplateFileName;
		}

	public static String getOfficeListFileName()
		{
		return officeListFileName;
		}

	public static void setOfficeListFileName(String officeListFileName)
		{
		Variables.officeListFileName = officeListFileName;
		}

	public static String getSubstitutesFileName()
		{
		return substitutesFileName;
		}

	public static void setSubstitutesFileName(String substitutesFileName)
		{
		Variables.substitutesFileName = substitutesFileName;
		}

	public static String getUserCreationProfileFileName()
		{
		return userCreationProfileFileName;
		}

	public static void setUserCreationProfileFileName(
			String userCreationProfileFileName)
		{
		Variables.userCreationProfileFileName = userCreationProfileFileName;
		}

	public static ArrayList<String> getMatcherList() throws Exception
		{
		if(matcherList == null)
			{
			Variables.getLogger().debug("Initialisation of matcherList");
			Variables.setMatcherList(UsefulMethod.readFile("matchers", Variables.getMatcherFileName()));
			}
		return matcherList;
		}

	public static void setMatcherList(ArrayList<String> matcherList)
		{
		Variables.matcherList = matcherList;
		}

	public static ArrayList<ValueMatcher> getSubstituteList() throws Exception
		{
		if(substituteList == null)
			{
			Variables.getLogger().debug("Initialisation of substituteList");
			Variables.setSubstituteList(UsefulMethod.initSubstituteList(UsefulMethod.readFileTab("substitute", Variables.getSubstitutesFileName())));
			}
		
		return substituteList;
		}

	public static void setSubstituteList(ArrayList<ValueMatcher> substituteList)
		{
		Variables.substituteList = substituteList;
		}

	public static boolean isPause()
		{
		return pause;
		}

	public static void setPause(boolean pause)
		{
		Variables.pause = pause;
		}

	public static UserSource getUserSource()
		{
		return userSource;
		}

	public static void setUserSource(UserSource userSource)
		{
		Variables.userSource = userSource;
		}

	public static ArrayList<UserCreationProfile> getUserCreationProfileList() throws Exception
		{
		if(userCreationProfileList == null)
			{
			Variables.getLogger().debug("Initialisation of userCreationProfileList");
			Variables.setUserCreationProfileList(UsefulMethod.initUserCreationprofileList());
			}
		
		return userCreationProfileList;
		}

	public static void setUserCreationProfileList(
			ArrayList<UserCreationProfile> userCreationProfileList)
		{
		Variables.userCreationProfileList = userCreationProfileList;
		}

	public static ArrayList<ItemToInject> getUserTemplateList() throws Exception
		{
		if(userTemplateList == null)
			{
			Variables.getLogger().debug("Initialisation of userTemplateList");
			Variables.setUserTemplateList(TemplateUserReader.readUserTemplate());
			}
		
		return userTemplateList;
		}

	public static void setUserTemplateList(ArrayList<ItemToInject> userTemplateList)
		{
		Variables.userTemplateList = userTemplateList;
		}

	public static String getLogFileName()
		{
		return logFileName;
		}

	public static void setLogFileName(String logFileName)
		{
		Variables.logFileName = logFileName;
		}

	public static ArrayList<SecurityToken> getSecurityTokenList()
		{
		return securityTokenList;
		}

	public static void setSecurityTokenList(
			ArrayList<SecurityToken> securityTokenList)
		{
		Variables.securityTokenList = securityTokenList;
		}

	public static ArrayList<Task> getTaskList()
		{
		if(taskList == null)taskList = new ArrayList<Task>();
		return taskList;
		}

	public static void setTaskList(ArrayList<Task> taskList)
		{
		Variables.taskList = taskList;
		}
	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}
