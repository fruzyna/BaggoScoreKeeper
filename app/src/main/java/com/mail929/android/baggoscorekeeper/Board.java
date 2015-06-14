package com.mail929.android.baggoscorekeeper;

/**
 * Created by mail929 on 6/9/15.
 */
public class Board
{
    String redName = "";
    int redOn = 0;
    int redIn = 0;
    int redOff = 0;
    int redKnockedIn = 0;
    int redKnockedOff = 0;

    String blueName = "";
    int blueOn = 0;
    int blueIn = 0;
    int blueOff = 0;
    int blueKnockedIn = 0;
    int blueKnockedOff = 0;
    
    public void output()
    {
        System.out.println(redName);
        System.out.println(redOn);
        System.out.println(redIn);
        System.out.println(redOff);
        System.out.println(redKnockedIn);
        System.out.println(redKnockedOff);
        System.out.println("--------------------");
        System.out.println(blueName);
        System.out.println(blueOn);
        System.out.println(blueIn);
        System.out.println(blueOff);
        System.out.println(blueKnockedIn);
        System.out.println(blueKnockedOff);
    }
}
