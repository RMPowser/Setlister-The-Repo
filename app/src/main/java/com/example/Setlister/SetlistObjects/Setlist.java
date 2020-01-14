package com.example.Setlister.SetlistObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Setlist implements Parcelable {
	
	// initializing default values for constructor
	private ArrayList<SetlistEntity> songs = new ArrayList<>();
	
	
	// variable counts how many sets are in the setlist. used to
	// determine the number of a new set
	private int CountOfSets = 0;
	
	
	
	public Setlist(String title, String date, String time) {
		// all setlists start with a default header
		AddHeader(title, date, time);
		FixButtons(0);
	}
	
	public ArrayList<SetlistEntity> getSongs() {
		return songs;
	}
	
	public void AddSong(String title, String artist, String length, String keySignature) {
		SongEntry song = new SongEntry(title, artist, length, keySignature);
		songs.add(song);
		FixButtons(0);
	}
	
	public void InsertSong(int index, String title, String artist, String length, String keySignature) {
		SongEntry song = new SongEntry(title, artist, length, keySignature);
		songs.add(index, song);
		FixButtons(index);
	}
	
	public void AddSet() {
		Set set = new Set();
		CountOfSets++;
		set.setSetIndex(CountOfSets);
		songs.add(set);
		FixButtons(0);
	}
	
	public void InsertSet(int index) {
		// add the new set in. index will be wrong
		songs.add(index+1, new Set());
		
		// add the set's buttons
		songs.add(index+2, new ButtonAddSongEntry());
		songs.add(index+3, new ButtonAddSet());
		
		int newIndexForSets = 1; // new index starts at 1
		
		// correct all set indices using new index
		for (int i = 0; i < songs.size(); i++) {
			if (songs.get(i) instanceof Set){
				((Set) songs.get(i)).setSetIndex(newIndexForSets);
				newIndexForSets++;
			}
		}
		
		FixButtons(index);
	}
	
	public void AddHeader(String titleOrLocation, String date, String time) {
		if (songs.size() != 0) {
			if (songs.get(0) instanceof SetlistHeader) {
				((SetlistHeader) songs.get(0)).setTitleOrLocation(titleOrLocation);
				((SetlistHeader) songs.get(0)).setDate(date);
				((SetlistHeader) songs.get(0)).setTime(time);
			} else {
				SetlistHeader header = new SetlistHeader(titleOrLocation, date, time);
				songs.add(0, header);
			}
		} else {
			SetlistHeader header = new SetlistHeader(titleOrLocation, date, time);
			songs.add(header);
		}
	}
	
	public void RemoveAt(int index) {
		if (songs.get(index) instanceof Set) {
			// remove the set itself
			songs.remove(index);
			
			// subtract 1 from the count of all sets
			CountOfSets--;
			
			while (index < songs.size() && !(songs.get(index) instanceof Set)) {
				// then remove everything after it up until the next set
				songs.remove(index);
			}
			
			int newIndexForSets = 1; // new index starts at 1
			
			// correct all set indices using new index
			for (int i = 0; i < songs.size(); i++) {
				if (songs.get(i) instanceof Set){
					((Set) songs.get(i)).setSetIndex(newIndexForSets);
					newIndexForSets++;
				}
			}
			
		} else if (songs.get(index) instanceof SongEntry || songs.get(index) instanceof SetlistHeader) {
			songs.remove(index);
		}
		FixButtons(index);
	}
	
	public void Clear() {
		CountOfSets = 0;
		songs.clear();
		AddHeader("New Setlist", "Jan 1st", "8PM");
		FixButtons(0);
	}
	
	private void FixButtons(int startingIndex) { // makes sure these two functions are always
		// called in this order
		MakeSureThereIsOneAddSetButtonPerSet(startingIndex);
		MakeSureThereIsOneAddSongEntryButtonPerSet(startingIndex);
	}
	
	private void MakeSureThereIsOneAddSetButtonPerSet(int startingIndex) {
		// !!do not use outside of FixButtons!!
		
		// check for and add buttons in correct spots
		for (int i = startingIndex; i < songs.size(); i++) {
			if (i > 0 && songs.get(i) instanceof Set) {
				if (!(songs.get(i-1) instanceof ButtonAddSet)) {
					songs.add(i, new ButtonAddSet());
					i++;
				}
				else {
					if (i-3 >= 0 && songs.get(i-3) instanceof ButtonAddSet) {
						songs.remove(i-1);
					}
				}
			}
		}
		// check for duplicate and ButtonAddSongEntry directly above self
		for (int i = startingIndex; i < songs.size(); i++) {
			if (i > 0 && songs.get(i) instanceof ButtonAddSet && songs.get(i-1) instanceof ButtonAddSet){
				songs.remove(i);
			}
		}
		// check for button at the end
		if (!(songs.get(songs.size() - 1) instanceof ButtonAddSet)){
			songs.add(new ButtonAddSet());
		}
	}
	
	private void MakeSureThereIsOneAddSongEntryButtonPerSet(int startingIndex) {
		// !!do not use outside of FixButtons!!
		
		// check for and add buttons in correct spots
		for (int i = startingIndex; i < songs.size(); i++) {
			if ((songs.get(i) instanceof Set || songs.get(i) instanceof ButtonAddSet) && i != 0) {
				if (!(songs.get(i - 1) instanceof ButtonAddSongEntry) && !(songs.get(i - 1) instanceof ButtonAddSet) && !(songs.get(i - 1) instanceof SetlistHeader)) {
					songs.add(i, new ButtonAddSongEntry());
					i++;
				}
			}
		}
		// check for duplicate and ButtonAddSet directly above self
		for (int i = startingIndex; i < songs.size(); i++) {
			if (i > 0 && songs.get(i) instanceof ButtonAddSongEntry && (songs.get(i-1) instanceof ButtonAddSongEntry || songs.get(i-1) instanceof ButtonAddSet)){
				songs.remove(i);
			}
		}
		// check for button directly after header
		if (songs.get(1) instanceof ButtonAddSongEntry){
			songs.remove(1);
		}
	}
	
	@Override
	public String toString() {
		return "Setlist{" +
				"songs=" + songs +
				"CountOfSets=" + CountOfSets +
				'}';
	}
	
	// parcelable functions below this point
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(this.songs);
		dest.writeInt(this.CountOfSets);
	}
	
	protected Setlist(Parcel in) {
		this.songs = new ArrayList<SetlistEntity>();
		in.readList(this.songs, SetlistEntity.class.getClassLoader());
		this.CountOfSets = in.readInt();
	}
	
	public static final Parcelable.Creator<Setlist> CREATOR = new Parcelable.Creator<Setlist>() {
		@Override
		public Setlist createFromParcel(Parcel source) {
			return new Setlist(source);
		}
		
		@Override
		public Setlist[] newArray(int size) {
			return new Setlist[size];
		}
	};
}
