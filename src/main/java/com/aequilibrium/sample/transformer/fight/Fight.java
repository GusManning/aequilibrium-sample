package com.aequilibrium.sample.transformer.fight;

import java.util.List;

import com.aequilibrium.sample.transformer.Transformer;

public interface Fight {

	public static final String NAME_OPTIMUS_PRIME = "Optimus Prime";
	public static final String NAME_PREDAKING = "Predaking";
	
	public static final String OUTCOME_AUTOBOTS = "Autobots";
	public static final String OUTCOME_DECEPTICONS = "Decepticons";
	public static final String OUTCOME_TIE = "TIE";
	
	public static final String MIDROLL_DECEPTICON = "https://www.youtube.com/watch?v=_70TAZPStzQ";
	public static final String MIDROLL_AUTOBOT = "https://www.youtube.com/watch?v=ODy_VrL_EXo";

	public static Fight startBattle( List<Transformer> combatants ) {
		return null;
	}
	
	public List<Transformer> survivingLosers();
	
	public List<Transformer> survivingVictors();
	
	public int numberOfBattles();
	
	public String getVictor();
	
	public String getMidroll();
}
