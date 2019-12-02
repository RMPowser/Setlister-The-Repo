package com.example.setlisterattempt2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SetlistViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = "SetlistViewAdapter";
    private static int TYPE_SONG = 1;
    private static int TYPE_SET = 2;
    private static int TYPE_HEADER = 3;
    private static int TYPE_ADD_SET_BUTTON = 4;
    private static int TYPE_ADD_SONG_ENTRY_BUTTON = 5;
    
    private Setlist mSetlist;
    private Context mContext;
    
    public SetlistViewAdapter(Context Context, Setlist setlist ) {
        Log.i(TAG, "SetlistViewAdapter: constructor called.");
        mContext = Context;
        mSetlist = setlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: called.");
        
        View view;
        if(viewType == TYPE_SET ) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_set, parent, false);
            return new SetViewHolder(view);
        }
        else if(viewType == TYPE_SONG) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_song_entry, parent, false);
            return new SongEntryViewHolder(view);
        }
        else if(viewType == TYPE_HEADER) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_setlist_header, parent, false);
            return new HeaderViewHolder(view);
        }
        else if(viewType == TYPE_ADD_SET_BUTTON) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_add_set_button, parent, false);
            return new AddSetButtonViewHolder(view);
        }
        else if(viewType == TYPE_ADD_SONG_ENTRY_BUTTON) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_add_song_entry_button,
                    parent, false);
            return new AddSongEntryButtonViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        
        if (getItemViewType(position) == TYPE_SET){
            ((SetViewHolder) holder).setSetDetails((Set) mSetlist.getSongs().get(position));
            ((SetViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(mContext, "long clicked set. activate remove mode", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
        else if (getItemViewType(position) == TYPE_SONG){
            ((SongEntryViewHolder) holder).SetSongEntryDetails((SongEntry) mSetlist.getSongs().get(position));
            ((SongEntryViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Open song editing menu here", Toast.LENGTH_SHORT).show();
                }
            });
            ((SongEntryViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(mContext, "long clicked song entry. activate remove mode",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
        else if (getItemViewType(position) == TYPE_HEADER){
            ((HeaderViewHolder) holder).SetHeaderDetails((SetlistHeader) mSetlist.getSongs().get(position));
            ((HeaderViewHolder) holder).headerTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Selected Title", Toast.LENGTH_SHORT).show();
                }
            });
            ((HeaderViewHolder) holder).headerDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Selected Date", Toast.LENGTH_SHORT).show();
                }
            });
            ((HeaderViewHolder) holder).headerTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Selected Time", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (getItemViewType(position) == TYPE_ADD_SET_BUTTON){
            ((AddSetButtonViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,
                            "Selected \"+ set\"", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (getItemViewType(position) == TYPE_ADD_SONG_ENTRY_BUTTON) {
            ((AddSongEntryButtonViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Selected \"+ song\". activate new song activity here", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
    	return mSetlist.getSongs().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mSetlist.getSongs().get(position) instanceof Set) {
            return TYPE_SET;
        }
        else if (mSetlist.getSongs().get(position) instanceof SongEntry) {
            return TYPE_SONG;
        }
        else if (mSetlist.getSongs().get(position) instanceof SetlistHeader){
            return TYPE_HEADER;
        }
        else if (mSetlist.getSongs().get(position) instanceof ButtonAddSet){
            return TYPE_ADD_SET_BUTTON;
        }
        else if (mSetlist.getSongs().get(position) instanceof ButtonAddSongEntry) {
            return TYPE_ADD_SONG_ENTRY_BUTTON;
        }
        else return -1;
    }
    
    
    
    
    private class SetViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "SetViewHolder";
        TextView setNumber;
        
        public SetViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i(TAG, "SetViewHolder: constructor called.");
            setNumber = itemView.findViewById(R.id.set_number_text);
        }
        
        private void setSetDetails(Set set){
            String text = "Set " + (set.getSetIndex());
            setNumber.setText(text);
        }
    }
    
    private class SongEntryViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "SongEntryViewHolder";
        TextView title;
        TextView artist;
        TextView length;
        TextView keySignature;
        
        public SongEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i(TAG, "SongEntryViewHolder: constructor called.");
            title = itemView.findViewById(R.id.song_title_text);
            artist = itemView.findViewById(R.id.artist_text);
            length = itemView.findViewById(R.id.length_text);
            keySignature = itemView.findViewById(R.id.key_text);
        }
        
        private void SetSongEntryDetails(@NonNull SongEntry songEntry){
            title.setText(songEntry.getTitle());
            artist.setText(songEntry.getArtist());
            length.setText(songEntry.getLength());
            keySignature.setText(songEntry.getKeySignature());
        }
    }
    
    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView headerTitle;
        TextView headerDate;
        TextView headerTime;
    
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
           headerTitle = itemView.findViewById(R.id.setlistTitleText);
           headerDate = itemView.findViewById(R.id.setlistDateTextView);
           headerTime = itemView.findViewById(R.id.setlistTimeTextView);
        }
        
        private void SetHeaderDetails(@NonNull SetlistHeader header){
            headerTitle.setText(header.getTitleOrLocation());
            headerDate.setText(header.getDate());
            headerTime.setText(header.getTime());
        }
    }
    
    private class AddSetButtonViewHolder extends RecyclerView.ViewHolder {
        TextView addSetButton;

        public AddSetButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            addSetButton = itemView.findViewById(R.id.add_set_button_view);
        }
    }
    
    private class AddSongEntryButtonViewHolder extends RecyclerView.ViewHolder {
        TextView addSongEntryButton;
        
        public AddSongEntryButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            addSongEntryButton = itemView.findViewById(R.id.add_song_entry_button_view);
        }
    }
}
