package com.example.teamsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView = null;
    private Button addButton = null;
    private ItemArrayAdapter adapter = null;
    private String[] teamNames = {"Brasov Centrul Vechi", "Brasov Main Street", "Brasov Biserica Neagra"};
    ArrayList<Team> teams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addTeamButton);
        listView = findViewById(R.id.listView);
        adapter = new ItemArrayAdapter(this, R.layout.item_layout, teams);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, teamNames[position], Toast.LENGTH_SHORT).show();
            }

        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTeamActivity.class);
                startActivity(intent);
            }
        });
        //parseXML();
    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getSharedPreferences("Teams", MODE_PRIVATE);
        Map<String, ?> teamsMap = sharedPref.getAll();

        teams.clear(); // Curăță lista actuală pentru a evita dublarea

        for (Map.Entry<String, ?> entry : teamsMap.entrySet()) {
            try {
                JSONObject teamJson = new JSONObject(entry.getValue().toString());
                Team team = new Team();
                team.name = teamJson.getString("name");
                team.members = teamJson.getString("members");
                team.position = teamJson.getString("position");
                team.image = teamJson.getString("image");
                team.description = teamJson.getString("description");

                teams.add(team);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("data.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);
        }catch (XmlPullParserException | IOException e) {}

    }
    private void processParsing(XmlPullParser parser) throws  IOException, XmlPullParserException{
        int eventType = parser.getEventType();
        Team currentTeam = null;

        while(eventType != XmlPullParser.END_DOCUMENT)
        {
            String eltName= null;
            switch(eventType)
            {
                case XmlPullParser.START_TAG:
                   eltName = parser.getName();
                   if("team".equals(eltName))
                   {
                       currentTeam = new Team();
                       teams.add(currentTeam);
                   }
                   else if(currentTeam !=null)
                   {
                       if("name".equals(eltName)) {
                           currentTeam.name = parser.nextText();
                       }
                       else if("members".equals(eltName))
                       {
                           currentTeam.members = parser.nextText();
                       }
                       else if("position".equals(eltName))
                       {
                           currentTeam.position = parser.nextText();
                       }
                       else if("image".equals(eltName))
                       {
                           currentTeam.image = parser.nextText();
                       }
                   }
                   break;
            }
            eventType = parser.next();
        }
        printPlayers(teams);
    }

    private void printPlayers(ArrayList<Team> teams) {
        StringBuilder builder = new StringBuilder();
        for(Team team : teams)
        {
            builder.append(team.name).append("\n").append(team.members).append("\n").append(team.position).append("\n\n");
        }
    }
}