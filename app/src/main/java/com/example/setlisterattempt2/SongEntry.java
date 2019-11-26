package com.example.setlisterattempt2;

public class SongEntry extends SetlistEntity{

    // initializing default values for constructor
    private String title = "New Song";
    private String artist = "Artist";
    private String length = "3:45";
    private String keySignature = "C#";
    boolean isSet = false;

    public SongEntry(String title, String artist, String length, String keySignature) {
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

    public void setLengthInSeconds(String length) {
        this.length = length;
    }

    public String getKeySignature() {
        return keySignature;
    }

    public void setKeySignature(String keySignature) {
        this.keySignature = keySignature;
    }
}
