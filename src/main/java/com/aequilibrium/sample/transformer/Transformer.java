package com.aequilibrium.sample.transformer;

import java.util.EnumSet;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = TransformerImpl.class)
public interface Transformer {

	public enum Attribute { 
		STRENGTH, INTELLIGENCE, SPEED, ENDURANCE, RANK, COURAGE, FIREPOWER, SKILL
	}
	
	public static final EnumSet<Attribute> OVERALL = EnumSet.of( 
			Attribute.STRENGTH, 
			Attribute.INTELLIGENCE, 
			Attribute.SPEED, 
			Attribute.ENDURANCE, 
			Attribute.FIREPOWER
	); 
	
	public static final char AUTOBOT = 'A';
	public static final char DECEPTICON = 'D';
	
	public static Transformer blankTransformer() {
		return new TransformerImpl();
	}
	
	public Long getId();
	
	public void setId(long id);
	
	public String getName();
	
	public void setName(String name);
	
	public char getFaction();
	
	public void setFaction(char faction);
	
	public void setAttribute(Attribute attribute, byte value);
	
	public Byte getAttribute(Attribute attribute );
	
	public Map<Attribute, Byte> getAttributes();
	
}
