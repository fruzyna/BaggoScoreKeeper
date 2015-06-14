package com.mail929.android.baggoscorekeeper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by mail929 on 6/9/15.
 */
public class TextDialog extends DialogFragment
{
    String title = "NO TITLE";
    String message = "NO MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        message = getArguments().getString("message");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton("OKAY", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent intent = new Intent(getActivity(), ViewGameActivity.class);
                        intent.putExtra("Game", IO.getInstance().games.size() - 1);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}