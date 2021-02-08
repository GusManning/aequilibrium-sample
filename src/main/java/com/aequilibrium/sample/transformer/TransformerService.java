package com.aequilibrium.sample.transformer;

import java.util.List;

public interface TransformerService {
	
	public List<Transformer> getAllTransformers();
	
	public List<Transformer> getTransformersById(int[] ids);
	
	public void deleteTransformer(int id);
	
	public Transformer getTransformer(int id);
	
	public void saveTransformer(Transformer save);
	
}
