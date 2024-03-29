package com.alex.toad.misc;

/**
 * @author "Alexandre RATEL"
 *
 * Class used to represent a correction made
 */
public class Correction
	{
	/**
	 * Variables
	 */
	public enum correctionType
		{
		tooLong,
		other
		};
	
	private String description;
	private String advice;
	private boolean warning;
	private correctionType type;
	
	/**
	 * Constructor
	 */
	public Correction(String description, String advice, correctionType type, boolean warning)
		{
		this.description = description;
		this.advice = advice;
		this.type = type;
		this.warning = warning;
		}

	public boolean isWarning()
		{
		return warning;
		}

	public void setWarning(boolean warning)
		{
		this.warning = warning;
		}
	
	public String getAdvice()
		{
		return advice;
		}
	
	public void setAdvice(String advice)
		{
		this.advice = advice;
		}

	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
		}

	public correctionType getType()
		{
		return type;
		}

	public void setType(correctionType type)
		{
		this.type = type;
		}
	
	
	
	
	/*2017*//*AR 8)*/
	}
