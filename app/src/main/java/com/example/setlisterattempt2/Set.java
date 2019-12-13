package com.example.setlisterattempt2;

import android.os.Parcel;
import android.os.Parcelable;


public class Set extends SetlistEntity implements Parcelable{
	
	private int setIndex = -1;
	
	public Set() {
		super("set");
	}
	
	public int getSetIndex() {
		return setIndex;
	}
	
	public void setSetIndex(int setIndex) {
		this.setIndex = setIndex;
	}
	
	@Override
	public String toString() {
		return "Set{" +
				"setIndex=" + setIndex +
				'}';
	}
	
	
	
	
	
	// parcelable functions below this point
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.setIndex);
	}
	
	protected Set(Parcel in) {
		super("set");
		this.setIndex = in.readInt();
	}
	
	public static final Parcelable.Creator<Set> CREATOR = new Parcelable.Creator<Set>() {
		@Override
		public Set createFromParcel(Parcel source) {
			return new Set(source);
		}
		
		@Override
		public Set[] newArray(int size) {
			return new Set[size];
		}
	};
}
