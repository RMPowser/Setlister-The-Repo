package com.example.Setlister.SetlistObjects;

import android.os.Parcel;
import android.os.Parcelable;

// this class exists as a placeholder for the ViewHolder to see
public class ButtonAddSet extends SetlistEntity implements Parcelable {
	
	public ButtonAddSet(){
		super("buttonAddSet");
	}
	
	@Override
	public String toString() {
		return "ButtonAddSet{}";
	}
	
	// parcelable functions below this point
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}
	
	protected ButtonAddSet(Parcel in) {
		super("buttonAddSet");
	}
	
	public static final Parcelable.Creator<ButtonAddSet> CREATOR = new Parcelable.Creator<ButtonAddSet>() {
		@Override
		public ButtonAddSet createFromParcel(Parcel source) {
			return new ButtonAddSet(source);
		}
		
		@Override
		public ButtonAddSet[] newArray(int size) {
			return new ButtonAddSet[size];
		}
	};
}
