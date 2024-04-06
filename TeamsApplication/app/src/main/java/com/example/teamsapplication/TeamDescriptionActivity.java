package com.example.teamsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TeamDescriptionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_description);

        ImageView teamPhotoImageView = findViewById(R.id.teamPhotoImageView);
        TextView teamDescriptionTextView = findViewById(R.id.teamDescriptionTextView);

        String teamImage = getIntent().getStringExtra("teamImage");
        int imageResId = getResources().getIdentifier(teamImage, "drawable", getPackageName());
        if (imageResId != 0) {
            teamPhotoImageView.setImageResource(imageResId);
        }
        String teamDescription = getIntent().getStringExtra("teamDescription");
        teamDescriptionTextView.setText(teamDescription);

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button navigateButton = findViewById(R.id.navigateButton);
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamDescriptionActivity.this, WebInfoActivity.class);
                intent.putExtra("url", "https://www.flashscore.ro/handbal/romania/liga-nationala/clasament/#/CEe0y0fE/table/overall");
                startActivity(intent);
            }
        });
    }
}
