package com.aequilibrium.sample.transformer.fight;

import java.util.List;

import com.aequilibrium.sample.transformer.Transformer;

public interface Fight {

	// constants defining values of auto-winning names
	public static final String NAME_OPTIMUS_PRIME = "Optimus Prime";
	public static final String NAME_PREDAKING = "Predaking";
	
	// constants defining proper nations of factions for reporting victors 
	public static final String OUTCOME_AUTOBOTS = "Autobots";
	public static final String OUTCOME_DECEPTICONS = "Decepticons";
	public static final String OUTCOME_TIE = "Tie";
	
	// link to youtube videos of Decepticon and Autobot midrolls
	public static final String MIDROLL_DECEPTICON = "https://www.youtube.com/watch?v=_70TAZPStzQ";
	public static final String MIDROLL_AUTOBOT = "https://www.youtube.com/watch?v=ODy_VrL_EXo";

	/**
	 * Calculates fights based on a series of rules found in the Read me document.
	 * @param combatants a List of Transformers.
	 * @return a Fight which included all Transformers provided.
	 */
	public static Fight startBattle( List<Transformer> combatants ) {
		return new FightImpl(combatants);
	}
	
	/**
	 * Returns surviving members of losing faction, or all survivors in the event of a tie.
	 * @return List of Transformers.
	 */
	public List<Transformer> getLosers();
	
	/**
	 * Returns surviving members of wining faction.  Empty in the event of a tie. 
	 * @return List of Transformers.
	 */
	public List<Transformer> getVictors();
	
	/**
	 * Returns number of individual battles between two transformers.
	 * @return int number of battles.
	 */
	public int getBattles();
	
	/**
	 * Returns a string indicating an Autobot or Decepticon victory or a tie.
	 * @return One of three constants attached to Fight interface.
	 */
	public String getVictor();
	
	/**
	 * Returns a midroll for winning side.
	 * @return String, youtube link.
	 */
	public String getMidroll();
}
