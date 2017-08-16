package com.moodmicroservice.bean;

import java.util.List;
import java.util.Set;

public class MoodReportBean {

	private Set<String> chartLabels;
	private List<Integer>greatData;
	private List<Integer>goodData;
	private List<Integer>sadData;
	


	public Set<String> getChartLabels() {
		return chartLabels;
	}

	public void setChartLabels(Set<String> chartLabels) {
		this.chartLabels = chartLabels;
	}

	public List<Integer> getGreatData() {
		return greatData;
	}

	public void setGreatData(List<Integer> greatData) {
		this.greatData = greatData;
	}

	public List<Integer> getGoodData() {
		return goodData;
	}

	public void setGoodData(List<Integer> goodData) {
		this.goodData = goodData;
	}

	public List<Integer> getSadData() {
		return sadData;
	}

	public void setSadData(List<Integer> sadData) {
		this.sadData = sadData;
	}


	
 	
}

 


