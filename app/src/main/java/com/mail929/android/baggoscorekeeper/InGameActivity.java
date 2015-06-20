package com.mail929.android.baggoscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by mail929 on 6/8/15.
 */
public class InGameActivity extends AppCompatActivity
{
    Game game;

    boolean inHole = false;
    boolean onBoard = false;
    boolean missed = true;
    int yoursInHole = 0;
    int yoursOffBoard = 0;
    int theirsInHole = 0;
    int theirsOffBoard = 0;

    Button inHoleB;
    Button onBoardB;
    Button missedB;
    Button yoursIn;
    Button yoursOff;
    Button theirsIn;
    Button theirsOff;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);

        Intent i = getIntent();
        if(i.getStringExtra("Type").equals("new"))
        {
            game = new Game(i.getBooleanExtra("isFirstTo", true), i.getIntExtra("goal", 21), i.getIntExtra("throws", 5),
                    i.getStringExtra("player1"), i.getStringExtra("player2"), i.getStringExtra("player3"), i.getStringExtra("player4"), false);
            IO.getInstance().games.add(game);
        }
        else if(i.getStringExtra("Type").equals("existing"))
        {
            game = IO.getInstance().games.get(i.getIntExtra("Game", 0));
        }

        inHoleB = (Button) findViewById(R.id.landed_in);
        onBoardB = (Button) findViewById(R.id.landed_on);
        missedB = (Button) findViewById(R.id.missed);
        yoursIn = (Button) findViewById(R.id.knocked_yours_in);
        yoursOff = (Button) findViewById(R.id.knocked_yours_off);
        theirsIn = (Button) findViewById(R.id.knocked_theirs_in);
        theirsOff = (Button) findViewById(R.id.knocked_theirs_off);
        missedB.setEnabled(false);
        update();
        inHoleB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                inHole = true;
                onBoard = false;
                missed = false;
                inHoleB.setEnabled(false);
                onBoardB.setEnabled(true);
                missedB.setEnabled(true);
            }
        });
        onBoardB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                inHole = false;
                onBoard = true;
                missed = false;
                inHoleB.setEnabled(true);
                onBoardB.setEnabled(false);
                missedB.setEnabled(true);
            }
        });
        missedB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                inHole = false;
                onBoard = false;
                missed = true;
                inHoleB.setEnabled(true);
                onBoardB.setEnabled(true);
                missedB.setEnabled(false);
            }
        });
        yoursIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                yoursInHole++;
                refreshButtons();
            }
        });
        yoursIn.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if(yoursInHole > 0)
                {
                    yoursInHole--;
                }
                refreshButtons();
                return true;
            }
        });
        yoursOff.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                yoursOffBoard++;
                refreshButtons();
            }
        });
        yoursOff.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if(yoursOffBoard > 0)
                {
                    yoursOffBoard--;
                }
                refreshButtons();
                return true;
            }
        });
        theirsIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                theirsInHole++;
                refreshButtons();
            }
        });
        theirsIn.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if(theirsInHole > 0)
                {
                    theirsInHole--;
                }
                refreshButtons();
                return true;
            }
        });
        theirsOff.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                theirsOffBoard++;
                refreshButtons();
            }
        });
        theirsOff.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if(theirsOffBoard > 0)
                {
                    theirsOffBoard--;
                }
                refreshButtons();
                return true;
            }
        });
        final InGameActivity c = this;
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                calc(c);
                update();
            }
        });
    }

    public void calc(InGameActivity c)
    {
        int yours = 0;
        int theirs = 0;
        if(inHole)
        {
            yours +=3;
        }
        else if(onBoard)
        {
            yours += 1;
        }
        yours += 2 * yoursInHole;
        yours -= yoursOffBoard;
        theirs += 2 * theirsInHole;
        theirs -= theirsOffBoard;
        Board board = game.board;
        if(game.isBlue(game.currentPlayer))
        {
            if(inHole)
            {
                board.blueIn++;
            }
            else if(onBoard)
            {
                board.blueOn++;
            }
            else
            {
                board.blueOff++;
            }
            board.blueKnockedIn += yoursInHole;
            board.blueKnockedOff += yoursOffBoard;
            board.redKnockedIn += theirsInHole;
            board.redKnockedOff += theirsOffBoard;
            board.blueName = game.getPlayer(game.currentPlayer);
            board.redName = game.getPlayer(game.getOpposingPlayer(game.currentPlayer));
        }
        else
        {
            if(inHole)
            {
                board.redIn++;
            }
            else if(onBoard)
            {
                board.redOn++;
            }
            else
            {
                board.redOff++;
            }
            board.redKnockedIn += yoursInHole;
            board.redKnockedOff += yoursOffBoard;
            board.blueKnockedIn += theirsInHole;
            board.blueKnockedOff += theirsOffBoard;
            board.redName = game.getPlayer(game.currentPlayer);
            board.blueName = game.getPlayer(game.getOpposingPlayer(game.currentPlayer));
        }
        game.next(yours, theirs, c);
    }

    public void refreshButtons()
    {
        yoursIn.setText("In Hole (" + yoursInHole + ")");
        yoursOff.setText("Off Board (" + yoursOffBoard + ")");
        theirsIn.setText("In Hole (" + theirsInHole + ")");
        theirsOff.setText("Off Board (" + theirsOffBoard + ")");
    }

    public void update()
    {
        try {
            IO.getInstance().save();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((TextView) findViewById(R.id.round)).setText("Round " + game.currentRound);
        ((TextView) findViewById(R.id.side)).setText("Side " + game.currentSide);
        TextView player = ((TextView) findViewById(R.id.player));
        TextView you = ((TextView) findViewById(R.id.you));
        TextView them = ((TextView) findViewById(R.id.them));
        player.setText(game.getPlayer(game.currentPlayer));
        you.setText(game.getPlayer(game.currentPlayer));
        them.setText(game.getPlayer(game.getOpposingPlayer(game.currentPlayer)));
        if(game.isBlue(game.currentPlayer))
        {
            player.setTextColor(getResources().getColor(R.color.blue));
            you.setTextColor(getResources().getColor(R.color.blue));
            them.setTextColor(getResources().getColor(R.color.red));
        }
        else
        {
            player.setTextColor(getResources().getColor(R.color.red));
            you.setTextColor(getResources().getColor(R.color.red));
            them.setTextColor(getResources().getColor(R.color.blue));
        }
        ((TextView) findViewById(R.id.throwcount)).setText("Throw " + game.currentThrow);
        ((TextView) findViewById(R.id.score)).setText(game.blueScore + " - " + game.redScore);
        TextView change = ((TextView) findViewById(R.id.change));
        int difference = game.getRoundScoreDifference();
        if(difference > 0)
        {
            change.setText("+" + Math.abs(difference) + " Red");
        }
        else if(difference < 0)
        {
            change.setText("+" + Math.abs(difference) + " Blue");
        }
        else
        {
            change.setText("+" + 0);
        }

        resetValues();
        resetUI();
    }

    public void resetValues()
    {
        inHole = false;
        onBoard = false;
        missed = true;
        yoursInHole = 0;
        yoursOffBoard = 0;
        theirsInHole = 0;
        theirsOffBoard = 0;
    }

    public void resetUI()
    {
        inHoleB.setEnabled(!inHole);
        onBoardB.setEnabled(!onBoard);
        missedB.setEnabled(!missed);
        refreshButtons();
    }
}
