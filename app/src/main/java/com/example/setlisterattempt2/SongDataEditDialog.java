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

public class SongDataEditDialog extends AppCompatDialogFragment {
	private EditText editTextTitle;
	private EditText editTextArtist;
	private EditText editTextLength;
	private EditText editTextKey;
	
	private int position; // this is the index of the song to edit
	
	// need these to pre-fill the EditText fields
	private String title;
	private String artist;
	private String length;
	private String key;
	
	private SongDataEditDialogListener listener;
	
	public SongDataEditDialog(int position, String title, String artist, String length, String key) {
		this.position = position;
		this.title = title;
		this.artist = artist;
		this.length = length;
		this.key = key;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		// uses the same xml layout as SongDataEntryDialog
		View view = inflater.inflate(R.layout.layout_song_data_entry_dialog, null);
		
		builder.setView(view)
				// change the title from SongDataEntry
				.setTitle("Edit Song Entry")
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
						listener.ModifySongEntry(position, title, artist, length, key);
					}
				});
		editTextTitle = view.findViewById(R.id.edit_new_song_title);
		editTextArtist = view.findViewById(R.id.edit_new_song_artist);
		editTextLength = view.findViewById(R.id.edit_new_song_length);
		editTextKey = view.findViewById(R.id.edit_new_song_key);
		
		editTextTitle.setText(title);
		editTextArtist.setText(artist);
		editTextLength.setText(length);
		editTextKey.setText(key);
		
		return builder.create();
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		
		try {
			listener = (SongDataEditDialogListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() +
					" must implement SongDataEditDialogListener");
		}
	}
	
	public interface SongDataEditDialogListener {
		void ModifySongEntry(int position, String title, String artist, String length,
							 String key);
	}
}
