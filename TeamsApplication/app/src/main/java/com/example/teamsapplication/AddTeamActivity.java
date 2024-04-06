package com.example.teamsapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

public class AddTeamActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_team);

        final EditText teamNameEditText = findViewById(R.id.teamName);
        final EditText teamMembersEditText = findViewById(R.id.teamMembers);
        final EditText teamPositionEditText = findViewById(R.id.teamPosition);
        final EditText teamImageEditText = findViewById(R.id.teamImage);
        final EditText teamDescriptionEditText = findViewById(R.id.teamDescription);
        Button saveTeamButton = findViewById(R.id.saveTeamButton);

        // Verifică dacă activitatea este în modul de editare
        boolean isEditMode = getIntent().getBooleanExtra("editMode", false);
        if (isEditMode) {
            String teamName = getIntent().getStringExtra("teamName");
            String teamMembers = getIntent().getStringExtra("teamMembers");
            String teamPosition = getIntent().getStringExtra("teamPosition");
            String teamImage = getIntent().getStringExtra("teamImage");
            String teamDescription = getIntent().getStringExtra("teamDescription");

            teamNameEditText.setText(teamName);
            teamMembersEditText.setText(teamMembers);
            teamPositionEditText.setText(teamPosition);
            teamImageEditText.setText(teamImage);
            teamDescriptionEditText.setText(teamDescription);
        }

        saveTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamName = teamNameEditText.getText().toString();
                String teamMembers = teamMembersEditText.getText().toString();
                String teamPosition = teamPositionEditText.getText().toString();
                String teamImage = teamImageEditText.getText().toString();
                String teamDescription = teamDescriptionEditText.getText().toString();

                JSONObject teamJson = new JSONObject();
                try {
                    teamJson.put("name", teamName);
                    teamJson.put("members", teamMembers);
                    teamJson.put("position", teamPosition);
                    teamJson.put("image", teamImage);
                    teamJson.put("description", teamDescription);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Salvează echipa în SharedPreferences
                SharedPreferences sharedPref = getSharedPreferences("Teams", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(teamName, teamJson.toString());
                editor.apply();

                // Încheie activitatea
                finish();
            }
        });

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
