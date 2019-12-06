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


public class SetlistViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private static final String TAG = "SetlistViewActivity";
	private DrawerLayout navDrawer;
	
	
	Setlist mSetlist = new Setlist("this is a setlist", "jan 26th", "8:53AM");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setlist_view);
		Log.i(TAG, "onCreate: called.");
		
		navDrawer = findViewById(R.id.setlist_drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		
		
		initTestSetlist();
		initRecyclerView();
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.nav_new_setlist:
				Toast.makeText(this, "new setlist", Toast.LENGTH_SHORT).show();
				break;
			case R.id.nav_load_setlist:
				Toast.makeText(this, "load setlist", Toast.LENGTH_SHORT).show();
				break;
			case R.id.nav_save_this_setlist:
				Toast.makeText(this, "save this setlist", Toast.LENGTH_SHORT).show();
				break;
			case R.id.nav_manage_setlists:
				Toast.makeText(this, "manage setlists", Toast.LENGTH_SHORT).show();
				break;
		}
		navDrawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	private void initTestSetlist() {
		Log.i(TAG, "initTestSetlist: called.");
		mSetlist.Clear();
		mSetlist.AddSet();
		for (int i = 0; i < 8; i++) {
			mSetlist.AddSong("title " + i, "artist " + i, "length " + i, "key " + i);
		}
		mSetlist.AddSet();
		for (int i = 0; i < 8; i++) {
			mSetlist.AddSong("title " + i, "artist " + i, "length " + i, "key " + i);
		}
		mSetlist.AddHeader("Setlist Title/Location", "Jan 26, 1995", "8:00AM");
	}
	
	private void initRecyclerView() {
		Log.i(TAG, "initRecyclerView: called.");
		RecyclerView recyclerView = findViewById(R.id.setlist_recycler_view);
		SetlistViewAdapter adapter = new SetlistViewAdapter(this, mSetlist);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}
	
	@Override
	public void onBackPressed() {
		if (navDrawer.isDrawerOpen(GravityCompat.START)) {
			navDrawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
}
