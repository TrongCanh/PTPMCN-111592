package com.sapo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sapo.dto.Shop_covers;

@Entity
@Table(name = "shop")
public class Shop {
	@Id
	private Long shopid;
	@Column(nullable = true)
	private String name;
	@Column(nullable = true)
	private String images[];
	@Column(nullable = true)
	private String place;
	@Column(nullable = true)
	private int rating_good;
	@Column(nullable = true)
	private int rating_bad;
	@Column(nullable = true)
	private int rating_normal;
	@Column(nullable = true)
	private int follower_count;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	@JsonIgnore
	private User user;
	@Column(nullable = true)
	private float rating_star;
	@Column(nullable = true)
	private int[] rating_count;
	@JsonIgnore
	@Transient
	@Column(nullable = true)
	private Rating buyer_rating;
	@JsonIgnore
	@Transient
	@Column(nullable = true)
	private List<Shop_covers> shop_covers;
	@Column(nullable = true)
	@Transient
	private Long itemid;
	@Column(nullable = true)
	@Transient
	@JsonIgnore
	private Account account;
	@Column(nullable = true)
	private String portrait;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getRating_good() {
		return rating_good;
	}

	public void setRating_good(int rating_good) {
		this.rating_good = rating_good;
	}

	public int getRating_bad() {
		return rating_bad;
	}

	public void setRating_bad(int rating_bad) {
		this.rating_bad = rating_bad;
	}

	public int getRating_normal() {
		return rating_normal;
	}

	public void setRating_normal(int rating_normal) {
		this.rating_normal = rating_normal;
	}

	public float getRating_star() {
		return rating_star;
	}

	public void setRating_star(float rating_star) {
		this.rating_star = rating_star;
	}

	public int[] getRating_count() {
		return rating_count;
	}

	public void setRating_count(int[] rating_count) {
		this.rating_count = rating_count;
	}

	public int getFollower_count() {
		return follower_count;
	}

	public void setFollower_count(int follower_count) {
		this.follower_count = follower_count;
	}

	public Rating getBuyer_rating() {
		return buyer_rating;
	}

	public void setBuyer_rating(Rating buyer_rating) {
		this.buyer_rating = buyer_rating;
	}

	public List<Shop_covers> getShop_covers() {
		return shop_covers;
	}

	public void setShop_covers(List<Shop_covers> shop_covers) {
		this.shop_covers = shop_covers;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

}
