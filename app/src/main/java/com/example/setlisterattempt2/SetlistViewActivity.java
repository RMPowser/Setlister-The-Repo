package com.example.setlisterattempt2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


public class SetlistViewActivity extends AppCompatActivity {

    Setlist mSetlist = new Setlist();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist_view);


        initEmptySetlist();
        initRecyclerView();
    }

    private void initEmptySetlist(){
        mSetlist.Clear();
        SongEntry song = new SongEntry();
        Set set = new Set();
        set.AddNewSongEntry(song);
        mSetlist.AddNewSet(set);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.setlist_recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mSetlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
