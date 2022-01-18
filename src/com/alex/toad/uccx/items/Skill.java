package com.alex.toad.uccx.items;

import java.lang.reflect.Field;

import com.alex.toad.misc.ItemToInject;
import com.alex.toad.utils.Variables.itemType;

/**********************************
* Used to store a UCCX skill
* 
* @author RATEL Alexandre
**********************************/
public class Skill extends ItemToInject
	{
	/**
	 * Variables
	 */
	private String name;
	private int level;
	
	/***************
	 * Constructor
	 ***************/
	public Skill(String name)
		{
		super(itemType.skill, name);
		}
	
	public Skill(String name, int level)
		{
		super(itemType.skill, name);
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

	public void setLevel(int level)
		{
		this.level = level;
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

