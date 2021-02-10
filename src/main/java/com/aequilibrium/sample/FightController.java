package com.aequilibrium.sample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.sample.transformer.TransformerService;
import com.aequilibrium.sample.transformer.fight.Fight;

@RestController
@RequestMapping("/api/fight")
public class FightController {

	@Autowired
	private TransformerService transformerService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Fight create(@RequestBody List<Long> ids ) {
		return Fight.startBattle(transformerService.getTransformersById(ids));
	}
	
}
