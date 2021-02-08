package com.aequilibrium.sample.transformer;

import java.util.EnumSet;
import java.util.Map;

public interface Transformer {

	public enum Attribute { 
		STRENGTH, INTELLIGENCE, SPEED, ENDURANCE, RANK, COURAGE, FIREPOWER
	}
	
	public static final EnumSet<Attribute> SKILLS = EnumSet.of( 
			Attribute.STRENGTH, 
			Attribute.INTELLIGENCE, 
			Attribute.SPEED, 
			Attribute.ENDURANCE, 
			Attribute.FIREPOWER
	); 
	
	public static final char AUTOBOT = 'A';
	public static final char DECEPTICON = 'D';
	
	public int getId();
	
	public String getName();
	
	public void setName(String name);
	
	public char getFaction();
	
	public void setFaction(char faction);
	
	public Byte setAttribute(Attribute attribute, byte value);
	
	public Byte getAttribute(Attribute attribute );
	
	public Map<Attribute, Byte> getAttributes();
	
}
