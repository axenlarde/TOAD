package com.alex.toad.uccx.items;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.alex.toad.misc.ItemToInject;
import com.alex.toad.rest.misc.RESTTools;
import com.alex.toad.restitems.linkers.CSQLinker;
import com.alex.toad.utils.Variables;
import com.alex.toad.utils.Variables.itemType;

/**********************************
* Used to store a UCCX CSQ
* 
* @author RATEL Alexandre
**********************************/
public class CSQ extends ItemToInject
	{
	/**
	 * Variables
	 */
	private CSQLinker myCSQ;
	private ArrayList<Skill> skills;
	
	/**
	 * Constructor
	 * @throws Exception 
	 */
	public CSQ(String name,
			ArrayList<Skill> skills) throws Exception
		{
		super(itemType.csq, name);
		this.myCSQ = new CSQLinker(name);
		this.skills = skills;
		}
	
	public CSQ(String name) throws Exception
		{
		super(itemType.csq, name);
		this.myCSQ = new CSQLinker(name);
		}

	public String getString(String s) throws Exception
		{
		for(Field f : this.getClass().getDeclaredFields())
			{
			if(f.getName().toLowerCase().equals(s.toLowerCase()))
				{
				return (String) f.get(this);
				}
			}
		
		throw new Exception("ERROR : No value found");
		}

	@Override
	public void doBuild() throws Exception
		{
		//Nothing to build
		
		errorList.addAll(myCSQ.init());
		}

	@Override
	public String doInject() throws Exception
		{
		return myCSQ.inject();
		//Not implemented
		}

	@Override
	public void doDelete() throws Exception
		{
		myCSQ.delete();
		//Not implemented
		}

	@Override
	public void doUpdate() throws Exception
		{
		myCSQ.update(tuList);
		//Not implemented
		}
	
	public void doGet() throws Exception
		{
		CSQ myQ = (CSQ)myCSQ.get();
		UUID = myQ.getUUID();
		skills = myQ.getSkills();
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
		//Not implemented
		}

	@Override
	public void manageTuList() throws Exception
		{
		//Not implemented
		}

	public ArrayList<Skill> getSkills()
		{
		return skills;
		}

	public void setSkills(ArrayList<Skill> skills)
		{
		this.skills = skills;
		}
	
	
	
	/*2022*//*RATEL Alexandre 8)*/
	}

