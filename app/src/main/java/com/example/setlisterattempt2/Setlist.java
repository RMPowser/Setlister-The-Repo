package com.example.setlisterattempt2;

import java.util.List;

public class Setlist {
    private String titleOrLocation = "Setlist Title/Location";
    private String date = "Jan 26, 1995";
    private String time = "8:30AM";
    private List<Set> sets;

    public void Setlist(String title, String date, String time){
        titleOrLocation = title;
        this.date = date;
        this.time = time;
    }

    public void AddNewSet(Set newSet){
        sets.add(newSet);
    }

    public void RemoveSetAtIndex(int index){
        sets.remove(index);
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

    public void Clear(){
        sets.clear();
    }
}
