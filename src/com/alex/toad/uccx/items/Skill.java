package com.alex.toad.uccx.items;

import java.lang.reflect.Field;

import com.alex.toad.misc.ItemToInject;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.restitems.linkers.SkillLinker;
import com.alex.toad.utils.Variables;
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
	private SkillLinker mySkill;
	private int level;
	
	/***************
	 * Constructor
	 * @throws Exception 
	 ***************/
	public Skill(String name) throws Exception
		{
		super(itemType.skill, name);
		mySkill = new SkillLinker(name);
		}
	
	public Skill(String name, int level) throws Exception
		{
		super(itemType.skill, name);
		mySkill = new SkillLinker(name);
		this.level = level;
		}
	
	public String getString(String s) throws Exception
		{
		/*
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
			}*/
		
		for(Field f : this.getClass().getDeclaredFields())
			{
			if(f.getName().toLowerCase().equals(s.toLowerCase()))
				{
				return (String) f.get(this);
				}
			}
		
		throw new Exception("ERROR : No value found");
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
		//Nothing to build
		
		errorList.addAll(mySkill.init());
		}

	@Override
	public String doInject() throws Exception
		{
		return mySkill.inject();
		//Not implemented
		}

	@Override
	public void doDelete() throws Exception
		{
		mySkill.delete();
		//Not implemented
		}

	@Override
	public void doUpdate() throws Exception
		{
		mySkill.update(tuList);
		//Not implemented
		}
	
	public void doGet() throws Exception
		{
		Skill myS = (Skill)mySkill.get();
		UUID = myS.getUUID();
		Variables.getLogger().debug("Item "+this.name+" data fetch from the UCCX");
		}

	@Override
	public boolean doExist() throws Exception
		{
		UUID = RESTTools.getRESTUUIDV105(type, name);
		return true;
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

