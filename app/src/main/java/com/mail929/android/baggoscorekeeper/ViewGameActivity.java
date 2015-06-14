package com.mail929.android.baggoscorekeeper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by mail929 on 6/9/15.
 */
public class ViewGameActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgame);

        final Context c = this;

        ListView list = (ListView) findViewById(R.id.listView);
        final Game game = IO.getInstance().games.get(getIntent().getIntExtra("Game", 0));
        list.setAdapter(new ArrayAdapter<Board>(this, R.layout.listitem_game, R.id.redName, game.boards)
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

                Board b = game.boards.get(position);

                TextView redName = (TextView) view.findViewById(R.id.redName);
                redName.setText(b.redName);
                TextView redOn = (TextView) view.findViewById(R.id.redOn);
                redOn.setText(Integer.toString(b.redOn));
                TextView redIn = (TextView) view.findViewById(R.id.redIn);
                redIn.setText(Integer.toString(b.redIn));
                TextView redOff = (TextView) view.findViewById(R.id.redOff);
                redOff.setText(Integer.toString(b.redOff));
                TextView redKIn = (TextView) view.findViewById(R.id.redKIn);
                redKIn.setText(Integer.toString(b.redKnockedIn));
                TextView redKOff = (TextView) view.findViewById(R.id.redKOff);
                redKOff.setText(Integer.toString(b.redKnockedOff));
                TextView blueName = (TextView) view.findViewById(R.id.blueName);
                blueName.setText(b.blueName);
                TextView blueOn = (TextView) view.findViewById(R.id.blueOn);
                blueOn.setText(Integer.toString(b.blueOn));
                TextView blueIn = (TextView) view.findViewById(R.id.blueIn);
                blueIn.setText(Integer.toString(b.blueIn));
                TextView blueOff = (TextView) view.findViewById(R.id.blueOff);
                blueOff.setText(Integer.toString(b.blueOff));
                TextView blueKIn = (TextView) view.findViewById(R.id.blueKIn);
                blueKIn.setText(Integer.toString(b.blueKnockedIn));
                TextView blueKOff = (TextView) view.findViewById(R.id.blueKOff);
                blueKOff.setText(Integer.toString(b.blueKnockedOff));

                return view;
            }
        });
    }
}
