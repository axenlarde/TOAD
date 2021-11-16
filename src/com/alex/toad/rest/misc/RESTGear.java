package com.alex.toad.rest.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.net.util.Base64;

import com.alex.ubc.utils.UsefulMethod;
import com.alex.ubc.utils.Variables;
import com.alex.ubc.utils.Variables.requestType;


/**
 * Home made class to send REST request 
 *
 * @author Alexandre RATEL
 */
public class RESTGear
	{
	
	
	/**
	 * To send a GET request
	 */
	public static String send(requestType type, String uri, String content, String login, String password, int timeout) throws Exception
		{
		HttpsURLConnection request = null;
		BufferedWriter out = null;
		BufferedReader in = null;
		StringBuffer reply = new StringBuffer("");
		
		try
			{
			Variables.getLogger().debug("Sending "+type.name()+" request to "+uri+" \r\n "+content);
			
			String cred = login+":"+password;
			cred = new String(Base64.encodeBase64(cred.getBytes()));
			
			//We ignore SSL untrusted certificate
			UsefulMethod.disableSecurity();
			
			URL myUrl = new URL(uri);
			request = (HttpsURLConnection)myUrl.openConnection();
			request.setRequestProperty("Authorization", "Basic "+cred);
			request.setRequestMethod(type.name());
			request.setConnectTimeout(timeout);
			request.setRequestProperty("Content-Type", "application/XML");
			request.setRequestProperty("content-Length", Integer.toString(content.length()));
			request.setDoOutput(true);
			
			//Then we send the request
			out = new BufferedWriter(new OutputStreamWriter(request.getOutputStream(),"UTF-8"));
			out.write(content);
			out.flush();
			Variables.getLogger().debug(type.name()+" sent");
			
			in = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			String temp;
			while ((temp = in.readLine()) != null)
				{
			    reply.append(temp);
			 	}
			Variables.getLogger().debug("Server reply : "+reply.toString());
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR While sending a rest request : "+e.getMessage());
			throw e;
			}
		finally
			{
			try
				{
				out.close();
				in.close();
				request.disconnect();
				}
			catch(Exception e)
				{
				Variables.getLogger().error("ERROR While closing the connection : "+e.getMessage());
				throw e;
				}
			}
		return reply.toString();
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}
