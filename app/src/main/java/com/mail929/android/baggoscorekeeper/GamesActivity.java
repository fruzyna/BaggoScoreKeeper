package com.mail929.android.baggoscorekeeper;

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
public class GamesActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        final Context c = this;

        final ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new ArrayAdapter<Game>(this, android.R.layout.simple_list_item_1, android.R.id.text1, IO.getInstance().games)
        {
            public View getView(final int position, View convertView, ViewGroup parent)
            {
                View view;
                if (convertView == null)
                {
                    LayoutInflater infl = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = infl.inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                view = super.getView(position, convertView, parent);

                final Game game = IO.getInstance().games.get(position);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setText(game.getPlayer(1) + ", " + game.getPlayer(2) + ", " + game.getPlayer(3) + ", " + game.getPlayer(4));

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
                        IO.getInstance().games.remove(position);
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
}
