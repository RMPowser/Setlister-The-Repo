package com.example.setlisterattempt2;

import java.util.List;


public class Set {
    private List<SongEntry> songEntries;

    public void AddNewSongEntry(SongEntry song){
        songEntries.add(song);
    }

    public void RemoveSongEntryAtIndex(int index){
        songEntries.remove(index);
    }
}
