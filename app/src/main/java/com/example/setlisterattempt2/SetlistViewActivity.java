package com.example.setlisterattempt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public class SetlistViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SongDataEntryDialog.SongDataEntryDialogListener, HeaderEditDialog.HeaderEditDialogListener, SongDataEditDialog.SongDataEditDialogListener, RemoveDialog.RemoveDialogListener {
	private static final String TAG = "SetlistViewActivity";
	
	private DrawerLayout navDrawer;
	private SetlistViewAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setlist_view);
		Log.i(TAG, "onCreate: called.");
		
		navDrawer = findViewById(R.id.setlist_drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		
		
		//initTestSetlist();
		initRecyclerView();
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.nav_new_setlist:
				Toast.makeText(this, "new setlist", Toast.LENGTH_SHORT).show();
				break;
			case R.id.nav_load_setlist:
				LoadSetlist("/data/data/com.example.setlisterattempt2/files/Test Setlist_Test Date_Test Time.setlist");
				break;
			case R.id.nav_save_this_setlist:
				SaveSetlist(adapter.getmSetlist());
				break;
			case R.id.nav_manage_setlists:
				Toast.makeText(this, "manage setlists", Toast.LENGTH_SHORT).show();
				break;
		}
		navDrawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	private RuntimeTypeAdapterFactory CreateRuntimeTypeAdapterFactory(){
		RuntimeTypeAdapterFactory<SetlistEntity> runtimeTypeAdapterFactory =
				RuntimeTypeAdapterFactory.of(SetlistEntity.class, "type")
						.registerSubtype(Set.class, "set")
						.registerSubtype(SongEntry.class, "song")
						.registerSubtype(SetlistHeader.class, "header")
						.registerSubtype(ButtonAddSet.class, "buttonAddSet")
						.registerSubtype(ButtonAddSongEntry.class, "buttonAddSong");
		return runtimeTypeAdapterFactory;
	}
	
	private boolean LoadSetlist(String filepath){
		// file name is title of setlist
		FileReader reader = null;
		Gson gson;
		RuntimeTypeAdapterFactory runtimeTypeAdapterFactory = CreateRuntimeTypeAdapterFactory();
		
		boolean result;
		
		try {
			File file = new File(filepath);
			reader = new FileReader(file);
			gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
			Type entityType = new TypeToken<Setlist>(){}.getType();
			Setlist setlist = gson.fromJson(reader, entityType);
			adapter.setmSetlist(setlist);
			adapter.Refresh();
			result = true;
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
					result = true;
				} catch (IOException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}
		return result;
	}
	
	private boolean SaveSetlist(Setlist setlist) {
		// file name is title of setlist
		String FILE_NAME =
				getFilesDir().getPath() + "/" + ((SetlistHeader) setlist.getSongs().get(0)).getTitleOrLocation() +
						"_" + ((SetlistHeader) setlist.getSongs().get(0)).getDate() +
						"_" + ((SetlistHeader) setlist.getSongs().get(0)).getTime() + ".setlist";
		
		RuntimeTypeAdapterFactory runtimeTypeAdapterFactory = CreateRuntimeTypeAdapterFactory();
		FileOutputStream fileOutputStream = null;
		Gson gson;
		
		boolean result;
		
		try {
			gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
			String json = gson.toJson(setlist);
			File file = new File(FILE_NAME);
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(json.getBytes());
			Toast.makeText(this, "Setlist saved.", Toast.LENGTH_LONG).show();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
					result = true;
				} catch (IOException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}
		return result;
	}
	
	private void initTestSetlist() {
		Log.i(TAG, "initTestSetlist: called.");
		adapter.getmSetlist().AddHeader("Test Setlist", "Test Date", "Test Time");
		adapter.getmSetlist().AddSet();
		for (int i = 0; i < 8; i++) {
			adapter.getmSetlist().AddSong("title " + i, "artist " + i, "length " + i, "key " + i);
		}
		adapter.getmSetlist().AddSet();
		for (int i = 0; i < 8; i++) {
			adapter.getmSetlist().AddSong("title " + i, "artist " + i, "length " + i, "key " + i);
		}
	}
	
	private void initRecyclerView() {
		final Setlist mSetlist = getIntent().getExtras().getParcelable(MainMenuActivity.NEW_SETLIST_KEY);
		RecyclerView recyclerView = findViewById(R.id.setlist_recycler_view);
		adapter = new SetlistViewAdapter(this, mSetlist);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}
	
	
	// handles back button when navigation drawer is open
	@Override
	public void onBackPressed() {
		if (navDrawer.isDrawerOpen(GravityCompat.START)) {
			navDrawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
	
	// override and function for SongDataEntryDialogListener
	public void OpenSongDataEntryDialog(int position) {
		SongDataEntryDialog dialog = new SongDataEntryDialog(position);
		dialog.show(getSupportFragmentManager(), "SongDataEntryDialog");
	}
	
	@Override
	public void CreateAndInsertNewSongEntry(int position, String title, String artist, String length, String key) {
		adapter.getmSetlist().InsertSong(position, title, artist, length, key);
		adapter.Refresh();
	}
	
	// override and function for HeaderEditDialogListener
	public void OpenHeaderEditDialog(String title, String date, String time) {
		HeaderEditDialog dialog = new HeaderEditDialog(title, date, time);
		dialog.show(getSupportFragmentManager(), "HeaderEditDialog");
	}
	
	@Override
	public void ApplyHeaderInfo(String title, String date, String time) {
		adapter.getmSetlist().AddHeader(title, date, time);
		adapter.Refresh();
	}
	
	// override and function for SongDataEditDialogListener
	public void OpenSongDataEditDialog(int position, String title, String artist, String length, String key) {
		SongDataEditDialog dialog = new SongDataEditDialog(position, title, artist, length, key);
		dialog.show(getSupportFragmentManager(), "SongDataEditDialog");
	}
	
	@Override
	public void ModifySongEntry(int position, String title, String artist, String length, String key) {
		((SongEntry) adapter.getmSetlist().getSongs().get(position)).setTitle(title);
		((SongEntry) adapter.getmSetlist().getSongs().get(position)).setArtist(artist);
		((SongEntry) adapter.getmSetlist().getSongs().get(position)).setLength(length);
		((SongEntry) adapter.getmSetlist().getSongs().get(position)).setKeySignature(key);
		adapter.Refresh();
	}
	
	// override and function for RemoveDialog
	public void OpenRemoveDialog(SetlistEntity thingToRemove, int indexOfThing) {
		RemoveDialog dialog = new RemoveDialog(thingToRemove, indexOfThing);
		dialog.show(getSupportFragmentManager(), "RemoveDialog");
	}
	
	@Override
	public void RemoveFromSetlist(int index) {
		adapter.getmSetlist().RemoveAt(index);
		adapter.Refresh();
	}
}
