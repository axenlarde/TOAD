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
	
	public WebRequest(String content, webRequestType type)
		{
		super();
		this.content = content;
		this.type = type;
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
	
	/*2019*//*RATEL Alexandre 8)*/
	}
