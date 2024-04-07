package com.example.teamsapplication;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JSONParser {
    private Context context;

    public JSONParser(Context context) {
        this.context = context;
    }
    public void loadTeamsFromJson(List<Team> teams) {
        teams.clear();
        try {
            FileInputStream fis = context.openFileInput("teams.json");
            InputStreamReader isr = new InputStreamReader(fis);
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject root = new JSONObject(builder.toString());
            JSONArray teamsArray = root.getJSONArray("teams");
            for (int i = 0; i < teamsArray.length(); i++) {
                JSONObject teamJson = teamsArray.getJSONObject(i);
                Team team = new Team();
                team.name = teamJson.getString("name");
                team.members = teamJson.getString("members");
                team.position = teamJson.getString("position");
                team.image = teamJson.getString("image");
                team.description = teamJson.optString("description", "");
                teams.add(team);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    private String getJsonStringFromFile() {
        StringBuilder jsonData = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput("teams.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "{}";
        }
        return jsonData.toString();
    }

    public void saveTeam(JSONObject newTeam, boolean isEditMode) {
        String filename = "teams.json";
        JSONObject root;
        try {
            FileInputStream fis = context.openFileInput(filename);
            StringBuilder builder = new StringBuilder();
            int ch;
            while ((ch = fis.read()) != -1) {
                builder.append((char) ch);
            }
            root = new JSONObject(builder.toString());
            fis.close();
        } catch (IOException | JSONException e) {
            root = new JSONObject();
        }

        try {
            JSONArray teamsArray = root.optJSONArray("teams");
            if (teamsArray == null) {
                teamsArray = new JSONArray();
            }
            if (isEditMode) {
                String teamName = newTeam.getString("name");
                for (int i = 0; i < teamsArray.length(); i++) {
                    JSONObject teamJson = teamsArray.getJSONObject(i);
                    if (teamJson.getString("name").equals(teamName)) {
                        teamsArray.put(i, newTeam);
                        break;
                    }
                }
            } else {
                teamsArray.put(newTeam);
            }
            root.put("teams", teamsArray);

            FileOutputStream fos = context.openFileOutput(filename, MODE_PRIVATE);
            fos.write(root.toString().getBytes());
            fos.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteTeamFromFile(String teamName) {
        try {
            JSONObject root = new JSONObject(getJsonStringFromFile());
            JSONArray teamsArray = root.optJSONArray("teams");
            if (teamsArray != null) {
                for (int i = 0; i < teamsArray.length(); i++) {
                    JSONObject teamJson = teamsArray.getJSONObject(i);
                    if (teamName.equals(teamJson.getString("name"))) {
                        teamsArray.remove(i);
                        break;
                    }
                }
                root.put("teams", teamsArray);
                FileOutputStream fos = context.openFileOutput("teams.json", MODE_PRIVATE);
                fos.write(root.toString().getBytes());
                fos.close();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}
