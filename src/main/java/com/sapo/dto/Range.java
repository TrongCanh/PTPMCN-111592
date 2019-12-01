package com.sapo.dto;

public class Range {
	private int range;
	private int count;
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Range(int range, int count) {
		super();
		this.range = range;
		this.count = count;
	}
	
}
