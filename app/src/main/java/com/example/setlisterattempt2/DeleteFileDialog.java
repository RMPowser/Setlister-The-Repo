package com.example.setlisterattempt2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;


// this dialog handles the removal of both Sets and SongEntries

public class DeleteFileDialog extends AppCompatDialogFragment {
	
	private String fileName; // this is the name of the file we want to delete, not the full path
	
	private DeleteFileDialogListener listener;
	
	public DeleteFileDialog(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_remove_dialog, null); // uses the same
		// format as remove dialog but with an extra button
		
		builder.setView(view)
				.setTitle("Delete file?\n" + fileName)
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				})
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						listener.DeleteFile(fileName);
					}
				});
		return builder.create();
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		
		try {
			listener = (DeleteFileDialogListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() +
					" must implement DeleteFileDialogListener");
		}
	}
	
	public interface DeleteFileDialogListener {
		void DeleteFile(String filePath);
	}
}
