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
		RedistributeButtons();
	}
	
	public ArrayList<SetlistEntity> getSongs() {
		return songs;
	}
	
	public void AddSong(String title, String artist, String length, String keySignature) {
		SongEntry song = new SongEntry(title, artist, length, keySignature);
		songs.add(song);
		RedistributeButtons();
	}
	
	public void InsertSong(int index, String title, String artist, String length, String keySignature) {
		SongEntry song = new SongEntry(title, artist, length, keySignature);
		songs.add(index, song);
		RedistributeButtons();
	}
	
	public void AddSet() {
		Set set = new Set();
		CountOfSets++;
		set.setSetIndex(CountOfSets);
		songs.add(set);
		RedistributeButtons();
	}
	
	public void InsertSet(int index) {
		// add the new set in. index will be wrong
		Set set = new Set();
		CountOfSets++;
		set.setSetIndex(CountOfSets);
		songs.add(index, set);
		
		int newIndexForSets = 1; // new index starts at 1
		
		// correct all set indices using new index
		for (int i = 0; i < songs.size(); i++) {
			if (songs.get(i) instanceof Set){
				((Set) songs.get(i)).setSetIndex(newIndexForSets);
				newIndexForSets++;
			}
		}
		
		RedistributeButtons();
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
			
			while (songs.get(index) instanceof SongEntry) {
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
		RedistributeButtons();
	}
	
	public void Clear() {
		CountOfSets = 0;
		songs.clear();
		AddHeader("New Setlist", "Jan 1st", "8PM");
		RedistributeButtons();
	}
	
	private void RedistributeButtons() { // makes sure these two functions are always called in
		// this order
		MakeSureThereIsOneAddSetButtonPerSet();
		MakeSureThereIsOneAddSongEntryButtonPerSet();
	}
	
	private void MakeSureThereIsOneAddSetButtonPerSet() { // not to be used outside
		// of RedistributeButtons()
		// first get rid of all the AddSet buttons
		for (int i = 0; i < songs.size(); i++) {
			if (songs.get(i) instanceof ButtonAddSet) {
				songs.remove(i);
				i--;
			}
		}
		
		// then, add them back in at the right spots
		for (int i = 0; i < songs.size(); i++) {
			if (songs.get(i) instanceof Set) {
				ButtonAddSet button = new ButtonAddSet();
				songs.add(i, button);
				i++;
			}
		}
		// finally, put one at the very end
		ButtonAddSet button = new ButtonAddSet();
		songs.add(button);
	}
	
	private void MakeSureThereIsOneAddSongEntryButtonPerSet() { // not to be used outside
		// of RedistributeButtons()
		// first get rid of all the AddSongEntry buttons
		for (int i = 0; i < songs.size(); i++) {
			if (songs.get(i) instanceof ButtonAddSongEntry) {
				songs.remove(i);
				i--;
			}
		}
		
		// then, add them back in at the right spots
		for (int i = 0; i < songs.size(); i++) {
			if ((songs.get(i) instanceof Set || songs.get(i) instanceof ButtonAddSet) && i != 0) {
				if (!(songs.get(i - 1) instanceof ButtonAddSongEntry) && !(songs.get(i - 1) instanceof ButtonAddSet) && !(songs.get(i - 1) instanceof SetlistHeader)) {
					ButtonAddSongEntry button = new ButtonAddSongEntry();
					songs.add(i, button);
					i++;
				}
			}
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
