package com.aequilibrium.sample;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.sample.transformer.Transformer;
import com.aequilibrium.sample.transformer.TransformerService;

@RestController
@RequestMapping("/api/transformers")
public class TransformerController {
	
	@Autowired
	private TransformerService transformerService;
	
	@GetMapping
	public List<Transformer> findAll() {
		return transformerService.getAllTransformers();
	}
	
	@GetMapping(value = "/{id}") 
	public Transformer findById(@PathVariable("id") Long id){
		if(id == null) {
			throw new NoSuchElementException();
		}
		return transformerService.getTransformer(id).get();
	}
	
	@PutMapping(value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable( "id" ) Long id, @RequestBody Transformer resource ) {
		if (resource.getId() != null && id != resource.getId()) {
			throw new IllegalArgumentException("cannot update id of Transformer");
		}
		
		transformerService.updateTransformer(id, resource);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Transformer resource ) {
		transformerService.saveTransformer(resource);
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {
		transformerService.deleteTransformer(id);
	}
	
}
