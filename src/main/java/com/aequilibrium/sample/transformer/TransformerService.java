package com.aequilibrium.sample.transformer;

import java.util.List;
import java.util.Optional;

public interface TransformerService {
	
	public List<Transformer> getAllTransformers();
	
	public Optional<Transformer> getTransformer(long id);
	
	public List<Transformer> getTransformersById(List<Long> ids);
	
	public boolean deleteTransformer(long id);
	
	public boolean saveTransformer(Transformer save);
	
	public boolean updateTransformer(long id, Transformer save);
}
