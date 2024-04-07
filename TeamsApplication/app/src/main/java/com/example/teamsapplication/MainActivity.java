package com.example.teamsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ListView listView = null;
    private Button addButton = null;
    private ItemArrayAdapter adapter = null;
    ArrayList<Team> teams = new ArrayList<>();
    JSONParser jsonParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonParser = new JSONParser(this);
        addButton = findViewById(R.id.addTeamButton);
        listView = findViewById(R.id.listView);
        adapter = new ItemArrayAdapter(this, R.layout.item_layout, teams);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTeamActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        jsonParser.loadTeamsFromJson(teams);
        adapter.notifyDataSetChanged();
    }
}