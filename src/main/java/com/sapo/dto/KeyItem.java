package com.sapo.dto;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class KeyItem {
	private Long itemid;
	private Long shopid;
	@JsonIgnore
	@Transient
	@Column(nullable = true)
	private String ads_keyword;
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public Long getShopid() {
		return shopid;
	}
	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}
	public String getAds_keyword() {
		return ads_keyword;
	}
	public void setAds_keyword(String ads_keyword) {
		this.ads_keyword = ads_keyword;
	}
	
}
