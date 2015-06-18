package com.mail929.android.baggoscorekeeper;

import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Created by mail929 on 6/9/15.
 */
public class IO
{
    static IO instance;
    File file;
    List<Game> games = new ArrayList<>();

    public static IO getInstance()
    {
        if(instance == null)
        {
            instance = new IO();
        }
        return instance;
    }

    public IO()
    {
        File dir = Environment.getExternalStoragePublicDirectory("Baggo");
        dir.mkdirs();
        file = new File(dir, "games.json");
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void save() throws JSONException, IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        JSONArray jGames = new JSONArray();
        for(int i = 0; i < games.size(); i++)
        {
            JSONObject jGame = new JSONObject();
            Game game = games.get(i);
            jGame.put("isFirstTo", game.isFirstTo);
            jGame.put("complete", game.complete);
            jGame.put("goal", game.goal);
            jGame.put("throwsAllowed", game.throwsAllowed);
            jGame.put("player1", game.player1);
            jGame.put("player2", game.player2);
            jGame.put("player3", game.player3);
            jGame.put("player4", game.player4);
            jGame.put("currentRound", game.currentRound);
            jGame.put("currentSide", game.currentSide);
            jGame.put("currentPlayer", game.currentPlayer);
            jGame.put("currentThrow", game.currentThrow);
            jGame.put("isSecond", game.isSecond);
            jGame.put("blueScore", game.blueScore);
            jGame.put("redScore", game.redScore);
            jGame.put("blueRoundScore", game.blueRoundScore);
            jGame.put("redRoundScore", game.redRoundScore);
            jGame.put("redOn", game.board.redOn);
            jGame.put("redIn", game.board.redIn);
            jGame.put("redOff", game.board.redOff);
            jGame.put("redKnockedIn", game.board.redKnockedIn);
            jGame.put("redKnockedOff", game.board.redKnockedOff);
            jGame.put("blueOn", game.board.blueOn);
            jGame.put("blueIn", game.board.blueIn);
            jGame.put("blueOff", game.board.blueOff);
            jGame.put("blueKnockedIn", game.board.blueKnockedIn);
            jGame.put("blueKnockedOff", game.board.blueKnockedOff);
            JSONArray jBoards = new JSONArray();
            for(int j = 0; j < game.boards.size(); j++)
            {
                Board board = game.boards.get(j);
                JSONObject jBoard = new JSONObject();
                jBoard.put("redName", board.redName);
                jBoard.put("redOn", board.redOn);
                jBoard.put("redIn", board.redIn);
                jBoard.put("redOff", board.redOff);
                jBoard.put("redKnockedIn", board.redKnockedIn);
                jBoard.put("redKnockedOff", board.redKnockedOff);
                jBoard.put("blueName", board.blueName);
                jBoard.put("blueOn", board.blueOn);
                jBoard.put("blueIn", board.blueIn);
                jBoard.put("blueOff", board.blueOff);
                jBoard.put("blueKnockedIn", board.blueKnockedIn);
                jBoard.put("blueKnockedOff", board.blueKnockedOff);
                jBoards.put(jBoard);
            }
            jGame.put("boards", jBoards);
            jGames.put(jGame);
        }
        bw.write(jGames.toString());
        bw.close();
    }

    public void read() throws JSONException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null)
        {
            sb.append(line);
        }

        JSONArray jGames = new JSONArray(sb.toString());
        for(int i = 0; i < jGames.length(); i++)
        {
            JSONObject jGame = jGames.getJSONObject(i);
            Game game = new Game(jGame.getBoolean("isFirstTo"), jGame.getInt("goal"), jGame.getInt("throwsAllowed"),
                    jGame.getString("player1"), jGame.getString("player2"), jGame.getString("player3"), jGame.getString("player4"), jGame.getBoolean("complete"));
            game.currentRound = jGame.getInt("currentRound");
            game.currentSide = jGame.getInt("currentSide");
            game.currentPlayer = jGame.getInt("currentPlayer");
            game.currentThrow = jGame.getInt("currentThrow");
            game.isSecond = jGame.getBoolean("isSecond");
            game.blueScore = jGame.getInt("blueScore");
            game.redScore = jGame.getInt("redScore");
            game.blueRoundScore = jGame.getInt("blueRoundScore");
            game.redRoundScore = jGame.getInt("redRoundScore");
            game.board.redOn = jGame.getInt("redOn");
            game.board.redIn = jGame.getInt("redIn");
            game.board.redOff = jGame.getInt("redOff");
            game.board.redKnockedIn = jGame.getInt("redKnockedIn");
            game.board.redKnockedOff = jGame.getInt("redKnockedOff");
            game.board.blueOn = jGame.getInt("blueOn");
            game.board.blueIn = jGame.getInt("blueIn");
            game.board.blueOff = jGame.getInt("blueOff");
            game.board.blueKnockedIn = jGame.getInt("blueKnockedIn");
            game.board.blueKnockedOff = jGame.getInt("blueKnockedOff");
            JSONArray jBoards = jGame.getJSONArray("boards");
            for(int j = 0; j < jBoards.length(); j++)
            {
                Board board = new Board();
                JSONObject jBoard = jBoards.getJSONObject(j);
                board.redName = jBoard.getString("redName");
                board.redOn = jBoard.getInt("redOn");
                board.redIn = jBoard.getInt("redIn");
                board.redOff = jBoard.getInt("redOff");
                board.redKnockedIn = jBoard.getInt("redKnockedIn");
                board.redKnockedOff = jBoard.getInt("redKnockedOff");
                board.blueName = jBoard.getString("blueName");
                board.blueOn = jBoard.getInt("blueOn");
                board.blueIn = jBoard.getInt("blueIn");
                board.blueOff = jBoard.getInt("blueOff");
                board.blueKnockedIn = jBoard.getInt("blueKnockedIn");
                board.blueKnockedOff = jBoard.getInt("blueKnockedOff");
                game.boards.add(board);
            }
            games.add(game);
        }
        br.close();
    }
}
