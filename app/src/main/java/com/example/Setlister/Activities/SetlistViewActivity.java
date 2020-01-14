package com.example.Setlister.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Setlister.Dialogs.HeaderEditDialog;
import com.example.Setlister.Dialogs.ManageSetlistsConfirmDialog;
import com.example.Setlister.Dialogs.NewSetlistConfirmDialog;
import com.example.Setlister.HelperFunctions;
import com.example.Setlister.R;
import com.example.Setlister.Dialogs.RemoveDialog;
import com.example.Setlister.SetlistObjects.Setlist;
import com.example.Setlister.SetlistObjects.SetlistEntity;
import com.example.Setlister.SetlistObjects.SongEntry;
import com.example.Setlister.Dialogs.SongDataEditDialog;
import com.example.Setlister.Dialogs.SongDataEntryDialog;
import com.example.Setlister.ViewAdapters.SetlistViewAdapter;
import com.google.android.material.navigation.NavigationView;

public class SetlistViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SongDataEntryDialog.SongDataEntryDialogListener, HeaderEditDialog.HeaderEditDialogListener, SongDataEditDialog.SongDataEditDialogListener, RemoveDialog.RemoveDialogListener, NewSetlistConfirmDialog.NewSetlistConfirmDialogListener, ManageSetlistsConfirmDialog.ManageSetlistsConfirmDialogListener {
	private static final String TAG = "SetlistViewActivity";
	
	private DrawerLayout navDrawer;
	private SetlistViewAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setlist_view);
		
		navDrawer = findViewById(R.id.setlist_drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		
		
		//initTestSetlist();
		initRecyclerView();
	}
	
	private void initTestSetlist() {
		Log.i(TAG, "initTestSetlist: called.");
		adapter.getSetlist().AddHeader("Test Setlist", "Test Date", "Test Time");
		adapter.getSetlist().AddSet();
		for (int i = 0; i < 8; i++) {
			adapter.getSetlist().AddSong("title " + i, "artist " + i, "length " + i, "key " + i);
		}
		adapter.getSetlist().AddSet();
		for (int i = 0; i < 8; i++) {
			adapter.getSetlist().AddSong("title " + i, "artist " + i, "length " + i, "key " + i);
		}
	}
	
	private void initRecyclerView() {
		final Setlist mSetlist = getIntent().getExtras().getParcelable(MainMenuActivity.SETLIST_KEY);
		RecyclerView recyclerView = findViewById(R.id.setlist_recycler_view);
		adapter = new SetlistViewAdapter(this, mSetlist);
		adapter.setHasStableIds(true); //this enables animations
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.nav_new_setlist:
				OpenNewSetlistConfirmDialog(adapter.getSetlist(), this);
				break;
			case R.id.nav_load_setlist:
				OpenManageSetlistConfirmDialog(adapter.getSetlist(), this);
				break;
			case R.id.nav_save_this_setlist:
				HelperFunctions.SaveSetlist(adapter.getSetlist(), this);
				break;
			case R.id.nav_manage_setlists:
				// this is an intentional duplicate of case R.id.nav_load_setlist
				OpenManageSetlistConfirmDialog(adapter.getSetlist(), this);
				break;
		}
		navDrawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	// handles back button when navigation drawer is open and saving setlist before going back
	@Override
	public void onBackPressed() {
		if (navDrawer.isDrawerOpen(GravityCompat.START)) {
			navDrawer.closeDrawer(GravityCompat.START);
		} else {
			HelperFunctions.SaveSetlist(adapter.getSetlist(), this);
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
		adapter.getSetlist().InsertSong(position, title, artist, length, key);
		adapter.Refresh();
	}
	
	// override and function for HeaderEditDialogListener
	public void OpenHeaderEditDialog(String title, String date, String time) {
		HeaderEditDialog dialog = new HeaderEditDialog(title, date, time);
		dialog.show(getSupportFragmentManager(), "HeaderEditDialog");
	}
	
	@Override
	public void ApplyHeaderInfo(String title, String date, String time) {
		adapter.getSetlist().AddHeader(title, date, time);
		adapter.Refresh();
	}
	
	// override and function for SongDataEditDialogListener
	public void OpenSongDataEditDialog(int position, String title, String artist, String length, String key) {
		SongDataEditDialog dialog = new SongDataEditDialog(position, title, artist, length, key);
		dialog.show(getSupportFragmentManager(), "SongDataEditDialog");
	}
	
	@Override
	public void ModifySongEntry(int position, String title, String artist, String length, String key) {
		((SongEntry) adapter.getSetlist().getSongs().get(position)).setTitle(title);
		((SongEntry) adapter.getSetlist().getSongs().get(position)).setArtist(artist);
		((SongEntry) adapter.getSetlist().getSongs().get(position)).setLength(length);
		((SongEntry) adapter.getSetlist().getSongs().get(position)).setKeySignature(key);
		adapter.Refresh();
	}
	
	// override and function for RemoveDialog
	public void OpenRemoveDialog(SetlistEntity thingToRemove, int indexOfThing) {
		RemoveDialog dialog = new RemoveDialog(thingToRemove, indexOfThing);
		dialog.show(getSupportFragmentManager(), "RemoveDialog");
	}
	
	@Override
	public void RemoveFromSetlist(int index) {
		adapter.getSetlist().RemoveAt(index);
		adapter.Refresh();
	}
	
	// override and function for NewSetlistConfirmDialog
	public void OpenNewSetlistConfirmDialog(Setlist setlistToSave, Context context){
		NewSetlistConfirmDialog dialog = new NewSetlistConfirmDialog(setlistToSave, context);
		dialog.show(getSupportFragmentManager(), "NewSetlistConfirmDialog");
	}
	@Override
	public void CreateNewSetlist() {
		Setlist newSetlist = new Setlist("New Setlist", "Date", "Time");
		adapter.setSetlist(newSetlist);
		adapter.Refresh();
	}
	
	// override and function for ManageSetlistsConfirmDialog
	public void OpenManageSetlistConfirmDialog(Setlist setlistToSave, Context context){
		ManageSetlistsConfirmDialog dialog = new ManageSetlistsConfirmDialog(setlistToSave, context);
		dialog.show(getSupportFragmentManager(), "ManageSetlistConfirmDialog");
	}
	@Override
	public void GoToManagementActivity() {
		startActivity(new Intent(SetlistViewActivity.this, ManagementViewActivity.class));
	}
}
