package com.sapo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class AutoPrice {
	@Id
	@GeneratedValue
	private Long id;
	private Calendar date;
	private double price;
	private Long itemid;
	private Long rivalid;
	private double oldPrice;
	private String shopRival;
	@Transient
	private String time;
	public double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getRivalid() {
		return rivalid;
	}

	public void setRivalid(Long rivalid) {
		this.rivalid = rivalid;
	}

	public AutoPrice(Calendar date, double price,double oldPrice, Long itemid, Long rivalid) {
		super();
		this.date = date;
		this.oldPrice=oldPrice;
		this.price = price;
		this.itemid = itemid;
		this.rivalid = rivalid;
	}

	public AutoPrice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getShopRival() {
		return shopRival;
	}

	public void setShopRival(String shopRival) {
		this.shopRival = shopRival;
	}
	public String getTime() {
		Calendar date = this.date;
//		date.add(Calendar.HOUR, -7);
		return (date.get(Calendar.DAY_OF_MONTH))+"/"+(date.get(Calendar.MONTH)+1)+"/"+
				date.get(Calendar.YEAR)+"_"+date.get(Calendar.HOUR_OF_DAY);
	}

	public void setTime(String time) {
		this.time = time;
	}
}
