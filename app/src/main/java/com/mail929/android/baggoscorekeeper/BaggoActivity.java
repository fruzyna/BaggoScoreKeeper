package com.mail929.android.baggoscorekeeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

public class BaggoActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baggo);

        final Context c = this;

        try {
            IO.getInstance().read();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CardView newGame = (CardView) findViewById(R.id.newgame);
        newGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(c, NewGameActivity.class);
                startActivity(intent);
            }
        });
        CardView resume = (CardView) findViewById(R.id.resume);
        resume.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(c, ExistingGamesActivity.class);
                startActivity(intent);
            }
        });
        CardView view = (CardView) findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(c, ViewGamesActivity.class);
                startActivity(intent);
            }
        });
        CardView settings = (CardView) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(c, SettingsActivity.class);
                startActivity(intent);
            }
        });

        int existing = 0;
        for(int i = 0; i < IO.getInstance().games.size(); i++)
        {
            Game game = IO.getInstance().games.get(i);
            if(!game.complete)
            {
                existing++;
            }
        }

        ((TextView) findViewById(R.id.games)).setText(IO.getInstance().games.size() + " Games");
        ((TextView) findViewById(R.id.existingGames)).setText(existing + " Games");
    }
}
