package com.example.teamsapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter {
    private Context context;
    private int layout;
    private List<Team> teams;

    JSONParser jsonParser;


    public ItemArrayAdapter(Context context, int layout, List<Team> teams) {
        super(context, layout, teams);
        this.context = context;
        this.layout = layout;
        this.teams = teams;
        jsonParser = new JSONParser(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layoutView = inflater.inflate(layout, parent, false);

        ImageView imageView = layoutView.findViewById(R.id.imageView);
        TextView mainTextView = layoutView.findViewById(R.id.textView);
        TextView subtitileTextView = layoutView.findViewById(R.id.textView2);
        Button editButton = layoutView.findViewById(R.id.editButton);
        Button deleteButton = layoutView.findViewById(R.id.deleteButton);
        Button viewButton = layoutView.findViewById(R.id.viewButton);

        // populate components with content
        Team team = teams.get(position);
        int resourceId = context.getResources().getIdentifier(team.image, "drawable", context.getPackageName());
        if(resourceId!=0) {
            imageView.setImageResource(resourceId);
        }
        mainTextView.setText(team.name);
        subtitileTextView.setText("Members: " + team.members + ", Position: " + team.position);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddTeamActivity.class);
                intent.putExtra("editMode", true);
                intent.putExtra("teamName", team.name);
                intent.putExtra("teamMembers", team.members);
                intent.putExtra("teamPosition", team.position);
                intent.putExtra("teamImage", team.image);
                intent.putExtra("teamDescription", team.description);
                context.startActivity(intent);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParser.deleteTeamFromFile(team.name);
                teams.remove(position);
                notifyDataSetChanged();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeamDetailsActivity.class);
                intent.putExtra("teamName", team.name);
                intent.putExtra("teamMembers", team.members);
                intent.putExtra("teamPosition", team.position);
                intent.putExtra("teamImage", team.image);
                intent.putExtra("teamDescription", team.description);
                context.startActivity(intent);
            }
        });

        return layoutView;
    }
}
