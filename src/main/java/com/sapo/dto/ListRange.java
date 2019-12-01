package com.sapo.dto;

import java.util.List;

public class ListRange {
	private List<Range> ranks;
	private double medium;
	private int median;
	public List<Range> getRanks() {
		return ranks;
	}
	public void setRanks(List<Range> ranks) {
		this.ranks = ranks;
	}
	public double getMedium() {
		return medium;
	}
	public void setMedium(double medium) {
		this.medium = medium;
	}
	public int getMedian() {
		return median;
	}
	public void setMedian(int median) {
		this.median = median;
	}
	
}
