package com.challenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Boat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private Integer version;

	public Boat() {
	}
	public Boat(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public Boat(Long id, String name, String description, Integer version) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.version = version;
	}
	public Boat(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
