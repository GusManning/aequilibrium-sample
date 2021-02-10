package com.aequilibrium.sample;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.server.ResponseStatusException;

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
		Optional<Transformer> transformer= transformerService.getTransformer(id);

		if(transformer.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Transformer not found");
		}
		
		return transformer.get();
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public void update( @RequestBody Transformer resource ) {
		if (resource == null || resource.getId() == null ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Transformer not saved");
		}
		
		if(!transformerService.updateTransformer(resource)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Transformer not found");
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Transformer resource ) {
		if(resource == null || resource.getId() != null || !transformerService.saveTransformer(resource)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Transformer not saved");
		}
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {
		if(!transformerService.deleteTransformer(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Transformer not found");
		}
	}
	
}
