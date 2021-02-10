package com.aequilibrium.sample.transformer;

import java.util.EnumSet;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = TransformerImpl.class)
public interface Transformer {

	// enum of all transformer stats
	public enum Stats { 
		STRENGTH, INTELLIGENCE, SPEED, ENDURANCE, RANK, COURAGE, FIREPOWER, SKILL
	}
	
	// this is an enum set of just the stats used in calculating overall battle value
	public static final EnumSet<Stats> OVERALL = EnumSet.of( 
			Stats.STRENGTH, 
			Stats.INTELLIGENCE, 
			Stats.SPEED, 
			Stats.ENDURANCE, 
			Stats.FIREPOWER
	); 
	
	// we store the only two acceptable factions as constants
	public static final char AUTOBOT = 'A';
	public static final char DECEPTICON = 'D';
	
	
	public static Transformer blankTransformer() {
		return new TransformerImpl();
	}
	
	/**
	 * Getter for id
	 * @return Long, unique record identifier
	 */
	public Long getId();
	
	/**
	 * Setter for id, used only by testing classes
	 * @param id Long, sequential id generated from database.
	 */
	public void setId(long id);
	
	/**
	 * Getter for Transformer name.
	 * @return String, name less that 100 characters long, not null
	 */
	public String getName();
	
	/**
	 * Setter for name.  Invalid values for name will not save.
	 * @param name String, name less that 100 characters long, not null
	 */
	public void setName(String name);
	
	/**
	 * Getter for faction, returns constant AUTOBOT or DECEPTICON.
	 * @return Char A or D
	 */
	public char getFaction();
	
	/**
	 * Setter for faction, accepts only valid factions. 
	 * @param faction Char AUTOBOT 'A' or DECEPTICON 'D'
	 */
	public void setFaction(char faction);
	
	/**
	 * Setter for stats, invalid values will be discarded
	 * @param stat, an enum attached to the Transformer interface 
	 * @param value, a byte ranging from 1 to 10
	 */
	public void setStat(Stats stat, byte value);
	
	/**
	 * Getter for stats
	 * @param stat, an enum attached to the Transformer interface 
	 * @return a Byte between 1 and 10 inclusive
	 */
	public Byte getStat(Stats stat );
	
	/**
	 * Returns the Map containing stats
	 * @return Map
	 */
	public Map<Stats, Byte> getStats();
	
}
