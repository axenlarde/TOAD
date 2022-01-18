package com.alex.toad.webserver;

import com.alex.toad.webserver.ManageWebRequest.webRequestType;

/**
 * Contain a web request
 *
 * @author Alexandre RATEL
 */
public class WebRequest
	{
	/**
	 * Variables
	 */
	private String content;
	private webRequestType type;
	private SecurityToken securityToken;
	
	public WebRequest(String content, webRequestType type)
		{
		super();
		this.content = content;
		this.type = type;
		this.securityToken = new SecurityToken("", null);
		}
	
	public WebRequest(String content, webRequestType type, SecurityToken securityToken)
		{
		super();
		this.content = content;
		this.type = type;
		this.securityToken = securityToken;
		}

	public String getContent()
		{
		return content;
		}

	public void setContent(String content)
		{
		this.content = content;
		}

	public webRequestType getType()
		{
		return type;
		}

	public void setType(webRequestType type)
		{
		this.type = type;
		}

	public SecurityToken getSecurityToken()
		{
		return securityToken;
		}

	public void setSecurityToken(SecurityToken securityToken)
		{
		this.securityToken = securityToken;
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}
