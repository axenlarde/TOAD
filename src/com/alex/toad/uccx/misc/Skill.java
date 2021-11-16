package com.alex.toad.uccx.misc;

import java.lang.reflect.Field;

/**********************************
* Used to store a UCCX skill
* 
* @author RATEL Alexandre
**********************************/
public class Skill
	{
	/**
	 * Variables
	 */
	private String name;
	private int level;
	
	/***************
	 * Constructor
	 ***************/
	public Skill(String name, int level)
		{
		super();
		this.name = name;
		this.level = level;
		}
	
	public String getString(String s) throws Exception
		{
		String tab[] = s.split("\\.");
		
		if(tab.length == 2)
			{
			for(Field f : this.getClass().getDeclaredFields())
				{
				if(f.getName().equals(tab[1]))
					{
					return (String) f.get(this);
					}
				}
			}
		throw new Exception("ERROR : No value found");
		}

	public String getName()
		{
		return name;
		}

	public int getLevel()
		{
		return level;
		}
	
	
	/*2021*//*RATEL Alexandre 8)*/
	}

