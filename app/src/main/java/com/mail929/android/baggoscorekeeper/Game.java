package com.mail929.android.baggoscorekeeper;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mail929 on 6/8/15.
 */
public class Game
{
    boolean isFirstTo;
    boolean complete;
    int goal;
    int throwsAllowed;
    String player1;
    String player2;
    String player3;
    String player4;

    int currentRound = 1;
    int currentSide = 1;
    int currentPlayer = 1;
    int currentThrow = 1;
    boolean isSecond = false;

    int blueScore = 0;
    int redScore = 0;

    int blueRoundScore = 0;
    int redRoundScore = 0;

    Board board;
    List<Board> boards = new ArrayList<>();
    
    public Game(boolean isFirstTo, int goal, int throwsAllowed, String player1, String player2, String player3, String player4, boolean complete)
    {
        this.isFirstTo = isFirstTo;
        this.goal = goal;
        this.throwsAllowed = throwsAllowed;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.complete = complete;
        board = new Board();
        checkNames();
    }

    public void checkNames()
    {
        if(player1.equals(""))
        {
            player1 = "Blue A";
        }
        if(player2.equals(""))
        {
            player2 = "Blue B";
        }
        if(player3.equals(""))
        {
            player3 = "Red A";
        }
        if(player4.equals(""))
        {
            player4 = "Red B";
        }
    }

    public void next(int yours, int theirs, InGameActivity a)
    {
        int difference = yours - theirs;
        if(isBlue(currentPlayer))
        {
            if(difference > 0)
            {
                blueRoundScore += difference;
            }
            else if(difference < 0)
            {
                redRoundScore -= difference;
            }
        }
        else
        {
            if(difference > 0)
            {
                redRoundScore += difference;
            }
            else if(difference < 0)
            {
                blueRoundScore -= difference;
            }
        }

        if(isSecond)
        {
            isSecond = false;
            if(currentThrow == throwsAllowed)
            {
                boards.add(board);
                board = new Board();
                currentThrow = 1;
                if(getRoundScoreDifference() > 0)
                {
                    redScore += getRoundScoreDifference();
                }
                else
                {
                    blueScore -= getRoundScoreDifference();
                }
                blueRoundScore = 0;
                redRoundScore = 0;
                if(!isFirstTo)
                {
                    if(currentRound == goal)
                    {
                        complete = true;
                        DialogFragment dialog = new TextDialog();
                        Bundle args = new Bundle();
                        String winner;
                        if(blueScore > redScore)
                        {
                            winner = "Blue";
                        }
                        else if(redScore > blueScore)
                        {
                            winner = "Red";
                        }
                        else
                        {
                            winner = "No";
                        }

                        args.putString("title", winner + " team wins!");
                        args.putString("message", "The game has finished its " + goal + " rounds. The final score was " + blueScore + " to " + redScore + ".");
                        dialog.setArguments(args);
                        dialog.show(a.getFragmentManager(), "");
                    }
                }
                else
                {
                    if(blueScore >= goal || redScore >= 21)
                    {
                        complete = true;
                        DialogFragment dialog = new TextDialog();
                        Bundle args = new Bundle();
                        String winner = "No";
                        if(blueScore >= goal)
                        {
                            winner = "Blue";
                        }
                        else if(redScore >= goal)
                        {
                            winner = "Red";
                        }
                        args.putString("title", winner + " team wins!");
                        args.putString("message", "The score of " + goal + " has been reached. The final score was " + blueScore + " to " + redScore + ".");
                        dialog.setArguments(args);
                        dialog.show(a.getFragmentManager(), "");
                    }
                }
                if(currentSide == 1)
                {
                    currentSide = 2;
                }
                else
                {
                    currentSide = 1;
                    currentRound++;

                }
                if(redRoundScore > blueRoundScore)
                {
                    if(isBlue(currentPlayer))
                    {
                        currentPlayer = getOpposingPlayer(getPartner(currentPlayer));
                    }
                    else
                    {
                        currentPlayer = getPartner(currentPlayer);
                    }
                }
                else if(blueRoundScore > redRoundScore)
                {
                    if(isBlue(currentPlayer))
                    {
                        currentPlayer = getPartner(currentPlayer);
                    }
                    else
                    {
                        currentPlayer = getOpposingPlayer(getPartner(currentPlayer));
                    }
                }
                else
                {
                    currentPlayer = getPartner(getOpposingPlayer(currentPlayer));
                }
            }
            else
            {
                currentThrow++;
                currentPlayer = getOpposingPlayer(currentPlayer);
            }
        }
        else
        {
            isSecond = true;
            currentPlayer = getOpposingPlayer(currentPlayer);
        }
    }
    
    public int getOpposingPlayer(int player)
    {
        switch(player)
        {
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 1;
            case 4:
                return 2;
        }
        return 0;
    }

    public String getPlayer(int player)
    {
        switch(player)
        {
            case 1:
                return player1;
            case 2:
                return player2;
            case 3:
                return player3;
            case 4:
                return player4;
        }
        return null;
    }

    public boolean isBlue(int player)
    {
        switch(player)
        {
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return false;
            case 4:
                return false;
        }
        return false;
    }

    public int getPartner(int player)
    {
        switch(player)
        {
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 4;
            case 4:
                return 3;
        }
        return 0;
    }

    public int getRoundScoreDifference()
    {
        return redRoundScore - blueRoundScore;
    }
}
