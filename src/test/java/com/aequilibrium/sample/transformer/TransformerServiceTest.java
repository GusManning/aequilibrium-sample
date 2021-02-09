package com.aequilibrium.sample.transformer;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.aequilibrium.sample.transformer.Transformer.Attribute;
import com.aequilibrium.sample.transformer.persistance.TransformerRepository;

@SpringBootTest
public class TransformerServiceTest {

	@MockBean
	private TransformerRepository repository;
	
	@Autowired
	private TransformerService service;
	
	private List<Long> ids = new ArrayList<>();
	private List<TransformerImpl> transformers = new ArrayList<>();
	
	@BeforeEach
	public void setupMock() {
		ids.add(5L);
		ids.add(9L);
		ids.add(12L);
		
		transformers.add(stubTransformer(5L, "Optimus"));
		transformers.add(stubTransformer(9L, "Bumblebee"));
		transformers.add(stubTransformer(12L, "Soundwave"));
	}
	
	private static TransformerImpl stubTransformer(Long id, String name ) {
		TransformerImpl transformer = new TransformerImpl();
		transformer.setId(id);
		transformer.setName(name);
		transformer.setFaction(Transformer.AUTOBOT);
		
		for(Attribute attr:Transformer.SKILLS) {
			transformer.setAttribute(attr,(byte) 5);
		}
		return transformer;
	}
	
	@Test
	public void testGetAll() {
		Mockito.when(repository.findAll()).thenReturn(transformers);
		
		assertEquals(service.getAllTransformers(),transformers);
	}
	
	@Test
	public void testGetAllById() {
		Mockito.when(repository.findAllById(ids)).thenReturn(transformers);
		
		assertEquals(service.getTransformersById(ids),transformers);
	}
	
	@Test
	public void testGetAllByIdFail() {
		List<Long> badIds = new ArrayList<>();
		badIds.add(4L);
		badIds.add(6L);
		badIds.add(8L);
		
		assertTrue(service.getTransformersById(badIds).isEmpty());
	}
	
	@Test
	public void testGetById() {
		Mockito.when(repository.findById(5L)).thenReturn(Optional.of(transformers.get(0)));
		
		assertEquals(service.getTransformer(5L).get().getName(),transformers.get(0).getName());
	}
	
	@Test
	public void testGetByIdFail() {
		assertTrue(service.getTransformer(6L).isEmpty());
	}
	
	@Test
	public void testSave() {
		assertTrue(service.saveTransformer(transformers.get(0)));
	}
	
	@Test
	public void testSaveFail() {
		Transformer badRobot = new TransformerImpl();
		assertFalse(service.saveTransformer(badRobot));
	}
	
	@Test
	public void testDelete() {
		service.deleteTransformer(5L);
		Mockito.verify(repository,Mockito.times(0)).deleteById(5L);
	}
}
	

