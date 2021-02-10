package com.aequilibrium.sample.transformer;

import java.util.List;
import java.util.Optional;

public interface TransformerService {
	
	/**
	 * @return a List of all transformers in the database
	 */
	public List<Transformer> getAllTransformers();
	
	/**
	 * 
	 * @param id A Long identifying a single record
	 * @return A Transformer in an Optional that matches id.
	 */
	public Optional<Transformer> getTransformer(long id);
	
	/**
	 * Returns all Transformers by id, ids not found will not be included.
	 * @param ids a List of ids identifying several Transformers
	 * @return a List of all Transformers with ids matching the list passed.
	 */
	public List<Transformer> getTransformersById(List<Long> ids);
	
	/**
	 * 
	 * @param id of Transformer to be deleted from the Database
	 * @return true on successful deletion
	 */
	public boolean deleteTransformer(long id);
	
	/**
	 * Creator for transformers, all transformer stats and variables must be defined with only id null.
	 * @param save Transformer to be created or overwritten
	 * @return true if action was successful
	 */
	public boolean saveTransformer(Transformer save);
	
	/**
	 * Updater for transformer, can handle partial updates, if a value is not provided it will not be changed.
	 * @param id of Transformer to save
	 * @param save Transformer to be updated, note that id will not be updated and must match other param.
	 * @return true of action is successful.
	 */
	public boolean updateTransformer(long id, Transformer save);
}
