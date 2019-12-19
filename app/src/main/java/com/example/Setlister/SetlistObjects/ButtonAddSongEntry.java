package com.example.Setlister.SetlistObjects;

import android.os.Parcel;
import android.os.Parcelable;

// this class exists as a placeholder for the ViewHolder to see
public class ButtonAddSongEntry extends SetlistEntity implements Parcelable{
	
	public ButtonAddSongEntry() {
		super("buttonAddSong");
	}
	
	@Override
	public String toString() {
		return "ButtonAddSongEntry{}";
	}

	
	
	
	
	// parcelable functions below this point
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}
	
	protected ButtonAddSongEntry(Parcel in) {
		super("buttonAddSong");
	}
	
	public static final Parcelable.Creator<ButtonAddSongEntry> CREATOR = new Parcelable.Creator<ButtonAddSongEntry>() {
		@Override
		public ButtonAddSongEntry createFromParcel(Parcel source) {
			return new ButtonAddSongEntry(source);
		}
		
		@Override
		public ButtonAddSongEntry[] newArray(int size) {
			return new ButtonAddSongEntry[size];
		}
	};
}
