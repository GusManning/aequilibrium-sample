package com.aequilibrium.sample.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransformerTest {

	private Transformer transformer;
	
	@BeforeEach
	private void setupTransformer() {
		transformer = Transformer.blankTransformer();
	}
	
	@Test
	public void testName() {
		transformer.setName("test");
		assertEquals(transformer.getName(),"test");
	}
	
	@Test
	public void testNameFail() {
		transformer.setName(null);
		assertNotNull(transformer.getName());
	}
	
	@Test
	public void testFaction() {
		transformer.setFaction(Transformer.DECEPTICON);
		assertEquals(transformer.getFaction(),Transformer.DECEPTICON);
	}
	
	@Test
	public void testFactionFail() {
		transformer.setFaction(Transformer.AUTOBOT);
		transformer.setFaction('Z');
		assertNotEquals(transformer.getFaction(),'Z');
	}
	
	@Test
	public void testStat() {
		transformer.setStat(Transformer.Stats.COURAGE,(byte) 5);
		assertEquals(transformer.getStat(Transformer.Stats.COURAGE),(byte) 5);
		assertEquals(transformer.getStats().size(), 1);
	}
	
	@Test
	public void testStatFail() {
		// cap Stats above 10 at 10
		transformer.setStat(Transformer.Stats.COURAGE,(byte) 15);
		assertEquals(transformer.getStat(Transformer.Stats.COURAGE),(byte) 10);
		// cap Stats below 1 at 1
		transformer.setStat(Transformer.Stats.COURAGE,(byte) -5);
		assertEquals(transformer.getStat(Transformer.Stats.COURAGE),(byte) 1);
	}
	
}
