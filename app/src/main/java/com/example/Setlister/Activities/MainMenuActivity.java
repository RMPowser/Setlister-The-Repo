package com.example.Setlister.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.Setlister.R;
import com.example.Setlister.SetlistObjects.Setlist;


public class MainMenuActivity extends AppCompatActivity {
	SharedPreferences pref;
	
	public static final String SETLIST_KEY = "setlist";
	
	private Button newSetlistButton;
	private Button loadSetlistButton;
	private Button manageSetlistsButton;
	private Switch darkModeToggle;
	
	public static final String prefFileName = "SetlisterPreferences";
	public static final String darkModeEnabledKey = "DarkMode";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		initButtons();
	}
	
	private void initButtons() {
		newSetlistButton = findViewById(R.id.buttonNewSetlist);
		newSetlistButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenuActivity.this, SetlistViewActivity.class);
				Setlist setlist = new Setlist("New Setlist", "Date", "Time");
				intent.putExtra(SETLIST_KEY, setlist);
				// the above function works because the Setlist class and all children of the SetlistEntity
				// class were made parcelable
				startActivity(intent);
			}
		});
		
		loadSetlistButton = findViewById(R.id.buttonLoadSetlist);
		loadSetlistButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainMenuActivity.this, ManagementViewActivity.class));
			}
		});
		
		manageSetlistsButton = findViewById(R.id.buttonManageSetlists);
		manageSetlistsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainMenuActivity.this, ManagementViewActivity.class));
			}
		});
		
		
		// get shared preferences so we can set the toggle on dark mode
		pref = getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
		final SharedPreferences.Editor prefEditor = pref.edit();
		
		// set the toggle on dark mode
		darkModeToggle = findViewById(R.id.DarkModeToggle);
		if (pref.contains(darkModeEnabledKey)){
			if (pref.getBoolean(darkModeEnabledKey, false)) {
				darkModeToggle.setChecked(true);
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
			}
			else{
				darkModeToggle.setChecked(false);
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
			}
		}
		
		// handle click on dark mode
		darkModeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					prefEditor.clear();
					prefEditor.putBoolean(darkModeEnabledKey, true);
					prefEditor.commit();
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
				}
				else {
					prefEditor.clear();
					prefEditor.putBoolean(darkModeEnabledKey, false);
					prefEditor.commit();
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
				}
				recreate();
			}
		});
	}
}
