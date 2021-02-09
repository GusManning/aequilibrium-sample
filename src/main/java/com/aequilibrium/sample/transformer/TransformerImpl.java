package com.aequilibrium.sample.transformer;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "TRANSFORMER")
public class TransformerImpl implements Transformer {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="NAME",length=100,nullable=false)
	private String name = "";
	@Column(name="FACTION",nullable=false)
	private char faction;
	@ElementCollection
	private Map<Attribute,Byte> attributes = new HashMap<>();
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		if (name != null && name.length() <= 100) {
			this.name = name;
		}
	}

	@Override
	public char getFaction() {
		return faction;
	}

	@Override
	public void setFaction(char faction) {
		if (faction == Transformer.AUTOBOT || faction == Transformer.DECEPTICON) {
			this.faction = faction;
		}
	}

	@Override
	public void setAttribute(Attribute attribute, byte value) {
		if( value > 10 ) {
			value = 10;
		} else if ( value < 0 ) {
			value = 0;
		}
		
		attributes.put(attribute, value);
	}

	@Override
	public Byte getAttribute(Attribute attribute) {
		return attributes.get(attribute);
	}

	@Override
	public Map<Attribute, Byte> getAttributes() {
		return attributes;
	}

}