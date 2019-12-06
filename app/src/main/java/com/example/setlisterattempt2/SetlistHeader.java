package com.example.setlisterattempt2;

import android.os.Parcel;
import android.os.Parcelable;

public class SetlistHeader extends SetlistEntity implements Parcelable {
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
	
	
	
	
	
	// parcelable functions below this point
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.titleOrLocation);
		dest.writeString(this.date);
		dest.writeString(this.time);
	}
	
	protected SetlistHeader(Parcel in) {
		this.titleOrLocation = in.readString();
		this.date = in.readString();
		this.time = in.readString();
	}
	
	public static final Parcelable.Creator<SetlistHeader> CREATOR = new Parcelable.Creator<SetlistHeader>() {
		@Override
		public SetlistHeader createFromParcel(Parcel source) {
			return new SetlistHeader(source);
		}
		
		@Override
		public SetlistHeader[] newArray(int size) {
			return new SetlistHeader[size];
		}
	};
}
