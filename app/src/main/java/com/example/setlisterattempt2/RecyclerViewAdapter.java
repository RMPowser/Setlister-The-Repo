package com.example.setlisterattempt2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private Setlist mSetlist = new Setlist();
    private Context mContext;

    public RecyclerViewAdapter(Context Context, Setlist setlist ) {
        mContext = Context;
        mSetlist = setlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_entry, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView songTitle;
        TextView artist;
        TextView lengthInMinutesAndSeconds;
        TextView keySignature;

        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.song_title_text);
            artist = itemView.findViewById(R.id.artist_text);
            lengthInMinutesAndSeconds = itemView.findViewById(R.id.length_text);
            keySignature = itemView.findViewById(R.id.key_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
