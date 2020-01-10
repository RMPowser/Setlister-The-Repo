package com.example.Setlister.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Setlister.Dialogs.DeleteFileDialog;
import com.example.Setlister.HelperFunctions;
import com.example.Setlister.R;
import com.example.Setlister.SetlistObjects.Setlist;
import com.example.Setlister.SetlistObjects.SetlistHeader;
import com.example.Setlister.ViewAdapters.ManagementViewAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ManagementViewActivity extends AppCompatActivity implements DeleteFileDialog.DeleteFileDialogListener {
	
	
	private ManagementViewAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_management_view);
		
		initRecyclerView();
	}
	
	private void initRecyclerView() {
		final ArrayList<SetlistHeader> headerList = GetHeaderListFromSaveDirectory();
		RecyclerView recyclerView = findViewById(R.id.management_recycler_view);
		adapter = new ManagementViewAdapter(this, headerList);
		adapter.setHasStableIds(true); //this enables animations
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}
	
	private ArrayList<SetlistHeader> GetHeaderListFromSaveDirectory() {
		File[] files = getFilesDir().listFiles(); // get a list of all the files in the save
		// directory
		FileReader reader = null;
		Gson gson;
		
		RuntimeTypeAdapterFactory runtimeTypeAdapterFactory =
				HelperFunctions.CreateRuntimeTypeAdapterFactory();
		
		ArrayList<SetlistHeader> setlistHeaders = new ArrayList<>();
		
		try {
			for (File file : files) {
				File loneFile = new File(file.getPath()); // get the file by itself
				reader = new FileReader(loneFile); // assign the reader to it
				
				gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
				Type entityType = new TypeToken<Setlist>(){}.getType();
				
				Setlist setlist = gson.fromJson(reader, entityType); // rebuild the full setlist
				
				// copy the header from the setlist
				SetlistHeader header = (SetlistHeader) (setlist.getSongs().get(0));
				
				// set the new headers file path. this is to be used when loading the setlist
				header.setSetlistFileName(file.getName());
				
				// add the header to the list
				setlistHeaders.add(header);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return setlistHeaders;
	}
	
	
	
	
	// override and function for DeleteFileDialogListener
	public void OpenDeleteFileDialog(String fileName) {
		DeleteFileDialog dialog = new DeleteFileDialog(fileName);
		dialog.show(getSupportFragmentManager(), "DeleteFileDialog");
	}
	
	@Override
	public void DeleteFile(String filePath) {
		deleteFile(filePath);
		final ArrayList<SetlistHeader> headerList = GetHeaderListFromSaveDirectory();
		adapter.setHeaderList(headerList);
		adapter.Refresh();
	}
}
