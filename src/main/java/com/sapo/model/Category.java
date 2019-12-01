package com.sapo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Category {
	@Id
	@GeneratedValue
	private Long id;
	private String display_name;
	private Long catid;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id",nullable = false)
	@JsonIgnore
	private Item item;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public Long getCatid() {
		return catid;
	}
	public void setCatid(Long catid) {
		this.catid = catid;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
