package com.aequilibrium.sample.transformer;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.aequilibrium.sample.transformer.Transformer.Attribute;
import com.aequilibrium.sample.transformer.persistance.TransformerRepository;

public class TransformerServiceTest {

	@Autowired
	private TransformerService service;
	
	@MockBean
	private TransformerRepository repository;
	
	private List<Long> ids = new ArrayList<>();
	private List<Transformer> transformers = new ArrayList<>();
	
	@BeforeEach
	public void setupMock() {
		ids.add(5L);
		ids.add(9L);
		ids.add(12L);
		
		transformers.add(stubTransformer(5L, "Optimus"));
		transformers.add(stubTransformer(9L, "Bumblebee"));
		transformers.add(stubTransformer(12L, "Soundwave"));
	}
	
	private static Transformer stubTransformer(Long id, String name ) {
		return new Transformer() {
			Long tId = id;
			String tName = name;
			Map<Attribute, Byte> attrs = null;
			@Override
			public Long getId() {
				return tId;
			}
			@Override
			public String getName() {
				return tName;
			}
			@Override
			public void setName(String name) {}
			@Override
			public char getFaction() {return Transformer.AUTOBOT;}
			@Override
			public void setFaction(char faction) {}
			@Override
			public void setAttribute(Attribute attribute, byte value) {mockAttributes().put(attribute, value);}
			@Override
			public Byte getAttribute(Attribute attribute) {return mockAttributes().get(attribute);} 
			@Override
			public Map<Attribute, Byte> getAttributes() {return mockAttributes();}
			private Map<Attribute, Byte> mockAttributes() {
				if ( attrs == null ) {
					attrs = new HashMap<>();
					for(Attribute attr : Transformer.Attribute.values()) {
						attrs.put(attr,(byte) 5);
					}
				}
				return attrs;
			}
		};
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
		
		assertEquals(service.getTransformer(5L).getName(),transformers.get(0).getName());
	}
	
	@Test
	public void testGetByIdFail() {
		assertNull(service.getTransformer(6L));
	}
	
	@Test
	public void testSave() {
		ArgumentCaptor<Transformer> capture = ArgumentCaptor.forClass(Transformer.class);
		Mockito.doNothing().when(repository).save(capture.capture());
		service.saveTransformer(transformers.get(0));
		assertEquals(transformers.get(0),capture.getValue());
	}
	
	@Test
	public void testSaveFail() {
		Transformer badRobot = stubTransformer(2L, "fail");
		badRobot.setAttribute(Attribute.COURAGE,(byte) -5);
		service.saveTransformer(null);
		service.saveTransformer(badRobot);
		Mockito.verify(repository,Mockito.times(0)).save(Mockito.any());
	}
	
	@Test
	public void testDelete() {
		service.deleteTransformer(5L);
		Mockito.verify(repository,Mockito.times(0)).deleteById(5L);
	}
}
	

