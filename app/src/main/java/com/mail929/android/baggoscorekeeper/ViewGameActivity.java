package com.mail929.android.baggoscorekeeper;

import android.content.Context;
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
        list.setAdapter(new ArrayAdapter<Board>(this, R.layout.listitem_board, R.id.redName, game.boards)
        {
            public View getView(final int position, View convertView, ViewGroup parent)
            {
                View view;
                if (convertView == null)
                {
                    LayoutInflater infl = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = infl.inflate(R.layout.listitem_board, parent, false);
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
        
        Board aTotals = new Board();
        Board bTotals = new Board();

        boolean a = false;
        for(int i = 0; i < game.boards.size(); i++)
        {
            a = !a;
            Board b = game.boards.get(i);
            if(a)
            {
                aTotals.redName = b.redName;
                aTotals.redOn += b.redOn;
                aTotals.redIn += b.redIn;
                aTotals.redOff += b.redOff;
                aTotals.redKnockedIn += b.redKnockedIn;
                aTotals.redKnockedOff += b.redKnockedOff;

                aTotals.blueName = b.blueName;
                aTotals.blueOn += b.blueOn;
                aTotals.blueIn += b.blueIn;
                aTotals.blueOff += b.blueOff;
                aTotals.blueKnockedIn += b.blueKnockedIn;
                aTotals.blueKnockedOff += b.blueKnockedOff;
            }
            else
            {
                bTotals.redName = b.redName;
                bTotals.redOn += b.redOn;
                bTotals.redIn += b.redIn;
                bTotals.redOff += b.redOff;
                bTotals.redKnockedIn += b.redKnockedIn;
                bTotals.redKnockedOff += b.redKnockedOff;

                bTotals.blueName = b.blueName;
                bTotals.blueOn += b.blueOn;
                bTotals.blueIn += b.blueIn;
                bTotals.blueOff += b.blueOff;
                bTotals.blueKnockedIn += b.blueKnockedIn;
                bTotals.blueKnockedOff += b.blueKnockedOff;
            }
        }

        ((TextView) findViewById(R.id.ra_name)).setText(aTotals.redName);
        ((TextView) findViewById(R.id.ra_on)).setText(Integer.toString(aTotals.redOn));
        ((TextView) findViewById(R.id.ra_in)).setText(Integer.toString(aTotals.redIn));
        ((TextView) findViewById(R.id.ra_missed)).setText(Integer.toString(aTotals.redOff));
        ((TextView) findViewById(R.id.ra_kin)).setText(Integer.toString(aTotals.redKnockedIn));
        ((TextView) findViewById(R.id.ra_koff)).setText(Integer.toString(aTotals.redKnockedOff));
        
        ((TextView) findViewById(R.id.ba_name)).setText(aTotals.blueName);
        ((TextView) findViewById(R.id.ba_on)).setText(Integer.toString(aTotals.blueOn));
        ((TextView) findViewById(R.id.ba_in)).setText(Integer.toString(aTotals.blueIn));
        ((TextView) findViewById(R.id.ba_missed)).setText(Integer.toString(aTotals.blueOff));
        ((TextView) findViewById(R.id.ba_kin)).setText(Integer.toString(aTotals.blueKnockedIn));
        ((TextView) findViewById(R.id.ba_koff)).setText(Integer.toString(aTotals.blueKnockedOff));

        ((TextView) findViewById(R.id.rb_name)).setText(bTotals.redName);
        ((TextView) findViewById(R.id.rb_on)).setText(Integer.toString(bTotals.redOn));
        ((TextView) findViewById(R.id.rb_in)).setText(Integer.toString(bTotals.redIn));
        ((TextView) findViewById(R.id.rb_missed)).setText(Integer.toString(bTotals.redOff));
        ((TextView) findViewById(R.id.rb_kin)).setText(Integer.toString(bTotals.redKnockedIn));
        ((TextView) findViewById(R.id.rb_koff)).setText(Integer.toString(bTotals.redKnockedOff));

        ((TextView) findViewById(R.id.bb_name)).setText(bTotals.blueName);
        ((TextView) findViewById(R.id.bb_on)).setText(Integer.toString(bTotals.blueOn));
        ((TextView) findViewById(R.id.bb_in)).setText(Integer.toString(bTotals.blueIn));
        ((TextView) findViewById(R.id.bb_missed)).setText(Integer.toString(bTotals.blueOff));
        ((TextView) findViewById(R.id.bb_kin)).setText(Integer.toString(bTotals.blueKnockedIn));
        ((TextView) findViewById(R.id.bb_koff)).setText(Integer.toString(bTotals.blueKnockedOff));
    }
}
