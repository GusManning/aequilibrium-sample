package com.aequilibrium.sample.transformer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.sample.transformer.Transformer.Stats;
import com.aequilibrium.sample.transformer.persistance.TransformerRepository;

@Service
public class TransformerServiceImpl implements TransformerService {

	@Autowired
	private TransformerRepository repository;
	
	@Override
	public List<Transformer> getAllTransformers() {
		return repository.findAll().stream().map((t) ->((Transformer) t)).collect(Collectors.toList());
	}

	@Override
	public Optional<Transformer> getTransformer(long id) {
		return repository.findById(id).flatMap((t) -> Optional.of((Transformer) t));
	}

	@Override
	public List<Transformer> getTransformersById(List<Long> ids) {
		// TODO test to see if they include null for not found ids, if so filter nulls
		return repository.findAllById(ids).stream().map((t) ->((Transformer) t)).collect(Collectors.toList());
	}

	@Override
	public boolean deleteTransformer(long id) {
		if(!repository.existsById(id)) {
			return false;
		}

		repository.deleteById(id);
		return true;
	}

	@Override
	public boolean saveTransformer(Transformer save) {
		if (save == null) {
			return false;
		}
		// we don't save any Transformer with skills lower that 1 or higher than 10
		// no factions that are not Autobot or Decepticon are allowed
		if (save.getStats().values().stream().filter((b)->(b > 10 && b < 1 )).count() > 0
				|| save.getName() == null
				|| save.getName().length() > 100
				|| !( save.getFaction() == Transformer.AUTOBOT || save.getFaction() == Transformer.DECEPTICON )
				|| save.getStats().size() != Transformer.Stats.values().length ) {
			return false;
		}
		
		repository.save((TransformerImpl) save);
		return true;
	}
	
	@Override
	public boolean updateTransformer(Transformer update) {
		if (update == null) {
			return false;
		}
		
		Optional<TransformerImpl> opt = repository.findById(update.getId());
		if (opt.isEmpty()) {
			return false;
		}
		
		TransformerImpl old = opt.get();
		
		// update the old transformer with new stats
		for(Transformer.Stats stat: Transformer.Stats.values()) {
			Byte nValue = update.getStat(stat);
			if(nValue != null) {
				old.setStat(stat, nValue);
			}
		}

		old.setName(update.getName());
		old.setFaction(update.getFaction());
		
		repository.save(old);
		return true;
	}
	
	@PostConstruct
	public void postConstruct() {
		// create six default transformers from canon tech sheets
		Transformer optimus = Transformer.blankTransformer();
		optimus.setStat(Stats.COURAGE,(byte) 10);
		optimus.setStat(Stats.ENDURANCE,(byte) 10);
		optimus.setStat(Stats.FIREPOWER,(byte) 10);
		optimus.setStat(Stats.INTELLIGENCE,(byte) 10);
		optimus.setStat(Stats.RANK,(byte) 10);
		optimus.setStat(Stats.SPEED,(byte) 7);
		optimus.setStat(Stats.STRENGTH, (byte) 10);
		optimus.setStat(Stats.SKILL, (byte) 9);
		optimus.setFaction(Transformer.AUTOBOT);
		optimus.setName("Optimus Prime");
		
		repository.save((TransformerImpl) optimus);
		
		Transformer megatron = Transformer.blankTransformer();
		megatron.setStat(Stats.COURAGE,(byte) 9);
		megatron.setStat(Stats.ENDURANCE,(byte) 10);
		megatron.setStat(Stats.FIREPOWER,(byte) 10);
		megatron.setStat(Stats.INTELLIGENCE,(byte) 10);
		megatron.setStat(Stats.RANK,(byte) 10);
		megatron.setStat(Stats.SPEED,(byte) 10);
		megatron.setStat(Stats.STRENGTH, (byte) 10);
		megatron.setStat(Stats.SKILL, (byte) 9);
		megatron.setFaction(Transformer.DECEPTICON);
		megatron.setName("Megatron");
		
		repository.save((TransformerImpl) megatron);
		
		Transformer ratchet = Transformer.blankTransformer();
		ratchet.setStat(Stats.COURAGE,(byte) 10);
		ratchet.setStat(Stats.ENDURANCE,(byte) 9);
		ratchet.setStat(Stats.FIREPOWER,(byte) 3);
		ratchet.setStat(Stats.INTELLIGENCE,(byte) 9);
		ratchet.setStat(Stats.RANK,(byte) 5);
		ratchet.setStat(Stats.SPEED,(byte) 6);
		ratchet.setStat(Stats.STRENGTH, (byte) 4);
		ratchet.setStat(Stats.SKILL, (byte) 10);
		ratchet.setFaction(Transformer.AUTOBOT);
		ratchet.setName("Ratchet");
		
		repository.save((TransformerImpl) ratchet);
		
		Transformer soundwave = Transformer.blankTransformer();
		soundwave.setStat(Stats.COURAGE,(byte) 9);
		soundwave.setStat(Stats.ENDURANCE,(byte) 9);
		soundwave.setStat(Stats.FIREPOWER,(byte) 6);
		soundwave.setStat(Stats.INTELLIGENCE,(byte) 6);
		soundwave.setStat(Stats.RANK,(byte) 8);
		soundwave.setStat(Stats.SPEED,(byte) 5);
		soundwave.setStat(Stats.STRENGTH, (byte) 8);
		soundwave.setStat(Stats.SKILL, (byte) 5);
		soundwave.setFaction(Transformer.DECEPTICON);
		soundwave.setName("Soundwave");
		
		repository.save((TransformerImpl) soundwave);
		
		Transformer ironhide = Transformer.blankTransformer();
		ironhide.setStat(Stats.COURAGE,(byte) 8);
		ironhide.setStat(Stats.ENDURANCE,(byte) 10);
		ironhide.setStat(Stats.FIREPOWER,(byte) 10);
		ironhide.setStat(Stats.INTELLIGENCE,(byte) 10);
		ironhide.setStat(Stats.RANK,(byte) 9);
		ironhide.setStat(Stats.SPEED,(byte) 8);
		ironhide.setStat(Stats.STRENGTH, (byte) 9);
		ironhide.setStat(Stats.SKILL, (byte) 9);
		ironhide.setFaction(Transformer.AUTOBOT);
		ironhide.setName("Ironhide");
		
		repository.save((TransformerImpl) ironhide);
		
		Transformer astrotrain = Transformer.blankTransformer();
		astrotrain.setStat(Stats.COURAGE,(byte) 7);
		astrotrain.setStat(Stats.ENDURANCE,(byte) 7);
		astrotrain.setStat(Stats.FIREPOWER,(byte) 6);
		astrotrain.setStat(Stats.INTELLIGENCE,(byte) 7);
		astrotrain.setStat(Stats.RANK,(byte) 6);
		astrotrain.setStat(Stats.SPEED,(byte) 10);
		astrotrain.setStat(Stats.STRENGTH, (byte) 9);
		astrotrain.setStat(Stats.SKILL, (byte) 8);
		astrotrain.setFaction(Transformer.DECEPTICON);
		astrotrain.setName("Astrotrain");
		
		repository.save((TransformerImpl) astrotrain);
		
	}


}
