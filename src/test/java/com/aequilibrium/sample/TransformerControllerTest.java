package com.aequilibrium.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import com.aequilibrium.sample.transformer.Transformer;
import com.aequilibrium.sample.transformer.TransformerService;

@SpringBootTest
public class TransformerControllerTest {

	@MockBean
	private TransformerService service;
	
	@Captor
	private ArgumentCaptor<Transformer> transformerCaptor;
	@Captor
	private ArgumentCaptor<Long> idCaptor;
	
	@Autowired
	private TransformerController controller;
	
	private List<Transformer> transformers;
	
	@BeforeEach
	private void setupTransformers() {
		transformers = new ArrayList<Transformer>();
		transformers.add(stubTransformer(1L,"Optimus"));
		transformers.add(stubTransformer(2L,"Megatron"));
		transformers.add(stubTransformer(3L,"Bumblebee"));
		transformers.add(stubTransformer(4L,"Starscreem"));
	}
	
	private static Transformer stubTransformer(Long id, String name) {
		Transformer transformer = Transformer.blankTransformer();
		transformer.setId(id);
		transformer.setName(name);
		
		return transformer;
	}
	
	@Test
	public void testGetAll() {
		Mockito.when(service.getAllTransformers()).thenReturn(transformers);
		assertEquals(controller.findAll(),transformers);
	}
	
	@Test
	public void testGetById() {
		Mockito.when(service.getTransformer(1L)).thenReturn(Optional.of(transformers.get(0)));
		assertEquals(controller.findById(1L),transformers.get(0));
	}
	
	@Test
	public void testGetByIdFail() {
		Mockito.when(service.getTransformer(1L)).thenReturn(Optional.empty());
		assertThrows(ResponseStatusException.class, () -> {
			controller.findById(1L);
		});
	}
	
	@Test
	public void testUpdate() {
		Mockito.when(service.updateTransformer(transformers.get(0))).thenReturn(true);
		controller.update(transformers.get(0));
		Mockito.verify(service).updateTransformer(transformerCaptor.capture());
		Transformer captured = transformerCaptor.getValue();
		assertEquals(captured, transformers.get(0));
	}
	
	@Test
	public void testUpdateFail() {
		Mockito.when(service.updateTransformer(Mockito.any())).thenReturn(false);
		assertThrows(ResponseStatusException.class, () -> {
			controller.update(transformers.get(2));
		});
		Transformer noId = Transformer.blankTransformer();
		assertThrows(ResponseStatusException.class, () -> {
			controller.update(noId);
		});
	}
	
	@Test
	public void testDelete() {
		Long id = 5L;
		Mockito.when(service.deleteTransformer(id)).thenReturn(true);
		controller.delete(id);
		Mockito.verify(service).deleteTransformer(idCaptor.capture());
		Long captured = idCaptor.getValue();
		assertEquals(id,captured);
	}
	
	@Test
	public void testCreate() {
		Transformer transformer = Transformer.blankTransformer();
		transformer.setName("test");
		Mockito.when(service.saveTransformer(transformer)).thenReturn(true);
		controller.create(transformer);
		Mockito.verify(service).saveTransformer(transformerCaptor.capture());
		Transformer captured = transformerCaptor.getValue();
		assertEquals(transformer,captured);
	}

	
}
