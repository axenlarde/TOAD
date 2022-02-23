package com.alex.toad.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.alex.toad.cucm.user.misc.UserError;
import com.alex.toad.misc.Correction.correctionType;
import com.alex.toad.utils.ClearFrenchString;
import com.alex.toad.utils.LanguageManagement;
import com.alex.toad.utils.UsefulMethod;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.SubstituteType;
import com.alex.toad.webserver.AgentData;


/**********************************
 * Class used to store static method used to work with the collection and template file
 * 
 * @author RATEL Alexandre
 **********************************/
public class CollectionTools
	{
	/****************************************
	 * Method used to apply the pattern
	 * @throws Exception 
	 ****************************************/
	public static String doRegex(String pattern, AgentData ad, Object obj, boolean emptyException) throws Exception
		{
		/**********
		 * Add here a special regex detection for too long value
		 * pattern "\\(.+\\)IfLongerThan\\d+\\(.+\\)"
		 * 
		 * BETA
		 ****/
		if(Pattern.matches("\\(.+\\)IfLongerThan\\d+\\(.+\\)", pattern))
			{
			Variables.getLogger().debug("TooLong value detection required");
			
			//Get the 2 values
			String normal="", tooLong="";
			int maxLength=0;
			
			Pattern first = Pattern.compile("\\(.+\\)If");
			Pattern last = Pattern.compile("\\d+\\(.+\\)");
			Pattern length = Pattern.compile("IfLongerThan\\d+");
			Matcher mFirst = first.matcher(pattern);
			Matcher mLast = last.matcher(pattern);
			Matcher mLength = length.matcher(pattern);
			
			if(mFirst.find())
				{
				normal = mFirst.group();
				normal = normal.replace("(", "").replace(")If", "");
				Variables.getLogger().debug("Normal pattern : \""+normal+"\"");
				}
			if(mLast.find())
				{
				tooLong = mLast.group();
				tooLong = tooLong.replace(")", "").replaceAll("\\d+\\(", "");
				Variables.getLogger().debug("TooLong pattern : \""+tooLong+"\"");
				}
			if(mLength.find())
				{
				String str = mLength.group();
				str = str.replace("IfLongerThan", "");
				maxLength = Integer.parseInt(str);
				Variables.getLogger().debug("Max length : \""+maxLength+"\"");
				}
			
			if((normal == null) || (normal.equals("")) ||
					(tooLong == null) || (tooLong.equals("")) ||
					(maxLength == 0))
				{
				throw new Exception("ERROR : The \"IfLongerThan\" regex returned a bad Result");
				}
			/********/
			
			Variables.getLogger().debug("We try the normal pattern");
			normal = dodoRegex(normal, ad, emptyException);
			if(normal.length() > maxLength)
				{
				Variables.getLogger().debug("The normal pattern is longer than "+maxLength+" : "+normal+" so we try the backup pattern");
				tooLong = dodoRegex(tooLong, ad, emptyException);
				
				if(tooLong.length() > maxLength)
					{
					//The corrected value is still too long so we add it as an error
					try
						{
						ItemToInject iti = (ItemToInject)obj;
						iti.addNewError(new UserError(LanguageManagement.getString("correctionalert")+" : "+normal+" > "+tooLong));
						Variables.getLogger().debug("New User error added for the following item : "+iti.getType()+" "+iti.getName());
						}
					catch (Exception e)
						{
						//The object is not an ItemToInject so we try with a BasicItem
						try
							{
							BasicItem bi = (BasicItem)obj;
							bi.addNewError(new UserError(LanguageManagement.getString("correctionalert")+" : "+normal+" > "+tooLong));
							Variables.getLogger().debug("New User error added to the error list");
							}
						catch (Exception exc)
							{
							Variables.getLogger().error("ERROR : The object is neither an \"ItemToInject\" nor a \"BasicItem\"", exc);
							Variables.getLogger().debug("Failed to add an error description to the error list");
							}
						}
					
					Variables.getLogger().debug(ad.getFirstName()+" "+ad.getLastName()+" : Even after the IfLongerThan regex the value is still longer than "+maxLength+" : "+tooLong);
					return tooLong;
					}
				else
					{
					//A correction was made so we add it in the correction list
					try
						{
						ItemToInject iti = (ItemToInject)obj;
						iti.addNewCorrection(new Correction(normal+" > "+tooLong, "", correctionType.tooLong, false));
						}
					catch (Exception e)
						{
						//The object is not an ItemToInject so we try with a BasicItem
						try
							{
							BasicItem bi = (BasicItem)obj;
							bi.addNewCorrection(new Correction(normal+" > "+tooLong, "", correctionType.tooLong, false));
							}
						catch (Exception exc)
							{
							Variables.getLogger().error("ERROR : The object is neither an \"ItemToInject\" nor a \"BasicItem\"", exc);
							Variables.getLogger().debug("Failed to add a correction description in the correction list");
							}
						}
					
					Variables.getLogger().debug(ad.getFirstName()+" "+ad.getLastName()+" : The value was longer than \""+maxLength+"\" so the \"too long pattern\" has been used instead. The result is : "+tooLong);
					return tooLong;
					}
				}
			else
				{
				Variables.getLogger().debug("Full value after regex : "+normal);
				return normal;
				}
			}
		else
			{
			return dodoRegex(pattern, ad, emptyException);
			}
		}
	
	
	/****************************************
	 * Method used to return the pattern
	 * @throws Exception 
	 ****************************************/
	private static String dodoRegex(String pat, AgentData ad, boolean emptyException) throws Exception
		{
		StringBuffer regex = new StringBuffer("");
		
		String[] param = getSplittedValue(pat, UsefulMethod.getTargetOption("splitter"));
		
		for(int i = 0; i<param.length; i++)
			{
			boolean match = false;
			
			if(Pattern.matches(".*agent\\..*", param[i]))
				{
				String result = ad.getString(param[i]);
				regex.append(applyRegex(result, param[i]));
				match = true;
				}
			else if(Pattern.matches(".*availableuseridindex.*", param[i]))
				{
				/**
				 * In this special case, we know that we have to consider the whole pattern so we use "pat" and not param[i]
				 */
				//We get the prefix and suffix
				String prefix = pat.substring(0,pat.indexOf("#availableuseridindex")-1);
				String suffix = pat.substring(pat.indexOf("#availableuseridindex"),pat.length()).replace("#availableuseridindex", "");
				suffix = suffix.substring(1, suffix.length());//To remove the first +
				
				//We resolve them in case of regex
				prefix = applyPattern(ad, prefix, null, false);
				suffix = applyPattern(ad, suffix, null, false);
				int userIDIndex = getAvailableUserIdIndex(prefix, suffix);
				Variables.getLogger().debug("Available index found : "+userIDIndex);
				regex.append(userIDIndex);
				match = true;
				
				/*
				String prefix = UsefulMethod.getTargetOption("agentidprefix");
				prefix = applyPattern(ad, prefix, null, false);
				String userID = getAvailableUserId(prefix);
				Variables.getLogger().debug("Generated userID : "+userID);
				regex.append(userID);
				match = true;
				*/
				}
			else if(Pattern.matches(".*office\\..*", param[i]))
				{
				String result = ad.getOffice().getString(param[i]);
				regex.append(applyRegex(result, param[i]));
				match = true;
				}
			else if(Pattern.matches(".*config\\..*", param[i]))
				{
				String[] tab = param[i].split("\\.");
				String result = UsefulMethod.getTargetOption(tab[1]);
				//regex.append(applyRegex(result, param[i]));
				regex.append(applyRegex(applyPattern(ad, result, null, false),param[i]));//BETA : Should allow to store regex in the configuration file then apply them as a pattern
				
				match = true;
				}
			else if(Pattern.matches(".*cucm.availableline.*", param[i]))
				{
				regex.append(applyRegex(getAvailableInternalNumber(UsefulMethod.getTargetOption("nodidrange")), param[i]));
				match = true;
				}
			
			/***********/
			
			//Default
			if(!match)
				{
				regex.append(param[i]);
				}
			}
		
		if(regex.toString().equals("") && emptyException)throw new EmptyValueException("The processed regex return an empty value : "+pat);
		
		return regex.toString();
		}
			
	/****
	 * Method used to apply a regex to a value	
	 * @throws Exception 
	 */
	private static String applyRegex(String newValue, String param) throws Exception
		{
		try
			{
			/*********
			 * Number before
			 **/
			if(Pattern.matches("\\*\\d+_\\*.*", param))
				{
				int number = howMany("\\*\\d+_\\*", param);
				if(newValue.length() >= number)
					{
					newValue = newValue.substring(0, number);
					}
				}
			/**
			 * End number before
			 *************/
			
			/*********
			 * Number after
			 **/
			if(Pattern.matches("\\*_\\d+\\*.*", param))
				{
				int number = howMany("\\*_\\d+\\*", param);
				if(newValue.length() >= number)
					{
					newValue = newValue.substring(newValue.length()-number, newValue.length());
					}
				}
			/**
			 * End number after
			 *************/
			
			/*************
			 * Majuscule
			 **/
			if(Pattern.matches(".*\\*M\\*.*", param))
				{
				newValue = newValue.toUpperCase();
				}
			if(Pattern.matches(".*\\*\\d+M\\*.*", param))
				{
				int majuscule = howMany("\\*\\d+M\\*", param);
				if(newValue.length() >= majuscule)
					{
					String first = newValue.substring(0, majuscule);
					String last = newValue.substring(majuscule,newValue.length());
					first = first.toUpperCase();
					last = last.toLowerCase();
					newValue = first+last;
					}
				}
			/**
			 * End majuscule
			 ****************/
			
			/*************
			 * Minuscule
			 **/
			if(Pattern.matches(".*\\*m\\*.*", param))
				{
				newValue = newValue.toLowerCase();
				}
			if(Pattern.matches(".*\\*\\d+m\\*.*", param))
				{
				int minuscule = howMany("\\*\\d+m\\*", param);
				if(newValue.length() >= minuscule)
					{
					String first = newValue.substring(0, minuscule);
					String last = newValue.substring(minuscule,newValue.length());
					first = first.toLowerCase();
					last = last.toUpperCase();
					newValue = first+last;
					}
				}
			/**
			 * End minuscule
			 ****************/
			
			/*************
			 * Split
			 * 
			 * Example : *1S/*
			 * means to split using "/" and to keep the first value
			 **/
			if(Pattern.matches(".*\\*\\d+S.+\\*.*", param))
				{
				int split = howMany("\\*\\d+S.+\\*", param);
				String splitter = getSplitter("\\*\\d+S.+\\*", param);
				newValue = newValue.split(splitter)[split-1];
				}
			/**
			 * End Split
			 ****************/
			
			/*************
			 * Replace
			 * 
			 * Example : *"test"R"testo"*
			 **/
			if(Pattern.matches(".*\\*\".+\"R\".*\"\\*.*", param))
				{
				String pattern = null;
				String replaceBy = null;
				Pattern begin = Pattern.compile("\".+\"R");
				Matcher mBegin = begin.matcher(param);
				Pattern end = Pattern.compile("R\".*\"");
				Matcher mEnd = end.matcher(param);
				
				if(mBegin.find())
					{
					String str = mBegin.group();
					str = str.substring(0,str.length()-1);//We remove the "R"
					str = str.replace("\"", "");
					pattern = str;
					}
				if(mEnd.find())
					{
					String str = mEnd.group();
					str = str.substring(1,str.length());//We remove the "R"
					str = str.replace("\"", "");
					replaceBy = str;
					}
				if((pattern != null) && (replaceBy != null))
					{
					newValue = newValue.replace(pattern, replaceBy);
					}
				}
			/**
			 * End Replace
			 ****************/
			
			/*************
			 * Clear French Char
			 * 
			 * Example : *C*
			 **/
			if(Pattern.matches(".*\\*C\\*.*", param))
				{
				newValue = ClearFrenchString.translate(newValue);
				}
			/**
			 * End Clear French Char
			 ****************/
			
			/**
			 * End
			 ****************/
			
			/*************
			 * Convert values into CUCM acceptable ones
			 * 
			 * For instance : "7962" into "cisco 7962" etc...
			 * LF Means "Look For"
			 * Example : *LFcss* or *LFphone*
			 **/
			if(Pattern.matches(".*\\*LF\\w+\\*.*", param))
				{
				//We extract the substitute type
				String substitute = null;
				Pattern p = Pattern.compile("\\*LF\\w+\\*");
				Matcher m = p.matcher(param);
				
				if(m.find())
					{
					String str = m.group();
					str = str.replace("*LF", "").replace("*", "");
					substitute = str;
					}
				
				if(substitute != null)
					{
					newValue = UsefulMethod.findSubstitute(newValue, SubstituteType.valueOf(substitute));
					}
				}
			/**
			 * End Convert values into CUCM acceptable ones
			 ****************/
			
			/***************
			 * Validate if the given value is a MAC address 
			 * 
			 * Example : *MAC*
			 */
			if(Pattern.matches(".*\\*MAC\\*.*", param))
				{
				if(!Pattern.matches("\\p{XDigit}{12}", newValue))throw new Exception(newValue+" is not a valid MAC address");
				}
			/**
			 * End of MAC validation
			 ********************/
			
			
			/**************************************/
			return newValue;
			}
		catch(Exception exc)
			{
			throw new Exception("An issue occured while applying the regex : "+exc.getMessage());
			}
		}
	
	/**
	 * Method used to return a number present in a regex
	 * 
	 * for instance : *1M* return 1
	 */
	private static int howMany(String regex, String param) throws Exception
		{
		Pattern p = Pattern.compile(regex);
		Pattern pChiffre = Pattern.compile("\\d+");
		Matcher m = p.matcher(param);
		
		if(m.find())
			{
			Matcher mChiffre = pChiffre.matcher(m.group());
			if(mChiffre.find())
				{
				return Integer.parseInt(mChiffre.group());
				}
			}
		return 0;
		}
	
	/**
	 * Method used to find and return 
	 * Character used to split
	 */
	private static String getSplitter(String regex, String param) throws Exception
		{
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(param);
		
		if(m.find())
			{
			String temp = m.group().replace("*", "");
			return temp.split("S")[1];
			}
		throw new Exception();
		}
	
	/********
	 * Method used to apply a pattern to the given value
	 * We can here choose what behavior we want regarding the empty value
	 * in the collection file :
	 * - True : We will get EmptyValueException if empty
	 * - False : We will just get an empty String
	 * @throws Exception 
	 */
	public static String applyPattern(AgentData ad, String pattern, Object obj, Boolean emptyBehavior) throws Exception
		{
		if((pattern == null) || (pattern .equals("")))
			{
			Variables.getLogger().debug("The pattern is either null or empty, so we return an empty value");
			return "";
			}
		
		Variables.getLogger().debug("Value before : "+pattern);
		String result = doRegex(pattern, ad, obj, emptyBehavior);
		
		result = result.trim();//Just to remove unwanted spaces
		
		Variables.getLogger().debug("Value after : "+result);
		return result;
		}
	
	/*************
	 * Method used to get an available number
	 * from the CUCM
	 */
	public static String getAvailableInternalNumber(String range) throws Exception
		{
		try
			{
			UsedItemList uil = UsefulMethod.getUsedNumberList(range);
			
			String[] tab = range.split(":");
			String firstNumber = tab[0];
			String lastNumber = tab[1];
			
			int currentNum = Integer.parseInt(firstNumber);
			int lastNum = Integer.parseInt(lastNumber);
			
			while(currentNum < lastNum)
				{
				if(!(uil.getItemList().contains(Integer.toString(currentNum))))
					{
					String num = Integer.toString(currentNum);
					uil.getItemList().add(num);//We add the number to the list to be sure to not use it twice
					Variables.getLogger().debug("Available number found : "+num);
					return num;
					}
				currentNum++;
				}
			}
		catch (Exception e)
			{
			throw new Exception("Error while trying to get an available number : "+e.getMessage());
			}
		
		throw new Exception("No available number found in the range : "+range);
		}
	
	/*************
	 * Method used to get an available userID index in the given range
	 * from the CUCM
	 */
	public static int getAvailableUserIdIndex(String prefix, String suffix) throws Exception
		{
		try
			{
			UsedItemList uil = UsefulMethod.getUsedUserIDList(prefix, suffix);
			
			int currentIndex = 1;
			int lastIndex = Integer.parseInt(UsefulMethod.getTargetOption("maxuseridindex"));//Max index
			
			while(currentIndex < lastIndex)
				{
				if(!(uil.getItemList().contains(prefix+currentIndex+suffix)))
					{
					String userID = prefix+currentIndex+suffix;
					uil.getItemList().add(userID);//We add the userID to the list to be sure to not use it twice
					Variables.getLogger().debug("Available userID found : "+userID);
					return currentIndex;
					}
				currentIndex++;
				}
			}
		catch(Exception e)
			{
			throw new Exception("Error while trying to get an available userID : "+e.getMessage());
			}
		throw new Exception("No available userID found with the prefix '"+prefix+"' and suffix '"+suffix+"'");
		}
	
	/*************
	 * Method used to get an available userID in the given range
	 * from the CUCM
	 */
	public static ArrayList<AgentData> searchForUser(String searchPattern) throws Exception
		{
		try
			{
			ArrayList<AgentData> userList = new ArrayList<AgentData>();
			
			List<Object> SQLResp = SimpleRequest.doSQLQuery("select userid, firstname, lastname from enduser where userid like '%"+searchPattern+"%' or lastname like '%"+searchPattern+"%' or firstname like '%"+searchPattern+"%'");
			
			for(Object o : SQLResp)
				{
				Element rowElement = (Element) o;
				NodeList list = rowElement.getChildNodes();
				
				String userid = null, lastname = null, firstname = null;
				
				for(int i = 0; i< list.getLength(); i++)
					{
					if(list.item(i).getNodeName().equals("userid"))userid = list.item(i).getTextContent();
					else if(list.item(i).getNodeName().equals("firstname"))firstname = list.item(i).getTextContent();
					else if(list.item(i).getNodeName().equals("lastname"))lastname = list.item(i).getTextContent();
					}
				
				AgentData ad = new AgentData(userid);
				ad.setLastName(lastname);
				ad.setFirstName(firstname);
				
				userList.add(ad);
				}
			
			Variables.getLogger().debug("No user found with the following search pattern : "+searchPattern);
			throw new Exception("No user found with the following search pattern : "+searchPattern);
			}
		catch(Exception e)
			{
			throw new Exception("Error while trying to search for a user : "+e.getMessage());
			}
		}
	
	/**
	 * Used to split a value while using the escape character "\"
	 * @param pat
	 * @param splitter
	 * @return
	 */
	public static String[] getSplittedValue(String pat, String splitter)
		{
		pat = pat.replace("'", "");
		String splitRegex = "(?<!\\\\)" + Pattern.quote(splitter);//To activate "\" as an escape character
		
		String[] tab = pat.split(splitRegex);
		//We now remove the remaining \
		for(int i=0; i<tab.length; i++)
			{
			if(tab[i].contains("\\\\"))
				{
				//to keep one \ when it has been escaped \\
				tab[i] = tab[i].replace("\\\\", "\\");
				}
			else if(tab[i].contains("\\"))
				{
				tab[i] = tab[i].replace("\\", "");
				}
			}
		
		return tab;
		}
	
	/*2022*//*RATEL Alexandre 8)*/
	}

