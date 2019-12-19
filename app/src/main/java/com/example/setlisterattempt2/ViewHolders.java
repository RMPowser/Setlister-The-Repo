package com.example.setlisterattempt2;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// all ViewHolders are declared within this container class
abstract class ViewHolders {
	static class HeaderViewHolder extends RecyclerView.ViewHolder {
		TextView headerTitle;
		TextView headerDate;
		TextView headerTime;
		
		HeaderViewHolder(View itemView) {
			super(itemView);
			headerTitle = itemView.findViewById(R.id.setlistTitleText);
			headerDate = itemView.findViewById(R.id.setlistDateTextView);
			headerTime = itemView.findViewById(R.id.setlistTimeTextView);
		}
		
		void SetHeaderDetails(@NonNull SetlistHeader header) {
			headerTitle.setText(header.getTitleOrLocation());
			headerDate.setText(header.getDate());
			headerTime.setText(header.getTime());
		}
	}
	
	static class SetViewHolder extends RecyclerView.ViewHolder {
		TextView setNumber;
		
		SetViewHolder(@NonNull View itemView) {
			super(itemView);
			setNumber = itemView.findViewById(R.id.set_number_text);
		}
		
		void setSetDetails(Set set) {
			String text = "Set " + (set.getSetIndex());
			setNumber.setText(text);
		}
	}
	
	static class SongEntryViewHolder extends RecyclerView.ViewHolder {
		TextView title;
		TextView artist;
		TextView length;
		TextView keySignature;
		
		SongEntryViewHolder(@NonNull View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.song_title_text);
			artist = itemView.findViewById(R.id.artist_text);
			length = itemView.findViewById(R.id.length_text);
			keySignature = itemView.findViewById(R.id.key_text);
		}
		
		void SetSongEntryDetails(@NonNull SongEntry songEntry) {
			title.setText(songEntry.getTitle());
			artist.setText(songEntry.getArtist());
			length.setText(songEntry.getLength());
			keySignature.setText(songEntry.getKeySignature());
		}
	}
	
	static class AddSetButtonViewHolder extends RecyclerView.ViewHolder {
		TextView addSetButton;
		
		AddSetButtonViewHolder(@NonNull View itemView) {
			super(itemView);
			addSetButton = itemView.findViewById(R.id.add_set_button_view);
		}
	}
	
	static class AddSongEntryButtonViewHolder extends RecyclerView.ViewHolder {
		TextView addSongEntryButton;
		
		AddSongEntryButtonViewHolder(@NonNull View itemView) {
			super(itemView);
			addSongEntryButton = itemView.findViewById(R.id.add_song_entry_button_view);
		}
	}
}