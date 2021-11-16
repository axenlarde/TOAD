package com.alex.toad.webserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alex.perceler.utils.HttpsTrustManager;
import com.alex.perceler.utils.UsefulMethod;
import com.alex.perceler.utils.Variables;
import com.alex.perceler.webserver.ManageWebRequest.webRequestType;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

/**
 * Class used to manage web request
 *
 * @author Alexandre
 */
public class WebListenerManager implements HttpHandler
	{
	/**
	 * Variables
	 */
	
	/**
	 * Constructor
	 */
	public WebListenerManager()
		{
		try
			{
			HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(UsefulMethod.getTargetOption("webserverport"))), 0);
			HttpContext context = server.createContext("/PERCELER", this);
			server.start();
			Variables.getLogger().debug("Web Server started !");
			
			/*
			HttpsServer server = HttpsServer.create(new InetSocketAddress(Integer.parseInt(UsefulMethod.getTargetOption("webserverport"))), 0);
			
			X509TrustManager xtm = new HttpsTrustManager();
            TrustManager[] mytm = { xtm };
            SSLContext sslContext = SSLContext.getInstance("SSL");
            
            //initialise the keystore
            char[] password = "password".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("./testkey.jks");
            ks.load(fis, password);
            
            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);
            
            sslContext.init(kmf.getKeyManagers(), mytm, null);
            
            server.setHttpsConfigurator(new HttpsConfigurator(sslContext)
            	{
	            public void configure(HttpsParameters params)
	            	{
	                try
	                	{
	                    SSLContext c = SSLContext.getDefault();
	                    SSLEngine engine = c.createSSLEngine();
	                    params.setNeedClientAuth(false);
	                    params.setCipherSuites(engine.getEnabledCipherSuites());
	                    params.setProtocols(engine.getEnabledProtocols());
	                    SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
	                    params.setSSLParameters(defaultSSLParameters);
	                	}
	                catch(Exception exc)
	                	{
	                    Variables.getLogger().debug("ERROR : "+exc.getMessage(),exc);
	                	}
	            	}
            	});
			
			HttpContext context = server.createContext("/PERCELER", this);
			server.start();
			Variables.getLogger().debug("Web Server started !");*/
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR : While listening for new web request : "+e.getMessage(),e);
			} 
		}
	
	@Override
	public void handle(HttpExchange exc) throws IOException
		{
		Variables.getLogger().debug("Web Server : Request received");
		
		try
			{
			//we treat only request from the local web server
			if(exc.getLocalAddress().getAddress().equals(exc.getRemoteAddress().getAddress()))
				{
				if(exc.getRequestMethod().equals("HEAD"))
					{
					Variables.getLogger().debug("Web Server : "+exc.getRequestMethod()+" Method received");
					
					Headers hs = exc.getResponseHeaders();
					hs.add("Connection", "Keep-Alive");
					hs.add("Keep-Alive", "timeout = 20000 max = 100");
					exc.sendResponseHeaders(200, 0);
					}
				else if((exc.getRequestMethod().equals("POST")) || (exc.getRequestMethod().equals("GET")))
					{
					Variables.getLogger().debug("Web Server : "+exc.getRequestMethod()+" Method received");
					
					String content = getContent(exc);
					Variables.getLogger().debug("Web request content : ");
					Variables.getLogger().debug(content);
					
					WebRequest wr = ManageWebRequest.parseWebRequest(content);
					WebRequest reply = null;
					
					if(wr.getType().equals(webRequestType.doAuthenticate))
						{
						reply = ManageWebRequest.doAuthenticate(content);
						}
					else if(wr.getType().equals(webRequestType.search))
						{
						reply = ManageWebRequest.search(content);
						}
					else if(wr.getType().equals(webRequestType.getOfficeList))
						{
						reply = ManageWebRequest.getOfficeList();
						}
					else if(wr.getType().equals(webRequestType.getDeviceList))
						{
						reply = ManageWebRequest.getDeviceList();
						}
					else if(wr.getType().equals(webRequestType.getTaskList))
						{
						reply = ManageWebRequest.getTaskList();
						}
					else if(wr.getType().equals(webRequestType.getOffice))
						{
						reply = ManageWebRequest.getOffice(content);
						}
					else if(wr.getType().equals(webRequestType.getDevice))
						{
						reply = ManageWebRequest.getDevice(content);
						}
					else if(wr.getType().equals(webRequestType.getTask))
						{
						reply = ManageWebRequest.getTask(content);
						}
					else if(wr.getType().equals(webRequestType.newTask))
						{
						reply = ManageWebRequest.newTask(content);
						}
					else if(wr.getType().equals(webRequestType.setTask))
						{
						reply = ManageWebRequest.setTask(content);
						}
					else if(wr.getType().equals(webRequestType.copyLogFile))
						{
						reply = ManageWebRequest.copyLogFile();
						}
					else if(wr.getType().equals(webRequestType.newDevice))
						{
						reply = ManageWebRequest.newDevice(content);
						}
					else if(wr.getType().equals(webRequestType.newOffice))
						{
						reply = ManageWebRequest.newOffice(content);
						}
					
					OutputStream os = exc.getResponseBody();
					
					if(reply != null)
						{
						Variables.getLogger().debug("Web Server : Sending response for request "+wr.getType().name());
						Variables.getLogger().debug(reply.getContent());
						Variables.getLogger().debug("Reply length : "+reply.getContent().getBytes().length);
						
						exc.sendResponseHeaders(200, reply.getContent().getBytes().length);
						os.write(reply.getContent().getBytes());
						}
					else
						{
						Variables.getLogger().debug("Something went wrong while building the reply so we send an error message");
						exc.sendResponseHeaders(500, 0);
						os.write("".getBytes());
						}
					
					os.flush();
					os.close();
					
					Variables.getLogger().debug("Web Server : Response sent");
					}
				else
					{
					Variables.getLogger().debug("Unkown method : "+exc.getRequestMethod());
					}
				}
			else
				{
				Variables.getLogger().debug("Request received from an external source \""+exc.getRemoteAddress().getAddress()+"\" so we discard it");
				}
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR : While treating a web request : "+e.getMessage(),e);
			}
		}
	
	
	private String getContent(HttpExchange exc) throws Exception
		{
		BufferedReader in = new BufferedReader(new InputStreamReader(exc.getRequestBody()));
		StringBuffer buf = new StringBuffer();
		
		while(true)
			{
			String s = in.readLine();
			if(s != null)
				{
				buf.append(s);
				buf.append("\r\n");
				}
			else
				{
				break;
				}
			}
		
		return buf.toString();
		}
	
	/*2019*//*RATEL Alexandre 8)*/
	}
