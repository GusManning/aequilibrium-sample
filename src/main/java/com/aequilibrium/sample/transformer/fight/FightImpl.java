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
		final Transformer autobot = autobots.get(battle);
		final Transformer decepticon = decepticons.get(battle);
		
		// see if either is Optimus or Predaking
		if(isPrime(autobot)) { 
			autobotWin(battle);
			return;
		} else if(isPrime(decepticon)) {
			decepticonWin(battle);
			return;
		}
		
		// check if one side or the other will run
		if(hasRun(autobot, decepticon)) {
			return;
		}
		
		if (autobot.getAttribute(Attribute.SKILL) - decepticon.getAttribute(Attribute.SKILL) >= 3) {
			autobotWin(battle);
			return;
		} else if (decepticon.getAttribute(Attribute.SKILL) - autobot.getAttribute(Attribute.SKILL) >= 3) {
			decepticonWin(battle);
			return;
		}
		
		// this mapping function adds all the overall skills of both combatants into a single value
		int overall = Transformer.OVERALL.stream().mapToInt((a)->((int)(autobot.getAttribute(a)-decepticon.getAttribute(a)))).sum();
		
		// positive value is Autobot victory, negative is decepticon
		if(overall > 0) {
			autobotWin(battle);
			return;
		} else if (overall < 0) {
			decepticonWin(battle);
			return;
		}
		// ties destroy both sides
		autobotWin(battle);
		decepticonWin(battle);
	}
	
	private boolean hasRun(Transformer autobot, Transformer decepticon) {
		int courageDiff = autobot.getAttribute(Attribute.COURAGE) - decepticon.getAttribute(Attribute.COURAGE);
		int strengthDiff = autobot.getAttribute(Attribute.STRENGTH) - decepticon.getAttribute(Attribute.STRENGTH);
		boolean ran = false;
		if(courageDiff >= 4 && strengthDiff >= 3) {
			autobotWins++;
			ran = true;
		} else if (courageDiff <= -4 && strengthDiff <= -3) {
			decepticonWins++;
			ran = true;
		}
		return ran;
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
	public List<Transformer> getLosers() {
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
	public List<Transformer> getVictors() {
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
	public int getBattles() {
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
