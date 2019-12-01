package com.sapo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPrice {
	@Id
	@GeneratedValue
	private Long id;
	private Calendar date;
	@Transient
	private String time;
	private double price;
	@JsonIgnore
	private boolean auto;
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
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public boolean isAuto() {
		return auto;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public String getTime() {
		Calendar date = this.date;
//		date.add(Calendar.HOUR, -7);
		return (date.get(Calendar.DAY_OF_MONTH))+"/"+(date.get(Calendar.MONTH)+1)+"/"+
				date.get(Calendar.YEAR)+"_"+date.get(Calendar.HOUR_OF_DAY);
	}
	
}
