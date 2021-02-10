package com.aequilibrium.sample.transformer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.sample.transformer.Transformer.Attribute;
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
		// we don't save any Transformer with skills lower that 0 or higher than 10
		// no factions that are not Autobot or Decepticon are allowed
		if (save.getAttributes().values().stream().filter((b)->(b > 10 && b < 0 )).count() > 0
				|| save.getName() == null
				|| save.getName().length() > 100
				|| !( save.getFaction() == Transformer.AUTOBOT || save.getFaction() == Transformer.DECEPTICON ) ) {
			return false;
		}
		
		repository.save((TransformerImpl) save);
		return true;
	}
	
	@Override
	public boolean updateTransformer(long id, Transformer update) {
		// this will throw an exception if the old transformer is not found. 
		TransformerImpl old = repository.findById(id).get();
		
		for(Transformer.Attribute attr: Transformer.Attribute.values()) {
			Byte nValue = update.getAttribute(attr);
			if(nValue != null) {
				old.setAttribute(attr, nValue);
			}
		}

		old.setName(update.getName());
		old.setFaction(update.getFaction());
		
		repository.save(old);
		return true;
	}
	
	@PostConstruct
	public void postConstruct() {

		Transformer optimus = Transformer.blankTransformer();
		optimus.setAttribute(Attribute.COURAGE,(byte) 10);
		optimus.setAttribute(Attribute.ENDURANCE,(byte) 10);
		optimus.setAttribute(Attribute.FIREPOWER,(byte) 10);
		optimus.setAttribute(Attribute.INTELLIGENCE,(byte) 10);
		optimus.setAttribute(Attribute.RANK,(byte) 10);
		optimus.setAttribute(Attribute.SPEED,(byte) 7);
		optimus.setAttribute(Attribute.STRENGTH, (byte) 10);
		optimus.setAttribute(Attribute.SKILL, (byte) 9);
		optimus.setFaction(Transformer.AUTOBOT);
		optimus.setName("Optimus Prime");
		
		repository.save((TransformerImpl) optimus);
		
		Transformer megatron = Transformer.blankTransformer();
		megatron.setAttribute(Attribute.COURAGE,(byte) 9);
		megatron.setAttribute(Attribute.ENDURANCE,(byte) 10);
		megatron.setAttribute(Attribute.FIREPOWER,(byte) 10);
		megatron.setAttribute(Attribute.INTELLIGENCE,(byte) 10);
		megatron.setAttribute(Attribute.RANK,(byte) 10);
		megatron.setAttribute(Attribute.SPEED,(byte) 10);
		megatron.setAttribute(Attribute.STRENGTH, (byte) 10);
		megatron.setAttribute(Attribute.SKILL, (byte) 9);
		megatron.setFaction(Transformer.DECEPTICON);
		megatron.setName("Megatron");
		
		repository.save((TransformerImpl) megatron);
		
		Transformer ratchet = Transformer.blankTransformer();
		ratchet.setAttribute(Attribute.COURAGE,(byte) 10);
		ratchet.setAttribute(Attribute.ENDURANCE,(byte) 9);
		ratchet.setAttribute(Attribute.FIREPOWER,(byte) 3);
		ratchet.setAttribute(Attribute.INTELLIGENCE,(byte) 9);
		ratchet.setAttribute(Attribute.RANK,(byte) 5);
		ratchet.setAttribute(Attribute.SPEED,(byte) 6);
		ratchet.setAttribute(Attribute.STRENGTH, (byte) 4);
		ratchet.setAttribute(Attribute.SKILL, (byte) 10);
		ratchet.setFaction(Transformer.AUTOBOT);
		ratchet.setName("Ratchet");
		
		repository.save((TransformerImpl) ratchet);
		
		Transformer soundwave = Transformer.blankTransformer();
		soundwave.setAttribute(Attribute.COURAGE,(byte) 9);
		soundwave.setAttribute(Attribute.ENDURANCE,(byte) 9);
		soundwave.setAttribute(Attribute.FIREPOWER,(byte) 6);
		soundwave.setAttribute(Attribute.INTELLIGENCE,(byte) 6);
		soundwave.setAttribute(Attribute.RANK,(byte) 8);
		soundwave.setAttribute(Attribute.SPEED,(byte) 5);
		soundwave.setAttribute(Attribute.STRENGTH, (byte) 8);
		soundwave.setAttribute(Attribute.SKILL, (byte) 5);
		soundwave.setFaction(Transformer.DECEPTICON);
		soundwave.setName("Soundwave");
		
		repository.save((TransformerImpl) soundwave);
		
		Transformer ironhide = Transformer.blankTransformer();
		ironhide.setAttribute(Attribute.COURAGE,(byte) 8);
		ironhide.setAttribute(Attribute.ENDURANCE,(byte) 10);
		ironhide.setAttribute(Attribute.FIREPOWER,(byte) 10);
		ironhide.setAttribute(Attribute.INTELLIGENCE,(byte) 10);
		ironhide.setAttribute(Attribute.RANK,(byte) 9);
		ironhide.setAttribute(Attribute.SPEED,(byte) 8);
		ironhide.setAttribute(Attribute.STRENGTH, (byte) 9);
		ironhide.setAttribute(Attribute.SKILL, (byte) 9);
		ironhide.setFaction(Transformer.AUTOBOT);
		ironhide.setName("Ironhide");
		
		repository.save((TransformerImpl) ironhide);
		
		Transformer astrotrain = Transformer.blankTransformer();
		astrotrain.setAttribute(Attribute.COURAGE,(byte) 7);
		astrotrain.setAttribute(Attribute.ENDURANCE,(byte) 7);
		astrotrain.setAttribute(Attribute.FIREPOWER,(byte) 6);
		astrotrain.setAttribute(Attribute.INTELLIGENCE,(byte) 7);
		astrotrain.setAttribute(Attribute.RANK,(byte) 6);
		astrotrain.setAttribute(Attribute.SPEED,(byte) 10);
		astrotrain.setAttribute(Attribute.STRENGTH, (byte) 9);
		astrotrain.setAttribute(Attribute.SKILL, (byte) 8);
		astrotrain.setFaction(Transformer.DECEPTICON);
		astrotrain.setName("Astrotrain");
		
		repository.save((TransformerImpl) astrotrain);
		
	}


}
