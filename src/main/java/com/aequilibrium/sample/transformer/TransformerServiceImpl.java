package com.aequilibrium.sample.transformer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.sample.transformer.persistance.TransformerRepository;

@Service
public class TransformerServiceImpl implements TransformerService {

	@Autowired
	private TransformerRepository repository;
	
	@Override
	public List<Transformer> getAllTransformers() {
		return repository.findAll();
	}

	@Override
	public Optional<Transformer> getTransformer(long id) {
		return repository.findById(id);
	}

	@Override
	public List<Transformer> getTransformersById(List<Long> ids) {
		// TODO test to see if they include null on not found ids, if so filter nulls
		return repository.findAllById(ids);
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
		if (save.getAttributes().values().stream().filter((b)->(b <= 10 && b >= 0 )).count() > 0
				|| save.getName() == null
				|| save.getName().length() > 100
				|| !( save.getFaction() == Transformer.AUTOBOT || save.getFaction() == Transformer.DECEPTICON ) ) {
			return false;
		}
		
		repository.save(save);
		return true;
	}

}
