package com.mail929.android.baggoscorekeeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

        Button newGame = (Button) findViewById(R.id.newgame);
        newGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(c, NewGameActivity.class);
                startActivity(intent);
            }
        });
        Button resume = (Button) findViewById(R.id.resume);
        resume.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(c, GamesActivity.class);
                startActivity(intent);
            }
        });
        Button view = (Button) findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(c, ViewGamesActivity.class);
                startActivity(intent);
            }
        });
    }
}
