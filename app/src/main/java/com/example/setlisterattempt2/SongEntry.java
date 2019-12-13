package com.example.setlisterattempt2;

import android.os.Parcel;
import android.os.Parcelable;

public class SongEntry extends SetlistEntity implements Parcelable {

    // initializing default values for constructor
    private String title = "New Song";
    private String artist = "Artist";
    private String length = "3:45";
    private String keySignature = "C#";

    public SongEntry(String title, String artist, String length, String keySignature) {
        super("song");
        // java doesn't support default parameters like c++ does, so I have to do this instead...
		// makes me sad :C
        if (title != null) { this.title = title; }
        if (artist != null) { this.artist = artist; }
		if (length != null) { this.length = length; }
        if (keySignature != null) { this.keySignature = keySignature; }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getKeySignature() {
        return keySignature;
    }

    public void setKeySignature(String keySignature) {
        this.keySignature = keySignature;
    }
    
    @Override
    public String toString() {
        return "SongEntry{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", length='" + length + '\'' +
                ", keySignature='" + keySignature + '\'' +
                '}';
    }
    
    
    

    
    // parcelable functions below this point
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.artist);
        dest.writeString(this.length);
        dest.writeString(this.keySignature);
    }
    
    protected SongEntry(Parcel in) {
        super("song");
        this.title = in.readString();
        this.artist = in.readString();
        this.length = in.readString();
        this.keySignature = in.readString();
    }
    
    public static final Parcelable.Creator<SongEntry> CREATOR = new Parcelable.Creator<SongEntry>() {
        @Override
        public SongEntry createFromParcel(Parcel source) {
            return new SongEntry(source);
        }
        
        @Override
        public SongEntry[] newArray(int size) {
            return new SongEntry[size];
        }
    };
}
