package com.example.Setlister.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.solver.widgets.Helper;

import com.example.Setlister.HelperFunctions;
import com.example.Setlister.R;
import com.example.Setlister.SetlistObjects.Set;
import com.example.Setlister.SetlistObjects.Setlist;
import com.example.Setlister.SetlistObjects.SongEntry;


// this dialog handles the creation of a new setlist when done from the nav drawer

public class NewSetlistConfirmDialog extends AppCompatDialogFragment {
	
	private NewSetlistConfirmDialogListener listener;
	private Context context;
	private Setlist setlistToSave;
	
	public NewSetlistConfirmDialog(Setlist setlistToSave, Context context) {
		this.context = context;
		this.setlistToSave = setlistToSave;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_blank_dialog, null);
		
		builder.setView(view)
				.setTitle("Save current setlist before creating a new one?")
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						listener.CreateNewSetlist();
					}
				})
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						HelperFunctions.SaveSetlist(setlistToSave, context);
						listener.CreateNewSetlist();
					}
				})
				.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				});
		return builder.create();
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		
		try {
			listener = (NewSetlistConfirmDialogListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() +
					" must implement NewSetlistConfirmDialogListener");
		}
	}
	
	public interface NewSetlistConfirmDialogListener {
		void CreateNewSetlist();
	}
}
