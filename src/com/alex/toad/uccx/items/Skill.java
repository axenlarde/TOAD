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
	private SkillLinker mySkill;
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
				if(f.getName().toLowerCase().equals(tab[1].toLowerCase()))
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

	@Override
	public void doBuild() throws Exception
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public String doInject() throws Exception
		{
		// TODO Auto-generated method stub
		return null;
		}

	@Override
	public void doDelete() throws Exception
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public void doUpdate() throws Exception
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public boolean isExisting() throws Exception
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public void resolve() throws Exception
		{
		// TODO Auto-generated method stub
		
		}

	@Override
	public void manageTuList() throws Exception
		{
		// TODO Auto-generated method stub
		
		}
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

