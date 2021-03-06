package com.aequilibrium.sample.transformer.fight;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.aequilibrium.sample.transformer.Transformer;
import com.aequilibrium.sample.transformer.Transformer.Stats;

public class FightTest {
	
	public static Transformer PREDAKING = Transformer.blankTransformer();
	public static Transformer OPTIMUS = Transformer.blankTransformer();
	public static Transformer STARSCREAM = Transformer.blankTransformer();
	public static Transformer SOUNDWAVE = Transformer.blankTransformer();
	public static Transformer RODIMUS = Transformer.blankTransformer();
	public static Transformer BUMBLEBEE = Transformer.blankTransformer();
	
	private List<Transformer> combatants = new ArrayList<>();
	
	@BeforeAll
	public static void setupCombatants() {
		PREDAKING.setName(Fight.NAME_PREDAKING);
		PREDAKING.setFaction(Transformer.DECEPTICON);
		setAttributesTo(PREDAKING,(byte)1);
		PREDAKING.setStat(Stats.RANK,(byte) 10);
		
		OPTIMUS.setName(Fight.NAME_OPTIMUS_PRIME);
		OPTIMUS.setFaction(Transformer.AUTOBOT);
		setAttributesTo(OPTIMUS,(byte)1);
		OPTIMUS.setStat(Stats.RANK,(byte) 10);
		
		STARSCREAM.setName("Starscream");
		STARSCREAM.setFaction(Transformer.DECEPTICON);
		setAttributesTo(STARSCREAM,(byte)5);
		STARSCREAM.setStat(Stats.COURAGE,(byte) 1);
		STARSCREAM.setStat(Stats.RANK,(byte) 9);
		STARSCREAM.setStat(Stats.STRENGTH,(byte) 2);
		STARSCREAM.setStat(Stats.SPEED,(byte) 10);
		STARSCREAM.setStat(Stats.FIREPOWER,(byte)10);
		STARSCREAM.setStat(Stats.INTELLIGENCE,(byte) 10);
		STARSCREAM.setStat(Stats.SKILL,(byte) 1);
		
		SOUNDWAVE.setName("Soundwave");
		SOUNDWAVE.setFaction(Transformer.DECEPTICON);
		setAttributesTo(SOUNDWAVE,(byte)5);
		SOUNDWAVE.setStat(Stats.RANK,(byte) 9);
		
		RODIMUS.setName("Rodimus");
		RODIMUS.setFaction(Transformer.AUTOBOT);
		setAttributesTo(RODIMUS,(byte)5);
		RODIMUS.setStat(Stats.RANK,(byte) 9);
		
		BUMBLEBEE.setName("Bumblebee");
		BUMBLEBEE.setFaction(Transformer.AUTOBOT);
		setAttributesTo(BUMBLEBEE,(byte)4);
	}
	
	private static void setAttributesTo(Transformer bot, byte value ) {
		for(Stats attribute:Transformer.Stats.values()) {
			bot.setStat(attribute, value);
		}
	}
	
	@Test
	public void testOptimusWin() {
		combatants.add(OPTIMUS);
		combatants.add(SOUNDWAVE);
		assertEquals(Fight.startBattle(combatants).getVictor(), Fight.OUTCOME_AUTOBOTS);
	}
	
	@Test
	public void testPredWin() {
		combatants.add(PREDAKING);
		combatants.add(RODIMUS);
		assertEquals(Fight.startBattle(combatants).getVictor(), Fight.OUTCOME_DECEPTICONS);
	}
	
	@Test
	public void testRunaway() {
		combatants.add(STARSCREAM);
		combatants.add(RODIMUS);
		assertTrue(Fight.startBattle(combatants).getLosers().contains(STARSCREAM));
	}
	
	@Test
	public void testOutclassed() {
		combatants.add(STARSCREAM);
		combatants.add(BUMBLEBEE);
		assertEquals(Fight.startBattle(combatants).getVictor(),Fight.OUTCOME_AUTOBOTS);
	}
	
	@Test
	public void testSkillWin() {
		combatants.add(SOUNDWAVE);
		combatants.add(BUMBLEBEE);
		assertEquals(Fight.startBattle(combatants).getVictor(),Fight.OUTCOME_DECEPTICONS);
	}
	
	@Test
	public void testAllDown() {
		combatants.add(OPTIMUS);
		combatants.add(PREDAKING);
		combatants.add(SOUNDWAVE);

		Fight fight = Fight.startBattle(combatants);
		// no one survives Optimus + Predaking
		assertEquals(fight.getLosers().size() + fight.getVictors().size(), 0);
		// the autobots still win for having the most destroyed enemies
		assertEquals(fight.getVictor(), Fight.OUTCOME_AUTOBOTS);
		// there should only be one fight
		assertEquals(fight.getBattles(), 1);
	}
	
	@Test
	public void testOneSided() {
		combatants.add(SOUNDWAVE);
		combatants.add(STARSCREAM);
		// victory is measured by most destroyed opponents
		assertEquals(Fight.startBattle(combatants).getVictor(),Fight.OUTCOME_TIE);
	}
	
	@Test
	public void testEmptyFight() {
		assertEquals(Fight.startBattle(combatants).getVictor(),Fight.OUTCOME_TIE);
		assertEquals(Fight.startBattle(null).getVictor(),Fight.OUTCOME_TIE);
	}
	
	@Test
	public void testMatchup() {
		combatants.add(BUMBLEBEE);
		combatants.add(STARSCREAM);
		combatants.add(RODIMUS);
		Fight fight = Fight.startBattle(combatants);
		
		assertTrue(fight.getVictors().contains(BUMBLEBEE));
		assertEquals(fight.getBattles(), 1);
	}
	
	@Test
	public void testTie() {
		combatants.add(BUMBLEBEE);
		combatants.add(SOUNDWAVE);
		combatants.add(RODIMUS);
		Fight fight = Fight.startBattle(combatants);
		
		assertEquals(fight.getVictor(),Fight.OUTCOME_TIE);
		// in the case of a tie the losing forces have all the survivors
		assertTrue(fight.getLosers().contains(BUMBLEBEE));
	}
	
}
