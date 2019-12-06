package com.example.setlisterattempt2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SongDataEntryDialog extends AppCompatDialogFragment {
	private EditText editTextTitle;
	private EditText editTextArtist;
	private EditText editTextLength;
	private EditText editTextKey;
	
	private int position; // this is the index to insert the new song at
	
	private SongDataEntryDialogListener listener;
	
	public SongDataEntryDialog(int position) {
		this.position = position;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_song_data_entry_dialog, null);
		
		builder.setView(view)
				.setTitle("Song Data Entry")
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String title = editTextTitle.getText().toString();
						String artist = editTextArtist.getText().toString();
						String length = editTextLength.getText().toString();
						String key = editTextKey.getText().toString();
						listener.CreateAndInsertNewSongEntry(position, title, artist, length, key);
					}
				});
		editTextTitle = view.findViewById(R.id.edit_new_song_title);
		editTextArtist = view.findViewById(R.id.edit_new_song_artist);
		editTextLength = view.findViewById(R.id.edit_new_song_length);
		editTextKey = view.findViewById(R.id.edit_new_song_key);
		
		return builder.create();
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		
		try {
			listener = (SongDataEntryDialogListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() +
					" must implement SongDataEntryDialogListener");
		}
	}
	
	public interface SongDataEntryDialogListener {
		void CreateAndInsertNewSongEntry(int position, String title, String artist, String length,
								String key);
	}
}
