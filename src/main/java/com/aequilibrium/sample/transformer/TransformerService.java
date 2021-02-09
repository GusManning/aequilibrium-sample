package com.aequilibrium.sample.transformer;

import java.util.List;

public interface TransformerService {
	
	public List<Transformer> getAllTransformers();
	
	public Transformer getTransformer(long id);
	
	public List<Transformer> getTransformersById(List<Long> ids);
	
	public void deleteTransformer(long id);
	
	public void saveTransformer(Transformer save);
	
}
