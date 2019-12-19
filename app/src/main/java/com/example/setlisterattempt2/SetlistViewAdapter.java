package com.example.setlisterattempt2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SetlistViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private static int TYPE_SONG = 1;
	private static int TYPE_SET = 2;
	private static int TYPE_HEADER = 3;
	private static int TYPE_ADD_SET_BUTTON = 4;
	private static int TYPE_ADD_SONG_ENTRY_BUTTON = 5;
	
	private Setlist setlist;
	private final Context context;
	
	public SetlistViewAdapter(Context context, Setlist setlist) {
		this.context = context;
		this.setlist = setlist;
	}
	
	public Setlist getSetlist() {
		return setlist;
	}
	
	public void setSetlist(Setlist setlist) {
		this.setlist = setlist;
	}
	
	public void Refresh() {
		notifyDataSetChanged();
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		
		View view;
		if (viewType == TYPE_SET) {
			view = LayoutInflater.from(context).inflate(R.layout.layout_set, parent, false);
			return new ViewHolders.SetViewHolder(view);
		} else if (viewType == TYPE_SONG) {
			view = LayoutInflater.from(context).inflate(R.layout.layout_song_entry, parent, false);
			return new ViewHolders.SongEntryViewHolder(view);
		} else if (viewType == TYPE_HEADER) {
			view = LayoutInflater.from(context).inflate(R.layout.layout_setlist_header, parent, false);
			return new ViewHolders.HeaderViewHolder(view);
		} else if (viewType == TYPE_ADD_SET_BUTTON) {
			view = LayoutInflater.from(context).inflate(R.layout.layout_add_set_button, parent, false);
			return new ViewHolders.AddSetButtonViewHolder(view);
		} else if (viewType == TYPE_ADD_SONG_ENTRY_BUTTON) {
			view = LayoutInflater.from(context).inflate(R.layout.layout_add_song_entry_button,
					parent, false);
			return new ViewHolders.AddSongEntryButtonViewHolder(view);
		}
		return null;
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		final int index = position;
		
		if (getItemViewType(index) == TYPE_SET) {
			((ViewHolders.SetViewHolder) holder).setSetDetails((Set) setlist.getSongs().get(index));
			((ViewHolders.SetViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					SetlistEntity thingToRemove = setlist.getSongs().get(index);
					((SetlistViewActivity) context).OpenRemoveDialog(thingToRemove, index);
					return true; /*return true so that the click event is consumed and doesn't
					trigger OnClickListener */
				}
			});
		} else if (getItemViewType(index) == TYPE_SONG) {
			((ViewHolders.SongEntryViewHolder) holder).SetSongEntryDetails((SongEntry) setlist.getSongs().get(index));
			((ViewHolders.SongEntryViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String title = ((SongEntry) setlist.getSongs().get(index)).getTitle();
					String artist = ((SongEntry) setlist.getSongs().get(index)).getArtist();
					String length = ((SongEntry) setlist.getSongs().get(index)).getLength();
					String key = ((SongEntry) setlist.getSongs().get(index)).getKeySignature();
					((SetlistViewActivity) context).OpenSongDataEditDialog(index, title, artist, length, key);
				}
			});
			((ViewHolders.SongEntryViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					SetlistEntity thingToRemove = setlist.getSongs().get(index);
					((SetlistViewActivity) context).OpenRemoveDialog(thingToRemove, index);
					return true; /*return true so that the click event is consumed and doesn't
					trigger OnClickListener */
				}
			});
		} else if (getItemViewType(index) == TYPE_HEADER) {
			((ViewHolders.HeaderViewHolder) holder).SetHeaderDetails((SetlistHeader) setlist.getSongs().get(index));
			((ViewHolders.HeaderViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String title = ((SetlistHeader) setlist.getSongs().get(0)).getTitleOrLocation();
					String date = ((SetlistHeader) setlist.getSongs().get(0)).getDate();
					String time = ((SetlistHeader) setlist.getSongs().get(0)).getTime();
					((SetlistViewActivity) context).OpenHeaderEditDialog(title, date, time);
				}
			});
		} else if (getItemViewType(index) == TYPE_ADD_SET_BUTTON) {
			((ViewHolders.AddSetButtonViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					setlist.InsertSet(index);
					Refresh();
				}
			});
		} else if (getItemViewType(index) == TYPE_ADD_SONG_ENTRY_BUTTON) {
			((ViewHolders.AddSongEntryButtonViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((SetlistViewActivity) context).OpenSongDataEntryDialog(index);
				}
			});
		}
	}
	
	@Override
	public int getItemCount() {
		return setlist.getSongs().size();
	}
	
	@Override
	public int getItemViewType(int position) {
		if (setlist.getSongs().get(position) instanceof Set) {
			return TYPE_SET;
		} else if (setlist.getSongs().get(position) instanceof SongEntry) {
			return TYPE_SONG;
		} else if (setlist.getSongs().get(position) instanceof SetlistHeader) {
			return TYPE_HEADER;
		} else if (setlist.getSongs().get(position) instanceof ButtonAddSet) {
			return TYPE_ADD_SET_BUTTON;
		} else if (setlist.getSongs().get(position) instanceof ButtonAddSongEntry) {
			return TYPE_ADD_SONG_ENTRY_BUTTON;
		} else return -1;
	}
	
	

}
