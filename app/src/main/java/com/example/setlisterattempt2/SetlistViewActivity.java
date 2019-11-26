package com.example.setlisterattempt2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


public class SetlistViewActivity extends AppCompatActivity {
    private static final String TAG = "SetlistViewActivity";

    Setlist mSetlist = new Setlist("this is a setlist", "jan 26th", "8:53AM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist_view);

        Log.i(TAG, "onCreate: called.");

        initTestSetlist();
        initRecyclerView();
    }

    private void initTestSetlist(){
        Log.i(TAG, "initTestSetlist: called.");
        mSetlist.Clear();
        mSetlist.AddSet();
		for (int i = 0; i < 8; i++) {
			mSetlist.AddSong("title " + i, "artist " + i, "length " + i, "key " + i);
		}
		mSetlist.AddSet();
        for (int i = 0; i < 8; i++) {
            mSetlist.AddSong("title " + i, "artist " + i, "length " + i, "key " + i);
        }
        mSetlist.AddHeader("Setlist Title/Location", "Jan 26, 1995", "8:00AM");
    }

    private void initRecyclerView() {
        Log.i(TAG, "initRecyclerView: called.");
        RecyclerView recyclerView = findViewById(R.id.setlist_recycler_view);
        SetlistViewAdapter adapter = new SetlistViewAdapter(this, mSetlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
