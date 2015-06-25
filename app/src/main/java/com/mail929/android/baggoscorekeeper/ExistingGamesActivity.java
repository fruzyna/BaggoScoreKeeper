package com.mail929.android.baggoscorekeeper;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mail929 on 6/9/15.
 */
public class ExistingGamesActivity extends AppCompatActivity
{
    List<Game> games;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        getSupportActionBar().setTitle("Open Games");

        final Context c = this;

        games = new ArrayList<>();
        for(int i = 0; i < IO.getInstance().games.size(); i++)
        {
            Game game = IO.getInstance().games.get(i);
            if(!game.complete)
            {
                games.add(game);
            }
        }

        checkGames();

        final ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new ArrayAdapter<Game>(this, R.layout.listitem_game, R.id.blueNames, games)
        {
            public View getView(final int position, View convertView, ViewGroup parent)
            {
                View view;
                if (convertView == null)
                {
                    LayoutInflater infl = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = infl.inflate(R.layout.listitem_game, parent, false);
                }
                view = super.getView(position, convertView, parent);

                final Game game = games.get(position);

                ((TextView) view.findViewById(R.id.blueNames)).setText(game.getPlayer(1) + " and " + game.getPlayer(2));
                ((TextView) view.findViewById(R.id.redNames)).setText(game.getPlayer(3) + " and " + game.getPlayer(4));
                ((TextView) view.findViewById(R.id.blueScore)).setText(Integer.toString(game.blueScore));
                ((TextView) view.findViewById(R.id.redScore)).setText(Integer.toString(game.redScore));

                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(c, InGameActivity.class);
                        intent.putExtra("Game", position);
                        intent.putExtra("Type", "existing");
                        startActivity(intent);
                    }
                });
                view.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        for(int i = 0; i < IO.getInstance().games.size(); i++)
                        {
                            if(IO.getInstance().games.get(i).equals(games.get(position)))
                            {
                                IO.getInstance().games.remove(i);
                            }
                        }
                        games.remove(position);
                        checkGames();
                        try {
                            IO.getInstance().save();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        list.invalidateViews();
                        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Game Deleted", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        IO.getInstance().games.add(game);
                                        checkGames();
                                        try {
                                            IO.getInstance().save();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        list.invalidateViews();
                                    }
                                })
                                .show();
                        return true;
                    }
                });
                return view;
            }
        });
    }

    public void checkGames()
    {
        TextView message = (TextView) findViewById(R.id.message);
        if(games.size() == 0)
        {
            message.setText("There are no incomplete games.");
            message.setVisibility(View.VISIBLE);
        }
        else
        {
            message.setText("");
            message.setVisibility(View.GONE);
        }
    }
}
