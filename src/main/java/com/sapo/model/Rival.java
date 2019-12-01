package com.sapo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Rival {
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long itemid;
	private long shopid;
	private long rivalShopid;	//shop
	private long rivalItemid;		//item
	private boolean auto;
	private double price;
	private double max;
	private double min;
	public long getId() {
		return id;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItemid() {
		return itemid;
	}
	public void setItemid(long itemid) {
		this.itemid = itemid;
	}
	public boolean isAuto() {
		return auto;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getShopid() {
		return shopid;
	}
	public void setShopid(long shopid) {
		this.shopid = shopid;
	}
	
	public Rival() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getRivalShopid() {
		return rivalShopid;
	}
	public void setRivalShopid(long rivalShopid) {
		this.rivalShopid = rivalShopid;
	}
	public long getRivalItemid() {
		return rivalItemid;
	}
	public void setRivalItemid(long rivalItemid) {
		this.rivalItemid = rivalItemid;
	}
	public Rival(long itemid, long shopid, long rivalShopid, long rivalItemid, boolean auto, double price) {
		super();
		this.itemid = itemid;
		this.shopid = shopid;
		this.rivalShopid = rivalShopid;
		this.rivalItemid = rivalItemid;
		this.auto = auto;
		this.price = price;
	}

	
	
}
