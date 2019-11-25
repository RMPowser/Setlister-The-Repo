package com.example.setlisterattempt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {
    private Button newSetlistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        newSetlistButton = findViewById(R.id.buttonNewSetlist);
        newSetlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetlistViewActivity();
            }
        });
    }

    private void openSetlistViewActivity() {
        Intent intent = new Intent(this, SetlistViewActivity.class);
        startActivity(intent);
    }
}
