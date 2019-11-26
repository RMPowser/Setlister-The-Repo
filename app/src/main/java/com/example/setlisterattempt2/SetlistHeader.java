package com.example.setlisterattempt2;

public class SetlistHeader extends SetlistEntity {
	private String titleOrLocation;
	private String date;
	private String time;
	
	public SetlistHeader(String titleOrLocation, String date, String time) {
		this.titleOrLocation = titleOrLocation;
		this.date = date;
		this.time = time;
	}
	
	public String getTitleOrLocation() {
		return titleOrLocation;
	}
	
	public void setTitleOrLocation(String titleOrLocation) {
		this.titleOrLocation = titleOrLocation;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
}
