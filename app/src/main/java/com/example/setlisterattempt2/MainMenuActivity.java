package com.example.setlisterattempt2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
	public static final String SETLIST_KEY = "setlist";
	
	private Button newSetlistButton;
	private Button loadSetlistButton;
	private Button manageSetlistsButton;
	
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
	}
}
