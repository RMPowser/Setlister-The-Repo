package com.example.setlisterattempt2;

import android.widget.Button;

import java.util.ArrayList;

public class Setlist {

    // initializing default values for constructor
    private ArrayList<SetlistEntity> songs = new ArrayList<>();
    private int CountOfSets = 0; // variable counts how many sets are in the setlist

    public Setlist(String title, String date, String time){
        AddHeader(title, date, time);
        RedistributeButtons();
    }
    
    public ArrayList<SetlistEntity> getSongs() {
        return songs;
    }
    
    public void AddSong(String title, String artist, String length, String keySignature){
        SongEntry song = new SongEntry(title, artist, length, keySignature);
        songs.add(song);
        RedistributeButtons();
    }
    
    public void InsertSong(int index, String title, String artist, String length, String keySignature){
        SongEntry song = new SongEntry(title, artist, length, keySignature);
        songs.add(index, song);
        RedistributeButtons();
    }

    public void AddSet(){
        Set set = new Set();
        CountOfSets++;
        set.setSetIndex(CountOfSets);
        songs.add(set);
        RedistributeButtons();
    }
    
    public void InsertSet(int index){
        Set set = new Set();
        CountOfSets++;
        set.setSetIndex(CountOfSets);
        songs.add(index, set);
        RedistributeButtons();
    }
    
    public void AddHeader(String titleOrLocation, String date, String time){
        if (songs.size() != 0){
            if (songs.get(0) instanceof SetlistHeader){
                ((SetlistHeader) songs.get(0)).setTitleOrLocation(titleOrLocation);
                ((SetlistHeader) songs.get(0)).setDate(date);
                ((SetlistHeader) songs.get(0)).setTime(time);
            }
            else {
                SetlistHeader header = new SetlistHeader(titleOrLocation, date, time);
                songs.add(0, header);
            }
        }
        else {
            SetlistHeader header = new SetlistHeader(titleOrLocation, date, time);
            songs.add(header);
        }
    }
    
    public void RemoveAt(int index){
        if (songs.get(index) instanceof Set) {
            CountOfSets--;
            songs.remove(index); // remove the set itself

            while (songs.get(index) instanceof SongEntry) {
                // and then remove everything after it up until the next set
                songs.remove(index);
            }
        }
        else if (songs.get(index) instanceof SongEntry || songs.get(index) instanceof SetlistHeader ){
            songs.remove(index);
        }
    }
    
    public void Clear(){
        CountOfSets = 0;
        songs.clear();
        MakeSureThereIsOnlyOneAddSetButtonAndItIsAtTheEnd();
    }
    
    private void RedistributeButtons(){ // makes sure these two functions are always called in
        // this order
        MakeSureThereIsOnlyOneAddSetButtonAndItIsAtTheEnd();
        MakeSureThereIsOneAddSongEntryButtonPerSet();
    }
    
    private void MakeSureThereIsOnlyOneAddSetButtonAndItIsAtTheEnd(){ // not to be used outside
        // of RedistributeButtons()
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i) instanceof ButtonAddSet){
                songs.remove(i);
                i--;
            }
        }
        ButtonAddSet button = new ButtonAddSet();
        songs.add(button);
    }
    
    private void MakeSureThereIsOneAddSongEntryButtonPerSet(){ // not to be used outside
        // of RedistrubuteButtons()
        // first get rid of all the AddSongEntry buttons
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i) instanceof ButtonAddSongEntry){
                songs.remove(i);
                i--;
            }
        }
        // then, add them back in at the right spots
        for (int i = 0; i < songs.size(); i++) {
            if ((songs.get(i) instanceof Set || songs.get(i) instanceof ButtonAddSet) && i != 0){
                if (!(songs.get(i-1) instanceof ButtonAddSongEntry)){
                    ButtonAddSongEntry button = new ButtonAddSongEntry();
                    songs.add(i, button);
                    i++;
                }
            }
        }
    }
}
