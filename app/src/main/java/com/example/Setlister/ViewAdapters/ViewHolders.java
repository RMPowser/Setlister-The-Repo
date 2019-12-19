package com.example.Setlister.ViewAdapters;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Setlister.R;
import com.example.Setlister.SetlistObjects.Set;
import com.example.Setlister.SetlistObjects.SetlistHeader;
import com.example.Setlister.SetlistObjects.SongEntry;

// all ViewHolders are declared within this container class
public abstract class ViewHolders {
	public static class HeaderViewHolder extends RecyclerView.ViewHolder {
		TextView headerTitle;
		TextView headerDate;
		TextView headerTime;
		
		public HeaderViewHolder(View itemView) {
			super(itemView);
			headerTitle = itemView.findViewById(R.id.setlistTitleText);
			headerDate = itemView.findViewById(R.id.setlistDateTextView);
			headerTime = itemView.findViewById(R.id.setlistTimeTextView);
		}
		
		public void SetHeaderDetails(@NonNull SetlistHeader header) {
			headerTitle.setText(header.getTitleOrLocation());
			headerDate.setText(header.getDate());
			headerTime.setText(header.getTime());
		}
	}
	
	public static class SetViewHolder extends RecyclerView.ViewHolder {
		TextView setNumber;
		
		public SetViewHolder(@NonNull View itemView) {
			super(itemView);
			setNumber = itemView.findViewById(R.id.set_number_text);
		}
		
		public void setSetDetails(Set set) {
			String text = "Set " + (set.getSetIndex());
			setNumber.setText(text);
		}
	}
	
	public static class SongEntryViewHolder extends RecyclerView.ViewHolder {
		TextView title;
		TextView artist;
		TextView length;
		TextView keySignature;
		
		public SongEntryViewHolder(@NonNull View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.song_title_text);
			artist = itemView.findViewById(R.id.artist_text);
			length = itemView.findViewById(R.id.length_text);
			keySignature = itemView.findViewById(R.id.key_text);
		}
		
		public void SetSongEntryDetails(@NonNull SongEntry songEntry) {
			title.setText(songEntry.getTitle());
			artist.setText(songEntry.getArtist());
			length.setText(songEntry.getLength());
			keySignature.setText(songEntry.getKeySignature());
		}
	}
	
	public static class AddSetButtonViewHolder extends RecyclerView.ViewHolder {
		TextView addSetButton;
		
		public AddSetButtonViewHolder(@NonNull View itemView) {
			super(itemView);
			addSetButton = itemView.findViewById(R.id.add_set_button_view);
		}
	}
	
	public static class AddSongEntryButtonViewHolder extends RecyclerView.ViewHolder {
		TextView addSongEntryButton;
		
		public AddSongEntryButtonViewHolder(@NonNull View itemView) {
			super(itemView);
			addSongEntryButton = itemView.findViewById(R.id.add_song_entry_button_view);
		}
	}
}