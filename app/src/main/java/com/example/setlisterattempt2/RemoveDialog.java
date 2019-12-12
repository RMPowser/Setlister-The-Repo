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


// this dialog handles the removal of both Sets and SongEntries

public class RemoveDialog extends AppCompatDialogFragment {
	
	private SetlistEntity thingToRemove; // this is the thing in the setlist that we want to remove
	private int indexOfThing; // index into the setlist of the thing we want to remove
	
	private RemoveDialogListener listener;
	
	public RemoveDialog(SetlistEntity thingToRemove, int indexOfThatThing) {
		this.thingToRemove = thingToRemove;
		this.indexOfThing = indexOfThatThing;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_remove_dialog, null);
		if (thingToRemove instanceof SongEntry) {
			String songTitle = ((SongEntry) thingToRemove).getTitle();
			
			builder.setView(view)
					.setTitle("Remove entry for " + songTitle + "?")
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						
						}
					})
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							listener.RemoveFromSetlist(indexOfThing);
						}
					});
		} else if (thingToRemove instanceof Set){
			final int indexOfSet = ((Set) thingToRemove).getSetIndex(); /* only used for the
			title of the dialog */
			
			builder.setView(view)
					.setTitle("Remove all of Set " + indexOfSet + "?")
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						
						}
					})
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							listener.RemoveFromSetlist(indexOfThing);
						}
					});
		}
		
		return builder.create();
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		
		try {
			listener = (RemoveDialogListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() +
					" must implement RemoveDialogListener");
		}
	}
	
	public interface RemoveDialogListener {
		void RemoveFromSetlist(int index);
	}
}
