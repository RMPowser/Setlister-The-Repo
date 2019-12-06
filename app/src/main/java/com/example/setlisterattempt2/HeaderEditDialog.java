package com.example.setlisterattempt2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class HeaderEditDialog extends AppCompatDialogFragment {
	private EditText editTextTitle;
	private EditText editTextDate;
	private EditText editTextTime;
	
	String title;
	String date;
	String time;
	
	View view;
	
	private HeaderEditDialogListener listener;
	
	public HeaderEditDialog(@NonNull String title, @NonNull String date, @NonNull String time) {
		this.title = title;
		this.date = date;
		this.time = time;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.layout_header_edit_dialog, null);
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
						String date = editTextDate.getText().toString();
						String time = editTextTime.getText().toString();
						listener.ApplyHeaderInfo(title, date, time);
					}
				});
		editTextTitle = view.findViewById(R.id.edit_header_title);
		editTextDate = view.findViewById(R.id.edit_header_date);
		editTextTime = view.findViewById(R.id.edit_header_time);
		
		editTextTitle.setText(title);
		editTextDate.setText(date);
		editTextTime.setText(time);
		
		return builder.create();
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		
		try {
			listener = (HeaderEditDialogListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() +
					" must implement HeaderEditDialogListener");
		}
	}
	
	public interface HeaderEditDialogListener {
		void ApplyHeaderInfo(String title, String date, String time);
	}
}
