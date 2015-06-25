package com.mail929.android.baggoscorekeeper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by mail929 on 6/8/15.
 */
public class NewGameActivity extends AppCompatActivity
{
    RadioButton firstToRB;
    RadioButton highestAfterRB;
    EditText firstTo;
    EditText highestAfter;
    EditText player1;
    EditText player2;
    EditText player3;
    EditText player4;
    EditText throwsAllowed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame);
        getSupportActionBar().setTitle("New Game");

        final Context c = this;

        firstToRB = (RadioButton) findViewById(R.id.firstToRB);
        highestAfterRB = (RadioButton) findViewById(R.id.highestAfterRB);
        firstToRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                highestAfterRB.setChecked(!firstToRB.isChecked());
            }
        });
        highestAfterRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                firstToRB.setChecked(!highestAfterRB.isChecked());
            }
        });
        firstTo = (EditText) findViewById(R.id.firstTo);
        highestAfter = (EditText) findViewById(R.id.highestAfter);
        player1 = (EditText) findViewById(R.id.player1);
        player2 = (EditText) findViewById(R.id.player2);
        player3 = (EditText) findViewById(R.id.player3);
        player4 = (EditText) findViewById(R.id.player4);
        throwsAllowed = (EditText) findViewById(R.id.throwsallowed);
        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(c, InGameActivity.class);
                if(Integer.parseInt(throwsAllowed.getText().toString()) <= 0)
                {
                    intent.putExtra("throws", 1);
                }
                else
                {
                    intent.putExtra("throws", Integer.parseInt(throwsAllowed.getText().toString()));
                }
                intent.putExtra("isFirstTo", firstToRB.isChecked());
                if(firstToRB.isChecked())
                {
                    intent.putExtra("goal", Integer.parseInt(firstTo.getText().toString()));
                }
                else
                {
                    intent.putExtra("goal", Integer.parseInt(highestAfter.getText().toString()));
                }
                intent.putExtra("Type", "new");
                String p1 = player1.getText().toString();
                String p2 = player1.getText().toString();
                String p3 = player1.getText().toString();
                String p4 = player1.getText().toString();
                if(p1.equals(""))
                {
                    p1 = "Blue A";
                }
                if(p2.equals(""))
                {
                    p2 = "Blue B";
                }
                if(p3.equals(""))
                {
                    p3 = "Red A";
                }
                if(p4.equals(""))
                {
                    p4 = "Red B";
                }
                intent.putExtra("player1", p1);
                intent.putExtra("player2", p2);
                intent.putExtra("player3", p3);
                intent.putExtra("player4", p4);
                startActivity(intent);
            }
        });
    }
}
