package com.aequilibrium.sample.transformer.fight;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.aequilibrium.sample.transformer.Transformer;
import com.aequilibrium.sample.transformer.Transformer.Attribute;

public class FightImpl implements Fight {

	private List<Transformer> autobots = new ArrayList<>();
	private List<Transformer> decepticons = new ArrayList<>();
	private int autobotWins = 0;
	private int decepticonWins = 0;
	private int battles = 0;
	
	public FightImpl(List<Transformer> combatants) {
		if ( combatants == null || combatants.isEmpty() ) {
			return;
		}
		//TODO confirm order
		combatants.sort(Comparator.comparingInt((Transformer t) -> ((int) t.getAttribute(Attribute.RANK))).reversed());
		
		// sort combatants into Autobot and Decepticon
		for(Transformer bot:combatants) {
			switch(bot.getFaction()) {
				case Transformer.AUTOBOT:
					autobots.add(bot);
					break;
				case Transformer.DECEPTICON:
					decepticons.add(bot);
					break;
				default:
			}
		}
		
		resolve();
	}
	
	private void resolve() {
		// Calculate number of battles
		battles = Math.min(autobots.size(), decepticons.size());
		// process battles in reverse order removing combatants from the list as we go.
		for(int battle=battles-1;battle >= 0;battle--) {
			// Optimus-Predaking matchups which devastate both sides
			if(isPrime(autobots.get(battle)) && isPrime(decepticons.get(battle))) {
				autobotWins = decepticons.size();
				decepticonWins = autobots.size();
				decepticons.clear();
				autobots.clear();
				return;
			}
			
			matchUp(battle);
		}
	}
	
	private void matchUp(int battle) {
		Transformer autobot = autobots.get(battle);
		Transformer decepticon = decepticons.get(battle);
		
		// see if either is Optimus or Predaking
		if(isPrime(autobot)) { 
			autobotWin(battle);
			return;
		} else if(isPrime(decepticon)) {
			decepticonWin(battle);
			return;
		}
		
		// check if one side or the other will run
		if(willRun(autobot, decepticon)) {
			decepticonWins++;
			return;
		} else if(willRun(decepticon, autobot)) {
			autobotWins++;
			return;
		}
		
		int autobotSkill = 0,decepticonSkill = 0, autobotOverSkill = 0, decepticonOverSkill = 0;
		for(Attribute skill:Transformer.SKILLS) {
			autobotSkill += autobot.getAttribute(skill);
			decepticonSkill += decepticon.getAttribute(skill);
			
			int compare = autobot.getAttribute(skill) - decepticon.getAttribute(skill);
			if (compare <= -3) {
				decepticonOverSkill++;
			} else if (compare >= 3) {
				autobotOverSkill++;
			}
		}
		
		// comparison of number of skills 3 points or more over opponent
		if(autobotOverSkill > decepticonOverSkill) {
			autobotWin(battle);
			return;
		} else if(decepticonOverSkill > autobotOverSkill) {
			decepticonWin(battle);
			return;
		}
		
		// comparison of overall skill level
		if(autobotSkill > decepticonSkill) {
			autobotWin(battle);
			return;
		} else if(decepticonSkill > autobotSkill) {
			decepticonWin(battle);
			return;
		}
		
		// ties destroy both sides
		autobotWin(battle);
		decepticonWin(battle);
	}
	
	private boolean willRun(Transformer runner, Transformer from) {
		if(from.getAttribute(Attribute.COURAGE) - runner.getAttribute(Attribute.COURAGE) >= 4) {
			return from.getAttribute(Attribute.STRENGTH) - runner.getAttribute(Attribute.STRENGTH) >= 3;
		}
		return false;
	}
	
	private void autobotWin(int battle) {
		decepticons.remove(battle);
		autobotWins++;
	}
	
	private void decepticonWin(int battle) {
		autobots.remove(battle);
		decepticonWins++;
	}
	
	private boolean isPrime(Transformer bot) {
		return FightImpl.NAME_OPTIMUS_PRIME.equalsIgnoreCase(bot.getName()) || FightImpl.NAME_PREDAKING.equalsIgnoreCase(bot.getName());
	}
	
	
	@Override
	public List<Transformer> survivingLosers() {
		switch(getVictor()) {
		case Fight.OUTCOME_AUTOBOTS:
			return decepticons;
		case Fight.OUTCOME_DECEPTICONS:
			return autobots;
		default:
			List<Transformer> merged = new ArrayList<>(autobots);
			merged.addAll(decepticons);
			return merged;
		}
	}

	@Override
	public List<Transformer> survivingVictors() {
		switch(getVictor()) {
			case Fight.OUTCOME_AUTOBOTS:
				return autobots;
			case Fight.OUTCOME_DECEPTICONS:
				return decepticons;
			default:
				return List.of();
		}
	}

	@Override
	public int numberOfBattles() {
		return battles;
	}

	@Override
	public String getVictor() {
		if (autobotWins > decepticonWins) {
			return Fight.OUTCOME_AUTOBOTS;
		} else if ( decepticonWins > autobotWins) {
			return Fight.OUTCOME_DECEPTICONS;
		} 
		return Fight.OUTCOME_TIE;
	}

	@Override
	public String getMidroll() {
		switch(getVictor()) {
			case Fight.OUTCOME_AUTOBOTS:
				return Fight.MIDROLL_AUTOBOT;
			case Fight.OUTCOME_DECEPTICONS:
				return Fight.MIDROLL_DECEPTICON;
			default:
				return "";
		}
	}

}
