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

public class SetlistViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private static int TYPE_SONG = 1;
	private static int TYPE_SET = 2;
	private static int TYPE_HEADER = 3;
	private static int TYPE_ADD_SET_BUTTON = 4;
	private static int TYPE_ADD_SONG_ENTRY_BUTTON = 5;
	
	private Setlist mSetlist;
	private final Context mContext;
	
	public SetlistViewAdapter(Context Context, Setlist setlist) {
		mContext = Context;
		mSetlist = setlist;
	}
	
	public Setlist getmSetlist() {
		return mSetlist;
	}
	
	public void setmSetlist(Setlist mSetlist) {
		this.mSetlist = mSetlist;
	}
	
	public void Refresh() {
		notifyDataSetChanged();
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		
		View view;
		if (viewType == TYPE_SET) {
			view = LayoutInflater.from(mContext).inflate(R.layout.layout_set, parent, false);
			return new SetViewHolder(view);
		} else if (viewType == TYPE_SONG) {
			view = LayoutInflater.from(mContext).inflate(R.layout.layout_song_entry, parent, false);
			return new SongEntryViewHolder(view);
		} else if (viewType == TYPE_HEADER) {
			view = LayoutInflater.from(mContext).inflate(R.layout.layout_setlist_header, parent, false);
			return new HeaderViewHolder(view);
		} else if (viewType == TYPE_ADD_SET_BUTTON) {
			view = LayoutInflater.from(mContext).inflate(R.layout.layout_add_set_button, parent, false);
			return new AddSetButtonViewHolder(view);
		} else if (viewType == TYPE_ADD_SONG_ENTRY_BUTTON) {
			view = LayoutInflater.from(mContext).inflate(R.layout.layout_add_song_entry_button,
					parent, false);
			return new AddSongEntryButtonViewHolder(view);
		}
		return null;
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		final int index = position;
		
		if (getItemViewType(index) == TYPE_SET) {
			((SetViewHolder) holder).setSetDetails((Set) mSetlist.getSongs().get(index));
			((SetViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					SetlistEntity thingToRemove = mSetlist.getSongs().get(index);
					((SetlistViewActivity) mContext).OpenRemoveDialog(thingToRemove, index);
					return true; /*return true so that the click event is consumed and doesn't
					trigger OnClickListener */
				}
			});
		} else if (getItemViewType(index) == TYPE_SONG) {
			((SongEntryViewHolder) holder).SetSongEntryDetails((SongEntry) mSetlist.getSongs().get(index));
			((SongEntryViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String title = ((SongEntry) mSetlist.getSongs().get(index)).getTitle();
					String artist = ((SongEntry) mSetlist.getSongs().get(index)).getArtist();
					String length = ((SongEntry) mSetlist.getSongs().get(index)).getLength();
					String key = ((SongEntry) mSetlist.getSongs().get(index)).getKeySignature();
					((SetlistViewActivity) mContext).OpenSongDataEditDialog(index, title, artist, length, key);
				}
			});
			((SongEntryViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					SetlistEntity thingToRemove = mSetlist.getSongs().get(index);
					((SetlistViewActivity) mContext).OpenRemoveDialog(thingToRemove, index);
					return true; /*return true so that the click event is consumed and doesn't
					trigger OnClickListener */
				}
			});
		} else if (getItemViewType(index) == TYPE_HEADER) {
			((HeaderViewHolder) holder).SetHeaderDetails((SetlistHeader) mSetlist.getSongs().get(index));
			((HeaderViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String title = ((SetlistHeader) mSetlist.getSongs().get(0)).getTitleOrLocation();
					String date = ((SetlistHeader) mSetlist.getSongs().get(0)).getDate();
					String time = ((SetlistHeader) mSetlist.getSongs().get(0)).getTime();
					((SetlistViewActivity) mContext).OpenHeaderEditDialog(title, date, time);
				}
			});
		} else if (getItemViewType(index) == TYPE_ADD_SET_BUTTON) {
			((AddSetButtonViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mSetlist.InsertSet(index);
					Refresh();
				}
			});
		} else if (getItemViewType(index) == TYPE_ADD_SONG_ENTRY_BUTTON) {
			((AddSongEntryButtonViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((SetlistViewActivity) mContext).OpenSongDataEntryDialog(index);
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
		} else if (mSetlist.getSongs().get(position) instanceof SongEntry) {
			return TYPE_SONG;
		} else if (mSetlist.getSongs().get(position) instanceof SetlistHeader) {
			return TYPE_HEADER;
		} else if (mSetlist.getSongs().get(position) instanceof ButtonAddSet) {
			return TYPE_ADD_SET_BUTTON;
		} else if (mSetlist.getSongs().get(position) instanceof ButtonAddSongEntry) {
			return TYPE_ADD_SONG_ENTRY_BUTTON;
		} else return -1;
	}
	
	
	private class SetViewHolder extends RecyclerView.ViewHolder {
		TextView setNumber;
		
		public SetViewHolder(@NonNull View itemView) {
			super(itemView);
			setNumber = itemView.findViewById(R.id.set_number_text);
		}
		
		private void setSetDetails(Set set) {
			String text = "Set " + (set.getSetIndex());
			setNumber.setText(text);
		}
	}
	
	private class SongEntryViewHolder extends RecyclerView.ViewHolder {
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
		
		private void SetSongEntryDetails(@NonNull SongEntry songEntry) {
			title.setText(songEntry.getTitle());
			artist.setText(songEntry.getArtist());
			length.setText(songEntry.getLength());
			keySignature.setText(songEntry.getKeySignature());
		}
	}
	
	private class HeaderViewHolder extends RecyclerView.ViewHolder {
		TextView headerTitle;
		TextView headerDate;
		TextView headerTime;
		
		public HeaderViewHolder(@NonNull View itemView) {
			super(itemView);
			headerTitle = itemView.findViewById(R.id.setlistTitleText);
			headerDate = itemView.findViewById(R.id.setlistDateTextView);
			headerTime = itemView.findViewById(R.id.setlistTimeTextView);
		}
		
		private void SetHeaderDetails(@NonNull SetlistHeader header) {
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
