package com.example.teamsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TeamDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        String teamName = getIntent().getStringExtra("teamName");
        String teamMembers = getIntent().getStringExtra("teamMembers");
        String teamPosition = getIntent().getStringExtra("teamPosition");
        String teamImage = getIntent().getStringExtra("teamImage");
        String teamDescription = getIntent().getStringExtra("teamDescription");

        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView membersTextView = findViewById(R.id.membersTextView);
        TextView positionTextView = findViewById(R.id.positionTextView);
        ImageView teamImageView = findViewById(R.id.teamImageView);


        nameTextView.setText(teamName);
        membersTextView.setText("Members: " + teamMembers);
        positionTextView.setText("Position: " + teamPosition);

        int imageResId = getResources().getIdentifier(teamImage, "drawable", getPackageName());
        if(imageResId != 0) {
            teamImageView.setImageResource(imageResId);
        }

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button moreButton = findViewById(R.id.moreButton);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamDetailsActivity.this, TeamDescriptionActivity.class);

                intent.putExtra("teamImage", teamImage);
                intent.putExtra("teamDescription", teamDescription);
                startActivity(intent);
            }
        });
    }

}
